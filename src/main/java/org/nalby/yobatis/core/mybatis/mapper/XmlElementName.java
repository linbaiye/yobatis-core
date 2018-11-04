package org.nalby.yobatis.core.mybatis.mapper;

public enum XmlElementName {
    // "private" elements.
    BASE_RESULT_MAP("BASE_RESULT_MAP"), WHERE_CLAUSE("WHERE_CLAUSE"), WHERE_CLAUSE_FOR_UPDATE("WHERE_CLAUSE_FOR_UPDATE"),
    BASE_COLUMN_LIST("BASE_COLUMN_LIST"), PAGING("PAGING"), PRIVATE_SELECT_BY_CRITERIA("PRIVATE_SELECT_BY_CRITERIA"),


    INSERT("insert"), SELECT_BY_PK("selectByPk"), SELECT_BY_CRITERIA("selectByCriteria"),
    SELECT_LIST("selectList"), COUNT("count"), UPDATE_BY_PK("updateByPk"), UPDATE_BY_CRITERIA("updateByCriteria"),
    DELETE_BY_PK("deleteByPk"), DELETE_BY_CRITERIA("deleteByCriteria"),
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
