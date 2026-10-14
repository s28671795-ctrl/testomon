/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.JavalinEndpointTracker;
import com.incoresoft.plugins.PluginIconEndpointInitializer;
import com.incoresoft.plugins.configurations.PluginConfigurationsManager;

public class VezhaPluginsModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(PluginIconEndpointInitializer.class).asEagerSingleton();
        this.bind(JavalinEndpointTracker.class).asEagerSingleton();
        this.bind(PluginConfigurationsManager.class).asEagerSingleton();
        this.bind(JarFileManifestReader.class).asEagerSingleton();
        this.bind(MarketplaceClient.class).asEagerSingleton();
    }
}

