/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import net.sf.ehcache.hibernate.management.impl.CacheRegionStats;

public class AggregateCacheRegionStats
extends CacheRegionStats {
    private int nodeCount;

    public AggregateCacheRegionStats(String region) {
        super(region);
    }

    public void aggregate(CacheRegionStats stats) {
        ++this.nodeCount;
        this.hitCount += stats.getHitCount();
        this.missCount += stats.getMissCount();
        this.putCount += stats.getPutCount();
        this.hitRatio = this.determineHitRatio();
        this.elementCountInMemory += stats.getElementCountInMemory();
        if (stats.getElementCountOnDisk() > this.elementCountOnDisk) {
            this.elementCountOnDisk = stats.getElementCountOnDisk();
        }
        this.elementCountTotal = stats.getElementCountTotal();
    }

    @Override
    public long getElementCountInMemory() {
        return this.elementCountInMemory / (long)this.nodeCount;
    }
}

