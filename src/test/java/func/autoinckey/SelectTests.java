package func.autoinckey;

import func.autoinckey.dao.CustomerDao;
import func.autoinckey.model.Customer;
import func.autoinckey.model.criteria.CustomerCriteria;
import org.junit.Test;
import org.mybatis.spring.MyBatisSystemException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SelectTests extends SetupClass {

    @Resource
    private CustomerDao customerDao;

    @Override
    List<String> tablesToDeleteFrom() {
        return Arrays.asList("customer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectByNullPk() {
        customerDao.selectOne((Long)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectByNullCriteria() {
        customerDao.selectOne((CustomerCriteria)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectByEmptyCriteria() {
        customerDao.selectOne(new CustomerCriteria());
    }

    @Test(expected = IllegalArgumentException.class)
    public void countByNullCriteria() {
        customerDao.count(null);
    }

    @Test
    public void selectByPk() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        Customer customer = customerDao.selectOne(1L);
        assertRecord(customer, 1L, "f1", "f2", new BigDecimal("3"));
        assertNull(customerDao.selectOne(2L));
    }

    @Test
    public void selectOneByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("3"));
        Customer customer = customerDao.selectOne(CustomerCriteria.f1EqualTo("f1").andF3EqualTo(new BigDecimal("3")));
        assertRecord(customer, 1L, "f1", "f2", new BigDecimal("3"));
        try {
            customerDao.selectOne(CustomerCriteria.f2EqualTo("f2"));
            fail();
        } catch (MyBatisSystemException e) {
            // Expected.
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void selectWithPaging() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("4"));
        insertViaConnection(3L, "z1", "f2", new BigDecimal("5"));
        List<Customer> customerList = customerDao.selectList(CustomerCriteria.f2EqualTo("f2").setLimit(1L));
        assertTrue(customerList.size() == 1);
        assertRecord(customerList.get(0), 1L, "f1", "f2", new BigDecimal("3"));

        customerList = customerDao.selectList(CustomerCriteria.f2EqualTo("f2").setLimit(1L).setOffset(1L));
        assertTrue(customerList.size() == 1);
        assertRecord(customerList.get(0), 2L, "x1", "f2", new BigDecimal("4"));
    }


    @Test
    public void selectList() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("3"));
        List<Customer> customerList = customerDao.selectList(CustomerCriteria.f2EqualTo("f2"));
        assertTrue(customerList.size() == 2);
        customerList = customerDao.selectList(CustomerCriteria.idIn(Arrays.asList(1L, 2L)));
        assertTrue(customerList.size() == 2);
        customerList = customerDao.selectList(CustomerCriteria.idEqualTo(3L));
        assertTrue(customerList.isEmpty());
    }


    @Test
    public void countAll() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        assertTrue(customerDao.countAll() == 1L);
        insertViaConnection(2L, "f1", "f2", new BigDecimal("3"));
        assertTrue(customerDao.countAll() == 2L);
    }

    @Test
    public void count() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "f2", new BigDecimal("3"));
        assertTrue(customerDao.count(CustomerCriteria.f1EqualTo("f1").andF2EqualTo("f2")) == 2L);
        assertTrue(customerDao.count(CustomerCriteria.f1EqualTo("f1").andF2EqualTo("f3")) == 0);
    }

    @Test
    public void countWithOr() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("4"));
        insertViaConnection(3L, "z1", "f2", new BigDecimal("5"));
        assertTrue(customerDao.count(CustomerCriteria.f1EqualTo("f1").or().andF3EqualTo(new BigDecimal("4"))) == 2);
    }

    // Just to make sure the method exists.
    @Test
    public void selectForUpdate() {
        customerDao.selectOne(CustomerCriteria.f2EqualTo("f1").setForUpdate(true));
    }

    @Test
    public void selectWithOrder() {
        insertViaConnection(3L, "z1", "f2", new BigDecimal("5"));
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("4"));
        List<Customer> list = customerDao.selectList(CustomerCriteria.f2EqualTo("f2").descOrderBy("id"));
        assertEquals(list.get(0).getId().longValue(), 3L);
        assertEquals(list.get(2).getId().longValue(), 1L);

        list = customerDao.selectList(CustomerCriteria.f2EqualTo("f2").ascOrderBy("f3"));
        assertEquals(list.get(0).getId().longValue(), 1L);
        assertEquals(list.get(2).getId().longValue(), 3L);

        insertViaConnection(4L, "a1", "f2", new BigDecimal("3"));
        list = customerDao.selectList(CustomerCriteria.idIsNotNull().ascOrderBy("f3").descOrderBy("f1"));
        assertEquals(list.get(0).getId().longValue(), 1L);
        assertEquals(list.get(1).getId().longValue(), 4L);
    }

}
