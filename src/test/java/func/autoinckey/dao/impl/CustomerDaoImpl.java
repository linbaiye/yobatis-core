package func.autoinckey.dao.impl;

import func.autoinckey.dao.CustomerDao;
import func.autoinckey.model.Customer;
import func.autoinckey.model.base.BaseCustomer;
import org.springframework.stereotype.Repository;

/*
 * It is safe to add methods.
 */
@Repository("customerDao")
public final class CustomerDaoImpl extends BaseDaoImpl<Customer, BaseCustomer, Long> implements CustomerDao {

    @Override
    protected String namespace() {
        return "func.autoinckey.dao.impl.CustomerDaoImpl.";
    }
}