package org.nalby.yobatis.core.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

public class MethodUtil {

    private static Method createMethod(String name, String returnType, JavaVisibility javaVisibility) {
        Method method = new Method(name);
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        method.setVisibility(javaVisibility);
        return method;
    }

    public static Method publicMethod(String name, String returnType) {
        return createMethod(name, returnType, JavaVisibility.PUBLIC);
    }

    public static Method constructor(String name, String ... bodyLines) {
        Method method = new Method(name);
        method.setConstructor(true);
        if (bodyLines != null) {
            for (String bodyLine : bodyLines) {
                method.addBodyLine(bodyLine);
            }
        }
        return method;
    }
}


