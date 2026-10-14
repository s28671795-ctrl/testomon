/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.management.MBeanNotificationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.hibernate.management.api.EhcacheStats;
import net.sf.ehcache.hibernate.management.impl.BaseEmitterBean;
import net.sf.ehcache.management.sampled.SampledCacheManager;

public class EhcacheStatsImpl
extends BaseEmitterBean
implements EhcacheStats {
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final MBeanNotificationInfo NOTIFICATION_INFO;
    private final SampledCacheManager sampledCacheManager;
    private final CacheManager cacheManager;
    private long statsSince = System.currentTimeMillis();

    public EhcacheStatsImpl(CacheManager manager) throws NotCompliantMBeanException {
        super(EhcacheStats.class);
        this.sampledCacheManager = new SampledCacheManager(manager);
        this.cacheManager = manager;
    }

    @Override
    public void flushRegionCache(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.flush();
        }
    }

    @Override
    public void flushRegionCaches() {
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            cache.flush();
        }
    }

    @Override
    public String generateActiveConfigDeclaration() {
        return this.cacheManager.getActiveConfigurationText();
    }

    @Override
    public String generateActiveConfigDeclaration(String region) {
        return this.cacheManager.getActiveConfigurationText(region);
    }

    @Override
    public long getCacheHitCount() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cacheHitCount();
        }
        return count;
    }

    @Override
    public double getCacheHitRate() {
        long now = System.currentTimeMillis();
        double deltaSecs = (double)(now - this.statsSince) / 1000.0;
        return (double)this.getCacheHitCount() / deltaSecs;
    }

    @Override
    public long getCacheHitSample() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cacheHitOperation().rate().value().longValue();
        }
        return count;
    }

    @Override
    public long getCacheMissCount() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cacheMissCount();
        }
        return count;
    }

    @Override
    public double getCacheMissRate() {
        long now = System.currentTimeMillis();
        double deltaSecs = (double)(now - this.statsSince) / 1000.0;
        return (double)this.getCacheMissCount() / deltaSecs;
    }

    @Override
    public long getCacheMissSample() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cacheMissOperation().rate().value().longValue();
        }
        return count;
    }

    @Override
    public long getCachePutCount() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cachePutCount();
        }
        return count;
    }

    @Override
    public double getCachePutRate() {
        long now = System.currentTimeMillis();
        double deltaSecs = (double)(now - this.statsSince) / 1000.0;
        return (double)this.getCachePutCount() / deltaSecs;
    }

    @Override
    public long getCachePutSample() {
        long count = 0L;
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            count += cache.getStatistics().cachePutOperation().rate().value().longValue();
        }
        return count;
    }

    @Override
    public String getOriginalConfigDeclaration() {
        return this.cacheManager.getOriginalConfigurationText();
    }

    @Override
    public String getOriginalConfigDeclaration(String region) {
        return this.cacheManager.getOriginalConfigurationText(region);
    }

    @Override
    public Map<String, Map<String, Object>> getRegionCacheAttributes() {
        HashMap<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
        for (String regionName : this.cacheManager.getCacheNames()) {
            result.put(regionName, this.getRegionCacheAttributes(regionName));
        }
        return result;
    }

    @Override
    public Map<String, Object> getRegionCacheAttributes(String regionName) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("Enabled", this.isRegionCacheEnabled(regionName));
        result.put("LoggingEnabled", this.isRegionCacheLoggingEnabled(regionName));
        result.put("MaxTTISeconds", this.getRegionCacheMaxTTISeconds(regionName));
        result.put("MaxTTLSeconds", this.getRegionCacheMaxTTLSeconds(regionName));
        result.put("TargetMaxInMemoryCount", this.getRegionCacheTargetMaxInMemoryCount(regionName));
        result.put("TargetMaxTotalCount", this.getRegionCacheTargetMaxTotalCount(regionName));
        result.put("OrphanEvictionEnabled", this.isRegionCacheOrphanEvictionEnabled(regionName));
        result.put("OrphanEvictionPeriod", this.getRegionCacheOrphanEvictionPeriod(regionName));
        return result;
    }

    @Override
    public int getRegionCacheMaxTTISeconds(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return (int)cache.getCacheConfiguration().getTimeToIdleSeconds();
        }
        return -1;
    }

    @Override
    public int getRegionCacheMaxTTLSeconds(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return (int)cache.getCacheConfiguration().getTimeToLiveSeconds();
        }
        return -1;
    }

    @Override
    public int getRegionCacheOrphanEvictionPeriod(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null && cache.isTerracottaClustered()) {
            return cache.getCacheConfiguration().getTerracottaConfiguration().getOrphanEvictionPeriod();
        }
        return -1;
    }

    @Override
    public Map<String, int[]> getRegionCacheSamples() {
        HashMap<String, int[]> rv = new HashMap<String, int[]>();
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            rv.put(name, new int[]{cache.getStatistics().cacheHitOperation().rate().value().intValue(), cache.getStatistics().cacheMissNotFoundOperation().rate().value().intValue(), cache.getStatistics().cacheMissExpiredOperation().rate().value().intValue(), cache.getStatistics().cachePutOperation().rate().value().intValue()});
        }
        return rv;
    }

    @Override
    public int getRegionCacheTargetMaxInMemoryCount(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return cache.getCacheConfiguration().getMaxElementsInMemory();
        }
        return -1;
    }

    @Override
    public int getRegionCacheTargetMaxTotalCount(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return cache.getCacheConfiguration().getMaxElementsOnDisk();
        }
        return -1;
    }

    @Override
    public String[] getTerracottaHibernateCacheRegionNames() {
        ArrayList<String> rv = new ArrayList<String>();
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null || !cache.getCacheConfiguration().isTerracottaClustered()) continue;
            rv.add(name);
        }
        return rv.toArray(new String[0]);
    }

    @Override
    public boolean isRegionCacheEnabled(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return !cache.isDisabled();
        }
        return false;
    }

    @Override
    public void setRegionCacheEnabled(String region, boolean enabled) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.setDisabled(!enabled);
        }
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public boolean isRegionCachesEnabled() {
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null || !cache.isDisabled()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void setRegionCachesEnabled(boolean flag) {
        for (String name : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(name);
            if (cache == null) continue;
            cache.setDisabled(!flag);
        }
        this.sendNotification("CacheEnabled", flag);
    }

    @Override
    public boolean isRegionCacheLoggingEnabled(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return cache.getCacheConfiguration().getLogging();
        }
        return false;
    }

    @Override
    public boolean isRegionCacheOrphanEvictionEnabled(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null && cache.isTerracottaClustered()) {
            return cache.getCacheConfiguration().getTerracottaConfiguration().getOrphanEviction();
        }
        return false;
    }

    @Override
    public boolean isTerracottaHibernateCache(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return cache.getCacheConfiguration().isTerracottaClustered();
        }
        return false;
    }

    @Override
    public void setRegionCacheLoggingEnabled(String region, boolean loggingEnabled) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.getCacheConfiguration().setLogging(loggingEnabled);
            this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
        }
    }

    @Override
    public void setRegionCacheMaxTTISeconds(String region, int maxTTISeconds) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.getCacheConfiguration().setTimeToIdleSeconds(maxTTISeconds);
            this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
        }
    }

    @Override
    public void setRegionCacheMaxTTLSeconds(String region, int maxTTLSeconds) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.getCacheConfiguration().setTimeToLiveSeconds(maxTTLSeconds);
            this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
        }
    }

    @Override
    public void setRegionCacheTargetMaxInMemoryCount(String region, int targetMaxInMemoryCount) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.getCacheConfiguration().setMaxElementsInMemory(targetMaxInMemoryCount);
            this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
        }
    }

    @Override
    public void setRegionCacheTargetMaxTotalCount(String region, int targetMaxTotalCount) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            cache.getCacheConfiguration().setMaxElementsOnDisk(targetMaxTotalCount);
            this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
        }
    }

    @Override
    public int getNumberOfElementsInMemory(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return (int)cache.getStatistics().getLocalHeapSize();
        }
        return -1;
    }

    @Override
    public int getNumberOfElementsOffHeap(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return (int)cache.getStatistics().getLocalOffHeapSize();
        }
        return -1;
    }

    @Override
    public int getNumberOfElementsOnDisk(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return (int)cache.getStatistics().getLocalDiskSize();
        }
        return -1;
    }

    @Override
    public long getMaxGetTimeMillis() {
        long rv = 0L;
        for (String cacheName : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(cacheName);
            if (cache == null) continue;
            rv = Math.max(rv, TimeUnit.MILLISECONDS.convert(cache.getStatistics().cacheSearchOperation().latency().maximum().value(), TimeUnit.NANOSECONDS));
        }
        return rv;
    }

    @Override
    public long getMinGetTimeMillis() {
        long rv = 0L;
        for (String cacheName : this.cacheManager.getCacheNames()) {
            Cache cache = this.cacheManager.getCache(cacheName);
            if (cache == null) continue;
            rv = Math.max(rv, TimeUnit.MILLISECONDS.convert(cache.getStatistics().cacheSearchOperation().latency().minimum().value(), TimeUnit.NANOSECONDS));
        }
        return rv;
    }

    @Override
    public long getMaxGetTimeMillis(String cacheName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache != null) {
            return TimeUnit.MILLISECONDS.convert(cache.getStatistics().cacheGetOperation().latency().maximum().value(), TimeUnit.NANOSECONDS);
        }
        return 0L;
    }

    @Override
    public long getMinGetTimeMillis(String cacheName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache != null) {
            return TimeUnit.MILLISECONDS.convert(cache.getStatistics().cacheGetOperation().latency().minimum().value(), TimeUnit.NANOSECONDS);
        }
        return 0L;
    }

    @Override
    public float getAverageGetTimeMillis(String region) {
        Cache cache = this.cacheManager.getCache(region);
        if (cache != null) {
            return TimeUnit.MILLISECONDS.convert(cache.getStatistics().cacheGetOperation().latency().average().value().longValue(), TimeUnit.NANOSECONDS);
        }
        return -1.0f;
    }

    @Override
    protected void doDispose() {
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{NOTIFICATION_INFO};
    }

    static {
        String[] notifTypes = new String[]{"CacheEnabled", "CacheRegionChanged", "CacheFlushed", "CacheRegionFlushed", "CacheStatisticsEnabled", "CacheStatisticsReset"};
        String name = Notification.class.getName();
        String description = "Ehcache Hibernate Statistics Event";
        NOTIFICATION_INFO = new MBeanNotificationInfo(notifTypes, name, "Ehcache Hibernate Statistics Event");
    }
}

