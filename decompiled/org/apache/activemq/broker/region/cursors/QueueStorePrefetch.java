/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.io.IOException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.cursors.AbstractStoreCursor;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.MessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QueueStorePrefetch
extends AbstractStoreCursor {
    private static final Logger LOG = LoggerFactory.getLogger(QueueStorePrefetch.class);
    private final MessageStore store;
    private final Queue queue;
    private final Broker broker;

    public QueueStorePrefetch(Queue queue, Broker broker) {
        super(queue);
        this.queue = queue;
        this.store = queue.getMessageStore();
        this.broker = broker;
    }

    @Override
    public boolean recoverMessageReference(MessageId messageReference) throws Exception {
        Message msg = this.store.getMessage(messageReference);
        if (msg != null) {
            return this.recoverMessage(msg);
        }
        String err = "Failed to retrieve message for id: " + messageReference;
        LOG.error(err);
        throw new IOException(err);
    }

    @Override
    protected synchronized int getStoreSize() {
        try {
            int result = this.store.getMessageCount();
            return result;
        }
        catch (IOException e) {
            LOG.error("Failed to get message count", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected synchronized long getStoreMessageSize() {
        try {
            return this.store.getMessageSize();
        }
        catch (IOException e) {
            LOG.error("Failed to get message size", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean canEnableCash() {
        return super.canEnableCash() && this.queue.singlePendingSend();
    }

    @Override
    protected synchronized boolean isStoreEmpty() {
        try {
            return this.store.isEmpty();
        }
        catch (Exception e) {
            LOG.error("Failed to get message count", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void resetBatch() {
        this.store.resetBatching();
    }

    @Override
    protected void setBatch(MessageId messageId) throws Exception {
        if (LOG.isTraceEnabled()) {
            LOG.trace("{}  setBatch {} seq: {}, loc: {}", this, messageId, messageId.getFutureOrSequenceLong(), messageId.getEntryLocator());
        }
        this.store.setBatch(messageId);
        this.batchResetNeeded = false;
    }

    @Override
    protected void doFillBatch() throws Exception {
        this.hadSpace = this.hasSpace();
        if (!this.broker.getBrokerService().isPersistent() || this.hadSpace) {
            this.store.recoverNextMessages(this.maxBatchSize, this);
            this.dealWithDuplicates();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ",store=" + this.store;
    }
}

