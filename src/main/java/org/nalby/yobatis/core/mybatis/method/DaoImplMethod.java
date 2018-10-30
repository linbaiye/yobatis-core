package org.nalby.yobatis.core.mybatis.method;

public enum DaoImplMethod {
    INSERT("INSERT"), SELECT_ONE("SELECT_ONE_BY_PK"), SELECT_ONE_BY_CRITERIA("SELECT_BY_CRITERIA"),
    SELECT_LIST("SELECT_LIST"), COUNT("COUNT"), UPDATE("UPDATE_BY_PK"), UPDATE_BY_CRITERIA("UPDATE_BY_CRITERIA"),
    DELETE("DELETE_BY_PK"), DELETE_BY_CRITERIA("DELETE_BY_CRITERIA")
    ;
    private final String name;

    DaoImplMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean nameEquals(String str) {
        return getName().equals(str);
    }

    public boolean belongsTo(String str) {
        for (DaoImplMethod value: values()) {
            if (value.nameEquals(str)) {
                return true;
            }
        }
        return false;
    }

}
