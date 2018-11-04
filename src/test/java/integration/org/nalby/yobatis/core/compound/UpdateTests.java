package integration.org.nalby.yobatis.core.compound;

import org.junit.Test;
import org.mybatis.spring.MyBatisSystemException;
import sandbox.alltype.entity.CompoundKeyTable;
import sandbox.alltype.entity.criteria.CompoundKeyTableCriteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateTests extends SetupClass {

    @Test
    public void updateByNullPk() {
        assertEquals(0, compoundKeyTableDao.update(null));
    }

    @Test(expected = MyBatisSystemException.class)
    public void updateByNullCriteria() {
        compoundKeyTableDao.update(new CompoundKeyTable(), null);
    }

    @Test
    public void updateByPk() {
        insertViaConnection(1, "1", "3");
        insertViaConnection(1, "2", "3");
        CompoundKeyTable table = build(1, "1", "2");
        assertTrue(1 == compoundKeyTableDao.update(table));
        CompoundKeyTable selected = compoundKeyTableDao.selectOne(CompoundKeyTableCriteria.pk2EqualTo("1"));
        assertRecord(selected, 1, "1", "2");

        selected = compoundKeyTableDao.selectOne(CompoundKeyTableCriteria.pk2EqualTo("2"));
        assertRecord(selected, 1, "2", "3");

        table.setPk1(3);
        assertTrue(0 == compoundKeyTableDao.update(table));
    }

    @Test
    public void updateByCriteria() {
        insertViaConnection(1, "1", "3");
        insertViaConnection(1, "2", "3");
        CompoundKeyTable table = build(null, null, "2");
        assertTrue(2 == compoundKeyTableDao.update(table, CompoundKeyTableCriteria.pk1EqualTo(1)));
        CompoundKeyTable selected = compoundKeyTableDao.selectOne(CompoundKeyTableCriteria.pk2EqualTo("1"));
        assertRecord(selected, 1, "1", "2");
        selected = compoundKeyTableDao.selectOne(CompoundKeyTableCriteria.pk2EqualTo("2"));
        assertRecord(selected, 1, "2", "2");
    }
}
