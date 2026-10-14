/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerInfo;

public class CompositeDestinationBroker
extends BrokerFilter {
    public CompositeDestinationBroker(Broker next) {
        super(next);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        if (destination != null && destination.isComposite()) {
            ActiveMQDestination[] destinations = destination.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                ProducerInfo copy = info.copy();
                copy.setDestination(destinations[i]);
                this.next.addProducer(context, copy);
            }
        } else {
            this.next.addProducer(context, info);
        }
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        if (destination != null && destination.isComposite()) {
            ActiveMQDestination[] destinations = destination.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                ProducerInfo copy = info.copy();
                copy.setDestination(destinations[i]);
                this.next.removeProducer(context, copy);
            }
        } else {
            this.next.removeProducer(context, info);
        }
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message message) throws Exception {
        ActiveMQDestination destination = message.getDestination();
        if (destination.isComposite()) {
            ActiveMQDestination[] destinations = destination.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                if (i != 0) {
                    message = message.copy();
                    message.setMemoryUsage(null);
                }
                message.setOriginalDestination(destination);
                message.setDestination(destinations[i]);
                this.next.send(producerExchange, message);
            }
        } else {
            this.next.send(producerExchange, message);
        }
    }
}

