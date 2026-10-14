/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.metadata.resolver.RefreshableMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.impl.AbstractBatchMetadataResolver;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public abstract class AbstractReloadingMetadataResolver
extends AbstractBatchMetadataResolver
implements RefreshableMetadataResolver {
    private final Logger log = LoggerFactory.getLogger(AbstractReloadingMetadataResolver.class);
    private Timer taskTimer;
    private boolean createdOwnTaskTimer;
    private RefreshMetadataTask refreshMetadataTask;
    private float refreshDelayFactor = 0.75f;
    @Duration
    @Positive
    private long maxRefreshDelay = 14400000L;
    @Duration
    @Positive
    private long minRefreshDelay = 300000L;
    private DateTime expirationTime;
    private DateTime lastUpdate;
    private DateTime lastRefresh;
    private DateTime nextRefresh;

    protected AbstractReloadingMetadataResolver() {
        this(null);
    }

    protected AbstractReloadingMetadataResolver(@Nullable Timer backgroundTaskTimer) {
        this.setCacheSourceMetadata(true);
        if (backgroundTaskTimer == null) {
            this.taskTimer = new Timer(true);
            this.createdOwnTaskTimer = true;
        } else {
            this.taskTimer = backgroundTaskTimer;
        }
    }

    @Override
    protected void setCacheSourceMetadata(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (!flag) {
            this.log.warn("{} Caching of source metadata may not be disabled for reloading metadata resolvers", (Object)this.getLogPrefix());
        } else {
            super.setCacheSourceMetadata(flag);
        }
    }

    public DateTime getExpirationTime() {
        return this.expirationTime;
    }

    @Override
    @Nullable
    public DateTime getLastUpdate() {
        return this.lastUpdate;
    }

    @Override
    @Nullable
    public DateTime getLastRefresh() {
        return this.lastRefresh;
    }

    public DateTime getNextRefresh() {
        return this.nextRefresh;
    }

    @Duration
    public long getMaxRefreshDelay() {
        return this.maxRefreshDelay;
    }

    @Duration
    public void setMaxRefreshDelay(@Duration @Positive long delay) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (delay < 0L) {
            throw new IllegalArgumentException("Maximum refresh delay must be greater than 0");
        }
        this.maxRefreshDelay = delay;
    }

    public float getRefreshDelayFactor() {
        return this.refreshDelayFactor;
    }

    public void setRefreshDelayFactor(float factor) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (factor <= 0.0f || factor >= 1.0f) {
            throw new IllegalArgumentException("Refresh delay factor must be a number between 0.0 and 1.0, exclusive");
        }
        this.refreshDelayFactor = factor;
    }

    @Duration
    public long getMinRefreshDelay() {
        return this.minRefreshDelay;
    }

    @Duration
    public void setMinRefreshDelay(@Duration @Positive long delay) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (delay < 0L) {
            throw new IllegalArgumentException("Minimum refresh delay must be greater than 0");
        }
        this.minRefreshDelay = delay;
    }

    @Override
    protected void doDestroy() {
        this.refreshMetadataTask.cancel();
        if (this.createdOwnTaskTimer) {
            this.taskTimer.cancel();
        }
        this.expirationTime = null;
        this.lastRefresh = null;
        this.lastUpdate = null;
        this.nextRefresh = null;
        super.doDestroy();
    }

    @Override
    protected void initMetadataResolver() throws ComponentInitializationException {
        super.initMetadataResolver();
        try {
            this.refresh();
        }
        catch (ResolverException e) {
            throw new ComponentInitializationException("Error refreshing metadata during init", e);
        }
        if (this.minRefreshDelay > this.maxRefreshDelay) {
            throw new ComponentInitializationException("Minimum refresh delay " + this.minRefreshDelay + " is greater than maximum refresh delay " + this.maxRefreshDelay);
        }
    }

    @Override
    public synchronized void refresh() throws ResolverException {
        String mdId;
        DateTime now;
        block9: {
            now = null;
            mdId = null;
            if (!this.isDestroyed()) break block9;
            this.logCachedMetadataExpiration();
            this.refreshMetadataTask = new RefreshMetadataTask();
            long nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
            this.taskTimer.schedule((TimerTask)this.refreshMetadataTask, nextRefreshDelay);
            this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault()));
            this.lastRefresh = now;
            return;
        }
        try {
            if (this.refreshMetadataTask != null) {
                this.refreshMetadataTask.cancel();
            }
            now = new DateTime(ISOChronology.getInstanceUTC());
            mdId = this.getMetadataIdentifier();
            this.log.debug("{} Beginning refresh of metadata from '{}'", (Object)this.getLogPrefix(), (Object)mdId);
            byte[] mdBytes = this.fetchMetadata();
            if (mdBytes == null) {
                this.log.info("{} Metadata from '{}' has not changed since last refresh", (Object)this.getLogPrefix(), (Object)mdId);
                this.processCachedMetadata(mdId, now);
            } else {
                this.log.debug("{} Processing new metadata from '{}'", (Object)this.getLogPrefix(), (Object)mdId);
                this.processNewMetadata(mdId, now, mdBytes);
            }
            this.logCachedMetadataExpiration();
            this.refreshMetadataTask = new RefreshMetadataTask();
        }
        catch (Throwable t2) {
            try {
                this.log.error("{} Error occurred while attempting to refresh metadata from '{}'", this.getLogPrefix(), mdId, t2);
                this.nextRefresh = new DateTime(ISOChronology.getInstanceUTC()).plus(this.minRefreshDelay);
                if (t2 instanceof Exception) {
                    throw new ResolverException((Exception)t2);
                }
                throw new ResolverException(String.format("Saw an error of type '%s' with message '%s'", t2.getClass().getName(), t2.getMessage()));
            }
            catch (Throwable throwable) {
                this.logCachedMetadataExpiration();
                this.refreshMetadataTask = new RefreshMetadataTask();
                long nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
                this.taskTimer.schedule((TimerTask)this.refreshMetadataTask, nextRefreshDelay);
                this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault()));
                this.lastRefresh = now;
                throw throwable;
            }
        }
        long nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
        this.taskTimer.schedule((TimerTask)this.refreshMetadataTask, nextRefreshDelay);
        this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault()));
        this.lastRefresh = now;
    }

    private void logCachedMetadataExpiration() {
        String mdId = this.getMetadataIdentifier();
        XMLObject cached = this.getBackingStore().getCachedOriginalMetadata();
        if (cached != null && !this.isValid(cached)) {
            this.log.warn("{} Metadata root from '{}' currently live (post-refresh) is expired or otherwise invalid", (Object)this.getLogPrefix(), (Object)mdId);
        } else if (cached instanceof TimeBoundSAMLObject) {
            TimeBoundSAMLObject timebound = (TimeBoundSAMLObject)cached;
            if (this.isRequireValidMetadata() && timebound.getValidUntil() != null && timebound.getValidUntil().isBefore(this.nextRefresh)) {
                this.log.warn("{} Metadata root from '{}' currently live (post-refresh) will expire at '{}' before the next refresh scheduled for {}'", this.getLogPrefix(), mdId, timebound.getValidUntil(), this.nextRefresh);
            }
        }
    }

    protected abstract String getMetadataIdentifier();

    protected abstract byte[] fetchMetadata() throws ResolverException;

    protected XMLObject unmarshallMetadata(byte[] metadataBytes) throws ResolverException {
        try {
            return this.unmarshallMetadata(new ByteArrayInputStream(metadataBytes));
        }
        catch (UnmarshallingException e) {
            String errorMsg = "Unable to unmarshall metadata";
            this.log.error("{} Unable to unmarshall metadata", (Object)this.getLogPrefix(), (Object)e);
            throw new ResolverException("Unable to unmarshall metadata", e);
        }
    }

    protected void processCachedMetadata(String metadataIdentifier, DateTime refreshStart) throws ResolverException {
        DateTime metadataExpirationTime;
        this.log.debug("{} Computing new expiration time for cached metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        this.expirationTime = metadataExpirationTime = SAML2Support.getEarliestExpiration(this.getBackingStore().getCachedOriginalMetadata(), refreshStart.plus(this.getMaxRefreshDelay()), refreshStart);
        long nextRefreshDelay = this.computeNextRefreshDelay(this.expirationTime);
        this.nextRefresh = new DateTime(ISOChronology.getInstanceUTC()).plus(nextRefreshDelay);
    }

    protected void processNewMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes) throws ResolverException {
        this.log.debug("{} Unmarshalling metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        XMLObject metadata = this.unmarshallMetadata(metadataBytes);
        if (!this.isValid(metadata)) {
            this.processPreExpiredMetadata(metadataIdentifier, refreshStart, metadataBytes, metadata);
        } else {
            this.processNonExpiredMetadata(metadataIdentifier, refreshStart, metadataBytes, metadata);
        }
    }

    protected void processPreExpiredMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes, XMLObject metadata) {
        this.log.warn("{} Entire metadata document from '{}' was expired at time of loading, existing metadata retained", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        this.nextRefresh = new DateTime(ISOChronology.getInstanceUTC()).plus(this.getMinRefreshDelay());
    }

    protected void processNonExpiredMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes, XMLObject metadata) throws ResolverException {
        long nextRefreshDelay;
        Document metadataDom = metadata.getDOM().getOwnerDocument();
        this.log.debug("{} Preprocessing metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        AbstractBatchMetadataResolver.BatchEntityBackingStore newBackingStore = null;
        try {
            newBackingStore = this.preProcessNewMetadata(metadata);
        }
        catch (FilterException e) {
            String errMsg = "Error filtering metadata from " + metadataIdentifier;
            this.log.error("{} " + errMsg, (Object)this.getLogPrefix(), (Object)e);
            throw new ResolverException(errMsg, e);
        }
        this.log.debug("{} Releasing cached DOM for metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        this.releaseMetadataDOM(newBackingStore.getCachedOriginalMetadata());
        this.releaseMetadataDOM(newBackingStore.getCachedFilteredMetadata());
        this.log.debug("{} Post-processing metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        this.postProcessMetadata(metadataBytes, metadataDom, newBackingStore.getCachedOriginalMetadata(), newBackingStore.getCachedFilteredMetadata());
        this.log.debug("{} Computing expiration time for metadata from '{}'", (Object)this.getLogPrefix(), (Object)metadataIdentifier);
        DateTime metadataExpirationTime = SAML2Support.getEarliestExpiration(newBackingStore.getCachedOriginalMetadata(), refreshStart.plus(this.getMaxRefreshDelay()), refreshStart);
        this.log.debug("{} Expiration of metadata from '{}' will occur at {}", this.getLogPrefix(), metadataIdentifier, metadataExpirationTime.toString());
        this.setBackingStore(newBackingStore);
        this.lastUpdate = refreshStart;
        if (metadataExpirationTime.isBeforeNow()) {
            this.expirationTime = new DateTime(ISOChronology.getInstanceUTC()).plus(this.getMinRefreshDelay());
            nextRefreshDelay = this.getMaxRefreshDelay();
        } else {
            this.expirationTime = metadataExpirationTime;
            nextRefreshDelay = this.computeNextRefreshDelay(this.expirationTime);
        }
        this.nextRefresh = new DateTime(ISOChronology.getInstanceUTC()).plus(nextRefreshDelay);
        this.log.info("{} New metadata successfully loaded for '{}'", (Object)this.getLogPrefix(), (Object)this.getMetadataIdentifier());
    }

    protected void postProcessMetadata(byte[] metadataBytes, Document metadataDom, XMLObject originalMetadata, XMLObject filteredMetadata) throws ResolverException {
    }

    protected long computeNextRefreshDelay(DateTime expectedExpiration) {
        long refreshDelay;
        long now = new DateTime(ISOChronology.getInstanceUTC()).getMillis();
        long expireInstant = 0L;
        if (expectedExpiration != null) {
            expireInstant = expectedExpiration.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
        }
        if ((refreshDelay = (long)((float)(expireInstant - now) * this.getRefreshDelayFactor())) < this.getMinRefreshDelay()) {
            refreshDelay = this.getMinRefreshDelay();
        }
        return refreshDelay;
    }

    protected byte[] inputstreamToByteArray(InputStream ins) throws ResolverException {
        try {
            byte[] buffer = new byte[0x100000];
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int n = 0;
            while (-1 != (n = ins.read(buffer))) {
                output.write(buffer, 0, n);
            }
            ins.close();
            return output.toByteArray();
        }
        catch (IOException e) {
            throw new ResolverException(e);
        }
    }

    private class RefreshMetadataTask
    extends TimerTask {
        private RefreshMetadataTask() {
        }

        @Override
        public void run() {
            try {
                if (AbstractReloadingMetadataResolver.this.isDestroyed()) {
                    return;
                }
                AbstractReloadingMetadataResolver.this.refresh();
            }
            catch (ResolverException e) {
                return;
            }
        }
    }
}

