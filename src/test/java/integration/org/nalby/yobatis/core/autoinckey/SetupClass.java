package integration.org.nalby.yobatis.core.autoinckey;

import integration.org.nalby.yobatis.core.AbstractSpringSetup;
import sandbox.alltype.entity.AutoincPkTable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class SetupClass extends AbstractSpringSetup {

    protected AutoincPkTable buildRecord(Long id, String f1, String f2, BigDecimal f3) {
        AutoincPkTable customer = new AutoincPkTable();
        customer.setF1(f1);
        customer.setF2(f2);
        customer.setF3(f3);
        customer.setId(id);
        return customer;
    }

    protected void assertRecordHasValues(AutoincPkTable record, Long id, String f1, String f2, BigDecimal f3) {
        if (id == null) {
            assertNull(record.getId());
        } else {
            assertTrue(record.getId() == id.longValue());
        }
        if (f1 == null) {
            assertNull(record.getF1());
        } else {
            assertTrue(f1.equals(record.getF1()));
        }
        if (f2 == null) {
            assertNull(record.getF2());
        } else {
            assertTrue(f2.equals(record.getF2()));
        }
        if (f3 == null) {
            assertNull(record.getF3());
        } else {
            assertTrue(f3.compareTo(record.getF3()) == 0);
        }
    }

    protected void insertViaConnection(Long id, String f1, String f2, BigDecimal f3) {
        try (Connection connection = masterDataSource.getConnection()) {
            connection.prepareStatement("insert into autoinc_pk_table (f1, f2, f3, id) values ( '" +f1 +
                    "','" + f2 + "', '" + f3.toString() + "', " + id + ")").execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    protected List<AutoincPkTable> selectAutoincPkRecords() {
        List<AutoincPkTable> list = new LinkedList<>();
        try (Connection connection = masterDataSource.getConnection()) {
            ResultSet rs = connection.prepareStatement("select * from autoinc_pk_table").executeQuery();
            while (rs.next()) {
                AutoincPkTable customer = new AutoincPkTable();
                customer.setId(rs.getLong("id"));
                customer.setF1(rs.getString("f1"));
                customer.setF2(rs.getString("f2"));
                customer.setF3(rs.getBigDecimal("f3"));
                list.add(customer);
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

