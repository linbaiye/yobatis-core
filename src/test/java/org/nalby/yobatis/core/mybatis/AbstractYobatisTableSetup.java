package org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractYobatisTableSetup {


    protected  YobatisIntrospectedTable yobatisIntrospectedTable;

    public void setup() {
        yobatisIntrospectedTable = mock(YobatisIntrospectedTable.class);
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.base.BaseYobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.Yobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.YobatisCriteria"));
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("long"));

    }

    protected String asString(Method method) {
        return method.getFormattedContent(0, false, new TopLevelClass("test"));
    }

    protected void assertMethodContentEqual(String content, Method method) {
        assertEquals(content.replaceAll("\\s+", ""), asString(method).replaceAll("\\s+", ""));
    }

}
