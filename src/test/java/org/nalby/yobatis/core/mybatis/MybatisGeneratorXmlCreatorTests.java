package org.nalby.yobatis.core.mybatis;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.database.DatabaseMetadataProvider;
import org.nalby.yobatis.core.database.Table;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MybatisGeneratorXmlCreatorTests {

    private DatabaseMetadataProvider databaseMetadataProvider;

    private List<Table> tableList;

    private MybatisGeneratorXmlCreator creator;

    private List<Folder> folderList;

    private Project project;

    @Before
    public void setup() {
        tableList = new LinkedList<>();
        databaseMetadataProvider = mock(DatabaseMetadataProvider.class);
        when(databaseMetadataProvider.getTables()).thenReturn(tableList);
        when(databaseMetadataProvider.getPassword()).thenReturn("");
        when(databaseMetadataProvider.getSchema()).thenReturn("");
        when(databaseMetadataProvider.getUrl()).thenReturn("");
        when(databaseMetadataProvider.getUsername()).thenReturn("");
        when(databaseMetadataProvider.getDriverClassName()).thenReturn("");
        project = mock(Project.class);
        when(project.getAbsPathOfSqlConnector()).thenReturn("mysql.jar");
        folderList = new LinkedList<>();
        when(project.listFolders()).thenReturn(folderList);
    }

    private Folder addFolder(String path) {
        Folder folder = mock(Folder.class);
        when(folder.path()).thenReturn(path);
        String[] tokens = path.split("/");
        when(folder.name()).thenReturn(tokens[tokens.length - 1]);
        folderList.add(folder);
        return folder;
    }

    private final static String BASE_SCPATH = "/yobatis/src/main/java";
    private final static String BASE_RSPATH = "/yobatis/src/main/resources";



    private Element findElement(MybatisGeneratorContext context, String tagName) {
        Element element = context.getContext();
        return element.element(tagName);
    }

    private List<Element> findElements(MybatisGeneratorContext context, String tagName) {
        Element element = context.getContext();
        return element.elements(tagName);
    }

    @Test
    public void onePossibleCombOfPaths() {
        addFolder(BASE_SCPATH + "/model");
        addFolder(BASE_SCPATH + "/s/dao");
        addFolder(BASE_RSPATH);
        creator = MybatisGeneratorXmlCreator.create(project, databaseMetadataProvider);
        List<MybatisGeneratorContext> contextList = creator.getContexts();
        assertEquals(1, contextList.size());
        Element path = creator.getClassPathEntryElement();
        assertEquals("mysql.jar", path.attributeValue("location"));
        Element javaModelGenerator = findElement(contextList.get(0), "javaModelGenerator");
        assertEquals(BASE_SCPATH, javaModelGenerator.attributeValue("targetProject"));
        assertEquals("model", javaModelGenerator.attributeValue("targetPackage"));

        Element javaClientGenerator = findElement(contextList.get(0), "javaClientGenerator");
        assertEquals(BASE_SCPATH, javaClientGenerator.attributeValue("targetProject"));
        assertEquals("s.dao", javaClientGenerator.attributeValue("targetPackage"));
    }

    @Test
    public void hasNoModelFolder() {
        addFolder(BASE_SCPATH + "/s/dao");
        addFolder(BASE_RSPATH);
        creator = MybatisGeneratorXmlCreator.create(project, databaseMetadataProvider);
        List<MybatisGeneratorContext> contextList = creator.getContexts();
        assertEquals(1, contextList.size());
        Element path = creator.getClassPathEntryElement();
        assertEquals("mysql.jar", path.attributeValue("location"));
        Element javaModelGenerator = findElement(contextList.get(0), "javaModelGenerator");
        assertEquals("", javaModelGenerator.attributeValue("targetProject"));
        assertEquals("", javaModelGenerator.attributeValue("targetPackage"));

        Element javaClientGenerator = findElement(contextList.get(0), "javaClientGenerator");
        assertEquals(BASE_SCPATH, javaClientGenerator.attributeValue("targetProject"));
        assertEquals("s.dao", javaClientGenerator.attributeValue("targetPackage"));

        creator.asXmlText();
    }

    @Test
    public void ambiguousPaths() {
        addFolder(BASE_SCPATH + "/hello/model");
        addFolder(BASE_SCPATH + "/dao");
        addFolder(BASE_SCPATH + "/hello/dao");
        addFolder(BASE_RSPATH);
        creator = MybatisGeneratorXmlCreator.create(project, databaseMetadataProvider);
        List<MybatisGeneratorContext> contextList = creator.getContexts();
        assertEquals(1, contextList.size());
        Element path = creator.getClassPathEntryElement();
        assertEquals("mysql.jar", path.attributeValue("location"));
        Element javaModelGenerator = findElement(contextList.get(0), "javaModelGenerator");
        assertEquals(BASE_SCPATH, javaModelGenerator.attributeValue("targetProject"));
        assertEquals("hello.model", javaModelGenerator.attributeValue("targetPackage"));

        Element javaClientGenerator = findElement(contextList.get(0), "javaClientGenerator");
        assertEquals(BASE_SCPATH, javaClientGenerator.attributeValue("targetProject"));
        assertEquals("hello.dao", javaClientGenerator.attributeValue("targetPackage"));
    }

}
