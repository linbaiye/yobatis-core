package func.compoundkey.model.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/*
 * Do not modify, it will be overwritten every time yobatis runs.
 */
public abstract class BaseCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<BracketCriteria> oredCriteria;

    protected Long limit;

    protected Long offset;

    protected boolean forUpdate;

    public BaseCriteria() {
        oredCriteria = new ArrayList<BracketCriteria>();
    }

    protected BracketCriteria createCriteriaInternal() {
        return new BracketCriteria();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<BracketCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        limit = null;
        offset = null;
        forUpdate = false;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public BracketCriteria lastCriteria() {
        if (oredCriteria.isEmpty()) {
            oredCriteria.add(createCriteriaInternal());
        }
        return oredCriteria.get(oredCriteria.size() - 1);
    }

    public static class BracketCriteria {
        private List<Criterion> criteria;

        BracketCriteria() {
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void addCriterion(String condition) {
            criteria.add(new Criterion(condition));
        }

        public void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new IllegalArgumentException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        public void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new IllegalArgumentException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        public void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new IllegalArgumentException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        public void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new IllegalArgumentException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        public void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new IllegalArgumentException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        public void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}