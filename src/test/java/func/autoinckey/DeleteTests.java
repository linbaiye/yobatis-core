package func.autoinckey;

import func.autoinckey.dao.CustomerDao;
import func.autoinckey.model.criteria.CustomerCriteria;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DeleteTests extends SetupClass {
    @Resource
    private CustomerDao customerDao;

    @Override
    List<String> tablesToDeleteFrom() {
        return Arrays.asList("customer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByNullPk() {
        customerDao.delete((Long)null);
    }

    @Test
    public void deleteByPk() {
        assertTrue(customerDao.delete(1L) == 0);
        insertViaConnection(1L,"f1", "f2", new BigDecimal("1.1"));
        assertTrue(customerDao.delete(1L) == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByNullCriteria() {
        customerDao.delete((CustomerCriteria)null);
    }

    @Test
    public void deleteByCriteria() {
        insertViaConnection(1L,"f1", "f2", new BigDecimal("1.1"));
        insertViaConnection(2L,"f1", "f2", new BigDecimal("1.1"));
        insertViaConnection(3L,"f1", "f2", new BigDecimal("1.1"));
        assertTrue(customerDao.delete(CustomerCriteria.idEqualTo(1L)) == 1);
        assertTrue(customerDao.delete(CustomerCriteria.f1EqualTo("f1")) == 2);
        assertTrue(customerDao.delete(CustomerCriteria.f1EqualTo("f1")) == 0);
    }

}
