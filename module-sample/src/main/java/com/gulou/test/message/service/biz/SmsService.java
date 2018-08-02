package com.gulou.test.message.service.biz;

import com.gulou.test.common.BaseService;
import com.gulou.test.common.BizException;
import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.common.enums.RespCode;
import com.gulou.test.common.service.CacheService;
import com.gulou.test.message.api.SmsServiceApi;
import com.gulou.test.message.enums.CodeType;
import com.gulou.test.message.po.MessageTemplate;
import com.gulou.test.message.service.dao.MessageTemplateDaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gulou.test.common.enums.CacheKeyPrefix.*;
import static com.gulou.test.common.enums.RespCode.SMS_CODE_ERROR;
import static com.gulou.test.common.enums.RespCode.SMS_MAX_TIMES_IN24H_ERROR;

/**
 * <p>短息服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:30
 */

@Service
@Slf4j
public class SmsService extends BaseService implements SmsServiceApi{

    @Autowired
    private CacheService cacheService;
    @Autowired
    private MessageTemplateDaoService templateDaoService;

    /**
     * 发送短信验证码
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @param languageType
     */
    @Override
    public void send(String mobileArea, String mobileNum, CodeType codeType,
                     Map<String, String> replaceParam, LanguageType languageType) {

        canSend(mobileArea, mobileNum, codeType);

        Pair<String, String> pair = populateTemplate(replaceParam, codeType, languageType);

        doSend(mobileArea, mobileNum, codeType, pair.getRight());
    }

    /**
     * 校验短信验证码
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @param code
     */
    @Override
    public void validate(String mobileArea, String mobileNum, CodeType codeType, String code) {

        String codeKey = buildCodeKey(mobileArea, mobileNum, codeType);

        //校验
        String realCode = cacheService.get(codeKey);

        info("is code right,[{}].inputCode=[{}],unique=[{}]", !StringUtils.isBlank(realCode) || !code.equals(realCode)
                , code
                , buildValidateKey(mobileArea, mobileNum, codeType));

        if (StringUtils.isBlank(realCode) || !code.equals(realCode)) {
            ifToDel(mobileArea, mobileNum, codeType);
            throw new BizException(SMS_CODE_ERROR);
        }

        //删除验证码,删除错误次数
        long expireTime = cacheService.getExpire(codeKey);
        cacheService.delete(codeKey);
        cacheService.delete(buildCodeErrorKey(mobileArea, mobileNum, codeType));

        //设置校验有效时间
        info("validated flag time,[{}].unique=[{}]", expireTime, buildValidateKey(mobileArea, mobileNum, codeType));
        cacheService.set(buildValidateKey(mobileArea, mobileNum, codeType), Boolean.TRUE.toString(), expireTime, TimeUnit.SECONDS);
    }

    /**
     * 判断验证码是否已校验
     *
     * @param mobileArea
     * @param mobileNum
     * @param codeType
     * @return
     */
    @Override
    public Boolean hasValid(String mobileArea, String mobileNum, CodeType codeType) {
        info("is has valid,[{}],unique=[{}]", !StringUtils.isBlank(cacheService.get(buildValidateKey(mobileArea, mobileNum, codeType)))
                , buildValidateKey(mobileArea, mobileNum, codeType));
        return !StringUtils.isBlank(cacheService.get(buildValidateKey(mobileArea, mobileNum, codeType)));
    }

    private void ifToDel(String mobileArea, String mobileNum, CodeType codeType) {

        String codeErrorKey = buildCodeErrorKey(mobileArea, mobileNum, codeType);
        String errorTimes = cacheService.get(codeErrorKey);
        long expireTime = cacheService.getExpire(buildCodeKey(mobileArea, mobileNum, codeType));

        //增加错误次数
        if (StringUtils.isBlank(errorTimes)) {
            info("is code invalid,[{}],unique=[{}]", expireTime == -2, codeErrorKey);
            if (expireTime == -2) {
                //验证码已失效,删除错误次数,返回
                cacheService.delete(buildCodeErrorKey(mobileArea, mobileNum, codeType));
                return;
            }
            cacheService.set(codeErrorKey, "1", expireTime, TimeUnit.SECONDS);
        }
        else {
            cacheService.increment(codeErrorKey, 1L);
        }

        errorTimes = cacheService.get(codeErrorKey);

        info("if to delete code,[{}],errorTimes=[{}],unique=[{}]", !StringUtils.isBlank(errorTimes) && Integer.valueOf(errorTimes) > 3
                , errorTimes
                , buildCodeKey(mobileArea, mobileNum, codeType));

        if (!StringUtils.isBlank(errorTimes) && Integer.valueOf(errorTimes) > 3) {
            //删除验证码
            cacheService.delete(buildCodeKey(mobileArea, mobileNum, codeType));
            //删除错误次数
            cacheService.delete(buildCodeErrorKey(mobileArea, mobileNum, codeType));
        }
    }

