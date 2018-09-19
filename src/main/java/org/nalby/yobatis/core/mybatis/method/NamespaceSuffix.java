package org.nalby.yobatis.core.mybatis.method;

public final class NamespaceSuffix {

    private NamespaceSuffix() {}

    public static final String SELECT_BY_PK = "selectByPk";

    public static final String SELECT_BY_CRITERIA = "selectByCriteria";

    public static final String INSERT_ALL = "insertAll";

    public static final String INSERT = "insert";

    public static final String INSERT_IGNORE = "insertIgnore";

    public static final String INSERT_ALL_IGNORE = "insertAllIgnore";

    public static final String COUNT = "count";

    public static final String DELETE = "deleteByPk";

    public static final String DELETE_BY_CRITERIA = "deleteByCriteria";

    public static final String UPDATE = "update";

    public static final String UPDATE_BY_CRITERIA = "updateByCriteria";

    public static final String UPDATE_ALL = "updateAll";

    public static final String UPDATE_ALL_BY_CRITERIA = "updateAllByCriteria";
}
