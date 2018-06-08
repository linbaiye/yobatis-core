package func.autoinckey.dao.impl;

import func.autoinckey.dao.BaseDao;
import func.autoinckey.model.criteria.BaseCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;

/*
 * Do NOT modify, it will be overwrote every time func runs.
 */
public abstract class BaseDaoImpl<T extends B, B, PK> implements BaseDao<T, B, PK> {
    private static final String SELECT_BY_PK = "selectByPk";

    private static final String SELECT_BY_CRITERIA = "selectByCriteria";

    private static final String COUNT = "count";

    private static final String INSERT_ALL = "insertAll";

    private static final String INSERT_ALL_IGNORE = "insertAllIgnore";

    private static final String INSERT = "insert";

    private static final String DELETE_BY_PK = "deleteByPk";

    private static final String DELETE_BY_CRITERIA = "deleteByCriteria";

    private static final String UPDATE = "update";

    private static final String UPDATE_ALL = "updateAll";

    private static final String UPDATE_BY_CRITERIA = "updateByCriteria";

    private static final String UPDATE_ALL_BY_CRITERIA = "updateAllByCriteria";

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
        notNull(criteria, "criteria must not be null.");
        if (criteria.getOredCriteria().isEmpty()) {
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
        return doInsert(INSERT_ALL, record);
    }

    @Override
    public final int insertAllIgnore(B record) {
        notNull(record, "record must not be null.");
        return doInsert(INSERT_ALL_IGNORE, record);
    }

    @Override
    public final int insert(B record) {
        notNull(record, "record must not be null.");
        return doInsert(INSERT, record);
    }

    @Override
    public final T selectOne(PK pk) {
        notNull(pk, "Primary key must not be null.");
        return doSelectOne(SELECT_BY_PK, pk);
    }

    @Override
    public final T selectOne(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doSelectOne(SELECT_BY_CRITERIA, criteria);
    }

    @Override
    public final List<T> selectList(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doSelectList(SELECT_BY_CRITERIA, criteria);
    }

    @Override
    public final long countAll() {
        return sqlSessionTemplate.selectOne(namespace() + COUNT, null);
    }

    @Override
    public final long count(BaseCriteria criteria) {
        validateCriteria(criteria);
        return sqlSessionTemplate.selectOne(this.namespace() + COUNT, criteria);
    }

    @Override
    public final int update(B record) {
        notNull(record, "record must not be null.");
        return doUpdate(UPDATE, record);
    }

    @Override
    public final int updateAll(B record) {
        notNull(record, "record must not be null.");
        return doUpdate(UPDATE_ALL, record);
    }

    @Override
    public final int update(B record, BaseCriteria criteria) {
        notNull(record, "record must not be null.");
        validateCriteria(criteria);
        return doUpdate(UPDATE_BY_CRITERIA, makeParam(record, criteria));
    }

    @Override
    public final int updateAll(B record, BaseCriteria criteria) {
        notNull(record, "record must not be null.");
        validateCriteria(criteria);
        return doUpdate(UPDATE_ALL_BY_CRITERIA, makeParam(record, criteria));
    }

    @Override
    public final int delete(PK pk) {
        notNull(pk, "Primary key must not be null.");
        return doDelete(DELETE_BY_PK, pk);
    }

    @Override
    public final int delete(BaseCriteria criteria) {
        validateCriteria(criteria);
        return doDelete(DELETE_BY_CRITERIA, criteria);
    }
}