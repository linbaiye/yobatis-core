package func.alltype.model.criteria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Do not modify, it will be overwritten every time yobatis runs.
 */
public final class AllDataTypesCriteria extends BaseCriteria {
    private static final Map<String, String> PROPERTY_TO_COLUMN;

    static {
        PROPERTY_TO_COLUMN = new HashMap<>();
        PROPERTY_TO_COLUMN.put("typeBigint", "type_bigint");
        PROPERTY_TO_COLUMN.put("typeVarchar", "type_varchar");
        PROPERTY_TO_COLUMN.put("typeTinyint", "type_tinyint");
        PROPERTY_TO_COLUMN.put("typeSmallint", "type_smallint");
        PROPERTY_TO_COLUMN.put("typeMediumint", "type_mediumint");
        PROPERTY_TO_COLUMN.put("typeInt", "type_int");
        PROPERTY_TO_COLUMN.put("typeDate", "type_date");
        PROPERTY_TO_COLUMN.put("typeFloat", "type_float");
        PROPERTY_TO_COLUMN.put("typeDouble", "type_double");
        PROPERTY_TO_COLUMN.put("typeDecimal", "type_decimal");
        PROPERTY_TO_COLUMN.put("typeDatetime", "type_datetime");
        PROPERTY_TO_COLUMN.put("typeTimestamp", "type_timestamp");
        PROPERTY_TO_COLUMN.put("typeTime", "type_time");
        PROPERTY_TO_COLUMN.put("typeYear", "type_year");
        PROPERTY_TO_COLUMN.put("typeChar", "type_char");
        PROPERTY_TO_COLUMN.put("typeTinytext", "type_tinytext");
        PROPERTY_TO_COLUMN.put("typeEnum", "type_enum");
        PROPERTY_TO_COLUMN.put("typeSet", "type_set");
        PROPERTY_TO_COLUMN.put("typeBool", "type_bool");
        PROPERTY_TO_COLUMN.put("typeText", "type_text");
        PROPERTY_TO_COLUMN.put("typeTinyblob", "type_tinyblob");
        PROPERTY_TO_COLUMN.put("typeBlob", "type_blob");
        PROPERTY_TO_COLUMN.put("typeMediumblob", "type_mediumblob");
        PROPERTY_TO_COLUMN.put("typeMediumtext", "type_mediumtext");
        PROPERTY_TO_COLUMN.put("typeLongblob", "type_longblob");
        PROPERTY_TO_COLUMN.put("typeLongtext", "type_longtext");
        PROPERTY_TO_COLUMN.put("typeBinary", "type_binary");
        PROPERTY_TO_COLUMN.put("typeVarbinary", "type_varbinary");
    }

    private void orderBy(String order, String ... fields) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("Empty fields passed.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (orderByClause != null) {
            stringBuilder.append(orderByClause);
            stringBuilder.append(',');
        }
        for (String field : fields) {
            if (!PROPERTY_TO_COLUMN.containsKey(field)) {
                throw new IllegalArgumentException("Unrecognizable field:" + field);
            }
            stringBuilder.append(PROPERTY_TO_COLUMN.get(field));
            stringBuilder.append(" ");
            stringBuilder.append(order);
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        orderByClause = stringBuilder.toString();
    }

    public AllDataTypesCriteria ascOrderBy(String  ... fields) {
        orderBy("asc", fields);
        return this;
    }

    public AllDataTypesCriteria descOrderBy(String  ... fields) {
        orderBy("desc", fields);
        return this;
    }

    public AllDataTypesCriteria or() {
        oredCriteria.add(createCriteriaInternal());
        return this;
    }

