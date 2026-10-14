/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.ConsumerEvent;
import org.apache.activemq.advisory.ConsumerEventSource;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;

public class ConsumerStoppedEvent
extends ConsumerEvent {
    private static final long serialVersionUID = 5378835541037193206L;

    public ConsumerStoppedEvent(ConsumerEventSource source2, ActiveMQDestination destination, ConsumerId consumerId, int count) {
        super(source2, destination, consumerId, count);
    }

    @Override
    public boolean isStarted() {
        return false;
    }
}

