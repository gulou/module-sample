package com.gulou.test.common;

import com.gulou.test.common.enums.RespCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>基础响应信息</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 21:08
 */

//TODO gulou 通用的东西可以单独打成jar包
@Getter
@Setter
public class BaseResp<T> {

    private String code;//返回码
    private String msg;//描述信息
    private T data;//返回数据

    private BaseResp(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getDesc();
    }

    private BaseResp(RespCode respCode,T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getDesc();
        this.data = data;
    }

    private BaseResp(RespCode respCode,String msg) {
        this.code = respCode.getCode();
        this.msg = msg;
    }

    public static <T> BaseResp<T> of(RespCode respCode){
        return new BaseResp<T>(respCode);
    }

    public static <T> BaseResp<T> of(RespCode respCode,T data){
        return new BaseResp<T>(respCode,data);
    }

    public static <T> BaseResp<T> of(RespCode respCode,String msg){
        return new BaseResp<T>(respCode,msg);
    }
}
