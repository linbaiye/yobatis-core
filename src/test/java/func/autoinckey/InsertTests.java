package func.autoinckey;

import func.autoinckey.dao.CustomerDao;
import func.autoinckey.model.Customer;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InsertTests extends SetupClass {

    @Resource
    private CustomerDao customerDao;

    @Override
    List<String> tablesToDeleteFrom() {
        return Arrays.asList("customer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNull() {
        customerDao.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAllNull() {
        customerDao.insertAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAllIgnoreNull() {
        customerDao.insertAllIgnore(null);
    }

    @Test
    public void insert() {
        Customer customer = buildRecord(null, "f1", "f2", new BigDecimal("1.1"));
        assertTrue(customerDao.insert(customer) == 1);
        List<Customer> result = selectCustomers();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getId() != null);
        result.get(0).setId(null);
        assertRecord(result.get(0), null, "f1", "f2", new BigDecimal("1.1"));
    }

    @Test
    public void insertAll() {
        Customer customer = buildRecord(1L, "f1", "f2", new BigDecimal("1.1"));
        assertTrue(customerDao.insertAll(customer) == 1);
        List<Customer> result = selectCustomers();
        assertTrue(result.size() == 1);
        assertRecord(result.get(0), 1L, "f1", "f2", new BigDecimal("1.1"));
    }

    @Test(expected = DuplicateKeyException.class)
    public void insertAllDuplicateKey() {
        Customer customer = buildRecord(1L, "f1", "f2", new BigDecimal("1.1"));
        customerDao.insertAll(customer);
        customerDao.insertAll(customer);
    }

    @Test
    public void insertAllIgnore() {
        Customer customer = buildRecord(1L, "f1", "f2", new BigDecimal("1.1"));
        assertTrue(customerDao.insertAll(customer) == 1);
        assertTrue(customerDao.insertAllIgnore(customer) == 0);
    }

}
