package unit.org.nalby.yobatis.core.mybatis.clazz;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import unit.org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.clazz.BaseCriteria;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseCriteriaTests extends AbstractYobatisTableSetup {

    private IntrospectedTable table;

    @Before
    public void setup() {
        super.setup();
        table = mock(IntrospectedTable.class);
        when(yobatisIntrospectedTable.getWrappedTable()).thenReturn(table);
    }

    @Test
    public void test() throws InvalidUnitException {
        YobatisUnit clazz = BaseCriteria.newInstance(yobatisIntrospectedTable);
        assertTrue(clazz.getFormattedContent().contains("BaseCriteria"));
        clazz.merge("test");
        assertTrue(clazz.getFormattedContent().contains("BaseCriteria"));
    }

}
