/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.api;

import java.util.Map;
import javax.management.NotificationEmitter;

public interface EhcacheStats
extends NotificationEmitter {
    public static final String CACHE_ENABLED = "CacheEnabled";
    public static final String CACHE_REGION_CHANGED = "CacheRegionChanged";
    public static final String CACHE_FLUSHED = "CacheFlushed";
    public static final String CACHE_REGION_FLUSHED = "CacheRegionFlushed";
    public static final String CACHE_STATISTICS_ENABLED = "CacheStatisticsEnabled";
    public static final String CACHE_STATISTICS_RESET = "CacheStatisticsReset";

    public String getOriginalConfigDeclaration();

    public String getOriginalConfigDeclaration(String var1);

    public String generateActiveConfigDeclaration();

    public String generateActiveConfigDeclaration(String var1);

    public boolean isTerracottaHibernateCache(String var1);

    public String[] getTerracottaHibernateCacheRegionNames();

    public Map<String, Object> getRegionCacheAttributes(String var1);

    public Map<String, Map<String, Object>> getRegionCacheAttributes();

    public boolean isRegionCacheEnabled(String var1);

    public void setRegionCacheEnabled(String var1, boolean var2);

    public boolean isRegionCachesEnabled();

    public void setRegionCachesEnabled(boolean var1);

    public int getRegionCacheMaxTTISeconds(String var1);

    public void setRegionCacheMaxTTISeconds(String var1, int var2);

    public int getRegionCacheMaxTTLSeconds(String var1);

    public void setRegionCacheMaxTTLSeconds(String var1, int var2);

    public int getRegionCacheTargetMaxInMemoryCount(String var1);

    public void setRegionCacheTargetMaxInMemoryCount(String var1, int var2);

    public int getRegionCacheTargetMaxTotalCount(String var1);

    public void setRegionCacheTargetMaxTotalCount(String var1, int var2);

    public boolean isRegionCacheLoggingEnabled(String var1);

    public void setRegionCacheLoggingEnabled(String var1, boolean var2);

    public boolean isRegionCacheOrphanEvictionEnabled(String var1);

    public int getRegionCacheOrphanEvictionPeriod(String var1);

    public void flushRegionCache(String var1);

    public void flushRegionCaches();

    public long getCacheHitCount();

    public long getCacheHitSample();

    public double getCacheHitRate();

    public long getCacheMissCount();

    public long getCacheMissSample();

    public double getCacheMissRate();

    public long getCachePutSample();

    public long getCachePutCount();

    public double getCachePutRate();

    public Map<String, int[]> getRegionCacheSamples();

    public int getNumberOfElementsInMemory(String var1);

    public int getNumberOfElementsOffHeap(String var1);

    public int getNumberOfElementsOnDisk(String var1);

    public long getMinGetTimeMillis();

    public long getMaxGetTimeMillis();

    public float getAverageGetTimeMillis(String var1);

    public long getMinGetTimeMillis(String var1);

    public long getMaxGetTimeMillis(String var1);
}

