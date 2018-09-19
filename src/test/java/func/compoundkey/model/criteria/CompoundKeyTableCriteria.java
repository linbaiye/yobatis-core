package func.compoundkey.model.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CompoundKeyTableCriteria extends BaseCriteria {
    private static final Map<String, String> PROPERTY_TO_COLUMN;

    static {
        PROPERTY_TO_COLUMN = new HashMap<>();
        PROPERTY_TO_COLUMN.put("pk1", "pk1");
        PROPERTY_TO_COLUMN.put("pk2", "pk2");
        PROPERTY_TO_COLUMN.put("f3", "f3");
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

    public CompoundKeyTableCriteria setLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public CompoundKeyTableCriteria setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public CompoundKeyTableCriteria setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
        return this;
    }

    public CompoundKeyTableCriteria ascOrderBy(String  ... fields) {
        orderBy("asc", fields);
        return this;
    }

    public CompoundKeyTableCriteria descOrderBy(String  ... fields) {
        orderBy("desc", fields);
        return this;
    }

    public CompoundKeyTableCriteria or() {
        oredCriteria.add(createCriteriaInternal());
        return this;
    }

    public CompoundKeyTableCriteria andPk1IsNull() {
        lastCriteria().addCriterion("pk1 is null");
        return this;
    }

    public CompoundKeyTableCriteria andPk1IsNotNull() {
        lastCriteria().addCriterion("pk1 is not null");
        return this;
    }

    public CompoundKeyTableCriteria andPk1EqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 =", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1NotEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 <>", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1GreaterThan(Integer value) {
        lastCriteria().addCriterion("pk1 >", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1GreaterThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 >=", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1LessThan(Integer value) {
        lastCriteria().addCriterion("pk1 <", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1LessThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 <=", value, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1In(List<Integer> values) {
        lastCriteria().addCriterion("pk1 in", values, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1NotIn(List<Integer> values) {
        lastCriteria().addCriterion("pk1 not in", values, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1Between(Integer value1, Integer value2) {
        lastCriteria().addCriterion("pk1 between", value1, value2, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk1NotBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("pk1 not between", value1, value2, "pk1");
        return this;
    }

    public CompoundKeyTableCriteria andPk2IsNull() {
        lastCriteria().addCriterion("pk2 is null");
        return this;
    }

    public CompoundKeyTableCriteria andPk2IsNotNull() {
        lastCriteria().addCriterion("pk2 is not null");
        return this;
    }

    public CompoundKeyTableCriteria andPk2EqualTo(String value) {
        lastCriteria().addCriterion("pk2 =", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2NotEqualTo(String value) {
        lastCriteria().addCriterion("pk2 <>", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2GreaterThan(String value) {
        lastCriteria().addCriterion("pk2 >", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2GreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("pk2 >=", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2LessThan(String value) {
        lastCriteria().addCriterion("pk2 <", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2LessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("pk2 <=", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2Like(String value) {
        lastCriteria().addCriterion("pk2 like", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2NotLike(String value) {
        lastCriteria().addCriterion("pk2 not like", value, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2In(List<String> values) {
        lastCriteria().addCriterion("pk2 in", values, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2NotIn(List<String> values) {
        lastCriteria().addCriterion("pk2 not in", values, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2Between(String value1, String value2) {
        lastCriteria().addCriterion("pk2 between", value1, value2, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andPk2NotBetween(String value1, String value2) {
        lastCriteria().addCriterion("pk2 not between", value1, value2, "pk2");
        return this;
    }

    public CompoundKeyTableCriteria andF3IsNull() {
        lastCriteria().addCriterion("f3 is null");
        return this;
    }

    public CompoundKeyTableCriteria andF3IsNotNull() {
        lastCriteria().addCriterion("f3 is not null");
        return this;
    }

    public CompoundKeyTableCriteria andF3EqualTo(String value) {
        lastCriteria().addCriterion("f3 =", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3NotEqualTo(String value) {
        lastCriteria().addCriterion("f3 <>", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3GreaterThan(String value) {
        lastCriteria().addCriterion("f3 >", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3GreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f3 >=", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3LessThan(String value) {
        lastCriteria().addCriterion("f3 <", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3LessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("f3 <=", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3Like(String value) {
        lastCriteria().addCriterion("f3 like", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3NotLike(String value) {
        lastCriteria().addCriterion("f3 not like", value, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3In(List<String> values) {
        lastCriteria().addCriterion("f3 in", values, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3NotIn(List<String> values) {
        lastCriteria().addCriterion("f3 not in", values, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3Between(String value1, String value2) {
        lastCriteria().addCriterion("f3 between", value1, value2, "f3");
        return this;
    }

    public CompoundKeyTableCriteria andF3NotBetween(String value1, String value2) {
        lastCriteria().addCriterion("f3 not between", value1, value2, "f3");
        return this;
    }

    public static CompoundKeyTableCriteria pk1IsNull() {
        return new CompoundKeyTableCriteria().andPk1IsNull();
    }

    public static CompoundKeyTableCriteria pk1IsNotNull() {
        return new CompoundKeyTableCriteria().andPk1IsNotNull();
    }

    public static CompoundKeyTableCriteria pk1EqualTo(Integer value) {
        return new CompoundKeyTableCriteria().andPk1EqualTo(value);
    }

    public static CompoundKeyTableCriteria pk1NotEqualTo(Integer value) {
        return new CompoundKeyTableCriteria().andPk1NotEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk1GreaterThan(Integer value) {
        return new CompoundKeyTableCriteria().andPk1GreaterThan(value);
    }

    public static CompoundKeyTableCriteria pk1GreaterThanOrEqualTo(Integer value) {
        return new CompoundKeyTableCriteria().andPk1GreaterThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk1LessThan(Integer value) {
        return new CompoundKeyTableCriteria().andPk1LessThan(value);
    }

    public static CompoundKeyTableCriteria pk1LessThanOrEqualTo(Integer value) {
        return new CompoundKeyTableCriteria().andPk1LessThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk1In(List<Integer> values) {
        return new CompoundKeyTableCriteria().andPk1In(values);
    }

    public static CompoundKeyTableCriteria pk1NotIn(List<Integer> values) {
        return new CompoundKeyTableCriteria().andPk1NotIn(values);
    }

    public static CompoundKeyTableCriteria pk1Between(Integer value1, Integer value2) {
        return new CompoundKeyTableCriteria().andPk1Between(value1, value2);
    }

    public static CompoundKeyTableCriteria pk1NotBetween(Integer value1, Integer value2) {
        return new CompoundKeyTableCriteria().andPk1NotBetween(value1, value2);
    }

    public static CompoundKeyTableCriteria pk2IsNull() {
        return new CompoundKeyTableCriteria().andPk2IsNull();
    }

    public static CompoundKeyTableCriteria pk2IsNotNull() {
        return new CompoundKeyTableCriteria().andPk2IsNotNull();
    }

    public static CompoundKeyTableCriteria pk2EqualTo(String value) {
        return new CompoundKeyTableCriteria().andPk2EqualTo(value);
    }

    public static CompoundKeyTableCriteria pk2NotEqualTo(String value) {
        return new CompoundKeyTableCriteria().andPk2NotEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk2GreaterThan(String value) {
        return new CompoundKeyTableCriteria().andPk2GreaterThan(value);
    }

    public static CompoundKeyTableCriteria pk2GreaterThanOrEqualTo(String value) {
        return new CompoundKeyTableCriteria().andPk2GreaterThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk2LessThan(String value) {
        return new CompoundKeyTableCriteria().andPk2LessThan(value);
    }

    public static CompoundKeyTableCriteria pk2LessThanOrEqualTo(String value) {
        return new CompoundKeyTableCriteria().andPk2LessThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria pk2Like(String value) {
        return new CompoundKeyTableCriteria().andPk2Like(value);
    }

    public static CompoundKeyTableCriteria pk2NotLike(String value) {
        return new CompoundKeyTableCriteria().andPk2NotLike(value);
    }

    public static CompoundKeyTableCriteria pk2In(List<String> values) {
        return new CompoundKeyTableCriteria().andPk2In(values);
    }

    public static CompoundKeyTableCriteria pk2NotIn(List<String> values) {
        return new CompoundKeyTableCriteria().andPk2NotIn(values);
    }

    public static CompoundKeyTableCriteria pk2Between(String value1, String value2) {
        return new CompoundKeyTableCriteria().andPk2Between(value1, value2);
    }

    public static CompoundKeyTableCriteria pk2NotBetween(String value1, String value2) {
        return new CompoundKeyTableCriteria().andPk2NotBetween(value1, value2);
    }

    public static CompoundKeyTableCriteria f3IsNull() {
        return new CompoundKeyTableCriteria().andF3IsNull();
    }

    public static CompoundKeyTableCriteria f3IsNotNull() {
        return new CompoundKeyTableCriteria().andF3IsNotNull();
    }

    public static CompoundKeyTableCriteria f3EqualTo(String value) {
        return new CompoundKeyTableCriteria().andF3EqualTo(value);
    }

    public static CompoundKeyTableCriteria f3NotEqualTo(String value) {
        return new CompoundKeyTableCriteria().andF3NotEqualTo(value);
    }

    public static CompoundKeyTableCriteria f3GreaterThan(String value) {
        return new CompoundKeyTableCriteria().andF3GreaterThan(value);
    }

    public static CompoundKeyTableCriteria f3GreaterThanOrEqualTo(String value) {
        return new CompoundKeyTableCriteria().andF3GreaterThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria f3LessThan(String value) {
        return new CompoundKeyTableCriteria().andF3LessThan(value);
    }

    public static CompoundKeyTableCriteria f3LessThanOrEqualTo(String value) {
        return new CompoundKeyTableCriteria().andF3LessThanOrEqualTo(value);
    }

    public static CompoundKeyTableCriteria f3Like(String value) {
        return new CompoundKeyTableCriteria().andF3Like(value);
    }

    public static CompoundKeyTableCriteria f3NotLike(String value) {
        return new CompoundKeyTableCriteria().andF3NotLike(value);
    }

    public static CompoundKeyTableCriteria f3In(List<String> values) {
        return new CompoundKeyTableCriteria().andF3In(values);
    }

    public static CompoundKeyTableCriteria f3NotIn(List<String> values) {
        return new CompoundKeyTableCriteria().andF3NotIn(values);
    }

    public static CompoundKeyTableCriteria f3Between(String value1, String value2) {
        return new CompoundKeyTableCriteria().andF3Between(value1, value2);
    }

    public static CompoundKeyTableCriteria f3NotBetween(String value1, String value2) {
        return new CompoundKeyTableCriteria().andF3NotBetween(value1, value2);
    }
}