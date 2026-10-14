/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.virtual.CompositeDestination;
import org.apache.activemq.broker.region.virtual.MappedQueueFilter;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;

public class CompositeTopic
extends CompositeDestination {
    @Override
    public ActiveMQDestination getVirtualDestination() {
        return new ActiveMQTopic(this.getName());
    }

    @Override
    public Destination interceptMappedDestination(Destination destination) {
        if (!this.isForwardOnly() && destination.getActiveMQDestination().isQueue()) {
            return new MappedQueueFilter(this.getVirtualDestination(), destination);
        }
        return destination;
    }

    public String toString() {
        return "CompositeTopic [" + this.getName() + "]";
    }
}

