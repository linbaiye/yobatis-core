package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaoImplMethodFactoryTests {

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
        methodFactory = DaoImplMethodFactory.getInstance(yobatisIntrospectedTable);
    }

    private String asString(Method method) {
        return method.getFormattedContent(0, false, new TopLevelClass("test"));
    }

    @Test
    public void notNull() {
        String method = asString(methodFactory.create(FactoryMethodName.NOT_NULL.getName()));
        assertTrue(method.contains("if (obj == null)"));
    }

    @Test
    public void insert() {
        String method = asString(methodFactory.create(FactoryMethodName.INSERT.getName()));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("return sqlSessionTemplate.insert(NAMESPACE + \"INSERT\", record);"));
    }

    @Test
    public void selectOneByPk() {
        String method = asString(methodFactory.create(FactoryMethodName.SELECT_ONE_BY_PK.getName()));
        assertTrue(method.contains("notNull("));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"SELECT_ONE_BY_PK\", pk);"));
    }

    @Test
    public void selectByCriteria() {
        String method = asString(methodFactory.create(FactoryMethodName.SELECT_ONE_BY_CRITERIA.getName()));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"SELECT_BY_CRITERIA\", criteria);"));
    }

    @Test
    public void selectList() {
        String method = asString(methodFactory.create(FactoryMethodName.SELECT_LIST.getName()));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectList(NAMESPACE + \"SELECT_BY_CRITERIA\", criteria);"));
    }

    @Test
    public void count() {
        String method = asString(methodFactory.create(FactoryMethodName.COUNT.getName()));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"COUNT\", criteria);"));
    }

    @Test
    public void update() {
        String method = asString(methodFactory.create(FactoryMethodName.UPDATE_BY_PK.getName()));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"UPDATE_BY_PK\", record);"));
    }

    @Test
    public void updateByCriteria() {
        String method = asString(methodFactory.create(FactoryMethodName.UPDATE_BY_CRITERIA.getName()));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"UPDATE_BY_CRITERIA\", param);"));
    }

    @Test
    public void delete() {
        String method = asString(methodFactory.create(FactoryMethodName.DELETE_BY_PK.getName()));
        assertTrue(method.contains("notNull(pk"));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"DELETE_BY_PK\", pk);"));
    }

    @Test
    public void deleteByCriteria() {
        String method = asString(methodFactory.create(FactoryMethodName.DELETE_BY_CRITERIA.getName()));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"DELETE_BY_CRITERIA\", criteria);"));
    }

}
