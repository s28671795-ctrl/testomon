/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.SizeStatisticImpl;
import org.apache.activemq.store.AbstractMessageStoreStatistics;

public class MessageStoreSubscriptionStatistics
extends AbstractMessageStoreStatistics {
    private final ConcurrentMap<String, SubscriptionStatistics> subStatistics = new ConcurrentHashMap<String, SubscriptionStatistics>();

    public MessageStoreSubscriptionStatistics(boolean enabled) {
        super(enabled, "The number of messages or this subscription in the message store", "Size of messages contained by this subscription in the message store");
    }

    @Override
    public CountStatisticImpl getMessageCount() {
        return this.messageCount;
    }

    @Override
    public SizeStatisticImpl getMessageSize() {
        return this.messageSize;
    }

    public CountStatisticImpl getMessageCount(String subKey) {
        return this.getOrInitStatistics(subKey).getMessageCount();
    }

    public SizeStatisticImpl getMessageSize(String subKey) {
        return this.getOrInitStatistics(subKey).getMessageSize();
    }

    public void removeSubscription(String subKey) {
        SubscriptionStatistics subStats = (SubscriptionStatistics)this.subStatistics.remove(subKey);
        if (subStats != null) {
            this.getMessageCount().subtract(subStats.getMessageCount().getCount());
            this.getMessageSize().addSize(-subStats.getMessageSize().getTotalSize());
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.subStatistics.clear();
    }

    private SubscriptionStatistics getOrInitStatistics(String subKey) {
        SubscriptionStatistics stats;
        SubscriptionStatistics subStats = (SubscriptionStatistics)this.subStatistics.get(subKey);
        if (subStats == null && (subStats = this.subStatistics.putIfAbsent(subKey, stats = new SubscriptionStatistics())) == null) {
            subStats = stats;
        }
        return subStats;
    }

    private class SubscriptionStatistics
    extends AbstractMessageStoreStatistics {
        public SubscriptionStatistics() {
            this(messageStoreSubscriptionStatistics.enabled);
        }

        public SubscriptionStatistics(boolean enabled) {
            super(enabled, "The number of messages or this subscription in the message store", "Size of messages contained by this subscription in the message store");
            this.setParent(MessageStoreSubscriptionStatistics.this);
        }
    }
}

