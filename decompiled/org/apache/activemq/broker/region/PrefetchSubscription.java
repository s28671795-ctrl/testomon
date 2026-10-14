/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractSubscription;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.Response;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.transport.TransmitCallback;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PrefetchSubscription
extends AbstractSubscription {
    private static final Logger LOG = LoggerFactory.getLogger(PrefetchSubscription.class);
    protected final Scheduler scheduler;
    protected PendingMessageCursor pending;
    protected final List<MessageReference> dispatched = new ArrayList<MessageReference>();
    private int maxProducersToAudit = 32;
    private int maxAuditDepth = 2048;
    protected final SystemUsage usageManager;
    protected final Object pendingLock = new Object();
    protected final Object dispatchLock = new Object();
    private final CountDownLatch okForAckAsDispatchDone = new CountDownLatch(1);

    public PrefetchSubscription(Broker broker, SystemUsage usageManager, ConnectionContext context, ConsumerInfo info, PendingMessageCursor cursor) throws JMSException {
        super(broker, context, info);
        this.usageManager = usageManager;
        this.pending = cursor;
        try {
            this.pending.start();
        }
        catch (Exception e) {
            throw new JMSException(e.getMessage());
        }
        this.scheduler = broker.getScheduler();
    }

    public PrefetchSubscription(Broker broker, SystemUsage usageManager, ConnectionContext context, ConsumerInfo info) throws JMSException {
        this(broker, usageManager, context, info, new VMPendingMessageCursor(false));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Response pullMessage(ConnectionContext context, final MessagePull pull) throws Exception {
        if (this.getPrefetchSize() == 0) {
            this.prefetchExtension.set(pull.getQuantity());
            final long dispatchCounterBeforePull = this.getSubscriptionStatistics().getDispatched().getCount();
            for (Destination dest : this.destinations) {
                dest.iterate();
            }
            this.dispatchPending();
            PrefetchSubscription prefetchSubscription = this;
            synchronized (prefetchSubscription) {
                if (dispatchCounterBeforePull == this.getSubscriptionStatistics().getDispatched().getCount() || pull.isAlwaysSignalDone()) {
                    if (pull.getTimeout() == -1L) {
                        this.prefetchExtension.set(1);
                        this.add(QueueMessageReference.NULL_MESSAGE);
                        this.dispatchPending();
                    }
                    if (pull.getTimeout() > 0L) {
                        this.scheduler.executeAfterDelay(new Runnable(){

                            @Override
                            public void run() {
                                PrefetchSubscription.this.pullTimeout(dispatchCounterBeforePull, pull.isAlwaysSignalDone());
                            }
                        }, pull.getTimeout());
                    }
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void pullTimeout(long dispatchCounterBeforePull, boolean alwaysSignalDone) {
        Object object = this.pendingLock;
        synchronized (object) {
            if (dispatchCounterBeforePull == this.getSubscriptionStatistics().getDispatched().getCount() || alwaysSignalDone) {
                try {
                    this.prefetchExtension.set(1);
                    this.add(QueueMessageReference.NULL_MESSAGE);
                    this.dispatchPending();
                }
                catch (Exception e) {
                    this.context.getConnection().serviceException(e);
                }
                finally {
                    this.prefetchExtension.set(0);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(MessageReference node) throws Exception {
        Object object = this.pendingLock;
        synchronized (object) {
            if (!this.destinations.contains(node.getRegionDestination()) && node != QueueMessageReference.NULL_MESSAGE) {
                return;
            }
            if (!node.equals(QueueMessageReference.NULL_MESSAGE)) {
                this.getSubscriptionStatistics().getEnqueues().increment();
            }
            this.pending.addMessageLast(node);
        }
        this.dispatchPending();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processMessageDispatchNotification(MessageDispatchNotification mdn) throws Exception {
        Object object = this.pendingLock;
        synchronized (object) {
            try {
                this.pending.reset();
                while (true) {
                    if (!this.pending.hasNext()) break;
                    MessageReference node = this.pending.next();
                    node.decrementReferenceCount();
                    if (!node.getMessageId().equals(mdn.getMessageId())) continue;
                    Object object2 = this.dispatchLock;
                    synchronized (object2) {
                        this.pending.remove();
                        this.createMessageDispatch(node, node.getMessage());
                        this.dispatched.add(node);
                        this.getSubscriptionStatistics().getInflightMessageSize().addSize(node.getSize());
                        this.onDispatch(node, node.getMessage());
                    }
                    return;
                }
            }
            finally {
                this.pending.release();
            }
        }
        throw new JMSException("Slave broker out of sync with master: Dispatched message (" + mdn.getMessageId() + ") was not in the pending list for " + mdn.getConsumerId() + " on " + mdn.getDestination().getPhysicalName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void acknowledge(ConnectionContext context, MessageAck ack) throws Exception {
        boolean callDispatchMatched = false;
        Destination destination = null;
        if (!this.okForAckAsDispatchDone.await(0L, TimeUnit.MILLISECONDS)) {
            LOG.warn("Ignoring ack received before dispatch; result of failover with an outstanding ack. Acked messages will be replayed if present on this broker. Ignored ack: {}", (Object)ack);
            return;
        }
        LOG.trace("ack: {}", (Object)ack);
        Object object = this.dispatchLock;
        synchronized (object) {
            if (ack.isStandardAck()) {
                this.assertAckMatchesDispatched(ack);
                boolean inAckRange = false;
                ArrayList<MessageReference> removeList = new ArrayList<MessageReference>();
                for (MessageReference node : this.dispatched) {
                    MessageId messageId = node.getMessageId();
                    if (ack.getFirstMessageId() == null || ack.getFirstMessageId().equals(messageId)) {
                        inAckRange = true;
                    }
                    if (!inAckRange) continue;
                    if (!context.isInTransaction()) {
                        this.getSubscriptionStatistics().getDequeues().increment();
                        ((Destination)node.getRegionDestination()).getDestinationStatistics().getInflight().decrement();
                        removeList.add(node);
                        this.contractPrefetchExtension(1);
                    } else {
                        this.registerRemoveSync(context, node);
                    }
                    this.acknowledge(context, ack, node);
                    if (!ack.getLastMessageId().equals(messageId)) continue;
                    destination = (Destination)node.getRegionDestination();
                    callDispatchMatched = true;
                    break;
                }
                for (MessageReference node : removeList) {
                    this.dispatched.remove(node);
                    this.getSubscriptionStatistics().getInflightMessageSize().addSize(-node.getSize());
                }
                if (!callDispatchMatched) {
                    LOG.warn("Could not correlate acknowledgment with dispatched message: {}", (Object)ack);
                }
            } else if (ack.isIndividualAck()) {
                for (MessageReference node : this.dispatched) {
                    MessageId messageId = node.getMessageId();
                    if (!ack.getLastMessageId().equals(messageId)) continue;
                    if (!context.isInTransaction()) {
                        this.getSubscriptionStatistics().getDequeues().increment();
                        ((Destination)node.getRegionDestination()).getDestinationStatistics().getInflight().decrement();
                        this.dispatched.remove(node);
                        this.getSubscriptionStatistics().getInflightMessageSize().addSize(-node.getSize());
                        this.contractPrefetchExtension(1);
                    } else {
                        this.registerRemoveSync(context, node);
                        this.expandPrefetchExtension(1);
                    }
                    this.acknowledge(context, ack, node);
                    destination = (Destination)node.getRegionDestination();
                    callDispatchMatched = true;
                    break;
                }
            } else if (ack.isDeliveredAck()) {
                int index = 0;
                for (MessageReference node : this.dispatched) {
                    Destination nodeDest = (Destination)node.getRegionDestination();
                    if (ack.getLastMessageId().equals(node.getMessageId())) {
                        this.expandPrefetchExtension(ack.getMessageCount());
                        destination = nodeDest;
                        callDispatchMatched = true;
                        break;
                    }
                    ++index;
                }
                if (!callDispatchMatched) {
                    throw new JMSException("Could not correlate acknowledgment with dispatched message: " + ack);
                }
            } else if (ack.isExpiredAck()) {
                int index = 0;
                boolean inAckRange = false;
                Iterator<MessageReference> iter = this.dispatched.iterator();
                while (iter.hasNext()) {
                    MessageReference node = iter.next();
                    Destination nodeDest = (Destination)node.getRegionDestination();
                    MessageId messageId = node.getMessageId();
                    if (ack.getFirstMessageId() == null || ack.getFirstMessageId().equals(messageId)) {
                        inAckRange = true;
                    }
                    if (inAckRange) {
                        Destination regionDestination = nodeDest;
                        if (this.broker.isExpired(node)) {
                            regionDestination.messageExpired(context, this, node);
                        }
                        iter.remove();
                        nodeDest.getDestinationStatistics().getInflight().decrement();
                        if (ack.getLastMessageId().equals(messageId)) {
                            this.contractPrefetchExtension(1);
                            destination = (Destination)node.getRegionDestination();
                            callDispatchMatched = true;
                            break;
                        }
                    }
                    ++index;
                }
                if (!callDispatchMatched) {
                    throw new JMSException("Could not correlate expiration acknowledgment with dispatched message: " + ack);
                }
            } else if (ack.isRedeliveredAck()) {
                boolean inAckRange = false;
                for (MessageReference node : this.dispatched) {
                    MessageId messageId = node.getMessageId();
                    if (ack.getFirstMessageId() == null || ack.getFirstMessageId().equals(messageId)) {
                        inAckRange = true;
                    }
                    if (!inAckRange || !ack.getLastMessageId().equals(messageId)) continue;
                    destination = (Destination)node.getRegionDestination();
                    callDispatchMatched = true;
                    break;
                }
                if (!callDispatchMatched) {
                    throw new JMSException("Could not correlate acknowledgment with dispatched message: " + ack);
                }
            } else if (ack.isPoisonAck()) {
                if (ack.isInTransaction()) {
                    throw new JMSException("Poison ack cannot be transacted: " + ack);
                }
                int index = 0;
                boolean inAckRange = false;
                ArrayList<MessageReference> removeList = new ArrayList<MessageReference>();
                for (MessageReference node : this.dispatched) {
                    MessageId messageId = node.getMessageId();
                    if (ack.getFirstMessageId() == null || ack.getFirstMessageId().equals(messageId)) {
                        inAckRange = true;
                    }
                    if (!inAckRange) continue;
                    this.sendToDLQ(context, node, ack.getPoisonCause());
                    Destination nodeDest = (Destination)node.getRegionDestination();
                    nodeDest.getDestinationStatistics().getInflight().decrement();
                    removeList.add(node);
                    this.getSubscriptionStatistics().getDequeues().increment();
                    ++index;
                    this.acknowledge(context, ack, node);
                    if (!ack.getLastMessageId().equals(messageId)) continue;
                    this.contractPrefetchExtension(1);
                    destination = nodeDest;
                    callDispatchMatched = true;
                    break;
                }
                for (MessageReference node : removeList) {
                    this.dispatched.remove(node);
                    this.getSubscriptionStatistics().getInflightMessageSize().addSize(-node.getSize());
                }
                if (!callDispatchMatched) {
                    throw new JMSException("Could not correlate acknowledgment with dispatched message: " + ack);
                }
            }
        }
        if (callDispatchMatched && destination != null) {
            destination.wakeup();
            this.dispatchPending();
            if (this.pending.isEmpty()) {
                this.wakeupDestinationsForDispatch();
            }
        } else {
            LOG.debug("Acknowledgment out of sync (Normally occurs when failover connection reconnects): {}", (Object)ack);
        }
    }

    private void registerRemoveSync(ConnectionContext context, final MessageReference node) {
        context.getTransaction().addSynchronization(new Synchronization(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void afterCommit() throws Exception {
                Destination nodeDest = (Destination)node.getRegionDestination();
                Object object = PrefetchSubscription.this.dispatchLock;
                synchronized (object) {
                    PrefetchSubscription.this.getSubscriptionStatistics().getDequeues().increment();
                    PrefetchSubscription.this.dispatched.remove(node);
                    PrefetchSubscription.this.getSubscriptionStatistics().getInflightMessageSize().addSize(-node.getSize());
                    nodeDest.getDestinationStatistics().getInflight().decrement();
                }
                PrefetchSubscription.this.contractPrefetchExtension(1);
                nodeDest.wakeup();
                PrefetchSubscription.this.dispatchPending();
            }

            @Override
            public void afterRollback() throws Exception {
                PrefetchSubscription.this.contractPrefetchExtension(1);
            }
        });
    }

    protected void assertAckMatchesDispatched(MessageAck ack) throws JMSException {
        MessageId firstAckedMsg = ack.getFirstMessageId();
        MessageId lastAckedMsg = ack.getLastMessageId();
        int checkCount = 0;
        boolean checkFoundStart = false;
        boolean checkFoundEnd = false;
        for (MessageReference node : this.dispatched) {
            if (firstAckedMsg == null) {
                checkFoundStart = true;
            } else if (!checkFoundStart && firstAckedMsg.equals(node.getMessageId())) {
                checkFoundStart = true;
            }
            if (checkFoundStart) {
                ++checkCount;
            }
            if (lastAckedMsg == null || !lastAckedMsg.equals(node.getMessageId())) continue;
            checkFoundEnd = true;
            break;
        }
        if (!checkFoundStart && firstAckedMsg != null) {
            throw new JMSException("Unmatched acknowledge: " + ack + "; Could not find Message-ID " + firstAckedMsg + " in dispatched-list (start of ack)");
        }
        if (!checkFoundEnd && lastAckedMsg != null) {
            throw new JMSException("Unmatched acknowledge: " + ack + "; Could not find Message-ID " + lastAckedMsg + " in dispatched-list (end of ack)");
        }
        if (ack.getMessageCount() != checkCount && !ack.isInTransaction()) {
            throw new JMSException("Unmatched acknowledge: " + ack + "; Expected message count (" + ack.getMessageCount() + ") differs from count in dispatched-list (" + checkCount + ")");
        }
    }

    protected void sendToDLQ(ConnectionContext context, MessageReference node, Throwable poisonCause) throws IOException, Exception {
        this.broker.getRoot().sendToDeadLetterQueue(context, node, this, poisonCause);
    }

    @Override
    public int getInFlightSize() {
        return this.dispatched.size();
    }

    @Override
    public boolean isFull() {
        return this.getPrefetchSize() == 0 ? this.prefetchExtension.get() == 0 : this.dispatched.size() - this.prefetchExtension.get() >= this.info.getPrefetchSize();
    }

    @Override
    public boolean isLowWaterMark() {
        return (double)(this.dispatched.size() - this.prefetchExtension.get()) <= (double)this.info.getPrefetchSize() * 0.4;
    }

    @Override
    public boolean isHighWaterMark() {
        return (double)(this.dispatched.size() - this.prefetchExtension.get()) >= (double)this.info.getPrefetchSize() * 0.9;
    }

    @Override
    public int countBeforeFull() {
        return this.getPrefetchSize() == 0 ? this.prefetchExtension.get() : this.info.getPrefetchSize() + this.prefetchExtension.get() - this.dispatched.size();
    }

    @Override
    public int getPendingQueueSize() {
        return this.pending.size();
    }

    @Override
    public long getPendingMessageSize() {
        return this.pending.messageSize();
    }

    @Override
    public int getDispatchedQueueSize() {
        return this.dispatched.size();
    }

    @Override
    public long getDequeueCounter() {
        return this.getSubscriptionStatistics().getDequeues().getCount();
    }

    @Override
    public long getDispatchedCounter() {
        return this.getSubscriptionStatistics().getDispatched().getCount();
    }

    @Override
    public long getEnqueueCounter() {
        return this.getSubscriptionStatistics().getEnqueues().getCount();
    }

    @Override
    public boolean isRecoveryRequired() {
        return this.pending.isRecoveryRequired();
    }

    public PendingMessageCursor getPending() {
        return this.pending;
    }

    public void setPending(PendingMessageCursor pending) {
        this.pending = pending;
        if (this.pending != null) {
            this.pending.setSystemUsage(this.usageManager);
            this.pending.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(ConnectionContext context, Destination destination) throws Exception {
        Object object = this.pendingLock;
        synchronized (object) {
            super.add(context, destination);
            this.pending.add(context, destination);
        }
    }

    @Override
    public List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        return this.remove(context, destination, this.dispatched);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<MessageReference> remove(ConnectionContext context, Destination destination, List<MessageReference> dispatched) throws Exception {
        LinkedList<MessageReference> redispatch = new LinkedList<MessageReference>();
        Object object = this.pendingLock;
        synchronized (object) {
            super.remove(context, destination);
            redispatch.addAll(this.pending.remove(context, destination));
            if (dispatched == null) {
                return redispatch;
            }
            if (dispatched == this.dispatched) {
                Object object2 = this.dispatchLock;
                synchronized (object2) {
                    this.addReferencesAndUpdateRedispatch(redispatch, destination, dispatched);
                }
            } else {
                this.addReferencesAndUpdateRedispatch(redispatch, destination, dispatched);
            }
        }
        return redispatch;
    }

    private void addReferencesAndUpdateRedispatch(LinkedList<MessageReference> redispatch, Destination destination, List<MessageReference> dispatched) {
        ArrayList<MessageReference> references = new ArrayList<MessageReference>();
        for (MessageReference r : dispatched) {
            if (r.getRegionDestination() != destination) continue;
            references.add(r);
            this.getSubscriptionStatistics().getInflightMessageSize().addSize(-r.getSize());
        }
        redispatch.addAll(0, references);
        destination.getDestinationStatistics().getInflight().subtract(references.size());
        dispatched.removeAll(references);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispatchPending() throws IOException {
        CopyOnWriteArrayList slowConsumerTargets = null;
        Iterator iterator2 = this.pendingLock;
        synchronized (iterator2) {
            block19: {
                try {
                    int numberToDispatch = this.countBeforeFull();
                    if (numberToDispatch > 0) {
                        this.setSlowConsumer(false);
                        this.setPendingBatchSize(this.pending, numberToDispatch);
                        int count = 0;
                        this.pending.reset();
                        while (count < numberToDispatch && !this.isFull() && this.pending.hasNext()) {
                            MessageReference node = this.pending.next();
                            if (node == null) {
                                break block19;
                            }
                            Object object = this.dispatchLock;
                            synchronized (object) {
                                this.pending.remove();
                                if (!this.isDropped(node) && this.canDispatch(node)) {
                                    if (node != QueueMessageReference.NULL_MESSAGE && node.isExpired()) {
                                        ++numberToDispatch;
                                        if (this.broker.isExpired(node)) {
                                            ((Destination)node.getRegionDestination()).messageExpired(this.context, this, node);
                                        }
                                        if (!this.isBrowser()) {
                                            node.decrementReferenceCount();
                                            continue;
                                        }
                                    }
                                    this.dispatch(node);
                                    ++count;
                                }
                            }
                            node.decrementReferenceCount();
                        }
                        break block19;
                    }
                    if (!this.isSlowConsumer()) {
                        this.setSlowConsumer(true);
                        slowConsumerTargets = this.destinations;
                    }
                }
                finally {
                    this.pending.release();
                }
            }
        }
        if (slowConsumerTargets != null) {
            for (Destination dest : slowConsumerTargets) {
                dest.slowConsumer(this.context, this);
            }
        }
    }

    protected void setPendingBatchSize(PendingMessageCursor pending, int numberToDispatch) {
        pending.setMaxBatchSize(numberToDispatch);
    }

    protected boolean dispatch(final MessageReference node) throws IOException {
        final Message message = node.getMessage();
        if (message == null) {
            return false;
        }
        this.okForAckAsDispatchDone.countDown();
        MessageDispatch md = this.createMessageDispatch(node, message);
        if (node != QueueMessageReference.NULL_MESSAGE) {
            this.getSubscriptionStatistics().getDispatched().increment();
            this.dispatched.add(node);
            this.getSubscriptionStatistics().getInflightMessageSize().addSize(node.getSize());
        }
        if (this.getPrefetchSize() == 0) {
            int newExtension;
            int currentExtension;
            while (!this.prefetchExtension.compareAndSet(currentExtension = this.prefetchExtension.get(), newExtension = Math.max(0, currentExtension - 1))) {
            }
        }
        if (this.info.isDispatchAsync()) {
            md.setTransmitCallback(new TransmitCallback(){

                @Override
                public void onSuccess() {
                    PrefetchSubscription.this.onDispatch(node, message);
                }

                @Override
                public void onFailure() {
                    Destination nodeDest = (Destination)node.getRegionDestination();
                    if (nodeDest != null && node != QueueMessageReference.NULL_MESSAGE) {
                        nodeDest.getDestinationStatistics().getDispatched().increment();
                        nodeDest.getDestinationStatistics().getInflight().increment();
                        LOG.trace("{} failed to dispatch: {} - {}, dispatched: {}, inflight: {}", PrefetchSubscription.this.info.getConsumerId(), message.getMessageId(), message.getDestination(), PrefetchSubscription.this.getSubscriptionStatistics().getDispatched().getCount(), PrefetchSubscription.this.dispatched.size());
                    }
                    if (node instanceof QueueMessageReference) {
                        ((QueueMessageReference)node).unlock();
                    }
                }
            });
            this.context.getConnection().dispatchAsync(md);
        } else {
            this.context.getConnection().dispatchSync(md);
            this.onDispatch(node, message);
        }
        return true;
    }

    protected void onDispatch(MessageReference node, Message message) {
        Destination nodeDest = (Destination)node.getRegionDestination();
        if (nodeDest != null && node != QueueMessageReference.NULL_MESSAGE) {
            nodeDest.getDestinationStatistics().getDispatched().increment();
            nodeDest.getDestinationStatistics().getInflight().increment();
            LOG.trace("{} dispatched: {} - {}, dispatched: {}, inflight: {}", this.info.getConsumerId(), message.getMessageId(), message.getDestination(), this.getSubscriptionStatistics().getDispatched().getCount(), this.dispatched.size());
        }
        if (this.info.isDispatchAsync()) {
            try {
                this.dispatchPending();
            }
            catch (IOException e) {
                this.context.getConnection().serviceExceptionAsync(e);
            }
        }
    }

    @Override
    public void updateConsumerPrefetch(int newPrefetch) {
        if (this.context != null && this.context.getConnection() != null && this.context.getConnection().isManageable()) {
            ConsumerControl cc = new ConsumerControl();
            cc.setConsumerId(this.info.getConsumerId());
            cc.setPrefetch(newPrefetch);
            this.context.getConnection().dispatchAsync(cc);
        }
    }

    protected MessageDispatch createMessageDispatch(MessageReference node, Message message) {
        MessageDispatch md = new MessageDispatch();
        md.setConsumerId(this.info.getConsumerId());
        if (node == QueueMessageReference.NULL_MESSAGE) {
            md.setMessage(null);
            md.setDestination(null);
        } else {
            Destination regionDestination = (Destination)node.getRegionDestination();
            md.setDestination(regionDestination.getActiveMQDestination());
            md.setMessage(message);
            md.setRedeliveryCounter(node.getRedeliveryCounter());
        }
        return md;
    }

    protected abstract boolean canDispatch(MessageReference var1) throws IOException;

    protected abstract boolean isDropped(MessageReference var1);

    protected abstract void acknowledge(ConnectionContext var1, MessageAck var2, MessageReference var3) throws IOException;

    public int getMaxProducersToAudit() {
        return this.maxProducersToAudit;
    }

    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.maxProducersToAudit = maxProducersToAudit;
        if (this.pending != null) {
            this.pending.setMaxProducersToAudit(maxProducersToAudit);
        }
    }

    public int getMaxAuditDepth() {
        return this.maxAuditDepth;
    }

    public void setMaxAuditDepth(int maxAuditDepth) {
        this.maxAuditDepth = maxAuditDepth;
        if (this.pending != null) {
            this.pending.setMaxAuditDepth(maxAuditDepth);
        }
    }

    @Override
    public void setPrefetchSize(int prefetchSize) {
        this.info.setPrefetchSize(prefetchSize);
        try {
            this.dispatchPending();
        }
        catch (Exception e) {
            LOG.trace("Caught exception during dispatch after prefetch change.", e);
        }
    }
}

