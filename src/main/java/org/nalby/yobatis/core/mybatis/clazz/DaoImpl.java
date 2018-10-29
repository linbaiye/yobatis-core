package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.database.YobatisTableItem;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.DaoImplMethodFactory;

public class DaoImpl extends TopLevelClass implements YobatisUnit {

    private DaoImpl(FullyQualifiedJavaType type) {
        super(type);
    }

    @Override
    public String getPathToPut() {
        return null;
    }

    @Override
    public void merge(String fileContent) throws InvalidUnitException {

    }

    public static DaoImpl build(YobatisTableItem tableItem) {
        DaoImpl daoImpl = new DaoImpl(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO_IMPL));
        DaoImplMethodFactory methodFactory = DaoImplMethodFactory.getInstance();
        daoImpl.addMethod(methodFactory.insert(tableItem));
        daoImpl.addMethod(methodFactory.selectOne(tableItem));
        daoImpl.addMethod(methodFactory.selectOneByCriteria(tableItem));
        daoImpl.addMethod(methodFactory.selectList(tableItem));
        daoImpl.addMethod(methodFactory.count(tableItem));
        daoImpl.addMethod(methodFactory.update(tableItem));
        daoImpl.addMethod(methodFactory.updateByCriteria(tableItem));
        daoImpl.addMethod(methodFactory.delete(tableItem));
        daoImpl.addMethod(methodFactory.deleteByCriteria(tableItem));
        daoImpl.addSuperInterface(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.DAO));
        daoImpl.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        daoImpl.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
        daoImpl.addImportedType(tableItem.getPrimaryKey());
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY));
        daoImpl.addImportedType(tableItem.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA));
        return daoImpl;
    }
}
