/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.util.Collection;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.virtual.CompositeDestinationFilter;
import org.apache.activemq.broker.region.virtual.FilteredDestination;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.command.ActiveMQDestination;

public abstract class CompositeDestination
implements VirtualDestination {
    private String name;
    private Collection forwardTo;
    private boolean forwardOnly = true;
    private boolean concurrentSend = false;

    @Override
    public Destination intercept(Destination destination) {
        return new CompositeDestinationFilter(destination, this.getForwardTo(), this.isForwardOnly(), this.isConcurrentSend());
    }

    @Override
    public void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) {
    }

    @Override
    public void remove(Destination destination) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getForwardTo() {
        return this.forwardTo;
    }

    public void setForwardTo(Collection forwardDestinations) {
        this.forwardTo = forwardDestinations;
    }

    public boolean isForwardOnly() {
        return this.forwardOnly;
    }

    public void setForwardOnly(boolean forwardOnly) {
        this.forwardOnly = forwardOnly;
    }

    @Deprecated
    public boolean isCopyMessage() {
        return true;
    }

    @Deprecated
    public void setCopyMessage(boolean copyMessage) {
    }

    public void setConcurrentSend(boolean concurrentSend) {
        this.concurrentSend = concurrentSend;
    }

    public boolean isConcurrentSend() {
        return this.concurrentSend;
    }

    @Override
    public ActiveMQDestination getMappedDestinations() {
        ActiveMQDestination[] destinations = new ActiveMQDestination[this.forwardTo.size()];
        int i = 0;
        for (Object dest : this.forwardTo) {
            if (dest instanceof FilteredDestination) {
                FilteredDestination filteredDestination = (FilteredDestination)dest;
                destinations[i++] = filteredDestination.getDestination();
                continue;
            }
            if (dest instanceof ActiveMQDestination) {
                destinations[i++] = (ActiveMQDestination)dest;
                continue;
            }
            throw new IllegalArgumentException("Unknown mapped destination type " + dest);
        }
        return new ActiveMQDestination(destinations){

            @Override
            protected String getQualifiedPrefix() {
                return "mapped://";
            }

            @Override
            public byte getDestinationType() {
                return 3;
            }

            @Override
            public byte getDataStructureType() {
                return 101;
            }
        };
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.concurrentSend ? 1231 : 1237);
        result = 31 * result + (this.forwardOnly ? 1231 : 1237);
        result = 31 * result + (this.forwardTo == null ? 0 : this.forwardTo.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CompositeDestination other = (CompositeDestination)obj;
        if (this.concurrentSend != other.concurrentSend) {
            return false;
        }
        if (this.forwardOnly != other.forwardOnly) {
            return false;
        }
        if (this.forwardTo == null ? other.forwardTo != null : !this.forwardTo.equals(other.forwardTo)) {
            return false;
        }
        return !(this.name == null ? other.name != null : !this.name.equals(other.name));
    }
}

