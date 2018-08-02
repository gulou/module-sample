package com.gulou.test.common.config;

import com.gulou.test.common.annot.QuartzJob;
import com.gulou.test.common.QuartzAbstractJob;
import com.gulou.test.common.SpringBeanJobFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Properties;

/**
 * quartz框架和spring整合
 */
@Slf4j
@Configuration
public class QuartzJobConfig {

    @Value("${quartzJob.scheduler.instanceName:base}")
    @NotBlank
    private String schedulerInstanceName; //调度器实例名

    @Value("${quartzJob.threadPool.threadCount:30}")
    @NotNull
    @Min(1)
    private Integer threadPoolThreadCount; //调度线程池


    @Autowired
    private ConfigurableApplicationContext context; //spring上下文

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,
                                                     PlatformTransactionManager transactionManager,
                                                     SpringBeanJobFactory springBeanJobFactory) {
        registerCronTriggerBean();

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setStartupDelay(5);
        schedulerFactoryBean.setSchedulerName(schedulerInstanceName);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        schedulerFactoryBean.setTransactionManager(transactionManager);

        Properties quartzProperties = new Properties();
        quartzProperties.put("org.quartz.scheduler.instanceId", "AUTO");
        quartzProperties.put("org.quartz.scheduler.skipUpdateCheck", "true");
        quartzProperties.put("org.quartz.threadPool.threadCount", threadPoolThreadCount.toString());
        quartzProperties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        quartzProperties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        quartzProperties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        quartzProperties.put("org.quartz.jobStore.useProperties", "true");
        quartzProperties.put("org.quartz.jobStore.isClustered", "true");
        schedulerFactoryBean.setQuartzProperties(quartzProperties);

        Trigger[] triggers = context.getBeansOfType(Trigger.class).values().toArray(new Trigger[]{});//label : trigger
        schedulerFactoryBean.setTriggers(triggers);

        log.info("scheduler init success, has {} triggers", triggers.length);

        return schedulerFactoryBean;
    }

    //将JobDetail和trigger注入到spring容器中
    private void registerCronTriggerBean() {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
        Collection<QuartzAbstractJob> jobBeans = context.getBeansOfType(QuartzAbstractJob.class).values();

        for (QuartzAbstractJob jobBean : jobBeans) {
            // 注册 Job对应的JobDetail到spring容器(用于触发job时获取)
            BeanDefinitionBuilder jobDetailBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(JobDetailFactoryBean.class);
            jobDetailBeanBuilder.addPropertyValue("jobClass", jobBean.getClass());
            jobDetailBeanBuilder.addPropertyValue("durability", true);

            String jobDetailBeanName = jobBean.getClass().getName() + "_JobDetail";
            factory.registerBeanDefinition(jobDetailBeanName, jobDetailBeanBuilder.getBeanDefinition());
            log.info("{} bean registered", jobDetailBeanName);

            // 注册 Job对应的CronTrigger到spring容器(用于label : trigger获取)
            String cronExpression = jobBean.getClass().getAnnotation(QuartzJob.class).cron();

            BeanDefinitionBuilder cronTriggerBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(CronTriggerFactoryBean.class);
            cronTriggerBeanBuilder.addPropertyValue("jobDetail", context.getBean(jobDetailBeanName));
            cronTriggerBeanBuilder.addPropertyValue("cronExpression", cronExpression);

            String cronTriggerBeanName = jobBean.getClass().getName() + "_CronTrigger";
            factory.registerBeanDefinition(cronTriggerBeanName, cronTriggerBeanBuilder.getBeanDefinition());
            log.info("{} bean registered, cronExpression={}", cronTriggerBeanName, cronExpression);
        }
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }

}
