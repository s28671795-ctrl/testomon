/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationMap;

public class VirtualDestinationInterceptor
implements DestinationInterceptor {
    private DestinationMap destinationMap = new DestinationMap();
    private DestinationMap mappedDestinationMap = new DestinationMap();
    private VirtualDestination[] virtualDestinations;

    @Override
    public Destination intercept(Destination destination) {
        ActiveMQDestination activeMQDestination = destination.getActiveMQDestination();
        Set matchingDestinations = this.destinationMap.get(activeMQDestination);
        ArrayList<Destination> destinations = new ArrayList<Destination>();
        for (VirtualDestination virtualDestination : matchingDestinations) {
            Destination newDestination = virtualDestination.intercept(destination);
            destinations.add(newDestination);
        }
        if (!destinations.isEmpty()) {
            if (destinations.size() == 1) {
                return (Destination)destinations.get(0);
            }
            return this.createCompositeDestination(destination, destinations);
        }
        Set mappedDestinations = this.mappedDestinationMap.get(activeMQDestination);
        if (!mappedDestinations.isEmpty()) {
            VirtualDestination virtualDestination;
            virtualDestination = mappedDestinations.toArray(new VirtualDestination[mappedDestinations.size()])[0];
            return virtualDestination.interceptMappedDestination(destination);
        }
        return destination;
    }

    @Override
    public synchronized void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) throws Exception {
        for (VirtualDestination virt : this.virtualDestinations) {
            virt.create(broker, context, destination);
        }
    }

    @Override
    public synchronized void remove(Destination destination) {
    }

    public VirtualDestination[] getVirtualDestinations() {
        return this.virtualDestinations;
    }

    public void setVirtualDestinations(VirtualDestination[] virtualDestinations) {
        this.destinationMap = new DestinationMap();
        this.mappedDestinationMap = new DestinationMap();
        this.virtualDestinations = virtualDestinations;
        for (int i = 0; i < virtualDestinations.length; ++i) {
            VirtualDestination virtualDestination = virtualDestinations[i];
            this.destinationMap.put(virtualDestination.getVirtualDestination(), virtualDestination);
            this.mappedDestinationMap.put(virtualDestination.getMappedDestinations(), virtualDestination);
        }
    }

    protected Destination createCompositeDestination(Destination destination, final List<Destination> destinations) {
        return new DestinationFilter(destination){

            @Override
            public void send(ProducerBrokerExchange context, Message messageSend) throws Exception {
                for (Destination destination : destinations) {
                    destination.send(context, messageSend);
                }
            }
        };
    }

    public String toString() {
        return "VirtualDestinationInterceptor" + Arrays.asList(this.virtualDestinations);
    }
}

