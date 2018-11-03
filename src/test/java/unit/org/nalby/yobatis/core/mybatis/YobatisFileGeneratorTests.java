package unit.org.nalby.yobatis.core.mybatis;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisFileGenerator;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YobatisFileGeneratorTests {

    private YobatisFileGenerator fileGenerator;

    private List<YobatisUnit> yobatisUnitList;

    private Project project;

    @Before
    public void setup() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor[] constructors = YobatisFileGenerator.class.getDeclaredConstructors();
        Constructor constructor = constructors[0];
        constructor.setAccessible(true);
        yobatisUnitList = new LinkedList<>();
        project = mock(Project.class);
        fileGenerator = (YobatisFileGenerator) constructor.newInstance(yobatisUnitList, project);
    }

    private YobatisUnit mockUnit(String path, String content) {
        YobatisUnit unit = mock(YobatisUnit.class);
        when(unit.getPathToPut()).thenReturn(path);
        when(unit.getFormattedContent()).thenReturn(content);
        yobatisUnitList.add(unit);
        return unit;
    }

    @Test
    public void noNeedToMerge() {
        mockUnit("/tmp/test.xml", "hello");
        File file = mock(File.class);
        when(project.createFile(any())).thenReturn(file);
        when(project.findFile("/tmp/test.xml")).thenReturn(null);
        doAnswer(e -> {
            String content = (String)e.getArguments()[0];
            assertEquals("hello", content);
            return null;
        }).when(file).write(anyString());
        fileGenerator.mergeAndWrite();
    }

    @Test
    public void merged() throws InvalidUnitException {
        YobatisUnit unit = mockUnit("/tmp/test.xml", "hello");
        File file = mock(File.class);
        when(project.findFile("/tmp/test.xml")).thenReturn(file);
        when(file.open()).thenReturn(new ByteArrayInputStream("world".getBytes()));
        doAnswer(e -> {
            String content = (String)e.getArguments()[0];
            assertEquals("world", content);
            when(unit.getFormattedContent()).thenReturn(content);
            return null;
        }).when(unit).merge(anyString());
        when(project.createFile("/tmp/test.xml")).thenReturn(file);
        doAnswer(e -> {
            String content = (String)e.getArguments()[0];
            assertEquals("world", content);
            return null;
        }).when(file).write(anyString());
        fileGenerator.mergeAndWrite();
    }
}
