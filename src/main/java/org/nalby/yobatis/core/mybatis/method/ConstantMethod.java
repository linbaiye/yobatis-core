package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.nalby.yobatis.core.util.MethodUtil;

public enum ConstantMethod {


    IS_VALID(
            "isValid", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return criteria.size() > 0;"},
            new Parameter[] {}
    ),
    ASSERT_VALID(
            "isValid", "boolean", JavaVisibility.PUBLIC,
            new String[] {"if (oredCriteria.size() > 0) {",
                "return true;",
                "}",
                "throw new RuntimeException(\"Empty criteria is not allowed.\");",
            },
            new Parameter[] {}
    ),
    GET_CRITERIA(
            "getCriteria", "List<Criterion>", JavaVisibility.PUBLIC,
            new String[] {"return criteria;"},
            new Parameter[] {}
    ),
    ADD_CRITERION_1_PARAM(
            "addCriterion", "void", JavaVisibility.PUBLIC,
            new String[] {"criteria.add(new Criterion(condition));"},
            new Parameter[] {new Parameter(new FullyQualifiedJavaType("String"), "condition")}
    ),

    ADD_CRITERION_3_PARAM(
            "addCriterion", "void", JavaVisibility.PUBLIC,
             new String[] {"if (value == null) {", "throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");", "}", "criteria.add(new Criterion(condition, value));"},
             new Parameter[] {
                     new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                     new Parameter(new FullyQualifiedJavaType("Object"), "value"),
                     new Parameter(new FullyQualifiedJavaType("String"), "property")}
    ),

    ADD_CRITERION_4_PARAM(
            "addCriterion", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (value1 == null || value2 == null) {",
                    "throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");",
                    "}",
                    "criteria.add(new Criterion(condition, value1, value2));"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "value1"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "value2"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")}
    ),

    ADD_DATE_3_PARAM(
            "addCriterionForJDBCDate", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (value == null) {",
                    "throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");",
                    "}",
                    "addCriterion(condition, new java.sql.Date(value.getTime()), property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),
    ADD_DATE_LIST_PARAM (
            "addCriterionForJDBCDate", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (values == null || values.size() == 0) {",
                    "throw new IllegalArgumentException(\"Value list for \" + property + \" cannot be null or empty\");",
                    "}",
                    "List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();",
                    "Iterator<Date> iter = values.iterator();",
                    "while (iter.hasNext()) {",
                    "dateList.add(new java.sql.Date(iter.next().getTime()));",
                    "}",
                    "addCriterion(condition, dateList, property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("List<Date>"), "values"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),
    ADD_DATE_BETWEEN_PARAM (
            "addCriterionForJDBCDate", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (value1 == null || value2 == null) {",
                    "throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");",
                    "}",
                    "addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value1"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value2"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),
    ADD_TIME_3_PARAM(
            "addCriterionForJDBCTime", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (value == null) {",
                    "throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");",
                    "}",
                    "addCriterion(condition, new java.sql.Time(value.getTime()), property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),
    ADD_TIME_LIST_PARAM (
            "addCriterionForJDBCTime", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (values == null || values.size() == 0) {",
                    "throw new IllegalArgumentException(\"Value list for \" + property + \" cannot be null or empty\");",
                    "}",
                    "List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();",
                    "Iterator<Date> iter = values.iterator();",
                    "while (iter.hasNext()) {",
                    "timeList.add(new java.sql.Time(iter.next().getTime()));",
                    "}",
                    "addCriterion(condition, timeList, property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("List<Date>"), "values"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),
    ADD_TIME_BETWEEN_PARAM (
            "addCriterionForJDBCTime", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "if (value1 == null || value2 == null) {",
                    "throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");",
                    "}",
                    "addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value1"),
                    new Parameter(new FullyQualifiedJavaType("Date"), "value2"),
                    new Parameter(new FullyQualifiedJavaType("String"), "property")
            }
    ),

    // The following methods are for Criterion.
    GET_CONDITION(
            "getCondition", "String", JavaVisibility.PUBLIC,
            new String[] {"return condition;"},
            new Parameter[] {}
    ),
    GET_VALUE(
            "getValue", "Object", JavaVisibility.PUBLIC,
            new String[] {"return value;"},
            new Parameter[] {}
    ),
    GET_SECOND_VALUE(
            "getSecondValue", "Object", JavaVisibility.PUBLIC,
            new String[] {"return secondValue;"},
            new Parameter[] {}
    ),
    IS_NO_VALUE(
            "isNoValue", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return noValue;"},
            new Parameter[] {}
    ),
    IS_SINGLE_VALUE(
            "isSingleValue", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return singleValue;"},
            new Parameter[] {}
    ),
    iS_BETWEEN_VALUE(
            "isBetweenValue", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return betweenValue;"},
            new Parameter[] {}
    ),
    iS_LIST_VALUE(
            "isListValue", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return listValue;"},
            new Parameter[] {}
    ),
    CRITERION_CONDITION(
            "Criterion", null, JavaVisibility.PUBLIC,
            new String[] {
                    "this.condition = condition;",
                    "this.noValue = true;"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition")
            }
    ),
    CRITERION_CONDITION_VALUE(
            "Criterion", null, JavaVisibility.PUBLIC,
            new String[] {
                    "this.condition = condition;",
                    "this.value = value;",
                    "if (value instanceof List<?>) {",
                    "this.listValue = true;",
                    "} else {",
                    "this.singleValue = true;",
                    "}",
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "value")
            }
    ),
    CRITERION_CONDITION_TWO_VALUES(
            "Criterion", null, JavaVisibility.PUBLIC,
            new String[] {
                    "this.condition = condition;",
                    "this.value = value;",
                    "this.secondValue = secondValue;",
                    "this.betweenValue = true;",
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "condition"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "value"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "secondValue")
            }
    ),
    BASE_CRITERIA_CONSTRUCTOR(
            "BaseCriteria", null, JavaVisibility.PUBLIC,
            new String[]{"oredCriteria = new ArrayList<BracketCriteria>();"},
            new Parameter[] {}
    ),

    GET_ORDER_BY_CLAUSE(
            "getOrderByClause", "String", JavaVisibility.PUBLIC,
            new String[] {"return orderByClause;"},
            new Parameter[]{}
    ),

    IS_DISTINCT(
            "isDistinct", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return distinct;"},
            new Parameter[] {}
    ),

    GET_ORED_CRITERIA(
            "getOredCriteria", "List<BracketCriteria>", JavaVisibility.PUBLIC,
            new String[] {"return oredCriteria;"},
            new Parameter[] {}
    ),

    CREATE_CRITERIA_INTERNAL(
            "createCriteriaInternal", "BracketCriteria", JavaVisibility.PROTECTED,
            new String[] {"return new BracketCriteria();"},
            new Parameter[] {}
    ),
    CLEAR(
            "clear", "void", JavaVisibility.PUBLIC,
            new String[] {
                    "oredCriteria.clear();",
                    "orderByClause = null;",
                    "distinct = false;",
                    "limit = null;",
                    "offset = null;",
                    "forUpdate = false;",
            },
            new Parameter[] {}
    ),
    GET_LIMIT(
            "getLimit", "Long", JavaVisibility.PUBLIC,
            new String[] {"return limit;"},
            null
    ),
    GET_OFFSET(
            "getOffset", "Long", JavaVisibility.PUBLIC,
            new String[] {"return offset;"},
            null
    ),
    IS_FOR_UPDATE(
            "isForUpdate", "boolean", JavaVisibility.PUBLIC,
            new String[] {"return forUpdate;"},
            null
    ),
    LAST_CRITERIA(
            "lastCriteria", "BracketCriteria", JavaVisibility.PUBLIC,
            new String[] {
                    "if (oredCriteria.isEmpty()) {",
                    "oredCriteria.add(createCriteriaInternal());",
                    "}",
                    "return oredCriteria.get(oredCriteria.size() - 1);"
            },
            null
    ),
    HAS_COLUMN(
            "hasColumn", "boolean", JavaVisibility.PROTECTED,
            new String[] {},
            new Parameter[] {new Parameter(new FullyQualifiedJavaType("String"), "column")}
    ),
    ADD_ORDER_BY(
            "addOrderBy", "void", JavaVisibility.PROTECTED,
            new String[] {
                    "if (fields == null || fields.length == 0) {",
                    "throw new IllegalArgumentException(\"Empty fields passed.\");",
                    "}",
                    "StringBuilder stringBuilder = new StringBuilder();",
                    "if (orderByClause != null) {",
                    "stringBuilder.append(orderByClause);",
                    "stringBuilder.append(',');",
                    "}",
                    "for (String field : fields) {",
                    "if (!hasColumn(field)) {",
                    "throw new IllegalArgumentException(\"Unrecognizable field:\" + field);",
                    "}",
                    "stringBuilder.append(field).append(\" \").append(order).append(\",\");",
                    "}",
                    "stringBuilder.deleteCharAt(stringBuilder.length() - 1);",
                    "orderByClause = stringBuilder.toString();",
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "order"),
                    new Parameter(new FullyQualifiedJavaType("String"), "fields", true)
            }
    ),
    ORDER_BY(
            "orderBy", "void", JavaVisibility.PRIVATE,
            new String[] {
                    "if (fields == null || fields.length == 0) {",
                    "throw new IllegalArgumentException(\"Empty fields passed.\");",
                    "}",
                    "StringBuilder stringBuilder = new StringBuilder();",
                    "if (orderByClause != null) {",
                    "stringBuilder.append(orderByClause);",
                    "stringBuilder.append(',');",
                    "}",
                    "for (String field : fields) {",
                    "if (!PROPERTY_TO_COLUMN.containsKey(field)) {",
                    "throw new IllegalArgumentException(\"Unrecognizable field:\" + field);",
                    "}",
                    "stringBuilder.append(PROPERTY_TO_COLUMN.get(field));",
                    "stringBuilder.append(\" \");",
                    "stringBuilder.append(order);",
                    "stringBuilder.append(',');",
                    "}",
                    "stringBuilder.deleteCharAt(stringBuilder.length() - 1);",
                    "orderByClause = stringBuilder.toString();"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "order"),
                    new Parameter(new FullyQualifiedJavaType("String"), "fields", true)
            }
    ),
    INSERT_ALL_SIGNATURE(
            "insertAll", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_ALL_IGNORE_SIGNATURE(
            "insertAllIgnore", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_SIGNATURE(
            "insert", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_IGNORE_SIGNATURE(
            "insertIgnore", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    SELECT_ONE_SIGNATURE(
            "selectOne", "T", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("PK"), "pk")
            }
    ),
    SELECT_ONE_BY_CRITERIA_SIGNATURE(
            "selectOne", "T", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    SELECT_LIST_SIGNATURE(
            "selectList", "List<T>", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    COUNT_ALL_SIGNATURE(
            "countAll", "long", null,
            null,
            null
    ),
    COUNT_BY_CRITERIA_SIGNATURE(
            "count", "long", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    UPDATE_SIGNATURE(
            "update", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    UPDATE_ALL_SIGNATURE(
            "updateAll", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    UPDATE_BY_CRITERIA_SIGNATURE(
            "update", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record"),
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    UPDATE_ALL_BY_CRITERIA_SIGNATURE(
            "updateAll", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record"),
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    DELETE_SIGNATURE(
            "delete", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("PK"), "pk")
            }
    ),
    DELETE_BY_CRITERIA_SIGNATURE(
            "delete", "int", null,
            null,
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    INSERT_ALL_IMPL(
            "insertAll", "int", JavaVisibility.PUBLIC,
            new String[]{
                    "notNull(record, \"record must not be null.\");",
                    "return doInsert(\"" + NamespaceSuffix.INSERT_ALL + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_ALL_IGNORE_IMPL(
            "insertAllIgnore", "int", null,
            new String[]{
                    "notNull(record, \"record must not be null.\");",
                    "return doInsert(\"" + NamespaceSuffix.INSERT_ALL_IGNORE + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_IMPL(
            "insert", "int", null,
            new String[]{
                    "notNull(record, \"record must not be null.\");",
                    "return doInsert(\"" + NamespaceSuffix.INSERT + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    INSERT_IGNORE_IMPL(
            NamespaceSuffix.INSERT_IGNORE, "int", null,
            new String[]{
                    "notNull(record, \"record must not be null.\");",
                    "return doInsert(\"" + NamespaceSuffix.INSERT_IGNORE + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    SELECT_ONE_IMPL(
            "selectOne", "T", null,
            new String[] {
                    "notNull(pk, \"Primary key must not be null.\");",
                    "return doSelectOne(\""  + NamespaceSuffix.SELECT_BY_PK + "\", pk);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("PK"), "pk")
            }
    ),
    SELECT_ONE_BY_CRITERIA_IMPL(
            "selectOne", "T", null,
            new String[] {
                    "validateCriteria(criteria);",
                    "return doSelectOne(\"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    SELECT_LIST_IMPL(
            "selectList", "List<T>", null,
            new String[] {
                    "validateCriteria(criteria);",
                    "return doSelectList(\"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    COUNT_ALL_IMPL(
            "countAll", "long", null,
            new String[] {
                    "return sqlSessionTemplate.selectOne(namespace() + \"" + NamespaceSuffix.COUNT + "\", null);"
            },
            null
    ),
    COUNT_BY_CRITERIA_IMPL(
            "count", "long", null,
            new String[] {
                    "validateCriteria(criteria);",
                    "return sqlSessionTemplate.selectOne(namespace() + \"" + NamespaceSuffix.COUNT + "\", criteria);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    UPDATE_IMPL(
            "update", "int", null,
            new String[] {
                    "notNull(record, \"record must no be null.\");",
                    "return doUpdate(\"" + NamespaceSuffix.UPDATE + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    UPDATE_BY_CRITERIA_IMPL(
            "update", "int", null,
            new String[] {
                    "return doUpdate(\"" + NamespaceSuffix.UPDATE_BY_CRITERIA + "\", makeParam(record, criteria));"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record"),
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    UPDATE_ALL_IMPL(
            "updateAll", "int", null,
            new String[] {
                    "notNull(record, \"record must no be null.\");",
                    "return doUpdate(\"" + NamespaceSuffix.UPDATE_ALL + "\", record);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record")
            }
    ),
    UPDATE_ALL_BY_CRITERIA_IMPL(
            "updateAll", "int", null,
            new String[] {
                    "return doUpdate(\"" + NamespaceSuffix.UPDATE_ALL_BY_CRITERIA + "\", makeParam(record, criteria));"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record"),
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    DELETE_IMPL(
            "delete", "int", null,
            new String[] {
                    "notNull(pk, \"record must no be null.\");",
                    "return doDelete(\"" + NamespaceSuffix.DELETE + "\", pk);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("PK"), "pk")
            }
    ),
    DELETE_BY_CRITERIA_IMPL(
            "delete", "int", null,
            new String[] {
                    "validateCriteria(criteria);",
                    "return doDelete(\"" + NamespaceSuffix.DELETE_BY_CRITERIA+ "\", criteria);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    VALIDATE_CRITERIA(
            "validateCriteria", "void", JavaVisibility.PROTECTED,
            new String[] {
                     "if (criteria == null || criteria.getOredCriteria().isEmpty()) {",
                     "throw new IllegalArgumentException(\"criteria must not be empty.\");",
                     "}"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    MAKE_PARAM(
            "makeParam", "Map<String, Object>", JavaVisibility.PROTECTED,
            new String[] {
                    "notNull(record, \"record must not be null.\");",
                    "validateCriteria(criteria);",
                    "Map<String, Object> param = new HashMap<>();",
                    "param.put(\"record\", record);",
                    "param.put(\"example\", criteria);",
                    "return param;"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("B"), "record"),
                    new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria")
            }
    ),
    NOT_NULL(
            "notNull", "void", JavaVisibility.PROTECTED,
            new String[] {
                    "if (object == null) {",
                    "throw new IllegalArgumentException(errMsg);",
                    "}"
            },
            new Parameter[]{
                    new Parameter(new FullyQualifiedJavaType("Object"), "object"),
                    new Parameter(new FullyQualifiedJavaType("String"), "errMsg")
            }
    ),
    DO_SELECT_ONE(
            "doSelectOne", "T", JavaVisibility.PROTECTED,
            new String[] {
                "return sqlSessionTemplate.selectOne(namespace() + statement, parameter);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "statement"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "parameter")
            }
    ),
    DO_SELECT_LIST(
            "doSelectList", "List<T>", JavaVisibility.PROTECTED,
            new String[] {
                    "return sqlSessionTemplate.selectList(namespace() + statement, parameter);"
            },
            new Parameter[] {
                    new Parameter(new FullyQualifiedJavaType("String"), "statement"),
                    new Parameter(new FullyQualifiedJavaType("Object"), "parameter")
            }
    ),
    DO_UPDATE(
            "doUpdate", "int", JavaVisibility.PROTECTED,
            new String[] {
                "return sqlSessionTemplate.update(namespace() + statement, parameter);"
            },
            new Parameter[] {
                new Parameter(new FullyQualifiedJavaType("String"), "statement"),
                new Parameter(new FullyQualifiedJavaType("Object"), "parameter")
            }
    ),
    DO_INSERT(
            "doInsert", "int", JavaVisibility.PROTECTED,
            new String[] {
                "return sqlSessionTemplate.insert(namespace() + statement, parameter);"
            },
            new Parameter[] {
                new Parameter(new FullyQualifiedJavaType("String"), "statement"),
                new Parameter(new FullyQualifiedJavaType("Object"), "parameter")
            }
    ),
    DO_DELETE(
            "doDelete", "int", JavaVisibility.PROTECTED,
            new String[] {
                "return sqlSessionTemplate.delete(namespace() + statement, parameter);"
            },
            new Parameter[] {
                new Parameter(new FullyQualifiedJavaType("String"), "statement"),
                new Parameter(new FullyQualifiedJavaType("Object"), "parameter")
            }
    );

    private String name;

    private JavaVisibility javaVisibility;

    private String returnType;

    private String[] bodyLineList;

    private Parameter[] parameterList;

    ConstantMethod(String name, String returnType, JavaVisibility javaVisibility, String[] bodyLineList, Parameter[] parameterList) {
        this.name = name;
        this.returnType = returnType;
        this.bodyLineList = bodyLineList;
        this.parameterList = parameterList;
        this.javaVisibility = javaVisibility;
    }

    public String getName() {
        return this.name;
    }

    public Method get() {
        Method method;
        if (returnType != null) {
            method = MethodUtil.publicMethod(name, returnType);
        } else {
            method = MethodUtil.constructor(name);
        }
        if (javaVisibility != null) {
            method.setVisibility(javaVisibility);
        }
        if (bodyLineList != null) {
            for (String s : bodyLineList) {
                method.addBodyLine(s);
            }
        }
        if (parameterList != null) {
            for (Parameter parameter : parameterList) {
                method.addParameter(parameter);
            }
        }
        return method;
    }

}
