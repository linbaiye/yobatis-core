package unit.org.nalby.yobatis.core.mybatis;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.mybatis.Settings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SettingsTests {

    private Settings settings;

    @Before
    public void setup() {
        settings = new Settings();
        settings.setUrl("url");
        settings.setPassword("password");
        settings.setUser("username");
    }

    @Test
    public void dbConfigured() {
        assertTrue(settings.isDatabaseConfigured());
    }

    @Test
    public void dbNotConfigured() {
        settings.setUrl("");
        assertFalse(settings.isDatabaseConfigured());
        setup();
        settings.setUser("");
        assertFalse(settings.isDatabaseConfigured());
        setup();
        settings.setPassword("");
        assertFalse(settings.isDatabaseConfigured());
    }

}
