/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.analytics;

import com.google.inject.Inject;
import com.incoresoft.vezha.plugins.VezhaPluginsFacade;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginJarsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginUpdatesManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginVersionsManager;

public class AnalyticsPluginsManagerFacade
extends VezhaPluginsFacade {
    @Inject
    public AnalyticsPluginsManagerFacade(AnalyticsPluginJarsManager pluginJarsManager, AnalyticsPluginVersionsManager pluginVersionsManager, AnalyticsPluginUpdatesManager pluginUpdatesManager) {
        super(pluginJarsManager, pluginVersionsManager, pluginUpdatesManager);
    }
}

