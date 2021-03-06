package org.nalby.yobatis.core.structure.pom;

import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.log.LoggerFactory;
import org.nalby.yobatis.core.log.Logger;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Folder;
import org.nalby.yobatis.core.structure.Project;
import org.nalby.yobatis.core.util.PropertyUtil;

public class ProjectPom implements Pom {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectPom.class);

    private PomNode root;

    private ProjectPom(PomNode root) {
        this.root = root;
    }

    @Override
    public String lookupProperty(String name) {
        String tmp = PropertyUtil.valueOfPlaceholder(name);
        return root.getProperty(tmp);
    }

    private static void parsePomSubTree(Folder currentFolder, PomNode pomNode) {
        for (String moduleName: pomNode.getModuleNames()) {
            Folder nextFolder = currentFolder.findFolder(moduleName);
            if (nextFolder == null) {
                LOGGER.info("No maven module " + moduleName + " found.");
                continue;
            }
            File pomFile = nextFolder.findFile("pom.xml");
            PomNode child = PomNode.parse(pomFile);
            parsePomSubTree(nextFolder, child);
            pomNode.addChild(child);
        }
    }

    public static ProjectPom parse(Project project) {
        File file = project.findFile("pom.xml");
        if (file == null) {
            throw new ProjectException("Project is not a maven project.");
        }
        PomNode root = PomNode.parse(file);
        parsePomSubTree(project, root);
        return new ProjectPom(root);
    }

}
