package com.gulou.test.common.controller;

import com.alibaba.fastjson.JSONException;
import com.gulou.test.common.BaseResp;
import com.gulou.test.common.BaseService;
import com.gulou.test.common.BizException;
import com.gulou.test.common.enums.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

/**
 * <p>全局异常捕获</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 22:39
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseService {

    //参数绑定异常
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object bindExceptionHandler(BindException e){
        error("BindException : {}",e.getMessage(),e);
        return BaseResp.of(RespCode.PARAM_INVALID);
    }

    //参数不合法异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object argExceptionHandler(MethodArgumentNotValidException e){
        error("MethodArgumentNotValidException : {}",e.getMessage(),e);
        FieldError fieldError = (FieldError) e.getBindingResult().getAllErrors().get(0);
        return BaseResp.of(RespCode.PARAM_INVALID,fieldError.getField() + fieldError.getDefaultMessage());
    }

    //参数为空异常
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object missExceptionHandler(MissingServletRequestParameterException e){
        error("MissingServletRequestParameterException : {}",e.getMessage(),e);
        return BaseResp.of(RespCode.PARAM_INVALID,e.getMessage());
    }

    //JSON转换异常
    @ExceptionHandler(value = JSONException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object jsonExceptionHandler(JSONException e){
        error("MissingServletRequestParameterException : {}",e.getMessage(),e);
        return BaseResp.of(RespCode.PARAM_INVALID,e.getMessage());
    }


    //业务异常
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object bizExceptionHandler(BizException e){
        e.setExtraMsg(StringUtils.isBlank(e.getExtraMsg()) ? e.getRespCode().getDesc() : e.getExtraMsg());
        error("BizException : code=[{}],desc=[{}]",e.getRespCode().getCode(),e.getExtraMsg(),e);
        if(!StringUtils.isBlank(e.getExtraMsg())){
            return BaseResp.of(e.getRespCode(),e.getExtraMsg());
        }
        return BaseResp.of(e.getRespCode(),e.getExtraMsg());
    }

    //TODO gulou 完善异常捕获

    @Override
    public Logger getLogger() {
        return log;
    }
}
