package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;

public class CriteriaMethodFactory extends AbstractMethodFactory {

    private final static CriteriaMethodFactory instance = new CriteriaMethodFactory();

    private CriteriaMethodFactory() {}

    private YobatisIntrospectedTable table;

    public static CriteriaMethodFactory getInstance(YobatisIntrospectedTable table) {
        instance.table = table;
        return instance;
    }

    private String selectCallingMethodName(IntrospectedColumn column) {
        if (column.getJdbcTypeName().equals("DATE")) {
            return "addCriterionForJDBCDate";
        }
        return column.getJdbcTypeName().equals("TIME") ? "addCriterionForJDBCTime" : "addCriterion";
    }

    private Method setter(CriteriaMethodType type, String paramType, String paramName) {
        Method method = publicMethod(type.makeMethodName(null));
        method.addParameter(new Parameter(new FullyQualifiedJavaType(paramType), paramName));
        method.addBodyLine("this." + paramName + " = " + paramName + ";");
        method.addBodyLine("return this;");
        return method;
    }


    private Method setLimit() {
        return setter(CriteriaMethodType.SET_LIMIT, "Long", "limit");
    }

    private Method setOffset() {
        return setter(CriteriaMethodType.SET_OFFSET, "Long", "offset");
    }

    private Method setForUpdate() {
        return setter(CriteriaMethodType.SET_FOR_UPDATE, "boolean", "forUpdate");
    }

    private Method order(boolean asc) {
        Method method;
        if (asc) {
            method = publicMethod("ascOrderBy");
            method.addBodyLine("addOrderBy(\"asc\", fields);");
        } else {
            method = publicMethod("descOrderBy");
            method.addBodyLine("addOrderBy(\"desc\", fields);");
        }
        method.addBodyLine("return this;");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        return method;
    }

    private Method or() {
        Method method = publicMethod(CriteriaMethodType.OR.makeMethodName(null));
        method.addBodyLine("oredCriteria.add(new BracketCriteria());");
        method.addBodyLine("return this;");
        return method;
    }

    @Override
    public Method create(String type) {
        CriteriaMethodType methodType = CriteriaMethodType.findByType(type);
        switch (methodType) {
            case OR:
                return or();
            case ASC_ORDER_BY:
                return order(true);
            case DESC_ORDER_BY:
                return order(false);
            case SET_LIMIT:
                return setLimit();
            case SET_OFFSET:
                return setOffset();
            case SET_FOR_UPDATE:
                return setForUpdate();
            case HAS_COLUMN:
                return hasColumn();
        }
        throw new IllegalArgumentException("Should never happen.");
    }

    private Method publicMethod(String name) {
        Method method = new Method(name);
        method.setReturnType(table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA));
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }

    private Method publicStaticMethod(String name) {
        Method method = publicMethod(name);
        method.setStatic(true);
        return method;
    }

    private Method staticInValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicStaticMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + column.getFullyQualifiedJavaType().getShortName() + ">"), "values"));
        method.addBodyLine("return new " +
                table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA).getShortName() + "()." +
                type.makeCallingMethodName(column.getJavaProperty()) + "(values);"
        );
        return method;
    }

    private Method staticNoValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicStaticMethod(type.makeMethodName(column.getJavaProperty()));
        method.addBodyLine("return new " +
                table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA).getShortName() + "()." +
                type.makeCallingMethodName(column.getJavaProperty()) + "();"
        );
        return method;
    }

    private Method staticSingleValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicStaticMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value"));
        method.addBodyLine("return new " +
                table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA).getShortName() + "()." +
                type.makeCallingMethodName(column.getJavaProperty()) + "(value);"
        );
        return method;
    }

    private Method staticBetweenValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicStaticMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value1"));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value2"));
        method.addBodyLine("return new " +
                table.getFullyQualifiedJavaType(YobatisIntrospectedTable.ClassType.CRITERIA).getShortName() + "()." +
                type.makeCallingMethodName(column.getJavaProperty()) + "(value1, value2);"
        );
        return method;
    }


    private Method appendBodyLineAndReturnThis(Method method, String bodyLine) {
        method.addBodyLine(bodyLine);
        method.addBodyLine("return this;");
        return method;
    }

    private Method hasColumn() {
        Method method = publicMethod(CriteriaMethodType.HAS_COLUMN.makeMethodName(null));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "column"));
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setReturnType(new FullyQualifiedJavaType("boolean"));
        method.addAnnotation("@Override");
        method.addBodyLine("return COLUMNS.contains(column);");
        return method;
    }

    private Method inValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + column.getFullyQualifiedJavaType().getShortName() + ">"), "values"));
        String bodyLine = String.format("lastCriteria().%s(\"%s %s\", values, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), type.getSqlSnippet(), column.getJavaProperty());
        return appendBodyLineAndReturnThis(method, bodyLine);
    }

    private Method noValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicMethod(type.makeMethodName(column.getJavaProperty()));
        String bodyLine = String.format("lastCriteria().addCriterion(\"%s %s\");", column.getActualColumnName(), type.getSqlSnippet());
        return appendBodyLineAndReturnThis(method, bodyLine);
    }

    private Method singleValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value"));
        String bodyLine = String.format("lastCriteria().%s(\"%s %s\", value, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), type.getSqlSnippet(), column.getJavaProperty());
        return appendBodyLineAndReturnThis(method, bodyLine);
    }

    private Method betweenValueMethod(IntrospectedColumn column, CriteriaMethodType type) {
        Method method = publicMethod(type.makeMethodName(column.getJavaProperty()));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value1"));
        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value2"));
        String bodyLine = String.format("lastCriteria().%s(\"%s %s\", value1, value2, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), type.getSqlSnippet(), column.getJavaProperty());
        return appendBodyLineAndReturnThis(method, bodyLine);
    }

    @Override
    public Method create(String type, IntrospectedColumn column) {
        CriteriaMethodType methodType = CriteriaMethodType.findByType(type);
        if (!methodType.isJavaTypeSupported(column.getFullyQualifiedJavaType().getShortName())) {
            return null;
        }
        if (methodType.isListValue()) {
            return  methodType.isStatic() ? staticInValueMethod(column, methodType) : inValueMethod(column, methodType);
        } else if (methodType.isNoValue()) {
            return  methodType.isStatic() ? staticNoValueMethod(column, methodType) : noValueMethod(column, methodType);
        } else if (methodType.isSingleValue()) {
            return  methodType.isStatic() ? staticSingleValueMethod(column, methodType) : singleValueMethod(column, methodType);
        } else if (methodType.isBetweenValue())  {
            return  methodType.isStatic() ? staticBetweenValueMethod(column, methodType) : betweenValueMethod(column, methodType);
        }
        throw new IllegalArgumentException("Unknown type.");
    }
}
