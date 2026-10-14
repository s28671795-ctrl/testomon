/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.usage.Usage;

public class JobSchedulerUsage
extends Usage<JobSchedulerUsage> {
    private JobSchedulerStore store;

    public JobSchedulerUsage() {
        super(null, null, 1.0f);
    }

    public JobSchedulerUsage(String name, JobSchedulerStore store) {
        super(null, name, 1.0f);
        this.store = store;
    }

    public JobSchedulerUsage(JobSchedulerUsage parent, String name) {
        super(parent, name, 1.0f);
        this.store = parent.store;
    }

    @Override
    protected long retrieveUsage() {
        if (this.store == null) {
            return 0L;
        }
        return this.store.size();
    }

    public JobSchedulerStore getStore() {
        return this.store;
    }

    public void setStore(JobSchedulerStore store) {
        this.store = store;
        this.onLimitChange();
    }
}

