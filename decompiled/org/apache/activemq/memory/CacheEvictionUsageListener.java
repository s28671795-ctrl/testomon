/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.memory.CacheEvictor;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.usage.UsageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEvictionUsageListener
implements UsageListener {
    private static final Logger LOG = LoggerFactory.getLogger(CacheEvictionUsageListener.class);
    private final List<CacheEvictor> evictors = new CopyOnWriteArrayList<CacheEvictor>();
    private final int usageHighMark;
    private final int usageLowMark;
    private final TaskRunner evictionTask;
    private final Usage usage;

    public CacheEvictionUsageListener(Usage usage, int usageHighMark, int usageLowMark, TaskRunnerFactory taskRunnerFactory) {
        this.usage = usage;
        this.usageHighMark = usageHighMark;
        this.usageLowMark = usageLowMark;
        this.evictionTask = taskRunnerFactory.createTaskRunner(new Task(){

            @Override
            public boolean iterate() {
                return CacheEvictionUsageListener.this.evictMessages();
            }
        }, "Cache Evictor: " + System.identityHashCode(this));
    }

    boolean evictMessages() {
        LOG.debug("Evicting cache memory usage: {}", (Object)this.usage.getPercentUsage());
        LinkedList<CacheEvictor> list = new LinkedList<CacheEvictor>(this.evictors);
        while (list.size() > 0 && this.usage.getPercentUsage() > this.usageLowMark) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                CacheEvictor evictor = (CacheEvictor)iter.next();
                if (evictor.evictCacheEntry() != null) continue;
                iter.remove();
            }
        }
        return false;
    }

    @Override
    public void onUsageChanged(Usage usage, int oldPercentUsage, int newPercentUsage) {
        if (oldPercentUsage < newPercentUsage && usage.getPercentUsage() >= this.usageHighMark) {
            try {
                this.evictionTask.wakeup();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void add(CacheEvictor evictor) {
        this.evictors.add(evictor);
    }

    public void remove(CacheEvictor evictor) {
        this.evictors.remove(evictor);
    }
}

