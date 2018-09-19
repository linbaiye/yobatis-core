package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.method.ConstantMethod;

import java.util.ArrayList;
import java.util.List;

public class BaseDao extends Interface implements YobatisUnit {

    private final static List<ConstantMethod> METHOD_LIST = new ArrayList<>();

    private String existentFile;

    static {
        METHOD_LIST.add(ConstantMethod.INSERT_ALL_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.INSERT_ALL_IGNORE_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.INSERT_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.SELECT_ONE_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.SELECT_ONE_BY_CRITERIA_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.SELECT_LIST_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.COUNT_ALL_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.COUNT_BY_CRITERIA_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.UPDATE_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.UPDATE_ALL_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.UPDATE_BY_CRITERIA_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.UPDATE_ALL_BY_CRITERIA_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.DELETE_SIGNATURE);
        METHOD_LIST.add(ConstantMethod.DELETE_BY_CRITERIA_SIGNATURE);
    }

    private String pathToPut;

    private BaseDao(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        this.pathToPut = pathToPut;
    }


    public static BaseDao getInstance(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        String daoPackageName = config.getTargetPackage();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(daoPackageName + ".BaseDao<B, T extends B, PK>");
        String path = NamingHelper.glueDaoPath(introspectedTable, type.getFullyQualifiedNameWithoutTypeParameters());
        BaseDao interfaze = new BaseDao(type, path);
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage() + ".criteria.BaseCriteria"));
        for (ConstantMethod constantMethod : METHOD_LIST) {
            interfaze.addMethod(constantMethod.get());
        }
        return interfaze;
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
