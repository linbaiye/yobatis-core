package org.nalby.yobatis.core.database;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public interface YobatisTableItem {

    enum ClassType {
        DAO, DAO_IMPL, CRITERIA, BASE_CRITERIA, ENTITY, BASE_ENTITY
    }

    FullyQualifiedJavaType getPrimaryKey();

    boolean isAutoIncPrimaryKey();

    String getTableName();

    String getClassPath(ClassType classType);

    String getClassName(ClassType classType);

    FullyQualifiedJavaType getFullyQualifiedJavaType(ClassType classType);

}
