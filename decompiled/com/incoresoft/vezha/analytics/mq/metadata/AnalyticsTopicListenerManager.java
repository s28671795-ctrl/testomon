/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq.metadata;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.analytics.mq.MessageQueueDecryptor;
import com.incoresoft.vezha.analytics.mq.metadata.MetadataMessageQueueListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsTopicListenerManager {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsTopicListenerManager.class);
    private final Map<AnalyticsMetadataListener<? extends AnalyticsMetadata>, MessageQueueListener> metadataListeners = new ConcurrentHashMap<AnalyticsMetadataListener<? extends AnalyticsMetadata>, MessageQueueListener>();
    private final MessageQueueDecryptor decryptor;
    private final MessageQueueConnector messageQueueConnector;

    public void addAnalyticsMetadataListener(AnalyticsMetadataListener<? extends AnalyticsMetadata> listener) {
        Objects.requireNonNull(listener, "listener was null");
        String metadataSubject = listener.analyticsTopic();
        if (metadataSubject == null) {
            throw new NullPointerException("AnalyticsMetadataListener subject cannot be null");
        }
        if (this.metadataListeners.containsKey(listener)) {
            log.info("Listener {} with topic {} is already subscribed.", (Object)listener, (Object)listener.analyticsTopic());
            return;
        }
        MetadataMessageQueueListener<? extends AnalyticsMetadata> messageQueueListener = new MetadataMessageQueueListener<AnalyticsMetadata>(listener, this.decryptor);
        this.messageQueueConnector.addListener(messageQueueListener);
        this.metadataListeners.put(listener, messageQueueListener);
        log.info("Listener {} with topic {} subscribed.", (Object)listener, (Object)listener.analyticsTopic());
    }

    public void removeAnalyticsMetadataListener(AnalyticsMetadataListener<?> listener) {
        Objects.requireNonNull(listener, "listener was null");
        MessageQueueListener messageQueueListener = this.metadataListeners.get(listener);
        if (messageQueueListener == null) {
            log.info("Listener {} with topic {} was never subscribed.", (Object)listener, (Object)listener.analyticsTopic());
            return;
        }
        this.messageQueueConnector.removeListener(messageQueueListener);
        this.metadataListeners.remove(listener);
        log.info("Listener {} with topic {} unsubscribed.", (Object)listener, (Object)listener.analyticsTopic());
    }

    public void unregisterExtension(VezhaExtension extension) {
        this.metadataListeners.keySet().stream().filter(listener -> Objects.equals(listener.pluginName(), extension.getPluginName())).forEach(this::removeAnalyticsMetadataListener);
    }

    @Inject
    public AnalyticsTopicListenerManager(MessageQueueDecryptor decryptor, MessageQueueConnector messageQueueConnector) {
        this.decryptor = decryptor;
        this.messageQueueConnector = messageQueueConnector;
    }
}

