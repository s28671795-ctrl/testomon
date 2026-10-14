/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.region.ConnectorStatistics;
import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class ConnectionStatistics
extends StatsImpl {
    private CountStatisticImpl enqueues = new CountStatisticImpl("enqueues", "The number of messages that have been sent to the connection");
    private CountStatisticImpl dequeues = new CountStatisticImpl("dequeues", "The number of messages that have been dispatched from the connection");

    public ConnectionStatistics() {
        this.addStatistic("enqueues", this.enqueues);
        this.addStatistic("dequeues", this.dequeues);
    }

    public CountStatisticImpl getEnqueues() {
        return this.enqueues;
    }

    public CountStatisticImpl getDequeues() {
        return this.dequeues;
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
    }

    public void setParent(ConnectorStatistics parent) {
        if (parent != null) {
            this.enqueues.setParent(parent.getEnqueues());
            this.dequeues.setParent(parent.getDequeues());
        } else {
            this.enqueues.setParent(null);
            this.dequeues.setParent(null);
        }
    }
}

