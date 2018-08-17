package func.alltype;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-config.xml")
public abstract class SetupClass {

    @Resource
    protected DataSource masterDataSource;

    protected void setup() { }

    @Before
    public void _setup() {
        try (Connection connection = masterDataSource.getConnection()) {
                connection.prepareStatement("delete from all_data_types").execute();
            connection.close();
            setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
