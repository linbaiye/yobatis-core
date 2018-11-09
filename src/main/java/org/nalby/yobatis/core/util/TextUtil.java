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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtil {
	private TextUtil() {}
	
	public static boolean isEmpty(String text) {
		return text == null || "".equals(text.trim());
	}


	private final static Pattern PATTERN = Pattern.compile("([a-zA-Z_])(.*)");

	public static String capitalizeFirstChar(String str) {
		if (TextUtil.isEmpty(str)) {
			return str;
		}
		Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches()) {
			return str;
		}
		if (matcher.groupCount() == 2) {
			return matcher.group(1).toUpperCase() + matcher.group(2);
		}
		return matcher.group(1).toUpperCase();
	}

	public static String asString(InputStream inputStream) {
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			int nRead;
			byte[] data = new byte[2048];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			byte[] byteArray = buffer.toByteArray();
			return new String(byteArray, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// Nothing we can do here.
		}
		return null;
	}

	public static String addTimeoutToUrlIfAbsent(String url) {
	    if (url == null) {
	    	return null;
		}
		String timedoutUrl = url;
		if (!timedoutUrl.contains("socketTimeout")) {
			if (timedoutUrl.contains("?")) {
				timedoutUrl = timedoutUrl + "&socketTimeout=3000";
			} else {
				timedoutUrl = timedoutUrl + "?socketTimeout=3000";
			}
		}
		if (!timedoutUrl.contains("connectTimeout")) {
			timedoutUrl = timedoutUrl + "&connectTimeout=1000";
		}
		return timedoutUrl;
	}




}
