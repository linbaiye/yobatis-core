package func.compoundkey.dao.impl;

import func.compoundkey.dao.CompoundKeyTableDao;
import func.compoundkey.model.CompoundKeyTable;
import func.compoundkey.model.base.BaseCompoundKeyTable;
import org.springframework.stereotype.Repository;

/*
 * It is safe to add methods.
 */
@Repository("compoundKeyTableDao")
public final class CompoundKeyTableDaoImpl extends BaseDaoImpl<CompoundKeyTable, BaseCompoundKeyTable, BaseCompoundKeyTable> implements CompoundKeyTableDao {

    @Override
    protected String namespace() {
        return "func.compoundkey.dao.impl.CompoundKeyTableDaoImpl.";
    }
}