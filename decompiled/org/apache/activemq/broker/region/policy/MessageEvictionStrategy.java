/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.io.IOException;
import java.util.LinkedList;
import org.apache.activemq.broker.region.MessageReference;

public interface MessageEvictionStrategy {
    public MessageReference[] evictMessages(LinkedList var1) throws IOException;

    public int getEvictExpiredMessagesHighWatermark();
}

