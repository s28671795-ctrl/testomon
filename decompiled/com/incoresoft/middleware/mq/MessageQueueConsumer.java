/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.configs.MQConsumerConfigDTO;

public interface MessageQueueConsumer {
    public String getId();

    public MQConsumerConfigDTO getConfig();

    public void consume(Message var1);

    public String getStreamName();
}

