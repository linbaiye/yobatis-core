package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public interface JavaFileFactory {

    /**
     * Create the base dao interface which is extended by table specific DAOs.
     * @param introspectedTable the table.
     * @return the base dao java file.
     */
    GeneratedJavaFile baseDaoInterface(IntrospectedTable introspectedTable);

    /**
     * Create implementing java file of the base dao.
     * @param introspectedTable the table.
     * @return the implementing java file
     */
    GeneratedJavaFile baseDaoImpl(IntrospectedTable introspectedTable);

    /**
     * Create dao interface for the table {@code introspectedTable}.
     * @param introspectedTable the table.
     * @return dao interface of the table.
     */
    GeneratedJavaFile tableSpecificDaoInterface(IntrospectedTable introspectedTable);

    GeneratedJavaFile tableSpecificDaoImpl(IntrospectedTable introspectedTable);

    /**
     * Create base domain java file corresponding to the {@code introspectedTable}.
     * @param introspectedTable the table.
     * @return the base domain java file.
     */
    GeneratedJavaFile baseDomain(TopLevelClass originalClass, IntrospectedTable introspectedTable);

    GeneratedJavaFile domain(IntrospectedTable introspectedTable);

    GeneratedJavaFile baseCriteria(IntrospectedTable introspectedTable);

    GeneratedJavaFile criteria(TopLevelClass originalExample, IntrospectedTable introspectedTable);

}
