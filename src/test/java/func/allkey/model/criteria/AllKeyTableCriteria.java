package func.allkey.model.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Do NOT modify, it will be overwrote every time yobatis runs.
 */
public class AllKeyTableCriteria extends BaseCriteria {
    private static final Map<String, String> PROPERTY_TO_COLUMN;

    static {
        PROPERTY_TO_COLUMN = new HashMap<String, String>();
        PROPERTY_TO_COLUMN.put("pk1", "pk1");
        PROPERTY_TO_COLUMN.put("pk2", "pk2");
    }

    public AllKeyTableCriteria setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    private void orderBy(String order, String ... fields) {
        if ( fields == null || fields.length == 0) {
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

    /**
     * Add the 'order by field1 asc, field2 asc, ...' clause to query, only fields in {@code AllKeyTable}(not column names) are allowed.
     * By invoking this method and {@link #descOrderBy(String...) descOrderBy} alternately, a more complex 'order by' clause
     * can be constructed, shown as below.
     * <pre>
     * criteria.ascOrderBy('field1');
     * criteria.descOrderBy('field2');
     * -> 'order by field1 asc, field2 desc'
     * </pre>
     * @param fields the fields to sort.
     * @throws IllegalArgumentException if fields is empty, or any of the fields is invalid.
     * @return this criteria.
     */
    public AllKeyTableCriteria ascOrderBy(String  ... fields) {
        orderBy("asc", fields);
        return this;
    }

    /**
     * Add the 'order by field1 desc, field2 desc, ...' clause to query, only fields in {@code AllKeyTable}(not column names) are allowed.
     * By invoking this method and {@link #ascOrderBy(String...) ascOrderBy} alternately, a more complex 'order by' clause
     * can be constructed, shown as below.
     * <pre>
     * criteria.ascOrderBy('field1');
     * criteria.descOrderBy('field2');
     * -> 'order by field1 asc, field2 desc'
     * </pre>
     * @param fields the fields to sort.
     * @throws IllegalArgumentException if fields is empty, or any of the fields is invalid.
     * @return this criteria.
     */
    public AllKeyTableCriteria descOrderBy(String  ... fields) {
        orderBy("desc", fields);
        return this;
    }

    public AllKeyTableCriteria or() {
        oredCriteria.add(createCriteriaInternal());
        return this;
    }

    public AllKeyTableCriteria setLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public AllKeyTableCriteria setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public AllKeyTableCriteria setForUpdate(Boolean forUpdate) {
        this.forUpdate = forUpdate;
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

    public AllKeyTableCriteria andPk1EqualTo(Long value) {
        lastCriteria().addCriterion("pk1 =", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotEqualTo(Long value) {
        lastCriteria().addCriterion("pk1 <>", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1GreaterThan(Long value) {
        lastCriteria().addCriterion("pk1 >", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1GreaterThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("pk1 >=", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1LessThan(Long value) {
        lastCriteria().addCriterion("pk1 <", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1LessThanOrEqualTo(Long value) {
        lastCriteria().addCriterion("pk1 <=", value, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1In(List<Long> values) {
        lastCriteria().addCriterion("pk1 in", values, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotIn(List<Long> values) {
        lastCriteria().addCriterion("pk1 not in", values, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1Between(Long value1, Long value2) {
        lastCriteria().addCriterion("pk1 between", value1, value2, "pk1");
        return this;
    }

    public AllKeyTableCriteria andPk1NotBetween(Long value1, Long value2) {
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

    public static AllKeyTableCriteria pk1EqualTo(Long value) {
        return new AllKeyTableCriteria().andPk1EqualTo(value);
    }

    public static AllKeyTableCriteria pk1NotEqualTo(Long value) {
        return new AllKeyTableCriteria().andPk1NotEqualTo(value);
    }

    public static AllKeyTableCriteria pk1GreaterThan(Long value) {
        return new AllKeyTableCriteria().andPk1GreaterThan(value);
    }

    public static AllKeyTableCriteria pk1GreaterThanOrEqualTo(Long value) {
        return new AllKeyTableCriteria().andPk1GreaterThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk1LessThan(Long value) {
        return new AllKeyTableCriteria().andPk1LessThan(value);
    }

    public static AllKeyTableCriteria pk1LessThanOrEqualTo(Long value) {
        return new AllKeyTableCriteria().andPk1LessThanOrEqualTo(value);
    }

    public static AllKeyTableCriteria pk1In(List<Long> values) {
        return new AllKeyTableCriteria().andPk1In(values);
    }

    public static AllKeyTableCriteria pk1NotIn(List<Long> values) {
        return new AllKeyTableCriteria().andPk1NotIn(values);
    }

    public static AllKeyTableCriteria pk1Between(Long value1, Long value2) {
        return new AllKeyTableCriteria().andPk1Between(value1, value2);
    }

    public static AllKeyTableCriteria pk1NotBetween(Long value1, Long value2) {
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