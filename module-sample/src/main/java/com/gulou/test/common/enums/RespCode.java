package com.gulou.test.common.enums;

import lombok.Getter;

/**
 * <p>通用返回码</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 21:10
 */

@Getter
public enum RespCode {

    SUCCESS("000000","success"),

    SYS_FAIL("100000","sys fail"),
    PARAM_INVALID("100001","param invalid"),

    MEM_USER_NOT_EXIST("201001","user not exist"),

    /**
     * 验证码
     */
    SMS_MIN_DURATION_ERROR("202001","sms min duration error"),
    SMS_MAX_TIMES_IN24H_ERROR("202002","sms max times in 24h error"),
    SMS_CODE_ERROR("202003","sms code error"),

    ;

    private String code;
    private String desc;

    RespCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
