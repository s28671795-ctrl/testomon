/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq.metadata;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsEncryptionException;
import com.incoresoft.vezha.analytics.mq.MessageQueueDecryptor;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataMessageQueueListener<T extends AnalyticsMetadata>
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(MetadataMessageQueueListener.class);
    private final AnalyticsMetadataListener<T> listener;
    private final MessageQueueDecryptor decryptor;

    public MetadataMessageQueueListener(@NotNull AnalyticsMetadataListener<T> listener, @NotNull MessageQueueDecryptor decryptor) {
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
            decryptedMessage = this.cropJson(this.decryptor.decrypt(message));
        }
        catch (AnalyticsEncryptionException e) {
            return null;
        }
        log.debug("Decrypted message from analytics: {}", (Object)decryptedMessage);
        try {
            this.listener.onMetadata((AnalyticsMetadata)JSONObject.parseObject(decryptedMessage, this.listener.metadataClass()));
        }
        catch (Throwable e) {
            log.info("Decrypted message from analytics: {}", (Object)decryptedMessage);
            log.error("Error has occurred while processing metadata.", e);
        }
        return null;
    }

    private String cropJson(String json) {
        int lastIndex = json.lastIndexOf("}");
        if (lastIndex != -1) {
            return json.substring(0, lastIndex + 1);
        }
        return json;
    }
}

