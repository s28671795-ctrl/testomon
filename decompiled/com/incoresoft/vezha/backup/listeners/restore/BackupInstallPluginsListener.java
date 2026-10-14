/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.models.PluginType;

public interface BackupInstallPluginsListener {
    public void onStart(String var1, PluginType var2);

    public void onPartDownloaded(String var1, PluginType var2, double var3);

    public void onDownloaded(String var1, PluginType var2);

    public void onInstalled(String var1, PluginType var2);

    public void onError(String var1, PluginType var2, Throwable var3);

    default public InstallationListener getSingleListener(final String pluginId, final PluginType pluginType) {
        return new InstallationListener(){

            @Override
            public void onStart() {
                BackupInstallPluginsListener.this.onStart(pluginId, pluginType);
            }

            @Override
            public void onPartDownloaded(double downloadedPart) {
                BackupInstallPluginsListener.this.onPartDownloaded(pluginId, pluginType, downloadedPart);
            }

            @Override
            public void onDownloaded() {
                BackupInstallPluginsListener.this.onDownloaded(pluginId, pluginType);
            }

            @Override
            public void onPreInstall() {
            }

            @Override
            public void onInstalled() {
                BackupInstallPluginsListener.this.onInstalled(pluginId, pluginType);
            }

            @Override
            public void onError(Throwable throwable) {
                BackupInstallPluginsListener.this.onError(pluginId, pluginType, throwable);
            }
        };
    }
}

