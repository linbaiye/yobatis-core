package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public abstract class AbstractMethodFactory implements MethodFactory {

    protected Method makeMethod(String name, FullyQualifiedJavaType returnType, Parameter parameter) {
        Method method = new Method(name);
        method.setReturnType(returnType);
        method.addParameter(parameter);
        return method;
    }

    protected Parameter makeParam(FullyQualifiedJavaType type, String name) {
        return new Parameter(type, name);
    }

    @Override
    public Method create(String name) {
        return null;
    }

    @Override
    public Method create(String name, IntrospectedColumn column) {
        return null;
    }
}
