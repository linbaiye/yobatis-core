package org.nalby.yobatis.core.exception;

/**
 * A InvalidUnitException should be thrown when an existent file is broken.
 */
public class InvalidUnitException extends Exception {

    public InvalidUnitException(String msg) {
        super(msg);
    }

    public InvalidUnitException(Exception e) {
        super(e);
    }
}
