package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.database.YobatisTableItem;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaoImplMethodFactoryTests {

    private DaoMethodFactory daoMethodFactory;

    private YobatisTableItem yobatisTableItem;

    @Before
    public void setup() {
        daoMethodFactory = DaoImplMethodFactory.getInstance();
        yobatisTableItem = mock(YobatisTableItem.class);
        when(yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.base.BaseYobatis"));
        when(yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.Yobatis"));
        when(yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.YobatisCriteria"));
        when(yobatisTableItem.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("long"));
    }

    private String asString(Method method) {
        return method.getFormattedContent(0, false, new TopLevelClass("test"));
    }

    @Test
    public void insert() {
        String method = asString(daoMethodFactory.insert(yobatisTableItem));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("return sqlSessionTemplate.insert(NAMESPACE + \"insert\", record);"));
    }

    @Test
    public void selectOneByPk() {
        String method = asString(daoMethodFactory.selectOne(yobatisTableItem));
        assertTrue(method.contains("notNull("));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"selectByPk\", pk);"));
    }

    @Test
    public void selectByCriteria() {
        String method = asString(daoMethodFactory.selectOneByCriteria(yobatisTableItem));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"selectByCriteria\", criteria);"));
    }

    @Test
    public void selectList() {
        String method = asString(daoMethodFactory.selectList(yobatisTableItem));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectList(NAMESPACE + \"selectByCriteria\", criteria);"));
    }

    @Test
    public void count() {
        String method = asString(daoMethodFactory.count(yobatisTableItem));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"count\", criteria);"));
    }

    @Test
    public void update() {
        String method = asString(daoMethodFactory.update(yobatisTableItem));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"update\", record);"));
    }

    @Test
    public void updateByCriteria() {
        String method = asString(daoMethodFactory.updateByCriteria(yobatisTableItem));
        assertTrue(method.contains("notNull(record"));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"updateByCriteria\", param);"));
    }

    @Test
    public void delete() {
        String method = asString(daoMethodFactory.delete(yobatisTableItem));
        assertTrue(method.contains("notNull(pk"));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"deleteByPk\", pk);"));
    }

    @Test
    public void deleteByCriteria() {
        String method = asString(daoMethodFactory.deleteByCriteria(yobatisTableItem));
        assertTrue(method.contains("notNull(criteria"));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"deleteByCriteria\", criteria);"));
    }

}
