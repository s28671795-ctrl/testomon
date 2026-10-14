/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.XAConnection;
import org.apache.activemq.ActiveMQConnectionConsumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQConnectionMetaData;
import org.apache.activemq.ActiveMQDispatcher;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.ActiveMQTopicSession;
import org.apache.activemq.AdvisoryConsumer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ClientInternalExceptionListener;
import org.apache.activemq.Closeable;
import org.apache.activemq.ConnectionAudit;
import org.apache.activemq.ConnectionClosedException;
import org.apache.activemq.ConnectionFailedException;
import org.apache.activemq.EnhancedConnection;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.ConnectionError;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ControlCommand;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.management.JMSConnectionStatsImpl;
import org.apache.activemq.management.JMSStatsImpl;
import org.apache.activemq.management.StatsCapable;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.state.CommandVisitorAdapter;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.RequestTimedOutIOException;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.transport.failover.FailoverTransport;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQConnection
implements Connection,
TopicConnection,
QueueConnection,
StatsCapable,
Closeable,
TransportListener,
EnhancedConnection {
    public static final String DEFAULT_USER = ActiveMQConnectionFactory.DEFAULT_USER;
    public static final String DEFAULT_PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    public static final String DEFAULT_BROKER_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    public static int DEFAULT_THREAD_POOL_SIZE = 1000;
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQConnection.class);
    public final ConcurrentMap<ActiveMQTempDestination, ActiveMQTempDestination> activeTempDestinations = new ConcurrentHashMap<ActiveMQTempDestination, ActiveMQTempDestination>();
    protected boolean dispatchAsync = true;
    protected boolean alwaysSessionAsync = true;
    private TaskRunnerFactory sessionTaskRunner;
    private final ThreadPoolExecutor executor;
    private final ConnectionInfo info;
    private ExceptionListener exceptionListener;
    private ClientInternalExceptionListener clientInternalExceptionListener;
    private boolean clientIDSet;
    private boolean isConnectionInfoSentToBroker;
    private boolean userSpecifiedClientID;
    private ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
    private BlobTransferPolicy blobTransferPolicy;
    private RedeliveryPolicyMap redeliveryPolicyMap;
    private MessageTransformer transformer;
    private boolean disableTimeStampsByDefault;
    private boolean optimizedMessageDispatch = true;
    private boolean copyMessageOnSend = true;
    private boolean useCompression;
    private boolean objectMessageSerializationDefered;
    private boolean useAsyncSend;
    private boolean optimizeAcknowledge;
    private long optimizeAcknowledgeTimeOut = 0L;
    private long optimizedAckScheduledAckInterval = 0L;
    private boolean nestedMapAndListEnabled = true;
    private boolean useRetroactiveConsumer;
    private boolean exclusiveConsumer;
    private boolean alwaysSyncSend;
    private int closeTimeout = 15000;
    private boolean watchTopicAdvisories = true;
    private long warnAboutUnstartedConnectionTimeout = 500L;
    private int sendTimeout = 0;
    private boolean sendAcksAsync = true;
    private boolean checkForDuplicates = true;
    private boolean queueOnlyConnection = false;
    private boolean consumerExpiryCheckEnabled = true;
    private final Transport transport;
    private final IdGenerator clientIdGenerator;
    private final JMSStatsImpl factoryStats;
    private final JMSConnectionStatsImpl stats;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean closing = new AtomicBoolean(false);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicBoolean transportFailed = new AtomicBoolean(false);
    private final CopyOnWriteArrayList<ActiveMQSession> sessions = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<ActiveMQConnectionConsumer> connectionConsumers = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<TransportListener> transportListeners = new CopyOnWriteArrayList();
    private final ConcurrentMap<ConsumerId, ActiveMQDispatcher> dispatchers = new ConcurrentHashMap<ConsumerId, ActiveMQDispatcher>();
    private final ConcurrentMap<ProducerId, ActiveMQMessageProducer> producers = new ConcurrentHashMap<ProducerId, ActiveMQMessageProducer>();
    private final LongSequenceGenerator sessionIdGenerator = new LongSequenceGenerator();
    private final SessionId connectionSessionId;
    private final LongSequenceGenerator consumerIdGenerator = new LongSequenceGenerator();
    private final LongSequenceGenerator tempDestinationIdGenerator = new LongSequenceGenerator();
    private final LongSequenceGenerator localTransactionIdGenerator = new LongSequenceGenerator();
    private AdvisoryConsumer advisoryConsumer;
    private final CountDownLatch brokerInfoReceived = new CountDownLatch(1);
    private BrokerInfo brokerInfo;
    private IOException firstFailureError;
    private int producerWindowSize = 0;
    private final AtomicInteger protocolVersion = new AtomicInteger(12);
    private final long timeCreated;
    private final ConnectionAudit connectionAudit = new ConnectionAudit();
    private DestinationSource destinationSource;
    private final Object ensureConnectionInfoSentMutex = new Object();
    private boolean useDedicatedTaskRunner;
    protected AtomicInteger transportInterruptionProcessingComplete = new AtomicInteger(0);
    private long consumerFailoverRedeliveryWaitPeriod;
    private volatile Scheduler scheduler;
    private boolean messagePrioritySupported = false;
    private boolean transactedIndividualAck = false;
    private boolean nonBlockingRedelivery = false;
    private boolean rmIdFromConnectionId = false;
    private int maxThreadPoolSize = DEFAULT_THREAD_POOL_SIZE;
    private RejectedExecutionHandler rejectedTaskHandler = null;
    private List<String> trustedPackages = new ArrayList<String>();
    private boolean trustAllPackages = false;
    private int connectResponseTimeout;

    protected ActiveMQConnection(final Transport transport, IdGenerator clientIdGenerator, IdGenerator connectionIdGenerator, JMSStatsImpl factoryStats) throws Exception {
        this.transport = transport;
        this.clientIdGenerator = clientIdGenerator;
        this.factoryStats = factoryStats;
        this.executor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){

            @Override
            public Thread newThread(Runnable r) {
                Thread thread2 = new Thread(r, "ActiveMQ Connection Executor: " + transport);
                return thread2;
            }
        });
        String uniqueId = connectionIdGenerator.generateId();
        this.info = new ConnectionInfo(new ConnectionId(uniqueId));
        this.info.setManageable(true);
        this.info.setFaultTolerant(transport.isFaultTolerant());
        this.connectionSessionId = new SessionId(this.info.getConnectionId(), -1L);
        this.transport.setTransportListener(this);
        this.stats = new JMSConnectionStatsImpl(this.sessions, this instanceof XAConnection);
        this.factoryStats.addConnection(this);
        this.timeCreated = System.currentTimeMillis();
        this.connectionAudit.setCheckForDuplicates(transport.isFaultTolerant());
    }

    protected void setUserName(String userName) {
        this.info.setUserName(userName);
    }

    protected void setPassword(String password) {
        this.info.setPassword(password);
    }

    public static ActiveMQConnection makeConnection() throws JMSException {
        ActiveMQConnectionFactory factory2 = new ActiveMQConnectionFactory();
        return (ActiveMQConnection)factory2.createConnection();
    }

    public static ActiveMQConnection makeConnection(String uri) throws JMSException, URISyntaxException {
        ActiveMQConnectionFactory factory2 = new ActiveMQConnectionFactory(uri);
        return (ActiveMQConnection)factory2.createConnection();
    }

    public static ActiveMQConnection makeConnection(String user, String password, String uri) throws JMSException, URISyntaxException {
        ActiveMQConnectionFactory factory2 = new ActiveMQConnectionFactory(user, password, new URI(uri));
        return (ActiveMQConnection)factory2.createConnection();
    }

    public JMSConnectionStatsImpl getConnectionStats() {
        return this.stats;
    }

    @Override
    public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
        this.checkClosedOrFailed();
        this.ensureConnectionInfoSent();
        if (!transacted) {
            if (acknowledgeMode == 0) {
                throw new JMSException("acknowledgeMode SESSION_TRANSACTED cannot be used for an non-transacted Session");
            }
            if (acknowledgeMode < 0 || acknowledgeMode > 4) {
                throw new JMSException("invalid acknowledgeMode: " + acknowledgeMode + ". Valid values are Session.AUTO_ACKNOWLEDGE (1), Session.CLIENT_ACKNOWLEDGE (2), Session.DUPS_OK_ACKNOWLEDGE (3), ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE (4) or for transacted sessions Session.SESSION_TRANSACTED (0)");
            }
        }
        return new ActiveMQSession(this, this.getNextSessionId(), transacted ? 0 : acknowledgeMode, this.isDispatchAsync(), this.isAlwaysSessionAsync());
    }

    protected SessionId getNextSessionId() {
        return new SessionId(this.info.getConnectionId(), this.sessionIdGenerator.getNextSequenceId());
    }

    @Override
    public String getClientID() throws JMSException {
        this.checkClosedOrFailed();
        return this.info.getClientId();
    }

    @Override
    public void setClientID(String newClientID) throws JMSException {
        this.checkClosedOrFailed();
        if (this.clientIDSet) {
            throw new IllegalStateException("The clientID has already been set");
        }
        if (this.isConnectionInfoSentToBroker) {
            throw new IllegalStateException("Setting clientID on a used Connection is not allowed");
        }
        this.info.setClientId(newClientID);
        this.userSpecifiedClientID = true;
        this.ensureConnectionInfoSent();
    }

    public void setDefaultClientID(String clientID) throws JMSException {
        this.info.setClientId(clientID);
        this.userSpecifiedClientID = true;
    }

    @Override
    public ConnectionMetaData getMetaData() throws JMSException {
        this.checkClosedOrFailed();
        return ActiveMQConnectionMetaData.INSTANCE;
    }

    @Override
    public ExceptionListener getExceptionListener() throws JMSException {
        this.checkClosedOrFailed();
        return this.exceptionListener;
    }

    @Override
    public void setExceptionListener(ExceptionListener listener) throws JMSException {
        this.checkClosedOrFailed();
        this.exceptionListener = listener;
    }

    public ClientInternalExceptionListener getClientInternalExceptionListener() {
        return this.clientInternalExceptionListener;
    }

    public void setClientInternalExceptionListener(ClientInternalExceptionListener listener) {
        this.clientInternalExceptionListener = listener;
    }

    @Override
    public void start() throws JMSException {
        this.checkClosedOrFailed();
        this.ensureConnectionInfoSent();
        if (this.started.compareAndSet(false, true)) {
            for (ActiveMQSession session : this.sessions) {
                session.start();
            }
        }
    }

    @Override
    public void stop() throws JMSException {
        this.doStop(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void doStop(boolean checkClosed) throws JMSException {
        if (checkClosed) {
            this.checkClosedOrFailed();
        }
        if (this.started.compareAndSet(true, false)) {
            CopyOnWriteArrayList<ActiveMQSession> copyOnWriteArrayList = this.sessions;
            synchronized (copyOnWriteArrayList) {
                for (ActiveMQSession s2 : this.sessions) {
                    s2.stop();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws JMSException {
        try {
            if (!this.closed.get() && !this.transportFailed.get()) {
                this.doStop(false);
            }
            ActiveMQConnection activeMQConnection = this;
            synchronized (activeMQConnection) {
                block26: {
                    if (!this.closed.get()) {
                        Scheduler scheduler;
                        this.closing.set(true);
                        if (this.destinationSource != null) {
                            this.destinationSource.stop();
                            this.destinationSource = null;
                        }
                        if (this.advisoryConsumer != null) {
                            this.advisoryConsumer.dispose();
                            this.advisoryConsumer = null;
                        }
                        if ((scheduler = this.scheduler) != null) {
                            try {
                                scheduler.stop();
                            }
                            catch (Exception e) {
                                JMSException ex = JMSExceptionSupport.create(e);
                                throw ex;
                            }
                        }
                        long lastDeliveredSequenceId = -1L;
                        for (ActiveMQSession s2 : this.sessions) {
                            s2.dispose();
                            lastDeliveredSequenceId = Math.max(lastDeliveredSequenceId, s2.getLastDeliveredSequenceId());
                        }
                        for (ActiveMQConnectionConsumer c : this.connectionConsumers) {
                            c.dispose();
                        }
                        this.activeTempDestinations.clear();
                        try {
                            block27: {
                                if (!this.isConnectionInfoSentToBroker) break block26;
                                RemoveInfo removeCommand = this.info.createRemoveCommand();
                                removeCommand.setLastDeliveredSequenceId(lastDeliveredSequenceId);
                                try {
                                    this.syncSendPacket((Command)removeCommand, this.closeTimeout);
                                }
                                catch (JMSException e) {
                                    if (e.getCause() instanceof RequestTimedOutIOException) break block27;
                                    throw e;
                                }
                            }
                            this.doAsyncSendPacket(new ShutdownInfo());
                        }
                        finally {
                            this.started.set(false);
                            if (this.sessionTaskRunner != null) {
                                this.sessionTaskRunner.shutdown();
                            }
                            this.closed.set(true);
                            this.closing.set(false);
                        }
                    }
                }
            }
        }
        finally {
            try {
                if (this.executor != null) {
                    ThreadPoolUtils.shutdown(this.executor);
                }
            }
            catch (Throwable e) {
                LOG.warn("Error shutting down thread pool: " + this.executor + ". This exception will be ignored.", e);
            }
            ServiceSupport.dispose(this.transport);
            this.factoryStats.removeConnection(this);
        }
    }

    @Override
    public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        return this.createDurableConnectionConsumer(topic, subscriptionName, messageSelector, sessionPool, maxMessages, false);
    }

    public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool sessionPool, int maxMessages, boolean noLocal) throws JMSException {
        this.checkClosedOrFailed();
        if (this.queueOnlyConnection) {
            throw new IllegalStateException("QueueConnection cannot be used to create Pub/Sub based resources.");
        }
        this.ensureConnectionInfoSent();
        SessionId sessionId = new SessionId(this.info.getConnectionId(), -1L);
        ConsumerInfo info = new ConsumerInfo(new ConsumerId(sessionId, this.consumerIdGenerator.getNextSequenceId()));
        info.setDestination(ActiveMQMessageTransformation.transformDestination(topic));
        info.setSubscriptionName(subscriptionName);
        info.setSelector(messageSelector);
        info.setPrefetchSize(maxMessages);
        info.setDispatchAsync(this.isDispatchAsync());
        if (info.getDestination().getOptions() != null) {
            HashMap<String, String> options = new HashMap<String, String>(info.getDestination().getOptions());
            IntrospectionSupport.setProperties((Object)this.info, options, "consumer.");
        }
        return new ActiveMQConnectionConsumer(this, sessionPool, info);
    }

    public boolean isStarted() {
        return this.started.get();
    }

    public boolean isClosed() {
        return this.closed.get();
    }

    public boolean isClosing() {
        return this.closing.get();
    }

    public boolean isTransportFailed() {
        return this.transportFailed.get();
    }

    public ActiveMQPrefetchPolicy getPrefetchPolicy() {
        return this.prefetchPolicy;
    }

    public void setPrefetchPolicy(ActiveMQPrefetchPolicy prefetchPolicy) {
        this.prefetchPolicy = prefetchPolicy;
    }

    public Transport getTransportChannel() {
        return this.transport;
    }

    public String getInitializedClientID() throws JMSException {
        this.ensureConnectionInfoSent();
        return this.info.getClientId();
    }

    public boolean isDisableTimeStampsByDefault() {
        return this.disableTimeStampsByDefault;
    }

    public void setDisableTimeStampsByDefault(boolean timeStampsDisableByDefault) {
        this.disableTimeStampsByDefault = timeStampsDisableByDefault;
    }

    public boolean isOptimizedMessageDispatch() {
        return this.optimizedMessageDispatch;
    }

    public void setOptimizedMessageDispatch(boolean dispatchOptimizedMessage) {
        this.optimizedMessageDispatch = dispatchOptimizedMessage;
    }

    public int getCloseTimeout() {
        return this.closeTimeout;
    }

    public void setCloseTimeout(int closeTimeout) {
        this.closeTimeout = closeTimeout;
    }

    public ConnectionInfo getConnectionInfo() {
        return this.info;
    }

    public boolean isUseRetroactiveConsumer() {
        return this.useRetroactiveConsumer;
    }

    public void setUseRetroactiveConsumer(boolean useRetroactiveConsumer) {
        this.useRetroactiveConsumer = useRetroactiveConsumer;
    }

    public boolean isNestedMapAndListEnabled() {
        return this.nestedMapAndListEnabled;
    }

    public void setNestedMapAndListEnabled(boolean structuredMapsEnabled) {
        this.nestedMapAndListEnabled = structuredMapsEnabled;
    }

    public boolean isExclusiveConsumer() {
        return this.exclusiveConsumer;
    }

    public void setExclusiveConsumer(boolean exclusiveConsumer) {
        this.exclusiveConsumer = exclusiveConsumer;
    }

    public void addTransportListener(TransportListener transportListener) {
        this.transportListeners.add(transportListener);
    }

    public void removeTransportListener(TransportListener transportListener) {
        this.transportListeners.remove(transportListener);
    }

    public boolean isUseDedicatedTaskRunner() {
        return this.useDedicatedTaskRunner;
    }

    public void setUseDedicatedTaskRunner(boolean useDedicatedTaskRunner) {
        this.useDedicatedTaskRunner = useDedicatedTaskRunner;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TaskRunnerFactory getSessionTaskRunner() {
        ActiveMQConnection activeMQConnection = this;
        synchronized (activeMQConnection) {
            if (this.sessionTaskRunner == null) {
                this.sessionTaskRunner = new TaskRunnerFactory("ActiveMQ Session Task", 7, false, 1000, this.isUseDedicatedTaskRunner(), this.maxThreadPoolSize);
                this.sessionTaskRunner.setRejectedTaskHandler(this.rejectedTaskHandler);
            }
        }
        return this.sessionTaskRunner;
    }

    public void setSessionTaskRunner(TaskRunnerFactory sessionTaskRunner) {
        this.sessionTaskRunner = sessionTaskRunner;
    }

    public MessageTransformer getTransformer() {
        return this.transformer;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    public boolean isStatsEnabled() {
        return this.stats.isEnabled();
    }

    public void setStatsEnabled(boolean statsEnabled) {
        this.stats.setEnabled(statsEnabled);
    }

    @Override
    public DestinationSource getDestinationSource() throws JMSException {
        if (this.destinationSource == null) {
            this.destinationSource = new DestinationSource(this);
            this.destinationSource.start();
        }
        return this.destinationSource;
    }

    protected void addSession(ActiveMQSession session) throws JMSException {
        this.sessions.add(session);
        if (this.sessions.size() > 1 || session.isTransacted()) {
            this.optimizedMessageDispatch = false;
        }
    }

    protected void removeSession(ActiveMQSession session) {
        this.sessions.remove(session);
        this.removeDispatcher(session);
    }

    protected void addConnectionConsumer(ActiveMQConnectionConsumer connectionConsumer) throws JMSException {
        this.connectionConsumers.add(connectionConsumer);
    }

    protected void removeConnectionConsumer(ActiveMQConnectionConsumer connectionConsumer) {
        this.connectionConsumers.remove(connectionConsumer);
        this.removeDispatcher(connectionConsumer);
    }

    @Override
    public TopicSession createTopicSession(boolean transacted, int acknowledgeMode) throws JMSException {
        return new ActiveMQTopicSession((ActiveMQSession)this.createSession(transacted, acknowledgeMode));
    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Topic topic, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        return this.createConnectionConsumer(topic, messageSelector, sessionPool, maxMessages, false);
    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Queue queue, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        return this.createConnectionConsumer(queue, messageSelector, sessionPool, maxMessages, false);
    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        return this.createConnectionConsumer(destination, messageSelector, sessionPool, maxMessages, false);
    }

    public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool sessionPool, int maxMessages, boolean noLocal) throws JMSException {
        this.checkClosedOrFailed();
        this.ensureConnectionInfoSent();
        ConsumerId consumerId = this.createConsumerId();
        ConsumerInfo consumerInfo = new ConsumerInfo(consumerId);
        consumerInfo.setDestination(ActiveMQMessageTransformation.transformDestination(destination));
        consumerInfo.setSelector(messageSelector);
        consumerInfo.setPrefetchSize(maxMessages);
        consumerInfo.setNoLocal(noLocal);
        consumerInfo.setDispatchAsync(this.isDispatchAsync());
        if (consumerInfo.getDestination().getOptions() != null) {
            HashMap<String, String> options = new HashMap<String, String>(consumerInfo.getDestination().getOptions());
            IntrospectionSupport.setProperties((Object)consumerInfo, options, "consumer.");
        }
        return new ActiveMQConnectionConsumer(this, sessionPool, consumerInfo);
    }

    private ConsumerId createConsumerId() {
        return new ConsumerId(this.connectionSessionId, this.consumerIdGenerator.getNextSequenceId());
    }

    @Override
    public QueueSession createQueueSession(boolean transacted, int acknowledgeMode) throws JMSException {
        return new ActiveMQQueueSession((ActiveMQSession)this.createSession(transacted, acknowledgeMode));
    }

    public void checkClientIDWasManuallySpecified() throws JMSException {
        if (!this.userSpecifiedClientID) {
            throw new JMSException("You cannot create a durable subscriber without specifying a unique clientID on a Connection");
        }
    }

    public void asyncSendPacket(Command command) throws JMSException {
        if (this.isClosed()) {
            throw new ConnectionClosedException();
        }
        this.doAsyncSendPacket(command);
    }

    private void doAsyncSendPacket(Command command) throws JMSException {
        try {
            this.transport.oneway(command);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    public void syncSendPacket(final Command command, final AsyncCallback onComplete) throws JMSException {
        if (onComplete == null) {
            this.syncSendPacket(command);
        } else {
            if (this.isClosed()) {
                throw new ConnectionClosedException();
            }
            try {
                this.transport.asyncRequest(command, new ResponseCallback(){

                    @Override
                    public void onCompletion(FutureResponse resp) {
                        Throwable exception = null;
                        try {
                            Response response = resp.getResult();
                            if (response.isException()) {
                                ExceptionResponse er = (ExceptionResponse)response;
                                exception = er.getException();
                            }
                        }
                        catch (Exception e) {
                            exception = e;
                        }
                        if (exception != null) {
                            if (exception instanceof JMSException) {
                                onComplete.onException((JMSException)exception);
                            } else {
                                if (ActiveMQConnection.this.isClosed() || ActiveMQConnection.this.closing.get()) {
                                    LOG.debug("Received an exception but connection is closing");
                                }
                                JMSException jmsEx = null;
                                try {
                                    jmsEx = JMSExceptionSupport.create(exception);
                                }
                                catch (Throwable e) {
                                    LOG.error("Caught an exception trying to create a JMSException for " + exception, e);
                                }
                                if (exception instanceof SecurityException && command instanceof ConnectionInfo) {
                                    try {
                                        ActiveMQConnection.this.forceCloseOnSecurityException(exception);
                                    }
                                    catch (Throwable throwable) {
                                        // empty catch block
                                    }
                                }
                                if (jmsEx != null) {
                                    onComplete.onException(jmsEx);
                                }
                            }
                        } else {
                            onComplete.onSuccess();
                        }
                    }
                });
            }
            catch (IOException e) {
                throw JMSExceptionSupport.create(e);
            }
        }
    }

    private void forceCloseOnSecurityException(Throwable exception) {
        LOG.trace("force close on security exception:{}, transport={}", this, this.transport, exception);
        this.onException(new IOException("Force close due to SecurityException on connect", exception));
    }

    public Response syncSendPacket(Command command, int timeout2) throws JMSException {
        if (this.isClosed()) {
            throw new ConnectionClosedException();
        }
        try {
            Response response = (Response)(timeout2 > 0 ? this.transport.request(command, timeout2) : this.transport.request(command));
            if (response.isException()) {
                ExceptionResponse er = (ExceptionResponse)response;
                if (er.getException() instanceof JMSException) {
                    throw (JMSException)er.getException();
                }
                if (this.isClosed() || this.closing.get()) {
                    LOG.debug("Received an exception but connection is closing");
                }
                JMSException jmsEx = null;
                try {
                    jmsEx = JMSExceptionSupport.create(er.getException());
                }
                catch (Throwable e) {
                    LOG.error("Caught an exception trying to create a JMSException for " + er.getException(), e);
                }
                if (er.getException() instanceof SecurityException && command instanceof ConnectionInfo) {
                    try {
                        this.forceCloseOnSecurityException(er.getException());
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                if (jmsEx != null) {
                    throw jmsEx;
                }
            }
            return response;
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    public Response syncSendPacket(Command command) throws JMSException {
        return this.syncSendPacket(command, 0);
    }

    @Override
    public StatsImpl getStats() {
        return this.stats;
    }

    protected synchronized void checkClosedOrFailed() throws JMSException {
        this.checkClosed();
        if (this.transportFailed.get()) {
            throw new ConnectionFailedException(this.firstFailureError);
        }
    }

    protected synchronized void checkClosed() throws JMSException {
        if (this.closed.get()) {
            throw new ConnectionClosedException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void ensureConnectionInfoSent() throws JMSException {
        Object object = this.ensureConnectionInfoSentMutex;
        synchronized (object) {
            if (this.isConnectionInfoSentToBroker || this.closed.get()) {
                return;
            }
            if (this.info.getClientId() == null || this.info.getClientId().trim().length() == 0) {
                this.info.setClientId(this.clientIdGenerator.generateId());
            }
            this.syncSendPacket((Command)this.info.copy(), this.getConnectResponseTimeout());
            this.isConnectionInfoSentToBroker = true;
            ConsumerId consumerId = new ConsumerId(new SessionId(this.info.getConnectionId(), -1L), this.consumerIdGenerator.getNextSequenceId());
            if (this.watchTopicAdvisories) {
                this.advisoryConsumer = new AdvisoryConsumer(this, consumerId);
            }
        }
    }

    public synchronized boolean isWatchTopicAdvisories() {
        return this.watchTopicAdvisories;
    }

    public synchronized void setWatchTopicAdvisories(boolean watchTopicAdvisories) {
        this.watchTopicAdvisories = watchTopicAdvisories;
    }

    public boolean isUseAsyncSend() {
        return this.useAsyncSend;
    }

    public void setUseAsyncSend(boolean useAsyncSend) {
        this.useAsyncSend = useAsyncSend;
    }

    public boolean isAlwaysSyncSend() {
        return this.alwaysSyncSend;
    }

    public void setAlwaysSyncSend(boolean alwaysSyncSend) {
        this.alwaysSyncSend = alwaysSyncSend;
    }

    public boolean isMessagePrioritySupported() {
        return this.messagePrioritySupported;
    }

    public void setMessagePrioritySupported(boolean messagePrioritySupported) {
        this.messagePrioritySupported = messagePrioritySupported;
    }

    public void cleanup() throws JMSException {
        this.doCleanup(false);
    }

    public boolean isUserSpecifiedClientID() {
        return this.userSpecifiedClientID;
    }

    public void doCleanup(boolean removeConnection) throws JMSException {
        if (this.advisoryConsumer != null && !this.isTransportFailed()) {
            this.advisoryConsumer.dispose();
            this.advisoryConsumer = null;
        }
        for (ActiveMQSession s2 : this.sessions) {
            s2.dispose();
        }
        for (ActiveMQConnectionConsumer c : this.connectionConsumers) {
            c.dispose();
        }
        if (removeConnection) {
            if (this.isConnectionInfoSentToBroker) {
                if (!this.transportFailed.get() && !this.closing.get()) {
                    this.syncSendPacket(this.info.createRemoveCommand());
                }
                this.isConnectionInfoSentToBroker = false;
            }
            if (this.userSpecifiedClientID) {
                this.info.setClientId(null);
                this.userSpecifiedClientID = false;
            }
            this.clientIDSet = false;
        }
        this.started.set(false);
    }

    public void changeUserInfo(String userName, String password) throws JMSException {
        if (this.isConnectionInfoSentToBroker) {
            throw new IllegalStateException("changeUserInfo used Connection is not allowed");
        }
        this.info.setUserName(userName);
        this.info.setPassword(password);
    }

    public String getResourceManagerId() throws JMSException {
        if (this.isRmIdFromConnectionId()) {
            return this.info.getConnectionId().getValue();
        }
        this.waitForBrokerInfo();
        if (this.brokerInfo == null) {
            throw new JMSException("Connection failed before Broker info was received.");
        }
        return this.brokerInfo.getBrokerId().getValue();
    }

    public String getBrokerName() {
        try {
            this.brokerInfoReceived.await(5L, TimeUnit.SECONDS);
            if (this.brokerInfo == null) {
                return null;
            }
            return this.brokerInfo.getBrokerName();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public BrokerInfo getBrokerInfo() {
        return this.brokerInfo;
    }

    public RedeliveryPolicy getRedeliveryPolicy() throws JMSException {
        return this.redeliveryPolicyMap.getDefaultEntry();
    }

    public void setRedeliveryPolicy(RedeliveryPolicy redeliveryPolicy) {
        this.redeliveryPolicyMap.setDefaultEntry(redeliveryPolicy);
    }

    public BlobTransferPolicy getBlobTransferPolicy() {
        if (this.blobTransferPolicy == null) {
            this.blobTransferPolicy = this.createBlobTransferPolicy();
        }
        return this.blobTransferPolicy;
    }

    public void setBlobTransferPolicy(BlobTransferPolicy blobTransferPolicy) {
        this.blobTransferPolicy = blobTransferPolicy;
    }

    public boolean isAlwaysSessionAsync() {
        return this.alwaysSessionAsync;
    }

    public void setAlwaysSessionAsync(boolean alwaysSessionAsync) {
        this.alwaysSessionAsync = alwaysSessionAsync;
    }

    public boolean isOptimizeAcknowledge() {
        return this.optimizeAcknowledge;
    }

    public void setOptimizeAcknowledge(boolean optimizeAcknowledge) {
        this.optimizeAcknowledge = optimizeAcknowledge;
    }

    public void setOptimizeAcknowledgeTimeOut(long optimizeAcknowledgeTimeOut) {
        this.optimizeAcknowledgeTimeOut = optimizeAcknowledgeTimeOut;
    }

    public long getOptimizeAcknowledgeTimeOut() {
        return this.optimizeAcknowledgeTimeOut;
    }

    public long getWarnAboutUnstartedConnectionTimeout() {
        return this.warnAboutUnstartedConnectionTimeout;
    }

    public void setWarnAboutUnstartedConnectionTimeout(long warnAboutUnstartedConnectionTimeout) {
        this.warnAboutUnstartedConnectionTimeout = warnAboutUnstartedConnectionTimeout;
    }

    public int getSendTimeout() {
        return this.sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public boolean isSendAcksAsync() {
        return this.sendAcksAsync;
    }

    public void setSendAcksAsync(boolean sendAcksAsync) {
        this.sendAcksAsync = sendAcksAsync;
    }

    public long getTimeCreated() {
        return this.timeCreated;
    }

    private void waitForBrokerInfo() throws JMSException {
        try {
            this.brokerInfoReceived.await();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw JMSExceptionSupport.create(e);
        }
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void addProducer(ProducerId producerId, ActiveMQMessageProducer producer) {
        this.producers.put(producerId, producer);
    }

    public void removeProducer(ProducerId producerId) {
        this.producers.remove(producerId);
    }

    public void addDispatcher(ConsumerId consumerId, ActiveMQDispatcher dispatcher) {
        this.dispatchers.put(consumerId, dispatcher);
    }

    public void removeDispatcher(ConsumerId consumerId) {
        this.dispatchers.remove(consumerId);
    }

    public boolean hasDispatcher(ConsumerId consumerId) {
        return this.dispatchers.containsKey(consumerId);
    }

    @Override
    public void onCommand(Object o) {
        final Command command = (Command)o;
        if (!this.closed.get() && command != null) {
            try {
                command.visit(new CommandVisitorAdapter(){

                    @Override
                    public Response processMessageDispatch(MessageDispatch md) throws Exception {
                        ActiveMQConnection.this.waitForTransportInterruptionProcessingToComplete();
                        ActiveMQDispatcher dispatcher = (ActiveMQDispatcher)ActiveMQConnection.this.dispatchers.get(md.getConsumerId());
                        if (dispatcher != null) {
                            Message msg = md.getMessage();
                            if (msg != null) {
                                msg = msg.copy();
                                msg.setReadOnlyBody(true);
                                msg.setReadOnlyProperties(true);
                                msg.setRedeliveryCounter(md.getRedeliveryCounter());
                                msg.setConnection(ActiveMQConnection.this);
                                msg.setMemoryUsage(null);
                                md.setMessage(msg);
                            }
                            dispatcher.dispatch(md);
                        } else {
                            LOG.debug("{} no dispatcher for {} in {}", this, md, ActiveMQConnection.this.dispatchers);
                        }
                        return null;
                    }

                    @Override
                    public Response processProducerAck(ProducerAck pa) throws Exception {
                        ActiveMQMessageProducer producer;
                        if (pa != null && pa.getProducerId() != null && (producer = (ActiveMQMessageProducer)ActiveMQConnection.this.producers.get(pa.getProducerId())) != null) {
                            producer.onProducerAck(pa);
                        }
                        return null;
                    }

                    @Override
                    public Response processBrokerInfo(BrokerInfo info) throws Exception {
                        ActiveMQConnection.this.brokerInfo = info;
                        ActiveMQConnection.this.brokerInfoReceived.countDown();
                        ActiveMQConnection.this.optimizeAcknowledge = ActiveMQConnection.this.optimizeAcknowledge & !ActiveMQConnection.this.brokerInfo.isFaultTolerantConfiguration();
                        ActiveMQConnection.this.getBlobTransferPolicy().setBrokerUploadUrl(info.getBrokerUploadUrl());
                        return null;
                    }

                    @Override
                    public Response processConnectionError(final ConnectionError error) throws Exception {
                        ActiveMQConnection.this.executor.execute(new Runnable(){

                            @Override
                            public void run() {
                                ActiveMQConnection.this.onAsyncException(error.getException());
                            }
                        });
                        return null;
                    }

                    @Override
                    public Response processControlCommand(ControlCommand command2) throws Exception {
                        return null;
                    }

                    @Override
                    public Response processConnectionControl(ConnectionControl control) throws Exception {
                        ActiveMQConnection.this.onConnectionControl((ConnectionControl)command);
                        return null;
                    }

                    @Override
                    public Response processConsumerControl(ConsumerControl control) throws Exception {
                        ActiveMQConnection.this.onConsumerControl((ConsumerControl)command);
                        return null;
                    }

                    @Override
                    public Response processWireFormat(WireFormatInfo info) throws Exception {
                        ActiveMQConnection.this.onWireFormatInfo((WireFormatInfo)command);
                        return null;
                    }
                });
            }
            catch (Exception e) {
                this.onClientInternalException(e);
            }
        }
        for (TransportListener listener : this.transportListeners) {
            listener.onCommand(command);
        }
    }

    protected void onWireFormatInfo(WireFormatInfo info) {
        this.protocolVersion.set(info.getVersion());
    }

    public void onClientInternalException(final Throwable error) {
        if (!this.closed.get() && !this.closing.get()) {
            if (this.clientInternalExceptionListener != null) {
                this.executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        ActiveMQConnection.this.clientInternalExceptionListener.onException(error);
                    }
                });
            } else {
                LOG.debug("Async client internal exception occurred with no exception listener registered: {}", (Object)error, (Object)error);
            }
        }
    }

    public void onAsyncException(Throwable error) {
        if (!this.closed.get() && !this.closing.get()) {
            if (this.exceptionListener != null) {
                if (!(error instanceof JMSException)) {
                    error = JMSExceptionSupport.create(error);
                }
                final JMSException e = (JMSException)error;
                this.executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        ActiveMQConnection.this.exceptionListener.onException(e);
                    }
                });
            } else {
                LOG.debug("Async exception with no exception listener: {}", (Object)error, (Object)error);
            }
        }
    }

    @Override
    public void onException(final IOException error) {
        this.onAsyncException(error);
        if (!this.closed.get() && !this.closing.get()) {
            this.executor.execute(new Runnable(){

                @Override
                public void run() {
                    ActiveMQConnection.this.transportFailed(error);
                    ServiceSupport.dispose(ActiveMQConnection.this.transport);
                    ActiveMQConnection.this.brokerInfoReceived.countDown();
                    try {
                        ActiveMQConnection.this.doCleanup(true);
                    }
                    catch (JMSException e) {
                        LOG.warn("Exception during connection cleanup, " + e, e);
                    }
                    for (TransportListener listener : ActiveMQConnection.this.transportListeners) {
                        listener.onException(error);
                    }
                }
            });
        }
    }

    @Override
    public void transportInterupted() {
        this.transportInterruptionProcessingComplete.set(1);
        for (ActiveMQSession s2 : this.sessions) {
            s2.clearMessagesInProgress(this.transportInterruptionProcessingComplete);
        }
        for (ActiveMQConnectionConsumer connectionConsumer : this.connectionConsumers) {
            connectionConsumer.clearMessagesInProgress(this.transportInterruptionProcessingComplete);
        }
        if (this.transportInterruptionProcessingComplete.decrementAndGet() > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("transport interrupted - processing required, dispatchers: " + this.transportInterruptionProcessingComplete.get());
            }
            this.signalInterruptionProcessingNeeded();
        }
        for (TransportListener listener : this.transportListeners) {
            listener.transportInterupted();
        }
    }

    @Override
    public void transportResumed() {
        for (TransportListener listener : this.transportListeners) {
            listener.transportResumed();
        }
    }

    protected ActiveMQTempDestination createTempDestination(boolean topic) throws JMSException {
        ActiveMQTempDestination dest = topic ? new ActiveMQTempTopic(this.info.getConnectionId(), this.tempDestinationIdGenerator.getNextSequenceId()) : new ActiveMQTempQueue(this.info.getConnectionId(), this.tempDestinationIdGenerator.getNextSequenceId());
        DestinationInfo info = new DestinationInfo();
        info.setConnectionId(this.info.getConnectionId());
        info.setOperationType((byte)0);
        info.setDestination(dest);
        this.syncSendPacket(info);
        dest.setConnection(this);
        this.activeTempDestinations.put(dest, dest);
        return dest;
    }

    public void deleteTempDestination(ActiveMQTempDestination destination) throws JMSException {
        this.checkClosedOrFailed();
        for (ActiveMQSession session : this.sessions) {
            if (!session.isInUse(destination)) continue;
            throw new JMSException("A consumer is consuming from the temporary destination");
        }
        this.activeTempDestinations.remove(destination);
        DestinationInfo destInfo = new DestinationInfo();
        destInfo.setConnectionId(this.info.getConnectionId());
        destInfo.setOperationType((byte)1);
        destInfo.setDestination(destination);
        destInfo.setTimeout(0L);
        this.syncSendPacket(destInfo);
    }

    public boolean isDeleted(ActiveMQDestination dest) {
        if (this.advisoryConsumer == null) {
            return false;
        }
        return !this.activeTempDestinations.containsValue(dest);
    }

    public boolean isCopyMessageOnSend() {
        return this.copyMessageOnSend;
    }

    public LongSequenceGenerator getLocalTransactionIdGenerator() {
        return this.localTransactionIdGenerator;
    }

    public boolean isUseCompression() {
        return this.useCompression;
    }

    public void setUseCompression(boolean useCompression) {
        this.useCompression = useCompression;
    }

    public void destroyDestination(ActiveMQDestination destination) throws JMSException {
        this.checkClosedOrFailed();
        this.ensureConnectionInfoSent();
        DestinationInfo info = new DestinationInfo();
        info.setConnectionId(this.info.getConnectionId());
        info.setOperationType((byte)1);
        info.setDestination(destination);
        info.setTimeout(0L);
        this.syncSendPacket(info);
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean asyncDispatch) {
        this.dispatchAsync = asyncDispatch;
    }

    public boolean isObjectMessageSerializationDefered() {
        return this.objectMessageSerializationDefered;
    }

    public void setObjectMessageSerializationDefered(boolean objectMessageSerializationDefered) {
        this.objectMessageSerializationDefered = objectMessageSerializationDefered;
    }

    public void unsubscribe(String name) throws InvalidDestinationException, JMSException {
        this.checkClosedOrFailed();
        RemoveSubscriptionInfo rsi = new RemoveSubscriptionInfo();
        rsi.setConnectionId(this.getConnectionInfo().getConnectionId());
        rsi.setSubscriptionName(name);
        rsi.setClientId(this.getConnectionInfo().getClientId());
        this.syncSendPacket(rsi);
    }

    void send(ActiveMQDestination destination, ActiveMQMessage msg, MessageId messageId, int deliveryMode, int priority, long timeToLive, boolean async) throws JMSException {
        this.checkClosedOrFailed();
        if (destination.isTemporary() && this.isDeleted(destination)) {
            throw new JMSException("Cannot publish to a deleted Destination: " + destination);
        }
        msg.setJMSDestination(destination);
        msg.setJMSDeliveryMode(deliveryMode);
        long expiration = 0L;
        if (!this.isDisableTimeStampsByDefault()) {
            long timeStamp = System.currentTimeMillis();
            msg.setJMSTimestamp(timeStamp);
            if (timeToLive > 0L) {
                expiration = timeToLive + timeStamp;
            }
        }
        msg.setJMSExpiration(expiration);
        msg.setJMSPriority(priority);
        msg.setJMSRedelivered(false);
        msg.setMessageId(messageId);
        msg.onSend();
        msg.setProducerId(msg.getMessageId().getProducerId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending message: " + msg);
        }
        if (async) {
            this.asyncSendPacket(msg);
        } else {
            this.syncSendPacket(msg);
        }
    }

    protected void onConnectionControl(ConnectionControl command) {
        if (command.isFaultTolerant()) {
            this.optimizeAcknowledge = false;
            for (ActiveMQSession s2 : this.sessions) {
                s2.setOptimizeAcknowledge(false);
            }
        }
    }

    protected void onConsumerControl(ConsumerControl command) {
        if (command.isClose()) {
            for (ActiveMQSession session : this.sessions) {
                session.close(command.getConsumerId());
            }
        } else {
            for (ActiveMQSession session : this.sessions) {
                session.setPrefetchSize(command.getConsumerId(), command.getPrefetch());
            }
            for (ActiveMQConnectionConsumer connectionConsumer : this.connectionConsumers) {
                ConsumerInfo consumerInfo = connectionConsumer.getConsumerInfo();
                if (!consumerInfo.getConsumerId().equals(command.getConsumerId())) continue;
                consumerInfo.setPrefetchSize(command.getPrefetch());
            }
        }
    }

    protected void transportFailed(IOException error) {
        this.transportFailed.set(true);
        if (this.firstFailureError == null) {
            this.firstFailureError = error;
        }
    }

    public void setCopyMessageOnSend(boolean copyMessageOnSend) {
        this.copyMessageOnSend = copyMessageOnSend;
    }

    public String toString() {
        return "ActiveMQConnection {id=" + this.info.getConnectionId() + ",clientId=" + this.info.getClientId() + ",started=" + this.started.get() + "}";
    }

    protected BlobTransferPolicy createBlobTransferPolicy() {
        return new BlobTransferPolicy();
    }

    public int getProtocolVersion() {
        return this.protocolVersion.get();
    }

    public int getProducerWindowSize() {
        return this.producerWindowSize;
    }

    public void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
    }

    public void setAuditDepth(int auditDepth) {
        this.connectionAudit.setAuditDepth(auditDepth);
    }

    public void setAuditMaximumProducerNumber(int auditMaximumProducerNumber) {
        this.connectionAudit.setAuditMaximumProducerNumber(auditMaximumProducerNumber);
    }

    protected void removeDispatcher(ActiveMQDispatcher dispatcher) {
        this.connectionAudit.removeDispatcher(dispatcher);
    }

    protected boolean isDuplicate(ActiveMQDispatcher dispatcher, Message message) {
        return this.checkForDuplicates && this.connectionAudit.isDuplicate(dispatcher, message);
    }

    protected void rollbackDuplicate(ActiveMQDispatcher dispatcher, Message message) {
        this.connectionAudit.rollbackDuplicate(dispatcher, message);
    }

    public IOException getFirstFailureError() {
        return this.firstFailureError;
    }

    protected void waitForTransportInterruptionProcessingToComplete() throws InterruptedException {
        if (!this.closed.get() && !this.transportFailed.get() && this.transportInterruptionProcessingComplete.get() > 0) {
            LOG.warn("dispatch with outstanding dispatch interruption processing count " + this.transportInterruptionProcessingComplete.get());
            this.signalInterruptionProcessingComplete();
        }
    }

    protected void transportInterruptionProcessingComplete() {
        if (this.transportInterruptionProcessingComplete.decrementAndGet() == 0) {
            this.signalInterruptionProcessingComplete();
        }
    }

    private void signalInterruptionProcessingComplete() {
        FailoverTransport failoverTransport;
        if (LOG.isDebugEnabled()) {
            LOG.debug("transportInterruptionProcessingComplete: " + this.transportInterruptionProcessingComplete.get() + " for:" + this.getConnectionInfo().getConnectionId());
        }
        if ((failoverTransport = this.transport.narrow(FailoverTransport.class)) != null) {
            failoverTransport.connectionInterruptProcessingComplete(this.getConnectionInfo().getConnectionId());
            if (LOG.isDebugEnabled()) {
                LOG.debug("notified failover transport (" + failoverTransport + ") of interruption completion for: " + this.getConnectionInfo().getConnectionId());
            }
        }
        this.transportInterruptionProcessingComplete.set(0);
    }

    private void signalInterruptionProcessingNeeded() {
        FailoverTransport failoverTransport = this.transport.narrow(FailoverTransport.class);
        if (failoverTransport != null) {
            failoverTransport.getStateTracker().transportInterrupted(this.getConnectionInfo().getConnectionId());
            if (LOG.isDebugEnabled()) {
                LOG.debug("notified failover transport (" + failoverTransport + ") of pending interruption processing for: " + this.getConnectionInfo().getConnectionId());
            }
        }
    }

    public void setConsumerFailoverRedeliveryWaitPeriod(long consumerFailoverRedeliveryWaitPeriod) {
        this.consumerFailoverRedeliveryWaitPeriod = consumerFailoverRedeliveryWaitPeriod;
    }

    public long getConsumerFailoverRedeliveryWaitPeriod() {
        return this.consumerFailoverRedeliveryWaitPeriod;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Scheduler getScheduler() throws JMSException {
        Scheduler result = this.scheduler;
        if (result == null) {
            if (this.isClosing() || this.isClosed()) {
                throw new ConnectionClosedException();
            }
            ActiveMQConnection activeMQConnection = this;
            synchronized (activeMQConnection) {
                result = this.scheduler;
                if (result == null) {
                    this.checkClosed();
                    try {
                        result = new Scheduler("ActiveMQConnection[" + this.info.getConnectionId().getValue() + "] Scheduler");
                        result.start();
                        this.scheduler = result;
                    }
                    catch (Exception e) {
                        throw JMSExceptionSupport.create(e);
                    }
                }
            }
        }
        return result;
    }

    protected ThreadPoolExecutor getExecutor() {
        return this.executor;
    }

    protected CopyOnWriteArrayList<ActiveMQSession> getSessions() {
        return this.sessions;
    }

    public boolean isCheckForDuplicates() {
        return this.checkForDuplicates;
    }

    public void setCheckForDuplicates(boolean checkForDuplicates) {
        this.checkForDuplicates = checkForDuplicates;
    }

    public boolean isTransactedIndividualAck() {
        return this.transactedIndividualAck;
    }

    public void setTransactedIndividualAck(boolean transactedIndividualAck) {
        this.transactedIndividualAck = transactedIndividualAck;
    }

    public boolean isNonBlockingRedelivery() {
        return this.nonBlockingRedelivery;
    }

    public void setNonBlockingRedelivery(boolean nonBlockingRedelivery) {
        this.nonBlockingRedelivery = nonBlockingRedelivery;
    }

    public boolean isRmIdFromConnectionId() {
        return this.rmIdFromConnectionId;
    }

    public void setRmIdFromConnectionId(boolean rmIdFromConnectionId) {
        this.rmIdFromConnectionId = rmIdFromConnectionId;
    }

    public void cleanUpTempDestinations() {
        if (this.activeTempDestinations == null || this.activeTempDestinations.isEmpty()) {
            return;
        }
        for (Map.Entry entry : this.activeTempDestinations.entrySet()) {
            try {
                String thisConnectionId;
                ActiveMQTempDestination dest = (ActiveMQTempDestination)entry.getValue();
                String string = thisConnectionId = this.info.getConnectionId() == null ? "" : this.info.getConnectionId().toString();
                if (dest.getConnectionId() == null || !dest.getConnectionId().equals(thisConnectionId)) continue;
                this.deleteTempDestination((ActiveMQTempDestination)entry.getValue());
            }
            catch (Exception exception) {}
        }
    }

    public void setRedeliveryPolicyMap(RedeliveryPolicyMap redeliveryPolicyMap) {
        this.redeliveryPolicyMap = redeliveryPolicyMap;
    }

    public RedeliveryPolicyMap getRedeliveryPolicyMap() {
        return this.redeliveryPolicyMap;
    }

    public int getMaxThreadPoolSize() {
        return this.maxThreadPoolSize;
    }

    public void setMaxThreadPoolSize(int maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    ActiveMQConnection enforceQueueOnlyConnection() {
        this.queueOnlyConnection = true;
        return this;
    }

    public RejectedExecutionHandler getRejectedTaskHandler() {
        return this.rejectedTaskHandler;
    }

    public void setRejectedTaskHandler(RejectedExecutionHandler rejectedTaskHandler) {
        this.rejectedTaskHandler = rejectedTaskHandler;
    }

    public long getOptimizedAckScheduledAckInterval() {
        return this.optimizedAckScheduledAckInterval;
    }

    public void setOptimizedAckScheduledAckInterval(long optimizedAckScheduledAckInterval) {
        this.optimizedAckScheduledAckInterval = optimizedAckScheduledAckInterval;
    }

    public boolean isConsumerExpiryCheckEnabled() {
        return this.consumerExpiryCheckEnabled;
    }

    public void setConsumerExpiryCheckEnabled(boolean consumerExpiryCheckEnabled) {
        this.consumerExpiryCheckEnabled = consumerExpiryCheckEnabled;
    }

    public List<String> getTrustedPackages() {
        return this.trustedPackages;
    }

    public void setTrustedPackages(List<String> trustedPackages) {
        this.trustedPackages = trustedPackages;
    }

    public boolean isTrustAllPackages() {
        return this.trustAllPackages;
    }

    public void setTrustAllPackages(boolean trustAllPackages) {
        this.trustAllPackages = trustAllPackages;
    }

    public int getConnectResponseTimeout() {
        return this.connectResponseTimeout;
    }

    public void setConnectResponseTimeout(int connectResponseTimeout) {
        this.connectResponseTimeout = connectResponseTimeout;
    }
}

