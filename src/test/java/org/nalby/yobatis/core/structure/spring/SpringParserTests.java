package org.nalby.yobatis.core.structure.spring;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringParserTests {

    private Folder root;

    private List<File> fileList;

    @Before
    public void setup() {
        root = mock(Folder.class);
        fileList = new LinkedList<>();
        when(root.listFiles()).thenReturn(fileList);
    }

    private File mockFile(String path, String content) {
        File file = mock(File.class);
        when(file.path()).thenReturn(path);
        when(file.open()).thenReturn(new ByteArrayInputStream(content.getBytes()));
        return file;
    }

    private void addFile(String path, String content) {
        File file = mockFile(path, content);
        fileList.add(file);
    }

    @Test
    public void singleFile() {
        String content = "<beans xmlns:p=\"http://www.springframework.org/schema/p\"><bean class=\"test.Datasource\">"
                + "<property name=\"username\" value=\"test\"/>"
                + "<property name=\"password\" value=\"test\"/>"
                + "<property name=\"url\" value=\"url\"/>"
                + "<property name=\"driverClassName\" value=\"driver\"/>"
                + "</bean></beans>";
        addFile("test.xml", content);
        Spring spring = SpringParser.parse(root);
        assertEquals("test", spring.lookupDbUser());
        assertEquals("test", spring.lookupDbPassword());
        assertEquals("url", spring.lookupDbUrl());
    }

    @Test
    public void hasInvalidSpringXml() {
        String content = "<beans xmlns:p=\"http://www.springframework.org/schema/p\">" +
                "<bean class=\"test.Datasource\">"
                + "</bean></beans>";
        addFile("test.xml", content);
        addFile("test1.xml", "<beans xmlns:p=\"http://www.");
        Spring spring = SpringParser.parse(root);
        assertNull(spring.lookupDbUrl());
        assertNull(spring.lookupDbPassword());
    }


}
