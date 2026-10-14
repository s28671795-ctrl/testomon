/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import org.apache.activemq.memory.CacheEntry;

public interface CacheEvictor {
    public CacheEntry evictCacheEntry();
}

