package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.nalby.yobatis.core.mybatis.CompatibleYobatisUnit;
import org.nalby.yobatis.core.mybatis.NamingHelper;
import org.nalby.yobatis.core.util.Expect;

public class TableSpecificDao extends Interface implements CompatibleYobatisUnit {

    private String pathToPut;

    private String existentFile;

    private boolean compatible;

    private TableSpecificDao(FullyQualifiedJavaType type, String pathToPut) {
        super(type);
        this.setVisibility(JavaVisibility.PUBLIC);
        this.pathToPut = pathToPut;
    }

    public static TableSpecificDao getInstance(IntrospectedTable table) {

        FullyQualifiedJavaType pk = NamingHelper.getPrimaryKey(table);
        Expect.notNull(pk, "table " + table.getFullyQualifiedTableNameAtRuntime() + " does not have primary key.");
        FullyQualifiedJavaType baseEntity = NamingHelper.getBaseEntityType(table);
        FullyQualifiedJavaType entity = NamingHelper.getEntityType(table);
        FullyQualifiedJavaType baseDao = NamingHelper.getBaseDaoType(table);
        FullyQualifiedJavaType daoType = NamingHelper.getDaoType(table);

        TableSpecificDao tableSpecificDao = new TableSpecificDao(daoType, NamingHelper.glueDaoPath(table, daoType));
        Interface supInterface = new Interface(baseDao.getShortName() + "<" + baseEntity.getShortName() + ", " +
                 entity.getShortName() + ", " + pk.getShortName() +">");

        tableSpecificDao.addSuperInterface(supInterface.getType());
        tableSpecificDao.addImportedType(baseEntity);
        tableSpecificDao.addImportedType(entity);
        tableSpecificDao.addImportedType(baseDao);
        return tableSpecificDao;
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
