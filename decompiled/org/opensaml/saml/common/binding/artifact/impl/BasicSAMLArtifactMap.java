/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.artifact.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.artifact.ExpiringSAMLArtifactMapEntry;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.binding.artifact.impl.ExpiringSAMLArtifactMapEntryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSAMLArtifactMap
extends AbstractInitializableComponent
implements SAMLArtifactMap {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BasicSAMLArtifactMap.class);
    @NonnullAfterInit
    private Map<String, ExpiringSAMLArtifactMapEntry> artifactStore;
    @Duration
    @Positive
    private long artifactLifetime = 60000L;
    @Nonnull
    private SAMLArtifactMap.SAMLArtifactMapEntryFactory entryFactory = new ExpiringSAMLArtifactMapEntryFactory();
    @Duration
    @NonNegative
    private long cleanupInterval = 300L;
    @NonnullAfterInit
    private Timer cleanupTaskTimer;
    @Nullable
    private TimerTask cleanupTask;

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        this.artifactStore = new ConcurrentHashMap<String, ExpiringSAMLArtifactMapEntry>();
        if (this.cleanupInterval > 0L) {
            this.cleanupTask = new Cleanup();
            this.cleanupTaskTimer = new Timer(true);
            this.cleanupTaskTimer.schedule(this.cleanupTask, this.cleanupInterval * 1000L, this.cleanupInterval * 1000L);
        }
    }

    @Override
    protected void doDestroy() {
        if (this.cleanupTask != null) {
            this.cleanupTask.cancel();
            this.cleanupTask = null;
            this.cleanupTaskTimer = null;
        }
        this.artifactStore = null;
        super.doDestroy();
    }

    @Positive
    public long getArtifactLifetime() {
        return this.artifactLifetime;
    }

    @Nonnull
    public SAMLArtifactMap.SAMLArtifactMapEntryFactory getEntryFactory() {
        return this.entryFactory;
    }

    @Duration
    public void setArtifactLifetime(@Duration @Positive long lifetime) {
        this.artifactLifetime = Constraint.isGreaterThan(0L, lifetime, "Artifact lifetime must be greater than zero");
    }

    @Duration
    public void setCleanupInterval(@Duration @NonNegative long interval) {
        this.cleanupInterval = Constraint.isGreaterThanOrEqual(0L, interval, "Cleanup interval must be non-negative");
    }

    public void setEntryFactory(@Nonnull SAMLArtifactMap.SAMLArtifactMapEntryFactory factory2) {
        this.entryFactory = Constraint.isNotNull(factory2, "SAMLArtifactMapEntryFactory cannot be null");
    }

    @Override
    public boolean contains(@Nonnull @NotEmpty String artifact) throws IOException {
        return this.artifactStore.containsKey(artifact);
    }

    @Override
    @Nullable
    public SAMLArtifactMap.SAMLArtifactMapEntry get(@Nonnull @NotEmpty String artifact) throws IOException {
        this.log.debug("Attempting to retrieve entry for artifact: {}", (Object)artifact);
        ExpiringSAMLArtifactMapEntry entry = this.artifactStore.get(artifact);
        if (entry == null) {
            this.log.debug("No entry found for artifact: {}", (Object)artifact);
            return null;
        }
        if (!entry.isValid()) {
            this.log.debug("Entry for artifact was expired: {}", (Object)artifact);
            this.remove(artifact);
            return null;
        }
        this.log.debug("Found valid entry for artifact: {}", (Object)artifact);
        return entry;
    }

    @Override
    public void put(@Nonnull @NotEmpty String artifact, @Nonnull @NotEmpty String relyingPartyId, @Nonnull @NotEmpty String issuerId, @Nonnull SAMLObject samlMessage) throws IOException {
        ExpiringSAMLArtifactMapEntry artifactEntry = (ExpiringSAMLArtifactMapEntry)this.entryFactory.newEntry(artifact, issuerId, relyingPartyId, samlMessage);
        artifactEntry.setExpiration(System.currentTimeMillis() + this.getArtifactLifetime());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Storing new artifact entry '{}' for relying party '{}', expiring at '{}'", artifact, relyingPartyId, new DateTime(artifactEntry.getExpiration()));
        }
        this.artifactStore.put(artifact, artifactEntry);
    }

    @Override
    public void remove(@Nonnull @NotEmpty String artifact) throws IOException {
        this.log.debug("Removing artifact entry: {}", (Object)artifact);
        this.artifactStore.remove(artifact);
    }

    protected class Cleanup
    extends TimerTask {
        protected Cleanup() {
        }

        @Override
        public void run() {
            BasicSAMLArtifactMap.this.log.info("Running cleanup task");
            Long now = System.currentTimeMillis();
            Iterator i = BasicSAMLArtifactMap.this.artifactStore.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry entry = i.next();
                if (((ExpiringSAMLArtifactMapEntry)entry.getValue()).isValid(now)) continue;
                i.remove();
            }
        }
    }
}

