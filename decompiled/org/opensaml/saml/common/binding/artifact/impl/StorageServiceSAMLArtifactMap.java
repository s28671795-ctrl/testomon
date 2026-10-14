/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.storage.StorageRecord
 *  org.opensaml.storage.StorageSerializer
 *  org.opensaml.storage.StorageService
 */
package org.opensaml.saml.common.binding.artifact.impl;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.binding.artifact.impl.StorageServiceSAMLArtifactMapEntryFactory;
import org.opensaml.storage.StorageRecord;
import org.opensaml.storage.StorageSerializer;
import org.opensaml.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageServiceSAMLArtifactMap
extends AbstractInitializableComponent
implements SAMLArtifactMap {
    @Nonnull
    @NotEmpty
    public static final String STORAGE_CONTEXT = StorageServiceSAMLArtifactMap.class.getName();
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(StorageServiceSAMLArtifactMap.class);
    @NonnullAfterInit
    private StorageService artifactStore;
    private int artifactStoreKeySize;
    @Duration
    @Positive
    private long artifactLifetime = 60000L;
    @Nonnull
    private SAMLArtifactMap.SAMLArtifactMapEntryFactory entryFactory = new StorageServiceSAMLArtifactMapEntryFactory();

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.artifactStore == null) {
            throw new ComponentInitializationException("StorageService cannot be null");
        }
        this.artifactStoreKeySize = this.getStorageService().getCapabilities().getKeySize();
    }

    @NonnullAfterInit
    public StorageService getStorageService() {
        return this.artifactStore;
    }

    @Duration
    @Positive
    public long getArtifactLifetime() {
        return this.artifactLifetime;
    }

    @Nonnull
    public SAMLArtifactMap.SAMLArtifactMapEntryFactory getEntryFactory() {
        return this.entryFactory;
    }

    public void setStorageService(@Nonnull StorageService store) {
        this.artifactStore = Constraint.isNotNull(store, "StorageService cannot be null");
    }

    @Duration
    public void setArtifactLifetime(@Duration @Positive long lifetime) {
        this.artifactLifetime = Constraint.isGreaterThan(0L, lifetime, "Artifact lifetime must be greater than zero");
    }

    public void setEntryFactory(@Nonnull SAMLArtifactMap.SAMLArtifactMapEntryFactory factory2) {
        Constraint.isTrue(factory2 != null && factory2 instanceof StorageSerializer, "SAMLArtifactMapEntryFactory cannot be null and must support the StorageSerializer interface");
        this.entryFactory = factory2;
    }

    @Override
    public boolean contains(@Nonnull @NotEmpty String artifact) throws IOException {
        if (artifact.length() > this.artifactStoreKeySize) {
            throw new IOException("Length of artifact (" + artifact.length() + ") exceeds storage capabilities");
        }
        return this.getStorageService().read(STORAGE_CONTEXT, artifact) != null;
    }

    @Override
    @Nullable
    public SAMLArtifactMap.SAMLArtifactMapEntry get(@Nonnull @NotEmpty String artifact) throws IOException {
        this.log.debug("Attempting to retrieve entry for artifact: {}", (Object)artifact);
        if (artifact.length() > this.artifactStoreKeySize) {
            throw new IOException("Length of artifact (" + artifact.length() + ") exceeds storage capabilities");
        }
        StorageRecord record = this.getStorageService().read(STORAGE_CONTEXT, artifact);
        if (record == null) {
            this.log.debug("No unexpired entry found for artifact: {}", (Object)artifact);
            return null;
        }
        this.log.debug("Found valid entry for artifact: {}", (Object)artifact);
        return (SAMLArtifactMap.SAMLArtifactMapEntry)record.getValue((StorageSerializer)this.getEntryFactory(), STORAGE_CONTEXT, artifact);
    }

    @Override
    public void put(@Nonnull @NotEmpty String artifact, @Nonnull @NotEmpty String relyingPartyId, @Nonnull @NotEmpty String issuerId, @Nonnull SAMLObject samlMessage) throws IOException {
        boolean success;
        if (artifact.length() > this.artifactStoreKeySize) {
            throw new IOException("Length of artifact (" + artifact.length() + ") exceeds storage capabilities");
        }
        SAMLArtifactMap.SAMLArtifactMapEntry artifactEntry = this.getEntryFactory().newEntry(artifact, issuerId, relyingPartyId, samlMessage);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Storing new artifact entry '{}' for relying party '{}', expiring after {} seconds", artifact, relyingPartyId, this.getArtifactLifetime() / 1000L);
        }
        if (!(success = this.getStorageService().create(STORAGE_CONTEXT, artifact, (Object)artifactEntry, (StorageSerializer)this.getEntryFactory(), Long.valueOf(System.currentTimeMillis() + this.getArtifactLifetime())))) {
            throw new IOException("A duplicate artifact was generated");
        }
    }

    @Override
    public void remove(@Nonnull @NotEmpty String artifact) throws IOException {
        this.log.debug("Removing artifact entry: {}", (Object)artifact);
        if (artifact.length() > this.artifactStoreKeySize) {
            throw new IOException("Length of artifact (" + artifact.length() + ") exceeds storage capabilities");
        }
        this.getStorageService().delete(STORAGE_CONTEXT, artifact);
    }
}

