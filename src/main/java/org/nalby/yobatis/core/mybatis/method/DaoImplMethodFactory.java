package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisTableItem;

public class DaoImplMethodFactory extends AbstractMethodFactory {

    private MethodFactory signatureFactory;

    private final static DaoImplMethodFactory instance = new DaoImplMethodFactory();

    public static DaoImplMethodFactory getInstance(YobatisTableItem table) {
        instance.signatureFactory = DaoMethodFactory.getInstance(table);
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
    public Method create(String name) {
        if (DaoMethod.COUNT.nameEquals(name)) {
            return count();
        } else if (DaoMethod.SELECT_ONE.nameEquals(name)) {
            return selectOne();
        } else if (DaoMethod.SELECT_ONE_BY_CRITERIA.nameEquals(name)) {
            return selectOneByCriteria();
        } else if (DaoMethod.SELECT_LIST.nameEquals(name)) {
            return selectList();
        } else if (DaoMethod.UPDATE.nameEquals(name)) {
            return update();
        } else if (DaoMethod.UPDATE_BY_CRITERIA.nameEquals(name)) {
            return updateByCriteria();
        } else if (DaoMethod.DELETE.nameEquals(name)) {
            return delete();
        } else if (DaoMethod.DELETE_BY_CRITERIA.nameEquals(name)) {
            return deleteByCriteria();
        }
        throw new IllegalArgumentException("Unknown name.");
    }

    public Method insert() {
        Method method = signatureFactory.create(DaoMethod.INSERT.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.insert(NAMESPACE + \"" + NamespaceSuffix.INSERT + "\", record);"
        });
    }

    public Method selectOne() {
        Method method = signatureFactory.create(DaoMethod.SELECT_ONE.getName());
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_PK + "\", pk);"
        });
    }


    public Method selectOneByCriteria() {
        Method method = signatureFactory.create(DaoMethod.SELECT_ONE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
        });
    }


    public Method selectList() {
        Method method = signatureFactory.create(DaoMethod.SELECT_LIST.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectList(NAMESPACE + \"" + NamespaceSuffix.SELECT_BY_CRITERIA + "\", criteria);"
        });
    }


    public Method count() {
        Method method = signatureFactory.create(DaoMethod.COUNT.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + NamespaceSuffix.COUNT + "\", criteria);"
        });
    }


    public Method update() {
        Method method = signatureFactory.create(DaoMethod.UPDATE.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + NamespaceSuffix.UPDATE + "\", record);"
        });
    }


    public Method updateByCriteria() {
        Method method = signatureFactory.create(DaoMethod.UPDATE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "notNull(criteria, \"criteria must not be null.\");",
                "Map<String, Object> param = new HashMap<>();",
                "param.put(\"record\", record);",
                "param.put(\"criteria\", criteria);",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + NamespaceSuffix.UPDATE_BY_CRITERIA + "\", param);"
        });
    }


    public Method delete() {
        Method method = signatureFactory.create(DaoMethod.DELETE.getName());
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + NamespaceSuffix.DELETE + "\", pk);"
        });
    }


    public Method deleteByCriteria() {
        Method method = signatureFactory.create(DaoMethod.DELETE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + NamespaceSuffix.DELETE_BY_CRITERIA + "\", criteria);"
        });
    }


}
