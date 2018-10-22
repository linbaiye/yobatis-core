package func.autoinckey.model.criteria;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Do not modify, it will be overwritten every time yobatis runs.
 */
public final class CustomerCriteria extends BaseCriteria {
    private static final Map<String, String> PROPERTY_TO_COLUMN;

    static {
        PROPERTY_TO_COLUMN = new HashMap<>();
        PROPERTY_TO_COLUMN.put("id", "id");
        PROPERTY_TO_COLUMN.put("f1", "f1");
        PROPERTY_TO_COLUMN.put("f3", "f3");
        PROPERTY_TO_COLUMN.put("f2", "f2");
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

    public CustomerCriteria ascOrderBy(String  ... fields) {
        orderBy("asc", fields);
        return this;
    }

    public CustomerCriteria descOrderBy(String  ... fields) {
        orderBy("desc", fields);
        return this;
    }

    public CustomerCriteria or() {
        oredCriteria.add(createCriteriaInternal());
        return this;
    }

    public CustomerCriteria setLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public CustomerCriteria setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public CustomerCriteria setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
        return this;
    }

    public CustomerCriteria andIdIsNull() {
        lastCriteria().addCriterion("id is null");
        return this;
    }

    public CustomerCriteria andIdIsNotNull() {
        lastCriteria().addCriterion("id is not null");
        return this;
    }

    public CustomerCriteria andIdEqualTo(Long value) {
        lastCriteria().addCriterion("id =", value, "id");
        return this;
    }

    public CustomerCriteria andIdNotEqualTo(Long value) {
        lastCriteria().addCriterion("id <>", value, "id");
        return this;
    }

    public CustomerCriteria andIdGreaterThan(Long value) {
        lastCriteria().addCriterion("id >", value, "id");
        return this;
    }

