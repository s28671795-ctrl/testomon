/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast;

import com.incoresoft.plugins.models.PluginType;

public interface PluginStateSSEBroadcaster {
    public void broadcastPluginStarted(String var1, PluginType var2);

    public void broadcastPluginStopped(String var1, PluginType var2);

    public void broadcastPluginUninstalled(String var1, PluginType var2);
}

