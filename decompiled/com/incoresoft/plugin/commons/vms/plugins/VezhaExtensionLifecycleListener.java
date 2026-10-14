/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.plugins.extensions.VezhaExtensionLifecycleEventDTO;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VezhaExtensionLifecycleListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(VezhaExtensionLifecycleListener.class);

    @Inject
    public void init(MessageQueueConnector connector) {
        connector.addListener(this);
    }

    @Override
    public String subject() {
        return String.format("vezha.plugins.%s.lifecycle", this.getVezhaExtensionName());
    }

    @Override
    public Message messageReceived(Message message) {
        try {
            VezhaExtensionLifecycleEventDTO eventDto = Serialization.objectMapper().readValue(message.getData(), VezhaExtensionLifecycleEventDTO.class);
            switch (eventDto.action()) {
                case INITIALIZED: {
                    this.onVezhaExtensionInitialized();
                    break;
                }
                case TERMINATED: {
                    this.onVezhaExtensionTerminated();
                }
            }
        }
        catch (IOException e) {
            log.error("Failed to parse extension lifecycle event", e);
        }
        catch (RuntimeException re) {
            log.error("Failed to process extension lifecycle event", re);
        }
        return null;
    }

    protected abstract String getVezhaExtensionName();

    protected abstract void onVezhaExtensionInitialized();

    protected abstract void onVezhaExtensionTerminated();
}

