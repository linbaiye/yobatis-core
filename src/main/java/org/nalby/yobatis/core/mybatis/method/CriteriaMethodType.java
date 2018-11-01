package org.nalby.yobatis.core.mybatis.method;

import org.nalby.yobatis.core.util.TextUtil;

public enum CriteriaMethodType {
    IS_NULL("IsNull", "is null", 1),
    IS_NOT_NULL("IsNotNull", "is not null", 1),

    EQUAL_TO("EqualTo", "=", 2),
    NOT_EQUAL_TO("NotEqualTo", "<>", 2),
    LESS_THAN("LessThan", "<", 2),
    LESS_THAN_OR_EQUAL_TO("LessThanOrEqualTo", "<=", 2),
    GREATER_THAN("GreaterThan", ">", 2),
    GREATER_THAN_OR_EQUAL_TO("GreaterThanOrEqualTo", ">=", 2),
    LIKE("Like", "like", 2),
    NOT_LIKE("NotLike", "not like", 2),

    IN("In", "in", 3),
    NOT_IN("NotIn", "not in", 3),

    BETWEEN("Between", "between", 4),
    NOT_BETWEEN("NotBetween", "not between", 4),

    OR("OR", "or", 5),
    ASC_ORDER_BY("ASC_ORDER_BY", "ascOrderBy", 5),
    DESC_ORDER_BY("DESC_ORDER_BY", "descOrderBy", 5),
    SET_LIMIT("SET_LIMIT", "setLimit", 5),
    SET_OFFSET("SET_OFFSET", "setOffset", 5),
    SET_FOR_UPDATE("SET_FOR_UPDATE", "setForUpdate", 5),
    HAS_COLUMN("HAS_COLUMN", "hasColumn", 5),

    STATIC_IS_NULL("StaticIsNull", "is null", 1, true),
    STATIC_IS_NOT_NULL("StaticIsNotNull", "is not null", 1, true),
    STATIC_EQUAL_TO("StaticEqualTo", "=", 2, true),
    STATIC_NOT_EQUAL_TO("StaticNotEqualTo", "<>", 2, true),
    STATIC_LESS_THAN("StaticLessThan", "<", 2, true),
    STATIC_LESS_THAN_OR_EQUAL_TO("StaticLessThanOrEqualTo", "<=", 2, true),
    STATIC_GREATER_THAN("StaticGreaterThan", ">", 2, true),
    STATIC_GREATER_THAN_OR_EQUAL_TO("StaticGreaterThanOrEqualTo", ">=", 2, true),
    STATIC_LIKE("StaticLike", "like", 2, true),
    STATIC_NOT_LIKE("StaticNotLike", "not like", 2, true),

    STATIC_IN("StaticIn", "in", 3, true),
    STATIC_NOT_IN("StaticNotIn", "not in", 3, true),

    STATIC_BETWEEN("StaticBetween", "between", 4, true),
    STATIC_NOT_BETWEEN("StaticNotBetween", "not between", 4, true),
    ;

    private String type;

    private String sqlSnippet;

    private int valueType;

    private boolean statik;

    CriteriaMethodType(String type, String sqlSnippet, int valueType, boolean statik) {
        this.type = type;
        this.sqlSnippet = sqlSnippet;
        this.valueType = valueType;
        this.statik = statik;
    }

    CriteriaMethodType(String type, String sqlSnippet, int valueType) {
        this(type, sqlSnippet, valueType, false);
    }

    public String getType() {
        return type;
    }

    public String getSqlSnippet() {
        return sqlSnippet;
    }

    public boolean isStatic() {
        return statik;
    }

    public boolean isNoValue() {
        return valueType == 1;
    }

    public boolean isListValue() {
        return valueType == 3;
    }

    public boolean isBetweenValue() {
        return valueType == 4;
    }

    public boolean isSingleValue() {
        return valueType == 2;
    }

    public boolean isJavaTypeSupported(String javaType) {
        if (javaType.equals("Boolean")) {
           if (this != IS_NULL && this != IS_NOT_NULL && this != EQUAL_TO &&
                   this != STATIC_IS_NULL && this != STATIC_IS_NOT_NULL && this != STATIC_EQUAL_TO) {
               return false;
           }
        } else if (javaType.equals("byte[]")) {
            if (this != IS_NULL && this != IS_NOT_NULL && this != STATIC_IS_NULL && this != STATIC_IS_NOT_NULL) {
                return false;
            }
        } else if (!javaType.equals("String")) {
            if (this == LIKE || this == NOT_LIKE || this == STATIC_LIKE || this == STATIC_NOT_LIKE) {
                return false;
            }
        }
        return true;
    }

    public String makeMethodName(String property) {
        if (this.valueType == 5) {
            return this.getSqlSnippet();
        }
        if (statik) {
            return property + type.replaceFirst("Static", "");
        }
        return "and" + TextUtil.capitalizeFirstChar(property) + type;
    }

    public String makeCallingMethodName(String property) {
        if (statik) {
            return "and" + TextUtil.capitalizeFirstChar(property) +
                    type.replaceFirst("Static", "");
        }
        return null;
    }
}
