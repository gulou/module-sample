package com.gulou.test.member.bo;

import com.gulou.test.common.BaseModel;
import com.gulou.test.member.enums.MemRegisterType;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>登录</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 20:41
 */

@Getter
@Setter
public class MemLoginReqBO extends BaseModel{
    private String mobileArea;
    private String mobileNum;
    private String email;
    private String loginPwd;
    private MemRegisterType loginType;
}
