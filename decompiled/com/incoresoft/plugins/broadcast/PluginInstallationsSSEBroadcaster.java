/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast;

import com.incoresoft.plugins.models.PluginType;

public interface PluginInstallationsSSEBroadcaster {
    public void broadcastInstallationStarted(String var1, PluginType var2);

    public void broadcastInstallationProgress(String var1, PluginType var2, double var3);

    public void broadcastInstallationCompleted(String var1, PluginType var2);

    public void broadcastInstallationFailed(String var1, PluginType var2);

    public void broadcastAllPluginsUpdated(PluginType var1);
}

