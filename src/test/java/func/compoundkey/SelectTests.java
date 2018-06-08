package func.compoundkey;

import func.compoundkey.model.CompoundKeyTable;
import func.compoundkey.model.criteria.CompoundKeyTableCriteria;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SelectTests extends SetupClass {

    @Test(expected = IllegalArgumentException.class)
    public void selectByNullPk() {
        compoundKeyTableDao.selectOne((CompoundKeyTable)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectByNullCriteria() {
        compoundKeyTableDao.selectOne((CompoundKeyTableCriteria)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectByEmptyCriteria() {
        compoundKeyTableDao.selectOne(new CompoundKeyTableCriteria());
    }

    @Test
    public void selectOneByCriteria() {
        insertViaConnection(1, "1", "3");
        CompoundKeyTable selected = compoundKeyTableDao.selectOne(CompoundKeyTableCriteria.pk1EqualTo(1));
        assertRecord(selected, 1, "1", "3");
    }

    @Test
    public void selectByPk() {
        CompoundKeyTable table = build(1, "1", null);
        insertViaConnection(1, "1", "3");
        CompoundKeyTable selected = compoundKeyTableDao.selectOne(table);
        assertRecord(selected, 1, "1", "3");
    }

    @Test
    public void selectList() {
        insertViaConnection(1, "1", "3");
        insertViaConnection(2, "1", "3");
        insertViaConnection(3, "1", "3");
        List<CompoundKeyTable> selected = compoundKeyTableDao.selectList(CompoundKeyTableCriteria.pk2EqualTo("1"));
        assertTrue(selected.size() == 3);
    }

}
