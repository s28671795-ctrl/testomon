/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListenerManager;
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
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.storage.Storage;
import io.javalin.Javalin;
import org.jooq.DSLContext;

public interface ExtensionContext {
    public Javalin getJavalin();

    public DSLContext getDSLContext();

    public DatabaseConnector getDatabaseConnector();

    public MessageQueueConnector getMessageQueueConnector();

    public Storage getStorage();

    public StreamsManager getStreamsManager();

    public RolesManager getRolesManager();

    public AnalyticsManager getAnalyticsManager();

    default public AnalyticsGroupsManager getAnalyticsGroupManager() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public UsersManager getUsersManager();

    public NotificationsPanel getNotificationsPanel();

    public EventsManagerConnector getEventsManagerConnector();

    public GlobalSettings getGlobalSettings();

    public Cleaner getCleaner();

    public Auditor getAuditor();

    public Bus getEventBus();

    public AnalyticsMetadataListenerManager getAnalyticsMetadataListenerManager();

    public Settings getSettings();
}

