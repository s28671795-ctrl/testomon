/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import org.apache.activemq.network.jms.QueueBridge;

public class InboundQueueBridge
extends QueueBridge {
    String inboundQueueName;
    String localQueueName;

    public InboundQueueBridge(String inboundQueueName) {
        this.inboundQueueName = inboundQueueName;
        this.localQueueName = inboundQueueName;
    }

    public InboundQueueBridge() {
    }

    public String getInboundQueueName() {
        return this.inboundQueueName;
    }

    public void setInboundQueueName(String inboundQueueName) {
        this.inboundQueueName = inboundQueueName;
        if (this.localQueueName == null) {
            this.localQueueName = inboundQueueName;
        }
    }

    public String getLocalQueueName() {
        return this.localQueueName;
    }

    public void setLocalQueueName(String localQueueName) {
        this.localQueueName = localQueueName;
    }
}

