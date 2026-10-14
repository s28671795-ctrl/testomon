/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.utils;

import com.incoresoft.plugins.models.PluginType;
import java.util.Map;
import java.util.Optional;

public class PluginTypeParser {
    private static final Map<String, PluginType> mappings = Map.of("service", PluginType.SERVICE_PLUGIN, "analytics", PluginType.ANALYTICS_PLUGIN, "event-manager", PluginType.EVENT_MANAGER_PLUGIN);

    public static Optional<PluginType> getByValue(String type) {
        return Optional.ofNullable(mappings.get(type));
    }
}

