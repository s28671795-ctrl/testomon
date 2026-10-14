/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import org.apache.activemq.network.jms.TopicBridge;

public class OutboundTopicBridge
extends TopicBridge {
    String outboundTopicName;
    String localTopicName;

    public OutboundTopicBridge(String outboundTopicName) {
        this.outboundTopicName = outboundTopicName;
        this.localTopicName = outboundTopicName;
    }

    public OutboundTopicBridge() {
    }

    public String getOutboundTopicName() {
        return this.outboundTopicName;
    }

    public void setOutboundTopicName(String outboundTopicName) {
        this.outboundTopicName = outboundTopicName;
        if (this.localTopicName == null) {
            this.localTopicName = outboundTopicName;
        }
    }

    public String getLocalTopicName() {
        return this.localTopicName;
    }

    public void setLocalTopicName(String localTopicName) {
        this.localTopicName = localTopicName;
    }
}

