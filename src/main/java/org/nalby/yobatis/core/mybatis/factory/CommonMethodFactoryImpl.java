package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class CommonMethodFactoryImpl implements AbstractCommonMethodFactory {

    private static final CommonMethodFactoryImpl factory = new CommonMethodFactoryImpl();

    private CommonMethodFactoryImpl() {}

    public static CommonMethodFactoryImpl getInstance() {
        return factory;
    }

    private Method createMethod(String name, String returnType, JavaVisibility javaVisibility) {
        Method method = new Method(name);
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        method.setVisibility(javaVisibility);
        return method;
    }

    @Override
    public Method publicMethod(String name, String returnType) {
        return createMethod(name, returnType, JavaVisibility.PUBLIC);
    }

    @Override
    public Method protectedMethod(String name, String returnType) {
        return createMethod(name, returnType, JavaVisibility.PROTECTED);
    }

    @Override
    public Method privateMethod(String name, String returnType) {
        return createMethod(name, returnType, JavaVisibility.PRIVATE);
    }

    @Override
    public Method getter(String name, String type) {
        String s1 = name.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + name.substring(1);
        Method method = publicMethod("get" + nameCapitalized, type);
        method.addBodyLine("return " + name + ";");
        return method;
    }

    @Override
    public Method setter(String name, String type) {
        String s1 = name.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + name.substring(1);
        Method method = publicMethod("set" + nameCapitalized, "void");
        method.addParameter(
                new Parameter(new FullyQualifiedJavaType(type), name));
        method.addBodyLine("this." + name + " = " + name + ";");
        return method;
    }
}