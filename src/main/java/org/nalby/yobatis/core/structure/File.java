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

import java.io.InputStream;

public interface File {
	
	String name();
	
	String path();

	InputStream open();
	
	void write(InputStream inputStream);

	void write(String content);
	
	/**
	 * Get the folder that contains this file.
	 * @return parent folder.
	 */
	Folder parentFolder();

}
