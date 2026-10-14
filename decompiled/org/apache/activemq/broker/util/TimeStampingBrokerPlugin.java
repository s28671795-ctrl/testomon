/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeStampingBrokerPlugin
extends BrokerPluginSupport {
    private static final Logger LOG = LoggerFactory.getLogger(TimeStampingBrokerPlugin.class);
    long zeroExpirationOverride = 0L;
    long ttlCeiling = 0L;
    boolean futureOnly = false;
    boolean processNetworkMessages = false;

    public void setZeroExpirationOverride(long ttl) {
        this.zeroExpirationOverride = ttl;
    }

    public void setTtlCeiling(long ttlCeiling) {
        this.ttlCeiling = ttlCeiling;
    }

    public void setFutureOnly(boolean futureOnly) {
        this.futureOnly = futureOnly;
    }

    public void setProcessNetworkMessages(Boolean processNetworkMessages) {
        this.processNetworkMessages = processNetworkMessages;
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message message) throws Exception {
        if (message.getTimestamp() > 0L && !this.isDestinationDLQ(message) && (this.processNetworkMessages || message.getBrokerPath() == null || message.getBrokerPath().length == 0)) {
            long oldExpiration = message.getExpiration();
            long newTimeStamp = System.currentTimeMillis();
            long timeToLive = this.zeroExpirationOverride;
            long oldTimestamp = message.getTimestamp();
            if (oldExpiration > 0L) {
                timeToLive = oldExpiration - oldTimestamp;
            }
            if (timeToLive > 0L && this.ttlCeiling > 0L && timeToLive > this.ttlCeiling) {
                timeToLive = this.ttlCeiling;
            }
            long expiration = timeToLive + newTimeStamp;
            if (!this.futureOnly || expiration > oldExpiration) {
                if (timeToLive > 0L && expiration > 0L) {
                    message.setExpiration(expiration);
                }
                message.setTimestamp(newTimeStamp);
                LOG.debug("Set message {} timestamp from {} to {}", message.getMessageId(), oldTimestamp, newTimeStamp);
            }
        }
        super.send(producerExchange, message);
    }

    private boolean isDestinationDLQ(Message message) {
        DeadLetterStrategy deadLetterStrategy;
        Destination regionDestination = (Destination)message.getRegionDestination();
        if (message != null && regionDestination != null && (deadLetterStrategy = regionDestination.getDeadLetterStrategy()) != null && message.getOriginalDestination() != null) {
            ActiveMQMessage tmp = new ActiveMQMessage();
            tmp.setDestination(message.getOriginalDestination());
            tmp.setRegionDestination(regionDestination);
            ActiveMQDestination deadLetterDestination = deadLetterStrategy.getDeadLetterQueueFor(tmp, null);
            if (deadLetterDestination.equals(message.getDestination())) {
                return true;
            }
        }
        return false;
    }
}

