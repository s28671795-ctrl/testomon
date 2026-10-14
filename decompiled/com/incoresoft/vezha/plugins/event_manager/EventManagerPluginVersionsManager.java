/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.event_manager;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.plugins.BasePluginVersionsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginJarsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsMarketplaceManager;

public class EventManagerPluginVersionsManager
extends BasePluginVersionsManager {
    @Inject
    public EventManagerPluginVersionsManager(EventManagerPluginJarsManager systemVezhaPluginsManager, EventManagerPluginsMarketplaceManager marketplaceManager, CoreServiceVersionSupplier coreServiceVersionSupplier) {
        super(systemVezhaPluginsManager, marketplaceManager, coreServiceVersionSupplier);
    }
}