    private void canSend(String mobileArea, String mobileNum, CodeType codeType) {

        //校验最短间隔时间
        checkMinDuration(mobileArea, mobileNum, codeType);

        //判断24H内发送次数
        checkMaxTimesIn24H(mobileArea, mobileNum, codeType);
    }

    private void checkMinDuration(String mobileArea, String mobileNum, CodeType codeType) {

        String minDurationKey = buildMinDurationKey(mobileArea, mobileNum, codeType);

        info("is minDurationKey exist,[{}].unique=[{}]", !StringUtils.isBlank(cacheService.get(minDurationKey)),
                buildMinDurationKey(mobileArea, mobileNum, codeType));

        if (!StringUtils.isBlank(cacheService.get(minDurationKey))) {
            throw new BizException(RespCode.SMS_MIN_DURATION_ERROR);
        }
    }

    private void checkMaxTimesIn24H(String mobileArea, String mobileNum, CodeType codeType) {

        String maxTimesIn24HKey = buildMaxTimesIn24HKey(mobileArea, mobileNum, codeType);
        String maxTimesIn24H = null;

        info("is send times > 7 in 24h,[{}].unique=[{}]", !StringUtils.isBlank(maxTimesIn24H = cacheService.get(maxTimesIn24HKey))
                && Integer.valueOf(maxTimesIn24H) > 7, buildMaxTimesIn24HKey(mobileArea, mobileNum, codeType));

        //限制值不为空且次数大于7次返回相应返回码
        if (!StringUtils.isBlank(maxTimesIn24H = cacheService.get(maxTimesIn24HKey))
                && Integer.valueOf(maxTimesIn24H) > 7) {
            throw new BizException(SMS_MAX_TIMES_IN24H_ERROR);
        }
    }

    private void doSend(String mobileArea, String mobileNum, CodeType codeType, String content) {

        String maxTimesIn24HKey = buildMaxTimesIn24HKey(mobileArea, mobileNum, codeType);
        String code = genCode();

        //todo gulou 调用发送服务
        info("send message success,mobile=[{}]", mobileArea + "-" + mobileNum);
        info("message content:{}",content);

        //设置发送最小时间间隔
        cacheService.set(buildMinDurationKey(mobileArea, mobileNum, codeType), Boolean.TRUE.toString(), 60, TimeUnit.SECONDS);

        //增加发送24H发送次数
        if (StringUtils.isBlank(cacheService.get(maxTimesIn24HKey))) {
            cacheService.set(maxTimesIn24HKey, "1", 24, TimeUnit.HOURS);
        }
        else {
            cacheService.increment(buildMaxTimesIn24HKey(mobileArea, mobileNum, codeType), 1);
        }

        //发送成功设置验证码,清除之前的验证标识
        cacheService.set(buildCodeKey(mobileArea, mobileNum, codeType), code, 15, TimeUnit.MINUTES);
        cacheService.delete(buildValidateKey(mobileArea, mobileNum, codeType));

        info("send success,unique=[{}]", buildCodeKey(mobileArea, mobileNum, codeType));
    }

    private Pair<String, String> populateTemplate(Map<String, String> replaceParam, CodeType codeType, LanguageType languageType) {

        MessageTemplate template = templateDaoService.queryByCodeAndLanguage(codeType.getTemplate().getCode(), languageType);
        template = ifNullDefault(template, codeType);

        return Pair.of(template.getSubject(), populateContent(template, replaceParam));
    }

    private String populateContent(MessageTemplate template, Map<String, String> replaceParam) {

        String content = template.getContent();

        for (Map.Entry<String, String> entry : replaceParam.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        return content;
    }

    private MessageTemplate ifNullDefault(MessageTemplate template, CodeType codeType) {
        if(template == null){
            return templateDaoService.queryByCodeAndLanguage(codeType.getTemplate().getCode(), LanguageType.SIMPLIFIED_CHINESE);
        }
        return template;
    }

    //构建redis key
    private String buildMinDurationKey(String mobileArea, String mobileNum, CodeType codeType) {
        return SMS_MIN_DURATION + SPLIT + mobileArea + mobileNum + SPLIT + codeType;
    }

    private String buildMaxTimesIn24HKey(String mobileArea, String mobileNum, CodeType codeType) {
        return SMS_MAX_TIMES_IN_24H + SPLIT + mobileArea + mobileNum + SPLIT + codeType;
    }

    private String buildCodeKey(String mobileArea, String mobileNum, CodeType codeType) {
        return SMS_CODE + SPLIT + mobileArea + mobileNum + SPLIT + codeType;
    }

    private String buildValidateKey(String mobileArea, String mobileNum, CodeType codeType) {
        return SMS_VALIDATE + SPLIT + mobileArea + mobileNum + SPLIT + codeType;
    }

    private String buildCodeErrorKey(String mobileArea, String mobileNum, CodeType codeType) {
        return SMS_CODE_ERROR_TIMES + SPLIT + mobileArea + mobileNum + SPLIT + codeType;
    }

    private String genCode() {
        return RandomStringUtils.random(6, "1234567890");
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
