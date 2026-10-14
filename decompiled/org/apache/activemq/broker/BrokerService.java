/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.activemq.ActiveMQConnectionMetaData;
import org.apache.activemq.ConfigurationException;
import org.apache.activemq.Service;
import org.apache.activemq.advisory.AdvisoryBroker;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerContext;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.BrokerStoppedException;
import org.apache.activemq.broker.CompositeDestinationBroker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ErrorBroker;
import org.apache.activemq.broker.MutableBrokerFilter;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.broker.TransactionBroker;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.UserIDBroker;
import org.apache.activemq.broker.cluster.ConnectionSplitBroker;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.BrokerView;
import org.apache.activemq.broker.jmx.ConnectorView;
import org.apache.activemq.broker.jmx.HealthView;
import org.apache.activemq.broker.jmx.JmsConnectorView;
import org.apache.activemq.broker.jmx.JobSchedulerView;
import org.apache.activemq.broker.jmx.Log4JConfigView;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.jmx.NetworkConnectorView;
import org.apache.activemq.broker.jmx.ProxyConnectorView;
import org.apache.activemq.broker.region.CompositeDestinationInterceptor;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationFactoryImpl;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.broker.region.virtual.MirroredQueue;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.broker.region.virtual.VirtualDestinationInterceptor;
import org.apache.activemq.broker.region.virtual.VirtualTopic;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.broker.scheduler.SchedulerBroker;
import org.apache.activemq.broker.scheduler.memory.InMemoryJobSchedulerStore;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.network.ConnectionFilter;
import org.apache.activemq.network.DiscoveryNetworkConnector;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.network.jms.JmsConnector;
import org.apache.activemq.proxy.ProxyConnector;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.activemq.selector.SelectorParser;
import org.apache.activemq.store.JournaledStore;
import org.apache.activemq.store.PListStore;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.PersistenceAdapterFactory;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.TransportFactorySupport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.vm.VMTransportFactory;
import org.apache.activemq.usage.PercentLimitUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.util.BrokerSupport;
import org.apache.activemq.util.DefaultIOExceptionHandler;
import org.apache.activemq.util.IOExceptionHandler;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IOHelper;
import org.apache.activemq.util.InetAddressUtil;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.StoreUtil;
import org.apache.activemq.util.ThreadPoolUtils;
import org.apache.activemq.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class BrokerService
implements Service {
    public static final String DEFAULT_PORT = "61616";
    public static final String LOCAL_HOST_NAME;
    public static final String BROKER_VERSION;
    public static final String DEFAULT_BROKER_NAME = "localhost";
    public static final int DEFAULT_MAX_FILE_LENGTH = 0x2000000;
    public static final long DEFAULT_START_TIMEOUT = 600000L;
    private static final Logger LOG;
    private static final long serialVersionUID = 7353129142305630237L;
    private boolean useJmx = true;
    private boolean enableStatistics = true;
    private boolean persistent = true;
    private boolean populateJMSXUserID;
    private boolean useAuthenticatedPrincipalForJMSXUserID;
    private boolean populateUserNameInMBeans;
    private long mbeanInvocationTimeout = 0L;
    private boolean useShutdownHook = true;
    private boolean useLoggingForShutdownErrors;
    private boolean shutdownOnMasterFailure;
    private boolean shutdownOnSlaveFailure;
    private boolean waitForSlave;
    private long waitForSlaveTimeout = 600000L;
    private boolean passiveSlave;
    private String brokerName = "localhost";
    private File dataDirectoryFile;
    private File tmpDataDirectory;
    private Broker broker;
    private BrokerView adminView;
    private ManagementContext managementContext;
    private ObjectName brokerObjectName;
    private TaskRunnerFactory taskRunnerFactory;
    private TaskRunnerFactory persistenceTaskRunnerFactory;
    private SystemUsage systemUsage;
    private SystemUsage producerSystemUsage;
    private SystemUsage consumerSystemUsage;
    private PersistenceAdapter persistenceAdapter;
    private PersistenceAdapterFactory persistenceFactory;
    protected DestinationFactory destinationFactory;
    private MessageAuthorizationPolicy messageAuthorizationPolicy;
    private final List<TransportConnector> transportConnectors = new CopyOnWriteArrayList<TransportConnector>();
    private final List<NetworkConnector> networkConnectors = new CopyOnWriteArrayList<NetworkConnector>();
    private final List<ProxyConnector> proxyConnectors = new CopyOnWriteArrayList<ProxyConnector>();
    private final List<JmsConnector> jmsConnectors = new CopyOnWriteArrayList<JmsConnector>();
    private final List<Service> services = new ArrayList<Service>();
    private transient Thread shutdownHook;
    private String[] transportConnectorURIs;
    private String[] networkConnectorURIs;
    private JmsConnector[] jmsBridgeConnectors;
    private boolean deleteAllMessagesOnStartup;
    private boolean advisorySupport = true;
    private boolean anonymousProducerAdvisorySupport = false;
    private URI vmConnectorURI;
    private String defaultSocketURIString;
    private PolicyMap destinationPolicy;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final AtomicBoolean stopping = new AtomicBoolean(false);
    private final AtomicBoolean preShutdownHooksInvoked = new AtomicBoolean(false);
    private BrokerPlugin[] plugins;
    private boolean keepDurableSubsActive = true;
    private boolean useVirtualTopics = true;
    private boolean useMirroredQueues = false;
    private boolean useTempMirroredQueues = true;
    private boolean useVirtualDestSubs = false;
    private boolean useVirtualDestSubsOnCreation = false;
    private BrokerId brokerId;
    private volatile DestinationInterceptor[] destinationInterceptors;
    private ActiveMQDestination[] destinations;
    private PListStore tempDataStore;
    private int persistenceThreadPriority = 10;
    private boolean useLocalHostBrokerName;
    private final CountDownLatch stoppedLatch = new CountDownLatch(1);
    private final CountDownLatch startedLatch = new CountDownLatch(1);
    private Broker regionBroker;
    private int producerSystemUsagePortion = 60;
    private int consumerSystemUsagePortion = 40;
    private boolean splitSystemUsageForProducersConsumers;
    private boolean monitorConnectionSplits = false;
    private int taskRunnerPriority = 5;
    private boolean dedicatedTaskRunner;
    private boolean cacheTempDestinations = false;
    private int timeBeforePurgeTempDestinations = 5000;
    private final List<Runnable> shutdownHooks = new ArrayList<Runnable>();
    private boolean systemExitOnShutdown;
    private int systemExitOnShutdownExitCode;
    private SslContext sslContext;
    private boolean forceStart = false;
    private IOExceptionHandler ioExceptionHandler;
    private boolean schedulerSupport = false;
    private File schedulerDirectoryFile;
    private Scheduler scheduler;
    private ThreadPoolExecutor executor;
    private int schedulePeriodForDestinationPurge = 0;
    private int maxPurgedDestinationsPerSweep = 0;
    private int schedulePeriodForDiskUsageCheck = 0;
    private int diskUsageCheckRegrowThreshold = -1;
    private boolean adjustUsageLimits = true;
    private BrokerContext brokerContext;
    private boolean networkConnectorStartAsync = false;
    private boolean allowTempAutoCreationOnSend;
    private JobSchedulerStore jobSchedulerStore;
    private final AtomicLong totalConnections = new AtomicLong();
    private final AtomicInteger currentConnections = new AtomicInteger();
    private long offlineDurableSubscriberTimeout = -1L;
    private long offlineDurableSubscriberTaskSchedule = 300000L;
    private DestinationFilter virtualConsumerDestinationFilter;
    private final AtomicBoolean persistenceAdapterStarted = new AtomicBoolean(false);
    private Throwable startException = null;
    private boolean startAsync = false;
    private Date startDate;
    private boolean slave = true;
    private boolean restartAllowed = true;
    private boolean restartRequested = false;
    private boolean rejectDurableConsumers = false;
    private boolean rollbackOnlyOnAsyncException = true;
    private int storeOpenWireVersion = 11;
    private final List<Runnable> preShutdownHooks = new CopyOnWriteArrayList<Runnable>();
    private static final String brokerNameReplacedCharsRegExp = "[^a-zA-Z0-9\\.\\_\\-\\:]";

    public String toString() {
        return "BrokerService[" + this.getBrokerName() + "]";
    }

    private String getBrokerVersion() {
        String version = ActiveMQConnectionMetaData.PROVIDER_VERSION;
        if (version == null) {
            version = BROKER_VERSION;
        }
        return version;
    }

    public TransportConnector addConnector(String bindAddress) throws Exception {
        return this.addConnector(new URI(bindAddress));
    }

    public TransportConnector addConnector(URI bindAddress) throws Exception {
        return this.addConnector(this.createTransportConnector(bindAddress));
    }

    public TransportConnector addConnector(TransportServer transport) throws Exception {
        return this.addConnector(new TransportConnector(transport));
    }

    public TransportConnector addConnector(TransportConnector connector) throws Exception {
        this.transportConnectors.add(connector);
        return connector;
    }

    public boolean removeConnector(TransportConnector connector) throws Exception {
        boolean rc = this.transportConnectors.remove(connector);
        if (rc) {
            this.unregisterConnectorMBean(connector);
        }
        return rc;
    }

    public NetworkConnector addNetworkConnector(String discoveryAddress) throws Exception {
        return this.addNetworkConnector(new URI(discoveryAddress));
    }

    public ProxyConnector addProxyConnector(String bindAddress) throws Exception {
        return this.addProxyConnector(new URI(bindAddress));
    }

    public NetworkConnector addNetworkConnector(URI discoveryAddress) throws Exception {
        DiscoveryNetworkConnector connector = new DiscoveryNetworkConnector(discoveryAddress);
        return this.addNetworkConnector(connector);
    }

    public ProxyConnector addProxyConnector(URI bindAddress) throws Exception {
        ProxyConnector connector = new ProxyConnector();
        connector.setBind(bindAddress);
        connector.setRemote(new URI("fanout:multicast://default"));
        return this.addProxyConnector(connector);
    }

    public NetworkConnector addNetworkConnector(NetworkConnector connector) throws Exception {
        connector.setBrokerService(this);
        connector.setLocalUri(this.getVmConnectorURI());
        connector.setConnectionFilter(new ConnectionFilter(){

            @Override
            public boolean connectTo(URI location) {
                List<TransportConnector> transportConnectors = BrokerService.this.getTransportConnectors();
                Iterator<TransportConnector> iter = transportConnectors.iterator();
                while (iter.hasNext()) {
                    try {
                        TransportConnector tc = iter.next();
                        if (!location.equals(tc.getConnectUri())) continue;
                        return false;
                    }
                    catch (Throwable throwable) {
                    }
                }
                return true;
            }
        });
        this.networkConnectors.add(connector);
        return connector;
    }

    public boolean removeNetworkConnector(NetworkConnector connector) {
        boolean answer = this.networkConnectors.remove(connector);
        if (answer) {
            this.unregisterNetworkConnectorMBean(connector);
        }
        return answer;
    }

    public ProxyConnector addProxyConnector(ProxyConnector connector) throws Exception {
        URI uri = this.getVmConnectorURI();
        connector.setLocalUri(uri);
        this.proxyConnectors.add(connector);
        if (this.isUseJmx()) {
            this.registerProxyConnectorMBean(connector);
        }
        return connector;
    }

    public JmsConnector addJmsConnector(JmsConnector connector) throws Exception {
        connector.setBrokerService(this);
        this.jmsConnectors.add(connector);
        if (this.isUseJmx()) {
            this.registerJmsConnectorMBean(connector);
        }
        return connector;
    }

    public final void addPreShutdownHook(Runnable hook) {
        this.preShutdownHooks.add(hook);
    }

    public JmsConnector removeJmsConnector(JmsConnector connector) {
        if (this.jmsConnectors.remove(connector)) {
            return connector;
        }
        return null;
    }

    public void masterFailed() {
        if (this.shutdownOnMasterFailure) {
            LOG.error("The Master has failed ... shutting down");
            try {
                this.stop();
            }
            catch (Exception e) {
                LOG.error("Failed to stop for master failure", e);
            }
        } else {
            LOG.warn("Master Failed - starting all connectors");
            try {
                this.startAllConnectors();
                this.broker.nowMasterBroker();
            }
            catch (Exception e) {
                LOG.error("Failed to startAllConnectors", e);
            }
        }
    }

    public String getUptime() {
        long delta = this.getUptimeMillis();
        if (delta == 0L) {
            return "not started";
        }
        return TimeUtils.printDuration(delta);
    }

    public long getUptimeMillis() {
        if (this.startDate == null) {
            return 0L;
        }
        return new Date().getTime() - this.startDate.getTime();
    }

    public boolean isStarted() {
        return this.started.get() && this.startedLatch.getCount() == 0L;
    }

    public void start(boolean force) throws Exception {
        this.forceStart = force;
        this.stopped.set(false);
        this.started.set(false);
        this.start();
    }

    protected boolean shouldAutostart() {
        return true;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            this.autoStart();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void autoStart() throws Exception {
        if (this.shouldAutostart()) {
            this.start();
        }
    }

    @Override
    public void start() throws Exception {
        if (this.stopped.get() || !this.started.compareAndSet(false, true)) {
            return;
        }
        this.setStartException(null);
        this.stopping.set(false);
        this.preShutdownHooksInvoked.set(false);
        this.startDate = new Date();
        MDC.put("activemq.broker", this.brokerName);
        try {
            BrokerRegistry brokerRegistry;
            this.checkMemorySystemUsageLimits();
            if (this.systemExitOnShutdown && this.useShutdownHook) {
                throw new ConfigurationException("'useShutdownHook' property cannot be be used with 'systemExitOnShutdown', please turn it off (useShutdownHook=false)");
            }
            this.processHelperProperties();
            if (this.isUseJmx()) {
                MDC.remove("activemq.broker");
                try {
                    this.startManagementContext();
                    for (NetworkConnector connector : this.getNetworkConnectors()) {
                        this.registerNetworkConnectorMBean(connector);
                    }
                }
                finally {
                    MDC.put("activemq.broker", this.brokerName);
                }
            }
            if ((brokerRegistry = BrokerRegistry.getInstance()).lookup(this.getBrokerName()) == null) {
                brokerRegistry.bind(this.getBrokerName(), this);
            }
            this.startPersistenceAdapter(this.startAsync);
            this.startBroker(this.startAsync);
            brokerRegistry.bind(this.getBrokerName(), this);
        }
        catch (Exception e) {
            LOG.error("Failed to start Apache ActiveMQ ({}, {})", this.getBrokerName(), this.brokerId, e);
            try {
                if (!this.stopped.get()) {
                    this.stop();
                }
            }
            catch (Exception ex) {
                LOG.warn("Failed to stop broker after failure in start. This exception will be ignored.", ex);
            }
            throw e;
        }
        finally {
            MDC.remove("activemq.broker");
        }
    }

    private void startPersistenceAdapter(boolean async) throws Exception {
        if (async) {
            new Thread("Persistence Adapter Starting Thread"){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    try {
                        BrokerService.this.doStartPersistenceAdapter();
                    }
                    catch (Throwable e) {
                        BrokerService.this.setStartException(e);
                    }
                    finally {
                        AtomicBoolean atomicBoolean = BrokerService.this.persistenceAdapterStarted;
                        synchronized (atomicBoolean) {
                            BrokerService.this.persistenceAdapterStarted.set(true);
                            BrokerService.this.persistenceAdapterStarted.notifyAll();
                        }
                    }
                }
            }.start();
        } else {
            this.doStartPersistenceAdapter();
        }
    }

    private void doStartPersistenceAdapter() throws Exception {
        PersistenceAdapter persistenceAdapterToStart = this.getPersistenceAdapter();
        if (persistenceAdapterToStart == null) {
            this.checkStartException();
            throw new ConfigurationException("Cannot start null persistence adapter");
        }
        persistenceAdapterToStart.setUsageManager(this.getProducerSystemUsage());
        persistenceAdapterToStart.setBrokerName(this.getBrokerName());
        LOG.info("Using Persistence Adapter: {}", (Object)persistenceAdapterToStart);
        if (this.deleteAllMessagesOnStartup) {
            this.deleteAllMessages();
        }
        persistenceAdapterToStart.start();
        this.getTempDataStore();
        if (this.tempDataStore != null) {
            try {
                this.tempDataStore.start();
            }
            catch (Exception e) {
                RuntimeException exception = new RuntimeException("Failed to start temp data store: " + this.tempDataStore, e);
                LOG.error(exception.getLocalizedMessage(), e);
                throw exception;
            }
        }
        this.getJobSchedulerStore();
        if (this.jobSchedulerStore != null) {
            try {
                this.jobSchedulerStore.start();
            }
            catch (Exception e) {
                RuntimeException exception = new RuntimeException("Failed to start job scheduler store: " + this.jobSchedulerStore, e);
                LOG.error(exception.getLocalizedMessage(), e);
                throw exception;
            }
        }
    }

    private void startBroker(boolean async) throws Exception {
        if (async) {
            new Thread("Broker Starting Thread"){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    try {
                        AtomicBoolean atomicBoolean = BrokerService.this.persistenceAdapterStarted;
                        synchronized (atomicBoolean) {
                            if (!BrokerService.this.persistenceAdapterStarted.get()) {
                                BrokerService.this.persistenceAdapterStarted.wait();
                            }
                        }
                        BrokerService.this.doStartBroker();
                    }
                    catch (Throwable t2) {
                        BrokerService.this.setStartException(t2);
                    }
                }
            }.start();
        } else {
            this.doStartBroker();
        }
    }

    private void doStartBroker() throws Exception {
        this.checkStartException();
        this.startDestinations();
        this.addShutdownHook();
        this.broker = this.getBroker();
        this.brokerId = this.broker.getBrokerId();
        LOG.info("Apache ActiveMQ {} ({}, {}) is starting", this.getBrokerVersion(), this.getBrokerName(), this.brokerId);
        this.broker.start();
        if (this.isUseJmx()) {
            if (this.getManagementContext().isCreateConnector() && !this.getManagementContext().isConnectorStarted()) {
                this.managementContext.stop();
                this.startManagementContext();
            }
            ManagedRegionBroker managedBroker = (ManagedRegionBroker)this.regionBroker;
            managedBroker.setContextBroker(this.broker);
            this.adminView.setBroker(managedBroker);
        }
        if (this.ioExceptionHandler == null) {
            this.setIoExceptionHandler(new DefaultIOExceptionHandler());
        }
        if (this.isUseJmx() && Log4JConfigView.isLog4JAvailable()) {
            ObjectName objectName = BrokerMBeanSupport.createLog4JConfigViewName(this.getBrokerObjectName().toString());
            Log4JConfigView log4jConfigView = new Log4JConfigView();
            AnnotatedMBean.registerMBean(this.getManagementContext(), log4jConfigView, objectName);
        }
        this.startAllConnectors();
        LOG.info("Apache ActiveMQ {} ({}, {}) started", this.getBrokerVersion(), this.getBrokerName(), this.brokerId);
        LOG.info("For help or more information please see: http://activemq.apache.org");
        this.getBroker().brokerServiceStarted();
        this.checkStoreSystemUsageLimits();
        this.startedLatch.countDown();
        this.getBroker().nowMasterBroker();
    }

    @PreDestroy
    private void preDestroy() {
        try {
            this.stop();
        }
        catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        ServiceStopper stopper = new ServiceStopper();
        if (this.preShutdownHooksInvoked.compareAndSet(false, true)) {
            for (Runnable hook : this.preShutdownHooks) {
                try {
                    hook.run();
                }
                catch (Throwable e) {
                    stopper.onException(hook, e);
                }
            }
        }
        if (!this.stopping.compareAndSet(false, true)) {
            LOG.trace("Broker already stopping/stopped");
            return;
        }
        this.setStartException(new BrokerStoppedException("Stop invoked"));
        MDC.put("activemq.broker", this.brokerName);
        if (this.systemExitOnShutdown) {
            new Thread(){

                @Override
                public void run() {
                    System.exit(BrokerService.this.systemExitOnShutdownExitCode);
                }
            }.start();
        }
        LOG.info("Apache ActiveMQ {} ({}, {}) is shutting down", this.getBrokerVersion(), this.getBrokerName(), this.brokerId);
        this.removeShutdownHook();
        if (this.scheduler != null) {
            this.scheduler.stop();
            this.scheduler = null;
        }
        if (this.services != null) {
            for (Service service2 : this.services) {
                stopper.stop(service2);
            }
        }
        this.stopAllConnectors(stopper);
        this.slave = true;
        BrokerRegistry.getInstance().unbind(this.getBrokerName());
        VMTransportFactory.stopped(this.getBrokerName());
        if (this.broker != null) {
            stopper.stop(this.broker);
            this.broker = null;
        }
        if (this.jobSchedulerStore != null) {
            this.jobSchedulerStore.stop();
            this.jobSchedulerStore = null;
        }
        if (this.tempDataStore != null) {
            this.tempDataStore.stop();
            this.tempDataStore = null;
        }
        try {
            stopper.stop(this.getPersistenceAdapter());
            this.persistenceAdapter = null;
            if (this.isUseJmx()) {
                stopper.stop(this.managementContext);
                this.managementContext = null;
            }
            SelectorParser.clearCache();
        }
        finally {
            this.started.set(false);
            this.stopped.set(true);
            this.stoppedLatch.countDown();
        }
        if (this.taskRunnerFactory != null) {
            this.taskRunnerFactory.shutdown();
            this.taskRunnerFactory = null;
        }
        if (this.executor != null) {
            ThreadPoolUtils.shutdownNow(this.executor);
            this.executor = null;
        }
        this.destinationInterceptors = null;
        this.destinationFactory = null;
        if (this.startDate != null) {
            LOG.info("Apache ActiveMQ {} ({}, {}) uptime {}", this.getBrokerVersion(), this.getBrokerName(), this.brokerId, this.getUptime());
        }
        LOG.info("Apache ActiveMQ {} ({}, {}) is shutdown", this.getBrokerVersion(), this.getBrokerName(), this.brokerId);
        List<Runnable> list = this.shutdownHooks;
        synchronized (list) {
            for (Runnable hook : this.shutdownHooks) {
                try {
                    hook.run();
                }
                catch (Throwable e) {
                    stopper.onException(hook, e);
                }
            }
        }
        MDC.remove("activemq.broker");
        this.startDate = null;
        stopper.throwFirstException();
    }

    public boolean checkQueueSize(String queueName) {
        long count = 0L;
        long queueSize = 0L;
        Map<ActiveMQDestination, Destination> destinationMap = this.regionBroker.getDestinationMap();
        for (Map.Entry<ActiveMQDestination, Destination> entry : destinationMap.entrySet()) {
            if (!entry.getKey().isQueue() || !entry.getValue().getName().matches(queueName)) continue;
            queueSize = entry.getValue().getDestinationStatistics().getMessages().getCount();
            count += queueSize;
            if (queueSize <= 0L) continue;
            LOG.info("Queue has pending message: {} queueSize is: {}", (Object)entry.getValue().getName(), (Object)queueSize);
        }
        return count == 0L;
    }

    public void stopGracefully(String connectorName, String queueName, long timeout2, long pollInterval) throws Exception {
        if (this.isUseJmx()) {
            if (connectorName == null || queueName == null || timeout2 <= 0L) {
                throw new Exception("connectorName and queueName cannot be null and timeout should be >0 for stopGracefully.");
            }
            if (pollInterval <= 0L) {
                pollInterval = 30L;
            }
            LOG.info("Stop gracefully with connectorName: {} queueName: {} timeout: {} pollInterval: {}", connectorName, queueName, timeout2, pollInterval);
            for (int i = 0; i < this.transportConnectors.size(); ++i) {
                TransportConnector connector = this.transportConnectors.get(i);
                if (connector == null || connector.getName() == null || !connector.getName().matches(connectorName)) continue;
                connector.stop();
            }
            long start2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - start2 < timeout2 * 1000L) {
                if (this.checkQueueSize(queueName)) {
                    this.stop();
                    break;
                }
                Thread.sleep(pollInterval * 1000L);
            }
            if (this.stopped.get()) {
                LOG.info("Successfully stop the broker.");
            } else {
                LOG.info("There is still pending message on the queue. Please check and stop the broker manually.");
            }
        }
    }

    public void waitUntilStopped() {
        while (this.isStarted() && !this.stopped.get()) {
            try {
                this.stoppedLatch.await();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public boolean isStopped() {
        return this.stopped.get();
    }

    public boolean waitUntilStarted() {
        return this.waitUntilStarted(600000L);
    }

    public boolean waitUntilStarted(long timeout2) {
        boolean waitSucceeded = this.isStarted();
        long expiration = Math.max(0L, timeout2 + System.currentTimeMillis());
        while (!(this.isStarted() || this.stopped.get() || waitSucceeded || expiration <= System.currentTimeMillis())) {
            try {
                if (this.getStartException() != null) {
                    return waitSucceeded;
                }
                waitSucceeded = this.startedLatch.await(100L, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException interruptedException) {}
        }
        return waitSucceeded;
    }

    public Broker getBroker() throws Exception {
        if (this.broker == null) {
            this.checkStartException();
            this.broker = this.createBroker();
        }
        return this.broker;
    }

    public BrokerView getAdminView() throws Exception {
        if (this.adminView == null) {
            this.getBroker();
        }
        return this.adminView;
    }

    public void setAdminView(BrokerView adminView) {
        this.adminView = adminView;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        if (brokerName == null) {
            throw new NullPointerException("The broker name cannot be null");
        }
        String str = brokerName.replaceAll(brokerNameReplacedCharsRegExp, "_");
        if (!str.equals(brokerName)) {
            LOG.error("Broker Name: {} contained illegal characters matching regExp: {} - replaced with {}", brokerName, brokerNameReplacedCharsRegExp, str);
        }
        this.brokerName = str.trim();
    }

    public PersistenceAdapterFactory getPersistenceFactory() {
        return this.persistenceFactory;
    }

    public File getDataDirectoryFile() {
        if (this.dataDirectoryFile == null) {
            this.dataDirectoryFile = new File(IOHelper.getDefaultDataDirectory());
        }
        return this.dataDirectoryFile;
    }

    public File getBrokerDataDirectory() {
        String brokerDir = this.getBrokerName();
        return new File(this.getDataDirectoryFile(), brokerDir);
    }

    public void setDataDirectory(String dataDirectory) {
        this.setDataDirectoryFile(new File(dataDirectory));
    }

    public void setDataDirectoryFile(File dataDirectoryFile) {
        this.dataDirectoryFile = dataDirectoryFile;
    }

    public File getTmpDataDirectory() {
        if (this.tmpDataDirectory == null) {
            this.tmpDataDirectory = new File(this.getBrokerDataDirectory(), "tmp_storage");
        }
        return this.tmpDataDirectory;
    }

    public void setTmpDataDirectory(File tmpDataDirectory) {
        this.tmpDataDirectory = tmpDataDirectory;
    }

    public void setPersistenceFactory(PersistenceAdapterFactory persistenceFactory) {
        this.persistenceFactory = persistenceFactory;
    }

    public void setDestinationFactory(DestinationFactory destinationFactory) {
        this.destinationFactory = destinationFactory;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isPopulateJMSXUserID() {
        return this.populateJMSXUserID;
    }

    public void setPopulateJMSXUserID(boolean populateJMSXUserID) {
        this.populateJMSXUserID = populateJMSXUserID;
    }

    public SystemUsage getSystemUsage() {
        try {
            if (this.systemUsage == null) {
                this.systemUsage = new SystemUsage("Main", this.getPersistenceAdapter(), this.getTempDataStore(), this.getJobSchedulerStore());
                this.systemUsage.setExecutor(this.getExecutor());
                this.systemUsage.getMemoryUsage().setLimit(0x40000000L);
                this.systemUsage.getTempUsage().setLimit(0xC80000000L);
                this.systemUsage.getStoreUsage().setLimit(0x1900000000L);
                this.systemUsage.getJobSchedulerUsage().setLimit(0xC80000000L);
                this.addService(this.systemUsage);
            }
            return this.systemUsage;
        }
        catch (IOException e) {
            LOG.error("Cannot create SystemUsage", e);
            throw new RuntimeException("Fatally failed to create SystemUsage" + e.getMessage(), e);
        }
    }

    public void setSystemUsage(SystemUsage memoryManager) {
        if (this.systemUsage != null) {
            this.removeService(this.systemUsage);
        }
        this.systemUsage = memoryManager;
        if (this.systemUsage.getExecutor() == null) {
            this.systemUsage.setExecutor(this.getExecutor());
        }
        this.addService(this.systemUsage);
    }

    public SystemUsage getConsumerSystemUsage() throws IOException {
        if (this.consumerSystemUsage == null) {
            if (this.splitSystemUsageForProducersConsumers) {
                this.consumerSystemUsage = new SystemUsage(this.getSystemUsage(), "Consumer");
                float portion = (float)this.consumerSystemUsagePortion / 100.0f;
                this.consumerSystemUsage.getMemoryUsage().setUsagePortion(portion);
                this.addService(this.consumerSystemUsage);
            } else {
                this.consumerSystemUsage = this.getSystemUsage();
            }
        }
        return this.consumerSystemUsage;
    }

    public void setConsumerSystemUsage(SystemUsage consumerSystemUsage) {
        if (this.consumerSystemUsage != null) {
            this.removeService(this.consumerSystemUsage);
        }
        this.consumerSystemUsage = consumerSystemUsage;
        this.addService(this.consumerSystemUsage);
    }

    public SystemUsage getProducerSystemUsage() throws IOException {
        if (this.producerSystemUsage == null) {
            if (this.splitSystemUsageForProducersConsumers) {
                this.producerSystemUsage = new SystemUsage(this.getSystemUsage(), "Producer");
                float portion = (float)this.producerSystemUsagePortion / 100.0f;
                this.producerSystemUsage.getMemoryUsage().setUsagePortion(portion);
                this.addService(this.producerSystemUsage);
            } else {
                this.producerSystemUsage = this.getSystemUsage();
            }
        }
        return this.producerSystemUsage;
    }

    public void setProducerSystemUsage(SystemUsage producerUsageManager) {
        if (this.producerSystemUsage != null) {
            this.removeService(this.producerSystemUsage);
        }
        this.producerSystemUsage = producerUsageManager;
        this.addService(this.producerSystemUsage);
    }

    public synchronized PersistenceAdapter getPersistenceAdapter() throws IOException {
        if (this.persistenceAdapter == null && !this.hasStartException()) {
            this.persistenceAdapter = this.createPersistenceAdapter();
            this.configureService(this.persistenceAdapter);
            this.persistenceAdapter = this.registerPersistenceAdapterMBean(this.persistenceAdapter);
        }
        return this.persistenceAdapter;
    }

    public void setPersistenceAdapter(PersistenceAdapter persistenceAdapter) throws IOException {
        if (!this.isPersistent() && !(persistenceAdapter instanceof MemoryPersistenceAdapter)) {
            LOG.warn("persistent=\"false\", ignoring configured persistenceAdapter: {}", (Object)persistenceAdapter);
            return;
        }
        this.persistenceAdapter = persistenceAdapter;
        this.configureService(this.persistenceAdapter);
        this.persistenceAdapter = this.registerPersistenceAdapterMBean(persistenceAdapter);
    }

    public TaskRunnerFactory getTaskRunnerFactory() {
        if (this.taskRunnerFactory == null) {
            this.taskRunnerFactory = new TaskRunnerFactory("ActiveMQ BrokerService[" + this.getBrokerName() + "] Task", this.getTaskRunnerPriority(), true, 1000, this.isDedicatedTaskRunner());
            this.taskRunnerFactory.setThreadClassLoader(this.getClass().getClassLoader());
        }
        return this.taskRunnerFactory;
    }

    public void setTaskRunnerFactory(TaskRunnerFactory taskRunnerFactory) {
        this.taskRunnerFactory = taskRunnerFactory;
    }

    public TaskRunnerFactory getPersistenceTaskRunnerFactory() {
        if (this.taskRunnerFactory == null) {
            this.persistenceTaskRunnerFactory = new TaskRunnerFactory("Persistence Adaptor Task", this.persistenceThreadPriority, true, 1000, this.isDedicatedTaskRunner());
        }
        return this.persistenceTaskRunnerFactory;
    }

    public void setPersistenceTaskRunnerFactory(TaskRunnerFactory persistenceTaskRunnerFactory) {
        this.persistenceTaskRunnerFactory = persistenceTaskRunnerFactory;
    }

    public boolean isUseJmx() {
        return this.useJmx;
    }

    public boolean isEnableStatistics() {
        return this.enableStatistics;
    }

    public void setEnableStatistics(boolean enableStatistics) {
        this.enableStatistics = enableStatistics;
    }

    public void setUseJmx(boolean useJmx) {
        this.useJmx = useJmx;
    }

    public ObjectName getBrokerObjectName() throws MalformedObjectNameException {
        if (this.brokerObjectName == null) {
            this.brokerObjectName = this.createBrokerObjectName();
        }
        return this.brokerObjectName;
    }

    public void setBrokerObjectName(ObjectName brokerObjectName) {
        this.brokerObjectName = brokerObjectName;
    }

    public ManagementContext getManagementContext() {
        if (this.managementContext == null) {
            this.checkStartException();
            this.managementContext = new ManagementContext();
        }
        return this.managementContext;
    }

    private synchronized void checkStartException() {
        if (this.startException != null) {
            throw new BrokerStoppedException(this.startException);
        }
    }

    private synchronized boolean hasStartException() {
        return this.startException != null;
    }

    private synchronized void setStartException(Throwable t2) {
        this.startException = t2;
    }

    public void setManagementContext(ManagementContext managementContext) {
        this.managementContext = managementContext;
    }

    public NetworkConnector getNetworkConnectorByName(String connectorName) {
        for (NetworkConnector connector : this.networkConnectors) {
            if (!connector.getName().equals(connectorName)) continue;
            return connector;
        }
        return null;
    }

    public String[] getNetworkConnectorURIs() {
        return this.networkConnectorURIs;
    }

    public void setNetworkConnectorURIs(String[] networkConnectorURIs) {
        this.networkConnectorURIs = networkConnectorURIs;
    }

    public TransportConnector getConnectorByName(String connectorName) {
        for (TransportConnector connector : this.transportConnectors) {
            if (!connector.getName().equals(connectorName)) continue;
            return connector;
        }
        return null;
    }

    public Map<String, String> getTransportConnectorURIsAsMap() {
        HashMap<String, String> answer = new HashMap<String, String>();
        for (TransportConnector connector : this.transportConnectors) {
            try {
                String scheme2;
                URI uri = connector.getConnectUri();
                if (uri == null || (scheme2 = uri.getScheme()) == null) continue;
                answer.put(scheme2.toLowerCase(Locale.ENGLISH), uri.toString());
            }
            catch (Exception e) {
                LOG.debug("Failed to read URI to build transportURIsAsMap", e);
            }
        }
        return answer;
    }

    public ProducerBrokerExchange getProducerBrokerExchange(ProducerInfo producerInfo) {
        ProducerBrokerExchange result = null;
        for (TransportConnector connector : this.transportConnectors) {
            for (TransportConnection tc : connector.getConnections()) {
                result = tc.getProducerBrokerExchangeIfExists(producerInfo);
                if (result == null) continue;
                return result;
            }
        }
        return result;
    }

    public String[] getTransportConnectorURIs() {
        return this.transportConnectorURIs;
    }

    public void setTransportConnectorURIs(String[] transportConnectorURIs) {
        this.transportConnectorURIs = transportConnectorURIs;
    }

    public JmsConnector[] getJmsBridgeConnectors() {
        return this.jmsBridgeConnectors;
    }

    public void setJmsBridgeConnectors(JmsConnector[] jmsConnectors) {
        this.jmsBridgeConnectors = jmsConnectors;
    }

    public Service[] getServices() {
        return this.services.toArray(new Service[0]);
    }

    public void setServices(Service[] services) {
        this.services.clear();
        if (services != null) {
            for (int i = 0; i < services.length; ++i) {
                this.services.add(services[i]);
            }
        }
    }

    public void addService(Service service2) {
        this.services.add(service2);
    }

    public void removeService(Service service2) {
        this.services.remove(service2);
    }

    public boolean isUseLoggingForShutdownErrors() {
        return this.useLoggingForShutdownErrors;
    }

    public void setUseLoggingForShutdownErrors(boolean useLoggingForShutdownErrors) {
        this.useLoggingForShutdownErrors = useLoggingForShutdownErrors;
    }

    public boolean isUseShutdownHook() {
        return this.useShutdownHook;
    }

    public void setUseShutdownHook(boolean useShutdownHook) {
        this.useShutdownHook = useShutdownHook;
    }

    public boolean isAdvisorySupport() {
        return this.advisorySupport;
    }

    public void setAdvisorySupport(boolean advisorySupport) {
        this.advisorySupport = advisorySupport;
    }

    public boolean isAnonymousProducerAdvisorySupport() {
        return this.anonymousProducerAdvisorySupport;
    }

    public void setAnonymousProducerAdvisorySupport(boolean anonymousProducerAdvisorySupport) {
        this.anonymousProducerAdvisorySupport = anonymousProducerAdvisorySupport;
    }

    public List<TransportConnector> getTransportConnectors() {
        return new ArrayList<TransportConnector>(this.transportConnectors);
    }

    public void setTransportConnectors(List<TransportConnector> transportConnectors) throws Exception {
        for (TransportConnector connector : transportConnectors) {
            this.addConnector(connector);
        }
    }

    public TransportConnector getTransportConnectorByName(String name) {
        for (TransportConnector transportConnector : this.transportConnectors) {
            if (!name.equals(transportConnector.getName())) continue;
            return transportConnector;
        }
        return null;
    }

    public TransportConnector getTransportConnectorByScheme(String scheme2) {
        for (TransportConnector transportConnector : this.transportConnectors) {
            if (!scheme2.equals(transportConnector.getUri().getScheme())) continue;
            return transportConnector;
        }
        return null;
    }

    public List<NetworkConnector> getNetworkConnectors() {
        return new ArrayList<NetworkConnector>(this.networkConnectors);
    }

    public List<ProxyConnector> getProxyConnectors() {
        return new ArrayList<ProxyConnector>(this.proxyConnectors);
    }

    public void setNetworkConnectors(List<?> networkConnectors) throws Exception {
        for (Object connector : networkConnectors) {
            this.addNetworkConnector((NetworkConnector)connector);
        }
    }

    public void setProxyConnectors(List<?> proxyConnectors) throws Exception {
        for (Object connector : proxyConnectors) {
            this.addProxyConnector((ProxyConnector)connector);
        }
    }

    public PolicyMap getDestinationPolicy() {
        return this.destinationPolicy;
    }

    public void setDestinationPolicy(PolicyMap policyMap) {
        this.destinationPolicy = policyMap;
    }

    public BrokerPlugin[] getPlugins() {
        return this.plugins;
    }

    public void setPlugins(BrokerPlugin[] plugins) {
        this.plugins = plugins;
    }

    public MessageAuthorizationPolicy getMessageAuthorizationPolicy() {
        return this.messageAuthorizationPolicy;
    }

    public void setMessageAuthorizationPolicy(MessageAuthorizationPolicy messageAuthorizationPolicy) {
        this.messageAuthorizationPolicy = messageAuthorizationPolicy;
    }

    public void deleteAllMessages() throws IOException {
        this.getPersistenceAdapter().deleteAllMessages();
    }

    public boolean isDeleteAllMessagesOnStartup() {
        return this.deleteAllMessagesOnStartup;
    }

    public void setDeleteAllMessagesOnStartup(boolean deletePersistentMessagesOnStartup) {
        this.deleteAllMessagesOnStartup = deletePersistentMessagesOnStartup;
    }

    public URI getVmConnectorURI() {
        if (this.vmConnectorURI == null) {
            try {
                this.vmConnectorURI = new URI("vm://" + this.getBrokerName());
            }
            catch (URISyntaxException e) {
                LOG.error("Badly formed URI from {}", (Object)this.getBrokerName(), (Object)e);
            }
        }
        return this.vmConnectorURI;
    }

    public void setVmConnectorURI(URI vmConnectorURI) {
        this.vmConnectorURI = vmConnectorURI;
    }

    public String getDefaultSocketURIString() {
        if (this.started.get()) {
            if (this.defaultSocketURIString == null) {
                for (TransportConnector tc : this.transportConnectors) {
                    String result = null;
                    try {
                        result = tc.getPublishableConnectString();
                    }
                    catch (Exception e) {
                        LOG.warn("Failed to get the ConnectURI for {}", (Object)tc, (Object)e);
                    }
                    if (result == null) continue;
                    if (tc.isUpdateClusterClients() || tc.isRebalanceClusterClients()) {
                        this.defaultSocketURIString = result;
                        break;
                    }
                    if (this.defaultSocketURIString != null) continue;
                    this.defaultSocketURIString = result;
                }
            }
            return this.defaultSocketURIString;
        }
        return null;
    }

    public boolean isShutdownOnMasterFailure() {
        return this.shutdownOnMasterFailure;
    }

    public void setShutdownOnMasterFailure(boolean shutdownOnMasterFailure) {
        this.shutdownOnMasterFailure = shutdownOnMasterFailure;
    }

    public boolean isKeepDurableSubsActive() {
        return this.keepDurableSubsActive;
    }

    public void setKeepDurableSubsActive(boolean keepDurableSubsActive) {
        this.keepDurableSubsActive = keepDurableSubsActive;
    }

    public boolean isUseVirtualTopics() {
        return this.useVirtualTopics;
    }

    public void setUseVirtualTopics(boolean useVirtualTopics) {
        this.useVirtualTopics = useVirtualTopics;
    }

    public DestinationInterceptor[] getDestinationInterceptors() {
        return this.destinationInterceptors;
    }

    public boolean isUseMirroredQueues() {
        return this.useMirroredQueues;
    }

    public void setUseMirroredQueues(boolean useMirroredQueues) {
        this.useMirroredQueues = useMirroredQueues;
    }

    public void setDestinationInterceptors(DestinationInterceptor[] destinationInterceptors) {
        this.destinationInterceptors = destinationInterceptors;
    }

    public ActiveMQDestination[] getDestinations() {
        return this.destinations;
    }

    public void setDestinations(ActiveMQDestination[] destinations) {
        this.destinations = destinations;
    }

    public synchronized PListStore getTempDataStore() {
        if (this.tempDataStore == null) {
            if (!this.isPersistent()) {
                return null;
            }
            try {
                PersistenceAdapter pa = this.getPersistenceAdapter();
                if (pa != null && pa instanceof PListStore) {
                    return (PListStore)((Object)pa);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                String clazz = "org.apache.activemq.store.kahadb.plist.PListStoreImpl";
                this.tempDataStore = (PListStore)this.getClass().getClassLoader().loadClass(clazz).newInstance();
                this.tempDataStore.setDirectory(this.getTmpDataDirectory());
                this.configureService(this.tempDataStore);
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException("Kahadb class PListStoreImpl not found. Add activemq-kahadb jar or set persistent to false on BrokerService.", e);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.tempDataStore;
    }

    public void setTempDataStore(PListStore tempDataStore) {
        this.tempDataStore = tempDataStore;
        if (tempDataStore != null) {
            if (this.tmpDataDirectory == null) {
                this.tmpDataDirectory = tempDataStore.getDirectory();
            } else if (tempDataStore.getDirectory() == null) {
                tempDataStore.setDirectory(this.tmpDataDirectory);
            }
        }
        this.configureService(tempDataStore);
    }

    public int getPersistenceThreadPriority() {
        return this.persistenceThreadPriority;
    }

    public void setPersistenceThreadPriority(int persistenceThreadPriority) {
        this.persistenceThreadPriority = persistenceThreadPriority;
    }

    public boolean isUseLocalHostBrokerName() {
        return this.useLocalHostBrokerName;
    }

    public void setUseLocalHostBrokerName(boolean useLocalHostBrokerName) {
        this.useLocalHostBrokerName = useLocalHostBrokerName;
        if (useLocalHostBrokerName && !this.started.get() && this.brokerName == null || this.brokerName == DEFAULT_BROKER_NAME) {
            this.brokerName = LOCAL_HOST_NAME;
        }
    }

    public Destination getDestination(ActiveMQDestination destination) throws Exception {
        return this.getBroker().addDestination(this.getAdminConnectionContext(), destination, false);
    }

    public void removeDestination(ActiveMQDestination destination) throws Exception {
        this.getBroker().removeDestination(this.getAdminConnectionContext(), destination, 0L);
    }

    public int getProducerSystemUsagePortion() {
        return this.producerSystemUsagePortion;
    }

    public void setProducerSystemUsagePortion(int producerSystemUsagePortion) {
        this.producerSystemUsagePortion = producerSystemUsagePortion;
    }

    public int getConsumerSystemUsagePortion() {
        return this.consumerSystemUsagePortion;
    }

    public void setConsumerSystemUsagePortion(int consumerSystemUsagePortion) {
        this.consumerSystemUsagePortion = consumerSystemUsagePortion;
    }

    public boolean isSplitSystemUsageForProducersConsumers() {
        return this.splitSystemUsageForProducersConsumers;
    }

    public void setSplitSystemUsageForProducersConsumers(boolean splitSystemUsageForProducersConsumers) {
        this.splitSystemUsageForProducersConsumers = splitSystemUsageForProducersConsumers;
    }

    public boolean isMonitorConnectionSplits() {
        return this.monitorConnectionSplits;
    }

    public void setMonitorConnectionSplits(boolean monitorConnectionSplits) {
        this.monitorConnectionSplits = monitorConnectionSplits;
    }

    public int getTaskRunnerPriority() {
        return this.taskRunnerPriority;
    }

    public void setTaskRunnerPriority(int taskRunnerPriority) {
        this.taskRunnerPriority = taskRunnerPriority;
    }

    public boolean isDedicatedTaskRunner() {
        return this.dedicatedTaskRunner;
    }

    public void setDedicatedTaskRunner(boolean dedicatedTaskRunner) {
        this.dedicatedTaskRunner = dedicatedTaskRunner;
    }

    public boolean isCacheTempDestinations() {
        return this.cacheTempDestinations;
    }

    public void setCacheTempDestinations(boolean cacheTempDestinations) {
        this.cacheTempDestinations = cacheTempDestinations;
    }

    public int getTimeBeforePurgeTempDestinations() {
        return this.timeBeforePurgeTempDestinations;
    }

    public void setTimeBeforePurgeTempDestinations(int timeBeforePurgeTempDestinations) {
        this.timeBeforePurgeTempDestinations = timeBeforePurgeTempDestinations;
    }

    public boolean isUseTempMirroredQueues() {
        return this.useTempMirroredQueues;
    }

    public void setUseTempMirroredQueues(boolean useTempMirroredQueues) {
        this.useTempMirroredQueues = useTempMirroredQueues;
    }

    public synchronized JobSchedulerStore getJobSchedulerStore() {
        if (!this.isSchedulerSupport()) {
            return null;
        }
        if (this.jobSchedulerStore == null) {
            PersistenceAdapter pa;
            if (!this.isPersistent()) {
                this.jobSchedulerStore = new InMemoryJobSchedulerStore();
                this.configureService(this.jobSchedulerStore);
                return this.jobSchedulerStore;
            }
            try {
                pa = this.getPersistenceAdapter();
                if (pa != null) {
                    this.jobSchedulerStore = pa.createJobSchedulerStore();
                    this.jobSchedulerStore.setDirectory(this.getSchedulerDirectoryFile());
                    this.configureService(this.jobSchedulerStore);
                    return this.jobSchedulerStore;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            catch (UnsupportedOperationException e) {
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                pa = this.getPersistenceAdapter();
                if (pa != null && pa instanceof JobSchedulerStore) {
                    this.jobSchedulerStore = (JobSchedulerStore)((Object)pa);
                    this.configureService(this.jobSchedulerStore);
                    return this.jobSchedulerStore;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                String clazz = "org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter";
                PersistenceAdapter adaptor = (PersistenceAdapter)this.getClass().getClassLoader().loadClass(clazz).newInstance();
                this.jobSchedulerStore = adaptor.createJobSchedulerStore();
                this.jobSchedulerStore.setDirectory(this.getSchedulerDirectoryFile());
                this.configureService(this.jobSchedulerStore);
                LOG.info("JobScheduler using directory: {}", (Object)this.getSchedulerDirectoryFile());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.jobSchedulerStore;
    }

    public void setJobSchedulerStore(JobSchedulerStore jobSchedulerStore) {
        this.jobSchedulerStore = jobSchedulerStore;
        this.configureService(jobSchedulerStore);
    }

    protected void processHelperProperties() throws Exception {
        String uri;
        int i;
        if (this.transportConnectorURIs != null) {
            for (i = 0; i < this.transportConnectorURIs.length; ++i) {
                uri = this.transportConnectorURIs[i];
                this.addConnector(uri);
            }
        }
        if (this.networkConnectorURIs != null) {
            for (i = 0; i < this.networkConnectorURIs.length; ++i) {
                uri = this.networkConnectorURIs[i];
                this.addNetworkConnector(uri);
            }
        }
        if (this.jmsBridgeConnectors != null) {
            for (i = 0; i < this.jmsBridgeConnectors.length; ++i) {
                this.addJmsConnector(this.jmsBridgeConnectors[i]);
            }
        }
    }

    protected void checkStoreUsageLimits() throws Exception {
        SystemUsage usage = this.getSystemUsage();
        if (this.getPersistenceAdapter() != null) {
            PersistenceAdapter adapter = this.getPersistenceAdapter();
            this.checkUsageLimit(adapter.getDirectory(), usage.getStoreUsage(), usage.getStoreUsage().getPercentLimit());
            long maxJournalFileSize = 0L;
            long storeLimit = usage.getStoreUsage().getLimit();
            if (adapter instanceof JournaledStore) {
                maxJournalFileSize = ((JournaledStore)((Object)adapter)).getJournalMaxFileLength();
            }
            if (storeLimit > 0L && storeLimit < maxJournalFileSize) {
                LOG.error("Store limit is " + storeLimit / 0x100000L + " mb, whilst the max journal file size for the store is: " + maxJournalFileSize / 0x100000L + " mb, the store will not accept any data when used.");
            }
        }
    }

    protected void checkTmpStoreUsageLimits() throws Exception {
        SystemUsage usage = this.getSystemUsage();
        File tmpDir = this.getTmpDataDirectory();
        if (tmpDir != null) {
            this.checkUsageLimit(tmpDir, usage.getTempUsage(), usage.getTempUsage().getPercentLimit());
            if (this.isPersistent()) {
                PListStore store = usage.getTempUsage().getStore();
                long maxJournalFileSize = store != null && store instanceof JournaledStore ? (long)((JournaledStore)((Object)store)).getJournalMaxFileLength() : 0x2000000L;
                long storeLimit = usage.getTempUsage().getLimit();
                if (storeLimit > 0L && storeLimit < maxJournalFileSize) {
                    LOG.error("Temporary Store limit is " + storeLimit / 0x100000L + " mb, whilst the max journal file size for the temporary store is: " + maxJournalFileSize / 0x100000L + " mb, the temp store will not accept any data when used.");
                }
            }
        }
    }

    protected void checkUsageLimit(File dir, PercentLimitUsage<?> storeUsage, int percentLimit) throws ConfigurationException {
        if (dir != null) {
            dir = StoreUtil.findParentDirectory(dir);
            String storeName = storeUsage instanceof StoreUsage ? "Store" : "Temporary Store";
            long storeLimit = storeUsage.getLimit();
            long storeCurrent = storeUsage.getUsage();
            long totalSpace = storeUsage.getTotal() > 0L ? storeUsage.getTotal() : dir.getTotalSpace();
            long totalUsableSpace = (storeUsage.getTotal() > 0L ? storeUsage.getTotal() : dir.getUsableSpace()) + storeCurrent;
            if (totalUsableSpace < 0L || totalSpace < 0L) {
                String message = "File system space reported by: " + dir + " was negative, possibly a huge file system, set a sane usage.total to provide some guidance";
                LOG.error(message);
                throw new ConfigurationException(message);
            }
            long bytePercentLimit = totalSpace * (long)percentLimit / 100L;
            int oneMeg = 0x100000;
            if (this.diskUsageCheckRegrowThreshold > -1 && percentLimit > 0 && storeUsage.getTotal() == 0L && storeLimit < bytePercentLimit && storeLimit < totalUsableSpace) {
                long newLimit;
                long l = newLimit = bytePercentLimit > totalUsableSpace ? totalUsableSpace : bytePercentLimit;
                if (newLimit - storeLimit >= (long)this.diskUsageCheckRegrowThreshold) {
                    LOG.info("Usable disk space has been increased, attempting to regrow " + storeName + " limit to " + percentLimit + "% of the partition size.");
                    storeUsage.setLimit(newLimit);
                    LOG.info(storeName + " limit has been increased to " + newLimit * 100L / totalSpace + "% (" + newLimit / (long)oneMeg + " mb) of the partition size.");
                }
            } else if (storeLimit > totalUsableSpace) {
                String message = storeName + " limit is " + storeLimit / (long)oneMeg + " mb (current store usage is " + storeCurrent / (long)oneMeg + " mb). The data directory: " + dir.getAbsolutePath() + " only has " + totalUsableSpace / (long)oneMeg + " mb of usable space.";
                if (!this.isAdjustUsageLimits()) {
                    LOG.error(message);
                    throw new ConfigurationException(message);
                }
                if (percentLimit > 0) {
                    LOG.warn(storeName + " limit has been set to " + percentLimit + "% (" + bytePercentLimit / (long)oneMeg + " mb) of the partition size but there is not enough usable space. The current store limit (which may have been adjusted by a previous usage limit check) is set to (" + storeLimit / (long)oneMeg + " mb) but only " + totalUsableSpace * 100L / totalSpace + "% (" + totalUsableSpace / (long)oneMeg + " mb) is available - resetting limit");
                } else {
                    LOG.warn(message + " - resetting to maximum available disk space: " + totalUsableSpace / (long)oneMeg + " mb");
                }
                storeUsage.setLimit(totalUsableSpace);
            }
        }
    }

    protected void scheduleDiskUsageLimitsCheck() throws IOException {
        if (this.schedulePeriodForDiskUsageCheck > 0 && (this.getPersistenceAdapter() != null || this.getTmpDataDirectory() != null)) {
            Runnable diskLimitCheckTask = new Runnable(){

                @Override
                public void run() {
                    try {
                        BrokerService.this.checkStoreUsageLimits();
                    }
                    catch (Exception e) {
                        LOG.error("Failed to check persistent disk usage limits", e);
                    }
                    try {
                        BrokerService.this.checkTmpStoreUsageLimits();
                    }
                    catch (Exception e) {
                        LOG.error("Failed to check temporary store usage limits", e);
                    }
                }
            };
            this.scheduler.executePeriodically(diskLimitCheckTask, this.schedulePeriodForDiskUsageCheck);
        }
    }

    protected void checkMemorySystemUsageLimits() throws Exception {
        long jvmLimit;
        SystemUsage usage = this.getSystemUsage();
        long memLimit = usage.getMemoryUsage().getLimit();
        if (memLimit > (jvmLimit = Runtime.getRuntime().maxMemory())) {
            String message = "Memory Usage for the Broker (" + memLimit / 0x100000L + "mb) is more than the maximum available for the JVM: " + jvmLimit / 0x100000L;
            if (this.adjustUsageLimits) {
                usage.getMemoryUsage().setPercentOfJvmHeap(70);
                LOG.warn(message + " mb - resetting to 70% of maximum available: " + usage.getMemoryUsage().getLimit() / 0x100000L + " mb");
            } else {
                LOG.error(message);
                throw new ConfigurationException(message);
            }
        }
    }

    protected void checkStoreSystemUsageLimits() throws Exception {
        JobSchedulerStore scheduler;
        File schedulerDir;
        SystemUsage usage = this.getSystemUsage();
        this.checkStoreUsageLimits();
        this.checkTmpStoreUsageLimits();
        this.scheduleDiskUsageLimitsCheck();
        if (this.getJobSchedulerStore() != null && (schedulerDir = (scheduler = this.getJobSchedulerStore()).getDirectory()) != null) {
            long dirFreeSpace;
            String schedulerDirPath = schedulerDir.getAbsolutePath();
            if (!schedulerDir.isAbsolute()) {
                schedulerDir = new File(schedulerDirPath);
            }
            while (schedulerDir != null && !schedulerDir.isDirectory()) {
                schedulerDir = schedulerDir.getParentFile();
            }
            long schedulerLimit = usage.getJobSchedulerUsage().getLimit();
            if (schedulerLimit > (dirFreeSpace = schedulerDir.getUsableSpace())) {
                LOG.warn("Job Scheduler Store limit is " + schedulerLimit / 0x100000L + " mb, whilst the data directory: " + schedulerDir.getAbsolutePath() + " only has " + dirFreeSpace / 0x100000L + " mb of usable space - resetting to " + dirFreeSpace / 0x100000L + " mb.");
                usage.getJobSchedulerUsage().setLimit(dirFreeSpace);
            }
        }
    }

    public void stopAllConnectors(ServiceStopper stopper) {
        for (NetworkConnector networkConnector : this.getNetworkConnectors()) {
            this.unregisterNetworkConnectorMBean(networkConnector);
            stopper.stop(networkConnector);
        }
        for (ProxyConnector proxyConnector : this.getProxyConnectors()) {
            stopper.stop(proxyConnector);
        }
        for (JmsConnector jmsConnector : this.jmsConnectors) {
            stopper.stop(jmsConnector);
        }
        for (TransportConnector transportConnector : this.getTransportConnectors()) {
            try {
                this.unregisterConnectorMBean(transportConnector);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            stopper.stop(transportConnector);
        }
    }

    protected TransportConnector registerConnectorMBean(TransportConnector connector) throws IOException {
        try {
            ObjectName objectName = this.createConnectorObjectName(connector);
            connector = connector.asManagedConnector(this.getManagementContext(), objectName);
            ConnectorView view = new ConnectorView(connector);
            AnnotatedMBean.registerMBean(this.getManagementContext(), view, objectName);
            return connector;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Transport Connector could not be registered in JMX: " + e, e);
        }
    }

    protected void unregisterConnectorMBean(TransportConnector connector) throws IOException {
        if (this.isUseJmx()) {
            try {
                ObjectName objectName = this.createConnectorObjectName(connector);
                this.getManagementContext().unregisterMBean(objectName);
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create("Transport Connector could not be unregistered in JMX: " + e.getMessage(), e);
            }
        }
    }

    protected PersistenceAdapter registerPersistenceAdapterMBean(PersistenceAdapter adaptor) throws IOException {
        return adaptor;
    }

    protected void unregisterPersistenceAdapterMBean(PersistenceAdapter adaptor) throws IOException {
        if (this.isUseJmx()) {
            // empty if block
        }
    }

    private ObjectName createConnectorObjectName(TransportConnector connector) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createConnectorName(this.getBrokerObjectName(), "clientConnectors", connector.getName());
    }

    public void registerNetworkConnectorMBean(NetworkConnector connector) throws IOException {
        NetworkConnectorView view = new NetworkConnectorView(connector);
        try {
            ObjectName objectName = this.createNetworkConnectorObjectName(connector);
            connector.setObjectName(objectName);
            AnnotatedMBean.registerMBean(this.getManagementContext(), view, objectName);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Network Connector could not be registered in JMX: " + e.getMessage(), e);
        }
    }

    public ObjectName createNetworkConnectorObjectName(NetworkConnector connector) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createNetworkConnectorName(this.getBrokerObjectName(), "networkConnectors", connector.getName());
    }

    public ObjectName createDuplexNetworkConnectorObjectName(String transport) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createNetworkConnectorName(this.getBrokerObjectName(), "duplexNetworkConnectors", transport);
    }

    protected void unregisterNetworkConnectorMBean(NetworkConnector connector) {
        if (this.isUseJmx()) {
            try {
                ObjectName objectName = this.createNetworkConnectorObjectName(connector);
                this.getManagementContext().unregisterMBean(objectName);
            }
            catch (Exception e) {
                LOG.warn("Network Connector could not be unregistered from JMX due " + e.getMessage() + ". This exception is ignored.", e);
            }
        }
    }

    protected void registerProxyConnectorMBean(ProxyConnector connector) throws IOException {
        ProxyConnectorView view = new ProxyConnectorView(connector);
        try {
            ObjectName objectName = BrokerMBeanSupport.createNetworkConnectorName(this.getBrokerObjectName(), "proxyConnectors", connector.getName());
            AnnotatedMBean.registerMBean(this.getManagementContext(), view, objectName);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Broker could not be registered in JMX: " + e.getMessage(), e);
        }
    }

    protected void registerJmsConnectorMBean(JmsConnector connector) throws IOException {
        JmsConnectorView view = new JmsConnectorView(connector);
        try {
            ObjectName objectName = BrokerMBeanSupport.createNetworkConnectorName(this.getBrokerObjectName(), "jmsConnectors", connector.getName());
            AnnotatedMBean.registerMBean(this.getManagementContext(), view, objectName);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Broker could not be registered in JMX: " + e.getMessage(), e);
        }
    }

    protected Broker createBroker() throws Exception {
        this.regionBroker = this.createRegionBroker();
        Broker broker = this.addInterceptors(this.regionBroker);
        broker = new MutableBrokerFilter(broker){
            Broker old;

            @Override
            public void stop() throws Exception {
                this.old = this.next.getAndSet(new ErrorBroker("Broker has been stopped: " + this){

                    @Override
                    public void stop() throws Exception {
                    }
                });
                this.old.stop();
            }

            @Override
            public void start() throws Exception {
                if (BrokerService.this.forceStart && this.old != null) {
                    this.next.set(this.old);
                }
                this.getNext().start();
            }
        };
        return broker;
    }

    protected Broker createRegionBroker() throws Exception {
        if (this.destinationInterceptors == null) {
            this.destinationInterceptors = this.createDefaultDestinationInterceptor();
        }
        this.configureServices(this.destinationInterceptors);
        CompositeDestinationInterceptor destinationInterceptor = new CompositeDestinationInterceptor(this.destinationInterceptors);
        if (this.destinationFactory == null) {
            this.destinationFactory = new DestinationFactoryImpl(this, this.getTaskRunnerFactory(), this.getPersistenceAdapter());
        }
        return this.createRegionBroker(destinationInterceptor);
    }

    protected Broker createRegionBroker(DestinationInterceptor destinationInterceptor) throws IOException {
        RegionBroker regionBroker;
        if (this.isUseJmx()) {
            try {
                regionBroker = new ManagedRegionBroker(this, this.getManagementContext(), this.getBrokerObjectName(), this.getTaskRunnerFactory(), this.getConsumerSystemUsage(), this.destinationFactory, destinationInterceptor, this.getScheduler(), this.getExecutor());
            }
            catch (MalformedObjectNameException me) {
                LOG.warn("Cannot create ManagedRegionBroker due " + me.getMessage(), me);
                throw new IOException(me);
            }
        } else {
            regionBroker = new RegionBroker(this, this.getTaskRunnerFactory(), this.getConsumerSystemUsage(), this.destinationFactory, destinationInterceptor, this.getScheduler(), this.getExecutor());
        }
        this.destinationFactory.setRegionBroker(regionBroker);
        regionBroker.setKeepDurableSubsActive(this.keepDurableSubsActive);
        regionBroker.setBrokerName(this.getBrokerName());
        regionBroker.getDestinationStatistics().setEnabled(this.enableStatistics);
        regionBroker.setAllowTempAutoCreationOnSend(this.isAllowTempAutoCreationOnSend());
        if (this.brokerId != null) {
            regionBroker.setBrokerId(this.brokerId);
        }
        return regionBroker;
    }

    protected DestinationInterceptor[] createDefaultDestinationInterceptor() {
        DestinationInterceptor interceptor;
        ArrayList<DestinationInterceptor> answer = new ArrayList<DestinationInterceptor>();
        if (this.isUseVirtualTopics()) {
            interceptor = new VirtualDestinationInterceptor();
            VirtualTopic virtualTopic = new VirtualTopic();
            virtualTopic.setName("VirtualTopic.>");
            VirtualDestination[] virtualDestinations = new VirtualDestination[]{virtualTopic};
            ((VirtualDestinationInterceptor)interceptor).setVirtualDestinations(virtualDestinations);
            answer.add(interceptor);
        }
        if (this.isUseMirroredQueues()) {
            interceptor = new MirroredQueue();
            answer.add(interceptor);
        }
        DestinationInterceptor[] array = new DestinationInterceptor[answer.size()];
        answer.toArray(array);
        return array;
    }

    protected Broker addInterceptors(Broker broker) throws Exception {
        if (this.isSchedulerSupport()) {
            SchedulerBroker sb = new SchedulerBroker(this, broker, this.getJobSchedulerStore());
            if (this.isUseJmx()) {
                JobSchedulerView view = new JobSchedulerView(sb.getJobScheduler());
                try {
                    ObjectName objectName = BrokerMBeanSupport.createJobSchedulerServiceName(this.getBrokerObjectName());
                    AnnotatedMBean.registerMBean(this.getManagementContext(), view, objectName);
                    this.adminView.setJMSJobScheduler(objectName);
                }
                catch (Throwable e) {
                    throw IOExceptionSupport.create("JobScheduler could not be registered in JMX: " + e.getMessage(), e);
                }
            }
            broker = sb;
        }
        if (this.isUseJmx()) {
            HealthView statusView = new HealthView((ManagedRegionBroker)this.getRegionBroker());
            try {
                ObjectName objectName = BrokerMBeanSupport.createHealthServiceName(this.getBrokerObjectName());
                AnnotatedMBean.registerMBean(this.getManagementContext(), statusView, objectName);
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create("Status MBean could not be registered in JMX: " + e.getMessage(), e);
            }
        }
        if (this.isAdvisorySupport()) {
            broker = new AdvisoryBroker(broker);
        }
        broker = new CompositeDestinationBroker(broker);
        broker = new TransactionBroker(broker, this.getPersistenceAdapter().createTransactionStore());
        if (this.isPopulateJMSXUserID()) {
            UserIDBroker userIDBroker = new UserIDBroker(broker);
            userIDBroker.setUseAuthenticatePrincipal(this.isUseAuthenticatedPrincipalForJMSXUserID());
            broker = userIDBroker;
        }
        if (this.isMonitorConnectionSplits()) {
            broker = new ConnectionSplitBroker(broker);
        }
        if (this.plugins != null) {
            for (int i = 0; i < this.plugins.length; ++i) {
                BrokerPlugin plugin = this.plugins[i];
                broker = plugin.installPlugin(broker);
            }
        }
        return broker;
    }

    protected PersistenceAdapter createPersistenceAdapter() throws IOException {
        if (this.isPersistent()) {
            PersistenceAdapterFactory fac = this.getPersistenceFactory();
            if (fac != null) {
                return fac.createPersistenceAdapter();
            }
            try {
                String clazz = "org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter";
                PersistenceAdapter adaptor = (PersistenceAdapter)this.getClass().getClassLoader().loadClass(clazz).newInstance();
                File dir = new File(this.getBrokerDataDirectory(), "KahaDB");
                adaptor.setDirectory(dir);
                return adaptor;
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create(e);
            }
        }
        return new MemoryPersistenceAdapter();
    }

    protected ObjectName createBrokerObjectName() throws MalformedObjectNameException {
        return BrokerMBeanSupport.createBrokerObjectName(this.getManagementContext().getJmxDomainName(), this.getBrokerName());
    }

    protected TransportConnector createTransportConnector(URI brokerURI) throws Exception {
        TransportServer transport = TransportFactorySupport.bind(this, brokerURI);
        return new TransportConnector(transport);
    }

    protected Object getPort(Map<?, ?> options) {
        Object port = options.get("port");
        if (port == null) {
            port = DEFAULT_PORT;
            LOG.warn("No port specified so defaulting to: {}", port);
        }
        return port;
    }

    protected void addShutdownHook() {
        if (this.useShutdownHook) {
            this.shutdownHook = new Thread("ActiveMQ ShutdownHook"){

                @Override
                public void run() {
                    BrokerService.this.containerShutdown();
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
    }

    protected void removeShutdownHook() {
        if (this.shutdownHook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            }
            catch (Exception e) {
                LOG.debug("Caught exception, must be shutting down. This exception is ignored.", e);
            }
        }
    }

    public void setShutdownHooks(List<Runnable> hooks) throws Exception {
        for (Runnable hook : hooks) {
            this.addShutdownHook(hook);
        }
    }

    protected void containerShutdown() {
        try {
            this.stop();
        }
        catch (IOException e) {
            Throwable linkedException = e.getCause();
            if (linkedException != null) {
                this.logError("Failed to shut down: " + e + ". Reason: " + linkedException, linkedException);
            } else {
                this.logError("Failed to shut down: " + e, e);
            }
            if (!this.useLoggingForShutdownErrors) {
                e.printStackTrace(System.err);
            }
        }
        catch (Exception e) {
            this.logError("Failed to shut down: " + e, e);
        }
    }

    protected void logError(String message, Throwable e) {
        if (this.useLoggingForShutdownErrors) {
            LOG.error("Failed to shut down: " + e);
        } else {
            System.err.println("Failed to shut down: " + e);
        }
    }

    protected void startDestinations() throws Exception {
        if (this.destinations != null) {
            ConnectionContext adminConnectionContext = this.getAdminConnectionContext();
            for (int i = 0; i < this.destinations.length; ++i) {
                ActiveMQDestination destination = this.destinations[i];
                this.getBroker().addDestination(adminConnectionContext, destination, true);
            }
        }
        if (this.isUseVirtualTopics()) {
            this.startVirtualConsumerDestinations();
        }
    }

    public ConnectionContext getAdminConnectionContext() throws Exception {
        return BrokerSupport.getConnectionContext(this.getBroker());
    }

    protected void startManagementContext() throws Exception {
        this.getManagementContext().setBrokerName(this.brokerName);
        this.getManagementContext().start();
        this.adminView = new BrokerView(this, null);
        ObjectName objectName = this.getBrokerObjectName();
        AnnotatedMBean.registerMBean(this.getManagementContext(), this.adminView, objectName);
    }

    public void startAllConnectors() throws Exception {
        Set<ActiveMQDestination> durableDestinations = this.getBroker().getDurableDestinations();
        ArrayList<TransportConnector> al = new ArrayList<TransportConnector>();
        for (TransportConnector connector : this.getTransportConnectors()) {
            al.add(this.startTransportConnector(connector));
        }
        if (al.size() > 0) {
            this.transportConnectors.clear();
            this.setTransportConnectors(al);
        }
        this.slave = false;
        if (!this.stopped.get()) {
            ThreadPoolExecutor networkConnectorStartExecutor = null;
            if (this.isNetworkConnectorStartAsync()) {
                networkConnectorStartExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory(){
                    int count = 0;

                    @Override
                    public Thread newThread(Runnable runnable2) {
                        Thread thread2 = new Thread(runnable2, "NetworkConnector Start Thread-" + this.count++);
                        thread2.setDaemon(true);
                        return thread2;
                    }
                });
            }
            for (NetworkConnector networkConnector : this.getNetworkConnectors()) {
                networkConnector.setLocalUri(this.getVmConnectorURI());
                this.startNetworkConnector(networkConnector, durableDestinations, networkConnectorStartExecutor);
            }
            if (networkConnectorStartExecutor != null) {
                ThreadPoolUtils.shutdown(networkConnectorStartExecutor);
            }
            for (ProxyConnector proxyConnector : this.getProxyConnectors()) {
                proxyConnector.start();
            }
            for (JmsConnector jmsConnector : this.jmsConnectors) {
                jmsConnector.start();
            }
            for (Service service2 : this.services) {
                this.configureService(service2);
                service2.start();
            }
        }
    }

    public void startNetworkConnector(NetworkConnector connector, ThreadPoolExecutor networkConnectorStartExecutor) throws Exception {
        this.startNetworkConnector(connector, this.getBroker().getDurableDestinations(), networkConnectorStartExecutor);
    }

    public void startNetworkConnector(final NetworkConnector connector, Set<ActiveMQDestination> durableDestinations, ThreadPoolExecutor networkConnectorStartExecutor) throws Exception {
        String defaultSocketURI;
        connector.setBrokerName(this.getBrokerName());
        if (connector.getDurableDestinations() == null) {
            connector.setDurableDestinations(durableDestinations);
        }
        if ((defaultSocketURI = this.getDefaultSocketURIString()) != null) {
            connector.setBrokerURL(defaultSocketURI);
        }
        if (this.isUseJmx()) {
            ObjectName networkMbean = this.createNetworkConnectorObjectName(connector);
            try {
                this.getManagementContext().getObjectInstance(networkMbean);
            }
            catch (InstanceNotFoundException e) {
                LOG.debug("Network connector MBean {} not found, registering", (Object)networkMbean);
                this.registerNetworkConnectorMBean(connector);
            }
        }
        if (networkConnectorStartExecutor != null) {
            networkConnectorStartExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        LOG.info("Async start of {}", (Object)connector);
                        connector.start();
                    }
                    catch (Exception e) {
                        LOG.error("Async start of network connector: {} failed", (Object)connector, (Object)e);
                    }
                }
            });
        } else {
            connector.start();
        }
    }

    public TransportConnector startTransportConnector(TransportConnector connector) throws Exception {
        connector.setBrokerService(this);
        connector.setTaskRunnerFactory(this.getTaskRunnerFactory());
        MessageAuthorizationPolicy policy = this.getMessageAuthorizationPolicy();
        if (policy != null) {
            connector.setMessageAuthorizationPolicy(policy);
        }
        if (this.isUseJmx()) {
            connector = this.registerConnectorMBean(connector);
        }
        connector.getStatistics().setEnabled(this.enableStatistics);
        connector.start();
        return connector;
    }

    protected void configureServices(Object[] services) {
        for (Object service2 : services) {
            this.configureService(service2);
        }
    }

    protected void configureService(Object service2) {
        if (service2 instanceof BrokerServiceAware) {
            BrokerServiceAware serviceAware = (BrokerServiceAware)service2;
            serviceAware.setBrokerService(this);
        }
    }

    public void handleIOException(IOException exception) {
        if (this.ioExceptionHandler != null) {
            this.ioExceptionHandler.handle(exception);
        } else {
            LOG.info("No IOExceptionHandler registered, ignoring IO exception", exception);
        }
    }

    protected void startVirtualConsumerDestinations() throws Exception {
        this.checkStartException();
        ConnectionContext adminConnectionContext = this.getAdminConnectionContext();
        Set<ActiveMQDestination> destinations = this.destinationFactory.getDestinations();
        DestinationFilter filter2 = this.getVirtualTopicConsumerDestinationFilter();
        if (!destinations.isEmpty()) {
            for (ActiveMQDestination destination : destinations) {
                if (!filter2.matches(destination)) continue;
                this.broker.addDestination(adminConnectionContext, destination, false);
            }
        }
    }

    private DestinationFilter getVirtualTopicConsumerDestinationFilter() {
        if (this.virtualConsumerDestinationFilter == null) {
            HashSet<ActiveMQQueue> consumerDestinations = new HashSet<ActiveMQQueue>();
            if (this.destinationInterceptors != null) {
                for (DestinationInterceptor interceptor : this.destinationInterceptors) {
                    if (!(interceptor instanceof VirtualDestinationInterceptor)) continue;
                    VirtualDestinationInterceptor virtualDestinationInterceptor = (VirtualDestinationInterceptor)interceptor;
                    for (VirtualDestination virtualDestination : virtualDestinationInterceptor.getVirtualDestinations()) {
                        if (virtualDestination instanceof VirtualTopic) {
                            consumerDestinations.add(new ActiveMQQueue(((VirtualTopic)virtualDestination).getPrefix() + ">"));
                        }
                        if (!this.isUseVirtualDestSubs()) continue;
                        try {
                            this.broker.virtualDestinationAdded(this.getAdminConnectionContext(), virtualDestination);
                            LOG.debug("Adding virtual destination: {}", (Object)virtualDestination);
                        }
                        catch (Exception e) {
                            LOG.warn("Could not fire virtual destination consumer advisory", e);
                        }
                    }
                }
            }
            ActiveMQQueue filter2 = new ActiveMQQueue();
            filter2.setCompositeDestinations(consumerDestinations.toArray(new ActiveMQDestination[0]));
            this.virtualConsumerDestinationFilter = DestinationFilter.parseFilter(filter2);
        }
        return this.virtualConsumerDestinationFilter;
    }

    protected synchronized ThreadPoolExecutor getExecutor() {
        if (this.executor == null) {
            this.executor = new ThreadPoolExecutor(1, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){
                private long i = 0L;

                @Override
                public Thread newThread(Runnable runnable2) {
                    ++this.i;
                    Thread thread2 = new Thread(runnable2, "ActiveMQ BrokerService.worker." + this.i);
                    thread2.setDaemon(true);
                    thread2.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

                        @Override
                        public void uncaughtException(Thread t2, Throwable e) {
                            LOG.error("Error in thread '{}'", (Object)t2.getName(), (Object)e);
                        }
                    });
                    return thread2;
                }
            }, new RejectedExecutionHandler(){

                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor2) {
                    try {
                        executor2.getQueue().offer(r, 60L, TimeUnit.SECONDS);
                    }
                    catch (InterruptedException e) {
                        throw new RejectedExecutionException("Interrupted waiting for BrokerService.worker");
                    }
                    throw new RejectedExecutionException("Timed Out while attempting to enqueue Task.");
                }
            });
        }
        return this.executor;
    }

    public synchronized Scheduler getScheduler() {
        if (this.scheduler == null) {
            this.scheduler = new Scheduler("ActiveMQ Broker[" + this.getBrokerName() + "] Scheduler");
            try {
                this.scheduler.start();
            }
            catch (Exception e) {
                LOG.error("Failed to start Scheduler", e);
            }
        }
        return this.scheduler;
    }

    public Broker getRegionBroker() {
        return this.regionBroker;
    }

    public void setRegionBroker(Broker regionBroker) {
        this.regionBroker = regionBroker;
    }

    public final void removePreShutdownHook(Runnable hook) {
        this.preShutdownHooks.remove(hook);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addShutdownHook(Runnable hook) {
        List<Runnable> list = this.shutdownHooks;
        synchronized (list) {
            this.shutdownHooks.add(hook);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeShutdownHook(Runnable hook) {
        List<Runnable> list = this.shutdownHooks;
        synchronized (list) {
            this.shutdownHooks.remove(hook);
        }
    }

    public boolean isSystemExitOnShutdown() {
        return this.systemExitOnShutdown;
    }

    public void setSystemExitOnShutdown(boolean systemExitOnShutdown) {
        this.systemExitOnShutdown = systemExitOnShutdown;
    }

    public int getSystemExitOnShutdownExitCode() {
        return this.systemExitOnShutdownExitCode;
    }

    public void setSystemExitOnShutdownExitCode(int systemExitOnShutdownExitCode) {
        this.systemExitOnShutdownExitCode = systemExitOnShutdownExitCode;
    }

    public SslContext getSslContext() {
        return this.sslContext;
    }

    public void setSslContext(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    public boolean isShutdownOnSlaveFailure() {
        return this.shutdownOnSlaveFailure;
    }

    public void setShutdownOnSlaveFailure(boolean shutdownOnSlaveFailure) {
        this.shutdownOnSlaveFailure = shutdownOnSlaveFailure;
    }

    public boolean isWaitForSlave() {
        return this.waitForSlave;
    }

    public void setWaitForSlave(boolean waitForSlave) {
        this.waitForSlave = waitForSlave;
    }

    public long getWaitForSlaveTimeout() {
        return this.waitForSlaveTimeout;
    }

    public void setWaitForSlaveTimeout(long waitForSlaveTimeout) {
        this.waitForSlaveTimeout = waitForSlaveTimeout;
    }

    public boolean isPassiveSlave() {
        return this.passiveSlave;
    }

    public void setPassiveSlave(boolean passiveSlave) {
        this.passiveSlave = passiveSlave;
    }

    public void setIoExceptionHandler(IOExceptionHandler ioExceptionHandler) {
        this.configureService(ioExceptionHandler);
        this.ioExceptionHandler = ioExceptionHandler;
    }

    public IOExceptionHandler getIoExceptionHandler() {
        return this.ioExceptionHandler;
    }

    public boolean isSchedulerSupport() {
        return this.schedulerSupport;
    }

    public void setSchedulerSupport(boolean schedulerSupport) {
        this.schedulerSupport = schedulerSupport;
    }

    public File getSchedulerDirectoryFile() {
        if (this.schedulerDirectoryFile == null) {
            this.schedulerDirectoryFile = new File(this.getBrokerDataDirectory(), "scheduler");
        }
        return this.schedulerDirectoryFile;
    }

    public void setSchedulerDirectoryFile(File schedulerDirectory) {
        this.schedulerDirectoryFile = schedulerDirectory;
    }

    public void setSchedulerDirectory(String schedulerDirectory) {
        this.setSchedulerDirectoryFile(new File(schedulerDirectory));
    }

    public int getSchedulePeriodForDestinationPurge() {
        return this.schedulePeriodForDestinationPurge;
    }

    public void setSchedulePeriodForDestinationPurge(int schedulePeriodForDestinationPurge) {
        this.schedulePeriodForDestinationPurge = schedulePeriodForDestinationPurge;
    }

    public void setSchedulePeriodForDiskUsageCheck(int schedulePeriodForDiskUsageCheck) {
        this.schedulePeriodForDiskUsageCheck = schedulePeriodForDiskUsageCheck;
    }

    public int getDiskUsageCheckRegrowThreshold() {
        return this.diskUsageCheckRegrowThreshold;
    }

    public void setDiskUsageCheckRegrowThreshold(int diskUsageCheckRegrowThreshold) {
        this.diskUsageCheckRegrowThreshold = diskUsageCheckRegrowThreshold;
    }

    public int getMaxPurgedDestinationsPerSweep() {
        return this.maxPurgedDestinationsPerSweep;
    }

    public void setMaxPurgedDestinationsPerSweep(int maxPurgedDestinationsPerSweep) {
        this.maxPurgedDestinationsPerSweep = maxPurgedDestinationsPerSweep;
    }

    public BrokerContext getBrokerContext() {
        return this.brokerContext;
    }

    public void setBrokerContext(BrokerContext brokerContext) {
        this.brokerContext = brokerContext;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = new BrokerId(brokerId);
    }

    public boolean isUseAuthenticatedPrincipalForJMSXUserID() {
        return this.useAuthenticatedPrincipalForJMSXUserID;
    }

    public void setUseAuthenticatedPrincipalForJMSXUserID(boolean useAuthenticatedPrincipalForJMSXUserID) {
        this.useAuthenticatedPrincipalForJMSXUserID = useAuthenticatedPrincipalForJMSXUserID;
    }

    public boolean isPopulateUserNameInMBeans() {
        return this.populateUserNameInMBeans;
    }

    public void setPopulateUserNameInMBeans(boolean value) {
        this.populateUserNameInMBeans = value;
    }

    public long getMbeanInvocationTimeout() {
        return this.mbeanInvocationTimeout;
    }

    public void setMbeanInvocationTimeout(long mbeanInvocationTimeout) {
        this.mbeanInvocationTimeout = mbeanInvocationTimeout;
    }

    public boolean isNetworkConnectorStartAsync() {
        return this.networkConnectorStartAsync;
    }

    public void setNetworkConnectorStartAsync(boolean networkConnectorStartAsync) {
        this.networkConnectorStartAsync = networkConnectorStartAsync;
    }

    public boolean isAllowTempAutoCreationOnSend() {
        return this.allowTempAutoCreationOnSend;
    }

    public void setAllowTempAutoCreationOnSend(boolean allowTempAutoCreationOnSend) {
        this.allowTempAutoCreationOnSend = allowTempAutoCreationOnSend;
    }

    public long getOfflineDurableSubscriberTimeout() {
        return this.offlineDurableSubscriberTimeout;
    }

    public void setOfflineDurableSubscriberTimeout(long offlineDurableSubscriberTimeout) {
        this.offlineDurableSubscriberTimeout = offlineDurableSubscriberTimeout;
    }

    public long getOfflineDurableSubscriberTaskSchedule() {
        return this.offlineDurableSubscriberTaskSchedule;
    }

    public void setOfflineDurableSubscriberTaskSchedule(long offlineDurableSubscriberTaskSchedule) {
        this.offlineDurableSubscriberTaskSchedule = offlineDurableSubscriberTaskSchedule;
    }

    public boolean shouldRecordVirtualDestination(ActiveMQDestination destination) {
        return this.isUseVirtualTopics() && destination.isQueue() && this.getVirtualTopicConsumerDestinationFilter().matches(destination);
    }

    public synchronized Throwable getStartException() {
        return this.startException;
    }

    public boolean isStartAsync() {
        return this.startAsync;
    }

    public void setStartAsync(boolean startAsync) {
        this.startAsync = startAsync;
    }

    public boolean isSlave() {
        return this.slave;
    }

    public boolean isStopping() {
        return this.stopping.get();
    }

    public boolean isRestartAllowed() {
        return this.restartAllowed;
    }

    public void setRestartAllowed(boolean restartAllowed) {
        this.restartAllowed = restartAllowed;
    }

    public boolean isRestartRequested() {
        return this.restartRequested;
    }

    public void requestRestart() {
        this.restartRequested = true;
    }

    public int getStoreOpenWireVersion() {
        return this.storeOpenWireVersion;
    }

    public void setStoreOpenWireVersion(int storeOpenWireVersion) {
        this.storeOpenWireVersion = storeOpenWireVersion;
    }

    public int getCurrentConnections() {
        return this.currentConnections.get();
    }

    public long getTotalConnections() {
        return this.totalConnections.get();
    }

    public void incrementCurrentConnections() {
        this.currentConnections.incrementAndGet();
    }

    public void decrementCurrentConnections() {
        this.currentConnections.decrementAndGet();
    }

    public void incrementTotalConnections() {
        this.totalConnections.incrementAndGet();
    }

    public boolean isRejectDurableConsumers() {
        return this.rejectDurableConsumers;
    }

    public void setRejectDurableConsumers(boolean rejectDurableConsumers) {
        this.rejectDurableConsumers = rejectDurableConsumers;
    }

    public boolean isUseVirtualDestSubs() {
        return this.useVirtualDestSubs;
    }

    public void setUseVirtualDestSubs(boolean useVirtualDestSubs) {
        this.useVirtualDestSubs = useVirtualDestSubs;
    }

    public boolean isUseVirtualDestSubsOnCreation() {
        return this.useVirtualDestSubsOnCreation;
    }

    public void setUseVirtualDestSubsOnCreation(boolean useVirtualDestSubsOnCreation) {
        this.useVirtualDestSubsOnCreation = useVirtualDestSubsOnCreation;
    }

    public boolean isAdjustUsageLimits() {
        return this.adjustUsageLimits;
    }

    public void setAdjustUsageLimits(boolean adjustUsageLimits) {
        this.adjustUsageLimits = adjustUsageLimits;
    }

    public void setRollbackOnlyOnAsyncException(boolean rollbackOnlyOnAsyncException) {
        this.rollbackOnlyOnAsyncException = rollbackOnlyOnAsyncException;
    }

    public boolean isRollbackOnlyOnAsyncException() {
        return this.rollbackOnlyOnAsyncException;
    }

    static {
        String version;
        block42: {
            LOG = LoggerFactory.getLogger(BrokerService.class);
            try {
                ClassLoader loader = BrokerService.class.getClassLoader();
                Class<?> clazz = loader.loadClass("org.bouncycastle.jce.provider.BouncyCastleProvider");
                Provider bouncycastle = (Provider)clazz.newInstance();
                Integer bouncyCastlePosition = Integer.getInteger("org.apache.activemq.broker.BouncyCastlePosition");
                int ret = 0;
                ret = bouncyCastlePosition != null ? Security.insertProviderAt(bouncycastle, bouncyCastlePosition) : Security.addProvider(bouncycastle);
                LOG.info("Loaded the Bouncy Castle security provider at position: " + ret);
            }
            catch (Throwable loader) {
                // empty catch block
            }
            String localHostName = DEFAULT_BROKER_NAME;
            try {
                localHostName = InetAddressUtil.getLocalHostName();
            }
            catch (UnknownHostException e) {
                LOG.error("Failed to resolve localhost");
            }
            LOCAL_HOST_NAME = localHostName;
            version = null;
            try (InputStream in = BrokerService.class.getResourceAsStream("/org/apache/activemq/version.txt");){
                if (in == null) break block42;
                try (InputStreamReader isr = new InputStreamReader(in);
                     BufferedReader reader = new BufferedReader(isr);){
                    version = reader.readLine();
                }
            }
            catch (IOException ie) {
                LOG.warn("Error reading broker version ", ie);
            }
        }
        BROKER_VERSION = version;
    }
}

