package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaoMethodFactoryTests {

    private DaoMethodFactory daoMethodFactory;

    private YobatisTableItem yobatisTableItem;

    @Before
    public void setup() {
        daoMethodFactory = DaoMethodFactoryImpl.getInstance();
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
        return method.getFormattedContent(0, true, new Interface("test"));
    }

    @Test
    public void insert() {
        Method method = daoMethodFactory.insert(yobatisTableItem);
        assertEquals("int insert(org.yobatis.entity.base.BaseYobatis record);", asString(method));
    }

    @Test
    public void selectOne() {
        Method method = daoMethodFactory.selectOne(yobatisTableItem);
        assertEquals("org.yobatis.entity.Yobatis selectOne(long pk);", asString(method));
    }

    @Test
    public void selectOneByCriteria() {
        Method method = daoMethodFactory.selectOneByCriteria(yobatisTableItem);
        assertEquals("org.yobatis.entity.Yobatis selectOne(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void selectList() {
        Method method = daoMethodFactory.selectList(yobatisTableItem);
        assertEquals("List<Yobatis> selectList(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void update() {
        Method method = daoMethodFactory.update(yobatisTableItem);
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record);",
                asString(method));
    }

    @Test
    public void updateByCriteria() {
        Method method = daoMethodFactory.updateByCriteria(yobatisTableItem);
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record, org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void delete() {
        Method method = daoMethodFactory.delete(yobatisTableItem);
        assertEquals("int delete(long pk);",
                asString(method));
    }

    @Test
    public void deleteByCriteria() {
        Method method = daoMethodFactory.deleteByCriteria(yobatisTableItem);
        assertEquals("int delete(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void count() {
        Method method = daoMethodFactory.count(yobatisTableItem);
        assertEquals("int count(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

}
