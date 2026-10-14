/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionListener;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.plugins.extensions.VezhaExtensionLifecycleAction;
import com.incoresoft.plugin.commons.plugins.extensions.VezhaExtensionLifecycleEventDTO;
import com.incoresoft.vezha.protocol.server.HttpServerInitActionsExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginExtensionsLifecycleEventsPublisher
implements ExtensionListener {
    private static final Logger log = LoggerFactory.getLogger(PluginExtensionsLifecycleEventsPublisher.class);
    private final MessageQueueConnector messageQueueConnector;
    private final HttpServerInitActionsExecutor httpServerInitActionsExecutor;

    @Override
    public void onExtensionInit(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            this.httpServerInitActionsExecutor.onHttpServerInit(() -> this.publish(vezhaExtension, VezhaExtensionLifecycleAction.INITIALIZED));
        }
    }

    @Override
    public void onExtensionTerminate(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            this.publish(vezhaExtension, VezhaExtensionLifecycleAction.TERMINATED);
        }
    }

    private void publish(VezhaExtension vezhaExtension, VezhaExtensionLifecycleAction action) {
        String subject = String.format("vezha.plugins.%s.lifecycle", vezhaExtension.getPluginName());
        VezhaExtensionLifecycleEventDTO dto = new VezhaExtensionLifecycleEventDTO(action);
        try {
            byte[] body2 = Serialization.objectMapper().writeValueAsBytes(dto);
            this.messageQueueConnector.publish(subject, body2);
            log.info("Published extension lifecycle event: {}", (Object)dto);
        }
        catch (JsonProcessingException e) {
            log.error("Failed to publish extension lifecycle event", e);
        }
    }

    @Inject
    public PluginExtensionsLifecycleEventsPublisher(MessageQueueConnector messageQueueConnector, HttpServerInitActionsExecutor httpServerInitActionsExecutor) {
        this.messageQueueConnector = messageQueueConnector;
        this.httpServerInitActionsExecutor = httpServerInitActionsExecutor;
    }
}

