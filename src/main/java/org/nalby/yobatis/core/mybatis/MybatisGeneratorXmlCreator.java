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
package org.nalby.yobatis.core.mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.nalby.yobatis.core.database.DatabaseMetadataProvider;
import org.nalby.yobatis.core.log.LogFactory;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.MavenProject;
import org.nalby.yobatis.core.structure.PomTree;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.util.Expect;
import org.nalby.yobatis.core.util.FolderUtil;
import org.nalby.yobatis.core.util.XmlUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Generate MyBaits Generator's configuration file according to current project structure.
 * 
 * @author Kyle Lin
 */
public class MybatisGeneratorXmlCreator implements MybatisGenerator {

	private Document document;

	private PomTree pomTree;
	
	private Element root;

	private Element classPathEntry;
	
	private DocumentFactory factory = DocumentFactory.getInstance();

	private final static Logger logger = LogFactory.getLogger(MybatisGeneratorXmlCreator.class);
	
	private List<MybatisGeneratorContext> contexts;

	public MybatisGeneratorXmlCreator(PomTree pomTree, 
			DatabaseMetadataProvider sql,
			List<TableGroup> tableGroups) {
		Expect.notNull(pomTree, "pomTree must not be null.");
		Expect.notNull(sql , "database must not be null.");
		Expect.notNull(tableGroups, "tableGroups must not be null.");
		this.pomTree = pomTree;
		createClassPathEntry(sql);
		createContexts(sql, tableGroups);
		logger.info("Generated MyBatis Generator's configuration file.");
	}
	
	private void createContexts(DatabaseMetadataProvider sql, List<TableGroup> groups) {
		contexts = new ArrayList<>();
		for (TableGroup group : groups) {
			String packageName = FolderUtil.extractPackageName(group.getFolder().path());

			MybatisGeneratorContext thisContext = new MybatisGeneratorContext(packageName, sql);
			thisContext.createJavaModelGenerator(group.getFolder());

			Folder daoFolder = pomTree.findMostMatchingDaoFolder(group.getFolder());
			thisContext.createJavaClientGenerator(daoFolder);

			Folder resourceFolder = pomTree.findMostMatchingResourceFolder(group.getFolder());
			thisContext.createSqlMapGenerator(resourceFolder);

			thisContext.appendTables(group.getTables(), sql.getSchema());
			contexts.add(thisContext);
		}
	}
	
	/**
	 * Get contexts.
	 * @return contexts, or an empty list.
	 */
	public List<MybatisGeneratorContext> getContexts() {
		return contexts;
	}
	
	public Element getClassPathEntryElement() {
		return classPathEntry;
	}
	
	private void createClassPathEntry(DatabaseMetadataProvider sql) {
		classPathEntry = factory.createElement(CLASS_PATH_ENTRY_TAG);
		classPathEntry.addAttribute("location", sql.getConnectorJarPath());
	}

	private void createDocument() {
		document = factory.createDocument();
		DocumentType type = factory.createDocType(ROOT_TAG, 
				"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN",
				"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
		document.setDocType(type);
	}

	@Override
	public String asXmlText() {
		if (document == null) {
			createDocument();
			root = factory.createElement(ROOT_TAG);
			document.setRootElement(root);
			root.add(classPathEntry);
			for (MybatisGeneratorContext thisContext : contexts) {
				root.add(thisContext.getContext().createCopy());
			}
		}
		return XmlUtil.toXmlString(document);
	}

	private static Set<Folder> findFoldersContainingSubPath(Folder folder, final String subPath) {
		Set<Folder> folders = FolderUtil.listAllFolders(folder);
		folders.removeIf(next -> !next.path().contains(subPath));
		return folders;
	}

	public static MybatisGeneratorXmlCreator create(Project project) {
	    Set<Folder> sourceCodeFolders = findFoldersContainingSubPath(project, "src/main/java");
		Set<Folder> resourceFolders = findFoldersContainingSubPath(project, "src/main/resources");
		MybatisGeneratorXmlCreator mybatisGeneratorXmlCreator = new MybatisGeneratorXmlCreator();
	    return mybatisGeneratorXmlCreator;
	}


}
