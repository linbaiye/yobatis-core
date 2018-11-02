package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.DomainMethodFactory;
import org.nalby.yobatis.core.mybatis.method.DomainMethodFactoryImpl;

public class BaseEntity extends TopLevelClass implements YobatisUnit {

    private String pathToPut;

    private BaseEntity(FullyQualifiedJavaType javaType, String pathToPut) {
        super(javaType);
        this.setAbstract(true);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.pathToPut = pathToPut;
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public void merge(String fileContent) {

    }

    public static BaseEntity getInstance(TopLevelClass originalClass, IntrospectedTable table) {

        FullyQualifiedJavaType type = NamingHelper.getBaseEntityType(originalClass.getType());
        String path = NamingHelper.glueEntityPath(table, type);
        BaseEntity baseEntity = new BaseEntity(type, path);
        DomainMethodFactory domainMethodFactory = DomainMethodFactoryImpl.getInstance();
        for (Field field : originalClass.getFields()) {
            field.getJavaDocLines().clear();
            field.setVisibility(JavaVisibility.PROTECTED);
            baseEntity.addField(field);
        }
        for (Method method : originalClass.getMethods()) {
            method.getJavaDocLines().clear();
            baseEntity.addMethod(method);
        }
        for (FullyQualifiedJavaType javaType: originalClass.getImportedTypes()) {
            baseEntity.addImportedType(javaType);
        }
        baseEntity.addMethod(domainMethodFactory.createToString(originalClass));
        baseEntity.addMethod(domainMethodFactory.createCopy(baseEntity));
        return baseEntity;
    }
}
