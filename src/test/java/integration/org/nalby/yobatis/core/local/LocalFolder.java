package integration.org.nalby.yobatis.core.local;

import org.nalby.yobatis.core.exception.ResourceNotAvailableExeception;
import org.nalby.yobatis.core.structure.AbstractFolder;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.util.FolderUtil;

import java.util.LinkedList;
import java.util.List;

public class LocalFolder extends AbstractFolder {

    private final static String REAL_BASE_PATH = "/Users/lintao/Work/idea-projects/yobatis-core/src";

    private String realpath;

    public LocalFolder(String path, String name) {
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
            ret.add(new LocalFolder(FolderUtil.concatPath(this.path, file1.getName()), file1.getName()));
        }
        return ret;
    }

    @Override
    protected List<File> doListFiles() {
        java.io.File file = new java.io.File(realpath);
        java.io.File[] tmp = file.listFiles(java.io.File::isFile);
        List<File> ret = new LinkedList<>();
        if (tmp == null || tmp.length == 0) {
            return ret;
        }
        for (java.io.File file1 : tmp) {
            ret.add(new LocalFile(this, FolderUtil.concatPath(this.path, file1.getName()),
                    FolderUtil.concatPath(this.realpath, file1.getName()), file1.getName()));
        }
        return ret;
    }

    @Override
    protected Folder doCreateFolder(String name) {
        java.io.File file = new java.io.File(FolderUtil.concatPath(realpath, name));
        if (!file.mkdir()) {
            throw new ResourceNotAvailableExeception("Unable to create:" + realpath + "/" + name);
        }
        return new LocalFolder(FolderUtil.concatPath(path, name), name);
    }

    @Override
    protected File doCreateFile(String name) {
        java.io.File file = new java.io.File(FolderUtil.concatPath(realpath, name));
        try {
            if (!file.createNewFile()) {
                throw new ResourceNotAvailableExeception("Unable to create:" + realpath + "/" + name);
            }
            return new LocalFile(this, FolderUtil.concatPath(this.path, name),
                    FolderUtil.concatPath(this.realpath, name), name);
        } catch (Exception e) {
            throw new ResourceNotAvailableExeception(e);
        }
    }

    public static Folder openRoot() {
        return new LocalFolder("/test", "test");
    }
}
