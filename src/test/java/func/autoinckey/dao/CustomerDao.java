package func.autoinckey.dao;

import func.autoinckey.model.Customer;
import func.autoinckey.model.base.BaseCustomer;

/*
 * It is safe to add methods.
 */
public interface CustomerDao extends BaseDao<Customer, BaseCustomer, Long> {
}