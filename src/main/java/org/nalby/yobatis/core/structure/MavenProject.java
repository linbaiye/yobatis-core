package org.nalby.yobatis.core.structure;

import java.util.List;

public interface MavenProject {

    List<Folder> daoFolderList();

    List<Folder> domainFolderList();

}
