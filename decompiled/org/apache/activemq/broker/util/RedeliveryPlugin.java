/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.filter.AnyDestination;
import org.apache.activemq.state.ProducerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedeliveryPlugin
extends BrokerPluginSupport {
    private static final Logger LOG = LoggerFactory.getLogger(RedeliveryPlugin.class);
    public static final String REDELIVERY_DELAY = "redeliveryDelay";
    RedeliveryPolicyMap redeliveryPolicyMap = new RedeliveryPolicyMap();
    boolean sendToDlqIfMaxRetriesExceeded = true;
    private boolean fallbackToDeadLetter = true;

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        if (!broker.getBrokerService().isSchedulerSupport()) {
            throw new IllegalStateException("RedeliveryPlugin requires schedulerSupport=true on the broker");
        }
        this.validatePolicyDelay(1000L);
        return super.installPlugin(broker);
    }

    private void validatePolicyDelay(long limit) {
        AnyDestination matchAll = new AnyDestination(new ActiveMQDestination[]{new ActiveMQQueue(">"), new ActiveMQTopic(">")});
        for (Object entry : this.redeliveryPolicyMap.get(matchAll)) {
            RedeliveryPolicy redeliveryPolicy = (RedeliveryPolicy)entry;
            this.validateLimit(limit, redeliveryPolicy);
        }
        RedeliveryPolicy defaultEntry = this.redeliveryPolicyMap.getDefaultEntry();
        if (defaultEntry != null) {
            this.validateLimit(limit, defaultEntry);
        }
    }

    private void validateLimit(long limit, RedeliveryPolicy redeliveryPolicy) {
        if (redeliveryPolicy.getInitialRedeliveryDelay() < limit) {
            throw new IllegalStateException("RedeliveryPolicy initialRedeliveryDelay must exceed: " + limit + ". " + redeliveryPolicy);
        }
        if (redeliveryPolicy.getRedeliveryDelay() < limit) {
            throw new IllegalStateException("RedeliveryPolicy redeliveryDelay must exceed: " + limit + ". " + redeliveryPolicy);
        }
    }

    public RedeliveryPolicyMap getRedeliveryPolicyMap() {
        return this.redeliveryPolicyMap;
    }

    public void setRedeliveryPolicyMap(RedeliveryPolicyMap redeliveryPolicyMap) {
        this.redeliveryPolicyMap = redeliveryPolicyMap;
    }

    public boolean isSendToDlqIfMaxRetriesExceeded() {
        return this.sendToDlqIfMaxRetriesExceeded;
    }

    public void setSendToDlqIfMaxRetriesExceeded(boolean sendToDlqIfMaxRetriesExceeded) {
        this.sendToDlqIfMaxRetriesExceeded = sendToDlqIfMaxRetriesExceeded;
    }

    public boolean isFallbackToDeadLetter() {
        return this.fallbackToDeadLetter;
    }

    public void setFallbackToDeadLetter(boolean fallbackToDeadLetter) {
        this.fallbackToDeadLetter = fallbackToDeadLetter;
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference messageReference, Subscription subscription, Throwable poisonCause) {
        if (messageReference.isExpired()) {
            return super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
        }
        try {
            Destination regionDestination = (Destination)messageReference.getRegionDestination();
            RedeliveryPolicy redeliveryPolicy = this.redeliveryPolicyMap.getEntryFor(regionDestination.getActiveMQDestination());
            if (redeliveryPolicy != null) {
                int maximumRedeliveries = redeliveryPolicy.getMaximumRedeliveries();
                int redeliveryCount = messageReference.getRedeliveryCounter();
                if (-1 == maximumRedeliveries || redeliveryCount < maximumRedeliveries) {
                    long delay = redeliveryPolicy.getInitialRedeliveryDelay();
                    for (int i = 0; i < redeliveryCount; ++i) {
                        delay = redeliveryPolicy.getNextRedeliveryDelay(delay);
                    }
                    this.scheduleRedelivery(context, messageReference, delay, ++redeliveryCount);
                } else {
                    if (this.isSendToDlqIfMaxRetriesExceeded()) {
                        return super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
                    }
                    LOG.debug("Discarding message that exceeds max redelivery count({}), {}", (Object)maximumRedeliveries, (Object)messageReference.getMessageId());
                }
            } else {
                if (this.isFallbackToDeadLetter()) {
                    return super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
                }
                LOG.debug("Ignoring dlq request for: {}, RedeliveryPolicy not found (and no fallback) for: {}", (Object)messageReference.getMessageId(), (Object)regionDestination.getActiveMQDestination());
            }
            return false;
        }
        catch (Exception exception) {
            RuntimeException toThrow = new RuntimeException("Failed to schedule redelivery for: " + messageReference.getMessageId(), exception);
            LOG.error(toThrow.toString(), exception);
            throw toThrow;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void scheduleRedelivery(ConnectionContext context, MessageReference messageReference, long delay, int redeliveryCount) throws Exception {
        if (LOG.isTraceEnabled()) {
            Destination regionDestination = (Destination)messageReference.getRegionDestination();
            LOG.trace("redelivery #{} of: {} with delay: {}, dest: {}", redeliveryCount, messageReference.getMessageId(), delay, regionDestination.getActiveMQDestination());
        }
        Message old = messageReference.getMessage();
        Message message = old.copy();
        message.setTransactionId(null);
        message.setMemoryUsage(null);
        message.removeProperty("scheduledJobId");
        message.setProperty(REDELIVERY_DELAY, delay);
        message.setProperty("AMQ_SCHEDULED_DELAY", delay);
        message.setRedeliveryCounter(redeliveryCount);
        boolean originalFlowControl = context.isProducerFlowControl();
        try {
            context.setProducerFlowControl(false);
            ProducerInfo info = new ProducerInfo();
            ProducerState state = new ProducerState(info);
            ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
            producerExchange.setProducerState(state);
            producerExchange.setMutable(true);
            producerExchange.setConnectionContext(context);
            context.getBroker().send(producerExchange, message);
        }
        finally {
            context.setProducerFlowControl(originalFlowControl);
        }
    }
}

