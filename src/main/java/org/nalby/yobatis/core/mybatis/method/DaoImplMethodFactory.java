package org.nalby.yobatis.core.mybatis.method;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;

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
        if (FactoryMethodName.COUNT.nameEquals(name)) {
            return count();
        } else if (FactoryMethodName.SELECT_ONE_BY_PK.nameEquals(name)) {
            return selectOne();
        } else if (FactoryMethodName.SELECT_ONE_BY_CRITERIA.nameEquals(name)) {
            return selectOneByCriteria();
        } else if (FactoryMethodName.SELECT_LIST.nameEquals(name)) {
            return selectList();
        } else if (FactoryMethodName.UPDATE_BY_PK.nameEquals(name)) {
            return update();
        } else if (FactoryMethodName.UPDATE_BY_CRITERIA.nameEquals(name)) {
            return updateByCriteria();
        } else if (FactoryMethodName.DELETE_BY_PK.nameEquals(name)) {
            return delete();
        } else if (FactoryMethodName.DELETE_BY_CRITERIA.nameEquals(name)) {
            return deleteByCriteria();
        } else if (FactoryMethodName.INSERT.nameEquals(name)) {
            return insert();
        } else if (FactoryMethodName.NOT_NULL.nameEquals(name)) {
            return notNull();
        }
        throw new IllegalArgumentException("Unknown name.");
    }

    private Method notNull() {
        Method method = new Method("notNull");
        method.setVisibility(JavaVisibility.PRIVATE);
        method.addParameter(makeParam(new FullyQualifiedJavaType("Object"), "obj"));
        method.addParameter(makeParam(new FullyQualifiedJavaType("String"), "msg"));
        method.addBodyLine("if (obj == null) {");
        method.addBodyLine("throw new IllegalArgumentException(msg);");
        method.addBodyLine("}");
        return method;
    }

    private Method insert() {
        Method method = signatureFactory.create(FactoryMethodName.INSERT.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.insert(NAMESPACE + \"" + FactoryMethodName.INSERT.getName() + "\", record);"
        });
    }

    private Method selectOne() {
        Method method = signatureFactory.create(FactoryMethodName.SELECT_ONE_BY_PK.getName());
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + FactoryMethodName.SELECT_ONE_BY_PK.getName() + "\", pk);"
        });
    }


    private Method selectOneByCriteria() {
        Method method = signatureFactory.create(FactoryMethodName.SELECT_ONE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + FactoryMethodName.SELECT_ONE_BY_CRITERIA.getName() + "\", criteria);"
        });
    }

    private Method selectList() {
        Method method = signatureFactory.create(FactoryMethodName.SELECT_LIST.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectList(NAMESPACE + \"" + FactoryMethodName.SELECT_ONE_BY_CRITERIA.getName() + "\", criteria);"
        });
    }

    private Method count() {
        Method method = signatureFactory.create(FactoryMethodName.COUNT.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.selectOne(NAMESPACE + \"" + FactoryMethodName.COUNT.getName() + "\", criteria);"
        });
    }


    private Method update() {
        Method method = signatureFactory.create(FactoryMethodName.UPDATE_BY_PK.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + FactoryMethodName.UPDATE_BY_PK.getName() + "\", record);"
        });
    }

    private Method updateByCriteria() {
        Method method = signatureFactory.create(FactoryMethodName.UPDATE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(record, \"record must not be null.\");",
                "notNull(criteria, \"criteria must not be null.\");",
                "Map<String, Object> param = new HashMap<>();",
                "param.put(\"record\", record);",
                "param.put(\"criteria\", criteria);",
                "return sqlSessionTemplate.update(NAMESPACE + \"" + FactoryMethodName.UPDATE_BY_CRITERIA.getName() + "\", param);"
        });
    }

    private Method delete() {
        Method method = signatureFactory.create(FactoryMethodName.DELETE_BY_PK.getName());
        return completeMethod(method, new String[]{
                "notNull(pk, \"pk must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + FactoryMethodName.DELETE_BY_PK.getName() + "\", pk);"
        });
    }

    private Method deleteByCriteria() {
        Method method = signatureFactory.create(FactoryMethodName.DELETE_BY_CRITERIA.getName());
        return completeMethod(method, new String[]{
                "notNull(criteria, \"criteria must not be null.\");",
                "return sqlSessionTemplate.delete(NAMESPACE + \"" + FactoryMethodName.DELETE_BY_CRITERIA.getName() + "\", criteria);"
        });
    }

}
