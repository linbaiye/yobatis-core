package integration.org.nalby.yobatis.core;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;

public class YobatisShellTests {

    private Project project;

    private Folder root;

    @Before
    public void setup() {
        root = TestingFolder.openRoot();
        project = new TestingProject(root);
    }

    @Test
    public void all() {
        project.listFolders().forEach(e -> {
            System.out.println(e.path());
        });
    }
}
