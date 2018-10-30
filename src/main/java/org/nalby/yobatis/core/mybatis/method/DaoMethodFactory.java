package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

public class DaoMethodFactory extends AbstractMethodFactory {

    private static final DaoMethodFactory factory = new DaoMethodFactory();

    private DaoMethodFactory() {}

    private YobatisTableItem table;

    public static DaoMethodFactory getInstance(YobatisTableItem table) {
        factory.table = table;
        return factory;
    }

    @Override
    public Method create(String name) {
        if (DaoMethod.COUNT.nameEquals(name)) {
            return count();
        } else if (DaoMethod.SELECT_ONE.nameEquals(name)) {
            return selectOne();
        } else if (DaoMethod.SELECT_ONE_BY_CRITERIA.nameEquals(name)) {
            return selectOneByCriteria();
        } else if (DaoMethod.SELECT_LIST.nameEquals(name)) {
            return selectList();
        } else if (DaoMethod.UPDATE.nameEquals(name)) {
            return update();
        } else if (DaoMethod.UPDATE_BY_CRITERIA.nameEquals(name)) {
            return updateByCriteria();
        } else if (DaoMethod.DELETE.nameEquals(name)) {
            return delete();
        } else if (DaoMethod.DELETE_BY_CRITERIA.nameEquals(name)) {
            return deleteByCriteria();
        }
        throw new IllegalArgumentException("Unknown name.");
    }


    public Method insert() {
        return makeMethod("insert", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
    }


    public Method selectOne() {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY),
                makeParam(table.getPrimaryKey(), "pk"));
    }


    public Method selectOneByCriteria() {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }


    public Method selectList() {
        return makeMethod("selectList",
                new FullyQualifiedJavaType("List<"
                        + table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.ENTITY).getShortName() + ">"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }


    public Method count() {
        return makeMethod("count", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }


    public Method update() {
        return makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
    }


    public Method updateByCriteria() {
        Method method = makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.BASE_ENTITY), "record"));
        method.addParameter(makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
        return method;
    }


    public Method delete() {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getPrimaryKey(), "pk"));
    }


    public Method deleteByCriteria() {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisTableItem.ClassType.CRITERIA), "criteria"));
    }
}
