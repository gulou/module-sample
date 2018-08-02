package com.gulou.test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gulou.test.common.service.RespCodeTransferService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>http请求切面</p>
 * 1.打印请求耗时,2.转换返回码
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/16 20:32
 */

@Component
@Aspect
@Slf4j
public class HttpReqAspect{

    private static final String START_TIME = "startTime";
    private static final String LANGUAGE_TYPE = "language-type";

    @Autowired
    private RespCodeTransferService codeTransferService;

    @Pointcut("execution(public * com.gulou.test.*.controller.*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void before(JoinPoint joinPoint) throws IOException {
        //将请求开始时间放入request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(START_TIME,System.currentTimeMillis());

        if(!joinPoint.getSignature().getName().contains("ExceptionHandler")){
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                log.info("[{}] request json ; {}", request.getRequestURL().toString(),
                        JSON.toJSONString(arg, SerializerFeature.PrettyFormat));
            }
        }
    }

    @AfterReturning(value = "controller()",returning = "resp")
    public void afterReturn(BaseResp resp){

        //请求耗时
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        //设置对应显示信息
        String language = request.getHeader(LANGUAGE_TYPE);
        resp.setMsg(codeTransferService.transfer(resp,language).getMsg());

        log.info("[{}] spend : [{}]ms",request.getRequestURL().toString(),
                System.currentTimeMillis() - (Long) request.getAttribute(START_TIME));

        log.info("[{}] response json : {}",request.getRequestURL().toString(),
                JSON.toJSONString(resp,SerializerFeature.PrettyFormat));
    }
}
