/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq;

import com.incoresoft.middleware.analytics.metadata.ListenerFactory;
import com.incoresoft.middleware.plugin.VezhaExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class AnalyticsListenerFactoriesRegistry {
    @GuardedBy(value="this")
    private final Map<String, List<ListenerFactory>> listetingPluginNameToFactoriesMap = new HashMap<String, List<ListenerFactory>>();

    public synchronized void register(ListenerFactory factory2) {
        this.listetingPluginNameToFactoriesMap.computeIfAbsent(factory2.getPluginName(), k -> new ArrayList()).add(factory2);
    }

    public synchronized void unregisterByExtension(VezhaExtension extension) {
        ClassLoader classLoader = extension.getClass().getClassLoader();
        for (Map.Entry<String, List<ListenerFactory>> entry : this.listetingPluginNameToFactoriesMap.entrySet()) {
            entry.getValue().removeIf(factory2 -> factory2.getClass().getClassLoader() == classLoader);
        }
    }

    public synchronized List<ListenerFactory> getByPluginName(String pluginName) {
        return this.listetingPluginNameToFactoriesMap.get(pluginName);
    }
}

