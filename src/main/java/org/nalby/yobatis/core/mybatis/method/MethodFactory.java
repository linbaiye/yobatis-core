package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

public interface MethodFactory {

    Method create(String name);

//    Method insert(YobatisTableItem table);
//
//    Method selectOne(YobatisTableItem table);
//
//    Method selectOneByCriteria(YobatisTableItem table);
//
//    Method selectList(YobatisTableItem table);
//
//    Method count(YobatisTableItem table);
//
//    Method update(YobatisTableItem table);
//
//    Method updateByCriteria(YobatisTableItem table);
//
//    Method delete(YobatisTableItem table);
//
//    Method deleteByCriteria(YobatisTableItem table);

}
