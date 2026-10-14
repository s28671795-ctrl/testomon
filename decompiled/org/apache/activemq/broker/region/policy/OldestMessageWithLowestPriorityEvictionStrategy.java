/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.policy.MessageEvictionStrategySupport;

public class OldestMessageWithLowestPriorityEvictionStrategy
extends MessageEvictionStrategySupport {
    @Override
    public MessageReference[] evictMessages(LinkedList messages) throws IOException {
        byte lowestPriority = 127;
        int pivot = 0;
        Iterator iter = messages.iterator();
        int i = 0;
        while (iter.hasNext()) {
            MessageReference reference = (MessageReference)iter.next();
            byte priority = reference.getMessage().getPriority();
            if (priority < lowestPriority) {
                lowestPriority = priority;
                pivot = i;
            }
            ++i;
        }
        return new MessageReference[]{(MessageReference)messages.remove(pivot)};
    }
}

