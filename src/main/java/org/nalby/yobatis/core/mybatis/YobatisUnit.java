package org.nalby.yobatis.core.mybatis;

import org.nalby.yobatis.core.exception.InvalidUnitException;

public interface YobatisUnit {
    /**
     * Get formatted content of this unit.
     * @return the content.
     */
    String getFormattedContent();

    /**
     * Get the path where the file should be put.
     * @return the path.
     */
    String getPathToPut();

    /**
     * Merge the file represented by fileContent, if possible, to preserve manually
     * added code/comments.
     * @param fileContent the file to merge.
     * @throws InvalidUnitException if the fileContent is invalid.
     */
    void merge(String fileContent) throws InvalidUnitException;

}
