/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.policy.MessageEvictionStrategySupport;

public class UniquePropertyMessageEvictionStrategy
extends MessageEvictionStrategySupport {
    protected String propertyName;

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public MessageReference[] evictMessages(LinkedList messages) throws IOException {
        MessageReference oldest = (MessageReference)messages.getFirst();
        HashMap<Object, MessageReference> pivots = new HashMap<Object, MessageReference>();
        Iterator iter = messages.iterator();
        int i = 0;
        while (iter.hasNext()) {
            MessageReference reference = (MessageReference)iter.next();
            if (this.propertyName != null && reference.getMessage().getProperty(this.propertyName) != null) {
                Object key = reference.getMessage().getProperty(this.propertyName);
                if (pivots.containsKey(key)) {
                    MessageReference pivot = (MessageReference)pivots.get(key);
                    if (reference.getMessage().getTimestamp() > pivot.getMessage().getTimestamp()) {
                        pivots.put(key, reference);
                    }
                } else {
                    pivots.put(key, reference);
                }
            }
            ++i;
        }
        if (!pivots.isEmpty()) {
            for (MessageReference ref : pivots.values()) {
                messages.remove(ref);
            }
            if (messages.size() != 0) {
                return messages.toArray(new MessageReference[messages.size()]);
            }
        }
        return new MessageReference[]{oldest};
    }
}

