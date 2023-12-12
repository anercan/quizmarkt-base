package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.aspect.LoggingAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anercan
 */
public class BaseService {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    protected int getAppId() {
        return 1;
    }

    protected Long getUserId() {
        return 1L;
    }
}
