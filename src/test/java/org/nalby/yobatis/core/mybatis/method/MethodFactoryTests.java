package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodFactoryTests {

    private MethodFactory methodFactory;

    private YobatisIntrospectedTable yobatisIntrospectedTable;

    @Before
    public void setup() {
        yobatisIntrospectedTable = mock(YobatisIntrospectedTable.class);
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.base.BaseYobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.Yobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.YobatisCriteria"));
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("long"));
        methodFactory = DaoMethodFactory.getInstance(yobatisIntrospectedTable);
    }

    private String asString(Method method) {
        return method.getFormattedContent(0, true, new Interface("test"));
    }

    @Test
    public void insert() {
        Method method = methodFactory.create(FactoryMethodName.INSERT.getName());
        assertEquals("int insert(org.yobatis.entity.base.BaseYobatis record);", asString(method));
    }

    @Test
    public void selectOne() {
        Method method = methodFactory.create(FactoryMethodName.SELECT_ONE_BY_PK.getName());
        assertEquals("org.yobatis.entity.Yobatis selectOne(long pk);", asString(method));
    }

    @Test
    public void selectOneByCriteria() {
        Method method = methodFactory.create(FactoryMethodName.SELECT_ONE_BY_CRITERIA.getName());
        assertEquals("org.yobatis.entity.Yobatis selectOne(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void selectList() {
        Method method = methodFactory.create(FactoryMethodName.SELECT_LIST.getName());
        assertEquals("List<Yobatis> selectList(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void update() {
        Method method = methodFactory.create(FactoryMethodName.UPDATE_BY_PK.getName());
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record);",
                asString(method));
    }

    @Test
    public void updateByCriteria() {
        Method method = methodFactory.create(FactoryMethodName.UPDATE_BY_CRITERIA.getName());
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record, org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void delete() {
        Method method = methodFactory.create(FactoryMethodName.DELETE_BY_PK.getName());
        assertEquals("int delete(long pk);",
                asString(method));
    }

    @Test
    public void deleteByCriteria() {
        Method method = methodFactory.create(FactoryMethodName.DELETE_BY_CRITERIA.getName());
        assertEquals("int delete(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void count() {
        Method method = methodFactory.create(FactoryMethodName.COUNT.getName());
        assertEquals("int count(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

}
