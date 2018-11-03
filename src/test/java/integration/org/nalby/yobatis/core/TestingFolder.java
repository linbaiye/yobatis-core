package integration.org.nalby.yobatis.core;

import org.nalby.yobatis.core.structure.AbstractFolder;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.util.FolderUtil;

import java.util.LinkedList;
import java.util.List;

public class TestingFolder extends AbstractFolder {

    private final static String REAL_BASE_PATH = "/Users/lintao/Work/idea-projects/yobatis-core/src/test";

    private String realpath;

    public TestingFolder(String path, String name) {
        super(path, name);
        realpath = REAL_BASE_PATH + path;
    }

    @Override
    protected List<Folder> doListFolders() {
        java.io.File file = new java.io.File(realpath);
        java.io.File[] tmp = file.listFiles(java.io.File::isDirectory);
        List<Folder> ret = new LinkedList<>();
        if (tmp == null || tmp.length == 0) {
            return ret;
        }
        for (java.io.File file1 : tmp) {
            ret.add(new TestingFolder(FolderUtil.concatPath(this.path, file1.getName()), file1.getName()));
        }
        return ret;
    }

    @Override
    protected List<File> doListFiles() {
        return null;
    }

    @Override
    protected Folder doCreateFolder(String name) {
        return null;
    }

    @Override
    protected File doCreateFile(String name) {
        return null;
    }

    public static Folder openRoot() {
        return new TestingFolder("/", "test");
    }
}
