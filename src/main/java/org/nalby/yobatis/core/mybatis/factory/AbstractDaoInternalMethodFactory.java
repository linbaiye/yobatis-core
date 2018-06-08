package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.Method;

public interface AbstractDaoInternalMethodFactory {
    Method doSelectOne();
    Method doSelectList();
    Method doUpdate();
    Method doInsert();
    Method doDelete();
    Method notNull();
    Method validateCriteria();
    Method makeParam();
}
