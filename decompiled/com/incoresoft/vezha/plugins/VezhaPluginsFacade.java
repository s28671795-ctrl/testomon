/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.BasePluginUpdatesManager;
import com.incoresoft.plugins.BasePluginVersionsManager;
import com.incoresoft.plugins.BasePluginsFacade;

public class VezhaPluginsFacade
extends BasePluginsFacade {
    public VezhaPluginsFacade(BasePluginJarsManager pluginJarsManager, BasePluginVersionsManager pluginVersionsManager, BasePluginUpdatesManager basePluginUpdatesManager) {
        super(pluginJarsManager, pluginVersionsManager, basePluginUpdatesManager);
    }

    public void startAll() {
        this.pluginJarsManager.startAll();
    }

    public void stopAll() {
        this.pluginJarsManager.stopAll();
    }

    public void startPlugin(String pluginId) {
        this.pluginJarsManager.startPlugin(pluginId);
    }

    public void stopPlugin(String pluginId) {
        this.pluginJarsManager.stopPlugin(pluginId);
    }
}

