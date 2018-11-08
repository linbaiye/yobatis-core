package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;
import org.nalby.yobatis.core.mybatis.clazz.Dao;

public class DaoMethodFactory extends AbstractMethodFactory {

    private static final DaoMethodFactory factory = new DaoMethodFactory();

    private DaoMethodFactory() {}

    private YobatisIntrospectedTable table;

    public static DaoMethodFactory getInstance(YobatisIntrospectedTable table) {
        factory.table = table;
        return factory;
    }

    @Override
    public Method create(String name) {
        DaoMethodName methodName = DaoMethodName.findByVal(name);
        switch (methodName) {
            case COUNT:
                return count();
            case SELECT_BY_PK:
                return selectOne();
            case SELECT_BY_CRITERIA:
                return selectOneByCriteria();
            case SELECT_LIST:
                return selectList();
            case INSERT:
                return insert();
            case UPDATE_BY_PK:
                return update();
            case UPDATE_BY_CRITERIA:
                return updateByCriteria();
            case DELETE_BY_PK:
                return delete();
            case DELETE_BY_CRITERIA:
                return deleteByCriteria();
        }
        throw new IllegalArgumentException("Unknown name.");
    }


    private Method insert() {
        return makeMethod("insert", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY), "record"));
    }


    private Method selectOne() {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY),
                makeParam(table.getPrimaryKey(), "pk"));
    }


    private Method selectOneByCriteria() {
        return makeMethod("selectOne", table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA), "criteria"));
    }


    private Method selectList() {
        return makeMethod("selectList",
                new FullyQualifiedJavaType("List<"
                        + table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.ENTITY).getShortName() + ">"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA), "criteria"));
    }


    private Method count() {
        return makeMethod("count", new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA), "criteria"));
    }


    private Method update() {
        return makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY), "record"));
    }


    private Method updateByCriteria() {
        Method method = makeMethod("update",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.BASE_ENTITY), "record"));
        method.addParameter(makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA), "criteria"));
        return method;
    }


    private Method delete() {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getPrimaryKey(), "pk"));
    }


    private Method deleteByCriteria() {
        return makeMethod("delete",
                new FullyQualifiedJavaType("int"),
                makeParam(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA), "criteria"));
    }
}
