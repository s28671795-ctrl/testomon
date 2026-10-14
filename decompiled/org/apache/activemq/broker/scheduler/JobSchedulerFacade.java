/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.util.Collections;
import java.util.List;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobListener;
import org.apache.activemq.broker.scheduler.JobScheduler;
import org.apache.activemq.broker.scheduler.SchedulerBroker;
import org.apache.activemq.util.ByteSequence;

public class JobSchedulerFacade
implements JobScheduler {
    private final SchedulerBroker broker;

    JobSchedulerFacade(SchedulerBroker broker) {
        this.broker = broker;
    }

    @Override
    public void addListener(JobListener l) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.addListener(l);
        }
    }

    @Override
    public List<Job> getAllJobs() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            return js.getAllJobs();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Job> getAllJobs(long start2, long finish) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            return js.getAllJobs(start2, finish);
        }
        return Collections.emptyList();
    }

    @Override
    public String getName() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            return js.getName();
        }
        return "";
    }

    @Override
    public List<Job> getNextScheduleJobs() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            return js.getNextScheduleJobs();
        }
        return Collections.emptyList();
    }

    @Override
    public long getNextScheduleTime() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            return js.getNextScheduleTime();
        }
        return 0L;
    }

    @Override
    public void remove(long time) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.remove(time);
        }
    }

    @Override
    public void remove(String jobId) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.remove(jobId);
        }
    }

    @Override
    public void removeAllJobs() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.removeAllJobs();
        }
    }

    @Override
    public void removeAllJobs(long start2, long finish) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.removeAllJobs(start2, finish);
        }
    }

    @Override
    public void removeListener(JobListener l) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.removeListener(l);
        }
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, long delay) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.schedule(jobId, payload, delay);
        }
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, String cronEntry, long start2, long period, int repeat) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.schedule(jobId, payload, cronEntry, start2, period, repeat);
        }
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, String cronEntry) throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.schedule(jobId, payload, cronEntry);
        }
    }

    @Override
    public void startDispatching() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.startDispatching();
        }
    }

    @Override
    public void stopDispatching() throws Exception {
        JobScheduler js = this.broker.getInternalScheduler();
        if (js != null) {
            js.stopDispatching();
        }
    }
}

