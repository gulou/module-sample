package com.gulou.test.common.annot;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <p>定时任务注解</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/03 21:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface QuartzJob {
    String cron();
    String description() default "";
    String group() default "DEFAULT";
}
