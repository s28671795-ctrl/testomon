/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class NetworkBridgeStatistics
extends StatsImpl {
    protected CountStatisticImpl enqueues = new CountStatisticImpl("enqueues", "The current number of enqueues this bridge has, which is the number of potential messages to be forwarded.");
    protected CountStatisticImpl dequeues = new CountStatisticImpl("dequeues", "The current number of dequeues this bridge has, which is the number of messages received by the remote broker.");
    protected CountStatisticImpl receivedCount = new CountStatisticImpl("receivedCount", "The number of messages that have been received by the NetworkBridge from the remote broker.  Only applies for Duplex bridges.");

    public NetworkBridgeStatistics() {
        this.addStatistic("enqueues", this.enqueues);
        this.addStatistic("dequeues", this.dequeues);
        this.addStatistic("receivedCount", this.receivedCount);
    }

    public CountStatisticImpl getEnqueues() {
        return this.enqueues;
    }

    public CountStatisticImpl getDequeues() {
        return this.dequeues;
    }

    public CountStatisticImpl getReceivedCount() {
        return this.receivedCount;
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.enqueues.reset();
            this.dequeues.reset();
            this.receivedCount.reset();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enqueues.setEnabled(enabled);
        this.dequeues.setEnabled(enabled);
        this.receivedCount.setEnabled(enabled);
    }

    public void setParent(NetworkBridgeStatistics parent) {
        if (parent != null) {
            this.enqueues.setParent(parent.enqueues);
            this.dequeues.setParent(parent.dequeues);
            this.receivedCount.setParent(parent.receivedCount);
        } else {
            this.enqueues.setParent(null);
            this.dequeues.setParent(null);
            this.receivedCount.setParent(null);
        }
    }
}

