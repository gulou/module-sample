package com.gulou.test.message.controller;

import com.gulou.test.common.BaseResp;
import com.gulou.test.common.BizException;
import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.common.enums.RespCode;
import com.gulou.test.message.bo.SmsSendReq;
import com.gulou.test.message.bo.SmsValidateReq;
import com.gulou.test.message.service.biz.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>短信发送接口</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:25
 */

@RestController()
@RequestMapping("/v1/message/sms/")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("send")
    public BaseResp<Void> send(@RequestBody @Valid SmsSendReq sendReq,
                               @RequestHeader(value = "language-type")LanguageType languageType) {

        smsService.send(sendReq.getMobileArea(), sendReq.getMobileNum(), sendReq.getCodeType()
                , sendReq.getParam(),languageType);

        return BaseResp.of(RespCode.SUCCESS);
    }

    @PostMapping("validate")
    public BaseResp<Void> validate(@RequestBody @Valid SmsValidateReq validateReq) {

        smsService.validate(validateReq.getMobileArea(), validateReq.getMobileNum(), validateReq.getCodeType(),
                validateReq.getCode());

        return BaseResp.of(RespCode.SUCCESS);
    }

    @PostMapping("has-validated")
    public BaseResp<Void> hasValidated(@RequestBody @Valid SmsValidateReq validateReq) {

        Boolean result = smsService.hasValid(validateReq.getMobileArea(), validateReq.getMobileNum(), validateReq.getCodeType());
        if (!result) {
            throw new BizException(RespCode.SMS_CODE_ERROR);
        }

        return BaseResp.of(RespCode.SUCCESS);
    }

}
