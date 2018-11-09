package org.nalby.yobatis.core.mybatis.method;

/**
 * It is just an assistance that collects names used to newInstance Methods
 * via MethodFactory.
 */
public enum DaoMethodName {
    INSERT("insert"), SELECT_BY_PK("selectByPk"), SELECT_BY_CRITERIA("selectByCriteria"),
    SELECT_LIST("selectList"), COUNT("count"), UPDATE_BY_PK("updateByPk"), UPDATE_BY_CRITERIA("updateByCriteria"),
    DELETE_BY_PK("deleteByPk"), DELETE_BY_CRITERIA("deleteByCriteria"),
    ;
    private String name;

    DaoMethodName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DaoMethodName findByVal(String str) {
        for (DaoMethodName tmp : values()) {
            if (tmp.name.equals(str)) {
                return tmp;
            }
        }
        throw new IllegalArgumentException("unknown name");
    }

}
