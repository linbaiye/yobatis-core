package func.autoinckey;

import func.autoinckey.model.Customer;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-config.xml")
public abstract class SetupClass {

    @Resource
    protected DataSource masterDataSource;

    List<String> tablesToDeleteFrom() {
        return Collections.EMPTY_LIST;
    }

    void setup() {}

    protected Customer buildRecord(Long id, String f1, String f2, BigDecimal f3) {
        Customer customer = new Customer();
        customer.setF1(f1);
        customer.setF2(f2);
        customer.setF3(f3);
        customer.setId(id);
        return customer;
    }

    protected void assertRecord(Customer record, Long id, String f1, String f2, BigDecimal f3) {
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
            connection.prepareStatement("insert into customer (f1, f2, f3, id) values ( '" +f1 +
                    "','" + f2 + "', '" + f3.toString() + "', " + id + ")").execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void insertViaConnection(String f1, String f2, BigDecimal f3) {
        try (Connection connection = masterDataSource.getConnection()) {
            connection.prepareStatement("insert into customer (f1, f2, f3) values ( '" +f1 +
                    "','" + f2 + "', '" + f3.toString() + "')").execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    protected List<Customer> selectCustomers() {
        List<Customer> list = new LinkedList<Customer>();
        try (Connection connection = masterDataSource.getConnection()) {
            ResultSet rs = connection.prepareStatement("select * from customer").executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
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

}

