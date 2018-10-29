package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

public class DaoMethodFactoryImpl extends AbstractDaoMethodFactory {

    private static final DaoMethodFactoryImpl factory = new DaoMethodFactoryImpl();

    private DaoMethodFactoryImpl() {}

    public static DaoMethodFactoryImpl getInstance() {
        return factory;
    }

    @Override
    public Method insert(YobatisTableItem table) {
        return makeMethod("insert", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
    }

    @Override
    public Method selectOne(YobatisTableItem table) {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY),
                makeParam(table.getPrimaryKey(), "pk"));
    }

    @Override
    public Method selectOneByCriteria(YobatisTableItem table) {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }

    @Override
    public Method selectList(YobatisTableItem table) {
        return makeMethod("selectList",
                new FullyQualifiedJavaType("List<"
                        + table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY).getShortName() + ">"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }

    @Override
    public Method count(YobatisTableItem table) {
        return makeMethod("count", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }

    @Override
    public Method update(YobatisTableItem table) {
        return makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
    }

    @Override
    public Method updateByCriteria(YobatisTableItem table) {
        Method method = makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
        method.addParameter(makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
        return method;
    }

    @Override
    public Method delete(YobatisTableItem table) {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getPrimaryKey(), "pk"));
    }

    @Override
    public Method deleteByCriteria(YobatisTableItem table) {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }
}
