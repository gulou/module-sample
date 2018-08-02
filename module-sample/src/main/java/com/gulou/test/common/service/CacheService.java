package com.gulou.test.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>缓存服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/30 16:48
 */

@Service
public class CacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value, long timeout,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    public void increment(String key,long delta){
        redisTemplate.opsForValue().increment(key,delta) ;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }
}
