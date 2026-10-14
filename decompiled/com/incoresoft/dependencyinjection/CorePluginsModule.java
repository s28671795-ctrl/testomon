/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugins.CorePluginsManager;

public class CorePluginsModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(CorePluginsManager.class).asEagerSingleton();
    }

    @Provides
    @Inject
    @Singleton
    public DatabaseConnector provideDatabaseConnector(CorePluginsManager pluginManager) {
        return pluginManager.getDatabaseConnector();
    }

    @Provides
    @Inject
    @Singleton
    public MessageQueueConnector provideMqConnection(CorePluginsManager pluginManager) {
        return pluginManager.getMessageQueueConnector();
    }

    @Provides
    @Inject
    @Singleton
    public Storage provideStorage(CorePluginsManager pluginManager) {
        return pluginManager.getStorage();
    }
}

