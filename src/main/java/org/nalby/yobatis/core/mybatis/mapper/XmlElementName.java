package org.nalby.yobatis.core.mybatis.mapper;

public enum XmlElementName {
    // "private" elements.
    BASE_RESULT_MAP("BASE_RESULT_MAP"), WHERE_CLAUSE("WHERE_CLAUSE"), WHERE_CLAUSE_FOR_UPDATE("WHERE_CLAUSE_FOR_UPDATE"),
    BASE_COLUMN_LIST("BASE_COLUMN_LIST"), PAGING("PAGING"),

    INSERT("INSERT"), SELECT_ONE_BY_PK("SELECT_ONE_BY_PK"), SELECT_BY_CRITERIA("SELECT_BY_CRITERIA"),
    SELECT_LIST("SELECT_LIST"), COUNT("COUNT"), UPDATE_BY_PK("UPDATE_BY_PK"), UPDATE_BY_CRITERIA("UPDATE_BY_CRITERIA"),
    DELETE_BY_PK("DELETE_BY_PK"), DELETE_BY_CRITERIA("DELETE_BY_CRITERIA"),
    ;

    private String name;

    XmlElementName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean nameEquals(String str) {
        return getName().equals(str);
    }
}
