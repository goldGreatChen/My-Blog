package com.my.blog.website.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 借口aop
 * Created by wangq on 2017/3/24.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger LOGGE = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.my.blog.website.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        LOGGE.info("URL : " + request.getRequestURL().toString());
        LOGGE.info("HTTP_METHOD : " + request.getMethod());
        LOGGE.info("IP : " + request.getRemoteAddr());
        LOGGE.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGE.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        LOGGE.info("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp) {
        LOGGE.info("方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp) {
        LOGGE.info("方法最后执行.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        long start=System.currentTimeMillis();

        try {
            Object o = pjp.proceed();
            long end=System.currentTimeMillis();
            LOGGE.error((end-start)+":请求用时");
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
