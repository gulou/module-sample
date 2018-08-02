package com.gulou.test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;

/**
 * <p>基础service服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 20:57
 */

public abstract class BaseService {
    
    public abstract Logger getLogger();

    private String getMethodName(){
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    protected void info(String format,Object... args){
        getLogger().info("[" + getMethodName() + "] : " + format,args);
    }

    protected void error(String format, Object... args){
        getLogger().error("[" + getMethodName() + "] : " + format,args);
    }

    protected String toJson(Object object){
        return JSON.toJSONString(object) ;
    }
    protected String toJson(Object object, SerializerFeature... serializerFeatures){
        return JSON.toJSONString(object,serializerFeatures);
    }
}
