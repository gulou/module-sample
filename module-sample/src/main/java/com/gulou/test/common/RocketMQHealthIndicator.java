package com.gulou.test.common;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * <p>rocketmq状态检测</p>
 * 通过spring-actuator实现
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/06 14:49
 */

@Component
public class RocketMQHealthIndicator implements HealthIndicator{
    @Override
    public Health health() {

        return Health.up().build();
    }
}
