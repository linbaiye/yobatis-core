package org.nalby.yobatis.core.mybatis.javadoc;

import org.mybatis.generator.api.dom.java.Method;

public class NullMethodJavaDocDecorator implements JavaDocDecorator<Method> {

    private static NullMethodJavaDocDecorator ourInstance = new NullMethodJavaDocDecorator();

    public static NullMethodJavaDocDecorator getInstance() {
        return ourInstance;
    }

    private NullMethodJavaDocDecorator() {
    }

    @Override
    public void decorate(Method method) {
        // Do nothing.
    }
}
