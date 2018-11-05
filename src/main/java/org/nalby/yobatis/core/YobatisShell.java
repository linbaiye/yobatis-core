package org.nalby.yobatis.core;

import org.nalby.yobatis.core.database.MysqlDatabaseMetadataProvider;
import org.nalby.yobatis.core.database.Table;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.log.LoggerFactory;
import org.nalby.yobatis.core.mybatis.Settings;
import org.nalby.yobatis.core.mybatis.TableElement;
import org.nalby.yobatis.core.mybatis.YobatisConfiguration;
import org.nalby.yobatis.core.mybatis.YobatisFileGenerator;
import org.nalby.yobatis.core.structure.Project;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

public class YobatisShell {

    private static YobatisShell shell = null;

    private YobatisConfiguration configuration;

    private Project project;

    private final static Logger LOGGER = LoggerFactory.getLogger(YobatisShell.class);

    private YobatisShell(YobatisConfiguration configuration, Project project) {
        this.configuration = configuration;
        this.project = project;
    }

    public List<TableElement> loadTables() {
        Settings settings = this.configuration.getSettings();
        if (!settings.isDatabaseConfigured()) {
            return Collections.emptyList();
        }
        MysqlDatabaseMetadataProvider.Builder builder = MysqlDatabaseMetadataProvider.builder();
        builder.setConnectorJarPath(settings.getConnectorPath());
        builder.setDriverClassName("com.mysql.jdbc.Driver");
        builder.setUrl(settings.getUrl());
        builder.setUsername(settings.getUser());
        builder.setPassword(settings.getPassword());
        MysqlDatabaseMetadataProvider provider = builder.build();
        List<Table> tableList = provider.fetchTables();
        this.configuration.sync(tableList);
        return this.configuration.listTableElementAsc();
    }

    public void save(Settings settings) {
        this.configuration.update(settings);
        this.configuration.flush();
    }

    public Settings loadSettings() {
        return this.configuration.getSettings();
    }

    public void generate(List<TableElement> tableElementList) {
        configuration.update(tableElementList);
        String config = configuration.asStringWithoutDisabledTables();
        LOGGER.debug("Got config:{}.", config);
        YobatisFileGenerator generator = YobatisFileGenerator.parse(
                new ByteArrayInputStream(config.getBytes()), project);
        generator.mergeAndWrite();
    }

    public static synchronized YobatisShell open(Project project) {
        if (shell != null) {
            return shell;
        }
        shell = new YobatisShell(YobatisConfiguration.open(project), project);
        return shell;
    }
}
