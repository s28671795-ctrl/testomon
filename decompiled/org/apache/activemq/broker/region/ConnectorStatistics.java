/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.PollCountStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class ConnectorStatistics
extends StatsImpl {
    protected CountStatisticImpl enqueues = new CountStatisticImpl("enqueues", "The number of messages that have been sent to the destination");
    protected CountStatisticImpl dequeues = new CountStatisticImpl("dequeues", "The number of messages that have been dispatched from the destination");
    protected CountStatisticImpl consumers = new CountStatisticImpl("consumers", "The number of consumers that that are subscribing to messages from the destination");
    protected CountStatisticImpl messages = new CountStatisticImpl("messages", "The number of messages that that are being held by the destination");
    protected PollCountStatisticImpl messagesCached = new PollCountStatisticImpl("messagesCached", "The number of messages that are held in the destination's memory cache");

    public ConnectorStatistics() {
        this.addStatistic("enqueues", this.enqueues);
        this.addStatistic("dequeues", this.dequeues);
        this.addStatistic("consumers", this.consumers);
        this.addStatistic("messages", this.messages);
        this.addStatistic("messagesCached", this.messagesCached);
    }

    public CountStatisticImpl getEnqueues() {
        return this.enqueues;
    }

    public CountStatisticImpl getDequeues() {
        return this.dequeues;
    }

    public CountStatisticImpl getConsumers() {
        return this.consumers;
    }

    public PollCountStatisticImpl getMessagesCached() {
        return this.messagesCached;
    }

    public CountStatisticImpl getMessages() {
        return this.messages;
    }

    @Override
    public void reset() {
        super.reset();
        this.enqueues.reset();
        this.dequeues.reset();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enqueues.setEnabled(enabled);
        this.dequeues.setEnabled(enabled);
        this.consumers.setEnabled(enabled);
        this.messages.setEnabled(enabled);
        this.messagesCached.setEnabled(enabled);
    }

    public void setParent(ConnectorStatistics parent) {
        if (parent != null) {
            this.enqueues.setParent(parent.enqueues);
            this.dequeues.setParent(parent.dequeues);
            this.consumers.setParent(parent.consumers);
            this.messagesCached.setParent(parent.messagesCached);
            this.messages.setParent(parent.messages);
        } else {
            this.enqueues.setParent(null);
            this.dequeues.setParent(null);
            this.consumers.setParent(null);
            this.messagesCached.setParent(null);
            this.messages.setParent(null);
        }
    }

    public void setMessagesCached(PollCountStatisticImpl messagesCached) {
        this.messagesCached = messagesCached;
    }
}

