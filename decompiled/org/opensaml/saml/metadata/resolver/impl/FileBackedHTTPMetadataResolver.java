/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.client.HttpClient;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.impl.HTTPMetadataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class FileBackedHTTPMetadataResolver
extends HTTPMetadataResolver {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(FileBackedHTTPMetadataResolver.class);
    @Nullable
    private File metadataBackupFile;
    private boolean initializing;
    private boolean initializeFromBackupFile = true;
    private boolean initializedFromBackupFile;
    @Duration
    @Positive
    private long backupFileInitNextRefreshDelay = 5000L;

    public FileBackedHTTPMetadataResolver(HttpClient client, String metadataURL, String backupFilePath) throws ResolverException {
        this(null, client, metadataURL, backupFilePath);
    }

    public FileBackedHTTPMetadataResolver(Timer backgroundTaskTimer, HttpClient client, String metadataURL, String backupFilePath) throws ResolverException {
        super(backgroundTaskTimer, client, metadataURL);
        this.setBackupFile(backupFilePath);
    }

    public boolean isInitializedFromBackupFile() {
        return this.initializedFromBackupFile;
    }

    public boolean isInitializeFromBackupFile() {
        return this.initializeFromBackupFile;
    }

    public void setInitializeFromBackupFile(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.initializeFromBackupFile = flag;
    }

    public long getBackupFileInitNextRefreshDelay() {
        return this.backupFileInitNextRefreshDelay;
    }

    public void setBackupFileInitNextRefreshDelay(long delay) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (delay < 0L) {
            throw new IllegalArgumentException("Backup file init next refresh delay must be greater than 0");
        }
        this.backupFileInitNextRefreshDelay = delay;
    }

    @Override
    protected void doDestroy() {
        this.metadataBackupFile = null;
        super.doDestroy();
    }

    @Override
    protected void initMetadataResolver() throws ComponentInitializationException {
        try {
            this.validateBackupFile(this.metadataBackupFile);
        }
        catch (ResolverException e) {
            if (this.isFailFastInitialization()) {
                this.log.error("{} Metadata backup file path was invalid, initialization is fatal", (Object)this.getLogPrefix());
                throw new ComponentInitializationException("Metadata backup file path was invalid", e);
            }
            this.log.error("{} Metadata backup file path was invalid, continuing without known good backup file", (Object)this.getLogPrefix());
        }
        try {
            this.initializing = true;
            super.initMetadataResolver();
        }
        finally {
            this.initializing = false;
        }
    }

    protected void setBackupFile(String backupFilePath) throws ResolverException {
        File backingFile;
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.metadataBackupFile = backingFile = new File(backupFilePath);
    }

    protected void validateBackupFile(File backupFile) throws ResolverException {
        if (!backupFile.exists()) {
            try {
                this.log.debug("{} Testing creation of backup file", (Object)this.getLogPrefix());
                backupFile.createNewFile();
            }
            catch (IOException e) {
                String msg = "Unable to create backup file " + backupFile.getAbsolutePath();
                this.log.error("{} " + msg, (Object)this.getLogPrefix(), (Object)e);
                throw new ResolverException(msg, e);
            }
            finally {
                boolean deleted;
                if (backupFile.exists() && !(deleted = backupFile.delete())) {
                    this.log.debug("{} Deletion of test backup file failed", (Object)this.getLogPrefix());
                }
            }
        }
        if (backupFile.exists()) {
            if (backupFile.isDirectory()) {
                throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " is a directory and may not be used as a backup metadata file");
            }
            if (!backupFile.canRead()) {
                throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " exists but can not be read by this user");
            }
            if (!backupFile.canWrite()) {
                throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " exists but can not be written to by this user");
            }
        }
    }

    @Override
    protected byte[] fetchMetadata() throws ResolverException {
        if (this.initializing && this.initializeFromBackupFile && this.metadataBackupFile.exists()) {
            this.log.debug("{} On initialization, detected existing backup file, attempting load from that: {}", (Object)this.getLogPrefix(), (Object)this.metadataBackupFile.getAbsolutePath());
            try {
                byte[] backingData = Files.toByteArray(this.metadataBackupFile);
                this.log.debug("{} Successfully initialized from backup file: {}", (Object)this.getLogPrefix(), (Object)this.metadataBackupFile.getAbsolutePath());
                this.initializedFromBackupFile = true;
                return backingData;
            }
            catch (IOException e) {
                this.log.warn("{} Error initializing from backup file, continuing with normal HTTP fetch", (Object)this.getLogPrefix(), (Object)e);
            }
        }
        try {
            return super.fetchMetadata();
        }
        catch (ResolverException e) {
            if (this.getCachedOriginalMetadata() != null) {
                this.log.warn("{} Problem reading metadata from remote source; detected existing cached metadata, skipping load of backup file", (Object)this.getLogPrefix());
                return null;
            }
            if (this.metadataBackupFile.exists()) {
                this.log.warn("{} Problem reading metadata from remote source, processing existing backup file: {}", (Object)this.getLogPrefix(), (Object)this.metadataBackupFile.getAbsolutePath());
                try {
                    return Files.toByteArray(this.metadataBackupFile);
                }
                catch (IOException ioe) {
                    String errMsg = "Unable to retrieve metadata from backup file " + this.metadataBackupFile.getAbsolutePath();
                    this.log.error("{} " + errMsg, (Object)this.getLogPrefix(), (Object)ioe);
                    throw new ResolverException(errMsg, ioe);
                }
            }
            this.log.error("{} Unable to read metadata from remote server and backup does not exist", (Object)this.getLogPrefix());
            throw new ResolverException("Unable to read metadata from remote server and backup does not exist");
        }
    }

    @Override
    protected long computeNextRefreshDelay(DateTime expectedExpiration) {
        if (this.initializing && this.initializedFromBackupFile) {
            this.log.debug("{} Detected initialization from backup file, scheduling next refresh from HTTP in {}ms", (Object)this.getLogPrefix(), (Object)this.getBackupFileInitNextRefreshDelay());
            return this.getBackupFileInitNextRefreshDelay();
        }
        return super.computeNextRefreshDelay(expectedExpiration);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void postProcessMetadata(byte[] metadataBytes, Document metadataDom, XMLObject originalMetadata, XMLObject filteredMetadata) throws ResolverException {
        try {
            this.validateBackupFile(this.metadataBackupFile);
            try (FileOutputStream out = new FileOutputStream(this.metadataBackupFile);){
                out.write(metadataBytes);
                out.flush();
            }
        }
        catch (ResolverException e) {
            this.log.error("{} Unable to write metadata to backup file: {}", this.getLogPrefix(), this.metadataBackupFile.getAbsoluteFile(), e);
        }
        catch (IOException e) {
            this.log.error("{} Unable to write metadata to backup file: {}", this.getLogPrefix(), this.metadataBackupFile.getAbsoluteFile(), e);
        }
        finally {
            super.postProcessMetadata(metadataBytes, metadataDom, originalMetadata, filteredMetadata);
        }
    }
}

