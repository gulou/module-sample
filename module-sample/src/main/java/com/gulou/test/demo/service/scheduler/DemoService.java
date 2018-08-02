package com.gulou.test.demo.service.scheduler;

import com.gulou.test.common.QuartzAbstractJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>定时任务示例服务</p>
 * 测试分布式定时任务(Quartz通过数据库实现),当只有一个定时任务时,
 * 只会在单一节点上执行,当有多个定时任务时会实现负载均衡
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/01 14:28
 */

@Service
public class DemoService {

    @Value("${server.port}")
    private String port;

    public void demo1(QuartzAbstractJob job){
        job.info("demo1 service start at port,port=[{}]",port);

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //业务逻辑

//        info("demo service end at port,port=[{}]",port);
    }

    public void demo2(QuartzAbstractJob job){
        job.info("demo2 service start at port,port=[{}]",port);

        //业务逻辑

//        info("demo service end at port,port=[{}]",port);
    }

    public void demo3(QuartzAbstractJob job){
        job.info("demo3 service start at port,port=[{}]",port);

        //业务逻辑

//        info("demo service end at port,port=[{}]",port);
    }
}
