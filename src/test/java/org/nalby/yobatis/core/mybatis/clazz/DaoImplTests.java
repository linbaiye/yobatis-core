package org.nalby.yobatis.core.mybatis.clazz;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class DaoImplTests extends AbstractYobatisTableSetup {

    @Before
    public void setup() {
        super.setup();
        when(yobatisIntrospectedTable.getPrimaryKey()).thenReturn(new FullyQualifiedJavaType("Integer"));
    }


    @Test
    public void all() throws InvalidUnitException {
        when(yobatisIntrospectedTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO_IMPL)).thenReturn("/yobatisdao.java");
        DaoImpl dao = DaoImpl.build(yobatisIntrospectedTable);
        assertTrue(dao.getFormattedContent().contains("public class YobatisDaoImpl implements YobatisDao"));

        String current = "current";
        dao.merge(current);
        assertEquals(current, dao.getFormattedContent());
        assertEquals("/yobatisdao.java", dao.getPathToPut());

    }

}
