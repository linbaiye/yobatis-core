package org.nalby.yobatis.core.mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.nalby.yobatis.core.database.Table;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.log.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(YobatisConfiguration.class);

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
        List<Node> nodeList = document.selectNodes("//context/table");
        nodeList.forEach(Node::detach);
        tableElementList.forEach(e -> ((Element)context).add(e.asElement()));
    }


    /**
     * Sync table presence with database, dropping tables no longer exist, and adding new tables.
     * @param tableList current tables that exist in db.
     */
    public void sync(List<Table> tableList) {
        if (tableList == null || tableList.isEmpty()) {
            return;
        }
        Element context = (Element)document.selectSingleNode("//context");
        Set<String> names = tableList.stream().map(Table::getName).collect(Collectors.toSet());
        List<Node> nodeList = document.selectNodes("//context/table");
        Set<String> presence = new HashSet<>();
        // Drop tables that no longer exist.
        nodeList.forEach(e -> {
            if (!names.contains(((Element)e).attributeValue("tableName"))) {
                e.detach();
            } else {
                presence.add(((Element) e).attributeValue("tableName"));
            }
        });
        // Grab tables that we don't have.
        names.forEach(e -> {
            if (!presence.contains(e)) {
                context.add(new TableElement(e, false).asElement());
            }
        });
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

    public List<TableElement> disableAll() {
        List<Node> nodeList = document.selectNodes("//context/table/property[@name='enable']");
        nodeList.forEach(e -> {
            Node node = e.selectSingleNode("@value");
            if (node != null) {
                node.setText("false");
            }
        });
        return listTableElementAsc();
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
        upsert("//context/sqlMapGenerator/@targetPackage", "mybatis-mappers");
        upsert("//classPathEntry/@location", setting.getConnectorPath());
    }

    private String appendTimeoutIfAbsent() {
        String origin = getValue("//context/jdbcConnection/@connectionURL");
        String newUrl = TextUtil.addTimeoutToUrlIfAbsent(origin);
        upsert("//context/jdbcConnection/@connectionURL", newUrl);
        return origin;
    }

    public String asStringWithoutDisabledTables() {
        String orginUrl = appendTimeoutIfAbsent();
        String content = this.document.asXML();
        upsert("//context/jdbcConnection/@connectionURL", orginUrl);
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
        saxReader.setValidation(true);
        saxReader.setEntityResolver(GeneratorEntityResolver.ENTITY_RESOLVER);
        return saxReader;
    }

    /**
     * Open MybatisGenerator/Yobatis configuration if exists, or create a new one if not.
     * @param project
     * @return YobatisConfiguration
     * @throws InvalidMybatisGeneratorConfigException if current configuration file is invalid.
     */
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
            LOGGER.error("Invalid configuration file:{}", e);
            throw new InvalidMybatisGeneratorConfigException(FILE_NAME + " is misconfigured, please delete or correct it first.");
        }
    }

}
