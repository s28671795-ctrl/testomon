/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.ConsumerEvent;
import org.apache.activemq.advisory.ConsumerEventSource;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;

public class ConsumerStartedEvent
extends ConsumerEvent {
    private static final long serialVersionUID = 5088138839609391074L;
    private final transient ConsumerInfo consumerInfo;

    public ConsumerStartedEvent(ConsumerEventSource source2, ActiveMQDestination destination, ConsumerInfo consumerInfo, int count) {
        super(source2, destination, consumerInfo.getConsumerId(), count);
        this.consumerInfo = consumerInfo;
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    public ConsumerInfo getConsumerInfo() {
        return this.consumerInfo;
    }
}

