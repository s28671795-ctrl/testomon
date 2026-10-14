/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsErrorsListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsErrorsListener.class);
    private static final String ERROR_CRASH = "crash";
    private static final String ERROR_FAILED_START = "failed_start";
    private static final String ERROR_FAILED_SERVICE = "failed_service";
    private final AnalyticsManagerImpl analyticsManager;
    private final ServersMonitor serversMonitor;

    public AnalyticsErrorsListener(@NotNull AnalyticsManagerImpl analyticsManager, @NotNull ServersMonitor serversMonitor) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.serversMonitor = Objects.requireNonNull(serversMonitor);
    }

    @Override
    public String subject() {
        return "analyticsErrors";
    }

    @Override
    public Message messageReceived(Message message) {
        JSONObject json = JSON.parseObject(new String(message.getData()));
        String serverId = json.getString("server_id");
        String error = json.getString("error");
        int analyticsId = json.getIntValue("va_id");
        int exitCode = json.getIntValue("code");
        switch (error) {
            case "crash": {
                Server server;
                BaseAnalytics analytics = (BaseAnalytics)this.analyticsManager.get(analyticsId);
                if (analytics == null || (server = this.serversMonitor.getServerById(serverId)) == null) break;
                log.info("ANALYTICS: analytics crashed, sent to queue id " + analytics.getId());
                this.analyticsManager.enqueue(analytics);
                break;
            }
            case "failed_service": 
            case "failed_start": {
                BaseAnalytics analytics = (BaseAnalytics)this.analyticsManager.get(analyticsId);
                if (analytics == null) break;
                if (exitCode == 9) {
                    log.info("ANALYTICS: analytics program outdated, server_id " + serverId);
                } else {
                    log.info("ANALYTICS: analytics failure " + analyticsId + ", server_id " + serverId);
                }
                this.analyticsManager.enqueue(analytics);
                break;
            }
        }
        return ResponseMessage.empty();
    }
}

