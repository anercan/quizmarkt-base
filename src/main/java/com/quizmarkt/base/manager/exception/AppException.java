package com.quizmarkt.base.manager.exception;

/**
 * @author anercan
 */
public class AppException extends RuntimeException {

    public AppException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

}
