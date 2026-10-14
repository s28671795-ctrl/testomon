/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.SizeStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class SubscriptionStatistics
extends StatsImpl {
    protected CountStatisticImpl consumedCount = new CountStatisticImpl("consumedCount", "The number of messages that have been consumed by the subscription");
    protected CountStatisticImpl enqueues = new CountStatisticImpl("enqueues", "The number of messages that have been sent to the subscription");
    protected CountStatisticImpl dequeues;
    protected CountStatisticImpl dispatched = new CountStatisticImpl("dispatched", "The number of messages that have been dispatched from the subscription");
    protected SizeStatisticImpl inflightMessageSize;

    public SubscriptionStatistics() {
        this(true);
    }

    public SubscriptionStatistics(boolean enabled) {
        this.dequeues = new CountStatisticImpl("dequeues", "The number of messages that have been acknowledged from the subscription");
        this.inflightMessageSize = new SizeStatisticImpl("inflightMessageSize", "The size in bytes of messages dispatched but awaiting acknowledgement");
        this.addStatistic("consumedCount", this.consumedCount);
        this.addStatistic("enqueues", this.enqueues);
        this.addStatistic("dispatched", this.dispatched);
        this.addStatistic("dequeues", this.dequeues);
        this.addStatistic("inflightMessageSize", this.inflightMessageSize);
        this.setEnabled(enabled);
    }

    public CountStatisticImpl getConsumedCount() {
        return this.consumedCount;
    }

    public CountStatisticImpl getEnqueues() {
        return this.enqueues;
    }

    public CountStatisticImpl getDequeues() {
        return this.dequeues;
    }

    public CountStatisticImpl getDispatched() {
        return this.dispatched;
    }

    public SizeStatisticImpl getInflightMessageSize() {
        return this.inflightMessageSize;
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.consumedCount.reset();
            this.enqueues.reset();
            this.dequeues.reset();
            this.dispatched.reset();
            this.inflightMessageSize.reset();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.consumedCount.setEnabled(enabled);
        this.enqueues.setEnabled(enabled);
        this.dispatched.setEnabled(enabled);
        this.dequeues.setEnabled(enabled);
        this.inflightMessageSize.setEnabled(enabled);
    }

    public void setParent(SubscriptionStatistics parent) {
        if (parent != null) {
            this.consumedCount.setParent(parent.consumedCount);
            this.enqueues.setParent(parent.enqueues);
            this.dispatched.setParent(parent.dispatched);
            this.dequeues.setParent(parent.dequeues);
            this.inflightMessageSize.setParent(parent.inflightMessageSize);
        } else {
            this.consumedCount.setParent(null);
            this.enqueues.setParent(null);
            this.dispatched.setParent(null);
            this.dequeues.setParent(null);
            this.inflightMessageSize.setParent(null);
        }
    }
}

