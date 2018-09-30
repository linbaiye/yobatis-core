package org.nalby.yobatis.core.mybatis.field;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

public final class FieldFactoryImpl implements FieldFactory {

    private static final FieldFactory instance = new FieldFactoryImpl();

    public static FieldFactory getInstance() {
        return instance;
    }

    private Field buildField(String name, String type, JavaVisibility javaVisibility) {
        Field field = new Field(name, new FullyQualifiedJavaType(type));
        field.setVisibility(javaVisibility);
        return field;
    }

    @Override
    public Field privateField(String name, String type) {
        return buildField(name, type, JavaVisibility.PRIVATE);
    }

    @Override
    public Field protectedField(String name, String type) {
        return buildField(name, type, JavaVisibility.PROTECTED);
    }

    @Override
    public Field publicField(String name, String type) {
        return buildField(name, type, JavaVisibility.PUBLIC);
    }

}
