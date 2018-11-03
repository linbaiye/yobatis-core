package integration.org.nalby.yobatis.core;

import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;

public class TestingProject extends Project {

    public TestingProject(Folder root) {
        super(root);
    }

    @Override
    public String getAbsPathOfSqlConnector() {
        return null;
    }
}
