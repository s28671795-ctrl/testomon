/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

public class PluginIsBeingInstalledException
extends RuntimeException {
    private final String pluginId;

    public PluginIsBeingInstalledException(String pluginId) {
        super(String.format("Plugin with id %s is being installed", pluginId));
        this.pluginId = pluginId;
    }

    public String getPluginId() {
        return this.pluginId;
    }
}

