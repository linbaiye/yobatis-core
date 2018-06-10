package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class DaoMethodImplFactory implements AbstractDaoMethodFactory, AbstractDaoInternalMethodFactory {

    private static DaoMethodImplFactory publicMethodImplFactory = new DaoMethodImplFactory();

    private final static String OVERRIDE_ANNOTATION = "@Override";

    private AbstractDaoMethodFactory signatureFactory;

    private DaoMethodImplFactory() {
        signatureFactory = DaoMethodSignatureFactory.getInstance();
    }

    public static DaoMethodImplFactory getInstance() {
        return publicMethodImplFactory;
    }

    private Method createProtectedMethod(String name, String returnType) {
        Method method = new Method(name);
        if (returnType != null) {
            method.setReturnType(new FullyQualifiedJavaType(returnType));
        }
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setFinal(true);
        return method;
    }

    private Method decorateMethod(Method method, String ... bodyLines) {
        method.addAnnotation(OVERRIDE_ANNOTATION);
        method.setFinal(true);
        method.setVisibility(JavaVisibility.PUBLIC);
        if (bodyLines.length != 0) {
            for (String bodyLine : bodyLines) {
                method.addBodyLine(bodyLine);
            }
        }
        method.getJavaDocLines().clear();
        return method;
    }

    @Override
    public Method insertAll() {
        Method method = signatureFactory.insertAll();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
                "return doInsert(INSERT_ALL, record);");
    }

    @Override
    public Method insertAllIgnore() {
        Method method = signatureFactory.insertAllIgnore();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
                "return doInsert(INSERT_ALL_IGNORE, record);");
    }

    @Override
    public Method insert() {
        Method method = signatureFactory.insert();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
            "return doInsert(INSERT, record);");
    }

    @Override
    public Method selectOne() {
        Method method = signatureFactory.selectOne();
        return decorateMethod(method, "notNull(pk, \"Primary key must not be null.\");",
                "return doSelectOne(SELECT_BY_PK, pk);");
    }

    @Override
    public Method selectOneByCriteria() {
        Method method = signatureFactory.selectOneByCriteria();
        return decorateMethod(method, "validateCriteria(criteria);",
                "return doSelectOne(SELECT_BY_CRITERIA, criteria);");
    }

    @Override
    public Method selectList() {
        Method method = signatureFactory.selectList();
        return decorateMethod(method, "validateCriteria(criteria);",
                "return doSelectList(SELECT_BY_CRITERIA, criteria);");
    }

    @Override
    public Method countAll() {
        Method method = signatureFactory.countAll();
        return decorateMethod(method, "return sqlSessionTemplate.selectOne(namespace() + COUNT, null);");
    }

    @Override
    public Method countByCriteria() {
        Method method = signatureFactory.countByCriteria();
        return decorateMethod(method, "validateCriteria(criteria);",
        "return sqlSessionTemplate.selectOne(this.namespace() + COUNT, criteria);");
    }

    @Override
    public Method update() {
        Method method = signatureFactory.update();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
             "return doUpdate(UPDATE, record);");
    }

    @Override
    public Method updateAll() {
        Method method = signatureFactory.updateAll();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
            "return doUpdate(UPDATE_ALL, record);");
    }

    @Override
    public Method updateByCriteria() {
        Method method = signatureFactory.updateByCriteria();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
                "validateCriteria(criteria);", "return doUpdate(UPDATE_BY_CRITERIA, makeParam(record, criteria));");
    }

    @Override
    public Method updateAllByCriteria() {
        Method method = signatureFactory.updateAllByCriteria();
        return decorateMethod(method, "notNull(record, \"record must not be null.\");",
                "validateCriteria(criteria);", "return doUpdate(UPDATE_ALL_BY_CRITERIA, makeParam(record, criteria));");
    }

    @Override
    public Method delete() {
        Method method = signatureFactory.delete();
        return decorateMethod(method, "notNull(pk, \"pk must not be null.\");",
                "return doDelete(DELETE_BY_PK, pk);");
    }

    @Override
    public Method deleteByCriteria() {
        Method method = signatureFactory.deleteByCriteria();
        return decorateMethod(method, "validateCriteria(criteria);",
                "return doDelete(DELETE_BY_CRITERIA, criteria);");
    }

    @Override
    public Method doSelectOne() {
        Method method = createProtectedMethod("doSelectOne","T");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("String"), "statement");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("Object"), "parameter");
        method.addParameter(parameter);
        method.addBodyLine("return sqlSessionTemplate.selectOne(namespace() + statement, parameter);");
        return method;
    }

    @Override
    public Method doSelectList() {
        Method method = createProtectedMethod("doSelectList", "List<T>");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("String"), "statement");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("Object"), "parameter");
        method.addParameter(parameter);
        method.addBodyLine("return sqlSessionTemplate.selectList(namespace() + statement, parameter);");
        return method;
    }

    @Override
    public Method doUpdate() {
        Method method = createProtectedMethod("doUpdate", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("String"), "statement");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("Object"), "parameter");
        method.addParameter(parameter);
        method.addBodyLine("return sqlSessionTemplate.update(namespace() + statement, parameter);");
        return method;
    }

    @Override
    public Method doInsert() {
        Method method = createProtectedMethod("doInsert", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("String"), "statement");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("Object"), "parameter");
        method.addParameter(parameter);
        method.addBodyLine("return sqlSessionTemplate.insert(namespace() + statement, parameter);");
        return method;
    }

    @Override
    public Method doDelete() {
        Method method = createProtectedMethod("doDelete", "int");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("String"), "statement");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("Object"), "parameter");
        method.addParameter(parameter);
        method.addBodyLine("return sqlSessionTemplate.delete(namespace() + statement, parameter);");
        return method;
    }

    @Override
    public Method notNull() {
        Method method = createProtectedMethod("notNull", null);
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("Object"), "object");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("String"), "errMsg");
        method.addParameter(parameter);
        method.addBodyLine("if (object == null) {");
        method.addBodyLine("throw new IllegalArgumentException(errMsg);");
        method.addBodyLine("}");
        return method;
    }

    @Override
    public Method validateCriteria() {
        Method method = createProtectedMethod("validateCriteria", null);
        Parameter parameter = new Parameter(
                new FullyQualifiedJavaType("BaseCriteria"), "criteria");
        method.addParameter(parameter);
        method.addBodyLine("notNull(criteria, \"criteria must not be null.\");");
        method.addBodyLine("if (criteria.getOredCriteria().isEmpty()) {");
        method.addBodyLine("throw new IllegalArgumentException(\"criteria must not be empty.\");");
        method.addBodyLine("}");
        return method;
    }

    @Override
    public Method makeParam() {
        Method method = createProtectedMethod("makeParam", "Map<String, Object>");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("B"), "record");
        method.addParameter(parameter);
        parameter = new Parameter(new FullyQualifiedJavaType("BaseCriteria"), "criteria");
        method.addParameter(parameter);
        method.addBodyLine("notNull(record, \"record must not be null.\");");
        method.addBodyLine("validateCriteria(criteria);");
        method.addBodyLine("Map<String, Object> param = new HashMap<>();");
        method.addBodyLine("param.put(\"record\", record);");
        method.addBodyLine("param.put(\"example\", criteria);");
        method.addBodyLine("return param;");
        return method;
    }
}
