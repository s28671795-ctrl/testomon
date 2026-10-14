/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.concurrent.GuardedBy;

public class MetadataListenersRegistry<T> {
    @GuardedBy(value="this")
    private final Map<UUID, Set<T>> topicToListenersMap = new HashMap<UUID, Set<T>>();

    public synchronized List<T> unregisterByClassLoader(ClassLoader classLoader) {
        ArrayList<T> unregistered = new ArrayList<T>();
        HashMap newMap = new HashMap();
        for (Map.Entry<UUID, Set<T>> entry : this.topicToListenersMap.entrySet()) {
            HashSet<T> listenersForTopic = new HashSet<T>();
            for (T listener : entry.getValue()) {
                if (listener.getClass().getClassLoader() != classLoader) {
                    listenersForTopic.add(listener);
                    continue;
                }
                unregistered.add(listener);
            }
            if (listenersForTopic.isEmpty()) continue;
            newMap.put(entry.getKey(), listenersForTopic);
        }
        this.topicToListenersMap.putAll(newMap);
        return unregistered;
    }

    public synchronized List<T> unregisterByTopic(UUID topic) {
        return Optional.ofNullable(this.topicToListenersMap.remove(topic)).map(List::copyOf).orElse(Collections.emptyList());
    }

    public synchronized boolean registerIfAbsent(UUID topic, T listener) {
        return this.topicToListenersMap.computeIfAbsent(topic, k -> new HashSet()).add(listener);
    }
}

