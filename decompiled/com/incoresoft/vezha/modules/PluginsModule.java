/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.JavalinEndpointTracker;
import com.incoresoft.plugins.PluginIconEndpointInitializer;
import com.incoresoft.vezha.plugins.PluginExtensionsLifecycleEventsPublisher;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginJarsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginUpdatesManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginVersionsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsMarketplaceManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginJarsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginUpdatesManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginVersionsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsMarketplaceManager;

public class PluginsModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(PluginExtensionsLifecycleEventsPublisher.class).asEagerSingleton();
        this.bind(MarketplaceClient.class).asEagerSingleton();
        this.bind(PluginIconEndpointInitializer.class).asEagerSingleton();
        this.bind(JavalinEndpointTracker.class).asEagerSingleton();
        this.bind(JarFileManifestReader.class).asEagerSingleton();
        this.bind(AnalyticsPluginsMarketplaceManager.class).asEagerSingleton();
        this.bind(AnalyticsPluginJarsManager.class).asEagerSingleton();
        this.bind(AnalyticsPluginVersionsManager.class).asEagerSingleton();
        this.bind(AnalyticsPluginUpdatesManager.class).asEagerSingleton();
        this.bind(AnalyticsPluginsManagerFacade.class).asEagerSingleton();
        this.bind(EventManagerPluginsMarketplaceManager.class).asEagerSingleton();
        this.bind(EventManagerPluginJarsManager.class).asEagerSingleton();
        this.bind(EventManagerPluginVersionsManager.class).asEagerSingleton();
        this.bind(EventManagerPluginUpdatesManager.class).asEagerSingleton();
        this.bind(EventManagerPluginsManagerFacade.class).asEagerSingleton();
    }
}

