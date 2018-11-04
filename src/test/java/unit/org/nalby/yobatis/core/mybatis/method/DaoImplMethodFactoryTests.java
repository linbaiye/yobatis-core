package unit.org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import unit.org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;
import org.nalby.yobatis.core.mybatis.method.DaoImplMethodFactory;
import org.nalby.yobatis.core.mybatis.method.DaoMethodName;
import org.nalby.yobatis.core.mybatis.method.MethodFactory;

import static org.junit.Assert.assertTrue;

public class DaoImplMethodFactoryTests  extends AbstractYobatisTableSetup {

    private MethodFactory methodFactory;

    @Before
    public void setup() {
        super.setup();
        methodFactory = DaoImplMethodFactory.getInstance(yobatisIntrospectedTable);
    }


    @Test
    public void insert() {
        String method = asString(methodFactory.create(DaoMethodName.INSERT.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.insert(NAMESPACE + \"insert\", record);"));
    }

    @Test
    public void selectOneByPk() {
        String method = asString(methodFactory.create(DaoMethodName.SELECT_BY_PK.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"selectByPk\", pk);"));
    }

    @Test
    public void selectByCriteria() {
        String method = asString(methodFactory.create(DaoMethodName.SELECT_BY_CRITERIA.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"selectByCriteria\", criteria);"));
    }

    @Test
    public void selectList() {
        String method = asString(methodFactory.create(DaoMethodName.SELECT_LIST.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.selectList(NAMESPACE + \"selectList\", criteria);"));
    }

    @Test
    public void count() {
        String method = asString(methodFactory.create(DaoMethodName.COUNT.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.selectOne(NAMESPACE + \"count\", criteria);"));
    }

    @Test
    public void update() {
        String method = asString(methodFactory.create(DaoMethodName.UPDATE_BY_PK.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"updateByPk\", record);"));
    }

    @Test
    public void updateByCriteria() {
        String method = asString(methodFactory.create(DaoMethodName.UPDATE_BY_CRITERIA.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.update(NAMESPACE + \"updateByCriteria\", param);"));
    }

    @Test
    public void delete() {
        String method = asString(methodFactory.create(DaoMethodName.DELETE_BY_PK.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"deleteByPk\", pk);"));
    }

    @Test
    public void deleteByCriteria() {
        String method = asString(methodFactory.create(DaoMethodName.DELETE_BY_CRITERIA.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"deleteByCriteria\", criteria);"));
    }


    @Test
    public void setLimit() {
        String method = asString(methodFactory.create(DaoMethodName.DELETE_BY_CRITERIA.getName()));
        assertTrue(method.contains("return sqlSessionTemplate.delete(NAMESPACE + \"deleteByCriteria\", criteria);"));
    }

}
