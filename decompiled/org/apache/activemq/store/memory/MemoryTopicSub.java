/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.util.SubscriptionKey;

class MemoryTopicSub {
    private final Map<MessageId, Message> map = new LinkedHashMap<MessageId, Message>();
    private final SubscriptionKey subscriptionKey;
    private MessageId lastBatch;

    public MemoryTopicSub(SubscriptionKey subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addMessage(MessageId id, Message message) {
        MemoryTopicSub memoryTopicSub = this;
        synchronized (memoryTopicSub) {
            this.map.put(id, message);
        }
        message.incrementReferenceCount();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeMessage(MessageId id) {
        Message removed;
        MemoryTopicSub memoryTopicSub = this;
        synchronized (memoryTopicSub) {
            removed = this.map.remove(id);
            if (this.lastBatch != null && this.lastBatch.equals(id) || this.map.isEmpty()) {
                this.resetBatching();
            }
        }
        if (removed != null) {
            removed.decrementReferenceCount();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    List<Message> getStoredMessages() {
        MemoryTopicSub memoryTopicSub = this;
        synchronized (memoryTopicSub) {
            return new ArrayList<Message>(this.map.values());
        }
    }

    synchronized int size() {
        return this.map.size();
    }

    synchronized long messageSize() {
        long messageSize = 0L;
        for (Map.Entry<MessageId, Message> entry : this.map.entrySet()) {
            messageSize += (long)entry.getValue().getSize();
        }
        return messageSize;
    }

    synchronized void recoverSubscription(MessageRecoveryListener listener) throws Exception {
        for (Map.Entry<MessageId, Message> entry : this.map.entrySet()) {
            Message msg = entry.getValue();
            listener.recoverMessage(msg);
        }
    }

    synchronized void recoverNextMessages(int maxReturned, MessageRecoveryListener listener) throws Exception {
        boolean pastLackBatch = this.lastBatch == null;
        MessageId lastId = null;
        int count = 0;
        Iterator<Map.Entry<MessageId, Message>> iter = this.map.entrySet().iterator();
        while (iter.hasNext() && count < maxReturned) {
            Map.Entry<MessageId, Message> entry = iter.next();
            if (pastLackBatch) {
                ++count;
                Message msg = entry.getValue();
                lastId = entry.getKey();
                listener.recoverMessage(msg);
                continue;
            }
            pastLackBatch = entry.getKey().equals(this.lastBatch);
        }
        if (lastId != null) {
            this.lastBatch = lastId;
        }
    }

    synchronized void resetBatching() {
        this.lastBatch = null;
    }

    SubscriptionKey getSubscriptionKey() {
        return this.subscriptionKey;
    }
}

