package com.quesmarkt.quesmarktbase.manager.exception;

/**
 * @author anercan
 */
public class AppException extends RuntimeException {

    public AppException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

}
