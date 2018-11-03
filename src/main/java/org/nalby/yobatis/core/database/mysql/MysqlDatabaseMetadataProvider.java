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
package org.nalby.yobatis.core.database.mysql;


import org.nalby.yobatis.core.database.DatabaseMetadataProvider;
import org.nalby.yobatis.core.database.Table;
import org.nalby.yobatis.core.exception.InvalidSqlConfigException;
import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.log.LogFactory;
import org.nalby.yobatis.core.log.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlDatabaseMetadataProvider extends DatabaseMetadataProvider {
	
	private String timedoutUrl;
	
	private final static Logger LOGGER = LogFactory.getLogger(MysqlDatabaseMetadataProvider.class);

	private List<Table> tableList;

	private MysqlDatabaseMetadataProvider(String username, String password,
			String url, String driverClassName, String jdbcJarPath) {
		try {
			this.username = username;
			this.password = password;
			this.url = url;
			this.driverClassName = driverClassName;
			this.connectorJarPath = jdbcJarPath;
			LOGGER.info("Detected database configuration:[username:{}, password:{}, url:{}].", username, password, url);
			timedoutUrl = this.url;
			if (!this.timedoutUrl.contains("socketTimeout")) {
				if (this.timedoutUrl.contains("?")) {
					this.timedoutUrl = this.timedoutUrl + "&socketTimeout=10000";
				} else {
					this.timedoutUrl = this.timedoutUrl + "?socketTimeout=10000";
				}
			}
			if (!this.timedoutUrl.contains("connectTimeout")) {
				this.timedoutUrl = this.timedoutUrl + "&connectTimeout=2000";
			}
		} catch (Exception e) {
		    //Ignore.
		}
	}
	
	private Table makeTable(String name, DatabaseMetaData metaData) {
		Table table = new Table(name);
		// Fetch column metadata.
		try (ResultSet resultSet =
					 metaData.getColumns(null, null, name, null)) {
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				String autoIncrment = resultSet.getString("IS_AUTOINCREMENT");
				if (autoIncrment.toLowerCase().contains("true") ||
						autoIncrment.toLowerCase().contains("yes")) {
					table.addAutoIncColumn(columnName);
				}
			}
		} catch (Exception e) {
		    LOGGER.error("Failed to fetch metadata for {}.", name);
		    throw new InvalidSqlConfigException(e.getMessage());
		}
		try (ResultSet resultSet = metaData.getPrimaryKeys(null,null, name)) {
			while(resultSet.next()) {
				table.addPrimaryKey(resultSet.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
		    throw new InvalidSqlConfigException("Unable to fetch primary key meta for " + name);
		}
		return table;
	}
	
	/**
	 * Get all tables according to the configuration.
	 * @return tables.
	 * @throws ProjectException, if any configuration value is not valid.
	 */
	@Override
	public List<Table> getTables() {
	    if (tableList != null) {
	    	return tableList;
		}
		tableList = new LinkedList<>();
		try (Connection connection = DriverManager.getConnection(this.timedoutUrl, username, password)) {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet res = meta.getTables(null, null, null, new String[] {"TABLE"});
			while (res.next()) {
				String tmp = res.getString("TABLE_NAME");
				tableList.add(makeTable(tmp, meta));
			}
			res.close();
		} catch (Exception e) {
			LOGGER.info("Yobatis is unable to list tables, please configure table element manually.");
		}
		return tableList;
	}
	
	public static class Builder {
		private String username;
		private String password;
		private String url;
		private String connectorJarPath;
		private String driverClassName;
		private Builder(){}
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
			try {
				Class.forName(driverClassName);
				return new MysqlDatabaseMetadataProvider(username, password, url, driverClassName, connectorJarPath);
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
