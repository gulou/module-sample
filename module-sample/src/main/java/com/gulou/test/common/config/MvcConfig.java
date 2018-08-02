package com.gulou.test.common.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gulou.test.common.InterfaceSpendInterceptor;
import com.gulou.test.common.service.RespCodeTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.SortField;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

/**
 * <p>Http消息转换器</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/06/30 22:47
 */

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private RespCodeTransferService codeTransferService;

    /**
     * 通过注入方式实现消息转换器实现
     * @param converters
     */
    //配置json转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(WriteMapNullValue ,SortField,PrettyFormat);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastJsonHttpMessageConverter);
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterfaceSpendInterceptor());
        super.addInterceptors(registry);
    }
}
