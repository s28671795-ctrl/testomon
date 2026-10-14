/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq.service;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.analytics.mq.MessageQueueDecryptor;
import com.incoresoft.vezha.analytics.mq.service.ServiceMessageQueueListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsServiceListenerManager {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsServiceListenerManager.class);
    private final Map<ServiceMetadataListener<? extends ServiceMetadata>, MessageQueueListener> serviceListeners = new ConcurrentHashMap<ServiceMetadataListener<? extends ServiceMetadata>, MessageQueueListener>();
    private final MessageQueueConnector messageQueueConnector;
    private final MessageQueueDecryptor decryptor;

    public <T extends ServiceMetadata> void addAnalyticsServiceListener(ServiceMetadataListener<T> listener) {
        Objects.requireNonNull(listener, "listener was null");
        String metadataSubject = listener.analyticsTopic();
        if (metadataSubject == null) {
            throw new NullPointerException("ServiceMetadataListener subject cannot be null");
        }
        if (this.serviceListeners.containsKey(listener)) {
            log.info("Listener {} with topic {} is already subscribed.", (Object)listener, (Object)listener.analyticsTopic());
            return;
        }
        ServiceMessageQueueListener<T> serviceMessageQueueListener = new ServiceMessageQueueListener<T>(listener, this.decryptor);
        this.messageQueueConnector.addListener(serviceMessageQueueListener);
        this.serviceListeners.put(listener, serviceMessageQueueListener);
        log.info("Listener {} with topic {} subscribed.", (Object)listener, (Object)listener.analyticsTopic());
    }

    public void removeAnalyticsServiceListener(ServiceMetadataListener<? extends ServiceMetadata> listener) {
        Objects.requireNonNull(listener, "listener was null");
        MessageQueueListener messageQueueListener = this.serviceListeners.get(listener);
        if (messageQueueListener == null) {
            log.info("Listener {} with topic {} was never subscribed.", (Object)listener, (Object)listener.analyticsTopic());
            return;
        }
        this.messageQueueConnector.removeListener(messageQueueListener);
        this.serviceListeners.remove(listener);
        log.info("Listener {} with topic {} unsubscribed.", (Object)listener, (Object)listener.analyticsTopic());
    }

    public void unregisterExtension(VezhaExtension extension) {
        this.serviceListeners.keySet().stream().filter(listener -> Objects.equals(listener.pluginName(), extension.getPluginName())).forEach(this::removeAnalyticsServiceListener);
    }

    @Inject
    public AnalyticsServiceListenerManager(MessageQueueConnector messageQueueConnector, MessageQueueDecryptor decryptor) {
        this.messageQueueConnector = messageQueueConnector;
        this.decryptor = decryptor;
    }
}

