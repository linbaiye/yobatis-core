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

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.nalby.yobatis.core.database.YobatisIntrospectedTable;
import org.nalby.yobatis.core.database.YobatisIntrospectedTableImpl;
import org.nalby.yobatis.core.mybatis.clazz.JavaFileFactory;
import org.nalby.yobatis.core.mybatis.clazz.JavaFileFactoryImpl;
import org.nalby.yobatis.core.mybatis.mapper.legacy.LegacyXmlMapper;
import org.nalby.yobatis.core.util.Expect;

import java.util.LinkedList;
import java.util.List;


public class YobatisDaoPlugin extends PluginAdapter {

    private List<GeneratedJavaFile> additionalFiles = new LinkedList<>();

    private List<GeneratedXmlFile> generatedXmlFileList = new LinkedList<>();

    private JavaFileFactory javaFileFactory = JavaFileFactoryImpl.getInstance();

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        generatedXmlFileList.add(LegacyXmlMapper.wrap(document, introspectedTable));
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        Expect.asTrue(introspectedTable.hasPrimaryKeyColumns(),
                "table " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " has no primary key.");
        if (introspectedTable.hasBLOBColumns()) {
            introspectedTable.getBaseColumns().addAll(introspectedTable.getBLOBColumns());
            introspectedTable.getBLOBColumns().clear();
        }
        YobatisIntrospectedTable yobatisIntrospectedTable = YobatisIntrospectedTableImpl.wrap(introspectedTable);
        additionalFiles.add(javaFileFactory.baseCriteria(yobatisIntrospectedTable));
        additionalFiles.add(javaFileFactory.criteria(yobatisIntrospectedTable));

        GeneratedJavaFile domainJavaFile = javaFileFactory.domain(introspectedTable);
        additionalFiles.add(domainJavaFile);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        return generatedXmlFileList;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return additionalFiles;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        GeneratedJavaFile baseDomainJavaFile = javaFileFactory.baseDomain(topLevelClass, introspectedTable);
        additionalFiles.add(baseDomainJavaFile);
        return true;
    }
}

