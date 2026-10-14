/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.configs.MQStreamConfigDTO;

public interface MessageQueueStream {
    public void publish(Message var1);

    public MQStreamConfigDTO getConfig();
}

