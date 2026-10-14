/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.events;

import com.incoresoft.plugins.broadcast.events.AllPluginsUpdatedEvent;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationCompletedEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationFailedEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationProgressEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationStartedEvent;
import com.incoresoft.plugins.broadcast.events.PluginStartedEvent;
import com.incoresoft.plugins.broadcast.events.PluginStoppedEvent;
import com.incoresoft.plugins.broadcast.events.PluginUninstalledEvent;

public enum PluginEventType {
    INSTALLATION_STARTED(PluginInstallationStartedEvent.class),
    INSTALLATION_PROGRESS(PluginInstallationProgressEvent.class),
    INSTALLATION_COMPLETED(PluginInstallationCompletedEvent.class),
    INSTALLATION_FAILED(PluginInstallationFailedEvent.class),
    ALL_UPDATED(AllPluginsUpdatedEvent.class),
    PLUGIN_STARTED(PluginStartedEvent.class),
    PLUGIN_STOPPED(PluginStoppedEvent.class),
    PLUGIN_UNINSTALLED(PluginUninstalledEvent.class),
    UNKNOWN(null);

    private final Class<? extends PluginEvent> messageClass;

    public String toString() {
        return this.name().toLowerCase();
    }

    public static PluginEventType parse(String value) {
        return PluginEventType.valueOf(value.toUpperCase());
    }

    public static PluginEventType getByMessageClass(Class<? extends PluginEvent> messageClass) {
        for (PluginEventType messageType : PluginEventType.values()) {
            if (!messageType.getMessageClass().equals(messageClass)) continue;
            return messageType;
        }
        return UNKNOWN;
    }

    public Class<? extends PluginEvent> getMessageClass() {
        return this.messageClass;
    }

    private PluginEventType(Class<? extends PluginEvent> messageClass) {
        this.messageClass = messageClass;
    }
}

