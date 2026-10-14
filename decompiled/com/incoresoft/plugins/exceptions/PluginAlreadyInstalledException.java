/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

public class PluginAlreadyInstalledException
extends RuntimeException {
    private final String pluginId;

    public PluginAlreadyInstalledException(String pluginId) {
        super(String.format("Plugin with id %s is already installed", pluginId));
        this.pluginId = pluginId;
    }

    public String getPluginId() {
        return this.pluginId;
    }
}

