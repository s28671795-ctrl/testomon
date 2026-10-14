/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.AbstractDeadLetterStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.Message;

public class IndividualDeadLetterStrategy
extends AbstractDeadLetterStrategy {
    private String topicPrefix = "ActiveMQ.DLQ.Topic.";
    private String queuePrefix = "ActiveMQ.DLQ.Queue.";
    private String topicSuffix;
    private String queueSuffix;
    private boolean useQueueForQueueMessages = true;
    private boolean useQueueForTopicMessages = true;
    private boolean destinationPerDurableSubscriber;

    @Override
    public ActiveMQDestination getDeadLetterQueueFor(Message message, Subscription subscription) {
        if (message.getDestination().isQueue()) {
            return this.createDestination(message, this.queuePrefix, this.queueSuffix, this.useQueueForQueueMessages, subscription);
        }
        return this.createDestination(message, this.topicPrefix, this.topicSuffix, this.useQueueForTopicMessages, subscription);
    }

    public String getQueuePrefix() {
        return this.queuePrefix;
    }

    public void setQueuePrefix(String queuePrefix) {
        this.queuePrefix = queuePrefix;
    }

    public String getTopicPrefix() {
        return this.topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public String getQueueSuffix() {
        return this.queueSuffix;
    }

    public void setQueueSuffix(String queueSuffix) {
        this.queueSuffix = queueSuffix;
    }

    public String getTopicSuffix() {
        return this.topicSuffix;
    }

    public void setTopicSuffix(String topicSuffix) {
        this.topicSuffix = topicSuffix;
    }

    public boolean isUseQueueForQueueMessages() {
        return this.useQueueForQueueMessages;
    }

    public void setUseQueueForQueueMessages(boolean useQueueForQueueMessages) {
        this.useQueueForQueueMessages = useQueueForQueueMessages;
    }

    public boolean isUseQueueForTopicMessages() {
        return this.useQueueForTopicMessages;
    }

    public void setUseQueueForTopicMessages(boolean useQueueForTopicMessages) {
        this.useQueueForTopicMessages = useQueueForTopicMessages;
    }

    public boolean isDestinationPerDurableSubscriber() {
        return this.destinationPerDurableSubscriber;
    }

    public void setDestinationPerDurableSubscriber(boolean destinationPerDurableSubscriber) {
        this.destinationPerDurableSubscriber = destinationPerDurableSubscriber;
    }

    protected ActiveMQDestination createDestination(Message message, String prefix, String suffix, boolean useQueue, Subscription subscription) {
        String name = null;
        Destination regionDestination = (Destination)message.getRegionDestination();
        name = regionDestination != null && regionDestination.getActiveMQDestination() != null && regionDestination.getActiveMQDestination().getPhysicalName() != null && !regionDestination.getActiveMQDestination().getPhysicalName().isEmpty() ? prefix + regionDestination.getActiveMQDestination().getPhysicalName() : prefix + message.getDestination().getPhysicalName();
        if (this.destinationPerDurableSubscriber && subscription instanceof DurableTopicSubscription) {
            name = name + "." + ((DurableTopicSubscription)subscription).getSubscriptionKey();
        }
        if (suffix != null && !suffix.isEmpty()) {
            name = name + suffix;
        }
        if (useQueue) {
            return new ActiveMQQueue(name);
        }
        return new ActiveMQTopic(name);
    }
}

