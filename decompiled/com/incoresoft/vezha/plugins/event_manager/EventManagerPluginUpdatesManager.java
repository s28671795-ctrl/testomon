/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.event_manager;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.plugins.BasePluginUpdatesManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.broadcast.BasicPluginSSEBroadcaster;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginJarsManager;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginVersionsManager;

public class EventManagerPluginUpdatesManager
extends BasePluginUpdatesManager {
    @Inject
    public EventManagerPluginUpdatesManager(CoreServiceVersionSupplier coreServiceSupplier, EventManagerPluginJarsManager jarsManager, EventManagerPluginVersionsManager versionsManager, BasicPluginSSEBroadcaster broadcaster, JarFileManifestReader manifestReader, InterferingProcessesRegistry processesRegistry) {
        super(coreServiceSupplier, jarsManager, versionsManager, broadcaster, manifestReader, processesRegistry, PluginType.EVENT_MANAGER_PLUGIN);
    }
}

