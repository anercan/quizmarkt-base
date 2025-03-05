package com.quizmarkt.base.controller;

import org.springframework.http.ResponseEntity;

/**
 * @author anercan
 */
public interface ApiResponseSupport {
     default <T> ResponseEntity<T> respond(T data) {
        return ResponseEntity.ok(data);
    }
}
