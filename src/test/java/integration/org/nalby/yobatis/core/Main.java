package integration.org.nalby.yobatis.core;

import integration.org.nalby.yobatis.core.local.LocalFolder;
import integration.org.nalby.yobatis.core.local.LocalProject;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.YobatisShell;
import org.nalby.yobatis.core.database.MysqlDatabaseMetadataProvider;
import org.nalby.yobatis.core.log.AbstractLogger;
import org.nalby.yobatis.core.mybatis.TableElement;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;

import java.util.LinkedList;
import java.util.List;

public class Main {

    private Project project;

    private Folder root;

    private YobatisShell yobatisShell;

    private List<TableElement> tableElementList;

    @Before
    public void setup() {
        root = LocalFolder.openRoot();
        project = new LocalProject(root);
        yobatisShell = YobatisShell.open(project);
        tableElementList = new LinkedList<>();
        AbstractLogger.defaultLevel = AbstractLogger.LogLevel.DEBUG;
    }

    @Test
    public void generate() {
        TableElement tableElement = new TableElement("all_data_types", true);
        tableElementList.add(tableElement);
        tableElementList.add(new TableElement("compound_key_table", true));
        tableElementList.add(new TableElement("autoinc_pk_table", true));
        yobatisShell.generate(tableElementList);
    }

    @Test
    public void listTables() {
        MysqlDatabaseMetadataProvider.Builder builder = MysqlDatabaseMetadataProvider.builder();
        builder.setConnectorJarPath("/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar");
        builder.setDriverClassName("com.mysql.jdbc.Driver");
        builder.setUrl("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8");
        builder.setUsername("root");
        builder.setPassword("root");
        MysqlDatabaseMetadataProvider provider = builder.build();
        provider.fetchTables().forEach(e -> {
            System.out.println(e.getName());
        });
    }

    @Test
    public void loadTables() {
        List<TableElement> list = yobatisShell.loadTables();
        list.forEach(e -> {
            System.out.println(e.getName());
        });
    }

}
