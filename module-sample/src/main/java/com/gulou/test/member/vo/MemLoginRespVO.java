package com.gulou.test.member.vo;

import com.gulou.test.common.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>注册/登录返回</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 20:50
 */

@Getter
@Setter
public class MemLoginRespVO extends BaseModel{
    private String mobile;
    private String email;
    private String name;
}
