package springboot.playwright.cucumber.aop;

import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@ScenarioScope
public class CommonLogger {
    @Before("execution(* springboot.playwright.cucumber.pageObjects.PageObject.*(..))")
    public void putCommonLog(JoinPoint joinPoint) {
        log.debug("Invoking this method from PageObject class instance: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.microsoft.playwright.*.*(..))")
    public Object exceptionHandlerWithReturnType(ProceedingJoinPoint joinPoint) throws Throwable{
        Object obj;
        obj = joinPoint.proceed();
        return obj;
    }
}
