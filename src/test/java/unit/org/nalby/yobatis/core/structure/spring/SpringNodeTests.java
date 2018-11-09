package unit.org.nalby.yobatis.core.structure.spring;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.spring.SpringNode;
import unit.org.nalby.yobatis.core.util.TestUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringNodeTests {

    private final static String[] DATASOURCE_CLASSES = {"org.apache.commons.dbcp.BasicDataSource", "com.alibaba.druid.pool.DruidDataSource"};

    private File file;

    @Before
    public void setup() {
        file = mock(File.class);
    }

    private void setFileContent(String content) {
        when(file.open()).thenReturn(new ByteArrayInputStream(content.getBytes()));
    }

    private SpringNode parse(String content) {
        setFileContent(content);
        return SpringNode.parse(file);
    }

    @Test
    public void noDataSource() throws DocumentException, IOException {
        setFileContent("<beans><bean class=\"org.test.Clazz\" /></beans>");
        SpringNode springNode = SpringNode.parse(file);
        assertNull(springNode.getDbUsername());
        assertTrue(springNode.getImportLocations().isEmpty());
        assertTrue(springNode.getPropertyFileLocations().isEmpty());
        setFileContent("<beans:beans xmlns:beans=\"http://test.com/beans\"></beans:beans>");
        assertNull(springNode.getDbUsername());
    }

    @Test
    public void noUsername() throws DocumentException, IOException {
        setFileContent("<beans><bean class=\"org.apache.commons.dbcp.BasicDataSource\" /></beans>");
        SpringNode springNode = SpringNode.parse(file);
        assertNull(springNode.getDbUsername());
    }

    @Test
    public void usernameInPNamespace() throws DocumentException, IOException {
        for (String clazz: DATASOURCE_CLASSES) {
            setFileContent("<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"" +
                    clazz + "\" p:username=\"test\"/></beans>");
            SpringNode springNode = SpringNode.parse(file);
            assertEquals("test", springNode.getDbUsername());
        }
    }

    @Test
    public void usernameInProperty() throws DocumentException, IOException {
        for (String clazz: DATASOURCE_CLASSES) {
            setFileContent("<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"" +
                    clazz + "\"><property name=\"username\" value=\"test\"/></bean></beans>");
            SpringNode springNode = SpringNode.parse(file);
            assertEquals("test", springNode.getDbUsername());
        }
    }

    @Test
    public void importAnotherSpringXmlFile() throws DocumentException, IOException {
        setFileContent("<beans><import resource=\"classpath:test.config\"/><import resource=\"test.config\"/></beans>");
        SpringNode springNode = SpringNode.parse(file);
        Set<String> importedConfigFiles = springNode.getImportLocations();
        TestUtil.assertCollectionSizeAndStringsIn(importedConfigFiles, 2, "test.config",
                "classpath:test.config");
    }

    @Test
    public void noPropertyFiles() throws DocumentException, IOException {
        String xml = "<beans><bean id=\"propertyConfigurer\" " +
                "class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" /></bean></beans>";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertTrue(locations.isEmpty());
    }

    @Test
    public void propertyFilesInConfigureBean() throws DocumentException, IOException {
        String xml = "<beans><bean id=\"propertyConfigurer\" " +
                "class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" />" +
                "<property name=\"locations\"><list><value>classpath:conf/important.properties</value></list></property></bean></beans>";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertEquals(1, locations.size());
        for (String location: locations) {
            assertTrue(location.equals("classpath:conf/important.properties"));
        }
    }

    @Test
    public void absolutePropertiesPath() throws DocumentException, IOException {
        String xml = "<beans><bean id=\"propertyConfigurer\" " +
                "class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" />" +
                "<property name=\"locations\"><list><value>/important.properties</value></list></property></bean></beans>";
        SpringNode parser =  parse(xml);
        Set<String> locations = parser.getPropertyFileLocations();
        TestUtil.assertCollectionSizeAndStringsIn(locations, 1, "/important.properties");
    }

    @Test
    public void trimPropertiesValue() throws DocumentException, IOException {
        String xml = "<beans><bean id=\"propertyConfigurer\" " +
                "class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" />" +
                "<property name=\"locations\"><list><value>  classpath:conf/important.properties   </value></list></property></bean></beans>";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertEquals(1, locations.size());
        for (String location: locations) {
            assertTrue(location.equals("classpath:conf/important.properties"));
        }
    }

    @Test
    public void multiplePropertyFilesInConfigureBean() throws DocumentException, IOException {
        String xml = "<beans><bean id=\"propertyConfigurer\" " +
                "class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" />" +
                "<property name=\"locations\"><list><value>classpath:conf/important.properties</value><value>classpath:conf/test.properties</value></list></property></bean></beans>";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertTrue(locations.size() == 2);
        for (String location: locations) {
            assertTrue(location.equals("classpath:conf/important.properties")
                    || location.equals("classpath:conf/test.properties"));
        }
    }

    @Test
    public void mutiplePropertyFileInPlaceholderTag() throws DocumentException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                "	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "	xmlns:context=\"http://www.springframework.org/schema/context\"\n" +
                "	xmlns:aop=\"http://www.springframework.org/schema/aop\"\n" +
                "	xmlns:mvc=\"http://www.springframework.org/schema/mvc\"\n" +
                "	xmlns:mybatis=\"http://mybatis.org/schema/mybatis-spring\"\n" +
                "	xsi:schemaLocation=\"http://www.springframework.org/schema/beans \n" +
                "       		http://www.springframework.org/schema/beans/spring-beans.xsd \n" +
                "       		http://www.springframework.org/schema/context \n" +
                "       		http://www.springframework.org/schema/context/spring-context.xsd\n" +
                "       		http://www.springframework.org/schema/aop \n" +
                "       		http://www.springframework.org/schema/aop/spring-aop.xsd\n" +
                "       		http://www.springframework.org/schema/mvc\n" +
                "       		http://www.springframework.org/schema/mvc/spring-mvc.xsd\n" +
                "       		http://mybatis.org/schema/mybatis-spring\n" +
                "       		http://mybatis.org/schema/mybatis-spring.xsd\">\n" +
                "<context:property-placeholder location=\"classpath:conf/test.properties, classpath:conf/important.properties\"/>" +
                "</beans>\n";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertTrue(locations.size() == 2);
        for (String location : locations) {
            assertTrue(location.equals("classpath:conf/important.properties")
                    || location.equals("classpath:conf/test.properties"));
        }
    }

    @Test
    public void mixedPropertyFiles() throws DocumentException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                "	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "	xmlns:context=\"http://www.springframework.org/schema/context\"\n" +
                "	xmlns:aop=\"http://www.springframework.org/schema/aop\"\n" +
                "	xmlns:mvc=\"http://www.springframework.org/schema/mvc\"\n" +
                "	xmlns:mybatis=\"http://mybatis.org/schema/mybatis-spring\"\n" +
                "	xsi:schemaLocation=\"http://www.springframework.org/schema/beans \n" +
                "       		http://www.springframework.org/schema/beans/spring-beans.xsd \n" +
                "       		http://www.springframework.org/schema/context \n" +
                "       		http://www.springframework.org/schema/context/spring-context.xsd\n" +
                "       		http://www.springframework.org/schema/aop \n" +
                "       		http://www.springframework.org/schema/aop/spring-aop.xsd\n" +
                "       		http://www.springframework.org/schema/mvc\n" +
                "       		http://www.springframework.org/schema/mvc/spring-mvc.xsd\n" +
                "       		http://mybatis.org/schema/mybatis-spring\n" +
                "       		http://mybatis.org/schema/mybatis-spring.xsd\">\n" +
                "<context:property-placeholder location=\"classpath:conf/test.properties, classpath:conf/important.properties\"/>" +
                "<bean id=\"propertyConfigurer\" class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">" +
                "<property name=\"systemPropertiesModeName\" value=\"SYSTEM_PROPERTIES_MODE_OVERRIDE\" />" +
                "<property name=\"ignoreResourceNotFound\" value=\"true\" />" +
                "<property name=\"locations\"><list><value>classpath:conf/important.properties</value></list></property></bean> "+
                "</beans>\n";
        SpringNode springNode = parse(xml);
        Set<String> locations = springNode.getPropertyFileLocations();
        assertTrue(locations.size() == 2);
        for (String location : locations) {
            assertTrue(location.equals("classpath:conf/important.properties")
                    || location.equals("classpath:conf/test.properties"));
        }
    }

    @Test
    public void noBuiltinDataSource() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.Datasource\"" +
                " p:username=\"test\" p:password=\"username\" p:url=\"testurl\"/></beans>";
        SpringNode springNode = parse(xml);
        assertEquals("test", springNode.getDbUsername());

        xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.Datasource\">"
                + "<property name=\"username\" value=\"test\"/>"
                + "<property name=\"password\" value=\"test\"/>"
                + "<property name=\"url\" value=\"url\"/>"
                + "<property name=\"driverClassName\" value=\"driver\"/>"
                + "</bean></beans>";
        springNode = parse(xml);
        assertEquals("test", springNode.getDbUsername());
        assertEquals("test", springNode.getDbPassword());
        assertEquals("url", springNode.getDbUrl());
    }

    @Test
    public void hasNoDatasource() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.NoClass\"" +
                " p:username=\"test\" p:password=\"username\" p:url=\"testurl\"/></beans>";
        SpringNode springNode = parse(xml);
        assertNull(springNode.getDbPassword());
        assertNull(springNode.getDbUrl());
    }

    @Test
    public void notEnoughDBProperties() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.Datasource\"" +
                " p:username=\"test\" p:password=\"username\"/></beans>";
        SpringNode springNode = parse(xml);
        assertNull(springNode.getDbPassword());

        xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.Datasource\">"
                + "<property name=\"username\" value=\"test\"/>"
                + "<property name=\"password\" value=\"test\"/>"
                + "</bean></beans>";
        springNode = parse(xml);
        assertNull(springNode.getDbPassword());
    }

    @Test
    public void placeholderConfiguerSingleLocation() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\"" +
                " p:location=\"classpath:test.properties\" p:password=\"username\"></bean></beans>";
        SpringNode node = parse(xml);
        TestUtil.assertCollectionEqual(node.getPropertyFileLocations(), "classpath:test.properties");

        xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"location\" value=\"classpath:test1.properties\"/>"
                + "</bean></beans>";
        node = parse(xml);
        TestUtil.assertCollectionEqual(node.getPropertyFileLocations(), "classpath:test1.properties");

        xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"location\"><value>classpath:test1.properties</value></property>"
                + "</bean></beans>";
        node = parse(xml);
        TestUtil.assertCollectionEqual(node.getPropertyFileLocations(), "classpath:test1.properties");
    }

    @Test
    public void misconfigedPlaceholderLocation() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"location\">"
                + "<value>classpath:test1.properties</value>"
                + "<value>classpath:test2.properties</value>"
                + "</property>"
                + "</bean></beans>";
        SpringNode springNode = parse(xml);
        assertTrue(springNode.getPropertyFileLocations().isEmpty());
    }

    @Test
    public void singleValueInLocations() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"locations\">"
                + "<value>classpath:test1.properties</value>"
                + "</property>"
                + "</bean></beans>";
        SpringNode springNode = parse(xml);
        TestUtil.assertCollectionEqual(springNode.getPropertyFileLocations(), "classpath:test1.properties");
    }

    @Test
    public void setValuesInLocations() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"locations\">"
                + "<set>"
                + "<value>classpath:test1.properties</value>"
                + "</set>"
                + "</property>"
                + "</bean></beans>";
        SpringNode springNode = parse(xml);
        TestUtil.assertCollectionEqual(springNode.getPropertyFileLocations(), "classpath:test1.properties");
    }

    @Test
    public void emptyLocations() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">"
                + "<property name=\"locations\">"
                + "</property>"
                + "</bean></beans>";
        SpringNode node = parse(xml);
        assertTrue(node.getPropertyFileLocations().isEmpty());
    }

    @Test
    public void nonstandardSpringConfigurer() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.omg.PlaceholderResolver\">"
                + "<property name=\"locations\">"
                + "<value>classpath:test1.properties</value>"
                + "</property>"
                + "</bean></beans>";
        SpringNode node = parse(xml);
        TestUtil.assertCollectionEqual(node.getPropertyFileLocations(), "classpath:test1.properties");

        xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.omg.PlaceholderResolver\">"
                + "<property name=\"locations\">"
                + "<value>classpath:test1.prooep</value>"
                + "</property>"
                + "</bean></beans>";
        node = parse(xml);
        assertTrue(node.getPropertyFileLocations().isEmpty());
    }

    @Test
    public void nonstandardSpringLocations() throws DocumentException, IOException {
        String xml = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">"
                + "<bean class=\"org.test.Resolver\">"
                + "<property name=\"locations\">"
                + "<list>"
                + "<value>classpath:test1.properties</value>"
                + "</list>"
                + "</property>"
                + "</bean></beans>";
        SpringNode springNode = parse(xml);
        TestUtil.assertCollectionEqual(springNode.getPropertyFileLocations(), "classpath:test1.properties");
    }

}
