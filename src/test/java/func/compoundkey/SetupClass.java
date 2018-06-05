package func.compoundkey;

import func.compoundkey.dao.CompoundKeyTableDao;
import func.compoundkey.model.CompoundKeyTable;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-config.xml")
public abstract class SetupClass {

    @Resource
    protected DataSource masterDataSource;

    @Resource
    protected CompoundKeyTableDao compoundKeyTableDao;

    List<String> tablesToDeleteFrom() {
        return Arrays.asList("compound_key_table");
    }

    void setup() {}

    protected CompoundKeyTable build(Integer pk1, String pk2, String f3) {
        CompoundKeyTable table = new CompoundKeyTable();
        table.setPk1(pk1);
        table.setPk2(pk2);
        table.setF3(f3);
        return table;
    }


    protected void insertViaConnection(int pk1, String pk2, String f3) {
        try (Connection connection = masterDataSource.getConnection()) {
            connection.prepareStatement("insert into compound_key_table(pk1, pk2, f3) values ( " + pk1 +
                    ",'" + pk2 + "', '" + f3 + "')").execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    protected List<CompoundKeyTable> selectListViaConnection() {
        List<CompoundKeyTable> list = new LinkedList<>();
        try (Connection connection = masterDataSource.getConnection()) {
            ResultSet rs = connection.prepareStatement("select * from compound_key_table").executeQuery();
            while (rs.next()) {
                CompoundKeyTable table = new CompoundKeyTable();
                table.setPk1(rs.getInt("pk1"));
                table.setPk2(rs.getString("pk2"));
                table.setF3(rs.getString("f3"));
                list.add(table);
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void _setup() {
        try (Connection connection = masterDataSource.getConnection()) {
            for (String table :tablesToDeleteFrom()) {
                connection.prepareStatement("delete from " + table).execute();
            }
            connection.close();
            setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void assertRecord(CompoundKeyTable table, Integer pk1, String pk2, String f3) {
        if (pk1 == null) {
            assertNull(table.getPk1());
        } else  {
            assertEquals(pk1.intValue(), table.getPk1().intValue());
        }
        if (f3 == null) {
            assertNull(table.getF3());
        } else {
            assertEquals(f3, table.getF3());
        }
        if (pk2 == null) {
            assertNull(table.getPk2());
        } else {
            assertEquals(pk2, table.getPk2());
        }

    }

}
