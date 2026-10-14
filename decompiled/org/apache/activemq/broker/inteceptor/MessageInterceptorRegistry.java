/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.inteceptor;

import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.MutableBrokerFilter;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.inteceptor.MessageInterceptor;
import org.apache.activemq.broker.inteceptor.MessageInterceptorFilter;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageInterceptorRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(MessageInterceptorRegistry.class);
    private static final MessageInterceptorRegistry INSTANCE = new MessageInterceptorRegistry();
    private final BrokerService brokerService;
    private MessageInterceptorFilter filter;
    private final Map<BrokerService, MessageInterceptorRegistry> messageInterceptorRegistryMap = new HashMap<BrokerService, MessageInterceptorRegistry>();

    public static MessageInterceptorRegistry getInstance() {
        return INSTANCE;
    }

    public MessageInterceptorRegistry get(String brokerName) {
        BrokerService brokerService = BrokerRegistry.getInstance().lookup(brokerName);
        return this.get(brokerService);
    }

    public synchronized MessageInterceptorRegistry get(BrokerService brokerService) {
        MessageInterceptorRegistry result = this.messageInterceptorRegistryMap.get(brokerService);
        if (result == null) {
            result = new MessageInterceptorRegistry(brokerService);
            this.messageInterceptorRegistryMap.put(brokerService, result);
        }
        return result;
    }

    private MessageInterceptorRegistry() {
        this.brokerService = BrokerRegistry.getInstance().findFirst();
        this.messageInterceptorRegistryMap.put(this.brokerService, this);
    }

    private MessageInterceptorRegistry(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public MessageInterceptor addMessageInterceptor(String destinationName, MessageInterceptor messageInterceptor) {
        return this.getFilter().addMessageInterceptor(destinationName, messageInterceptor);
    }

    public void removeMessageInterceptor(String destinationName, MessageInterceptor messageInterceptor) {
        this.getFilter().removeMessageInterceptor(destinationName, messageInterceptor);
    }

    public MessageInterceptor addMessageInterceptorForQueue(String destinationName, MessageInterceptor messageInterceptor) {
        return this.getFilter().addMessageInterceptorForQueue(destinationName, messageInterceptor);
    }

    public void removeMessageInterceptorForQueue(String destinationName, MessageInterceptor messageInterceptor) {
        this.getFilter().addMessageInterceptorForQueue(destinationName, messageInterceptor);
    }

    public MessageInterceptor addMessageInterceptorForTopic(String destinationName, MessageInterceptor messageInterceptor) {
        return this.getFilter().addMessageInterceptorForTopic(destinationName, messageInterceptor);
    }

    public void removeMessageInterceptorForTopic(String destinationName, MessageInterceptor messageInterceptor) {
        this.getFilter().removeMessageInterceptorForTopic(destinationName, messageInterceptor);
    }

    public MessageInterceptor addMessageInterceptor(ActiveMQDestination activeMQDestination, MessageInterceptor messageInterceptor) {
        return this.getFilter().addMessageInterceptor(activeMQDestination, messageInterceptor);
    }

    public void removeMessageInterceptor(ActiveMQDestination activeMQDestination, MessageInterceptor interceptor) {
        this.getFilter().removeMessageInterceptor(activeMQDestination, interceptor);
    }

    public void injectMessage(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        this.getFilter().injectMessage(producerExchange, messageSend);
    }

    private synchronized MessageInterceptorFilter getFilter() {
        if (this.filter == null) {
            try {
                MutableBrokerFilter mutableBrokerFilter = (MutableBrokerFilter)this.brokerService.getBroker().getAdaptor(MutableBrokerFilter.class);
                Broker next = mutableBrokerFilter.getNext();
                this.filter = new MessageInterceptorFilter(next);
                mutableBrokerFilter.setNext(this.filter);
            }
            catch (Exception e) {
                LOG.error("Failed to create MessageInterceptorFilter", e);
            }
        }
        return this.filter;
    }
}

