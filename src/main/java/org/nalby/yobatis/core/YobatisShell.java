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

    private YobatisConfiguration configuration;

    private Project project;

    private final static Logger LOGGER = LoggerFactory.getLogger(YobatisShell.class);

    private YobatisShell(YobatisConfiguration configuration, Project project) {
        this.configuration = configuration;
        this.project = project;
    }

    /**
     * Load tables from current configuration.
     * @return
     */
    public List<TableElement> loadTables() {
        return this.configuration.listTableElementAsc();
    }

    public List<TableElement> disableAll() {
        return this.configuration.disableAll();
    }


    /**
     * Sync tables' presence with current configuration, existent 'enable' values will be
     * preserved.
     * @return tables after combining.
     */
    public List<TableElement> syncWithDatabase() {
        Settings settings = this.configuration.getSettings();
        if (!settings.isDatabaseConfigured()) {
            return Collections.emptyList();
        }
        MysqlDatabaseMetadataProvider.Builder builder = MysqlDatabaseMetadataProvider.builder();
        builder.setConnectorJarPath(project.getAbsPathOfSqlConnector());
        builder.setDriverClassName("com.mysql.jdbc.Driver");
        builder.setUrl(settings.getUrl());
        builder.setUsername(settings.getUser());
        builder.setPassword(settings.getPassword());
        MysqlDatabaseMetadataProvider provider = builder.build();
        List<Table> tableList = provider.fetchTables();
        LOGGER.info("Found {} table{}.", tableList.size(), tableList.size() > 0 ? "s" : "");
        this.configuration.sync(tableList);
        return this.configuration.listTableElementAsc();
    }

    /**
     * Save settings.
     * @param settings
     */
    public void save(Settings settings) {
        if (settings != null) {
            settings.setConnectorPath(project.getAbsPathOfSqlConnector());
            this.configuration.update(settings);
            this.configuration.flush();
        }
    }

    public Settings loadSettings() {
        return this.configuration.getSettings();
    }

    /**
     * Generate files according to tables in tableElementList,
     * only enabled table will take effect.
     * @param tableElementList
     */
    public void generate(List<TableElement> tableElementList) {
        if (tableElementList == null || tableElementList.isEmpty()) {
            LOGGER.info("No table selected.");
            return;
        }
        final int[] count = {0};
        tableElementList.forEach(e -> count[0] += e.isEnabled() ? 1 : 0);
        if (count[0] == 0) {
            LOGGER.info("No table selected.");
            return;
        }
        configuration.update(tableElementList);
        String config = configuration.asStringWithoutDisabledTables();
        LOGGER.debug("Got config:{}.", config);
        YobatisFileGenerator generator = YobatisFileGenerator.parse(
                new ByteArrayInputStream(config.getBytes()), project);
        LOGGER.info("Merging files.");
        generator.mergeAndWrite();
    }

    public static synchronized YobatisShell newInstance(Project project) {
        return new YobatisShell(YobatisConfiguration.open(project), project);
    }
}
