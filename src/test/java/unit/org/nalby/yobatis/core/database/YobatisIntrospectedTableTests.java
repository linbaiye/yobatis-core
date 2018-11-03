package unit.org.nalby.yobatis.core.database;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTableImpl;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YobatisIntrospectedTableTests {

    private IntrospectedTable introspectedTable;

    private Context context;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    private YobatisIntrospectedTable yobatisIntrospectedTable;

    private List<IntrospectedColumn> columnList;

    private IntrospectedColumn introspectedColumn;

    @Before
    public void setup() {
        javaModelGeneratorConfiguration = mock(JavaModelGeneratorConfiguration.class);
        when(javaModelGeneratorConfiguration.getTargetProject()).thenReturn("/project");
        when(javaModelGeneratorConfiguration.getTargetPackage()).thenReturn("org.yobatis.entity");

        javaClientGeneratorConfiguration = mock(JavaClientGeneratorConfiguration.class);
        when(javaClientGeneratorConfiguration.getTargetProject()).thenReturn("/project");
        when(javaClientGeneratorConfiguration.getTargetPackage()).thenReturn("org.yobatis.dao");

        sqlMapGeneratorConfiguration = mock(SqlMapGeneratorConfiguration.class);
        when(sqlMapGeneratorConfiguration.getTargetProject()).thenReturn("/project/resources");
        when(sqlMapGeneratorConfiguration.getTargetPackage()).thenReturn("mappers");

        context = mock(Context.class);
        when(context.getJavaClientGeneratorConfiguration()).thenReturn(javaClientGeneratorConfiguration);
        when(context.getJavaModelGeneratorConfiguration()).thenReturn(javaModelGeneratorConfiguration);
        when(context.getSqlMapGeneratorConfiguration()).thenReturn(sqlMapGeneratorConfiguration);
        introspectedTable = mock(IntrospectedTable.class);

        when(introspectedTable.getBaseRecordType()).thenReturn("org.yobatis.entity.Yobatis");

        when(introspectedTable.getContext()).thenReturn(context);
        when(introspectedTable.hasPrimaryKeyColumns()).thenReturn(true);

        introspectedColumn = mock(IntrospectedColumn.class);
        when(introspectedColumn.getFullyQualifiedJavaType()).thenReturn(new FullyQualifiedJavaType("long"));
        when(introspectedColumn.isAutoIncrement()).thenReturn(true);

        columnList = new LinkedList<>();
        columnList.add(introspectedColumn);

        when(introspectedTable.getPrimaryKeyColumns()).thenReturn(columnList);

        yobatisIntrospectedTable = YobatisIntrospectedTableImpl.wrap(introspectedTable);
    }

    @Test
    public void paths() {
        assertEquals("/project/org/yobatis/dao/YobatisDao.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO));
        assertEquals("/project/org/yobatis/dao/impl/YobatisDaoImpl.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO_IMPL));
        assertEquals("/project/org/yobatis/entity/Yobatis.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.ENTITY));
        assertEquals("/project/org/yobatis/entity/base/BaseYobatis.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.BASE_ENTITY));
        assertEquals("/project/org/yobatis/entity/criteria/BaseCriteria.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.BASE_CRITERIA));
        assertEquals("/project/org/yobatis/entity/criteria/YobatisCriteria.java",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.CRITERIA));
        assertEquals("/project/resources/mappers/YobatisMapper.xml",
                yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.XML_MAPPER));
    }

    @Test
    public void fullyQualifiedNames() {
        assertEquals("org.yobatis.entity.Yobatis",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.base.BaseYobatis",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY).getFullyQualifiedName());
        assertEquals("org.yobatis.dao.YobatisDao",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO).getFullyQualifiedName());
        assertEquals("org.yobatis.dao.impl.YobatisDaoImpl",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO_IMPL).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.criteria.BaseCriteria",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_CRITERIA).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.criteria.YobatisCriteria",
                yobatisIntrospectedTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA).getFullyQualifiedName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void hasNoPk() {
        when(introspectedTable.hasPrimaryKeyColumns()).thenReturn(false);
        yobatisIntrospectedTable = YobatisIntrospectedTableImpl.wrap(introspectedTable);
    }

    @Test
    public void singleColumnPk() {
        yobatisIntrospectedTable = YobatisIntrospectedTableImpl.wrap(introspectedTable);
        assertEquals("long", yobatisIntrospectedTable.getPrimaryKey().getFullyQualifiedName());
        assertTrue(yobatisIntrospectedTable.isAutoIncPrimaryKey());
    }

    @Test
    public void compoundPk() {
        columnList.add(mock(IntrospectedColumn.class));
        yobatisIntrospectedTable = YobatisIntrospectedTableImpl.wrap(introspectedTable);
        assertEquals("org.yobatis.entity.base.BaseYobatis", yobatisIntrospectedTable.getPrimaryKey().getFullyQualifiedName());
        assertFalse(yobatisIntrospectedTable.isAutoIncPrimaryKey());
    }

}
