package func.alltype.dao.impl;

import func.alltype.dao.BaseDao;
import func.alltype.model.criteria.BaseCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;

abstract class BaseDaoImpl<B, T extends B, PK> implements BaseDao<B, T, PK> {
    @Resource
    protected SqlSessionTemplate sqlSessionTemplate;

    protected abstract String namespace();

    protected final T doSelectOne(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(namespace() + statement, parameter);
    }

    protected final List<T> doSelectList(String statement, Object parameter) {
        return sqlSessionTemplate.selectList(namespace() + statement, parameter);
    }

    protected final int doUpdate(String statement, Object parameter) {
        return sqlSessionTemplate.update(namespace() + statement, parameter);
    }

    protected final int doInsert(String statement, Object parameter) {
        return sqlSessionTemplate.insert(namespace() + statement, parameter);
    }

    protected final int doDelete(String statement, Object parameter) {
        return sqlSessionTemplate.delete(namespace() + statement, parameter);
    }

    protected final void notNull(Object object, String errMsg) {
        if (object == null) {
            throw new IllegalArgumentException(errMsg);
        }
    }

    protected final void validateCriteria(BaseCriteria criteria) {
        if (criteria == null || criteria.getOredCriteria().isEmpty()) {
            throw new IllegalArgumentException("criteria must not be empty.");
        }
    }

    protected final Map<String, Object> makeParam(B record, BaseCriteria criteria) {
        notNull(record, "record must not be null.");
        validateCriteria(criteria);
        Map<String, Object> param = new HashMap<>();
        param.put("record", record);
        param.put("example", criteria);
        return param;
    }

    @Override
    public final int insertAll(B record) {
        notNull(record, "record must not be null.");
        return sqlSessionTemplate.selectOne(namespace() + "insertAll", record);
    }

    @Override
    public final int insertAllIgnore(B record) {
        notNull(record, "record must not be null.");
        return doInsert("insertAllIgnore", record);
    }

    @Override
    public final int insert(B record) {
        notNull(record, "record must not be null.");
        return doInsert("insert", record);
    }

    @Override
    public final T selectOne(PK pk) {
        notNull(pk, "Primary key must not be null.");
        return sqlSessionTemplate.selectOne(namespace() + "selectByPk", pk);
    }

    @Override
    public final T selectOne(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doSelectOne("selectByCriteria", criteria);
    }

    @Override
    public final List<T> selectList(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doSelectList("selectByCriteria", criteria);
    }

    @Override
    public final long countAll() {
        return sqlSessionTemplate.selectOne(namespace() + "count", null);
    }

    @Override
    public final long count(BaseCriteria criteria) {
        validateCriteria(criteria);
        return sqlSessionTemplate.selectOne(namespace() + "count", criteria);
    }

    @Override
    public final int update(B record) {
        notNull(record, "record must no be null.");
        return doUpdate("update", record);
    }

    @Override
    public final int updateAll(B record) {
        notNull(record, "record must no be null.");
        return doUpdate("updateAll", record);
    }

    @Override
    public final int update(B record, BaseCriteria criteria) {
        return doUpdate("updateByCriteria", makeParam(record, criteria));
    }

    @Override
    public final int updateAll(B record, BaseCriteria criteria) {
        return doUpdate("updateAllByCriteria", makeParam(record, criteria));
    }

    @Override
    public final int delete(PK pk) {
        notNull(pk, "record must no be null.");
        return doDelete("deleteByPk", pk);
    }

    @Override
    public final int delete(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doDelete("deleteByCriteria", criteria);
    }
}