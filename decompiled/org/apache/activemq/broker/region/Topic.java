/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.JMSException;
import javax.jms.ResourceAllocationException;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.LockOwner;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.broker.region.policy.LastImageSubscriptionRecoveryPolicy;
import org.apache.activemq.broker.region.policy.RetainedMessageSubscriptionRecoveryPolicy;
import org.apache.activemq.broker.region.policy.SimpleDispatchPolicy;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.broker.util.InsertionCountList;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.NoLocalSubscriptionAware;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.util.SubscriptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Topic
extends BaseDestination
implements Task {
    protected static final Logger LOG = LoggerFactory.getLogger(Topic.class);
    private final TopicMessageStore topicStore;
    protected final CopyOnWriteArrayList<Subscription> consumers = new CopyOnWriteArrayList();
    private final ReentrantReadWriteLock dispatchLock = new ReentrantReadWriteLock();
    private DispatchPolicy dispatchPolicy = new SimpleDispatchPolicy();
    private SubscriptionRecoveryPolicy subscriptionRecoveryPolicy;
    private final ConcurrentMap<SubscriptionKey, DurableTopicSubscription> durableSubscribers = new ConcurrentHashMap<SubscriptionKey, DurableTopicSubscription>();
    private final TaskRunner taskRunner;
    private final LinkedList<Runnable> messagesWaitingForSpace = new LinkedList();
    private final Runnable sendMessagesWaitingForSpaceTask = new Runnable(){

        @Override
        public void run() {
            try {
                Topic.this.taskRunner.wakeup();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    };
    private final Runnable expireMessagesTask = new Runnable(){

        @Override
        public void run() {
            InsertionCountList browsedMessages = new InsertionCountList();
            Topic.this.doBrowse(browsedMessages, Topic.this.getMaxExpirePageSize());
        }
    };

    public Topic(BrokerService brokerService, ActiveMQDestination destination, TopicMessageStore store, DestinationStatistics parentStats, TaskRunnerFactory taskFactory) throws Exception {
        super(brokerService, store, destination, parentStats);
        this.topicStore = store;
        this.subscriptionRecoveryPolicy = new RetainedMessageSubscriptionRecoveryPolicy(null);
        this.taskRunner = taskFactory.createTaskRunner(this, "Topic  " + destination.getPhysicalName());
    }

    @Override
    public void initialize() throws Exception {
        super.initialize();
        if (AdvisorySupport.isMasterBrokerAdvisoryTopic(this.destination)) {
            this.subscriptionRecoveryPolicy = new LastImageSubscriptionRecoveryPolicy();
            this.setAlwaysRetroactive(true);
        }
        if (this.store != null) {
            this.store.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Subscription> getConsumers() {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
        synchronized (copyOnWriteArrayList) {
            return new ArrayList<Subscription>(this.consumers);
        }
    }

    public boolean lock(MessageReference node, LockOwner sub) {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        if (!sub.getConsumerInfo().isDurable()) {
            if (sub.getConsumerInfo().isRetroactive() || this.isAlwaysRetroactive()) {
                this.dispatchLock.writeLock().lock();
                try {
                    boolean applyRecovery = false;
                    CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
                    synchronized (copyOnWriteArrayList) {
                        if (!this.consumers.contains(sub)) {
                            sub.add(context, this);
                            this.consumers.add(sub);
                            applyRecovery = true;
                            super.addSubscription(context, sub);
                        }
                    }
                    if (!applyRecovery) return;
                    this.subscriptionRecoveryPolicy.recover(context, this, sub);
                    return;
                }
                finally {
                    this.dispatchLock.writeLock().unlock();
                }
            }
            CopyOnWriteArrayList<Subscription> applyRecovery = this.consumers;
            synchronized (applyRecovery) {
                if (this.consumers.contains(sub)) return;
                sub.add(context, this);
                this.consumers.add(sub);
                super.addSubscription(context, sub);
                return;
            }
        }
        DurableTopicSubscription dsub = (DurableTopicSubscription)sub;
        super.addSubscription(context, sub);
        sub.add(context, this);
        if (dsub.isActive()) {
            CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
            synchronized (copyOnWriteArrayList) {
                boolean hasSubscription = false;
                if (this.consumers.size() == 0) {
                    hasSubscription = false;
                } else {
                    for (Subscription currentSub : this.consumers) {
                        DurableTopicSubscription dcurrentSub;
                        if (!currentSub.getConsumerInfo().isDurable() || !(dcurrentSub = (DurableTopicSubscription)currentSub).getSubscriptionKey().equals(dsub.getSubscriptionKey())) continue;
                        hasSubscription = true;
                        break;
                    }
                }
                if (!hasSubscription) {
                    this.consumers.add(sub);
                }
            }
        }
        this.durableSubscribers.put(dsub.getSubscriptionKey(), dsub);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeSubscription(ConnectionContext context, Subscription sub, long lastDeliveredSequenceId) throws Exception {
        if (!sub.getConsumerInfo().isDurable()) {
            boolean removed = false;
            CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
            synchronized (copyOnWriteArrayList) {
                removed = this.consumers.remove(sub);
            }
            if (removed) {
                super.removeSubscription(context, sub, lastDeliveredSequenceId);
            }
        }
        sub.remove(context, this);
    }

    public void deleteSubscription(ConnectionContext context, SubscriptionKey key) throws Exception {
        if (this.topicStore != null) {
            this.topicStore.deleteSubscription(key.clientId, key.subscriptionName);
            DurableTopicSubscription removed = (DurableTopicSubscription)this.durableSubscribers.remove(key);
            if (removed != null) {
                this.destinationStatistics.getConsumers().decrement();
                removed.deactivate(false, 0L);
                this.consumers.remove(removed);
            }
        }
    }

    private boolean hasDurableSubChanged(SubscriptionInfo info1, ConsumerInfo info2) throws IOException {
        if (this.hasSelectorChanged(info1, info2)) {
            return true;
        }
        return this.hasNoLocalChanged(info1, info2);
    }

    private boolean hasNoLocalChanged(SubscriptionInfo info1, ConsumerInfo info2) throws IOException {
        PersistenceAdapter adapter = this.broker.getBrokerService().getPersistenceAdapter();
        return adapter instanceof NoLocalSubscriptionAware && info1.isNoLocal() ^ info2.isNoLocal();
    }

    private boolean hasSelectorChanged(SubscriptionInfo info1, ConsumerInfo info2) {
        if (info1.getSelector() != null ^ info2.getSelector() != null) {
            return true;
        }
        return info1.getSelector() != null && !info1.getSelector().equals(info2.getSelector());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void activate(ConnectionContext context, final DurableTopicSubscription subscription) throws Exception {
        this.dispatchLock.writeLock().lock();
        try {
            CopyOnWriteArrayList<Subscription> copyOnWriteArrayList;
            String subscriptionName;
            if (this.topicStore == null) {
                return;
            }
            String clientId = subscription.getSubscriptionKey().getClientId();
            SubscriptionInfo info = this.topicStore.lookupSubscription(clientId, subscriptionName = subscription.getSubscriptionKey().getSubscriptionName());
            if (info != null) {
                if (this.hasDurableSubChanged(info, subscription.getConsumerInfo())) {
                    this.topicStore.deleteSubscription(clientId, subscriptionName);
                    info = null;
                    subscription.setSelector(subscription.getConsumerInfo().getSelector());
                    copyOnWriteArrayList = this.consumers;
                    synchronized (copyOnWriteArrayList) {
                        this.consumers.remove(subscription);
                    }
                }
                copyOnWriteArrayList = this.consumers;
                synchronized (copyOnWriteArrayList) {
                    if (!this.consumers.contains(subscription)) {
                        this.consumers.add(subscription);
                    }
                }
            }
            if (info == null) {
                info = new SubscriptionInfo();
                info.setClientId(clientId);
                info.setSelector(subscription.getConsumerInfo().getSelector());
                info.setSubscriptionName(subscriptionName);
                info.setDestination(this.getActiveMQDestination());
                info.setNoLocal(subscription.getConsumerInfo().isNoLocal());
                info.setSubscribedDestination(subscription.getConsumerInfo().getDestination());
                copyOnWriteArrayList = this.consumers;
                synchronized (copyOnWriteArrayList) {
                    this.consumers.add(subscription);
                    this.topicStore.addSubscription(info, subscription.getConsumerInfo().isRetroactive());
                }
            }
            final NonCachedMessageEvaluationContext msgContext = new NonCachedMessageEvaluationContext();
            msgContext.setDestination(this.destination);
            if (subscription.isRecoveryRequired()) {
                this.topicStore.recoverSubscription(clientId, subscriptionName, new MessageRecoveryListener(){

                    @Override
                    public boolean recoverMessage(Message message) throws Exception {
                        message.setRegionDestination(Topic.this);
                        try {
                            msgContext.setMessageReference(message);
                            if (subscription.matches(message, msgContext)) {
                                subscription.add(message);
                            }
                        }
                        catch (IOException e) {
                            LOG.error("Failed to recover this message {}", (Object)message, (Object)e);
                        }
                        return true;
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
                });
            }
        }
        finally {
            this.dispatchLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deactivate(ConnectionContext context, DurableTopicSubscription sub, List<MessageReference> dispatched) throws Exception {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
        synchronized (copyOnWriteArrayList) {
            this.consumers.remove(sub);
        }
        sub.remove(context, this, dispatched);
    }

    public void recoverRetroactiveMessages(ConnectionContext context, Subscription subscription) throws Exception {
        if (subscription.getConsumerInfo().isRetroactive()) {
            this.subscriptionRecoveryPolicy.recover(context, this, subscription);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void send(final ProducerBrokerExchange producerExchange, final Message message) throws Exception {
        final ConnectionContext context = producerExchange.getConnectionContext();
        final ProducerInfo producerInfo = producerExchange.getProducerState().getInfo();
        producerExchange.incrementSend();
        final boolean sendProducerAck = !message.isResponseRequired() && producerInfo.getWindowSize() > 0 && !context.isInRecoveryMode();
        message.setRegionDestination(this);
        if (message.isExpired()) {
            this.broker.messageExpired(context, message, null);
            this.getDestinationStatistics().getExpired().increment();
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
                    LOG.info("{}, Usage Manager memory limit reached {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.", (Object)this.getActiveMQDestination().getQualifiedName(), (Object)this.memoryUsage.getLimit());
                }
                if (!context.isNetworkConnection() && this.systemUsage.isSendFailIfNoSpace()) {
                    throw new ResourceAllocationException("Usage Manager memory limit (" + this.memoryUsage.getLimit() + ") reached. Rejecting send for producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info");
                }
                if (producerInfo.getWindowSize() > 0 || message.isResponseRequired()) {
                    LinkedList<Runnable> ack = this.messagesWaitingForSpace;
                    synchronized (ack) {
                        this.messagesWaitingForSpace.add(new Runnable(){

                            @Override
                            public void run() {
                                block7: {
                                    try {
                                        if (message.isInTransaction() && (context.getTransaction() == null || context.getTransaction().getState() > 1)) {
                                            throw new JMSException("Send transaction completed while waiting for space");
                                        }
                                        if (message.isExpired()) {
                                            Topic.this.broker.messageExpired(context, message, null);
                                            Topic.this.getDestinationStatistics().getExpired().increment();
                                        } else {
                                            Topic.this.doMessageSend(producerExchange, message);
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
                                        if (sendProducerAck || context.isInRecoveryMode()) break block7;
                                        ExceptionResponse response = new ExceptionResponse(e);
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    }
                                }
                            }
                        });
                        this.registerCallbackForNotFullNotification();
                        context.setDontSendReponse(true);
                        return;
                    }
                }
                if (this.memoryUsage.isFull()) {
                    if (context.isInTransaction()) {
                        int count = 0;
                        while (!this.memoryUsage.waitForSpace(1000L)) {
                            if (context.getStopping().get()) {
                                throw new IOException("Connection closed, send aborted.");
                            }
                            if (count > 2 && context.isInTransaction()) {
                                count = 0;
                                int size = context.getTransaction().size();
                                LOG.warn("Waiting for space to send transacted message - transaction elements = {} need more space to commit. Message = {}", (Object)size, (Object)message);
                            }
                            ++count;
                        }
                    } else {
                        this.waitForSpace(context, producerExchange, this.memoryUsage, "Usage Manager Memory Usage limit reached. Stopping producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info");
                    }
                }
                if (message.isExpired()) {
                    this.getDestinationStatistics().getExpired().increment();
                    LOG.debug("Expired message: {}", (Object)message);
                    return;
                }
            }
        }
        this.doMessageSend(producerExchange, message);
        this.messageDelivered(context, message);
        if (sendProducerAck) {
            ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
            context.getConnection().dispatchAsync(ack);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void doMessageSend(ProducerBrokerExchange producerExchange, final Message message) throws IOException, Exception {
        final ConnectionContext context = producerExchange.getConnectionContext();
        message.getMessageId().setBrokerSequenceId(this.getDestinationSequenceId());
        ListenableFuture<Object> result = null;
        if (this.topicStore != null && message.isPersistent() && !this.canOptimizeOutPersistence()) {
            if (this.systemUsage.getStoreUsage().isFull(this.getStoreUsageHighWaterMark())) {
                String logMessage = "Persistent store is Full, " + this.getStoreUsageHighWaterMark() + "% of " + this.systemUsage.getStoreUsage().getLimit() + ". Stopping producer (" + message.getProducerId() + ") to prevent flooding " + this.getActiveMQDestination().getQualifiedName() + ". See http://activemq.apache.org/producer-flow-control.html for more info";
                if (!context.isNetworkConnection() && this.systemUsage.isSendFailIfNoSpace()) {
                    throw new ResourceAllocationException(logMessage);
                }
                this.waitForSpace(context, producerExchange, this.systemUsage.getStoreUsage(), this.getStoreUsageHighWaterMark(), logMessage);
            }
            result = this.topicStore.asyncAddTopicMessage(context, message, this.isOptimizeStorage());
        }
        message.incrementReferenceCount();
        if (context.isInTransaction()) {
            context.getTransaction().addSynchronization(new Synchronization(){

                @Override
                public void afterCommit() throws Exception {
                    if (message.isExpired()) {
                        if (Topic.this.broker.isExpired(message)) {
                            Topic.this.getDestinationStatistics().getExpired().increment();
                            Topic.this.broker.messageExpired(context, message, null);
                        }
                        message.decrementReferenceCount();
                        return;
                    }
                    try {
                        Topic.this.dispatch(context, message);
                    }
                    finally {
                        message.decrementReferenceCount();
                    }
                }

                @Override
                public void afterRollback() throws Exception {
                    message.decrementReferenceCount();
                }
            });
        } else {
            try {
                this.dispatch(context, message);
            }
            finally {
                message.decrementReferenceCount();
            }
        }
        if (result != null && !result.isCancelled()) {
            try {
                result.get();
            }
            catch (CancellationException cancellationException) {
                // empty catch block
            }
        }
    }

    private boolean canOptimizeOutPersistence() {
        return this.durableSubscribers.size() == 0;
    }

    public String toString() {
        return "Topic: destination=" + this.destination.getPhysicalName() + ", subscriptions=" + this.consumers.size();
    }

    @Override
    public void acknowledge(ConnectionContext context, Subscription sub, MessageAck ack, MessageReference node) throws IOException {
        if (this.topicStore != null && node.isPersistent()) {
            DurableTopicSubscription dsub = (DurableTopicSubscription)sub;
            SubscriptionKey key = dsub.getSubscriptionKey();
            this.topicStore.acknowledge(context, key.getClientId(), key.getSubscriptionName(), node.getMessageId(), this.convertToNonRangedAck(ack, node));
        }
        this.messageConsumed(context, node);
    }

    @Override
    public void gc() {
    }

    public Message loadMessage(MessageId messageId) throws IOException {
        return this.topicStore != null ? this.topicStore.getMessage(messageId) : null;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.subscriptionRecoveryPolicy.start();
            if (this.memoryUsage != null) {
                this.memoryUsage.start();
            }
            if (this.getExpireMessagesPeriod() > 0L && !AdvisorySupport.isAdvisoryTopic(this.getActiveMQDestination())) {
                this.scheduler.executePeriodically(this.expireMessagesTask, this.getExpireMessagesPeriod());
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            if (this.taskRunner != null) {
                this.taskRunner.shutdown();
            }
            this.subscriptionRecoveryPolicy.stop();
            if (this.memoryUsage != null) {
                this.memoryUsage.stop();
            }
            if (this.topicStore != null) {
                this.topicStore.stop();
            }
            this.scheduler.cancel(this.expireMessagesTask);
        }
    }

    @Override
    public Message[] browse() {
        ArrayList<Message> result = new ArrayList<Message>();
        this.doBrowse(result, this.getMaxBrowsePageSize());
        return result.toArray(new Message[result.size()]);
    }

    private void doBrowse(final List<Message> browseList, final int max) {
        try {
            if (this.topicStore != null) {
                final ArrayList toExpire = new ArrayList();
                this.topicStore.recover(new MessageRecoveryListener(){

                    @Override
                    public boolean recoverMessage(Message message) throws Exception {
                        if (message.isExpired()) {
                            toExpire.add(message);
                        }
                        browseList.add(message);
                        return true;
                    }

                    @Override
                    public boolean recoverMessageReference(MessageId messageReference) throws Exception {
                        return true;
                    }

                    @Override
                    public boolean hasSpace() {
                        return browseList.size() < max;
                    }

                    @Override
                    public boolean isDuplicate(MessageId id) {
                        return false;
                    }
                });
                ConnectionContext connectionContext = this.createConnectionContext();
                for (Message message : toExpire) {
                    for (DurableTopicSubscription sub : this.durableSubscribers.values()) {
                        if (sub.isActive()) continue;
                        message.setRegionDestination(this);
                        this.messageExpired(connectionContext, sub, message);
                    }
                }
                Message[] msgs = this.subscriptionRecoveryPolicy.browse(this.getActiveMQDestination());
                if (msgs != null) {
                    for (int i = 0; i < msgs.length && browseList.size() < max; ++i) {
                        browseList.add(msgs[i]);
                    }
                }
            }
        }
        catch (Throwable e) {
            LOG.warn("Failed to browse Topic: {}", (Object)this.getActiveMQDestination().getPhysicalName(), (Object)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean iterate() {
        LinkedList<Runnable> linkedList = this.messagesWaitingForSpace;
        synchronized (linkedList) {
            while (!this.memoryUsage.isFull() && !this.messagesWaitingForSpace.isEmpty()) {
                Runnable op = this.messagesWaitingForSpace.removeFirst();
                op.run();
            }
            if (!this.messagesWaitingForSpace.isEmpty()) {
                this.registerCallbackForNotFullNotification();
            }
        }
        return false;
    }

    private void registerCallbackForNotFullNotification() {
        if (!this.memoryUsage.notifyCallbackWhenNotFull(this.sendMessagesWaitingForSpaceTask)) {
            this.sendMessagesWaitingForSpaceTask.run();
        }
    }

    public DispatchPolicy getDispatchPolicy() {
        return this.dispatchPolicy;
    }

    public void setDispatchPolicy(DispatchPolicy dispatchPolicy) {
        this.dispatchPolicy = dispatchPolicy;
    }

    public SubscriptionRecoveryPolicy getSubscriptionRecoveryPolicy() {
        return this.subscriptionRecoveryPolicy;
    }

    public void setSubscriptionRecoveryPolicy(SubscriptionRecoveryPolicy recoveryPolicy) {
        if (this.subscriptionRecoveryPolicy != null && this.subscriptionRecoveryPolicy instanceof RetainedMessageSubscriptionRecoveryPolicy) {
            RetainedMessageSubscriptionRecoveryPolicy policy = (RetainedMessageSubscriptionRecoveryPolicy)this.subscriptionRecoveryPolicy;
            policy.setWrapped(recoveryPolicy);
        } else {
            this.subscriptionRecoveryPolicy = recoveryPolicy;
        }
    }

    @Override
    public final void wakeup() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void dispatch(ConnectionContext context, Message message) throws Exception {
        this.destinationStatistics.getEnqueues().increment();
        this.destinationStatistics.getMessageSize().addSize(message.getSize());
        MessageEvaluationContext msgContext = null;
        this.dispatchLock.readLock().lock();
        try {
            if (!this.subscriptionRecoveryPolicy.add(context, message)) {
                return;
            }
            CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.consumers;
            // MONITORENTER : copyOnWriteArrayList
            if (this.consumers.isEmpty()) {
                this.onMessageWithNoConsumers(context, message);
                // MONITOREXIT : copyOnWriteArrayList
                return;
            }
            // MONITOREXIT : copyOnWriteArrayList
            if (this.isReduceMemoryFootprint() && message.isMarshalled()) {
                message.clearUnMarshalledState();
            }
            msgContext = context.getMessageEvaluationContext();
            msgContext.setDestination(this.destination);
            msgContext.setMessageReference(message);
            if (this.dispatchPolicy.dispatch(message, msgContext, this.consumers)) return;
            this.onMessageWithNoConsumers(context, message);
            return;
        }
        finally {
            this.dispatchLock.readLock().unlock();
            if (msgContext != null) {
                msgContext.clear();
            }
        }
    }

    @Override
    public void messageExpired(ConnectionContext context, Subscription subs, MessageReference reference) {
        this.broker.messageExpired(context, reference, subs);
        this.destinationStatistics.getExpired().increment();
        MessageAck ack = new MessageAck();
        ack.setAckType((byte)2);
        ack.setDestination(this.destination);
        ack.setMessageID(reference.getMessageId());
        try {
            if (subs instanceof DurableTopicSubscription) {
                ((DurableTopicSubscription)subs).removePending(reference);
            }
            this.acknowledge(context, subs, ack, reference);
        }
        catch (Exception e) {
            LOG.error("Failed to remove expired Message from the store ", e);
        }
    }

    @Override
    protected Logger getLog() {
        return LOG;
    }

    protected boolean isOptimizeStorage() {
        boolean result = false;
        if (this.isDoOptimzeMessageStorage() && !this.durableSubscribers.isEmpty()) {
            result = true;
            for (DurableTopicSubscription s2 : this.durableSubscribers.values()) {
                if (!s2.isActive()) {
                    result = false;
                    break;
                }
                if (s2.getPrefetchSize() == 0) {
                    result = false;
                    break;
                }
                if (s2.isSlowConsumer()) {
                    result = false;
                    break;
                }
                if (s2.getInFlightUsage() <= this.getOptimizeMessageStoreInFlightLimit()) continue;
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void clearPendingMessages() {
        this.dispatchLock.readLock().lock();
        try {
            for (DurableTopicSubscription durableTopicSubscription : this.durableSubscribers.values()) {
                this.clearPendingAndDispatch(durableTopicSubscription);
            }
        }
        finally {
            this.dispatchLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void clearPendingAndDispatch(DurableTopicSubscription durableTopicSubscription) {
        Object object = durableTopicSubscription.pendingLock;
        synchronized (object) {
            durableTopicSubscription.pending.clear();
            try {
                durableTopicSubscription.dispatchPending();
            }
            catch (IOException exception) {
                LOG.warn("After clear of pending, failed to dispatch to: {}, for: {}, pending: {}, exception: {}", durableTopicSubscription, this.destination, durableTopicSubscription.pending, exception);
            }
        }
    }

    public Map<SubscriptionKey, DurableTopicSubscription> getDurableTopicSubs() {
        return this.durableSubscribers;
    }
}

