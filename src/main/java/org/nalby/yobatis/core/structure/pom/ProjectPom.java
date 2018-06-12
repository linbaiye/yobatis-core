package org.nalby.yobatis.core.structure.pom;


import org.nalby.yobatis.core.exception.ProjectException;
import org.nalby.yobatis.core.structure.File;
import org.nalby.yobatis.core.structure.Project;

public class ProjectPom implements Pom {

    private Project project;

    @Override
    public String lookupProperty(String name) {
        return null;
    }

    public static ProjectPom parse(Project project) {
        File file = project.findFile("pom.xml");
        if (file == null) {
            throw new ProjectException("Project is not a maven project.");
        }
        return null;
    }

}
