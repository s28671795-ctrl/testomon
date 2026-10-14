/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.inteceptor;

import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.inteceptor.MessageInterceptor;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.state.ProducerState;

class MessageInterceptorFilter
extends BrokerFilter {
    private DestinationMap interceptorMap = new DestinationMap();

    MessageInterceptorFilter(Broker next) {
        super(next);
    }

    MessageInterceptor addMessageInterceptor(String destinationName, MessageInterceptor messageInterceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        this.interceptorMap.put(activeMQDestination, messageInterceptor);
        return messageInterceptor;
    }

    void removeMessageInterceptor(String destinationName, MessageInterceptor interceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        this.interceptorMap.remove(activeMQDestination, interceptor);
    }

    MessageInterceptor addMessageInterceptorForQueue(String destinationName, MessageInterceptor messageInterceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        this.interceptorMap.put(activeMQDestination, messageInterceptor);
        return messageInterceptor;
    }

    void removeMessageInterceptorForQueue(String destinationName, MessageInterceptor interceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        this.interceptorMap.remove(activeMQDestination, interceptor);
    }

    MessageInterceptor addMessageInterceptorForTopic(String destinationName, MessageInterceptor messageInterceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)2);
        this.interceptorMap.put(activeMQDestination, messageInterceptor);
        return messageInterceptor;
    }

    void removeMessageInterceptorForTopic(String destinationName, MessageInterceptor interceptor) {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, (byte)2);
        this.interceptorMap.remove(activeMQDestination, interceptor);
    }

    MessageInterceptor addMessageInterceptor(ActiveMQDestination activeMQDestination, MessageInterceptor messageInterceptor) {
        this.interceptorMap.put(activeMQDestination, messageInterceptor);
        return messageInterceptor;
    }

    void removeMessageInterceptor(ActiveMQDestination activeMQDestination, MessageInterceptor interceptor) {
        this.interceptorMap.remove(activeMQDestination, interceptor);
    }

    void injectMessage(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        ProducerBrokerExchange pe = producerExchange;
        if (pe == null) {
            pe = new ProducerBrokerExchange();
            ConnectionContext cc = new ConnectionContext();
            cc.setBroker(this.getRoot());
            pe.setConnectionContext(cc);
            pe.setMutable(true);
            pe.setProducerState(new ProducerState(new ProducerInfo()));
        }
        super.send(pe, messageSend);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        ActiveMQDestination activeMQDestination = messageSend.getDestination();
        if (!this.interceptorMap.isEmpty() && activeMQDestination != null) {
            Set set = this.interceptorMap.get(activeMQDestination);
            if (set != null && !set.isEmpty()) {
                for (MessageInterceptor mi : set) {
                    mi.intercept(producerExchange, messageSend);
                }
            } else {
                super.send(producerExchange, messageSend);
            }
        } else {
            super.send(producerExchange, messageSend);
        }
    }
}

