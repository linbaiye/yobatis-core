package unit.org.nalby.yobatis.core.database;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.database.MysqlDatabaseMetadataProvider;
import org.nalby.yobatis.core.exception.InvalidSqlConfigException;

import static org.junit.Assert.assertNotNull;

public class BuilderTests {

    private MysqlDatabaseMetadataProvider.Builder builder;

    @Before
    public void setup() {
        builder = MysqlDatabaseMetadataProvider.builder();
        builder.setConnectorJarPath("not null");
        builder.setDriverClassName("com.mysql.jdbc.Driver");
        builder.setPassword("not null");
        builder.setUrl("not null");
        builder.setUsername("not null");
    }

    @Test
    public void ok() {
        assertNotNull(builder.build());
    }

    @Test(expected = InvalidSqlConfigException.class)
    public void emptyUser() {
        builder.setUsername(null).build();
    }

    @Test(expected = InvalidSqlConfigException.class)
    public void emptyPassword() {
        builder.setPassword(null).build();
    }

    @Test(expected = InvalidSqlConfigException.class)
    public void emptyUrl() {
        builder.setPassword("").build();
    }

    @Test(expected = InvalidSqlConfigException.class)
    public void emptyConnector() {
        builder.setConnectorJarPath("").build();
    }

    @Test(expected = InvalidSqlConfigException.class)
    public void emptyDriver() {
        builder.setDriverClassName("").build();
    }
}
