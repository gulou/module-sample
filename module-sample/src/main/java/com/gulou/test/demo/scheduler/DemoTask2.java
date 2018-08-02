package com.gulou.test.demo.scheduler;

import com.gulou.test.common.QuartzAbstractJob;
import com.gulou.test.demo.service.scheduler.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>定时任务示例</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/01 14:27
 */

//@QuartzJob(cron = "*/5 * * * * ?")
@Slf4j
public class DemoTask2 extends QuartzAbstractJob {

    @Autowired
    private DemoService demoService;

    @Override
    protected void executeJob(JobExecutionContext context) throws Exception {
        demoService.demo2(this);
    }
}
