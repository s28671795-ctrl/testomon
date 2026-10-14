/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.AbstractDeadLetterStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.Message;

public class SharedDeadLetterStrategy
extends AbstractDeadLetterStrategy {
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "ActiveMQ.DLQ";
    private ActiveMQDestination deadLetterQueue = new ActiveMQQueue("ActiveMQ.DLQ");

    @Override
    public ActiveMQDestination getDeadLetterQueueFor(Message message, Subscription subscription) {
        return this.deadLetterQueue;
    }

    public ActiveMQDestination getDeadLetterQueue() {
        return this.deadLetterQueue;
    }

    public void setDeadLetterQueue(ActiveMQDestination deadLetterQueue) {
        this.deadLetterQueue = deadLetterQueue;
    }
}

