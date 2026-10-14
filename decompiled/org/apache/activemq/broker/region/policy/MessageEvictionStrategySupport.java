/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.policy.MessageEvictionStrategy;

public abstract class MessageEvictionStrategySupport
implements MessageEvictionStrategy {
    private int evictExpiredMessagesHighWatermark = 1000;

    @Override
    public int getEvictExpiredMessagesHighWatermark() {
        return this.evictExpiredMessagesHighWatermark;
    }

    public void setEvictExpiredMessagesHighWatermark(int evictExpiredMessagesHighWaterMark) {
        this.evictExpiredMessagesHighWatermark = evictExpiredMessagesHighWaterMark;
    }
}

