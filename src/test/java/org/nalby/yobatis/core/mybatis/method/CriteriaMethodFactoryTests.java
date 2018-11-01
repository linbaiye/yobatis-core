package org.nalby.yobatis.core.mybatis.method;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.AbstractYobatisTableSetup;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CriteriaMethodFactoryTests extends AbstractYobatisTableSetup {

    private MethodFactory methodFactory;

    private IntrospectedColumn column;

    @Before
    public void setup() {
        super.setup();
        methodFactory = CriteriaMethodFactory.getInstance(yobatisIntrospectedTable);
        column = mock(IntrospectedColumn.class);
    }

    private void mockColumn(String property, String jdbcType, String type) {
        when(column.getFullyQualifiedJavaType()).thenReturn(new FullyQualifiedJavaType(type));
        when(column.getJavaProperty()).thenReturn(property);
        when(column.getJdbcTypeName()).thenReturn(jdbcType);
        when(column.getActualColumnName()).thenReturn(property);
    }

    @Test
    public void in() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.IN.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andIdIn(List<Integer> values) {\n" +
                "    lastCriteria().addCriterion(\"id in\", values, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void jdbcTime() {
        mockColumn("at", "TIME", "java.util.Date");
        Method method = methodFactory.create(CriteriaMethodType.EQUAL_TO.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andAtEqualTo(java.util.Date value) {\n" +
                        "    lastCriteria().addCriterionForJDBCTime(\"at =\", value, \"at\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void jdbcDateBetweenValue() {
        mockColumn("at", "DATE", "java.util.Date");
        Method method = methodFactory.create(CriteriaMethodType.BETWEEN.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andAtBetween(java.util.Date value1, java.util.Date value2) {\n" +
                        "    lastCriteria().addCriterionForJDBCDate(\"at between\", value1, value2, \"at\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void jdbcDateNoValue() {
        mockColumn("at", "DATE", "java.util.Date");
        Method method = methodFactory.create(CriteriaMethodType.IS_NOT_NULL.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andAtIsNotNull() {\n" +
                        "    lastCriteria().addCriterion(\"at is not null\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void jdbcDateSingleValue() {
        mockColumn("at", "DATE", "java.util.Date");
        Method method = methodFactory.create(CriteriaMethodType.EQUAL_TO.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andAtEqualTo(java.util.Date value) {\n" +
                        "    lastCriteria().addCriterionForJDBCDate(\"at =\", value, \"at\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void jdbcDateListValue() {
        mockColumn("at", "DATE", "java.util.Date");
        Method method = methodFactory.create(CriteriaMethodType.IN.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andAtIn(List<Date> values) {\n" +
                        "    lastCriteria().addCriterionForJDBCDate(\"at in\", values, \"at\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void boolIn() {
        mockColumn("buff", "BOOL", "Boolean");
        Method method = methodFactory.create(CriteriaMethodType.IN.getType(), column);
        assertNull(method);
    }

    @Test
    public void binaryIn() {
        mockColumn("buff", "BINARY", "byte[]");
        Method method = methodFactory.create(CriteriaMethodType.IN.getType(), column);
        assertNull(method);
    }

    @Test
    public void notIn() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.NOT_IN.getType(), column);
        String result =
                "public org.yobatis.entity.criteria.YobatisCriteria andIdNotIn(List<Integer> values) {\n" +
                        "    lastCriteria().addCriterion(\"id not in\", values, \"id\");\n" +
                        "    return this;\n" +
                        "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void isNull() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.IS_NULL.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdIsNull() {\n" +
                "    lastCriteria().addCriterion(\"id is null\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }


    @Test
    public void isNotNull() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.IS_NOT_NULL.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdIsNotNull() {\n" +
                "    lastCriteria().addCriterion(\"id is not null\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void equal() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.EQUAL_TO.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdEqualTo(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id =\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void binaryEqual() {
        mockColumn("id", "BINARY", "byte[]");
        Method method = methodFactory.create(CriteriaMethodType.EQUAL_TO.getType(), column);
        assertNull(method);
        method = methodFactory.create(CriteriaMethodType.NOT_EQUAL_TO.getType(), column);
        assertNull(method);
    }

    @Test
    public void notEqual() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.NOT_EQUAL_TO.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdNotEqualTo(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id <>\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void lessThan() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.LESS_THAN.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdLessThan(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id <\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void lessThanOrEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.LESS_THAN_OR_EQUAL_TO.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdLessThanOrEqualTo(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id <=\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void greaterThan() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.GREATER_THAN.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdGreaterThan(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id >\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void greaterThanOrEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.GREATER_THAN_OR_EQUAL_TO.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdGreaterThanOrEqualTo(Integer value) {\n" +
                "    lastCriteria().addCriterion(\"id >=\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void between() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.BETWEEN.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdBetween(Integer value1, Integer value2) {\n" +
                "    lastCriteria().addCriterion(\"id between\", value1, value2, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void like() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.LIKE.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdLike(String value) {\n" +
                "    lastCriteria().addCriterion(\"id like\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
        mockColumn("id", "INT", "Integer");
        method = methodFactory.create(CriteriaMethodType.LIKE.getType(), column);
        assertNull(method);
    }

    @Test
    public void notLike() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.NOT_LIKE.getType(), column);
        String result = "public org.yobatis.entity.criteria.YobatisCriteria andIdNotLike(String value) {\n" +
                "    lastCriteria().addCriterion(\"id not like\", value, \"id\");\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }


    @Test
    public void staticIsNull() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_IS_NULL.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idIsNull() {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdIsNull();\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticIsNotNull() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_IS_NOT_NULL.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idIsNotNull() {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdIsNotNull();\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticIn() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_IN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idIn(List<Integer> values) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdIn(values);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticNotIn() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_NOT_IN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idNotIn(List<Integer> values) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdNotIn(values);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_EQUAL_TO.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idEqualTo(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdEqualTo(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticNotEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_NOT_EQUAL_TO.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idNotEqualTo(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdNotEqualTo(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticLessThan() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_LESS_THAN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idLessThan(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdLessThan(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticLessThanOrEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_LESS_THAN_OR_EQUAL_TO.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idLessThanOrEqualTo(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdLessThanOrEqualTo(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticGreaterThan() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_GREATER_THAN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idGreaterThan(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdGreaterThan(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticGreaterThanOrEqualTo() {
        mockColumn("id", "INT", "Integer");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_GREATER_THAN_OR_EQUAL_TO.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idGreaterThanOrEqualTo(Integer value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdGreaterThanOrEqualTo(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticLike() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_LIKE.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idLike(String value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdLike(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticNotLike() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_NOT_LIKE.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idNotLike(String value) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdNotLike(value);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticBetween() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_BETWEEN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idBetween(String value1, String value2) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdBetween(value1, value2);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void staticNotBetween() {
        mockColumn("id", "CHAR", "String");
        Method method = methodFactory.create(CriteriaMethodType.STATIC_NOT_BETWEEN.getType(), column);
        String result = "public static org.yobatis.entity.criteria.YobatisCriteria idNotBetween(String value1, String value2) {\n" +
                "    return new org.yobatis.entity.criteria.YobatisCriteria().andIdNotBetween(value1, value2);\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void notSupportedTypes() {
        mockColumn("id", "INT", "Integer");
        assertNull(methodFactory.create(CriteriaMethodType.LIKE.getType(), column));
        mockColumn("id", "BOOL", "Boolean");
        assertNull(methodFactory.create(CriteriaMethodType.GREATER_THAN.getType(), column));
    }

    @Test
    public void or() {
        Method method = methodFactory.create(CriteriaMethodType.OR.getType());
        String result = "public org.yobatis.entity.criteria.YobatisCriteria or() {\n" +
                "    oredCriteria.add(new BracketCriteria());\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(result, method);
    }

    @Test
    public void ascOrder() {
        Method method = methodFactory.create(CriteriaMethodType.ASC_ORDER_BY.getType());
        String content = "public org.yobatis.entity.criteria.YobatisCriteria ascOrderBy(String  ... fields) {\n" +
                "    orderBy(\"asc\", fields);\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(content, method);
    }

    @Test
    public void descOrder() {
        Method method = methodFactory.create(CriteriaMethodType.DESC_ORDER_BY.getType());
        String content = "public org.yobatis.entity.criteria.YobatisCriteria descOrderBy(String  ... fields) {\n" +
                "    orderBy(\"desc\", fields);\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(content, method);
    }

    @Test
    public void setLimit() {
        Method method = methodFactory.create(CriteriaMethodType.SET_LIMIT.getType());
        String content = "public org.yobatis.entity.criteria.YobatisCriteria setLimit(Long limit) {\n" +
                "    this.limit = limit;\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(content, method);
    }

    @Test
    public void setOffset() {
        Method method = methodFactory.create(CriteriaMethodType.SET_OFFSET.getType());
        String content = "public org.yobatis.entity.criteria.YobatisCriteria setOffset(Long offset) {\n" +
                "    this.offset = offset;\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(content, method);
    }

    @Test
    public void setForUpdate() {
        Method method = methodFactory.create(CriteriaMethodType.SET_FOR_UPDATE.getType());
        String content = "public org.yobatis.entity.criteria.YobatisCriteria setForUpdate(boolean forUpdate) {\n" +
                "    this.forUpdate = forUpdate;\n" +
                "    return this;\n" +
                "}";
        assertMethodContentEqual(content, method);
    }

    @Test
    public void hasColumn() {
        Method method = methodFactory.create(CriteriaMethodType.HAS_COLUMN.getType());
        String content = "@Override\n" +
                "protected boolean hasColumn(String column) {\n" +
                "    return COLUMNS.contains(column);\n" +
                "}";
        assertMethodContentEqual(content, method);
   }
}
