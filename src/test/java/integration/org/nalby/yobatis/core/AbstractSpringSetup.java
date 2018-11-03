package integration.org.nalby.yobatis.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-config.xml")
public abstract class AbstractSpringSetup {

    @Resource
    protected DataSource masterDataSource;

    protected void clearTable(String table) {
        try (Connection connection = masterDataSource.getConnection()) {
            connection.prepareStatement("delete from " + table).execute();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
