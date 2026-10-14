/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueuePluginsListenerBasedConsumer;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventManager;
import com.incoresoft.middleware.vms.notifications.VmsNotificationManager;
import com.incoresoft.middleware.vms.plugin.VmsExtension;
import com.incoresoft.middleware.vms.plugin.VmsExtensionContext;
import com.incoresoft.middleware.vms.roles.VmsPluginRolesManager;
import com.incoresoft.middleware.vms.rules.RuleManager;
import com.incoresoft.middleware.vms.vezhaintegration.VezhaIntegrationManager;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;
import io.javalin.Javalin;
import java.net.http.HttpClient;
import java.time.Duration;
import okhttp3.OkHttpClient;
import org.jooq.DSLContext;

public class VmsPluginModule
extends AbstractModule {
    private final VmsExtensionContext extensionContext;
    private final VmsExtension vmsExtension;

    @Override
    protected void configure() {
        this.bind(VmsExtension.class).toInstance(this.vmsExtension);
        this.bind(VideoFileProgressTracker.class).asEagerSingleton();
        this.bind(Storage.class).toInstance(this.extensionContext.getStorage());
        this.bind(MessageQueueConnector.class).toInstance(this.extensionContext.getMessageQueueConnector());
        this.bind(DSLContext.class).toInstance(this.extensionContext.getDSLContext());
        this.bind(DatabaseConnector.class).toInstance(this.extensionContext.getDatabaseConnector());
        this.bind(Javalin.class).toInstance(this.extensionContext.getJavalin());
        this.bind(VmsPluginRolesManager.class).toInstance(this.extensionContext.getRolesManager());
        this.bind(StreamsManager.class).toInstance(this.extensionContext.getStreamsManager());
        this.bind(UsersManager.class).toInstance(this.extensionContext.getUsersManager());
        this.bind(VmsNotificationManager.class).toInstance(this.extensionContext.getNotificationsManager());
        this.bind(GlobalSettings.class).toInstance(this.extensionContext.getGlobalSettings());
        this.bind(AnalyticsManager.class).toInstance(this.extensionContext.getAnalyticsManager());
        this.bind(AnalyticsGroupsManager.class).toInstance(this.extensionContext.getAnalyticsGroupsManager());
        this.bind(Auditor.class).toInstance(this.extensionContext.getAuditor());
        this.bind(Cleaner.class).toInstance(this.extensionContext.getCleaner());
        this.bind(RuleManager.class).toInstance(this.extensionContext.getRuleManager());
        this.bind(BeanValidatorImpl.class).asEagerSingleton();
        this.bind(BeanValidator.class).to(BeanValidatorImpl.class);
        this.bind(VezhaIntegrationManager.class).toInstance(this.extensionContext.getVezhaIntegrationManager());
        this.bind(HttpClient.class).toInstance(HttpClient.newHttpClient());
        this.bind(MessageQueuePluginsListenerBasedConsumer.class).toInstance(this.extensionContext.getPluginMessagesConsumer());
        this.bind(MapPlanEventManager.class).toInstance(this.extensionContext.getMapPlanEventManager());
    }

    @Provides
    @Singleton
    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(Duration.ZERO).build();
    }

    public VmsPluginModule(VmsExtensionContext extensionContext, VmsExtension vmsExtension) {
        this.extensionContext = extensionContext;
        this.vmsExtension = vmsExtension;
    }
}

