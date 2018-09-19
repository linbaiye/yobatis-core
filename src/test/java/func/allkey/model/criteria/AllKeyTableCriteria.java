package func.allkey.model.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AllKeyTableCriteria extends BaseCriteria {
    private static final Map<String, String> PROPERTY_TO_COLUMN;

    static {
        PROPERTY_TO_COLUMN = new HashMap<>();
        PROPERTY_TO_COLUMN.put("pk1", "pk1");
        PROPERTY_TO_COLUMN.put("pk2", "pk2");
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

    public AllKeyTableCriteria setLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public AllKeyTableCriteria setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public AllKeyTableCriteria setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
        return this;
    }

    public AllKeyTableCriteria ascOrderBy(String  ... fields) {
        orderBy("asc", fields);
        return this;
    }

    public AllKeyTableCriteria descOrderBy(String  ... fields) {
        orderBy("desc", fields);
        return this;
    }

    public AllKeyTableCriteria or() {
        oredCriteria.add(createCriteriaInternal());
        return this;
    }

    public AllKeyTableCriteria andPk1IsNull() {
        lastCriteria().addCriterion("pk1 is null");
        return this;
    }

    public AllKeyTableCriteria andPk1IsNotNull() {
        lastCriteria().addCriterion("pk1 is not null");
        return this;
    }

    public AllKeyTableCriteria andPk1EqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 =", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 <>", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1GreaterThan(Integer value) {
        lastCriteria().addCriterion("pk1 >", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1GreaterThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 >=", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1LessThan(Integer value) {
        lastCriteria().addCriterion("pk1 <", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1LessThanOrEqualTo(Integer value) {
        lastCriteria().addCriterion("pk1 <=", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1In(List<Integer> values) {
        lastCriteria().addCriterion("pk1 in", values, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotIn(List<Integer> values) {
        lastCriteria().addCriterion("pk1 not in", values, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1Between(Integer value1, Integer value2) {
        lastCriteria().addCriterion("pk1 between", value1, value2, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotBetween(Integer value1, Integer value2) {
        lastCriteria().addCriterion("pk1 not between", value1, value2, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk2IsNull() {
        lastCriteria().addCriterion("pk2 is null");
        return this;
    }

    public AllKeyTableCriteria andPk2IsNotNull() {
        lastCriteria().addCriterion("pk2 is not null");
        return this;
    }

    public AllKeyTableCriteria andPk2EqualTo(String value) {
        lastCriteria().addCriterion("pk2 =", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2NotEqualTo(String value) {
        lastCriteria().addCriterion("pk2 <>", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2GreaterThan(String value) {
        lastCriteria().addCriterion("pk2 >", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2GreaterThanOrEqualTo(String value) {
        lastCriteria().addCriterion("pk2 >=", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2LessThan(String value) {
        lastCriteria().addCriterion("pk2 <", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2LessThanOrEqualTo(String value) {
        lastCriteria().addCriterion("pk2 <=", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2Like(String value) {
        lastCriteria().addCriterion("pk2 like", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2NotLike(String value) {
        lastCriteria().addCriterion("pk2 not like", value, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2In(List<String> values) {
        lastCriteria().addCriterion("pk2 in", values, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2NotIn(List<String> values) {
        lastCriteria().addCriterion("pk2 not in", values, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2Between(String value1, String value2) {
        lastCriteria().addCriterion("pk2 between", value1, value2, "pk2");
        return this;
    }

    public AllKeyTableCriteria andPk2NotBetween(String value1, String value2) {
        lastCriteria().addCriterion("pk2 not between", value1, value2, "pk2");
        return this;
    }

    public static AllKeyTableCriteria pk1IsNull() {
        return new AllKeyTableCriteria().andPk1IsNull();
    }

    public static AllKeyTableCriteria pk1IsNotNull() {
        return new AllKeyTableCriteria().andPk1IsNotNull();
    }

    public static AllKeyTableCriteria pk1EqualTo(Integer value) {
        return new AllKeyTableCriteria().andPk1EqualTo(value);
    }

    public static AllKeyTableCriteria pk1NotEqualTo(Integer value) {
        return new AllKeyTableCriteria().andPk1NotEqualTo(value);
    }

    public static AllKeyTableCriteria pk1GreaterThan(Integer value) {
        return new AllKeyTableCriteria().andPk1GreaterThan(value);
    }

    public static AllKeyTableCriteria pk1GreaterThanOrEqualTo(Integer value) {
        return new AllKeyTableCriteria().andPk1GreaterThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk1LessThan(Integer value) {
        return new AllKeyTableCriteria().andPk1LessThan(value);
    }

    public static AllKeyTableCriteria pk1LessThanOrEqualTo(Integer value) {
        return new AllKeyTableCriteria().andPk1LessThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk1In(List<Integer> values) {
        return new AllKeyTableCriteria().andPk1In(values);
    }

    public static AllKeyTableCriteria pk1NotIn(List<Integer> values) {
        return new AllKeyTableCriteria().andPk1NotIn(values);
    }

    public static AllKeyTableCriteria pk1Between(Integer value1, Integer value2) {
        return new AllKeyTableCriteria().andPk1Between(value1, value2);
    }

    public static AllKeyTableCriteria pk1NotBetween(Integer value1, Integer value2) {
        return new AllKeyTableCriteria().andPk1NotBetween(value1, value2);
    }

    public static AllKeyTableCriteria pk2IsNull() {
        return new AllKeyTableCriteria().andPk2IsNull();
    }

    public static AllKeyTableCriteria pk2IsNotNull() {
        return new AllKeyTableCriteria().andPk2IsNotNull();
    }

    public static AllKeyTableCriteria pk2EqualTo(String value) {
        return new AllKeyTableCriteria().andPk2EqualTo(value);
    }

    public static AllKeyTableCriteria pk2NotEqualTo(String value) {
        return new AllKeyTableCriteria().andPk2NotEqualTo(value);
    }

    public static AllKeyTableCriteria pk2GreaterThan(String value) {
        return new AllKeyTableCriteria().andPk2GreaterThan(value);
    }

    public static AllKeyTableCriteria pk2GreaterThanOrEqualTo(String value) {
        return new AllKeyTableCriteria().andPk2GreaterThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk2LessThan(String value) {
        return new AllKeyTableCriteria().andPk2LessThan(value);
    }

    public static AllKeyTableCriteria pk2LessThanOrEqualTo(String value) {
        return new AllKeyTableCriteria().andPk2LessThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk2Like(String value) {
        return new AllKeyTableCriteria().andPk2Like(value);
    }

    public static AllKeyTableCriteria pk2NotLike(String value) {
        return new AllKeyTableCriteria().andPk2NotLike(value);
    }

    public static AllKeyTableCriteria pk2In(List<String> values) {
        return new AllKeyTableCriteria().andPk2In(values);
    }

    public static AllKeyTableCriteria pk2NotIn(List<String> values) {
        return new AllKeyTableCriteria().andPk2NotIn(values);
    }

    public static AllKeyTableCriteria pk2Between(String value1, String value2) {
        return new AllKeyTableCriteria().andPk2Between(value1, value2);
    }

    public static AllKeyTableCriteria pk2NotBetween(String value1, String value2) {
        return new AllKeyTableCriteria().andPk2NotBetween(value1, value2);
    }
}