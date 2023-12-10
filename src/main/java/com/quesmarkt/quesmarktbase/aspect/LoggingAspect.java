package com.quesmarkt.quesmarktbase.aspect;

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
        String methodName = proceedingJoinPoint.getSignature().getName();
        long elapsedTime = System.currentTimeMillis() - startTime;
        String response = "";
        if (proceed instanceof ResponseEntity<?>) {
            Object body = ((ResponseEntity<?>) proceed).getBody();
            response = "ResponseBody:" + (Objects.nonNull(body) ? body : null) + " ResponseCode:" + ((ResponseEntity<?>) proceed).getStatusCode();
        }
        String request = Arrays.stream(proceedingJoinPoint.getArgs()).map(Objects::toString).reduce("", String::concat);
        logger.info("RequestBody:{} to {} finished in {} ms.{}", request, methodName, elapsedTime, response);
        return proceed;
    }
}
