/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.license.LicenseProperties;
import com.incoresoft.plugin.commons.localization.LocalizationCache;
import com.incoresoft.vezha.audit.VezhaAuditEventActionsManager;
import com.incoresoft.vezha.backup.BackupNamesManager;
import com.incoresoft.vezha.backup.BackupsCreationManager;
import com.incoresoft.vezha.backup.BackupsManager;
import com.incoresoft.vezha.backup.BackupsRestoreManager;
import com.incoresoft.vezha.backup.file.BackupFilesHashManager;
import com.incoresoft.vezha.backup.file.BackupFilesManager;
import com.incoresoft.vezha.backup.file.BackupFilesValidator;
import com.incoresoft.vezha.backup.ws.BackupRestoreProcessesRegistry;
import com.incoresoft.vezha.eventmanager.EventManager;
import com.incoresoft.vezha.frames.FramesManager;
import com.incoresoft.vezha.license.VezhaLicenseProperties;
import com.incoresoft.vezha.log.LogFilesManager;
import com.incoresoft.vezha.metrics.MetricsManager;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetricsManager;
import com.incoresoft.vezha.metrics.client.MetricsClient;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetricsManager;
import com.incoresoft.vezha.models.servers.ServerManager;
import com.incoresoft.vezha.models.servers.ServersStateWsBroadcaster;
import com.incoresoft.vezha.models.settings.CustomSettingsManager;
import com.incoresoft.vezha.models.streams.groups.StreamGroupManager;
import com.incoresoft.vezha.storage.S3StorageCleaner;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class ManagersModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(StreamGroupManager.class).asEagerSingleton();
        this.bind(CustomSettingsManager.class).asEagerSingleton();
        this.bind(EventManager.class).asEagerSingleton();
        this.bind(ServerManager.class).asEagerSingleton();
        this.bind(ServersStateWsBroadcaster.class).asEagerSingleton();
        this.bind(BackupsManager.class).asEagerSingleton();
        this.bind(BackupsCreationManager.class).asEagerSingleton();
        this.bind(BackupFilesHashManager.class).asEagerSingleton();
        this.bind(BackupsRestoreManager.class).asEagerSingleton();
        this.bind(BackupRestoreProcessesRegistry.class).asEagerSingleton();
        this.bind(BackupFilesValidator.class).asEagerSingleton();
        this.bind(BackupFilesManager.class).asEagerSingleton();
        this.bind(BackupNamesManager.class).asEagerSingleton();
        this.bind(LicenseProperties.class).to(VezhaLicenseProperties.class).asEagerSingleton();
        this.bind(AuditEventActionsManager.class).to(VezhaAuditEventActionsManager.class).asEagerSingleton();
        this.bind(MetricsClient.class).asEagerSingleton();
        this.bind(LogFilesManager.class).asEagerSingleton();
        this.bind(SnapshotMetricsManager.class).asEagerSingleton();
        this.bind(AggregatedMetricsManager.class).asEagerSingleton();
        this.bind(MetricsManager.class).asEagerSingleton();
        this.bind(FramesManager.class).asEagerSingleton();
        this.bind(S3StorageCleaner.class).asEagerSingleton();
    }

    @Singleton
    @Provides
    public LocalizationCache getLocalizationCache() {
        return new LocalizationCache(this.getClass().getClassLoader(), "core");
    }

    @Singleton
    @Provides
    public HttpClient httpClient() {
        return HttpClients.createDefault();
    }
}

