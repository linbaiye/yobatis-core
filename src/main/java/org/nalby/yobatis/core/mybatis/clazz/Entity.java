package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;

public class Entity extends TopLevelClass implements YobatisUnit {

    private String pathToPut;

    private String existentFile;

    private Entity(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.pathToPut = pathToPut;
    }

    public static Entity getInstance(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        Entity entity = new Entity(type, NamingHelper.glueEntityPath(introspectedTable, type));
        FullyQualifiedJavaType baseType = NamingHelper.getBaseEntityType(type);
        entity.setSuperClass(baseType);
        entity.addImportedType(baseType);
        return entity;
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public String getFormattedContent() {
        if (existentFile != null) {
            return existentFile;
        }
        return super.getFormattedContent();
    }

    @Override
    public void merge(String fileContent) {
        existentFile = fileContent;
    }
}