    public AllDataTypesCriteria setLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public AllDataTypesCriteria setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public AllDataTypesCriteria setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
        return this;
    }

    public AllDataTypesCriteria andTypeBigintIsNull() {
        lastCriteria().addCriterion("type_bigint is null");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintIsNotNull() {
        lastCriteria().addCriterion("type_bigint is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintEqualTo(Long value) {
        lastCriteria().addCriterion("type_bigint =", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintNotEqualTo(Long value) {
        lastCriteria().addCriterion("type_bigint <>", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintGreaterThan(Long value) {
        lastCriteria().addCriterion("type_bigint >", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintGreaterThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("type_bigint >=", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintLessThan(Long value) {
        lastCriteria().addCriterion("type_bigint <", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintLessThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("type_bigint <=", value, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintIn(List<Long> values) {
        lastCriteria().addCriterion("type_bigint in", values, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintNotIn(List<Long> values) {
        lastCriteria().addCriterion("type_bigint not in", values, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintBetween(Long value1, Long value2) {
        lastCriteria().addCriterion("type_bigint between", value1, value2, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeBigintNotBetween(Long value1, Long value2) {
        lastCriteria().addCriterion("type_bigint not between", value1, value2, "typeBigint");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharIsNull() {
        lastCriteria().addCriterion("type_varchar is null");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharIsNotNull() {
        lastCriteria().addCriterion("type_varchar is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharEqualTo(String value) {
        lastCriteria().addCriterion("type_varchar =", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharNotEqualTo(String value) {
        lastCriteria().addCriterion("type_varchar <>", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharGreaterThan(String value) {
        lastCriteria().addCriterion("type_varchar >", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_varchar >=", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharLessThan(String value) {
        lastCriteria().addCriterion("type_varchar <", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_varchar <=", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharLike(String value) {
        lastCriteria().addCriterion("type_varchar like", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharNotLike(String value) {
        lastCriteria().addCriterion("type_varchar not like", value, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharIn(List<String> values) {
        lastCriteria().addCriterion("type_varchar in", values, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharNotIn(List<String> values) {
        lastCriteria().addCriterion("type_varchar not in", values, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_varchar between", value1, value2, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeVarcharNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_varchar not between", value1, value2, "typeVarchar");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintIsNull() {
        lastCriteria().addCriterion("type_tinyint is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintIsNotNull() {
        lastCriteria().addCriterion("type_tinyint is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintEqualTo(Byte value) {
        lastCriteria().addCriterion("type_tinyint =", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintNotEqualTo(Byte value) {
        lastCriteria().addCriterion("type_tinyint <>", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintGreaterThan(Byte value) {
        lastCriteria().addCriterion("type_tinyint >", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintGreaterThanOrEqualTo(Byte value) {
        lastCriteria().addCriterion("type_tinyint >=", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintLessThan(Byte value) {
        lastCriteria().addCriterion("type_tinyint <", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintLessThanOrEqualTo(Byte value) {
        lastCriteria().addCriterion("type_tinyint <=", value, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintIn(List<Byte> values) {
        lastCriteria().addCriterion("type_tinyint in", values, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintNotIn(List<Byte> values) {
        lastCriteria().addCriterion("type_tinyint not in", values, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintBetween(Byte value1, Byte value2) {
        lastCriteria().addCriterion("type_tinyint between", value1, value2, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyintNotBetween(Byte value1, Byte value2) {
        lastCriteria().addCriterion("type_tinyint not between", value1, value2, "typeTinyint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintIsNull() {
        lastCriteria().addCriterion("type_smallint is null");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintIsNotNull() {
        lastCriteria().addCriterion("type_smallint is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintEqualTo(Short value) {
        lastCriteria().addCriterion("type_smallint =", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintNotEqualTo(Short value) {
        lastCriteria().addCriterion("type_smallint <>", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintGreaterThan(Short value) {
        lastCriteria().addCriterion("type_smallint >", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintGreaterThanOrEqualTo(Short value) {
        lastCriteria().addCriterion("type_smallint >=", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintLessThan(Short value) {
        lastCriteria().addCriterion("type_smallint <", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintLessThanOrEqualTo(Short value) {
        lastCriteria().addCriterion("type_smallint <=", value, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintIn(List<Short> values) {
        lastCriteria().addCriterion("type_smallint in", values, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintNotIn(List<Short> values) {
        lastCriteria().addCriterion("type_smallint not in", values, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintBetween(Short value1, Short value2) {
        lastCriteria().addCriterion("type_smallint between", value1, value2, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeSmallintNotBetween(Short value1, Short value2) {
        lastCriteria().addCriterion("type_smallint not between", value1, value2, "typeSmallint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintIsNull() {
        lastCriteria().addCriterion("type_mediumint is null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintIsNotNull() {
        lastCriteria().addCriterion("type_mediumint is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintEqualTo(Integer value) {
        lastCriteria().addCriterion("type_mediumint =", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintNotEqualTo(Integer value) {
        lastCriteria().addCriterion("type_mediumint <>", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintGreaterThan(Integer value) {
        lastCriteria().addCriterion("type_mediumint >", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintGreaterThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("type_mediumint >=", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintLessThan(Integer value) {
        lastCriteria().addCriterion("type_mediumint <", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintLessThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("type_mediumint <=", value, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintIn(List<Integer> values) {
        lastCriteria().addCriterion("type_mediumint in", values, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintNotIn(List<Integer> values) {
        lastCriteria().addCriterion("type_mediumint not in", values, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("type_mediumint between", value1, value2, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumintNotBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("type_mediumint not between", value1, value2, "typeMediumint");
        return this;
    }

    public AllDataTypesCriteria andTypeIntIsNull() {
        lastCriteria().addCriterion("type_int is null");
        return this;
    }

    public AllDataTypesCriteria andTypeIntIsNotNull() {
        lastCriteria().addCriterion("type_int is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeIntEqualTo(Integer value) {
        lastCriteria().addCriterion("type_int =", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntNotEqualTo(Integer value) {
        lastCriteria().addCriterion("type_int <>", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntGreaterThan(Integer value) {
        lastCriteria().addCriterion("type_int >", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntGreaterThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("type_int >=", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntLessThan(Integer value) {
        lastCriteria().addCriterion("type_int <", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntLessThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("type_int <=", value, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntIn(List<Integer> values) {
        lastCriteria().addCriterion("type_int in", values, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntNotIn(List<Integer> values) {
        lastCriteria().addCriterion("type_int not in", values, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("type_int between", value1, value2, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeIntNotBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("type_int not between", value1, value2, "typeInt");
        return this;
    }

    public AllDataTypesCriteria andTypeDateIsNull() {
        lastCriteria().addCriterion("type_date is null");
        return this;
    }

    public AllDataTypesCriteria andTypeDateIsNotNull() {
        lastCriteria().addCriterion("type_date is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeDateEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date =", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateNotEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date <>", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateGreaterThan(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date >", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateGreaterThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date >=", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateLessThan(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date <", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateLessThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_date <=", value, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCDate("type_date in", values, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateNotIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCDate("type_date not in", values, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCDate("type_date between", value1, value2, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeDateNotBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCDate("type_date not between", value1, value2, "typeDate");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatIsNull() {
        lastCriteria().addCriterion("type_float is null");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatIsNotNull() {
        lastCriteria().addCriterion("type_float is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatEqualTo(Float value) {
        lastCriteria().addCriterion("type_float =", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatNotEqualTo(Float value) {
        lastCriteria().addCriterion("type_float <>", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatGreaterThan(Float value) {
        lastCriteria().addCriterion("type_float >", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatGreaterThanOrEqualTo(Float value) {
        lastCriteria().addCriterion("type_float >=", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatLessThan(Float value) {
        lastCriteria().addCriterion("type_float <", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatLessThanOrEqualTo(Float value) {
        lastCriteria().addCriterion("type_float <=", value, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatIn(List<Float> values) {
        lastCriteria().addCriterion("type_float in", values, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatNotIn(List<Float> values) {
        lastCriteria().addCriterion("type_float not in", values, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatBetween(Float value1, Float value2) {
        lastCriteria().addCriterion("type_float between", value1, value2, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeFloatNotBetween(Float value1, Float value2) {
        lastCriteria().addCriterion("type_float not between", value1, value2, "typeFloat");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleIsNull() {
        lastCriteria().addCriterion("type_double is null");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleIsNotNull() {
        lastCriteria().addCriterion("type_double is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleEqualTo(Double value) {
        lastCriteria().addCriterion("type_double =", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleNotEqualTo(Double value) {
        lastCriteria().addCriterion("type_double <>", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleGreaterThan(Double value) {
        lastCriteria().addCriterion("type_double >", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleGreaterThanOrEqualTo(Double value) {
        lastCriteria().addCriterion("type_double >=", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleLessThan(Double value) {
        lastCriteria().addCriterion("type_double <", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleLessThanOrEqualTo(Double value) {
        lastCriteria().addCriterion("type_double <=", value, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleIn(List<Double> values) {
        lastCriteria().addCriterion("type_double in", values, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleNotIn(List<Double> values) {
        lastCriteria().addCriterion("type_double not in", values, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleBetween(Double value1, Double value2) {
        lastCriteria().addCriterion("type_double between", value1, value2, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDoubleNotBetween(Double value1, Double value2) {
        lastCriteria().addCriterion("type_double not between", value1, value2, "typeDouble");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalIsNull() {
        lastCriteria().addCriterion("type_decimal is null");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalIsNotNull() {
        lastCriteria().addCriterion("type_decimal is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal =", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalNotEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal <>", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalGreaterThan(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal >", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalGreaterThanOrEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal >=", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalLessThan(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal <", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalLessThanOrEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("type_decimal <=", value, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalIn(List<BigDecimal> values) {
        lastCriteria().addCriterion("type_decimal in", values, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalNotIn(List<BigDecimal> values) {
        lastCriteria().addCriterion("type_decimal not in", values, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalBetween(BigDecimal value1, BigDecimal value2) {
        lastCriteria().addCriterion("type_decimal between", value1, value2, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
        lastCriteria().addCriterion("type_decimal not between", value1, value2, "typeDecimal");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeIsNull() {
        lastCriteria().addCriterion("type_datetime is null");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeIsNotNull() {
        lastCriteria().addCriterion("type_datetime is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeEqualTo(Date value) {
        lastCriteria().addCriterion("type_datetime =", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeNotEqualTo(Date value) {
        lastCriteria().addCriterion("type_datetime <>", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeGreaterThan(Date value) {
        lastCriteria().addCriterion("type_datetime >", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeGreaterThanOrEqualTo(Date value) {
        lastCriteria().addCriterion("type_datetime >=", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeLessThan(Date value) {
        lastCriteria().addCriterion("type_datetime <", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeLessThanOrEqualTo(Date value) {
        lastCriteria().addCriterion("type_datetime <=", value, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeIn(List<Date> values) {
        lastCriteria().addCriterion("type_datetime in", values, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeNotIn(List<Date> values) {
        lastCriteria().addCriterion("type_datetime not in", values, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeBetween(Date value1, Date value2) {
        lastCriteria().addCriterion("type_datetime between", value1, value2, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeDatetimeNotBetween(Date value1, Date value2) {
        lastCriteria().addCriterion("type_datetime not between", value1, value2, "typeDatetime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampIsNull() {
        lastCriteria().addCriterion("type_timestamp is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampIsNotNull() {
        lastCriteria().addCriterion("type_timestamp is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampEqualTo(Date value) {
        lastCriteria().addCriterion("type_timestamp =", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampNotEqualTo(Date value) {
        lastCriteria().addCriterion("type_timestamp <>", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampGreaterThan(Date value) {
        lastCriteria().addCriterion("type_timestamp >", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampGreaterThanOrEqualTo(Date value) {
        lastCriteria().addCriterion("type_timestamp >=", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampLessThan(Date value) {
        lastCriteria().addCriterion("type_timestamp <", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampLessThanOrEqualTo(Date value) {
        lastCriteria().addCriterion("type_timestamp <=", value, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampIn(List<Date> values) {
        lastCriteria().addCriterion("type_timestamp in", values, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampNotIn(List<Date> values) {
        lastCriteria().addCriterion("type_timestamp not in", values, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampBetween(Date value1, Date value2) {
        lastCriteria().addCriterion("type_timestamp between", value1, value2, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimestampNotBetween(Date value1, Date value2) {
        lastCriteria().addCriterion("type_timestamp not between", value1, value2, "typeTimestamp");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeIsNull() {
        lastCriteria().addCriterion("type_time is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeIsNotNull() {
        lastCriteria().addCriterion("type_time is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time =", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeNotEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time <>", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeGreaterThan(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time >", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeGreaterThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time >=", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeLessThan(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time <", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeLessThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCTime("type_time <=", value, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCTime("type_time in", values, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeNotIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCTime("type_time not in", values, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCTime("type_time between", value1, value2, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeTimeNotBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCTime("type_time not between", value1, value2, "typeTime");
        return this;
    }

    public AllDataTypesCriteria andTypeYearIsNull() {
        lastCriteria().addCriterion("type_year is null");
        return this;
    }

    public AllDataTypesCriteria andTypeYearIsNotNull() {
        lastCriteria().addCriterion("type_year is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeYearEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year =", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearNotEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year <>", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearGreaterThan(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year >", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearGreaterThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year >=", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearLessThan(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year <", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearLessThanOrEqualTo(Date value) {
        lastCriteria().addCriterionForJDBCDate("type_year <=", value, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCDate("type_year in", values, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearNotIn(List<Date> values) {
        lastCriteria().addCriterionForJDBCDate("type_year not in", values, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCDate("type_year between", value1, value2, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeYearNotBetween(Date value1, Date value2) {
        lastCriteria().addCriterionForJDBCDate("type_year not between", value1, value2, "typeYear");
        return this;
    }

    public AllDataTypesCriteria andTypeCharIsNull() {
        lastCriteria().addCriterion("type_char is null");
        return this;
    }

    public AllDataTypesCriteria andTypeCharIsNotNull() {
        lastCriteria().addCriterion("type_char is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeCharEqualTo(String value) {
        lastCriteria().addCriterion("type_char =", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharNotEqualTo(String value) {
        lastCriteria().addCriterion("type_char <>", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharGreaterThan(String value) {
        lastCriteria().addCriterion("type_char >", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_char >=", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharLessThan(String value) {
        lastCriteria().addCriterion("type_char <", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_char <=", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharLike(String value) {
        lastCriteria().addCriterion("type_char like", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharNotLike(String value) {
        lastCriteria().addCriterion("type_char not like", value, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharIn(List<String> values) {
        lastCriteria().addCriterion("type_char in", values, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharNotIn(List<String> values) {
        lastCriteria().addCriterion("type_char not in", values, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_char between", value1, value2, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeCharNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_char not between", value1, value2, "typeChar");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextIsNull() {
        lastCriteria().addCriterion("type_tinytext is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextIsNotNull() {
        lastCriteria().addCriterion("type_tinytext is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextEqualTo(String value) {
        lastCriteria().addCriterion("type_tinytext =", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextNotEqualTo(String value) {
        lastCriteria().addCriterion("type_tinytext <>", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextGreaterThan(String value) {
        lastCriteria().addCriterion("type_tinytext >", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_tinytext >=", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextLessThan(String value) {
        lastCriteria().addCriterion("type_tinytext <", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_tinytext <=", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextLike(String value) {
        lastCriteria().addCriterion("type_tinytext like", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextNotLike(String value) {
        lastCriteria().addCriterion("type_tinytext not like", value, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextIn(List<String> values) {
        lastCriteria().addCriterion("type_tinytext in", values, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextNotIn(List<String> values) {
        lastCriteria().addCriterion("type_tinytext not in", values, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_tinytext between", value1, value2, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeTinytextNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_tinytext not between", value1, value2, "typeTinytext");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumIsNull() {
        lastCriteria().addCriterion("type_enum is null");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumIsNotNull() {
        lastCriteria().addCriterion("type_enum is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumEqualTo(String value) {
        lastCriteria().addCriterion("type_enum =", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumNotEqualTo(String value) {
        lastCriteria().addCriterion("type_enum <>", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumGreaterThan(String value) {
        lastCriteria().addCriterion("type_enum >", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_enum >=", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumLessThan(String value) {
        lastCriteria().addCriterion("type_enum <", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_enum <=", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumLike(String value) {
        lastCriteria().addCriterion("type_enum like", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumNotLike(String value) {
        lastCriteria().addCriterion("type_enum not like", value, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumIn(List<String> values) {
        lastCriteria().addCriterion("type_enum in", values, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumNotIn(List<String> values) {
        lastCriteria().addCriterion("type_enum not in", values, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_enum between", value1, value2, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeEnumNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_enum not between", value1, value2, "typeEnum");
        return this;
    }

    public AllDataTypesCriteria andTypeSetIsNull() {
        lastCriteria().addCriterion("type_set is null");
        return this;
    }

    public AllDataTypesCriteria andTypeSetIsNotNull() {
        lastCriteria().addCriterion("type_set is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeSetEqualTo(String value) {
        lastCriteria().addCriterion("type_set =", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetNotEqualTo(String value) {
        lastCriteria().addCriterion("type_set <>", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetGreaterThan(String value) {
        lastCriteria().addCriterion("type_set >", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_set >=", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetLessThan(String value) {
        lastCriteria().addCriterion("type_set <", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_set <=", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetLike(String value) {
        lastCriteria().addCriterion("type_set like", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetNotLike(String value) {
        lastCriteria().addCriterion("type_set not like", value, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetIn(List<String> values) {
        lastCriteria().addCriterion("type_set in", values, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetNotIn(List<String> values) {
        lastCriteria().addCriterion("type_set not in", values, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_set between", value1, value2, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeSetNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_set not between", value1, value2, "typeSet");
        return this;
    }

    public AllDataTypesCriteria andTypeBoolIsNull() {
        lastCriteria().addCriterion("type_bool is null");
        return this;
    }

    public AllDataTypesCriteria andTypeBoolIsNotNull() {
        lastCriteria().addCriterion("type_bool is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeBoolEqualTo(Boolean value) {
        lastCriteria().addCriterion("type_bool =", value, "typeBool");
        return this;
    }

    public AllDataTypesCriteria andTypeTextIsNull() {
        lastCriteria().addCriterion("type_text is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTextIsNotNull() {
        lastCriteria().addCriterion("type_text is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeTextEqualTo(String value) {
        lastCriteria().addCriterion("type_text =", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextNotEqualTo(String value) {
        lastCriteria().addCriterion("type_text <>", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextGreaterThan(String value) {
        lastCriteria().addCriterion("type_text >", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_text >=", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextLessThan(String value) {
        lastCriteria().addCriterion("type_text <", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_text <=", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextLike(String value) {
        lastCriteria().addCriterion("type_text like", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextNotLike(String value) {
        lastCriteria().addCriterion("type_text not like", value, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextIn(List<String> values) {
        lastCriteria().addCriterion("type_text in", values, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextNotIn(List<String> values) {
        lastCriteria().addCriterion("type_text not in", values, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_text between", value1, value2, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTextNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_text not between", value1, value2, "typeText");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyblobIsNull() {
        lastCriteria().addCriterion("type_tinyblob is null");
        return this;
    }

    public AllDataTypesCriteria andTypeTinyblobIsNotNull() {
        lastCriteria().addCriterion("type_tinyblob is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeBlobIsNull() {
        lastCriteria().addCriterion("type_blob is null");
        return this;
    }

    public AllDataTypesCriteria andTypeBlobIsNotNull() {
        lastCriteria().addCriterion("type_blob is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumblobIsNull() {
        lastCriteria().addCriterion("type_mediumblob is null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumblobIsNotNull() {
        lastCriteria().addCriterion("type_mediumblob is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextIsNull() {
        lastCriteria().addCriterion("type_mediumtext is null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextIsNotNull() {
        lastCriteria().addCriterion("type_mediumtext is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextEqualTo(String value) {
        lastCriteria().addCriterion("type_mediumtext =", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextNotEqualTo(String value) {
        lastCriteria().addCriterion("type_mediumtext <>", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextGreaterThan(String value) {
        lastCriteria().addCriterion("type_mediumtext >", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_mediumtext >=", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextLessThan(String value) {
        lastCriteria().addCriterion("type_mediumtext <", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_mediumtext <=", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextLike(String value) {
        lastCriteria().addCriterion("type_mediumtext like", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextNotLike(String value) {
        lastCriteria().addCriterion("type_mediumtext not like", value, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextIn(List<String> values) {
        lastCriteria().addCriterion("type_mediumtext in", values, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextNotIn(List<String> values) {
        lastCriteria().addCriterion("type_mediumtext not in", values, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_mediumtext between", value1, value2, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeMediumtextNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_mediumtext not between", value1, value2, "typeMediumtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongblobIsNull() {
        lastCriteria().addCriterion("type_longblob is null");
        return this;
    }

    public AllDataTypesCriteria andTypeLongblobIsNotNull() {
        lastCriteria().addCriterion("type_longblob is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextIsNull() {
        lastCriteria().addCriterion("type_longtext is null");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextIsNotNull() {
        lastCriteria().addCriterion("type_longtext is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextEqualTo(String value) {
        lastCriteria().addCriterion("type_longtext =", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextNotEqualTo(String value) {
        lastCriteria().addCriterion("type_longtext <>", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextGreaterThan(String value) {
        lastCriteria().addCriterion("type_longtext >", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextGreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_longtext >=", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextLessThan(String value) {
        lastCriteria().addCriterion("type_longtext <", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextLessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("type_longtext <=", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextLike(String value) {
        lastCriteria().addCriterion("type_longtext like", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextNotLike(String value) {
        lastCriteria().addCriterion("type_longtext not like", value, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextIn(List<String> values) {
        lastCriteria().addCriterion("type_longtext in", values, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextNotIn(List<String> values) {
        lastCriteria().addCriterion("type_longtext not in", values, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_longtext between", value1, value2, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeLongtextNotBetween(String value1, String value2) {
        lastCriteria().addCriterion("type_longtext not between", value1, value2, "typeLongtext");
        return this;
    }

    public AllDataTypesCriteria andTypeBinaryIsNull() {
        lastCriteria().addCriterion("type_binary is null");
        return this;
    }

    public AllDataTypesCriteria andTypeBinaryIsNotNull() {
        lastCriteria().addCriterion("type_binary is not null");
        return this;
    }

    public AllDataTypesCriteria andTypeVarbinaryIsNull() {
        lastCriteria().addCriterion("type_varbinary is null");
        return this;
    }

    public AllDataTypesCriteria andTypeVarbinaryIsNotNull() {
        lastCriteria().addCriterion("type_varbinary is not null");
        return this;
    }

    public static AllDataTypesCriteria typeBigintIsNull() {
        return new AllDataTypesCriteria().andTypeBigintIsNull();
    }

    public static AllDataTypesCriteria typeBigintIsNotNull() {
        return new AllDataTypesCriteria().andTypeBigintIsNotNull();
    }

    public static AllDataTypesCriteria typeBigintEqualTo(Long value) {
        return new AllDataTypesCriteria().andTypeBigintEqualTo(value);
    }

    public static AllDataTypesCriteria typeBigintNotEqualTo(Long value) {
        return new AllDataTypesCriteria().andTypeBigintNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeBigintGreaterThan(Long value) {
        return new AllDataTypesCriteria().andTypeBigintGreaterThan(value);
    }

    public static AllDataTypesCriteria typeBigintGreaterThanOrEqualTo(Long value) {
        return new AllDataTypesCriteria().andTypeBigintGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeBigintLessThan(Long value) {
        return new AllDataTypesCriteria().andTypeBigintLessThan(value);
    }

    public static AllDataTypesCriteria typeBigintLessThanOrEqualTo(Long value) {
        return new AllDataTypesCriteria().andTypeBigintLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeBigintIn(List<Long> values) {
        return new AllDataTypesCriteria().andTypeBigintIn(values);
    }

    public static AllDataTypesCriteria typeBigintNotIn(List<Long> values) {
        return new AllDataTypesCriteria().andTypeBigintNotIn(values);
    }

    public static AllDataTypesCriteria typeBigintBetween(Long value1, Long value2) {
        return new AllDataTypesCriteria().andTypeBigintBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeBigintNotBetween(Long value1, Long value2) {
        return new AllDataTypesCriteria().andTypeBigintNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeVarcharIsNull() {
        return new AllDataTypesCriteria().andTypeVarcharIsNull();
    }

    public static AllDataTypesCriteria typeVarcharIsNotNull() {
        return new AllDataTypesCriteria().andTypeVarcharIsNotNull();
    }

    public static AllDataTypesCriteria typeVarcharEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeVarcharEqualTo(value);
    }

    public static AllDataTypesCriteria typeVarcharNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeVarcharNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeVarcharGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeVarcharGreaterThan(value);
    }

    public static AllDataTypesCriteria typeVarcharGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeVarcharGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeVarcharLessThan(String value) {
        return new AllDataTypesCriteria().andTypeVarcharLessThan(value);
    }

    public static AllDataTypesCriteria typeVarcharLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeVarcharLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeVarcharLike(String value) {
        return new AllDataTypesCriteria().andTypeVarcharLike(value);
    }

    public static AllDataTypesCriteria typeVarcharNotLike(String value) {
        return new AllDataTypesCriteria().andTypeVarcharNotLike(value);
    }

    public static AllDataTypesCriteria typeVarcharIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeVarcharIn(values);
    }

    public static AllDataTypesCriteria typeVarcharNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeVarcharNotIn(values);
    }

    public static AllDataTypesCriteria typeVarcharBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeVarcharBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeVarcharNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeVarcharNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTinyintIsNull() {
        return new AllDataTypesCriteria().andTypeTinyintIsNull();
    }

    public static AllDataTypesCriteria typeTinyintIsNotNull() {
        return new AllDataTypesCriteria().andTypeTinyintIsNotNull();
    }

    public static AllDataTypesCriteria typeTinyintEqualTo(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinyintNotEqualTo(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinyintGreaterThan(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintGreaterThan(value);
    }

    public static AllDataTypesCriteria typeTinyintGreaterThanOrEqualTo(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinyintLessThan(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintLessThan(value);
    }

    public static AllDataTypesCriteria typeTinyintLessThanOrEqualTo(Byte value) {
        return new AllDataTypesCriteria().andTypeTinyintLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinyintIn(List<Byte> values) {
        return new AllDataTypesCriteria().andTypeTinyintIn(values);
    }

    public static AllDataTypesCriteria typeTinyintNotIn(List<Byte> values) {
        return new AllDataTypesCriteria().andTypeTinyintNotIn(values);
    }

    public static AllDataTypesCriteria typeTinyintBetween(Byte value1, Byte value2) {
        return new AllDataTypesCriteria().andTypeTinyintBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTinyintNotBetween(Byte value1, Byte value2) {
        return new AllDataTypesCriteria().andTypeTinyintNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeSmallintIsNull() {
        return new AllDataTypesCriteria().andTypeSmallintIsNull();
    }

    public static AllDataTypesCriteria typeSmallintIsNotNull() {
        return new AllDataTypesCriteria().andTypeSmallintIsNotNull();
    }

    public static AllDataTypesCriteria typeSmallintEqualTo(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintEqualTo(value);
    }

    public static AllDataTypesCriteria typeSmallintNotEqualTo(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeSmallintGreaterThan(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintGreaterThan(value);
    }

    public static AllDataTypesCriteria typeSmallintGreaterThanOrEqualTo(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeSmallintLessThan(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintLessThan(value);
    }

    public static AllDataTypesCriteria typeSmallintLessThanOrEqualTo(Short value) {
        return new AllDataTypesCriteria().andTypeSmallintLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeSmallintIn(List<Short> values) {
        return new AllDataTypesCriteria().andTypeSmallintIn(values);
    }

    public static AllDataTypesCriteria typeSmallintNotIn(List<Short> values) {
        return new AllDataTypesCriteria().andTypeSmallintNotIn(values);
    }

    public static AllDataTypesCriteria typeSmallintBetween(Short value1, Short value2) {
        return new AllDataTypesCriteria().andTypeSmallintBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeSmallintNotBetween(Short value1, Short value2) {
        return new AllDataTypesCriteria().andTypeSmallintNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeMediumintIsNull() {
        return new AllDataTypesCriteria().andTypeMediumintIsNull();
    }

    public static AllDataTypesCriteria typeMediumintIsNotNull() {
        return new AllDataTypesCriteria().andTypeMediumintIsNotNull();
    }

    public static AllDataTypesCriteria typeMediumintEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumintNotEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumintGreaterThan(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintGreaterThan(value);
    }

    public static AllDataTypesCriteria typeMediumintGreaterThanOrEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumintLessThan(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintLessThan(value);
    }

    public static AllDataTypesCriteria typeMediumintLessThanOrEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeMediumintLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumintIn(List<Integer> values) {
        return new AllDataTypesCriteria().andTypeMediumintIn(values);
    }

    public static AllDataTypesCriteria typeMediumintNotIn(List<Integer> values) {
        return new AllDataTypesCriteria().andTypeMediumintNotIn(values);
    }

    public static AllDataTypesCriteria typeMediumintBetween(Integer value1, Integer value2) {
        return new AllDataTypesCriteria().andTypeMediumintBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeMediumintNotBetween(Integer value1, Integer value2) {
        return new AllDataTypesCriteria().andTypeMediumintNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeIntIsNull() {
        return new AllDataTypesCriteria().andTypeIntIsNull();
    }

    public static AllDataTypesCriteria typeIntIsNotNull() {
        return new AllDataTypesCriteria().andTypeIntIsNotNull();
    }

    public static AllDataTypesCriteria typeIntEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeIntEqualTo(value);
    }

    public static AllDataTypesCriteria typeIntNotEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeIntNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeIntGreaterThan(Integer value) {
        return new AllDataTypesCriteria().andTypeIntGreaterThan(value);
    }

    public static AllDataTypesCriteria typeIntGreaterThanOrEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeIntGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeIntLessThan(Integer value) {
        return new AllDataTypesCriteria().andTypeIntLessThan(value);
    }

    public static AllDataTypesCriteria typeIntLessThanOrEqualTo(Integer value) {
        return new AllDataTypesCriteria().andTypeIntLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeIntIn(List<Integer> values) {
        return new AllDataTypesCriteria().andTypeIntIn(values);
    }

    public static AllDataTypesCriteria typeIntNotIn(List<Integer> values) {
        return new AllDataTypesCriteria().andTypeIntNotIn(values);
    }

    public static AllDataTypesCriteria typeIntBetween(Integer value1, Integer value2) {
        return new AllDataTypesCriteria().andTypeIntBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeIntNotBetween(Integer value1, Integer value2) {
        return new AllDataTypesCriteria().andTypeIntNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDateIsNull() {
        return new AllDataTypesCriteria().andTypeDateIsNull();
    }

    public static AllDataTypesCriteria typeDateIsNotNull() {
        return new AllDataTypesCriteria().andTypeDateIsNotNull();
    }

    public static AllDataTypesCriteria typeDateEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDateEqualTo(value);
    }

    public static AllDataTypesCriteria typeDateNotEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDateNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeDateGreaterThan(Date value) {
        return new AllDataTypesCriteria().andTypeDateGreaterThan(value);
    }

    public static AllDataTypesCriteria typeDateGreaterThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDateGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDateLessThan(Date value) {
        return new AllDataTypesCriteria().andTypeDateLessThan(value);
    }

    public static AllDataTypesCriteria typeDateLessThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDateLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDateIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeDateIn(values);
    }

    public static AllDataTypesCriteria typeDateNotIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeDateNotIn(values);
    }

    public static AllDataTypesCriteria typeDateBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeDateBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDateNotBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeDateNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeFloatIsNull() {
        return new AllDataTypesCriteria().andTypeFloatIsNull();
    }

    public static AllDataTypesCriteria typeFloatIsNotNull() {
        return new AllDataTypesCriteria().andTypeFloatIsNotNull();
    }

    public static AllDataTypesCriteria typeFloatEqualTo(Float value) {
        return new AllDataTypesCriteria().andTypeFloatEqualTo(value);
    }

    public static AllDataTypesCriteria typeFloatNotEqualTo(Float value) {
        return new AllDataTypesCriteria().andTypeFloatNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeFloatGreaterThan(Float value) {
        return new AllDataTypesCriteria().andTypeFloatGreaterThan(value);
    }

    public static AllDataTypesCriteria typeFloatGreaterThanOrEqualTo(Float value) {
        return new AllDataTypesCriteria().andTypeFloatGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeFloatLessThan(Float value) {
        return new AllDataTypesCriteria().andTypeFloatLessThan(value);
    }

    public static AllDataTypesCriteria typeFloatLessThanOrEqualTo(Float value) {
        return new AllDataTypesCriteria().andTypeFloatLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeFloatIn(List<Float> values) {
        return new AllDataTypesCriteria().andTypeFloatIn(values);
    }

    public static AllDataTypesCriteria typeFloatNotIn(List<Float> values) {
        return new AllDataTypesCriteria().andTypeFloatNotIn(values);
    }

    public static AllDataTypesCriteria typeFloatBetween(Float value1, Float value2) {
        return new AllDataTypesCriteria().andTypeFloatBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeFloatNotBetween(Float value1, Float value2) {
        return new AllDataTypesCriteria().andTypeFloatNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDoubleIsNull() {
        return new AllDataTypesCriteria().andTypeDoubleIsNull();
    }

    public static AllDataTypesCriteria typeDoubleIsNotNull() {
        return new AllDataTypesCriteria().andTypeDoubleIsNotNull();
    }

    public static AllDataTypesCriteria typeDoubleEqualTo(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleEqualTo(value);
    }

    public static AllDataTypesCriteria typeDoubleNotEqualTo(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeDoubleGreaterThan(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleGreaterThan(value);
    }

    public static AllDataTypesCriteria typeDoubleGreaterThanOrEqualTo(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDoubleLessThan(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleLessThan(value);
    }

    public static AllDataTypesCriteria typeDoubleLessThanOrEqualTo(Double value) {
        return new AllDataTypesCriteria().andTypeDoubleLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDoubleIn(List<Double> values) {
        return new AllDataTypesCriteria().andTypeDoubleIn(values);
    }

    public static AllDataTypesCriteria typeDoubleNotIn(List<Double> values) {
        return new AllDataTypesCriteria().andTypeDoubleNotIn(values);
    }

    public static AllDataTypesCriteria typeDoubleBetween(Double value1, Double value2) {
        return new AllDataTypesCriteria().andTypeDoubleBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDoubleNotBetween(Double value1, Double value2) {
        return new AllDataTypesCriteria().andTypeDoubleNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDecimalIsNull() {
        return new AllDataTypesCriteria().andTypeDecimalIsNull();
    }

    public static AllDataTypesCriteria typeDecimalIsNotNull() {
        return new AllDataTypesCriteria().andTypeDecimalIsNotNull();
    }

    public static AllDataTypesCriteria typeDecimalEqualTo(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalEqualTo(value);
    }

    public static AllDataTypesCriteria typeDecimalNotEqualTo(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeDecimalGreaterThan(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalGreaterThan(value);
    }

    public static AllDataTypesCriteria typeDecimalGreaterThanOrEqualTo(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDecimalLessThan(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalLessThan(value);
    }

    public static AllDataTypesCriteria typeDecimalLessThanOrEqualTo(BigDecimal value) {
        return new AllDataTypesCriteria().andTypeDecimalLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDecimalIn(List<BigDecimal> values) {
        return new AllDataTypesCriteria().andTypeDecimalIn(values);
    }

    public static AllDataTypesCriteria typeDecimalNotIn(List<BigDecimal> values) {
        return new AllDataTypesCriteria().andTypeDecimalNotIn(values);
    }

    public static AllDataTypesCriteria typeDecimalBetween(BigDecimal value1, BigDecimal value2) {
        return new AllDataTypesCriteria().andTypeDecimalBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
        return new AllDataTypesCriteria().andTypeDecimalNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDatetimeIsNull() {
        return new AllDataTypesCriteria().andTypeDatetimeIsNull();
    }

    public static AllDataTypesCriteria typeDatetimeIsNotNull() {
        return new AllDataTypesCriteria().andTypeDatetimeIsNotNull();
    }

    public static AllDataTypesCriteria typeDatetimeEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeEqualTo(value);
    }

    public static AllDataTypesCriteria typeDatetimeNotEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeDatetimeGreaterThan(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeGreaterThan(value);
    }

    public static AllDataTypesCriteria typeDatetimeGreaterThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDatetimeLessThan(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeLessThan(value);
    }

    public static AllDataTypesCriteria typeDatetimeLessThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeDatetimeLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeDatetimeIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeDatetimeIn(values);
    }

    public static AllDataTypesCriteria typeDatetimeNotIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeDatetimeNotIn(values);
    }

    public static AllDataTypesCriteria typeDatetimeBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeDatetimeBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeDatetimeNotBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeDatetimeNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTimestampIsNull() {
        return new AllDataTypesCriteria().andTypeTimestampIsNull();
    }

    public static AllDataTypesCriteria typeTimestampIsNotNull() {
        return new AllDataTypesCriteria().andTypeTimestampIsNotNull();
    }

    public static AllDataTypesCriteria typeTimestampEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimestampNotEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimestampGreaterThan(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampGreaterThan(value);
    }

    public static AllDataTypesCriteria typeTimestampGreaterThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimestampLessThan(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampLessThan(value);
    }

    public static AllDataTypesCriteria typeTimestampLessThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimestampLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimestampIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeTimestampIn(values);
    }

    public static AllDataTypesCriteria typeTimestampNotIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeTimestampNotIn(values);
    }

    public static AllDataTypesCriteria typeTimestampBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeTimestampBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTimestampNotBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeTimestampNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTimeIsNull() {
        return new AllDataTypesCriteria().andTypeTimeIsNull();
    }

    public static AllDataTypesCriteria typeTimeIsNotNull() {
        return new AllDataTypesCriteria().andTypeTimeIsNotNull();
    }

    public static AllDataTypesCriteria typeTimeEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimeEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimeNotEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimeNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimeGreaterThan(Date value) {
        return new AllDataTypesCriteria().andTypeTimeGreaterThan(value);
    }

    public static AllDataTypesCriteria typeTimeGreaterThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimeGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimeLessThan(Date value) {
        return new AllDataTypesCriteria().andTypeTimeLessThan(value);
    }

    public static AllDataTypesCriteria typeTimeLessThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeTimeLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTimeIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeTimeIn(values);
    }

    public static AllDataTypesCriteria typeTimeNotIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeTimeNotIn(values);
    }

    public static AllDataTypesCriteria typeTimeBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeTimeBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTimeNotBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeTimeNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeYearIsNull() {
        return new AllDataTypesCriteria().andTypeYearIsNull();
    }

    public static AllDataTypesCriteria typeYearIsNotNull() {
        return new AllDataTypesCriteria().andTypeYearIsNotNull();
    }

    public static AllDataTypesCriteria typeYearEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeYearEqualTo(value);
    }

    public static AllDataTypesCriteria typeYearNotEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeYearNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeYearGreaterThan(Date value) {
        return new AllDataTypesCriteria().andTypeYearGreaterThan(value);
    }

    public static AllDataTypesCriteria typeYearGreaterThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeYearGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeYearLessThan(Date value) {
        return new AllDataTypesCriteria().andTypeYearLessThan(value);
    }

    public static AllDataTypesCriteria typeYearLessThanOrEqualTo(Date value) {
        return new AllDataTypesCriteria().andTypeYearLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeYearIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeYearIn(values);
    }

    public static AllDataTypesCriteria typeYearNotIn(List<Date> values) {
        return new AllDataTypesCriteria().andTypeYearNotIn(values);
    }

    public static AllDataTypesCriteria typeYearBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeYearBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeYearNotBetween(Date value1, Date value2) {
        return new AllDataTypesCriteria().andTypeYearNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeCharIsNull() {
        return new AllDataTypesCriteria().andTypeCharIsNull();
    }

    public static AllDataTypesCriteria typeCharIsNotNull() {
        return new AllDataTypesCriteria().andTypeCharIsNotNull();
    }

    public static AllDataTypesCriteria typeCharEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeCharEqualTo(value);
    }

    public static AllDataTypesCriteria typeCharNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeCharNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeCharGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeCharGreaterThan(value);
    }

    public static AllDataTypesCriteria typeCharGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeCharGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeCharLessThan(String value) {
        return new AllDataTypesCriteria().andTypeCharLessThan(value);
    }

    public static AllDataTypesCriteria typeCharLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeCharLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeCharLike(String value) {
        return new AllDataTypesCriteria().andTypeCharLike(value);
    }

    public static AllDataTypesCriteria typeCharNotLike(String value) {
        return new AllDataTypesCriteria().andTypeCharNotLike(value);
    }

    public static AllDataTypesCriteria typeCharIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeCharIn(values);
    }

    public static AllDataTypesCriteria typeCharNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeCharNotIn(values);
    }

    public static AllDataTypesCriteria typeCharBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeCharBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeCharNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeCharNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTinytextIsNull() {
        return new AllDataTypesCriteria().andTypeTinytextIsNull();
    }

    public static AllDataTypesCriteria typeTinytextIsNotNull() {
        return new AllDataTypesCriteria().andTypeTinytextIsNotNull();
    }

    public static AllDataTypesCriteria typeTinytextEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTinytextEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinytextNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTinytextNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinytextGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeTinytextGreaterThan(value);
    }

    public static AllDataTypesCriteria typeTinytextGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTinytextGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinytextLessThan(String value) {
        return new AllDataTypesCriteria().andTypeTinytextLessThan(value);
    }

    public static AllDataTypesCriteria typeTinytextLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTinytextLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTinytextLike(String value) {
        return new AllDataTypesCriteria().andTypeTinytextLike(value);
    }

    public static AllDataTypesCriteria typeTinytextNotLike(String value) {
        return new AllDataTypesCriteria().andTypeTinytextNotLike(value);
    }

    public static AllDataTypesCriteria typeTinytextIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeTinytextIn(values);
    }

    public static AllDataTypesCriteria typeTinytextNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeTinytextNotIn(values);
    }

    public static AllDataTypesCriteria typeTinytextBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeTinytextBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTinytextNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeTinytextNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeEnumIsNull() {
        return new AllDataTypesCriteria().andTypeEnumIsNull();
    }

    public static AllDataTypesCriteria typeEnumIsNotNull() {
        return new AllDataTypesCriteria().andTypeEnumIsNotNull();
    }

    public static AllDataTypesCriteria typeEnumEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeEnumEqualTo(value);
    }

    public static AllDataTypesCriteria typeEnumNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeEnumNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeEnumGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeEnumGreaterThan(value);
    }

    public static AllDataTypesCriteria typeEnumGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeEnumGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeEnumLessThan(String value) {
        return new AllDataTypesCriteria().andTypeEnumLessThan(value);
    }

    public static AllDataTypesCriteria typeEnumLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeEnumLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeEnumLike(String value) {
        return new AllDataTypesCriteria().andTypeEnumLike(value);
    }

    public static AllDataTypesCriteria typeEnumNotLike(String value) {
        return new AllDataTypesCriteria().andTypeEnumNotLike(value);
    }

    public static AllDataTypesCriteria typeEnumIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeEnumIn(values);
    }

    public static AllDataTypesCriteria typeEnumNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeEnumNotIn(values);
    }

    public static AllDataTypesCriteria typeEnumBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeEnumBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeEnumNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeEnumNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeSetIsNull() {
        return new AllDataTypesCriteria().andTypeSetIsNull();
    }

    public static AllDataTypesCriteria typeSetIsNotNull() {
        return new AllDataTypesCriteria().andTypeSetIsNotNull();
    }

    public static AllDataTypesCriteria typeSetEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeSetEqualTo(value);
    }

    public static AllDataTypesCriteria typeSetNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeSetNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeSetGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeSetGreaterThan(value);
    }

    public static AllDataTypesCriteria typeSetGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeSetGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeSetLessThan(String value) {
        return new AllDataTypesCriteria().andTypeSetLessThan(value);
    }

    public static AllDataTypesCriteria typeSetLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeSetLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeSetLike(String value) {
        return new AllDataTypesCriteria().andTypeSetLike(value);
    }

    public static AllDataTypesCriteria typeSetNotLike(String value) {
        return new AllDataTypesCriteria().andTypeSetNotLike(value);
    }

    public static AllDataTypesCriteria typeSetIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeSetIn(values);
    }

    public static AllDataTypesCriteria typeSetNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeSetNotIn(values);
    }

    public static AllDataTypesCriteria typeSetBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeSetBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeSetNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeSetNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeBoolIsNull() {
        return new AllDataTypesCriteria().andTypeBoolIsNull();
    }

    public static AllDataTypesCriteria typeBoolIsNotNull() {
        return new AllDataTypesCriteria().andTypeBoolIsNotNull();
    }

    public static AllDataTypesCriteria typeBoolEqualTo(Boolean value) {
        return new AllDataTypesCriteria().andTypeBoolEqualTo(value);
    }

    public static AllDataTypesCriteria typeTextIsNull() {
        return new AllDataTypesCriteria().andTypeTextIsNull();
    }

    public static AllDataTypesCriteria typeTextIsNotNull() {
        return new AllDataTypesCriteria().andTypeTextIsNotNull();
    }

    public static AllDataTypesCriteria typeTextEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTextEqualTo(value);
    }

    public static AllDataTypesCriteria typeTextNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTextNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeTextGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeTextGreaterThan(value);
    }

    public static AllDataTypesCriteria typeTextGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTextGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTextLessThan(String value) {
        return new AllDataTypesCriteria().andTypeTextLessThan(value);
    }

    public static AllDataTypesCriteria typeTextLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeTextLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeTextLike(String value) {
        return new AllDataTypesCriteria().andTypeTextLike(value);
    }

    public static AllDataTypesCriteria typeTextNotLike(String value) {
        return new AllDataTypesCriteria().andTypeTextNotLike(value);
    }

    public static AllDataTypesCriteria typeTextIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeTextIn(values);
    }

    public static AllDataTypesCriteria typeTextNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeTextNotIn(values);
    }

    public static AllDataTypesCriteria typeTextBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeTextBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTextNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeTextNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeTinyblobIsNull() {
        return new AllDataTypesCriteria().andTypeTinyblobIsNull();
    }

    public static AllDataTypesCriteria typeTinyblobIsNotNull() {
        return new AllDataTypesCriteria().andTypeTinyblobIsNotNull();
    }

    public static AllDataTypesCriteria typeBlobIsNull() {
        return new AllDataTypesCriteria().andTypeBlobIsNull();
    }

    public static AllDataTypesCriteria typeBlobIsNotNull() {
        return new AllDataTypesCriteria().andTypeBlobIsNotNull();
    }

    public static AllDataTypesCriteria typeMediumblobIsNull() {
        return new AllDataTypesCriteria().andTypeMediumblobIsNull();
    }

    public static AllDataTypesCriteria typeMediumblobIsNotNull() {
        return new AllDataTypesCriteria().andTypeMediumblobIsNotNull();
    }

    public static AllDataTypesCriteria typeMediumtextIsNull() {
        return new AllDataTypesCriteria().andTypeMediumtextIsNull();
    }

    public static AllDataTypesCriteria typeMediumtextIsNotNull() {
        return new AllDataTypesCriteria().andTypeMediumtextIsNotNull();
    }

    public static AllDataTypesCriteria typeMediumtextEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumtextNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumtextGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextGreaterThan(value);
    }

    public static AllDataTypesCriteria typeMediumtextGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumtextLessThan(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextLessThan(value);
    }

    public static AllDataTypesCriteria typeMediumtextLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeMediumtextLike(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextLike(value);
    }

    public static AllDataTypesCriteria typeMediumtextNotLike(String value) {
        return new AllDataTypesCriteria().andTypeMediumtextNotLike(value);
    }

    public static AllDataTypesCriteria typeMediumtextIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeMediumtextIn(values);
    }

    public static AllDataTypesCriteria typeMediumtextNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeMediumtextNotIn(values);
    }

    public static AllDataTypesCriteria typeMediumtextBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeMediumtextBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeMediumtextNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeMediumtextNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeLongblobIsNull() {
        return new AllDataTypesCriteria().andTypeLongblobIsNull();
    }

    public static AllDataTypesCriteria typeLongblobIsNotNull() {
        return new AllDataTypesCriteria().andTypeLongblobIsNotNull();
    }

    public static AllDataTypesCriteria typeLongtextIsNull() {
        return new AllDataTypesCriteria().andTypeLongtextIsNull();
    }

    public static AllDataTypesCriteria typeLongtextIsNotNull() {
        return new AllDataTypesCriteria().andTypeLongtextIsNotNull();
    }

    public static AllDataTypesCriteria typeLongtextEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeLongtextEqualTo(value);
    }

    public static AllDataTypesCriteria typeLongtextNotEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeLongtextNotEqualTo(value);
    }

    public static AllDataTypesCriteria typeLongtextGreaterThan(String value) {
        return new AllDataTypesCriteria().andTypeLongtextGreaterThan(value);
    }

    public static AllDataTypesCriteria typeLongtextGreaterThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeLongtextGreaterThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeLongtextLessThan(String value) {
        return new AllDataTypesCriteria().andTypeLongtextLessThan(value);
    }

    public static AllDataTypesCriteria typeLongtextLessThanOrEqualTo(String value) {
        return new AllDataTypesCriteria().andTypeLongtextLessThanOrEqualTo(value);
    }

    public static AllDataTypesCriteria typeLongtextLike(String value) {
        return new AllDataTypesCriteria().andTypeLongtextLike(value);
    }

    public static AllDataTypesCriteria typeLongtextNotLike(String value) {
        return new AllDataTypesCriteria().andTypeLongtextNotLike(value);
    }

    public static AllDataTypesCriteria typeLongtextIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeLongtextIn(values);
    }

    public static AllDataTypesCriteria typeLongtextNotIn(List<String> values) {
        return new AllDataTypesCriteria().andTypeLongtextNotIn(values);
    }

    public static AllDataTypesCriteria typeLongtextBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeLongtextBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeLongtextNotBetween(String value1, String value2) {
        return new AllDataTypesCriteria().andTypeLongtextNotBetween(value1, value2);
    }

    public static AllDataTypesCriteria typeBinaryIsNull() {
        return new AllDataTypesCriteria().andTypeBinaryIsNull();
    }

    public static AllDataTypesCriteria typeBinaryIsNotNull() {
        return new AllDataTypesCriteria().andTypeBinaryIsNotNull();
    }

    public static AllDataTypesCriteria typeVarbinaryIsNull() {
        return new AllDataTypesCriteria().andTypeVarbinaryIsNull();
    }

    public static AllDataTypesCriteria typeVarbinaryIsNotNull() {
        return new AllDataTypesCriteria().andTypeVarbinaryIsNotNull();
    }
}