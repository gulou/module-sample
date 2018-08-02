package com.gulou.test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>单元测试</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/18 19:48
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    public String toJson(Object object){
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat) ;
    }
}
