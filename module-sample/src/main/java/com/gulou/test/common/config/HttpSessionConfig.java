package com.gulou.test.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * <p>RedisSession配置</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/04 18:09
 */

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

}
