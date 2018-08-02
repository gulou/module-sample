package com.gulou.test.member.service.biz;
import com.gulou.test.account.api.AccountServiceApi;
import com.gulou.test.common.BaseService;
import com.gulou.test.common.BizException;
import com.gulou.test.common.TransactionHelper;
import com.gulou.test.common.enums.RespCode;
import com.gulou.test.common.service.UniqueIdGenService;
import com.gulou.test.common.utils.PasswordUtil;
import com.gulou.test.member.bo.MemLoginReqBO;
import com.gulou.test.member.bo.MemRegisterReqBO;
import com.gulou.test.member.enums.MemRegisterType;
import com.gulou.test.member.enums.MemUserStatus;
import com.gulou.test.member.po.MemUser;
import com.gulou.test.member.service.dao.MemUserDaoService;
import com.gulou.test.member.utils.FormatUtil;
import com.gulou.test.member.vo.MemLoginRespVO;
import com.gulou.test.message.api.SmsServiceApi;
import com.gulou.test.message.enums.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>用户相关</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 20:40
 */

@Service
@Slf4j
public class MemUserService extends BaseService {

    @Autowired
    private MemUserDaoService userDaoService;
    @Autowired
    private SmsServiceApi smsServiceApi;
    @Autowired
    private UniqueIdGenService idGenService;
    @Autowired
    private AccountServiceApi accountServiceApi;
    @Autowired
    private TransactionHelper transactionHelper;

    public MemLoginRespVO register(MemRegisterReqBO reqBO) {

        MemLoginRespVO respVO = null;

        //限制昵称
        checkNickName(reqBO.getName());

        info("register type,[{}],mobile=[{}]", reqBO.getRegisterType(), reqBO.getMobileNum());
        //根据注册类型调用
        if (MemRegisterType.MOBILE.equals(reqBO.getRegisterType())) {
            respVO = rgByMobile(reqBO.getMobileArea(), reqBO.getMobileNum(), reqBO.getLoginPwd(), reqBO.getName());
        }
        else if (MemRegisterType.EMAIL.equals(reqBO.getRegisterType())) {
            respVO = rgByEmail(reqBO.getEmail(), reqBO.getLoginPwd(), reqBO.getName());
        }
        else {
            throw new BizException(RespCode.SYS_FAIL);
        }

        return respVO;
    }


    public MemLoginRespVO login(MemLoginReqBO reqBO) {

        MemLoginRespVO respVO = null;

        info("register type,[{}],mobile=[{}],email=[{}]", reqBO.getLoginType(), reqBO.getMobileNum(), reqBO.getEmail());
        //根据登录类型调用
        if (MemRegisterType.MOBILE.equals(reqBO.getLoginType())) {
            respVO = liByMobile(reqBO.getMobileArea(), reqBO.getMobileNum(), reqBO.getLoginPwd());
        }
        else if (MemRegisterType.EMAIL.equals(reqBO.getLoginType())) {
            respVO = liByEmail(reqBO.getEmail(), reqBO.getLoginPwd());
        }
        else {
            throw new BizException(RespCode.SYS_FAIL);
        }

        return respVO;
    }

    public MemLoginRespVO rgByMobile(String mobileArea, String mobileNum,
                                     String loginPwd, String name) {
        //格式化
        Pair<String, String> mobile = FormatUtil.mobile(mobileArea, mobileNum);

        //校验验证码
        if (!smsServiceApi.hasValid(mobile.getLeft(), mobile.getRight(), CodeType.MEM_REGISTER)) {
            error("not valid sms code,mobile=[{}]", mobile.getLeft() + "-" + mobile.getRight());
            throw new BizException(RespCode.SYS_FAIL);
        }

        //初始化用户信息
        MemUser initUser = buildMemUser(mobile,null,loginPwd,name);
        //初始化账务信息
        accountServiceApi.createAccount();
        //其他处理

        //事务处理
        transactionHelper.doTransaction(() ->{
            //创建用户
            userDaoService.insert(initUser);

            //创建账户

        });

        MemLoginRespVO respVO = new MemLoginRespVO();
        respVO.setMobile(FormatUtil.mobileMask(initUser.getMobileNum()));
        respVO.setEmail(FormatUtil.emailMask(initUser.getEmail()));
        respVO.setName(initUser.getName());

        return respVO;
    }

    public MemLoginRespVO rgByEmail(String email,
                                    String loginPwd, String name) {
        //格式化
        email = FormatUtil.email(email);

        //校验验证码

        return null;
    }

    public MemLoginRespVO liByMobile(String mobileArea, String mobileNum,
                                     String loginPwd) {

        return null;
    }

    public MemLoginRespVO liByEmail(String email, String loginPwd) {

        return null;
    }

    private void checkNickName(String name) {
        //todo gulou 限制规则
    }

    private MemUser buildMemUser(Pair<String,String> mobile,String email,String loginPwd,String name){

        MemUser memUser = new MemUser();
        memUser.setUserId(idGenService.nextId());
        memUser.setMobileArea(mobile.getLeft());
        memUser.setMobileNum(mobile.getRight());
        memUser.setEmail(email);
        memUser.setLoginPwd(PasswordUtil.create(loginPwd));
        memUser.setName(name);
        memUser.setStatus(MemUserStatus.NORMAL.getCode());
        memUser.setCreateAt(new Date());
        memUser.setUpdateAt(new Date());

        return memUser;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
