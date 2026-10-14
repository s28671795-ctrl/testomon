/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler.memory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.activemq.broker.scheduler.JobScheduler;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.broker.scheduler.memory.InMemoryJobScheduler;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryJobSchedulerStore
extends ServiceSupport
implements JobSchedulerStore {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryJobSchedulerStore.class);
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, InMemoryJobScheduler> schedulers = new HashMap<String, InMemoryJobScheduler>();

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        for (InMemoryJobScheduler scheduler : this.schedulers.values()) {
            try {
                scheduler.stop();
            }
            catch (Exception e) {
                LOG.error("Failed to stop scheduler: {}", (Object)scheduler.getName(), (Object)e);
            }
        }
    }

    @Override
    protected void doStart() throws Exception {
        for (InMemoryJobScheduler scheduler : this.schedulers.values()) {
            try {
                scheduler.start();
            }
            catch (Exception e) {
                LOG.error("Failed to start scheduler: {}", (Object)scheduler.getName(), (Object)e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobScheduler getJobScheduler(String name) throws Exception {
        this.lock.lock();
        try {
            InMemoryJobScheduler result = this.schedulers.get(name);
            if (result == null) {
                LOG.debug("Creating new in-memory scheduler: {}", (Object)name);
                result = new InMemoryJobScheduler(name);
                this.schedulers.put(name, result);
                if (this.isStarted()) {
                    result.start();
                }
            }
            InMemoryJobScheduler inMemoryJobScheduler = result;
            return inMemoryJobScheduler;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJobScheduler(String name) throws Exception {
        boolean result = false;
        this.lock.lock();
        try {
            InMemoryJobScheduler scheduler = this.schedulers.remove(name);
            boolean bl = result = scheduler != null;
            if (result) {
                LOG.debug("Removing in-memory Job Scheduler: {}", (Object)name);
                scheduler.stop();
                this.schedulers.remove(name);
            }
        }
        finally {
            this.lock.unlock();
        }
        return result;
    }

    @Override
    public long size() {
        return 0L;
    }

    @Override
    public File getDirectory() {
        return null;
    }

    @Override
    public void setDirectory(File directory) {
    }
}

