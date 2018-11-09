package integration.org.nalby.yobatis.core.autoinckey;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.jdbc.BadSqlGrammarException;
import sandbox.alltype.dao.AutoincPkTableDao;
import sandbox.alltype.entity.criteria.AutoincPkTableCriteria;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class DeleteTests extends SetupClass {

    @Resource
    private AutoincPkTableDao autoincPkTableDao;


    @Before
    public void setup() {
        clearTable("autoinc_pk_table");
    }

    @Test(expected = BadSqlGrammarException.class)
    public void deleteByNullPk() {
        autoincPkTableDao.delete((Long)null);
    }

    @Test
    public void deleteByPk() {
        assertTrue(autoincPkTableDao.delete(1L) == 0);
        insertViaConnection(1L,"f1", "f2", new BigDecimal("1.1"));
        assertTrue(autoincPkTableDao.delete(1L) == 1);
    }

    @Test(expected = MyBatisSystemException.class)
    public void deleteByNullCriteria() {
        autoincPkTableDao.delete((AutoincPkTableCriteria) null);
    }

    @Test
    public void deleteByCriteria() {
        insertViaConnection(1L,"f1", "f2", new BigDecimal("1.1"));
        insertViaConnection(2L,"f1", "f2", new BigDecimal("1.1"));
        insertViaConnection(3L,"f1", "f2", new BigDecimal("1.1"));
        assertTrue(autoincPkTableDao.delete(AutoincPkTableCriteria.idEqualTo(1L)) == 1);
        assertTrue(autoincPkTableDao.delete(AutoincPkTableCriteria.f1EqualTo("f1")) == 2);
        assertTrue(autoincPkTableDao.delete(AutoincPkTableCriteria.f1EqualTo("f1")) == 0);
    }

}
