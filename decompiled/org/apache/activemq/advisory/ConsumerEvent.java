/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.EventObject;
import javax.jms.Destination;
import org.apache.activemq.advisory.ConsumerEventSource;
import org.apache.activemq.command.ConsumerId;

public abstract class ConsumerEvent
extends EventObject {
    private static final long serialVersionUID = 2442156576867593780L;
    private final Destination destination;
    private final ConsumerId consumerId;
    private final int consumerCount;

    public ConsumerEvent(ConsumerEventSource source2, Destination destination, ConsumerId consumerId, int consumerCount) {
        super(source2);
        this.destination = destination;
        this.consumerId = consumerId;
        this.consumerCount = consumerCount;
    }

    public ConsumerEventSource getAdvisor() {
        return (ConsumerEventSource)this.getSource();
    }

    public Destination getDestination() {
        return this.destination;
    }

    public int getConsumerCount() {
        return this.consumerCount;
    }

    public ConsumerId getConsumerId() {
        return this.consumerId;
    }

    public abstract boolean isStarted();
}

