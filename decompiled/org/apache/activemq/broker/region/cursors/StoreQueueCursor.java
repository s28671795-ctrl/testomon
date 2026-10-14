/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.FilePendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.QueueStorePrefetch;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.command.Message;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreQueueCursor
extends AbstractPendingMessageCursor {
    private static final Logger LOG = LoggerFactory.getLogger(StoreQueueCursor.class);
    private final Broker broker;
    private int pendingCount;
    private final Queue queue;
    private PendingMessageCursor nonPersistent;
    private final QueueStorePrefetch persistent;
    private boolean started;
    private PendingMessageCursor currentCursor;

    public StoreQueueCursor(Broker broker, Queue queue) {
        super(queue != null ? queue.isPrioritizedMessages() : false);
        this.broker = broker;
        this.queue = queue;
        this.persistent = new QueueStorePrefetch(queue, broker);
        this.currentCursor = this.persistent;
    }

    @Override
    public synchronized void start() throws Exception {
        this.started = true;
        super.start();
        if (this.nonPersistent == null) {
            this.nonPersistent = this.broker.getBrokerService().isPersistent() ? new FilePendingMessageCursor(this.broker, this.queue.getName(), this.prioritizedMessages) : new VMPendingMessageCursor(this.prioritizedMessages);
            this.nonPersistent.setMaxBatchSize(this.getMaxBatchSize());
            this.nonPersistent.setSystemUsage(this.systemUsage);
            this.nonPersistent.setEnableAudit(this.isEnableAudit());
            this.nonPersistent.setMaxAuditDepth(this.getMaxAuditDepth());
            this.nonPersistent.setMaxProducersToAudit(this.getMaxProducersToAudit());
        }
        this.nonPersistent.setMessageAudit(this.getMessageAudit());
        this.nonPersistent.start();
        this.persistent.setMessageAudit(this.getMessageAudit());
        this.persistent.start();
        this.pendingCount = this.persistent.size() + this.nonPersistent.size();
    }

    @Override
    public synchronized void stop() throws Exception {
        this.started = false;
        if (this.nonPersistent != null) {
            this.nonPersistent.destroy();
        }
        this.persistent.stop();
        this.persistent.gc();
        super.stop();
        this.pendingCount = 0;
    }

    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long maxWait) throws Exception {
        boolean result = true;
        if (node != null) {
            Message msg = node.getMessage();
            if (this.started) {
                ++this.pendingCount;
                if (!msg.isPersistent()) {
                    result = this.nonPersistent.tryAddMessageLast(node, maxWait);
                }
            }
            if (msg.isPersistent()) {
                result = this.persistent.addMessageLast(node);
            }
        }
        return result;
    }

    @Override
    public synchronized void addMessageFirst(MessageReference node) throws Exception {
        if (node != null) {
            Message msg = node.getMessage();
            if (this.started) {
                ++this.pendingCount;
                if (!msg.isPersistent()) {
                    this.nonPersistent.addMessageFirst(node);
                }
            }
            if (msg.isPersistent()) {
                this.persistent.addMessageFirst(node);
            }
        }
    }

    @Override
    public synchronized void clear() {
        this.pendingCount = 0;
    }

    @Override
    public synchronized boolean hasNext() {
        try {
            this.getNextCursor();
        }
        catch (Exception e) {
            LOG.error("Failed to get current cursor ", e);
            throw new RuntimeException(e);
        }
        return this.currentCursor != null ? this.currentCursor.hasNext() : false;
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
        --this.pendingCount;
    }

    @Override
    public synchronized void remove(MessageReference node) {
        if (!node.isPersistent()) {
            this.nonPersistent.remove(node);
        } else {
            this.persistent.remove(node);
        }
        --this.pendingCount;
    }

    @Override
    public synchronized void reset() {
        this.nonPersistent.reset();
        this.persistent.reset();
        this.pendingCount = this.persistent.size() + this.nonPersistent.size();
    }

    @Override
    public void release() {
        this.nonPersistent.release();
        this.persistent.release();
    }

    @Override
    public synchronized int size() {
        if (this.pendingCount < 0) {
            this.pendingCount = this.persistent.size() + this.nonPersistent.size();
        }
        return this.pendingCount;
    }

    @Override
    public synchronized long messageSize() {
        return this.persistent.messageSize() + this.nonPersistent.messageSize();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.pendingCount == 0;
    }

    @Override
    public boolean isRecoveryRequired() {
        return false;
    }

    public PendingMessageCursor getNonPersistent() {
        return this.nonPersistent;
    }

    public void setNonPersistent(PendingMessageCursor nonPersistent) {
        this.nonPersistent = nonPersistent;
    }

    @Override
    public void setMaxBatchSize(int maxBatchSize) {
        this.persistent.setMaxBatchSize(maxBatchSize);
        if (this.nonPersistent != null) {
            this.nonPersistent.setMaxBatchSize(maxBatchSize);
        }
        super.setMaxBatchSize(maxBatchSize);
    }

    @Override
    public void setMaxProducersToAudit(int maxProducersToAudit) {
        super.setMaxProducersToAudit(maxProducersToAudit);
        if (this.persistent != null) {
            this.persistent.setMaxProducersToAudit(maxProducersToAudit);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setMaxProducersToAudit(maxProducersToAudit);
        }
    }

    @Override
    public void setMaxAuditDepth(int maxAuditDepth) {
        super.setMaxAuditDepth(maxAuditDepth);
        if (this.persistent != null) {
            this.persistent.setMaxAuditDepth(maxAuditDepth);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setMaxAuditDepth(maxAuditDepth);
        }
    }

    @Override
    public void setEnableAudit(boolean enableAudit) {
        super.setEnableAudit(enableAudit);
        if (this.persistent != null) {
            this.persistent.setEnableAudit(enableAudit);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setEnableAudit(enableAudit);
        }
    }

    @Override
    public void setUseCache(boolean useCache) {
        super.setUseCache(useCache);
        if (this.persistent != null) {
            this.persistent.setUseCache(useCache);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setUseCache(useCache);
        }
    }

    @Override
    public void setMemoryUsageHighWaterMark(int memoryUsageHighWaterMark) {
        super.setMemoryUsageHighWaterMark(memoryUsageHighWaterMark);
        if (this.persistent != null) {
            this.persistent.setMemoryUsageHighWaterMark(memoryUsageHighWaterMark);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setMemoryUsageHighWaterMark(memoryUsageHighWaterMark);
        }
    }

    @Override
    public synchronized void gc() {
        if (this.persistent != null) {
            this.persistent.gc();
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.gc();
        }
        this.pendingCount = this.persistent.size() + this.nonPersistent.size();
    }

    @Override
    public void setSystemUsage(SystemUsage usageManager) {
        super.setSystemUsage(usageManager);
        if (this.persistent != null) {
            this.persistent.setSystemUsage(usageManager);
        }
        if (this.nonPersistent != null) {
            this.nonPersistent.setSystemUsage(usageManager);
        }
    }

    protected synchronized PendingMessageCursor getNextCursor() throws Exception {
        if (this.currentCursor == null || !this.currentCursor.hasMessagesBufferedToDeliver()) {
            PendingMessageCursor pendingMessageCursor = this.currentCursor = this.currentCursor == this.persistent ? this.nonPersistent : this.persistent;
            if (this.currentCursor.isEmpty()) {
                this.currentCursor = this.currentCursor == this.persistent ? this.nonPersistent : this.persistent;
            }
        }
        return this.currentCursor;
    }

    @Override
    public boolean isCacheEnabled() {
        boolean cacheEnabled = this.isUseCache();
        if (cacheEnabled) {
            if (this.persistent != null) {
                cacheEnabled &= this.persistent.isCacheEnabled();
            }
            if (this.nonPersistent != null) {
                cacheEnabled &= this.nonPersistent.isCacheEnabled();
            }
            this.setCacheEnabled(cacheEnabled);
        }
        return cacheEnabled;
    }

    @Override
    public void rebase() {
        this.persistent.rebase();
        this.reset();
    }
}

