package com.gulou.test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>基础BO</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/29 21:01
 */

@Getter
@Setter
@Slf4j
public class BaseModel {

    @Override
    public String toString() {
        try {
            JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error("json to string error,class=[{}]",this.getClass().getName());
            return this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
        }
        return super.toString();
    }

}
