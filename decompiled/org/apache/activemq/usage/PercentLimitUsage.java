/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import java.io.File;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.util.StoreUtil;

public abstract class PercentLimitUsage<T extends Usage>
extends Usage<T> {
    protected int percentLimit = 0;
    protected long total = 0L;

    public PercentLimitUsage(T parent, String name, float portion) {
        super(parent, name, portion);
    }

    public void setPercentLimit(int percentLimit) {
        this.usageLock.writeLock().lock();
        try {
            this.percentLimit = percentLimit;
            this.updateLimitBasedOnPercent();
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    public int getPercentLimit() {
        this.usageLock.readLock().lock();
        try {
            int n = this.percentLimit;
            return n;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public void setTotal(long max) {
        this.total = max;
    }

    public long getTotal() {
        return this.total;
    }

    protected void percentLimitFromFile(File directory) {
        if (this.percentLimit > 0) {
            File dir;
            if (this.total > 0L) {
                this.setLimit(this.total * (long)this.percentLimit / 100L);
            } else if (directory != null && (dir = StoreUtil.findParentDirectory(directory)) != null) {
                this.setLimit(dir.getTotalSpace() * (long)this.percentLimit / 100L);
            }
        }
    }

    protected abstract void updateLimitBasedOnPercent();
}

