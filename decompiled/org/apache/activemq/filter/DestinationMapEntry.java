/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;

public abstract class DestinationMapEntry<T>
implements Comparable<T> {
    protected ActiveMQDestination destination;

    @Override
    public int compareTo(Object that) {
        if (that instanceof DestinationMapEntry) {
            DestinationMapEntry thatEntry = (DestinationMapEntry)that;
            return ActiveMQDestination.compare(this.destination, thatEntry.destination);
        }
        if (that == null) {
            return 1;
        }
        return this.getClass().getName().compareTo(that.getClass().getName());
    }

    public void setQueue(String name) {
        this.setDestination(new ActiveMQQueue(name));
    }

    public void setTopic(String name) {
        this.setDestination(new ActiveMQTopic(name));
    }

    public void setTempTopic(boolean flag) {
        this.setDestination(new ActiveMQTempTopic(">"));
    }

    public void setTempQueue(boolean flag) {
        this.setDestination(new ActiveMQTempQueue(">"));
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public Comparable<T> getValue() {
        return this;
    }
}

