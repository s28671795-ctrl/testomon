/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.event_manager;

import com.google.inject.Inject;
import com.incoresoft.vezha.plugins.VezhaPluginsFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginJarsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginUpdatesManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginVersionsManager;

public class EventManagerPluginsManagerFacade
extends VezhaPluginsFacade {
    @Inject
    public EventManagerPluginsManagerFacade(EventManagerPluginJarsManager pluginJarsManager, EventManagerPluginVersionsManager pluginVersionsManager, EventManagerPluginUpdatesManager pluginUpdatesManager) {
        super(pluginJarsManager, pluginVersionsManager, pluginUpdatesManager);
    }
}

