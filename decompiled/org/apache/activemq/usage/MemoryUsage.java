/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import java.util.concurrent.TimeUnit;
import org.apache.activemq.usage.Usage;

public class MemoryUsage
extends Usage<MemoryUsage> {
    private long usage;

    public MemoryUsage() {
        this(null, null);
    }

    public MemoryUsage(MemoryUsage parent) {
        this(parent, "default");
    }

    public MemoryUsage(String name) {
        this(null, name);
    }

    public MemoryUsage(MemoryUsage parent, String name) {
        this(parent, name, 1.0f);
    }

    public MemoryUsage(MemoryUsage parent, String name, float portion) {
        super(parent, name, portion);
    }

    @Override
    public void waitForSpace() throws InterruptedException {
        if (this.parent != null) {
            ((MemoryUsage)this.parent).waitForSpace();
        }
        this.usageLock.readLock().lock();
        try {
            if (this.percentUsage >= 100 && this.isStarted()) {
                this.usageLock.readLock().unlock();
                this.usageLock.writeLock().lock();
                try {
                    while (this.percentUsage >= 100 && this.isStarted()) {
                        this.waitForSpaceCondition.await();
                    }
                }
                finally {
                    this.usageLock.writeLock().unlock();
                    this.usageLock.readLock().lock();
                }
            }
            if (this.percentUsage >= 100 && !this.isStarted()) {
                throw new InterruptedException("waitForSpace stopped during wait.");
            }
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean waitForSpace(long timeout2) throws InterruptedException {
        if (this.parent != null && !((MemoryUsage)this.parent).waitForSpace(timeout2)) {
            return false;
        }
        this.usageLock.readLock().lock();
        try {
            if (this.percentUsage >= 100) {
                this.usageLock.readLock().unlock();
                this.usageLock.writeLock().lock();
                try {
                    long deadline;
                    long timeleft = deadline = timeout2 > 0L ? System.currentTimeMillis() + timeout2 : Long.MAX_VALUE;
                    while (this.percentUsage >= 100 && timeleft > 0L) {
                        this.waitForSpaceCondition.await(Math.min((long)this.getPollingTime(), timeleft), TimeUnit.MILLISECONDS);
                        timeleft = deadline - System.currentTimeMillis();
                    }
                }
                finally {
                    this.usageLock.writeLock().unlock();
                    this.usageLock.readLock().lock();
                }
            }
            boolean bl = this.percentUsage < 100;
            return bl;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    @Override
    public boolean isFull() {
        if (this.parent != null && ((MemoryUsage)this.parent).isFull()) {
            return true;
        }
        this.usageLock.readLock().lock();
        try {
            boolean bl = this.percentUsage >= 100;
            return bl;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public void enqueueUsage(long value) throws InterruptedException {
        this.waitForSpace();
        this.increaseUsage(value);
    }

    public void increaseUsage(long value) {
        if (value == 0L) {
            return;
        }
        this.usageLock.writeLock().lock();
        try {
            this.usage += value;
            this.setPercentUsage(this.caclPercentUsage());
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
        if (this.parent != null) {
            ((MemoryUsage)this.parent).increaseUsage(value);
        }
    }

    public void decreaseUsage(long value) {
        if (value == 0L) {
            return;
        }
        this.usageLock.writeLock().lock();
        try {
            this.usage -= value;
            this.setPercentUsage(this.caclPercentUsage());
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
        if (this.parent != null) {
            ((MemoryUsage)this.parent).decreaseUsage(value);
        }
    }

    @Override
    protected long retrieveUsage() {
        return this.usage;
    }

    @Override
    public long getUsage() {
        return this.usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }

    public void setPercentOfJvmHeap(int percentOfJvmHeap) {
        if (percentOfJvmHeap > 0) {
            this.setLimit(Math.round((double)(Runtime.getRuntime().maxMemory() * (long)percentOfJvmHeap) / 100.0));
        }
    }
}

