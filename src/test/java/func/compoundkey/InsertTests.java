package func.compoundkey;

import func.compoundkey.model.CompoundKeyTable;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class InsertTests extends SetupClass {


    @Test(expected = IllegalArgumentException.class)
    public void insertNullRecord() {
        compoundKeyTableDao.insert(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertAllNullRecord() {
        compoundKeyTableDao.insertAll(null);
    }

    @Test
    public void insertAllIgnore() {
        CompoundKeyTable record = build(1, "1", null);
        assertTrue(compoundKeyTableDao.insertAll(record) == 1);
        assertTrue(compoundKeyTableDao.insertAllIgnore(record) == 0);
    }

    @Test
    public void insertAll() {
        CompoundKeyTable record = build(1, "1", null);
        compoundKeyTableDao.insertAll(record);
        List<CompoundKeyTable> selected = selectListViaConnection();
        assertTrue(selected.size() == 1);
        assertRecord(selected.get(0), 1, "1", null);
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
