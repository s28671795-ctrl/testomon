/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.buffer;

import org.apache.activemq.memory.buffer.MessageQueue;

public interface MessageBuffer {
    public int getSize();

    public MessageQueue createMessageQueue();

    public void onSizeChanged(MessageQueue var1, int var2, int var3);

    public void clear();
}

