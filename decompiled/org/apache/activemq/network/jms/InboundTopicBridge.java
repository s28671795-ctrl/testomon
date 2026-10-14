/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import org.apache.activemq.network.jms.TopicBridge;

public class InboundTopicBridge
extends TopicBridge {
    String inboundTopicName;
    String localTopicName;

    public InboundTopicBridge(String inboundTopicName) {
        this.inboundTopicName = inboundTopicName;
        this.localTopicName = inboundTopicName;
    }

    public InboundTopicBridge() {
    }

    public String getInboundTopicName() {
        return this.inboundTopicName;
    }

    public void setInboundTopicName(String inboundTopicName) {
        this.inboundTopicName = inboundTopicName;
        if (this.localTopicName == null) {
            this.localTopicName = inboundTopicName;
        }
    }

    public String getLocalTopicName() {
        return this.localTopicName;
    }

    public void setLocalTopicName(String localTopicName) {
        this.localTopicName = localTopicName;
    }
}

