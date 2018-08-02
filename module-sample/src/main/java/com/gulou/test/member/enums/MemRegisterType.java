package com.gulou.test.member.enums;

import lombok.Getter;

/**
 * <p>注册/登录类型</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 20:46
 */

@Getter
public enum MemRegisterType {

    MOBILE(0,"mobile"),
    EMAIL(1,"email"),
    ;

    private Integer code;
    private String desc;

    MemRegisterType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
