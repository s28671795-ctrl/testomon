/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.ResourceAllocationException;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerStoppedException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.IndirectMessageReference;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.MessageReferenceFilter;
import org.apache.activemq.broker.region.PendingMarshalUsageTracker;
import org.apache.activemq.broker.region.QueueBrowserSubscription;
import org.apache.activemq.broker.region.QueueDispatchSelector;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.AbstractStoreCursor;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PrioritizedPendingList;
import org.apache.activemq.broker.region.cursors.QueueDispatchPendingList;
import org.apache.activemq.broker.region.cursors.StoreQueueCursor;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.broker.region.group.CachedMessageGroupMapFactory;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.broker.region.policy.RoundRobinDispatchPolicy;
import org.apache.activemq.broker.util.InsertionCountList;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.selector.SelectorParser;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.store.IndexListener;
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.usage.UsageListener;
import org.apache.activemq.util.BrokerSupport;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Queue
extends BaseDestination
implements Task,
UsageListener,
IndexListener {
    protected static final Logger LOG = LoggerFactory.getLogger(Queue.class);
    protected final TaskRunnerFactory taskFactory;
    protected TaskRunner taskRunner;
    private final ReentrantReadWriteLock consumersLock = new ReentrantReadWriteLock();
    protected final List<Subscription> consumers = new ArrayList<Subscription>(50);
    private final ReentrantReadWriteLock messagesLock = new ReentrantReadWriteLock();
    protected PendingMessageCursor messages;
    private final ReentrantReadWriteLock pagedInMessagesLock = new ReentrantReadWriteLock();
    private final PendingList pagedInMessages = new OrderedPendingList();
    private final ReentrantReadWriteLock pagedInPendingDispatchLock = new ReentrantReadWriteLock();
    protected QueueDispatchPendingList dispatchPendingList = new QueueDispatchPendingList();
    private AtomicInteger pendingSends = new AtomicInteger(0);
    private MessageGroupMap messageGroupOwners;
    private DispatchPolicy dispatchPolicy = new RoundRobinDispatchPolicy();
    private MessageGroupMapFactory messageGroupMapFactory = new CachedMessageGroupMapFactory();
    final Lock sendLock = new ReentrantLock();
    private ExecutorService executor;
    private final Map<MessageId, Runnable> messagesWaitingForSpace = new LinkedHashMap<MessageId, Runnable>();
    private boolean useConsumerPriority = true;
    private boolean strictOrderDispatch = false;
    private final QueueDispatchSelector dispatchSelector;
    private boolean optimizedDispatch = false;
    private boolean iterationRunning = false;
    private boolean firstConsumer = false;
    private int timeBeforeDispatchStarts = 0;
    private int consumersBeforeDispatchStarts = 0;
    private CountDownLatch consumersBeforeStartsLatch;
    private final AtomicLong pendingWakeups = new AtomicLong();
    private boolean allConsumersExclusiveByDefault = false;
    private volatile boolean resetNeeded;
    private final Runnable sendMessagesWaitingForSpaceTask = new Runnable(){

        @Override
        public void run() {
            Queue.this.asyncWakeup();
        }
    };
    private final Runnable expireMessagesTask = new Runnable(){

        @Override
        public void run() {
            Queue.this.expireMessages();
        }
    };
    private final Object iteratingMutex = new Object();
    DelayQueue<TimeoutMessage> flowControlTimeoutMessages = new DelayQueue();
    private final FlowControlTimeoutTask flowControlTimeoutTask = new FlowControlTimeoutTask();
    private final Comparator<Subscription> orderedCompare = new Comparator<Subscription>(){

        @Override
        public int compare(Subscription s1, Subscription s2) {
            int val = s2.getConsumerInfo().getPriority() - s1.getConsumerInfo().getPriority();
            if (val == 0 && Queue.this.messageGroupOwners != null) {
                long y;
                long x = s1.getConsumerInfo().getAssignedGroupCount(Queue.this.destination);
                val = x < (y = s2.getConsumerInfo().getAssignedGroupCount(Queue.this.destination)) ? -1 : (x == y ? 0 : 1);
            }
            return val;
        }
    };
    ConcurrentLinkedQueue<QueueBrowserSubscription> browserSubscriptions = new ConcurrentLinkedQueue();
    private final LinkedList<IndexListener.MessageContext> indexOrderedCursorUpdates = new LinkedList();

    public boolean singlePendingSend() {
        return this.pendingSends.get() <= 1;
    }

    public Queue(BrokerService brokerService, ActiveMQDestination destination, MessageStore store, DestinationStatistics parentStats, TaskRunnerFactory taskFactory) throws Exception {
        super(brokerService, store, destination, parentStats);
        this.taskFactory = taskFactory;
        this.dispatchSelector = new QueueDispatchSelector(destination);
        if (store != null) {
            store.registerIndexListener(this);
        }
    }

    @Override
    public List<Subscription> getConsumers() {
        this.consumersLock.readLock().lock();
        try {
            ArrayList<Subscription> arrayList = new ArrayList<Subscription>(this.consumers);
            return arrayList;
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
    }

    @Override
    public void setPrioritizedMessages(boolean prioritizedMessages) {
        super.setPrioritizedMessages(prioritizedMessages);
        this.dispatchPendingList.setPrioritizedMessages(prioritizedMessages);
    }

    @Override
    public void initialize() throws Exception {
        if (this.messages == null) {
            this.messages = this.destination.isTemporary() || this.broker == null || this.store == null ? new VMPendingMessageCursor(this.isPrioritizedMessages()) : new StoreQueueCursor(this.broker, this);
        }
        if (this.messages instanceof VMPendingMessageCursor) {
            this.systemUsage = this.brokerService.getSystemUsage();
            this.memoryUsage.setParent(this.systemUsage.getMemoryUsage());
        }
        this.taskRunner = this.taskFactory.createTaskRunner(this, "Queue:" + this.destination.getPhysicalName());
        super.initialize();
        if (this.store != null) {
            this.messages.setSystemUsage(this.systemUsage);
            this.messages.setEnableAudit(this.isEnableAudit());
            this.messages.setMaxAuditDepth(this.getMaxAuditDepth());
            this.messages.setMaxProducersToAudit(this.getMaxProducersToAudit());
            this.messages.setUseCache(this.isUseCache());
            this.messages.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
            this.store.start();
            int messageCount = this.store.getMessageCount();
            if (messageCount > 0 && this.messages.isRecoveryRequired()) {
                BatchMessageRecoveryListener listener = new BatchMessageRecoveryListener(messageCount);
                do {
                    listener.reset();
                    this.store.recoverNextMessages(this.getMaxPageSize(), listener);
                    listener.processExpired();
                } while (!listener.done());
            } else {
                this.destinationStatistics.getMessages().add(messageCount);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        LOG.debug("{} add sub: {}, dequeues: {}, dispatched: {}, inflight: {}", this.getActiveMQDestination().getQualifiedName(), sub, this.getDestinationStatistics().getDequeues().getCount(), this.getDestinationStatistics().getDispatched().getCount(), this.getDestinationStatistics().getInflight().getCount());
        super.addSubscription(context, sub);
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            sub.add(context, this);
            this.consumersLock.writeLock().lock();
            try {
                if (this.consumers.size() == 0) {
                    this.firstConsumer = true;
                    if (this.consumersBeforeDispatchStarts != 0) {
                        this.consumersBeforeStartsLatch = new CountDownLatch(this.consumersBeforeDispatchStarts - 1);
                    }
                } else if (this.consumersBeforeStartsLatch != null) {
                    this.consumersBeforeStartsLatch.countDown();
                }
                this.addToConsumerList(sub);
                if (sub.getConsumerInfo().isExclusive() || this.isAllConsumersExclusiveByDefault()) {
                    Subscription exclusiveConsumer = this.dispatchSelector.getExclusiveConsumer();
                    if (exclusiveConsumer == null) {
                        exclusiveConsumer = sub;
                    } else if (sub.getConsumerInfo().getPriority() == 127 || sub.getConsumerInfo().getPriority() > exclusiveConsumer.getConsumerInfo().getPriority()) {
                        exclusiveConsumer = sub;
                    }
                    this.dispatchSelector.setExclusiveConsumer(exclusiveConsumer);
                }
            }
            finally {
                this.consumersLock.writeLock().unlock();
            }
            if (sub instanceof QueueBrowserSubscription) {
                QueueBrowserSubscription browserSubscription = (QueueBrowserSubscription)sub;
                browserSubscription.incrementQueueRef();
                this.browserSubscriptions.add(browserSubscription);
            }
            if (!this.optimizedDispatch) {
                this.wakeup();
            }
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
        if (this.optimizedDispatch) {
            this.wakeup();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeSubscription(ConnectionContext context, Subscription sub, long lastDeliveredSequenceId) throws Exception {
        super.removeSubscription(context, sub, lastDeliveredSequenceId);
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            LOG.debug("{} remove sub: {}, lastDeliveredSeqId: {}, dequeues: {}, dispatched: {}, inflight: {}, groups: {}", this.getActiveMQDestination().getQualifiedName(), sub, lastDeliveredSequenceId, this.getDestinationStatistics().getDequeues().getCount(), this.getDestinationStatistics().getDispatched().getCount(), this.getDestinationStatistics().getInflight().getCount(), sub.getConsumerInfo().getAssignedGroupCount(this.destination));
            this.consumersLock.writeLock().lock();
            try {
                Subscription exclusiveConsumer;
                this.removeFromConsumerList(sub);
                if (sub.getConsumerInfo().isExclusive()) {
                    exclusiveConsumer = this.dispatchSelector.getExclusiveConsumer();
                    if (exclusiveConsumer == sub) {
                        exclusiveConsumer = null;
                        for (Subscription s2 : this.consumers) {
                            if (!s2.getConsumerInfo().isExclusive() || exclusiveConsumer != null && s2.getConsumerInfo().getPriority() <= exclusiveConsumer.getConsumerInfo().getPriority()) continue;
                            exclusiveConsumer = s2;
                        }
                        this.dispatchSelector.setExclusiveConsumer(exclusiveConsumer);
                    }
                } else if (this.isAllConsumersExclusiveByDefault()) {
                    exclusiveConsumer = null;
                    for (Subscription s2 : this.consumers) {
                        if (exclusiveConsumer != null && s2.getConsumerInfo().getPriority() <= exclusiveConsumer.getConsumerInfo().getPriority()) continue;
                        exclusiveConsumer = s2;
                    }
                    this.dispatchSelector.setExclusiveConsumer(exclusiveConsumer);
                }
                ConsumerId consumerId = sub.getConsumerInfo().getConsumerId();
                this.getMessageGroupOwners().removeConsumer(consumerId);
                boolean markAsRedelivered = false;
                MessageReference lastDeliveredRef = null;
                List<MessageReference> unAckedMessages = sub.remove(context, this);
                if (lastDeliveredSequenceId > -1L) {
                    for (MessageReference ref : unAckedMessages) {
                        if (ref.getMessageId().getBrokerSequenceId() != lastDeliveredSequenceId) continue;
                        lastDeliveredRef = ref;
                        markAsRedelivered = true;
                        LOG.debug("found lastDeliveredSeqID: {}, message reference: {}", (Object)lastDeliveredSequenceId, (Object)ref.getMessageId());
                        break;
                    }
                }
                Iterator<MessageReference> unackedListIterator = unAckedMessages.iterator();
                while (unackedListIterator.hasNext()) {
                    MessageReference ref;
                    ref = unackedListIterator.next();
                    if (this.brokerService.isStopping()) break;
                    QueueMessageReference qmr = (QueueMessageReference)ref;
                    if (qmr.getLockOwner() == sub) {
                        qmr.unlock();
                        if (lastDeliveredSequenceId == -2L) {
                            qmr.incrementRedeliveryCounter();
                        } else {
                            if (markAsRedelivered) {
                                qmr.incrementRedeliveryCounter();
                            }
                            if (ref == lastDeliveredRef) {
                                markAsRedelivered = false;
                            }
                        }
                    }
                    if (!qmr.isDropped()) continue;
                    unackedListIterator.remove();
                }
                this.dispatchPendingList.addForRedelivery(unAckedMessages, this.strictOrderDispatch && this.consumers.isEmpty());
                if (sub instanceof QueueBrowserSubscription) {
                    ((QueueBrowserSubscription)sub).decrementQueueRef();
                    this.browserSubscriptions.remove(sub);
                }
                if (this.dispatchPendingList.hasRedeliveries() && !this.brokerService.isStopping()) {
                    this.doDispatch(new OrderedPendingList());
                }
            }
            finally {
                this.consumersLock.writeLock().unlock();
            }
            if (!this.optimizedDispatch) {
                this.wakeup();
            }
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
        if (this.optimizedDispatch) {
            this.wakeup();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void send(ProducerBrokerExchange producerExchange, final Message message) throws Exception {
        boolean sendProducerAck;
        final ConnectionContext context = producerExchange.getConnectionContext();
        message.setRegionDestination(this);
        ProducerState state = producerExchange.getProducerState();
        if (state == null) {
            LOG.warn("Send failed for: {}, missing producer state for: {}", (Object)message, (Object)producerExchange);
            throw new JMSException("Cannot send message to " + this.getActiveMQDestination() + " with invalid (null) producer state");
        }
        final ProducerInfo producerInfo = producerExchange.getProducerState().getInfo();
        boolean bl = sendProducerAck = !message.isResponseRequired() && producerInfo.getWindowSize() > 0 && !context.isInRecoveryMode();
        if (message.isExpired()) {
            this.broker.getRoot().messageExpired(context, message, null);
            if (sendProducerAck) {
                ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
                context.getConnection().dispatchAsync(ack);
            }
            return;
        }
        if (this.memoryUsage.isFull()) {
            this.isFull(context, this.memoryUsage);
            this.fastProducer(context, producerInfo);
            if (this.isProducerFlowControl() && context.isProducerFlowControl()) {
                if (this.isFlowControlLogRequired()) {
                    LOG.info("Usage Manager Memory Limit ({}) reached on {}, size {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.", this.memoryUsage.getLimit(), this.getActiveMQDestination().getQualifiedName(), this.destinationStatistics.getMessages().getCount());
                }
                if (!context.isNetworkConnection() && this.systemUsage.isSendFailIfNoSpace()) {
                    throw new ResourceAllocationException("Usage Manager Memory Limit reached. Stopping producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info");
                }
                if (producerInfo.getWindowSize() > 0 || message.isResponseRequired()) {
                    final ProducerBrokerExchange producerExchangeCopy = producerExchange.copy();
                    Map<MessageId, Runnable> map = this.messagesWaitingForSpace;
                    synchronized (map) {
                        if (!this.flowControlTimeoutTask.isAlive()) {
                            this.flowControlTimeoutTask.setName(this.getName() + " Producer Flow Control Timeout Task");
                            this.flowControlTimeoutTask.start();
                        }
                        this.messagesWaitingForSpace.put(message.getMessageId(), new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    if (message.isInTransaction() && (context.getTransaction() == null || context.getTransaction().getState() > 1)) {
                                        throw new JMSException("Send transaction completed while waiting for space");
                                    }
                                    if (message.isExpired()) {
                                        LOG.error("message expired waiting for space");
                                        Queue.this.broker.messageExpired(context, message, null);
                                        Queue.this.destinationStatistics.getExpired().increment();
                                    } else {
                                        Queue.this.doMessageSend(producerExchangeCopy, message);
                                    }
                                    if (sendProducerAck) {
                                        ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
                                        context.getConnection().dispatchAsync(ack);
                                    } else {
                                        Response response = new Response();
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    }
                                }
                                catch (Exception e) {
                                    if (!(sendProducerAck || context.isInRecoveryMode() || Queue.this.brokerService.isStopping())) {
                                        ExceptionResponse response = new ExceptionResponse(e);
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    } else {
                                        LOG.debug("unexpected exception on deferred send of: {}", (Object)message, (Object)e);
                                    }
                                }
                                finally {
                                    Queue.this.getDestinationStatistics().getBlockedSends().decrement();
                                    producerExchangeCopy.blockingOnFlowControl(false);
                                }
                            }
                        });
                        this.getDestinationStatistics().getBlockedSends().increment();
                        producerExchange.blockingOnFlowControl(true);
                        if (!context.isNetworkConnection() && this.systemUsage.getSendFailIfNoSpaceAfterTimeout() != 0L) {
                            this.flowControlTimeoutMessages.add(new TimeoutMessage(message, context, this.systemUsage.getSendFailIfNoSpaceAfterTimeout()));
                        }
                        this.registerCallbackForNotFullNotification();
                        context.setDontSendReponse(true);
                        return;
                    }
                }
                if (this.memoryUsage.isFull()) {
                    this.waitForSpace(context, producerExchange, this.memoryUsage, "Usage Manager Memory Limit reached. Producer (" + message.getProducerId() + ") stopped to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info");
                }
                if (message.isExpired()) {
                    LOG.debug("Expired message: {}", (Object)message);
                    this.broker.getRoot().messageExpired(context, message, null);
                    return;
                }
            }
        }
        this.doMessageSend(producerExchange, message);
        if (sendProducerAck) {
            ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
            context.getConnection().dispatchAsync(ack);
        }
    }

    private void registerCallbackForNotFullNotification() {
        if (!this.memoryUsage.notifyCallbackWhenNotFull(this.sendMessagesWaitingForSpaceTask)) {
            this.sendMessagesWaitingForSpaceTask.run();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onAdd(IndexListener.MessageContext messageContext) {
        LinkedList<IndexListener.MessageContext> linkedList = this.indexOrderedCursorUpdates;
        synchronized (linkedList) {
            this.indexOrderedCursorUpdates.addLast(messageContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doPendingCursorAdditions() throws Exception {
        LinkedList<IndexListener.MessageContext> orderedUpdates = new LinkedList<IndexListener.MessageContext>();
        this.sendLock.lockInterruptibly();
        try {
            Iterator iterator2 = this.indexOrderedCursorUpdates;
            synchronized (iterator2) {
                IndexListener.MessageContext candidate = this.indexOrderedCursorUpdates.peek();
                while (candidate != null && candidate.message.getMessageId().getFutureOrSequenceLong() != null) {
                    candidate = this.indexOrderedCursorUpdates.removeFirst();
                    if (candidate.message.getMessageId().getFutureOrSequenceLong() instanceof Long && ((Long)candidate.message.getMessageId().getFutureOrSequenceLong()).compareTo(-1L) == 0) {
                        LOG.warn("{} messageStore indicated duplicate add attempt for {}, suppressing duplicate dispatch", (Object)this, (Object)candidate.message.getMessageId());
                    } else {
                        orderedUpdates.add(candidate);
                    }
                    candidate = this.indexOrderedCursorUpdates.peek();
                }
            }
            this.messagesLock.writeLock().lock();
            try {
                for (IndexListener.MessageContext messageContext : orderedUpdates) {
                    if (!this.messages.addMessageLast(messageContext.message)) {
                        messageContext.duplicate = true;
                    }
                    if (messageContext.onCompletion == null) continue;
                    messageContext.onCompletion.run();
                }
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
        }
        finally {
            this.sendLock.unlock();
        }
        for (IndexListener.MessageContext messageContext : orderedUpdates) {
            if (messageContext.duplicate) continue;
            this.messageSent(messageContext.context, messageContext.message);
        }
        orderedUpdates.clear();
    }

    void doMessageSend(ProducerBrokerExchange producerExchange, Message message) throws IOException, Exception {
        ConnectionContext context = producerExchange.getConnectionContext();
        ListenableFuture<Object> result = null;
        producerExchange.incrementSend();
        this.pendingSends.incrementAndGet();
        do {
            this.checkUsage(context, producerExchange, message);
            message.getMessageId().setBrokerSequenceId(this.getDestinationSequenceId());
            if (this.store != null && message.isPersistent()) {
                message.getMessageId().setFutureOrSequenceLong(null);
                try {
                    if (this.messages.isCacheEnabled() && !this.isPersistJMSRedelivered()) {
                        result = this.store.asyncAddQueueMessage(context, message, this.isOptimizeStorage());
                        result.addListener(new PendingMarshalUsageTracker(message));
                    } else {
                        this.store.addMessage(context, message);
                    }
                }
                catch (Exception e) {
                    this.resetNeeded = true;
                    this.pendingSends.decrementAndGet();
                    throw e;
                }
            }
            if (!this.isReduceMemoryFootprint() || !message.isMarshalled()) continue;
            message.clearUnMarshalledState();
        } while (!this.tryOrderedCursorAdd(message, context) && this.started.get());
        if (result != null && message.isResponseRequired() && !result.isCancelled()) {
            try {
                result.get();
            }
            catch (CancellationException cancellationException) {
                // empty catch block
            }
        }
    }

    private boolean tryOrderedCursorAdd(Message message, ConnectionContext context) throws Exception {
        boolean result = true;
        if (context.isInTransaction()) {
            context.getTransaction().addSynchronization(new CursorAddSync(new IndexListener.MessageContext(context, message, null)));
        } else if (this.store != null && message.isPersistent()) {
            this.doPendingCursorAdditions();
        } else {
            result = this.tryCursorAdd(message);
            this.messageSent(context, message);
        }
        return result;
    }

    private void checkUsage(ConnectionContext context, ProducerBrokerExchange producerBrokerExchange, Message message) throws ResourceAllocationException, IOException, InterruptedException {
        if (message.isPersistent()) {
            if (this.store != null && this.systemUsage.getStoreUsage().isFull(this.getStoreUsageHighWaterMark())) {
                String logMessage = "Persistent store is Full, " + this.getStoreUsageHighWaterMark() + "% of " + this.systemUsage.getStoreUsage().getLimit() + ". Stopping producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info";
                this.waitForSpace(context, producerBrokerExchange, this.systemUsage.getStoreUsage(), this.getStoreUsageHighWaterMark(), logMessage);
            }
        } else if (this.messages.getSystemUsage() != null && this.systemUsage.getTempUsage().isFull()) {
            String logMessage = "Temp Store is Full (" + this.systemUsage.getTempUsage().getPercentUsage() + "% of " + this.systemUsage.getTempUsage().getLimit() + "). Stopping producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info";
            this.waitForSpace(context, producerBrokerExchange, this.messages.getSystemUsage().getTempUsage(), logMessage);
        }
    }

    private void expireMessages() {
        LOG.debug("{} expiring messages ..", (Object)this.getActiveMQDestination().getQualifiedName());
        InsertionCountList<Message> browsedMessages = new InsertionCountList<Message>();
        this.doBrowse(browsedMessages, this.getMaxExpirePageSize());
        this.asyncWakeup();
        LOG.debug("{} expiring messages done.", (Object)this.getActiveMQDestination().getQualifiedName());
    }

    @Override
    public void gc() {
    }

    @Override
    public void acknowledge(ConnectionContext context, Subscription sub, MessageAck ack, MessageReference node) throws IOException {
        this.messageConsumed(context, node);
        if (this.store != null && node.isPersistent()) {
            this.store.removeAsyncMessage(context, this.convertToNonRangedAck(ack, node));
        }
    }

    Message loadMessage(MessageId messageId) throws IOException {
        Message msg = null;
        if (this.store != null && (msg = this.store.getMessage(messageId)) != null) {
            msg.setRegionDestination(this);
        }
        return msg;
    }

    public long getPendingMessageSize() {
        this.messagesLock.readLock().lock();
        try {
            long l = this.messages.messageSize();
            return l;
        }
        finally {
            this.messagesLock.readLock().unlock();
        }
    }

    public long getPendingMessageCount() {
        return this.destinationStatistics.getMessages().getCount();
    }

    public String toString() {
        return this.destination.getQualifiedName() + ", subscriptions=" + this.consumers.size() + ", memory=" + this.memoryUsage.getPercentUsage() + "%, size=" + this.destinationStatistics.getMessages().getCount() + ", pending=" + this.indexOrderedCursorUpdates.size();
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            if (this.memoryUsage != null) {
                this.memoryUsage.start();
            }
            if (this.systemUsage.getStoreUsage() != null) {
                this.systemUsage.getStoreUsage().start();
            }
            if (this.systemUsage.getTempUsage() != null) {
                this.systemUsage.getTempUsage().start();
            }
            this.systemUsage.getMemoryUsage().addUsageListener(this);
            this.messages.start();
            if (this.getExpireMessagesPeriod() > 0L) {
                this.scheduler.executePeriodically(this.expireMessagesTask, this.getExpireMessagesPeriod());
            }
            this.doPageIn(false);
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            if (this.taskRunner != null) {
                this.taskRunner.shutdown();
            }
            if (this.executor != null) {
                ThreadPoolUtils.shutdownNow(this.executor);
                this.executor = null;
            }
            this.scheduler.cancel(this.expireMessagesTask);
            if (this.flowControlTimeoutTask.isAlive()) {
                this.flowControlTimeoutTask.interrupt();
            }
            if (this.messages != null) {
                this.messages.stop();
            }
            for (MessageReference messageReference : this.pagedInMessages.values()) {
                messageReference.decrementReferenceCount();
            }
            this.pagedInMessages.clear();
            this.systemUsage.getMemoryUsage().removeUsageListener(this);
            if (this.memoryUsage != null) {
                this.memoryUsage.stop();
            }
            if (this.systemUsage.getStoreUsage() != null) {
                this.systemUsage.getStoreUsage().stop();
            }
            if (this.store != null) {
                this.store.stop();
            }
        }
    }

    @Override
    public ActiveMQDestination getActiveMQDestination() {
        return this.destination;
    }

    public MessageGroupMap getMessageGroupOwners() {
        if (this.messageGroupOwners == null) {
            this.messageGroupOwners = this.getMessageGroupMapFactory().createMessageGroupMap();
            this.messageGroupOwners.setDestination(this);
        }
        return this.messageGroupOwners;
    }

    public DispatchPolicy getDispatchPolicy() {
        return this.dispatchPolicy;
    }

    public void setDispatchPolicy(DispatchPolicy dispatchPolicy) {
        this.dispatchPolicy = dispatchPolicy;
    }

    public MessageGroupMapFactory getMessageGroupMapFactory() {
        return this.messageGroupMapFactory;
    }

    public void setMessageGroupMapFactory(MessageGroupMapFactory messageGroupMapFactory) {
        this.messageGroupMapFactory = messageGroupMapFactory;
    }

    public PendingMessageCursor getMessages() {
        return this.messages;
    }

    public void setMessages(PendingMessageCursor messages) {
        this.messages = messages;
    }

    public boolean isUseConsumerPriority() {
        return this.useConsumerPriority;
    }

    public void setUseConsumerPriority(boolean useConsumerPriority) {
        this.useConsumerPriority = useConsumerPriority;
    }

    public boolean isStrictOrderDispatch() {
        return this.strictOrderDispatch;
    }

    public void setStrictOrderDispatch(boolean strictOrderDispatch) {
        this.strictOrderDispatch = strictOrderDispatch;
    }

    public boolean isOptimizedDispatch() {
        return this.optimizedDispatch;
    }

    public void setOptimizedDispatch(boolean optimizedDispatch) {
        this.optimizedDispatch = optimizedDispatch;
    }

    public int getTimeBeforeDispatchStarts() {
        return this.timeBeforeDispatchStarts;
    }

    public void setTimeBeforeDispatchStarts(int timeBeforeDispatchStarts) {
        this.timeBeforeDispatchStarts = timeBeforeDispatchStarts;
    }

    public int getConsumersBeforeDispatchStarts() {
        return this.consumersBeforeDispatchStarts;
    }

    public void setConsumersBeforeDispatchStarts(int consumersBeforeDispatchStarts) {
        this.consumersBeforeDispatchStarts = consumersBeforeDispatchStarts;
    }

    public void setAllConsumersExclusiveByDefault(boolean allConsumersExclusiveByDefault) {
        this.allConsumersExclusiveByDefault = allConsumersExclusiveByDefault;
    }

    public boolean isAllConsumersExclusiveByDefault() {
        return this.allConsumersExclusiveByDefault;
    }

    public boolean isResetNeeded() {
        return this.resetNeeded;
    }

    private QueueMessageReference createMessageReference(Message message) {
        IndirectMessageReference result = new IndirectMessageReference(message);
        return result;
    }

    @Override
    public Message[] browse() {
        ArrayList<Message> browseList = new ArrayList<Message>();
        this.doBrowse(browseList, this.getMaxBrowsePageSize());
        return browseList.toArray(new Message[browseList.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doBrowse(List<Message> browseList, int max) {
        ConnectionContext connectionContext = this.createConnectionContext();
        try {
            int maxPageInAttempts = 1;
            if (max > 0) {
                this.messagesLock.readLock().lock();
                try {
                    maxPageInAttempts += this.messages.size() / max;
                }
                finally {
                    this.messagesLock.readLock().unlock();
                }
                while (this.shouldPageInMoreForBrowse(max) && maxPageInAttempts-- > 0) {
                    this.pageInMessages(!this.memoryUsage.isFull(110), max);
                }
            }
            this.doBrowseList(browseList, max, this.dispatchPendingList, this.pagedInPendingDispatchLock, connectionContext, "redeliveredWaitingDispatch+pagedInPendingDispatch");
            this.doBrowseList(browseList, max, this.pagedInMessages, this.pagedInMessagesLock, connectionContext, "pagedInMessages");
        }
        catch (BrokerStoppedException maxPageInAttempts) {
        }
        catch (Exception e) {
            LOG.error("Problem retrieving message for browse", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doBrowseList(List<Message> browseList, int max, PendingList list, ReentrantReadWriteLock lock, ConnectionContext connectionContext, String name) throws Exception {
        ArrayList<MessageReference> toExpire = new ArrayList<MessageReference>();
        lock.readLock().lock();
        try {
            this.addAll(list.values(), browseList, max, toExpire);
        }
        finally {
            lock.readLock().unlock();
        }
        for (MessageReference ref : toExpire) {
            if (this.broker.isExpired(ref)) {
                LOG.debug("expiring from {}: {}", (Object)name, (Object)ref);
                this.messageExpired(connectionContext, ref);
                continue;
            }
            lock.writeLock().lock();
            try {
                list.remove(ref);
            }
            finally {
                lock.writeLock().unlock();
            }
            ref.decrementReferenceCount();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean shouldPageInMoreForBrowse(int max) {
        int alreadyPagedIn = 0;
        this.pagedInMessagesLock.readLock().lock();
        try {
            alreadyPagedIn = this.pagedInMessages.size();
        }
        finally {
            this.pagedInMessagesLock.readLock().unlock();
        }
        int messagesInQueue = alreadyPagedIn;
        this.messagesLock.readLock().lock();
        try {
        }
        finally {
            this.messagesLock.readLock().unlock();
        }
        LOG.trace("max {}, alreadyPagedIn {}, messagesCount {}, memoryUsage {}%", max, alreadyPagedIn, messagesInQueue += this.messages.size(), this.memoryUsage.getPercentUsage());
        return alreadyPagedIn == 0 || alreadyPagedIn < max && alreadyPagedIn < messagesInQueue && this.messages.hasSpace();
    }

    private void addAll(Collection<? extends MessageReference> refs, List<Message> l, int max, List<MessageReference> toExpire) throws Exception {
        Iterator<? extends MessageReference> i = refs.iterator();
        while (i.hasNext() && l.size() < max) {
            QueueMessageReference ref = (QueueMessageReference)i.next();
            if (ref.isExpired() && ref.getLockOwner() == null) {
                toExpire.add(ref);
                continue;
            }
            if (l.contains(ref.getMessage())) continue;
            l.add(ref.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public QueueMessageReference getMessage(String id) {
        MessageId msgId = new MessageId(id);
        this.pagedInMessagesLock.readLock().lock();
        try {
            QueueMessageReference ref = (QueueMessageReference)this.pagedInMessages.get(msgId);
            if (ref != null) {
                QueueMessageReference queueMessageReference = ref;
                return queueMessageReference;
            }
        }
        finally {
            this.pagedInMessagesLock.readLock().unlock();
        }
        this.messagesLock.writeLock().lock();
        try {
            try {
                this.messages.reset();
                while (true) {
                    if (this.messages.hasNext()) {
                        MessageReference mr = this.messages.next();
                        QueueMessageReference qmr = this.createMessageReference(mr.getMessage());
                        qmr.decrementReferenceCount();
                        this.messages.rollback(qmr.getMessageId());
                        if (!msgId.equals(qmr.getMessageId())) continue;
                        QueueMessageReference queueMessageReference = qmr;
                        return queueMessageReference;
                        continue;
                    }
                    break;
                }
            }
            finally {
                this.messages.release();
            }
        }
        finally {
            this.messagesLock.writeLock().unlock();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void purge() throws Exception {
        ConnectionContext c = this.createConnectionContext();
        ArrayList<MessageReference> list = null;
        try {
            this.sendLock.lock();
            long originalMessageCount = this.destinationStatistics.getMessages().getCount();
            do {
                this.doPageIn(true, false, this.getMaxPageSize());
                this.pagedInMessagesLock.readLock().lock();
                try {
                    list = new ArrayList<MessageReference>(this.pagedInMessages.values());
                }
                finally {
                    this.pagedInMessagesLock.readLock().unlock();
                }
                for (MessageReference ref : list) {
                    try {
                        QueueMessageReference r = (QueueMessageReference)ref;
                        this.removeMessage(c, r);
                    }
                    catch (IOException iOException) {}
                }
            } while (!list.isEmpty() && this.destinationStatistics.getMessages().getCount() > 0L);
            if (this.getMessages().getMessageAudit() != null) {
                this.getMessages().getMessageAudit().clear();
            }
            if (this.destinationStatistics.getMessages().getCount() > 0L) {
                LOG.warn("{} after purge of {} messages, message count stats report: {}", this.getActiveMQDestination().getQualifiedName(), originalMessageCount, this.destinationStatistics.getMessages().getCount());
            }
        }
        finally {
            this.sendLock.unlock();
        }
    }

    @Override
    public void clearPendingMessages() {
        this.messagesLock.writeLock().lock();
        try {
            if (this.resetNeeded) {
                this.messages.gc();
                this.messages.reset();
                this.resetNeeded = false;
            } else {
                this.messages.rebase();
            }
            this.asyncWakeup();
        }
        finally {
            this.messagesLock.writeLock().unlock();
        }
    }

    public boolean removeMessage(String messageId) throws Exception {
        return this.removeMatchingMessages(this.createMessageIdFilter(messageId), 1) > 0;
    }

    public int removeMatchingMessages(String selector) throws Exception {
        return this.removeMatchingMessages(selector, -1);
    }

    public int removeMatchingMessages(String selector, int maximumMessages) throws Exception {
        return this.removeMatchingMessages(this.createSelectorFilter(selector), maximumMessages);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int removeMatchingMessages(MessageReferenceFilter filter2, int maximumMessages) throws Exception {
        int movedCounter = 0;
        LinkedHashSet<MessageReference> set = new LinkedHashSet<MessageReference>();
        ConnectionContext context = this.createConnectionContext();
        do {
            this.doPageIn(true);
            this.pagedInMessagesLock.readLock().lock();
            try {
                set.addAll(this.pagedInMessages.values());
            }
            finally {
                this.pagedInMessagesLock.readLock().unlock();
            }
            ArrayList list = new ArrayList(set);
            for (MessageReference ref : list) {
                IndirectMessageReference r = (IndirectMessageReference)ref;
                if (!filter2.evaluate(context, r)) continue;
                this.removeMessage(context, r);
                set.remove(r);
                if (++movedCounter < maximumMessages || maximumMessages <= 0) continue;
                return movedCounter;
            }
        } while ((long)set.size() < this.destinationStatistics.getMessages().getCount());
        return movedCounter;
    }

    public boolean copyMessageTo(ConnectionContext context, String messageId, ActiveMQDestination dest) throws Exception {
        return this.copyMatchingMessages(context, this.createMessageIdFilter(messageId), dest, 1) > 0;
    }

    public int copyMatchingMessagesTo(ConnectionContext context, String selector, ActiveMQDestination dest) throws Exception {
        return this.copyMatchingMessagesTo(context, selector, dest, -1);
    }

    public int copyMatchingMessagesTo(ConnectionContext context, String selector, ActiveMQDestination dest, int maximumMessages) throws Exception {
        return this.copyMatchingMessages(context, this.createSelectorFilter(selector), dest, maximumMessages);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int copyMatchingMessages(ConnectionContext context, MessageReferenceFilter filter2, ActiveMQDestination dest, int maximumMessages) throws Exception {
        if (this.destination.equals(dest)) {
            return 0;
        }
        int movedCounter = 0;
        int count = 0;
        LinkedHashSet<MessageReference> set = new LinkedHashSet<MessageReference>();
        do {
            int oldMaxSize = this.getMaxPageSize();
            this.setMaxPageSize((int)this.destinationStatistics.getMessages().getCount());
            this.doPageIn(true);
            this.setMaxPageSize(oldMaxSize);
            this.pagedInMessagesLock.readLock().lock();
            try {
                set.addAll(this.pagedInMessages.values());
            }
            finally {
                this.pagedInMessagesLock.readLock().unlock();
            }
            ArrayList list = new ArrayList(set);
            for (MessageReference ref : list) {
                IndirectMessageReference r = (IndirectMessageReference)ref;
                if (filter2.evaluate(context, r)) {
                    r.incrementReferenceCount();
                    try {
                        Message m4 = r.getMessage();
                        BrokerSupport.resend(context, m4, dest);
                        if (++movedCounter >= maximumMessages && maximumMessages > 0) {
                            int n = movedCounter;
                            return n;
                        }
                    }
                    finally {
                        r.decrementReferenceCount();
                    }
                }
                ++count;
            }
        } while ((long)count < this.destinationStatistics.getMessages().getCount());
        return movedCounter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean moveMessageTo(ConnectionContext context, QueueMessageReference m4, ActiveMQDestination dest) throws Exception {
        Set<Destination> destsToPause = this.regionBroker.getDestinations(dest);
        try {
            for (Destination destination : destsToPause) {
                if (!(destination instanceof Queue)) continue;
                ((Queue)destination).pauseDispatch();
            }
            BrokerSupport.resend(context, m4.getMessage(), dest);
            this.removeMessage(context, m4);
            this.messagesLock.writeLock().lock();
            try {
                ActiveMQDestination originalDestination;
                this.messages.rollback(m4.getMessageId());
                if (this.isDLQ() && (originalDestination = m4.getMessage().getOriginalDestination()) != null) {
                    for (Destination destination : this.regionBroker.getDestinations(originalDestination)) {
                        DeadLetterStrategy strategy = destination.getDeadLetterStrategy();
                        strategy.rollback(m4.getMessage());
                    }
                }
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
        }
        finally {
            for (Destination destination : destsToPause) {
                if (!(destination instanceof Queue)) continue;
                ((Queue)destination).resumeDispatch();
            }
        }
        return true;
    }

    public boolean moveMessageTo(ConnectionContext context, String messageId, ActiveMQDestination dest) throws Exception {
        return this.moveMatchingMessagesTo(context, this.createMessageIdFilter(messageId), dest, 1) > 0;
    }

    public int moveMatchingMessagesTo(ConnectionContext context, String selector, ActiveMQDestination dest) throws Exception {
        return this.moveMatchingMessagesTo(context, selector, dest, Integer.MAX_VALUE);
    }

    public int moveMatchingMessagesTo(ConnectionContext context, String selector, ActiveMQDestination dest, int maximumMessages) throws Exception {
        return this.moveMatchingMessagesTo(context, this.createSelectorFilter(selector), dest, maximumMessages);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int moveMatchingMessagesTo(ConnectionContext context, MessageReferenceFilter filter2, ActiveMQDestination dest, int maximumMessages) throws Exception {
        if (this.destination.equals(dest)) {
            return 0;
        }
        int movedCounter = 0;
        LinkedHashSet<MessageReference> set = new LinkedHashSet<MessageReference>();
        do {
            this.doPageIn(true);
            this.pagedInMessagesLock.readLock().lock();
            try {
                set.addAll(this.pagedInMessages.values());
            }
            finally {
                this.pagedInMessagesLock.readLock().unlock();
            }
            ArrayList list = new ArrayList(set);
            for (MessageReference ref : list) {
                if (!filter2.evaluate(context, ref)) continue;
                this.moveMessageTo(context, (QueueMessageReference)ref, dest);
                set.remove(ref);
                if (++movedCounter < maximumMessages || maximumMessages <= 0) continue;
                return movedCounter;
            }
        } while ((long)set.size() < this.destinationStatistics.getMessages().getCount() && set.size() < maximumMessages);
        return movedCounter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int retryMessages(ConnectionContext context, int maximumMessages) throws Exception {
        if (!this.isDLQ()) {
            throw new Exception("Retry of message is only possible on Dead Letter Queues!");
        }
        int restoredCounter = 0;
        long numberOfRetryAttemptsToCheckAllMessagesOnce = this.destinationStatistics.getMessages().getCount();
        LinkedHashSet<MessageReference> set = new LinkedHashSet<MessageReference>();
        do {
            this.doPageIn(true);
            this.pagedInMessagesLock.readLock().lock();
            try {
                set.addAll(this.pagedInMessages.values());
            }
            finally {
                this.pagedInMessagesLock.readLock().unlock();
            }
            ArrayList list = new ArrayList(set);
            for (MessageReference ref : list) {
                --numberOfRetryAttemptsToCheckAllMessagesOnce;
                if (ref.getMessage().getOriginalDestination() == null) continue;
                this.moveMessageTo(context, (QueueMessageReference)ref, ref.getMessage().getOriginalDestination());
                set.remove(ref);
                if (++restoredCounter < maximumMessages || maximumMessages <= 0) continue;
                return restoredCounter;
            }
        } while (numberOfRetryAttemptsToCheckAllMessagesOnce > 0L && (long)set.size() < this.destinationStatistics.getMessages().getCount());
        return restoredCounter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean iterate() {
        MDC.put("activemq.destination", this.getName());
        boolean pageInMoreMessages = false;
        Object object = this.iteratingMutex;
        synchronized (object) {
            boolean hasBrowsers;
            boolean bl;
            this.iterationRunning = true;
            Map<MessageId, Runnable> map = this.messagesWaitingForSpace;
            synchronized (map) {
                Iterator<Runnable> it = this.messagesWaitingForSpace.values().iterator();
                while (it.hasNext()) {
                    if (!this.memoryUsage.isFull()) {
                        Runnable op = it.next();
                        it.remove();
                        op.run();
                        continue;
                    }
                    this.registerCallbackForNotFullNotification();
                    break;
                }
            }
            if (this.firstConsumer) {
                this.firstConsumer = false;
                try {
                    if (this.consumersBeforeDispatchStarts > 0) {
                        int timeout2 = 1000;
                        if (this.timeBeforeDispatchStarts > 0) {
                            timeout2 = this.timeBeforeDispatchStarts;
                        }
                        if (this.consumersBeforeStartsLatch.await(timeout2, TimeUnit.MILLISECONDS)) {
                            LOG.debug("{} consumers subscribed. Starting dispatch.", (Object)this.consumers.size());
                        } else {
                            LOG.debug("{} ms elapsed and {} consumers subscribed. Starting dispatch.", (Object)timeout2, (Object)this.consumers.size());
                        }
                    }
                    if (this.timeBeforeDispatchStarts > 0 && this.consumersBeforeDispatchStarts <= 0) {
                        this.iteratingMutex.wait(this.timeBeforeDispatchStarts);
                        LOG.debug("{} ms elapsed. Starting dispatch.", (Object)this.timeBeforeDispatchStarts);
                    }
                }
                catch (Exception e) {
                    LOG.error(e.toString());
                }
            }
            this.messagesLock.readLock().lock();
            try {
                pageInMoreMessages |= !this.messages.isEmpty();
            }
            finally {
                this.messagesLock.readLock().unlock();
            }
            this.pagedInPendingDispatchLock.readLock().lock();
            try {
                bl = !this.dispatchPendingList.isEmpty();
            }
            finally {
                this.pagedInPendingDispatchLock.readLock().unlock();
            }
            boolean bl2 = hasBrowsers = !this.browserSubscriptions.isEmpty();
            if ((pageInMoreMessages |= bl) || hasBrowsers || !this.dispatchPendingList.hasRedeliveries()) {
                try {
                    this.pageInMessages(hasBrowsers && this.getMaxBrowsePageSize() > 0, this.getMaxPageSize());
                }
                catch (Throwable e) {
                    LOG.error("Failed to page in more queue messages ", e);
                }
            }
            if (hasBrowsers) {
                PendingList messagesInMemory = this.isPrioritizedMessages() ? new PrioritizedPendingList() : new OrderedPendingList();
                this.pagedInMessagesLock.readLock().lock();
                try {
                    messagesInMemory.addAll(this.pagedInMessages);
                }
                finally {
                    this.pagedInMessagesLock.readLock().unlock();
                }
                Iterator<QueueBrowserSubscription> browsers = this.browserSubscriptions.iterator();
                while (browsers.hasNext()) {
                    QueueBrowserSubscription browser = browsers.next();
                    try {
                        NonCachedMessageEvaluationContext msgContext = new NonCachedMessageEvaluationContext();
                        msgContext.setDestination(this.destination);
                        LOG.debug("dispatch to browser: {}, already dispatched/paged count: {}", (Object)browser, (Object)messagesInMemory.size());
                        boolean added = false;
                        for (MessageReference node : messagesInMemory) {
                            if (((QueueMessageReference)node).isAcked() || browser.isDuplicate(node.getMessageId()) || browser.atMax()) continue;
                            ((MessageEvaluationContext)msgContext).setMessageReference(node);
                            if (!browser.matches(node, msgContext)) continue;
                            browser.add(node);
                            added = true;
                        }
                        if (!added || browser.atMax()) {
                            browser.decrementQueueRef();
                            browsers.remove();
                            continue;
                        }
                        this.wakeup();
                    }
                    catch (Exception e) {
                        LOG.warn("exception on dispatch to browser: {}", (Object)browser, (Object)e);
                    }
                }
            }
            if (this.pendingWakeups.get() > 0L) {
                this.pendingWakeups.decrementAndGet();
            }
            MDC.remove("activemq.destination");
            this.iterationRunning = false;
            return this.pendingWakeups.get() > 0L;
        }
    }

    public void pauseDispatch() {
        this.dispatchSelector.pause();
    }

    public void resumeDispatch() {
        this.dispatchSelector.resume();
        this.wakeup();
    }

    public boolean isDispatchPaused() {
        return this.dispatchSelector.isPaused();
    }

    protected MessageReferenceFilter createMessageIdFilter(final String messageId) {
        return new MessageReferenceFilter(){

            @Override
            public boolean evaluate(ConnectionContext context, MessageReference r) {
                return messageId.equals(r.getMessageId().toString());
            }

            public String toString() {
                return "MessageIdFilter: " + messageId;
            }
        };
    }

    protected MessageReferenceFilter createSelectorFilter(String selector) throws InvalidSelectorException {
        if (selector == null || selector.isEmpty()) {
            return new MessageReferenceFilter(){

                @Override
                public boolean evaluate(ConnectionContext context, MessageReference messageReference) throws JMSException {
                    return true;
                }
            };
        }
        final BooleanExpression selectorExpression = SelectorParser.parse(selector);
        return new MessageReferenceFilter(){

            @Override
            public boolean evaluate(ConnectionContext context, MessageReference r) throws JMSException {
                MessageEvaluationContext messageEvaluationContext = context.getMessageEvaluationContext();
                messageEvaluationContext.setMessageReference(r);
                if (messageEvaluationContext.getDestination() == null) {
                    messageEvaluationContext.setDestination(Queue.this.getActiveMQDestination());
                }
                return selectorExpression.matches(messageEvaluationContext);
            }
        };
    }

    protected void removeMessage(ConnectionContext c, QueueMessageReference r) throws IOException {
        this.removeMessage(c, null, r);
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            this.dispatchPendingList.remove(r);
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
    }

    protected void removeMessage(ConnectionContext c, Subscription subs, QueueMessageReference r) throws IOException {
        MessageAck ack = new MessageAck();
        ack.setAckType((byte)2);
        ack.setDestination(this.destination);
        ack.setMessageID(r.getMessageId());
        this.removeMessage(c, subs, r, ack);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void removeMessage(ConnectionContext context, Subscription sub, QueueMessageReference reference, MessageAck ack) throws IOException {
        LOG.trace("ack of {} with {}", (Object)reference.getMessageId(), (Object)ack);
        if (!ack.isInTransaction()) {
            this.acknowledge(context, sub, ack, reference);
            this.dropMessage(reference);
        } else {
            try {
                this.acknowledge(context, sub, ack, reference);
                context.getTransaction().addSynchronization(new Synchronization(reference){
                    final /* synthetic */ QueueMessageReference val$reference;
                    {
                        this.val$reference = queueMessageReference;
                    }

                    @Override
                    public void afterCommit() throws Exception {
                        Queue.this.dropMessage(this.val$reference);
                        Queue.this.wakeup();
                    }

                    @Override
                    public void afterRollback() throws Exception {
                        this.val$reference.setAcked(false);
                        Queue.this.wakeup();
                    }
                });
            }
            catch (Throwable throwable) {
                context.getTransaction().addSynchronization(new /* invalid duplicate definition of identical inner class */);
                throw throwable;
            }
        }
        if (ack.isPoisonAck() || sub != null && sub.getConsumerInfo().isNetworkSubscription()) {
            this.messagesLock.writeLock().lock();
            try {
                this.messages.rollback(reference.getMessageId());
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
            if (sub != null && sub.getConsumerInfo().isNetworkSubscription()) {
                this.getDestinationStatistics().getForwards().increment();
            }
        }
        reference.setAcked(true);
    }

    private void dropMessage(QueueMessageReference reference) {
        if (reference.dropIfLive()) {
            this.getDestinationStatistics().getDequeues().increment();
            this.getDestinationStatistics().getMessages().decrement();
            this.pagedInMessagesLock.writeLock().lock();
            try {
                this.pagedInMessages.remove(reference);
            }
            finally {
                this.pagedInMessagesLock.writeLock().unlock();
            }
        }
    }

    public void messageExpired(ConnectionContext context, MessageReference reference) {
        this.messageExpired(context, null, reference);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void messageExpired(ConnectionContext context, Subscription subs, MessageReference reference) {
        LOG.debug("message expired: {}", (Object)reference);
        this.broker.messageExpired(context, reference, subs);
        this.destinationStatistics.getExpired().increment();
        try {
            this.removeMessage(context, subs, (QueueMessageReference)reference);
            this.messagesLock.writeLock().lock();
            try {
                this.messages.rollback(reference.getMessageId());
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
        }
        catch (IOException e) {
            LOG.error("Failed to remove expired Message from the store ", e);
        }
    }

    private final boolean cursorAdd(Message msg) throws Exception {
        this.messagesLock.writeLock().lock();
        try {
            boolean bl = this.messages.addMessageLast(msg);
            return bl;
        }
        finally {
            this.messagesLock.writeLock().unlock();
        }
    }

    private final boolean tryCursorAdd(Message msg) throws Exception {
        this.messagesLock.writeLock().lock();
        try {
            boolean bl = this.messages.tryAddMessageLast(msg, 50L);
            return bl;
        }
        finally {
            this.messagesLock.writeLock().unlock();
        }
    }

    final void messageSent(ConnectionContext context, Message msg) throws Exception {
        this.pendingSends.decrementAndGet();
        this.destinationStatistics.getEnqueues().increment();
        this.destinationStatistics.getMessages().increment();
        this.destinationStatistics.getMessageSize().addSize(msg.getSize());
        this.messageDelivered(context, msg);
        this.consumersLock.readLock().lock();
        try {
            if (this.consumers.isEmpty()) {
                this.onMessageWithNoConsumers(context, msg);
            }
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
        LOG.debug("{} Message {} sent to {}", this.broker.getBrokerName(), msg.getMessageId(), this.destination);
        this.wakeup();
    }

    @Override
    public void wakeup() {
        if (this.optimizedDispatch && !this.iterationRunning) {
            this.iterate();
            this.pendingWakeups.incrementAndGet();
        } else {
            this.asyncWakeup();
        }
    }

    private void asyncWakeup() {
        try {
            this.pendingWakeups.incrementAndGet();
            this.taskRunner.wakeup();
        }
        catch (InterruptedException e) {
            LOG.warn("Async task runner failed to wakeup ", e);
        }
    }

    private void doPageIn(boolean force) throws Exception {
        this.doPageIn(force, true, this.getMaxPageSize());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doPageIn(boolean force, boolean processExpired, int maxPageSize) throws Exception {
        PendingList newlyPaged = this.doPageInForDispatch(force, processExpired, maxPageSize);
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            if (this.dispatchPendingList.isEmpty()) {
                this.dispatchPendingList.addAll(newlyPaged);
            } else {
                for (MessageReference qmr : newlyPaged) {
                    if (this.dispatchPendingList.contains(qmr)) continue;
                    this.dispatchPendingList.addMessageLast(qmr);
                }
            }
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PendingList doPageInForDispatch(boolean force, boolean processExpired, int maxPageSize) throws Exception {
        ArrayList<QueueMessageReference> result = null;
        PendingList resultList = null;
        int toPageIn = maxPageSize;
        this.messagesLock.readLock().lock();
        try {
            toPageIn = Math.min(toPageIn, this.messages.size());
        }
        finally {
            this.messagesLock.readLock().unlock();
        }
        int pagedInPendingSize = 0;
        this.pagedInPendingDispatchLock.readLock().lock();
        try {
            pagedInPendingSize = this.dispatchPendingList.size();
        }
        finally {
            this.pagedInPendingDispatchLock.readLock().unlock();
        }
        if (this.isLazyDispatch() && !force) {
            toPageIn = Math.min(toPageIn, this.getConsumerMessageCountBeforeFull());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} toPageIn: {}, force:{}, Inflight: {}, pagedInMessages.size {}, pagedInPendingDispatch.size {}, enqueueCount: {}, dequeueCount: {}, memUsage:{}, maxPageSize:{}", this, toPageIn, force, this.destinationStatistics.getInflight().getCount(), this.pagedInMessages.size(), pagedInPendingSize, this.destinationStatistics.getEnqueues().getCount(), this.destinationStatistics.getDequeues().getCount(), this.getMemoryUsage().getUsage(), maxPageSize);
        }
        if (toPageIn > 0 && (force || this.haveRealConsumer() && pagedInPendingSize < maxPageSize)) {
            int count = 0;
            result = new ArrayList<QueueMessageReference>(toPageIn);
            this.messagesLock.writeLock().lock();
            try {
                try {
                    this.messages.setMaxBatchSize(toPageIn);
                    this.messages.reset();
                    while (count < toPageIn && this.messages.hasNext()) {
                        MessageReference node = this.messages.next();
                        this.messages.remove();
                        QueueMessageReference ref = this.createMessageReference(node.getMessage());
                        if (processExpired && ref.isExpired()) {
                            if (this.broker.isExpired(ref)) {
                                this.messageExpired(this.createConnectionContext(), ref);
                                continue;
                            }
                            ref.decrementReferenceCount();
                            continue;
                        }
                        result.add(ref);
                        ++count;
                    }
                }
                finally {
                    this.messages.release();
                }
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
            this.pagedInMessagesLock.writeLock().lock();
            try {
                resultList = this.isPrioritizedMessages() ? new PrioritizedPendingList() : new OrderedPendingList();
                for (QueueMessageReference ref : result) {
                    if (!this.pagedInMessages.contains(ref)) {
                        this.pagedInMessages.addMessageLast(ref);
                        resultList.addMessageLast(ref);
                        continue;
                    }
                    ref.decrementReferenceCount();
                    LOG.warn("{}, duplicate message {} - {} from cursor, is cursor audit disabled or too constrained? Redirecting to dlq", this, ref.getMessageId(), ref.getMessage().getMessageId().getFutureOrSequenceLong());
                    if (this.store == null) continue;
                    ConnectionContext connectionContext = this.createConnectionContext();
                    this.dropMessage(ref);
                    if (AbstractStoreCursor.gotToTheStore(ref.getMessage())) {
                        LOG.debug("Duplicate message {} from cursor, removing from store", (Object)ref.getMessage());
                        this.store.removeMessage(connectionContext, new MessageAck(ref.getMessage(), 1, 1));
                    }
                    this.broker.getRoot().sendToDeadLetterQueue(connectionContext, ref.getMessage(), null, new Throwable("duplicate paged in from cursor for " + this.destination));
                }
            }
            finally {
                this.pagedInMessagesLock.writeLock().unlock();
            }
        }
        resultList = new OrderedPendingList();
        return resultList;
    }

    private final boolean haveRealConsumer() {
        return this.consumers.size() - this.browserSubscriptions.size() > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doDispatch(PendingList list) throws Exception {
        boolean doWakeUp = false;
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            if (this.isPrioritizedMessages() && !this.dispatchPendingList.isEmpty() && list != null && !list.isEmpty()) {
                for (MessageReference qmr : list) {
                    if (this.dispatchPendingList.contains(qmr)) continue;
                    this.dispatchPendingList.addMessageLast(qmr);
                }
                list = null;
            }
            this.doActualDispatch(this.dispatchPendingList);
            if (list != null && !list.isEmpty()) {
                if (this.dispatchPendingList.isEmpty()) {
                    this.dispatchPendingList.addAll(this.doActualDispatch(list));
                } else {
                    for (MessageReference qmr : list) {
                        if (this.dispatchPendingList.contains(qmr)) continue;
                        this.dispatchPendingList.addMessageLast(qmr);
                    }
                    doWakeUp = true;
                }
            }
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
        if (doWakeUp) {
            this.asyncWakeup();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PendingList doActualDispatch(PendingList list) throws Exception {
        ArrayList<Subscription> consumers;
        this.consumersLock.readLock().lock();
        try {
            if (this.consumers.isEmpty()) {
                PendingList pendingList = list;
                return pendingList;
            }
            consumers = new ArrayList<Subscription>(this.consumers);
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
        HashSet<Subscription> fullConsumers = new HashSet<Subscription>(this.consumers.size());
        Iterator<MessageReference> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            MessageReference node = iterator2.next();
            Subscription target = null;
            for (Subscription s2 : consumers) {
                if (s2 instanceof QueueBrowserSubscription || fullConsumers.contains(s2)) continue;
                if (!s2.isFull()) {
                    if (!this.dispatchSelector.canSelect(s2, node) || !this.assignMessageGroup(s2, (QueueMessageReference)node) || ((QueueMessageReference)node).isAcked()) continue;
                    s2.add(node);
                    LOG.trace("assigned {} to consumer {}", (Object)node.getMessageId(), (Object)s2.getConsumerInfo().getConsumerId());
                    iterator2.remove();
                    target = s2;
                    break;
                }
                fullConsumers.add(s2);
                LOG.trace("Subscription full {}", (Object)s2);
            }
            if (target == null && node.isDropped()) {
                iterator2.remove();
            }
            if (target == null && consumers.size() == fullConsumers.size()) {
                return list;
            }
            if (target == null || this.strictOrderDispatch || consumers.size() <= 1 || this.dispatchSelector.isExclusiveConsumer(target)) continue;
            this.consumersLock.writeLock().lock();
            try {
                if (!this.removeFromConsumerList(target)) continue;
                this.addToConsumerList(target);
                consumers = new ArrayList<Subscription>(this.consumers);
            }
            finally {
                this.consumersLock.writeLock().unlock();
            }
        }
        return list;
    }

    protected boolean assignMessageGroup(Subscription subscription, QueueMessageReference node) throws Exception {
        boolean result = true;
        String groupId = node.getGroupID();
        int sequence2 = node.getGroupSequence();
        if (groupId != null) {
            MessageGroupMap messageGroupOwners = this.getMessageGroupOwners();
            if (sequence2 == 1) {
                this.assignGroup(subscription, messageGroupOwners, node, groupId);
            } else {
                ConsumerId groupOwner = messageGroupOwners.get(groupId);
                if (groupOwner == null) {
                    this.assignGroup(subscription, messageGroupOwners, node, groupId);
                } else if (groupOwner.equals(subscription.getConsumerInfo().getConsumerId())) {
                    if (sequence2 < 0) {
                        messageGroupOwners.removeGroup(groupId);
                        subscription.getConsumerInfo().decrementAssignedGroupCount(this.destination);
                    }
                } else {
                    result = false;
                }
            }
        }
        return result;
    }

    protected void assignGroup(Subscription subs, MessageGroupMap messageGroupOwners, MessageReference n, String groupId) throws IOException {
        messageGroupOwners.put(groupId, subs.getConsumerInfo().getConsumerId());
        Message message = n.getMessage();
        message.setJMSXGroupFirstForConsumer(true);
        subs.getConsumerInfo().incrementAssignedGroupCount(this.destination);
    }

    protected void pageInMessages(boolean force, int maxPageSize) throws Exception {
        this.doDispatch(this.doPageInForDispatch(force, true, maxPageSize));
    }

    private void addToConsumerList(Subscription sub) {
        if (this.useConsumerPriority) {
            this.consumers.add(sub);
            Collections.sort(this.consumers, this.orderedCompare);
        } else {
            this.consumers.add(sub);
        }
    }

    private boolean removeFromConsumerList(Subscription sub) {
        return this.consumers.remove(sub);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int getConsumerMessageCountBeforeFull() throws Exception {
        int total = 0;
        this.consumersLock.readLock().lock();
        try {
            for (Subscription s2 : this.consumers) {
                if (s2.isBrowser()) continue;
                int countBeforeFull = s2.countBeforeFull();
                total += countBeforeFull;
            }
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
        return total;
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        Subscription sub = this.getMatchingSubscription(messageDispatchNotification);
        if (sub != null) {
            QueueMessageReference message = this.getMatchingMessage(messageDispatchNotification);
            sub.add(message);
            sub.processMessageDispatchNotification(messageDispatchNotification);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private QueueMessageReference getMatchingMessage(MessageDispatchNotification messageDispatchNotification) throws Exception {
        Message msg;
        QueueMessageReference message = null;
        MessageId messageId = messageDispatchNotification.getMessageId();
        this.pagedInPendingDispatchLock.writeLock().lock();
        try {
            for (MessageReference ref : this.dispatchPendingList) {
                if (!messageId.equals(ref.getMessageId())) continue;
                message = (QueueMessageReference)ref;
                this.dispatchPendingList.remove(ref);
                break;
            }
        }
        finally {
            this.pagedInPendingDispatchLock.writeLock().unlock();
        }
        if (message == null) {
            this.pagedInMessagesLock.readLock().lock();
            try {
                message = (QueueMessageReference)this.pagedInMessages.get(messageId);
            }
            finally {
                this.pagedInMessagesLock.readLock().unlock();
            }
        }
        if (message == null) {
            this.messagesLock.writeLock().lock();
            try {
                try {
                    this.messages.setMaxBatchSize(this.getMaxPageSize());
                    this.messages.reset();
                    while (this.messages.hasNext()) {
                        MessageReference node = this.messages.next();
                        this.messages.remove();
                        if (!messageId.equals(node.getMessageId())) continue;
                        message = this.createMessageReference(node.getMessage());
                        break;
                    }
                }
                finally {
                    this.messages.release();
                }
            }
            finally {
                this.messagesLock.writeLock().unlock();
            }
        }
        if (message == null && (msg = this.loadMessage(messageId)) != null) {
            message = this.createMessageReference(msg);
        }
        if (message == null) {
            throw new JMSException("Slave broker out of sync with master - Message: " + messageDispatchNotification.getMessageId() + " on " + messageDispatchNotification.getDestination() + " does not exist among pending(" + this.dispatchPendingList.size() + ") for subscription: " + messageDispatchNotification.getConsumerId());
        }
        return message;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Subscription getMatchingSubscription(MessageDispatchNotification messageDispatchNotification) throws JMSException {
        Subscription sub = null;
        this.consumersLock.readLock().lock();
        try {
            for (Subscription s2 : this.consumers) {
                if (!messageDispatchNotification.getConsumerId().equals(s2.getConsumerInfo().getConsumerId())) continue;
                sub = s2;
                break;
            }
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
        return sub;
    }

    @Override
    public void onUsageChanged(Usage usage, int oldPercentUsage, int newPercentUsage) {
        if (oldPercentUsage > newPercentUsage) {
            this.asyncWakeup();
        }
    }

    @Override
    protected Logger getLog() {
        return LOG;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean isOptimizeStorage() {
        boolean result;
        block9: {
            result = false;
            if (this.isDoOptimzeMessageStorage()) {
                this.consumersLock.readLock().lock();
                try {
                    if (this.consumers.isEmpty()) break block9;
                    result = true;
                    for (Subscription s2 : this.consumers) {
                        if (s2.getPrefetchSize() == 0) {
                            result = false;
                        } else if (s2.isSlowConsumer()) {
                            result = false;
                        } else {
                            if (s2.getInFlightUsage() <= this.getOptimizeMessageStoreInFlightLimit()) continue;
                            result = false;
                        }
                        break;
                    }
                }
                finally {
                    this.consumersLock.readLock().unlock();
                }
            }
        }
        return result;
    }

    final class CursorAddSync
    extends Synchronization {
        private final IndexListener.MessageContext messageContext;

        CursorAddSync(IndexListener.MessageContext messageContext) {
            this.messageContext = messageContext;
            this.messageContext.message.incrementReferenceCount();
        }

        @Override
        public void afterCommit() throws Exception {
            if (Queue.this.store != null && this.messageContext.message.isPersistent()) {
                Queue.this.doPendingCursorAdditions();
            } else {
                Queue.this.cursorAdd(this.messageContext.message);
                Queue.this.messageSent(this.messageContext.context, this.messageContext.message);
            }
            this.messageContext.message.decrementReferenceCount();
        }

        @Override
        public void afterRollback() throws Exception {
            this.messageContext.message.decrementReferenceCount();
        }
    }

    class BatchMessageRecoveryListener
    implements MessageRecoveryListener {
        final LinkedList<Message> toExpire = new LinkedList();
        final double totalMessageCount;
        int recoveredAccumulator = 0;
        int currentBatchCount;

        BatchMessageRecoveryListener(int totalMessageCount) {
            this.totalMessageCount = totalMessageCount;
            this.currentBatchCount = this.recoveredAccumulator;
        }

        @Override
        public boolean recoverMessage(Message message) {
            ++this.recoveredAccumulator;
            if (this.recoveredAccumulator % 10000 == 0) {
                LOG.info("cursor for {} has recovered {} messages. {}% complete", Queue.this.getActiveMQDestination().getQualifiedName(), this.recoveredAccumulator, new Integer((int)((double)(this.recoveredAccumulator * 100) / this.totalMessageCount)));
            }
            message.setRegionDestination(Queue.this);
            if (message.isExpired() && Queue.this.broker.isExpired(message)) {
                this.toExpire.add(message);
                return true;
            }
            if (this.hasSpace()) {
                Queue.this.messagesLock.writeLock().lock();
                try {
                    try {
                        Queue.this.messages.addMessageLast(message);
                    }
                    catch (Exception e) {
                        LOG.error("Failed to add message to cursor", e);
                    }
                }
                finally {
                    Queue.this.messagesLock.writeLock().unlock();
                }
                Queue.this.destinationStatistics.getMessages().increment();
                return true;
            }
            return false;
        }

        @Override
        public boolean recoverMessageReference(MessageId messageReference) throws Exception {
            throw new RuntimeException("Should not be called.");
        }

        @Override
        public boolean hasSpace() {
            return true;
        }

        @Override
        public boolean isDuplicate(MessageId id) {
            return false;
        }

        public void reset() {
            this.currentBatchCount = this.recoveredAccumulator;
        }

        public void processExpired() {
            for (Message message : this.toExpire) {
                Queue.this.messageExpired(Queue.this.createConnectionContext(), Queue.this.createMessageReference(message));
                Queue.this.destinationStatistics.getMessages().increment();
            }
            this.toExpire.clear();
        }

        public boolean done() {
            return this.currentBatchCount == this.recoveredAccumulator;
        }
    }

    final class QueueThread
    extends Thread {
        final Queue queue;

        public QueueThread(Runnable runnable2, String name, Queue queue) {
            super(runnable2, name);
            this.queue = queue;
        }
    }

    class FlowControlTimeoutTask
    extends Thread {
        FlowControlTimeoutTask() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                while (true) {
                    TimeoutMessage timeout2;
                    if ((timeout2 = (TimeoutMessage)Queue.this.flowControlTimeoutMessages.take()) == null) {
                        continue;
                    }
                    Map map = Queue.this.messagesWaitingForSpace;
                    synchronized (map) {
                        if (Queue.this.messagesWaitingForSpace.remove(timeout2.message.getMessageId()) != null) {
                            ExceptionResponse response = new ExceptionResponse(new ResourceAllocationException("Usage Manager Memory Limit reached. Stopping producer (" + timeout2.message.getProducerId() + ") to prevent flooding " + Queue.this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info"));
                            response.setCorrelationId(timeout2.message.getCommandId());
                            timeout2.context.getConnection().dispatchAsync(response);
                        }
                    }
                }
            }
            catch (InterruptedException e) {
                LOG.debug(this.getName() + "Producer Flow Control Timeout Task is stopping");
                return;
            }
        }
    }

    class TimeoutMessage
    implements Delayed {
        Message message;
        ConnectionContext context;
        long trigger;

        public TimeoutMessage(Message message, ConnectionContext context, long delay) {
            this.message = message;
            this.context = context;
            this.trigger = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long n = this.trigger - System.currentTimeMillis();
            return unit.convert(n, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            long other = ((TimeoutMessage)delayed).trigger;
            int returnValue = this.trigger < other ? -1 : (this.trigger > other ? 1 : 0);
            return returnValue;
        }
    }
}

