package org.nalby.yobatis.core.mybatis.javadoc;

import org.nalby.yobatis.core.mybatis.YobatisUnit;

public final class ClassJavaDocDecorator implements JavaDocDecorator<YobatisUnit> {

    private ClassJavaDocDecorator() { }

    private final static ClassJavaDocDecorator decorator = new ClassJavaDocDecorator();

    public static ClassJavaDocDecorator getInstance() {
        return decorator;
    }

    @Override
    public void decorate(YobatisUnit yobatisUnit) {
    }
}
