/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

public class PluginInitializationException
extends RuntimeException {
    private final String pluginId;

    public PluginInitializationException(String pluginId, Throwable cause) {
        super(cause);
        this.pluginId = pluginId;
    }

    public String getPluginId() {
        return this.pluginId;
    }
}

