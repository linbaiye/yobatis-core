package org.nalby.yobatis.core.database;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.List;

/**
 * It is a wrapper of IntrospectedTable.
 */
public interface YobatisIntrospectedTable {

    enum ClassType {
        DAO, DAO_IMPL, CRITERIA, BASE_CRITERIA, ENTITY, BASE_ENTITY
    }

    FullyQualifiedJavaType getPrimaryKey();

    boolean isAutoIncPrimaryKey();

    String getTableName();

    /**
     * Get the path of a class file according to type class type.
     * @param classType
     * @return the path where the class file should rest.
     */
    String getClassPath(ClassType classType);

    FullyQualifiedJavaType getFullyQualifiedJavaType(ClassType classType);

    List<IntrospectedColumn> getColumns();

    List<IntrospectedColumn> getPrimaryKeyColumns();

}
