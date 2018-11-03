package unit.org.nalby.yobatis.core.mybatis.clazz;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import unit.org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;
import org.nalby.yobatis.core.mybatis.clazz.Criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CriteriaTests extends AbstractYobatisTableSetup {

    @Before
    public void setup() {
        super.setup();
        when(yobatisIntrospectedTable
                .getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.CRITERIA))
                .thenReturn("/yobatis/YobatisCriteria.java");
    }

    protected IntrospectedColumn addColumn(String property, String jdbcType, String type) {
        IntrospectedColumn column = mock(IntrospectedColumn.class);
        when(column.getFullyQualifiedJavaType()).thenReturn(new FullyQualifiedJavaType(type));
        when(column.getJavaProperty()).thenReturn(property);
        when(column.getJdbcTypeName()).thenReturn(jdbcType);
        when(column.getActualColumnName()).thenReturn(property);
        columnList.add(column);
        return column;
    }

    @Test
    public void all() throws InvalidUnitException {
        addColumn("id", "id", "Integer");
        Criteria criteria = Criteria.newInstance(yobatisIntrospectedTable);
        assertTrue(criteria.getFormattedContent().contains("import java.util.HashSet;\n" +
                "import java.util.Set;"));
        criteria.merge("test");
        assertEquals("/yobatis/YobatisCriteria.java", criteria.getPathToPut());
        addColumn("f1", "DATE", "java.util.Date");
        criteria = Criteria.newInstance(yobatisIntrospectedTable);
        assertTrue(criteria.getFormattedContent().contains("import java.util.Date;"));
    }
}
