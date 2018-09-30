package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.field.FieldFactory;
import org.nalby.yobatis.core.mybatis.field.FieldFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactoryImpl;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoImpl extends TopLevelClass implements YobatisUnit {

    private final static List<ConstantMethod> METHOD_LIST = new ArrayList<>();

    private String existentFile;

    static {
        METHOD_LIST.add(ConstantMethod.DO_SELECT_ONE);
        METHOD_LIST.add(ConstantMethod.DO_SELECT_LIST);
        METHOD_LIST.add(ConstantMethod.DO_UPDATE);
        METHOD_LIST.add(ConstantMethod.DO_INSERT);
        METHOD_LIST.add(ConstantMethod.DO_DELETE);
        METHOD_LIST.add(ConstantMethod.NOT_NULL);
        METHOD_LIST.add(ConstantMethod.VALIDATE_CRITERIA);
        METHOD_LIST.add(ConstantMethod.MAKE_PARAM);
        METHOD_LIST.add(ConstantMethod.INSERT_ALL_IMPL);
        METHOD_LIST.add(ConstantMethod.INSERT_ALL_IGNORE_IMPL);
        METHOD_LIST.add(ConstantMethod.INSERT_IMPL);
        METHOD_LIST.add(ConstantMethod.SELECT_ONE_IMPL);
        METHOD_LIST.add(ConstantMethod.SELECT_ONE_BY_CRITERIA_IMPL);
        METHOD_LIST.add(ConstantMethod.SELECT_LIST_IMPL);
        METHOD_LIST.add(ConstantMethod.COUNT_ALL_IMPL);
        METHOD_LIST.add(ConstantMethod.COUNT_BY_CRITERIA_IMPL);
        METHOD_LIST.add(ConstantMethod.UPDATE_IMPL);
        METHOD_LIST.add(ConstantMethod.UPDATE_ALL_IMPL);
        METHOD_LIST.add(ConstantMethod.UPDATE_BY_CRITERIA_IMPL);
        METHOD_LIST.add(ConstantMethod.UPDATE_ALL_BY_CRITERIA_IMPL);
        METHOD_LIST.add(ConstantMethod.DELETE_IMPL);
        METHOD_LIST.add(ConstantMethod.DELETE_BY_CRITERIA_IMPL);
    }

    private String pathToPut;

    private BaseDaoImpl(String daoPackageName, String pathToPut) {
        super(new FullyQualifiedJavaType(daoPackageName + ".impl.BaseDaoImpl<B, T extends B, PK>"));
        this.addSuperInterface(new FullyQualifiedJavaType(daoPackageName + ".BaseDao<B, T, PK>"));
        this.addImportedType(new FullyQualifiedJavaType(daoPackageName + ".BaseDao"));
        this.setAbstract(true);
        this.pathToPut = pathToPut;
    }

    public static BaseDaoImpl getInstance(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        String path = NamingHelper.glueDaoPath(introspectedTable, config.getTargetPackage() + ".impl.BaseDaoImpl");
        BaseDaoImpl baseDaoImpl = new BaseDaoImpl(config.getTargetPackage(), path);
        FieldFactory fieldFactory = FieldFactoryImpl.getInstance();
        CommonMethodFactory commonMethodFactory = CommonMethodFactoryImpl.getInstance();
        Field field = fieldFactory.protectedField("sqlSessionTemplate", "SqlSessionTemplate");
        field.addAnnotation("@Resource");
        baseDaoImpl.addField(field);
        baseDaoImpl.addMethod(commonMethodFactory.protectedMethod("namespace", "String"));
        for (ConstantMethod constantMethod: METHOD_LIST) {
            Method method = constantMethod.get();
            method.setFinal(true);
            baseDaoImpl.addMethod(method);
            if (method.getVisibility() == JavaVisibility.PUBLIC) {
                method.addAnnotation("@Override");
            }
        }
        baseDaoImpl.addImportedType(new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        baseDaoImpl.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        baseDaoImpl.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        baseDaoImpl.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        baseDaoImpl.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
        baseDaoImpl.addImportedType(NamingHelper.getBaseCriteriaType(introspectedTable));
        return baseDaoImpl;
    }

    @Override
    public String getPathToPut() {
        return pathToPut;
    }

    @Override
    public String getFormattedContent() {
        if (existentFile != null) {
            return existentFile;
        }
        return super.getFormattedContent();
    }

    @Override
    public void merge(String fileContent) {
        existentFile = fileContent;
    }
}
