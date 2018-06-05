package func.allkey.dao.impl;

import func.allkey.dao.AllKeyTableDao;
import func.allkey.model.AllKeyTable;
import func.allkey.model.base.BaseAllKeyTable;
import org.springframework.stereotype.Repository;

/*
 * It is safe to add methods.
 */
@Repository("allKeyTableDao")
public final class AllKeyTableDaoImpl extends BaseDaoImpl<AllKeyTable, BaseAllKeyTable, BaseAllKeyTable> implements AllKeyTableDao {

    @Override
    protected String namespace() {
        return "func.allkey.dao.impl.AllKeyTableDaoImpl.";
    }
}