package com.quizmarkt.base.service;

import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.data.enums.PremiumType;
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

    protected String getUserId() {
        return UserContextHolder.getUserId();
    }

    protected boolean isRegularPremium() {
        return !PremiumType.NONE.equals(UserContextHolder.getPremiumType());
    }

    protected PremiumType getPremiumType() {
        return UserContextHolder.getPremiumType();
    }

}
