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

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.exception.InvalidUnitException;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.log.LoggerFactory;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YobatisFileGenerator {

    private Project project;

    private static final Logger LOGGER = LoggerFactory.getLogger(YobatisFileGenerator.class);

    private List<YobatisUnit> fileList;

    private YobatisFileGenerator(List<YobatisUnit> fileList,
                                 Project project) {
        this.fileList = fileList;
        this.project = project;
    }


    private void mergeFileIfPresent(YobatisUnit unit) {
        String filePath = unit.getPathToPut();
        File file = project.findFile(filePath);
        if (file == null) {
            return;
        }
        try (InputStream inputStream = file.open()) {
            String content = TextUtil.asString(inputStream);
            unit.merge(content);
        } catch (InvalidUnitException e) {
            LOGGER.error("File {} is broken and Yobatis is unable to merge it, please fix it and retry, error:{}.", file.path(), e.getMessage());
            throw new InvalidMybatisGeneratorConfigException(e);
        } catch (IOException e) {
            LOGGER.error("Failed to read file {}.", file.path());
            throw new InvalidMybatisGeneratorConfigException(e);
        }
    }

    private void mergeFiles() {
        fileList.forEach(this::mergeFileIfPresent);
    }


    private void writeFiles() {
        fileList.forEach(e -> {
            String filePath = e.getPathToPut();
            File file = project.createFile(filePath);
            file.write(e.getFormattedContent());
        });
    }

    public void mergeAndWrite() {
        mergeFiles();
        writeFiles();
        LOGGER.info("Files have been generated, happy coding.");
    }

    public static YobatisFileGenerator parse(InputStream inputStream, Project project) {
        try {
            List<String> warnings = new ArrayList<>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);

            DefaultShellCallback shellCallback = new DefaultShellCallback(false);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            myBatisGenerator.generate(null, null, null, false);
            List<YobatisUnit> fileList = myBatisGenerator.getGeneratedJavaFiles()
                    .stream().filter(e -> e.getCompilationUnit() instanceof YobatisUnit)
                        .map(e -> (YobatisUnit)e.getCompilationUnit())
                        .collect(Collectors.toList());
            LOGGER.debug("Collected {} java files.", fileList.size());
            myBatisGenerator.getGeneratedXmlFiles().forEach(e -> {
                if (e instanceof YobatisUnit) {
                    fileList.add((YobatisUnit)e);
                }
            });
            if (fileList.isEmpty()) {
                throw new InvalidMybatisGeneratorConfigException("No file to generate, please check your configuration and retry.");
            }
            return new YobatisFileGenerator(fileList, project);
        } catch (XMLParserException | InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            throw new InvalidMybatisGeneratorConfigException(e);
        }
    }
}

