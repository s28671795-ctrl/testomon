/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.cursors.AbstractStoreCursor;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.store.TopicMessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TopicStorePrefetch
extends AbstractStoreCursor {
    private static final Logger LOG = LoggerFactory.getLogger(TopicStorePrefetch.class);
    private final TopicMessageStore store;
    private final String clientId;
    private final String subscriberName;
    private final Subscription subscription;
    private byte lastRecoveredPriority = (byte)9;
    private boolean storeHasMessages = false;

    public TopicStorePrefetch(Subscription subscription, Topic topic, String clientId, String subscriberName) {
        super(topic);
        this.subscription = subscription;
        this.store = (TopicMessageStore)topic.getMessageStore();
        this.clientId = clientId;
        this.subscriberName = subscriberName;
        this.maxProducersToAudit = 32;
        this.maxAuditDepth = 10000;
        this.resetSize();
        this.storeHasMessages = this.size > 0;
    }

    @Override
    public boolean recoverMessageReference(MessageId messageReference) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public synchronized void addMessageFirst(MessageReference node) throws Exception {
        this.batchList.addMessageFirst(node);
        ++this.size;
        node.incrementReferenceCount();
    }

    @Override
    public final synchronized boolean addMessageLast(MessageReference node) throws Exception {
        this.storeHasMessages = super.addMessageLast(node);
        return this.storeHasMessages;
    }

    @Override
    public synchronized boolean recoverMessage(Message message, boolean cached) throws Exception {
        LOG.trace("{} recover: {}, priority: {}", this, message.getMessageId(), message.getPriority());
        boolean recovered = false;
        NonCachedMessageEvaluationContext messageEvaluationContext = new NonCachedMessageEvaluationContext();
        ((MessageEvaluationContext)messageEvaluationContext).setMessageReference(message);
        if (this.subscription.matches(message, messageEvaluationContext)) {
            recovered = super.recoverMessage(message, cached);
            if (recovered && !cached) {
                this.lastRecoveredPriority = message.getPriority();
            }
            this.storeHasMessages = true;
        }
        return recovered;
    }

    @Override
    protected boolean duplicateFromStoreExcepted(Message message) {
        return true;
    }

    @Override
    protected synchronized int getStoreSize() {
        try {
            return this.store.getMessageCount(this.clientId, this.subscriberName);
        }
        catch (Exception e) {
            LOG.error("{} Failed to get the outstanding message count from the store", (Object)this, (Object)e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected synchronized long getStoreMessageSize() {
        try {
            return this.store.getMessageSize(this.clientId, this.subscriberName);
        }
        catch (Exception e) {
            LOG.error("{} Failed to get the outstanding message count from the store", (Object)this, (Object)e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected synchronized boolean isStoreEmpty() {
        try {
            return this.store.isEmpty();
        }
        catch (Exception e) {
            LOG.error("Failed to determine if store is empty", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void resetBatch() {
        this.store.resetBatching(this.clientId, this.subscriberName);
    }

    @Override
    protected void doFillBatch() throws Exception {
        this.storeHasMessages = false;
        this.store.recoverNextMessages(this.clientId, this.subscriberName, this.maxBatchSize, this);
        this.dealWithDuplicates();
        if (!(this.storeHasMessages || this.batchList.isEmpty() && this.hadSpace)) {
            this.storeHasMessages = true;
        }
    }

    public byte getLastRecoveredPriority() {
        return this.lastRecoveredPriority;
    }

    public final boolean isPaging() {
        return !this.isCacheEnabled() && !this.batchList.isEmpty();
    }

    @Override
    public Subscription getSubscription() {
        return this.subscription;
    }

    @Override
    public String toString() {
        return "TopicStorePrefetch(" + this.clientId + "," + this.subscriberName + ",storeHasMessages=" + this.storeHasMessages + ") " + this.subscription.getConsumerInfo().getConsumerId() + " - " + super.toString();
    }
}

