package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;

public interface MethodFactory {

    Method create(String name);

    Method create(String name, IntrospectedColumn column);

}
