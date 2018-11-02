package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

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
        if (CriteriaMethodType.OR.getType().equals(type)) {
            return or();
        } else if (CriteriaMethodType.ASC_ORDER_BY.getType().equals(type)) {
            return order(true);
        } else if (CriteriaMethodType.DESC_ORDER_BY.getType().equals(type)) {
            return order(false);
        } else if (CriteriaMethodType.SET_LIMIT.getType().equals(type)) {
            return setLimit();
        } else if (CriteriaMethodType.SET_FOR_UPDATE.getType().equals(type)) {
            return setForUpdate();
        } else if (CriteriaMethodType.SET_OFFSET.getType().equals(type)) {
            return setOffset();
        } else if (CriteriaMethodType.HAS_COLUMN.getType().equals(type)) {
            return hasColumn();
        }
        throw new IllegalArgumentException("Unknown type.");
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
        CriteriaMethodType methodType = null;
        for (CriteriaMethodType e: CriteriaMethodType.values()) {
            if (e.getType().equals(type)) {
                methodType = e;
                break;
            }
        }
        if (methodType == null) {
            throw new IllegalArgumentException("Unknown type.");
        }
        if (!methodType.isJavaTypeSupported(column.getFullyQualifiedJavaType().getShortName())) {
            return null;
        }
        if (methodType.isListValue()) {
            if (methodType.isStatic()) {
                return staticInValueMethod(column, methodType);
            }
            return inValueMethod(column, methodType);
        }
        if (methodType.isNoValue()) {
            if (methodType.isStatic()) {
                return staticNoValueMethod(column, methodType);
            }
            return noValueMethod(column, methodType);
        }
        if (methodType.isSingleValue()) {
            if (methodType.isStatic()) {
                return staticSingleValueMethod(column, methodType);
            }
            return singleValueMethod(column, methodType);
        }
        if (methodType.isBetweenValue())  {
            if (methodType.isStatic()) {
                return staticBetweenValueMethod(column, methodType);
            }
            return betweenValueMethod(column, methodType);
        }
        throw new IllegalArgumentException("Unknown type.");
    }
}
