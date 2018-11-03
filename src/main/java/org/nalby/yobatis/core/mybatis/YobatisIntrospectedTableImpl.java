package org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.nalby.yobatis.core.util.Expect;

import java.util.List;

public class YobatisIntrospectedTableImpl implements YobatisIntrospectedTable {

    private IntrospectedTable wrappedTable;

    private String entityName;

    private String daoPackage;

    private String entityPackage;

    private String daoPackagePath;

    private String entityPackagePath;

    private String mapperPath;

    private FullyQualifiedJavaType primaryKey;

    private boolean autoIncPk;

    private YobatisIntrospectedTableImpl(IntrospectedTable wrappedTable,
                                         String entityName,
                                         String daoPackage,
                                         String entityPackage,
                                         String daoPath,
                                         String entityPath,
                                         String mapperPath,
                                         FullyQualifiedJavaType primaryKey,
                                         boolean autoIncPk) {
        this.wrappedTable = wrappedTable;
        this.entityName = entityName;
        this.daoPackage = daoPackage;
        this.entityPackage = entityPackage;
        this.daoPackagePath = daoPath;
        this.entityPackagePath = entityPath;
        this.mapperPath = mapperPath;
        this.primaryKey = primaryKey;
        this.autoIncPk = autoIncPk;
    }

    @Override
    public IntrospectedTable getWrappedTable() {
        return wrappedTable;
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
    public String getPathForGeneratedFile(ClassType classType) {
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
            case XML_MAPPER:
                return mapperPath + "/" + entityName + "Mapper.xml";
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

    @Override
    public List<IntrospectedColumn> getColumns() {
        return wrappedTable.getAllColumns();
    }

    @Override
    public List<IntrospectedColumn> getPrimaryKeyColumns() {
        return wrappedTable.getPrimaryKeyColumns();
    }

    public static YobatisIntrospectedTableImpl wrap(IntrospectedTable introspectedTable) {
        Expect.asTrue(introspectedTable.hasPrimaryKeyColumns(),
                "table " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " has no primary key.");
        String name = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()).getShortName();
        String daoPackage = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        String daoProject = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetProject();
        String entityPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String entityProject = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject();
        String xmlPackage = introspectedTable.getContext().getSqlMapGeneratorConfiguration().getTargetPackage();
        String xmlProject = introspectedTable.getContext().getSqlMapGeneratorConfiguration().getTargetProject();
        FullyQualifiedJavaType primaryKey = null;
        List<IntrospectedColumn> pkColumns = introspectedTable.getPrimaryKeyColumns();
        introspectedTable.getMyBatis3XmlMapperPackage();
        boolean autoInc = false;
        if (pkColumns.size() == 1) {
            primaryKey = pkColumns.get(0).getFullyQualifiedJavaType();
            autoInc = pkColumns.get(0).isAutoIncrement();
        }
        YobatisIntrospectedTableImpl yobatisTableItem =
                new YobatisIntrospectedTableImpl(introspectedTable, name, daoPackage, entityPackage,
                        daoProject + "/" + daoPackage.replaceAll("\\.", "/"),
                        entityProject+ "/" + entityPackage.replaceAll("\\.", "/"),
                         xmlProject + "/" + xmlPackage.replaceAll("\\.", "/"),
                         primaryKey, autoInc);
        if (primaryKey == null) {
            yobatisTableItem.setPrimaryKey(yobatisTableItem.getFullyQualifiedJavaType(ClassType.BASE_ENTITY));
        }
        return yobatisTableItem;
    }
}
