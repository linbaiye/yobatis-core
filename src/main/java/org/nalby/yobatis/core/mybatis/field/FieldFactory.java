package org.nalby.yobatis.core.mybatis.field;

import org.mybatis.generator.api.dom.java.Field;

public interface FieldFactory {

    Field privateField(String name, String type);

    Field protectedField(String name, String type);

    Field publicField(String name, String type);
}
