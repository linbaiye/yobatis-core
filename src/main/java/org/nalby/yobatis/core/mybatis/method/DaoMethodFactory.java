package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

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
        if (FactoryMethodName.COUNT.nameEquals(name)) {
            return count();
        } else if (FactoryMethodName.SELECT_ONE_BY_PK.nameEquals(name)) {
            return selectOne();
        } else if (FactoryMethodName.SELECT_ONE_BY_CRITERIA.nameEquals(name)) {
            return selectOneByCriteria();
        } else if (FactoryMethodName.SELECT_LIST.nameEquals(name)) {
            return selectList();
        } else if (FactoryMethodName.INSERT.nameEquals(name)) {
            return insert();
        } else if (FactoryMethodName.UPDATE_BY_PK.nameEquals(name)) {
            return update();
        } else if (FactoryMethodName.UPDATE_BY_CRITERIA.nameEquals(name)) {
            return updateByCriteria();
        } else if (FactoryMethodName.DELETE_BY_PK.nameEquals(name)) {
            return delete();
        } else if (FactoryMethodName.DELETE_BY_CRITERIA.nameEquals(name)) {
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
