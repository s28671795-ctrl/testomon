/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.scheduledrefresh.OverseerJob;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshCacheExtension;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.loader.CacheLoader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshBatchJob
implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(OverseerJob.class);
    private static HashMap<String, AtomicInteger> bulkLoadTrackingMap = new HashMap(1);
    private static ReentrantLock bulkloadLock = new ReentrantLock();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void requestBulkLoadEnabled(Ehcache cache) {
        bulkloadLock.lock();
        try {
            boolean prior = cache.isNodeBulkLoadEnabled();
            if (prior) {
                String key = cache.getCacheManager().getName() + "/" + cache.getName();
                AtomicInteger permits = bulkLoadTrackingMap.get(key);
                if (permits == null) {
                    permits = new AtomicInteger(1);
                    bulkLoadTrackingMap.put(key, permits);
                    cache.setNodeBulkLoadEnabled(true);
                } else {
                    permits.incrementAndGet();
                }
            }
        }
        finally {
            bulkloadLock.unlock();
        }
    }

    private static void requestBulkLoadRestored(Ehcache cache) {
        bulkloadLock.lock();
        try {
            String key = cache.getCacheManager().getName() + "/" + cache.getName();
            AtomicInteger permits = bulkLoadTrackingMap.get(key);
            if (permits != null && permits.decrementAndGet() == 0) {
                bulkLoadTrackingMap.remove(key);
                cache.setNodeBulkLoadEnabled(true);
            }
        }
        finally {
            bulkloadLock.unlock();
        }
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jdm = context.getMergedJobDataMap();
            ScheduledRefreshConfiguration config = (ScheduledRefreshConfiguration)jdm.get((Object)ScheduledRefreshCacheExtension.PROP_CONFIG_OBJECT);
            String cacheManagerName = jdm.getString(ScheduledRefreshCacheExtension.PROP_CACHE_MGR_NAME);
            String cacheName = jdm.getString(ScheduledRefreshCacheExtension.PROP_CACHE_NAME);
            CacheManager cacheManager = CacheManager.getCacheManager(cacheManagerName);
            Ehcache underlyingCache = cacheManager.getEhcache(cacheName);
            HashSet keysToProcess = new HashSet((Collection)jdm.get((Object)"keyObjects"));
            ScheduledRefreshCacheExtension extension = ScheduledRefreshCacheExtension.findExtensionFromCache(underlyingCache, context.getJobDetail().getKey().getGroup());
            boolean keepingStats = false;
            if (extension != null) {
                extension.incrementJobCount();
                extension.incrementProcessedCount(keysToProcess.size());
                keepingStats = true;
            }
            LOG.info("Scheduled refresh batch job: " + context.getJobDetail().getKey() + " size: " + keysToProcess.size() + " " + OverseerJob.statsNote(keepingStats));
            try {
                if (config.isUseBulkload()) {
                    RefreshBatchJob.requestBulkLoadEnabled(underlyingCache);
                }
            }
            catch (UnsupportedOperationException e) {
                LOG.warn("Bulk Load requested for cache that does not support bulk load.");
            }
            for (CacheLoader loader : underlyingCache.getRegisteredCacheLoaders()) {
                if (keysToProcess.isEmpty()) break;
                Map values2 = loader.loadAll(keysToProcess);
                keysToProcess.removeAll(values2.keySet());
                for (Map.Entry entry : values2.entrySet()) {
                    Element newElement = new Element(entry.getKey(), entry.getValue());
                    underlyingCache.put(newElement);
                }
            }
            if (config.isEvictOnLoadMiss() && !keysToProcess.isEmpty()) {
                underlyingCache.removeAll(keysToProcess);
            }
            try {
                if (config.isUseBulkload()) {
                    RefreshBatchJob.requestBulkLoadRestored(underlyingCache);
                }
            }
            catch (UnsupportedOperationException unsupportedOperationException) {}
        }
        catch (Throwable t2) {
            LOG.warn("Scheduled refresh batch job failure: " + context.getJobDetail().getKey(), t2);
        }
    }
}

