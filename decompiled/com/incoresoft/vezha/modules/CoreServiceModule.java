/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.incoresoft.core.CoreServiceManager;
import com.incoresoft.core.CoreServiceMarketplaceManager;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.core.installations.CoreServiceUpdatesRegistry;
import com.incoresoft.vezha.core.VezhaCoreMarketplaceManager;

public class CoreServiceModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(VezhaCoreMarketplaceManager.class).asEagerSingleton();
        this.bind(CoreServiceMarketplaceManager.class).to(VezhaCoreMarketplaceManager.class).asEagerSingleton();
        this.bind(CoreServiceVersionSupplier.class).asEagerSingleton();
        this.bind(CoreServiceManager.class).asEagerSingleton();
        this.bind(CoreServiceUpdatesRegistry.class).asEagerSingleton();
    }
}

