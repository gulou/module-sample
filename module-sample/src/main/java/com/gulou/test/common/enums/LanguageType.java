package com.gulou.test.common.enums;

import lombok.Getter;

/**
 * <p>语言类型</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/05 10:45
 */

@Getter
public enum LanguageType {

    SIMPLIFIED_CHINESE(0,"SIMPLIFIED_CHINESE"),
    TRADITIONAL_CHINESE(1,"TRADITIONAL_CHINESE"),
    ENGLISH(2,"ENGLISH"),
    ;

    private Integer code;
    private String desc;

    LanguageType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
