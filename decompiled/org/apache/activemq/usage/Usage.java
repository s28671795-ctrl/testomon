/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.activemq.Service;
import org.apache.activemq.usage.DefaultUsageCapacity;
import org.apache.activemq.usage.UsageCapacity;
import org.apache.activemq.usage.UsageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Usage<T extends Usage>
implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(Usage.class);
    protected final ReentrantReadWriteLock usageLock = new ReentrantReadWriteLock();
    protected final Condition waitForSpaceCondition = this.usageLock.writeLock().newCondition();
    protected int percentUsage;
    protected T parent;
    protected String name;
    private UsageCapacity limiter = new DefaultUsageCapacity();
    private int percentUsageMinDelta = 1;
    private final List<UsageListener> listeners = new CopyOnWriteArrayList<UsageListener>();
    private final boolean debug = LOG.isDebugEnabled();
    private float usagePortion = 1.0f;
    private final List<T> children = new CopyOnWriteArrayList<T>();
    private final List<Runnable> callbacks = new LinkedList<Runnable>();
    private int pollingTime = 100;
    private final AtomicBoolean started = new AtomicBoolean();
    private ThreadPoolExecutor executor;

    public Usage(T parent, String name, float portion) {
        this.parent = parent;
        this.usagePortion = portion;
        if (parent != null) {
            this.limiter.setLimit((long)((double)((Usage)parent).getLimit() * (double)portion));
            name = ((Usage)parent).name + ":" + name;
        }
        this.name = name;
    }

    protected abstract long retrieveUsage();

    public void waitForSpace() throws InterruptedException {
        this.waitForSpace(0L);
    }

    public boolean waitForSpace(long timeout2) throws InterruptedException {
        return this.waitForSpace(timeout2, 100);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean waitForSpace(long timeout2, int highWaterMark) throws InterruptedException {
        if (this.parent != null && !((Usage)this.parent).waitForSpace(timeout2, highWaterMark)) {
            return false;
        }
        this.usageLock.writeLock().lock();
        try {
            this.percentUsage = this.caclPercentUsage();
            if (this.percentUsage >= highWaterMark) {
                long deadline;
                long timeleft = deadline = timeout2 > 0L ? System.currentTimeMillis() + timeout2 : Long.MAX_VALUE;
                while (timeleft > 0L) {
                    this.percentUsage = this.caclPercentUsage();
                    if (this.percentUsage < highWaterMark) break;
                    this.waitForSpaceCondition.await(this.pollingTime, TimeUnit.MILLISECONDS);
                    timeleft = deadline - System.currentTimeMillis();
                }
            }
            boolean bl = this.percentUsage < highWaterMark;
            return bl;
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    public boolean isFull() {
        return this.isFull(100);
    }

    public boolean isFull(int highWaterMark) {
        if (this.parent != null && ((Usage)this.parent).isFull(highWaterMark)) {
            return true;
        }
        this.usageLock.writeLock().lock();
        try {
            this.percentUsage = this.caclPercentUsage();
            boolean bl = this.percentUsage >= highWaterMark;
            return bl;
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    public void addUsageListener(UsageListener listener) {
        this.listeners.add(listener);
    }

    public void removeUsageListener(UsageListener listener) {
        this.listeners.remove(listener);
    }

    public int getNumUsageListeners() {
        return this.listeners.size();
    }

    public long getLimit() {
        this.usageLock.readLock().lock();
        try {
            long l = this.limiter.getLimit();
            return l;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public void setLimit(long limit) {
        if (this.percentUsageMinDelta < 0) {
            throw new IllegalArgumentException("percentUsageMinDelta must be greater or equal to 0");
        }
        this.usageLock.writeLock().lock();
        try {
            this.limiter.setLimit(limit);
            this.usagePortion = 0.0f;
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
        this.onLimitChange();
    }

    protected void onLimitChange() {
        if (this.usagePortion > 0.0f && this.parent != null) {
            this.usageLock.writeLock().lock();
            try {
                this.limiter.setLimit((long)((double)((Usage)this.parent).getLimit() * (double)this.usagePortion));
            }
            finally {
                this.usageLock.writeLock().unlock();
            }
        }
        this.usageLock.writeLock().lock();
        try {
            this.setPercentUsage(this.caclPercentUsage());
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
        for (Usage child : this.children) {
            child.onLimitChange();
        }
    }

    public float getUsagePortion() {
        this.usageLock.readLock().lock();
        try {
            float f = this.usagePortion;
            return f;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public void setUsagePortion(float usagePortion) {
        this.usageLock.writeLock().lock();
        try {
            this.usagePortion = usagePortion;
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
        this.onLimitChange();
    }

    public int getPercentUsage() {
        this.usageLock.readLock().lock();
        try {
            int n = this.percentUsage;
            return n;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public int getPercentUsageMinDelta() {
        this.usageLock.readLock().lock();
        try {
            int n = this.percentUsageMinDelta;
            return n;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    public void setPercentUsageMinDelta(int percentUsageMinDelta) {
        if (percentUsageMinDelta < 1) {
            throw new IllegalArgumentException("percentUsageMinDelta must be greater than 0");
        }
        this.usageLock.writeLock().lock();
        try {
            this.percentUsageMinDelta = percentUsageMinDelta;
            this.setPercentUsage(this.caclPercentUsage());
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    public long getUsage() {
        this.usageLock.readLock().lock();
        try {
            long l = this.retrieveUsage();
            return l;
        }
        finally {
            this.usageLock.readLock().unlock();
        }
    }

    protected void setPercentUsage(int value) {
        this.usageLock.writeLock().lock();
        try {
            int oldValue = this.percentUsage;
            this.percentUsage = value;
            if (oldValue != value) {
                this.fireEvent(oldValue, value);
            }
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    protected int caclPercentUsage() {
        if (this.limiter.getLimit() == 0L) {
            return 0;
        }
        return (int)(this.retrieveUsage() * 100L / this.limiter.getLimit() / (long)this.percentUsageMinDelta * (long)this.percentUsageMinDelta);
    }

    private void fireEvent(final int oldPercentUsage, final int newPercentUsage) {
        if (this.debug) {
            LOG.debug(this.getName() + ": usage change from: " + oldPercentUsage + "% of available memory, to: " + newPercentUsage + "% of available memory");
        }
        if (this.started.get()) {
            if (oldPercentUsage >= 100 && newPercentUsage < 100) {
                this.waitForSpaceCondition.signalAll();
                if (!this.callbacks.isEmpty()) {
                    for (Runnable callback : this.callbacks) {
                        this.getExecutor().execute(callback);
                    }
                    this.callbacks.clear();
                }
            }
            if (!this.listeners.isEmpty()) {
                Runnable listenerNotifier = new Runnable(){

                    @Override
                    public void run() {
                        for (UsageListener listener : Usage.this.listeners) {
                            listener.onUsageChanged(Usage.this, oldPercentUsage, newPercentUsage);
                        }
                    }
                };
                if (this.started.get()) {
                    this.getExecutor().execute(listenerNotifier);
                } else {
                    LOG.warn("Not notifying memory usage change to listeners on shutdown");
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "Usage(" + this.getName() + ") percentUsage=" + this.percentUsage + "%, usage=" + this.retrieveUsage() + ", limit=" + this.limiter.getLimit() + ", percentUsageMinDelta=" + this.percentUsageMinDelta + "%" + (this.parent != null ? ";Parent:" + ((Usage)this.parent).toString() : "");
    }

    @Override
    public void start() {
        if (this.started.compareAndSet(false, true)) {
            if (this.parent != null) {
                this.parent.addChild((Usage)this);
                if (this.getLimit() > ((Usage)this.parent).getLimit()) {
                    LOG.info("Usage({}) limit={} should be smaller than its parent limit={}", this.getName(), this.getLimit(), ((Usage)this.parent).getLimit());
                }
            }
            for (Usage t2 : this.children) {
                t2.start();
            }
        }
    }

    @Override
    public void stop() {
        if (this.started.compareAndSet(true, false)) {
            if (this.parent != null) {
                this.parent.removeChild((Usage)this);
            }
            this.usageLock.writeLock().lock();
            try {
                this.waitForSpaceCondition.signalAll();
                for (Runnable callback : this.callbacks) {
                    callback.run();
                }
                this.callbacks.clear();
            }
            finally {
                this.usageLock.writeLock().unlock();
            }
            for (Usage t2 : this.children) {
                t2.stop();
            }
        }
    }

    protected void addChild(T child) {
        this.children.add(child);
        if (this.started.get()) {
            ((Usage)child).start();
        }
    }

    protected void removeChild(T child) {
        this.children.remove(child);
    }

    public boolean notifyCallbackWhenNotFull(final Runnable callback) {
        Runnable r;
        if (this.parent != null && ((Usage)this.parent).notifyCallbackWhenNotFull(r = new Runnable(){

            @Override
            public void run() {
                Usage.this.usageLock.writeLock().lock();
                try {
                    if (Usage.this.percentUsage >= 100) {
                        Usage.this.callbacks.add(callback);
                    } else {
                        callback.run();
                    }
                }
                finally {
                    Usage.this.usageLock.writeLock().unlock();
                }
            }
        })) {
            return true;
        }
        this.usageLock.writeLock().lock();
        try {
            if (this.percentUsage >= 100) {
                this.callbacks.add(callback);
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }

    public UsageCapacity getLimiter() {
        return this.limiter;
    }

    public void setLimiter(UsageCapacity limiter) {
        this.limiter = limiter;
    }

    public int getPollingTime() {
        return this.pollingTime;
    }

    public void setPollingTime(int pollingTime) {
        this.pollingTime = pollingTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getParent() {
        return this.parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public void setExecutor(ThreadPoolExecutor executor2) {
        this.executor = executor2;
    }

    public ThreadPoolExecutor getExecutor() {
        return this.executor;
    }

    public boolean isStarted() {
        return this.started.get();
    }
}

