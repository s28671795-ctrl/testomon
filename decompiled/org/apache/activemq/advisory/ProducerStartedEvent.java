/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.ProducerEvent;
import org.apache.activemq.advisory.ProducerEventSource;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ProducerInfo;

public class ProducerStartedEvent
extends ProducerEvent {
    private static final long serialVersionUID = 5088138839609391074L;
    private final transient ProducerInfo consumerInfo;

    public ProducerStartedEvent(ProducerEventSource source2, ActiveMQDestination destination, ProducerInfo consumerInfo, int count) {
        super(source2, destination, consumerInfo.getProducerId(), count);
        this.consumerInfo = consumerInfo;
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    public ProducerInfo getProducerInfo() {
        return this.consumerInfo;
    }
}

