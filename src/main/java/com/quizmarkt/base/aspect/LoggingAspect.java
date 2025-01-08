package com.quizmarkt.base.aspect;

import com.quizmarkt.base.data.context.UserContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author anercan
 */

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    @Around("restControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        StringBuilder response = new StringBuilder();
        if (proceed instanceof ResponseEntity<?>) {
            //Object body = ((ResponseEntity<?>) proceed).getBody();
            response.append("Code:").append(((ResponseEntity<?>) proceed).getStatusCode());
                    //.append(Objects.nonNull(body) ? body : "null");
        }
        StringBuilder requestBuilder = new StringBuilder();
        Arrays.stream(proceedingJoinPoint.getArgs()).map(Objects::toString).forEach(requestBuilder::append);
        logger.info("Method:{} called and " +
                "took time:{} ms " +
                "Request:{} " +
                "Response: {} " +
                "userID:{}", proceedingJoinPoint.getSignature().getName(), elapsedTime, requestBuilder, response, UserContextHolder.getUserId());
        return proceed;
    }
}
