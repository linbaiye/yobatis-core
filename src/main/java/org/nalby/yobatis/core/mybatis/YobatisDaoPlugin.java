package org.nalby.yobatis.core.mybatis;

/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YobatisDaoPlugin extends PluginAdapter {

    private boolean isBaseDaoGenerated = false;

    private GeneratedJavaFile baseDao;

    private GeneratedJavaFile baseDaoImpl;

    private List<GeneratedJavaFile> additionalFiles = new LinkedList<>();

    private static final String OVERRIDE_ANNOTATION = "@Override";

    private static List<String> RESERVED_IDS = Arrays.asList("BASE_RESULT_MAP", "WHERE_CLAUSE",
            "WHERE_CLAUSE_FOR_UPDATE", "BASE_COLUMN_LIST", "selectByCriteria", "selectByPk",
            "deleteByPk", "deleteByCriteria", "insertAll", "insertAllIgnore", "insert",
            "count", "updateByCriteria", "updateAllByCriteria",
            "update", "updateAll");

    private String getDaoPackageName(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        return config.getTargetPackage();
    }


    private String getDaoProjectName(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext().getJavaClientGeneratorConfiguration();
        return config.getTargetProject();
    }


    private String getModelProjectName(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration config = introspectedTable.getContext().getJavaModelGeneratorConfiguration();
        return config.getTargetProject();
    }

    private JavaFormatter getJavaFormatter(IntrospectedTable introspectedTable) {
        return introspectedTable.getContext().getJavaFormatter();
    }

    private GeneratedJavaFile generateBaseDao(IntrospectedTable introspectedTable) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        String projectName = getDaoProjectName(introspectedTable);
        Interface interfaze = new Interface(new FullyQualifiedJavaType(daoPackageName + ".BaseDao<T extends B, B, PK>"));
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage() + ".criteria.BaseCriteria"));
        AbstractDaoMethodFactory factory = DaoMethodSignatureFactory.getInstance();
        interfaze.addMethod(factory.insertAll());
        interfaze.addMethod(factory.insertAllIgnore());
        interfaze.addMethod(factory.insert());
        interfaze.addMethod(factory.selectOne());
        interfaze.addMethod(factory.selectOneByCriteria());
        interfaze.addMethod(factory.selectList());
        interfaze.addMethod(factory.countAll());
        interfaze.addMethod(factory.countByCriteria());
        interfaze.addMethod(factory.update());
        interfaze.addMethod(factory.updateAll());
        interfaze.addMethod(factory.updateByCriteria());
        interfaze.addMethod(factory.updateAllByCriteria());
        interfaze.addMethod(factory.delete());
        interfaze.addMethod(factory.deleteByCriteria());
        addOverwriteComment(interfaze);
        YobatisJavaFile javaFile = new YobatisJavaFile(interfaze, projectName, getJavaFormatter(introspectedTable));
        javaFile.setOverWritable(true);
        return javaFile;
    }


    private void addBaseDaoImplFields(TopLevelClass topLevelClass) {
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
            topLevelClass.addField(field);
        }
        Field field = new Field("sqlSessionTemplate",
                new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        field.addAnnotation("@Resource");
        field.setVisibility(JavaVisibility.PROTECTED);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
    }

    private void addBaseDaoImplMethods(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String name = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        topLevelClass.addImportedType(new FullyQualifiedJavaType(name + ".criteria.BaseCriteria"));
        AbstractDaoMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.insertAll());
        topLevelClass.addMethod(factory.insertAllIgnore());
        topLevelClass.addMethod(factory.insert());
        topLevelClass.addMethod(factory.selectOne());
        topLevelClass.addMethod(factory.selectOneByCriteria());
        topLevelClass.addMethod(factory.selectList());
        topLevelClass.addMethod(factory.countAll());
        topLevelClass.addMethod(factory.countByCriteria());
        topLevelClass.addMethod(factory.update());
        topLevelClass.addMethod(factory.updateAll());
        topLevelClass.addMethod(factory.updateByCriteria());
        topLevelClass.addMethod(factory.updateAllByCriteria());
        topLevelClass.addMethod(factory.delete());
        topLevelClass.addMethod(factory.deleteByCriteria());
    }

    private Method createMethod(String name, boolean isFinal,
                                JavaVisibility visibility, FullyQualifiedJavaType returnType) {
        Method method = new Method(name);
        method.setVisibility(visibility);
        method.setFinal(isFinal);
        method.setReturnType(returnType);
        return method;
    }

    private void addNamespace(TopLevelClass topLevelClass) {
        Method method = createMethod("namespace", false, JavaVisibility.PROTECTED,
                new FullyQualifiedJavaType("String"));
        topLevelClass.addMethod(method);
    }

    private void addDoSelectOne(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.doSelectOne());
    }

    private void addDoSelectList(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.doSelectList());
    }

    private void addDoUpdate(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.doUpdate());
    }

    private void addDoInsert(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.doInsert());
    }

    private void addDoDelete(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.doDelete());
    }

    private void addNotNull(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.notNull());
    }

    private void addBaseDaoImplValidateCriteria(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.validateCriteria());
    }

    private void addBaseDaoImplMakeParam(TopLevelClass topLevelClass) {
        InternalMethodFactory factory = DaoMethodImplFactory.getInstance();
        topLevelClass.addMethod(factory.makeParam());
    }

    private void addBaseDaoImplPropiertaryMethods(TopLevelClass topLevelClass) {
        addNamespace(topLevelClass);
        addDoSelectOne(topLevelClass);
        addDoSelectList(topLevelClass);
        addDoUpdate(topLevelClass);
        addDoInsert(topLevelClass);
        addDoDelete(topLevelClass);
        addNotNull(topLevelClass);
        addBaseDaoImplValidateCriteria(topLevelClass);
        addBaseDaoImplMakeParam(topLevelClass);
        topLevelClass.addImportedType("java.util.List");
        topLevelClass.addImportedType("java.util.Map");
        topLevelClass.addImportedType("java.util.HashMap");
    }



    private GeneratedJavaFile generateBaseDaoImpl(IntrospectedTable introspectedTable) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        TopLevelClass topLevelClass = new TopLevelClass(new FullyQualifiedJavaType(daoPackageName + ".impl.BaseDaoImpl<T extends B, B, PK>"));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType(daoPackageName + ".BaseDao<T, B, PK>"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(daoPackageName + ".BaseDao"));
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.setAbstract(true);
        addBaseDaoImplFields(topLevelClass);
        addBaseDaoImplPropiertaryMethods(topLevelClass);
        addBaseDaoImplMethods(topLevelClass, introspectedTable);
        addOverwriteComment(topLevelClass);
        String projectName = getDaoProjectName(introspectedTable);
        YobatisJavaFile javaFile = new YobatisJavaFile(topLevelClass, projectName, getJavaFormatter(introspectedTable));
        javaFile.setOverWritable(true);
        return javaFile;
    }


    private Interface createExtentingInterface(Interface interfaze, IntrospectedTable introspectedTable) {
        String shortName = interfaze.getType().getShortName();
        String newShortName = shortName.replaceFirst("Mapper$", "Dao");
        String newType = interfaze.getType().getFullyQualifiedName().replaceFirst(shortName, newShortName);
        Interface daoInterfaze = new Interface(newType);
        daoInterfaze.setVisibility(JavaVisibility.PUBLIC);

        String modelName = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(modelName);
        String superName = baseDao.getCompilationUnit().getType().getFullyQualifiedNameWithoutTypeParameters();
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType baseType = getAbstractModel(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        FullyQualifiedJavaType pk = baseType;
        if (columns.size() == 1) {
            pk = columns.get(0).getFullyQualifiedJavaType();
        }
        Interface superInterface = new Interface(superName + "<" + modelType.getShortName() + ", " +
                baseType.getShortName() + ", " + pk.getShortName() +">");
        daoInterfaze.addSuperInterface(superInterface.getType());
        daoInterfaze.addImportedType(modelType);
        return daoInterfaze;
    }


    private FullyQualifiedJavaType getAbstractModel(FullyQualifiedJavaType model) {
        String fullName = model.getFullyQualifiedName();
        String shortName = model.getShortName();
        return new FullyQualifiedJavaType(fullName.replaceFirst("(" + shortName + ")$", "base.Base$1"));
    }

    private FullyQualifiedJavaType getAbstractModel(String model) {
        return getAbstractModel(new FullyQualifiedJavaType(model));
    }

    private void addSafeModifyComment(Interface interfaze) {
        interfaze.addJavaDocLine("/*");
        interfaze.addJavaDocLine(" * It is safe to modify this file.");
        interfaze.addJavaDocLine(" */");
    }

    private void addSafeModifyComment(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * It is safe to modify this file.");
        topLevelClass.addJavaDocLine(" */");
    }


    private Interface addSpecificDaoFile(Interface interfaze, IntrospectedTable introspectedTable) {
        Interface daoInterfaze = createExtentingInterface(interfaze, introspectedTable);
        FullyQualifiedJavaType abstractModel = getAbstractModel(introspectedTable.getBaseRecordType());
        daoInterfaze.addImportedType(abstractModel);
        addSafeModifyComment(daoInterfaze);
        String projectName = getDaoProjectName(introspectedTable);
        JavaFormatter formatter = getJavaFormatter(introspectedTable);
        GeneratedJavaFile javaFile = new YobatisJavaFile(daoInterfaze, projectName, formatter);
        additionalFiles.add(javaFile);
        return daoInterfaze;
    }

    private void generatedBaseDao(IntrospectedTable introspectedTable) {
        if (!isBaseDaoGenerated && introspectedTable.hasPrimaryKeyColumns()) {
            baseDao = generateBaseDao(introspectedTable);
            additionalFiles.add(baseDao);
            baseDaoImpl = generateBaseDaoImpl(introspectedTable);
            additionalFiles.add(baseDaoImpl);
            isBaseDaoGenerated = true;
        }
    }


    private TopLevelClass makeSpecificDaoImpl(Interface daoInterface,
                                              IntrospectedTable introspectedTable) {
        String daoPackageName = getDaoPackageName(introspectedTable);
        TopLevelClass topLevelClass = new TopLevelClass(
                daoPackageName + ".impl." + daoInterface.getType().getShortName() + "Impl");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.setFinal(true);
        topLevelClass.addImportedType(daoInterface.getType());
        topLevelClass.addSuperInterface(daoInterface.getType());

        String modelName = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(modelName);
        String superName = baseDaoImpl.getCompilationUnit().getType().getFullyQualifiedNameWithoutTypeParameters();
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType abstractModel = getAbstractModel(modelType);
        FullyQualifiedJavaType pk = abstractModel;
        if (columns.size() == 1) {
            pk = columns.get(0).getFullyQualifiedJavaType();
        }
        topLevelClass.setSuperClass(new FullyQualifiedJavaType(superName + "<" + modelType.getShortName() +
                ", " + abstractModel.getShortName() + ", " + pk.getShortName() +">"));
        for (FullyQualifiedJavaType type : daoInterface.getImportedTypes()) {
            topLevelClass.addImportedType(type);
        }
        return topLevelClass;
    }


    private void addSpecificDaoImplNamespace(TopLevelClass topLevelClass) {
        Method method = createMethod("namespace", false, JavaVisibility.PROTECTED,
                new FullyQualifiedJavaType("String"));
        method.addAnnotation(OVERRIDE_ANNOTATION);
        method.addBodyLine("return \"" + topLevelClass.getType().getFullyQualifiedName() + ".\";");
        topLevelClass.addMethod(method);
    }

    private GeneratedJavaFile addSpecificDaoImplFile(Interface daoInterface,
                                                     IntrospectedTable introspectedTable) {
        TopLevelClass topLevelClass = makeSpecificDaoImpl(daoInterface, introspectedTable);
        addSpecificDaoImplNamespace(topLevelClass);
        FullyQualifiedJavaType abstractModel = getAbstractModel(introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType(abstractModel);
        String projectName = getDaoProjectName(introspectedTable);
        JavaFormatter formatter = getJavaFormatter(introspectedTable);
        GeneratedJavaFile javaFile = new YobatisJavaFile(topLevelClass, projectName, formatter);
        additionalFiles.add(javaFile);
        addSafeModifyComment(topLevelClass);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        String shortName = topLevelClass.getType().getShortName();
        String newName = shortName.substring(0, 1).toLowerCase() + shortName.substring(1, shortName.length()).replaceFirst("Impl$", "");
        topLevelClass.addAnnotation("@Repository(\"" + newName + "\")");
        return javaFile;
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            Interface daoInterface = addSpecificDaoFile(interfaze, introspectedTable);
            addSpecificDaoImplFile(daoInterface, introspectedTable);
        }
        return true;
    }


    private void mergeBlobColumns(IntrospectedTable introspectedTable) {
        if (introspectedTable.hasBLOBColumns()) {
            introspectedTable.getBaseColumns().addAll(introspectedTable.getBLOBColumns());
            introspectedTable.getBLOBColumns().clear();
        }
    }

    private void renameExample(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getExampleType();
        Pattern pattern = Pattern.compile("([^.]+)Example$");
        Matcher matcher = pattern.matcher(oldType);
        String newType = matcher.replaceFirst("criteria.$1Criteria");
        introspectedTable.setExampleType(newType);
    }


    @FunctionalInterface
    private interface ElementCustomer {
        void custome(XmlElement element);
    }

    private void iterateXmlElements(Document document, ElementCustomer customer) {
        XmlElement root = document.getRootElement();
        for (Element element : root.getElements()) {
            if (element instanceof XmlElement) {
                customer.custome((XmlElement) element);
            }
        }
    }


    private XmlElement findXmlElement(Document document, String id) {
        XmlElement root = document.getRootElement();
        for (Element element : root.getElements()) {
            if (element instanceof XmlElement) {
                List<Attribute> list = ((XmlElement) element).getAttributes();
                for (Attribute attribute : list) {
                    if (attribute.getName().equals("id") &&
                            id.equals(attribute.getValue())) {
                        return (XmlElement)element;
                    }
                }
            }
        }
        return null;
    }

    private void iterateXmlElements(Document document, ElementCustomer customer, String id) {
        XmlElement root = document.getRootElement();
        for (Element element : root.getElements()) {
            if (element instanceof XmlElement) {
                List<Attribute> list = ((XmlElement) element).getAttributes();
                for (Attribute attribute : list) {
                    if (attribute.getName().equals("id") &&
                            id.equals(attribute.getValue())) {
                        customer.custome((XmlElement) element);
                    }
                }
            }
        }
    }

    private void eraseDateComment(Document document) {
        iterateXmlElements(document, (XmlElement parentElement) -> {
            Iterator<Element> iterator = parentElement.getElements().iterator();
            boolean commentStart = false;
            while (iterator.hasNext()) {
                Element element = iterator.next();
                if (element.getFormattedContent(0).contains("<!--")) {
                    commentStart = true;
                } else if (element.getFormattedContent(0).contains("-->")) {
                    return;
                } else if (commentStart && (element.getFormattedContent(0).contains("This element was generated"))) {
                    // Remove this comment since it contains a timestamp which
                    // differs from time to time.
                    iterator.remove();
                }
            }
        });
    }

    private void customInsertAll(Document document, final IntrospectedTable table) {
        iterateXmlElements(document, (XmlElement xmlElement) -> {
            xmlElement.getElements().clear();
            StringBuilder stringBuilder = new StringBuilder();
            xmlElement.addElement(new TextElement("<!--"));
            xmlElement.addElement(new TextElement("  WARNING - @mbg.generated"));
            xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
            xmlElement.addElement(new TextElement("-->"));
            stringBuilder.append("insert into ");
            stringBuilder.append(table.getFullyQualifiedTable().getIntrospectedTableName());
            stringBuilder.append(" (");
            for (int i = 0; i < table.getAllColumns().size(); i++) {
                IntrospectedColumn column = table.getAllColumns().get(i);
                stringBuilder.append(column.getActualColumnName());
                stringBuilder.append(i == table.getAllColumns().size() - 1? ")" : ", ");
                if (i != table.getAllColumns().size() - 1 && (i + 1) % 4 == 0) {
                    xmlElement.addElement(new TextElement(stringBuilder.toString()));
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("  ");
                }
            }
            TextElement textElement = new TextElement(stringBuilder.toString());
            xmlElement.addElement(textElement);
            stringBuilder = new StringBuilder();
            stringBuilder.append("values (");
            for (int i = 0; i < table.getAllColumns().size(); i++) {
                IntrospectedColumn column = table.getAllColumns().get(i);
                stringBuilder.append("#{");
                stringBuilder.append(column.getJavaProperty());
                stringBuilder.append(",jdbcType=");
                stringBuilder.append(column.getJdbcTypeName());
                stringBuilder.append("}");
                stringBuilder.append(i == table.getAllColumns().size() - 1? ")" : ", ");
                if (i != table.getAllColumns().size() - 1 &&  (i + 1) % 4 == 0) {
                    xmlElement.addElement(new TextElement(stringBuilder.toString()));
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("  ");
                }
            }
            textElement = new TextElement(stringBuilder.toString());
            xmlElement.addElement(textElement);
        }, "insertAll");
    }

    private void replaceAttribute(XmlElement xmlElement, Attribute src, Attribute dst) {
        List<Attribute> attributes = xmlElement.getAttributes();
        Iterator<Attribute> iterator = attributes.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Attribute attribute = iterator.next();
            if (src.getName().equals(attribute.getName()) &&
                    src.getValue().equals(attribute.getValue())) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (found) {
            xmlElement.getAttributes().add(dst);
        }
    }

    private boolean isAllKeyTable(IntrospectedTable introspectedTable) {
        return introspectedTable.getAllColumns() != null &&
                introspectedTable.getPrimaryKeyColumns() != null &&
                !introspectedTable.getPrimaryKeyColumns().isEmpty() &&
                introspectedTable.getPrimaryKeyColumns().size() == introspectedTable.getAllColumns().size();
    }

    private void addXmlComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        xmlElement.addElement(new TextElement("  WARNING - @mbg.generated"));
        xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
        xmlElement.addElement(new TextElement("-->"));
    }

    private XmlElement addSelectByPkElement(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "selectByPk"));
        xmlElement.addAttribute(new Attribute("parameterType", getAbstractModel(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        xmlElement.addAttribute(new Attribute("resultMap", "BASE_RESULT_MAP"));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("select"));
        xmlElement.addElement(new TextElement("<include refid=\"BASE_COLUMN_LIST\" />"));
        xmlElement.addElement(new TextElement("from " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        StringBuilder builder = new StringBuilder("where ");
        for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
            IntrospectedColumn column = introspectedTable.getAllColumns().get(i);
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("}");
            if (i < introspectedTable.getAllColumns().size() - 1) {
                builder.append(" and\n\t");
            }
        }
        xmlElement.addElement(new TextElement(builder.toString()));
        document.getRootElement().getElements().add(7, xmlElement);
        return xmlElement;
    }

    private XmlElement addUpdateElement(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "update"));
        xmlElement.addAttribute(new Attribute("parameterType", getAbstractModel(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        XmlElement setElement = new XmlElement("set");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            setElement.addElement(ifElement(column.getJavaProperty() + " != null",
                    column.getActualColumnName() + " = #{" + column.getJavaProperty() + ",jdbcType=" + column.getJdbcTypeName() + "},"));
        }
        xmlElement.addElement(setElement);
        StringBuilder builder = new StringBuilder("where ");
        for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
            IntrospectedColumn column = introspectedTable.getAllColumns().get(i);
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("}");
            if (i < introspectedTable.getAllColumns().size() - 1) {
                builder.append(" and\n\t");
            }
        }
        xmlElement.addElement(new TextElement(builder.toString()));
        document.getRootElement().getElements().add(document.getRootElement().getElements().size(), xmlElement);
        return xmlElement;
    }


    private XmlElement addUpdateAllElement(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "updateAll"));
        xmlElement.addAttribute(new Attribute("parameterType", getAbstractModel(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));
        addXmlComment(xmlElement);
        xmlElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        StringBuilder builder = new StringBuilder("set ");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("},\n\t");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        xmlElement.addElement(new TextElement(builder.toString()));
        builder = new StringBuilder("where ");
        for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
            IntrospectedColumn column = introspectedTable.getAllColumns().get(i);
            builder.append(column.getActualColumnName());
            builder.append(" = #{");
            builder.append(column.getJavaProperty());
            builder.append(",jdbcType=");
            builder.append(column.getJdbcTypeName());
            builder.append("}");
            if (i < introspectedTable.getAllColumns().size() - 1) {
                builder.append(" and\n\t");
            }
        }
        xmlElement.addElement(new TextElement(builder.toString()));
        document.getRootElement().getElements().add(document.getRootElement().getElements().size(), xmlElement);
        return xmlElement;
    }

    private void customeSelectByPk(Document document, IntrospectedTable introspectedTable) {
        //System.out.println(document.getFormattedContent());
        XmlElement xmlElement = findXmlElement(document, "selectByPk");
        replaceAttribute(xmlElement, new Attribute("parameterType", "map"),
                new Attribute("parameterType", introspectedTable.getBaseRecordType()));
    }

    private void customeDeleteByByPk(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = findXmlElement(document, "deleteByPk");
        replaceAttribute(xmlElement, new Attribute("parameterType", "map"),
                new Attribute("parameterType", introspectedTable.getBaseRecordType()));
    }


    private void sqlMapReplaceModelWithBaseModel(Document document,
                                                 IntrospectedTable introspectedTable, String id) {
        XmlElement xmlElement = findXmlElement(document, id);
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        replaceAttribute(xmlElement, new Attribute("parameterType", introspectedTable.getBaseRecordType()),
                new Attribute("parameterType", modelType.getFullyQualifiedName().replaceFirst(
                        "(" + modelType.getShortName() + ")$", "base.Base$1")));
    }


    private void addIgnoreFeature(Document document) {
        XmlElement e = findXmlElement(document, "insertAll");
        XmlElement xmlElement = new XmlElement(e);
        replaceAttribute(xmlElement, new Attribute("id", "insertAll"),
                new Attribute("id", "insertAllIgnore"));
        Iterator<Element> iterator = xmlElement.getElements().iterator();
        int pos = 0;
        String newContent = null;
        while (iterator.hasNext()) {
            Element tmp = iterator.next();
            pos++;
            if (!(tmp instanceof TextElement)) {
                continue;
            }
            TextElement textElement = ((TextElement)tmp);
            if (textElement.getContent().contains("insert into")) {
                newContent = textElement.getContent().replaceFirst("insert into", "insert ignore into");
                iterator.remove();
                break;
            }
        }
        if (newContent != null) {
            xmlElement.getElements().add(pos - 1, new TextElement(newContent));
            document.getRootElement().addElement(9, xmlElement);
        }
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            document.getRootElement().getAttributes().clear();
            Attribute attribute = new Attribute("namespace",
                    introspectedTable.getMyBatis3JavaMapperType().replaceFirst("([^.]+)Mapper$", "impl.$1DaoImpl"));
            document.getRootElement().addAttribute(attribute);
            eraseDateComment(document);
            if (isAllKeyTable(introspectedTable)) {
                addSelectByPkElement(document, introspectedTable);
                addUpdateElement(document, introspectedTable);
                addUpdateAllElement(document, introspectedTable);
            }
            customInsertAll(document, introspectedTable);
            customeSelectByPk(document, introspectedTable);
            customeDeleteByByPk(document, introspectedTable);
            sqlMapReplaceModelWithBaseModel(document, introspectedTable, "deleteByPk");
            sqlMapReplaceModelWithBaseModel(document, introspectedTable, "updateAll");
            sqlMapReplaceModelWithBaseModel(document, introspectedTable, "update");
            sqlMapReplaceModelWithBaseModel(document, introspectedTable, "insert");
            sqlMapReplaceModelWithBaseModel(document, introspectedTable, "insertAll");
            addIgnoreFeature(document);
        }
        return true;
    }

    private void renameXmlElements(IntrospectedTable introspectedTable) {
        introspectedTable.setSelectByExampleStatementId("selectByCriteria");
        introspectedTable.setDeleteByExampleStatementId("deleteByCriteria");
        introspectedTable.setUpdateByExampleStatementId("updateAllByCriteria");
        introspectedTable.setInsertStatementId("insertAll");
        introspectedTable.setInsertSelectiveStatementId("insert");
        introspectedTable.setCountByExampleStatementId("count");
        introspectedTable.setSelectByPrimaryKeyStatementId("selectByPk");
        introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByPk");
        introspectedTable.setUpdateByExampleSelectiveStatementId("updateByCriteria");
        introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("update");
        introspectedTable.setUpdateByPrimaryKeyStatementId("updateAll");
        introspectedTable.setBaseColumnListId("BASE_COLUMN_LIST");
        if (introspectedTable.hasBLOBColumns()) {
            introspectedTable.setResultMapWithBLOBsId("BASE_RESULT_MAP");
        }
        introspectedTable.setBaseResultMapId("BASE_RESULT_MAP");
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId("WHERE_CLAUSE_FOR_UPDATE");
        introspectedTable.setExampleWhereClauseId("WHERE_CLAUSE");
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        renameXmlElements(introspectedTable);
        renameExample(introspectedTable);
        mergeBlobColumns(introspectedTable);
        generatedBaseDao(introspectedTable);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return additionalFiles;
    }

    private Field buildProtectedField(String name, String fqName) {
        Field field = new Field(name, new FullyQualifiedJavaType(fqName));
        field.setVisibility(JavaVisibility.PROTECTED);
        return field;
    }

    private Method buildMethod(String prefix, String name, String type) {
        String s1 = name.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + name.substring(1);
        Method method = new Method(prefix + nameCapitalized);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(type));
        return method;
    }

    private Method buildGetter(String name, String returnFqType) {
        Method method = buildMethod("get", name, returnFqType);
        method.addBodyLine("return " + name + ";");
        return method;
    }

    private Method buildSetter(String name, String returnFqType,
                               String paramType) {
        Method method = buildMethod("set", name, returnFqType);
        method.addParameter(
                new Parameter(new FullyQualifiedJavaType(paramType), name));
        method.addBodyLine("this." + name + " = " + name + ";");
        return method;
    }

    private void appendClear(TopLevelClass topLevelClass, String bodyLine) {
        for (Method method : topLevelClass.getMethods()) {
            if ("clear".equals(method.getName())) {
                method.addBodyLine(bodyLine);
            }
        }
    }

    private void appendClear(TopLevelClass topLevelClass) {
        appendClear(topLevelClass, "limit = null;");
        appendClear(topLevelClass, "offset = null;");
        appendClear(topLevelClass, "forUpdate = null;");
    }

    private void addPaging(TopLevelClass topLevelClass,
                           IntrospectedTable introspectedTable) {
        topLevelClass.addField(buildProtectedField("limit", "java.lang.Long"));
        topLevelClass.addField(buildProtectedField("offset", "java.lang.Long"));
        topLevelClass.addField(buildProtectedField("forUpdate", "java.lang.Boolean"));

        Method method = buildSetter("limit", "void", "java.lang.Long");
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        method.addBodyLine("return this;");
        topLevelClass.addMethod(method);
        topLevelClass.addMethod(buildGetter("limit", "java.lang.Long"));

        method = buildSetter("offset", "void", "java.lang.Long");
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        method.addBodyLine("return this;");
        topLevelClass.addMethod(method);
        topLevelClass.addMethod(buildGetter("offset", "java.lang.Long"));

        method = buildSetter("forUpdate", "void", "java.lang.Boolean");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Set true to append 'for update' clause to this query.");
        method.addJavaDocLine(" */");
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        method.addBodyLine("return this;");
        topLevelClass.addMethod(method);
        topLevelClass.addMethod(buildGetter("forUpdate", "java.lang.Boolean"));
        appendClear(topLevelClass);
    }


    private void adjustOrMethod(TopLevelClass topLevelClass) {
        for (Method method : topLevelClass.getMethods()) {
            if (!method.getName().equals("or") || !method.getParameters().isEmpty()) {
                continue;
            }
            method.setReturnType(topLevelClass.getType());
            method.getBodyLines().clear();
            method.addBodyLine("oredCriteria.add(createCriteriaInternal());");
            method.addBodyLine("return this;");
        }
    }


    private void addJavaDoc(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * A " + topLevelClass.getType().getShortName() + " provides methods to construct 'where', 'limit', 'offset', 'for update'");
        topLevelClass.addJavaDocLine(
                " * clauses. Although building 'limit', 'offset', 'for update' and simple 'where' clauses is pretty");
        topLevelClass.addJavaDocLine(" * intuitive, a complex 'where' clause requires a little bit more attention.");
        topLevelClass.addJavaDocLine(
                " * <p>A complex 'where' consists of multiple expressions that are ORed together, such as <br>");
        topLevelClass.addJavaDocLine(" * {@code (id = 1 and field = 2) or (filed <= 3) or ( ... ) ...}");
        topLevelClass.addJavaDocLine(" * <p>Suppose we had a Book model which has author and name fields,");
        topLevelClass.addJavaDocLine(
                " * here is an example that utilizes BookCriteria to build a where clause of<br>");
        topLevelClass.addJavaDocLine(
                " * {@code (author = \"Some author\" and name = \"Some book\") or (name not in (\"hated ones\", \"boring ones\"))}");
        topLevelClass.addJavaDocLine(" * <pre>");
        topLevelClass.addJavaDocLine(" * BookCriteria.authorEqualTo(\"Some author\")");
        topLevelClass.addJavaDocLine(" * .andNameEqualTo(\"Some book\")");
        topLevelClass.addJavaDocLine(" * .or()");
        topLevelClass.addJavaDocLine(" * .andNameNotIn(Arrays.asList(\"hated ones\", \"boring ones\"));");
        topLevelClass.addJavaDocLine(" * </pre>");
        topLevelClass.addJavaDocLine(" */");
    }

    private void removeJavadoc(TopLevelClass topLevelClass) {
        for (Method method : topLevelClass.getMethods()) {
            method.getJavaDocLines().clear();
        }
        for (org.mybatis.generator.api.dom.java.Field field : topLevelClass.getFields()) {
            field.getJavaDocLines().clear();
        }

        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            innerClass.getJavaDocLines().clear();
        }
    }

    private void moveFields(TopLevelClass sub, TopLevelClass base) {
        Iterator<Field> iterator = sub.getFields().iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            if (field.getName().equals("PROPERTY_TO_COLUMN")) {
                continue;
            }
            base.addField(field);
            iterator.remove();
        }
    }


    private void moveMethods(TopLevelClass sub, TopLevelClass base) {
        Iterator<Method> iterator = sub.getMethods().iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            if (!method.isStatic() &&
                    !sub.getType().equals(method.getReturnType()) &&
                    !method.getName().equals("orderBy")) {
                iterator.remove();
                if (method.isConstructor()) {
                    method.setName("BaseCriteria");
                }
                base.addMethod(method);
            }
        }
        base.addImportedType("java.util.ArrayList");
    }

    private void moveInnerClasses(TopLevelClass sub, TopLevelClass base) {
        Iterator<InnerClass> iterator = sub.getInnerClasses().iterator();
        while (iterator.hasNext()) {
            InnerClass innerClass = iterator.next();
            base.addInnerClass(innerClass);
            iterator.remove();
        }
    }

    private void addTimeAndDateMethods(InnerClass innerClass) {
        Method method = createMethod("addCriterionForJDBCDate", false, JavaVisibility.PUBLIC, null);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Date(value.getTime()), property);");
        innerClass.addMethod(method);

        method = createMethod("addCriterionForJDBCDate", false, JavaVisibility.PUBLIC, null);
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

        method = createMethod("addCriterionForJDBCDate", false, JavaVisibility.PUBLIC, null);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value1"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value2"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);");
        innerClass.addMethod(method);

        method = createMethod("addCriterionForJDBCTime", false, JavaVisibility.PUBLIC, null);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Time(value.getTime()), property);");
        innerClass.addMethod(method);


        method = createMethod("addCriterionForJDBCTime", false, JavaVisibility.PUBLIC, null);
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


        method = createMethod("addCriterionForJDBCTime", false, JavaVisibility.PUBLIC, null);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "condition"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value1"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Date"), "value2"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "property"));
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);");
        innerClass.addMethod(method);

    }


    private void createBaseCriteria(TopLevelClass topLevelClass,
                                    IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType supType = topLevelClass.getType();
        TopLevelClass base = new TopLevelClass(supType.getPackageName() + ".BaseCriteria");
        Iterator<FullyQualifiedJavaType> iterator = topLevelClass.getImportedTypes().iterator();
        while (iterator.hasNext()) {
            FullyQualifiedJavaType type = iterator.next();
            if (type.getShortName().equals("ArrayList")) {
                iterator.remove();
            }
        }
        base.setAbstract(true);
        base.setVisibility(JavaVisibility.PUBLIC);
        base.addImportedType("java.util.List");
        moveFields(topLevelClass, base);
        moveMethods(topLevelClass, base);
        moveInnerClasses(topLevelClass, base);
        topLevelClass.setSuperClass(base.getType());
        String projectName = getModelProjectName(introspectedTable);
        JavaFormatter formatter = getJavaFormatter(introspectedTable);
        YobatisJavaFile baseCriteria = new YobatisJavaFile(base, projectName, formatter);
        base.addImportedType(new FullyQualifiedJavaType("java.util.Date"));
        base.addImportedType(new FullyQualifiedJavaType("java.util.Iterator"));
        base.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
        additionalFiles.add(baseCriteria);
    }

    private void addOverwriteComment(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * Do NOT modify, it will be overwrote every time yobatis runs.");
        topLevelClass.addJavaDocLine(" */");
    }

    private void addOverwriteComment(Interface interfaze) {
        interfaze.addJavaDocLine("/*");
        interfaze.addJavaDocLine(" * Do NOT modify, it will be overwrote every time yobatis runs.");
        interfaze.addJavaDocLine(" */");
    }

    private void removeImportedType(TopLevelClass topLevelClass, String name) {
        Iterator<FullyQualifiedJavaType> iterator = topLevelClass.getImportedTypes().iterator();
        while (iterator.hasNext()) {
            FullyQualifiedJavaType javaType = iterator.next();
            if (javaType.getFullyQualifiedName().equals(name)) {
                iterator.remove();
            }
        }
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            removeJavadoc(topLevelClass);
            addPaging(topLevelClass, introspectedTable);
            ascdentCriteria(topLevelClass, introspectedTable);
            fixOrderBy(topLevelClass, introspectedTable);
            addStaticConstructor(topLevelClass);
            adjustOrMethod(topLevelClass);
            addOverwriteComment(topLevelClass);
            addJavaDoc(topLevelClass);
            clearInnerCriteriaMethods(topLevelClass);
            createBaseCriteria(topLevelClass, introspectedTable);
            removeImportedType(topLevelClass, "java.util.Iterator");
            YobatisJavaFile javaFile = new YobatisJavaFile(topLevelClass,
                    context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    context.getJavaFormatter());
            javaFile.setOverWritable(true);
            additionalFiles.add(javaFile);
        }
        return true;
    }

    private XmlElement ifElement(String testClause, String text) {
        XmlElement xmlElement = new XmlElement("if");
        Attribute attribute = new Attribute("test", testClause);
        xmlElement.addAttribute(attribute);
        TextElement textElement = new TextElement(text);
        xmlElement.addElement(textElement);
        return xmlElement;
    }


    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        element.addElement(ifElement("limit != null", "limit #{limit}"));
        element.addElement(ifElement("offset != null", "offset #{offset}"));
        element.addElement(ifElement("forUpdate != null and forUpdate == true", "for update"));
        return true;
    }


    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        return sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }


    private void addLastCriteriaMethod(TopLevelClass topLevelClass,
                                       FullyQualifiedJavaType returnType) {
        Method newMethod = new Method("lastCriteria");
        newMethod.setVisibility(JavaVisibility.PROTECTED);
        newMethod.setReturnType(returnType);
        newMethod.addBodyLine("if (oredCriteria.isEmpty()) {");
        newMethod.addBodyLine("oredCriteria.add(createCriteriaInternal());");
        newMethod.addBodyLine("}");
        newMethod.addBodyLine("return oredCriteria.get(oredCriteria.size() - 1);");
        topLevelClass.addMethod(newMethod);
    }

    private void depcrateCreateCriteria(TopLevelClass topLevelClass) {
        Method method = findMethod(topLevelClass, "createCriteria");
        method.addAnnotation("@Deprecated");
    }

    private Method proxyMethod(Method sourceMethod, TopLevelClass topLevelClass) {
        Method method = new Method(sourceMethod);
        method.setReturnType(topLevelClass.getType());
        String firstLine = sourceMethod.getBodyLines().get(0);
        method.getBodyLines().clear();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lastCriteria().");
        stringBuilder.append(firstLine);
        method.addBodyLine(stringBuilder.toString());
        method.addBodyLine("return this;");
        return method;
    }

    private void clearInnerCriteriaMethods(TopLevelClass topLevelClass) {
        List<InnerClass> classes = topLevelClass.getInnerClasses();
        for (InnerClass clazz: classes) {
            if ("GeneratedCriteria".equals(clazz.getType().getShortName())) {
                Iterator<Method> iterator = clazz.getMethods().iterator();
                while (iterator.hasNext())  {
                    Method method = iterator.next();
                    if ((method.getName().startsWith("and") &&
                            "Criteria".equals(method.getReturnType().getShortName())) ||
                            "addCriterionForJDBCDate".equals(method.getName()) ||
                            "addCriterionForJDBCTime".equals(method.getName()) ) {
                        iterator.remove();
                    }
                    if (method.getName().equals("addCriterion")) {
                        method.setVisibility(JavaVisibility.PUBLIC);
                    }
                }

                addTimeAndDateMethods(clazz);
            }
        }
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

    private void buildColumnMap(TopLevelClass topLevelClass, IntrospectedTable table) {
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


    private Method findMethod(TopLevelClass topLevelClass, String methodName) {
        Iterator<Method> iterator = topLevelClass.getMethods().iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            if (methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    private final static Pattern PATTERN = Pattern.compile("and(.{1})(.+)");
    private void addStaticConstructor(TopLevelClass topLevelClass) {
        List<Method> newMethods = new LinkedList<Method>();
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


    private void addOrderBy(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = findMethod(topLevelClass, "setOrderByClause");
        method.getParameters().clear();
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " order"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("orderBy");
        method.getBodyLines().clear();
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
    }


    private void appendMethodAfter(TopLevelClass topLevelClass, String name, Method method) {
        for (int i = 0; i < topLevelClass.getMethods().size(); i++) {
            Method tmp = topLevelClass.getMethods().get(i);
            if (name.equals(tmp.getName())) {
                topLevelClass.getMethods().add(i + 1, method);
                break;
            }
        }
    }


    private void addAscOrderBy(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method("ascOrderBy");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(topLevelClass.getType());
        String name = topLevelClass.getType().getShortName();
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
        appendMethodAfter(topLevelClass, "orderBy", method);
    }

    private void addDescOrderBy(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method("descOrderBy");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(topLevelClass.getType());
        method.addParameter(new Parameter(new FullyQualifiedJavaType("String "), " ... fields"));
        String name = topLevelClass.getType().getShortName();
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
        appendMethodAfter(topLevelClass, "ascOrderBy", method);
    }



    private void fixOrderBy(TopLevelClass topLevelClass,
                            IntrospectedTable introspectedTable) {
        buildColumnMap(topLevelClass, introspectedTable);
        addOrderBy(topLevelClass, introspectedTable);
        addAscOrderBy(topLevelClass, introspectedTable);
        addDescOrderBy(topLevelClass, introspectedTable);
    }


    private void ascdentCriteria(TopLevelClass topLevelClass,
                                 IntrospectedTable introspectedTable) {
        InnerClass criteriaClass = null;
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) {
                criteriaClass = innerClass;
                break;
            }
        }
        addLastCriteriaMethod(topLevelClass, criteriaClass.getType());
        depcrateCreateCriteria(topLevelClass);
        for (Method method : criteriaClass.getMethods()) {
            if (method.getReturnType() != null &&
                    "Criteria".equals(method.getReturnType().getShortName())) {
                topLevelClass.addMethod(proxyMethod(method, topLevelClass));
            }
        }
        replaceRuntimeException(topLevelClass);
    }

    private void generateCopy(TopLevelClass topLevelClass) {
        Method method = createMethod("copy", false, JavaVisibility.PUBLIC,
                topLevelClass.getType());
        method.addParameter(new Parameter(topLevelClass.getType(), "dest"));
        method.addBodyLine("if (dest == null) {");
        method.addBodyLine("throw new NullPointerException(\"dest must not be null.\");");
        method.addBodyLine("}");
        for (Field field : topLevelClass.getFields()) {
            method.addBodyLine("dest." + field.getName() + " = this." + field.getName() + ";");
        }
        method.addBodyLine("return dest;");
        method.addAnnotation("/**");
        method.addAnnotation(" * Copy properties of this object to {@code dest} object.");
        method.addAnnotation(" * @param dest the object to copy properties to.");
        method.addAnnotation(" * @return the dest object.");
        method.addAnnotation(" */");
        topLevelClass.addMethod(method);
    }

    private void generateToString(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("toString"); //$NON-NLS-1$
        method.addAnnotation("@Override");
        method.addBodyLine("StringBuilder sb = new StringBuilder();"); //$NON-NLS-1$
        method.addBodyLine("sb.append(getClass().getSimpleName());"); //$NON-NLS-1$
        method.addBodyLine("sb.append(\"[\");"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        boolean needComma = false;
        for (Field field : topLevelClass.getFields()) {
            String property = field.getName();
            sb.setLength(0);
            sb.append("sb.append(\"");
            if (needComma) {
                sb.append(", ");
            }
            sb.append(property) //$NON-NLS-1$ //$NON-NLS-2$
                    .append("=\")").append(".append(").append(property) //$NON-NLS-1$ //$NON-NLS-2$
                    .append(");"); //$NON-NLS-1$
            needComma = true;
            method.addBodyLine(sb.toString());
        }

        method.addBodyLine("sb.append(\"]\");"); //$NON-NLS-1$
        method.addBodyLine("return sb.toString();"); //$NON-NLS-1$
        topLevelClass.addMethod(method);
    }

    private void generateBaseAndSubDoamins(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String name = topLevelClass.getType().getFullyQualifiedName();
        String shortName = topLevelClass.getType().getShortName();
        name = name.replace(shortName, "base.Base" + shortName);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType(name));

        TopLevelClass baseClass = new TopLevelClass(new FullyQualifiedJavaType(name));
        baseClass.setAbstract(true);
        baseClass.setVisibility(JavaVisibility.PUBLIC);
        for (Field field : topLevelClass.getFields()) {
            field.setVisibility(JavaVisibility.PROTECTED);
            baseClass.addField(field);
        }
        topLevelClass.getFields().clear();

        for (Method method : topLevelClass.getMethods()) {
            baseClass.addMethod(method);
        }
        topLevelClass.getMethods().clear();

        for (FullyQualifiedJavaType type : topLevelClass.getImportedTypes()) {
            baseClass.addImportedType(type);
        }
        topLevelClass.getImportedTypes().clear();
        topLevelClass.addImportedType(baseClass.getType());

        baseClass.addJavaDocLine("/*");
        baseClass.addJavaDocLine(" * This class corresponds to the table '" + introspectedTable.getFullyQualifiedTable() + "', and is generated by MyBatis Generator.");
        baseClass.addJavaDocLine(" * Do NOT modify as it will be overwrote every time MyBatis Generator runs, put your code into");
        baseClass.addJavaDocLine(" * " + shortName + " instead.");
        baseClass.addJavaDocLine(" */");

        generateToString(baseClass);
        generateCopy(baseClass);

        JavaModelGeneratorConfiguration configuration = context.getJavaModelGeneratorConfiguration();
        YobatisJavaFile yobatisJavaFil = new YobatisJavaFile(baseClass,
                configuration.getTargetProject(), context.getJavaFormatter());
        yobatisJavaFil.setOverWritable(true);
        additionalFiles.add(yobatisJavaFil);
        GeneratedJavaFile javaFile = new YobatisJavaFile(topLevelClass,
                configuration.getTargetProject(), context.getJavaFormatter());
        additionalFiles.add(javaFile);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            removeJavadoc(topLevelClass);
            generateBaseAndSubDoamins(topLevelClass, introspectedTable);
        }
        return true;
    }

    private final static String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n" +
            "\n" +
            "<generatorConfiguration>\n" +
            "  <classPathEntry location=\"/Users/lintao/.m2/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar\"/>\n" +
            "  <context id=\"yobatis\" targetRuntime=\"MyBatis3\">\n" +
            "    <plugin type=\"org.nalby.yobatis.core.mybatis.YobatisDaoPlugin\"/>\n" +
            "    <jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8\" userId=\"mybatis\" password=\"mybatis\"/>\n" +
            "    <!--jdbcConnection driverClass=\"com.mysql.jdbc.Driver\" connectionURL=\"jdbc:mysql://localhost:3306/book_store?characterEncoding=utf-8\" userId=\"root\" password=\"root\"/-->\n" +
            "    <javaTypeResolver>\n" +
            "      <property name=\"forceBigDecimals\" value=\"false\"/>\n" +
            "    </javaTypeResolver>\n" +
            "    <javaModelGenerator targetPackage=\"model\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <sqlMapGenerator targetPackage=\"mybatis-mappers/autogen\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"dao\" targetProject=\"/Users/lintao/Study/java/workspace/generator/core/mybatis-generator-core\"/>\n" +
            "    <!--table tableName=\"customer\" schema=\"mybatis\" modelType=\"flat\">\n" +
            " 		<generatedKey column=\"id\" sqlStatement=\"mysql\" identity=\"true\"/>" +
            "    </table>\n" +
            "    <table tableName=\"compound_key_table\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "    <table tableName=\"string_key_table\" schema=\"mybatis\" modelType=\"flat\"/-->\n" +
            "    <table tableName=\"book\" schema=\"mybatis\" modelType=\"flat\"/>\n" +
            "  </context>\n" +
            "</generatorConfiguration>\n";

    public static void main(String[] args) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        MybatisGeneratorRunner runner = new MybatisGeneratorRunner();
        runner.parse(inputStream);
        List<GeneratedJavaFile> javaFiles = runner.getGeneratedJavaFiles();
        for (GeneratedJavaFile javaFile : javaFiles) {
            if (
				/*!javaFile.getFileName().endsWith("Criteria.java") &&
				!javaFile.getFileName().endsWith("DaoImpl.java") &&
				!javaFile.getFileName().endsWith("Dao.java") &&
				!javaFile.getFileName().endsWith("Mapper.java")*/
                //javaFile.getFileName().endsWith("Criteria.java") &&
                    javaFile instanceof YobatisJavaFile
                            && javaFile.getFileName().contains("Base")
                //&& (javaFile.getFileName().endsWith("DaoImpl.java") ||
                //javaFile.getFileName().endsWith("Dao.java"))
				/*&& javaFile.getFileName().endsWith("Dao.java")
				 && javaFile.getFileName().endsWith("Criteria.java")*/
                    ) {
                System.out.println(javaFile.getFormattedContent());
                YobatisJavaFile file = (YobatisJavaFile) javaFile;
                //System.out.println(javaFile.getFormattedContent());
            }
        }

        for (GeneratedXmlFile xmlFile: runner.getGeneratedXmlFiles()) {
            //System.out.println(xmlFile.getFormattedContent());
            if (xmlFile.getFileName().equals("BaseDaoImpl.java")) {
            }
        }
    }

}

