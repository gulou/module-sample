package com.gulou.test.common;

import org.quartz.SchedulerContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 重写这个jobFactory可以让job纳入spring容器进行管理,
 * 以支持在job中注入service
 */
public class SpringBeanJobFactory extends AdaptableJobFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String[] ignoredUnknownProperties;
    private SchedulerContext schedulerContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Specify the unknown properties (not found in the bean) that should be ignored.
     * <p>Default is {@code null}, indicating that all unknown properties
     * should be ignored. Specify an empty array to throw an exception in case
     * of any unknown properties, or a list of property names that should be
     * ignored if there is no corresponding property found on the particular
     * job class (all other unknown properties will still trigger an exception).
     */
    public void setIgnoredUnknownProperties(String... ignoredUnknownProperties) {
        this.ignoredUnknownProperties = ignoredUnknownProperties;
    }

    public void setSchedulerContext(SchedulerContext schedulerContext) {
        this.schedulerContext = schedulerContext;
    }


    /**
     * Create the job instance, populating it with property values taken
     * from the scheduler context, job data map and trigger data map.
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //此处改造,通过从spring容器中获取对应的JobDetail,
        //从而支持在job中通过@Autowired注入service
        Object job = applicationContext.getBean(bundle.getJobDetail().getJobClass());
        if (isEligibleForPropertyPopulation(job)) {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(job);
            MutablePropertyValues pvs = new MutablePropertyValues();
            if (this.schedulerContext != null) {
                pvs.addPropertyValues(this.schedulerContext);
            }
            pvs.addPropertyValues(bundle.getJobDetail().getJobDataMap());
            pvs.addPropertyValues(bundle.getTrigger().getJobDataMap());
            if (this.ignoredUnknownProperties != null) {
                for (String propName : this.ignoredUnknownProperties) {
                    if (pvs.contains(propName) && !bw.isWritableProperty(propName)) {
                        pvs.removePropertyValue(propName);
                    }
                }
                bw.setPropertyValues(pvs);
            }
            else {
                bw.setPropertyValues(pvs, true);
            }
        }
        return job;
    }

    /**
     * Return whether the given job object is eligible for having
     * its bean properties populated.
     * <p>The default implementation ignores {@link QuartzJobBean} instances,
     * which will inject bean properties themselves.
     *
     * @param jobObject the job object to introspect
     * @see QuartzJobBean
     */
    protected boolean isEligibleForPropertyPopulation(Object jobObject) {
        return (!(jobObject instanceof QuartzJobBean));
    }

}
