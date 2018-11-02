package org.nalby.yobatis.core.mybatis.javadoc;

import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.YobatisUnit;

public final class MethodJavaDocDecoratorFactory {
    private final static  MethodJavaDocDecoratorFactory methodJavaDocDecoratorFactory =
            new MethodJavaDocDecoratorFactory();
    private MethodJavaDocDecoratorFactory() {
    }
    public static MethodJavaDocDecoratorFactory getInstance() {
        return methodJavaDocDecoratorFactory;
    }

    public JavaDocDecorator<Method> build(YobatisUnit yobatisUnit) {
        return null;
    }
}
