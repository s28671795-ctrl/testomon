/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.ProducerEvent;
import org.apache.activemq.advisory.ProducerEventSource;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ProducerId;

public class ProducerStoppedEvent
extends ProducerEvent {
    private static final long serialVersionUID = 5378835541037193206L;

    public ProducerStoppedEvent(ProducerEventSource source2, ActiveMQDestination destination, ProducerId consumerId, int count) {
        super(source2, destination, consumerId, count);
    }

    @Override
    public boolean isStarted() {
        return false;
    }
}

