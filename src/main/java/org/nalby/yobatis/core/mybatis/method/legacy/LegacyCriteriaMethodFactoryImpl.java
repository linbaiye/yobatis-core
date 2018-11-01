package org.nalby.yobatis.core.mybatis.method.legacy;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.mybatis.method.AbstractMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.CriteriaMethodType;
import org.nalby.yobatis.core.util.TextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LegacyCriteriaMethodFactoryImpl implements LegacyCriteriaMethodFactory {

    private final static LegacyCriteriaMethodFactoryImpl instance = new LegacyCriteriaMethodFactoryImpl();

    private LegacyCriteriaMethodFactoryImpl() {}

    private YobatisIntrospectedTable table;

    public static LegacyCriteriaMethodFactoryImpl getInstance() {
        return instance;
    }

    public static LegacyCriteriaMethodFactoryImpl getInstance(YobatisIntrospectedTable table) {
        instance.table = table;
        return instance;
    }

    private enum ParamType {
        NO_PARAM,
        SINGLE_PARAM,
        LIST_PARAM,
        BETWEEN_PARAMS
    }
    private static final CommonMethodFactory commonMethodFactory = CommonMethodFactoryImpl.getInstance();

    private Method assembleMethod(String methodName, String returnType, String bodyLine, Parameter... parameters) {
        Method method = commonMethodFactory.publicMethod(methodName, returnType);
        method.addBodyLine(bodyLine);
        if (parameters != null) {
            for (Parameter parameter : parameters) {
                method.addParameter(parameter);
            }
        }
        return method;
    }

    private Method assembleReturnThisMethod(String methodName, String returnType, String bodyLine, Parameter... parameters) {
        Method method = assembleMethod(methodName, returnType, bodyLine, parameters);
        method.addBodyLine("return this;");
        return method;
    }

    private Method assembleStaticMethod(IntrospectedColumn column, String returnType, String postfix, ParamType paramType) {
        String callingMethodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + postfix;
        String methodName = column.getJavaProperty() + postfix;
        Method method = null;
        switch (paramType) {
            case NO_PARAM:
                String bodyLine = String.format("return new %s().%s();", returnType, callingMethodName);
                method = assembleMethod(methodName, returnType, bodyLine);
                break;
            case SINGLE_PARAM:
                bodyLine = String.format("return new %s().%s(value);", returnType, callingMethodName);
                method = assembleMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
                break;
            case LIST_PARAM:
                bodyLine = String.format("return new %s().%s(values);", returnType, callingMethodName);
                method = assembleMethod(methodName, returnType, bodyLine, new Parameter(
                        new FullyQualifiedJavaType("List<" +column.getFullyQualifiedJavaType().getShortName() + ">"), "values"));
                break;
            case BETWEEN_PARAMS:
                bodyLine = String.format("return new %s().%s(value1, value2);", returnType, callingMethodName);
                method = assembleMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value1"),
                        new Parameter(column.getFullyQualifiedJavaType(), "value2"));
        }
        method.setStatic(true);
        return method;
    }

    private Method likeMethod(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().addCriterion(\"%s like\", value, \"%s\");", column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "Like";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticLikeMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "Like", ParamType.SINGLE_PARAM);
    }

    private Method notLikeMethod(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().addCriterion(\"%s not like\", value, \"%s\");", column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "NotLike";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticNotLikeMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "NotLike", ParamType.SINGLE_PARAM);
    }

    private String selectCallingMethodName(IntrospectedColumn column) {
        if (column.getJdbcTypeName().equals("DATE")) {
            return "addCriterionForJDBCDate";
        } else if (column.getJdbcTypeName().equals("TIME")) {
            return "addCriterionForJDBCTime";
        } else {
            return "addCriterion";
        }
    }

    private Method equalToMethod(IntrospectedColumn column, String returnType) {
        String methodToCall = selectCallingMethodName(column);
        String bodyLine = String.format("lastCriteria().%s(\"%s =\", value, \"%s\");",
                methodToCall,
                column.getActualColumnName(),
                column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "EqualTo";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticEqualToMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "EqualTo", ParamType.SINGLE_PARAM);
    }

    private Method notEqualToMethod(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s <>\", value, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "NotEqualTo";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticNotEqualToMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "NotEqualTo", ParamType.SINGLE_PARAM);
    }

    private Method lessThanMethod(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s <\", value, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "LessThan";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticLessThanMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "LessThan", ParamType.SINGLE_PARAM);
    }

    private Method lessThanOrEqualToMethod(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s <=\", value, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "LessThanOrEqualTo";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticLessThanOrEqualToMethod(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "LessThanOrEqualTo", ParamType.SINGLE_PARAM);
    }

    private Method greaterThan(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s >\", value, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "GreaterThan";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticGreaterThan(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "GreaterThan", ParamType.SINGLE_PARAM);
    }

    private Method greaterThanOrEqualTo(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s >=\", value, \"%s\");", selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "GreaterThanOrEqualTo";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(column.getFullyQualifiedJavaType(), "value"));
    }

    private Method staticGreaterThanOrEqualTo(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "GreaterThanOrEqualTo", ParamType.SINGLE_PARAM);
    }

    private Method isNull(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().addCriterion(\"%s is null\");", column.getActualColumnName());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "IsNull";
        return assembleReturnThisMethod(methodName, returnType, bodyLine);
    }

    private Method staticIsNull(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "IsNull", ParamType.NO_PARAM);
    }

    private Method isNotNull(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().addCriterion(\"%s is not null\");", column.getActualColumnName());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "IsNotNull";
        return assembleReturnThisMethod(methodName, returnType, bodyLine);
    }

    private Method staticIsNotNull(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "IsNotNull", ParamType.NO_PARAM);
    }

    private Method in(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s in\", values, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "In";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(new FullyQualifiedJavaType("List<" + column.getFullyQualifiedJavaType().getShortName() + ">"), "values"));
    }

    private Method staticIn(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "In", ParamType.LIST_PARAM);
    }

    private Method notIn(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s not in\", values, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "NotIn";
        return assembleReturnThisMethod(methodName, returnType, bodyLine, new Parameter(new FullyQualifiedJavaType("List<" + column.getFullyQualifiedJavaType().getShortName() + ">"), "values"));
    }

    private Method staticNotIn(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "NotIn", ParamType.LIST_PARAM);
    }

    private Method between(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s between\", value1, value2, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "Between";
        return assembleReturnThisMethod(methodName, returnType, bodyLine,
                new Parameter(column.getFullyQualifiedJavaType(), "value1"), new Parameter(column.getFullyQualifiedJavaType(), "value2"));
    }

    private Method staticBetween(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "Between", ParamType.BETWEEN_PARAMS);
    }

    private Method notBetween(IntrospectedColumn column, String returnType) {
        String bodyLine = String.format("lastCriteria().%s(\"%s not between\", value1, value2, \"%s\");",
                selectCallingMethodName(column),
                column.getActualColumnName(), column.getJavaProperty());
        String methodName = "and" + TextUtil.capitalizeFirstChar(column.getJavaProperty()) + "NotBetween";
        return assembleReturnThisMethod(methodName, returnType, bodyLine,
                new Parameter(column.getFullyQualifiedJavaType(), "value1"), new Parameter(column.getFullyQualifiedJavaType(), "value2"));
    }

    private Method staticNotBetween(IntrospectedColumn column, String returnType) {
        return assembleStaticMethod(column, returnType, "NotBetween", ParamType.BETWEEN_PARAMS);
    }

    private List<Method> allMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = new ArrayList<>();
        methodList.add(isNull(introspectedColumn, returnType));
        methodList.add(isNotNull(introspectedColumn, returnType));
        methodList.add(equalToMethod(introspectedColumn, returnType));
        methodList.add(notEqualToMethod(introspectedColumn, returnType));
        methodList.add(greaterThan(introspectedColumn, returnType));
        methodList.add(greaterThanOrEqualTo(introspectedColumn, returnType));
        methodList.add(lessThanMethod(introspectedColumn, returnType));
        methodList.add(lessThanOrEqualToMethod(introspectedColumn, returnType));
        if (introspectedColumn.isStringColumn()) {
            methodList.add(likeMethod(introspectedColumn, returnType));
            methodList.add(notLikeMethod(introspectedColumn, returnType));
        }
        methodList.add(in(introspectedColumn, returnType));
        methodList.add(notIn(introspectedColumn, returnType));
        methodList.add(between(introspectedColumn, returnType));
        methodList.add(notBetween(introspectedColumn, returnType));
        return methodList;
    }

    private List<Method> booleanMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = binaryMethodList(introspectedColumn, returnType);
        methodList.add(equalToMethod(introspectedColumn, returnType));
        return methodList;
    }

    private List<Method> binaryMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = new ArrayList<>();
        methodList.add(isNull(introspectedColumn, returnType));
        methodList.add(isNotNull(introspectedColumn, returnType));
        return methodList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Method> criteriaMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        switch (introspectedColumn.getFullyQualifiedJavaType().getShortName()) {
            case "Integer":
            case "Short":
            case "Long":
            case "Byte":
            case "String":
            case "Date":
            case "BigDecimal":
            case "Float":
            case "Double":
                return allMethodList(introspectedColumn, returnType);
            case "Boolean":
                return booleanMethodList(introspectedColumn, returnType);
            case "byte[]":
                return binaryMethodList(introspectedColumn, returnType);
        }
        return Collections.EMPTY_LIST;
    }


    private List<Method> allStaticMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = new ArrayList<>();
        methodList.add(staticIsNull(introspectedColumn, returnType));
        methodList.add(staticIsNotNull(introspectedColumn, returnType));
        methodList.add(staticEqualToMethod(introspectedColumn, returnType));
        methodList.add(staticNotEqualToMethod(introspectedColumn, returnType));
        methodList.add(staticGreaterThan(introspectedColumn, returnType));
        methodList.add(staticGreaterThanOrEqualTo(introspectedColumn, returnType));
        methodList.add(staticLessThanMethod(introspectedColumn, returnType));
        methodList.add(staticLessThanOrEqualToMethod(introspectedColumn, returnType));
        if (introspectedColumn.isStringColumn()) {
            methodList.add(staticLikeMethod(introspectedColumn, returnType));
            methodList.add(staticNotLikeMethod(introspectedColumn, returnType));
        }
        methodList.add(staticIn(introspectedColumn, returnType));
        methodList.add(staticNotIn(introspectedColumn, returnType));
        methodList.add(staticBetween(introspectedColumn, returnType));
        methodList.add(staticNotBetween(introspectedColumn, returnType));
        return methodList;
    }

    private List<Method> staticBooleanMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = staticBinaryMethodList(introspectedColumn, returnType);
        methodList.add(staticEqualToMethod(introspectedColumn, returnType));
        return methodList;
    }

    private List<Method> staticBinaryMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        List<Method> methodList = new ArrayList<>();
        methodList.add(staticIsNull(introspectedColumn, returnType));
        methodList.add(staticIsNotNull(introspectedColumn, returnType));
        return methodList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Method> factoryMethodList(IntrospectedColumn introspectedColumn, String returnType) {
        switch (introspectedColumn.getFullyQualifiedJavaType().getShortName()) {
            case "Integer":
            case "Short":
            case "Long":
            case "Byte":
            case "String":
            case "Date":
            case "BigDecimal":
            case "Float":
            case "Double":
                return allStaticMethodList(introspectedColumn, returnType);
            case "Boolean":
                return staticBooleanMethodList(introspectedColumn, returnType);
            case "byte[]":
                return staticBinaryMethodList(introspectedColumn, returnType);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Method setter(String type, String property, String returnType) {
        Method method = commonMethodFactory.setter(property, type);
        method.addBodyLine("return this;");
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        return method;
    }

    @Override
    public Method order(String returnType, boolean asc) {
        Method method;
        if (asc) {
            method = commonMethodFactory.publicMethod("ascOrderBy", returnType);
            method.addBodyLine("orderBy(\"asc\", fields);");
        } else {
            method = commonMethodFactory.publicMethod("descOrderBy", returnType);
            method.addBodyLine("orderBy(\"desc\", fields);");
        }
        method.addBodyLine("return this;");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        return method;
    }

    @Override
    public Method or(String returnType) {
        Method method = commonMethodFactory.publicMethod("or", returnType);
        method.addBodyLine("oredCriteria.add(createCriteriaInternal());");
        method.addBodyLine("return this;");
        return method;
    }
}
