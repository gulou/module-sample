package com.gulou.test.member.bo;

import com.gulou.test.common.BaseModel;
import com.gulou.test.member.enums.MemRegisterType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p>注册</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 20:44
 */

@Getter
@Setter
public class MemRegisterReqBO extends BaseModel{
    private String mobileArea;
    private String mobileNum;
    private String email;
    @NotBlank
    private String loginPwd;
    @NotBlank
    private String name;
    @NotNull
    private MemRegisterType registerType;
}
