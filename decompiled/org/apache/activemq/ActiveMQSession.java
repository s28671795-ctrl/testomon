/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.TransactionRolledBackException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQDispatcher;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQQueueBrowser;
import org.apache.activemq.ActiveMQQueueReceiver;
import org.apache.activemq.ActiveMQQueueSender;
import org.apache.activemq.ActiveMQSessionExecutor;
import org.apache.activemq.ActiveMQTopicPublisher;
import org.apache.activemq.ActiveMQTopicSubscriber;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.CustomDestination;
import org.apache.activemq.MessageDispatchChannel;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.TransactionContext;
import org.apache.activemq.blob.BlobDownloader;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.BlobUploader;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQStreamMessage;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.management.JMSSessionStatsImpl;
import org.apache.activemq.management.StatsCapable;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.util.Callback;
import org.apache.activemq.util.LongSequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQSession
implements Session,
QueueSession,
TopicSession,
StatsCapable,
ActiveMQDispatcher {
    public static final int INDIVIDUAL_ACKNOWLEDGE = 4;
    public static final int MAX_ACK_CONSTANT = 4;
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQSession.class);
    private final ThreadPoolExecutor connectionExecutor;
    protected int acknowledgementMode;
    protected final ActiveMQConnection connection;
    protected final SessionInfo info;
    protected final LongSequenceGenerator consumerIdGenerator = new LongSequenceGenerator();
    protected final LongSequenceGenerator producerIdGenerator = new LongSequenceGenerator();
    protected final LongSequenceGenerator deliveryIdGenerator = new LongSequenceGenerator();
    protected final ActiveMQSessionExecutor executor;
    protected final AtomicBoolean started = new AtomicBoolean(false);
    protected final CopyOnWriteArrayList<ActiveMQMessageConsumer> consumers = new CopyOnWriteArrayList();
    protected final CopyOnWriteArrayList<ActiveMQMessageProducer> producers = new CopyOnWriteArrayList();
    protected boolean closed;
    private volatile boolean synchronizationRegistered;
    protected boolean asyncDispatch;
    protected boolean sessionAsyncDispatch;
    protected final boolean debug;
    protected final Object sendMutex = new Object();
    protected final Object redeliveryGuard = new Object();
    private final AtomicBoolean clearInProgress = new AtomicBoolean();
    private MessageListener messageListener;
    private final JMSSessionStatsImpl stats;
    private TransactionContext transactionContext;
    private DeliveryListener deliveryListener;
    private MessageTransformer transformer;
    private BlobTransferPolicy blobTransferPolicy;
    private long lastDeliveredSequenceId = -2L;
    final AtomicInteger clearRequestsCounter = new AtomicInteger(0);

    protected ActiveMQSession(ActiveMQConnection connection, SessionId sessionId, int acknowledgeMode, boolean asyncDispatch, boolean sessionAsyncDispatch) throws JMSException {
        this.debug = LOG.isDebugEnabled();
        this.connection = connection;
        this.acknowledgementMode = acknowledgeMode;
        this.asyncDispatch = asyncDispatch;
        this.sessionAsyncDispatch = sessionAsyncDispatch;
        this.info = new SessionInfo(connection.getConnectionInfo(), sessionId.getValue());
        this.setTransactionContext(new TransactionContext(connection));
        this.stats = new JMSSessionStatsImpl(this.producers, this.consumers);
        this.connection.asyncSendPacket(this.info);
        this.setTransformer(connection.getTransformer());
        this.setBlobTransferPolicy(connection.getBlobTransferPolicy());
        this.connectionExecutor = connection.getExecutor();
        this.executor = new ActiveMQSessionExecutor(this);
        connection.addSession(this);
        if (connection.isStarted()) {
            this.start();
        }
    }

    protected ActiveMQSession(ActiveMQConnection connection, SessionId sessionId, int acknowledgeMode, boolean asyncDispatch) throws JMSException {
        this(connection, sessionId, acknowledgeMode, asyncDispatch, true);
    }

    public void setTransactionContext(TransactionContext transactionContext) {
        this.transactionContext = transactionContext;
    }

    public TransactionContext getTransactionContext() {
        return this.transactionContext;
    }

    @Override
    public StatsImpl getStats() {
        return this.stats;
    }

    public JMSSessionStatsImpl getSessionStats() {
        return this.stats;
    }

    @Override
    public BytesMessage createBytesMessage() throws JMSException {
        ActiveMQBytesMessage message = new ActiveMQBytesMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public MapMessage createMapMessage() throws JMSException {
        ActiveMQMapMessage message = new ActiveMQMapMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public Message createMessage() throws JMSException {
        ActiveMQMessage message = new ActiveMQMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public ObjectMessage createObjectMessage() throws JMSException {
        ActiveMQObjectMessage message = new ActiveMQObjectMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
        ActiveMQObjectMessage message = new ActiveMQObjectMessage();
        this.configureMessage(message);
        message.setObject(object);
        return message;
    }

    @Override
    public StreamMessage createStreamMessage() throws JMSException {
        ActiveMQStreamMessage message = new ActiveMQStreamMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public TextMessage createTextMessage() throws JMSException {
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        this.configureMessage(message);
        return message;
    }

    @Override
    public TextMessage createTextMessage(String text) throws JMSException {
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(text);
        this.configureMessage(message);
        return message;
    }

    public BlobMessage createBlobMessage(URL url2) throws JMSException {
        return this.createBlobMessage(url2, false);
    }

    public BlobMessage createBlobMessage(URL url2, boolean deletedByBroker) throws JMSException {
        ActiveMQBlobMessage message = new ActiveMQBlobMessage();
        this.configureMessage(message);
        message.setURL(url2);
        message.setDeletedByBroker(deletedByBroker);
        message.setBlobDownloader(new BlobDownloader(this.getBlobTransferPolicy()));
        return message;
    }

    public BlobMessage createBlobMessage(File file) throws JMSException {
        ActiveMQBlobMessage message = new ActiveMQBlobMessage();
        this.configureMessage(message);
        message.setBlobUploader(new BlobUploader(this.getBlobTransferPolicy(), file));
        message.setBlobDownloader(new BlobDownloader(this.getBlobTransferPolicy()));
        message.setDeletedByBroker(true);
        message.setName(file.getName());
        return message;
    }

    public BlobMessage createBlobMessage(InputStream in) throws JMSException {
        ActiveMQBlobMessage message = new ActiveMQBlobMessage();
        this.configureMessage(message);
        message.setBlobUploader(new BlobUploader(this.getBlobTransferPolicy(), in));
        message.setBlobDownloader(new BlobDownloader(this.getBlobTransferPolicy()));
        message.setDeletedByBroker(true);
        return message;
    }

    @Override
    public boolean getTransacted() throws JMSException {
        this.checkClosed();
        return this.isTransacted();
    }

    @Override
    public int getAcknowledgeMode() throws JMSException {
        this.checkClosed();
        return this.acknowledgementMode;
    }

    @Override
    public void commit() throws JMSException {
        this.checkClosed();
        if (!this.getTransacted()) {
            throw new IllegalStateException("Not a transacted session");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(this.getSessionId() + " Transaction Commit :" + this.transactionContext.getTransactionId());
        }
        this.transactionContext.commit();
    }

    @Override
    public void rollback() throws JMSException {
        this.checkClosed();
        if (!this.getTransacted()) {
            throw new IllegalStateException("Not a transacted session");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(this.getSessionId() + " Transaction Rollback, txid:" + this.transactionContext.getTransactionId());
        }
        this.transactionContext.rollback();
    }

    @Override
    public void close() throws JMSException {
        if (!this.closed) {
            if (this.getTransactionContext().isInXATransaction()) {
                if (!this.synchronizationRegistered) {
                    this.synchronizationRegistered = true;
                    this.getTransactionContext().addSynchronization(new Synchronization(){

                        @Override
                        public void afterCommit() throws Exception {
                            ActiveMQSession.this.doClose();
                            ActiveMQSession.this.synchronizationRegistered = false;
                        }

                        @Override
                        public void afterRollback() throws Exception {
                            ActiveMQSession.this.doClose();
                            ActiveMQSession.this.synchronizationRegistered = false;
                        }
                    });
                }
            } else {
                this.doClose();
            }
        }
    }

    private void doClose() throws JMSException {
        this.dispose();
        RemoveInfo removeCommand = this.info.createRemoveCommand();
        removeCommand.setLastDeliveredSequenceId(this.lastDeliveredSequenceId);
        this.connection.asyncSendPacket(removeCommand);
    }

    void clearMessagesInProgress(AtomicInteger transportInterruptionProcessingComplete) {
        this.clearRequestsCounter.incrementAndGet();
        this.executor.clearMessagesInProgress();
        if (this.consumers.isEmpty()) {
            return;
        }
        if (this.clearInProgress.compareAndSet(false, true)) {
            for (final ActiveMQMessageConsumer consumer : this.consumers) {
                consumer.inProgressClearRequired();
                transportInterruptionProcessingComplete.incrementAndGet();
                try {
                    this.connection.getScheduler().executeAfterDelay(new Runnable(){

                        @Override
                        public void run() {
                            consumer.clearMessagesInProgress();
                        }
                    }, 0L);
                }
                catch (JMSException e) {
                    this.connection.onClientInternalException(e);
                }
            }
            try {
                this.connection.getScheduler().executeAfterDelay(new Runnable(){

                    @Override
                    public void run() {
                        ActiveMQSession.this.clearInProgress.set(false);
                    }
                }, 0L);
            }
            catch (JMSException e) {
                this.connection.onClientInternalException(e);
            }
        }
    }

    void deliverAcks() {
        for (ActiveMQMessageConsumer consumer : this.consumers) {
            consumer.deliverAcks();
        }
    }

    public synchronized void dispose() throws JMSException {
        if (!this.closed) {
            try {
                this.executor.close();
                for (ActiveMQMessageConsumer consumer : this.consumers) {
                    consumer.setFailureError(this.connection.getFirstFailureError());
                    consumer.dispose();
                    this.lastDeliveredSequenceId = Math.max(this.lastDeliveredSequenceId, consumer.getLastDeliveredSequenceId());
                }
                this.consumers.clear();
                for (ActiveMQMessageProducer producer : this.producers) {
                    producer.dispose();
                }
                this.producers.clear();
                try {
                    if (this.getTransactionContext().isInLocalTransaction()) {
                        this.rollback();
                    }
                }
                catch (JMSException jMSException) {
                    // empty catch block
                }
            }
            finally {
                this.connection.removeSession(this);
                this.transactionContext = null;
                this.closed = true;
            }
        }
    }

    protected void configureMessage(ActiveMQMessage message) throws IllegalStateException {
        this.checkClosed();
        message.setConnection(this.connection);
    }

    protected void checkClosed() throws IllegalStateException {
        if (this.closed) {
            throw new IllegalStateException("The Session is closed");
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public void recover() throws JMSException {
        this.checkClosed();
        if (this.getTransacted()) {
            throw new IllegalStateException("This session is transacted");
        }
        for (ActiveMQMessageConsumer c : this.consumers) {
            c.rollback();
        }
    }

    @Override
    public MessageListener getMessageListener() throws JMSException {
        this.checkClosed();
        return this.messageListener;
    }

    @Override
    public void setMessageListener(MessageListener listener) throws JMSException {
        if (listener != null) {
            this.checkClosed();
        }
        this.messageListener = listener;
        if (listener != null) {
            this.executor.setDispatchedBySessionPool(true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        MessageDispatch messageDispatch;
        while ((messageDispatch = this.executor.dequeueNoWait()) != null) {
            final MessageDispatch md = messageDispatch;
            ActiveMQMessage message = (ActiveMQMessage)md.getMessage();
            MessageAck earlyAck = null;
            if (message.isExpired()) {
                earlyAck = new MessageAck(md, 6, 1);
                earlyAck.setFirstMessageId(message.getMessageId());
            } else if (this.connection.isDuplicate(this, message)) {
                LOG.debug("{} got duplicate: {}", (Object)this, (Object)message.getMessageId());
                earlyAck = new MessageAck(md, 1, 1);
                earlyAck.setFirstMessageId(md.getMessage().getMessageId());
                earlyAck.setPoisonCause(new Throwable("Duplicate delivery to " + this));
            }
            if (earlyAck != null) {
                try {
                    try {
                        this.asyncSendPacket(earlyAck);
                    }
                    catch (Throwable t2) {
                        LOG.error("error dispatching ack: {} ", (Object)earlyAck, (Object)t2);
                        this.connection.onClientInternalException(t2);
                    }
                }
                catch (Throwable throwable) {}
                continue;
            }
            if (this.isClientAcknowledge() || this.isIndividualAcknowledge()) {
                message.setAcknowledgeCallback(new Callback(){

                    @Override
                    public void execute() throws Exception {
                    }
                });
            }
            if (this.deliveryListener != null) {
                this.deliveryListener.beforeDelivery(this, message);
            }
            md.setDeliverySequenceId(this.getNextDeliveryId());
            this.lastDeliveredSequenceId = message.getMessageId().getBrokerSequenceId();
            final MessageAck ack = new MessageAck(md, 2, 1);
            final AtomicBoolean afterDeliveryError = new AtomicBoolean(false);
            Object object = this.redeliveryGuard;
            synchronized (object) {
                try {
                    ack.setFirstMessageId(md.getMessage().getMessageId());
                    this.doStartTransaction();
                    ack.setTransactionId(this.getTransactionContext().getTransactionId());
                    if (ack.getTransactionId() != null) {
                        this.getTransactionContext().addSynchronization(new Synchronization(){
                            final int clearRequestCount;
                            {
                                this.clearRequestCount = ActiveMQSession.this.clearRequestsCounter.get() == Integer.MAX_VALUE ? ActiveMQSession.this.clearRequestsCounter.incrementAndGet() : ActiveMQSession.this.clearRequestsCounter.get();
                            }

                            @Override
                            public void beforeEnd() throws Exception {
                                if (ack.getTransactionId().isXATransaction() && !ActiveMQSession.this.connection.hasDispatcher(ack.getConsumerId())) {
                                    LOG.debug("forcing rollback - {} consumer no longer active on {}", (Object)ack, (Object)ActiveMQSession.this.connection);
                                    throw new TransactionRolledBackException("consumer " + ack.getConsumerId() + " no longer active on " + ActiveMQSession.this.connection);
                                }
                                LOG.trace("beforeEnd ack {}", (Object)ack);
                                ActiveMQSession.this.sendAck(ack);
                            }

                            @Override
                            public void afterRollback() throws Exception {
                                if (LOG.isTraceEnabled()) {
                                    LOG.trace("rollback {}", (Object)ack, (Object)new Throwable("here"));
                                }
                                ActiveMQSession.this.connection.rollbackDuplicate(ActiveMQSession.this, md.getMessage());
                                if (ActiveMQSession.this.clearRequestsCounter.get() > this.clearRequestCount) {
                                    LOG.debug("No redelivery of {} on rollback of {} due to failover of {}", md, ack.getTransactionId(), ActiveMQSession.this.connection.getTransport());
                                    return;
                                }
                                if (ack.getTransactionId().isXATransaction() && !ActiveMQSession.this.connection.hasDispatcher(ack.getConsumerId())) {
                                    LOG.debug("No local redelivery of {} on rollback of {} because consumer is no longer active on {}", md, ack.getTransactionId(), ActiveMQSession.this.connection.getTransport());
                                    return;
                                }
                                RedeliveryPolicy redeliveryPolicy = ActiveMQSession.this.connection.getRedeliveryPolicy();
                                int redeliveryCounter = md.getMessage().getRedeliveryCounter();
                                if (redeliveryPolicy.getMaximumRedeliveries() != -1 && redeliveryCounter >= redeliveryPolicy.getMaximumRedeliveries()) {
                                    MessageAck ack2 = new MessageAck(md, 1, 1);
                                    ack2.setFirstMessageId(md.getMessage().getMessageId());
                                    ack2.setPoisonCause(new Throwable("Exceeded ra redelivery policy limit:" + redeliveryPolicy));
                                    ActiveMQSession.this.asyncSendPacket(ack2);
                                } else {
                                    MessageAck ack3 = new MessageAck(md, 3, 1);
                                    ack3.setFirstMessageId(md.getMessage().getMessageId());
                                    ActiveMQSession.this.asyncSendPacket(ack3);
                                    long redeliveryDelay = redeliveryPolicy.getInitialRedeliveryDelay();
                                    for (int i = 0; i < redeliveryCounter; ++i) {
                                        redeliveryDelay = redeliveryPolicy.getNextRedeliveryDelay(redeliveryDelay);
                                    }
                                    if (!ActiveMQSession.this.connection.isNonBlockingRedelivery()) {
                                        LOG.debug("Blocking session until re-delivery...");
                                        ActiveMQSession.this.executor.stop();
                                    }
                                    ActiveMQSession.this.connection.getScheduler().executeAfterDelay(new Runnable(){

                                        /*
                                         * WARNING - Removed try catching itself - possible behaviour change.
                                         */
                                        @Override
                                        public void run() {
                                            Object object = ActiveMQSession.this.redeliveryGuard;
                                            synchronized (object) {
                                                if (ActiveMQSession.this.connection.isNonBlockingRedelivery()) {
                                                    ((ActiveMQDispatcher)md.getConsumer()).dispatch(md);
                                                } else if (afterDeliveryError.get()) {
                                                    ((ActiveMQDispatcher)md.getConsumer()).dispatch(md);
                                                } else {
                                                    ActiveMQSession.this.executor.executeFirst(md);
                                                    ActiveMQSession.this.executor.start();
                                                }
                                            }
                                        }
                                    }, redeliveryDelay);
                                }
                                md.getMessage().onMessageRolledBack();
                            }
                        });
                    }
                    LOG.trace("{} onMessage({})", (Object)this, (Object)message.getMessageId());
                    this.messageListener.onMessage(message);
                }
                catch (Throwable e) {
                    LOG.error("error dispatching message: ", e);
                    if (this.getTransactionContext().isInXATransaction()) {
                        LOG.debug("Marking transaction: {} rollbackOnly", (Object)this.getTransactionContext());
                        this.getTransactionContext().setRollbackOnly(true);
                    }
                    this.connection.onClientInternalException(e);
                }
                finally {
                    if (ack.getTransactionId() == null) {
                        try {
                            this.asyncSendPacket(ack);
                        }
                        catch (Throwable e) {
                            this.connection.onClientInternalException(e);
                        }
                    }
                }
                if (this.deliveryListener != null) {
                    try {
                        this.deliveryListener.afterDelivery(this, message);
                    }
                    catch (Throwable t3) {
                        LOG.debug("Unable to call after delivery", t3);
                        afterDeliveryError.set(true);
                        throw new RuntimeException(t3);
                    }
                }
            }
            try {
                this.executor.waitForQueueRestart();
            }
            catch (InterruptedException ex) {
                this.connection.onClientInternalException(ex);
            }
        }
    }

    @Override
    public MessageProducer createProducer(Destination destination) throws JMSException {
        this.checkClosed();
        if (destination instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)destination;
            return customDestination.createProducer(this);
        }
        int timeSendOut = this.connection.getSendTimeout();
        return new ActiveMQMessageProducer(this, this.getNextProducerId(), ActiveMQMessageTransformation.transformDestination(destination), timeSendOut);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        return this.createConsumer(destination, (String)null);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
        return this.createConsumer(destination, messageSelector, false);
    }

    public MessageConsumer createConsumer(Destination destination, MessageListener messageListener) throws JMSException {
        return this.createConsumer(destination, null, messageListener);
    }

    public MessageConsumer createConsumer(Destination destination, String messageSelector, MessageListener messageListener) throws JMSException {
        return this.createConsumer(destination, messageSelector, false, messageListener);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) throws JMSException {
        return this.createConsumer(destination, messageSelector, noLocal, null);
    }

    public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal, MessageListener messageListener) throws JMSException {
        this.checkClosed();
        if (destination instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)destination;
            return customDestination.createConsumer(this, messageSelector, noLocal);
        }
        ActiveMQPrefetchPolicy prefetchPolicy = this.connection.getPrefetchPolicy();
        int prefetch = 0;
        prefetch = destination instanceof Topic ? prefetchPolicy.getTopicPrefetch() : prefetchPolicy.getQueuePrefetch();
        ActiveMQDestination activemqDestination = ActiveMQMessageTransformation.transformDestination(destination);
        return new ActiveMQMessageConsumer(this, this.getNextConsumerId(), activemqDestination, null, messageSelector, prefetch, prefetchPolicy.getMaximumPendingMessageLimit(), noLocal, false, this.isAsyncDispatch(), messageListener);
    }

    @Override
    public Queue createQueue(String queueName) throws JMSException {
        this.checkClosed();
        if (queueName.startsWith("ID:")) {
            return new ActiveMQTempQueue(queueName);
        }
        return new ActiveMQQueue(queueName);
    }

    @Override
    public Topic createTopic(String topicName) throws JMSException {
        this.checkClosed();
        if (topicName.startsWith("ID:")) {
            return new ActiveMQTempTopic(topicName);
        }
        return new ActiveMQTopic(topicName);
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
        this.checkClosed();
        return this.createDurableSubscriber(topic, name, null, false);
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
        this.checkClosed();
        if (topic == null) {
            throw new InvalidDestinationException("Topic cannot be null");
        }
        if (topic instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)((Object)topic);
            return customDestination.createDurableSubscriber(this, name, messageSelector, noLocal);
        }
        this.connection.checkClientIDWasManuallySpecified();
        ActiveMQPrefetchPolicy prefetchPolicy = this.connection.getPrefetchPolicy();
        int prefetch = this.isAutoAcknowledge() && this.connection.isOptimizedMessageDispatch() ? prefetchPolicy.getOptimizeDurableTopicPrefetch() : prefetchPolicy.getDurableTopicPrefetch();
        int maxPrendingLimit = prefetchPolicy.getMaximumPendingMessageLimit();
        return new ActiveMQTopicSubscriber(this, this.getNextConsumerId(), ActiveMQMessageTransformation.transformDestination(topic), name, messageSelector, prefetch, maxPrendingLimit, noLocal, false, this.asyncDispatch);
    }

    @Override
    public QueueBrowser createBrowser(Queue queue) throws JMSException {
        this.checkClosed();
        return this.createBrowser(queue, null);
    }

    @Override
    public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
        this.checkClosed();
        return new ActiveMQQueueBrowser(this, this.getNextConsumerId(), ActiveMQMessageTransformation.transformDestination(queue), messageSelector, this.asyncDispatch);
    }

    @Override
    public TemporaryQueue createTemporaryQueue() throws JMSException {
        this.checkClosed();
        return (TemporaryQueue)((Object)this.connection.createTempDestination(false));
    }

    @Override
    public TemporaryTopic createTemporaryTopic() throws JMSException {
        this.checkClosed();
        return (TemporaryTopic)((Object)this.connection.createTempDestination(true));
    }

    @Override
    public QueueReceiver createReceiver(Queue queue) throws JMSException {
        this.checkClosed();
        return this.createReceiver(queue, null);
    }

    @Override
    public QueueReceiver createReceiver(Queue queue, String messageSelector) throws JMSException {
        this.checkClosed();
        if (queue instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)((Object)queue);
            return customDestination.createReceiver(this, messageSelector);
        }
        ActiveMQPrefetchPolicy prefetchPolicy = this.connection.getPrefetchPolicy();
        return new ActiveMQQueueReceiver(this, this.getNextConsumerId(), ActiveMQMessageTransformation.transformDestination(queue), messageSelector, prefetchPolicy.getQueuePrefetch(), prefetchPolicy.getMaximumPendingMessageLimit(), this.asyncDispatch);
    }

    @Override
    public QueueSender createSender(Queue queue) throws JMSException {
        this.checkClosed();
        if (queue instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)((Object)queue);
            return customDestination.createSender(this);
        }
        int timeSendOut = this.connection.getSendTimeout();
        return new ActiveMQQueueSender(this, ActiveMQMessageTransformation.transformDestination(queue), timeSendOut);
    }

    @Override
    public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
        this.checkClosed();
        return this.createSubscriber(topic, null, false);
    }

    @Override
    public TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
        this.checkClosed();
        if (topic instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)((Object)topic);
            return customDestination.createSubscriber(this, messageSelector, noLocal);
        }
        ActiveMQPrefetchPolicy prefetchPolicy = this.connection.getPrefetchPolicy();
        return new ActiveMQTopicSubscriber(this, this.getNextConsumerId(), ActiveMQMessageTransformation.transformDestination(topic), null, messageSelector, prefetchPolicy.getTopicPrefetch(), prefetchPolicy.getMaximumPendingMessageLimit(), noLocal, false, this.asyncDispatch);
    }

    @Override
    public TopicPublisher createPublisher(Topic topic) throws JMSException {
        this.checkClosed();
        if (topic instanceof CustomDestination) {
            CustomDestination customDestination = (CustomDestination)((Object)topic);
            return customDestination.createPublisher(this);
        }
        int timeSendOut = this.connection.getSendTimeout();
        return new ActiveMQTopicPublisher(this, ActiveMQMessageTransformation.transformDestination(topic), timeSendOut);
    }

    @Override
    public void unsubscribe(String name) throws JMSException {
        this.checkClosed();
        this.connection.unsubscribe(name);
    }

    @Override
    public void dispatch(MessageDispatch messageDispatch) {
        try {
            this.executor.execute(messageDispatch);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.connection.onClientInternalException(e);
        }
    }

    public void acknowledge() throws JMSException {
        for (ActiveMQMessageConsumer c : this.consumers) {
            c.acknowledge();
        }
    }

    protected void addConsumer(ActiveMQMessageConsumer consumer) throws JMSException {
        this.consumers.add(consumer);
        if (consumer.isDurableSubscriber()) {
            this.stats.onCreateDurableSubscriber();
        }
        this.connection.addDispatcher(consumer.getConsumerId(), this);
    }

    protected void removeConsumer(ActiveMQMessageConsumer consumer) {
        this.connection.removeDispatcher(consumer.getConsumerId());
        if (consumer.isDurableSubscriber()) {
            this.stats.onRemoveDurableSubscriber();
        }
        this.consumers.remove(consumer);
        this.connection.removeDispatcher(consumer);
    }

    protected void addProducer(ActiveMQMessageProducer producer) throws JMSException {
        this.producers.add(producer);
        this.connection.addProducer(producer.getProducerInfo().getProducerId(), producer);
    }

    protected void removeProducer(ActiveMQMessageProducer producer) {
        this.connection.removeProducer(producer.getProducerInfo().getProducerId());
        this.producers.remove(producer);
    }

    protected void start() throws JMSException {
        this.started.set(true);
        for (ActiveMQMessageConsumer c : this.consumers) {
            c.start();
        }
        this.executor.start();
    }

    protected void stop() throws JMSException {
        for (ActiveMQMessageConsumer c : this.consumers) {
            c.stop();
        }
        this.started.set(false);
        this.executor.stop();
    }

    protected SessionId getSessionId() {
        return this.info.getSessionId();
    }

    protected ConsumerId getNextConsumerId() {
        return new ConsumerId(this.info.getSessionId(), this.consumerIdGenerator.getNextSequenceId());
    }

    protected ProducerId getNextProducerId() {
        return new ProducerId(this.info.getSessionId(), this.producerIdGenerator.getNextSequenceId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void send(ActiveMQMessageProducer producer, ActiveMQDestination destination, Message message, int deliveryMode, int priority, long timeToLive, MemoryUsage producerWindow, int sendTimeout, AsyncCallback onComplete) throws JMSException {
        this.checkClosed();
        if (destination.isTemporary() && this.connection.isDeleted(destination)) {
            throw new InvalidDestinationException("Cannot publish to a deleted Destination: " + destination);
        }
        Object object = this.sendMutex;
        synchronized (object) {
            this.doStartTransaction();
            TransactionId txid = this.transactionContext.getTransactionId();
            long sequenceNumber = producer.getMessageSequence();
            message.setJMSDeliveryMode(deliveryMode);
            long expiration = 0L;
            if (!producer.getDisableMessageTimestamp()) {
                long timeStamp = System.currentTimeMillis();
                message.setJMSTimestamp(timeStamp);
                if (timeToLive > 0L) {
                    expiration = timeToLive + timeStamp;
                }
            }
            message.setJMSExpiration(expiration);
            message.setJMSPriority(priority);
            message.setJMSRedelivered(false);
            ActiveMQMessage msg = ActiveMQMessageTransformation.transformMessage(message, this.connection);
            msg.setDestination(destination);
            msg.setMessageId(new MessageId(producer.getProducerInfo().getProducerId(), sequenceNumber));
            if (msg != message) {
                message.setJMSMessageID(msg.getMessageId().toString());
                message.setJMSDestination(destination);
            }
            msg.setBrokerPath(null);
            msg.setTransactionId(txid);
            if (this.connection.isCopyMessageOnSend()) {
                msg = (ActiveMQMessage)msg.copy();
            }
            msg.setConnection(this.connection);
            msg.onSend();
            msg.setProducerId(msg.getMessageId().getProducerId());
            if (LOG.isTraceEnabled()) {
                LOG.trace(this.getSessionId() + " sending message: " + msg);
            }
            if (!(onComplete != null || sendTimeout > 0 || msg.isResponseRequired() || this.connection.isAlwaysSyncSend() || msg.isPersistent() && !this.connection.isUseAsyncSend() && txid == null)) {
                this.connection.asyncSendPacket(msg);
                if (producerWindow != null) {
                    int size = msg.getSize();
                    producerWindow.increaseUsage(size);
                }
            } else if (sendTimeout > 0 && onComplete == null) {
                this.connection.syncSendPacket((Command)msg, sendTimeout);
            } else {
                this.connection.syncSendPacket((Command)msg, onComplete);
            }
        }
    }

    protected void doStartTransaction() throws JMSException {
        if (this.getTransacted() && !this.transactionContext.isInXATransaction()) {
            this.transactionContext.begin();
        }
    }

    public boolean hasUncomsumedMessages() {
        return this.executor.hasUncomsumedMessages();
    }

    public boolean isTransacted() {
        return this.acknowledgementMode == 0 || this.transactionContext.isInXATransaction();
    }

    protected boolean isClientAcknowledge() {
        return this.acknowledgementMode == 2;
    }

    public boolean isAutoAcknowledge() {
        return this.acknowledgementMode == 1;
    }

    public boolean isDupsOkAcknowledge() {
        return this.acknowledgementMode == 3;
    }

    public boolean isIndividualAcknowledge() {
        return this.acknowledgementMode == 4;
    }

    public DeliveryListener getDeliveryListener() {
        return this.deliveryListener;
    }

    public void setDeliveryListener(DeliveryListener deliveryListener) {
        this.deliveryListener = deliveryListener;
    }

    protected SessionInfo getSessionInfo() throws JMSException {
        SessionInfo info = new SessionInfo(this.connection.getConnectionInfo(), this.getSessionId().getValue());
        return info;
    }

    public void asyncSendPacket(Command command) throws JMSException {
        this.connection.asyncSendPacket(command);
    }

    public Response syncSendPacket(Command command) throws JMSException {
        return this.connection.syncSendPacket(command);
    }

    public long getNextDeliveryId() {
        return this.deliveryIdGenerator.getNextSequenceId();
    }

    public void redispatch(ActiveMQDispatcher dispatcher, MessageDispatchChannel unconsumedMessages) throws JMSException {
        List<MessageDispatch> c = unconsumedMessages.removeAll();
        for (MessageDispatch md : c) {
            this.connection.rollbackDuplicate(dispatcher, md.getMessage());
        }
        Collections.reverse(c);
        for (MessageDispatch md : c) {
            this.executor.executeFirst(md);
        }
    }

    public boolean isRunning() {
        return this.started.get();
    }

    public boolean isAsyncDispatch() {
        return this.asyncDispatch;
    }

    public void setAsyncDispatch(boolean asyncDispatch) {
        this.asyncDispatch = asyncDispatch;
    }

    public boolean isSessionAsyncDispatch() {
        return this.sessionAsyncDispatch;
    }

    public void setSessionAsyncDispatch(boolean sessionAsyncDispatch) {
        this.sessionAsyncDispatch = sessionAsyncDispatch;
    }

    public MessageTransformer getTransformer() {
        return this.transformer;
    }

    public ActiveMQConnection getConnection() {
        return this.connection;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    public BlobTransferPolicy getBlobTransferPolicy() {
        return this.blobTransferPolicy;
    }

    public void setBlobTransferPolicy(BlobTransferPolicy blobTransferPolicy) {
        this.blobTransferPolicy = blobTransferPolicy;
    }

    public List<MessageDispatch> getUnconsumedMessages() {
        return this.executor.getUnconsumedMessages();
    }

    public String toString() {
        return "ActiveMQSession {id=" + this.info.getSessionId() + ",started=" + this.started.get() + "} " + this.sendMutex;
    }

    public void checkMessageListener() throws JMSException {
        if (this.messageListener != null) {
            throw new IllegalStateException("Cannot synchronously receive a message when a MessageListener is set");
        }
        for (ActiveMQMessageConsumer consumer : this.consumers) {
            if (!consumer.hasMessageListener()) continue;
            throw new IllegalStateException("Cannot synchronously receive a message when a MessageListener is set");
        }
    }

    protected void setOptimizeAcknowledge(boolean value) {
        for (ActiveMQMessageConsumer c : this.consumers) {
            c.setOptimizeAcknowledge(value);
        }
    }

    protected void setPrefetchSize(ConsumerId id, int prefetch) {
        for (ActiveMQMessageConsumer c : this.consumers) {
            if (!c.getConsumerId().equals(id)) continue;
            c.setPrefetchSize(prefetch);
            break;
        }
    }

    protected void close(ConsumerId id) {
        for (ActiveMQMessageConsumer c : this.consumers) {
            if (!c.getConsumerId().equals(id)) continue;
            try {
                c.close();
            }
            catch (JMSException e) {
                LOG.warn("Exception closing consumer", e);
            }
            LOG.warn("Closed consumer on Command, " + id);
            break;
        }
    }

    public boolean isInUse(ActiveMQTempDestination destination) {
        for (ActiveMQMessageConsumer c : this.consumers) {
            if (!c.isInUse(destination)) continue;
            return true;
        }
        return false;
    }

    public long getLastDeliveredSequenceId() {
        return this.lastDeliveredSequenceId;
    }

    protected void sendAck(MessageAck ack) throws JMSException {
        this.sendAck(ack, false);
    }

    protected void sendAck(MessageAck ack, boolean lazy) throws JMSException {
        if (lazy || this.connection.isSendAcksAsync() || this.getTransacted()) {
            this.asyncSendPacket(ack);
        } else {
            this.syncSendPacket(ack);
        }
    }

    protected Scheduler getScheduler() throws JMSException {
        return this.connection.getScheduler();
    }

    protected ThreadPoolExecutor getConnectionExecutor() {
        return this.connectionExecutor;
    }

    public static interface DeliveryListener {
        public void beforeDelivery(ActiveMQSession var1, Message var2);

        public void afterDelivery(ActiveMQSession var1, Message var2);
    }
}

