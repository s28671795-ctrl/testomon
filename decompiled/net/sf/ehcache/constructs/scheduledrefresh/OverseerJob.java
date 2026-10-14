/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.DisallowConcurrentExecution
 *  org.quartz.Job
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.PersistJobDataAfterExecution
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.impl.matchers.GroupMatcher
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.RefreshBatchJob;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshCacheExtension;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshKeyGenerator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class OverseerJob
implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(OverseerJob.class);
    private static final AtomicLong INSTANCE_ID_GENERATOR = new AtomicLong(0L);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ScheduledRefreshKeyGenerator<Serializable> generator;
            JobDataMap jdm = context.getMergedJobDataMap();
            ScheduledRefreshConfiguration config = (ScheduledRefreshConfiguration)jdm.get((Object)ScheduledRefreshCacheExtension.PROP_CONFIG_OBJECT);
            String cacheManagerName = jdm.getString(ScheduledRefreshCacheExtension.PROP_CACHE_MGR_NAME);
            String cacheName = jdm.getString(ScheduledRefreshCacheExtension.PROP_CACHE_NAME);
            CacheManager cacheManager = CacheManager.getCacheManager(cacheManagerName);
            if (cacheManager == null) {
                LOG.warn("Unable to process Scheduled Refresh batch" + context.getJobDetail().getKey() + ": cache manager " + cacheManager + " not found");
                return;
            }
            Ehcache cache = cacheManager.getEhcache(cacheName);
            if (cache == null) {
                LOG.warn("Unable to process Scheduled Refresh batch" + context.getJobDetail().getKey() + ": cache " + cacheName + " not found");
                return;
            }
            ScheduledRefreshCacheExtension extension = ScheduledRefreshCacheExtension.findExtensionFromCache(cache, context.getJobDetail().getKey().getGroup());
            boolean keepingStats = false;
            if (extension != null) {
                keepingStats = true;
                extension.incrementRefreshCount();
            }
            if ((generator = this.makeGeneratorObject(config.getKeyGeneratorClass())) != null) {
                Scheduler scheduler = context.getScheduler();
                LOG.info("Starting Scheduled refresh: " + context.getJobDetail().getKey() + " " + OverseerJob.statsNote(keepingStats));
                this.processKeys(context, config, cache, generator);
                if (config.isUseBulkload()) {
                    try {
                        this.waitForOutstandingJobCount(context, config, scheduler, 0);
                    }
                    catch (SchedulerException e) {
                        LOG.warn("Unable to process Scheduled Refresh batch termination" + context.getJobDetail().getKey(), e);
                    }
                }
            }
        }
        catch (Throwable e) {
            try {
                if (!context.getScheduler().isShutdown()) {
                    LOG.warn("Unable to process Scheduled Refresh batch " + context.getJobDetail().getKey(), e);
                }
            }
            catch (SchedulerException e1) {
                LOG.warn(e1.getMessage(), e1);
            }
        }
    }

    static String statsNote(boolean keepingStats) {
        return "[" + (keepingStats ? "with stats" : "no stats") + "]";
    }

    private int getOutstandingJobCount(JobExecutionContext context, Scheduler scheduler) throws SchedulerException {
        GroupMatcher matcher = GroupMatcher.jobGroupEquals((String)context.getJobDetail().getKey().getGroup());
        Set queuedKeys = scheduler.getJobKeys(matcher);
        return queuedKeys.size();
    }

    private void waitForOutstandingJobCount(JobExecutionContext context, ScheduledRefreshConfiguration config, Scheduler scheduler, int minCount) throws SchedulerException {
        GroupMatcher matcher = GroupMatcher.jobGroupEquals((String)context.getJobDetail().getKey().getGroup());
        Set queuedKeys = scheduler.getJobKeys(matcher);
        while (!scheduler.isShutdown() && queuedKeys.size() > minCount) {
            try {
                Thread.sleep(config.getPollTimeMs());
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            queuedKeys = scheduler.getJobKeys(matcher);
        }
    }

    private void processKeys(JobExecutionContext context, ScheduledRefreshConfiguration config, Ehcache cache, ScheduledRefreshKeyGenerator<Serializable> generator) throws JobExecutionException {
        ArrayList<Serializable> batch = new ArrayList<Serializable>(config.getBatchSize());
        for (Serializable key : generator.generateKeys(cache)) {
            batch.add(key);
            if (batch.size() < config.getBatchSize()) continue;
            try {
                this.process(context, cache, config, batch);
                batch = new ArrayList();
            }
            catch (SchedulerException e) {
                LOG.warn("Unable to process Scheduled Refresh batch" + context.getJobDetail().getKey(), e);
                throw new JobExecutionException((Throwable)e);
            }
            batch.clear();
        }
        if (!batch.isEmpty()) {
            try {
                this.process(context, cache, config, batch);
            }
            catch (SchedulerException e) {
                LOG.warn("Unable to process Scheduled Refresh batch" + context.getJobDetail().getKey(), e);
                throw new JobExecutionException((Throwable)e);
            }
        }
    }

    private void process(JobExecutionContext context, Ehcache underlyingCache, ScheduledRefreshConfiguration config, List<Serializable> batch) throws SchedulerException {
        block3: {
            JobDataMap map = new JobDataMap((Map)context.getJobDetail().getJobDataMap());
            map.put("keyObjects", batch);
            Scheduler scheduler = context.getScheduler();
            JobDetail job = JobBuilder.newJob(RefreshBatchJob.class).withIdentity("RefreshBatch-" + INSTANCE_ID_GENERATOR.incrementAndGet(), context.getTrigger().getJobKey().getGroup()).usingJobData(map).build();
            try {
                this.waitForOutstandingJobCount(context, config, scheduler, config.getParallelJobCount());
                if (!scheduler.isShutdown()) {
                    Trigger trigger = TriggerBuilder.newTrigger().startNow().forJob(job).build();
                    scheduler.scheduleJob(job, trigger);
                }
            }
            catch (SchedulerException e) {
                if (scheduler.isShutdown()) break block3;
                throw e;
            }
        }
    }

    private ScheduledRefreshKeyGenerator<Serializable> makeGeneratorObject(String keyGeneratorClass) {
        try {
            Class<?> gen = Class.forName(keyGeneratorClass);
            ScheduledRefreshKeyGenerator obj = (ScheduledRefreshKeyGenerator)gen.newInstance();
            return obj;
        }
        catch (ClassNotFoundException e) {
            LOG.warn("Unable to instantiate key generator class: " + keyGeneratorClass, e);
        }
        catch (InstantiationException e) {
            LOG.warn("Unable to instantiate key generator class: " + keyGeneratorClass, e);
        }
        catch (IllegalAccessException e) {
            LOG.warn("Unable to instantiate key generator class: " + keyGeneratorClass, e);
        }
        return null;
    }
}

