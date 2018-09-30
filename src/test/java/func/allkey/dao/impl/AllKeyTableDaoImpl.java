package func.allkey.dao.impl;

import func.allkey.dao.AllKeyTableDao;
import func.allkey.model.AllKeyTable;
import func.allkey.model.base.BaseAllKeyTable;
import org.springframework.stereotype.Component;

/*
 * It is safe to add code to this file.
 */
@Component("allKeyTableDao")
public final class AllKeyTableDaoImpl extends BaseDaoImpl<BaseAllKeyTable, AllKeyTable, BaseAllKeyTable> implements AllKeyTableDao {

    @Override
    protected String namespace() {
        return "func.allkey.dao.impl.AllKeyTableDaoImpl.";
    }
}