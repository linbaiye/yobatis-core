package func.compoundkey;

import func.compoundkey.model.CompoundKeyTable;
import func.compoundkey.model.criteria.CompoundKeyTableCriteria;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DeleteTests extends SetupClass {

    @Test(expected = IllegalArgumentException.class)
    public void deleteByNullPk() {
        compoundKeyTableDao.delete((CompoundKeyTable)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByNullCriteria() {
        compoundKeyTableDao.delete((CompoundKeyTableCriteria)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByEmptyCriteria() {
        compoundKeyTableDao.delete(new CompoundKeyTableCriteria());
    }

    @Test
    public void deleteByPk() {
        insertViaConnection(1, "1", "3");
        CompoundKeyTable pk = new CompoundKeyTable();
        pk.setPk1(1);
        pk.setPk2("2");
        assertTrue(compoundKeyTableDao.delete(pk) == 0);
        List<CompoundKeyTable> list = selectListViaConnection();
        assertTrue(list.size() == 1);
        pk.setPk2("1");
        assertTrue(compoundKeyTableDao.delete(pk) == 1);
        list = selectListViaConnection();
        assertTrue(list.size() == 0);
    }

    @Test
    public void deleteByCriteria() {
        insertViaConnection(1, "1", "3");
        assertTrue(compoundKeyTableDao.delete(CompoundKeyTableCriteria.pk1EqualTo(2)) == 0);
        List<CompoundKeyTable> list = selectListViaConnection();
        assertTrue(list.size() == 1);
        assertTrue(compoundKeyTableDao.delete(CompoundKeyTableCriteria.pk1EqualTo(1)) == 1);
        list = selectListViaConnection();
        assertTrue(list.size() == 0);
    }
}
