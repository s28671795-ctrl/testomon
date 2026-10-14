/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import org.apache.activemq.network.jms.QueueBridge;

public class OutboundQueueBridge
extends QueueBridge {
    String outboundQueueName;
    String localQueueName;

    public OutboundQueueBridge(String outboundQueueName) {
        this.outboundQueueName = outboundQueueName;
        this.localQueueName = outboundQueueName;
    }

    public OutboundQueueBridge() {
    }

    public String getOutboundQueueName() {
        return this.outboundQueueName;
    }

    public void setOutboundQueueName(String outboundQueueName) {
        this.outboundQueueName = outboundQueueName;
        if (this.localQueueName == null) {
            this.localQueueName = outboundQueueName;
        }
    }

    public String getLocalQueueName() {
        return this.localQueueName;
    }

    public void setLocalQueueName(String localQueueName) {
        this.localQueueName = localQueueName;
    }
}

