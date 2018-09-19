package org.nalby.yobatis.core.mybatis.method;


import org.mybatis.generator.api.dom.java.Method;

public interface CommonMethodFactory {

    Method publicMethod(String name, String returnType);

    Method protectedMethod(String name, String returnType);

    Method finalProtectedMethod(String name, String returnType);

    Method privateMethod(String name, String returnType);

    Method getter(String name, String type);

    Method setter(String name, String type);

    Method constructor(String name, String ... bodyLines);

}
