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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpdateTests extends SetupClass {
    @Resource
    private AutoincPkTableDao autoincPkTableDao;

    @Before
    public void setup() {
        clearTable("autoinc_pk_table");
    }

    @Test(expected = BadSqlGrammarException.class)
    public void updateByNullPk() {
        autoincPkTableDao.update(null);
    }


    @Test(expected = MyBatisSystemException.class)
    public void updateByNullCriteria() {
        try {
            autoincPkTableDao.update(null, null);
            fail();
        } catch (IllegalArgumentException e) {
            //Expected.
        }
        autoincPkTableDao.update(new AutoincPkTable(), null);
    }

    @Test(expected = MyBatisSystemException.class)
    public void updateByEmptyCriteria() {
        autoincPkTableDao.update(buildRecord(1L, "f3", "f4", null), new AutoincPkTableCriteria());
    }

    @Test
    public void updateByPk() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        AutoincPkTable customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(autoincPkTableDao.update(customer) == 1);
        assertRecordHasValues(autoincPkTableDao.selectOne(1L), 1L, "f3", "f4", new BigDecimal("3"));
    }

    @Test
    public void updateByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "z2", new BigDecimal("3"));
        AutoincPkTable customer = buildRecord(1L, "f3", "f4", null);
        assertTrue(autoincPkTableDao.update(customer, AutoincPkTableCriteria.f1EqualTo("f1").andF2EqualTo("f2")) == 1);
        assertRecordHasValues(autoincPkTableDao.selectOne(1L), 1L, "f3", "f4", new BigDecimal("3"));
        assertRecordHasValues(autoincPkTableDao.selectOne(2L), 2L, "f1", "z2", new BigDecimal("3"));
    }

    @Test
    public void updatePkByCriteria() {
        insertViaConnection(1L, "f1", "f2", new BigDecimal("3"));
        insertViaConnection(2L, "f1", "z2", new BigDecimal("3"));
        AutoincPkTable customer = buildRecord(3L, "f3", "f4", null);
        assertEquals(1, autoincPkTableDao.update(customer, AutoincPkTableCriteria.f1EqualTo("f1").andF2EqualTo("f2")));
        assertRecordHasValues(autoincPkTableDao.selectOne(3L), 3L, "f3", "f4", new BigDecimal("3"));
    }

}
