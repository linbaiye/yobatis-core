package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.javadoc.ClassJavaDocDecorator;
import org.nalby.yobatis.core.mybatis.javadoc.JavaDocDecorator;
import org.nalby.yobatis.core.mybatis.method.*;

public final class JavaFileFactoryImpl implements JavaFileFactory {

    private CommonMethodFactory commonMethodFactory;

    private final static JavaFileFactoryImpl instance = new JavaFileFactoryImpl();

    private JavaFileFactoryImpl() {
        commonMethodFactory = CommonMethodFactoryImpl.getInstance();
    }

    private final static JavaDocDecorator<YobatisUnit> docDecorator = ClassJavaDocDecorator.getInstance();

    public static JavaFileFactoryImpl getInstance() {
        return instance;
    }

    private String getDomainProjectName(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration configuration = introspectedTable.getContext().getJavaModelGeneratorConfiguration();
        return configuration.getTargetProject();
    }

    private String getDaoProjectName(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        return config.getTargetProject();
    }

    private void addJavaDoc(TopLevelClass topLevelClass) {
        if (topLevelClass instanceof YobatisUnit) {
            docDecorator.decorate((YobatisUnit)topLevelClass);
        }
    }

    private void addJavaDoc(Interface interfaze) {
        if (interfaze instanceof YobatisUnit) {
            docDecorator.decorate((YobatisUnit)interfaze);
        }
    }


    @Override
    public GeneratedJavaFile baseDaoInterface(IntrospectedTable introspectedTable) {
        BaseDao interfaze = BaseDao.getInstance(introspectedTable);
        addJavaDoc(interfaze);
        String projectName = getDaoProjectName(introspectedTable);
        return new GeneratedJavaFile(interfaze, projectName, introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile baseDaoImpl(IntrospectedTable introspectedTable) {
        String projectName = getDaoProjectName(introspectedTable);
        BaseDaoImpl baseDaoImpl = BaseDaoImpl.getInstance(introspectedTable);
        addJavaDoc(baseDaoImpl);
        return new GeneratedJavaFile(baseDaoImpl, projectName, introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile tableSpecificDaoInterface(IntrospectedTable introspectedTable) {
        TableSpecificDao daoInterfaze = TableSpecificDao.getInstance(introspectedTable);
        addJavaDoc(daoInterfaze);
        return new GeneratedJavaFile(daoInterfaze,
                getDaoProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile tableSpecificDaoImpl(IntrospectedTable introspectedTable) {
        TableSpecificDaoImpl daoImpl = TableSpecificDaoImpl.getInstance(introspectedTable);
        addJavaDoc(daoImpl);
        return new GeneratedJavaFile(daoImpl,
                getDaoProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile baseDomain(TopLevelClass originalDomainClass, IntrospectedTable introspectedTable) {
        BaseEntity baseEntity = BaseEntity.getInstance(originalDomainClass, introspectedTable);
        addJavaDoc(baseEntity);
        return new GeneratedJavaFile(baseEntity, getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile domain(IntrospectedTable introspectedTable) {
        Entity entity = Entity.getInstance(introspectedTable);
        addJavaDoc(entity);
        return new GeneratedJavaFile(entity,
                getDomainProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public GeneratedJavaFile baseCriteria(IntrospectedTable introspectedTable) {
        TopLevelClass base = BaseCriteria.newInstance(introspectedTable);
        addJavaDoc(base);
        return new GeneratedJavaFile(base, getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
    }

    private void addAscOrderBy(TopLevelClass topLevelClass) {
        String name = topLevelClass.getType().getShortName();
        Method method = commonMethodFactory.publicMethod("ascOrderBy", name);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        method.addBodyLine("orderBy(\"asc\", fields);");
        method.addBodyLine("return this;");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Add the 'order by field1 asc, field2 asc, ...' clause to query, only fields in {@code " + name.replaceFirst("Criteria$", "") + "}(not column names) are allowed.");
        method.addJavaDocLine(" * By invoking this method and {@link #descOrderBy(String...) descOrderBy} alternately, a more complex 'order by' clause");
        method.addJavaDocLine(" * can be constructed, shown as below.");
        method.addJavaDocLine(" * <pre>");
        method.addJavaDocLine(" * criteria.ascOrderBy('field1');");
        method.addJavaDocLine(" * criteria.descOrderBy('field2');");
        method.addJavaDocLine(" * -> 'order by field1 asc, field2 desc'");
        method.addJavaDocLine(" * </pre>");
        method.addJavaDocLine(" * @param fields the fields to sort.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if fields is empty, or any of the fields is invalid.");
        method.addJavaDocLine(" * @return this criteria.");
        method.addJavaDocLine(" */");
        topLevelClass.addMethod(method);
    }

    private void addDescOrderBy(TopLevelClass topLevelClass) {
        String name = topLevelClass.getType().getShortName();
        Method method = commonMethodFactory.publicMethod("descOrderBy", name);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        method.addBodyLine("orderBy(\"desc\", fields);");
        method.addBodyLine("return this;");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Add the 'order by field1 desc, field2 desc, ...' clause to query, only fields in {@code " + name.replaceFirst("Criteria$", "") +"}(not column names) are allowed.");
        method.addJavaDocLine(" * By invoking this method and {@link #ascOrderBy(String...) ascOrderBy} alternately, a more complex 'order by' clause");
        method.addJavaDocLine(" * can be constructed, shown as below.");
        method.addJavaDocLine(" * <pre>");
        method.addJavaDocLine(" * criteria.ascOrderBy('field1');");
        method.addJavaDocLine(" * criteria.descOrderBy('field2');");
        method.addJavaDocLine(" * -> 'order by field1 asc, field2 desc'");
        method.addJavaDocLine(" * </pre>");
        method.addJavaDocLine(" * @param fields the fields to sort.");
        method.addJavaDocLine(" * @throws IllegalArgumentException if fields is empty, or any of the fields is invalid.");
        method.addJavaDocLine(" * @return this criteria.");
        method.addJavaDocLine(" */");
        topLevelClass.addMethod(method);
    }

    @Override
    public GeneratedJavaFile criteria(TopLevelClass originalExample, IntrospectedTable introspectedTable) {
        TopLevelClass criteriaClass = TableSpecificCriteria.newInstance(originalExample, introspectedTable);
        addJavaDoc(criteriaClass);
        return new GeneratedJavaFile(criteriaClass,
                getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
    }
}
