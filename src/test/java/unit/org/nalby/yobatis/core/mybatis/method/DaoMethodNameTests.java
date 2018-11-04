package unit.org.nalby.yobatis.core.mybatis.method;

import org.junit.Test;
import org.nalby.yobatis.core.mybatis.method.DaoMethodName;

import static org.junit.Assert.assertTrue;

public class DaoMethodNameTests {

    @Test
    public void nameEqual() {
        assertTrue(DaoMethodName.COUNT.nameEquals("count"));
    }

}
