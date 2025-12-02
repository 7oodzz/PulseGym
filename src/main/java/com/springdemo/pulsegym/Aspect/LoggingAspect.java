package com.springdemo.pulsegym.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.springdemo.pulsegym.Controller.*.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.springdemo.pulsegym.*.*.*(..))")
    public void projectMethods() {}

    @Before("controllerMethods()")
    public void beforeEndpointLog (JoinPoint joinPoint) {

        log.info("Called controller method: {}", joinPoint.getSignature().getName());
        log.info("Arguments passed: {}", Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void afterEndpointLog (JoinPoint joinPoint, Object result) {
        log.info("Controller method: {} done successfully, Result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "projectMethods()", throwing = "exception")
    public void exceptionsLog (JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method {}: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

}
