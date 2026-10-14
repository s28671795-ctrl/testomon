/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleanerTasksExecutor;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.generated.tables.AuditTrail;
import com.incoresoft.loading.AppLoadingListener;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;
import com.incoresoft.notifications.sounds.SoundsManager;
import com.incoresoft.plugin.commons.cleaner.VezhaCleaningCategory;
import com.incoresoft.plugins.configurations.PluginConfigurationsManager;
import com.incoresoft.system.disk.DiskSpaceManager;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequester;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.models.role.RoleManagerImpl;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.plugins.PluginExtensionsLifecycleEventsPublisher;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import java.util.Optional;

public class MiddlewareApp {
    private final AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;
    private final ExtensionsManager extensionsManager;
    private final RoleManagerImpl rolesManager;
    private final PluginExtensionsLifecycleEventsPublisher lifecycleEventsPublisher;
    private final UsersManagerImpl users;
    private final StreamManagerImpl streamManager;
    private final AnalyticsManagerImpl analyticsManager;
    private final SoundsManager soundsManager;
    private final CleanerTasksExecutor cleanerTasksExecutor;
    private final CleanerTasksRepositoryImpl cleanerTasksRepository;
    private final VezhaConfigManager configManager;
    private final InitializationRequester initializationRequester;
    private final PluginConfigurationsManager pluginConfigurationsManager;
    private final DiskSpaceManager diskSpaceManager;

    public void start(AppLoadingListener loadingListener) {
        Optional<String> path = ((VezhaConfig)this.configManager.getConfig()).getStorage().localDirectory();
        if (path.isPresent()) {
            this.cleanerTasksRepository.addStorageCleaningTask(VezhaCleaningCategory.STORAGE_DATA, this.configManager.getStorage().localDirectory().orElse(""));
        }
        this.cleanerTasksRepository.addDatabaseCleaningTask(VezhaCleaningCategory.METADATA, AuditTrail.AUDIT_TRAIL.getName(), AuditTrail.AUDIT_TRAIL.CREATED_AT.getName());
        this.cleanerTasksExecutor.schedule();
        this.diskSpaceManager.scheduleDiskSpaceCheck();
        this.extensionsManager.addListener(this.rolesManager);
        this.extensionsManager.addListener(this.lifecycleEventsPublisher);
        this.extensionsManager.addListener(this.pluginConfigurationsManager);
        this.users.init();
        this.streamManager.init();
        loadingListener.onStageChanged(LoadingStage.LOAD_PLUGINS);
        this.analyticsPluginsManagerFacade.loadAndStartEnabledPlugins();
        this.eventManagerPluginsManagerFacade.loadAndStartEnabledPlugins();
        this.analyticsManager.init();
        this.initializationRequester.init();
        this.soundsManager.init();
        loadingListener.onCurrentStageStateChange(LoadingStage.WEB_APPLICATION, LoadingStatus.OK, null);
    }

    @Inject
    public MiddlewareApp(AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade, EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade, ExtensionsManager extensionsManager, RoleManagerImpl rolesManager, PluginExtensionsLifecycleEventsPublisher lifecycleEventsPublisher, UsersManagerImpl users, StreamManagerImpl streamManager, AnalyticsManagerImpl analyticsManager, SoundsManager soundsManager, CleanerTasksExecutor cleanerTasksExecutor, CleanerTasksRepositoryImpl cleanerTasksRepository, VezhaConfigManager configManager, InitializationRequester initializationRequester, PluginConfigurationsManager pluginConfigurationsManager, DiskSpaceManager diskSpaceManager) {
        this.analyticsPluginsManagerFacade = analyticsPluginsManagerFacade;
        this.eventManagerPluginsManagerFacade = eventManagerPluginsManagerFacade;
        this.extensionsManager = extensionsManager;
        this.rolesManager = rolesManager;
        this.lifecycleEventsPublisher = lifecycleEventsPublisher;
        this.users = users;
        this.streamManager = streamManager;
        this.analyticsManager = analyticsManager;
        this.soundsManager = soundsManager;
        this.cleanerTasksExecutor = cleanerTasksExecutor;
        this.cleanerTasksRepository = cleanerTasksRepository;
        this.configManager = configManager;
        this.initializationRequester = initializationRequester;
        this.pluginConfigurationsManager = pluginConfigurationsManager;
        this.diskSpaceManager = diskSpaceManager;
    }
}

