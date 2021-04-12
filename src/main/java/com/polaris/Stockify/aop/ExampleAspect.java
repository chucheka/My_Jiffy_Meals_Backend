package com.polaris.Stockify.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ExampleAspect {

    @Around("@annotation(com.polaris.Stockify.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + " ms");

        return proceed;
    }

    @Before(value = "execution(* com.polaris.Stockify.controller.*.*())")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before method:" + joinPoint.getSignature());

        System.out.println("Creating Employee with name - "+joinPoint.getSignature());
    }
}