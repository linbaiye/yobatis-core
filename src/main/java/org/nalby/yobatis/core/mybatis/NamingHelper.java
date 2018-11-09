package org.nalby.yobatis.core.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.List;

public final class NamingHelper {
    private NamingHelper() {}

    public static FullyQualifiedJavaType getBaseEntityType(FullyQualifiedJavaType entity) {
        String fullName = entity.getFullyQualifiedName();
        String shortName = entity.getShortName();
        return new FullyQualifiedJavaType(fullName.replaceFirst("(" + shortName + ")$", "base.Base$1"));
    }

    public static FullyQualifiedJavaType getBaseEntityType(String entity) {
        return getBaseEntityType(new FullyQualifiedJavaType(entity));
    }

    public static FullyQualifiedJavaType getBaseEntityType(IntrospectedTable introspectedTable) {
        return getBaseEntityType(introspectedTable.getBaseRecordType());
    }

    public static FullyQualifiedJavaType getEntityType(IntrospectedTable introspectedTable) {
        return new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    }

    public static FullyQualifiedJavaType getPrimaryKey(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> pkColumns = introspectedTable.getPrimaryKeyColumns();
        if (pkColumns == null || pkColumns.isEmpty()) {
            return null;
        }
        if (pkColumns.size() == 1) {
            return pkColumns.get(0).getFullyQualifiedJavaType();
        }
        return getBaseEntityType(introspectedTable);
    }

    public static FullyQualifiedJavaType getDaoImplType(IntrospectedTable introspectedTable) {
        String packageName = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        FullyQualifiedJavaType entityType = getEntityType(introspectedTable);
        return new FullyQualifiedJavaType(packageName  + ".impl." + entityType.getShortName() + "DaoImpl");
    }

    public static FullyQualifiedJavaType getBaseCriteriaType(IntrospectedTable introspectedTable) {
        return new FullyQualifiedJavaType( introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage() + ".criteria.BaseCriteria");
    }


    public static String glueEntityPath(IntrospectedTable table, String fullname) {
        String project = table.getContext().getJavaModelGeneratorConfiguration().getTargetProject();
        return project + "/" + fullname.replaceAll("\\.", "/") + ".java";
    }

    public static String glueEntityPath(IntrospectedTable table, FullyQualifiedJavaType type) {
        return glueEntityPath(table, type.getFullyQualifiedName());
    }

    public static boolean isAllKeyTable(IntrospectedTable introspectedTable) {
        return introspectedTable.getAllColumns() != null &&
                introspectedTable.getPrimaryKeyColumns() != null &&
                !introspectedTable.getPrimaryKeyColumns().isEmpty() &&
                introspectedTable.getPrimaryKeyColumns().size() == introspectedTable.getAllColumns().size();
    }

    public static String getDaoFileName(String mapperNamespace) {
        String tokens[] = mapperNamespace.split("\\.");
        return tokens[tokens.length - 1].replaceFirst("Impl", "") + ".java";
    }

    public static String getDaoPackageName(String mapperNamespace) {
        return mapperNamespace.replaceFirst("\\.impl\\.([^.]+)$", "");
    }

    public static String glueMapperNamespace(String daoPackageName, String daoFileName) {
        return daoPackageName + ".impl." + daoFileName.replaceAll("\\.java$", "Impl");
    }

    public static String getMapperFileName(String daoFileName) {
        return daoFileName.replaceAll("Dao\\.java$", "Mapper.xml");
    }
}
