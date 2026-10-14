/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.api;

import javax.management.NotificationEmitter;
import javax.management.openmbean.TabularData;

public interface HibernateStats
extends NotificationEmitter {
    public static final String CACHE_ENABLED = "CacheEnabled";
    public static final String CACHE_REGION_CHANGED = "CacheRegionChanged";
    public static final String CACHE_FLUSHED = "CacheFlushed";
    public static final String CACHE_REGION_FLUSHED = "CacheRegionFlushed";
    public static final String CACHE_STATISTICS_ENABLED = "CacheStatisticsEnabled";
    public static final String CACHE_STATISTICS_RESET = "CacheStatisticsReset";

    public boolean isStatisticsEnabled();

    public void setStatisticsEnabled(boolean var1);

    public void enableStats();

    public void disableStats();

    public void clearStats();

    public long getQueryExecutionCount();

    public long getQueryExecutionSample();

    public double getQueryExecutionRate();

    public long getCloseStatementCount();

    public long getConnectCount();

    public long getFlushCount();

    public long getOptimisticFailureCount();

    public long getPrepareStatementCount();

    public long getSessionCloseCount();

    public long getSessionOpenCount();

    public long getSuccessfulTransactionCount();

    public long getTransactionCount();

    public TabularData getEntityStats();

    public TabularData getCollectionStats();

    public TabularData getQueryStats();

    public TabularData getCacheRegionStats();
}

