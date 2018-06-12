package org.nalby.yobatis.core.structure.pom;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.util.TextUtil;
import org.nalby.yobatis.core.xml.AbstractXmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PomNode extends AbstractXmlParser {

    private final static String POM_TAG = "project";

    //The values in <properties></properties> and in <profile></profile>.
    private Map<String, String> properties;

    private String packaging;

    private Set<String> moduleNames;

    private List<PomNode> children;

    private Element root;

    private String artifactId;

    public boolean isContainer() {
        return TextUtil.isEmpty(packaging);
    }

    private void loadSubModuleNames() {
        moduleNames = new HashSet<>();
        Element modules = root.element("modules");
        if (modules == null) {
            return;
        }
        List<Element> moduleList = modules.elements("module");
        for (Element module: moduleList) {
            if (!TextUtil.isEmpty(module.getTextTrim())) {
                moduleNames.add(module.getTextTrim());
            }
        }
    }

    private void loadArtifactId() {
        Element e = root.element("artifactId");
        if (e == null) {
            throw new ProjectException("No artifactId found.");
        }
        artifactId = e.getTextTrim();
        if (TextUtil.isEmpty(artifactId)) {
            throw new ProjectException("No artifactId found.");
        }
    }

    public void addChild(PomNode pomNode) {
        if (!moduleNames.contains(pomNode.artifactId)) {
            throw new ProjectException(artifactId + " does not contain a module named " + pomNode.artifactId);
        }
        children.add(pomNode);
    }

    private void loadPackagingType() {
        Element e = root.element("packaging");
        if (e != null) {
            packaging = e.getTextTrim();
        }
    }

    private void loadProperties() {
        properties = new HashMap<>();
        Element propertiesElement = root.element("properties");
        if (propertiesElement != null) {
            for (Element e: propertiesElement.elements()) {
                if (!TextUtil.isEmpty(e.getTextTrim())) {
                    properties.put(e.getName().trim(), e.getTextTrim());
                }
            }
        }
        loadProfileProperties();
    }

    private boolean isProfileActive(Element profileElement) {
        Element activation = profileElement.element("activation");
        if (activation == null) {
            return false;
        }
        Element activeByDefault = activation.element("activeByDefault");
        if (activeByDefault == null || activeByDefault.getText() == null) {
            return false;
        }
        return "true".equals(activeByDefault.getTextTrim());
    }

    private void loadProfileProperties() {
        Element profilesElement = root.element("profiles");
        if (profilesElement == null) {
            return;
        }
        List<Element> profileElements = profilesElement.elements("profile");
        for (Element profileElement : profileElements) {
            if (!isProfileActive(profileElement)) {
                continue;
            }
            Element propertiesElement = profileElement.element("properties");
            for (Element property: propertiesElement.elements()) {
                if (!TextUtil.isEmpty(property.getTextTrim())) {
                    properties.put(property.getName().trim(), property.getTextTrim());
                }
            }
        }
    }

    /**
     * Get property.
     * @param name property name
     * @return the property if found, null else.
     */
    public String getProperty(String name) {
        return properties.get(name);
    }

    public Set<String> getModuleNames() {
        return moduleNames;
    }

    /**
     * Use {@code parse(File file)} instead.
     * @param inputStream
     * @param rootElmentTag
     * @throws DocumentException
     * @throws IOException
     */
    public PomNode(InputStream inputStream, String rootElmentTag) throws DocumentException, IOException {
        super(inputStream, rootElmentTag);
        root = document.getRootElement();
        moduleNames = new HashSet<>();
        children = new LinkedList<>();
    }

    public static PomNode parse(File file) {
        try (InputStream inputStream = file.open()) {
            PomNode pomNode = new PomNode(inputStream, POM_TAG);
            pomNode.loadSubModuleNames();
            pomNode.loadPackagingType();
            pomNode.loadProperties();
            pomNode.loadArtifactId();
            return pomNode;
        } catch (ProjectException e) {
            throw  e;
        } catch (Exception e) {
            throw new ProjectException(file.name() + " is not a valid pom file.");
        }
    }

    public List<PomNode> getChildren() {
        return children;
    }
}
