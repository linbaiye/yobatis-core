package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

public interface CriteriaMethodFactory {

    List<Method> criteriaMethodList(IntrospectedColumn introspectedColumn, String returnType);

    List<Method> factoryMethodList(IntrospectedColumn introspectedColumn, String returnType);

    Method setter(String type, String property, String returnType);

    Method order(String returnType, boolean asc);

    Method or(String returnType);

}
