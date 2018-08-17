package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.Method;

public interface MethodBodyDecorator {

    void decorate(Method method);
}
