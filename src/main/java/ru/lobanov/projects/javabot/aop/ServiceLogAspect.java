package ru.lobanov.projects.javabot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogAspect {
    @Pointcut("execution(* ru.lobanov.projects.javabot.service.UsersServiceImpl.*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        log.info("Called method: {}", joinPoint.getSignature().getName());
    }
}