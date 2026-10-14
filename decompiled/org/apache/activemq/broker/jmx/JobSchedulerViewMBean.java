/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface JobSchedulerViewMBean {
    @MBeanInfo(value="remove jobs with matching execution time")
    public void removeAllJobsAtScheduledTime(@MBeanInfo(value="time: yyyy-MM-dd hh:mm:ss") String var1) throws Exception;

    @MBeanInfo(value="remove jobs with matching jobId")
    public void removeJob(@MBeanInfo(value="jobId") String var1) throws Exception;

    @MBeanInfo(value="remove all scheduled jobs")
    public void removeAllJobs() throws Exception;

    @MBeanInfo(value="remove all scheduled jobs between time ranges ")
    public void removeAllJobs(@MBeanInfo(value="start: yyyy-MM-dd hh:mm:ss") String var1, @MBeanInfo(value="finish: yyyy-MM-dd hh:mm:ss") String var2) throws Exception;

    @MBeanInfo(value="get the next time a job is due to be scheduled ")
    public String getNextScheduleTime() throws Exception;

    @MBeanInfo(value="get the next time a job is due to be scheduled ")
    public int getExecutionCount(@MBeanInfo(value="jobId") String var1) throws Exception;

    @MBeanInfo(value="get the next job(s) to be scheduled. Not HTML friendly ")
    public TabularData getNextScheduleJobs() throws Exception;

    @MBeanInfo(value="get the scheduled Jobs in the Store. Not HTML friendly ")
    public TabularData getAllJobs() throws Exception;

    @MBeanInfo(value="get the scheduled Jobs in the Store within the time range. Not HTML friendly ")
    public TabularData getAllJobs(@MBeanInfo(value="start: yyyy-MM-dd hh:mm:ss") String var1, @MBeanInfo(value="finish: yyyy-MM-dd hh:mm:ss") String var2) throws Exception;
}

