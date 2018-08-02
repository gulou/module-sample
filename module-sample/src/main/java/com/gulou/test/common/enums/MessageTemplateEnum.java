package com.gulou.test.common.enums;

import lombok.Getter;

import static com.gulou.test.common.enums.MessageTemplateEnum.TemplateType.SMS;

/**
 * <p>消息模板</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/31 12:47
 */

@Getter
public enum MessageTemplateEnum {

    SMS_MEM_LOGIN(SMS,0,"sms template of member login") ,
    SMS_MEM_REGISTER(SMS,1,"sms template of member register") ,
    ;

    private TemplateType type;//模板类型
    private Integer code;//模板代码
    private String desc;//描述

    MessageTemplateEnum(TemplateType type, Integer code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public enum TemplateType{
        SMS,EMAIL
    }
}
