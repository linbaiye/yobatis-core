package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;
import org.nalby.yobatis.core.mybatis.method.CriteriaMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CriteriaMethodFactoryImpl;

import java.util.List;

public class TableSpecificCriteria extends TopLevelClass implements YobatisUnit {

    private String pathToPut;

    private TableSpecificCriteria(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.setFinal(true);
        this.setSuperClass("BaseCriteria");
        this.pathToPut = pathToPut;
    }

    public static TableSpecificCriteria newInstance(TopLevelClass origin,
                                                    IntrospectedTable table) {
        FullyQualifiedJavaType thisType = new FullyQualifiedJavaType(
                origin.getType().getFullyQualifiedNameWithoutTypeParameters().replaceFirst("([^.]+)Example$", "criteria.$1Criteria"));
        TableSpecificCriteria criteria = new TableSpecificCriteria(thisType, NamingHelper.glueCriteriaPath(table, thisType));
        CriteriaMethodFactory methodFactory = CriteriaMethodFactoryImpl.getInstance();
        criteria.addMethod(ConstantMethod.ORDER_BY.get());
        criteria.addMethod(methodFactory.setter("Long", "limit", thisType.getFullyQualifiedName()));
        criteria.addMethod(methodFactory.setter("Long", "offset", thisType.getFullyQualifiedName()));
        criteria.addMethod(methodFactory.setter("boolean", "forUpdate", thisType.getFullyQualifiedName()));
        criteria.addMethod(methodFactory.order(thisType.getFullyQualifiedName(), true));
        criteria.addMethod(methodFactory.order(thisType.getFullyQualifiedName(), false));
        criteria.addMethod(methodFactory.or(thisType.getFullyQualifiedName()));

        criteria.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        criteria.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
        Field field = new Field("PROPERTY_TO_COLUMN", new FullyQualifiedJavaType("java.util.Map<String, String>"));
        field.setFinal(true);
        field.setStatic(true);
        field.setVisibility(JavaVisibility.PRIVATE);
        criteria.addField(field);
        InitializationBlock initializationBlock = new InitializationBlock(true);
        initializationBlock.addBodyLine("PROPERTY_TO_COLUMN = new HashMap<>();");
        List<IntrospectedColumn> introspectedColumns =  table.getAllColumns();
        for (IntrospectedColumn column : introspectedColumns) {
            initializationBlock.addBodyLine(String.format("PROPERTY_TO_COLUMN.put(\"%s\", \"%s\");", column.getJavaProperty(), column.getActualColumnName()));
        }
        criteria.addInitializationBlock(initializationBlock);


        for (IntrospectedColumn column : table.getAllColumns()) {
            criteria.getMethods().addAll(methodFactory.criteriaMethodList(column, thisType.getFullyQualifiedName()));
        }
        for (IntrospectedColumn column : table.getAllColumns()) {
            criteria.getMethods().addAll(methodFactory.factoryMethodList(column, thisType.getShortName()));
        }
        for (FullyQualifiedJavaType t: origin.getImportedTypes()) {
            if (!"ArrayList".equals(t.getShortName()) && !"Iterator".equals(t.getShortName())) {
                criteria.addImportedType(t);
            }
        }
        return criteria;
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public void merge(String fileContent) {
    }

}
