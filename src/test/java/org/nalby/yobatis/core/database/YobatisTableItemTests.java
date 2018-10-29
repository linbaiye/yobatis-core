package org.nalby.yobatis.core.database;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YobatisTableItemTests {

    private IntrospectedTable introspectedTable;

    private Context context;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

    private YobatisTableItem  yobatisTableItem;

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

        context = mock(Context.class);
        when(context.getJavaClientGeneratorConfiguration()).thenReturn(javaClientGeneratorConfiguration);
        when(context.getJavaModelGeneratorConfiguration()).thenReturn(javaModelGeneratorConfiguration);
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

        yobatisTableItem = YobatisTableItemImpl.wrap(introspectedTable);
    }

    @Test
    public void paths() {
        assertEquals("/project/org/yobatis/dao/YobatisDao.java",
                yobatisTableItem.getClassPath(YobatisTableItem.ClassType.DAO));
        assertEquals("/project/org/yobatis/dao/impl/YobatisDaoImpl.java",
                yobatisTableItem.getClassPath(YobatisTableItem.ClassType.DAO_IMPL));
        assertEquals("/project/org/yobatis/entity/Yobatis.java",
                yobatisTableItem.getClassPath(YobatisTableItem.ClassType.ENTITY));
        assertEquals("/project/org/yobatis/entity/base/BaseYobatis.java",
                yobatisTableItem.getClassPath(YobatisTableItem.ClassType.BASE_ENTITY));
        assertEquals("/project/org/yobatis/entity/criteria/BaseCriteria.java",
                yobatisTableItem.getClassPath(YobatisTableItem.ClassType.BASE_CRITERIA));
    }

    @Test
    public void fullyQualifiedNames() {
        assertEquals("org.yobatis.entity.Yobatis",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.base.BaseYobatis",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY).getFullyQualifiedName());
        assertEquals("org.yobatis.dao.YobatisDao",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO).getFullyQualifiedName());
        assertEquals("org.yobatis.dao.impl.YobatisDaoImpl",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO_IMPL).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.criteria.BaseCriteria",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_CRITERIA).getFullyQualifiedName());
        assertEquals("org.yobatis.entity.criteria.YobatisCriteria",
                yobatisTableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA).getFullyQualifiedName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void hasNoPk() {
        when(introspectedTable.hasPrimaryKeyColumns()).thenReturn(false);
        yobatisTableItem = YobatisTableItemImpl.wrap(introspectedTable);
    }

    @Test
    public void singleColumnPk() {
        yobatisTableItem = YobatisTableItemImpl.wrap(introspectedTable);
        assertEquals("long", yobatisTableItem.getPrimaryKey().getFullyQualifiedName());
        assertTrue(yobatisTableItem.isAutoIncPrimaryKey());
    }

    @Test
    public void compoundPk() {
        columnList.add(mock(IntrospectedColumn.class));
        yobatisTableItem = YobatisTableItemImpl.wrap(introspectedTable);
        assertEquals("org.yobatis.entity.base.BaseYobatis", yobatisTableItem.getPrimaryKey().getFullyQualifiedName());
        assertFalse(yobatisTableItem.isAutoIncPrimaryKey());
    }

}
