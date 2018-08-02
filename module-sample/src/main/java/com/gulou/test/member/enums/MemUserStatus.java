package com.gulou.test.member.enums;

import lombok.Getter;

/**
 * <p>用户状态</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 19:54
 */

@Getter
public enum MemUserStatus {

    NORMAL(0,"normal"),
    ABNORMAL(1,"abnormal"),
    ;

    private Integer code;
    private String desc;

    MemUserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
