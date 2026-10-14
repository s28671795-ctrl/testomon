/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.saml.metadata.resolver.impl.AbstractReloadingMetadataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesystemMetadataResolver
extends AbstractReloadingMetadataResolver {
    private final Logger log = LoggerFactory.getLogger(FilesystemMetadataResolver.class);
    @Nonnull
    private File metadataFile;

    public FilesystemMetadataResolver(@Nonnull File metadata) throws ResolverException {
        this.setMetadataFile(metadata);
    }

    public FilesystemMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull File metadata) throws ResolverException {
        super(backgroundTaskTimer);
        this.setMetadataFile(metadata);
    }

    protected void setMetadataFile(@Nonnull File file) throws ResolverException {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.metadataFile = Constraint.isNotNull(file, "Metadata file cannot be null");
    }

    @Override
    protected void doDestroy() {
        this.metadataFile = null;
        super.doDestroy();
    }

    @Override
    protected String getMetadataIdentifier() {
        return this.metadataFile.getAbsolutePath();
    }

    @Override
    protected byte[] fetchMetadata() throws ResolverException {
        try {
            this.validateMetadataFile(this.metadataFile);
            DateTime metadataUpdateTime = new DateTime(this.metadataFile.lastModified(), (Chronology)ISOChronology.getInstanceUTC());
            if (this.getLastRefresh() == null || this.getLastUpdate() == null || metadataUpdateTime.isAfter(this.getLastUpdate())) {
                return this.inputstreamToByteArray(new FileInputStream(this.metadataFile));
            }
            return null;
        }
        catch (IOException e) {
            String errMsg = "Unable to read metadata file " + this.metadataFile.getAbsolutePath();
            this.log.error("{} " + errMsg, (Object)this.getLogPrefix(), (Object)e);
            throw new ResolverException(errMsg, e);
        }
    }

    protected void validateMetadataFile(@Nonnull File file) throws ResolverException {
        if (!file.exists()) {
            throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' does not exist");
        }
        if (!file.isFile()) {
            throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' is not a file");
        }
        if (!file.canRead()) {
            throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' is not readable");
        }
    }
}

