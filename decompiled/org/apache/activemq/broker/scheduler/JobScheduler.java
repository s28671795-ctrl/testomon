/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.util.List;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobListener;
import org.apache.activemq.util.ByteSequence;

public interface JobScheduler {
    public String getName() throws Exception;

    public void startDispatching() throws Exception;

    public void stopDispatching() throws Exception;

    public void addListener(JobListener var1) throws Exception;

    public void removeListener(JobListener var1) throws Exception;

    public void schedule(String var1, ByteSequence var2, long var3) throws Exception;

    public void schedule(String var1, ByteSequence var2, String var3) throws Exception;

    public void schedule(String var1, ByteSequence var2, String var3, long var4, long var6, int var8) throws Exception;

    public void remove(long var1) throws Exception;

    public void remove(String var1) throws Exception;

    public void removeAllJobs() throws Exception;

    public void removeAllJobs(long var1, long var3) throws Exception;

    public long getNextScheduleTime() throws Exception;

    public List<Job> getNextScheduleJobs() throws Exception;

    public List<Job> getAllJobs() throws Exception;

    public List<Job> getAllJobs(long var1, long var3) throws Exception;
}

