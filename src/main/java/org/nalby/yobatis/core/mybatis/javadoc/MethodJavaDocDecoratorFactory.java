package org.nalby.yobatis.core.mybatis.javadoc;

import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.clazz.BaseDao;
import org.nalby.yobatis.core.mybatis.clazz.BaseDaoImpl;

public final class MethodJavaDocDecoratorFactory {
    private final static  MethodJavaDocDecoratorFactory methodJavaDocDecoratorFactory =
            new MethodJavaDocDecoratorFactory();
    private MethodJavaDocDecoratorFactory() {
    }
    public static MethodJavaDocDecoratorFactory getInstance() {
        return methodJavaDocDecoratorFactory;
    }

    public JavaDocDecorator<Method> build(YobatisUnit yobatisUnit) {
        if (yobatisUnit instanceof BaseDao) {
            return BaseDaoMethodJavaDocDecorator.getInstance();
        }
        return null;
    }
}
