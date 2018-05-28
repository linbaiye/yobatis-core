package org.nalby.yobatis.core.mybatis.factory;


import org.mybatis.generator.api.dom.java.Method;

public interface AbstractCommonMethodFactory {

    Method publicMethod(String name, String returnType);

    Method protectedMethod(String name, String returnType);

    Method privateMethod(String name, String returnType);

    Method getter(String name, String type);

    Method setter(String name, String type);
}
