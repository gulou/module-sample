package com.gulou.test.message.bo;

import com.gulou.test.common.BaseModel;
import com.gulou.test.message.enums.CodeType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * <p>短信发送请求</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:27
 */

@Getter
@Setter
public class SmsSendReq extends BaseModel{
    @NotNull
    private CodeType codeType;
    @NotBlank
    private String mobileArea;
    @NotBlank
    private String mobileNum;
    @NotNull
    private Map<String,String> param;
}
