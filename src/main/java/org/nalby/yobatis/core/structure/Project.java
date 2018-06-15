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
package org.nalby.yobatis.core.structure;

import org.nalby.yobatis.core.exception.InvalidMybatisGeneratorConfigException;
import org.nalby.yobatis.core.util.Expect;
import org.nalby.yobatis.core.util.FolderUtil;

import java.util.List;

public abstract class Project implements Folder {

	/**
	 * The folder of the project itself.
	 */
	protected Folder root;

	public Project(Folder root) {
		this.root = root;
	}

	private String wipeRootFolderPath(String path) {
		Expect.notEmpty(path, "path must not be empty.");
		if (path.startsWith(root.path())) {
			return path.replaceFirst(root.path() + "/", "");
		}
		if (path.startsWith("/")) {
			throw new InvalidMybatisGeneratorConfigException("Path " + path + " should start with /" + root.name());
		}
		return path;
	}

	/**
	 * It is left to the platform to find out where the maven repository is.
	 * @return the maven path, null if failed to find.
	 */
	abstract public String getAbsPathOfSqlConnector();

	@Override
	public String path() {
		return root.path();
	}

	@Override
	public String name() {
		return root.name();
	}

	@Override
	public List<Folder> listFolders() {
		return root.listFolders();
	}

	@Override
	public File findFile(String filepath) {
		return root.findFile(wipeRootFolderPath(filepath));
	}

	@Override
	public File createFile(String filepath) {
		return root.createFile(wipeRootFolderPath(filepath));
	}

	@Override
	public Folder createFolder(String folderpath) {
		return root.createFolder(wipeRootFolderPath(folderpath));
	}

	@Override
	public Folder findFolder(String folerpath) {
		return root.findFolder(wipeRootFolderPath(folerpath));
	}

	@Override
	public List<File> listFiles() {
		return root.listFiles();
	}
}
