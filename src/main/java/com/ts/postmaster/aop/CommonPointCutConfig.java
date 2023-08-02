package com.ts.postmaster.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author toyewole
 */
public class CommonPointCutConfig {

    @Pointcut("@annotation(com.ts.postmaster.aop.TrackTime)")
    public void trackTimeAnnotation() {}
}
