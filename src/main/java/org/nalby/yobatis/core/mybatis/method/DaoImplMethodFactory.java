package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.mybatis.YobatisIntrospectedTable;

public class DaoImplMethodFactory extends AbstractMethodFactory {

    private MethodFactory signatureFactory;

    private final static DaoImplMethodFactory instance = new DaoImplMethodFactory();

    public static DaoImplMethodFactory getInstance(YobatisIntrospectedTable table) {
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
        if (DaoMethodName.COUNT.nameEquals(name)) {
            return count();
        } else if (DaoMethodName.SELECT_BY_PK.nameEquals(name)) {
            return selectOne();
        } else if (DaoMethodName.SELECT_BY_CRITERIA.nameEquals(name)) {
            return selectOneByCriteria();
        } else if (DaoMethodName.SELECT_LIST.nameEquals(name)) {
            return selectList();
        } else if (DaoMethodName.UPDATE_BY_PK.nameEquals(name)) {
            return update();
        } else if (DaoMethodName.UPDATE_BY_CRITERIA.nameEquals(name)) {
            return updateByCriteria();
        } else if (DaoMethodName.DELETE_BY_PK.nameEquals(name)) {
            return delete();
        } else if (DaoMethodName.DELETE_BY_CRITERIA.nameEquals(name)) {
            return deleteByCriteria();
        } else if (DaoMethodName.INSERT.nameEquals(name)) {
            return insert();
        }
        throw new IllegalArgumentException("Unknown name.");
    }


    private Method insert() {
        Method method = signatureFactory.create(DaoMethodName.INSERT.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.insert(NAMESPACE + \"" + DaoMethodName.INSERT.getName() + "\", record);"
        });
    }

    private Method selectOne() {
        Method method = signatureFactory.create(DaoMethodName.SELECT_BY_PK.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + DaoMethodName.SELECT_BY_PK.getName() + "\", pk);"
        });
    }


    private Method selectOneByCriteria() {
        Method method = signatureFactory.create(DaoMethodName.SELECT_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + DaoMethodName.SELECT_BY_CRITERIA.getName() + "\", criteria);"
        });
    }

    private Method selectList() {
        Method method = signatureFactory.create(DaoMethodName.SELECT_LIST.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.selectList(NAMESPACE + \"" + DaoMethodName.SELECT_LIST.getName() + "\", criteria);"
        });
    }

    private Method count() {
        Method method = signatureFactory.create(DaoMethodName.COUNT.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + DaoMethodName.COUNT.getName() + "\", criteria);"
        });
    }


    private Method update() {
        Method method = signatureFactory.create(DaoMethodName.UPDATE_BY_PK.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.update(NAMESPACE + \"" + DaoMethodName.UPDATE_BY_PK.getName() + "\", record);"
        });
    }

    private Method updateByCriteria() {
        Method method = signatureFactory.create(DaoMethodName.UPDATE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "Map<String, Object> param = new HashMap<>();",
                "param.put(\"record\", record);",
                "param.put(\"criteria\", criteria);",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + DaoMethodName.UPDATE_BY_CRITERIA.getName() + "\", param);"
        });
    }

    private Method delete() {
        Method method = signatureFactory.create(DaoMethodName.DELETE_BY_PK.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + DaoMethodName.DELETE_BY_PK.getName() + "\", pk);"
        });
    }

    private Method deleteByCriteria() {
        Method method = signatureFactory.create(DaoMethodName.DELETE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + DaoMethodName.DELETE_BY_CRITERIA.getName() + "\", criteria);"
        });
    }

}
