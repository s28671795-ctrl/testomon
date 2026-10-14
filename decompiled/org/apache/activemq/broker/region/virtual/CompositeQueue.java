/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.virtual.CompositeDestination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;

public class CompositeQueue
extends CompositeDestination {
    @Override
    public ActiveMQDestination getVirtualDestination() {
        return new ActiveMQQueue(this.getName());
    }

    @Override
    public Destination interceptMappedDestination(Destination destination) {
        return destination;
    }

    public String toString() {
        return "CompositeQueue [" + this.getName() + "]";
    }
}

