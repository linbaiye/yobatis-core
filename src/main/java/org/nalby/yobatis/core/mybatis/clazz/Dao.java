package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.nalby.yobatis.core.database.YobatisTableItem;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.DaoMethodFactory;
import org.nalby.yobatis.core.mybatis.method.DaoMethodFactoryImpl;

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
    public static Dao build(YobatisTableItem yobatisTable) {
        DaoMethodFactory methodFactory = DaoMethodFactoryImpl.getInstance();
        Dao dao = new Dao(yobatisTable.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO),
                yobatisTable.getClassPath(YobatisTableItem.ClassType.DAO));
        dao.addMethod(methodFactory.insert(yobatisTable));
        dao.addMethod(methodFactory.selectOne(yobatisTable));
        dao.addMethod(methodFactory.selectOneByCriteria(yobatisTable));
        dao.addMethod(methodFactory.selectList(yobatisTable));
        dao.addMethod(methodFactory.count(yobatisTable));
        dao.addMethod(methodFactory.update(yobatisTable));
        dao.addMethod(methodFactory.updateByCriteria(yobatisTable));
        dao.addMethod(methodFactory.delete(yobatisTable));
        dao.addMethod(methodFactory.deleteByCriteria(yobatisTable));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY));
        dao.addImportedType(yobatisTable.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA));
        dao.addImportedType(yobatisTable.getPrimaryKey());
        return dao;
    }
}
