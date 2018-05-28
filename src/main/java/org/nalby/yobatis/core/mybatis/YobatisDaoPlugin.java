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
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.*;
import org.nalby.yobatis.core.mybatis.factory.JavaFileFactory;
import org.nalby.yobatis.core.mybatis.factory.JavaFileFactoryImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YobatisDaoPlugin extends PluginAdapter {

    private boolean isBaseDaoGenerated = false;

    private GeneratedJavaFile baseDao;

    private GeneratedJavaFile baseDaoImpl;

    private List<GeneratedJavaFile> additionalFiles = new LinkedList<>();

    private GeneratedJavaFile generateBaseDao(IntrospectedTable introspectedTable) {
        return JavaFileFactoryImpl.getInstance().baseDaoInterface(introspectedTable);
    }

    private FullyQualifiedJavaType getAbstractModel(FullyQualifiedJavaType model) {
        String fullName = model.getFullyQualifiedName();
        String shortName = model.getShortName();
        return new FullyQualifiedJavaType(fullName.replaceFirst("(" + shortName + ")$", "base.Base$1"));
    }

    private FullyQualifiedJavaType getAbstractModel(String model) {
        return getAbstractModel(new FullyQualifiedJavaType(model));
    }

    private Interface addSpecificDaoFile(Interface interfaze, IntrospectedTable introspectedTable) {
        JavaFileFactory javaFileFactory = JavaFileFactoryImpl.getInstance();
        String baseDaoName = baseDao.getCompilationUnit().getType().getFullyQualifiedNameWithoutTypeParameters();
        YobatisJavaFile javaFile = javaFileFactory.tableSpecificDaoInterface(interfaze, introspectedTable, baseDaoName);
        additionalFiles.add(javaFile);
        return (Interface) javaFile.getCompilationUnit();
    }

    private void generatedBaseDao(IntrospectedTable introspectedTable) {
        if (!isBaseDaoGenerated && introspectedTable.hasPrimaryKeyColumns()) {
            baseDao = generateBaseDao(introspectedTable);
            additionalFiles.add(baseDao);
            baseDaoImpl = JavaFileFactoryImpl.getInstance().baseDaoImpl(introspectedTable);
            additionalFiles.add(baseDaoImpl);
            isBaseDaoGenerated = true;
        }
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            Interface daoInterface = addSpecificDaoFile(interfaze, introspectedTable);
            GeneratedJavaFile javaFile = JavaFileFactoryImpl.getInstance().tableSpecificDaoImpl(daoInterface, introspectedTable, baseDaoImpl);
            additionalFiles.add(javaFile);
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

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            additionalFiles.add(JavaFileFactoryImpl.getInstance().criteria(topLevelClass, introspectedTable));
            additionalFiles.add(JavaFileFactoryImpl.getInstance().baseCriteria(topLevelClass, introspectedTable));
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

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        if (introspectedTable.hasPrimaryKeyColumns()) {
            GeneratedJavaFile baseDomainJavaFile = JavaFileFactoryImpl.getInstance().baseDomain(topLevelClass, introspectedTable);
            additionalFiles.add(baseDomainJavaFile);
            GeneratedJavaFile domainJavaFile = JavaFileFactoryImpl.getInstance().domain(topLevelClass, introspectedTable, baseDomainJavaFile);
            additionalFiles.add(domainJavaFile);
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
                            //&& !javaFile.getFileName().contains("BaseDao")
                    && javaFile.getFileName().contains("BaseCriteria")
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

        for (GeneratedJavaFile javaFile : runner.getGeneratedJavaFiles()) {
            if (javaFile.getFileName().contains("Example")) {
                System.out.println(javaFile.getFormattedContent());
            }
        }

        for (GeneratedXmlFile xmlFile: runner.getGeneratedXmlFiles()) {
            //System.out.println(xmlFile.getFormattedContent());
            if (xmlFile.getFileName().contains("Example")) {
            }
        }
    }

}

