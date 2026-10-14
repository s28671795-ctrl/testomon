/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.PrefetchSubscription;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.StoreDurableSubscriberCursor;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.usage.UsageListener;
import org.apache.activemq.util.SubscriptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurableTopicSubscription
extends PrefetchSubscription
implements UsageListener {
    private static final Logger LOG = LoggerFactory.getLogger(DurableTopicSubscription.class);
    private final ConcurrentMap<MessageId, Integer> redeliveredMessages = new ConcurrentHashMap<MessageId, Integer>();
    private final ConcurrentMap<ActiveMQDestination, Destination> durableDestinations = new ConcurrentHashMap<ActiveMQDestination, Destination>();
    private final SubscriptionKey subscriptionKey;
    private boolean keepDurableSubsActive;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicLong offlineTimestamp = new AtomicLong(-1L);

    public DurableTopicSubscription(Broker broker, SystemUsage usageManager, ConnectionContext context, ConsumerInfo info, boolean keepDurableSubsActive) throws JMSException {
        super(broker, usageManager, context, info);
        this.pending = new StoreDurableSubscriberCursor(broker, context.getClientId(), info.getSubscriptionName(), info.getPrefetchSize(), this);
        this.pending.setSystemUsage(usageManager);
        this.pending.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
        this.keepDurableSubsActive = keepDurableSubsActive;
        this.subscriptionKey = new SubscriptionKey(context.getClientId(), info.getSubscriptionName());
    }

    public final boolean isActive() {
        return this.active.get();
    }

    public final long getOfflineTimestamp() {
        return this.offlineTimestamp.get();
    }

    public void setOfflineTimestamp(long timestamp) {
        this.offlineTimestamp.set(timestamp);
    }

    @Override
    public boolean isFull() {
        return !this.active.get() || super.isFull();
    }

    @Override
    public void gc() {
    }

    @Override
    public void unmatched(MessageReference node) throws IOException {
        MessageAck ack = new MessageAck();
        ack.setAckType((byte)5);
        ack.setMessageID(node.getMessageId());
        Destination regionDestination = (Destination)node.getRegionDestination();
        regionDestination.acknowledge(this.getContext(), this, ack, node);
    }

    @Override
    protected void setPendingBatchSize(PendingMessageCursor pending, int numberToDispatch) {
    }

    @Override
    public void add(ConnectionContext context, Destination destination) throws Exception {
        if (!this.destinations.contains(destination)) {
            super.add(context, destination);
        }
        if (this.durableDestinations.containsKey(destination.getActiveMQDestination())) {
            return;
        }
        this.durableDestinations.put(destination.getActiveMQDestination(), destination);
        if (this.active.get() || this.keepDurableSubsActive) {
            Topic topic = (Topic)destination;
            topic.activate(context, this);
            this.getSubscriptionStatistics().getEnqueues().add(this.pending.size());
        } else if (destination.getMessageStore() != null) {
            TopicMessageStore store = (TopicMessageStore)destination.getMessageStore();
            try {
                this.getSubscriptionStatistics().getEnqueues().add(store.getMessageCount(this.subscriptionKey.getClientId(), this.subscriptionKey.getSubscriptionName()));
            }
            catch (IOException e) {
                JMSException jmsEx = new JMSException("Failed to retrieve enqueueCount from store " + e);
                jmsEx.setLinkedException(e);
                throw jmsEx;
            }
        }
        this.dispatchPending();
    }

    public boolean isEmpty(Topic topic) {
        return this.pending.isEmpty(topic);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void activate(SystemUsage memoryManager, ConnectionContext context, ConsumerInfo info, RegionBroker regionBroker) throws Exception {
        if (!this.active.get()) {
            this.context = context;
            this.info = info;
            LOG.debug("Activating {}", (Object)this);
            if (!this.keepDurableSubsActive) {
                PolicyEntry entry;
                for (Destination destination : this.durableDestinations.values()) {
                    Topic topic = (Topic)destination;
                    this.add(context, topic);
                    topic.activate(context, this);
                }
                ActiveMQDestination dest = this.info.getDestination();
                if (dest != null && regionBroker.getDestinationPolicy() != null && (entry = regionBroker.getDestinationPolicy().getEntryFor(dest)) != null) {
                    entry.configure(this.broker, this.usageManager, this);
                }
            }
            Iterator iterator2 = this.pendingLock;
            synchronized (iterator2) {
                if (!((AbstractPendingMessageCursor)this.pending).isStarted() || !this.keepDurableSubsActive) {
                    this.pending.setSystemUsage(memoryManager);
                    this.pending.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
                    this.pending.setMaxAuditDepth(this.getMaxAuditDepth());
                    this.pending.setMaxProducersToAudit(this.getMaxProducersToAudit());
                    this.pending.start();
                }
                for (Destination destination : this.durableDestinations.values()) {
                    Topic topic = (Topic)destination;
                    if (!topic.isAlwaysRetroactive() && !info.isRetroactive()) continue;
                    topic.recoverRetroactiveMessages(context, this);
                }
            }
            this.active.set(true);
            this.offlineTimestamp.set(-1L);
            this.dispatchPending();
            this.usageManager.getMemoryUsage().addUsageListener(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deactivate(boolean keepDurableSubsActive, long lastDeliveredSequenceId) throws Exception {
        LOG.debug("Deactivating keepActive={}, {}", (Object)keepDurableSubsActive, (Object)this);
        this.active.set(false);
        this.keepDurableSubsActive = keepDurableSubsActive;
        this.offlineTimestamp.set(System.currentTimeMillis());
        this.usageManager.getMemoryUsage().removeUsageListener(this);
        ArrayList<Topic> topicsToDeactivate = new ArrayList<Topic>();
        ArrayList<MessageReference> savedDispateched = null;
        Iterator iterator2 = this.pendingLock;
        synchronized (iterator2) {
            if (!keepDurableSubsActive) {
                this.pending.stop();
            }
            Object object = this.dispatchLock;
            synchronized (object) {
                for (Destination destination : this.durableDestinations.values()) {
                    Topic topic = (Topic)destination;
                    if (!keepDurableSubsActive) {
                        topicsToDeactivate.add(topic);
                        continue;
                    }
                    topic.getDestinationStatistics().getInflight().subtract(this.dispatched.size());
                }
                Collections.reverse(this.dispatched);
                for (MessageReference node : this.dispatched) {
                    if (lastDeliveredSequenceId == -2L || lastDeliveredSequenceId == 0L || lastDeliveredSequenceId > 0L && node.getMessageId().getBrokerSequenceId() <= lastDeliveredSequenceId) {
                        Integer count = (Integer)this.redeliveredMessages.get(node.getMessageId());
                        if (count != null) {
                            this.redeliveredMessages.put(node.getMessageId(), count + 1);
                        } else {
                            this.redeliveredMessages.put(node.getMessageId(), 1);
                        }
                    }
                    if (keepDurableSubsActive && this.pending.isTransient()) {
                        this.pending.addMessageFirst(node);
                        this.pending.rollback(node.getMessageId());
                    }
                    node.decrementReferenceCount();
                }
                if (!topicsToDeactivate.isEmpty()) {
                    savedDispateched = new ArrayList<MessageReference>(this.dispatched);
                }
                this.dispatched.clear();
                this.getSubscriptionStatistics().getInflightMessageSize().reset();
            }
            if (!keepDurableSubsActive && this.pending.isTransient()) {
                try {
                    this.pending.reset();
                    while (this.pending.hasNext()) {
                        MessageReference node = this.pending.next();
                        node.decrementReferenceCount();
                        this.pending.remove();
                    }
                }
                finally {
                    this.pending.release();
                }
            }
        }
        for (Topic topic : topicsToDeactivate) {
            topic.deactivate(this.context, this, savedDispateched);
        }
        this.prefetchExtension.set(0);
    }

    @Override
    protected MessageDispatch createMessageDispatch(MessageReference node, Message message) {
        MessageDispatch md = super.createMessageDispatch(node, message);
        if (node != QueueMessageReference.NULL_MESSAGE) {
            node.incrementReferenceCount();
            Integer count = (Integer)this.redeliveredMessages.get(node.getMessageId());
            if (count != null) {
                md.setRedeliveryCounter(count);
            }
        }
        return md;
    }

    @Override
    public void add(MessageReference node) throws Exception {
        if (!this.active.get() && !this.keepDurableSubsActive) {
            return;
        }
        super.add(node);
    }

    @Override
    public void dispatchPending() throws IOException {
        if (this.isActive()) {
            super.dispatchPending();
        }
    }

    public void removePending(MessageReference node) throws IOException {
        this.pending.remove(node);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doAddRecoveredMessage(MessageReference message) throws Exception {
        PendingMessageCursor pendingMessageCursor = this.pending;
        synchronized (pendingMessageCursor) {
            this.pending.addRecoveredMessage(message);
        }
    }

    @Override
    public int getPendingQueueSize() {
        if (this.active.get() || this.keepDurableSubsActive) {
            return super.getPendingQueueSize();
        }
        return 0;
    }

    @Override
    public void setSelector(String selector) throws InvalidSelectorException {
        if (this.active.get()) {
            throw new UnsupportedOperationException("You cannot dynamically change the selector for durable topic subscriptions");
        }
        super.setSelector(this.getSelector());
    }

    @Override
    protected boolean canDispatch(MessageReference node) {
        return true;
    }

    @Override
    protected void acknowledge(ConnectionContext context, MessageAck ack, MessageReference node) throws IOException {
        this.setTimeOfLastMessageAck(System.currentTimeMillis());
        Destination regionDestination = (Destination)node.getRegionDestination();
        regionDestination.acknowledge(context, this, ack, node);
        this.redeliveredMessages.remove(node.getMessageId());
        node.decrementReferenceCount();
        ((Destination)node.getRegionDestination()).getDestinationStatistics().getDequeues().increment();
        if (this.info.isNetworkSubscription()) {
            ((Destination)node.getRegionDestination()).getDestinationStatistics().getForwards().add(ack.getMessageCount());
        }
    }

    public synchronized String toString() {
        return "DurableTopicSubscription-" + this.getSubscriptionKey() + ", id=" + this.info.getConsumerId() + ", active=" + this.isActive() + ", destinations=" + this.durableDestinations.size() + ", total=" + this.getSubscriptionStatistics().getEnqueues().getCount() + ", pending=" + this.getPendingQueueSize() + ", dispatched=" + this.getSubscriptionStatistics().getDispatched().getCount() + ", inflight=" + this.dispatched.size() + ", prefetchExtension=" + this.getPrefetchExtension();
    }

    public SubscriptionKey getSubscriptionKey() {
        return this.subscriptionKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void destroy() {
        Object object = this.pendingLock;
        synchronized (object) {
            try {
                this.pending.reset();
                while (this.pending.hasNext()) {
                    MessageReference node = this.pending.next();
                    node.decrementReferenceCount();
                }
            }
            finally {
                this.pending.release();
                this.pending.clear();
            }
        }
        object = this.dispatchLock;
        synchronized (object) {
            for (MessageReference node : this.dispatched) {
                node.decrementReferenceCount();
            }
            this.dispatched.clear();
        }
        this.setSlowConsumer(false);
    }

    @Override
    public void onUsageChanged(Usage usage, int oldPercentUsage, int newPercentUsage) {
        if (oldPercentUsage > newPercentUsage && oldPercentUsage >= 90) {
            try {
                this.dispatchPending();
            }
            catch (IOException e) {
                LOG.warn("problem calling dispatchMatched", e);
            }
        }
    }

    @Override
    protected boolean isDropped(MessageReference node) {
        return false;
    }

    public boolean isKeepDurableSubsActive() {
        return this.keepDurableSubsActive;
    }
}

