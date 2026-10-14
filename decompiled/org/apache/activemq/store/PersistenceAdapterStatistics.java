/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.management.TimeStatisticImpl;

public class PersistenceAdapterStatistics
extends StatsImpl {
    protected TimeStatisticImpl writeTime = new TimeStatisticImpl("writeTime", "Time to write data to the PersistentAdapter.");
    protected TimeStatisticImpl readTime = new TimeStatisticImpl("readTime", "Time to read data from the PersistentAdapter.");

    public PersistenceAdapterStatistics() {
        this.addStatistic("writeTime", this.writeTime);
        this.addStatistic("readTime", this.readTime);
    }

    public void addWriteTime(long time) {
        this.writeTime.addTime(time);
    }

    public void addReadTime(long time) {
        this.readTime.addTime(time);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.writeTime.setEnabled(enabled);
        this.readTime.setEnabled(enabled);
    }

    public TimeStatisticImpl getWriteTime() {
        return this.writeTime;
    }

    public TimeStatisticImpl getReadTime() {
        return this.readTime;
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            this.writeTime.reset();
            this.readTime.reset();
        }
    }

    public void setParent(PersistenceAdapterStatistics parent) {
        if (parent != null) {
            this.writeTime.setParent(parent.writeTime);
            this.readTime.setParent(parent.readTime);
        } else {
            this.writeTime.setParent(null);
            this.readTime.setParent(null);
        }
    }
}

