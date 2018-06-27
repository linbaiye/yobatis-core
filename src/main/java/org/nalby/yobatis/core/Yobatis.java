/*
 *    Copyright 2018 the original author or authors.
 *    
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *    use this file except in compliance with the License.  You may obtain a copy
 *    of the License at
 *    
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *    License for the specific language governing permissions and limitations under
 *    the License.
 */
package org.nalby.yobatis.core;

import org.nalby.yobatis.core.database.DatabaseMetadataProvider;
import org.nalby.yobatis.core.database.mysql.MysqlDatabaseMetadataProvider;
import org.nalby.yobatis.core.database.mysql.MysqlDatabaseMetadataProvider.Builder;
import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.log.LogFactory;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.mybatis.*;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.structure.pom.ProjectPom;
import org.nalby.yobatis.core.structure.spring.SpringParser;

import java.io.IOException;
import java.io.InputStream;

public class Yobatis {

	private static Logger logger = LogFactory.getLogger(Yobatis.class);
	
	/**
	 *  Build the generator of mybatis-generator's config file according to project config.
	 */
	private static MybatisGeneratorXmlCreator buildMybatisGeneratorXmlCreator(Project project) {


		SpringParser springParser = SpringParser.parse(project);

		ProjectPom projectPom = ProjectPom.parse(project);


		Builder builder = MysqlDatabaseMetadataProvider.builder();
		builder.setConnectorJarPath(project.getAbsPathOfSqlConnector())
		.setUsername(projectPom.lookupProperty(springParser.lookupDbUser()))
		.setPassword(projectPom.lookupProperty(springParser.lookupDbPassword()))
		.setUrl(projectPom.lookupProperty(springParser.lookupDbUrl()))
		.setDriverClassName(projectPom.lookupProperty(springParser.lookupDbDriver()));
		DatabaseMetadataProvider provider = builder.build();

		return MybatisGeneratorXmlCreator.create(project, provider);
		//return new MybatisGeneratorXmlCreator(pomTree, provider, groups);
	}
	

	private static MybatisGeneratorRunner buildMyBatisRunner(Project project) {
		try {
			File file = project.findFile(MybatisGenerator.CONFIG_FILENAME);
			try (InputStream inputStream = file.open()) {
			    MybatisGeneratorRunner runner = new MybatisGeneratorRunner();
				runner.parse(inputStream);
				return runner;
			}
		} catch (Exception e) {
			throw new InvalidMybatisGeneratorConfigException(e);
		}
	}
	
	/*
	 * Merge the new file into the existent one if exists.
	 */
	private static MybatisGenerator mergeIntoExistentConfig(MybatisGeneratorXmlCreator configFileGenerator, Project project) {
		MybatisGenerator generator = configFileGenerator;
		File file = project.findFile(MybatisGenerator.CONFIG_FILENAME);
		if (file != null) {
			try (InputStream inputStream = file.open()) {
				MybatisGeneratorXmlReader mybatisXmlParser = MybatisGeneratorXmlReader.build(inputStream);
				mybatisXmlParser.mergeGeneratedConfig(configFileGenerator);
				generator = mybatisXmlParser;
			} catch (IOException  e) {
				logger.info("Unable to merge existent file:{}.", e);
			}
		}
		file = project.createFile(MybatisGenerator.CONFIG_FILENAME);
		file.write(generator.asXmlText());
		return generator;
	}

	public static void onClickProject(Project project) {
		logger.info("Scanning project:{}.", project.name());
		MybatisGeneratorXmlCreator generator = buildMybatisGeneratorXmlCreator(project);
		mergeIntoExistentConfig(generator, project);
		logger.info("Config file has been created, right-click on {} to generate java and xml mapper files.", MybatisGenerator.CONFIG_FILENAME);
	}
	
	public static void onClickFile(Project project) {
		logger.info("Using existent config file.");
		MybatisGeneratorRunner runner = buildMyBatisRunner(project);
		new MybatisFilesWriter(project, runner).writeAll();
	}

}
