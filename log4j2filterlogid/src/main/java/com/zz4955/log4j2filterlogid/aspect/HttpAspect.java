package com.zz4955.log4j2filterlogid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.zz4955.log4j2filterlogid.controller.UserController.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        LOGGER.info("doBefore");
        // 记录http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 从request中获取http请求的url/请求的方法类型/响应该http请求的类方法/IP地址/请求中的参数
        // url
        LOGGER.info("url={}", request.getRequestURL());

        // method
        LOGGER.info("method={}", request.getMethod());

        // ip
        LOGGER.info("ip={}", request.getRemoteAddr());

        // 类方法
        LOGGER.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // 参数
        LOGGER.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter() {
        LOGGER.info("doAfter");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        LOGGER.info("student={}", object);
    }
}
