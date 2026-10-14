/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.BrokerStoppedException;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.MapTransportConnectionStateRegister;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.SingleTransportConnectionStateRegister;
import org.apache.activemq.broker.SuppressReplyException;
import org.apache.activemq.broker.TransportConnectionState;
import org.apache.activemq.broker.TransportConnectionStateRegister;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.ConnectionStatistics;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.BrokerSubscriptionInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.ConnectionError;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ControlCommand;
import org.apache.activemq.command.DataArrayResponse;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.FlushCommand;
import org.apache.activemq.command.IntegerResponse;
import org.apache.activemq.command.KeepAliveInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.network.DemandForwardingBridge;
import org.apache.activemq.network.MBeanNetworkListener;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeFactory;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.activemq.state.CommandVisitor;
import org.apache.activemq.state.ConnectionState;
import org.apache.activemq.state.ConsumerState;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.state.SessionState;
import org.apache.activemq.state.TransactionState;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transaction.Transaction;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.TransmitCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportDisposedIOException;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.MarshallingSupport;
import org.apache.activemq.util.NetworkBridgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class TransportConnection
implements Connection,
Task,
CommandVisitor {
    private static final Logger LOG = LoggerFactory.getLogger(TransportConnection.class);
    private static final Logger TRANSPORTLOG = LoggerFactory.getLogger(TransportConnection.class.getName() + ".Transport");
    private static final Logger SERVICELOG = LoggerFactory.getLogger(TransportConnection.class.getName() + ".Service");
    protected final Broker broker;
    protected final BrokerService brokerService;
    protected final TransportConnector connector;
    protected final Map<ConnectionId, ConnectionState> brokerConnectionStates;
    protected BrokerInfo brokerInfo;
    protected final List<Command> dispatchQueue = new LinkedList<Command>();
    protected TaskRunner taskRunner;
    protected final AtomicReference<Throwable> transportException = new AtomicReference();
    protected AtomicBoolean dispatchStopped = new AtomicBoolean(false);
    private final Transport transport;
    private MessageAuthorizationPolicy messageAuthorizationPolicy;
    private WireFormatInfo wireFormatInfo;
    private boolean inServiceException;
    private final ConnectionStatistics statistics = new ConnectionStatistics();
    private boolean manageable;
    private boolean slow;
    private boolean markedCandidate;
    private boolean blockedCandidate;
    private boolean blocked;
    private boolean connected;
    private boolean active;
    private static final int NEW = 0;
    private static final int STARTING = 1;
    private static final int STARTED = 2;
    private static final int PENDING_STOP = 3;
    private final AtomicInteger status = new AtomicInteger(0);
    private long timeStamp;
    private final AtomicBoolean stopping = new AtomicBoolean(false);
    private final CountDownLatch stopped = new CountDownLatch(1);
    private final AtomicBoolean asyncException = new AtomicBoolean(false);
    private final Map<ProducerId, ProducerBrokerExchange> producerExchanges = new HashMap<ProducerId, ProducerBrokerExchange>();
    private final Map<ConsumerId, ConsumerBrokerExchange> consumerExchanges = new HashMap<ConsumerId, ConsumerBrokerExchange>();
    private final CountDownLatch dispatchStoppedLatch = new CountDownLatch(1);
    private ConnectionContext context;
    private boolean networkConnection;
    private boolean faultTolerantConnection;
    private final AtomicInteger protocolVersion = new AtomicInteger(12);
    private DemandForwardingBridge duplexBridge;
    private final TaskRunnerFactory taskRunnerFactory;
    private final TaskRunnerFactory stopTaskRunnerFactory;
    private TransportConnectionStateRegister connectionStateRegister = new SingleTransportConnectionStateRegister();
    private final ReentrantReadWriteLock serviceLock = new ReentrantReadWriteLock();
    private String duplexNetworkConnectorId;

    public TransportConnection(TransportConnector connector, Transport transport, Broker broker, TaskRunnerFactory taskRunnerFactory, TaskRunnerFactory stopTaskRunnerFactory) {
        this.connector = connector;
        this.broker = broker;
        this.brokerService = broker.getBrokerService();
        RegionBroker rb = (RegionBroker)broker.getAdaptor(RegionBroker.class);
        this.brokerConnectionStates = rb.getConnectionStates();
        if (connector != null) {
            this.statistics.setParent(connector.getStatistics());
            this.messageAuthorizationPolicy = connector.getMessageAuthorizationPolicy();
        }
        this.taskRunnerFactory = taskRunnerFactory;
        this.stopTaskRunnerFactory = stopTaskRunnerFactory;
        this.transport = transport;
        if (this.transport instanceof BrokerServiceAware) {
            ((BrokerServiceAware)((Object)this.transport)).setBrokerService(this.brokerService);
        }
        this.transport.setTransportListener(new DefaultTransportListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onCommand(Object o) {
                block6: {
                    TransportConnection.this.serviceLock.readLock().lock();
                    try {
                        if (!(o instanceof Command)) {
                            throw new RuntimeException("Protocol violation - Command corrupted: " + o.toString());
                        }
                        Command command = (Command)o;
                        if (!TransportConnection.this.brokerService.isStopping()) {
                            Response response = TransportConnection.this.service(command);
                            if (response != null && !TransportConnection.this.brokerService.isStopping()) {
                                TransportConnection.this.dispatchSync(response);
                            }
                            break block6;
                        }
                        throw new BrokerStoppedException("Broker " + TransportConnection.this.brokerService + " is being stopped");
                    }
                    finally {
                        TransportConnection.this.serviceLock.readLock().unlock();
                    }
                }
            }

            @Override
            public void onException(IOException exception) {
                TransportConnection.this.serviceLock.readLock().lock();
                try {
                    TransportConnection.this.serviceTransportException(exception);
                }
                finally {
                    TransportConnection.this.serviceLock.readLock().unlock();
                }
            }
        });
        this.connected = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getDispatchQueueSize() {
        List<Command> list = this.dispatchQueue;
        synchronized (list) {
            return this.dispatchQueue.size();
        }
    }

    public void serviceTransportException(IOException e) {
        if (!this.stopping.get() && this.status.get() != 3) {
            this.transportException.set(e);
            if (TRANSPORTLOG.isDebugEnabled()) {
                TRANSPORTLOG.debug(this + " failed: " + e, e);
            } else if (TRANSPORTLOG.isWarnEnabled() && !this.expected(e)) {
                TRANSPORTLOG.warn(this + " failed: " + e);
            }
            this.stopAsync(e);
        }
    }

    private boolean expected(IOException e) {
        return this.isStomp() && (e instanceof SocketException && e.getMessage().indexOf("reset") != -1 || e instanceof EOFException);
    }

    private boolean isStomp() {
        URI uri = this.connector.getUri();
        return uri != null && uri.getScheme() != null && uri.getScheme().indexOf("stomp") != -1;
    }

    @Override
    public void serviceExceptionAsync(final IOException e) {
        if (this.asyncException.compareAndSet(false, true)) {
            new Thread("Async Exception Handler"){

                @Override
                public void run() {
                    TransportConnection.this.serviceException(e);
                }
            }.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void serviceException(Throwable e) {
        if (e instanceof IOException) {
            this.serviceTransportException((IOException)e);
        } else if (e.getClass() == BrokerStoppedException.class) {
            if (!this.stopping.get()) {
                SERVICELOG.debug("Broker has been stopped.  Notifying client and closing his connection.");
                ConnectionError ce = new ConnectionError();
                ce.setException(e);
                this.dispatchSync(ce);
                this.transportException.set(e);
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                this.stopAsync();
            }
        } else if (!this.stopping.get() && !this.inServiceException) {
            this.inServiceException = true;
            try {
                if (SERVICELOG.isDebugEnabled()) {
                    SERVICELOG.debug("Async error occurred: " + e, e);
                } else {
                    SERVICELOG.warn("Async error occurred: " + e);
                }
                ConnectionError ce = new ConnectionError();
                ce.setException(e);
                if (this.status.get() == 3) {
                    this.dispatchSync(ce);
                } else {
                    this.dispatchAsync(ce);
                }
            }
            finally {
                this.inServiceException = false;
            }
        }
    }

    @Override
    public Response service(Command command) {
        MDC.put("activemq.connector", this.connector.getUri().toString());
        Response response = null;
        boolean responseRequired = command.isResponseRequired();
        int commandId = command.getCommandId();
        try {
            response = this.status.get() != 3 ? command.visit(this) : new ExceptionResponse(this.transportException.get());
        }
        catch (Throwable e) {
            if (SERVICELOG.isDebugEnabled() && e.getClass() != BrokerStoppedException.class) {
                SERVICELOG.debug("Error occured while processing " + (responseRequired ? "sync" : "async") + " command: " + command + ", exception: " + e, e);
            }
            if (e instanceof SuppressReplyException || e.getCause() instanceof SuppressReplyException) {
                LOG.info("Suppressing reply to: " + command + " on: " + e + ", cause: " + e.getCause());
                responseRequired = false;
            }
            if (responseRequired) {
                if (e instanceof SecurityException || e.getCause() instanceof SecurityException) {
                    SERVICELOG.warn("Security Error occurred on connection to: {}, {}", (Object)this.transport.getRemoteAddress(), (Object)e.getMessage());
                }
                response = new ExceptionResponse(e);
            }
            this.forceRollbackOnlyOnFailedAsyncTransactionOp(e, command);
            this.serviceException(e);
        }
        if (responseRequired) {
            if (response == null) {
                response = new Response();
            }
            response.setCorrelationId(commandId);
        }
        if (this.context != null) {
            if (this.context.isDontSendReponse()) {
                this.context.setDontSendReponse(false);
                response = null;
            }
            this.context = null;
        }
        MDC.remove("activemq.connector");
        return response;
    }

    private void forceRollbackOnlyOnFailedAsyncTransactionOp(Throwable e, Command command) {
        Transaction transaction;
        if (this.brokerService.isRollbackOnlyOnAsyncException() && !(e instanceof IOException) && this.isInTransaction(command) && (transaction = this.getActiveTransaction(command)) != null && !transaction.isRollbackOnly()) {
            LOG.debug("on async exception, force rollback of transaction for: " + command, e);
            transaction.setRollbackOnly(e);
        }
    }

    private Transaction getActiveTransaction(Command command) {
        Transaction transaction = null;
        try {
            MessageAck messageAck;
            ConsumerBrokerExchange consumerExchange;
            if (command instanceof Message) {
                Message messageSend = (Message)command;
                ProducerId producerId = messageSend.getProducerId();
                ProducerBrokerExchange producerExchange = this.getProducerBrokerExchange(producerId);
                transaction = (Transaction)producerExchange.getConnectionContext().getTransactions().get(messageSend.getTransactionId());
            } else if (command instanceof MessageAck && (consumerExchange = this.getConsumerBrokerExchange((messageAck = (MessageAck)command).getConsumerId())) != null) {
                transaction = (Transaction)consumerExchange.getConnectionContext().getTransactions().get(messageAck.getTransactionId());
            }
        }
        catch (Exception ignored) {
            LOG.trace("failed to find active transaction for command: " + command, ignored);
        }
        return transaction;
    }

    private boolean isInTransaction(Command command) {
        return command instanceof Message && ((Message)command).isInTransaction() || command instanceof MessageAck && ((MessageAck)command).isInTransaction();
    }

    @Override
    public Response processKeepAlive(KeepAliveInfo info) throws Exception {
        return null;
    }

    @Override
    public Response processRemoveSubscription(RemoveSubscriptionInfo info) throws Exception {
        this.broker.removeSubscription(this.lookupConnectionState(info.getConnectionId()).getContext(), info);
        return null;
    }

    @Override
    public Response processWireFormat(WireFormatInfo info) throws Exception {
        this.wireFormatInfo = info;
        this.protocolVersion.set(info.getVersion());
        return null;
    }

    @Override
    public Response processShutdown(ShutdownInfo info) throws Exception {
        this.stopAsync();
        return null;
    }

    @Override
    public Response processFlush(FlushCommand command) throws Exception {
        return null;
    }

    @Override
    public Response processBeginTransaction(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = null;
        if (cs != null) {
            this.context = cs.getContext();
        }
        if (cs == null) {
            throw new NullPointerException("Context is null");
        }
        if (cs.getTransactionState(info.getTransactionId()) == null) {
            cs.addTransactionState(info.getTransactionId());
            this.broker.beginTransaction(this.context, info.getTransactionId());
        }
        return null;
    }

    @Override
    public int getActiveTransactionCount() {
        int rc = 0;
        for (TransportConnectionState cs : this.connectionStateRegister.listConnectionStates()) {
            rc += cs.getTransactionStates().size();
        }
        return rc;
    }

    @Override
    public Long getOldestActiveTransactionDuration() {
        TransactionState oldestTX = null;
        for (TransportConnectionState cs : this.connectionStateRegister.listConnectionStates()) {
            Collection<TransactionState> transactions = cs.getTransactionStates();
            for (TransactionState transaction : transactions) {
                if (oldestTX != null && oldestTX.getCreatedAt() >= transaction.getCreatedAt()) continue;
                oldestTX = transaction;
            }
        }
        if (oldestTX == null) {
            return null;
        }
        return System.currentTimeMillis() - oldestTX.getCreatedAt();
    }

    @Override
    public Response processEndTransaction(TransactionInfo info) throws Exception {
        return null;
    }

    @Override
    public Response processPrepareTransaction(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = null;
        if (cs != null) {
            this.context = cs.getContext();
        }
        if (cs == null) {
            throw new NullPointerException("Context is null");
        }
        TransactionState transactionState = cs.getTransactionState(info.getTransactionId());
        if (transactionState == null) {
            throw new IllegalStateException("Cannot prepare a transaction that had not been started or previously returned XA_RDONLY: " + info.getTransactionId());
        }
        if (!transactionState.isPrepared()) {
            transactionState.setPrepared(true);
            int result = this.broker.prepareTransaction(this.context, info.getTransactionId());
            transactionState.setPreparedResult(result);
            if (result == 3) {
                cs.removeTransactionState(info.getTransactionId());
            }
            IntegerResponse response = new IntegerResponse(result);
            return response;
        }
        IntegerResponse response = new IntegerResponse(transactionState.getPreparedResult());
        return response;
    }

    @Override
    public Response processCommitTransactionOnePhase(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = cs.getContext();
        cs.removeTransactionState(info.getTransactionId());
        this.broker.commitTransaction(this.context, info.getTransactionId(), true);
        return null;
    }

    @Override
    public Response processCommitTransactionTwoPhase(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = cs.getContext();
        cs.removeTransactionState(info.getTransactionId());
        this.broker.commitTransaction(this.context, info.getTransactionId(), false);
        return null;
    }

    @Override
    public Response processRollbackTransaction(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = cs.getContext();
        cs.removeTransactionState(info.getTransactionId());
        this.broker.rollbackTransaction(this.context, info.getTransactionId());
        return null;
    }

    @Override
    public Response processForgetTransaction(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = cs.getContext();
        this.broker.forgetTransaction(this.context, info.getTransactionId());
        return null;
    }

    @Override
    public Response processRecoverTransactions(TransactionInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.context = cs.getContext();
        DataStructure[] preparedTransactions = this.broker.getPreparedTransactions(this.context);
        return new DataArrayResponse(preparedTransactions);
    }

    @Override
    public Response processMessage(Message messageSend) throws Exception {
        ProducerId producerId = messageSend.getProducerId();
        ProducerBrokerExchange producerExchange = this.getProducerBrokerExchange(producerId);
        if (producerExchange.canDispatch(messageSend)) {
            this.broker.send(producerExchange, messageSend);
        }
        return null;
    }

    @Override
    public Response processMessageAck(MessageAck ack) throws Exception {
        ConsumerBrokerExchange consumerExchange = this.getConsumerBrokerExchange(ack.getConsumerId());
        if (consumerExchange != null) {
            this.broker.acknowledge(consumerExchange, ack);
        } else if (ack.isInTransaction()) {
            LOG.warn("no matching consumer {}, ignoring ack {}", (Object)consumerExchange, (Object)ack);
        }
        return null;
    }

    @Override
    public Response processMessagePull(MessagePull pull) throws Exception {
        return this.broker.messagePull(this.lookupConnectionState(pull.getConsumerId()).getContext(), pull);
    }

    @Override
    public Response processMessageDispatchNotification(MessageDispatchNotification notification) throws Exception {
        this.broker.processDispatchNotification(notification);
        return null;
    }

    @Override
    public Response processAddDestination(DestinationInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.broker.addDestinationInfo(cs.getContext(), info);
        if (info.getDestination().isTemporary()) {
            cs.addTempDestination(info);
        }
        return null;
    }

    @Override
    public Response processRemoveDestination(DestinationInfo info) throws Exception {
        TransportConnectionState cs = this.lookupConnectionState(info.getConnectionId());
        this.broker.removeDestinationInfo(cs.getContext(), info);
        if (info.getDestination().isTemporary()) {
            cs.removeTempDestination(info.getDestination());
        }
        return null;
    }

    @Override
    public Response processAddProducer(ProducerInfo info) throws Exception {
        SessionId sessionId = info.getProducerId().getParentId();
        ConnectionId connectionId = sessionId.getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs == null) {
            throw new IllegalStateException("Cannot add a producer to a connection that had not been registered: " + connectionId);
        }
        SessionState ss = cs.getSessionState(sessionId);
        if (ss == null) {
            throw new IllegalStateException("Cannot add a producer to a session that had not been registered: " + sessionId);
        }
        if (!ss.getProducerIds().contains(info.getProducerId())) {
            ActiveMQDestination destination = info.getDestination();
            if (!AdvisorySupport.isAdvisoryTopic(destination) && this.getProducerCount(connectionId) >= this.connector.getMaximumProducersAllowedPerConnection()) {
                throw new IllegalStateException("Can't add producer on connection " + connectionId + ": at maximum limit: " + this.connector.getMaximumProducersAllowedPerConnection());
            }
            this.broker.addProducer(cs.getContext(), info);
            try {
                ss.addProducer(info);
            }
            catch (IllegalStateException e) {
                this.broker.removeProducer(cs.getContext(), info);
            }
        }
        return null;
    }

    @Override
    public Response processRemoveProducer(ProducerId id) throws Exception {
        SessionId sessionId = id.getParentId();
        ConnectionId connectionId = sessionId.getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        SessionState ss = cs.getSessionState(sessionId);
        if (ss == null) {
            throw new IllegalStateException("Cannot remove a producer from a session that had not been registered: " + sessionId);
        }
        ProducerState ps = ss.removeProducer(id);
        if (ps == null) {
            throw new IllegalStateException("Cannot remove a producer that had not been registered: " + id);
        }
        this.removeProducerBrokerExchange(id);
        this.broker.removeProducer(cs.getContext(), ps.getInfo());
        return null;
    }

    @Override
    public Response processAddConsumer(ConsumerInfo info) throws Exception {
        SessionId sessionId = info.getConsumerId().getParentId();
        ConnectionId connectionId = sessionId.getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs == null) {
            throw new IllegalStateException("Cannot add a consumer to a connection that had not been registered: " + connectionId);
        }
        SessionState ss = cs.getSessionState(sessionId);
        if (ss == null) {
            throw new IllegalStateException(this.broker.getBrokerName() + " Cannot add a consumer to a session that had not been registered: " + sessionId);
        }
        if (!ss.getConsumerIds().contains(info.getConsumerId())) {
            ActiveMQDestination destination = info.getDestination();
            if (destination != null && !AdvisorySupport.isAdvisoryTopic(destination) && this.getConsumerCount(connectionId) >= this.connector.getMaximumConsumersAllowedPerConnection()) {
                throw new IllegalStateException("Can't add consumer on connection " + connectionId + ": at maximum limit: " + this.connector.getMaximumConsumersAllowedPerConnection());
            }
            this.broker.addConsumer(cs.getContext(), info);
            try {
                ss.addConsumer(info);
                this.addConsumerBrokerExchange(cs, info.getConsumerId());
            }
            catch (IllegalStateException e) {
                this.broker.removeConsumer(cs.getContext(), info);
            }
        }
        return null;
    }

    @Override
    public Response processRemoveConsumer(ConsumerId id, long lastDeliveredSequenceId) throws Exception {
        SessionId sessionId = id.getParentId();
        ConnectionId connectionId = sessionId.getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs == null) {
            throw new IllegalStateException("Cannot remove a consumer from a connection that had not been registered: " + connectionId);
        }
        SessionState ss = cs.getSessionState(sessionId);
        if (ss == null) {
            throw new IllegalStateException("Cannot remove a consumer from a session that had not been registered: " + sessionId);
        }
        ConsumerState consumerState = ss.removeConsumer(id);
        if (consumerState == null) {
            throw new IllegalStateException("Cannot remove a consumer that had not been registered: " + id);
        }
        ConsumerInfo info = consumerState.getInfo();
        info.setLastDeliveredSequenceId(lastDeliveredSequenceId);
        this.broker.removeConsumer(cs.getContext(), consumerState.getInfo());
        this.removeConsumerBrokerExchange(id);
        return null;
    }

    @Override
    public Response processAddSession(SessionInfo info) throws Exception {
        ConnectionId connectionId = info.getSessionId().getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs != null && !cs.getSessionIds().contains(info.getSessionId())) {
            this.broker.addSession(cs.getContext(), info);
            try {
                cs.addSession(info);
            }
            catch (IllegalStateException e) {
                LOG.warn("Failed to add session: {}", (Object)info.getSessionId(), (Object)e);
                this.broker.removeSession(cs.getContext(), info);
            }
        }
        return null;
    }

    @Override
    public Response processRemoveSession(SessionId id, long lastDeliveredSequenceId) throws Exception {
        ConnectionId connectionId = id.getParentId();
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs == null) {
            throw new IllegalStateException("Cannot remove session from connection that had not been registered: " + connectionId);
        }
        SessionState session = cs.getSessionState(id);
        if (session == null) {
            throw new IllegalStateException("Cannot remove session that had not been registered: " + id);
        }
        session.shutdown();
        for (ConsumerId consumerId : session.getConsumerIds()) {
            try {
                this.processRemoveConsumer(consumerId, lastDeliveredSequenceId);
            }
            catch (Throwable e) {
                LOG.warn("Failed to remove consumer: {}", (Object)consumerId, (Object)e);
            }
        }
        for (ProducerId producerId : session.getProducerIds()) {
            try {
                this.processRemoveProducer(producerId);
            }
            catch (Throwable e) {
                LOG.warn("Failed to remove producer: {}", (Object)producerId, (Object)e);
            }
        }
        cs.removeSession(id);
        this.broker.removeSession(cs.getContext(), session.getInfo());
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Response processAddConnection(ConnectionInfo info) throws Exception {
        TransportConnectionState state;
        if (this.wireFormatInfo != null && this.wireFormatInfo.getVersion() <= 2) {
            info.setClientMaster(true);
        }
        Object object = this.brokerConnectionStates;
        synchronized (object) {
            state = (TransportConnectionState)this.brokerConnectionStates.get(info.getConnectionId());
            if (state == null) {
                state = new TransportConnectionState(info, this);
                this.brokerConnectionStates.put(info.getConnectionId(), state);
            }
            state.incrementReference();
        }
        object = state.getConnectionMutex();
        synchronized (object) {
            if (state.getConnection() != this) {
                LOG.debug("Killing previous stale connection: {}", (Object)state.getConnection().getRemoteAddress());
                state.getConnection().stop();
                LOG.debug("Connection {} taking over previous connection: {}", (Object)this.getRemoteAddress(), (Object)state.getConnection().getRemoteAddress());
                state.setConnection(this);
                state.reset(info);
            }
        }
        this.registerConnectionState(info.getConnectionId(), state);
        LOG.debug("Setting up new connection id: {}, address: {}, info: {}", info.getConnectionId(), this.getRemoteAddress(), info);
        this.faultTolerantConnection = info.isFaultTolerant();
        String clientId = info.getClientId();
        this.context = new ConnectionContext();
        this.context.setBroker(this.broker);
        this.context.setClientId(clientId);
        this.context.setClientMaster(info.isClientMaster());
        this.context.setConnection(this);
        this.context.setConnectionId(info.getConnectionId());
        this.context.setConnector(this.connector);
        this.context.setMessageAuthorizationPolicy(this.getMessageAuthorizationPolicy());
        this.context.setNetworkConnection(this.networkConnection);
        this.context.setFaultTolerant(this.faultTolerantConnection);
        this.context.setTransactions(new ConcurrentHashMap<TransactionId, Transaction>());
        this.context.setUserName(info.getUserName());
        this.context.setWireFormatInfo(this.wireFormatInfo);
        this.context.setReconnect(info.isFailoverReconnect());
        this.manageable = info.isManageable();
        this.context.setConnectionState(state);
        state.setContext(this.context);
        state.setConnection(this);
        if (info.getClientIp() == null) {
            info.setClientIp(this.getRemoteAddress());
        }
        try {
            this.broker.addConnection(this.context, info);
        }
        catch (Exception e) {
            Map<ConnectionId, ConnectionState> map = this.brokerConnectionStates;
            synchronized (map) {
                this.brokerConnectionStates.remove(info.getConnectionId());
            }
            this.unregisterConnectionState(info.getConnectionId());
            LOG.warn("Failed to add Connection id={}, clientId={}, clientIP={} due to {}", info.getConnectionId(), clientId, info.getClientIp(), e.getLocalizedMessage());
            this.delayedStop(2000, "Failed with SecurityException: " + e.getLocalizedMessage(), e);
            throw e;
        }
        if (info.isManageable()) {
            ConnectionControl command = this.connector.getConnectionControl();
            command.setFaultTolerant(this.broker.isFaultTolerantConfiguration());
            if (info.isFailoverReconnect()) {
                command.setRebalanceConnection(false);
            }
            this.dispatchAsync(command);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized Response processRemoveConnection(ConnectionId id, long lastDeliveredSequenceId) throws InterruptedException {
        LOG.debug("remove connection id: {}", (Object)id);
        TransportConnectionState cs = this.lookupConnectionState(id);
        if (cs != null) {
            cs.shutdown();
            for (SessionId sessionId : cs.getSessionIds()) {
                try {
                    this.processRemoveSession(sessionId, lastDeliveredSequenceId);
                }
                catch (Throwable e) {
                    SERVICELOG.warn("Failed to remove session {}", (Object)sessionId, (Object)e);
                }
            }
            Iterator<DestinationInfo> iter = cs.getTempDestinations().iterator();
            while (iter.hasNext()) {
                DestinationInfo di = iter.next();
                try {
                    this.broker.removeDestination(cs.getContext(), di.getDestination(), 0L);
                }
                catch (Throwable e) {
                    SERVICELOG.warn("Failed to remove tmp destination {}", (Object)di.getDestination(), (Object)e);
                }
                iter.remove();
            }
            try {
                this.broker.removeConnection(cs.getContext(), cs.getInfo(), this.transportException.get());
            }
            catch (Throwable e) {
                SERVICELOG.warn("Failed to remove connection {}", (Object)cs.getInfo(), (Object)e);
            }
            TransportConnectionState state = this.unregisterConnectionState(id);
            if (state != null) {
                Map<ConnectionId, ConnectionState> map = this.brokerConnectionStates;
                synchronized (map) {
                    if (state.decrementReference() == 0) {
                        this.brokerConnectionStates.remove(id);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Response processProducerAck(ProducerAck ack) throws Exception {
        return null;
    }

    @Override
    public Connector getConnector() {
        return this.connector;
    }

    @Override
    public void dispatchSync(Command message) {
        try {
            this.processDispatch(message);
        }
        catch (IOException e) {
            this.serviceExceptionAsync(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dispatchAsync(Command message) {
        if (!this.stopping.get()) {
            if (this.taskRunner == null) {
                this.dispatchSync(message);
            } else {
                List<Command> list = this.dispatchQueue;
                synchronized (list) {
                    this.dispatchQueue.add(message);
                }
                try {
                    this.taskRunner.wakeup();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } else if (message.isMessageDispatch()) {
            MessageDispatch md = (MessageDispatch)message;
            TransmitCallback sub = md.getTransmitCallback();
            this.broker.postProcessDispatch(md);
            if (sub != null) {
                sub.onFailure();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processDispatch(Command command) throws IOException {
        block13: {
            MessageDispatch messageDispatch = (MessageDispatch)(command.isMessageDispatch() ? command : null);
            try {
                if (this.stopping.get()) break block13;
                if (messageDispatch != null) {
                    try {
                        this.broker.preProcessDispatch(messageDispatch);
                    }
                    catch (RuntimeException convertToIO) {
                        throw new IOException(convertToIO);
                    }
                }
                this.dispatch(command);
            }
            catch (IOException e) {
                if (messageDispatch != null) {
                    TransmitCallback sub = messageDispatch.getTransmitCallback();
                    this.broker.postProcessDispatch(messageDispatch);
                    if (sub != null) {
                        sub.onFailure();
                    }
                    messageDispatch = null;
                    throw e;
                }
                if (TRANSPORTLOG.isDebugEnabled()) {
                    TRANSPORTLOG.debug("Unexpected exception on asyncDispatch, command of type: " + command.getDataStructureType(), e);
                }
            }
            finally {
                if (messageDispatch != null) {
                    TransmitCallback sub = messageDispatch.getTransmitCallback();
                    this.broker.postProcessDispatch(messageDispatch);
                    if (sub != null) {
                        sub.onSuccess();
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean iterate() {
        try {
            if (this.status.get() == 3 || this.stopping.get()) {
                if (this.dispatchStopped.compareAndSet(false, true)) {
                    if (this.transportException.get() == null) {
                        try {
                            this.dispatch(new ShutdownInfo());
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                    }
                    this.dispatchStoppedLatch.countDown();
                }
                return false;
            }
            if (!this.dispatchStopped.get()) {
                Command command = null;
                List<Command> list = this.dispatchQueue;
                synchronized (list) {
                    if (this.dispatchQueue.isEmpty()) {
                        return false;
                    }
                    command = this.dispatchQueue.remove(0);
                }
                this.processDispatch(command);
                return true;
            }
            return false;
        }
        catch (IOException e) {
            if (this.dispatchStopped.compareAndSet(false, true)) {
                this.dispatchStoppedLatch.countDown();
            }
            this.serviceExceptionAsync(e);
            return false;
        }
    }

    @Override
    public ConnectionStatistics getStatistics() {
        return this.statistics;
    }

    public MessageAuthorizationPolicy getMessageAuthorizationPolicy() {
        return this.messageAuthorizationPolicy;
    }

    public void setMessageAuthorizationPolicy(MessageAuthorizationPolicy messageAuthorizationPolicy) {
        this.messageAuthorizationPolicy = messageAuthorizationPolicy;
    }

    @Override
    public boolean isManageable() {
        return this.manageable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws Exception {
        if (this.status.compareAndSet(0, 1)) {
            try {
                TransportConnection transportConnection = this;
                synchronized (transportConnection) {
                    this.taskRunner = this.taskRunnerFactory != null ? this.taskRunnerFactory.createTaskRunner(this, "ActiveMQ Connection Dispatcher: " + this.getRemoteAddress()) : null;
                    this.transport.start();
                    this.active = true;
                    BrokerInfo info = this.connector.getBrokerInfo().copy();
                    if (this.connector.isUpdateClusterClients()) {
                        info.setPeerBrokerInfos(this.broker.getPeerBrokerInfos());
                    } else {
                        info.setPeerBrokerInfos(null);
                    }
                    this.dispatchAsync(info);
                    this.connector.onStarted(this);
                }
            }
            catch (Exception e) {
                this.status.set(3);
                throw e;
            }
            finally {
                if (!this.status.compareAndSet(1, 2)) {
                    LOG.debug("Calling the delayed stop() after start() {}", (Object)this);
                    this.stop();
                }
            }
        }
    }

    @Override
    public void stop() throws Exception {
        this.stopAsync();
        while (!this.stopped.await(5L, TimeUnit.SECONDS)) {
            LOG.info("The connection to '{}' is taking a long time to shutdown.", (Object)this.transport.getRemoteAddress());
        }
    }

    public void delayedStop(final int waitTime, final String reason, Throwable cause) {
        if (waitTime > 0) {
            this.status.compareAndSet(1, 3);
            this.transportException.set(cause);
            try {
                this.stopTaskRunnerFactory.execute(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(waitTime);
                            TransportConnection.this.stopAsync();
                            LOG.info("Stopping {} because {}", (Object)TransportConnection.this.transport.getRemoteAddress(), (Object)reason);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                });
            }
            catch (Throwable t2) {
                LOG.warn("Cannot create stopAsync. This exception will be ignored.", t2);
            }
        }
    }

    public void stopAsync(Throwable cause) {
        this.transportException.set(cause);
        this.stopAsync();
    }

    public void stopAsync() {
        if (this.status.compareAndSet(1, 3)) {
            LOG.debug("stopAsync() called in the middle of start(). Delaying till start completes..");
            return;
        }
        if (this.stopping.compareAndSet(false, true)) {
            List<TransportConnectionState> connectionStates = this.listConnectionStates();
            for (TransportConnectionState cs : connectionStates) {
                ConnectionContext connectionContext = cs.getContext();
                if (connectionContext == null) continue;
                connectionContext.getStopping().set(true);
            }
            try {
                this.stopTaskRunnerFactory.execute(new Runnable(){

                    @Override
                    public void run() {
                        TransportConnection.this.serviceLock.writeLock().lock();
                        try {
                            TransportConnection.this.doStop();
                        }
                        catch (Throwable e) {
                            LOG.debug("Error occurred while shutting down a connection {}", (Object)this, (Object)e);
                        }
                        finally {
                            TransportConnection.this.stopped.countDown();
                            TransportConnection.this.serviceLock.writeLock().unlock();
                        }
                    }
                });
            }
            catch (Throwable t2) {
                LOG.warn("Cannot create async transport stopper thread. This exception is ignored. Not waiting for stop to complete", t2);
                this.stopped.countDown();
            }
        }
    }

    public String toString() {
        return "Transport Connection to: " + this.transport.getRemoteAddress();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doStop() throws Exception {
        LOG.debug("Stopping connection: {}", (Object)this.transport.getRemoteAddress());
        this.connector.onStopped(this);
        try {
            TransportConnection transportConnection = this;
            synchronized (transportConnection) {
                if (this.duplexBridge != null) {
                    this.duplexBridge.stop();
                }
            }
        }
        catch (Exception ignore) {
            LOG.trace("Exception caught stopping. This exception is ignored.", ignore);
        }
        try {
            this.transport.stop();
            LOG.debug("Stopped transport: {}", (Object)this.transport.getRemoteAddress());
        }
        catch (Exception e) {
            LOG.debug("Could not stop transport to {}. This exception is ignored.", (Object)this.transport.getRemoteAddress(), (Object)e);
        }
        if (this.taskRunner != null) {
            this.taskRunner.shutdown(1L);
            this.taskRunner = null;
        }
        this.active = false;
        List<Command> e = this.dispatchQueue;
        synchronized (e) {
            for (Command command : this.dispatchQueue) {
                if (!command.isMessageDispatch()) continue;
                MessageDispatch md = (MessageDispatch)command;
                TransmitCallback sub = md.getTransmitCallback();
                this.broker.postProcessDispatch(md);
                if (sub == null) continue;
                sub.onFailure();
            }
            this.dispatchQueue.clear();
        }
        if (!this.broker.isStopped()) {
            List<TransportConnectionState> connectionStates = this.listConnectionStates();
            connectionStates = this.listConnectionStates();
            for (TransportConnectionState cs : connectionStates) {
                cs.getContext().getStopping().set(true);
                try {
                    LOG.debug("Cleaning up connection resources: {}", (Object)this.getRemoteAddress());
                    this.processRemoveConnection(cs.getInfo().getConnectionId(), -2L);
                }
                catch (Throwable ignore) {
                    LOG.debug("Exception caught removing connection {}. This exception is ignored.", (Object)cs.getInfo().getConnectionId(), (Object)ignore);
                }
            }
        }
        LOG.debug("Connection Stopped: {}", (Object)this.getRemoteAddress());
    }

    public boolean isBlockedCandidate() {
        return this.blockedCandidate;
    }

    public void setBlockedCandidate(boolean blockedCandidate) {
        this.blockedCandidate = blockedCandidate;
    }

    public boolean isMarkedCandidate() {
        return this.markedCandidate;
    }

    public void setMarkedCandidate(boolean markedCandidate) {
        this.markedCandidate = markedCandidate;
        if (!markedCandidate) {
            this.timeStamp = 0L;
            this.blockedCandidate = false;
        }
    }

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    @Override
    public boolean isSlow() {
        return this.slow;
    }

    public boolean isMarkedBlockedCandidate() {
        return this.markedCandidate;
    }

    public void doMark() {
        if (this.timeStamp == 0L) {
            this.timeStamp = System.currentTimeMillis();
        }
    }

    @Override
    public boolean isBlocked() {
        return this.blocked;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setConnected(boolean connected2) {
        this.connected = connected2;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isStarting() {
        return this.status.get() == 1;
    }

    @Override
    public synchronized boolean isNetworkConnection() {
        return this.networkConnection;
    }

    @Override
    public boolean isFaultTolerantConnection() {
        return this.faultTolerantConnection;
    }

    public boolean isPendingStop() {
        return this.status.get() == 3;
    }

    private NetworkBridgeConfiguration getNetworkConfiguration(BrokerInfo info) throws IOException {
        Properties properties = MarshallingSupport.stringToProperties(info.getNetworkProperties());
        HashMap<String, String> props = this.createMap(properties);
        NetworkBridgeConfiguration config = new NetworkBridgeConfiguration();
        IntrospectionSupport.setProperties((Object)config, props, "");
        return config;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public Response processBrokerInfo(BrokerInfo info) {
        if (info.isSlaveBroker()) {
            TransportConnection.LOG.error(" Slave Brokers are no longer supported - slave trying to attach is: {}", (Object)info.getBrokerName());
        } else if (info.isNetworkConnection() && !info.isDuplexConnection()) {
            try {
                config = this.getNetworkConfiguration(info);
                if (!config.isSyncDurableSubs() || this.protocolVersion.get() < 12) ** GOTO lbl58
                TransportConnection.LOG.debug("SyncDurableSubs is enabled, Sending BrokerSubscriptionInfo");
                this.dispatchSync(NetworkBridgeUtils.getBrokerSubscriptionInfo(this.broker.getBrokerService(), config));
            }
            catch (Exception e) {
                TransportConnection.LOG.error("Failed to respond to network bridge creation from broker {}", (Object)info.getBrokerId(), (Object)e);
                return null;
            }
        } else if (info.isNetworkConnection() && info.isDuplexConnection()) {
            try {
                config = this.getNetworkConfiguration(info);
                config.setBrokerName(this.broker.getBrokerName());
                if (config.isSyncDurableSubs() && this.protocolVersion.get() >= 12) {
                    TransportConnection.LOG.debug("SyncDurableSubs is enabled, Sending BrokerSubscriptionInfo");
                    this.dispatchSync(NetworkBridgeUtils.getBrokerSubscriptionInfo(this.broker.getBrokerService(), config));
                }
                duplexNetworkConnectorId = config.getName() + "@" + info.getBrokerId();
                var5_11 = connections = this.connector.getConnections();
                synchronized (var5_11) {
                    for (TransportConnection c : connections) {
                        if (c == this || !duplexNetworkConnectorId.equals(c.getDuplexNetworkConnectorId())) continue;
                        TransportConnection.LOG.warn("Stopping an existing active duplex connection [{}] for network connector ({}).", (Object)c, (Object)duplexNetworkConnectorId);
                        c.stopAsync();
                        c.getStopped().await(1L, TimeUnit.SECONDS);
                    }
                    this.setDuplexNetworkConnectorId(duplexNetworkConnectorId);
                }
                localTransport = NetworkBridgeFactory.createLocalTransport(config, this.broker.getVmConnectorURI());
                remoteBridgeTransport = this.transport;
                if (!(remoteBridgeTransport instanceof ResponseCorrelator)) {
                    remoteBridgeTransport = new ResponseCorrelator(remoteBridgeTransport);
                }
                if ((duplexName = localTransport.toString()).contains("#")) {
                    duplexName = duplexName.substring(duplexName.lastIndexOf("#"));
                }
                listener = new MBeanNetworkListener(this.brokerService, config, this.brokerService.createDuplexNetworkConnectorObjectName(duplexName));
                listener.setCreatedByDuplex(true);
                this.duplexBridge = config.getBridgeFactory().createNetworkBridge(config, localTransport, remoteBridgeTransport, listener);
                this.duplexBridge.setBrokerService(this.brokerService);
                this.duplexBridge.setDurableDestinations(NetworkConnector.getDurableTopicDestinations(this.broker.getDurableDestinations()));
                info.setDuplexConnection(false);
                this.duplexBridge.setCreatedByDuplex(true);
                this.duplexBridge.duplexStart(this, this.brokerInfo, info);
                TransportConnection.LOG.info("Started responder end of duplex bridge {}", (Object)duplexNetworkConnectorId);
                return null;
            }
            catch (TransportDisposedIOException e) {
                TransportConnection.LOG.warn("Duplex bridge {} was stopped before it was correctly started.", (Object)this.duplexNetworkConnectorId);
                return null;
            }
            catch (Exception e) {
                TransportConnection.LOG.error("Failed to create responder end of duplex network bridge {}", (Object)this.duplexNetworkConnectorId, (Object)e);
                return null;
            }
        }
lbl58:
        // 5 sources

        if (this.brokerInfo != null) {
            TransportConnection.LOG.warn("Unexpected extra broker info command received: {}", (Object)info);
        }
        this.brokerInfo = info;
        this.networkConnection = true;
        connectionStates = this.listConnectionStates();
        for (TransportConnectionState cs : connectionStates) {
            cs.getContext().setNetworkConnection(true);
        }
        return null;
    }

    private HashMap<String, String> createMap(Properties properties) {
        return new HashMap<Object, Object>(properties);
    }

    protected void dispatch(Command command) throws IOException {
        try {
            this.setMarkedCandidate(true);
            this.transport.oneway(command);
        }
        finally {
            this.setMarkedCandidate(false);
        }
    }

    @Override
    public String getRemoteAddress() {
        return this.transport.getRemoteAddress();
    }

    public Transport getTransport() {
        return this.transport;
    }

    @Override
    public String getConnectionId() {
        List<TransportConnectionState> connectionStates = this.listConnectionStates();
        Iterator<TransportConnectionState> iterator2 = connectionStates.iterator();
        if (iterator2.hasNext()) {
            TransportConnectionState cs = iterator2.next();
            if (cs.getInfo().getClientId() != null) {
                return cs.getInfo().getClientId();
            }
            return cs.getInfo().getConnectionId().toString();
        }
        return null;
    }

    @Override
    public void updateClient(ConnectionControl control) {
        if (this.isActive() && !this.isBlocked() && this.isFaultTolerantConnection() && this.wireFormatInfo != null && this.wireFormatInfo.getVersion() >= 6) {
            this.dispatchAsync(control);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProducerBrokerExchange getProducerBrokerExchangeIfExists(ProducerInfo producerInfo) {
        ProducerBrokerExchange result = null;
        if (producerInfo != null && producerInfo.getProducerId() != null) {
            Map<ProducerId, ProducerBrokerExchange> map = this.producerExchanges;
            synchronized (map) {
                result = this.producerExchanges.get(producerInfo.getProducerId());
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ProducerBrokerExchange getProducerBrokerExchange(ProducerId id) throws IOException {
        ProducerBrokerExchange result = this.producerExchanges.get(id);
        if (result == null) {
            Map<ProducerId, ProducerBrokerExchange> map = this.producerExchanges;
            synchronized (map) {
                SessionState ss;
                result = new ProducerBrokerExchange();
                TransportConnectionState state = this.lookupConnectionState(id);
                this.context = state.getContext();
                result.setConnectionContext(this.context);
                if (this.context.isReconnect() || this.context.isNetworkConnection() && this.connector.isAuditNetworkProducers()) {
                    result.setLastStoredSequenceId(this.brokerService.getPersistenceAdapter().getLastProducerSequenceId(id));
                }
                if ((ss = state.getSessionState(id.getParentId())) != null) {
                    result.setProducerState(ss.getProducerState(id));
                    ProducerState producerState = ss.getProducerState(id);
                    if (producerState != null && producerState.getInfo() != null) {
                        ProducerInfo info = producerState.getInfo();
                        result.setMutable(info.getDestination() == null || info.getDestination().isComposite());
                    }
                }
                this.producerExchanges.put(id, result);
            }
        } else {
            this.context = result.getConnectionContext();
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeProducerBrokerExchange(ProducerId id) {
        Map<ProducerId, ProducerBrokerExchange> map = this.producerExchanges;
        synchronized (map) {
            this.producerExchanges.remove(id);
        }
    }

    private ConsumerBrokerExchange getConsumerBrokerExchange(ConsumerId id) {
        ConsumerBrokerExchange result = this.consumerExchanges.get(id);
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConsumerBrokerExchange addConsumerBrokerExchange(TransportConnectionState connectionState, ConsumerId id) {
        ConsumerBrokerExchange result = this.consumerExchanges.get(id);
        if (result == null) {
            Map<ConsumerId, ConsumerBrokerExchange> map = this.consumerExchanges;
            synchronized (map) {
                ConsumerInfo info;
                ConsumerState cs;
                result = new ConsumerBrokerExchange();
                this.context = connectionState.getContext();
                result.setConnectionContext(this.context);
                SessionState ss = connectionState.getSessionState(id.getParentId());
                if (ss != null && (cs = ss.getConsumerState(id)) != null && (info = cs.getInfo()) != null && info.getDestination() != null && info.getDestination().isPattern()) {
                    result.setWildcard(true);
                }
                this.consumerExchanges.put(id, result);
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeConsumerBrokerExchange(ConsumerId id) {
        Map<ConsumerId, ConsumerBrokerExchange> map = this.consumerExchanges;
        synchronized (map) {
            this.consumerExchanges.remove(id);
        }
    }

    public int getProtocolVersion() {
        return this.protocolVersion.get();
    }

    @Override
    public Response processControlCommand(ControlCommand command) throws Exception {
        return null;
    }

    @Override
    public Response processMessageDispatch(MessageDispatch dispatch) throws Exception {
        return null;
    }

    @Override
    public Response processConnectionControl(ConnectionControl control) throws Exception {
        if (control != null) {
            this.faultTolerantConnection = control.isFaultTolerant();
        }
        return null;
    }

    @Override
    public Response processConnectionError(ConnectionError error) throws Exception {
        return null;
    }

    @Override
    public Response processConsumerControl(ConsumerControl control) throws Exception {
        ConsumerBrokerExchange consumerExchange = this.getConsumerBrokerExchange(control.getConsumerId());
        this.broker.processConsumerControl(consumerExchange, control);
        return null;
    }

    protected synchronized TransportConnectionState registerConnectionState(ConnectionId connectionId, TransportConnectionState state) {
        TransportConnectionState cs = null;
        if (!this.connectionStateRegister.isEmpty() && !this.connectionStateRegister.doesHandleMultipleConnectionStates()) {
            MapTransportConnectionStateRegister newRegister = new MapTransportConnectionStateRegister();
            newRegister.intialize(this.connectionStateRegister);
            this.connectionStateRegister = newRegister;
        }
        cs = this.connectionStateRegister.registerConnectionState(connectionId, state);
        return cs;
    }

    protected synchronized TransportConnectionState unregisterConnectionState(ConnectionId connectionId) {
        return this.connectionStateRegister.unregisterConnectionState(connectionId);
    }

    protected synchronized List<TransportConnectionState> listConnectionStates() {
        return this.connectionStateRegister.listConnectionStates();
    }

    protected synchronized TransportConnectionState lookupConnectionState(String connectionId) {
        return this.connectionStateRegister.lookupConnectionState(connectionId);
    }

    protected synchronized TransportConnectionState lookupConnectionState(ConsumerId id) {
        return this.connectionStateRegister.lookupConnectionState(id);
    }

    protected synchronized TransportConnectionState lookupConnectionState(ProducerId id) {
        return this.connectionStateRegister.lookupConnectionState(id);
    }

    protected synchronized TransportConnectionState lookupConnectionState(SessionId id) {
        return this.connectionStateRegister.lookupConnectionState(id);
    }

    public synchronized TransportConnectionState lookupConnectionState(ConnectionId connectionId) {
        return this.connectionStateRegister.lookupConnectionState(connectionId);
    }

    protected synchronized void setDuplexNetworkConnectorId(String duplexNetworkConnectorId) {
        this.duplexNetworkConnectorId = duplexNetworkConnectorId;
    }

    protected synchronized String getDuplexNetworkConnectorId() {
        return this.duplexNetworkConnectorId;
    }

    public boolean isStopping() {
        return this.stopping.get();
    }

    protected CountDownLatch getStopped() {
        return this.stopped;
    }

    private int getProducerCount(ConnectionId connectionId) {
        int result = 0;
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs != null) {
            for (SessionId sessionId : cs.getSessionIds()) {
                SessionState sessionState = cs.getSessionState(sessionId);
                if (sessionState == null) continue;
                result += sessionState.getProducerIds().size();
            }
        }
        return result;
    }

    private int getConsumerCount(ConnectionId connectionId) {
        int result = 0;
        TransportConnectionState cs = this.lookupConnectionState(connectionId);
        if (cs != null) {
            for (SessionId sessionId : cs.getSessionIds()) {
                SessionState sessionState = cs.getSessionState(sessionId);
                if (sessionState == null) continue;
                result += sessionState.getConsumerIds().size();
            }
        }
        return result;
    }

    public WireFormatInfo getRemoteWireFormatInfo() {
        return this.wireFormatInfo;
    }

    @Override
    public Response processBrokerSubscriptionInfo(BrokerSubscriptionInfo info) throws Exception {
        return null;
    }
}

