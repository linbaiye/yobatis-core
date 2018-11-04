package integration.org.nalby.yobatis.core.autoinckey;

import org.junit.Before;
import org.junit.Test;
import sandbox.alltype.dao.AutoincPkTableDao;
import sandbox.alltype.entity.AutoincPkTable;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InsertTests extends SetupClass {

    @Resource
    private AutoincPkTableDao autoincPkTableDao;

    @Before
    public void setup() {
        clearTable("autoinc_pk_table");
    }

    @Test
    public void insertWithKey() {
        AutoincPkTable record = buildRecord(100L, "f1", "f2", new BigDecimal("1.1"));
        assertEquals(1, autoincPkTableDao.insert(record));
        assertNotNull(autoincPkTableDao.selectOne(100L));
    }

    @Test
    public void insertWithoutKey() {
        AutoincPkTable customer = buildRecord(null, "f1", "f2", new BigDecimal("1.1"));
        assertTrue(autoincPkTableDao.insert(customer) == 1);
        List<AutoincPkTable> result = selectAutoincPkRecords();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getId() != null);
        result.get(0).setId(null);
        assertRecordHasValues(result.get(0), null, "f1", "f2", new BigDecimal("1.1"));
    }
}
