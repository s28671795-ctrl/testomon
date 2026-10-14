/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.incoresoft.middleware.mq.Message;

public interface MessageQueuePersistentMessagesListener {
    public void onMessage(Message var1);

    public String subject();
}

