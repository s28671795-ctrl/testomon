/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.List;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.broker.jmx.JobSchedulerViewMBean;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobScheduler;
import org.apache.activemq.broker.scheduler.JobSupport;

public class JobSchedulerView
implements JobSchedulerViewMBean {
    private final JobScheduler jobScheduler;

    public JobSchedulerView(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @Override
    public TabularData getAllJobs() throws Exception {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(Job.class);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("Scheduled Jobs", "Scheduled Jobs", ct, new String[]{"jobId"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        List<Job> jobs = this.jobScheduler.getAllJobs();
        for (Job job : jobs) {
            rc.put(new CompositeDataSupport(ct, factory2.getFields(job)));
        }
        return rc;
    }

    @Override
    public TabularData getAllJobs(String startTime, String finishTime) throws Exception {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(Job.class);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("Scheduled Jobs", "Scheduled Jobs", ct, new String[]{"jobId"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        long start2 = JobSupport.getDataTime(startTime);
        long finish = JobSupport.getDataTime(finishTime);
        List<Job> jobs = this.jobScheduler.getAllJobs(start2, finish);
        for (Job job : jobs) {
            rc.put(new CompositeDataSupport(ct, factory2.getFields(job)));
        }
        return rc;
    }

    @Override
    public TabularData getNextScheduleJobs() throws Exception {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(Job.class);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("Scheduled Jobs", "Scheduled Jobs", ct, new String[]{"jobId"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        List<Job> jobs = this.jobScheduler.getNextScheduleJobs();
        for (Job job : jobs) {
            rc.put(new CompositeDataSupport(ct, factory2.getFields(job)));
        }
        return rc;
    }

    @Override
    public String getNextScheduleTime() throws Exception {
        long time = this.jobScheduler.getNextScheduleTime();
        return JobSupport.getDateTime(time);
    }

    @Override
    public void removeAllJobs() throws Exception {
        this.jobScheduler.removeAllJobs();
    }

    @Override
    public void removeAllJobs(String startTime, String finishTime) throws Exception {
        long start2 = JobSupport.getDataTime(startTime);
        long finish = JobSupport.getDataTime(finishTime);
        this.jobScheduler.removeAllJobs(start2, finish);
    }

    @Override
    public void removeAllJobsAtScheduledTime(String time) throws Exception {
        long removeAtTime = JobSupport.getDataTime(time);
        this.jobScheduler.remove(removeAtTime);
    }

    @Override
    public void removeJob(String jobId) throws Exception {
        this.jobScheduler.remove(jobId);
    }

    @Override
    public int getExecutionCount(String jobId) throws Exception {
        int result = 0;
        List<Job> jobs = this.jobScheduler.getAllJobs();
        for (Job job : jobs) {
            if (!job.getJobId().equals(jobId)) continue;
            result = job.getExecutionCount();
        }
        return result;
    }
}

