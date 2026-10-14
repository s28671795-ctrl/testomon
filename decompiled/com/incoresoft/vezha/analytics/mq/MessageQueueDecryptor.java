/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.crypto.Cipher;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsEncryptionException;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsNotFoundException;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageQueueDecryptor {
    private static final Logger log = LoggerFactory.getLogger(MessageQueueDecryptor.class);
    private static final String DEBUG_SIG = "4SDMI85aUgvHf2EdJY2Fy0cFZTNRrJ9w";
    private final Cipher cipher;
    private final AnalyticsManager analyticsManager;

    @Inject
    public MessageQueueDecryptor(@NotNull Cipher cipher, @NotNull AnalyticsManager analyticsManager) {
        this.cipher = Objects.requireNonNull(cipher);
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
    }

    public String decrypt(Message message) throws AnalyticsEncryptionException {
        JSONObject json = Optional.ofNullable(message).map(Message::getData).map(String::new).map(JSON::parseObject).orElseThrow(RuntimeException::new);
        BaseAnalytics analytics = Optional.of(json.getIntValue("va_id")).map(this.analyticsManager::get).map(it -> (BaseAnalytics)it).orElseThrow(AnalyticsNotFoundException::new);
        analytics.getAnalyticsStatus().updateLastMessage();
        try {
            String encryptionKey = Optional.of(analytics.getAnalyticsStatus()).map(AnalyticsStatusImpl::getEncryptionKeyValue).orElseThrow(AnalyticsEncryptionException::new);
            String encrypted = json.getString("data");
            String result = this.cipher.decrypt(encryptionKey, encrypted);
            JSON.parse(result);
            return result;
        }
        catch (Exception e) {
            log.error("AnalyticsEncryptionException for analytics {}. Encryption for this analytics will be reinitialized.", (Object)analytics.getId());
            this.analyticsManager.updateSignature(analytics);
            throw new AnalyticsEncryptionException();
        }
    }
}