    public CustomerCriteria andIdGreaterThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("id >=", value, "id");
        return this;
    }

    public CustomerCriteria andIdLessThan(Long value) {
        lastCriteria().addCriterion("id <", value, "id");
        return this;
    }

    public CustomerCriteria andIdLessThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("id <=", value, "id");
        return this;
    }

    public CustomerCriteria andIdIn(List<Long> values) {
        lastCriteria().addCriterion("id in", values, "id");
        return this;
    }

    public CustomerCriteria andIdNotIn(List<Long> values) {
        lastCriteria().addCriterion("id not in", values, "id");
        return this;
    }

    public CustomerCriteria andIdBetween(Long value1, Long value2) {
        lastCriteria().addCriterion("id between", value1, value2, "id");
        return this;
    }

    public CustomerCriteria andIdNotBetween(Long value1, Long value2) {
        lastCriteria().addCriterion("id not between", value1, value2, "id");
        return this;
    }

    public CustomerCriteria andF1IsNull() {
        lastCriteria().addCriterion("f1 is null");
        return this;
    }

    public CustomerCriteria andF1IsNotNull() {
        lastCriteria().addCriterion("f1 is not null");
        return this;
    }

    public CustomerCriteria andF1EqualTo(String value) {
        lastCriteria().addCriterion("f1 =", value, "f1");
        return this;
    }

    public CustomerCriteria andF1NotEqualTo(String value) {
        lastCriteria().addCriterion("f1 <>", value, "f1");
        return this;
    }

    public CustomerCriteria andF1GreaterThan(String value) {
        lastCriteria().addCriterion("f1 >", value, "f1");
        return this;
    }

    public CustomerCriteria andF1GreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f1 >=", value, "f1");
        return this;
    }

    public CustomerCriteria andF1LessThan(String value) {
        lastCriteria().addCriterion("f1 <", value, "f1");
        return this;
    }

    public CustomerCriteria andF1LessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f1 <=", value, "f1");
        return this;
    }

    public CustomerCriteria andF1Like(String value) {
        lastCriteria().addCriterion("f1 like", value, "f1");
        return this;
    }

    public CustomerCriteria andF1NotLike(String value) {
        lastCriteria().addCriterion("f1 not like", value, "f1");
        return this;
    }

    public CustomerCriteria andF1In(List<String> values) {
        lastCriteria().addCriterion("f1 in", values, "f1");
        return this;
    }

    public CustomerCriteria andF1NotIn(List<String> values) {
        lastCriteria().addCriterion("f1 not in", values, "f1");
        return this;
    }

    public CustomerCriteria andF1Between(String value1, String value2) {
        lastCriteria().addCriterion("f1 between", value1, value2, "f1");
        return this;
    }

    public CustomerCriteria andF1NotBetween(String value1, String value2) {
        lastCriteria().addCriterion("f1 not between", value1, value2, "f1");
        return this;
    }

    public CustomerCriteria andF3IsNull() {
        lastCriteria().addCriterion("f3 is null");
        return this;
    }

    public CustomerCriteria andF3IsNotNull() {
        lastCriteria().addCriterion("f3 is not null");
        return this;
    }

    public CustomerCriteria andF3EqualTo(BigDecimal value) {
        lastCriteria().addCriterion("f3 =", value, "f3");
        return this;
    }

    public CustomerCriteria andF3NotEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("f3 <>", value, "f3");
        return this;
    }

    public CustomerCriteria andF3GreaterThan(BigDecimal value) {
        lastCriteria().addCriterion("f3 >", value, "f3");
        return this;
    }

    public CustomerCriteria andF3GreaterThanOrEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("f3 >=", value, "f3");
        return this;
    }

    public CustomerCriteria andF3LessThan(BigDecimal value) {
        lastCriteria().addCriterion("f3 <", value, "f3");
        return this;
    }

    public CustomerCriteria andF3LessThanOrEqualTo(BigDecimal value) {
        lastCriteria().addCriterion("f3 <=", value, "f3");
        return this;
    }

    public CustomerCriteria andF3In(List<BigDecimal> values) {
        lastCriteria().addCriterion("f3 in", values, "f3");
        return this;
    }

    public CustomerCriteria andF3NotIn(List<BigDecimal> values) {
        lastCriteria().addCriterion("f3 not in", values, "f3");
        return this;
    }

    public CustomerCriteria andF3Between(BigDecimal value1, BigDecimal value2) {
        lastCriteria().addCriterion("f3 between", value1, value2, "f3");
        return this;
    }

    public CustomerCriteria andF3NotBetween(BigDecimal value1, BigDecimal value2) {
        lastCriteria().addCriterion("f3 not between", value1, value2, "f3");
        return this;
    }

    public CustomerCriteria andF2IsNull() {
        lastCriteria().addCriterion("f2 is null");
        return this;
    }

    public CustomerCriteria andF2IsNotNull() {
        lastCriteria().addCriterion("f2 is not null");
        return this;
    }

    public CustomerCriteria andF2EqualTo(String value) {
        lastCriteria().addCriterion("f2 =", value, "f2");
        return this;
    }

    public CustomerCriteria andF2NotEqualTo(String value) {
        lastCriteria().addCriterion("f2 <>", value, "f2");
        return this;
    }

    public CustomerCriteria andF2GreaterThan(String value) {
        lastCriteria().addCriterion("f2 >", value, "f2");
        return this;
    }

    public CustomerCriteria andF2GreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f2 >=", value, "f2");
        return this;
    }

    public CustomerCriteria andF2LessThan(String value) {
        lastCriteria().addCriterion("f2 <", value, "f2");
        return this;
    }

    public CustomerCriteria andF2LessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f2 <=", value, "f2");
        return this;
    }

    public CustomerCriteria andF2Like(String value) {
        lastCriteria().addCriterion("f2 like", value, "f2");
        return this;
    }

    public CustomerCriteria andF2NotLike(String value) {
        lastCriteria().addCriterion("f2 not like", value, "f2");
        return this;
    }

    public CustomerCriteria andF2In(List<String> values) {
        lastCriteria().addCriterion("f2 in", values, "f2");
        return this;
    }

    public CustomerCriteria andF2NotIn(List<String> values) {
        lastCriteria().addCriterion("f2 not in", values, "f2");
        return this;
    }

    public CustomerCriteria andF2Between(String value1, String value2) {
        lastCriteria().addCriterion("f2 between", value1, value2, "f2");
        return this;
    }

    public CustomerCriteria andF2NotBetween(String value1, String value2) {
        lastCriteria().addCriterion("f2 not between", value1, value2, "f2");
        return this;
    }

    public static CustomerCriteria idIsNull() {
        return new CustomerCriteria().andIdIsNull();
    }

    public static CustomerCriteria idIsNotNull() {
        return new CustomerCriteria().andIdIsNotNull();
    }

    public static CustomerCriteria idEqualTo(Long value) {
        return new CustomerCriteria().andIdEqualTo(value);
    }

    public static CustomerCriteria idNotEqualTo(Long value) {
        return new CustomerCriteria().andIdNotEqualTo(value);
    }

    public static CustomerCriteria idGreaterThan(Long value) {
        return new CustomerCriteria().andIdGreaterThan(value);
    }

    public static CustomerCriteria idGreaterThanOrEqualTo(Long value) {
        return new CustomerCriteria().andIdGreaterThanOrEqualTo(value);
    }

    public static CustomerCriteria idLessThan(Long value) {
        return new CustomerCriteria().andIdLessThan(value);
    }

    public static CustomerCriteria idLessThanOrEqualTo(Long value) {
        return new CustomerCriteria().andIdLessThanOrEqualTo(value);
    }

    public static CustomerCriteria idIn(List<Long> values) {
        return new CustomerCriteria().andIdIn(values);
    }

    public static CustomerCriteria idNotIn(List<Long> values) {
        return new CustomerCriteria().andIdNotIn(values);
    }

    public static CustomerCriteria idBetween(Long value1, Long value2) {
        return new CustomerCriteria().andIdBetween(value1, value2);
    }

    public static CustomerCriteria idNotBetween(Long value1, Long value2) {
        return new CustomerCriteria().andIdNotBetween(value1, value2);
    }

    public static CustomerCriteria f1IsNull() {
        return new CustomerCriteria().andF1IsNull();
    }

    public static CustomerCriteria f1IsNotNull() {
        return new CustomerCriteria().andF1IsNotNull();
    }

    public static CustomerCriteria f1EqualTo(String value) {
        return new CustomerCriteria().andF1EqualTo(value);
    }

    public static CustomerCriteria f1NotEqualTo(String value) {
        return new CustomerCriteria().andF1NotEqualTo(value);
    }

    public static CustomerCriteria f1GreaterThan(String value) {
        return new CustomerCriteria().andF1GreaterThan(value);
    }

    public static CustomerCriteria f1GreaterThanOrEqualTo(String value) {
        return new CustomerCriteria().andF1GreaterThanOrEqualTo(value);
    }

    public static CustomerCriteria f1LessThan(String value) {
        return new CustomerCriteria().andF1LessThan(value);
    }

    public static CustomerCriteria f1LessThanOrEqualTo(String value) {
        return new CustomerCriteria().andF1LessThanOrEqualTo(value);
    }

    public static CustomerCriteria f1Like(String value) {
        return new CustomerCriteria().andF1Like(value);
    }

    public static CustomerCriteria f1NotLike(String value) {
        return new CustomerCriteria().andF1NotLike(value);
    }

    public static CustomerCriteria f1In(List<String> values) {
        return new CustomerCriteria().andF1In(values);
    }

    public static CustomerCriteria f1NotIn(List<String> values) {
        return new CustomerCriteria().andF1NotIn(values);
    }

    public static CustomerCriteria f1Between(String value1, String value2) {
        return new CustomerCriteria().andF1Between(value1, value2);
    }

    public static CustomerCriteria f1NotBetween(String value1, String value2) {
        return new CustomerCriteria().andF1NotBetween(value1, value2);
    }

    public static CustomerCriteria f3IsNull() {
        return new CustomerCriteria().andF3IsNull();
    }

    public static CustomerCriteria f3IsNotNull() {
        return new CustomerCriteria().andF3IsNotNull();
    }

    public static CustomerCriteria f3EqualTo(BigDecimal value) {
        return new CustomerCriteria().andF3EqualTo(value);
    }

    public static CustomerCriteria f3NotEqualTo(BigDecimal value) {
        return new CustomerCriteria().andF3NotEqualTo(value);
    }

    public static CustomerCriteria f3GreaterThan(BigDecimal value) {
        return new CustomerCriteria().andF3GreaterThan(value);
    }

    public static CustomerCriteria f3GreaterThanOrEqualTo(BigDecimal value) {
        return new CustomerCriteria().andF3GreaterThanOrEqualTo(value);
    }

    public static CustomerCriteria f3LessThan(BigDecimal value) {
        return new CustomerCriteria().andF3LessThan(value);
    }

    public static CustomerCriteria f3LessThanOrEqualTo(BigDecimal value) {
        return new CustomerCriteria().andF3LessThanOrEqualTo(value);
    }

    public static CustomerCriteria f3In(List<BigDecimal> values) {
        return new CustomerCriteria().andF3In(values);
    }

    public static CustomerCriteria f3NotIn(List<BigDecimal> values) {
        return new CustomerCriteria().andF3NotIn(values);
    }

    public static CustomerCriteria f3Between(BigDecimal value1, BigDecimal value2) {
        return new CustomerCriteria().andF3Between(value1, value2);
    }

    public static CustomerCriteria f3NotBetween(BigDecimal value1, BigDecimal value2) {
        return new CustomerCriteria().andF3NotBetween(value1, value2);
    }

    public static CustomerCriteria f2IsNull() {
        return new CustomerCriteria().andF2IsNull();
    }

    public static CustomerCriteria f2IsNotNull() {
        return new CustomerCriteria().andF2IsNotNull();
    }

    public static CustomerCriteria f2EqualTo(String value) {
        return new CustomerCriteria().andF2EqualTo(value);
    }

    public static CustomerCriteria f2NotEqualTo(String value) {
        return new CustomerCriteria().andF2NotEqualTo(value);
    }

    public static CustomerCriteria f2GreaterThan(String value) {
        return new CustomerCriteria().andF2GreaterThan(value);
    }

    public static CustomerCriteria f2GreaterThanOrEqualTo(String value) {
        return new CustomerCriteria().andF2GreaterThanOrEqualTo(value);
    }

    public static CustomerCriteria f2LessThan(String value) {
        return new CustomerCriteria().andF2LessThan(value);
    }

    public static CustomerCriteria f2LessThanOrEqualTo(String value) {
        return new CustomerCriteria().andF2LessThanOrEqualTo(value);
    }

    public static CustomerCriteria f2Like(String value) {
        return new CustomerCriteria().andF2Like(value);
    }

    public static CustomerCriteria f2NotLike(String value) {
        return new CustomerCriteria().andF2NotLike(value);
    }

    public static CustomerCriteria f2In(List<String> values) {
        return new CustomerCriteria().andF2In(values);
    }

    public static CustomerCriteria f2NotIn(List<String> values) {
        return new CustomerCriteria().andF2NotIn(values);
    }

    public static CustomerCriteria f2Between(String value1, String value2) {
        return new CustomerCriteria().andF2Between(value1, value2);
    }

    public static CustomerCriteria f2NotBetween(String value1, String value2) {
        return new CustomerCriteria().andF2NotBetween(value1, value2);
    }
}