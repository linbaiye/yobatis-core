package org.nalby.yobatis.core.mybatis.mapper;

import java.util.HashSet;
import java.util.Set;

public final class MapperReservedIdentifierx {

    private final static Set<String> SET;

    static {
        SET = new HashSet<>();
        SET.add("BASE_RESULT_MAP");
        SET.add("WHERE_CLAUSE");
        SET.add("WHERE_CLAUSE_FOR_UPDATE");
        SET.add("BASE_COLUMN_LIST");
        SET.add("selectByCriteria");
        SET.add("selectByPk");
        SET.add("deleteByPk");
        SET.add("deleteByCriteria");
        SET.add("insert");
        SET.add("insertAll");
        SET.add("insertAllIgnore");
        SET.add("count");
        SET.add("updateByCriteria");
        SET.add("updateAllByCriteria");
        SET.add("update");
        SET.add("updateAll");
        SET.add("_PAGING_");
    }

    public static boolean contains(String key) {
        return SET.contains(key);
    }

}
