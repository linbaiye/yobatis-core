package org.nalby.yobatis.core;

import org.nalby.yobatis.core.mybatis.Settings;
import org.nalby.yobatis.core.mybatis.TableElement;
import org.nalby.yobatis.core.mybatis.YobatisConfiguration;
import org.nalby.yobatis.core.mybatis.YobatisFileGenerator;
import org.nalby.yobatis.core.structure.Project;

import java.io.ByteArrayInputStream;
import java.util.List;

public class YobatisShell {

    private static YobatisShell shell = null;

    private YobatisConfiguration configuration;

    private Project project;

    private YobatisShell(YobatisConfiguration configuration, Project project) {
        this.configuration = configuration;
        this.project = project;
    }

    public void onSaveClicked(Settings settings) {
    }

    public void onGenerateClicked(List<TableElement> tableElementList) {
        configuration.update(tableElementList);
        String config = configuration.asStringWithoutDisabledTables();
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
