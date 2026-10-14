/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

import com.incoresoft.middleware.mq.MessageQueueConsumer;
import com.incoresoft.middleware.mq.MessageQueuePersistentMessagesListener;

public interface MessageQueueListenerBasedConsumer
extends MessageQueueConsumer {
    public void addListener(MessageQueuePersistentMessagesListener var1);

    public void removeListener(MessageQueuePersistentMessagesListener var1);
}

