/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.incoresoft.vms.drivers.sdk.DeviceManager
 */
package com.incoresoft.middleware.vms.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListenerManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.eventmanager.EventsManagerConnector;
import com.incoresoft.middleware.model.settings.Settings;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueuePluginsListenerBasedConsumer;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventManager;
import com.incoresoft.middleware.vms.notifications.VmsNotificationManager;
import com.incoresoft.middleware.vms.roles.VmsPluginRolesManager;
import com.incoresoft.middleware.vms.rules.RuleManager;
import com.incoresoft.middleware.vms.vezhaintegration.VezhaIntegrationManager;
import com.incoresoft.vms.drivers.sdk.DeviceManager;
import io.javalin.Javalin;
import org.jooq.DSLContext;

public interface VmsExtensionContext {
    public Javalin getJavalin();

    public DSLContext getDSLContext();

    public DatabaseConnector getDatabaseConnector();

    public MessageQueueConnector getMessageQueueConnector();

    public Storage getStorage();

    public StreamsManager getStreamsManager();

    public VmsPluginRolesManager getRolesManager();

    public AnalyticsManager getAnalyticsManager();

    public AnalyticsGroupsManager getAnalyticsGroupsManager();

    public UsersManager getUsersManager();

    public VmsNotificationManager getNotificationsManager();

    public EventsManagerConnector getEventsManagerConnector();

    public GlobalSettings getGlobalSettings();

    public Cleaner getCleaner();

    public Auditor getAuditor();

    public AnalyticsMetadataListenerManager getAnalyticsMetadataListenerManager();

    public Settings getSettings();

    public ObjectMapper getObjectMapper();

    public RuleManager getRuleManager();

    public DeviceManager getDeviceManager();

    public MapPlanEventManager getMapPlanEventManager();

    public MessageQueuePluginsListenerBasedConsumer getPluginMessagesConsumer();

    public VezhaIntegrationManager getVezhaIntegrationManager();
}

