package org.nalby.yobatis.core.mybatis.method;

public enum DaoMethod {
    INSERT("insert"), SELECT_ONE("selectOneByPk"), SELECT_ONE_BY_CRITERIA("selectOneByCriteria"),
    SELECT_LIST("selectList"), COUNT("count"), update("updateByPk"), UPDATE_BY_CRITERIA("updateByCriteria"),
    DELETE("deleteByPk"), DELETE_BY_CRITERIA("deleteByCriteria")
    ;
    private String name;
    DaoMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
