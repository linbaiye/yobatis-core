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
package org.nalby.yobatis.core.util;

import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FolderUtil {

	private FolderUtil() {}
	
	/**
	 * Get the folder path of the file path.
	 * aa/bb/xx/test -> aa/bb/xx
	 * @return "" if not folder path, the folder path else.
	 */
	public static String folderPath(String filepath) {
		Expect.notEmpty(filepath, "filepath must not be null.");
		if (!filepath.contains("/")) {
			return "";
		}
		return filepath.replaceFirst("(^.*)/[^/]*$", "$1");
	}
	
	/**
	 * Get the file name of the file path.
	 * aa/bb/xx/test -> test
	 * @return the file name.
	 */
	public static String filename(String filepath) {
		Expect.notEmpty(filepath, "filepath must not be null.");
		return filepath.replaceFirst("^.*/([^/]*)$", "$1");
	}
	
	
	public static String concatPath(String base, String appending) {
		Expect.notEmpty(base, "base must not be null.");
		Expect.notEmpty(appending, "appending must not be null.");
		base = base.trim();
		appending = appending.trim();
		String tmp = base + "/" + appending;
		return tmp.replaceAll("/+", "/");
	}

	private final static String MAVEN_SOURCE_CODE_PATH = "src/main/java";

	private final static Pattern SOURCE_CODE_PATTERN = Pattern.compile("^.+" + MAVEN_SOURCE_CODE_PATH + "/(.+)$");

	/**
	 * Extract package name from path.
	 * @param path the path.
	 * @return package name if found, null else.
	 */
	public static String extractPackageName(String path) {
		if (path == null || !path.contains(MAVEN_SOURCE_CODE_PATH)) {
			return null;
		}
		Matcher matcher = SOURCE_CODE_PATTERN.matcher(path);
		String ret = null;
		if (matcher.find()) {
			ret = matcher.group(1);
		}
		if (ret != null) {
			ret = ret.replaceAll("/", ".");
		}
		return ret;
	}
	
	/**
	 * Wipe package path out from path.
	 * @param fullpath
	 * @return new path if found, the original path else.
	 */
	public static String wipePackagePath(String fullpath) {
		if (TextUtil.isEmpty(fullpath)) {
			return fullpath;
		}
		Matcher matcher = SOURCE_CODE_PATTERN.matcher(fullpath);
		String ret = null;
		if (matcher.find()) {
			ret = matcher.group(1);
		}
		if (ret == null) {
			return fullpath;
		}
		return fullpath.replace("/" + ret, "");
	}

	@FunctionalInterface
	private interface FileAppender<T> {
		void append(Set<T> resources, Folder folder);
	}

	private static <T> Set<T> iterateTree(Folder folder, FileAppender<T> fileAppender) {
		Set<T> result = new HashSet<>();
		Stack<Folder> stack = new Stack<>();
		stack.push(folder);
		while (!stack.isEmpty()) {
			Folder node = stack.pop();
			fileAppender.append(result, node);
			for (Folder tmp : node.listFolders()) {
				stack.push(tmp);
			}
		}
		return result;
	}

	/**
	 * List folders of all depth contained by this folder.
	 * @param folder the folder to list.
	 * @return all the folders if any, an empty set otherwise.
	 */
	public static Set<Folder> listAllFolders(Folder folder) {
		Expect.notNull(folder, "Folder must not be null.");
		return iterateTree(folder, (resources, folder1) -> resources.addAll(folder1.listFolders()));
	}

	/**
	 * List files of all depth contained by this folder.
	 * @param folder the folder to list.
	 * @return all the files if any, an empty set otherwise.
	 */
	public static Set<File> listAllFiles(Folder folder) {
		Expect.notNull(folder, "Folder must not be null.");
		return iterateTree(folder, (resources, folder1) -> resources.addAll(folder1.listFiles()));
	}
	
}
