package org.nalby.yobatis.core.structure.pom;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.Assert.*;

public class PomNodeTests {

    private class TestFile implements File {

        private String content;

        public TestFile(String content) {
            this.content = content;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public String path() {
            return null;
        }

        @Override
        public InputStream open() {
            return new ByteArrayInputStream(content.getBytes());
        }

        @Override
        public void write(InputStream inputStream) {

        }

        @Override
        public void write(String content) {

        }

        @Override
        public Folder parentFolder() {
            return null;
        }
    }

    @Test(expected = ProjectException.class)
    public void containsNoArtifactId() {
        String xmlContent = "<project><dependencies>"
                + "</dependencies></project>";
        PomNode.parse(new TestFile(xmlContent));
    }

    @Test(expected = ProjectException.class)
    public void emptyArtifactId() {
        String xmlContent = "<project><artifactId> </artifactId><dependencies>"
                + "</dependencies></project>";
        PomNode.parse(new TestFile(xmlContent));
    }

    @Test
    public void containerPom() throws DocumentException, IOException {
        String xmlContent = "<project><artifactId>test</artifactId><dependencies>"
                + "</dependencies></project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        assertTrue(pomNode.isContainer());
    }

    @Test
    public void modules() throws DocumentException, IOException {
        String xmlContent = "<project>" +
                "<artifactId>test</artifactId><modules>" +
                "<module>module1</module>" +
                "<module>module2  </module>" +
                "</modules></project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        Set<String> names = pomNode.getModuleNames();
        assertTrue(names.contains("module1"));
        assertTrue(names.contains("module2"));
    }

    @Test(expected = ProjectException.class)
    public void addNonExistentChild() {
        String xmlContent = "<project>" +
                "<artifactId>test</artifactId><modules>" +
                "</modules></project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        xmlContent = "<project>" +
                "<artifactId>test1</artifactId><modules>" +
                "</modules></project>";
        PomNode pomNode1 = PomNode.parse(new TestFile(xmlContent));
        pomNode.addChild(pomNode1);
    }

    @Test
    public void addChild() {
        String xmlContent = "<project>" +
                "<artifactId>test</artifactId><modules><module>test1</module>" +
                "</modules></project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        xmlContent = "<project>" +
                "<artifactId>test1</artifactId><modules>" +
                "</modules></project>";
        PomNode pomNode1 = PomNode.parse(new TestFile(xmlContent));
        pomNode.addChild(pomNode1);
        assertTrue(pomNode.getChildren().size() == 1);
    }

    @Test
    public void getProperty() {
        String xmlContent = "<project><artifactId>test1</artifactId><properties>" +
                "<hello>world</hello>" +
                "</properties></project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        assertEquals("world", pomNode.getProperty("hello"));
    }

    @Test
    public void getPropertyInActiveProfile() {
        String xmlContent = "<project><artifactId>test1</artifactId>" +
                "<properties>" +
                "<hello>world</hello>" +
                "</properties>" +
                "<profiles>" +
                "<profile>" +
                "<id>product</id>" +
                "<activation>" +
                "<activeByDefault>true</activeByDefault>" +
                "</activation>" +
                "<properties>" +
                "<hello>newWorld</hello>" +
                "</properties>" +
                "</profile>" +
                "<profile>" +
                "<id>test</id>" +
                "<activation>" +
                "<activeByDefault>false</activeByDefault>" +
                "</activation>" +
                "<properties>" +
                "<hello>newWorld1</hello>" +
                "</properties>" +
                "</profile>" +
                "</profiles>" +
                "</project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        assertEquals("newWorld", pomNode.getProperty("hello"));
    }

    @Test
    public void packaging() {
        String xmlContent = "<project>" +
                "<artifactId>test1</artifactId>" +
                "</project>";
        PomNode pomNode = PomNode.parse(new TestFile(xmlContent));
        assertTrue(pomNode.isContainer());
        xmlContent = "<project><packaging>jar</packaging>" +
                "<artifactId>test1</artifactId>" +
                "</project>";
        pomNode = PomNode.parse(new TestFile(xmlContent));
        assertFalse(pomNode.isContainer());
    }

    @Test(expected = ProjectException.class)
    public void invalidPom() {
        String xmlContent = "<project>" +
                "<artifactId>test1</artifactId>";
        PomNode.parse(new TestFile(xmlContent));
    }
}

