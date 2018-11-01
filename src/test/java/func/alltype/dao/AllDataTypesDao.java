package func.alltype.dao;

import func.alltype.model.AllDataTypes;
import func.alltype.model.base.BaseAllDataTypes;

public interface AllDataTypesDao extends BaseDao<BaseAllDataTypes, AllDataTypes, Long> {

    <K extends BaseAllDataTypes> K select1();
}