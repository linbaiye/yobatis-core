package org.nalby.yobatis.core.mybatis.factory;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.nalby.yobatis.core.mybatis.YobatisJavaFile;

import java.awt.image.ImagingOpException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFileFactoryImpl implements JavaFileFactory {

    private AbstractDaoMethodFactory signatureFactory;

    private AbstractDaoInternalMethodFactory internalMethodFactory;


    private AbstractDomainMethodFactory domainMethodFactory;

    private AbstractCommonMethodFactory commonMethodFactory;

    private final static JavaFileFactoryImpl instance = new JavaFileFactoryImpl();

    private final static String OVERRIDE_ANNOTATION = "@Override";

    private JavaFileFactoryImpl() {
        signatureFactory = DaoMethodSignatureFactory.getInstance();
        internalMethodFactory = DaoMethodImplFactory.getInstance();
        domainMethodFactory = DomainMethodFactoryImpl.getInstance();
        commonMethodFactory = CommonMethodFactoryImpl.getInstance();
    }

    public static JavaFileFactoryImpl getInstance() {
        return instance;
    }

    private String getDomainProjectName(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration configuration = introspectedTable.getContext().getJavaModelGeneratorConfiguration();
        return configuration.getTargetProject();
    }

    private String getDaoPackageName(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        return config.getTargetPackage();
    }

    private String getDaoProjectName(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        return config.getTargetProject();
    }

    private void addDoNotOverwriteComment(TopLevelClass domainClass) {
        domainClass.addJavaDocLine("/*");
        domainClass.addJavaDocLine(" * Do NOT modify, it will be overwrote every time yobatis runs.");
        domainClass.addJavaDocLine(" */");
    }

    private void addDoNotOverwriteComment(Interface interfaze) {
        interfaze.addJavaDocLine("/*");
        interfaze.addJavaDocLine(" * Do NOT modify, it will be overwrote every time yobatis runs.");
        interfaze.addJavaDocLine(" */");
    }

    private void addSafeAddingMethodsComment(Interface interfaze) {
        interfaze.addJavaDocLine("/*");
        interfaze.addJavaDocLine(" * It is safe to add methods.");
        interfaze.addJavaDocLine(" */");
    }

    private void addSafeAddingMethodsComment(TopLevelClass domainClass) {
        domainClass.addJavaDocLine("/*");
        domainClass.addJavaDocLine(" * It is safe to add methods.");
        domainClass.addJavaDocLine(" */");
    }

    private Field buildProtectedField(String name, String fqName) {
        Field field = new Field(name, new FullyQualifiedJavaType(fqName));
        field.setVisibility(JavaVisibility.PROTECTED);
        return field;
    }


    private FullyQualifiedJavaType getBaseModelType(FullyQualifiedJavaType model) {
        String fullName = model.getFullyQualifiedName();
        String shortName = model.getShortName();
        return new FullyQualifiedJavaType(fullName.replaceFirst("(" + shortName + ")$", "base.Base$1"));
    }

    private FullyQualifiedJavaType getBaseModelType(String model) {
        return getBaseModelType(new FullyQualifiedJavaType(model));
    }

    @Override
    public YobatisJavaFile baseDaoInterface(IntrospectedTable introspectedTable) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        String projectName = getDaoProjectName(introspectedTable);
        Interface interfaze = new Interface(new FullyQualifiedJavaType(daoPackageName + ".BaseDao<T extends B, B, PK>"));
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage() + ".criteria.BaseCriteria"));
        interfaze.addMethod(signatureFactory.insertAll());
        interfaze.addMethod(signatureFactory.insertAllIgnore());
        interfaze.addMethod(signatureFactory.insert());
        interfaze.addMethod(signatureFactory.selectOne());
        interfaze.addMethod(signatureFactory.selectOneByCriteria());
        interfaze.addMethod(signatureFactory.selectList());
        interfaze.addMethod(signatureFactory.countAll());
        interfaze.addMethod(signatureFactory.countByCriteria());
        interfaze.addMethod(signatureFactory.update());
        interfaze.addMethod(signatureFactory.updateAll());
        interfaze.addMethod(signatureFactory.updateByCriteria());
        interfaze.addMethod(signatureFactory.updateAllByCriteria());
        interfaze.addMethod(signatureFactory.delete());
        interfaze.addMethod(signatureFactory.deleteByCriteria());
        addDoNotOverwriteComment(interfaze);
        YobatisJavaFile javaFile = new YobatisJavaFile(interfaze, projectName, introspectedTable.getContext().getJavaFormatter());
        javaFile.setOverWritable(true);
        return javaFile;
    }

    private void addBaseDaoImplFields(TopLevelClass domainClass) {
        String[] names = {
                "SELECT_BY_PK", "SELECT_BY_CRITERIA", "COUNT",
                "INSERT_ALL", "INSERT_ALL_IGNORE", "INSERT",
                "DELETE_BY_PK", "DELETE_BY_CRITERIA",
                "UPDATE", "UPDATE_ALL", "UPDATE_BY_CRITERIA", "UPDATE_ALL_BY_CRITERIA"
        };
        String[] values = {
                "selectByPk", "selectByCriteria", "count",
                "insertAll", "insertAllIgnore", "insert",
                "deleteByPk", "deleteByCriteria",
                "update", "updateAll", "updateByCriteria", "updateAllByCriteria"
        };
        for (int i = 0; i < names.length; i++) {
            Field field = new Field(names[i], new FullyQualifiedJavaType("String"));
            field.setFinal(true);
            field.setStatic(true);
            field.setVisibility(JavaVisibility.PRIVATE);
            field.setInitializationString("\"" + values[i] + "\"");
            domainClass.addField(field);
        }
        Field field = new Field("sqlSessionTemplate",
                new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        field.addAnnotation("@Resource");
        field.setVisibility(JavaVisibility.PROTECTED);
        domainClass.addField(field);
        domainClass.addImportedType(new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        domainClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
    }

    private void addBaseDaoImplMethods(TopLevelClass domainClass, IntrospectedTable introspectedTable) {
        String name = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        domainClass.addImportedType(new FullyQualifiedJavaType(name + ".criteria.BaseCriteria"));
        AbstractDaoMethodFactory factory = DaoMethodImplFactory.getInstance();
        domainClass.addMethod(factory.insertAll());
        domainClass.addMethod(factory.insertAllIgnore());
        domainClass.addMethod(factory.insert());
        domainClass.addMethod(factory.selectOne());
        domainClass.addMethod(factory.selectOneByCriteria());
        domainClass.addMethod(factory.selectList());
        domainClass.addMethod(factory.countAll());
        domainClass.addMethod(factory.countByCriteria());
        domainClass.addMethod(factory.update());
        domainClass.addMethod(factory.updateAll());
        domainClass.addMethod(factory.updateByCriteria());
        domainClass.addMethod(factory.updateAllByCriteria());
        domainClass.addMethod(factory.delete());
        domainClass.addMethod(factory.deleteByCriteria());
    }

    private void addBaseDaoImplProprietaryMethods(TopLevelClass domainClass) {
        Method method = commonMethodFactory.protectedMethod("namespace", "String");
        domainClass.addMethod(method);
        domainClass.addMethod(internalMethodFactory.doSelectOne());
        domainClass.addMethod(internalMethodFactory.doSelectList());
        domainClass.addMethod(internalMethodFactory.doUpdate());
        domainClass.addMethod(internalMethodFactory.doInsert());
        domainClass.addMethod(internalMethodFactory.doDelete());
        domainClass.addMethod(internalMethodFactory.notNull());
        domainClass.addMethod(internalMethodFactory.validateCriteria());
        domainClass.addMethod(internalMethodFactory.makeParam());
        domainClass.addImportedType("java.util.List");
        domainClass.addImportedType("java.util.Map");
        domainClass.addImportedType("java.util.HashMap");
    }

    @Override
    public YobatisJavaFile baseDaoImpl(IntrospectedTable introspectedTable) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        TopLevelClass domainClass = new TopLevelClass(new FullyQualifiedJavaType(daoPackageName + ".impl.BaseDaoImpl<T extends B, B, PK>"));
        domainClass.addSuperInterface(new FullyQualifiedJavaType(daoPackageName + ".BaseDao<T, B, PK>"));
        domainClass.addImportedType(new FullyQualifiedJavaType(daoPackageName + ".BaseDao"));
        domainClass.setVisibility(JavaVisibility.PUBLIC);
        domainClass.setAbstract(true);
        addBaseDaoImplFields(domainClass);
        addBaseDaoImplProprietaryMethods(domainClass);
        addBaseDaoImplMethods(domainClass, introspectedTable);
        addDoNotOverwriteComment(domainClass);
        String projectName = getDaoProjectName(introspectedTable);
        YobatisJavaFile javaFile = new YobatisJavaFile(domainClass, projectName, introspectedTable.getContext().getJavaFormatter());
        javaFile.setOverWritable(true);
        return javaFile;
    }

    @Override
    public YobatisJavaFile tableSpecificDaoInterface(Interface interfaze, IntrospectedTable introspectedTable, String baseDaoTypeName) {
        // XXXJavaMapper -> XXXJavaDao
        String shortName = interfaze.getType().getShortName();
        String newShortName = shortName.replaceFirst("Mapper$", "Dao");
        String newType = interfaze.getType().getFullyQualifiedName().replaceFirst(shortName, newShortName);
        Interface daoInterfaze = new Interface(newType);
        daoInterfaze.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType baseType = getBaseModelType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        FullyQualifiedJavaType pk = baseType;
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        if (columns.size() == 1) {
            pk = columns.get(0).getFullyQualifiedJavaType();
        }

        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        daoInterfaze.addImportedType(modelType);

        Interface superInterface = new Interface(baseDaoTypeName + "<" + modelType.getShortName() + ", " +
                baseType.getShortName() + ", " + pk.getShortName() +">");
        daoInterfaze.addSuperInterface(superInterface.getType());

        FullyQualifiedJavaType abstractModelType = getBaseModelType(introspectedTable.getBaseRecordType());
        daoInterfaze.addImportedType(abstractModelType);
        addSafeAddingMethodsComment(daoInterfaze);
        return new YobatisJavaFile(daoInterfaze, getDaoProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public YobatisJavaFile tableSpecificDaoImpl(Interface specificDaoInterface, IntrospectedTable introspectedTable, GeneratedJavaFile baseDaoImpl) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        TopLevelClass domainClass = new TopLevelClass(
                daoPackageName + ".impl." + specificDaoInterface.getType().getShortName() + "Impl");
        domainClass.setVisibility(JavaVisibility.PUBLIC);
        domainClass.setFinal(true);
        domainClass.addImportedType(specificDaoInterface.getType());
        domainClass.addSuperInterface(specificDaoInterface.getType());

        String modelName = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(modelName);
        String superName = baseDaoImpl.getCompilationUnit().getType().getFullyQualifiedNameWithoutTypeParameters();
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType abstractModel = getBaseModelType(modelType);
        FullyQualifiedJavaType pk = abstractModel;
        if (columns.size() == 1) {
            pk = columns.get(0).getFullyQualifiedJavaType();
        }
        domainClass.setSuperClass(new FullyQualifiedJavaType(superName + "<" + modelType.getShortName() +
                ", " + abstractModel.getShortName() + ", " + pk.getShortName() +">"));
        for (FullyQualifiedJavaType type : specificDaoInterface.getImportedTypes()) {
            domainClass.addImportedType(type);
        }
        domainClass.addImportedType(getBaseModelType(introspectedTable.getBaseRecordType()));


        Method method = commonMethodFactory.protectedMethod("namespace", "String");
        method.addAnnotation(OVERRIDE_ANNOTATION);
        method.addBodyLine("return \"" + domainClass.getType().getFullyQualifiedName() + ".\";");
        domainClass.addMethod(method);
        addSafeAddingMethodsComment(domainClass);

        domainClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        String shortName = domainClass.getType().getShortName();
        String newName = shortName.substring(0, 1).toLowerCase() + shortName.substring(1, shortName.length()).replaceFirst("Impl$", "");
        domainClass.addAnnotation("@Repository(\"" + newName + "\")");
        return new YobatisJavaFile(domainClass, getDaoProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    @Override
    public YobatisJavaFile baseDomain(TopLevelClass originalDomainClass, IntrospectedTable introspectedTable) {
        String name = originalDomainClass.getType().getFullyQualifiedName();
        String shortName = originalDomainClass.getType().getShortName();
        name = name.replace(shortName, "base.Base" + shortName);
        TopLevelClass baseClass = new TopLevelClass(new FullyQualifiedJavaType(name));
        baseClass.setAbstract(true);
        baseClass.setVisibility(JavaVisibility.PUBLIC);
        for (Field field : originalDomainClass.getFields()) {
            Field field1 = new Field(field);
            field1.setVisibility(JavaVisibility.PROTECTED);
            field1.getJavaDocLines().clear();
            field1.getAnnotations().clear();
            baseClass.addField(field1);
        }

        for (Method method : originalDomainClass.getMethods()) {
            Method method1 = new Method(method);
            method1.getAnnotations().clear();
            method1.getJavaDocLines().clear();
            baseClass.addMethod(method1);
        }

        for (FullyQualifiedJavaType type : originalDomainClass.getImportedTypes()) {
            baseClass.addImportedType(type);
        }
        baseClass.addJavaDocLine("/*");
        baseClass.addJavaDocLine(" * This class corresponds to the table '" + introspectedTable.getFullyQualifiedTable() + "', and is generated by MyBatis Generator.");
        baseClass.addJavaDocLine(" * Do NOT modify as it will be overwrote every time MyBatis Generator runs, put your code into");
        baseClass.addJavaDocLine(" * " + shortName + " instead.");
        baseClass.addJavaDocLine(" */");

        baseClass.addMethod(domainMethodFactory.createToString(baseClass));
        baseClass.addMethod(domainMethodFactory.createCopy(baseClass));
        YobatisJavaFile yobatisJavaFile = new YobatisJavaFile(baseClass, getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
        yobatisJavaFile.setOverWritable(true);
        return yobatisJavaFile;
    }

    @Override
    public YobatisJavaFile domain(TopLevelClass originalDomain, IntrospectedTable introspectedTable, GeneratedJavaFile baseDomainJavaFile) {
        TopLevelClass domainClass = new TopLevelClass(originalDomain.getType());
        domainClass.setVisibility(JavaVisibility.PUBLIC);
        TopLevelClass baseDomainClass = (TopLevelClass)baseDomainJavaFile.getCompilationUnit();
        domainClass.setSuperClass(baseDomainClass.getType());
        domainClass.addImportedType(baseDomainClass.getType());
        domainClass.addJavaDocLine("/*");
        domainClass.addJavaDocLine(" * It is safe to add methods and fields.");
        domainClass.addJavaDocLine(" */");
        return new YobatisJavaFile(domainClass,
                getDomainProjectName(introspectedTable), introspectedTable.getContext().getJavaFormatter());
    }

    private void copyFields(TopLevelClass sub, TopLevelClass base) {
        for (Field field : sub.getFields()) {
            if (field.getName().equals("PROPERTY_TO_COLUMN")) {
                continue;
            }
            field.getJavaDocLines().clear();
            base.addField(field);
        }
    }

    private void copyExampleClassMethods(TopLevelClass sub, TopLevelClass base) {
        for (Method method : sub.getMethods()) {
            if (method.getName().startsWith("set") ||
               ("or".equals(method.getName()) && method.getParameters().isEmpty())) {
                continue;
            }
            if (method.isConstructor()) {
                method.setName("BaseCriteria");
            } else if (method.getName().equals("createCriteriaInternal")) {
                method.getBodyLines().clear();
                method.addBodyLine("return new Criteria();");
            }
            method.getJavaDocLines().clear();
            base.addMethod(method);
        }
    }

    private void addKeyword(TopLevelClass base, String key, String type) {
        base.addField(buildProtectedField(key, type));
        Method method = commonMethodFactory.getter(key, type);
        base.addMethod(method);
        for (Method m: base.getMethods()) {
            if ("clear".equals(m.getName())) {
                m.addBodyLine(key + " = null;");
            }
        }
    }

    private void addLastCriteriaMethod(TopLevelClass topLevelClass) {
        Method method = commonMethodFactory.protectedMethod("lastCriteria", "GeneratedCriteria");
        method.addBodyLine("if (oredCriteria.isEmpty()) {");
        method.addBodyLine("oredCriteria.add(createCriteriaInternal());");
        method.addBodyLine("}");
        method.addBodyLine("return oredCriteria.get(oredCriteria.size() - 1);");
        topLevelClass.addMethod(method);
    }

    private InnerClass findInnerClassByName(String name, TopLevelClass topLevelClass) {
        for (InnerClass tmp : topLevelClass.getInnerClasses()) {
            if (tmp.getType().getShortName().equalsIgnoreCase(name)) {
                return tmp;
            }
        }
        return null;
    }

    private void deleteFirstMethodByName(TopLevelClass topLevelClass, String methodName) {
        for (Iterator<Method> iterator = topLevelClass.getMethods().iterator(); iterator.hasNext();) {
            Method method = iterator.next();
            if (method.getName().equals(methodName)) {
                iterator.remove();
                break;
            }
        }
    }

    private void addTemporalMethods(TopLevelClass baseCriteria) {
        InnerClass innerClass = findInnerClassByName("GeneratedCriteria", baseCriteria);
        if (innerClass == null) {
            return;
        }
        Method method = commonMethodFactory.publicMethod("addCriterionForJDBCDate", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Date(value.getTime()), property);");
        innerClass.addMethod(method);

        method = commonMethodFactory.publicMethod("addCriterionForJDBCDate", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<Date>"), "values"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (values == null || values.size() == 0) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value list for \" + property + \" cannot be null or empty\");");
        method.addBodyLine("}");
        method.addBodyLine("List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();");
        method.addBodyLine("Iterator<Date> iter = values.iterator();");
        method.addBodyLine("while (iter.hasNext()) {");
        method.addBodyLine("    dateList.add(new java.sql.Date(iter.next().getTime()));");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, dateList, property);");
        innerClass.addMethod(method);

        method = commonMethodFactory.publicMethod("addCriterionForJDBCDate", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value1"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value2"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);");
        innerClass.addMethod(method);

        method = commonMethodFactory.publicMethod("addCriterionForJDBCTime", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Time(value.getTime()), property);");
        innerClass.addMethod(method);

        method = commonMethodFactory.publicMethod("addCriterionForJDBCTime", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<Date>"), "values"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (values == null || values.size() == 0) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value list for \" + property + \" cannot be null or empty\");");
        method.addBodyLine("}");
        method.addBodyLine("List<java.sql.Time> dateList = new ArrayList<java.sql.Time>();");
        method.addBodyLine("Iterator<Date> iter = values.iterator();");
        method.addBodyLine("while (iter.hasNext()) {");
        method.addBodyLine("    dateList.add(new java.sql.Time(iter.next().getTime()));");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, dateList, property);");
        innerClass.addMethod(method);

        method = commonMethodFactory.publicMethod("addCriterionForJDBCTime", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Date"), "value1"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Date"), "value2"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);");
        innerClass.addMethod(method);
    }

    private void replaceRuntimeException(TopLevelClass topLevelClass) {
        List<InnerClass> classes = topLevelClass.getInnerClasses();
        for (InnerClass clazz: classes) {
            for (Method method : clazz.getMethods()) {
                List<String> lines = new LinkedList<>();
                for (String line : method.getBodyLines()) {
                    lines.add(line.replace("RuntimeException", "IllegalArgumentException"));
                }
                method.getBodyLines().clear();
                method.getBodyLines().addAll(lines);
            }
        }
    }

    private void addOrMethod(TopLevelClass topLevelClass) {
        Method method = commonMethodFactory.publicMethod("or", topLevelClass.getType().getShortName());
        method.addBodyLine("oredCriteria.add(createCriteriaInternal());");
        method.addBodyLine("return this;");
        topLevelClass.addMethod(method);
    }

    private void modifyInnerClasses(TopLevelClass topLevelClass, TopLevelClass base) {
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            innerClass.getJavaDocLines().clear();
            innerClass.getMethods().removeIf((Method method) ->
                    method.getName().startsWith("and") && method.getReturnType().getShortName().equals("Criteria"));
            for (Method method : innerClass.getMethods()) {
                if ("addCriterion".equals(method.getName())) {
                    method.setVisibility(JavaVisibility.PUBLIC);
                }
            }
            base.addInnerClass(innerClass);
        }
    }

    @Override
    public YobatisJavaFile baseCriteria(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.getType().getFullyQualifiedName();
        FullyQualifiedJavaType supType = topLevelClass.getType();
        TopLevelClass base = new TopLevelClass(supType.getPackageName() + ".BaseCriteria");
        base.setAbstract(true);
        base.setVisibility(JavaVisibility.PUBLIC);
        base.addImportedType("java.util.List");
        base.addImportedType("java.util.Date");
        base.addImportedType("java.util.Iterator");
        base.addImportedType("java.util.ArrayList");
        modifyInnerClasses(topLevelClass, base);
        copyFields(topLevelClass, base);
        copyExampleClassMethods(topLevelClass, base);
        addKeyword(base, "limit", "java.lang.Long");
        addKeyword(base, "offset", "java.lang.Long");
        addKeyword(base, "forUpdate", "java.lang.Boolean");
        addLastCriteriaMethod(base);
        replaceRuntimeException(base);
        addTemporalMethods(base);
        YobatisJavaFile javaFile = new YobatisJavaFile(base, getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
        javaFile.setOverWritable(true);
        return javaFile;
    }


    private void addColumnMap(TopLevelClass topLevelClass, IntrospectedTable table) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
        Field field = new Field("PROPERTY_TO_COLUMN", new FullyQualifiedJavaType("java.util.Map<String, String>"));
        field.setFinal(true);
        field.setStatic(true);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        InitializationBlock initializationBlock = new InitializationBlock(true);
        initializationBlock.addBodyLine("PROPERTY_TO_COLUMN = new HashMap<String, String>();");
        List<IntrospectedColumn> introspectedColumns =  table.getAllColumns();
        for (IntrospectedColumn column : introspectedColumns) {
            initializationBlock.addBodyLine(String.format("PROPERTY_TO_COLUMN.put(\"%s\", \"%s\");", column.getJavaProperty(), column.getActualColumnName()));
        }
        topLevelClass.addInitializationBlock(initializationBlock);
    }

    private final static Pattern PATTERN = Pattern.compile("and(.)(.+)");
    private void addConstructorMethods(TopLevelClass topLevelClass) {
        List<Method> newMethods = new LinkedList<>();
        for (Method m: topLevelClass.getMethods()) {
            if (!m.getName().startsWith("and") ||
                    !m.getReturnType().getFullyQualifiedName().equals(topLevelClass.getType().getFullyQualifiedName())) {
                continue;
            }
            Matcher matcher = PATTERN.matcher(m.getName());
            if (!matcher.find()) {
                continue;
            }
            String name = matcher.group(1).toLowerCase() + matcher.group(2);
            Method method = new Method(m);
            method.getBodyLines().clear();
            method.setName(name);
            method.setStatic(true);
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(topLevelClass.getType());
            StringBuilder stringBuilder = new StringBuilder("return new ");
            stringBuilder.append(topLevelClass.getType().getShortName());
            stringBuilder.append("().");
            stringBuilder.append(m.getName());
            stringBuilder.append("(");
            boolean hasParam = false;
            for (Parameter parameter : m.getParameters()) {
                stringBuilder.append(parameter.getName());
                stringBuilder.append(", ");
                hasParam = true;
            }
            if (hasParam) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.append(");");
            method.addBodyLine(stringBuilder.toString());
            newMethods.add(method);
        }
        for (Method method : newMethods) {
            topLevelClass.addMethod(method);
        }
    }

    private void copyClauseMethodsToContainingClass(TopLevelClass originalExample, TopLevelClass dst) {
        InnerClass innerClass = findInnerClassByName("GeneratedCriteria", originalExample);
        if (innerClass == null) {
            return;
        }
        for (Method sourceMethod : innerClass.getMethods()) {
            if (sourceMethod.getReturnType() != null &&
                    "Criteria".equals(sourceMethod.getReturnType().getShortName())) {
                Method method = new Method(sourceMethod);
                method.setReturnType(originalExample.getType());
                String firstLine = sourceMethod.getBodyLines().get(0);
                method.getBodyLines().clear();
                method.addBodyLine("lastCriteria()." + firstLine);
                method.addBodyLine("return this;");
                dst.addMethod(method);
            }
        }
    }

    private void addOrderByMethod(TopLevelClass topLevelClass) {
        Method method = commonMethodFactory.privateMethod("orderBy", "void");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "order"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "fields", true));
        method.addBodyLine("if ( fields == null || fields.length == 0) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Empty fields passed.\");");
        method.addBodyLine("}");
        method.addBodyLine("StringBuilder stringBuilder = new StringBuilder();");
        method.addBodyLine("if (orderByClause != null) {");
        method.addBodyLine("stringBuilder.append(orderByClause);");
        method.addBodyLine("stringBuilder.append(',');");
        method.addBodyLine("}");
        method.addBodyLine("for (String field : fields) {");
        method.addBodyLine("if (!PROPERTY_TO_COLUMN.containsKey(field)) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Unrecognizable field:\" + field);");
        method.addBodyLine("}");
        method.addBodyLine("stringBuilder.append(PROPERTY_TO_COLUMN.get(field));");
        method.addBodyLine("stringBuilder.append(\" \");");
        method.addBodyLine("stringBuilder.append(order);");
        method.addBodyLine("stringBuilder.append(',');");
        method.addBodyLine("}");
        method.addBodyLine("stringBuilder.deleteCharAt(stringBuilder.length() - 1);");
        method.addBodyLine("orderByClause = stringBuilder.toString();");
        topLevelClass.addMethod(method);
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

    private TopLevelClass copyClassWithoutInnerClassesAndFields(TopLevelClass src) {
        TopLevelClass dst = new TopLevelClass(src.getType());
        dst.setVisibility(src.getVisibility());
        dst.setAbstract(src.isAbstract());
        dst.setFinal(src.isFinal());
        for (Method method : src.getMethods()) {
            if (method.getName().equals("getOredCriteria") ||
                    method.getName().equals("isDistinct") ||
                    method.getName().equals("getOrderByClause") ||
                    method.getName().equals("clear") ||
                    method.getName().equals("createCriteriaInternal") ||
                    method.getName().equals("createCriteria") ||
                    method.getName().equals("or") ||
                    method.isConstructor()) {
                continue;
            }
            Method newMethod = new Method(method);
            newMethod.getJavaDocLines().clear();
            if ("setDistinct".equals(newMethod.getName())) {
                newMethod.setReturnType(src.getType());
                newMethod.addBodyLine("return this;");
            }
            dst.addMethod(newMethod);
        }
        return dst;
    }

    private void addSetClauzeMethod(TopLevelClass criteriaClass, String name, String type) {
        Method method = commonMethodFactory.setter(name, type);
        method.setReturnType(criteriaClass.getType());
        method.addBodyLine("return this;");
        criteriaClass.addMethod(method);
    }


    @Override
    public YobatisJavaFile criteria(TopLevelClass originalExample, IntrospectedTable introspectedTable) {
        TopLevelClass criteriaClass = copyClassWithoutInnerClassesAndFields(originalExample);
        criteriaClass.setSuperClass("BaseCriteria");
        addOrderByMethod(criteriaClass);
        addAscOrderBy(criteriaClass);
        addDescOrderBy(criteriaClass);
        addOrMethod(criteriaClass);
        addSetClauzeMethod(criteriaClass, "limit", "Long");
        addSetClauzeMethod(criteriaClass, "offset", "Long");
        addSetClauzeMethod(criteriaClass, "forUpdate", "Boolean");
        addColumnMap(criteriaClass, introspectedTable);
        copyClauseMethodsToContainingClass(originalExample, criteriaClass);
        addConstructorMethods(criteriaClass);
        deleteFirstMethodByName(criteriaClass, "setOrderByClause");
        addDoNotOverwriteComment(criteriaClass);
        for (FullyQualifiedJavaType type: originalExample.getImportedTypes()) {
            if (!"ArrayList".equals(type.getShortName())) {
                criteriaClass.addImportedType(type);
            }
        }
        YobatisJavaFile yobatisJavaFile = new YobatisJavaFile(criteriaClass, getDomainProjectName(introspectedTable),
                introspectedTable.getContext().getJavaFormatter());
        yobatisJavaFile.setOverWritable(true);
        return yobatisJavaFile;
    }
}
