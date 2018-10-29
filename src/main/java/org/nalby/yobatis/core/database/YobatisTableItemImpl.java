package org.nalby.yobatis.core.database;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.nalby.yobatis.core.util.Expect;

import java.util.List;

public class YobatisTableItemImpl implements YobatisTableItem {

    private IntrospectedTable wrappedTable;

    private String entityName;

    private String daoPackage;

    private String entityPackage;

    private String projectPath;

    private String daoPackagePath;

    private String entityPackagePath;

    private FullyQualifiedJavaType primaryKey;

    private boolean autoIncPk;

    private YobatisTableItemImpl(IntrospectedTable wrappedTable,
                                 String entityName,
                                 String daoPackage,
                                 String entityPackage,
                                 String projectPath,
                                 FullyQualifiedJavaType primaryKey,
                                 boolean autoIncPk) {
        this.wrappedTable = wrappedTable;
        this.entityName = entityName;
        this.daoPackage = daoPackage;
        this.entityPackage = entityPackage;
        this.projectPath = projectPath;
        this.daoPackagePath = projectPath + "/" + daoPackage.replaceAll("\\.", "/");
        this.entityPackagePath = projectPath + "/" + entityPackage.replaceAll("\\.", "/");
        this.primaryKey = primaryKey;
        this.autoIncPk = autoIncPk;
    }


    @Override
    public FullyQualifiedJavaType getPrimaryKey() {
        return primaryKey;
    }

    private void setPrimaryKey(FullyQualifiedJavaType primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public boolean isAutoIncPrimaryKey() {
        return autoIncPk;
    }

    @Override
    public String getTableName() {
        return wrappedTable.getFullyQualifiedTable().getIntrospectedTableName();
    }

    @Override
    public String getClassPath(ClassType classType) {
        switch (classType) {
            case DAO:
                return daoPackagePath + "/" + entityName + "Dao.java";
            case DAO_IMPL:
                return daoPackagePath + "/impl/" + entityName + "DaoImpl.java";
            case ENTITY:
                return entityPackagePath + "/" + entityName + ".java";
            case BASE_ENTITY:
                return entityPackagePath + "/base/Base" + entityName + ".java";
            case CRITERIA:
                return entityPackagePath + "/criteria/" + entityName + "Criteria.java";
            case BASE_CRITERIA:
                return entityPackagePath + "/criteria/BaseCriteria.java";
            default:
                throw new IllegalArgumentException("Not supported type.");
        }
    }

    @Override
    public String getClassName(ClassType classType) {
        switch (classType) {
            case DAO:
                return entityName + "Dao";
            case DAO_IMPL:
                return entityName + "DaoImpl";
            case ENTITY:
                return entityName;
            case BASE_ENTITY:
                return "Base" + entityName;
            default:
                throw new IllegalArgumentException("Not supported type.");
        }
    }

    @Override
    public FullyQualifiedJavaType getFullyQualifiedJavaType(ClassType classType) {
        switch (classType) {
            case DAO:
                return new FullyQualifiedJavaType(daoPackage+ "." + entityName + "Dao");
            case DAO_IMPL:
                return new FullyQualifiedJavaType(daoPackage+ ".impl." + entityName + "DaoImpl");
            case ENTITY:
                return new FullyQualifiedJavaType(entityPackage + "." + entityName);
            case BASE_ENTITY:
                return new FullyQualifiedJavaType(entityPackage + ".base.Base" + entityName);
            case BASE_CRITERIA:
                return new FullyQualifiedJavaType(entityPackage + ".criteria.BaseCriteria");
            case CRITERIA:
                return new FullyQualifiedJavaType(entityPackage + ".criteria." + entityName + "Criteria");
            default:
                throw new IllegalArgumentException("Not supported type.");
        }
    }

    public static YobatisTableItemImpl wrap(IntrospectedTable introspectedTable) {
        Expect.asTrue(introspectedTable.hasPrimaryKeyColumns(),
                "table " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " has no primary key.");
        String name = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()).getShortName();
        String daoPackage = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        String entityPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String projectPath = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject();
        FullyQualifiedJavaType primaryKey = null;
        List<IntrospectedColumn> pkColumns = introspectedTable.getPrimaryKeyColumns();
        boolean autoInc = false;
        if (pkColumns.size() == 1) {
            primaryKey = pkColumns.get(0).getFullyQualifiedJavaType();
            autoInc = pkColumns.get(0).isAutoIncrement();
        }
        YobatisTableItemImpl yobatisTableItem =
                new YobatisTableItemImpl(introspectedTable, name, daoPackage, entityPackage, projectPath, primaryKey, autoInc);
        if (primaryKey == null) {
            yobatisTableItem.setPrimaryKey(yobatisTableItem.getFullyQualifiedJavaType(ClassType.BASE_ENTITY));
        }
        return yobatisTableItem;
    }
}
