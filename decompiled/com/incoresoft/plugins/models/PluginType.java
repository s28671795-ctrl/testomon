/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

public enum PluginType {
    SERVICE_PLUGIN,
    ANALYTICS_PLUGIN,
    EVENT_MANAGER_PLUGIN;


    public String toString() {
        return this.name().toLowerCase();
    }
}

