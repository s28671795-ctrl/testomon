/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.crypto.Cipher;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;

public abstract class ConfigModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(Cipher.class).asEagerSingleton();
        this.bind(DatabaseUrlParsingManager.class).asEagerSingleton();
        this.bind(ConfigConnectionsValidator.class).asEagerSingleton();
        this.bind(this.configManagerClass()).asEagerSingleton();
        this.bind(ConfigManager.class).to(this.configManagerClass());
        this.bind(new TypeLiteral<ConfigManager<?>>(this){}).to(this.configManagerClass());
    }

    @Singleton
    @Provides
    public final CoreService provideCoreService() {
        return this.coreService();
    }

    protected abstract CoreService coreService();

    protected abstract Class<? extends ConfigManager<?>> configManagerClass();
}

