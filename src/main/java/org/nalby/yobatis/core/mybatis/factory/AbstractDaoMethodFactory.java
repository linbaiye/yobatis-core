package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.Method;

public interface AbstractDaoMethodFactory {
    Method insertAll();
    Method insertAllIgnore();
    Method insert();
    Method selectOne();
    Method selectOneByCriteria();
    Method selectList();
    Method countAll();
    Method countByCriteria();
    Method update();
    Method updateAll();
    Method updateByCriteria();
    Method updateAllByCriteria();
    Method delete();
    Method deleteByCriteria();
}
