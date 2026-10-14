/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshRAMJobStoreFactory;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshTerracottaJobStoreFactory;
import net.sf.ehcache.constructs.scheduledrefresh.SimpleScheduledRefreshKeyGenerator;

public class ScheduledRefreshConfiguration
implements Serializable,
Cloneable {
    public static final boolean DEFAULT_NULL_EVICTS = true;
    public static final boolean DEFAULT_USE_BULKLOAD = false;
    public static final int DEFAULT_BATCHSIZE = 100;
    public static final int DEFAULT_QUARTZ_THREADCOUNT = 2;
    public static final int DEFAULT_PARALLEL_JOB_COUNT = 2;
    public static final int DEFAULT_POLL_TIME_MS = (int)TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
    private static final String DEFAULT_KEY_GENERATOR_CLASS = SimpleScheduledRefreshKeyGenerator.class.getName();
    private static final String DEFAULT_JOB_STORE_FACTORY_CLASS = ScheduledRefreshRAMJobStoreFactory.class.getName();
    private static final long serialVersionUID = -6877036694574988955L;
    private int batchSize = 100;
    private boolean useBulkload = false;
    private String cronExpression = null;
    private int quartzThreadCount = 2;
    private int parallelJobCount = 2;
    private String keyGeneratorClass = DEFAULT_KEY_GENERATOR_CLASS;
    private String scheduledRefreshName = null;
    private String jobStoreFactoryClassName = DEFAULT_JOB_STORE_FACTORY_CLASS;
    private String tcConfigUrl = null;
    private int pollTimeMs = DEFAULT_POLL_TIME_MS;
    private boolean evictOnLoadMiss = true;
    private volatile boolean valid = false;
    private Properties excessProperties = new Properties();
    private volatile boolean frozen = false;

    public ScheduledRefreshConfiguration fromProperties(Properties properties) {
        this.valid = false;
        this.frozen = false;
        this.excessProperties.clear();
        if (properties != null) {
            block14: for (String property : properties.stringPropertyNames()) {
                PropKey pk;
                String stringValue = properties.getProperty(property).trim();
                try {
                    pk = PropKey.valueOf(property);
                }
                catch (Exception e) {
                    pk = null;
                }
                if (pk != null) {
                    switch (pk) {
                        case batchSize: {
                            this.setBatchSize(Integer.parseInt(stringValue));
                            continue block14;
                        }
                        case useBulkload: {
                            this.setUseBulkload(Boolean.parseBoolean(stringValue));
                            continue block14;
                        }
                        case cronExpression: {
                            this.setCronExpression(stringValue);
                            continue block14;
                        }
                        case jobStoreFactory: {
                            this.setJobStoreFactoryClassName(stringValue);
                            continue block14;
                        }
                        case quartzJobCount: {
                            this.setQuartzThreadCount(Integer.parseInt(stringValue));
                            continue block14;
                        }
                        case parallelJobCount: {
                            this.setParallelJobCount(Integer.parseInt(stringValue));
                            continue block14;
                        }
                        case pollTimeMs: {
                            this.setPollTimeMs(Integer.parseInt(stringValue));
                            continue block14;
                        }
                        case evictOnLoadMiss: {
                            this.setEvictOnLoadMiss(Boolean.parseBoolean(stringValue));
                            continue block14;
                        }
                        case tcConfigUrl: {
                            this.setTerracottaConfigUrl(stringValue);
                            continue block14;
                        }
                        case keyGenerator: {
                            this.setKeyGeneratorClass(stringValue);
                            continue block14;
                        }
                    }
                    throw new IllegalStateException("Unhandled property key: " + (Object)((Object)pk));
                }
                this.excessProperties.put(property, stringValue);
            }
        }
        return this.build();
    }

    public Properties toProperties() {
        Properties p = new Properties();
        p.setProperty(PropKey.batchSize.name(), Long.toString(this.getBatchSize()));
        p.setProperty(PropKey.useBulkload.name(), Boolean.toString(this.isUseBulkload()));
        p.setProperty(PropKey.evictOnLoadMiss.name(), Boolean.toString(this.isEvictOnLoadMiss()));
        p.setProperty(PropKey.cronExpression.name(), this.getCronExpression());
        p.setProperty(PropKey.jobStoreFactory.name(), this.getJobStoreFactoryClass());
        p.setProperty(PropKey.quartzJobCount.name(), Integer.toString(this.getQuartzThreadCount()));
        p.setProperty(PropKey.parallelJobCount.name(), Integer.toString(this.getParallelJobCount()));
        p.setProperty(PropKey.pollTimeMs.name(), Integer.toString(this.getPollTimeMs()));
        p.setProperty(PropKey.keyGenerator.name(), this.getKeyGeneratorClass());
        p.setProperty(PropKey.tcConfigUrl.name(), this.getTerracottaConfigUrl());
        for (String property : this.excessProperties.stringPropertyNames()) {
            String stringValue = this.excessProperties.getProperty(property).trim();
            p.put(property, stringValue);
        }
        return p;
    }

    public ScheduledRefreshConfiguration build() {
        this.validate();
        this.frozen = true;
        return this;
    }

    private void checkFrozen() {
        if (this.frozen) {
            throw new IllegalStateException("Can't modify a frozen configuration.");
        }
    }

    public void validate() {
        if (this.cronExpression == null) {
            throw new IllegalArgumentException("Cron Schedule cannot be unspecified");
        }
        if (this.parallelJobCount < 2) {
            throw new IllegalArgumentException("parallelJobCount must be >= 2 [" + this.parallelJobCount + "]");
        }
        if (this.quartzThreadCount < 2) {
            throw new IllegalArgumentException("quartzThreadCount must be >= 2 [" + this.quartzThreadCount + "]");
        }
        if (this.batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be >= 1 [" + this.batchSize + "]");
        }
        if (this.pollTimeMs < 0) {
            throw new IllegalArgumentException("pollTimeMS must be >=0 [" + this.pollTimeMs + "]");
        }
        long oneMinuteMS = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.MINUTES);
        if ((long)this.pollTimeMs > oneMinuteMS) {
            throw new IllegalArgumentException("pollTimeMS must be < " + oneMinuteMS + " [" + this.pollTimeMs + "]");
        }
        if (this.jobStoreFactoryClassName == null) {
            this.jobStoreFactoryClassName = DEFAULT_JOB_STORE_FACTORY_CLASS;
        }
        if (this.keyGeneratorClass == null) {
            this.keyGeneratorClass = DEFAULT_KEY_GENERATOR_CLASS;
        }
        this.valid = true;
    }

    public boolean isValid() {
        return this.valid;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.checkFrozen();
        this.valid = false;
        this.batchSize = batchSize;
    }

    public ScheduledRefreshConfiguration batchSize(int batchSize) {
        this.setBatchSize(batchSize);
        return this;
    }

    public boolean isUseBulkload() {
        return this.useBulkload;
    }

    public void setUseBulkload(boolean useBulkload) {
        this.checkFrozen();
        this.valid = false;
        this.useBulkload = useBulkload;
    }

    public ScheduledRefreshConfiguration useBulkload(boolean yes) {
        this.setUseBulkload(yes);
        return this;
    }

    public String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.checkFrozen();
        this.valid = false;
        this.cronExpression = cronExpression;
    }

    public ScheduledRefreshConfiguration cronExpression(String cronExpression) {
        this.setCronExpression(cronExpression);
        return this;
    }

    public int getQuartzThreadCount() {
        return this.quartzThreadCount;
    }

    public void setQuartzThreadCount(int quartzThreadCount) {
        this.checkFrozen();
        this.valid = false;
        this.quartzThreadCount = quartzThreadCount;
    }

    public ScheduledRefreshConfiguration quartzThreadCount(int quartzThreadCount) {
        this.setQuartzThreadCount(quartzThreadCount);
        return this;
    }

    public String getKeyGeneratorClass() {
        return this.keyGeneratorClass;
    }

    public void setKeyGeneratorClass(String keyGeneratorClass) {
        this.checkFrozen();
        this.keyGeneratorClass = keyGeneratorClass;
    }

    public ScheduledRefreshConfiguration keyGeneratorClass(String keyGeneratorClass) {
        this.setKeyGeneratorClass(keyGeneratorClass);
        return this;
    }

    public String getScheduledRefreshName() {
        return this.scheduledRefreshName;
    }

    public void setScheduledRefreshName(String part) {
        this.checkFrozen();
        this.scheduledRefreshName = part;
    }

    public ScheduledRefreshConfiguration scheduledRefreshName(String part) {
        this.setScheduledRefreshName(part);
        return this;
    }

    public boolean isEvictOnLoadMiss() {
        return this.evictOnLoadMiss;
    }

    public void setEvictOnLoadMiss(boolean loadMissEvicts) {
        this.checkFrozen();
        this.valid = false;
        this.evictOnLoadMiss = loadMissEvicts;
    }

    public ScheduledRefreshConfiguration evictOnLoadMiss(boolean loadMissEvicts) {
        this.setEvictOnLoadMiss(loadMissEvicts);
        return this;
    }

    public int getPollTimeMs() {
        return this.pollTimeMs;
    }

    public void setPollTimeMs(int pollTimeMs) {
        this.checkFrozen();
        this.valid = false;
        this.pollTimeMs = pollTimeMs;
    }

    public ScheduledRefreshConfiguration pollTimeMs(int pollTimeMs) {
        this.setPollTimeMs(pollTimeMs);
        return this;
    }

    public String getJobStoreFactoryClass() {
        return this.jobStoreFactoryClassName;
    }

    public void setJobStoreFactoryClassName(String className) {
        this.checkFrozen();
        this.jobStoreFactoryClassName = className;
    }

    public ScheduledRefreshConfiguration jobStoreFactory(String className) {
        this.setJobStoreFactoryClassName(className);
        return this;
    }

    public Properties getExcessProperties() {
        return this.excessProperties;
    }

    public String toString() {
        return "ScheduledRefreshConfiguration{terracottaConfigUrl=" + this.getTerracottaConfigUrl() + ", batchSize=" + this.batchSize + ", useBulkload=" + this.useBulkload + ", cronExpression='" + this.cronExpression + '\'' + ", quartzThreadCount=" + this.quartzThreadCount + ", parallelJobCount=" + this.parallelJobCount + ", keyGeneratorClass='" + this.keyGeneratorClass + '\'' + ", uniqueNamePart='" + this.scheduledRefreshName + '\'' + ", pollTimeMs=" + this.pollTimeMs + ", loadMissEvicts=" + this.evictOnLoadMiss + ", valid=" + this.valid + '}';
    }

    protected Object clone() throws CloneNotSupportedException {
        ScheduledRefreshConfiguration clone = (ScheduledRefreshConfiguration)super.clone();
        clone.fromProperties(this.toProperties());
        return clone;
    }

    public void setTerracottaConfigUrl(String terracottaConfigUrl) {
        this.checkFrozen();
        this.valid = false;
        this.tcConfigUrl = terracottaConfigUrl;
        if (terracottaConfigUrl == null) {
            this.setJobStoreFactoryClassName(null);
        } else {
            this.setJobStoreFactoryClassName(ScheduledRefreshTerracottaJobStoreFactory.class.getName());
        }
    }

    public ScheduledRefreshConfiguration terracottaConfigUrl(String terracottaConfigUrl) {
        this.setTerracottaConfigUrl(terracottaConfigUrl);
        return this;
    }

    public String getTerracottaConfigUrl() {
        return this.tcConfigUrl;
    }

    public void setParallelJobCount(int parallelJobCount) {
        this.checkFrozen();
        this.parallelJobCount = parallelJobCount;
        this.valid = false;
    }

    public ScheduledRefreshConfiguration parallelJobCount(int parallelJobCount) {
        this.setParallelJobCount(parallelJobCount);
        return this;
    }

    public int getParallelJobCount() {
        return this.parallelJobCount;
    }

    public static enum PropKey {
        batchSize,
        keyGenerator,
        cronExpression,
        useBulkload,
        quartzJobCount,
        tcConfigUrl,
        scheduledRefreshName,
        pollTimeMs,
        evictOnLoadMiss,
        jobStoreFactory,
        parallelJobCount;

    }
}

