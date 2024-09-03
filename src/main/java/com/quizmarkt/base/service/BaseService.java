package com.quizmarkt.base.service;

import com.quizmarkt.base.config.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public class BaseService {

    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected Integer getAppId() {
        return UserContextHolder.getAppId();
    }

    protected Long getUserId() {
        return UserContextHolder.getUserId();
    }
}
