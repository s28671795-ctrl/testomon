/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.CronTrigger
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.impl.StdSchedulerFactory
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.constructs.scheduledrefresh.OverseerJob;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshJobStorePropertiesFactory;
import net.sf.ehcache.extension.CacheExtension;
import net.sf.ehcache.statistics.extended.ExtendedStatistics;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.statistics.Statistic;
import org.terracotta.statistics.StatisticsManager;

public class ScheduledRefreshCacheExtension
implements CacheExtension {
    static final Logger LOG = LoggerFactory.getLogger(ScheduledRefreshCacheExtension.class);
    static final String PROP_QUARTZ_JOB_STORE_TC_CONFIG_URL = "org.quartz.jobStore.tcConfigUrl";
    static final String PROP_CACHE_NAME = ScheduledRefreshCacheExtension.class.getName() + ".cacheName";
    static final String PROP_CACHE_MGR_NAME = ScheduledRefreshCacheExtension.class.getName() + "cacheManagerName";
    static final String PROP_CONFIG_OBJECT = ScheduledRefreshCacheExtension.class.getName() + "scheduledRefreshConfig";
    static final String PROP_KEYS_TO_PROCESS = "keyObjects";
    private static final String OVERSEER_JOB_NAME = "Overseer";
    private Ehcache underlyingCache;
    private ScheduledRefreshConfiguration config;
    private String name;
    private Scheduler scheduler;
    private String groupName;
    private Status status;
    private AtomicLong refreshCount = new AtomicLong();
    private AtomicLong jobCount = new AtomicLong();
    private AtomicLong keysProcessedCount = new AtomicLong();

    public ScheduledRefreshCacheExtension(ScheduledRefreshConfiguration config, Ehcache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("ScheduledRefresh Cache cannot be null");
        }
        this.underlyingCache = cache;
        if (config == null) {
            throw new IllegalArgumentException("ScheduledRefresh extension config cannot be null");
        }
        this.config = config;
        config.validate();
        this.status = Status.STATUS_UNINITIALISED;
        StatisticsManager.associate(this).withParent(cache);
    }

    @Override
    public void init() {
        try {
            this.name = this.config.getScheduledRefreshName() != null ? "scheduledRefresh_" + this.underlyingCache.getCacheManager().getName() + "_" + this.underlyingCache.getName() + "_" + this.config.getScheduledRefreshName() : "scheduledRefresh_" + this.underlyingCache.getCacheManager().getName() + "_" + this.underlyingCache.getName();
            this.groupName = this.name + "_grp";
            try {
                this.makeAndStartQuartzScheduler();
                try {
                    this.scheduleOverseerJob();
                    this.status = Status.STATUS_ALIVE;
                }
                catch (SchedulerException e) {
                    LOG.error("Unable to schedule control job for Scheduled Refresh", e);
                }
            }
            catch (SchedulerException e) {
                LOG.error("Unable to instantiate Quartz Job Scheduler for Scheduled Refresh", e);
            }
        }
        catch (RuntimeException e) {
            LOG.error("Unable to initialise ScheduledRefesh extension", e);
        }
    }

    private void scheduleOverseerJob() throws SchedulerException {
        JobDetail seed = this.makeOverseerJob();
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule((String)this.config.getCronExpression());
        CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(OVERSEER_JOB_NAME, this.groupName).forJob(seed).withSchedule((ScheduleBuilder)cronSchedule).build();
        try {
            this.scheduler.addJob(seed, false);
        }
        catch (SchedulerException schedulerException) {
            // empty catch block
        }
        try {
            this.scheduler.scheduleJob((Trigger)trigger);
        }
        catch (SchedulerException e) {
            try {
                this.scheduler.rescheduleJob(trigger.getKey(), (Trigger)trigger);
            }
            catch (SchedulerException ee) {
                LOG.error("Unable to modify trigger for: " + trigger.getKey());
            }
        }
    }

    private JobDetail makeOverseerJob() throws SchedulerException {
        JobDataMap jdm = new JobDataMap();
        jdm.put(PROP_CACHE_MGR_NAME, this.underlyingCache.getCacheManager().getName());
        jdm.put(PROP_CACHE_NAME, this.underlyingCache.getName());
        jdm.put(PROP_CONFIG_OBJECT, (Object)this.config);
        JobDetail seed = JobBuilder.newJob(OverseerJob.class).storeDurably().withIdentity(OVERSEER_JOB_NAME, this.groupName).usingJobData(jdm).build();
        return seed;
    }

    private void makeAndStartQuartzScheduler() throws SchedulerException {
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", this.name);
        props.put("schedName", this.name);
        props.put("org.quartz.scheduler.makeSchedulerThreadDaemon", Boolean.TRUE.toString());
        props.put("org.quartz.threadPool.threadCount", Integer.toString(this.config.getQuartzThreadCount() + 1));
        Properties jsProps = this.getJobStoreProperties();
        for (Object key : props.keySet()) {
            if (jsProps.containsKey(key)) continue;
            jsProps.put(key, props.get(key));
        }
        StdSchedulerFactory factory2 = new StdSchedulerFactory(jsProps);
        this.scheduler = factory2.getScheduler();
        this.scheduler.start();
    }

    private Properties getJobStoreProperties() throws SchedulerException {
        try {
            String clzName = this.config.getJobStoreFactoryClass();
            Class<?> clz = Class.forName(clzName);
            ScheduledRefreshJobStorePropertiesFactory fact = (ScheduledRefreshJobStorePropertiesFactory)clz.newInstance();
            Properties jsProps = fact.jobStoreProperties(this.underlyingCache, this.config);
            return jsProps;
        }
        catch (Throwable t2) {
            throw new SchedulerException(t2);
        }
    }

    @Override
    public void dispose() throws CacheException {
        try {
            this.scheduler.shutdown();
        }
        catch (SchedulerException e) {
            throw new CacheException(e);
        }
        catch (Throwable t2) {
            LOG.info("ScheduledRefresh cache extension exception during shutdown.", t2);
        }
        this.status = Status.STATUS_SHUTDOWN;
    }

    @Override
    public CacheExtension clone(Ehcache cache) throws CloneNotSupportedException {
        return null;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    String getExtensionGroupName() {
        return this.groupName;
    }

    Ehcache getUnderlyingCache() {
        return this.underlyingCache;
    }

    static ScheduledRefreshCacheExtension findExtensionFromCache(Ehcache cache, String groupName) {
        for (CacheExtension ce : cache.getRegisteredCacheExtensions()) {
            ScheduledRefreshCacheExtension probe;
            if (!(ce instanceof ScheduledRefreshCacheExtension) || !(probe = (ScheduledRefreshCacheExtension)ce).getUnderlyingCache().getName().equals(cache.getName()) || !probe.getExtensionGroupName().equals(groupName)) continue;
            return probe;
        }
        return null;
    }

    static Collection<ScheduledRefreshCacheExtension> findExtensionsFromCache(Ehcache cache) {
        LinkedList<ScheduledRefreshCacheExtension> exts = new LinkedList<ScheduledRefreshCacheExtension>();
        for (CacheExtension ce : cache.getRegisteredCacheExtensions()) {
            if (!(ce instanceof ScheduledRefreshCacheExtension)) continue;
            exts.add((ScheduledRefreshCacheExtension)ce);
        }
        return exts;
    }

    void incrementRefreshCount() {
        this.refreshCount.incrementAndGet();
    }

    void incrementJobCount() {
        this.jobCount.incrementAndGet();
    }

    void incrementProcessedCount(int many) {
        this.keysProcessedCount.addAndGet(many);
    }

    @Statistic(name="refresh", tags={"scheduledrefresh"})
    public long getRefreshCount() {
        return this.refreshCount.get();
    }

    @Statistic(name="job", tags={"scheduledrefresh"})
    public long getJobCount() {
        return this.jobCount.get();
    }

    @Statistic(name="keysprocessed", tags={"scheduledrefresh"})
    public long getKeysProcessedCount() {
        return this.keysProcessedCount.get();
    }

    public static Set<ExtendedStatistics.Statistic<Number>> findRefreshStatistics(Ehcache cache) {
        return cache.getStatistics().getExtended().passthru("refresh", Collections.singletonMap("scheduledrefresh", null).keySet());
    }

    public static Set<ExtendedStatistics.Statistic<Number>> findJobStatistics(Ehcache cache) {
        return cache.getStatistics().getExtended().passthru("job", Collections.singletonMap("scheduledrefresh", null).keySet());
    }

    public static Set<ExtendedStatistics.Statistic<Number>> findKeysProcessedStatistics(Ehcache cache) {
        return cache.getStatistics().getExtended().passthru("keysprocessed", Collections.singletonMap("scheduledrefresh", null).keySet());
    }

    public static ExtendedStatistics.Statistic<Number> findRefreshStatistic(Ehcache cache) {
        Set<ExtendedStatistics.Statistic<Number>> set = ScheduledRefreshCacheExtension.findRefreshStatistics(cache);
        if (set.size() == 1) {
            return set.iterator().next();
        }
        throw new IllegalStateException("Multiple scheduled refresh stats found for this cache");
    }

    public static ExtendedStatistics.Statistic<Number> findJobStatistic(Ehcache cache) {
        Set<ExtendedStatistics.Statistic<Number>> set = ScheduledRefreshCacheExtension.findJobStatistics(cache);
        if (set.size() == 1) {
            return set.iterator().next();
        }
        throw new IllegalStateException("Multiple scheduled refresh stats found for this cache");
    }

    public static ExtendedStatistics.Statistic<Number> findKeysProcessedStatistic(Ehcache cache) {
        Set<ExtendedStatistics.Statistic<Number>> set = ScheduledRefreshCacheExtension.findKeysProcessedStatistics(cache);
        if (set.size() == 1) {
            return set.iterator().next();
        }
        throw new IllegalStateException("Multiple scheduled refresh stats found for this cache");
    }
}

