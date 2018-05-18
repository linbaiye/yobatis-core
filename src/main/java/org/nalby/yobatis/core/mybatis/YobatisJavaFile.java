package org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.CompilationUnit;

public class YobatisJavaFile extends GeneratedJavaFile {

    private boolean overWritable = false;

    public YobatisJavaFile(CompilationUnit compilationUnit,
                           String targetProject,
                           JavaFormatter javaFormatter) {
        super(compilationUnit, targetProject, javaFormatter);
    }

    public boolean isOverWritable() {
        return overWritable;
    }

    public void setOverWritable(boolean overWritable) {
        this.overWritable = overWritable;
    }
}
