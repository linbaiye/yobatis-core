package org.nalby.yobatis.core.mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.log.LogFactory;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Expose interfaces to the view layer.
 */
public class YobatisConfiguration {

    private final static String FILE_NAME = "mybatisGeneratorConfig.xml";

    private final static String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"\"/>\n" +
            "  <context id=\"yobatis\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"\" userId=\"\" password=\"\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"true\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"\" targetProject=\"\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"\" targetProject=\"\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"\" targetProject=\"\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>";

    private Document document;

    private Project project;

    private final static Logger LOGGER = LogFactory.getLogger(YobatisConfiguration.class);

    private YobatisConfiguration(Document document, Project project) {
        this.document = document;
        this.project = project;
    }

    private void upsert(String xpath, String value) {
        if (TextUtil.isEmpty(value)) {
            return;
        }
        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            throw new InvalidMybatisGeneratorConfigException("Invalid config file, please delete " + FILE_NAME + " and retry.");
        }
        node.setText(value);
    }

    private String getValue(String xpath) {
        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            throw new InvalidMybatisGeneratorConfigException("Invalid config file, please delete " + FILE_NAME + "and retry.");
        }
        return node.getText();
    }


    public void update(List<TableElement> tableElementList) {
        Node context = document.selectSingleNode("//context");
        if (context == null) {
            throw new InvalidMybatisGeneratorConfigException("Invalid config file, please delete " + FILE_NAME + "and retry.");
        }
        List<Node> nodeList = document.selectNodes("//context/table");
        nodeList.forEach(Node::detach);
        tableElementList.forEach(e -> ((Element)context).add(e.asElement()));
    }


    public Settings getSettings() {
        Settings settings = new Settings();
        settings.setUrl(getValue("//context/jdbcConnection/@connectionURL"));
        settings.setUser(getValue("//context/jdbcConnection/@userId"));
        settings.setPassword(getValue("//context/jdbcConnection/@password"));

        settings.setEntityPackage(getValue("//context/javaModelGenerator/@targetPackage"));
        settings.setEntityPath(getValue("//context/javaModelGenerator/@targetProject"));

        settings.setDaoPath(getValue("//context/javaClientGenerator/@targetProject"));
        settings.setDaoPackage(getValue("//context/javaClientGenerator/@targetPackage"));

        settings.setXmlPath(getValue("//context/sqlMapGenerator/@targetProject"));
        settings.setConnectorPath(getValue("//classPathEntry/@location"));
        return settings;
    }


    public List<TableElement> listTableElementAsc() {
        List<Node> nodeList = document.selectNodes("//context/table");
        if (nodeList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TableElement> list = nodeList.stream()
                .map(e -> TableElement.fromElement((Element)e))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(list);
        return list;
    }

    public void flush() {
        File file = project.createFile(FILE_NAME);
        file.write(document.asXML());
    }

    public void update(Settings setting) {
        upsert("//context/jdbcConnection/@connectionURL", setting.getUrl());
        upsert("//context/jdbcConnection/@userId", setting.getUser());
        upsert("//context/jdbcConnection/@password", setting.getPassword());
        upsert("//context/javaModelGenerator/@targetPackage", setting.getEntityPackage());
        upsert("//context/javaModelGenerator/@targetProject", setting.getEntityPath());
        upsert("//context/javaClientGenerator/@targetPackage", setting.getDaoPackage());
        upsert("//context/javaClientGenerator/@targetProject", setting.getDaoPath());
        upsert("//context/sqlMapGenerator/@targetProject", setting.getXmlPath());
        upsert("//classPathEntry/@location", setting.getConnectorPath());
    }

    public String asStringWithoutDisabledTables() {
        String content = this.document.asXML();
        try {
            Document document = buildSaxReader().read(new ByteArrayInputStream(content.getBytes()));
            List<Node> nodeList = document.selectNodes("//context/table/property[@name='enable' and @value='false']");
            nodeList.forEach(e -> e.getParent().detach());
            return document.asXML();
        } catch (DocumentException e) {
            throw new InvalidMybatisGeneratorConfigException(FILE_NAME + " is misconfigured, please delete or correct it first.");
        }
    }

    private static SAXReader buildSaxReader( ) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEntityResolver(GeneratorEntityResolver.ENTITY_RESOLVER);
        return saxReader;
    }

    public static YobatisConfiguration open(Project project) {
        SAXReader saxReader = buildSaxReader();
        File file = project.findFile(FILE_NAME);
        try {
            if (file == null) {
                return new YobatisConfiguration(saxReader.read(new ByteArrayInputStream(TEMPLATE.getBytes())), project);
            }
            try (InputStream inputStream = file.open()) {
                    // Validate it ?
                return new YobatisConfiguration(saxReader.read(inputStream), project);
            }
        } catch (DocumentException | IOException e) {
            LOGGER.error("Invalid configuration file:", e);
            throw new InvalidMybatisGeneratorConfigException(FILE_NAME + " is misconfigured, please delete or correct it first.");
        }
    }

}
