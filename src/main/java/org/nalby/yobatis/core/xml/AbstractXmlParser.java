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
package org.nalby.yobatis.core.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.nalby.yobatis.core.util.Expect;
import org.nalby.yobatis.core.util.XmlUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractXmlParser {
	
	private static final int MAX_STREAM_SIZE = 5 * 1024 * 1024;

	protected Document document;

	public AbstractXmlParser(InputStream inputStream, String rootElmentTag) throws DocumentException, IOException {
		Expect.asTrue(inputStream != null
				&& inputStream.available() <= MAX_STREAM_SIZE
				&& inputStream.available() > 0,
				"invalid input stream");
		Expect.notNull(rootElmentTag, "root element tag is null.");
		SAXReader saxReader = new SAXReader();
		saxReader.setValidation(false);
		customSAXReader(saxReader);
	
		document = saxReader.read(new BufferedInputStream(inputStream));
		if (document == null || document.getRootElement() == null
				|| !rootElmentTag.equals(document.getRootElement().getName())) {
			throw new DocumentException("Unexpected document.");
		}
	}
	
	protected void customSAXReader(SAXReader saxReader) {}

	public String toXmlString() {
		return XmlUtil.toXmlString(document);
	}
}