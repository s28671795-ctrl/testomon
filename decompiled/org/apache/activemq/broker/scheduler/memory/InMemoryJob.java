/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler.memory;

import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobSupport;

public class InMemoryJob
implements Job {
    private final String jobId;
    private int repeat;
    private long start;
    private long nextTime;
    private long delay;
    private long period;
    private String cronEntry;
    private int executionCount;
    private byte[] payload;

    public InMemoryJob(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String getJobId() {
        return this.jobId;
    }

    @Override
    public int getRepeat() {
        return this.repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @Override
    public long getStart() {
        return this.start;
    }

    public void setStart(long start2) {
        this.start = start2;
    }

    public long getNextTime() {
        return this.nextTime;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public long getPeriod() {
        return this.period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    @Override
    public String getCronEntry() {
        return this.cronEntry;
    }

    public void setCronEntry(String cronEntry) {
        this.cronEntry = cronEntry;
    }

    @Override
    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String getStartTime() {
        return JobSupport.getDateTime(this.getStart());
    }

    @Override
    public String getNextExecutionTime() {
        return JobSupport.getDateTime(this.getNextTime());
    }

    @Override
    public int getExecutionCount() {
        return this.executionCount;
    }

    public void incrementExecutionCount() {
        ++this.executionCount;
    }

    public void decrementRepeatCount() {
        if (this.repeat > 0) {
            --this.repeat;
        }
    }

    public boolean isCron() {
        return this.getCronEntry() != null && this.getCronEntry().length() > 0;
    }

    public int hashCode() {
        return this.jobId.hashCode();
    }

    public String toString() {
        return "Job: " + this.getJobId();
    }
}

