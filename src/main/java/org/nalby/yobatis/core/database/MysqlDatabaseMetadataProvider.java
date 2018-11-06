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
package org.nalby.yobatis.core.database;


import org.nalby.yobatis.core.exception.InvalidSqlConfigException;
import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.log.LoggerFactory;
import org.nalby.yobatis.core.util.TextUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlDatabaseMetadataProvider extends DatabaseMetadataProvider {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MysqlDatabaseMetadataProvider.class);

	private MysqlDatabaseMetadataProvider(String username, String password,
			String url, String driverClassName, String jdbcJarPath) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.driverClassName = driverClassName;
		this.connectorJarPath = jdbcJarPath;
	}

	/**
	 * Get all tables according to the configuration.
	 * @return tables.
	 * @throws ProjectException, if any configuration value is not valid.
	 */
	@Override
	public List<Table> fetchTables() {
		List<Table> tableList = new LinkedList<>();
		try (Connection connection = DriverManager.getConnection(this.url, username, password)) {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet res = meta.getTables(null, null, null, new String[] {"TABLE"});
			while (res.next()) {
				String tmp = res.getString("TABLE_NAME");
				tableList.add(new Table(tmp));
			}
			res.close();
		} catch (Exception e) {
			LOGGER.error("Caught exception:", e);
			throw new InvalidSqlConfigException("Yobatis is unable to fetch tables, please check your configuration and retry.");
		}
		return tableList;
	}
	
	public static class Builder {
		private String username;
		private String password;
		private String url;
		private String connectorJarPath;
		private String driverClassName;
		private Builder(){ }
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		public Builder setConnectorJarPath(String connectorJarPath) {
			this.connectorJarPath = connectorJarPath;
			return this;
		}
		public Builder setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
			return this;
		}
		public MysqlDatabaseMetadataProvider build() {
			if (TextUtil.isEmpty(username)) {
				throw new InvalidSqlConfigException("username for db not configured");
			}
			if (TextUtil.isEmpty(password)) {
				throw new InvalidSqlConfigException("password for db not configured");
			}
			if (TextUtil.isEmpty(connectorJarPath)) {
				throw new InvalidSqlConfigException("connector for db not configured");
			}
			if (TextUtil.isEmpty(url)) {
				throw new InvalidSqlConfigException("url for db not configured");
			}
			if (TextUtil.isEmpty(driverClassName)) {
				throw new InvalidSqlConfigException("driverClassName for db not configured");
			}

			try {
				Class.forName(driverClassName);
				String timedoutUrl = TextUtil.addTimeoutToUrlIfAbsent(url);
				return new MysqlDatabaseMetadataProvider(username, password, timedoutUrl, driverClassName, connectorJarPath);
			} catch (Exception e) {
				throw new InvalidSqlConfigException(e);
			}
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	@Override
	public String getSchema() {
		Pattern pattern = Pattern.compile("jdbc:mysql://[^/]+/([^?]+).*");
		Matcher matcher = pattern.matcher(url);
		return matcher.find() ? matcher.group(1) : null;
	}
}
