/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import javax.management.ObjectName;
import org.apache.activemq.DestinationDoesNotExistException;
import org.apache.activemq.Service;
import org.apache.activemq.advisory.AdvisoryBroker;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.region.AbstractRegion;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.BrokerSubscriptionInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionError;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.NetworkBridgeFilter;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.network.DefaultNetworkBridgeFilterFactory;
import org.apache.activemq.network.DemandSubscription;
import org.apache.activemq.network.NetworkBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeFactory;
import org.apache.activemq.network.NetworkBridgeFilterFactory;
import org.apache.activemq.network.NetworkBridgeListener;
import org.apache.activemq.network.NetworkBridgeStatistics;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportDisposedIOException;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.failover.FailoverTransport;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.MarshallingSupport;
import org.apache.activemq.util.NetworkBridgeUtils;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.StringToListOfActiveMQDestinationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DemandForwardingBridgeSupport
implements NetworkBridge,
BrokerServiceAware {
    private static final Logger LOG = LoggerFactory.getLogger(DemandForwardingBridgeSupport.class);
    protected static final String DURABLE_SUB_PREFIX = "NC-DS_";
    protected final Transport localBroker;
    protected final Transport remoteBroker;
    protected IdGenerator idGenerator = new IdGenerator();
    protected final LongSequenceGenerator consumerIdGenerator = new LongSequenceGenerator();
    protected ConnectionInfo localConnectionInfo;
    protected ConnectionInfo remoteConnectionInfo;
    protected SessionInfo localSessionInfo;
    protected ProducerInfo producerInfo;
    protected String remoteBrokerName = "Unknown";
    protected String localClientId;
    protected ConsumerInfo demandConsumerInfo;
    protected int demandConsumerDispatched;
    protected final AtomicBoolean localBridgeStarted = new AtomicBoolean(false);
    protected final AtomicBoolean remoteBridgeStarted = new AtomicBoolean(false);
    protected final AtomicBoolean bridgeFailed = new AtomicBoolean();
    protected final AtomicBoolean disposed = new AtomicBoolean();
    protected BrokerId localBrokerId;
    protected ActiveMQDestination[] excludedDestinations;
    protected ActiveMQDestination[] dynamicallyIncludedDestinations;
    protected ActiveMQDestination[] staticallyIncludedDestinations;
    protected ActiveMQDestination[] durableDestinations;
    protected final ConcurrentMap<ConsumerId, DemandSubscription> subscriptionMapByLocalId = new ConcurrentHashMap<ConsumerId, DemandSubscription>();
    protected final ConcurrentMap<ConsumerId, DemandSubscription> subscriptionMapByRemoteId = new ConcurrentHashMap<ConsumerId, DemandSubscription>();
    protected final Set<ConsumerId> forcedDurableRemoteId = Collections.newSetFromMap(new ConcurrentHashMap());
    protected final BrokerId[] localBrokerPath = new BrokerId[]{null};
    protected final CountDownLatch startedLatch = new CountDownLatch(2);
    protected final CountDownLatch localStartedLatch = new CountDownLatch(1);
    protected final CountDownLatch staticDestinationsLatch = new CountDownLatch(1);
    protected final AtomicBoolean lastConnectSucceeded = new AtomicBoolean(false);
    protected NetworkBridgeConfiguration configuration;
    protected final NetworkBridgeFilterFactory defaultFilterFactory = new DefaultNetworkBridgeFilterFactory();
    protected final BrokerId[] remoteBrokerPath = new BrokerId[]{null};
    protected BrokerId remoteBrokerId;
    protected final NetworkBridgeStatistics networkBridgeStatistics = new NetworkBridgeStatistics();
    private NetworkBridgeListener networkBridgeListener;
    private boolean createdByDuplex;
    private BrokerInfo localBrokerInfo;
    private BrokerInfo remoteBrokerInfo;
    private final FutureBrokerInfo futureRemoteBrokerInfo = new FutureBrokerInfo(this.remoteBrokerInfo, this.disposed);
    private final FutureBrokerInfo futureLocalBrokerInfo = new FutureBrokerInfo(this.localBrokerInfo, this.disposed);
    private final AtomicBoolean started = new AtomicBoolean();
    private TransportConnection duplexInitiatingConnection;
    private final AtomicBoolean duplexInitiatingConnectionInfoReceived = new AtomicBoolean();
    protected BrokerService brokerService = null;
    private ObjectName mbeanObjectName;
    private final ExecutorService serialExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService syncExecutor = Executors.newSingleThreadExecutor();
    private Transport duplexInboundLocalBroker = null;
    private ProducerInfo duplexInboundLocalProducerInfo;

    public DemandForwardingBridgeSupport(NetworkBridgeConfiguration configuration, Transport localBroker, Transport remoteBroker) {
        this.configuration = configuration;
        this.localBroker = localBroker;
        this.remoteBroker = remoteBroker;
    }

    public void duplexStart(TransportConnection connection, BrokerInfo localBrokerInfo, BrokerInfo remoteBrokerInfo) throws Exception {
        this.localBrokerInfo = localBrokerInfo;
        this.remoteBrokerInfo = remoteBrokerInfo;
        this.duplexInitiatingConnection = connection;
        this.start();
        this.serviceRemoteCommand(remoteBrokerInfo);
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            if (this.brokerService == null) {
                throw new IllegalArgumentException("BrokerService is null on " + this);
            }
            this.networkBridgeStatistics.setEnabled(this.brokerService.isEnableStatistics());
            if (this.isDuplex()) {
                this.duplexInboundLocalBroker = NetworkBridgeFactory.createLocalAsyncTransport(this.brokerService.getBroker().getVmConnectorURI());
                this.duplexInboundLocalBroker.setTransportListener(new DefaultTransportListener(){

                    @Override
                    public void onCommand(Object o) {
                        Command command = (Command)o;
                        DemandForwardingBridgeSupport.this.serviceLocalCommand(command);
                    }

                    @Override
                    public void onException(IOException error) {
                        DemandForwardingBridgeSupport.this.serviceLocalException(error);
                    }
                });
                this.duplexInboundLocalBroker.start();
            }
            this.localBroker.setTransportListener(new DefaultTransportListener(){

                @Override
                public void onCommand(Object o) {
                    Command command = (Command)o;
                    DemandForwardingBridgeSupport.this.serviceLocalCommand(command);
                }

                @Override
                public void onException(IOException error) {
                    if (!DemandForwardingBridgeSupport.this.futureLocalBrokerInfo.isDone()) {
                        LOG.info("Error with pending local brokerInfo on: {} ({})", (Object)DemandForwardingBridgeSupport.this.localBroker, (Object)error.getMessage());
                        LOG.debug("Peer error: ", error);
                        DemandForwardingBridgeSupport.this.futureLocalBrokerInfo.cancel(true);
                        return;
                    }
                    DemandForwardingBridgeSupport.this.serviceLocalException(error);
                }
            });
            this.remoteBroker.setTransportListener(new DefaultTransportListener(){

                @Override
                public void onCommand(Object o) {
                    Command command = (Command)o;
                    DemandForwardingBridgeSupport.this.serviceRemoteCommand(command);
                }

                @Override
                public void onException(IOException error) {
                    if (!DemandForwardingBridgeSupport.this.futureRemoteBrokerInfo.isDone()) {
                        LOG.info("Error with pending remote brokerInfo on: {} ({})", (Object)DemandForwardingBridgeSupport.this.remoteBroker, (Object)error.getMessage());
                        LOG.debug("Peer error: ", error);
                        DemandForwardingBridgeSupport.this.futureRemoteBrokerInfo.cancel(true);
                        return;
                    }
                    DemandForwardingBridgeSupport.this.serviceRemoteException(error);
                }
            });
            this.remoteBroker.start();
            this.localBroker.start();
            if (!this.disposed.get()) {
                try {
                    this.triggerStartAsyncNetworkBridgeCreation();
                }
                catch (IOException e) {
                    LOG.warn("Caught exception from remote start", e);
                }
            } else {
                LOG.warn("Bridge was disposed before the start() method was fully executed.");
                throw new TransportDisposedIOException();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            if (this.disposed.compareAndSet(false, true)) {
                LOG.debug(" stopping {} bridge to {}", (Object)this.configuration.getBrokerName(), (Object)this.remoteBrokerName);
                this.futureRemoteBrokerInfo.cancel(true);
                this.futureLocalBrokerInfo.cancel(true);
                NetworkBridgeListener l = this.networkBridgeListener;
                if (l != null) {
                    l.onStop(this);
                }
                try {
                    if (this.startedLatch.getCount() < 2L) {
                        LOG.trace("{} unregister bridge ({}) to {}", this.configuration.getBrokerName(), this, this.remoteBrokerName);
                        this.brokerService.getBroker().removeBroker(null, this.remoteBrokerInfo);
                        this.brokerService.getBroker().networkBridgeStopped(this.remoteBrokerInfo);
                    }
                    this.remoteBridgeStarted.set(false);
                    final CountDownLatch sendShutdown = new CountDownLatch(1);
                    this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                List<Runnable> pendingTasks;
                                DemandForwardingBridgeSupport.this.serialExecutor.shutdown();
                                if (!DemandForwardingBridgeSupport.this.serialExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
                                    pendingTasks = DemandForwardingBridgeSupport.this.serialExecutor.shutdownNow();
                                    LOG.info("pending tasks on stop {}", (Object)pendingTasks);
                                }
                                DemandForwardingBridgeSupport.this.staticDestinationsLatch.countDown();
                                DemandForwardingBridgeSupport.this.syncExecutor.shutdown();
                                if (!DemandForwardingBridgeSupport.this.syncExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
                                    pendingTasks = DemandForwardingBridgeSupport.this.syncExecutor.shutdownNow();
                                    LOG.info("pending tasks on stop {}", (Object)pendingTasks);
                                }
                                DemandForwardingBridgeSupport.this.localBroker.oneway(new ShutdownInfo());
                                DemandForwardingBridgeSupport.this.remoteBroker.oneway(new ShutdownInfo());
                            }
                            catch (Throwable e) {
                                LOG.debug("Caught exception sending shutdown", e);
                            }
                            finally {
                                sendShutdown.countDown();
                            }
                        }
                    }, "ActiveMQ ForwardingBridge StopTask");
                    if (!sendShutdown.await(10L, TimeUnit.SECONDS)) {
                        LOG.info("Network Could not shutdown in a timely manner");
                    }
                }
                finally {
                    ServiceStopper ss = new ServiceStopper();
                    this.stopFailoverTransport(this.remoteBroker);
                    ss.stop(this.remoteBroker);
                    ss.stop(this.localBroker);
                    ss.stop(this.duplexInboundLocalBroker);
                    this.startedLatch.countDown();
                    this.startedLatch.countDown();
                    this.localStartedLatch.countDown();
                    this.staticDestinationsLatch.countDown();
                    ss.throwFirstException();
                }
            }
            LOG.info("{} bridge to {} stopped", (Object)this.configuration.getBrokerName(), (Object)this.remoteBrokerName);
        }
    }

    private void stopFailoverTransport(Transport transport) {
        FailoverTransport failoverTransport = transport.narrow(FailoverTransport.class);
        if (failoverTransport != null) {
            try {
                failoverTransport.handleTransportFailure(new IOException("Bridge stopped"));
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    protected void triggerStartAsyncNetworkBridgeCreation() throws IOException {
        this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

            @Override
            public void run() {
                String originalName = Thread.currentThread().getName();
                Thread.currentThread().setName("triggerStartAsyncNetworkBridgeCreation: remoteBroker=" + DemandForwardingBridgeSupport.this.remoteBroker + ", localBroker= " + DemandForwardingBridgeSupport.this.localBroker);
                try {
                    DemandForwardingBridgeSupport.this.collectBrokerInfos();
                    DemandForwardingBridgeSupport.this.doStartLocalAndRemoteBridges();
                }
                finally {
                    Thread.currentThread().setName(originalName);
                }
            }
        });
    }

    private void collectBrokerInfos() {
        int timeout2 = 30000;
        TcpTransport tcpTransport = this.remoteBroker.narrow(TcpTransport.class);
        if (tcpTransport != null) {
            timeout2 = tcpTransport.getConnectionTimeout();
        }
        try {
            this.remoteBrokerInfo = this.futureRemoteBrokerInfo.get(timeout2, TimeUnit.MILLISECONDS);
            if (this.remoteBrokerInfo == null) {
                this.serviceLocalException(new Throwable("remoteBrokerInfo is null"));
                return;
            }
        }
        catch (Exception e) {
            this.serviceRemoteException(e);
            return;
        }
        try {
            this.localBrokerInfo = this.futureLocalBrokerInfo.get(timeout2, TimeUnit.MILLISECONDS);
            if (this.localBrokerInfo == null) {
                this.serviceLocalException(new Throwable("localBrokerInfo is null"));
                return;
            }
            this.remoteBrokerId = this.remoteBrokerInfo.getBrokerId();
            if (this.localBrokerId.equals(this.remoteBrokerId)) {
                LOG.trace("{} disconnecting remote loop back connector for: {}, with id: {}", this.configuration.getBrokerName(), this.remoteBrokerName, this.remoteBrokerId);
                ServiceSupport.dispose(this.localBroker);
                ServiceSupport.dispose(this.remoteBroker);
                return;
            }
            this.remoteBrokerPath[0] = this.remoteBrokerId;
            this.remoteBrokerName = this.remoteBrokerInfo.getBrokerName();
            if (this.configuration.isUseBrokerNamesAsIdSeed()) {
                this.idGenerator = new IdGenerator(this.brokerService.getBrokerName() + "->" + this.remoteBrokerName);
            }
        }
        catch (Throwable e) {
            this.serviceLocalException(e);
        }
    }

    private void doStartLocalAndRemoteBridges() {
        if (this.disposed.get()) {
            return;
        }
        if (this.isCreatedByDuplex()) {
            Properties props = null;
            try {
                props = MarshallingSupport.stringToProperties(this.remoteBrokerInfo.getNetworkProperties());
                IntrospectionSupport.getProperties(this.configuration, props, null);
                if (this.configuration.getExcludedDestinations() != null) {
                    this.excludedDestinations = this.configuration.getExcludedDestinations().toArray(new ActiveMQDestination[this.configuration.getExcludedDestinations().size()]);
                }
                if (this.configuration.getStaticallyIncludedDestinations() != null) {
                    this.staticallyIncludedDestinations = this.configuration.getStaticallyIncludedDestinations().toArray(new ActiveMQDestination[this.configuration.getStaticallyIncludedDestinations().size()]);
                }
                if (this.configuration.getDynamicallyIncludedDestinations() != null) {
                    this.dynamicallyIncludedDestinations = this.configuration.getDynamicallyIncludedDestinations().toArray(new ActiveMQDestination[this.configuration.getDynamicallyIncludedDestinations().size()]);
                }
            }
            catch (Throwable t2) {
                LOG.error("Error mapping remote configuration: {}", (Object)props, (Object)t2);
            }
        }
        try {
            this.startLocalBridge();
        }
        catch (Throwable e) {
            this.serviceLocalException(e);
            return;
        }
        try {
            this.startRemoteBridge();
        }
        catch (Throwable e) {
            this.serviceRemoteException(e);
            return;
        }
        try {
            if (this.safeWaitUntilStarted()) {
                this.setupStaticDestinations();
                this.staticDestinationsLatch.countDown();
            }
        }
        catch (Throwable e) {
            this.serviceLocalException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void startLocalBridge() throws Throwable {
        if (!this.bridgeFailed.get() && this.localBridgeStarted.compareAndSet(false, true)) {
            DemandForwardingBridgeSupport demandForwardingBridgeSupport = this;
            synchronized (demandForwardingBridgeSupport) {
                LOG.trace("{} starting local Bridge, localBroker={}", (Object)this.configuration.getBrokerName(), (Object)this.localBroker);
                if (!this.disposed.get()) {
                    Object resp;
                    if (this.idGenerator == null) {
                        throw new IllegalStateException("Id Generator cannot be null");
                    }
                    this.localConnectionInfo = new ConnectionInfo();
                    this.localConnectionInfo.setConnectionId(new ConnectionId(this.idGenerator.generateId()));
                    this.localClientId = this.configuration.getName() + this.configuration.getClientIdToken() + this.remoteBrokerName + this.configuration.getClientIdToken() + "inbound" + this.configuration.getClientIdToken() + this.configuration.getBrokerName();
                    this.localConnectionInfo.setClientId(this.localClientId);
                    this.localConnectionInfo.setUserName(this.configuration.getUserName());
                    this.localConnectionInfo.setPassword(this.configuration.getPassword());
                    Transport originalTransport = this.remoteBroker;
                    while (originalTransport instanceof TransportFilter) {
                        originalTransport = ((TransportFilter)originalTransport).getNext();
                    }
                    if (originalTransport instanceof TcpTransport) {
                        X509Certificate[] peerCerts = originalTransport.getPeerCertificates();
                        this.localConnectionInfo.setTransportContext(peerCerts);
                    }
                    if ((resp = this.localBroker.request(this.localConnectionInfo)) instanceof ExceptionResponse) {
                        throw ((ExceptionResponse)resp).getException();
                    }
                    this.localSessionInfo = new SessionInfo(this.localConnectionInfo, 1L);
                    this.localBroker.oneway(this.localSessionInfo);
                    if (this.configuration.isDuplex()) {
                        this.remoteBrokerInfo.setNetworkConnection(true);
                        this.duplexInboundLocalBroker.oneway(this.remoteBrokerInfo);
                        ConnectionInfo duplexLocalConnectionInfo = new ConnectionInfo();
                        duplexLocalConnectionInfo.setConnectionId(new ConnectionId(this.idGenerator.generateId()));
                        duplexLocalConnectionInfo.setClientId(this.configuration.getName() + this.configuration.getClientIdToken() + this.remoteBrokerName + this.configuration.getClientIdToken() + "inbound" + this.configuration.getClientIdToken() + "duplex" + this.configuration.getClientIdToken() + this.configuration.getBrokerName());
                        duplexLocalConnectionInfo.setUserName(this.configuration.getUserName());
                        duplexLocalConnectionInfo.setPassword(this.configuration.getPassword());
                        if (originalTransport instanceof TcpTransport) {
                            X509Certificate[] peerCerts = originalTransport.getPeerCertificates();
                            duplexLocalConnectionInfo.setTransportContext(peerCerts);
                        }
                        if ((resp = this.duplexInboundLocalBroker.request(duplexLocalConnectionInfo)) instanceof ExceptionResponse) {
                            throw ((ExceptionResponse)resp).getException();
                        }
                        SessionInfo duplexInboundSession = new SessionInfo(duplexLocalConnectionInfo, 1L);
                        this.duplexInboundLocalProducerInfo = new ProducerInfo(duplexInboundSession, 1L);
                        this.duplexInboundLocalBroker.oneway(duplexInboundSession);
                        this.duplexInboundLocalBroker.oneway(this.duplexInboundLocalProducerInfo);
                    }
                    this.brokerService.getBroker().networkBridgeStarted(this.remoteBrokerInfo, this.createdByDuplex, this.remoteBroker.toString());
                    NetworkBridgeListener l = this.networkBridgeListener;
                    if (l != null) {
                        l.onStart(this);
                    }
                    this.localBroker.oneway(this.remoteBrokerInfo);
                    this.brokerService.getBroker().addBroker(null, this.remoteBrokerInfo);
                    LOG.info("Network connection between {} and {} ({}) has been established.", this.localBroker, this.remoteBroker, this.remoteBrokerName);
                    LOG.trace("{} register bridge ({}) to {}", this.configuration.getBrokerName(), this, this.remoteBrokerName);
                } else {
                    LOG.warn("Bridge was disposed before the startLocalBridge() method was fully executed.");
                }
                this.startedLatch.countDown();
                this.localStartedLatch.countDown();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void startRemoteBridge() throws Exception {
        if (!this.bridgeFailed.get() && this.remoteBridgeStarted.compareAndSet(false, true)) {
            LOG.trace("{} starting remote Bridge, remoteBroker={}", (Object)this.configuration.getBrokerName(), (Object)this.remoteBroker);
            DemandForwardingBridgeSupport demandForwardingBridgeSupport = this;
            synchronized (demandForwardingBridgeSupport) {
                if (!this.isCreatedByDuplex()) {
                    BrokerInfo brokerInfo = new BrokerInfo();
                    brokerInfo.setBrokerName(this.configuration.getBrokerName());
                    brokerInfo.setBrokerURL(this.configuration.getBrokerURL());
                    brokerInfo.setNetworkConnection(true);
                    brokerInfo.setDuplexConnection(this.configuration.isDuplex());
                    Properties props = new Properties();
                    IntrospectionSupport.getProperties(this.configuration, props, null);
                    String dynamicallyIncludedDestinationsKey = "dynamicallyIncludedDestinations";
                    String staticallyIncludedDestinationsKey = "staticallyIncludedDestinations";
                    if (!this.configuration.getDynamicallyIncludedDestinations().isEmpty()) {
                        props.put(dynamicallyIncludedDestinationsKey, StringToListOfActiveMQDestinationConverter.convertFromActiveMQDestination(this.configuration.getDynamicallyIncludedDestinations(), true));
                    }
                    if (!this.configuration.getStaticallyIncludedDestinations().isEmpty()) {
                        props.put(staticallyIncludedDestinationsKey, StringToListOfActiveMQDestinationConverter.convertFromActiveMQDestination(this.configuration.getStaticallyIncludedDestinations(), true));
                    }
                    props.remove("networkTTL");
                    String str = MarshallingSupport.propertiesToString(props);
                    brokerInfo.setNetworkProperties(str);
                    brokerInfo.setBrokerId(this.localBrokerId);
                    this.remoteBroker.oneway(brokerInfo);
                    if (this.configuration.isSyncDurableSubs() && this.remoteBroker.getWireFormat().getVersion() >= 12) {
                        this.remoteBroker.oneway(NetworkBridgeUtils.getBrokerSubscriptionInfo(this.brokerService, this.configuration));
                    }
                }
                if (this.remoteConnectionInfo != null) {
                    this.remoteBroker.oneway(this.remoteConnectionInfo.createRemoveCommand());
                }
                this.remoteConnectionInfo = new ConnectionInfo();
                this.remoteConnectionInfo.setConnectionId(new ConnectionId(this.idGenerator.generateId()));
                this.remoteConnectionInfo.setClientId(this.configuration.getName() + this.configuration.getClientIdToken() + this.configuration.getBrokerName() + this.configuration.getClientIdToken() + "outbound");
                this.remoteConnectionInfo.setUserName(this.configuration.getUserName());
                this.remoteConnectionInfo.setPassword(this.configuration.getPassword());
                this.remoteBroker.oneway(this.remoteConnectionInfo);
                SessionInfo remoteSessionInfo = new SessionInfo(this.remoteConnectionInfo, 1L);
                this.remoteBroker.oneway(remoteSessionInfo);
                this.producerInfo = new ProducerInfo(remoteSessionInfo, 1L);
                this.producerInfo.setResponseRequired(false);
                this.remoteBroker.oneway(this.producerInfo);
                if (!this.configuration.isStaticBridge()) {
                    this.demandConsumerInfo = new ConsumerInfo(remoteSessionInfo, 1L);
                    this.demandConsumerInfo.setDispatchAsync(true);
                    String advisoryTopic = this.configuration.getDestinationFilter();
                    if (this.configuration.isBridgeTempDestinations()) {
                        advisoryTopic = advisoryTopic + "," + AdvisorySupport.TEMP_DESTINATION_COMPOSITE_ADVISORY_TOPIC;
                    }
                    this.demandConsumerInfo.setDestination(new ActiveMQTopic(advisoryTopic));
                    this.configureConsumerPrefetch(this.demandConsumerInfo);
                    this.remoteBroker.oneway(this.demandConsumerInfo);
                }
                this.startedLatch.countDown();
            }
        }
    }

    @Override
    public void serviceRemoteException(Throwable error) {
        if (!this.disposed.get()) {
            if (error instanceof SecurityException || error instanceof GeneralSecurityException) {
                LOG.error("Network connection between {} and {} shutdown due to a remote error: {}", this.localBroker, this.remoteBroker, error.toString());
            } else {
                LOG.warn("Network connection between {} and {} shutdown due to a remote error: {}", this.localBroker, this.remoteBroker, error.toString());
            }
            LOG.debug("The remote Exception was: {}", (Object)error, (Object)error);
            this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

                @Override
                public void run() {
                    ServiceSupport.dispose(DemandForwardingBridgeSupport.this.getControllingService());
                }
            });
            this.fireBridgeFailed(error);
        }
    }

    protected boolean isDirectBridgeConsumer(ConsumerInfo info) {
        return info.getSubscriptionName() != null && info.getSubscriptionName().startsWith(DURABLE_SUB_PREFIX) && (info.getClientId() == null || info.getClientId().startsWith(this.configuration.getName()));
    }

    protected boolean isProxyBridgeSubscription(String clientId, String subName) {
        return subName != null && clientId != null && subName.startsWith(DURABLE_SUB_PREFIX) && !clientId.startsWith(this.configuration.getName());
    }

    protected void addProxyNetworkSubscriptionClientId(DemandSubscription sub, String clientId, String subName) {
        if (clientId != null && sub != null && subName != null) {
            String newClientId = this.getProxyBridgeClientId(clientId);
            SubscriptionInfo newSubInfo = new SubscriptionInfo(newClientId, subName);
            sub.getDurableRemoteSubs().add(newSubInfo);
            LOG.debug("Adding proxy network subscription {} to demand subscription", (Object)newSubInfo);
        } else {
            LOG.debug("Skipping addProxyNetworkSubscription");
        }
    }

    protected void addProxyNetworkSubscriptionBrokerPath(DemandSubscription sub, BrokerId[] path, String subName) {
        if (sub != null && path.length > 1 && subName != null) {
            String b1 = path[path.length - 1].toString();
            String b2 = path[path.length - 2].toString();
            SubscriptionInfo newSubInfo = new SubscriptionInfo(b2 + this.configuration.getClientIdToken() + "inbound" + this.configuration.getClientIdToken() + b1, subName);
            sub.getDurableRemoteSubs().add(newSubInfo);
        }
    }

    private String getProxyBridgeClientId(String clientId) {
        String[] clientIdTokens;
        String newClientId = clientId;
        String[] stringArray = clientIdTokens = newClientId != null ? newClientId.split(Pattern.quote(this.configuration.getClientIdToken())) : null;
        if (clientIdTokens != null && clientIdTokens.length > 2) {
            newClientId = clientIdTokens[clientIdTokens.length - 3] + this.configuration.getClientIdToken() + "inbound" + this.configuration.getClientIdToken() + clientIdTokens[clientIdTokens.length - 1];
        }
        return newClientId;
    }

    protected boolean isProxyNSConsumerBrokerPath(ConsumerInfo info) {
        return info.getBrokerPath() != null && info.getBrokerPath().length > 1;
    }

    protected boolean isProxyNSConsumerClientId(String clientId) {
        return clientId != null && clientId.split(Pattern.quote(this.configuration.getClientIdToken())).length > 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void serviceRemoteCommand(Command command) {
        if (!this.disposed.get()) {
            try {
                if (command.isMessageDispatch()) {
                    this.safeWaitUntilStarted();
                    MessageDispatch md = (MessageDispatch)command;
                    this.serviceRemoteConsumerAdvisory(md.getMessage().getDataStructure());
                    this.ackAdvisory(md.getMessage());
                } else if (command.isBrokerInfo()) {
                    this.futureRemoteBrokerInfo.set((BrokerInfo)command);
                } else if (command instanceof BrokerSubscriptionInfo) {
                    final BrokerSubscriptionInfo brokerSubscriptionInfo = (BrokerSubscriptionInfo)command;
                    this.syncExecutor.execute(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                DemandForwardingBridgeSupport.this.staticDestinationsLatch.await();
                                if (!DemandForwardingBridgeSupport.this.disposed.get()) {
                                    BrokerSubscriptionInfo subInfo = brokerSubscriptionInfo;
                                    LOG.debug("Received Remote BrokerSubscriptionInfo on {} from {}", (Object)DemandForwardingBridgeSupport.this.brokerService.getBrokerName(), (Object)subInfo.getBrokerName());
                                    if (DemandForwardingBridgeSupport.this.configuration.isSyncDurableSubs() && DemandForwardingBridgeSupport.this.configuration.isConduitSubscriptions() && !DemandForwardingBridgeSupport.this.configuration.isDynamicOnly() && DemandForwardingBridgeSupport.this.started.get()) {
                                        if (subInfo.getSubscriptionInfos() != null) {
                                            for (ConsumerInfo info : subInfo.getSubscriptionInfos()) {
                                                if (info.getSubscriptionName() != null && DemandForwardingBridgeSupport.this.isDirectBridgeConsumer(info) || !NetworkBridgeUtils.matchesDestinations(DemandForwardingBridgeSupport.this.dynamicallyIncludedDestinations, info.getDestination())) continue;
                                                DemandForwardingBridgeSupport.this.serviceRemoteConsumerAdvisory(info);
                                            }
                                        }
                                        Iterator i = DemandForwardingBridgeSupport.this.subscriptionMapByLocalId.values().iterator();
                                        while (i.hasNext()) {
                                            DemandSubscription ds = (DemandSubscription)i.next();
                                            if (!NetworkBridgeUtils.matchesDestinations(DemandForwardingBridgeSupport.this.dynamicallyIncludedDestinations, ds.getLocalInfo().getDestination())) continue;
                                            DemandForwardingBridgeSupport.this.cleanupDurableSub(ds, i);
                                        }
                                    }
                                }
                            }
                            catch (Exception e) {
                                LOG.warn("Error processing BrokerSubscriptionInfo: {}", (Object)e.getMessage(), (Object)e);
                                LOG.debug(e.getMessage(), e);
                            }
                        }
                    });
                } else if (command.getClass() == ConnectionError.class) {
                    ConnectionError ce = (ConnectionError)command;
                    this.serviceRemoteException(ce.getException());
                } else if (this.isDuplex()) {
                    LOG.trace("{} duplex command type: {}", (Object)this.configuration.getBrokerName(), (Object)command.getDataStructureType());
                    if (command.isMessage()) {
                        final ActiveMQMessage message = (ActiveMQMessage)command;
                        if (NetworkBridgeFilter.isAdvisoryInterpretedByNetworkBridge(message)) {
                            this.serviceRemoteConsumerAdvisory(message.getDataStructure());
                            this.ackAdvisory(message);
                        } else {
                            if (!this.isPermissableDestination(message.getDestination(), true)) {
                                return;
                            }
                            if (this.canDuplexDispatch(message)) {
                                message.setProducerId(this.duplexInboundLocalProducerInfo.getProducerId());
                                if (message.isResponseRequired() || this.configuration.isAlwaysSyncSend()) {
                                    this.duplexInboundLocalBroker.asyncRequest(message, new ResponseCallback(){
                                        final int correlationId;
                                        {
                                            this.correlationId = message.getCommandId();
                                        }

                                        @Override
                                        public void onCompletion(FutureResponse resp) {
                                            try {
                                                Response reply = resp.getResult();
                                                reply.setCorrelationId(this.correlationId);
                                                DemandForwardingBridgeSupport.this.remoteBroker.oneway(reply);
                                                DemandForwardingBridgeSupport.this.networkBridgeStatistics.getReceivedCount().increment();
                                            }
                                            catch (IOException error) {
                                                LOG.error("Exception: {} on duplex forward of: {}", (Object)error, (Object)message);
                                                DemandForwardingBridgeSupport.this.serviceRemoteException(error);
                                            }
                                        }
                                    });
                                } else {
                                    this.duplexInboundLocalBroker.oneway(message);
                                    this.networkBridgeStatistics.getReceivedCount().increment();
                                }
                                this.serviceInboundMessage(message);
                            } else if (message.isResponseRequired() || this.configuration.isAlwaysSyncSend()) {
                                Response reply = new Response();
                                reply.setCorrelationId(message.getCommandId());
                                this.remoteBroker.oneway(reply);
                            }
                        }
                    } else {
                        switch (command.getDataStructureType()) {
                            case 3: {
                                if (this.duplexInitiatingConnection != null && this.duplexInitiatingConnectionInfoReceived.compareAndSet(false, true)) {
                                    this.duplexInitiatingConnection.processAddConnection((ConnectionInfo)command);
                                    break;
                                }
                                this.localBroker.oneway(command);
                                break;
                            }
                            case 4: {
                                this.localBroker.oneway(command);
                                break;
                            }
                            case 6: {
                                break;
                            }
                            case 22: {
                                MessageAck ack = (MessageAck)command;
                                DemandSubscription localSub = (DemandSubscription)this.subscriptionMapByRemoteId.get(ack.getConsumerId());
                                if (localSub != null) {
                                    ack.setConsumerId(localSub.getLocalInfo().getConsumerId());
                                    this.localBroker.oneway(ack);
                                    break;
                                }
                                LOG.warn("Matching local subscription not found for ack: {}", (Object)ack);
                                break;
                            }
                            case 5: {
                                this.localStartedLatch.await();
                                if (this.started.get()) {
                                    ConsumerInfo consumerInfo = (ConsumerInfo)command;
                                    if (this.isDuplicateSuppressionOff(consumerInfo)) {
                                        this.addConsumerInfo(consumerInfo);
                                        break;
                                    }
                                    URI uRI = this.brokerService.getVmConnectorURI();
                                    synchronized (uRI) {
                                        this.addConsumerInfo(consumerInfo);
                                        break;
                                    }
                                }
                                LOG.warn("Stopping - ignoring ConsumerInfo: {}", (Object)command);
                                break;
                            }
                            case 11: {
                                LOG.info("Stopping network bridge on shutdown of remote broker");
                                this.serviceRemoteException(new IOException(command.toString()));
                                break;
                            }
                            default: {
                                LOG.debug("Ignoring remote command: {}", (Object)command);
                                break;
                            }
                        }
                    }
                } else {
                    switch (command.getDataStructureType()) {
                        case 1: 
                        case 10: 
                        case 11: {
                            break;
                        }
                        default: {
                            LOG.warn("Unexpected remote command: {}", (Object)command);
                        }
                    }
                }
            }
            catch (Throwable e) {
                LOG.debug("Exception processing remote command: {}", (Object)command, (Object)e);
                this.serviceRemoteException(e);
            }
        }
    }

    private void ackAdvisory(Message message) throws IOException {
        ++this.demandConsumerDispatched;
        if ((float)this.demandConsumerDispatched > (float)this.demandConsumerInfo.getPrefetchSize() * ((float)this.configuration.getAdvisoryAckPercentage() / 100.0f)) {
            final MessageAck ack = new MessageAck(message, 2, this.demandConsumerDispatched);
            ack.setConsumerId(this.demandConsumerInfo.getConsumerId());
            this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        DemandForwardingBridgeSupport.this.remoteBroker.oneway(ack);
                    }
                    catch (IOException e) {
                        LOG.warn("Failed to send advisory ack " + ack, e);
                    }
                }
            });
            this.demandConsumerDispatched = 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void serviceRemoteConsumerAdvisory(DataStructure data) throws IOException {
        block21: {
            block22: {
                block20: {
                    int networkTTL;
                    block19: {
                        networkTTL = this.configuration.getConsumerTTL();
                        if (data.getClass() != ConsumerInfo.class) break block19;
                        ConsumerInfo info = (ConsumerInfo)data;
                        BrokerId[] path = info.getBrokerPath();
                        if (info.isBrowser()) {
                            LOG.debug("{} Ignoring sub from {}, browsers explicitly suppressed", (Object)this.configuration.getBrokerName(), (Object)this.remoteBrokerName);
                            return;
                        }
                        if (path != null && networkTTL > -1 && path.length >= networkTTL) {
                            LOG.debug("{} Ignoring sub from {}, restricted to {} network hops only: {}", this.configuration.getBrokerName(), this.remoteBrokerName, networkTTL, info);
                            return;
                        }
                        if (DemandForwardingBridgeSupport.contains(path, this.localBrokerPath[0])) {
                            LOG.debug("{} Ignoring sub from {}, already routed through this broker once: {}", this.configuration.getBrokerName(), this.remoteBrokerName, info);
                            return;
                        }
                        if (!this.isPermissableDestination(info.getDestination())) {
                            LOG.debug("{} Ignoring sub from {}, destination {} is not permitted: {}", this.configuration.getBrokerName(), this.remoteBrokerName, info.getDestination(), info);
                            return;
                        }
                        if (this.isDuplicateSuppressionOff(info)) {
                            this.addConsumerInfo(info);
                        } else {
                            URI uRI = this.brokerService.getVmConnectorURI();
                            synchronized (uRI) {
                                this.addConsumerInfo(info);
                            }
                        }
                    }
                    if (data.getClass() != DestinationInfo.class) break block20;
                    final DestinationInfo destInfo = (DestinationInfo)data;
                    BrokerId[] path = destInfo.getBrokerPath();
                    if (path != null && networkTTL > -1 && path.length >= networkTTL) {
                        LOG.debug("{} Ignoring destination {} restricted to {} network hops only", this.configuration.getBrokerName(), destInfo, networkTTL);
                        return;
                    }
                    if (DemandForwardingBridgeSupport.contains(destInfo.getBrokerPath(), this.localBrokerPath[0])) {
                        LOG.debug("{} Ignoring destination {} already routed through this broker once", (Object)this.configuration.getBrokerName(), (Object)destInfo);
                        return;
                    }
                    destInfo.setConnectionId(this.localConnectionInfo.getConnectionId());
                    if (destInfo.getDestination() instanceof ActiveMQTempDestination) {
                        ActiveMQTempDestination tempDest = (ActiveMQTempDestination)destInfo.getDestination();
                        tempDest.setConnectionId(this.localSessionInfo.getSessionId().getConnectionId());
                    }
                    destInfo.setBrokerPath(this.appendToBrokerPath(destInfo.getBrokerPath(), this.getRemoteBrokerPath()));
                    LOG.trace("{} bridging {} destination on {} from {}, destination: {}", this.configuration.getBrokerName(), destInfo.isAddOperation() ? "add" : "remove", this.localBroker, this.remoteBrokerName, destInfo);
                    if (destInfo.isRemoveOperation()) {
                        this.serialExecutor.execute(new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    DemandForwardingBridgeSupport.this.localBroker.oneway(destInfo);
                                }
                                catch (IOException e) {
                                    LOG.warn("failed to deliver remove command for destination: {}", (Object)destInfo.getDestination(), (Object)e);
                                }
                            }
                        });
                    } else {
                        this.localBroker.oneway(destInfo);
                    }
                    break block21;
                }
                if (data.getClass() != RemoveInfo.class) break block22;
                ConsumerId id = (ConsumerId)((RemoveInfo)data).getObjectId();
                this.removeDemandSubscription(id);
                if (!this.forcedDurableRemoteId.remove(id)) break block21;
                Iterator<DemandSubscription> i = this.subscriptionMapByLocalId.values().iterator();
                while (i.hasNext()) {
                    DemandSubscription ds = (DemandSubscription)i.next();
                    boolean removed = ds.removeForcedDurableConsumer(id);
                    if (!removed) continue;
                    this.cleanupDurableSub(ds, i);
                }
                break block21;
            }
            if (data.getClass() == RemoveSubscriptionInfo.class) {
                RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)data;
                SubscriptionInfo subscriptionInfo = new SubscriptionInfo(info.getClientId(), info.getSubscriptionName());
                boolean proxyBridgeSub = this.isProxyBridgeSubscription(subscriptionInfo.getClientId(), subscriptionInfo.getSubscriptionName());
                Iterator<DemandSubscription> i = this.subscriptionMapByLocalId.values().iterator();
                while (i.hasNext()) {
                    DemandSubscription ds = (DemandSubscription)i.next();
                    boolean removed = ds.getDurableRemoteSubs().remove(subscriptionInfo);
                    if (!removed && proxyBridgeSub) {
                        subscriptionInfo.setClientId(this.getProxyBridgeClientId(subscriptionInfo.getClientId()));
                        if (ds.getDurableRemoteSubs().contains(subscriptionInfo)) {
                            ds.getDurableRemoteSubs().remove(subscriptionInfo);
                            removed = true;
                        }
                    }
                    if (!removed) continue;
                    this.cleanupDurableSub(ds, i);
                }
            }
        }
    }

    private void cleanupDurableSub(DemandSubscription ds, Iterator<DemandSubscription> i) throws IOException {
        if (ds != null && ds.getLocalDurableSubscriber() != null && ds.getDurableRemoteSubs().isEmpty() && ds.getForcedDurableConsumersSize() == 0) {
            RemoveInfo removeInfo = new RemoveInfo(ds.getLocalInfo().getConsumerId());
            this.localBroker.oneway(removeInfo);
            RemoveSubscriptionInfo sending = new RemoveSubscriptionInfo();
            sending.setClientId(this.localClientId);
            sending.setSubscriptionName(ds.getLocalDurableSubscriber().getSubscriptionName());
            sending.setConnectionId(this.localConnectionInfo.getConnectionId());
            this.localBroker.oneway(sending);
            i.remove();
            this.subscriptionMapByRemoteId.remove(ds.getRemoteInfo().getConsumerId());
        }
    }

    @Override
    public void serviceLocalException(Throwable error) {
        this.serviceLocalException(null, error);
    }

    public void serviceLocalException(MessageDispatch messageDispatch, Throwable error) {
        LOG.trace("serviceLocalException: disposed {} ex", (Object)this.disposed.get(), (Object)error);
        if (!this.disposed.get()) {
            if (error instanceof DestinationDoesNotExistException && ((DestinationDoesNotExistException)error).isTemporary()) {
                if (messageDispatch != null) {
                    LOG.warn("PoisonAck of {} on forwarding error: {}", (Object)messageDispatch.getMessage().getMessageId(), (Object)error);
                    try {
                        MessageAck poisonAck = new MessageAck(messageDispatch, 1, 1);
                        poisonAck.setPoisonCause(error);
                        this.localBroker.oneway(poisonAck);
                    }
                    catch (IOException ioe) {
                        LOG.error("Failed to posion ack message following forward failure: ", ioe);
                    }
                    this.fireFailedForwardAdvisory(messageDispatch, error);
                } else {
                    LOG.warn("Ignoring exception on forwarding to non existent temp dest: ", error);
                }
                return;
            }
            LOG.info("Network connection between {} and {} shutdown due to a local error: {}", this.localBroker, this.remoteBroker, error);
            LOG.debug("The local Exception was: {}", (Object)error, (Object)error);
            this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

                @Override
                public void run() {
                    ServiceSupport.dispose(DemandForwardingBridgeSupport.this.getControllingService());
                }
            });
            this.fireBridgeFailed(error);
        }
    }

    private void fireFailedForwardAdvisory(MessageDispatch messageDispatch, Throwable error) {
        if (this.configuration.isAdvisoryForFailedForward()) {
            AdvisoryBroker advisoryBroker = null;
            try {
                advisoryBroker = (AdvisoryBroker)this.brokerService.getBroker().getAdaptor(AdvisoryBroker.class);
                if (advisoryBroker != null) {
                    ConnectionContext context = new ConnectionContext();
                    context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
                    context.setBroker(this.brokerService.getBroker());
                    ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                    advisoryMessage.setStringProperty("cause", error.getLocalizedMessage());
                    advisoryBroker.fireAdvisory(context, AdvisorySupport.getNetworkBridgeForwardFailureAdvisoryTopic(), messageDispatch.getMessage(), null, advisoryMessage);
                }
            }
            catch (Exception e) {
                LOG.warn("failed to fire forward failure advisory, cause: {}", (Object)e);
                LOG.debug("detail", e);
            }
        }
    }

    protected Service getControllingService() {
        return this.duplexInitiatingConnection != null ? this.duplexInitiatingConnection : this;
    }

    protected void addSubscription(DemandSubscription sub) throws IOException {
        if (sub != null) {
            this.localBroker.oneway(sub.getLocalInfo());
        }
    }

    protected void removeSubscription(final DemandSubscription sub) throws IOException {
        if (sub != null) {
            LOG.trace("{} remove local subscription: {} for remote {}", this.configuration.getBrokerName(), sub.getLocalInfo().getConsumerId(), sub.getRemoteInfo().getConsumerId());
            this.subscriptionMapByLocalId.remove(sub.getLocalInfo().getConsumerId());
            this.subscriptionMapByRemoteId.remove(sub.getRemoteInfo().getConsumerId());
            this.serialExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    sub.waitForCompletion();
                    try {
                        DemandForwardingBridgeSupport.this.localBroker.oneway(sub.getLocalInfo().createRemoveCommand());
                    }
                    catch (IOException e) {
                        LOG.warn("failed to deliver remove command for local subscription, for remote {}", (Object)sub.getRemoteInfo().getConsumerId(), (Object)e);
                    }
                }
            });
        }
    }

    protected Message configureMessage(MessageDispatch md) throws IOException {
        Message message = md.getMessage().copy();
        message.setBrokerPath(this.appendToBrokerPath(message.getBrokerPath(), this.localBrokerPath));
        message.setProducerId(this.producerInfo.getProducerId());
        message.setDestination(md.getDestination());
        message.setMemoryUsage(null);
        if (message.getOriginalTransactionId() == null) {
            message.setOriginalTransactionId(message.getTransactionId());
        }
        message.setTransactionId(null);
        if (this.configuration.isUseCompression()) {
            message.compress();
        }
        return message;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void serviceLocalCommand(Command command) {
        block30: {
            if (!this.disposed.get()) {
                try {
                    if (command.isMessageDispatch()) {
                        this.safeWaitUntilStarted();
                        this.networkBridgeStatistics.getEnqueues().increment();
                        final MessageDispatch md = (MessageDispatch)command;
                        final DemandSubscription sub = (DemandSubscription)this.subscriptionMapByLocalId.get(md.getConsumerId());
                        if (sub != null && md.getMessage() != null && sub.incrementOutstandingResponses()) {
                            if (this.suppressMessageDispatch(md, sub)) {
                                LOG.debug("{} message not forwarded to {} because message came from there or fails TTL, brokerPath: {}, message: {}", this.configuration.getBrokerName(), this.remoteBrokerName, Arrays.toString(md.getMessage().getBrokerPath()), md.getMessage());
                                try {
                                    this.localBroker.oneway(new MessageAck(md, 4, 1));
                                }
                                finally {
                                    sub.decrementOutstandingResponses();
                                }
                                return;
                            }
                            Message message = this.configureMessage(md);
                            LOG.debug("bridging ({} -> {}), consumer: {}, destination: {}, brokerPath: {}, message: {}", this.configuration.getBrokerName(), this.remoteBrokerName, md.getConsumerId(), message.getDestination(), Arrays.toString(message.getBrokerPath()), LOG.isTraceEnabled() ? message : message.getMessageId());
                            if (this.isDuplex() && NetworkBridgeFilter.isAdvisoryInterpretedByNetworkBridge(message)) {
                                try {
                                    this.remoteBroker.oneway(message);
                                }
                                finally {
                                    sub.decrementOutstandingResponses();
                                }
                                return;
                            }
                            if (!this.isPermissableDestination(md.getDestination())) break block30;
                            if (message.isPersistent() || this.configuration.isAlwaysSyncSend()) {
                                this.remoteBroker.asyncRequest(message, new ResponseCallback(){

                                    /*
                                     * WARNING - Removed try catching itself - possible behaviour change.
                                     */
                                    @Override
                                    public void onCompletion(FutureResponse future) {
                                        try {
                                            Response response = future.getResult();
                                            if (response.isException()) {
                                                ExceptionResponse er = (ExceptionResponse)response;
                                                DemandForwardingBridgeSupport.this.serviceLocalException(md, er.getException());
                                            } else {
                                                DemandForwardingBridgeSupport.this.localBroker.oneway(new MessageAck(md, 4, 1));
                                                DemandForwardingBridgeSupport.this.networkBridgeStatistics.getDequeues().increment();
                                            }
                                        }
                                        catch (IOException e) {
                                            DemandForwardingBridgeSupport.this.serviceLocalException(md, e);
                                        }
                                        finally {
                                            sub.decrementOutstandingResponses();
                                        }
                                    }
                                });
                            } else {
                                try {
                                    this.remoteBroker.oneway(message);
                                    this.localBroker.oneway(new MessageAck(md, 4, 1));
                                    this.networkBridgeStatistics.getDequeues().increment();
                                }
                                finally {
                                    sub.decrementOutstandingResponses();
                                }
                            }
                            this.serviceOutbound(message);
                            break block30;
                        }
                        LOG.debug("No subscription registered with this network bridge for consumerId: {} for message: {}", (Object)md.getConsumerId(), (Object)md.getMessage());
                        break block30;
                    }
                    if (command.isBrokerInfo()) {
                        this.futureLocalBrokerInfo.set((BrokerInfo)command);
                    } else if (command.isShutdownInfo()) {
                        LOG.info("{} Shutting down {}", (Object)this.configuration.getBrokerName(), (Object)this.configuration.getName());
                        this.stop();
                    } else if (command.getClass() == ConnectionError.class) {
                        ConnectionError ce = (ConnectionError)command;
                        this.serviceLocalException(ce.getException());
                    } else {
                        switch (command.getDataStructureType()) {
                            case 1: {
                                break;
                            }
                            case 92: {
                                break;
                            }
                            default: {
                                LOG.warn("Unexpected local command: {}", (Object)command);
                            }
                        }
                    }
                }
                catch (Throwable e) {
                    LOG.warn("Caught an exception processing local command", e);
                    this.serviceLocalException(e);
                }
            }
        }
    }

    private boolean suppressMessageDispatch(MessageDispatch md, DemandSubscription sub) throws Exception {
        boolean suppress = false;
        if (sub.getLocalInfo().isDurable()) {
            NonCachedMessageEvaluationContext messageEvalContext = new NonCachedMessageEvaluationContext();
            messageEvalContext.setMessageReference(md.getMessage());
            messageEvalContext.setDestination(md.getDestination());
            suppress = !sub.getNetworkBridgeFilter().matches(messageEvalContext);
        }
        return suppress;
    }

    public static boolean contains(BrokerId[] brokerPath, BrokerId brokerId) {
        if (brokerPath != null) {
            for (BrokerId id : brokerPath) {
                if (!brokerId.equals(id)) continue;
                return true;
            }
        }
        return false;
    }

    protected BrokerId[] appendToBrokerPath(BrokerId[] brokerPath, BrokerId[] pathsToAppend) {
        if (brokerPath == null || brokerPath.length == 0) {
            return pathsToAppend;
        }
        BrokerId[] rc = new BrokerId[brokerPath.length + pathsToAppend.length];
        System.arraycopy(brokerPath, 0, rc, 0, brokerPath.length);
        System.arraycopy(pathsToAppend, 0, rc, brokerPath.length, pathsToAppend.length);
        return rc;
    }

    protected BrokerId[] appendToBrokerPath(BrokerId[] brokerPath, BrokerId idToAppend) {
        if (brokerPath == null || brokerPath.length == 0) {
            return new BrokerId[]{idToAppend};
        }
        BrokerId[] rc = new BrokerId[brokerPath.length + 1];
        System.arraycopy(brokerPath, 0, rc, 0, brokerPath.length);
        rc[brokerPath.length] = idToAppend;
        return rc;
    }

    protected boolean isPermissableDestination(ActiveMQDestination destination) {
        return this.isPermissableDestination(destination, false);
    }

    protected boolean isPermissableDestination(ActiveMQDestination destination, boolean allowTemporary) {
        DestinationFilter inclusionFilter;
        if (destination.isTemporary()) {
            if (allowTemporary) {
                return true;
            }
            return this.configuration.isBridgeTempDestinations();
        }
        ActiveMQDestination[] dests = this.excludedDestinations;
        if (dests != null && dests.length > 0) {
            for (ActiveMQDestination dest : dests) {
                DestinationFilter exclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !exclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return false;
            }
        }
        if ((dests = this.staticallyIncludedDestinations) != null && dests.length > 0) {
            for (ActiveMQDestination dest : dests) {
                inclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !inclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return true;
            }
        }
        if ((dests = this.dynamicallyIncludedDestinations) != null && dests.length > 0) {
            for (ActiveMQDestination dest : dests) {
                inclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !inclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    protected void setupStaticDestinations() {
        ActiveMQDestination[] dests = this.staticallyIncludedDestinations;
        if (dests != null) {
            for (ActiveMQDestination dest : dests) {
                if (this.isPermissableDestination(dest)) {
                    DemandSubscription sub = this.createDemandSubscription(dest, null);
                    if (sub != null) {
                        sub.setStaticallyIncluded(true);
                        try {
                            this.addSubscription(sub);
                        }
                        catch (IOException e) {
                            LOG.error("Failed to add static destination {}", (Object)dest, (Object)e);
                        }
                        LOG.trace("{}, bridging messages for static destination: {}", (Object)this.configuration.getBrokerName(), (Object)dest);
                        continue;
                    }
                    LOG.info("{}, static destination excluded: {}, demand already exists", (Object)this.configuration.getBrokerName(), (Object)dest);
                    continue;
                }
                LOG.info("{}, static destination excluded: {}", (Object)this.configuration.getBrokerName(), (Object)dest);
            }
        }
    }

    protected void addConsumerInfo(ConsumerInfo consumerInfo) throws IOException {
        ConsumerInfo info = consumerInfo.copy();
        this.addRemoteBrokerToBrokerPath(info);
        DemandSubscription sub = this.createDemandSubscription(info);
        if (sub != null) {
            if (this.duplicateSuppressionIsRequired(sub)) {
                this.undoMapRegistration(sub);
            } else {
                if (consumerInfo.isDurable()) {
                    if (this.isProxyNSConsumerBrokerPath(sub.getRemoteInfo()) && info.getSubscriptionName() != null && info.getSubscriptionName().startsWith(DURABLE_SUB_PREFIX)) {
                        BrokerId[] path = info.getBrokerPath();
                        this.addProxyNetworkSubscriptionBrokerPath(sub, path, consumerInfo.getSubscriptionName());
                    } else if (this.isProxyNSConsumerClientId(sub.getRemoteInfo().getClientId()) && this.isProxyBridgeSubscription(info.getClientId(), info.getSubscriptionName())) {
                        this.addProxyNetworkSubscriptionClientId(sub, sub.getRemoteInfo().getClientId(), consumerInfo.getSubscriptionName());
                    } else {
                        sub.getDurableRemoteSubs().add(new SubscriptionInfo(sub.getRemoteInfo().getClientId(), consumerInfo.getSubscriptionName()));
                    }
                }
                this.addSubscription(sub);
                LOG.debug("{} new demand subscription: {}", (Object)this.configuration.getBrokerName(), (Object)sub);
            }
        }
    }

    private void undoMapRegistration(DemandSubscription sub) {
        this.subscriptionMapByLocalId.remove(sub.getLocalInfo().getConsumerId());
        this.subscriptionMapByRemoteId.remove(sub.getRemoteInfo().getConsumerId());
    }

    private boolean duplicateSuppressionIsRequired(DemandSubscription candidate) {
        ConsumerInfo consumerInfo = candidate.getRemoteInfo();
        boolean suppress = false;
        if (this.isDuplicateSuppressionOff(consumerInfo)) {
            return suppress;
        }
        List<ConsumerId> candidateConsumers = consumerInfo.getNetworkConsumerIds();
        Collection<Subscription> currentSubs = this.getRegionSubscriptions(consumerInfo.getDestination());
        for (Subscription sub : currentSubs) {
            List<ConsumerId> networkConsumers = sub.getConsumerInfo().getNetworkConsumerIds();
            if (networkConsumers.isEmpty() || !this.matchFound(candidateConsumers, networkConsumers)) continue;
            if (this.isInActiveDurableSub(sub)) {
                suppress = false;
                break;
            }
            suppress = this.hasLowerPriority(sub, candidate.getLocalInfo());
            break;
        }
        return suppress;
    }

    private boolean isDuplicateSuppressionOff(ConsumerInfo consumerInfo) {
        return !this.configuration.isSuppressDuplicateQueueSubscriptions() && !this.configuration.isSuppressDuplicateTopicSubscriptions() || consumerInfo.getDestination().isQueue() && !this.configuration.isSuppressDuplicateQueueSubscriptions() || consumerInfo.getDestination().isTopic() && !this.configuration.isSuppressDuplicateTopicSubscriptions();
    }

    private boolean isInActiveDurableSub(Subscription sub) {
        return sub.getConsumerInfo().isDurable() && sub instanceof DurableTopicSubscription && !((DurableTopicSubscription)sub).isActive();
    }

    private boolean hasLowerPriority(Subscription existingSub, ConsumerInfo candidateInfo) {
        boolean suppress = false;
        if (existingSub.getConsumerInfo().getPriority() >= candidateInfo.getPriority()) {
            LOG.debug("{} Ignoring duplicate subscription from {}, sub: {} is duplicate by network subscription with equal or higher network priority: {}, networkConsumerIds: {}", this.configuration.getBrokerName(), this.remoteBrokerName, candidateInfo, existingSub, existingSub.getConsumerInfo().getNetworkConsumerIds());
            suppress = true;
        } else {
            try {
                this.removeDuplicateSubscription(existingSub);
                LOG.debug("{} Replacing duplicate subscription {} with sub from {}, which has a higher priority, new sub: {}, networkConsumerIds: {}", this.configuration.getBrokerName(), existingSub.getConsumerInfo(), this.remoteBrokerName, candidateInfo, candidateInfo.getNetworkConsumerIds());
            }
            catch (IOException e) {
                LOG.error("Failed to remove duplicated sub as a result of sub with higher priority, sub: {}", (Object)existingSub, (Object)e);
            }
        }
        return suppress;
    }

    private void removeDuplicateSubscription(Subscription existingSub) throws IOException {
        for (NetworkConnector connector : this.brokerService.getNetworkConnectors()) {
            if (connector.removeDemandSubscription(existingSub.getConsumerInfo().getConsumerId())) break;
        }
    }

    private boolean matchFound(List<ConsumerId> candidateConsumers, List<ConsumerId> networkConsumers) {
        boolean found = false;
        for (ConsumerId aliasConsumer : networkConsumers) {
            if (!candidateConsumers.contains(aliasConsumer)) continue;
            found = true;
            break;
        }
        return found;
    }

    protected final Collection<Subscription> getRegionSubscriptions(ActiveMQDestination dest) {
        RegionBroker region_broker = (RegionBroker)this.brokerService.getRegionBroker();
        Region region = null;
        switch (dest.getDestinationType()) {
            case 1: {
                region = region_broker.getQueueRegion();
                break;
            }
            case 2: {
                region = region_broker.getTopicRegion();
                break;
            }
            case 5: {
                region = region_broker.getTempQueueRegion();
                break;
            }
            case 6: {
                region = region_broker.getTempTopicRegion();
            }
        }
        Collection<Subscription> subs = region instanceof AbstractRegion ? ((AbstractRegion)region).getSubscriptions().values() : null;
        return subs;
    }

    protected DemandSubscription createDemandSubscription(ConsumerInfo info) throws IOException {
        info.addNetworkConsumerId(info.getConsumerId());
        return this.doCreateDemandSubscription(info);
    }

    protected DemandSubscription doCreateDemandSubscription(ConsumerInfo info) throws IOException {
        DemandSubscription result = new DemandSubscription(info);
        result.getLocalInfo().setConsumerId(new ConsumerId(this.localSessionInfo.getSessionId(), this.consumerIdGenerator.getNextSequenceId()));
        if (info.getDestination().isTemporary()) {
            ActiveMQTempDestination dest = (ActiveMQTempDestination)result.getLocalInfo().getDestination();
            dest.setConnectionId(this.localConnectionInfo.getConnectionId().toString());
        }
        if (this.configuration.isDecreaseNetworkConsumerPriority()) {
            byte priority = (byte)this.configuration.getConsumerPriorityBase();
            if (info.getBrokerPath() != null && info.getBrokerPath().length > 1) {
                priority = (byte)(priority - (info.getBrokerPath().length + 1));
            }
            result.getLocalInfo().setPriority(priority);
            LOG.debug("{} using priority: {} for subscription: {}", this.configuration.getBrokerName(), priority, info);
        }
        this.configureDemandSubscription(info, result);
        return result;
    }

    protected final DemandSubscription createDemandSubscription(ActiveMQDestination destination, String subscriptionName) {
        ConsumerInfo info = new ConsumerInfo();
        info.setNetworkSubscription(true);
        info.setDestination(destination);
        if (subscriptionName != null) {
            info.setSubscriptionName(subscriptionName);
        }
        info.setBrokerPath(new BrokerId[]{this.remoteBrokerId});
        info.setConsumerId(new ConsumerId(this.localSessionInfo.getSessionId(), this.consumerIdGenerator.getNextSequenceId()));
        DemandSubscription result = null;
        try {
            result = this.createDemandSubscription(info);
        }
        catch (IOException e) {
            LOG.error("Failed to create DemandSubscription ", e);
        }
        return result;
    }

    protected void configureDemandSubscription(ConsumerInfo info, DemandSubscription sub) throws IOException {
        if (AdvisorySupport.isConsumerAdvisoryTopic(info.getDestination()) || AdvisorySupport.isVirtualDestinationConsumerAdvisoryTopic(info.getDestination())) {
            sub.getLocalInfo().setDispatchAsync(true);
        } else {
            sub.getLocalInfo().setDispatchAsync(this.configuration.isDispatchAsync());
        }
        this.configureConsumerPrefetch(sub.getLocalInfo());
        this.subscriptionMapByLocalId.put(sub.getLocalInfo().getConsumerId(), sub);
        this.subscriptionMapByRemoteId.put(sub.getRemoteInfo().getConsumerId(), sub);
        sub.setNetworkBridgeFilter(this.createNetworkBridgeFilter(info));
        if (!info.isDurable()) {
            sub.getLocalInfo().setAdditionalPredicate(sub.getNetworkBridgeFilter());
        } else {
            sub.setLocalDurableSubscriber(new SubscriptionInfo(info.getClientId(), info.getSubscriptionName()));
        }
    }

    protected void removeDemandSubscription(ConsumerId id) throws IOException {
        DemandSubscription sub = (DemandSubscription)this.subscriptionMapByRemoteId.remove(id);
        LOG.debug("{} remove request on {} from {}, consumer id: {}, matching sub: {}", this.configuration.getBrokerName(), this.localBroker, this.remoteBrokerName, id, sub);
        if (sub != null) {
            this.removeSubscription(sub);
            LOG.debug("{} removed sub on {} from {}: {}", this.configuration.getBrokerName(), this.localBroker, this.remoteBrokerName, sub.getRemoteInfo());
        }
    }

    protected boolean removeDemandSubscriptionByLocalId(ConsumerId consumerId) {
        boolean removeDone = false;
        DemandSubscription sub = (DemandSubscription)this.subscriptionMapByLocalId.get(consumerId);
        if (sub != null) {
            try {
                this.removeDemandSubscription(sub.getRemoteInfo().getConsumerId());
                removeDone = true;
            }
            catch (IOException e) {
                LOG.debug("removeDemandSubscriptionByLocalId failed for localId: {}", (Object)consumerId, (Object)e);
            }
        }
        return removeDone;
    }

    protected boolean safeWaitUntilStarted() throws InterruptedException {
        while (!this.disposed.get() && !this.startedLatch.await(1L, TimeUnit.SECONDS)) {
        }
        return !this.disposed.get();
    }

    protected NetworkBridgeFilter createNetworkBridgeFilter(ConsumerInfo info) throws IOException {
        PolicyEntry entry;
        NetworkBridgeFilterFactory filterFactory = this.defaultFilterFactory;
        if (this.brokerService != null && this.brokerService.getDestinationPolicy() != null && (entry = this.brokerService.getDestinationPolicy().getEntryFor(info.getDestination())) != null && entry.getNetworkBridgeFilterFactory() != null) {
            filterFactory = entry.getNetworkBridgeFilterFactory();
        }
        return filterFactory.create(info, this.getRemoteBrokerPath(), this.configuration.getMessageTTL(), this.configuration.getConsumerTTL());
    }

    protected void addRemoteBrokerToBrokerPath(ConsumerInfo info) throws IOException {
        info.setBrokerPath(this.appendToBrokerPath(info.getBrokerPath(), this.getRemoteBrokerPath()));
    }

    protected BrokerId[] getRemoteBrokerPath() {
        return this.remoteBrokerPath;
    }

    @Override
    public void setNetworkBridgeListener(NetworkBridgeListener listener) {
        this.networkBridgeListener = listener;
    }

    private void fireBridgeFailed(Throwable reason) {
        LOG.trace("fire bridge failed, listener: {}", (Object)this.networkBridgeListener, (Object)reason);
        NetworkBridgeListener l = this.networkBridgeListener;
        if (l != null && this.bridgeFailed.compareAndSet(false, true)) {
            l.bridgeFailed();
        }
    }

    public ActiveMQDestination[] getDynamicallyIncludedDestinations() {
        return this.dynamicallyIncludedDestinations;
    }

    public void setDynamicallyIncludedDestinations(ActiveMQDestination[] dynamicallyIncludedDestinations) {
        this.dynamicallyIncludedDestinations = dynamicallyIncludedDestinations;
    }

    public ActiveMQDestination[] getExcludedDestinations() {
        return this.excludedDestinations;
    }

    public void setExcludedDestinations(ActiveMQDestination[] excludedDestinations) {
        this.excludedDestinations = excludedDestinations;
    }

    public ActiveMQDestination[] getStaticallyIncludedDestinations() {
        return this.staticallyIncludedDestinations;
    }

    public void setStaticallyIncludedDestinations(ActiveMQDestination[] staticallyIncludedDestinations) {
        this.staticallyIncludedDestinations = staticallyIncludedDestinations;
    }

    public ActiveMQDestination[] getDurableDestinations() {
        return this.durableDestinations;
    }

    public void setDurableDestinations(ActiveMQDestination[] durableDestinations) {
        this.durableDestinations = durableDestinations;
    }

    public Transport getLocalBroker() {
        return this.localBroker;
    }

    public Transport getRemoteBroker() {
        return this.remoteBroker;
    }

    public boolean isCreatedByDuplex() {
        return this.createdByDuplex;
    }

    public void setCreatedByDuplex(boolean createdByDuplex) {
        this.createdByDuplex = createdByDuplex;
    }

    @Override
    public String getRemoteAddress() {
        return this.remoteBroker.getRemoteAddress();
    }

    @Override
    public String getLocalAddress() {
        return this.localBroker.getRemoteAddress();
    }

    @Override
    public String getRemoteBrokerName() {
        return this.remoteBrokerInfo == null ? null : this.remoteBrokerInfo.getBrokerName();
    }

    @Override
    public String getRemoteBrokerId() {
        return this.remoteBrokerInfo == null || this.remoteBrokerInfo.getBrokerId() == null ? null : this.remoteBrokerInfo.getBrokerId().toString();
    }

    @Override
    public String getLocalBrokerName() {
        return this.localBrokerInfo == null ? null : this.localBrokerInfo.getBrokerName();
    }

    @Override
    public long getDequeueCounter() {
        return this.networkBridgeStatistics.getDequeues().getCount();
    }

    @Override
    public long getEnqueueCounter() {
        return this.networkBridgeStatistics.getEnqueues().getCount();
    }

    @Override
    public NetworkBridgeStatistics getNetworkBridgeStatistics() {
        return this.networkBridgeStatistics;
    }

    protected boolean isDuplex() {
        return this.configuration.isDuplex() || this.createdByDuplex;
    }

    public ConcurrentMap<ConsumerId, DemandSubscription> getLocalSubscriptionMap() {
        return this.subscriptionMapByRemoteId;
    }

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
        this.localBrokerPath[0] = this.localBrokerId = brokerService.getRegionBroker().getBrokerId();
    }

    @Override
    public void setMbeanObjectName(ObjectName objectName) {
        this.mbeanObjectName = objectName;
    }

    @Override
    public ObjectName getMbeanObjectName() {
        return this.mbeanObjectName;
    }

    @Override
    public void resetStats() {
        this.networkBridgeStatistics.reset();
    }

    protected void serviceOutbound(Message message) {
        NetworkBridgeListener l = this.networkBridgeListener;
        if (l != null) {
            l.onOutboundMessage(this, message);
        }
    }

    protected void serviceInboundMessage(Message message) {
        NetworkBridgeListener l = this.networkBridgeListener;
        if (l != null) {
            l.onInboundMessage(this, message);
        }
    }

    protected boolean canDuplexDispatch(Message message) {
        long lastStoredForMessageProducer;
        long producerSequenceId;
        boolean result = true;
        if (this.configuration.isCheckDuplicateMessagesOnDuplex() && (producerSequenceId = message.getMessageId().getProducerSequenceId()) <= (lastStoredForMessageProducer = this.getStoredSequenceIdForMessage(message.getMessageId()))) {
            result = false;
            LOG.debug("suppressing duplicate message send [{}] from network producer with producerSequence [{}] less than last stored: {}", LOG.isTraceEnabled() ? message : message.getMessageId(), producerSequenceId, lastStoredForMessageProducer);
        }
        return result;
    }

    protected long getStoredSequenceIdForMessage(MessageId messageId) {
        try {
            return this.brokerService.getPersistenceAdapter().getLastProducerSequenceId(messageId.getProducerId());
        }
        catch (IOException ignored) {
            LOG.debug("Failed to determine last producer sequence id for: {}", (Object)messageId, (Object)ignored);
            return -1L;
        }
    }

    protected void configureConsumerPrefetch(ConsumerInfo consumerInfo) {
        if (AdvisorySupport.isAdvisoryTopic(consumerInfo.getDestination()) && this.configuration.getAdvisoryPrefetchSize() > 0) {
            consumerInfo.setPrefetchSize(this.configuration.getAdvisoryPrefetchSize());
        } else {
            consumerInfo.setPrefetchSize(this.configuration.getPrefetchSize());
        }
    }

    private static class FutureBrokerInfo
    implements Future<BrokerInfo> {
        private final CountDownLatch slot = new CountDownLatch(1);
        private final AtomicBoolean disposed;
        private volatile BrokerInfo info = null;

        public FutureBrokerInfo(BrokerInfo info, AtomicBoolean disposed) {
            this.info = info;
            this.disposed = disposed;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            this.slot.countDown();
            return true;
        }

        @Override
        public boolean isCancelled() {
            return this.slot.getCount() == 0L && this.info == null;
        }

        @Override
        public boolean isDone() {
            return this.info != null;
        }

        @Override
        public BrokerInfo get() throws InterruptedException, ExecutionException {
            try {
                if (this.info == null) {
                    while (!this.disposed.get() && !this.slot.await(1L, TimeUnit.SECONDS)) {
                    }
                }
                return this.info;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.debug("Operation interrupted: {}", (Object)e, (Object)e);
                throw new InterruptedException("Interrupted.");
            }
        }

        @Override
        public BrokerInfo get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            try {
                if (this.info == null) {
                    long deadline = System.currentTimeMillis() + unit.toMillis(timeout2);
                    while (!(this.disposed.get() && System.currentTimeMillis() - deadline >= 0L || this.slot.await(1L, TimeUnit.MILLISECONDS))) {
                    }
                    if (this.info == null) {
                        throw new TimeoutException();
                    }
                }
                return this.info;
            }
            catch (InterruptedException e) {
                throw new InterruptedException("Interrupted.");
            }
        }

        public void set(BrokerInfo info) {
            this.info = info;
            this.slot.countDown();
        }
    }
}

