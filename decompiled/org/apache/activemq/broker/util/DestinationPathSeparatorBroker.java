/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.filter.DestinationPath;

public class DestinationPathSeparatorBroker
extends BrokerPluginSupport {
    String pathSeparator = "/";

    protected ActiveMQDestination convertDestination(ActiveMQDestination destination) {
        if (destination != null && destination.getPhysicalName().contains(this.pathSeparator)) {
            ArrayList<String> l = new ArrayList<String>();
            StringTokenizer iter = new StringTokenizer(destination.getPhysicalName(), this.pathSeparator);
            while (iter.hasMoreTokens()) {
                String name = iter.nextToken().trim();
                if (name.length() == 0) continue;
                l.add(name);
            }
            String newName = DestinationPath.toString(l.toArray(new String[l.size()]));
            return ActiveMQDestination.createDestination(newName, destination.getDestinationType());
        }
        return destination;
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        ack.setDestination(this.convertDestination(ack.getDestination()));
        super.acknowledge(consumerExchange, ack);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        return super.addConsumer(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        super.addProducer(context, info);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        super.removeConsumer(context, info);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        super.removeProducer(context, info);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        messageSend.setDestination(this.convertDestination(messageSend.getDestination()));
        super.send(producerExchange, messageSend);
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemporary) throws Exception {
        return super.addDestination(context, this.convertDestination(destination), createIfTemporary);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, this.convertDestination(destination), timeout2);
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        super.addDestinationInfo(context, info);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        info.setDestination(this.convertDestination(info.getDestination()));
        super.removeDestinationInfo(context, info);
    }

    @Override
    public void processConsumerControl(ConsumerBrokerExchange consumerExchange, ConsumerControl control) {
        control.setDestination(this.convertDestination(control.getDestination()));
        super.processConsumerControl(consumerExchange, control);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        pull.setDestination(this.convertDestination(pull.getDestination()));
        return super.messagePull(context, pull);
    }

    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }
}

