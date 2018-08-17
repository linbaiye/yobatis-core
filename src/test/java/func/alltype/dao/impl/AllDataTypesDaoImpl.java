package func.alltype.dao.impl;

import func.alltype.dao.AllDataTypesDao;
import func.alltype.model.AllDataTypes;
import func.alltype.model.base.BaseAllDataTypes;
import org.springframework.stereotype.Repository;

/*
 * It is safe to add methods.
 */
@Repository("allDataTypesDao")
public final class AllDataTypesDaoImpl extends BaseDaoImpl<AllDataTypes, BaseAllDataTypes, Long> implements AllDataTypesDao {

    @Override
    protected String namespace() {
        return "func.alltype.dao.impl.AllDataTypesDaoImpl.";
    }
}