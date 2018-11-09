package unit.org.nalby.yobatis.core.mybatis.method;

import org.junit.Test;
import org.nalby.yobatis.core.mybatis.method.DaoMethodName;

public class DaoMethodNameTests {

    @Test(expected = IllegalArgumentException.class)
    public void notFound() {
        DaoMethodName.findByVal("hsss");
    }
}
