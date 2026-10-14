/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins;

import com.google.inject.AbstractModule;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.eventbus.Bus;
import com.incoresoft.middleware.eventmanager.EventsManagerConnector;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.settings.Settings;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.http.websocket.NotificationsWS;
import com.incoresoft.plugin.commons.http.websocket.ViewAnalyticWS;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;
import io.javalin.Javalin;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class VezhaPluginModule
extends AbstractModule {
    private final ExtensionContext extensionContext;
    private final VezhaExtension vezhaExtension;

    public VezhaPluginModule(@NotNull ExtensionContext extensionContext, VezhaExtension vezhaExtension) {
        this.extensionContext = Objects.requireNonNull(extensionContext);
        this.vezhaExtension = Objects.requireNonNull(vezhaExtension);
    }

    @Override
    protected void configure() {
        this.bind(VezhaExtension.class).toInstance(this.vezhaExtension);
        this.bind(NotificationsWS.class).asEagerSingleton();
        this.bind(ViewAnalyticWS.class).asEagerSingleton();
        this.bind(VideoFileProgressTracker.class).asEagerSingleton();
        this.bind(Storage.class).toInstance(this.extensionContext.getStorage());
        this.bind(MessageQueueConnector.class).toInstance(this.extensionContext.getMessageQueueConnector());
        this.bind(DSLContext.class).toInstance(this.extensionContext.getDSLContext());
        this.bind(DatabaseConnector.class).toInstance(this.extensionContext.getDatabaseConnector());
        this.bind(Javalin.class).toInstance(this.extensionContext.getJavalin());
        this.bind(RolesManager.class).toInstance(this.extensionContext.getRolesManager());
        this.bind(StreamsManager.class).toInstance(this.extensionContext.getStreamsManager());
        this.bind(UsersManager.class).toInstance(this.extensionContext.getUsersManager());
        this.bind(NotificationsPanel.class).toInstance(this.extensionContext.getNotificationsPanel());
        this.bind(GlobalSettings.class).toInstance(this.extensionContext.getGlobalSettings());
        this.bind(EventsManagerConnector.class).toInstance(this.extensionContext.getEventsManagerConnector());
        this.bind(AnalyticsManager.class).toInstance(this.extensionContext.getAnalyticsManager());
        this.bind(AnalyticsGroupsManager.class).toInstance(this.extensionContext.getAnalyticsGroupManager());
        this.bind(Auditor.class).toInstance(this.extensionContext.getAuditor());
        this.bind(Bus.class).toInstance(this.extensionContext.getEventBus());
        this.bind(Settings.class).toInstance(this.extensionContext.getSettings());
        this.bind(Cleaner.class).toInstance(this.extensionContext.getCleaner());
        this.bind(BeanValidatorImpl.class).asEagerSingleton();
        this.bind(BeanValidator.class).to(BeanValidatorImpl.class);
    }
}

