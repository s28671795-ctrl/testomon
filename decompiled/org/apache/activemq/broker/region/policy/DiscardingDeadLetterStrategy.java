/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.policy.SharedDeadLetterStrategy;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardingDeadLetterStrategy
extends SharedDeadLetterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DiscardingDeadLetterStrategy.class);

    @Override
    public boolean isSendToDeadLetterQueue(Message message) {
        boolean result = false;
        LOG.debug("Discarding message sent to DLQ: {}, dest: {}", (Object)message.getMessageId(), (Object)message.getDestination());
        return result;
    }
}

