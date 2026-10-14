/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.EventObject;
import javax.jms.Destination;
import org.apache.activemq.advisory.ProducerEventSource;
import org.apache.activemq.command.ProducerId;

public abstract class ProducerEvent
extends EventObject {
    private static final long serialVersionUID = 2442156576867593780L;
    private final Destination destination;
    private final ProducerId producerId;
    private final int producerCount;

    public ProducerEvent(ProducerEventSource source2, Destination destination, ProducerId producerId, int producerCount) {
        super(source2);
        this.destination = destination;
        this.producerId = producerId;
        this.producerCount = producerCount;
    }

    public ProducerEventSource getAdvisor() {
        return (ProducerEventSource)this.getSource();
    }

    public Destination getDestination() {
        return this.destination;
    }

    public int getProducerCount() {
        return this.producerCount;
    }

    public ProducerId getProducerId() {
        return this.producerId;
    }

    public abstract boolean isStarted();
}

