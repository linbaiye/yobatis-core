package integration.org.nalby.yobatis.core;

import integration.org.nalby.yobatis.core.local.LocalFolder;
import integration.org.nalby.yobatis.core.local.LocalProject;
import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.YobatisShell;
import org.nalby.yobatis.core.log.AbstractLogger;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.log.LoggerFactory;
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
    public void all() {
        TableElement tableElement = new TableElement("all_data_types", true);
        tableElementList.add(tableElement);
        tableElementList.add(new TableElement("compound_key_table", true));
        yobatisShell.onGenerateClicked(tableElementList);

//        project.listFolders().forEach(e -> {
//            System.out.println(e.path());
//        });
//        project.listFiles().forEach(e -> {
//            System.out.println(e.path());
//        });
    }
}
