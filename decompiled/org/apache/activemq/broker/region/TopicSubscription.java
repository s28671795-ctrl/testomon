/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQMessageAudit;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractSubscription;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.IndirectMessageReference;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.FilePendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.broker.region.policy.MessageEvictionStrategy;
import org.apache.activemq.broker.region.policy.OldestMessageEvictionStrategy;
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

public class TopicSubscription
extends AbstractSubscription {
    private static final Logger LOG = LoggerFactory.getLogger(TopicSubscription.class);
    private static final AtomicLong CURSOR_NAME_COUNTER = new AtomicLong(0L);
    protected PendingMessageCursor matched;
    protected final SystemUsage usageManager;
    boolean singleDestination = true;
    Destination destination;
    private final Scheduler scheduler;
    private int maximumPendingMessages = -1;
    private MessageEvictionStrategy messageEvictionStrategy = new OldestMessageEvictionStrategy();
    private final AtomicInteger discarded = new AtomicInteger();
    private final Object matchedListMutex = new Object();
    private int memoryUsageHighWaterMark = 95;
    protected int maxProducersToAudit = 1024;
    protected int maxAuditDepth = 1000;
    protected boolean enableAudit = false;
    protected ActiveMQMessageAudit audit;
    protected boolean active = false;
    protected boolean discarding = false;
    private boolean useTopicSubscriptionInflightStats = true;
    protected final Object dispatchLock = new Object();
    protected final List<DispatchedNode> dispatched = new ArrayList<DispatchedNode>();

    public TopicSubscription(Broker broker, ConnectionContext context, ConsumerInfo info, SystemUsage usageManager) throws Exception {
        super(broker, context, info);
        this.usageManager = usageManager;
        String matchedName = "TopicSubscription:" + CURSOR_NAME_COUNTER.getAndIncrement() + "[" + info.getConsumerId().toString() + "]";
        this.matched = info.getDestination().isTemporary() || broker.getTempDataStore() == null ? new VMPendingMessageCursor(false) : new FilePendingMessageCursor(broker, matchedName, false);
        this.scheduler = broker.getScheduler();
    }

    public void init() throws Exception {
        this.matched.setSystemUsage(this.usageManager);
        this.matched.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
        this.matched.start();
        if (this.enableAudit) {
            this.audit = new ActiveMQMessageAudit(this.maxAuditDepth, this.maxProducersToAudit);
        }
        this.active = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(MessageReference node) throws Exception {
        if (this.isDuplicate(node)) {
            return;
        }
        node = new IndirectMessageReference(node.getMessage());
        this.getSubscriptionStatistics().getEnqueues().increment();
        Object object = this.matchedListMutex;
        synchronized (object) {
            if (this.discarding) {
                return;
            }
            if (!this.isFull() && this.matched.isEmpty()) {
                this.dispatch(node);
                this.setSlowConsumer(false);
            } else {
                if (this.info.getPrefetchSize() > 1 && this.matched.size() > this.info.getPrefetchSize() && !this.isSlowConsumer()) {
                    LOG.warn("{}: has twice its prefetch limit pending, without an ack; it appears to be slow", (Object)this.toString());
                    this.setSlowConsumer(true);
                    for (Destination dest : this.destinations) {
                        dest.slowConsumer(this.getContext(), this);
                    }
                }
                if (this.maximumPendingMessages != 0) {
                    boolean warnedAboutWait = false;
                    while (this.active) {
                        while (this.matched.isFull()) {
                            if (this.getContext().getStopping().get()) {
                                LOG.warn("{}: stopped waiting for space in pendingMessage cursor for: {}", (Object)this.toString(), (Object)node.getMessageId());
                                this.getSubscriptionStatistics().getEnqueues().decrement();
                                return;
                            }
                            if (!warnedAboutWait) {
                                LOG.info("{}: Pending message cursor [{}] is full, temp usag ({}%) or memory usage ({}%) limit reached, blocking message add() pending the release of resources.", this.toString(), this.matched, this.matched.getSystemUsage().getTempUsage().getPercentUsage(), this.matched.getSystemUsage().getMemoryUsage().getPercentUsage());
                                warnedAboutWait = true;
                            }
                            this.matchedListMutex.wait(20L);
                        }
                        if (!this.matched.tryAddMessageLast(node, 10L)) continue;
                    }
                    if (this.maximumPendingMessages > 0) {
                        int max = this.messageEvictionStrategy.getEvictExpiredMessagesHighWatermark();
                        if (this.maximumPendingMessages > 0 && this.maximumPendingMessages < max) {
                            max = this.maximumPendingMessages;
                        }
                        if (!this.matched.isEmpty() && this.matched.size() > max) {
                            this.removeExpiredMessages();
                        }
                        while (!this.matched.isEmpty() && this.matched.size() > this.maximumPendingMessages) {
                            int pageInSize = this.matched.size() - this.maximumPendingMessages;
                            pageInSize = Math.max(1000, pageInSize);
                            LinkedList<MessageReference> list = null;
                            MessageReference[] oldMessages = null;
                            PendingMessageCursor pendingMessageCursor = this.matched;
                            synchronized (pendingMessageCursor) {
                                list = this.matched.pageInList(pageInSize);
                                oldMessages = this.messageEvictionStrategy.evictMessages(list);
                                for (MessageReference ref : list) {
                                    ref.decrementReferenceCount();
                                }
                            }
                            int messagesToEvict = 0;
                            if (oldMessages != null) {
                                messagesToEvict = oldMessages.length;
                                for (int i = 0; i < messagesToEvict; ++i) {
                                    MessageReference oldMessage = oldMessages[i];
                                    this.discard(oldMessage);
                                }
                            }
                            if (messagesToEvict != 0) continue;
                            LOG.warn("No messages to evict returned for {} from eviction strategy: {} out of {} candidates", this.destination, this.messageEvictionStrategy, list.size());
                            break;
                        }
                    }
                    this.dispatchMatched();
                }
            }
        }
    }

    private boolean isDuplicate(MessageReference node) {
        boolean duplicate = false;
        if (this.enableAudit && this.audit != null) {
            duplicate = this.audit.isDuplicate(node);
            if (LOG.isDebugEnabled() && duplicate) {
                LOG.debug("{}, ignoring duplicate add: {}", (Object)this, (Object)node.getMessageId());
            }
        }
        return duplicate;
    }

    protected void removeExpiredMessages() throws IOException {
        try {
            this.matched.reset();
            while (this.matched.hasNext()) {
                MessageReference node = this.matched.next();
                node.decrementReferenceCount();
                if (!node.isExpired()) continue;
                this.matched.remove();
                node.decrementReferenceCount();
                if (this.broker.isExpired(node)) {
                    ((Destination)node.getRegionDestination()).getDestinationStatistics().getExpired().increment();
                    this.broker.messageExpired(this.getContext(), node, this);
                }
                break;
            }
        }
        finally {
            this.matched.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processMessageDispatchNotification(MessageDispatchNotification mdn) {
        Object object = this.matchedListMutex;
        synchronized (object) {
            try {
                MessageReference node;
                this.matched.reset();
                do {
                    if (!this.matched.hasNext()) return;
                    node = this.matched.next();
                    node.decrementReferenceCount();
                } while (!node.getMessageId().equals(mdn.getMessageId()));
                Object object2 = this.dispatchLock;
                synchronized (object2) {
                    this.matched.remove();
                    this.getSubscriptionStatistics().getDispatched().increment();
                    if (this.isUseTopicSubscriptionInflightStats()) {
                        this.dispatched.add(new DispatchedNode(node));
                        this.getSubscriptionStatistics().getInflightMessageSize().addSize(node.getSize());
                    }
                    node.decrementReferenceCount();
                }
            }
            finally {
                this.matched.release();
            }
            return;
        }
    }

    @Override
    public synchronized void acknowledge(ConnectionContext context, MessageAck ack) throws Exception {
        super.acknowledge(context, ack);
        if (ack.isStandardAck()) {
            this.updateStatsOnAck(context, ack);
        } else if (ack.isPoisonAck()) {
            if (ack.isInTransaction()) {
                throw new JMSException("Poison ack cannot be transacted: " + ack);
            }
            this.updateStatsOnAck(context, ack);
            this.contractPrefetchExtension(ack.getMessageCount());
        } else if (ack.isIndividualAck()) {
            this.updateStatsOnAck(context, ack);
            if (ack.isInTransaction()) {
                this.expandPrefetchExtension(1);
            }
        } else if (ack.isExpiredAck()) {
            this.updateStatsOnAck(ack);
            this.contractPrefetchExtension(ack.getMessageCount());
        } else if (ack.isDeliveredAck()) {
            this.expandPrefetchExtension(ack.getMessageCount());
        } else {
            if (ack.isRedeliveredAck()) {
                return;
            }
            throw new JMSException("Invalid acknowledgment: " + ack);
        }
        this.dispatchMatched();
    }

    private void updateStatsOnAck(ConnectionContext context, final MessageAck ack) {
        if (context.isInTransaction()) {
            context.getTransaction().addSynchronization(new Synchronization(){

                @Override
                public void afterRollback() {
                    TopicSubscription.this.contractPrefetchExtension(ack.getMessageCount());
                }

                @Override
                public void afterCommit() throws Exception {
                    TopicSubscription.this.contractPrefetchExtension(ack.getMessageCount());
                    TopicSubscription.this.updateStatsOnAck(ack);
                    TopicSubscription.this.dispatchMatched();
                }
            });
        } else {
            this.updateStatsOnAck(ack);
        }
    }

    @Override
    public Response pullMessage(ConnectionContext context, final MessagePull pull) throws Exception {
        if (this.getPrefetchSize() == 0) {
            final long currentDispatchedCount = this.getSubscriptionStatistics().getDispatched().getCount();
            this.prefetchExtension.set(pull.getQuantity());
            this.dispatchMatched();
            if (currentDispatchedCount == this.getSubscriptionStatistics().getDispatched().getCount() || pull.isAlwaysSignalDone()) {
                if (pull.getTimeout() == -1L) {
                    this.dispatch(null);
                    this.prefetchExtension.set(0);
                }
                if (pull.getTimeout() > 0L) {
                    this.scheduler.executeAfterDelay(new Runnable(){

                        @Override
                        public void run() {
                            TopicSubscription.this.pullTimeout(currentDispatchedCount, pull.isAlwaysSignalDone());
                        }
                    }, pull.getTimeout());
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void pullTimeout(long currentDispatchedCount, boolean alwaysSendDone) {
        Object object = this.matchedListMutex;
        synchronized (object) {
            if (currentDispatchedCount == this.getSubscriptionStatistics().getDispatched().getCount() || alwaysSendDone) {
                try {
                    this.dispatch(null);
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
    private void updateStatsOnAck(MessageAck ack) {
        if (this.isUseTopicSubscriptionInflightStats()) {
            Object object = this.dispatchLock;
            synchronized (object) {
                boolean inAckRange = false;
                ArrayList<DispatchedNode> removeList = new ArrayList<DispatchedNode>();
                for (DispatchedNode node : this.dispatched) {
                    MessageId messageId = node.getMessageId();
                    if (ack.getFirstMessageId() == null || ack.getFirstMessageId().equals(messageId)) {
                        inAckRange = true;
                    }
                    if (!inAckRange) continue;
                    removeList.add(node);
                    if (!ack.getLastMessageId().equals(messageId)) continue;
                    break;
                }
                for (DispatchedNode node : removeList) {
                    this.dispatched.remove(node);
                    this.getSubscriptionStatistics().getInflightMessageSize().addSize(-node.getSize());
                    Destination destination = node.getDestination();
                    this.incrementStatsOnAck(destination, ack, 1);
                    if (ack.isInTransaction()) continue;
                    this.contractPrefetchExtension(1);
                }
            }
        } else {
            if (this.singleDestination && this.destination != null) {
                this.incrementStatsOnAck(this.destination, ack, ack.getMessageCount());
            }
            if (!ack.isInTransaction()) {
                this.contractPrefetchExtension(ack.getMessageCount());
            }
        }
    }

    private void incrementStatsOnAck(Destination destination, MessageAck ack, int count) {
        this.getSubscriptionStatistics().getDequeues().add(count);
        destination.getDestinationStatistics().getDequeues().add(count);
        destination.getDestinationStatistics().getInflight().subtract(count);
        if (this.info.isNetworkSubscription()) {
            destination.getDestinationStatistics().getForwards().add(count);
        }
        if (ack.isExpiredAck()) {
            destination.getDestinationStatistics().getExpired().add(count);
        }
    }

    @Override
    public int countBeforeFull() {
        return this.getPrefetchSize() == 0 ? this.prefetchExtension.get() : this.info.getPrefetchSize() + this.prefetchExtension.get() - this.getDispatchedQueueSize();
    }

    @Override
    public int getPendingQueueSize() {
        return this.matched();
    }

    @Override
    public long getPendingMessageSize() {
        return this.matched.messageSize();
    }

    @Override
    public int getDispatchedQueueSize() {
        return (int)(this.getSubscriptionStatistics().getDispatched().getCount() - this.getSubscriptionStatistics().getDequeues().getCount());
    }

    public int getMaximumPendingMessages() {
        return this.maximumPendingMessages;
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
    public long getDequeueCounter() {
        return this.getSubscriptionStatistics().getDequeues().getCount();
    }

    public int discarded() {
        return this.discarded.get();
    }

    public int matched() {
        return this.matched.size();
    }

    public void setMaximumPendingMessages(int maximumPendingMessages) {
        this.maximumPendingMessages = maximumPendingMessages;
    }

    public MessageEvictionStrategy getMessageEvictionStrategy() {
        return this.messageEvictionStrategy;
    }

    public void setMessageEvictionStrategy(MessageEvictionStrategy messageEvictionStrategy) {
        this.messageEvictionStrategy = messageEvictionStrategy;
    }

    public synchronized int getMaxProducersToAudit() {
        return this.maxProducersToAudit;
    }

    public synchronized void setMaxProducersToAudit(int maxProducersToAudit) {
        this.maxProducersToAudit = maxProducersToAudit;
        if (this.audit != null) {
            this.audit.setMaximumNumberOfProducersToTrack(maxProducersToAudit);
        }
    }

    public synchronized int getMaxAuditDepth() {
        return this.maxAuditDepth;
    }

    public synchronized void setMaxAuditDepth(int maxAuditDepth) {
        this.maxAuditDepth = maxAuditDepth;
        if (this.audit != null) {
            this.audit.setAuditDepth(maxAuditDepth);
        }
    }

    public boolean isEnableAudit() {
        return this.enableAudit;
    }

    public synchronized void setEnableAudit(boolean enableAudit) {
        this.enableAudit = enableAudit;
        if (enableAudit && this.audit == null) {
            this.audit = new ActiveMQMessageAudit(this.maxAuditDepth, this.maxProducersToAudit);
        }
    }

    @Override
    public boolean isFull() {
        return this.getPrefetchSize() == 0 ? this.prefetchExtension.get() == 0 : this.getDispatchedQueueSize() - this.prefetchExtension.get() >= this.info.getPrefetchSize();
    }

    @Override
    public int getInFlightSize() {
        return this.getDispatchedQueueSize();
    }

    @Override
    public boolean isLowWaterMark() {
        return (double)(this.getDispatchedQueueSize() - this.prefetchExtension.get()) <= (double)this.info.getPrefetchSize() * 0.4;
    }

    @Override
    public boolean isHighWaterMark() {
        return (double)(this.getDispatchedQueueSize() - this.prefetchExtension.get()) >= (double)this.info.getPrefetchSize() * 0.9;
    }

    public void setMemoryUsageHighWaterMark(int memoryUsageHighWaterMark) {
        this.memoryUsageHighWaterMark = memoryUsageHighWaterMark;
    }

    public int getMemoryUsageHighWaterMark() {
        return this.memoryUsageHighWaterMark;
    }

    public SystemUsage getUsageManager() {
        return this.usageManager;
    }

    public PendingMessageCursor getMatched() {
        return this.matched;
    }

    public void setMatched(PendingMessageCursor matched) {
        this.matched = matched;
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dispatchMatched() throws IOException {
        Object object = this.matchedListMutex;
        synchronized (object) {
            if (!this.matched.isEmpty() && !this.isFull()) {
                try {
                    this.matched.reset();
                    while (this.matched.hasNext() && !this.isFull()) {
                        MessageReference message = this.matched.next();
                        message.decrementReferenceCount();
                        this.matched.remove();
                        if (message.isExpired()) {
                            this.discard(message);
                            continue;
                        }
                        this.dispatch(message);
                    }
                }
                finally {
                    this.matched.release();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dispatch(final MessageReference node) throws IOException {
        Message message;
        Message message2 = message = node != null ? node.getMessage() : null;
        if (node != null) {
            node.incrementReferenceCount();
        }
        MessageDispatch md = new MessageDispatch();
        md.setMessage(message);
        md.setConsumerId(this.info.getConsumerId());
        if (node != null) {
            md.setDestination(((Destination)node.getRegionDestination()).getActiveMQDestination());
            Object object = this.dispatchLock;
            synchronized (object) {
                this.getSubscriptionStatistics().getDispatched().increment();
                if (this.isUseTopicSubscriptionInflightStats()) {
                    this.dispatched.add(new DispatchedNode(node));
                    this.getSubscriptionStatistics().getInflightMessageSize().addSize(node.getSize());
                }
            }
            if (this.singleDestination) {
                if (this.destination == null) {
                    this.destination = (Destination)node.getRegionDestination();
                } else if (this.destination != node.getRegionDestination()) {
                    this.singleDestination = false;
                }
            }
            if (this.getPrefetchSize() == 0) {
                this.decrementPrefetchExtension(1);
            }
        }
        if (this.info.isDispatchAsync()) {
            if (node != null) {
                md.setTransmitCallback(new TransmitCallback(){

                    @Override
                    public void onSuccess() {
                        Destination regionDestination = (Destination)node.getRegionDestination();
                        regionDestination.getDestinationStatistics().getDispatched().increment();
                        regionDestination.getDestinationStatistics().getInflight().increment();
                        node.decrementReferenceCount();
                    }

                    @Override
                    public void onFailure() {
                        Destination regionDestination = (Destination)node.getRegionDestination();
                        regionDestination.getDestinationStatistics().getDispatched().increment();
                        regionDestination.getDestinationStatistics().getInflight().increment();
                        node.decrementReferenceCount();
                    }
                });
            }
            this.context.getConnection().dispatchAsync(md);
        } else {
            this.context.getConnection().dispatchSync(md);
            if (node != null) {
                Destination regionDestination = (Destination)node.getRegionDestination();
                regionDestination.getDestinationStatistics().getDispatched().increment();
                regionDestination.getDestinationStatistics().getInflight().increment();
                node.decrementReferenceCount();
            }
        }
    }

    private void discard(MessageReference message) {
        this.discarding = true;
        try {
            message.decrementReferenceCount();
            this.matched.remove(message);
            this.discarded.incrementAndGet();
            if (this.destination != null) {
                this.destination.getDestinationStatistics().getDequeues().increment();
            }
            LOG.debug("{}, discarding message {}", (Object)this, (Object)message);
            Destination dest = (Destination)message.getRegionDestination();
            if (dest != null) {
                dest.messageDiscarded(this.getContext(), this, message);
            }
            this.broker.getRoot().sendToDeadLetterQueue(this.getContext(), message, this, new Throwable("TopicSubDiscard. ID:" + this.info.getConsumerId()));
        }
        finally {
            this.discarding = false;
        }
    }

    public String toString() {
        return "TopicSubscription: consumer=" + this.info.getConsumerId() + ", destinations=" + this.destinations.size() + ", dispatched=" + this.getDispatchedQueueSize() + ", delivered=" + this.getDequeueCounter() + ", matched=" + this.matched() + ", discarded=" + this.discarded() + ", prefetchExtension=" + this.prefetchExtension.get() + ", usePrefetchExtension=" + this.isUsePrefetchExtension();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void destroy() {
        this.active = false;
        Object object = this.matchedListMutex;
        synchronized (object) {
            try {
                this.matched.destroy();
            }
            catch (Exception e) {
                LOG.warn("Failed to destroy cursor", e);
            }
        }
        this.setSlowConsumer(false);
        object = this.dispatchLock;
        synchronized (object) {
            this.dispatched.clear();
        }
    }

    @Override
    public int getPrefetchSize() {
        return this.info.getPrefetchSize();
    }

    @Override
    public void setPrefetchSize(int newSize) {
        this.info.setPrefetchSize(newSize);
        try {
            this.dispatchMatched();
        }
        catch (Exception e) {
            LOG.trace("Caught exception on dispatch after prefetch size change.");
        }
    }

    public boolean isUseTopicSubscriptionInflightStats() {
        return this.useTopicSubscriptionInflightStats;
    }

    public void setUseTopicSubscriptionInflightStats(boolean useTopicSubscriptionInflightStats) {
        this.useTopicSubscriptionInflightStats = useTopicSubscriptionInflightStats;
    }

    private static class DispatchedNode {
        private final int size;
        private final MessageId messageId;
        private final Destination destination;

        public DispatchedNode(MessageReference node) {
            this.size = node.getSize();
            this.messageId = node.getMessageId();
            this.destination = node.getRegionDestination() instanceof Destination ? (Destination)node.getRegionDestination() : null;
        }

        public long getSize() {
            return this.size;
        }

        public MessageId getMessageId() {
            return this.messageId;
        }

        public Destination getDestination() {
            return this.destination;
        }
    }
}

