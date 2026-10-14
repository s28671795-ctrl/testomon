/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.util.Map;
import java.util.Set;
import org.apache.activemq.Service;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;

public interface Region
extends Service {
    public Destination addDestination(ConnectionContext var1, ActiveMQDestination var2, boolean var3) throws Exception;

    public void removeDestination(ConnectionContext var1, ActiveMQDestination var2, long var3) throws Exception;

    public Map<ActiveMQDestination, Destination> getDestinationMap();

    public Subscription addConsumer(ConnectionContext var1, ConsumerInfo var2) throws Exception;

    public void removeConsumer(ConnectionContext var1, ConsumerInfo var2) throws Exception;

    public void addProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    public void removeProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    public void removeSubscription(ConnectionContext var1, RemoveSubscriptionInfo var2) throws Exception;

    public void send(ProducerBrokerExchange var1, Message var2) throws Exception;

    public void acknowledge(ConsumerBrokerExchange var1, MessageAck var2) throws Exception;

    public Response messagePull(ConnectionContext var1, MessagePull var2) throws Exception;

    public void processDispatchNotification(MessageDispatchNotification var1) throws Exception;

    public void gc();

    public Set<Destination> getDestinations(ActiveMQDestination var1);

    public void processConsumerControl(ConsumerBrokerExchange var1, ConsumerControl var2);

    public void reapplyInterceptor();
}

