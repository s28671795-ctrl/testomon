/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListenerManager;
import com.incoresoft.middleware.analytics.metadata.ListenerFactory;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.analytics.mq.AnalyticsListenerFactoriesRegistry;
import com.incoresoft.vezha.analytics.mq.MetadataListenersRegistry;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsListenerManagerImpl
implements AnalyticsMetadataListenerManager,
AnalyticsStateListener {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsListenerManagerImpl.class);
    private final AnalyticsListenerFactoriesRegistry listenerFactoriesRegistry = new AnalyticsListenerFactoriesRegistry();
    private final MetadataListenersRegistry<ServiceMetadataListener<? extends ServiceMetadata>> serviceListenersRegistry = new MetadataListenersRegistry();
    private final MetadataListenersRegistry<AnalyticsMetadataListener<? extends AnalyticsMetadata>> analyticsListenersRegistry = new MetadataListenersRegistry();
    private final AnalyticsManager analyticsManager;

    @Inject
    public void init() {
        this.analyticsManager.getAll(it -> it.getStatus() == AnalyticsStatus.started).forEach(this::addListener);
        this.analyticsManager.addAnalyticsStateListener(this);
    }

    @Override
    public void onAnalyticsStarted(Analytics analytics) {
        this.addListener(analytics);
    }

    private void addListener(Analytics analytics) {
        UUID topic = analytics.getUUID();
        if (topic == null) {
            log.error("Analytics with id {} has null topic. No subscription will be performed.", (Object)analytics.getId());
            return;
        }
        String pluginName = analytics.getPluginName();
        List<ListenerFactory> listenerFactories = this.listenerFactoriesRegistry.getByPluginName(pluginName);
        if (CollectionUtils.isEmpty(listenerFactories)) {
            log.error("Listener factories for {} was not found.", (Object)pluginName);
            return;
        }
        for (ListenerFactory listenerFactory : listenerFactories) {
            this.subscribeListeners(listenerFactory, topic);
        }
    }

    @Override
    public void onAnalyticsStopped(Analytics analytics) {
        this.removeListeners(analytics);
    }

    @Override
    public void register(ListenerFactory listenerFactory) {
        String pluginName = listenerFactory.getPluginName();
        this.listenerFactoriesRegistry.register(listenerFactory);
        List<UUID> startedAnalyticsTopics = this.analyticsManager.getAll().stream().filter(analytics -> Objects.equals(analytics.getPluginName(), pluginName)).filter(analytics -> analytics.getStatus() == AnalyticsStatus.started).map(Analytics::getUUID).toList();
        for (UUID topic : startedAnalyticsTopics) {
            this.subscribeListeners(listenerFactory, topic);
        }
    }

    public void unregister(VezhaExtension extension) {
        this.listenerFactoriesRegistry.unregisterByExtension(extension);
        this.removeDependentListeners(extension);
        this.removeInternalListeners(extension);
    }

    private void removeInternalListeners(VezhaExtension extension) {
        ClassLoader classLoader = extension.getClass().getClassLoader();
        List<AnalyticsMetadataListener<? extends AnalyticsMetadata>> removedAnalyticsListeners = this.analyticsListenersRegistry.unregisterByClassLoader(classLoader);
        for (AnalyticsMetadataListener<? extends AnalyticsMetadata> listener : removedAnalyticsListeners) {
            this.analyticsManager.removeAnalyticsMetadataListener(listener);
        }
        List<ServiceMetadataListener<? extends ServiceMetadata>> removedServiceListeners = this.serviceListenersRegistry.unregisterByClassLoader(classLoader);
        for (ServiceMetadataListener<? extends ServiceMetadata> listener : removedServiceListeners) {
            this.analyticsManager.removeAnalyticsServiceListener(listener);
        }
    }

    private void removeDependentListeners(VezhaExtension extension) {
        Collection<Analytics> pluginAnalytics = this.analyticsManager.getAll(it -> Objects.equals(it.getPluginName(), extension.getPluginName()));
        for (Analytics singleAnalytics : pluginAnalytics) {
            this.removeListeners(singleAnalytics);
        }
    }

    private void removeListeners(Analytics analytics) {
        List<AnalyticsMetadataListener<? extends AnalyticsMetadata>> removedAnalyticsListeners = this.analyticsListenersRegistry.unregisterByTopic(analytics.getUUID());
        for (AnalyticsMetadataListener<? extends AnalyticsMetadata> removedListener : removedAnalyticsListeners) {
            this.analyticsManager.removeAnalyticsMetadataListener(removedListener);
        }
        List<ServiceMetadataListener<? extends ServiceMetadata>> removedServiceListeners = this.serviceListenersRegistry.unregisterByTopic(analytics.getUUID());
        for (ServiceMetadataListener<? extends ServiceMetadata> removedListener : removedServiceListeners) {
            this.analyticsManager.removeAnalyticsServiceListener(removedListener);
        }
    }

    private void subscribeListeners(ListenerFactory listenerFactory, UUID topic) {
        boolean registered;
        for (AnalyticsMetadataListener<? extends AnalyticsMetadata> analyticsMetadataListener : listenerFactory.createMetadataListeners(topic.toString())) {
            registered = this.analyticsListenersRegistry.registerIfAbsent(topic, analyticsMetadataListener);
            if (!registered) continue;
            this.analyticsManager.addAnalyticsMetadataListener(analyticsMetadataListener);
        }
        for (ServiceMetadataListener serviceMetadataListener : listenerFactory.createServiceListeners(topic.toString())) {
            registered = this.serviceListenersRegistry.registerIfAbsent(topic, serviceMetadataListener);
            if (!registered) continue;
            this.analyticsManager.addAnalyticsServiceListener(serviceMetadataListener);
        }
    }

    @Inject
    public AnalyticsListenerManagerImpl(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }
}

