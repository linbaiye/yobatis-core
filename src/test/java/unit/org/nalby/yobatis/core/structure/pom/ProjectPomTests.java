package unit.org.nalby.yobatis.core.structure.pom;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.structure.pom.ProjectPom;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ProjectPomTests {

    private Project project;

    @Before
    public void setup() {
        project = mock(Project.class);
    }

    private File mockFile(String content) {
        File file = mock(File.class);
        when(file.open()).thenReturn(new ByteArrayInputStream(content.getBytes()));
        return file;
    }

    private Folder mockFolder(File file) {
        Folder folder = mock(Folder.class);
        when(folder.findFile("pom.xml")).thenReturn(file);
        return folder;
    }


    @Test
    public void singlePomProject() {
        String xmlContent = "<project><artifactId>test1</artifactId><properties>" +
                "<hello>world</hello>" +
                "</properties></project>";
        File file = mockFile(xmlContent);
        when(project.findFile("pom.xml")).thenReturn(file);
        ProjectPom projectPom = ProjectPom.parse(project);
        assertEquals("world", projectPom.lookupProperty("hello"));
    }

    @Test
    public void pomTree() {
        String xmlContent = "<project><artifactId>root</artifactId>" +
                "<modules><module>node1</module><module>node2</module><module>node3</module></modules></project>";
        File rootPom = mockFile(xmlContent);
        when(project.findFile("pom.xml")).thenReturn(rootPom);

        File node1Pom = mockFile("<project><artifactId>node1</artifactId><properties><key1>val1</key1></properties></project>");
        Folder pom1Folder = mockFolder(node1Pom);
        when(project.findFolder("node1")).thenReturn(pom1Folder);

        File node2Pom = mockFile("<project><artifactId>node2</artifactId><modules><module>node4</module></modules></project>");
        Folder pom2Folder = mockFolder(node2Pom);
        when(project.findFolder("node2")).thenReturn(pom2Folder);

        when(project.findFolder("node3")).thenReturn(null);

        File node4Pom = mockFile("<project><artifactId>node4</artifactId><properties><key2>val2</key2></properties></project>");
        Folder pom4Folder = mockFolder(node4Pom);
        when(pom2Folder.findFolder("node4")).thenReturn(pom4Folder);

        ProjectPom projectPom = ProjectPom.parse(project);
        assertEquals("val1", projectPom.lookupProperty("key1"));
        assertEquals("val2", projectPom.lookupProperty("key2"));
    }

}
