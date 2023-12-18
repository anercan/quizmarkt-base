package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.aspect.LoggingAspect;
import com.quesmarkt.quesmarktbase.config.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public class BaseService {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    protected Integer getAppId() {
        return UserContextHolder.getAppId();
    }

    protected Long getUserId() {
        return UserContextHolder.getUserId();
    }
}
