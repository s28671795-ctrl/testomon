/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
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
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.storage.Storage;
import io.javalin.Javalin;
import org.jooq.DSLContext;

public class VezhaExtensionContext
implements ExtensionContext {
    private final Javalin javalin;
    private final DSLContext create;
    private final DatabaseConnector databaseConnector;
    private final MessageQueueConnector messageQueueConnector;
    private final Storage storage;
    private final StreamsManager streamsManager;
    private final RolesManager roleManager;
    private final AnalyticsManager analyticsManager;
    private final AnalyticsGroupsManager analyticsGroupsManager;
    private final UsersManager usersManager;
    private final NotificationsPanel notificationsPanel;
    private final EventsManagerConnector eventsManagerConnector;
    private final GlobalSettings globalSettings;
    private final CleanerTasksRepositoryImpl cleanerTasksRepository;
    private final Auditor auditor;
    private final Bus bus;
    private final AnalyticsMetadataListenerManager analyticsMetadataListenerManager;
    private final Settings settings;

    @Override
    public Javalin getJavalin() {
        return this.javalin;
    }

    @Override
    public DSLContext getDSLContext() {
        return this.create;
    }

    @Override
    public DatabaseConnector getDatabaseConnector() {
        return this.databaseConnector;
    }

    @Override
    public MessageQueueConnector getMessageQueueConnector() {
        return this.messageQueueConnector;
    }

    @Override
    public Storage getStorage() {
        return this.storage;
    }

    @Override
    public RolesManager getRolesManager() {
        return this.roleManager;
    }

    @Override
    public StreamsManager getStreamsManager() {
        return this.streamsManager;
    }

    @Override
    public AnalyticsManager getAnalyticsManager() {
        return this.analyticsManager;
    }

    @Override
    public AnalyticsGroupsManager getAnalyticsGroupManager() {
        return this.analyticsGroupsManager;
    }

    @Override
    public UsersManager getUsersManager() {
        return this.usersManager;
    }

    @Override
    public NotificationsPanel getNotificationsPanel() {
        return this.notificationsPanel;
    }

    @Override
    public EventsManagerConnector getEventsManagerConnector() {
        return this.eventsManagerConnector;
    }

    @Override
    public GlobalSettings getGlobalSettings() {
        return this.globalSettings;
    }

    @Override
    public Cleaner getCleaner() {
        return this.cleanerTasksRepository;
    }

    @Override
    public Auditor getAuditor() {
        return this.auditor;
    }

    @Override
    public Bus getEventBus() {
        return this.bus;
    }

    @Override
    public AnalyticsMetadataListenerManager getAnalyticsMetadataListenerManager() {
        return this.analyticsMetadataListenerManager;
    }

    @Override
    public Settings getSettings() {
        return this.settings;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Inject
    public VezhaExtensionContext(Javalin javalin, DSLContext create, DatabaseConnector databaseConnector, MessageQueueConnector messageQueueConnector, Storage storage, StreamsManager streamsManager, RolesManager roleManager, AnalyticsManager analyticsManager, AnalyticsGroupsManager analyticsGroupsManager, UsersManager usersManager, NotificationsPanel notificationsPanel, EventsManagerConnector eventsManagerConnector, GlobalSettings globalSettings, CleanerTasksRepositoryImpl cleanerTasksRepository, Auditor auditor, Bus bus, AnalyticsMetadataListenerManager analyticsMetadataListenerManager, Settings settings) {
        this.javalin = javalin;
        this.create = create;
        this.databaseConnector = databaseConnector;
        this.messageQueueConnector = messageQueueConnector;
        this.storage = storage;
        this.streamsManager = streamsManager;
        this.roleManager = roleManager;
        this.analyticsManager = analyticsManager;
        this.analyticsGroupsManager = analyticsGroupsManager;
        this.usersManager = usersManager;
        this.notificationsPanel = notificationsPanel;
        this.eventsManagerConnector = eventsManagerConnector;
        this.globalSettings = globalSettings;
        this.cleanerTasksRepository = cleanerTasksRepository;
        this.auditor = auditor;
        this.bus = bus;
        this.analyticsMetadataListenerManager = analyticsMetadataListenerManager;
        this.settings = settings;
    }

    public static class Builder {
        private Javalin javalin;
        private DSLContext create;
        private DatabaseConnector databaseConnector;
        private MessageQueueConnector messageQueueConnector;
        private Storage storage;
        private StreamsManager streamsManager;
        private RolesManager roleManager;
        private AnalyticsManager analyticsManager;
        private AnalyticsGroupsManager analyticsGroupsManager;
        private UsersManager usersManager;
        private NotificationsPanel notificationsPanel;
        private EventsManagerConnector eventsManagerConnector;
        private GlobalSettings globalSettings;
        private CleanerTasksRepositoryImpl cleanerTasksRepository;
        private Auditor auditor;
        private Bus bus;
        private AnalyticsMetadataListenerManager analyticsMetadataListenerManager;
        private Settings settings;

        Builder() {
        }

        public Builder javalin(Javalin javalin) {
            this.javalin = javalin;
            return this;
        }

        public Builder create(DSLContext create) {
            this.create = create;
            return this;
        }

        public Builder databaseConnector(DatabaseConnector databaseConnector) {
            this.databaseConnector = databaseConnector;
            return this;
        }

        public Builder messageQueueConnector(MessageQueueConnector messageQueueConnector) {
            this.messageQueueConnector = messageQueueConnector;
            return this;
        }

        public Builder storage(Storage storage) {
            this.storage = storage;
            return this;
        }

        public Builder streamsManager(StreamsManager streamsManager) {
            this.streamsManager = streamsManager;
            return this;
        }

        public Builder roleManager(RolesManager roleManager) {
            this.roleManager = roleManager;
            return this;
        }

        public Builder analyticsManager(AnalyticsManager analyticsManager) {
            this.analyticsManager = analyticsManager;
            return this;
        }

        public Builder analyticsGroupsManager(AnalyticsGroupsManager analyticsGroupsManager) {
            this.analyticsGroupsManager = analyticsGroupsManager;
            return this;
        }

        public Builder usersManager(UsersManager usersManager) {
            this.usersManager = usersManager;
            return this;
        }

        public Builder notificationsPanel(NotificationsPanel notificationsPanel) {
            this.notificationsPanel = notificationsPanel;
            return this;
        }

        public Builder eventsManagerConnector(EventsManagerConnector eventsManagerConnector) {
            this.eventsManagerConnector = eventsManagerConnector;
            return this;
        }

        public Builder globalSettings(GlobalSettings globalSettings) {
            this.globalSettings = globalSettings;
            return this;
        }

        public Builder cleanerTasksRepository(CleanerTasksRepositoryImpl cleanerTasksRepository) {
            this.cleanerTasksRepository = cleanerTasksRepository;
            return this;
        }

        public Builder auditor(Auditor auditor) {
            this.auditor = auditor;
            return this;
        }

        public Builder bus(Bus bus) {
            this.bus = bus;
            return this;
        }

        public Builder analyticsMetadataListenerManager(AnalyticsMetadataListenerManager analyticsMetadataListenerManager) {
            this.analyticsMetadataListenerManager = analyticsMetadataListenerManager;
            return this;
        }

        public Builder settings(Settings settings) {
            this.settings = settings;
            return this;
        }

        public VezhaExtensionContext build() {
            return new VezhaExtensionContext(this.javalin, this.create, this.databaseConnector, this.messageQueueConnector, this.storage, this.streamsManager, this.roleManager, this.analyticsManager, this.analyticsGroupsManager, this.usersManager, this.notificationsPanel, this.eventsManagerConnector, this.globalSettings, this.cleanerTasksRepository, this.auditor, this.bus, this.analyticsMetadataListenerManager, this.settings);
        }

        public String toString() {
            return "VezhaExtensionContext.Builder(javalin=" + String.valueOf(this.javalin) + ", create=" + String.valueOf(this.create) + ", databaseConnector=" + String.valueOf(this.databaseConnector) + ", messageQueueConnector=" + String.valueOf(this.messageQueueConnector) + ", storage=" + String.valueOf(this.storage) + ", streamsManager=" + String.valueOf(this.streamsManager) + ", roleManager=" + String.valueOf(this.roleManager) + ", analyticsManager=" + String.valueOf(this.analyticsManager) + ", analyticsGroupsManager=" + String.valueOf(this.analyticsGroupsManager) + ", usersManager=" + String.valueOf(this.usersManager) + ", notificationsPanel=" + String.valueOf(this.notificationsPanel) + ", eventsManagerConnector=" + String.valueOf(this.eventsManagerConnector) + ", globalSettings=" + String.valueOf(this.globalSettings) + ", cleanerTasksRepository=" + String.valueOf(this.cleanerTasksRepository) + ", auditor=" + String.valueOf(this.auditor) + ", bus=" + String.valueOf(this.bus) + ", analyticsMetadataListenerManager=" + String.valueOf(this.analyticsMetadataListenerManager) + ", settings=" + String.valueOf(this.settings) + ")";
        }
    }
}

