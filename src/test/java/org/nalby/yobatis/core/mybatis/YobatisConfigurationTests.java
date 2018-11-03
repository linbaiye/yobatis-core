package org.nalby.yobatis.core.mybatis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class YobatisConfigurationTests {

    private Project project;

    private File file;

    private Settings settings;

    private List<TableElement> tableElementList;

    private String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/mysql-connector-java.jar\"/>\n" +
            "  <context id=\"yobatis\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/yobatis?characterEncoding=utf-8\" userId=\"yobatis\" password=\"yobatis\"/>\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"true\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"org.yobatis.entity\" targetProject=\"/yobatis/src/main/java\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/yobatis/src/main/resources\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"org.yobatis.dao\" targetProject=\"/yobatis1/src/main/java\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>";

    @Before
    public void setup() {
        project = mock(Project.class);
        file = mock(File.class);
        when(file.open()).thenReturn(new ByteArrayInputStream(fileContent.getBytes()));
        when(project.findFile(any())).thenReturn(file);
        settings = new Settings();
        tableElementList = new LinkedList<>();
    }

    @Test
    public void loadPresence() {
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        settings = configure.getSettings();
        assertEquals("yobatis", settings.getPassword());
        assertEquals("yobatis", settings.getUser());
        assertEquals("jdbc:mysql://localhost:3306/yobatis?characterEncoding=utf-8", settings.getUrl());
        assertEquals("org.yobatis.entity", settings.getEntityPackage());
        assertEquals("/yobatis/src/main/java", settings.getEntityPath());
        assertEquals("/yobatis1/src/main/java", settings.getDaoPath());
        assertEquals("org.yobatis.dao", settings.getDaoPackage());
    }

    @Test
    public void loadfresh() {
        when(project.findFile(any())).thenReturn(null);
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        Settings settings = configure.getSettings();
        assertEquals("", settings.getPassword());
        assertEquals("", settings.getUser());
        assertEquals("", settings.getUrl());
        assertEquals("", settings.getEntityPackage());
        assertEquals("", settings.getEntityPath());
        assertEquals("", settings.getDaoPath());
        assertEquals("", settings.getDaoPackage());
    }

    @Test
    public void update() {
        settings.setPassword("newpassword");
        settings.setUser("newuser");
        settings.setUrl("newurl");
        settings.setDaoPath("newdaopath");
        settings.setDaoPackage("newdaopackage");
        settings.setEntityPackage("newentitypackage");
        settings.setEntityPath("newentitypath");
        settings.setXmlPath("newxmlpath");
        settings.setConnectorPath("newConnector");
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        configure.update(settings);
        settings = configure.getSettings();
        assertEquals("newpassword", settings.getPassword());
        assertEquals("newuser", settings.getUser());
        assertEquals("newurl", settings.getUrl());
        assertEquals("newdaopath", settings.getDaoPath());
        assertEquals("newdaopackage", settings.getDaoPackage());
        assertEquals("newentitypackage", settings.getEntityPackage());
        assertEquals("newentitypath", settings.getEntityPath());
        assertEquals("newxmlpath", settings.getXmlPath());
        assertEquals("newConnector", settings.getConnectorPath());
    }

    @Test(expected = InvalidMybatisGeneratorConfigException.class)
    public void whenFieldNull() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
                "<generatorConfiguration>\n" +
                "  <classPathEntry location=\"/mysql-connector-java.jar\"/>\n" +
                "  <context id=\"yobatis\" targetRuntime=\"MyBatis3\">\n" +
                "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
                "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/yobatis?characterEncoding=utf-8\" password=\"yobatis\"/>\n" +
                "    <javaTypeResolver>\n" +
                "      <property name=\"forceBigDecimals\" value=\"true\"/>\n" +
                "    </javaTypeResolver>\n" +
                "    <javaModelGenerator targetPackage=\"org.yobatis.entity\" targetProject=\"/yobatis/src/main/java\"/>\n" +
                "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/yobatis/src/main/resources\"/>\n" +
                "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"org.yobatis.dao\" targetProject=\"/yobatis1/src/main/java\"/>\n" +
                "    <table tableName=\"Yobatis\" schema=\"yobatis\" modelType=\"flat\">\n" +
                "      <generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>\n" +
                "    </table>\n" +
                "  </context>\n" +
                "</generatorConfiguration>";
        when(file.open()).thenReturn(new ByteArrayInputStream(fileContent.getBytes()));
        settings.setUser("newuser");
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        configure.update(settings);
    }

    @Test(expected = InvalidMybatisGeneratorConfigException.class)
    public void whenBadFile() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
                "<generatorConfiguration />\n" +
                "  <classPathEntry location=\"/mysql-connector-java.jar\"/>\n" +
                "  <context id=\"yobatis\" targetRuntime=\"MyBatis3\">\n" +
                "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
                "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/yobatis?characterEncoding=utf-8\" password=\"yobatis\"/>\n" +
                "    <javaTypeResolver>\n" +
                "      <property name=\"forceBigDecimals\" value=\"true\"/>\n" +
                "    </javaTypeResolver>\n" +
                "    <javaModelGenerator targetPackage=\"org.yobatis.entity\" targetProject=\"/yobatis/src/main/java\"/>\n" +
                "    <sqlMapGenerator targetPackage=\"mybatis-mappers\" targetProject=\"/yobatis/src/main/resources\"/>\n" +
                "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"org.yobatis.dao\" targetProject=\"/yobatis1/src/main/java\"/>\n" +
                "    <table tableName=\"Yobatis\" schema=\"yobatis\" modelType=\"flat\">\n" +
                "      <generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>\n" +
                "    </table>\n" +
                "  </context>\n" +
                "</generatorConfiguration>";
        when(file.open()).thenReturn(new ByteArrayInputStream(fileContent.getBytes()));
        YobatisConfiguration.open(project);
    }

    private TableElement addTable(String name, boolean enable) {
        TableElement tableElement = new TableElement(name, enable);
        tableElementList.add(tableElement);
        return tableElement;
    }

    @Test
    public void updateTable() {
        addTable("table1", false);
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        configure.update(tableElementList);
        when(project.createFile(anyString())).thenReturn(file);
        doAnswer(invocationOnMock -> {
            String content = (String)invocationOnMock.getArguments()[0];
            assertTrue(content.contains("<table tableName=\"table1\" modelType=\"flat\"><property name=\"enable\" value=\"false\"/></table>"));
            return null;
        }).when(file).write(anyString());
        configure.flush();

        // Enable and add again.
        addTable("table1", true);
        addTable("table2", true);
        configure.update(tableElementList);
        doAnswer(invocationOnMock -> {
            String content = (String)invocationOnMock.getArguments()[0];
            assertTrue(content.contains("<table tableName=\"table1\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table><table tableName=\"table2\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table>"));
            return null;
        }).when(file).write(anyString());
        configure.flush();
    }

    @Test
    public void listTable() {
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        assertTrue(configure.listTableElementAsc().isEmpty());

        addTable("table2", true);
        addTable("table1", true);
        configure.update(tableElementList);
        when(project.createFile(anyString())).thenReturn(file);
        doAnswer(invocationOnMock -> {
            String content = (String)invocationOnMock.getArguments()[0];
            assertTrue(content.contains("<table tableName=\"table2\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table><table tableName=\"table1\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table>"));
            return null;
        }).when(file).write(anyString());
        configure.flush();

        List<TableElement> list = configure.listTableElementAsc();
        assertEquals(2, list.size());
        assertEquals("table1", list.get(0).getName());
        assertEquals("table2", list.get(1).getName());
    }

    @Test
    public void asStringWithoutDisabledTables() {
        addTable("table2", false);
        addTable("table1", true);
        YobatisConfiguration configure = YobatisConfiguration.open(project);
        configure.update(tableElementList);
        String content = configure.asStringWithoutDisabledTables();
        assertFalse(content.contains("<table tableName=\"table2\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table>"));
        assertTrue(content.contains("<table tableName=\"table1\" modelType=\"flat\"><property name=\"enable\" value=\"true\"/></table>"));
    }

}
