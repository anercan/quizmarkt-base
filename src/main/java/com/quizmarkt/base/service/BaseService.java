package com.quizmarkt.base.service;

import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.data.enums.PremiumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public abstract class BaseService extends BaseAppSupport {

    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected boolean isRegularPremium() {
        return !PremiumType.NONE.equals(UserContextHolder.getPremiumType());
    }

    protected boolean isNonPremium() {
        return PremiumType.NONE.equals(UserContextHolder.getPremiumType());
    }

    protected PremiumType getPremiumType() {
        return UserContextHolder.getPremiumType();
    }

}
