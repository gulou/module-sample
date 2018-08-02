package com.gulou.test.common.enums;

import lombok.Getter;

/**
 * <p>缓存key前缀</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 17:20
 */

@Getter
public enum CacheKeyPrefix {


    SMS_MIN_DURATION("SMS_MIN_DURATION"),
    SMS_MAX_TIMES_IN_24H("SMS_MAX_TIMES_IN_24H"),
    SMS_CODE("SMS_CODE"),
    SMS_VALIDATE("SMS_VALIDATE"),
    SMS_CODE_ERROR_TIMES("SMS_CODE_ERROR_TIMES"),

    ;

    public static final String SPLIT = "_";

    private String code;

    CacheKeyPrefix(String code) {
        this.code = code;
    }
}
