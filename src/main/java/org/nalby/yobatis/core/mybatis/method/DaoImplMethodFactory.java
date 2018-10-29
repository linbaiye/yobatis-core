package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

public class DaoImplMethodFactory extends AbstractDaoMethodFactory {

    private final static DaoMethodFactory signatureFactory = DaoMethodFactoryImpl.getInstance();

    private final static DaoImplMethodFactory instance = new DaoImplMethodFactory();

    public static DaoImplMethodFactory getInstance() {
        return instance;
    }

    private Method completeMethod(Method method, String[] bodyLie) {
        for (String s : bodyLie) {
            method.addBodyLine(s);
        }
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }

    @Override
    public Method insert(YobatisTableItem table) {
        Method method = signatureFactory.insert(table);
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.insert(NAMESPACE + \"" + NamespaceSuffix.INSERT + "\", record);"
        });
    }

    @Override
    public Method selectOne(YobatisTableItem table) {
        Method method = signatureFactory.selectOne(table);
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_PK + "\", pk);"
        });
    }

    @Override
    public Method selectOneByCriteria(YobatisTableItem table) {
        Method method = signatureFactory.selectOneByCriteria(table);
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
        });
    }

    @Override
    public Method selectList(YobatisTableItem table) {
        Method method = signatureFactory.selectList(table);
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectList(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
        });
    }

    @Override
    public Method count(YobatisTableItem table) {
        Method method = signatureFactory.count(table);
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.COUNT + "\", criteria);"
        });
    }

    @Override
    public Method update(YobatisTableItem table) {
        Method method = signatureFactory.update(table);
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + NamespaceSuffix.UPDATE + "\", record);"
        });
    }

    @Override
    public Method updateByCriteria(YobatisTableItem table) {
        Method method = signatureFactory.updateByCriteria(table);
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "notNull(criteria, \"criteria must not be null.\");",
                "Map<String, Object> param = new HashMap<>();",
                "param.put(\"record\", record);",
                "param.put(\"criteria\", criteria);",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + NamespaceSuffix.UPDATE_BY_CRITERIA + "\", param);"
        });
    }

    @Override
    public Method delete(YobatisTableItem table) {
        Method method = signatureFactory.delete(table);
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + NamespaceSuffix.DELETE + "\", pk);"
        });
    }

    @Override
    public Method deleteByCriteria(YobatisTableItem table) {
        Method method = signatureFactory.deleteByCriteria(table);
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + NamespaceSuffix.DELETE_BY_CRITERIA + "\", criteria);"
        });
    }
}
