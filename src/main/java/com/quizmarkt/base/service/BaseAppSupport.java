package com.quizmarkt.base.service;

import com.quizmarkt.base.data.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anercan
 */
@Slf4j
public abstract class BaseAppSupport {
    protected Integer getAppId() {
        return UserContextHolder.getAppId();
    }

    protected String getUserId() {
        return UserContextHolder.getUserId();
    }
}
