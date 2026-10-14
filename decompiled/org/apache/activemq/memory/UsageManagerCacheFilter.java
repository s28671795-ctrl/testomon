/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.memory.Cache;
import org.apache.activemq.memory.CacheFilter;
import org.apache.activemq.usage.MemoryUsage;

public class UsageManagerCacheFilter
extends CacheFilter {
    private final AtomicLong totalUsage = new AtomicLong(0L);
    private final MemoryUsage usage;

    public UsageManagerCacheFilter(Cache next, MemoryUsage um) {
        super(next);
        this.usage = um;
    }

    @Override
    public Object put(Object key, Object value) {
        long usageValue = this.getUsageOfAddedObject(value);
        Object rc = super.put(key, value);
        if (rc != null) {
            usageValue -= this.getUsageOfRemovedObject(rc);
        }
        this.totalUsage.addAndGet(usageValue);
        this.usage.increaseUsage(usageValue);
        return rc;
    }

    @Override
    public Object remove(Object key) {
        Object rc = super.remove(key);
        if (rc != null) {
            long usageValue = this.getUsageOfRemovedObject(rc);
            this.totalUsage.addAndGet(-usageValue);
            this.usage.decreaseUsage(usageValue);
        }
        return rc;
    }

    protected long getUsageOfAddedObject(Object value) {
        return 1L;
    }

    protected long getUsageOfRemovedObject(Object value) {
        return 1L;
    }

    @Override
    public void close() {
        this.usage.decreaseUsage(this.totalUsage.get());
    }
}

