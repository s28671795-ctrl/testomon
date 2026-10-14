/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.connector.Connector;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConsumer;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.middleware.mq.MessageQueueStream;
import com.incoresoft.middleware.mq.configs.MQStreamConfigDTO;
import java.time.Duration;
import java.util.Optional;
import org.pf4j.ExtensionPoint;

public interface MessageQueueConnector
extends ExtensionPoint,
Connector {
    public void addListener(MessageQueueListener var1);

    public boolean removeListener(MessageQueueListener var1);

    @Deprecated
    public void publish(String var1, JSONObject var2);

    @Deprecated
    public void publish(String var1, Message var2);

    public void publish(String var1, byte[] var2);

    @Deprecated
    public Message request(String var1, Message var2, Duration var3);

    public Message request(String var1, byte[] var2, Duration var3);

    public void startConsumer(MessageQueueConsumer var1);

    public void stopConsumer(String var1);

    public Optional<MessageQueueStream> getStream(String var1);

    public MessageQueueStream createOrUpdateStream(MQStreamConfigDTO var1);
}

