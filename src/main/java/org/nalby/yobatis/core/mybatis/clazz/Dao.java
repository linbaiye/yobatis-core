package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.DaoMethodName;
import org.nalby.yobatis.core.mybatis.method.DaoMethodFactory;
import org.nalby.yobatis.core.mybatis.method.MethodFactory;

public class Dao extends Interface implements YobatisUnit {

    private String pathToPut;

    private String existentFile;

    private Dao(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.pathToPut = pathToPut;
        setVisibility(JavaVisibility.PUBLIC);
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {
        existentFile = fileContent;
    }

    @Override
    public String getFormattedContent() {
        if (existentFile != null) {
            return existentFile;
        }
        return super.getFormattedContent();
    }


    public static Dao newInstance(YobatisIntrospectedTable yobatisTable) {
        MethodFactory methodFactory = DaoMethodFactory.getInstance(yobatisTable);
        Dao dao = new Dao(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO),
                yobatisTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO));

        for (DaoMethodName daoMethodName : DaoMethodName.values()) {
            dao.addMethod(methodFactory.create(daoMethodName.getName()));
        }
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA));
        dao.addImportedType(yobatisTable.getPrimaryKey());
        dao.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        return dao;
    }
}
