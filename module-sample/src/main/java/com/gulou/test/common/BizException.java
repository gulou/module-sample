package com.gulou.test.common;

import com.gulou.test.common.enums.RespCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>通用业务异常</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 21:31
 */

@Getter
@Setter
public class BizException extends RuntimeException{

    private RespCode respCode;
    private String extraMsg;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizException(RespCode respCode) {
        this.respCode = respCode;
    }

    public BizException(RespCode respCode, String extraMsg) {
        super(extraMsg);
        this.respCode = respCode;
        this.extraMsg = extraMsg;
    }
}
