package integration.org.nalby.yobatis.core.autoinckey;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.jdbc.BadSqlGrammarException;
import sandbox.alltype.dao.AutoincPkTableDao;
import sandbox.alltype.entity.AutoincPkTable;
import sandbox.alltype.entity.criteria.AutoincPkTableCriteria;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SelectTests extends SetupClass {

    @Resource
    private AutoincPkTableDao autoincPkTableDao;

    @Before
    public void setup() {
        clearTable("autoinc_pk_table");
    }

    @Test(expected = BadSqlGrammarException.class)
    public void selectByNullPk() {
        autoincPkTableDao.selectOne((Long)null);
    }

    @Test(expected = MyBatisSystemException.class)
    public void selectByNullCriteria() {
        autoincPkTableDao.selectOne((AutoincPkTableCriteria)null);
    }

    @Test(expected = MyBatisSystemException.class)
    public void selectByEmptyCriteria() {
        autoincPkTableDao.selectOne(new AutoincPkTableCriteria());
    }

    @Test(expected = MyBatisSystemException.class)
    public void countByNullCriteria() {
        autoincPkTableDao.count(null);
    }

    @Test
    public void selectByPk() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        AutoincPkTable customer = autoincPkTableDao.selectOne(1L);
        assertRecordHasValues(customer, 1L, "f1", "f2", new BigDecimal("3"));
        assertNull(autoincPkTableDao.selectOne(2L));
    }

    @Test
    public void selectOneByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("3"));
        AutoincPkTable customer = autoincPkTableDao.selectOne(AutoincPkTableCriteria.f1EqualTo("f1").andF3EqualTo(new BigDecimal("3")));
        assertRecordHasValues(customer, 1L, "f1", "f2", new BigDecimal("3"));
        try {
            autoincPkTableDao.selectOne(AutoincPkTableCriteria.f2EqualTo("f2"));
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
        List<AutoincPkTable> customerList = autoincPkTableDao.selectList(AutoincPkTableCriteria.f2EqualTo("f2").setLimit(1L));
        assertTrue(customerList.size() == 1);
        assertRecordHasValues(customerList.get(0), 1L, "f1", "f2", new BigDecimal("3"));

        customerList = autoincPkTableDao.selectList(AutoincPkTableCriteria.f2EqualTo("f2").setLimit(1L).setOffset(1L));
        assertTrue(customerList.size() == 1);
        assertRecordHasValues(customerList.get(0), 2L, "x1", "f2", new BigDecimal("4"));
    }


    @Test
    public void selectList() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("3"));
        List<AutoincPkTable> customerList = autoincPkTableDao.selectList(AutoincPkTableCriteria.f2EqualTo("f2"));
        assertTrue(customerList.size() == 2);
        customerList = autoincPkTableDao.selectList(AutoincPkTableCriteria.idIn(Arrays.asList(1L, 2L)));
        assertTrue(customerList.size() == 2);
        customerList = autoincPkTableDao.selectList(AutoincPkTableCriteria.idEqualTo(3L));
        assertTrue(customerList.isEmpty());
    }


    @Test
    public void count() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "f2", new BigDecimal("3"));
        assertTrue(autoincPkTableDao.count(AutoincPkTableCriteria.f1EqualTo("f1").andF2EqualTo("f2")) == 2L);
        assertTrue(autoincPkTableDao.count(AutoincPkTableCriteria.f1EqualTo("f1").andF2EqualTo("f3")) == 0);
    }

    @Test
    public void countWithOr() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("4"));
        insertViaConnection(3L, "z1", "f2", new BigDecimal("5"));
        assertTrue(autoincPkTableDao.count(AutoincPkTableCriteria.f1EqualTo("f1").or().andF3EqualTo(new BigDecimal("4"))) == 2);
    }

    // Just to make sure the method exists.
    @Test
    public void selectForUpdate() {
        autoincPkTableDao.selectOne(AutoincPkTableCriteria.f2EqualTo("f1").setForUpdate(true));
    }

    @Test
    public void selectWithOrder() {
        insertViaConnection(3L, "z1", "f2", new BigDecimal("5"));
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "x1", "f2", new BigDecimal("4"));
        List<AutoincPkTable> list = autoincPkTableDao.selectList(AutoincPkTableCriteria.f2EqualTo("f2").descOrderBy("id"));
        assertEquals(list.get(0).getId().longValue(), 3L);
        assertEquals(list.get(2).getId().longValue(), 1L);

        list = autoincPkTableDao.selectList(AutoincPkTableCriteria.f2EqualTo("f2").ascOrderBy("f3"));
        assertEquals(list.get(0).getId().longValue(), 1L);
        assertEquals(list.get(2).getId().longValue(), 3L);

        insertViaConnection(4L, "a1", "f2", new BigDecimal("3"));
        list = autoincPkTableDao.selectList(AutoincPkTableCriteria.idIsNotNull().ascOrderBy("f3").descOrderBy("f1"));
        assertEquals(list.get(0).getId().longValue(), 1L);
        assertEquals(list.get(1).getId().longValue(), 4L);
    }

}
