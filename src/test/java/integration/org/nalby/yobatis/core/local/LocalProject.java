package integration.org.nalby.yobatis.core.local;

import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;

public class LocalProject extends Project {

    public LocalProject(Folder root) {
        super(root);
    }

    @Override
    public String getAbsPathOfSqlConnector() {
        return "/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar";
    }
}
