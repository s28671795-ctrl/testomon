/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.extensions;

import com.incoresoft.middleware.plugin.InitializableExtension;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ExtensionsRegistry {
    private final Map<String, Set<InitializableExtension<?>>> pluginExtensionsMap = new ConcurrentHashMap();

    ExtensionsRegistry() {
    }

    public void register(String pluginId, InitializableExtension<?> extension) {
        this.pluginExtensionsMap.computeIfAbsent(pluginId, k -> Collections.newSetFromMap(new ConcurrentHashMap())).add(extension);
    }

    public Set<InitializableExtension<?>> unregister(String pluginId) {
        Set<InitializableExtension<?>> removed = this.pluginExtensionsMap.remove(pluginId);
        return removed == null ? Set.of() : Collections.unmodifiableSet(removed);
    }

    public Set<? extends InitializableExtension<?>> getAll(String pluginId) {
        return this.pluginExtensionsMap.getOrDefault(pluginId, Set.of());
    }

    public Map<String, Set<? extends InitializableExtension<?>>> getAll() {
        return this.pluginExtensionsMap.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, e -> Collections.unmodifiableSet((Set)e.getValue())));
    }

    public String getPluginIdByExtension(InitializableExtension<?> extension) {
        return this.pluginExtensionsMap.entrySet().stream().filter(entry -> ((Set)entry.getValue()).contains(extension)).findFirst().map(Map.Entry::getKey).orElseThrow();
    }
}

