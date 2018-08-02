package com.gulou.test.common.service;

import com.gulou.test.common.BaseResp;
import com.gulou.test.common.BaseService;
import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.common.po.LanguageDesc;
import com.gulou.test.common.po.RespCodeRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>返回码信息转换</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/05 11:08
 */

@Service
@Slf4j
public class RespCodeTransferService extends BaseService {

    @Autowired
    private LanguageDescService descService;
    @Autowired
    private RespCodeService codeService;


    private boolean canTransfer(BaseResp resp, String language) {
        return !StringUtils.isBlank(resp.getCode())
                && containsLanguage(language);
    }

    private boolean containsLanguage(String language) {
        for (LanguageType languageType : LanguageType.values()) {
            if (languageType.name().equals(language)) {
                return true;
            }
        }
        return false;
    }

    public BaseResp transfer(BaseResp resp, String language) {

        if (!canTransfer(resp, language)) {
            return resp;
        }
        LanguageType languageType = LanguageType.valueOf(language);

        //查询对应返回码语言版本的提示信息
        RespCodeRecord codeRecord = codeService.queryByCode(Long.valueOf(resp.getCode()));
        if (codeRecord == null) {
            //数据库没有对应返回码
            return resp;
        }

        LanguageDesc languageDesc = descService.queryByDescIdAndLanguage(codeRecord.getDescId(), languageType);
        if (languageDesc != null) {
            resp.setMsg(languageDesc.getDescription());
        }

        return resp;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
