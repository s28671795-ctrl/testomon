/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TransactionRolledBackException;
import org.apache.activemq.ActiveMQDispatcher;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.ConfigurationException;
import org.apache.activemq.FifoMessageDispatchChannel;
import org.apache.activemq.MessageAvailableConsumer;
import org.apache.activemq.MessageAvailableListener;
import org.apache.activemq.MessageDispatchChannel;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.SimplePriorityMessageDispatchChannel;
import org.apache.activemq.blob.BlobDownloader;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.management.JMSConsumerStatsImpl;
import org.apache.activemq.management.StatsCapable;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.selector.SelectorParser;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.util.Callback;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQMessageConsumer
implements MessageAvailableConsumer,
StatsCapable,
ActiveMQDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQMessageConsumer.class);
    protected final ActiveMQSession session;
    protected final ConsumerInfo info;
    protected final MessageDispatchChannel unconsumedMessages;
    protected final LinkedList<MessageDispatch> deliveredMessages = new LinkedList();
    private PreviouslyDeliveredMap<MessageId, Boolean> previouslyDeliveredMessages;
    private int deliveredCounter;
    private int additionalWindowSize;
    private long redeliveryDelay;
    private int ackCounter;
    private int dispatchedCount;
    private final AtomicReference<MessageListener> messageListener = new AtomicReference();
    private final JMSConsumerStatsImpl stats;
    private final String selector;
    private boolean synchronizationRegistered;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private MessageAvailableListener availableListener;
    private RedeliveryPolicy redeliveryPolicy;
    private boolean optimizeAcknowledge;
    private final AtomicBoolean deliveryingAcknowledgements = new AtomicBoolean();
    private ExecutorService executorService;
    private MessageTransformer transformer;
    private boolean clearDeliveredList;
    AtomicInteger inProgressClearRequiredFlag = new AtomicInteger(0);
    private MessageAck pendingAck;
    private long lastDeliveredSequenceId = -1L;
    private IOException failureError;
    private long optimizeAckTimestamp = System.currentTimeMillis();
    private long optimizeAcknowledgeTimeOut = 0L;
    private long optimizedAckScheduledAckInterval = 0L;
    private Runnable optimizedAckTask;
    private long failoverRedeliveryWaitPeriod = 0L;
    private boolean transactedIndividualAck = false;
    private boolean nonBlockingRedelivery = false;
    private boolean consumerExpiryCheckEnabled = true;

    public ActiveMQMessageConsumer(ActiveMQSession session, ConsumerId consumerId, ActiveMQDestination dest, String name, String selector, int prefetch, int maximumPendingMessageCount, boolean noLocal, boolean browser, boolean dispatchAsync, MessageListener messageListener) throws JMSException {
        if (dest == null) {
            throw new InvalidDestinationException("Don't understand null destinations");
        }
        if (dest.getPhysicalName() == null) {
            throw new InvalidDestinationException("The destination object was not given a physical name.");
        }
        if (dest.isTemporary()) {
            String physicalName = dest.getPhysicalName();
            if (physicalName == null) {
                throw new IllegalArgumentException("Physical name of Destination should be valid: " + dest);
            }
            String connectionID = session.connection.getConnectionInfo().getConnectionId().getValue();
            if (physicalName.indexOf(connectionID) < 0) {
                throw new InvalidDestinationException("Cannot use a Temporary destination from another Connection");
            }
            if (session.connection.isDeleted(dest)) {
                throw new InvalidDestinationException("Cannot use a Temporary destination that has been deleted");
            }
            if (prefetch < 0) {
                throw new JMSException("Cannot have a prefetch size less than zero");
            }
        }
        this.unconsumedMessages = session.connection.isMessagePrioritySupported() ? new SimplePriorityMessageDispatchChannel() : new FifoMessageDispatchChannel();
        this.session = session;
        this.redeliveryPolicy = session.connection.getRedeliveryPolicyMap().getEntryFor(dest);
        if (this.redeliveryPolicy == null) {
            this.redeliveryPolicy = new RedeliveryPolicy();
        }
        this.setTransformer(session.getTransformer());
        this.info = new ConsumerInfo(consumerId);
        this.info.setExclusive(this.session.connection.isExclusiveConsumer());
        this.info.setClientId(this.session.connection.getClientID());
        this.info.setSubscriptionName(name);
        this.info.setPrefetchSize(prefetch);
        this.info.setCurrentPrefetchSize(prefetch);
        this.info.setMaximumPendingMessageLimit(maximumPendingMessageCount);
        this.info.setNoLocal(noLocal);
        this.info.setDispatchAsync(dispatchAsync);
        this.info.setRetroactive(this.session.connection.isUseRetroactiveConsumer());
        this.info.setSelector(null);
        if (dest.getOptions() != null) {
            Map<String, Object> options = IntrospectionSupport.extractProperties(new HashMap<String, String>(dest.getOptions()), "consumer.");
            IntrospectionSupport.setProperties(this.info, options);
            if (options.size() > 0) {
                String msg = "There are " + options.size() + " consumer options that couldn't be set on the consumer. Check the options are spelled correctly. Unknown parameters=[" + options + "]. This consumer cannot be started.";
                LOG.warn(msg);
                throw new ConfigurationException(msg);
            }
        }
        this.info.setDestination(dest);
        this.info.setBrowser(browser);
        if (selector != null && selector.trim().length() != 0) {
            SelectorParser.parse(selector);
            this.info.setSelector(selector);
            this.selector = selector;
        } else if (this.info.getSelector() != null) {
            SelectorParser.parse(this.info.getSelector());
            this.selector = this.info.getSelector();
        } else {
            this.selector = null;
        }
        this.stats = new JMSConsumerStatsImpl(session.getSessionStats(), dest);
        boolean bl = this.optimizeAcknowledge = session.connection.isOptimizeAcknowledge() && session.isAutoAcknowledge() && !this.info.isBrowser();
        if (this.optimizeAcknowledge) {
            this.optimizeAcknowledgeTimeOut = session.connection.getOptimizeAcknowledgeTimeOut();
            this.setOptimizedAckScheduledAckInterval(session.connection.getOptimizedAckScheduledAckInterval());
        }
        this.info.setOptimizedAcknowledge(this.optimizeAcknowledge);
        this.failoverRedeliveryWaitPeriod = session.connection.getConsumerFailoverRedeliveryWaitPeriod();
        this.nonBlockingRedelivery = session.connection.isNonBlockingRedelivery();
        this.transactedIndividualAck = session.connection.isTransactedIndividualAck() || this.nonBlockingRedelivery || session.connection.isMessagePrioritySupported();
        this.consumerExpiryCheckEnabled = session.connection.isConsumerExpiryCheckEnabled();
        if (messageListener != null) {
            this.setMessageListener(messageListener);
        }
        try {
            this.session.addConsumer(this);
            this.session.syncSendPacket(this.info);
        }
        catch (JMSException e) {
            this.session.removeConsumer(this);
            throw e;
        }
        if (session.connection.isStarted()) {
            this.start();
        }
    }

    private boolean isAutoAcknowledgeEach() {
        return this.session.isAutoAcknowledge() || this.session.isDupsOkAcknowledge() && this.getDestination().isQueue();
    }

    private boolean isAutoAcknowledgeBatch() {
        return this.session.isDupsOkAcknowledge() && !this.getDestination().isQueue();
    }

    @Override
    public StatsImpl getStats() {
        return this.stats;
    }

    public JMSConsumerStatsImpl getConsumerStats() {
        return this.stats;
    }

    public RedeliveryPolicy getRedeliveryPolicy() {
        return this.redeliveryPolicy;
    }

    public void setRedeliveryPolicy(RedeliveryPolicy redeliveryPolicy) {
        this.redeliveryPolicy = redeliveryPolicy;
    }

    public MessageTransformer getTransformer() {
        return this.transformer;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    public ConsumerId getConsumerId() {
        return this.info.getConsumerId();
    }

    public String getConsumerName() {
        return this.info.getSubscriptionName();
    }

    protected boolean isNoLocal() {
        return this.info.isNoLocal();
    }

    protected boolean isBrowser() {
        return this.info.isBrowser();
    }

    protected ActiveMQDestination getDestination() {
        return this.info.getDestination();
    }

    public int getPrefetchNumber() {
        return this.info.getPrefetchSize();
    }

    public boolean isDurableSubscriber() {
        return this.info.getSubscriptionName() != null && this.info.getDestination().isTopic();
    }

    @Override
    public String getMessageSelector() throws JMSException {
        this.checkClosed();
        return this.selector;
    }

    @Override
    public MessageListener getMessageListener() throws JMSException {
        this.checkClosed();
        return this.messageListener.get();
    }

    @Override
    public void setMessageListener(MessageListener listener) throws JMSException {
        this.checkClosed();
        if (this.info.getPrefetchSize() == 0) {
            throw new JMSException("Illegal prefetch size of zero. This setting is not supported for asynchronous consumers please set a value of at least 1");
        }
        if (listener != null) {
            boolean wasRunning = this.session.isRunning();
            if (wasRunning) {
                this.session.stop();
            }
            this.messageListener.set(listener);
            this.session.redispatch(this, this.unconsumedMessages);
            if (wasRunning) {
                this.session.start();
            }
        } else {
            this.messageListener.set(null);
        }
    }

    @Override
    public MessageAvailableListener getAvailableListener() {
        return this.availableListener;
    }

    @Override
    public void setAvailableListener(MessageAvailableListener availableListener) {
        this.availableListener = availableListener;
    }

    private MessageDispatch dequeue(long timeout2) throws JMSException {
        try {
            MessageDispatch md;
            long deadline = 0L;
            if (timeout2 > 0L) {
                deadline = System.currentTimeMillis() + timeout2;
            }
            while (true) {
                if ((md = this.unconsumedMessages.dequeue(timeout2)) == null) {
                    if (timeout2 > 0L && !this.unconsumedMessages.isClosed()) {
                        timeout2 = Math.max(deadline - System.currentTimeMillis(), 0L);
                        continue;
                    }
                    if (this.failureError != null) {
                        throw JMSExceptionSupport.create(this.failureError);
                    }
                    return null;
                }
                if (md.getMessage() == null) {
                    return null;
                }
                if (this.consumeExpiredMessage(md)) {
                    LOG.debug("{} received expired message: {}", (Object)this.getConsumerId(), (Object)md);
                    this.beforeMessageIsConsumed(md);
                    this.afterMessageIsConsumed(md, true);
                    if (timeout2 > 0L) {
                        timeout2 = Math.max(deadline - System.currentTimeMillis(), 0L);
                    }
                    this.sendPullCommand(timeout2);
                    continue;
                }
                if (!this.redeliveryExceeded(md)) break;
                LOG.debug("{} received with excessive redelivered: {}", (Object)this.getConsumerId(), (Object)md);
                this.posionAck(md, "Dispatch[" + md.getRedeliveryCounter() + "] to " + this.getConsumerId() + " exceeds redelivery policy limit:" + this.redeliveryPolicy);
                if (timeout2 > 0L) {
                    timeout2 = Math.max(deadline - System.currentTimeMillis(), 0L);
                }
                this.sendPullCommand(timeout2);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace(this.getConsumerId() + " received message: " + md);
            }
            return md;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw JMSExceptionSupport.create(e);
        }
    }

    private boolean consumeExpiredMessage(MessageDispatch dispatch) {
        return this.isConsumerExpiryCheckEnabled() && dispatch.getMessage().isExpired();
    }

    private void posionAck(MessageDispatch md, String cause) throws JMSException {
        MessageAck posionAck = new MessageAck(md, 1, 1);
        posionAck.setFirstMessageId(md.getMessage().getMessageId());
        posionAck.setPoisonCause(new Throwable(cause));
        this.session.sendAck(posionAck);
    }

    private boolean redeliveryExceeded(MessageDispatch md) {
        try {
            return this.session.getTransacted() && this.redeliveryPolicy != null && this.redeliveryPolicy.isPreDispatchCheck() && this.redeliveryPolicy.getMaximumRedeliveries() != -1 && md.getRedeliveryCounter() > this.redeliveryPolicy.getMaximumRedeliveries() && md.getMessage().getProperty("redeliveryDelay") == null;
        }
        catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public Message receive() throws JMSException {
        this.checkClosed();
        this.checkMessageListener();
        this.sendPullCommand(0L);
        MessageDispatch md = this.dequeue(-1L);
        if (md == null) {
            return null;
        }
        this.beforeMessageIsConsumed(md);
        this.afterMessageIsConsumed(md, false);
        return this.createActiveMQMessage(md);
    }

    private ActiveMQMessage createActiveMQMessage(final MessageDispatch md) throws JMSException {
        Message transformedMessage;
        ActiveMQMessage m4 = (ActiveMQMessage)md.getMessage().copy();
        if (m4.getDataStructureType() == 29) {
            ((ActiveMQBlobMessage)m4).setBlobDownloader(new BlobDownloader(this.session.getBlobTransferPolicy()));
        }
        if (m4.getDataStructureType() == 26) {
            ((ActiveMQObjectMessage)m4).setTrustAllPackages(this.session.getConnection().isTrustAllPackages());
            ((ActiveMQObjectMessage)m4).setTrustedPackages(this.session.getConnection().getTrustedPackages());
        }
        if (this.transformer != null && (transformedMessage = this.transformer.consumerTransform(this.session, this, m4)) != null) {
            m4 = ActiveMQMessageTransformation.transformMessage(transformedMessage, this.session.connection);
        }
        if (this.session.isClientAcknowledge()) {
            m4.setAcknowledgeCallback(new Callback(){

                @Override
                public void execute() throws Exception {
                    ActiveMQMessageConsumer.this.checkClosed();
                    ActiveMQMessageConsumer.this.session.checkClosed();
                    ActiveMQMessageConsumer.this.session.acknowledge();
                }
            });
        } else if (this.session.isIndividualAcknowledge()) {
            m4.setAcknowledgeCallback(new Callback(){

                @Override
                public void execute() throws Exception {
                    ActiveMQMessageConsumer.this.checkClosed();
                    ActiveMQMessageConsumer.this.session.checkClosed();
                    ActiveMQMessageConsumer.this.acknowledge(md);
                }
            });
        }
        return m4;
    }

    @Override
    public Message receive(long timeout2) throws JMSException {
        this.checkClosed();
        this.checkMessageListener();
        if (timeout2 == 0L) {
            return this.receive();
        }
        this.sendPullCommand(timeout2);
        if (timeout2 > 0L) {
            MessageDispatch md = this.info.getPrefetchSize() == 0 ? this.dequeue(-1L) : this.dequeue(timeout2);
            if (md == null) {
                return null;
            }
            this.beforeMessageIsConsumed(md);
            this.afterMessageIsConsumed(md, false);
            return this.createActiveMQMessage(md);
        }
        return null;
    }

    @Override
    public Message receiveNoWait() throws JMSException {
        this.checkClosed();
        this.checkMessageListener();
        this.sendPullCommand(-1L);
        MessageDispatch md = this.info.getPrefetchSize() == 0 ? this.dequeue(-1L) : this.dequeue(0L);
        if (md == null) {
            return null;
        }
        this.beforeMessageIsConsumed(md);
        this.afterMessageIsConsumed(md, false);
        return this.createActiveMQMessage(md);
    }

    @Override
    public void close() throws JMSException {
        if (!this.unconsumedMessages.isClosed()) {
            if (!this.deliveredMessages.isEmpty() && this.session.getTransactionContext().isInTransaction()) {
                this.session.getTransactionContext().addSynchronization(new Synchronization(){

                    @Override
                    public void afterCommit() throws Exception {
                        ActiveMQMessageConsumer.this.doClose();
                    }

                    @Override
                    public void afterRollback() throws Exception {
                        ActiveMQMessageConsumer.this.doClose();
                    }
                });
            } else {
                this.doClose();
            }
        }
    }

    void doClose() throws JMSException {
        this.dispose();
        RemoveInfo removeCommand = this.info.createRemoveCommand();
        LOG.debug("remove: {}, lastDeliveredSequenceId: {}", (Object)this.getConsumerId(), (Object)this.lastDeliveredSequenceId);
        removeCommand.setLastDeliveredSequenceId(this.lastDeliveredSequenceId);
        this.session.asyncSendPacket(removeCommand);
    }

    void inProgressClearRequired() {
        this.inProgressClearRequiredFlag.incrementAndGet();
        this.clearDeliveredList = true;
        if (!this.deliveredMessages.isEmpty() && this.session.getTransactionContext().isInTransaction()) {
            this.session.getTransactionContext().setRollbackOnly(true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void clearMessagesInProgress() {
        if (this.inProgressClearRequiredFlag.get() > 0) {
            Object object = this.unconsumedMessages.getMutex();
            synchronized (object) {
                if (this.inProgressClearRequiredFlag.get() > 0) {
                    LOG.debug("{} clearing unconsumed list ({}) on transport interrupt", (Object)this.getConsumerId(), (Object)this.unconsumedMessages.size());
                    List<MessageDispatch> list = this.unconsumedMessages.removeAll();
                    if (!this.info.isBrowser()) {
                        for (MessageDispatch old : list) {
                            this.session.connection.rollbackDuplicate(this, old.getMessage());
                        }
                    }
                    this.session.connection.transportInterruptionProcessingComplete();
                    this.inProgressClearRequiredFlag.decrementAndGet();
                    this.unconsumedMessages.getMutex().notifyAll();
                }
            }
        }
        this.clearDeliveredList();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void deliverAcks() {
        MessageAck ack = null;
        if (this.deliveryingAcknowledgements.compareAndSet(false, true)) {
            LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
            synchronized (linkedList) {
                if (this.isAutoAcknowledgeEach()) {
                    ack = this.makeAckForAllDeliveredMessages((byte)2);
                    if (ack != null) {
                        this.deliveredMessages.clear();
                        this.ackCounter = 0;
                    } else {
                        ack = this.pendingAck;
                        this.pendingAck = null;
                    }
                } else if (this.pendingAck != null && this.pendingAck.isStandardAck()) {
                    ack = this.pendingAck;
                    this.pendingAck = null;
                }
            }
            if (ack != null) {
                final MessageAck ackToSend = ack;
                if (this.executorService == null) {
                    this.executorService = Executors.newSingleThreadExecutor();
                }
                this.executorService.submit(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            ActiveMQMessageConsumer.this.session.sendAck(ackToSend, true);
                        }
                        catch (JMSException e) {
                            LOG.error(ActiveMQMessageConsumer.this.getConsumerId() + " failed to deliver acknowledgements", e);
                        }
                        finally {
                            ActiveMQMessageConsumer.this.deliveryingAcknowledgements.set(false);
                        }
                    }
                });
            } else {
                this.deliveryingAcknowledgements.set(false);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispose() throws JMSException {
        if (!this.unconsumedMessages.isClosed()) {
            AbstractList tmp;
            if (!this.session.getTransacted()) {
                this.deliverAcks();
                if (this.isAutoAcknowledgeBatch()) {
                    this.acknowledge();
                }
            }
            if (this.executorService != null) {
                ThreadPoolUtils.shutdownGraceful(this.executorService, 60000L);
                this.executorService = null;
            }
            if (this.optimizedAckTask != null) {
                this.session.connection.getScheduler().cancel(this.optimizedAckTask);
                this.optimizedAckTask = null;
            }
            if ((this.session.isClientAcknowledge() || this.session.isIndividualAcknowledge()) && !this.info.isBrowser()) {
                tmp = null;
                Iterator<MessageDispatch> iterator2 = this.deliveredMessages;
                synchronized (iterator2) {
                    tmp = new ArrayList<MessageDispatch>(this.deliveredMessages);
                }
                for (MessageDispatch old : tmp) {
                    this.session.connection.rollbackDuplicate(this, old.getMessage());
                }
                tmp.clear();
            }
            if (!this.session.isTransacted()) {
                tmp = this.deliveredMessages;
                synchronized (tmp) {
                    this.deliveredMessages.clear();
                }
            }
            this.unconsumedMessages.close();
            this.session.removeConsumer(this);
            List<MessageDispatch> list = this.unconsumedMessages.removeAll();
            if (!this.info.isBrowser()) {
                for (MessageDispatch old : list) {
                    if (old.getMessage() != null) {
                        LOG.debug("on close, rollback duplicate: {}", (Object)old.getMessage().getMessageId());
                    }
                    this.session.connection.rollbackDuplicate(this, old.getMessage());
                }
            }
        }
    }

    protected void checkClosed() throws IllegalStateException {
        if (this.unconsumedMessages.isClosed()) {
            throw new IllegalStateException("The Consumer is closed");
        }
    }

    protected void sendPullCommand(long timeout2) throws JMSException {
        this.clearDeliveredList();
        if (this.info.getCurrentPrefetchSize() == 0 && this.unconsumedMessages.isEmpty()) {
            MessagePull messagePull = new MessagePull();
            messagePull.configure(this.info);
            messagePull.setTimeout(timeout2);
            this.session.asyncSendPacket(messagePull);
        }
    }

    protected void checkMessageListener() throws JMSException {
        this.session.checkMessageListener();
    }

    protected void setOptimizeAcknowledge(boolean value) {
        if (this.optimizeAcknowledge && !value) {
            this.deliverAcks();
        }
        this.optimizeAcknowledge = value;
    }

    protected void setPrefetchSize(int prefetch) {
        this.deliverAcks();
        this.info.setCurrentPrefetchSize(prefetch);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void beforeMessageIsConsumed(MessageDispatch md) throws JMSException {
        md.setDeliverySequenceId(this.session.getNextDeliveryId());
        this.lastDeliveredSequenceId = md.getMessage().getMessageId().getBrokerSequenceId();
        if (!this.isAutoAcknowledgeBatch()) {
            LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
            synchronized (linkedList) {
                this.deliveredMessages.addFirst(md);
            }
            if (this.session.getTransacted()) {
                if (this.transactedIndividualAck) {
                    this.immediateIndividualTransactedAck(md);
                } else {
                    this.ackLater(md, (byte)0);
                }
            }
        }
    }

    private void immediateIndividualTransactedAck(MessageDispatch md) throws JMSException {
        this.registerSync();
        MessageAck ack = new MessageAck(md, 4, 1);
        ack.setTransactionId(this.session.getTransactionContext().getTransactionId());
        this.session.sendAck(ack);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void afterMessageIsConsumed(MessageDispatch md, boolean messageExpired) throws JMSException {
        if (this.unconsumedMessages.isClosed()) {
            return;
        }
        if (messageExpired) {
            this.acknowledge(md, (byte)6);
            this.stats.getExpiredMessageCount().increment();
        } else {
            this.stats.onMessage();
            if (!this.session.getTransacted()) {
                if (this.isAutoAcknowledgeEach()) {
                    if (this.deliveryingAcknowledgements.compareAndSet(false, true)) {
                        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
                        synchronized (linkedList) {
                            if (!this.deliveredMessages.isEmpty()) {
                                if (this.optimizeAcknowledge) {
                                    ++this.ackCounter;
                                    if ((double)(this.ackCounter + this.deliveredCounter) >= (double)this.info.getPrefetchSize() * 0.65 || this.optimizeAcknowledgeTimeOut > 0L && System.currentTimeMillis() >= this.optimizeAckTimestamp + this.optimizeAcknowledgeTimeOut) {
                                        MessageAck ack = this.makeAckForAllDeliveredMessages((byte)2);
                                        if (ack != null) {
                                            this.deliveredMessages.clear();
                                            this.ackCounter = 0;
                                            this.session.sendAck(ack);
                                            this.optimizeAckTimestamp = System.currentTimeMillis();
                                        }
                                        if (this.pendingAck != null && this.deliveredCounter > 0) {
                                            this.session.sendAck(this.pendingAck);
                                            this.pendingAck = null;
                                            this.deliveredCounter = 0;
                                        }
                                    }
                                } else {
                                    MessageAck ack = this.makeAckForAllDeliveredMessages((byte)2);
                                    if (ack != null) {
                                        this.deliveredMessages.clear();
                                        this.session.sendAck(ack);
                                    }
                                }
                            }
                        }
                        this.deliveryingAcknowledgements.set(false);
                    }
                } else if (this.isAutoAcknowledgeBatch()) {
                    this.ackLater(md, (byte)2);
                } else if (this.session.isClientAcknowledge() || this.session.isIndividualAcknowledge()) {
                    boolean messageUnackedByConsumer = false;
                    LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
                    synchronized (linkedList) {
                        messageUnackedByConsumer = this.deliveredMessages.contains(md);
                    }
                    if (messageUnackedByConsumer) {
                        this.ackLater(md, (byte)0);
                    }
                } else {
                    throw new IllegalStateException("Invalid session state.");
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MessageAck makeAckForAllDeliveredMessages(byte type) {
        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
        synchronized (linkedList) {
            if (this.deliveredMessages.isEmpty()) {
                return null;
            }
            MessageDispatch md = this.deliveredMessages.getFirst();
            MessageAck ack = new MessageAck(md, type, this.deliveredMessages.size());
            ack.setFirstMessageId(this.deliveredMessages.getLast().getMessage().getMessageId());
            return ack;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ackLater(MessageDispatch md, byte ackType) throws JMSException {
        if (this.session.getTransacted()) {
            this.registerSync();
        }
        ++this.deliveredCounter;
        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
        synchronized (linkedList) {
            MessageAck oldPendingAck = this.pendingAck;
            this.pendingAck = new MessageAck(md, ackType, this.deliveredCounter);
            this.pendingAck.setTransactionId(this.session.getTransactionContext().getTransactionId());
            if (oldPendingAck == null) {
                this.pendingAck.setFirstMessageId(this.pendingAck.getLastMessageId());
            } else if (oldPendingAck.getAckType() == this.pendingAck.getAckType()) {
                this.pendingAck.setFirstMessageId(oldPendingAck.getFirstMessageId());
            } else if (!oldPendingAck.isDeliveredAck()) {
                LOG.debug("Sending old pending ack {}, new pending: {}", (Object)oldPendingAck, (Object)this.pendingAck);
                this.session.sendAck(oldPendingAck);
            } else {
                LOG.debug("dropping old pending ack {}, new pending: {}", (Object)oldPendingAck, (Object)this.pendingAck);
            }
            if (0.5 * (double)this.info.getPrefetchSize() <= (double)(this.deliveredCounter + this.ackCounter - this.additionalWindowSize)) {
                LOG.debug("ackLater: sending: {}", (Object)this.pendingAck);
                this.session.sendAck(this.pendingAck);
                this.pendingAck = null;
                this.deliveredCounter = 0;
                this.additionalWindowSize = 0;
            }
        }
    }

    private void registerSync() throws JMSException {
        this.session.doStartTransaction();
        if (!this.synchronizationRegistered) {
            this.synchronizationRegistered = true;
            this.session.getTransactionContext().addSynchronization(new Synchronization(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void beforeEnd() throws Exception {
                    if (ActiveMQMessageConsumer.this.transactedIndividualAck) {
                        ActiveMQMessageConsumer.this.clearDeliveredList();
                        ActiveMQMessageConsumer.this.waitForRedeliveries();
                        LinkedList<MessageDispatch> linkedList = ActiveMQMessageConsumer.this.deliveredMessages;
                        synchronized (linkedList) {
                            ActiveMQMessageConsumer.this.rollbackOnFailedRecoveryRedelivery();
                        }
                    } else {
                        ActiveMQMessageConsumer.this.acknowledge();
                    }
                    ActiveMQMessageConsumer.this.synchronizationRegistered = false;
                }

                @Override
                public void afterCommit() throws Exception {
                    ActiveMQMessageConsumer.this.commit();
                    ActiveMQMessageConsumer.this.synchronizationRegistered = false;
                }

                @Override
                public void afterRollback() throws Exception {
                    ActiveMQMessageConsumer.this.rollback();
                    ActiveMQMessageConsumer.this.synchronizationRegistered = false;
                }
            });
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void acknowledge() throws JMSException {
        this.clearDeliveredList();
        this.waitForRedeliveries();
        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
        synchronized (linkedList) {
            MessageAck ack = this.makeAckForAllDeliveredMessages((byte)2);
            if (ack == null) {
                return;
            }
            if (this.session.getTransacted()) {
                this.rollbackOnFailedRecoveryRedelivery();
                this.session.doStartTransaction();
                ack.setTransactionId(this.session.getTransactionContext().getTransactionId());
            }
            this.pendingAck = null;
            this.session.sendAck(ack);
            this.deliveredCounter = Math.max(0, this.deliveredCounter - this.deliveredMessages.size());
            this.additionalWindowSize = Math.max(0, this.additionalWindowSize - this.deliveredMessages.size());
            if (!this.session.getTransacted()) {
                this.deliveredMessages.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void waitForRedeliveries() {
        if (this.failoverRedeliveryWaitPeriod > 0L && this.previouslyDeliveredMessages != null) {
            int numberNotReplayed;
            long expiry = System.currentTimeMillis() + this.failoverRedeliveryWaitPeriod;
            do {
                numberNotReplayed = 0;
                LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
                synchronized (linkedList) {
                    if (this.previouslyDeliveredMessages != null) {
                        for (Map.Entry entry : this.previouslyDeliveredMessages.entrySet()) {
                            if (((Boolean)entry.getValue()).booleanValue()) continue;
                            ++numberNotReplayed;
                        }
                    }
                }
                if (numberNotReplayed <= 0) continue;
                LOG.info("waiting for redelivery of {} in transaction: {}, to consumer: {}", numberNotReplayed, this.getConsumerId(), this.previouslyDeliveredMessages.transactionId);
                try {
                    Thread.sleep(Math.max(500L, this.failoverRedeliveryWaitPeriod / 4L));
                }
                catch (InterruptedException outOfhere) {
                    break;
                }
            } while (numberNotReplayed > 0 && expiry - System.currentTimeMillis() < 0L);
        }
    }

    private void rollbackOnFailedRecoveryRedelivery() throws JMSException {
        if (this.previouslyDeliveredMessages != null) {
            int numberNotReplayed = 0;
            for (Map.Entry entry : this.previouslyDeliveredMessages.entrySet()) {
                if (((Boolean)entry.getValue()).booleanValue()) continue;
                ++numberNotReplayed;
                LOG.debug("previously delivered message has not been replayed in transaction: {}, messageId: {}", (Object)this.previouslyDeliveredMessages.transactionId, entry.getKey());
            }
            if (numberNotReplayed > 0) {
                String message = "rolling back transaction (" + this.previouslyDeliveredMessages.transactionId + ") post failover recovery. " + numberNotReplayed + " previously delivered message(s) not replayed to consumer: " + this.getConsumerId();
                LOG.warn(message);
                throw new TransactionRolledBackException(message);
            }
        }
    }

    void acknowledge(MessageDispatch md) throws JMSException {
        this.acknowledge(md, (byte)4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void acknowledge(MessageDispatch md, byte ackType) throws JMSException {
        MessageAck ack = new MessageAck(md, ackType, 1);
        if (ack.isExpiredAck()) {
            ack.setFirstMessageId(ack.getLastMessageId());
        }
        this.session.sendAck(ack);
        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
        synchronized (linkedList) {
            this.deliveredMessages.remove(md);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void commit() throws JMSException {
        LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
        synchronized (linkedList) {
            this.deliveredMessages.clear();
            this.clearPreviouslyDelivered();
        }
        this.redeliveryDelay = 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollback() throws JMSException {
        this.clearDeliveredList();
        Object object = this.unconsumedMessages.getMutex();
        synchronized (object) {
            LinkedList<MessageDispatch> linkedList;
            if (this.optimizeAcknowledge && !this.info.isBrowser()) {
                linkedList = this.deliveredMessages;
                synchronized (linkedList) {
                    for (int i = 0; i < this.deliveredMessages.size() && i < this.ackCounter; ++i) {
                        MessageDispatch md = this.deliveredMessages.removeLast();
                        this.session.connection.rollbackDuplicate(this, md.getMessage());
                    }
                }
            }
            linkedList = this.deliveredMessages;
            synchronized (linkedList) {
                MessageAck ack;
                this.rollbackPreviouslyDeliveredAndNotRedelivered();
                if (this.deliveredMessages.isEmpty()) {
                    return;
                }
                MessageDispatch lastMd = this.deliveredMessages.getFirst();
                int currentRedeliveryCount = lastMd.getMessage().getRedeliveryCounter();
                this.redeliveryDelay = currentRedeliveryCount > 0 ? this.redeliveryPolicy.getNextRedeliveryDelay(this.redeliveryDelay) : this.redeliveryPolicy.getInitialRedeliveryDelay();
                MessageId firstMsgId = this.deliveredMessages.getLast().getMessage().getMessageId();
                for (MessageDispatch md : this.deliveredMessages) {
                    md.getMessage().onMessageRolledBack();
                    this.session.connection.rollbackDuplicate(this, md.getMessage());
                }
                if (this.redeliveryPolicy.getMaximumRedeliveries() != -1 && lastMd.getMessage().getRedeliveryCounter() > this.redeliveryPolicy.getMaximumRedeliveries()) {
                    ack = new MessageAck(lastMd, 1, this.deliveredMessages.size());
                    ack.setFirstMessageId(firstMsgId);
                    ack.setPoisonCause(new Throwable("Delivery[" + lastMd.getMessage().getRedeliveryCounter() + "] exceeds redelivery policy limit:" + this.redeliveryPolicy + ", cause:" + lastMd.getRollbackCause(), lastMd.getRollbackCause()));
                    this.session.sendAck(ack, true);
                    this.additionalWindowSize = Math.max(0, this.additionalWindowSize - this.deliveredMessages.size());
                    this.redeliveryDelay = 0L;
                    this.deliveredCounter -= this.deliveredMessages.size();
                    this.deliveredMessages.clear();
                } else {
                    if (currentRedeliveryCount > 0) {
                        ack = new MessageAck(lastMd, 3, this.deliveredMessages.size());
                        ack.setFirstMessageId(firstMsgId);
                        this.session.sendAck(ack, true);
                    }
                    if (this.nonBlockingRedelivery) {
                        if (!this.unconsumedMessages.isClosed()) {
                            final LinkedList<MessageDispatch> pendingRedeliveries = new LinkedList<MessageDispatch>(this.deliveredMessages);
                            Collections.reverse(pendingRedeliveries);
                            this.deliveredCounter -= this.deliveredMessages.size();
                            this.deliveredMessages.clear();
                            this.session.getScheduler().executeAfterDelay(new Runnable(){

                                @Override
                                public void run() {
                                    try {
                                        if (!ActiveMQMessageConsumer.this.unconsumedMessages.isClosed()) {
                                            for (MessageDispatch dispatch : pendingRedeliveries) {
                                                ActiveMQMessageConsumer.this.session.dispatch(dispatch);
                                            }
                                        }
                                    }
                                    catch (Exception e) {
                                        ActiveMQMessageConsumer.this.session.connection.onAsyncException(e);
                                    }
                                }
                            }, this.redeliveryDelay);
                        }
                    } else {
                        this.unconsumedMessages.stop();
                        for (MessageDispatch md : this.deliveredMessages) {
                            this.unconsumedMessages.enqueueFirst(md);
                        }
                        this.deliveredCounter -= this.deliveredMessages.size();
                        this.deliveredMessages.clear();
                        if (this.redeliveryDelay > 0L && !this.unconsumedMessages.isClosed()) {
                            this.session.getScheduler().executeAfterDelay(new Runnable(){

                                @Override
                                public void run() {
                                    try {
                                        if (ActiveMQMessageConsumer.this.started.get()) {
                                            ActiveMQMessageConsumer.this.start();
                                        }
                                    }
                                    catch (JMSException e) {
                                        ActiveMQMessageConsumer.this.session.connection.onAsyncException(e);
                                    }
                                }
                            }, this.redeliveryDelay);
                        } else {
                            this.start();
                        }
                    }
                }
            }
        }
        if (this.messageListener.get() != null) {
            this.session.redispatch(this, this.unconsumedMessages);
        }
    }

    private void rollbackPreviouslyDeliveredAndNotRedelivered() {
        if (this.previouslyDeliveredMessages != null) {
            for (Map.Entry entry : this.previouslyDeliveredMessages.entrySet()) {
                if (((Boolean)entry.getValue()).booleanValue()) continue;
                LOG.trace("rollback non redelivered: {}" + entry.getKey());
                this.removeFromDeliveredMessages((MessageId)entry.getKey());
            }
            this.clearPreviouslyDelivered();
        }
    }

    private void removeFromDeliveredMessages(MessageId key) {
        Iterator iterator2 = this.deliveredMessages.iterator();
        while (iterator2.hasNext()) {
            MessageDispatch candidate = (MessageDispatch)iterator2.next();
            if (!key.equals(candidate.getMessage().getMessageId())) continue;
            this.session.connection.rollbackDuplicate(this, candidate.getMessage());
            iterator2.remove();
            break;
        }
    }

    private void clearPreviouslyDelivered() {
        if (this.previouslyDeliveredMessages != null) {
            this.previouslyDeliveredMessages.clear();
            this.previouslyDeliveredMessages = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dispatch(MessageDispatch md) {
        MessageListener listener = this.messageListener.get();
        try {
            this.clearMessagesInProgress();
            this.clearDeliveredList();
            Object object = this.unconsumedMessages.getMutex();
            synchronized (object) {
                block29: {
                    if (!this.unconsumedMessages.isClosed()) {
                        if (this.info.isBrowser() || !this.session.connection.isDuplicate(this, md.getMessage())) {
                            if (listener != null && this.unconsumedMessages.isRunning()) {
                                if (this.redeliveryExceeded(md)) {
                                    this.posionAck(md, "listener dispatch[" + md.getRedeliveryCounter() + "] to " + this.getConsumerId() + " exceeds redelivery policy limit:" + this.redeliveryPolicy);
                                    return;
                                }
                                ActiveMQMessage message = this.createActiveMQMessage(md);
                                this.beforeMessageIsConsumed(md);
                                try {
                                    boolean expired;
                                    boolean bl = expired = this.isConsumerExpiryCheckEnabled() && message.isExpired();
                                    if (!expired) {
                                        listener.onMessage(message);
                                    }
                                    this.afterMessageIsConsumed(md, expired);
                                }
                                catch (RuntimeException e) {
                                    LOG.error("{} Exception while processing message: {}", this.getConsumerId(), md.getMessage().getMessageId(), e);
                                    md.setRollbackCause(e);
                                    if (this.isAutoAcknowledgeBatch() || this.isAutoAcknowledgeEach() || this.session.isIndividualAcknowledge()) {
                                        this.rollback();
                                        break block29;
                                    }
                                    this.afterMessageIsConsumed(md, false);
                                }
                            } else {
                                if (!this.unconsumedMessages.isRunning()) {
                                    this.session.connection.rollbackDuplicate(this, md.getMessage());
                                }
                                if (md.getMessage() == null) {
                                    this.unconsumedMessages.enqueue(md);
                                } else if (!this.consumeExpiredMessage(md)) {
                                    this.unconsumedMessages.enqueue(md);
                                    if (this.availableListener != null) {
                                        this.availableListener.onMessageAvailable(this);
                                    }
                                } else {
                                    this.beforeMessageIsConsumed(md);
                                    this.afterMessageIsConsumed(md, true);
                                    if (this.info.getCurrentPrefetchSize() == 0) {
                                        this.unconsumedMessages.enqueue(null);
                                    }
                                }
                            }
                        } else if (this.redeliveryExpectedInCurrentTransaction(md, true)) {
                            LOG.debug("{} tracking transacted redelivery {}", (Object)this.getConsumerId(), (Object)md.getMessage());
                            if (this.transactedIndividualAck) {
                                this.immediateIndividualTransactedAck(md);
                            } else {
                                this.session.sendAck(new MessageAck(md, 0, 1));
                            }
                        } else {
                            ConsumerId consumerWithPendingTransaction = this.redeliveryPendingInCompetingTransaction(md);
                            if (consumerWithPendingTransaction != null) {
                                LOG.warn("{} delivering duplicate {}, pending transaction completion on {} will rollback", this.getConsumerId(), md.getMessage(), consumerWithPendingTransaction);
                                this.session.getConnection().rollbackDuplicate(this, md.getMessage());
                                this.dispatch(md);
                            } else {
                                LOG.warn("{} suppressing duplicate delivery on connection, poison acking: {}", (Object)this.getConsumerId(), (Object)md);
                                this.posionAck(md, "Suppressing duplicate delivery on connection, consumer " + this.getConsumerId());
                            }
                        }
                    }
                }
            }
            if (++this.dispatchedCount % 1000 == 0) {
                this.dispatchedCount = 0;
                Thread.yield();
            }
        }
        catch (Exception e) {
            this.session.connection.onClientInternalException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean redeliveryExpectedInCurrentTransaction(MessageDispatch md, boolean markReceipt) {
        if (this.session.isTransacted()) {
            LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
            synchronized (linkedList) {
                if (this.previouslyDeliveredMessages != null && this.previouslyDeliveredMessages.containsKey(md.getMessage().getMessageId())) {
                    if (markReceipt) {
                        this.previouslyDeliveredMessages.put(md.getMessage().getMessageId(), true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private ConsumerId redeliveryPendingInCompetingTransaction(MessageDispatch md) {
        for (ActiveMQSession activeMQSession : this.session.connection.getSessions()) {
            for (ActiveMQMessageConsumer activeMQMessageConsumer : activeMQSession.consumers) {
                if (!activeMQMessageConsumer.redeliveryExpectedInCurrentTransaction(md, false)) continue;
                return activeMQMessageConsumer.getConsumerId();
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void clearDeliveredList() {
        if (this.clearDeliveredList) {
            LinkedList<MessageDispatch> linkedList = this.deliveredMessages;
            synchronized (linkedList) {
                if (this.clearDeliveredList) {
                    if (!this.deliveredMessages.isEmpty()) {
                        if (this.session.isTransacted()) {
                            if (this.previouslyDeliveredMessages == null) {
                                this.previouslyDeliveredMessages = new PreviouslyDeliveredMap(this.session.getTransactionContext().getTransactionId());
                            }
                            for (MessageDispatch delivered : this.deliveredMessages) {
                                this.previouslyDeliveredMessages.put(delivered.getMessage().getMessageId(), false);
                            }
                            LOG.debug("{} tracking existing transacted {} delivered list ({}) on transport interrupt", this.getConsumerId(), this.previouslyDeliveredMessages.transactionId, this.deliveredMessages.size());
                        } else {
                            if (this.session.isClientAcknowledge()) {
                                LOG.debug("{} rolling back delivered list ({}) on transport interrupt", (Object)this.getConsumerId(), (Object)this.deliveredMessages.size());
                                if (!this.info.isBrowser()) {
                                    for (MessageDispatch md : this.deliveredMessages) {
                                        this.session.connection.rollbackDuplicate(this, md.getMessage());
                                    }
                                }
                            }
                            LOG.debug("{} clearing delivered list ({}) on transport interrupt", (Object)this.getConsumerId(), (Object)this.deliveredMessages.size());
                            this.deliveredMessages.clear();
                            this.pendingAck = null;
                        }
                    }
                    this.clearDeliveredList = false;
                }
            }
        }
    }

    public int getMessageSize() {
        return this.unconsumedMessages.size();
    }

    public void start() throws JMSException {
        if (this.unconsumedMessages.isClosed()) {
            return;
        }
        this.started.set(true);
        this.unconsumedMessages.start();
        this.session.executor.wakeup();
    }

    public void stop() {
        this.started.set(false);
        this.unconsumedMessages.stop();
    }

    public String toString() {
        return "ActiveMQMessageConsumer { value=" + this.info.getConsumerId() + ", started=" + this.started.get() + " }";
    }

    public boolean iterate() {
        MessageDispatch md;
        MessageListener listener = this.messageListener.get();
        if (listener != null && (md = this.unconsumedMessages.dequeueNoWait()) != null) {
            this.dispatch(md);
            return true;
        }
        return false;
    }

    public boolean isInUse(ActiveMQTempDestination destination) {
        return this.info.getDestination().equals(destination);
    }

    public long getLastDeliveredSequenceId() {
        return this.lastDeliveredSequenceId;
    }

    public IOException getFailureError() {
        return this.failureError;
    }

    public void setFailureError(IOException failureError) {
        this.failureError = failureError;
    }

    public long getOptimizedAckScheduledAckInterval() {
        return this.optimizedAckScheduledAckInterval;
    }

    public void setOptimizedAckScheduledAckInterval(long optimizedAckScheduledAckInterval) throws JMSException {
        this.optimizedAckScheduledAckInterval = optimizedAckScheduledAckInterval;
        if (this.optimizedAckTask != null) {
            try {
                this.session.connection.getScheduler().cancel(this.optimizedAckTask);
            }
            catch (JMSException e) {
                LOG.debug("Caught exception while cancelling old optimized ack task", e);
                throw e;
            }
            this.optimizedAckTask = null;
        }
        if (this.optimizeAcknowledge && this.optimizedAckScheduledAckInterval > 0L) {
            this.optimizedAckTask = new Runnable(){

                @Override
                public void run() {
                    try {
                        if (ActiveMQMessageConsumer.this.optimizeAcknowledge && !ActiveMQMessageConsumer.this.unconsumedMessages.isClosed()) {
                            LOG.info("Consumer:{} is performing scheduled delivery of outstanding optimized Acks", (Object)ActiveMQMessageConsumer.this.info.getConsumerId());
                            ActiveMQMessageConsumer.this.deliverAcks();
                        }
                    }
                    catch (Exception e) {
                        LOG.debug("Optimized Ack Task caught exception during ack", e);
                    }
                }
            };
            try {
                this.session.connection.getScheduler().executePeriodically(this.optimizedAckTask, optimizedAckScheduledAckInterval);
            }
            catch (JMSException e) {
                LOG.debug("Caught exception while scheduling new optimized ack task", e);
                throw e;
            }
        }
    }

    public boolean hasMessageListener() {
        return this.messageListener.get() != null;
    }

    public boolean isConsumerExpiryCheckEnabled() {
        return this.consumerExpiryCheckEnabled;
    }

    public void setConsumerExpiryCheckEnabled(boolean consumerExpiryCheckEnabled) {
        this.consumerExpiryCheckEnabled = consumerExpiryCheckEnabled;
    }

    class PreviouslyDeliveredMap<K, V>
    extends HashMap<K, V> {
        final TransactionId transactionId;

        public PreviouslyDeliveredMap(TransactionId transactionId) {
            this.transactionId = transactionId;
        }
    }
}

