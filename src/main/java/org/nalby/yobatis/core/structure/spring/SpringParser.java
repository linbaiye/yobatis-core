package org.nalby.yobatis.core.structure.spring;

import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.util.FolderUtil;

import java.util.HashSet;
import java.util.Set;

public class SpringParser implements Spring {

    private Set<SpringNode> springNodes;

    private SpringParser() {
        springNodes = new HashSet<>();
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
                return tmp;
            }
        }
        return null;
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


    public static SpringParser parse(Folder projectFolder) {
        Set<File> fileSet = FolderUtil.listAllFiles(projectFolder);
        SpringParser spring = new SpringParser();
        for (File file : fileSet) {
            if (file.path().endsWith(".xml")) {
                try {
                    spring.addNode(SpringNode.parse(file));
                } catch (Exception e) {
                    //ignore
                }
            }
        }
        return spring;
    }


}
