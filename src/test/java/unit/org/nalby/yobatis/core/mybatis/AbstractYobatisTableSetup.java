package unit.org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractYobatisTableSetup {

    protected YobatisIntrospectedTable yobatisIntrospectedTable;

    protected List<IntrospectedColumn> columnList;

    public void setup() {
        yobatisIntrospectedTable = mock(YobatisIntrospectedTable.class);
        when(yobatisIntrospectedTable.getTableName()).thenReturn("yobatis");
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.base.BaseYobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.Yobatis"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.YobatisCriteria"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_CRITERIA))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.BaseCriteria"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO_IMPL))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.dao.impl.YobatisDaoImpl"));
        when(yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO))
                .thenReturn(new FullyQualifiedJavaType("org.yobatis.entity.criteria.dao.YobatisDao"));
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("long"));

        columnList = new LinkedList<>();
        when(yobatisIntrospectedTable.getColumns()).thenReturn(this.columnList);
    }

    protected String asString(Method method) {
        return method.getFormattedContent(0, false, new TopLevelClass("test"));
    }

    protected void assertMethodContentEqual(String content, Method method) {
        assertEquals(content.replaceAll("\\s+", ""), asString(method).replaceAll("\\s+", ""));
    }

    protected IntrospectedColumn makeColumn(String name, String jdbcType, String javaProperty) {
        IntrospectedColumn column = mock(IntrospectedColumn.class);
        when(column.getActualColumnName()).thenReturn(name);
        when(column.getJdbcTypeName()).thenReturn(jdbcType);
        when(column.getJavaProperty()).thenReturn(javaProperty);
        return column;
    }



}
