package func.alltype.dao.impl;

import func.alltype.dao.AllDataTypesDao;
import func.alltype.model.AllDataTypes;
import func.alltype.model.base.BaseAllDataTypes;
import org.springframework.stereotype.Component;

@Component("allDataTypesDao")
public class AllDataTypesDaoImpl extends BaseDaoImpl<BaseAllDataTypes, AllDataTypes, Long> implements AllDataTypesDao {

    @Override
    protected String namespace() {
        return "func.alltype.dao.impl.AllDataTypesDaoImpl.";
    }


    @Override
    public <K extends BaseAllDataTypes> K select1() {
        return sqlSessionTemplate.selectOne(namespace() + "select1");
    }
}