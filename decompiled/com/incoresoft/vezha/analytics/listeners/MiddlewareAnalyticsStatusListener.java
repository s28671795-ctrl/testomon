/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class MiddlewareAnalyticsStatusListener
implements MessageQueueListener {
    private final AnalyticsWSBroadcaster analyticsWsBroadcaster;

    public MiddlewareAnalyticsStatusListener(@NotNull AnalyticsWSBroadcaster analyticsWsBroadcaster) {
        this.analyticsWsBroadcaster = Objects.requireNonNull(analyticsWsBroadcaster);
    }

    @Override
    public String subject() {
        return "middleware-notifications";
    }

    @Override
    public Message messageReceived(Message message) {
        JSONObject jsonObject = JSON.parseObject(new String(message.getData()));
        this.analyticsWsBroadcaster.broadcast(jsonObject);
        return null;
    }
}

