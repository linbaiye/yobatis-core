package integration.org.nalby.yobatis.core.compound;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import sandbox.alltype.entity.CompoundKeyTable;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InsertTests extends SetupClass {

    @Before
    public void setup() {
        clearTable("compound_key_table");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertNullRecord() {
        assertEquals(0, compoundKeyTableDao.insert(null));
    }

    @Test
    public void insert() {
        CompoundKeyTable record = build(1, "1", null);
        compoundKeyTableDao.insert(record);
        List<CompoundKeyTable> selected = selectListViaConnection();
        assertTrue(selected.size() == 1);
        assertRecord(selected.get(0), 1, "1", null);
    }

}
