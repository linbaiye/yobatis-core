package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
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


    public static Dao build(YobatisIntrospectedTable yobatisTable) {
        MethodFactory methodFactory = DaoMethodFactory.getInstance(yobatisTable);
        Dao dao = new Dao(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.DAO),
                yobatisTable.getPathForGeneratedFile(YobatisIntrospectedTable.ClassType.DAO));
        for (String name : DaoMethodName.listMethodNamesByGroup(DaoMethodName.DAO_GROUP)) {
            dao.addMethod(methodFactory.create(name));
        }
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA));
        dao.addImportedType(yobatisTable.getPrimaryKey());
        return dao;
    }
}
