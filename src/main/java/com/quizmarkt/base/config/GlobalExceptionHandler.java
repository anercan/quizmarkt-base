package com.quizmarkt.base.config;

import com.quizmarkt.base.data.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception caught on request:{}", request.getContextPath(), ex);
        return new ResponseEntity<>(new ApiResponse<>(ApiResponse.Status.fail()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}