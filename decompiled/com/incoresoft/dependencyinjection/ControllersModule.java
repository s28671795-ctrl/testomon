/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.http.docs.AsyncApiDocumentationProvider;
import com.incoresoft.plugins.broadcast.BasicPluginSSEBroadcaster;
import com.incoresoft.plugins.broadcast.PluginInstallationsSSEBroadcaster;
import com.incoresoft.plugins.broadcast.PluginStateSSEBroadcaster;
import com.incoresoft.services.installations.CoreServiceInstallationsSSEBroadcaster;
import okhttp3.OkHttpClient;

public class ControllersModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(AsyncApiDocumentationProvider.class).asEagerSingleton();
        this.bind(CoreServiceInstallationsSSEBroadcaster.class).asEagerSingleton();
        this.bind(BasicPluginSSEBroadcaster.class).asEagerSingleton();
        this.bind(PluginInstallationsSSEBroadcaster.class).to(BasicPluginSSEBroadcaster.class);
        this.bind(PluginStateSSEBroadcaster.class).to(BasicPluginSSEBroadcaster.class);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}

