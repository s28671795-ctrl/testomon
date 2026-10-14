/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.google.inject.Inject;
import com.incoresoft.backup.database.BackupDatabaseManager;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.exceptions.BackupDatabaseStructureInvalidException;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.plugins.PluginsFacade;
import com.incoresoft.plugins.configurations.PluginConfigurationsManager;
import com.incoresoft.plugins.exceptions.PluginAlreadyInstalledException;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.BackupRequiredPluginInfo;
import com.incoresoft.vezha.backup.BackupRestoreStage;
import com.incoresoft.vezha.backup.exceptions.BackupFileCorruptedException;
import com.incoresoft.vezha.backup.exceptions.BackupFileInvalidException;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.file.BackupFilePluginInfo;
import com.incoresoft.vezha.backup.file.BackupFileRestoreInfo;
import com.incoresoft.vezha.backup.file.BackupFilesManager;
import com.incoresoft.vezha.backup.file.BackupFilesValidator;
import com.incoresoft.vezha.backup.listeners.restore.BackupInstallPluginsListener;
import com.incoresoft.vezha.backup.listeners.restore.BackupRestoreListener;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.concurrent.GuardedBy;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupsRestoreManager {
    private static final Logger log = LoggerFactory.getLogger(BackupsRestoreManager.class);
    private final BackupFilesManager backupFilesManager;
    private final BackupFilesValidator backupFilesValidator;
    private final BackupDatabaseManager backupDatabaseManager;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;
    private final AnalyticsPluginsManagerFacade analyticsPluginsFacade;
    private final PluginConfigurationsManager pluginConfigurationsManager;
    @GuardedBy(value="this")
    private BackupFileInfo currentBackupFileInfo;

    public synchronized BackupFileInfo getCurrentBackupFileInfo() {
        return this.currentBackupFileInfo;
    }

    public synchronized BackupFileRestoreInfo prepareForRestore(File backupZipFile) {
        try {
            BackupFileInfo backupFileInfo;
            this.backupFilesManager.unzipBackupFile(backupZipFile);
            BackupFileConfiguration configuration = this.backupFilesManager.readBackupFileConfiguration();
            String hash = this.backupFilesManager.readBackupFileHash();
            File dbFile = this.backupDatabaseManager.resolveBackupDatabaseFile(this.backupFilesManager.getTemporaryBackupDirectory());
            this.backupFilesValidator.validate(configuration, hash, dbFile);
            this.backupDatabaseManager.validateBackupSchema(this.backupFilesManager.getTemporaryBackupDirectory());
            this.currentBackupFileInfo = backupFileInfo = new BackupFileInfo(configuration, hash, backupZipFile);
            log.info("Backup {} is prepared to restore", (Object)backupFileInfo.getFileName());
            return new BackupFileRestoreInfo(backupFileInfo, this.getRequiredPluginInfos(backupFileInfo.getEventManagerPlugins(), this.eventManagerPluginsManagerFacade), this.getRequiredPluginInfos(backupFileInfo.getAnalyticsPlugins(), this.analyticsPluginsFacade));
        }
        catch (BackupFileInvalidException e) {
            throw e;
        }
        catch (BackupDatabaseStructureInvalidException e) {
            log.warn("Backup database structure is invalid", e);
            throw new BackupFileCorruptedException();
        }
        catch (Exception e) {
            throw new BackupFileCorruptedException();
        }
    }

    public synchronized void clearCurrentBackupAssociation() {
        this.currentBackupFileInfo = null;
        this.backupFilesManager.deleteTemporaryFiles();
    }

    public void installPluginLatestVersion(String pluginId, PluginType pluginType, InstallationListener listener) {
        PluginsFacade pluginManager = this.getPluginsFacade(pluginType);
        boolean isInstalled = pluginManager.getInstalledPluginsInfos().stream().anyMatch(installedPluginInfo -> installedPluginInfo.getId().equals(pluginId));
        if (isInstalled) {
            throw new PluginAlreadyInstalledException(pluginId);
        }
        pluginManager.installLatestVersion(pluginId, listener);
    }

    public void installAllPluginsLatestVersions(PluginType pluginType, BackupInstallPluginsListener listener) {
        for (BackupFilePluginInfo pluginInfo : this.getPluginInfos(pluginType)) {
            try {
                this.installPluginLatestVersion(pluginInfo.getId(), pluginType, listener.getSingleListener(pluginInfo.getId(), pluginType));
            }
            catch (PluginAlreadyInstalledException pluginAlreadyInstalledException) {
            }
            catch (Exception e) {
                listener.onError(pluginInfo.getId(), pluginType, e);
            }
        }
    }

    public void excludePlugin(String pluginId, PluginType pluginType) {
        PluginsFacade pluginsFacade = this.getPluginsFacade(pluginType);
        pluginsFacade.uninstallPlugin(pluginId);
    }

    public synchronized void confirmRestore(List<BackupRestoreListener> listeners) throws InterruptedException {
        this.backupDatabaseManager.restoreBackupDatabase(this.backupFilesManager.getTemporaryBackupDirectory(), this.getIgnoredTablesForBackupRestore(), listeners.stream().map(l -> l).toList());
        ListenersUtils.triggerListenersInParallel(l -> l.onBackupRestoreProgress(BackupRestoreStage.CONFIGURATION_FILES_IMPORT), listeners);
        this.backupFilesManager.importFilesFromBackup();
        ListenersUtils.triggerListenersInParallel(BackupRestoreListener::onBackupRestoreCompleted, listeners);
    }

    private PluginsFacade getPluginsFacade(PluginType pluginType) {
        return switch (pluginType) {
            case PluginType.EVENT_MANAGER_PLUGIN -> this.eventManagerPluginsManagerFacade;
            case PluginType.ANALYTICS_PLUGIN -> this.analyticsPluginsFacade;
            default -> throw new IllegalArgumentException("Unknown plugin type: " + String.valueOf((Object)pluginType));
        };
    }

    private synchronized List<BackupFilePluginInfo> getPluginInfos(PluginType pluginType) {
        return switch (pluginType) {
            case PluginType.EVENT_MANAGER_PLUGIN -> this.currentBackupFileInfo.getEventManagerPlugins();
            case PluginType.ANALYTICS_PLUGIN -> this.currentBackupFileInfo.getAnalyticsPlugins();
            default -> throw new IllegalArgumentException("Unknown plugin type: " + String.valueOf((Object)pluginType));
        };
    }

    private BackupIgnoredTables getIgnoredTablesForBackupRestore() {
        List<String> includedPluginIds = this.getIncludedAnalyticsPluginNames();
        List<String> excludedPluginIds = this.getExcludedAnalyticsPluginNames();
        Set<String> possibleIgnoredTables = this.getManagedTablesForPlugins(excludedPluginIds);
        Set<String> includedTables = this.getManagedTablesForPlugins(includedPluginIds);
        return new BackupIgnoredTables(CollectionUtils.subtract(possibleIgnoredTables, includedTables));
    }

    private List<BackupRequiredPluginInfo> getRequiredPluginInfos(List<BackupFilePluginInfo> pluginInfos, PluginsFacade pluginsManagerFacade) {
        Set installedPluginIds = pluginsManagerFacade.getInstalledPluginsInfos().stream().map(InstalledPluginInfo::getId).collect(Collectors.toSet());
        return pluginInfos.stream().map(pluginInfo -> {
            if (installedPluginIds.contains(pluginInfo.getId())) {
                return new BackupRequiredPluginInfo((BackupFilePluginInfo)pluginInfo, true);
            }
            return new BackupRequiredPluginInfo((BackupFilePluginInfo)pluginInfo, false);
        }).toList();
    }

    private List<String> getExcludedAnalyticsPluginNames() {
        List<BackupFilePluginInfo> analyticsPluginInfos = this.getPluginInfos(PluginType.ANALYTICS_PLUGIN);
        Set analyticsInstalledPluginsIds = this.analyticsPluginsFacade.getInstalledPluginsInfos().stream().map(InstalledPluginInfo::getId).collect(Collectors.toSet());
        return analyticsPluginInfos.stream().map(BackupFilePluginInfo::getId).filter(id -> !analyticsInstalledPluginsIds.contains(id)).toList();
    }

    private List<String> getIncludedAnalyticsPluginNames() {
        List<BackupFilePluginInfo> analyticsPluginInfos = this.getPluginInfos(PluginType.EVENT_MANAGER_PLUGIN);
        Set analyticsInstalledPluginsIds = this.analyticsPluginsFacade.getInstalledPluginsInfos().stream().map(InstalledPluginInfo::getId).collect(Collectors.toSet());
        return analyticsPluginInfos.stream().map(BackupFilePluginInfo::getId).filter(analyticsInstalledPluginsIds::contains).toList();
    }

    private Set<String> getManagedTablesForPlugins(List<String> pluginsIds) {
        return this.pluginConfigurationsManager.getConfigurations().stream().filter(configuration -> pluginsIds.contains(configuration.getPluginId())).flatMap(configuration -> configuration.getManagedTables().stream()).collect(Collectors.toCollection(HashSet::new));
    }

    @Inject
    public BackupsRestoreManager(BackupFilesManager backupFilesManager, BackupFilesValidator backupFilesValidator, BackupDatabaseManager backupDatabaseManager, EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade, AnalyticsPluginsManagerFacade analyticsPluginsFacade, PluginConfigurationsManager pluginConfigurationsManager) {
        this.backupFilesManager = backupFilesManager;
        this.backupFilesValidator = backupFilesValidator;
        this.backupDatabaseManager = backupDatabaseManager;
        this.eventManagerPluginsManagerFacade = eventManagerPluginsManagerFacade;
        this.analyticsPluginsFacade = analyticsPluginsFacade;
        this.pluginConfigurationsManager = pluginConfigurationsManager;
    }
}

