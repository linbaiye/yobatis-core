package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;

import static org.junit.Assert.assertEquals;

public class MethodFactoryTests extends AbstractYobatisTableSetup {

    private MethodFactory methodFactory;

    @Before
    public void setup() {
        super.setup();
        methodFactory = DaoMethodFactory.getInstance(yobatisIntrospectedTable);
    }

    @Override
    protected String asString(Method method) {
        return method.getFormattedContent(0, true, new Interface("Test"));
    }

    @Test
    public void insert() {
        Method method = methodFactory.create(DaoMethodName.INSERT.getName());
        assertEquals("int insert(org.yobatis.entity.base.BaseYobatis record);", asString(method));
    }

    @Test
    public void selectOne() {
        Method method = methodFactory.create(DaoMethodName.SELECT_BY_PK.getName());
        assertEquals("org.yobatis.entity.Yobatis selectOne(long pk);", asString(method));
    }

    @Test
    public void selectOneByCriteria() {
        Method method = methodFactory.create(DaoMethodName.SELECT_BY_CRITERIA.getName());
        assertEquals("org.yobatis.entity.Yobatis selectOne(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void selectList() {
        Method method = methodFactory.create(DaoMethodName.SELECT_LIST.getName());
        assertEquals("List<Yobatis> selectList(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void update() {
        Method method = methodFactory.create(DaoMethodName.UPDATE_BY_PK.getName());
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record);",
                asString(method));
    }

    @Test
    public void updateByCriteria() {
        Method method = methodFactory.create(DaoMethodName.UPDATE_BY_CRITERIA.getName());
        assertEquals("int update(org.yobatis.entity.base.BaseYobatis record, org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void delete() {
        Method method = methodFactory.create(DaoMethodName.DELETE_BY_PK.getName());
        assertEquals("int delete(long pk);",
                asString(method));
    }

    @Test
    public void deleteByCriteria() {
        Method method = methodFactory.create(DaoMethodName.DELETE_BY_CRITERIA.getName());
        assertEquals("int delete(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

    @Test
    public void count() {
        Method method = methodFactory.create(DaoMethodName.COUNT.getName());
        assertEquals("int count(org.yobatis.entity.criteria.YobatisCriteria criteria);",
                asString(method));
    }

}
