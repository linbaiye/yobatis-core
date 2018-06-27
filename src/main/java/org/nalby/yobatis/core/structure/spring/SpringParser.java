package org.nalby.yobatis.core.structure.spring;

import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.util.FolderUtil;
import org.nalby.yobatis.core.util.PropertyUtil;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SpringParser implements Spring {

    private Set<SpringNode> springNodes;

    private Map<String, String> propertyInFile;

    private SpringParser() {
        springNodes = new HashSet<>();
        propertyInFile = new HashMap<>();
    }

    private void addNode(SpringNode node) {
        springNodes.add(node);
    }

    @FunctionalInterface
    private interface Selector {
        String select(SpringNode springNode);
    }

    private String selectDbProperty(Selector selector) {
        for (SpringNode springNode : springNodes) {
            String tmp = selector.select(springNode);
            if (tmp != null) {
                String property = PropertyUtil.valueOfPlaceholder(tmp);
                return propertyInFile.getOrDefault(property, tmp);
            }
        }
        return null;
    }

    private void addProperties(File file) {
        try (InputStream inputStream = file.open()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames()) {
                String text = properties.getProperty(key);
                if (TextUtil.isEmpty(text)) {
                    continue;
                }
                propertyInFile.put(key, text);
            }
        } catch (IOException e) {
            //
        }
    }

    @Override
    public String lookupDbUser() {
        return selectDbProperty(SpringNode::getDbUsername);
    }

    @Override
    public String lookupDbPassword() {
        return selectDbProperty(SpringNode::getDbPassword);
    }

    @Override
    public String lookupDbUrl() {
        return selectDbProperty(SpringNode::getDbUrl);
    }

    @Override
    public String lookupDbDriver() {
        return "com.mysql.jdbc.Driver";
    }

    private static void loadPropertiesFiles(Set<File> fileSet, SpringParser springParser, Set<String> propertiesLocations) {
        if (propertiesLocations == null || propertiesLocations.isEmpty()) {
            return;
        }
        for (String propertiesLocation : propertiesLocations) {
            String location = propertiesLocation.replaceAll("^classpath\\s*\\*?:(.*)$", "$1");
            for (File file : fileSet) {
                if (file.path().contains(location)) {
                    springParser.addProperties(file);
                }
            }
        }
    }


    public static SpringParser parse(Folder projectFolder) {
        Set<File> fileSet = FolderUtil.listAllFiles(projectFolder);
        SpringParser spring = new SpringParser();
        Set<String> propertyFileLocations = new HashSet<>();
        for (File file : fileSet) {
            if (file.path().contains("src/test") ||
                    !file.path().endsWith(".xml")) {
                //Ignore files contained in test dir.
                continue;
            }
            try {
                SpringNode springNode = SpringNode.parse(file);
                propertyFileLocations.addAll(springNode.getPropertyFileLocations());
                spring.addNode(springNode);
            } catch (Exception e) {
                //ignore
            }
        }
        loadPropertiesFiles(fileSet, spring, propertyFileLocations);
        return spring;
    }
}
