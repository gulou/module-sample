package com.gulou.test.message.enums;

import com.gulou.test.common.enums.MessageTemplateEnum;
import lombok.Getter;

import static com.gulou.test.common.enums.MessageTemplateEnum.*;

/**
 * <p>验证码类型</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:31
 */

@Getter
public enum CodeType {

    MEM_REGISTER("MEM_REGISTER", SMS_MEM_LOGIN,"member register"),
    MEM_LOGIN("MEM_LOGIN", SMS_MEM_LOGIN,"member login"),
    ;

    private String code;
    private MessageTemplateEnum template;
    private String desc;

    CodeType(String code, MessageTemplateEnum template, String desc) {
        this.code = code;
        this.template = template;
        this.desc = desc;
    }
}
