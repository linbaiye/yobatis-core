package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.field.FieldFactory;
import org.nalby.yobatis.core.mybatis.field.FieldFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.LinkedList;
import java.util.List;

public class BaseCriteria extends TopLevelClass implements YobatisUnit {
    private final static List<ConstantMethod> METHOD_LIST = new LinkedList<>();

    static {
        METHOD_LIST.add(ConstantMethod.BASE_CRITERIA_CONSTRUCTOR);
        METHOD_LIST.add(ConstantMethod.GET_ORDER_BY_CLAUSE);
        METHOD_LIST.add(ConstantMethod.IS_DISTINCT);
        METHOD_LIST.add(ConstantMethod.GET_ORED_CRITERIA);
        METHOD_LIST.add(ConstantMethod.CREATE_CRITERIA_INTERNAL);
        METHOD_LIST.add(ConstantMethod.CLEAR);
        METHOD_LIST.add(ConstantMethod.GET_LIMIT);
        METHOD_LIST.add(ConstantMethod.GET_OFFSET);
        METHOD_LIST.add(ConstantMethod.IS_FOR_UPDATE);
        METHOD_LIST.add(ConstantMethod.LAST_CRITERIA);
    }

    private String pathToPut;

    private BaseCriteria(FullyQualifiedJavaType type, String projectPath) {
        super(type);
        this.setAbstract(true);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.pathToPut = projectPath;
    }

    public static BaseCriteria newInstance(IntrospectedTable table) {
        FullyQualifiedJavaType type = NamingHelper.getBaseCriteriaType(table);
        String pathToPut = NamingHelper.glueEntityPath(table, type);
        BaseCriteria clz = new BaseCriteria(type, pathToPut);
        FieldFactory fieldFactory = FieldFactoryImpl.getInstance();
        clz.addField(fieldFactory.protectedField("orderByClause", "String"));
        clz.addField(fieldFactory.protectedField("distinct", "boolean"));
        clz.addField(fieldFactory.protectedField("oredCriteria", "List<BracketCriteria>"));
        clz.addField(fieldFactory.protectedField("limit", "Long"));
        clz.addField(fieldFactory.protectedField("offset", "Long"));
        clz.addField(fieldFactory.protectedField("forUpdate", "boolean"));
        clz.addImportedType("java.util.ArrayList");
        clz.addImportedType("java.util.Date");
        clz.addImportedType("java.util.Iterator");
        clz.addImportedType("java.util.List");
        for (ConstantMethod method : METHOD_LIST) {
            clz.addMethod(method.get());
        }
        clz.addInnerClass(BracketCriteria.newInstance());
        clz.addInnerClass(Criterion.newInstance());
        return clz;
    }

    @Override
    public String getPathToPut() {
        return this.pathToPut;
    }

    @Override
    public void merge(String fileContent) {

    }
}
