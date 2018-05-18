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
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class allows the code generator to be run from the command line.
 *
 * @author Jeff Butler
 */
public class MybatisGeneratorRunner {

    private MyBatisGenerator myBatisGenerator;

    public void parse(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("null pointor passed.");
        }

        List<String> warnings = new ArrayList<>();

        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);

            DefaultShellCallback shellCallback = new DefaultShellCallback(false);

            myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            myBatisGenerator.generate(null, null, null, false);

        } catch (XMLParserException | InvalidConfigurationException | SQLException | IOException e) {
            throw new InvalidMybatisGeneratorConfigException(e);
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
        }
    }

    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return myBatisGenerator.getGeneratedXmlFiles();

    }

    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return myBatisGenerator.getGeneratedJavaFiles();
    }
}

