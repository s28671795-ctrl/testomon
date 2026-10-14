/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.SessionFactory
 */
package net.sf.ehcache.hibernate.management.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.MBeanNotificationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.openmbean.TabularData;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.hibernate.management.api.EhcacheHibernateMBean;
import net.sf.ehcache.hibernate.management.api.EhcacheStats;
import net.sf.ehcache.hibernate.management.api.HibernateStats;
import net.sf.ehcache.hibernate.management.impl.BaseEmitterBean;
import net.sf.ehcache.hibernate.management.impl.EhcacheStatsImpl;
import net.sf.ehcache.hibernate.management.impl.HibernateStatsImpl;
import net.sf.ehcache.hibernate.management.impl.NullHibernateStats;
import org.hibernate.SessionFactory;

public class EhcacheHibernate
extends BaseEmitterBean
implements EhcacheHibernateMBean {
    private static final MBeanNotificationInfo NOTIFICATION_INFO;
    private final AtomicBoolean statsEnabled = new AtomicBoolean(true);
    private EhcacheStats ehcacheStats;
    private volatile HibernateStats hibernateStats = NullHibernateStats.INSTANCE;

    public EhcacheHibernate(CacheManager manager) throws NotCompliantMBeanException {
        super(EhcacheHibernateMBean.class);
        this.ehcacheStats = new EhcacheStatsImpl(manager);
    }

    public void enableHibernateStatistics(SessionFactory sessionFactory) {
        try {
            this.hibernateStats = new HibernateStatsImpl(sessionFactory);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isHibernateStatisticsSupported() {
        return this.hibernateStats instanceof HibernateStatsImpl;
    }

    @Override
    public void setStatisticsEnabled(boolean flag) {
        if (flag) {
            this.hibernateStats.enableStats();
        } else {
            this.hibernateStats.disableStats();
        }
        this.statsEnabled.set(flag);
        this.sendNotification("CacheStatisticsEnabled", flag);
    }

    @Override
    public boolean isStatisticsEnabled() {
        return this.statsEnabled.get();
    }

    @Override
    public void clearStats() {
        this.hibernateStats.clearStats();
    }

    @Override
    public void disableStats() {
        this.setStatisticsEnabled(false);
    }

    @Override
    public void enableStats() {
        this.setStatisticsEnabled(true);
    }

    @Override
    public void flushRegionCache(String region) {
        this.ehcacheStats.flushRegionCache(region);
        this.sendNotification("CacheRegionFlushed", region);
    }

    @Override
    public void flushRegionCaches() {
        this.ehcacheStats.flushRegionCaches();
        this.sendNotification("CacheFlushed");
    }

    @Override
    public String generateActiveConfigDeclaration() {
        return this.ehcacheStats.generateActiveConfigDeclaration();
    }

    @Override
    public String generateActiveConfigDeclaration(String region) {
        return this.ehcacheStats.generateActiveConfigDeclaration(region);
    }

    @Override
    public long getCacheHitCount() {
        return this.ehcacheStats.getCacheHitCount();
    }

    @Override
    public double getCacheHitRate() {
        return this.ehcacheStats.getCacheHitRate();
    }

    @Override
    public long getCacheHitSample() {
        return this.ehcacheStats.getCacheHitSample();
    }

    @Override
    public long getCacheMissCount() {
        return this.ehcacheStats.getCacheMissCount();
    }

    @Override
    public double getCacheMissRate() {
        return this.ehcacheStats.getCacheMissRate();
    }

    @Override
    public long getCacheMissSample() {
        return this.ehcacheStats.getCacheMissSample();
    }

    @Override
    public long getCachePutCount() {
        return this.ehcacheStats.getCachePutCount();
    }

    @Override
    public double getCachePutRate() {
        return this.ehcacheStats.getCachePutRate();
    }

    @Override
    public long getCachePutSample() {
        return this.ehcacheStats.getCachePutSample();
    }

    @Override
    public TabularData getCacheRegionStats() {
        return this.hibernateStats.getCacheRegionStats();
    }

    @Override
    public long getCloseStatementCount() {
        return this.hibernateStats.getCloseStatementCount();
    }

    @Override
    public TabularData getCollectionStats() {
        return this.hibernateStats.getCollectionStats();
    }

    @Override
    public long getConnectCount() {
        return this.hibernateStats.getConnectCount();
    }

    @Override
    public TabularData getEntityStats() {
        return this.hibernateStats.getEntityStats();
    }

    @Override
    public long getFlushCount() {
        return this.hibernateStats.getFlushCount();
    }

    @Override
    public long getOptimisticFailureCount() {
        return this.hibernateStats.getOptimisticFailureCount();
    }

    @Override
    public String getOriginalConfigDeclaration() {
        return this.ehcacheStats.getOriginalConfigDeclaration();
    }

    @Override
    public String getOriginalConfigDeclaration(String region) {
        return this.ehcacheStats.getOriginalConfigDeclaration(region);
    }

    @Override
    public long getPrepareStatementCount() {
        return this.hibernateStats.getPrepareStatementCount();
    }

    @Override
    public long getQueryExecutionCount() {
        return this.hibernateStats.getQueryExecutionCount();
    }

    @Override
    public double getQueryExecutionRate() {
        return this.hibernateStats.getQueryExecutionRate();
    }

    @Override
    public long getQueryExecutionSample() {
        return this.hibernateStats.getQueryExecutionSample();
    }

    @Override
    public TabularData getQueryStats() {
        return this.hibernateStats.getQueryStats();
    }

    @Override
    public Map<String, Map<String, Object>> getRegionCacheAttributes() {
        return this.ehcacheStats.getRegionCacheAttributes();
    }

    @Override
    public Map<String, Object> getRegionCacheAttributes(String regionName) {
        return this.ehcacheStats.getRegionCacheAttributes(regionName);
    }

    @Override
    public int getRegionCacheMaxTTISeconds(String region) {
        return this.ehcacheStats.getRegionCacheMaxTTISeconds(region);
    }

    @Override
    public int getRegionCacheMaxTTLSeconds(String region) {
        return this.ehcacheStats.getRegionCacheMaxTTLSeconds(region);
    }

    @Override
    public int getRegionCacheOrphanEvictionPeriod(String region) {
        return this.ehcacheStats.getRegionCacheOrphanEvictionPeriod(region);
    }

    @Override
    public Map<String, int[]> getRegionCacheSamples() {
        return this.ehcacheStats.getRegionCacheSamples();
    }

    @Override
    public int getRegionCacheTargetMaxInMemoryCount(String region) {
        return this.ehcacheStats.getRegionCacheTargetMaxInMemoryCount(region);
    }

    @Override
    public int getRegionCacheTargetMaxTotalCount(String region) {
        return this.ehcacheStats.getRegionCacheTargetMaxTotalCount(region);
    }

    @Override
    public long getSessionCloseCount() {
        return this.hibernateStats.getSessionCloseCount();
    }

    @Override
    public long getSessionOpenCount() {
        return this.hibernateStats.getSessionOpenCount();
    }

    @Override
    public long getSuccessfulTransactionCount() {
        return this.hibernateStats.getSuccessfulTransactionCount();
    }

    @Override
    public String[] getTerracottaHibernateCacheRegionNames() {
        return this.ehcacheStats.getTerracottaHibernateCacheRegionNames();
    }

    @Override
    public long getTransactionCount() {
        return this.hibernateStats.getTransactionCount();
    }

    @Override
    public boolean isRegionCacheEnabled(String region) {
        return this.ehcacheStats.isRegionCacheEnabled(region);
    }

    @Override
    public void setRegionCachesEnabled(boolean enabled) {
        this.ehcacheStats.setRegionCachesEnabled(enabled);
        this.sendNotification("CacheEnabled", enabled);
    }

    @Override
    public void setRegionCacheEnabled(String region, boolean enabled) {
        this.ehcacheStats.setRegionCacheEnabled(region, enabled);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public boolean isRegionCacheLoggingEnabled(String region) {
        return this.ehcacheStats.isRegionCacheLoggingEnabled(region);
    }

    @Override
    public boolean isRegionCacheOrphanEvictionEnabled(String region) {
        return this.ehcacheStats.isRegionCacheOrphanEvictionEnabled(region);
    }

    @Override
    public boolean isRegionCachesEnabled() {
        return this.ehcacheStats.isRegionCachesEnabled();
    }

    @Override
    public boolean isTerracottaHibernateCache(String region) {
        return this.ehcacheStats.isTerracottaHibernateCache(region);
    }

    @Override
    public void setRegionCacheLoggingEnabled(String region, boolean loggingEnabled) {
        this.ehcacheStats.setRegionCacheLoggingEnabled(region, loggingEnabled);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public void setRegionCacheMaxTTISeconds(String region, int maxTTISeconds) {
        this.ehcacheStats.setRegionCacheMaxTTISeconds(region, maxTTISeconds);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public void setRegionCacheMaxTTLSeconds(String region, int maxTTLSeconds) {
        this.ehcacheStats.setRegionCacheMaxTTLSeconds(region, maxTTLSeconds);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public void setRegionCacheTargetMaxInMemoryCount(String region, int targetMaxInMemoryCount) {
        this.ehcacheStats.setRegionCacheTargetMaxInMemoryCount(region, targetMaxInMemoryCount);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public void setRegionCacheTargetMaxTotalCount(String region, int targetMaxTotalCount) {
        this.ehcacheStats.setRegionCacheTargetMaxTotalCount(region, targetMaxTotalCount);
        this.sendNotification("CacheRegionChanged", this.getRegionCacheAttributes(region), region);
    }

    @Override
    public int getNumberOfElementsInMemory(String region) {
        return this.ehcacheStats.getNumberOfElementsInMemory(region);
    }

    @Override
    public int getNumberOfElementsOffHeap(String region) {
        return this.ehcacheStats.getNumberOfElementsOffHeap(region);
    }

    @Override
    public int getNumberOfElementsOnDisk(String region) {
        return this.ehcacheStats.getNumberOfElementsOnDisk(region);
    }

    @Override
    public long getMaxGetTimeMillis() {
        return this.ehcacheStats.getMaxGetTimeMillis();
    }

    @Override
    public long getMaxGetTimeMillis(String cacheName) {
        return this.ehcacheStats.getMaxGetTimeMillis(cacheName);
    }

    @Override
    public long getMinGetTimeMillis() {
        return this.ehcacheStats.getMinGetTimeMillis();
    }

    @Override
    public long getMinGetTimeMillis(String cacheName) {
        return this.ehcacheStats.getMinGetTimeMillis(cacheName);
    }

    @Override
    public float getAverageGetTimeMillis(String region) {
        return this.ehcacheStats.getAverageGetTimeMillis(region);
    }

    @Override
    protected void doDispose() {
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{NOTIFICATION_INFO};
    }

    static {
        String[] notifTypes = new String[]{};
        String name = Notification.class.getName();
        String description = "Ehcache Hibernate Statistics Event";
        NOTIFICATION_INFO = new MBeanNotificationInfo(notifTypes, name, "Ehcache Hibernate Statistics Event");
    }
}

