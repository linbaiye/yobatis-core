package integration.org.nalby.yobatis.core.local;

import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;

import java.io.*;

public class LocalFile implements File {

    private String virtualPath;

    private String realpath;


    private String name;

    private Folder parent;

    public LocalFile(Folder parent, String virtualPath, String realpath, String name) {
        this.virtualPath = virtualPath;
        this.realpath = realpath;
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String path() {
        return virtualPath;
    }

    @Override
    public InputStream open() {
        try {
            return new FileInputStream(realpath);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void write(InputStream inputStream) {
        byte[] buffer = new byte[4000];
        int n;
        try (OutputStream outputStream = new FileOutputStream(realpath)){
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void write(String content) {
        write(new ByteArrayInputStream(content.getBytes()));
    }

    @Override
    public Folder parentFolder() {
        return parent;
    }
}
