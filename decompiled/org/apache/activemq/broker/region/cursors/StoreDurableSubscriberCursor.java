/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.FilePendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.TopicStorePrefetch;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.command.Message;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreDurableSubscriberCursor
extends AbstractPendingMessageCursor {
    private static final Logger LOG = LoggerFactory.getLogger(StoreDurableSubscriberCursor.class);
    private final String clientId;
    private final String subscriberName;
    private final Map<Destination, TopicStorePrefetch> topics = new HashMap<Destination, TopicStorePrefetch>();
    private final List<PendingMessageCursor> storePrefetches = new CopyOnWriteArrayList<PendingMessageCursor>();
    private final PendingMessageCursor nonPersistent;
    private PendingMessageCursor currentCursor;
    private final DurableTopicSubscription subscription;
    private boolean immediatePriorityDispatch = true;

    public StoreDurableSubscriberCursor(Broker broker, String clientId, String subscriberName, int maxBatchSize, DurableTopicSubscription subscription) {
        super(AbstractPendingMessageCursor.isPrioritizedMessageSubscriber(broker, subscription));
        this.subscription = subscription;
        this.clientId = clientId;
        this.subscriberName = subscriberName;
        this.nonPersistent = broker.getBrokerService().isPersistent() ? new FilePendingMessageCursor(broker, clientId + subscriberName, this.prioritizedMessages) : new VMPendingMessageCursor(this.prioritizedMessages);
        this.nonPersistent.setMaxBatchSize(maxBatchSize);
        this.nonPersistent.setSystemUsage(this.systemUsage);
        this.storePrefetches.add(this.nonPersistent);
        if (this.prioritizedMessages) {
            this.setMaxAuditDepth(10 * this.getMaxAuditDepth());
        }
    }

    @Override
    public synchronized void start() throws Exception {
        if (!this.isStarted()) {
            super.start();
            for (PendingMessageCursor tsp : this.storePrefetches) {
                tsp.setMessageAudit(this.getMessageAudit());
                tsp.start();
            }
        }
    }

    @Override
    public synchronized void stop() throws Exception {
        if (this.isStarted()) {
            if (this.subscription.isKeepDurableSubsActive()) {
                super.gc();
                for (PendingMessageCursor tsp : this.storePrefetches) {
                    tsp.gc();
                }
            } else {
                super.stop();
                for (PendingMessageCursor tsp : this.storePrefetches) {
                    tsp.stop();
                }
                this.getMessageAudit().clear();
            }
        }
    }

    @Override
    public synchronized void add(ConnectionContext context, Destination destination) throws Exception {
        if (destination != null && !AdvisorySupport.isAdvisoryTopic(destination.getActiveMQDestination())) {
            TopicStorePrefetch tsp = new TopicStorePrefetch(this.subscription, (Topic)destination, this.clientId, this.subscriberName);
            tsp.setMaxBatchSize(destination.getMaxPageSize());
            tsp.setSystemUsage(this.systemUsage);
            tsp.setMessageAudit(this.getMessageAudit());
            tsp.setEnableAudit(this.isEnableAudit());
            tsp.setMemoryUsageHighWaterMark(this.getMemoryUsageHighWaterMark());
            tsp.setUseCache(this.isUseCache());
            tsp.setCacheEnabled(this.isUseCache() && tsp.isEmpty());
            this.topics.put(destination, tsp);
            this.storePrefetches.add(tsp);
            if (this.isStarted()) {
                tsp.start();
            }
        }
    }

    @Override
    public synchronized List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        PendingMessageCursor tsp = this.topics.remove(destination);
        if (tsp != null) {
            this.storePrefetches.remove(tsp);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public synchronized boolean isEmpty() {
        for (PendingMessageCursor tsp : this.storePrefetches) {
            if (tsp.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public synchronized boolean isEmpty(Destination destination) {
        boolean result = true;
        TopicStorePrefetch tsp = this.topics.get(destination);
        if (tsp != null) {
            result = tsp.isEmpty();
        }
        return result;
    }

    @Override
    public boolean isRecoveryRequired() {
        return false;
    }

    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long wait) throws Exception {
        if (node != null) {
            Destination dest;
            TopicStorePrefetch tsp;
            Message msg = node.getMessage();
            if (this.isStarted() && !msg.isPersistent()) {
                this.nonPersistent.tryAddMessageLast(node, wait);
            }
            if (msg.isPersistent() && (tsp = this.topics.get(dest = (Destination)msg.getRegionDestination())) != null) {
                tsp.addMessageLast(node);
                if (this.prioritizedMessages && this.immediatePriorityDispatch && tsp.isPaging() && msg.getPriority() > tsp.getLastRecoveredPriority()) {
                    tsp.recoverMessage(node.getMessage(), true);
                    LOG.trace("cached high priority ({}) message: {}, current paged batch priority: {}, cache size: {}", msg.getPriority(), msg.getMessageId(), tsp.getLastRecoveredPriority(), tsp.batchList.size());
                }
            }
        }
        return true;
    }

    @Override
    public boolean isTransient() {
        return this.subscription.isKeepDurableSubsActive();
    }

    @Override
    public void addMessageFirst(MessageReference node) throws Exception {
        if (node != null) {
            Message msg = node.getMessage();
            if (!msg.isPersistent()) {
                this.nonPersistent.addMessageFirst(node);
            } else {
                Destination dest = (Destination)msg.getRegionDestination();
                TopicStorePrefetch tsp = this.topics.get(dest);
                if (tsp != null) {
                    tsp.addMessageFirst(node);
                }
            }
        }
    }

    @Override
    public synchronized void addRecoveredMessage(MessageReference node) throws Exception {
        this.nonPersistent.addMessageLast(node);
    }

    @Override
    public synchronized void clear() {
        for (PendingMessageCursor tsp : this.storePrefetches) {
            tsp.clear();
        }
    }

    @Override
    public synchronized boolean hasNext() {
        boolean result = true;
        if (result) {
            try {
                this.currentCursor = this.getNextCursor();
            }
            catch (Exception e) {
                LOG.error("Failed to get current cursor ", e);
                throw new RuntimeException(e);
            }
            result = this.currentCursor != null ? this.currentCursor.hasNext() : false;
        }
        return result;
    }

    @Override
    public synchronized MessageReference next() {
        MessageReference result = this.currentCursor != null ? this.currentCursor.next() : null;
        return result;
    }

    @Override
    public synchronized void remove() {
        if (this.currentCursor != null) {
            this.currentCursor.remove();
        }
    }

    @Override
    public synchronized void remove(MessageReference node) {
        for (PendingMessageCursor tsp : this.storePrefetches) {
            tsp.remove(node);
        }
    }

    @Override
    public synchronized void reset() {
        for (PendingMessageCursor storePrefetch : this.storePrefetches) {
            storePrefetch.reset();
        }
    }

    @Override
    public synchronized void release() {
        this.currentCursor = null;
        for (PendingMessageCursor storePrefetch : this.storePrefetches) {
            storePrefetch.release();
        }
    }

    @Override
    public synchronized int size() {
        int pendingCount = 0;
        for (PendingMessageCursor tsp : this.storePrefetches) {
            pendingCount += tsp.size();
        }
        return pendingCount;
    }

    @Override
    public synchronized long messageSize() {
        long pendingSize = 0L;
        for (PendingMessageCursor tsp : this.storePrefetches) {
            pendingSize += tsp.messageSize();
        }
        return pendingSize;
    }

    @Override
    public void setMaxBatchSize(int newMaxBatchSize) {
        for (PendingMessageCursor storePrefetch : this.storePrefetches) {
            storePrefetch.setMaxBatchSize(newMaxBatchSize);
        }
        super.setMaxBatchSize(newMaxBatchSize);
    }

    @Override
    public synchronized void gc() {
        for (PendingMessageCursor tsp : this.storePrefetches) {
            tsp.gc();
        }
    }

    @Override
    public void setSystemUsage(SystemUsage usageManager) {
        super.setSystemUsage(usageManager);
        for (PendingMessageCursor tsp : this.storePrefetches) {
            tsp.setSystemUsage(usageManager);
        }
    }

    @Override
    public void setMemoryUsageHighWaterMark(int memoryUsageHighWaterMark) {
        super.setMemoryUsageHighWaterMark(memoryUsageHighWaterMark);
        for (PendingMessageCursor cursor : this.storePrefetches) {
            cursor.setMemoryUsageHighWaterMark(memoryUsageHighWaterMark);
        }
    }

    @Override
    public void setMaxProducersToAudit(int maxProducersToAudit) {
        super.setMaxProducersToAudit(maxProducersToAudit);
        for (PendingMessageCursor cursor : this.storePrefetches) {
            cursor.setMaxProducersToAudit(maxProducersToAudit);
        }
    }

    @Override
    public void setMaxAuditDepth(int maxAuditDepth) {
        super.setMaxAuditDepth(maxAuditDepth);
        for (PendingMessageCursor cursor : this.storePrefetches) {
            cursor.setMaxAuditDepth(maxAuditDepth);
        }
    }

    @Override
    public void setEnableAudit(boolean enableAudit) {
        super.setEnableAudit(enableAudit);
        for (PendingMessageCursor cursor : this.storePrefetches) {
            cursor.setEnableAudit(enableAudit);
        }
    }

    @Override
    public void setUseCache(boolean useCache) {
        super.setUseCache(useCache);
        for (PendingMessageCursor cursor : this.storePrefetches) {
            cursor.setUseCache(useCache);
        }
    }

    protected synchronized PendingMessageCursor getNextCursor() throws Exception {
        if (this.currentCursor == null || this.currentCursor.isEmpty()) {
            this.currentCursor = null;
            for (PendingMessageCursor tsp : this.storePrefetches) {
                if (!tsp.hasNext()) continue;
                this.currentCursor = tsp;
                break;
            }
            if (this.storePrefetches.size() > 1) {
                PendingMessageCursor first = this.storePrefetches.remove(0);
                this.storePrefetches.add(first);
            }
        }
        return this.currentCursor;
    }

    public String toString() {
        return "StoreDurableSubscriber(" + this.clientId + ":" + this.subscriberName + ")";
    }

    public boolean isImmediatePriorityDispatch() {
        return this.immediatePriorityDispatch;
    }

    public void setImmediatePriorityDispatch(boolean immediatePriorityDispatch) {
        this.immediatePriorityDispatch = immediatePriorityDispatch;
    }
}

