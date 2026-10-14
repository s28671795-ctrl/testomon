/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.SizeStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public abstract class AbstractMessageStoreStatistics
extends StatsImpl {
    protected final CountStatisticImpl messageCount;
    protected final SizeStatisticImpl messageSize;

    protected AbstractMessageStoreStatistics(String countDescription, String sizeDescription) {
        this(true, countDescription, sizeDescription);
    }

    protected AbstractMessageStoreStatistics(boolean enabled, String countDescription, String sizeDescription) {
        this.messageCount = new CountStatisticImpl("messageCount", countDescription);
        this.messageSize = new SizeStatisticImpl("messageSize", sizeDescription);
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

    public void setParent(AbstractMessageStoreStatistics parent) {
        if (parent != null) {
            this.messageCount.setParent(parent.messageCount);
            this.messageSize.setParent(parent.messageSize);
        } else {
            this.messageCount.setParent(null);
            this.messageSize.setParent(null);
        }
    }
}

