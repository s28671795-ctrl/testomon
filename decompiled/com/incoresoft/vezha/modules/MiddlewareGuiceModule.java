/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.incoresoft.database.converters.AuditRecordConverter;
import com.incoresoft.dependencyinjection.CompositeModule;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListenerManager;
import com.incoresoft.middleware.eventbus.Bus;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.settings.Settings;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.sessions.api.ApiTokenManager;
import com.incoresoft.vezha.analytics.AnalyticsCoreScheduler;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequestReceiver;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequester;
import com.incoresoft.vezha.analytics.cameraanalytics.StoredImageRequester;
import com.incoresoft.vezha.analytics.groups.AnalyticsGroupsManagerImpl;
import com.incoresoft.vezha.analytics.listeners.AnalyticsExtensionListener;
import com.incoresoft.vezha.analytics.listeners.AnalyticsStateEventsPublisher;
import com.incoresoft.vezha.analytics.listeners.LicenseAndDiskChangesListener;
import com.incoresoft.vezha.analytics.mq.AnalyticsListenerManagerImpl;
import com.incoresoft.vezha.analytics.mq.metadata.AnalyticsTopicListenerManager;
import com.incoresoft.vezha.analytics.mq.service.AnalyticsServiceListenerManager;
import com.incoresoft.vezha.analytics.mq.state.AnalyticsStateListenersRegistry;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserveCalculator;
import com.incoresoft.vezha.analytics.servers.InFlightGpuReservations;
import com.incoresoft.vezha.analytics.servers.ServersLoadBalancer;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.bus.EventBusImpl;
import com.incoresoft.vezha.cleaner.VezhaCleanerTasksRepositoryImpl;
import com.incoresoft.vezha.database.ServerRepository;
import com.incoresoft.vezha.database.converters.VezhaAuditRecordConverter;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.models.role.RoleManagerImpl;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import com.incoresoft.vezha.models.streams.StreamSnapshotPathManager;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.models.users.ldap.VezhaLdapClient;
import com.incoresoft.vezha.modules.ControllersModule;
import com.incoresoft.vezha.modules.CoreServiceModule;
import com.incoresoft.vezha.modules.ManagersModule;
import com.incoresoft.vezha.modules.PluginsModule;
import com.incoresoft.vezha.modules.SettingsModule;
import com.incoresoft.vezha.modules.VezhaPluginsModule;
import com.incoresoft.vezha.plugins.VezhaExtensionsManager;
import com.incoresoft.vezha.settings.SettingsProxy;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigManager;

public class MiddlewareGuiceModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(VezhaExtensionsManager.class).asEagerSingleton();
        this.bind(ExtensionsManager.class).to(VezhaExtensionsManager.class);
        this.bind(AuditRecordConverter.class).to(VezhaAuditRecordConverter.class);
        this.install(new CompositeModule(VezhaCleanerTasksRepositoryImpl.class));
        this.install(new VezhaPluginsModule());
        this.install(new CoreServiceModule());
        this.install(new PluginsModule());
        this.install(new SettingsModule());
        this.install(new ControllersModule());
        this.install(new ManagersModule());
        this.bind(Bus.class).to(EventBusImpl.class).asEagerSingleton();
        this.bind(RoleManagerImpl.class).asEagerSingleton();
        this.bind(RolesManager.class).to(RoleManagerImpl.class);
        this.bind(UsersManagerImpl.class).asEagerSingleton();
        this.bind(UsersManager.class).to(UsersManagerImpl.class);
        this.bind(VezhaLdapClient.class).asEagerSingleton();
        this.bind(ApiTokenManager.class).asEagerSingleton();
        this.bind(StreamSnapshotPathManager.class).asEagerSingleton();
        this.bind(StreamManagerImpl.class).asEagerSingleton();
        this.bind(StreamsManager.class).to(StreamManagerImpl.class);
        this.bind(LicenseUsageManager.class).asEagerSingleton();
        this.bind(AnalyticsManagerImpl.class).asEagerSingleton();
        this.bind(AnalyticsManager.class).to(AnalyticsManagerImpl.class);
        this.bind(AnalyticsGroupsManager.class).to(AnalyticsGroupsManagerImpl.class).asEagerSingleton();
        this.bind(ServerRepository.class).asEagerSingleton();
        this.bind(AnalyticsCoreScheduler.class).asEagerSingleton();
        this.bind(InFlightGpuReservations.class).asEagerSingleton();
        this.bind(GpuMemoryReserveCalculator.class).asEagerSingleton();
        this.bind(ServersMonitor.class).asEagerSingleton();
        this.bind(ServersLoadBalancer.class).asEagerSingleton();
        this.bind(VideoFileProgressTracker.class).asEagerSingleton();
        this.bind(LicenseAndDiskChangesListener.class).asEagerSingleton();
        this.bind(InitializationRequestReceiver.class).asEagerSingleton();
        this.bind(InitializationRequester.class).asEagerSingleton();
        this.bind(StoredImageRequester.class).asEagerSingleton();
        this.bind(AnalyticsStateListenersRegistry.class).asEagerSingleton();
        this.bind(AnalyticsTopicListenerManager.class).asEagerSingleton();
        this.bind(AnalyticsServiceListenerManager.class).asEagerSingleton();
        this.bind(AnalyticsMetadataListenerManager.class).to(AnalyticsListenerManagerImpl.class);
        this.bind(AnalyticsListenerManagerImpl.class).asEagerSingleton();
        this.bind(AnalyticsExtensionListener.class).asEagerSingleton();
        this.bind(AnalyticsStateEventsPublisher.class).asEagerSingleton();
        this.bind(SettingsProxy.class).asEagerSingleton();
        this.bind(Settings.class).to(SettingsProxy.class);
        this.bind(WindowsShareConfigManager.class).asEagerSingleton();
    }
}

