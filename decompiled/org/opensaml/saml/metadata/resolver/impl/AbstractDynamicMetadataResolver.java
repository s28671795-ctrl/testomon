/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Timer;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.codec.StringDigester;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.metrics.MetricsSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.metadata.resolver.DynamicMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDynamicMetadataResolver
extends AbstractMetadataResolver
implements DynamicMetadataResolver {
    public static final String METRIC_TIMER_FETCH_FROM_ORIGIN_SOURCE = "timer.fetchFromOriginSource";
    public static final String METRIC_TIMER_RESOLVE = "timer.resolve";
    public static final String METRIC_RATIOGAUGE_FETCH_TO_RESOLVE = "ratioGauge.fetchToResolve";
    public static final String METRIC_GAUGE_NUM_LIVE_ENTITYIDS = "gauge.numLiveEntityIDs";
    public static final String METRIC_GAUGE_PERSISTENT_CACHE_INIT = "gauge.persistentCacheInitialization";
    private final Logger log = LoggerFactory.getLogger(AbstractDynamicMetadataResolver.class);
    @NonnullAfterInit
    private String metricsBaseName;
    @Nullable
    private com.codahale.metrics.Timer timerResolve;
    @Nullable
    private com.codahale.metrics.Timer timerFetchFromOriginSource;
    @Nullable
    private RatioGauge ratioGaugeFetchToResolve;
    @Nullable
    private Gauge<Integer> gaugeNumLiveEntityIDs;
    @Nullable
    private Gauge<PersistentCacheInitializationMetrics> gaugePersistentCacheInit;
    private Timer taskTimer;
    private boolean createdOwnTaskTimer;
    @Duration
    @Positive
    private Long minCacheDuration;
    @Duration
    @Positive
    private Long maxCacheDuration;
    @Positive
    private Float refreshDelayFactor;
    @Duration
    @Positive
    private Long maxIdleEntityData;
    private boolean removeIdleEntityData;
    @Duration
    @Positive
    private Long cleanupTaskInterval;
    private BackingStoreCleanupSweeper cleanupTask;
    private XMLObjectLoadSaveManager<EntityDescriptor> persistentCacheManager;
    private Function<EntityDescriptor, String> persistentCacheKeyGenerator;
    private boolean initializeFromPersistentCacheInBackground;
    @Duration
    @Positive
    private Long backgroundInitializationFromCacheDelay;
    private Predicate<EntityDescriptor> initializationFromCachePredicate;
    @NonnullAfterInit
    private PersistentCacheInitializationMetrics persistentCacheInitMetrics;
    private boolean initializing;

    public AbstractDynamicMetadataResolver(@Nullable Timer backgroundTaskTimer) {
        if (backgroundTaskTimer == null) {
            this.taskTimer = new Timer(true);
            this.createdOwnTaskTimer = true;
        } else {
            this.taskTimer = backgroundTaskTimer;
        }
        this.minCacheDuration = 600000L;
        this.maxCacheDuration = 28800000L;
        this.refreshDelayFactor = Float.valueOf(0.75f);
        this.cleanupTaskInterval = 1800000L;
        this.maxIdleEntityData = 28800000L;
        this.removeIdleEntityData = true;
        this.initializeFromPersistentCacheInBackground = true;
        this.backgroundInitializationFromCacheDelay = 2000L;
    }

    public boolean isInitializeFromPersistentCacheInBackground() {
        return this.initializeFromPersistentCacheInBackground;
    }

    public void setInitializeFromPersistentCacheInBackground(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.initializeFromPersistentCacheInBackground = flag;
    }

    @Nonnull
    public Long getBackgroundInitializationFromCacheDelay() {
        return this.backgroundInitializationFromCacheDelay;
    }

    public void setBackgroundInitializationFromCacheDelay(@Nonnull Long delay) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.backgroundInitializationFromCacheDelay = delay;
    }

    @Nullable
    public XMLObjectLoadSaveManager<EntityDescriptor> getPersistentCacheManager() {
        return this.persistentCacheManager;
    }

    public void setPersistentCacheManager(@Nullable XMLObjectLoadSaveManager<EntityDescriptor> manager) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.persistentCacheManager = manager;
    }

    public boolean isPersistentCachingEnabled() {
        return this.getPersistentCacheManager() != null;
    }

    @NonnullAfterInit
    public Function<EntityDescriptor, String> getPersistentCacheKeyGenerator() {
        return this.persistentCacheKeyGenerator;
    }

    public void setPersistentCacheKeyGenerator(@Nullable Function<EntityDescriptor, String> generator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.persistentCacheKeyGenerator = generator;
    }

    @NonnullAfterInit
    public Predicate<EntityDescriptor> getInitializationFromCachePredicate() {
        return this.initializationFromCachePredicate;
    }

    public void setInitializationFromCachePredicate(@Nullable Predicate<EntityDescriptor> predicate) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.initializationFromCachePredicate = predicate;
    }

    @Nonnull
    public Long getMinCacheDuration() {
        return this.minCacheDuration;
    }

    public void setMinCacheDuration(@Nonnull Long duration) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.minCacheDuration = Constraint.isNotNull(duration, "Minimum cache duration may not be null");
    }

    @Nonnull
    public Long getMaxCacheDuration() {
        return this.maxCacheDuration;
    }

    public void setMaxCacheDuration(@Nonnull Long duration) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.maxCacheDuration = Constraint.isNotNull(duration, "Maximum cache duration may not be null");
    }

    public Float getRefreshDelayFactor() {
        return this.refreshDelayFactor;
    }

    public void setRefreshDelayFactor(Float factor) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (factor.floatValue() <= 0.0f || factor.floatValue() >= 1.0f) {
            throw new IllegalArgumentException("Refresh delay factor must be a number between 0.0 and 1.0, exclusive");
        }
        this.refreshDelayFactor = factor;
    }

    public boolean isRemoveIdleEntityData() {
        return this.removeIdleEntityData;
    }

    public void setRemoveIdleEntityData(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.removeIdleEntityData = flag;
    }

    @Nonnull
    public Long getMaxIdleEntityData() {
        return this.maxIdleEntityData;
    }

    public void setMaxIdleEntityData(@Nonnull Long max) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.maxIdleEntityData = Constraint.isNotNull(max, "Max idle entity data may not be null");
    }

    @Nonnull
    public Long getCleanupTaskInterval() {
        return this.cleanupTaskInterval;
    }

    public void setCleanupTaskInterval(@Nonnull Long interval) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.cleanupTaskInterval = Constraint.isNotNull(interval, "Cleanup task interval may not be null");
    }

    @NonnullAfterInit
    public String getMetricsBaseName() {
        return this.metricsBaseName;
    }

    public void setMetricsBaseName(@Nullable String baseName) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.metricsBaseName = StringSupport.trimOrNull(baseName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public Iterable<EntityDescriptor> resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        Timer.Context contextResolve = MetricsSupport.startTimer(this.timerResolve);
        try {
            EntityIdCriterion entityIdCriterion = criteria.get(EntityIdCriterion.class);
            if (entityIdCriterion == null || Strings.isNullOrEmpty(entityIdCriterion.getEntityId())) {
                this.log.info("{} Entity Id was not supplied in criteria set, skipping resolution", (Object)this.getLogPrefix());
                Set<EntityDescriptor> set = Collections.emptySet();
                return set;
            }
            String entityID = StringSupport.trimOrNull(criteria.get(EntityIdCriterion.class).getEntityId());
            this.log.debug("{} Attempting to resolve metadata for entityID: {}", (Object)this.getLogPrefix(), (Object)entityID);
            EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
            Lock readLock = mgmtData.getReadWriteLock().readLock();
            Iterable<EntityDescriptor> candidates = null;
            try {
                readLock.lock();
                List<EntityDescriptor> descriptors = this.lookupEntityID(entityID);
                if (descriptors.isEmpty()) {
                    this.log.debug("{} Did not find requested metadata in backing store, attempting to resolve dynamically", (Object)this.getLogPrefix());
                } else if (this.shouldAttemptRefresh(mgmtData)) {
                    this.log.debug("{} Metadata was indicated to be refreshed based on refresh trigger time", (Object)this.getLogPrefix());
                } else {
                    this.log.debug("{} Found requested metadata in backing store", (Object)this.getLogPrefix());
                    candidates = descriptors;
                }
            }
            finally {
                readLock.unlock();
            }
            if (candidates == null) {
                candidates = this.resolveFromOriginSource(criteria);
            }
            Iterable<EntityDescriptor> iterable = this.predicateFilterCandidates(candidates, criteria, false);
            return iterable;
        }
        finally {
            MetricsSupport.stopTimer(contextResolve);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nonnull
    @NonnullElements
    protected Iterable<EntityDescriptor> resolveFromOriginSource(@Nonnull CriteriaSet criteria) throws ResolverException {
        String entityID = StringSupport.trimOrNull(criteria.get(EntityIdCriterion.class).getEntityId());
        EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
        Lock writeLock = mgmtData.getReadWriteLock().writeLock();
        try {
            writeLock.lock();
            List<EntityDescriptor> descriptors = this.lookupEntityID(entityID);
            if (!descriptors.isEmpty() && !this.shouldAttemptRefresh(mgmtData)) {
                this.log.debug("{} Metadata was resolved and stored by another thread while this thread was waiting on the write lock", (Object)this.getLogPrefix());
                List<EntityDescriptor> list = descriptors;
                return list;
            }
            this.log.debug("{} Resolving metadata dynamically for entity ID: {}", (Object)this.getLogPrefix(), (Object)entityID);
            Timer.Context contextFetchFromOriginSource = MetricsSupport.startTimer(this.timerFetchFromOriginSource);
            XMLObject root = null;
            try {
                root = this.fetchFromOriginSource(criteria);
            }
            finally {
                MetricsSupport.stopTimer(contextFetchFromOriginSource);
            }
            if (root == null) {
                this.log.debug("{} No metadata was fetched from the origin source", (Object)this.getLogPrefix());
            } else {
                try {
                    this.processNewMetadata(root, entityID);
                }
                catch (FilterException e) {
                    this.log.error("{} Metadata filtering problem processing new metadata", (Object)this.getLogPrefix(), (Object)e);
                }
            }
            List<EntityDescriptor> list = this.lookupEntityID(entityID);
            return list;
        }
        catch (IOException e) {
            this.log.error("{} Error fetching metadata from origin source", (Object)this.getLogPrefix(), (Object)e);
            List<EntityDescriptor> list = this.lookupEntityID(entityID);
            return list;
        }
        finally {
            writeLock.unlock();
        }
    }

    @Nullable
    protected abstract XMLObject fetchFromOriginSource(@Nonnull CriteriaSet var1) throws IOException;

    @Override
    @Nonnull
    @NonnullElements
    protected List<EntityDescriptor> lookupEntityID(@Nonnull String entityID) throws ResolverException {
        this.getBackingStore().getManagementData(entityID).recordEntityAccess();
        return super.lookupEntityID(entityID);
    }

    @Nonnull
    protected void processNewMetadata(@Nonnull XMLObject root, @Nonnull String expectedEntityID) throws FilterException {
        try {
            this.processNewMetadata(root, expectedEntityID, false);
        }
        catch (ResolverException e) {
            throw new FilterException(e);
        }
    }

    @Nonnull
    protected void processNewMetadata(@Nonnull XMLObject root, @Nonnull String expectedEntityID, boolean fromPersistentCache) throws FilterException, ResolverException {
        XMLObject filteredMetadata = this.filterMetadata(this.prepareForFiltering(root));
        if (filteredMetadata == null) {
            this.log.info("{} Metadata filtering process produced a null document, resulting in an empty data set", (Object)this.getLogPrefix());
            this.releaseMetadataDOM(root);
            if (fromPersistentCache) {
                throw new FilterException("Metadata filtering process produced a null XMLObject");
            }
            return;
        }
        if (filteredMetadata instanceof EntityDescriptor) {
            EntityDescriptor entityDescriptor = (EntityDescriptor)filteredMetadata;
            if (!Objects.equals(entityDescriptor.getEntityID(), expectedEntityID)) {
                this.log.warn("{} New metadata's entityID '{}' does not match expected entityID '{}', will not process", this.getLogPrefix(), entityDescriptor.getEntityID(), expectedEntityID);
                if (fromPersistentCache) {
                    throw new ResolverException("New metadata's entityID does not match expected entityID");
                }
                return;
            }
            this.preProcessEntityDescriptor(entityDescriptor, this.getBackingStore());
            this.log.info("{} Successfully loaded new EntityDescriptor with entityID '{}' from {}", this.getLogPrefix(), entityDescriptor.getEntityID(), fromPersistentCache ? "persistent cache" : "origin source");
            if (this.isPersistentCachingEnabled() && !fromPersistentCache && root instanceof EntityDescriptor) {
                EntityDescriptor origDescriptor = (EntityDescriptor)root;
                String key = this.getPersistentCacheKeyGenerator().apply(origDescriptor);
                this.log.trace("{} Storing resolved EntityDescriptor '{}' in persistent cache with key '{}'", this.getLogPrefix(), origDescriptor.getEntityID(), key);
                if (key == null) {
                    this.log.warn("{} Could not generate cache storage key for EntityDescriptor '{}', skipping caching", (Object)this.getLogPrefix(), (Object)origDescriptor.getEntityID());
                } else {
                    try {
                        this.getPersistentCacheManager().save(key, origDescriptor, true);
                    }
                    catch (IOException e) {
                        this.log.warn("{} Error saving EntityDescriptor '{}' to cache store with key {}'", this.getLogPrefix(), origDescriptor.getEntityID(), key);
                    }
                }
            }
        } else {
            this.log.warn("{} Document root was not an EntityDescriptor: {}", (Object)this.getLogPrefix(), (Object)root.getClass().getName());
        }
        this.releaseMetadataDOM(filteredMetadata);
        this.releaseMetadataDOM(root);
    }

    @Nonnull
    protected XMLObject prepareForFiltering(@Nonnull XMLObject input) {
        if (this.getMetadataFilter() != null && this.isPersistentCachingEnabled()) {
            try {
                return XMLObjectSupport.cloneXMLObject(input, XMLObjectSupport.CloneOutputOption.RootDOMInNewDocument);
            }
            catch (MarshallingException | UnmarshallingException e) {
                this.log.warn("{} Error cloning XMLObject, will use input root object as filter target", (Object)this.getLogPrefix(), (Object)e);
                return input;
            }
        }
        return input;
    }

    @Override
    protected void preProcessEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull AbstractMetadataResolver.EntityBackingStore backingStore) {
        String entityID = StringSupport.trimOrNull(entityDescriptor.getEntityID());
        this.removeByEntityID(entityID, backingStore);
        super.preProcessEntityDescriptor(entityDescriptor, backingStore);
        DynamicEntityBackingStore dynamicBackingStore = (DynamicEntityBackingStore)backingStore;
        EntityManagementData mgmtData = dynamicBackingStore.getManagementData(entityID);
        DateTime now = new DateTime(ISOChronology.getInstanceUTC());
        this.log.debug("{} For metadata expiration and refresh computation, 'now' is : {}", (Object)this.getLogPrefix(), (Object)now);
        mgmtData.setLastUpdateTime(now);
        mgmtData.setExpirationTime(this.computeExpirationTime(entityDescriptor, now));
        this.log.debug("{} Computed metadata expiration time: {}", (Object)this.getLogPrefix(), (Object)mgmtData.getExpirationTime());
        mgmtData.setRefreshTriggerTime(this.computeRefreshTriggerTime(mgmtData.getExpirationTime(), now));
        this.log.debug("{} Computed refresh trigger time: {}", (Object)this.getLogPrefix(), (Object)mgmtData.getRefreshTriggerTime());
    }

    @Nonnull
    protected DateTime computeExpirationTime(@Nonnull EntityDescriptor entityDescriptor, @Nonnull DateTime now) {
        DateTime lowerBound = now.toDateTime(ISOChronology.getInstanceUTC()).plus(this.getMinCacheDuration());
        DateTime expiration = SAML2Support.getEarliestExpiration(entityDescriptor, now.plus(this.getMaxCacheDuration()), now);
        if (expiration.isBefore(lowerBound)) {
            expiration = lowerBound;
        }
        return expiration;
    }

    @Nonnull
    protected DateTime computeRefreshTriggerTime(@Nullable DateTime expirationTime, @Nonnull DateTime nowDateTime) {
        long refreshDelay;
        DateTime nowDateTimeUTC = nowDateTime.toDateTime(ISOChronology.getInstanceUTC());
        long now = nowDateTimeUTC.getMillis();
        long expireInstant = 0L;
        if (expirationTime != null) {
            expireInstant = expirationTime.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
        }
        if ((refreshDelay = (long)((float)(expireInstant - now) * this.getRefreshDelayFactor().floatValue())) < this.getMinCacheDuration()) {
            refreshDelay = this.getMinCacheDuration();
        }
        return nowDateTimeUTC.plus(refreshDelay);
    }

    protected boolean shouldAttemptRefresh(@Nonnull EntityManagementData mgmtData) {
        DateTime now = new DateTime(ISOChronology.getInstanceUTC());
        return now.isAfter(mgmtData.getRefreshTriggerTime());
    }

    @Override
    @Nonnull
    protected DynamicEntityBackingStore createNewBackingStore() {
        return new DynamicEntityBackingStore();
    }

    @Override
    @NonnullAfterInit
    protected DynamicEntityBackingStore getBackingStore() {
        return (DynamicEntityBackingStore)super.getBackingStore();
    }

    @Override
    protected void initMetadataResolver() throws ComponentInitializationException {
        try {
            this.initializing = true;
            super.initMetadataResolver();
            this.initializeMetricsInstrumentation();
            this.setBackingStore(this.createNewBackingStore());
            if (this.getPersistentCacheKeyGenerator() == null) {
                this.setPersistentCacheKeyGenerator(new DefaultCacheKeyGenerator());
            }
            if (this.getInitializationFromCachePredicate() == null) {
                this.setInitializationFromCachePredicate(Predicates.alwaysTrue());
            }
            this.persistentCacheInitMetrics = new PersistentCacheInitializationMetrics();
            if (this.isPersistentCachingEnabled()) {
                this.persistentCacheInitMetrics.enabled = true;
                if (this.isInitializeFromPersistentCacheInBackground()) {
                    this.log.debug("{} Initializing from the persistent cache in the background in {} ms", (Object)this.getLogPrefix(), (Object)this.getBackgroundInitializationFromCacheDelay());
                    TimerTask initTask = new TimerTask(){

                        @Override
                        public void run() {
                            AbstractDynamicMetadataResolver.this.initializeFromPersistentCache();
                        }
                    };
                    this.taskTimer.schedule(initTask, this.getBackgroundInitializationFromCacheDelay());
                } else {
                    this.log.debug("{} Initializing from the persistent cache in the foreground", (Object)this.getLogPrefix());
                    this.initializeFromPersistentCache();
                }
            }
            this.cleanupTask = new BackingStoreCleanupSweeper();
            this.taskTimer.schedule((TimerTask)this.cleanupTask, 60000L, (long)this.getCleanupTaskInterval());
        }
        finally {
            this.initializing = false;
        }
    }

    private void initializeMetricsInstrumentation() {
        MetricRegistry metricRegistry;
        if (this.getMetricsBaseName() == null) {
            this.setMetricsBaseName(MetricRegistry.name(this.getClass(), this.getId()));
        }
        if ((metricRegistry = MetricsSupport.getMetricRegistry()) != null) {
            this.timerResolve = metricRegistry.timer(MetricRegistry.name(this.getMetricsBaseName(), METRIC_TIMER_RESOLVE));
            this.timerFetchFromOriginSource = metricRegistry.timer(MetricRegistry.name(this.getMetricsBaseName(), METRIC_TIMER_FETCH_FROM_ORIGIN_SOURCE));
            this.ratioGaugeFetchToResolve = MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), METRIC_RATIOGAUGE_FETCH_TO_RESOLVE), new RatioGauge(){

                @Override
                protected RatioGauge.Ratio getRatio() {
                    return RatioGauge.Ratio.of(AbstractDynamicMetadataResolver.this.timerFetchFromOriginSource.getCount(), AbstractDynamicMetadataResolver.this.timerResolve.getCount());
                }
            }, true);
            this.gaugeNumLiveEntityIDs = MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), METRIC_GAUGE_NUM_LIVE_ENTITYIDS), new Gauge<Integer>(){

                @Override
                public Integer getValue() {
                    return AbstractDynamicMetadataResolver.this.getBackingStore().getIndexedDescriptors().keySet().size();
                }
            }, true);
            this.gaugePersistentCacheInit = MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), METRIC_GAUGE_PERSISTENT_CACHE_INIT), new Gauge<PersistentCacheInitializationMetrics>(){

                @Override
                public PersistentCacheInitializationMetrics getValue() {
                    return AbstractDynamicMetadataResolver.this.persistentCacheInitMetrics;
                }
            }, true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void initializeFromPersistentCache() {
        if (!this.isPersistentCachingEnabled()) {
            this.log.trace("{} Persistent caching is not enabled, skipping init from cache", (Object)this.getLogPrefix());
            return;
        }
        this.log.trace("{} Attempting to load and process entities from the persistent cache", (Object)this.getLogPrefix());
        long start2 = System.nanoTime();
        try {
            for (Pair<String, EntityDescriptor> cacheEntry : this.getPersistentCacheManager().listAll()) {
                this.persistentCacheInitMetrics.entriesTotal++;
                EntityDescriptor descriptor = cacheEntry.getSecond();
                String currentKey = cacheEntry.getFirst();
                this.log.trace("{} Loaded EntityDescriptor from cache store with entityID '{}' and storage key '{}'", this.getLogPrefix(), descriptor.getEntityID(), currentKey);
                String entityID = StringSupport.trimOrNull(descriptor.getEntityID());
                EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
                Lock writeLock = mgmtData.getReadWriteLock().writeLock();
                try {
                    writeLock.lock();
                    if (!this.lookupIndexedEntityID(entityID).isEmpty()) {
                        this.log.trace("{} Metadata for entityID '{}' found in persistent cache was already live, ignoring cached entry", (Object)this.getLogPrefix(), (Object)entityID);
                        this.persistentCacheInitMetrics.entriesSkippedAlreadyLive++;
                        continue;
                    }
                    this.processPersistentCacheEntry(currentKey, descriptor);
                }
                finally {
                    writeLock.unlock();
                }
            }
        }
        catch (IOException e) {
            this.log.warn("{} Error loading EntityDescriptors from cache", (Object)this.getLogPrefix(), (Object)e);
        }
        finally {
            this.persistentCacheInitMetrics.processingTime = System.nanoTime() - start2;
            this.log.debug("{} Persistent cache initialization metrics: {}", (Object)this.getLogPrefix(), (Object)this.persistentCacheInitMetrics);
        }
    }

    protected void processPersistentCacheEntry(@Nonnull String currentKey, @Nonnull EntityDescriptor descriptor) {
        if (this.isValid(descriptor)) {
            if (this.getInitializationFromCachePredicate().apply(descriptor)) {
                try {
                    this.processNewMetadata(descriptor, descriptor.getEntityID(), true);
                    this.log.trace("{} Successfully processed EntityDescriptor with entityID '{}' from cache", (Object)this.getLogPrefix(), (Object)descriptor.getEntityID());
                    this.persistentCacheInitMetrics.entriesLoaded++;
                }
                catch (ResolverException | FilterException e) {
                    this.log.warn("{} Error processing EntityDescriptor '{}' from cache with storage key '{}'", this.getLogPrefix(), descriptor.getEntityID(), currentKey, e);
                    this.persistentCacheInitMetrics.entriesSkippedProcessingException++;
                }
            } else {
                this.log.trace("{} Cache initialization predicate indicated to not process EntityDescriptor with entityID '{}' and cache storage key '{}'", this.getLogPrefix(), descriptor.getEntityID(), currentKey);
                this.persistentCacheInitMetrics.entriesSkippedFailedPredicate++;
            }
            String expectedKey = this.getPersistentCacheKeyGenerator().apply(descriptor);
            try {
                if (!Objects.equals(currentKey, expectedKey)) {
                    this.log.trace("{} Current cache storage key '{}' differs from expected key '{}', updating", this.getLogPrefix(), currentKey, expectedKey);
                    this.getPersistentCacheManager().updateKey(currentKey, expectedKey);
                    this.log.trace("{} Successfully updated cache storage key '{}' to '{}'", this.getLogPrefix(), currentKey, expectedKey);
                }
            }
            catch (IOException e) {
                this.log.warn("{} Error updating cache storage key '{}' to '{}'", this.getLogPrefix(), currentKey, expectedKey, e);
            }
        } else {
            this.log.trace("{} EntityDescriptor with entityID '{}' and storaage key '{}' in cache was not valid, skipping and removing", this.getLogPrefix(), descriptor.getEntityID(), currentKey);
            this.persistentCacheInitMetrics.entriesSkippedInvalid++;
            try {
                this.getPersistentCacheManager().remove(currentKey);
            }
            catch (IOException e) {
                this.log.warn("{} Error removing invalid EntityDescriptor '{}' from persistent cache with key '{}'", this.getLogPrefix(), descriptor.getEntityID(), currentKey);
            }
        }
    }

    @Override
    protected void removeByEntityID(String entityID, AbstractMetadataResolver.EntityBackingStore backingStore) {
        List<EntityDescriptor> descriptors;
        if (this.isPersistentCachingEnabled() && (descriptors = backingStore.getIndexedDescriptors().get(entityID)) != null) {
            for (EntityDescriptor descriptor : descriptors) {
                String key = this.getPersistentCacheKeyGenerator().apply(descriptor);
                try {
                    this.getPersistentCacheManager().remove(key);
                }
                catch (IOException e) {
                    this.log.warn("{} Error removing EntityDescriptor '{}' from cache store with key '{}'", this.getLogPrefix(), descriptor.getEntityID(), key);
                }
            }
        }
        super.removeByEntityID(entityID, backingStore);
    }

    @Override
    protected void doDestroy() {
        if (this.cleanupTask != null) {
            this.cleanupTask.cancel();
        }
        if (this.createdOwnTaskTimer) {
            this.taskTimer.cancel();
        }
        this.cleanupTask = null;
        this.taskTimer = null;
        if (this.ratioGaugeFetchToResolve != null) {
            MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), METRIC_RATIOGAUGE_FETCH_TO_RESOLVE), this.ratioGaugeFetchToResolve);
        }
        if (this.gaugeNumLiveEntityIDs != null) {
            MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), METRIC_GAUGE_NUM_LIVE_ENTITYIDS), this.gaugeNumLiveEntityIDs);
        }
        if (this.gaugePersistentCacheInit != null) {
            MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), METRIC_GAUGE_PERSISTENT_CACHE_INIT), this.gaugePersistentCacheInit);
        }
        this.ratioGaugeFetchToResolve = null;
        this.gaugeNumLiveEntityIDs = null;
        this.gaugePersistentCacheInit = null;
        this.timerFetchFromOriginSource = null;
        this.timerResolve = null;
        super.doDestroy();
    }

    public static class PersistentCacheInitializationMetrics {
        private boolean enabled;
        private long processingTime;
        private int entriesTotal;
        private int entriesLoaded;
        private int entriesSkippedAlreadyLive;
        private int entriesSkippedInvalid;
        private int entriesSkippedFailedPredicate;
        private int entriesSkippedProcessingException;

        public boolean isEnabled() {
            return this.enabled;
        }

        public long getProcessingTime() {
            return this.processingTime;
        }

        public int getEntriesTotal() {
            return this.entriesTotal;
        }

        public int getEntriesLoaded() {
            return this.entriesLoaded;
        }

        public int getEntriesSkippedAlreadyLive() {
            return this.entriesSkippedAlreadyLive;
        }

        public int getEntriesSkippedInvalid() {
            return this.entriesSkippedInvalid;
        }

        public int getEntriesSkippedFailedPredicate() {
            return this.entriesSkippedFailedPredicate;
        }

        public int getEntriesSkippedProcessingException() {
            return this.entriesSkippedProcessingException;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("enabled", this.enabled).add("processingTime", this.processingTime).add("entriesTotal", this.entriesTotal).add("entriesLoaded", this.entriesLoaded).add("entriesSkippedAlreadyLive", this.entriesSkippedAlreadyLive).add("entriesSkippedInvalid", this.entriesSkippedInvalid).add("entriesSkippedFailedPredicate", this.entriesSkippedFailedPredicate).add("entriesSkippedProcessingException", this.entriesSkippedProcessingException).toString();
        }
    }

    public static class DefaultCacheKeyGenerator
    implements Function<EntityDescriptor, String> {
        private StringDigester digester;

        public DefaultCacheKeyGenerator() {
            try {
                this.digester = new StringDigester("SHA-1", StringDigester.OutputFormat.HEX_LOWER);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
        }

        @Override
        public String apply(EntityDescriptor input) {
            if (input == null) {
                return null;
            }
            String entityID = StringSupport.trimOrNull(input.getEntityID());
            if (entityID == null) {
                return null;
            }
            return this.digester.apply(entityID);
        }
    }

    protected class BackingStoreCleanupSweeper
    extends TimerTask {
        private final Logger log = LoggerFactory.getLogger(BackingStoreCleanupSweeper.class);

        protected BackingStoreCleanupSweeper() {
        }

        @Override
        public void run() {
            if (AbstractDynamicMetadataResolver.this.isDestroyed() || !AbstractDynamicMetadataResolver.this.isInitialized()) {
                this.log.debug("{} BackingStoreCleanupSweeper will not run because: inited: {}, destroyed: {}", AbstractDynamicMetadataResolver.this.getLogPrefix(), AbstractDynamicMetadataResolver.this.isInitialized(), AbstractDynamicMetadataResolver.this.isDestroyed());
                return;
            }
            this.removeExpiredAndIdleMetadata();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void removeExpiredAndIdleMetadata() {
            DateTime now = new DateTime(ISOChronology.getInstanceUTC());
            DateTime earliestValidLastAccessed = now.minus(AbstractDynamicMetadataResolver.this.getMaxIdleEntityData());
            DynamicEntityBackingStore backingStore = AbstractDynamicMetadataResolver.this.getBackingStore();
            Map<String, List<EntityDescriptor>> indexedDescriptors = backingStore.getIndexedDescriptors();
            for (String entityID : indexedDescriptors.keySet()) {
                EntityManagementData mgmtData = backingStore.getManagementData(entityID);
                Lock writeLock = mgmtData.getReadWriteLock().writeLock();
                try {
                    writeLock.lock();
                    if (!this.isRemoveData(mgmtData, now, earliestValidLastAccessed)) continue;
                    AbstractDynamicMetadataResolver.this.removeByEntityID(entityID, backingStore);
                    backingStore.removeManagementData(entityID);
                }
                finally {
                    writeLock.unlock();
                }
            }
        }

        private boolean isRemoveData(@Nonnull EntityManagementData mgmtData, @Nonnull DateTime now, @Nonnull DateTime earliestValidLastAccessed) {
            if (AbstractDynamicMetadataResolver.this.isRemoveIdleEntityData() && mgmtData.getLastAccessedTime().isBefore(earliestValidLastAccessed)) {
                this.log.debug("{} Entity metadata exceeds maximum idle time, removing: {}", (Object)AbstractDynamicMetadataResolver.this.getLogPrefix(), (Object)mgmtData.getEntityID());
                return true;
            }
            if (now.isAfter(mgmtData.getExpirationTime())) {
                this.log.debug("{} Entity metadata is expired, removing: {}", (Object)AbstractDynamicMetadataResolver.this.getLogPrefix(), (Object)mgmtData.getEntityID());
                return true;
            }
            return false;
        }
    }

    protected class EntityManagementData {
        private String entityID;
        private DateTime lastUpdateTime;
        private DateTime expirationTime;
        private DateTime refreshTriggerTime;
        private DateTime lastAccessedTime;
        private ReadWriteLock readWriteLock;

        protected EntityManagementData(String id) {
            this.entityID = Constraint.isNotNull(id, "Entity ID was null");
            DateTime now = new DateTime(ISOChronology.getInstanceUTC());
            this.expirationTime = now.plus(AbstractDynamicMetadataResolver.this.getMaxCacheDuration());
            this.refreshTriggerTime = now.plus(AbstractDynamicMetadataResolver.this.getMaxCacheDuration());
            this.lastAccessedTime = now;
            this.readWriteLock = new ReentrantReadWriteLock(true);
        }

        @Nonnull
        public String getEntityID() {
            return this.entityID;
        }

        @Nullable
        public DateTime getLastUpdateTime() {
            return this.lastUpdateTime;
        }

        public void setLastUpdateTime(@Nonnull DateTime dateTime) {
            this.lastUpdateTime = dateTime;
        }

        @Nonnull
        public DateTime getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(@Nonnull DateTime dateTime) {
            this.expirationTime = Constraint.isNotNull(dateTime, "Expiration time may not be null");
        }

        @Nonnull
        public DateTime getRefreshTriggerTime() {
            return this.refreshTriggerTime;
        }

        public void setRefreshTriggerTime(@Nonnull DateTime dateTime) {
            this.refreshTriggerTime = Constraint.isNotNull(dateTime, "Refresh trigger time may not be null");
        }

        @Nonnull
        public DateTime getLastAccessedTime() {
            return this.lastAccessedTime;
        }

        public void recordEntityAccess() {
            this.lastAccessedTime = new DateTime(ISOChronology.getInstanceUTC());
        }

        @Nonnull
        public ReadWriteLock getReadWriteLock() {
            return this.readWriteLock;
        }
    }

    protected class DynamicEntityBackingStore
    extends AbstractMetadataResolver.EntityBackingStore {
        private Map<String, EntityManagementData> mgmtDataMap;

        protected DynamicEntityBackingStore() {
            super(AbstractDynamicMetadataResolver.this);
            this.mgmtDataMap = new ConcurrentHashMap<String, EntityManagementData>();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nonnull
        public EntityManagementData getManagementData(@Nonnull String entityID) {
            Constraint.isNotNull(entityID, "EntityID may not be null");
            EntityManagementData entityData = this.mgmtDataMap.get(entityID);
            if (entityData != null) {
                return entityData;
            }
            DynamicEntityBackingStore dynamicEntityBackingStore = this;
            synchronized (dynamicEntityBackingStore) {
                entityData = this.mgmtDataMap.get(entityID);
                if (entityData != null) {
                    return entityData;
                }
                entityData = new EntityManagementData(entityID);
                this.mgmtDataMap.put(entityID, entityData);
                return entityData;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void removeManagementData(@Nonnull String entityID) {
            Constraint.isNotNull(entityID, "EntityID may not be null");
            DynamicEntityBackingStore dynamicEntityBackingStore = this;
            synchronized (dynamicEntityBackingStore) {
                this.mgmtDataMap.remove(entityID);
            }
        }
    }
}

