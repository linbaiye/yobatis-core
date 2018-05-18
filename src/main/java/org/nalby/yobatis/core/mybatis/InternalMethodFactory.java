package org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.dom.java.Method;

public interface InternalMethodFactory {
    Method doSelectOne();
    Method doSelectList();
    Method doUpdate();
    Method doInsert();
    Method doDelete();
    Method notNull();
    Method validateCriteria();
    Method makeParam();
}
