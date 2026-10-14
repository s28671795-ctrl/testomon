/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.backup.database.BackupDatabaseManager;
import com.incoresoft.backup.database.availability.BackupUnavailabilityReason;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.http.server.JavalinAvailabilityInterceptor;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.plugins.configurations.PluginConfiguration;
import com.incoresoft.plugins.configurations.PluginConfigurationsManager;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.utils.ConcurrencyUtils;
import com.incoresoft.vezha.backup.BackupCreationStage;
import com.incoresoft.vezha.backup.exceptions.BackupFileCreationException;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.file.BackupFilesHashManager;
import com.incoresoft.vezha.backup.file.BackupFilesManager;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupsCreationManager {
    private static final Logger log = LoggerFactory.getLogger(BackupsCreationManager.class);
    private final BackupFilesManager backupFilesManager;
    private final BackupFilesHashManager hashManager;
    private final BackupDatabaseManager backupDatabaseManager;
    private final EventManagerPluginsManagerFacade eventManagerPluginsFacade;
    private final AnalyticsPluginsManagerFacade analyticsPluginsFacade;
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final JavalinAvailabilityInterceptor javalinAvailabilityInterceptor;
    private final PluginConfigurationsManager pluginConfigurationsManager;
    private final VezhaConfigManager configManager;
    private final CoreService coreService;

    public synchronized BackupFileInfo createBackupFile(CreateBackupMessage createBackupMessage, List<BackupCreationListener> listeners) {
        String backupName = createBackupMessage.getName();
        this.javalinAvailabilityInterceptor.setUnavailable(BackupUnavailabilityReason.BACKUP_CREATION);
        try {
            ListenersUtils.triggerListenersInParallel(l -> l.onBackupCreationProgress(BackupCreationStage.FILES_COPYING), listeners);
            ConcurrencyUtils.ensureNotInterrupted();
            this.backupFilesManager.copyFilesToBackup();
            this.backupDatabaseManager.createBackupDatabase(this.backupFilesManager.getTemporaryBackupDirectory(), this.getIgnoredTablesForBackupDatabasePopulation(createBackupMessage), listeners);
            ListenersUtils.triggerListenersInParallel(l -> l.onBackupCreationProgress(BackupCreationStage.METADATA_CREATION), listeners);
            ConcurrencyUtils.ensureNotInterrupted();
            BackupFileConfiguration configuration = this.getBackupFileConfiguration(createBackupMessage);
            this.backupFilesManager.writeBackupFileConfiguration(configuration);
            String hash = this.getBackupFileHash(configuration);
            this.backupFilesManager.writeBackupFileHash(hash);
            ListenersUtils.triggerListenersInParallel(l -> l.onBackupCreationProgress(BackupCreationStage.FILES_ARCHIVING), listeners);
            ConcurrencyUtils.ensureNotInterrupted();
            File backupZipFile = this.backupFilesManager.zipBackupFile(backupName);
            this.backupFilesManager.deleteTemporaryBackupDirectory();
            BackupFileInfo backupFileInfo = new BackupFileInfo(configuration, hash, backupZipFile);
            ListenersUtils.triggerListenersInParallel(BackupCreationListener::onBackupFileCreationCompleted, listeners);
            BackupFileInfo backupFileInfo2 = backupFileInfo;
            return backupFileInfo2;
        }
        catch (Exception e) {
            this.backupFilesManager.deleteTemporaryBackupDirectory();
            ListenersUtils.triggerListeners(listener -> listener.onBackupCreationFailed(e), listeners);
            ConcurrencyUtils.ensureInterruptedStatusRestored(e);
            throw new BackupFileCreationException(e);
        }
        finally {
            this.javalinAvailabilityInterceptor.setAvailable();
        }
    }

    private BackupIgnoredTables getIgnoredTablesForBackupDatabasePopulation(CreateBackupMessage message) {
        BackupIgnoredTables ignoredTables = new BackupIgnoredTables();
        if (!message.isIncludeAuditLogs()) {
            ignoredTables.add(com.incoresoft.generated.Tables.AUDIT_TRAIL);
        }
        for (PluginConfiguration pluginConfiguration : this.pluginConfigurationsManager.getConfigurations()) {
            List<String> pluginIgnoredTables = pluginConfiguration.getBackupIgnoredTables();
            pluginIgnoredTables.forEach(ignoredTables::add);
        }
        ignoredTables.add(Tables.AGGREGATED_METRICS_CORE);
        return ignoredTables;
    }

    private BackupFileConfiguration getBackupFileConfiguration(CreateBackupMessage message) {
        Version coreVersion = this.coreServiceVersionSupplier.getCoreVersion();
        List<InstalledPluginInfo> eventManagerPlugins = this.eventManagerPluginsFacade.getInstalledPluginsInfos();
        List<InstalledPluginInfo> vezhaPlugins = this.analyticsPluginsFacade.getInstalledPluginsInfos();
        return new BackupFileConfiguration(message, coreVersion, eventManagerPlugins, vezhaPlugins, this.coreService, this.configManager.isVmsIntegrationEnabled());
    }

    private String getBackupFileHash(BackupFileConfiguration configuration) {
        File dbFile = this.backupDatabaseManager.resolveBackupDatabaseFile(this.backupFilesManager.getTemporaryBackupDirectory());
        return this.hashManager.generateHash(configuration, dbFile);
    }

    @Inject
    public BackupsCreationManager(BackupFilesManager backupFilesManager, BackupFilesHashManager hashManager, BackupDatabaseManager backupDatabaseManager, EventManagerPluginsManagerFacade eventManagerPluginsFacade, AnalyticsPluginsManagerFacade analyticsPluginsFacade, CoreServiceVersionSupplier coreServiceVersionSupplier, JavalinAvailabilityInterceptor javalinAvailabilityInterceptor, PluginConfigurationsManager pluginConfigurationsManager, VezhaConfigManager configManager, CoreService coreService) {
        this.backupFilesManager = backupFilesManager;
        this.hashManager = hashManager;
        this.backupDatabaseManager = backupDatabaseManager;
        this.eventManagerPluginsFacade = eventManagerPluginsFacade;
        this.analyticsPluginsFacade = analyticsPluginsFacade;
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.javalinAvailabilityInterceptor = javalinAvailabilityInterceptor;
        this.pluginConfigurationsManager = pluginConfigurationsManager;
        this.configManager = configManager;
        this.coreService = coreService;
    }
}

