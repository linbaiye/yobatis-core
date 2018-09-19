package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.CompatibleYobatisUnit;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactory;
import org.nalby.yobatis.core.mybatis.method.CommonMethodFactoryImpl;
import org.nalby.yobatis.core.util.Expect;

public class TableSpecificDaoImpl extends TopLevelClass implements CompatibleYobatisUnit {

    private String pathToPut;

    private String existentFile;

    private boolean compatible;

    private TableSpecificDaoImpl(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.pathToPut = pathToPut;
    }

    public static TableSpecificDaoImpl getInstance(IntrospectedTable table) {
        FullyQualifiedJavaType daoImplType = NamingHelper.getDaoImplType(table);
        TableSpecificDaoImpl tableSpecificDaoImpl = new TableSpecificDaoImpl(daoImplType,
                NamingHelper.glueDaoPath(table, daoImplType));

        FullyQualifiedJavaType dao = NamingHelper.getDaoType(table);
        tableSpecificDaoImpl.addSuperInterface(dao);

        FullyQualifiedJavaType baseDaoImpl = NamingHelper.getBaseDaoImplType(table);
        FullyQualifiedJavaType entity = NamingHelper.getEntityType(table);
        FullyQualifiedJavaType baseEntity = NamingHelper.getBaseEntityType(table);
        FullyQualifiedJavaType pk = NamingHelper.getPrimaryKey(table);
        Expect.notNull(pk, "table does not have primary key " + table.getFullyQualifiedTableNameAtRuntime());
        tableSpecificDaoImpl.setSuperClass(new FullyQualifiedJavaType(baseDaoImpl.getShortName() + "<" + baseEntity.getShortName() +
                ", " + entity.getShortName() + ", " + pk.getShortName() +">"));

        tableSpecificDaoImpl.addImportedType(entity);
        tableSpecificDaoImpl.addImportedType(baseEntity);
        tableSpecificDaoImpl.addImportedType(dao);

        CommonMethodFactory commonMethodFactory = CommonMethodFactoryImpl.getInstance();
        Method method = commonMethodFactory.protectedMethod("namespace", "String");
        method.addAnnotation("@Override");
        method.addBodyLine("return \"" + tableSpecificDaoImpl.getType().getFullyQualifiedName() + ".\";");
        tableSpecificDaoImpl.addMethod(method);

        tableSpecificDaoImpl.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        String shortName = tableSpecificDaoImpl.getType().getShortName();
        String newName = shortName.substring(0, 1).toLowerCase() + shortName.substring(1, shortName.length()).replaceFirst("Impl$", "");
        tableSpecificDaoImpl.addAnnotation("@Component(\"" + newName + "\")");
        return tableSpecificDaoImpl;
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
        if (compatible) {
            return super.getFormattedContent().replaceAll("BaseDao<B, T extends B, PK>", "BaseDao<T extends B, B, PK>");
        }
        return super.getFormattedContent();
    }

    @Override
    public void merge(String fileContent) {
        existentFile = fileContent;
    }

    @Override
    public void inspectBaseDao(String baseDao) {
        if (baseDao != null && baseDao.contains("BaseDao<T extends B, B, PK>")) {
            compatible = true;
        }
    }
}
