/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.LinkedList;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.policy.MessageEvictionStrategySupport;

public class OldestMessageEvictionStrategy
extends MessageEvictionStrategySupport {
    @Override
    public MessageReference[] evictMessages(LinkedList messages) {
        return new MessageReference[]{(MessageReference)messages.removeFirst()};
    }
}

