/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.analytics;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.plugins.BasePluginUpdatesManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.broadcast.BasicPluginSSEBroadcaster;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginJarsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginVersionsManager;

public class AnalyticsPluginUpdatesManager
extends BasePluginUpdatesManager {
    @Inject
    public AnalyticsPluginUpdatesManager(CoreServiceVersionSupplier coreServiceSupplier, AnalyticsPluginJarsManager jarsManager, AnalyticsPluginVersionsManager versionsManager, BasicPluginSSEBroadcaster broadcaster, JarFileManifestReader manifestReader, InterferingProcessesRegistry processesRegistry) {
        super(coreServiceSupplier, jarsManager, versionsManager, broadcaster, manifestReader, processesRegistry, PluginType.ANALYTICS_PLUGIN);
    }
}

