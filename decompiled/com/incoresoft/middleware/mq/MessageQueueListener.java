/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.incoresoft.middleware.mq.Message;

public interface MessageQueueListener {
    public String subject();

    public Message messageReceived(Message var1);
}

