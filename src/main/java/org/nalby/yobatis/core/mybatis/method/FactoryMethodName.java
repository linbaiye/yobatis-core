package org.nalby.yobatis.core.mybatis.method;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * It is just an assistance that collects names used to build Methods
 * via MethodFactory.
 */
public enum FactoryMethodName {
    //notNull method used by DaoImpl.
    NOT_NULL("NOT_NULL", 2),

    INSERT("INSERT"), SELECT_ONE_BY_PK("SELECT_ONE_BY_PK"), SELECT_ONE_BY_CRITERIA("SELECT_BY_CRITERIA"),
    SELECT_LIST("SELECT_LIST"), COUNT("COUNT"), UPDATE_BY_PK("UPDATE_BY_PK"), UPDATE_BY_CRITERIA("UPDATE_BY_CRITERIA"),
    DELETE_BY_PK("DELETE_BY_PK"), DELETE_BY_CRITERIA("DELETE_BY_CRITERIA"),
    ;
    private String name;
    private int groupBitMap;

    public final static int DAO_GROUP = 1;
    public final static int DAO_IMPL_GROUP = 2;

    FactoryMethodName(String name, int group) {
        this.name = name;
        this.groupBitMap = group;
    }

    FactoryMethodName(String name) {
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
        for (FactoryMethodName e: values()) {
            if ((e.getGroupBitMap() & group) != 0) {
                nameList.add(e.getName());
            }
        }
        return nameList;
    }

}
