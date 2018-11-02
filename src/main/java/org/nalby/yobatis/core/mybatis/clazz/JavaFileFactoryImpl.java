package org.nalby.yobatis.core.mybatis.clazz;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.javadoc.ClassJavaDocDecorator;
import org.nalby.yobatis.core.mybatis.javadoc.JavaDocDecorator;

public final class JavaFileFactoryImpl implements JavaFileFactory {


    private final static JavaFileFactoryImpl instance = new JavaFileFactoryImpl();


    private final static JavaDocDecorator<YobatisUnit> docDecorator = ClassJavaDocDecorator.getInstance();

    public static JavaFileFactoryImpl getInstance() {
        return instance;
    }

    private String getDomainProjectName(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration configuration = introspectedTable.getContext().getJavaModelGeneratorConfiguration();
        return configuration.getTargetProject();
    }

    private void addJavaDoc(TopLevelClass topLevelClass) {
        if (topLevelClass instanceof YobatisUnit) {
            docDecorator.decorate((YobatisUnit)topLevelClass);
        }
    }

    @Override
    public GeneratedJavaFile baseDomain(TopLevelClass originalDomainClass, IntrospectedTable introspectedTable) {
        BaseEntity baseEntity = BaseEntity.getInstance(originalDomainClass, introspectedTable);
        addJavaDoc(baseEntity);
        return new GeneratedJavaFile(baseEntity, getDomainProjectName(introspectedTable),
                new DefaultJavaFormatter());
    }

    @Override
    public GeneratedJavaFile domain(IntrospectedTable introspectedTable) {
        Entity entity = Entity.getInstance(introspectedTable);
        addJavaDoc(entity);
        return new GeneratedJavaFile(entity,
                getDomainProjectName(introspectedTable), new DefaultJavaFormatter());
    }

    @Override
    public GeneratedJavaFile baseCriteria(YobatisIntrospectedTable introspectedTable) {
        TopLevelClass base = BaseCriteria.newInstance(introspectedTable);
        return new GeneratedJavaFile(base, "/null", new DefaultJavaFormatter());
    }

    @Override
    public GeneratedJavaFile criteria(YobatisIntrospectedTable introspectedTable) {
        TopLevelClass criteriaClass = Criteria.newInstance(introspectedTable);
        return new GeneratedJavaFile(criteriaClass, "/null", new DefaultJavaFormatter());
    }
}
