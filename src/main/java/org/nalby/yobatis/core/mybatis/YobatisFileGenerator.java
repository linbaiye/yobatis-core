package org.nalby.yobatis.core.mybatis;

/**
 *    Copyright 2006-2017 the original author or authors.
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
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.log.LogFactory;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps MyBatisGenerator and po
 */
public class YobatisFileGenerator {

    private MyBatisGenerator myBatisGenerator;

    private Project project;

    private Logger logger = LogFactory.getLogger(YobatisFileGenerator.class);

    private YobatisFileGenerator(MyBatisGenerator myBatisGenerator, Project project) {
        this.myBatisGenerator = myBatisGenerator;
        this.project = project;
    }


    private void mergeFile(YobatisUnit unit) {
        String filePath = unit.getPathToPut();
        File file = project.findFile(filePath);
        if (file == null) {
            return;
        }
        try (InputStream inputStream = file.open()) {
            String content = TextUtil.asString(inputStream);
            unit.merge(content);
        } catch (InvalidUnitException e) {
            logger.error("File {} is broken and Yobatis is unable to merge it, please fix it and retry, error:{}.", file.path(), e.getMessage());
            throw new InvalidMybatisGeneratorConfigException(e);
        } catch (IOException e) {
            logger.error("Failed to read file {}.", file.path());
            throw new InvalidMybatisGeneratorConfigException(e);
        }
    }

    private void mergeFiles() {
        for (GeneratedXmlFile xml: myBatisGenerator.getGeneratedXmlFiles()) {
            if (xml instanceof YobatisUnit) {
                mergeFile((YobatisUnit) xml);
            }
        }
        for (GeneratedJavaFile tmp : myBatisGenerator.getGeneratedJavaFiles()) {
            CompilationUnit unit = tmp.getCompilationUnit();
            if (unit instanceof YobatisUnit) {
                mergeFile((YobatisUnit) unit);
            }
        }
    }

    private void writeFile(YobatisUnit unit) {
        String filePath = unit.getPathToPut();
        File file = project.createFile(filePath);
        file.write(unit.getFormattedContent());
    }

    private void writeFiles() {
        for (GeneratedXmlFile xml: myBatisGenerator.getGeneratedXmlFiles()) {
            if (xml instanceof YobatisUnit) {
                writeFile((YobatisUnit) xml);
            }
        }
        for (GeneratedJavaFile tmp : myBatisGenerator.getGeneratedJavaFiles()) {
            CompilationUnit unit = tmp.getCompilationUnit();
            if (unit instanceof YobatisUnit) {
                writeFile((YobatisUnit) unit);
            }
        }
    }

    public void writeAll() {
        mergeFiles();
        writeFiles();
        logger.info("Files have been generated, happy coding.");
    }

    public static YobatisFileGenerator parse(InputStream inputStream, Project project) {
        List<String> warnings = new ArrayList<>();
        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);

            DefaultShellCallback shellCallback = new DefaultShellCallback(false);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            myBatisGenerator.generate(null, null, null, false);
            return new YobatisFileGenerator(myBatisGenerator, project);
        } catch (XMLParserException | InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            throw new InvalidMybatisGeneratorConfigException(e);
        }
    }
}

