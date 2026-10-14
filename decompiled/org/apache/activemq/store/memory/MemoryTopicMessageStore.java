/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store.memory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStoreStatistics;
import org.apache.activemq.store.MessageStoreSubscriptionStatistics;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.store.memory.MemoryMessageStore;
import org.apache.activemq.store.memory.MemoryTopicSub;
import org.apache.activemq.util.LRUCache;
import org.apache.activemq.util.SubscriptionKey;

public class MemoryTopicMessageStore
extends MemoryMessageStore
implements TopicMessageStore {
    private Map<SubscriptionKey, SubscriptionInfo> subscriberDatabase;
    private Map<SubscriptionKey, MemoryTopicSub> topicSubMap;
    private final Map<MessageId, Message> originalMessageTable;
    private final MessageStoreSubscriptionStatistics stats = new MessageStoreSubscriptionStatistics(false);

    public MemoryTopicMessageStore(ActiveMQDestination destination) {
        this(destination, new MemoryTopicMessageStoreLRUCache(100, 100, 0.75f, false), MemoryTopicMessageStore.makeSubscriptionInfoMap());
        MemoryTopicMessageStoreLRUCache cache = (MemoryTopicMessageStoreLRUCache)this.originalMessageTable;
        cache.setMessageStoreStatistics(this.messageStoreStatistics);
    }

    public MemoryTopicMessageStore(ActiveMQDestination destination, Map<MessageId, Message> messageTable, Map<SubscriptionKey, SubscriptionInfo> subscriberDatabase) {
        super(destination, messageTable);
        this.subscriberDatabase = subscriberDatabase;
        this.topicSubMap = MemoryTopicMessageStore.makeSubMap();
        this.originalMessageTable = messageTable;
    }

    protected static Map<SubscriptionKey, SubscriptionInfo> makeSubscriptionInfoMap() {
        return Collections.synchronizedMap(new HashMap());
    }

    protected static Map<SubscriptionKey, MemoryTopicSub> makeSubMap() {
        return Collections.synchronizedMap(new HashMap());
    }

    @Override
    public synchronized void addMessage(ConnectionContext context, Message message) throws IOException {
        super.addMessage(context, message);
        for (MemoryTopicSub sub : this.topicSubMap.values()) {
            sub.addMessage(message.getMessageId(), message);
        }
    }

    @Override
    public synchronized void acknowledge(ConnectionContext context, String clientId, String subscriptionName, MessageId messageId, MessageAck ack) throws IOException {
        super.removeMessage(messageId);
        SubscriptionKey key = new SubscriptionKey(clientId, subscriptionName);
        MemoryTopicSub sub = this.topicSubMap.get(key);
        if (sub != null) {
            sub.removeMessage(messageId);
        }
    }

    @Override
    public synchronized SubscriptionInfo lookupSubscription(String clientId, String subscriptionName) throws IOException {
        return this.subscriberDatabase.get(new SubscriptionKey(clientId, subscriptionName));
    }

    @Override
    public synchronized void addSubscription(SubscriptionInfo info, boolean retroactive) throws IOException {
        SubscriptionKey key = new SubscriptionKey(info);
        MemoryTopicSub sub = new MemoryTopicSub(key);
        this.topicSubMap.put(key, sub);
        if (retroactive) {
            for (Map.Entry entry : this.messageTable.entrySet()) {
                sub.addMessage((MessageId)entry.getKey(), (Message)entry.getValue());
            }
        }
        this.subscriberDatabase.put(key, info);
    }

    @Override
    public synchronized void deleteSubscription(String clientId, String subscriptionName) {
        SubscriptionKey key = new SubscriptionKey(clientId, subscriptionName);
        this.subscriberDatabase.remove(key);
        MemoryTopicSub subscription = this.topicSubMap.get(key);
        if (subscription != null) {
            List<Message> storedMessages = subscription.getStoredMessages();
            for (Message message : storedMessages) {
                try {
                    this.acknowledge(null, key.getClientId(), key.getSubscriptionName(), message.getMessageId(), null);
                }
                catch (IOException iOException) {}
            }
        }
        this.subscriberDatabase.remove(key);
        this.topicSubMap.remove(key);
    }

    @Override
    public synchronized void recoverSubscription(String clientId, String subscriptionName, MessageRecoveryListener listener) throws Exception {
        MemoryTopicSub sub = this.topicSubMap.get(new SubscriptionKey(clientId, subscriptionName));
        if (sub != null) {
            sub.recoverSubscription(listener);
        }
    }

    @Override
    public synchronized void delete() {
        super.delete();
        this.subscriberDatabase.clear();
        this.topicSubMap.clear();
    }

    @Override
    public SubscriptionInfo[] getAllSubscriptions() throws IOException {
        return this.subscriberDatabase.values().toArray(new SubscriptionInfo[this.subscriberDatabase.size()]);
    }

    @Override
    public synchronized int getMessageCount(String clientId, String subscriberName) throws IOException {
        int result = 0;
        MemoryTopicSub sub = this.topicSubMap.get(new SubscriptionKey(clientId, subscriberName));
        if (sub != null) {
            result = sub.size();
        }
        return result;
    }

    @Override
    public synchronized long getMessageSize(String clientId, String subscriberName) throws IOException {
        long result = 0L;
        MemoryTopicSub sub = this.topicSubMap.get(new SubscriptionKey(clientId, subscriberName));
        if (sub != null) {
            result = sub.messageSize();
        }
        return result;
    }

    @Override
    public synchronized void recoverNextMessages(String clientId, String subscriptionName, int maxReturned, MessageRecoveryListener listener) throws Exception {
        MemoryTopicSub sub = this.topicSubMap.get(new SubscriptionKey(clientId, subscriptionName));
        if (sub != null) {
            sub.recoverNextMessages(maxReturned, listener);
        }
    }

    @Override
    public void resetBatching(String clientId, String subscriptionName) {
        MemoryTopicSub sub = this.topicSubMap.get(new SubscriptionKey(clientId, subscriptionName));
        if (sub != null) {
            sub.resetBatching();
        }
    }

    @Override
    public MessageStoreSubscriptionStatistics getMessageStoreSubStatistics() {
        return this.stats;
    }

    private static class MemoryTopicMessageStoreLRUCache
    extends LRUCache<MessageId, Message> {
        private static final long serialVersionUID = -342098639681884413L;
        private MessageStoreStatistics messageStoreStatistics;

        public MemoryTopicMessageStoreLRUCache(int initialCapacity, int maximumCacheSize, float loadFactor, boolean accessOrder) {
            super(initialCapacity, maximumCacheSize, loadFactor, accessOrder);
        }

        public void setMessageStoreStatistics(MessageStoreStatistics messageStoreStatistics) {
            this.messageStoreStatistics = messageStoreStatistics;
        }

        @Override
        protected void onCacheEviction(Map.Entry<MessageId, Message> eldest) {
            MemoryMessageStore.decMessageStoreStatistics(this.messageStoreStatistics, eldest.getValue());
            eldest.getValue().decrementReferenceCount();
        }
    }
}

