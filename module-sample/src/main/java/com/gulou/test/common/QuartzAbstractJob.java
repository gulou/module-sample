package com.gulou.test.common;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时调度抽象类
 */
@DisallowConcurrentExecution
public abstract class QuartzAbstractJob extends QuartzJobBean {

    private static final Logger TASK_LOGGER = LoggerFactory.getLogger("TASK_LOGGER");

    protected abstract void executeJob(JobExecutionContext context) throws Exception;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jobClassName = this.getClass().getName();

        TASK_LOGGER.info("quartz job [{}] execute start", jobClassName);
        try {
            executeJob(context);
        } catch (Exception e) {
            TASK_LOGGER.error("quartz job " + jobClassName + " throw exception: " + e.getMessage(), e);
            if (e instanceof JobExecutionException) {
                throw (JobExecutionException) e;
            }
            else {
                throw new JobExecutionException(e);
            }
        }

        TASK_LOGGER.info("quartz job [{}] execute finish", jobClassName);
    }

    private String getMethodName(){
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    public void info(String format,Object... args){
        TASK_LOGGER.info(this.getClass().getSimpleName() + "[{}] : " + format,getMethodName(),args);
    }

    public void error(String format,Object... args){
        TASK_LOGGER.error(this.getClass().getSimpleName() + "[{}] : " + format,getMethodName(),args);
    }
}
