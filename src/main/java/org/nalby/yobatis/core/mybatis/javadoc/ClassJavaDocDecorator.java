package org.nalby.yobatis.core.mybatis.javadoc;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.nalby.yobatis.core.mybatis.YobatisUnit;
import org.nalby.yobatis.core.mybatis.clazz.*;

public final class ClassJavaDocDecorator implements JavaDocDecorator<YobatisUnit> {

    private ClassJavaDocDecorator() { }

    private final static ClassJavaDocDecorator decorator = new ClassJavaDocDecorator();

    public static ClassJavaDocDecorator getInstance() {
        return decorator;
    }

    private MethodJavaDocDecoratorFactory methodJavaDocDecoratorFactory =
            MethodJavaDocDecoratorFactory.getInstance();

    private void addDoNotModify(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * Do not modify, it will be overwritten every time yobatis runs.");
        topLevelClass.addJavaDocLine(" */");
    }

    private void addDoNotModify(Interface topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * Do not modify, it will be overwritten every time yobatis runs.");
        topLevelClass.addJavaDocLine(" */");
    }

    private void addSafeToAdd(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * It is safe to add code to this file.");
        topLevelClass.addJavaDocLine(" */");
    }

    private void addSafeToAdd(Interface topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * It is safe to add code to this file.");
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void decorate(YobatisUnit yobatisUnit) {
        if (yobatisUnit instanceof BaseDao) {
            addDoNotModify((Interface) yobatisUnit);
        } else if (yobatisUnit instanceof BaseCriteria ||
                yobatisUnit instanceof BaseDaoImpl ||
                yobatisUnit instanceof BaseEntity ||
                yobatisUnit instanceof TableSpecificCriteria) {
            addDoNotModify((TopLevelClass) yobatisUnit);
        } else if (yobatisUnit instanceof TableSpecificDao) {
            addSafeToAdd((Interface) yobatisUnit);
        } else if (yobatisUnit instanceof TableSpecificDaoImpl) {
            addSafeToAdd((TopLevelClass) yobatisUnit);
        }
        JavaDocDecorator<Method> docDecorator = methodJavaDocDecoratorFactory.build(yobatisUnit);
        if (docDecorator != null) {
            if (yobatisUnit instanceof Interface) {
                for (Method method : ((Interface)yobatisUnit).getMethods()) {
                    //System.out.println(method.getParameters().get(0).getName());
                    docDecorator.decorate(method);
                }
            }
        }
    }
}
