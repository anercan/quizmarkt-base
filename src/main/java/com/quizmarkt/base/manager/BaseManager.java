package com.quizmarkt.base.manager;

import com.quizmarkt.base.aspect.LoggingAspect;
import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.data.enums.PremiumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public class BaseManager {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    protected Integer getAppId() {
        return UserContextHolder.getAppId();
    }
    protected boolean isRegularPremium() {
        return PremiumType.REGULAR_MONTHLY_PREMIUM.equals(UserContextHolder.getPremiumType());
    }

    protected String getUserId() {
        return UserContextHolder.getUserId();
    }
}
