package unit.org.nalby.yobatis.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.nalby.yobatis.core.YobatisShell;
import org.nalby.yobatis.core.database.MysqlDatabaseMetadataProvider;
import org.nalby.yobatis.core.database.Table;
import org.nalby.yobatis.core.mybatis.Settings;
import org.nalby.yobatis.core.mybatis.TableElement;
import org.nalby.yobatis.core.mybatis.YobatisConfiguration;
import org.nalby.yobatis.core.structure.Project;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MysqlDatabaseMetadataProvider.class)
public class YobatisShellTests {

    private MysqlDatabaseMetadataProvider.Builder builder;

    private MysqlDatabaseMetadataProvider databaseMetadataProvider;

    private Project project;

    private YobatisConfiguration yobatisConfiguration;

    private YobatisShell yobatisShell;

    private Settings settings;

    List<TableElement> tableElementList;
    List<Table> tableList;

    private void constructShell() {
        Constructor[] constructors = YobatisShell.class.getDeclaredConstructors();
        Constructor constructor = constructors[0];
        constructor.setAccessible(true);
        try {
            yobatisShell = (YobatisShell) constructor.newInstance(yobatisConfiguration, project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() {
        project = mock(Project.class);
        yobatisConfiguration = mock(YobatisConfiguration.class);
        builder = mock(MysqlDatabaseMetadataProvider.Builder.class);
        PowerMockito.mockStatic(MysqlDatabaseMetadataProvider.class);
        Mockito.when(MysqlDatabaseMetadataProvider.builder()).thenReturn(builder);
        databaseMetadataProvider = mock(MysqlDatabaseMetadataProvider.class);
        when(builder.build()).thenReturn(databaseMetadataProvider);
        settings = new Settings();
        when(yobatisConfiguration.getSettings()).thenReturn(settings);
        constructShell();
        tableList = new LinkedList<>();
        tableElementList = new LinkedList<>();
    }

    @Test
    public void dbNotConfigured() {
        when(yobatisConfiguration.getSettings()).thenReturn(settings);
        List<TableElement> tableElementList = yobatisShell.loadTables();
        assertTrue(tableElementList.isEmpty());
    }

    @Test
    public void loadTables() {
        settings.setUser("test");
        settings.setPassword("test");
        settings.setUrl("test");
        settings.setConnectorPath("test");
        when(databaseMetadataProvider.fetchTables()).thenReturn(tableList);
        when(yobatisConfiguration.listTableElementAsc()).thenReturn(tableElementList);
        TableElement tableElement = new TableElement("table1", true);
        tableElementList.add(tableElement);
        List<TableElement> ret = yobatisShell.loadTables();
        when(yobatisShell.loadTables()).thenReturn(tableElementList);
        assertEquals(1, ret.size());
    }
}
