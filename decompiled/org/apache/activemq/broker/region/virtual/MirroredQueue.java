/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MirroredQueue
implements DestinationInterceptor,
BrokerServiceAware {
    private static final transient Logger LOG = LoggerFactory.getLogger(MirroredQueue.class);
    private String prefix = "VirtualTopic.Mirror.";
    private String postfix = "";
    private boolean copyMessage = true;
    private BrokerService brokerService;

    @Override
    public Destination intercept(final Destination destination) {
        if (destination.getActiveMQDestination().isQueue() && (!destination.getActiveMQDestination().isTemporary() || this.brokerService.isUseTempMirroredQueues())) {
            try {
                final Destination mirrorDestination = this.getMirrorDestination(destination);
                if (mirrorDestination != null) {
                    return new DestinationFilter(destination){

                        @Override
                        public void send(ProducerBrokerExchange context, Message message) throws Exception {
                            message.setDestination(mirrorDestination.getActiveMQDestination());
                            mirrorDestination.send(context, message);
                            if (MirroredQueue.this.isCopyMessage()) {
                                message = message.copy();
                            }
                            message.setDestination(destination.getActiveMQDestination());
                            message.setMemoryUsage(null);
                            super.send(context, message);
                        }
                    };
                }
            }
            catch (Exception e) {
                LOG.error("Failed to lookup the mirror destination for: {}", (Object)destination, (Object)e);
            }
        }
        return destination;
    }

    @Override
    public void remove(Destination destination) {
        if (this.brokerService == null) {
            throw new IllegalArgumentException("No brokerService injected!");
        }
        ActiveMQDestination topic = this.getMirrorTopic(destination.getActiveMQDestination());
        if (topic != null) {
            try {
                this.brokerService.removeDestination(topic);
            }
            catch (Exception e) {
                LOG.error("Failed to remove mirror destination for {}", (Object)destination, (Object)e);
            }
        }
    }

    @Override
    public void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) {
    }

    public String getPostfix() {
        return this.postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isCopyMessage() {
        return this.copyMessage;
    }

    public void setCopyMessage(boolean copyMessage) {
        this.copyMessage = copyMessage;
    }

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    protected Destination getMirrorDestination(Destination destination) throws Exception {
        if (this.brokerService == null) {
            throw new IllegalArgumentException("No brokerService injected!");
        }
        ActiveMQDestination topic = this.getMirrorTopic(destination.getActiveMQDestination());
        return this.brokerService.getDestination(topic);
    }

    protected ActiveMQDestination getMirrorTopic(ActiveMQDestination original) {
        return new ActiveMQTopic(this.prefix + original.getPhysicalName() + this.postfix);
    }
}

