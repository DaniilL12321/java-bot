package ru.lobanov.projects.javabot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OrderExecutionAspect {
    @Pointcut("execution(* ru.lobanov.projects.javabot.service.JokesService.addNewJoke(..))")
    public void callAddNewJoke() {
    }

    @Around("callAddNewJoke()")
    public Object aroundNewJoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Method {} executed in {} ms", proceedingJoinPoint.getSignature().getName(), endTime - startTime);
        return result;
    }
}