/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.eventmanager.EventsManagerConnector;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.vezha.eventmanager.EventManager;
import com.incoresoft.vezha.notifications.NotificationsPanelImpl;
import com.incoresoft.vezha.plugins.EventsManagerConnectorImpl;
import com.incoresoft.vezha.plugins.VezhaExtensionContext;

public class VezhaPluginsModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(NotificationsPanel.class).to(NotificationsPanelImpl.class).asEagerSingleton();
        this.bind(ExtensionContext.class).to(VezhaExtensionContext.class).asEagerSingleton();
    }

    @Provides
    @Inject
    @Singleton
    public EventsManagerConnector provideEventsManagerConnector(AnalyticsManager analyticsManager, EventManager eventManager) {
        return new EventsManagerConnectorImpl(eventManager, analyticsManager);
    }
}

