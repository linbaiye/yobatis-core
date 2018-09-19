package func.alltype.dao;

import func.alltype.model.criteria.BaseCriteria;
import java.util.List;

public interface BaseDao<B, T extends B, PK> {
    int insertAll(B record);

    int insertAllIgnore(B record);

    int insert(B record);

    T selectOne(PK pk);

    T selectOne(BaseCriteria criteria);

    List<T> selectList(BaseCriteria criteria);

    long countAll();

    long count(BaseCriteria criteria);

    int update(B record);

    int updateAll(B record);

    int update(B record, BaseCriteria criteria);

    int updateAll(B record, BaseCriteria criteria);

    int delete(PK pk);

    int delete(BaseCriteria criteria);
}