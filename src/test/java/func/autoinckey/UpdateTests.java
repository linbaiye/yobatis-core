package func.autoinckey;

import func.autoinckey.dao.CustomerDao;
import func.autoinckey.model.Customer;
import func.autoinckey.model.criteria.CustomerCriteria;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpdateTests extends SetupClass {
    @Resource
    private CustomerDao customerDao;

    @Override
    List<String> tablesToDeleteFrom() {
        return Arrays.asList("customer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateByNullPk() {
        customerDao.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAllByNullPk() {
        customerDao.updateAll(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateByNullCriteria() {
        try {
            customerDao.update(null, null);
            fail();
        } catch (IllegalArgumentException e) {
            //Expected.
        }
        customerDao.update(new Customer(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateByEmptyCriteria() {
        customerDao.update(new Customer(), new CustomerCriteria());
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateAllByNullCriteria() {
        try {
            customerDao.updateAll(null, null);
            fail();
        } catch (IllegalArgumentException e) {
            //Expected.
        }
        customerDao.updateAll(new Customer(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAllByEmptyCriteria() {
        customerDao.updateAll(new Customer(), new CustomerCriteria());
    }

    @Test
    public void updateByPk() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        Customer customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(customerDao.update(customer) == 1);
        assertRecord(customerDao.selectOne(1L), 1L, "f3", "f4", new BigDecimal("3"));
    }

    @Test
    public void updateAllByPk() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        Customer customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(customerDao.updateAll(customer) == 1);
        assertRecord(customerDao.selectOne(1L), 1L, "f3", "f4", null);
    }

    @Test
    public void updateByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "z2", new BigDecimal("3"));
        Customer customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(customerDao.update(customer, CustomerCriteria.f1EqualTo("f1").andF2EqualTo("f2")) == 1);
        assertRecord(customerDao.selectOne(1L), 1L, "f3", "f4", new BigDecimal("3"));
        assertRecord(customerDao.selectOne(2L), 2L, "f1", "z2", new BigDecimal("3"));
    }

    @Test
    public void updateAllByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "z2", new BigDecimal("3"));
        Customer customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(customerDao.updateAll(customer, CustomerCriteria.f1EqualTo("f1").andF2EqualTo("f2")) == 1);
        assertRecord(customerDao.selectOne(1L), 1L, "f3", "f4", null);
        assertRecord(customerDao.selectOne(2L), 2L, "f1", "z2", new BigDecimal("3"));
    }

}
