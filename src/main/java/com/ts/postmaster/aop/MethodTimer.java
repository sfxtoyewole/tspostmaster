package com.ts.postmaster.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * @author toyewole
 */

@Aspect
@Configuration
@Slf4j
public class MethodTimer {

    @Around("com.ts.postmaster.aop.CommonPointCutConfig.trackTimeAnnotation()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object obj = proceedingJoinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("Time taken to execute is {}ms  Method Name {} ", timeTaken, proceedingJoinPoint.getSignature().getName());

        return obj;
    }
}
