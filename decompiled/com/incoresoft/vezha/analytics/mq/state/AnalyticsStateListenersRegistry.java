/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq.state;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsStateListenersRegistry {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsStateListenersRegistry.class);
    private final Set<AnalyticsStateListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap());

    public void addListener(AnalyticsStateListener listener) {
        this.listeners.add(Objects.requireNonNull(listener, "listener cannot be null"));
    }

    public void removeListener(AnalyticsStateListener listener) {
        this.listeners.remove(Objects.requireNonNull(listener, "listener cannot be null"));
    }

    public void onStatusChanged(Analytics analytics) {
        Map<AnalyticsStatus, Consumer<AnalyticsStateListener>> statusToActionMap = Map.of(AnalyticsStatus.started, listener -> listener.onAnalyticsStarted(analytics), AnalyticsStatus.stopped, listener -> listener.onAnalyticsStopped(analytics), AnalyticsStatus.processing, listener -> listener.onAnalyticsPending(analytics), AnalyticsStatus.paused, listener -> listener.onAnalyticsPaused(analytics), AnalyticsStatus.deleted, listener -> listener.onAnalyticsDeleted(analytics));
        Consumer<AnalyticsStateListener> action = statusToActionMap.get((Object)analytics.getStatus());
        if (action != null) {
            ListenersUtils.triggerListeners(action, this.listeners);
        } else {
            log.warn("Unhandled analytics status: {}", (Object)analytics.getStatus());
        }
    }
}

