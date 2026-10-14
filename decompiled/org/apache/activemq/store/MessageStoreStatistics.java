/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.SizeStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class MessageStoreStatistics
extends StatsImpl {
    protected CountStatisticImpl messageCount = new CountStatisticImpl("messageCount", "The number of messages in the store passing through the destination");
    protected SizeStatisticImpl messageSize = new SizeStatisticImpl("messageSize", "Size of messages in the store passing through the destination");

    public MessageStoreStatistics() {
        this(true);
    }

    public MessageStoreStatistics(boolean enabled) {
        this.addStatistic("messageCount", this.messageCount);
        this.addStatistic("messageSize", this.messageSize);
        this.setEnabled(enabled);
    }

    public CountStatisticImpl getMessageCount() {
        return this.messageCount;
    }

    public SizeStatisticImpl getMessageSize() {
        return this.messageSize;
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.messageCount.reset();
            this.messageSize.reset();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.messageCount.setEnabled(enabled);
        this.messageSize.setEnabled(enabled);
    }

    public void setParent(MessageStoreStatistics parent) {
        if (parent != null) {
            this.messageCount.setParent(parent.messageCount);
            this.messageSize.setParent(parent.messageSize);
        } else {
            this.messageCount.setParent(null);
            this.messageSize.setParent(null);
        }
    }
}

