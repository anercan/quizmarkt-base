package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.aspect.LoggingAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public class BaseManager {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    protected Integer getAppId() {
        return 1;
        //return UserContextHolder.getAppId();
    }

    protected Long getUserId() {
        return 1l;
        //return UserContextHolder.getUserId();
    }
}
