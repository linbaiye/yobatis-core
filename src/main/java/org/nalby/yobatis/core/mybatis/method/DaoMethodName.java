package org.nalby.yobatis.core.mybatis.method;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * It is just an assistance that collects names used to newInstance Methods
 * via MethodFactory.
 */
public enum DaoMethodName {
    //notNull method used by DaoImpl.
    NOT_NULL("NOT_NULL", 2),

    INSERT("insert"), SELECT_BY_PK("selectByPk"), SELECT_BY_CRITERIA("selectByCriteria"),
    SELECT_LIST("selectList"), COUNT("count"), UPDATE_BY_PK("updateByPk"), UPDATE_BY_CRITERIA("updateByCriteria"),
    DELETE_BY_PK("deleteByPk"), DELETE_BY_CRITERIA("deleteByCriteria"),
    ;
    private String name;
    private int groupBitMap;

    public final static int DAO_GROUP = 1;
    public final static int DAO_IMPL_GROUP = 2;

    DaoMethodName(String name, int group) {
        this.name = name;
        this.groupBitMap = group;
    }

    DaoMethodName(String name) {
        this(name, DAO_GROUP | DAO_IMPL_GROUP);
    }

    public String getName() {
        return name;
    }

    private int getGroupBitMap() {
        return groupBitMap;
    }

    public boolean nameEquals(String str) {
        return getName().equals(str);
    }

    public static List<String> listMethodNamesByGroup(int group) {
        if (group != DAO_GROUP && group != DAO_IMPL_GROUP) {
            return Collections.emptyList();
        }
        List<String> nameList = new LinkedList<>();
        for (DaoMethodName e: values()) {
            if ((e.getGroupBitMap() & group) != 0) {
                nameList.add(e.getName());
            }
        }
        return nameList;
    }

}
