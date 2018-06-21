package org.nalby.yobatis.core.mybatis;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.database.Table;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MybatisGeneratorContextTests {

    private MybatisGeneratorContext.Builder contextBuilder;

    private String daoPath;

    private String modelPath;

    private String resourcePath;

    private List<Table> tableList;

    private String dbUser;

    private String dbPassword;

    private String dbUrl;

    private String dbDriver;

    private String contextId;

    private MybatisGeneratorContext context;

    @Before
    public void setup() {
        this.daoPath = "/yobatis/src/main/java/dao";

        this.modelPath = "/yobatis/src/main/java/model";

        this.resourcePath = "/yobatis/src/main/resources";

        this.tableList = new LinkedList<>();

        this.dbDriver = "driver";

        this.dbPassword = "password";

        this.dbUser = "user";

        this.dbUrl = "jdbc:mysql://localhost/yobatis";

        this.contextId = "yobatis";

        initContextBuilder();

    }

    private void initContextBuilder() {
        contextBuilder = new MybatisGeneratorContext.Builder();

        contextBuilder.setContextId(contextId);
        contextBuilder.setDbDriver(dbDriver);
        contextBuilder.setDbPassword(dbPassword);
        contextBuilder.setDbUrl(dbUrl);
        contextBuilder.setDbUser(dbUser);
        contextBuilder.setResourcePath(resourcePath);
        contextBuilder.setModelPath(modelPath);
        contextBuilder.setDaoPath(daoPath);
        contextBuilder.setTableList(tableList);
    }

    private Element findElement(String tagName) {
        Element element = context.getContext();
        return element.element(tagName);
    }

    private List<Element> findElements(String tagName) {
        Element element = context.getContext();
        return element.elements(tagName);
    }

    private boolean hasAttr(Element element, String attrName, String attrVal) {
        return attrVal.equals(element.attributeValue(attrName));
    }

    private void addTable() {
        Table table = new Table("test");
        table.addAutoIncColumn("pk");
        table.addPrimaryKey("pk");
        tableList.add(table);
    }

    @Test
    public void fullAttr() {
        addTable();
        context = contextBuilder.build();
        Element plugin = findElement("plugin");
        assertTrue(hasAttr(plugin, "type", "org.mybatis.generator.plugins.YobatisDaoPlugin"));
        Element jdbcConnection = findElement("jdbcConnection");
        assertEquals("driver", jdbcConnection.attributeValue("driverClass"));
        assertEquals("jdbc:mysql://localhost/yobatis", jdbcConnection.attributeValue("connectionURL"));
        assertEquals("password", jdbcConnection.attributeValue("password"));
        Element javaModelGenerator = findElement("javaModelGenerator");
        assertEquals("model", javaModelGenerator.attributeValue("targetPackage"));
        assertEquals("/yobatis/src/main/java", javaModelGenerator.attributeValue("targetProject"));
        Element sqlMapGenerator = findElement("sqlMapGenerator");
        assertEquals("mybatis-mappers", sqlMapGenerator.attributeValue("targetPackage"));
        assertEquals("/yobatis/src/main/resources", sqlMapGenerator.attributeValue("targetProject"));
        Element javaClientGenerator = findElement("javaClientGenerator");
        assertEquals("dao", javaClientGenerator.attributeValue("targetPackage"));
        assertEquals("/yobatis/src/main/java", javaClientGenerator.attributeValue("targetProject"));
        assertEquals("XMLMAPPER", javaClientGenerator.attributeValue("type"));

        List<Element> tableElements = findElements("table");
        assertEquals(1, tableElements.size());
        Element tableElement = tableElements.get(0);
        assertEquals("yobatis", tableElement.attributeValue("schema"));
    }


    @Test
    public void emptyAttr() throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(this, null);
        }
        this.contextId = "yobatis";
        initContextBuilder();
        context = contextBuilder.build();
        Element plugin = findElement("plugin");
        assertTrue(hasAttr(plugin, "type", "org.mybatis.generator.plugins.YobatisDaoPlugin"));
        Element jdbcConnection = findElement("jdbcConnection");
        assertEquals("", jdbcConnection.attributeValue("driverClass"));
        assertEquals("", jdbcConnection.attributeValue("connectionURL"));
        assertEquals("", jdbcConnection.attributeValue("password"));
        Element javaModelGenerator = findElement("javaModelGenerator");
        assertEquals("", javaModelGenerator.attributeValue("targetPackage"));
        assertEquals("", javaModelGenerator.attributeValue("targetProject"));
        Element sqlMapGenerator = findElement("sqlMapGenerator");
        assertEquals("mybatis-mappers", sqlMapGenerator.attributeValue("targetPackage"));
        assertEquals("", sqlMapGenerator.attributeValue("targetProject"));
        Element javaClientGenerator = findElement("javaClientGenerator");
        assertEquals("", javaClientGenerator.attributeValue("targetPackage"));
        assertEquals("", javaClientGenerator.attributeValue("targetProject"));
        assertEquals("XMLMAPPER", javaClientGenerator.attributeValue("type"));
    }
}
