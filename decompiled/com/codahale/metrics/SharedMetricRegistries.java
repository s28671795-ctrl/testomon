/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.MetricRegistry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SharedMetricRegistries {
    private static final ConcurrentMap<String, MetricRegistry> REGISTRIES = new ConcurrentHashMap<String, MetricRegistry>();

    private SharedMetricRegistries() {
    }

    public static void clear() {
        REGISTRIES.clear();
    }

    public static Set<String> names() {
        return REGISTRIES.keySet();
    }

    public static void remove(String key) {
        REGISTRIES.remove(key);
    }

    public static MetricRegistry add(String name, MetricRegistry registry) {
        return REGISTRIES.putIfAbsent(name, registry);
    }

    public static MetricRegistry getOrCreate(String name) {
        MetricRegistry existing = (MetricRegistry)REGISTRIES.get(name);
        if (existing == null) {
            MetricRegistry created = new MetricRegistry();
            MetricRegistry raced = SharedMetricRegistries.add(name, created);
            if (raced == null) {
                return created;
            }
            return raced;
        }
        return existing;
    }
}

