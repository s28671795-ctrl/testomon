/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsEncryptionException;
import com.incoresoft.vezha.analytics.mq.MessageQueueDecryptor;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceMessageQueueListener<T extends ServiceMetadata>
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(ServiceMessageQueueListener.class);
    private final ServiceMetadataListener<T> listener;
    private final MessageQueueDecryptor decryptor;

    public ServiceMessageQueueListener(@NotNull ServiceMetadataListener<T> listener, @NotNull MessageQueueDecryptor decryptor) {
        this.listener = Objects.requireNonNull(listener);
        this.decryptor = Objects.requireNonNull(decryptor);
    }

    @Override
    public String subject() {
        return this.listener.analyticsTopic() + "." + this.listener.getToken();
    }

    @Override
    public Message messageReceived(Message message) {
        String decryptedMessage;
        try {
            decryptedMessage = this.decryptor.decrypt(message);
        }
        catch (AnalyticsEncryptionException e) {
            return null;
        }
        log.debug("Decrypted message from analytics: {}", (Object)decryptedMessage);
        try {
            this.listener.onMetadata((ServiceMetadata)JSONObject.parseObject(decryptedMessage, this.listener.metadataClass()));
        }
        catch (Throwable e) {
            log.error("Error has occurred while processing metadata.", e);
        }
        return null;
    }
}

