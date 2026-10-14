/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.io.File;
import org.apache.activemq.Service;
import org.apache.activemq.broker.scheduler.JobScheduler;

public interface JobSchedulerStore
extends Service {
    public File getDirectory();

    public void setDirectory(File var1);

    public long size();

    public JobScheduler getJobScheduler(String var1) throws Exception;

    public boolean removeJobScheduler(String var1) throws Exception;
}

