package com.gulou.test.message.api;

import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.message.enums.CodeType;

import java.util.Map;

/**
 * <p>短息服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:30
 */

public interface SmsServiceApi {

    /**
     * 发送短信验证码
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @param languageType
     */
    void send(String mobileArea, String mobileNum, CodeType codeType,
                     Map<String, String> replaceParam, LanguageType languageType);

    /**
     * 校验短信验证码
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @param code
     */
    void validate(String mobileArea, String mobileNum, CodeType codeType, String code);

    /**
     * 判断验证码是否已校验
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @return
     */
    Boolean hasValid(String mobileArea, String mobileNum, CodeType codeType);
}
