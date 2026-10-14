/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.crypto.aes128.AES128Wrapper;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsException;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsNotFoundException;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.license.LicenseUsageManager;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetParamsRequestListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(GetParamsRequestListener.class);
    private final AnalyticsManagerImpl analyticsManager;
    private final StreamsManager streamsManager;
    private final LicenseUsageManager licenseUsageManager;
    private final VezhaConfigManager configManager;

    public GetParamsRequestListener(@NotNull AnalyticsManagerImpl analyticsManager, @NotNull StreamsManager streamsManager, @NotNull LicenseUsageManager licenseUsageManager, @NotNull VezhaConfigManager configManager) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.licenseUsageManager = Objects.requireNonNull(licenseUsageManager);
        this.configManager = Objects.requireNonNull(configManager);
    }

    @Override
    public String subject() {
        return "getVideoAnalyticParams";
    }

    @Override
    public Message messageReceived(Message message) {
        String sig;
        JSONObject result = new JSONObject();
        JSONObject jsonMessage = Optional.ofNullable(message).map(Message::getData).map(String::new).map(JSON::parseObject).orElseThrow(RuntimeException::new);
        BaseAnalytics analytics = Optional.ofNullable(jsonMessage).map(it -> it.getIntValue("id")).map(this.analyticsManager::get).map(it -> (BaseAnalytics)it).orElseThrow(AnalyticsNotFoundException::new);
        AnalyticsStatusImpl analyticsStatus = Optional.of(analytics).map(BaseAnalytics::getAnalyticsStatus).orElseThrow(AnalyticsException::new);
        if (analyticsStatus.checkStartSignature(sig = jsonMessage.getString("sig"))) {
            String encrypted;
            String key = analytics.initEncryptionKey();
            JSONObject runParams = Optional.ofNullable((Stream)this.streamsManager.get(analytics.getStreamId())).map(s2 -> analytics.runParams((Stream)s2, this.configManager.isVmsIntegrationEnabled())).orElseThrow(RuntimeException::new);
            runParams.put("license", (Object)this.licenseUsageManager.getLicenseParamsForAnalytics(analytics));
            HashMap<String, String> storageParams = new HashMap<String, String>(((VezhaConfig)this.configManager.getConfig()).getStorage().getProperties());
            storageParams.put("prefix", "analytics/");
            runParams.put("storage", (Object)storageParams);
            try {
                if (this.configManager.isDebugMode()) {
                    log.info("Analytics id: {}, encryption key: {}", (Object)analytics.getId(), (Object)key);
                }
                encrypted = new AES128Wrapper().encode(key, runParams.toJSONString());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            result.put("data", (Object)encrypted);
        } else {
            result.put("error", (Object)true);
            result.put("message", (Object)"Bad request: Couldn't validate analytics start signature");
            log.info("Couldn't validate start signature \"{}\"", (Object)sig);
        }
        return new ResponseMessage(message.getReplyTo(), result.toJSONString().getBytes());
    }
}

