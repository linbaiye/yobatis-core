package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

public interface JavaFileFactory {

    /**
     * Create base domain java file corresponding to the {@code introspectedTable}.
     * @param introspectedTable the table.
     * @return the base domain java file.
     */
    GeneratedJavaFile baseDomain(TopLevelClass originalClass, IntrospectedTable introspectedTable);

    GeneratedJavaFile domain(IntrospectedTable introspectedTable);

    GeneratedJavaFile baseCriteria(YobatisIntrospectedTable introspectedTable);

    GeneratedJavaFile criteria(YobatisIntrospectedTable introspectedTable);

    GeneratedJavaFile dao(YobatisIntrospectedTable introspectedTable);

    GeneratedJavaFile daoImpl(YobatisIntrospectedTable introspectedTable);

}
