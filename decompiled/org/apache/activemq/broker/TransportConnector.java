/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import javax.management.ObjectName;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.BrokerStoppedException;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.PublishedAddressPolicy;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.TransportStatusDetector;
import org.apache.activemq.broker.jmx.ManagedTransportConnector;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.region.ConnectorStatistics;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportAcceptListener;
import org.apache.activemq.transport.TransportFactorySupport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportConnector
implements Connector,
BrokerServiceAware {
    final Logger LOG = LoggerFactory.getLogger(TransportConnector.class);
    protected final CopyOnWriteArrayList<TransportConnection> connections = new CopyOnWriteArrayList();
    protected TransportStatusDetector statusDector;
    private BrokerService brokerService;
    private TransportServer server;
    private URI uri;
    private BrokerInfo brokerInfo = new BrokerInfo();
    private TaskRunnerFactory taskRunnerFactory;
    private MessageAuthorizationPolicy messageAuthorizationPolicy;
    private DiscoveryAgent discoveryAgent;
    private final ConnectorStatistics statistics = new ConnectorStatistics();
    private URI discoveryUri;
    private String name;
    private boolean disableAsyncDispatch;
    private boolean enableStatusMonitor = false;
    private Broker broker;
    private boolean updateClusterClients = false;
    private boolean rebalanceClusterClients;
    private boolean updateClusterClientsOnRemove = false;
    private String updateClusterFilter;
    private boolean auditNetworkProducers = false;
    private int maximumProducersAllowedPerConnection = Integer.MAX_VALUE;
    private int maximumConsumersAllowedPerConnection = Integer.MAX_VALUE;
    private PublishedAddressPolicy publishedAddressPolicy = new PublishedAddressPolicy();
    private boolean allowLinkStealing;
    LinkedList<String> peerBrokers = new LinkedList();

    public TransportConnector() {
    }

    public TransportConnector(TransportServer server) {
        this();
        URI uri;
        this.setServer(server);
        if (server != null && server.getConnectURI() != null && (uri = server.getConnectURI()) != null && uri.getScheme().equals("vm")) {
            this.setEnableStatusMonitor(false);
        }
    }

    public CopyOnWriteArrayList<TransportConnection> getConnections() {
        return this.connections;
    }

    public ManagedTransportConnector asManagedConnector(ManagementContext context, ObjectName connectorName) throws IOException, URISyntaxException {
        ManagedTransportConnector rc = new ManagedTransportConnector(context, connectorName, this.getServer());
        rc.setBrokerInfo(this.getBrokerInfo());
        rc.setDisableAsyncDispatch(this.isDisableAsyncDispatch());
        rc.setDiscoveryAgent(this.getDiscoveryAgent());
        rc.setDiscoveryUri(this.getDiscoveryUri());
        rc.setEnableStatusMonitor(this.isEnableStatusMonitor());
        rc.setMessageAuthorizationPolicy(this.getMessageAuthorizationPolicy());
        rc.setName(this.getName());
        rc.setTaskRunnerFactory(this.getTaskRunnerFactory());
        rc.setUri(this.getUri());
        rc.setBrokerService(this.brokerService);
        rc.setUpdateClusterClients(this.isUpdateClusterClients());
        rc.setRebalanceClusterClients(this.isRebalanceClusterClients());
        rc.setUpdateClusterFilter(this.getUpdateClusterFilter());
        rc.setUpdateClusterClientsOnRemove(this.isUpdateClusterClientsOnRemove());
        rc.setAuditNetworkProducers(this.isAuditNetworkProducers());
        rc.setMaximumConsumersAllowedPerConnection(this.getMaximumConsumersAllowedPerConnection());
        rc.setMaximumProducersAllowedPerConnection(this.getMaximumProducersAllowedPerConnection());
        rc.setPublishedAddressPolicy(this.getPublishedAddressPolicy());
        rc.setAllowLinkStealing(this.isAllowLinkStealing());
        return rc;
    }

    @Override
    public BrokerInfo getBrokerInfo() {
        return this.brokerInfo;
    }

    public void setBrokerInfo(BrokerInfo brokerInfo) {
        this.brokerInfo = brokerInfo;
    }

    public TransportServer getServer() throws IOException, URISyntaxException {
        if (this.server == null) {
            this.setServer(this.createTransportServer());
        }
        return this.server;
    }

    public void setServer(TransportServer server) {
        this.server = server;
    }

    public URI getUri() {
        if (this.uri == null) {
            try {
                this.uri = this.getConnectUri();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return this.uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public TaskRunnerFactory getTaskRunnerFactory() {
        return this.taskRunnerFactory;
    }

    public void setTaskRunnerFactory(TaskRunnerFactory taskRunnerFactory) {
        this.taskRunnerFactory = taskRunnerFactory;
    }

    @Override
    public ConnectorStatistics getStatistics() {
        return this.statistics;
    }

    public MessageAuthorizationPolicy getMessageAuthorizationPolicy() {
        return this.messageAuthorizationPolicy;
    }

    public void setMessageAuthorizationPolicy(MessageAuthorizationPolicy messageAuthorizationPolicy) {
        this.messageAuthorizationPolicy = messageAuthorizationPolicy;
    }

    @Override
    public void start() throws Exception {
        this.broker = this.brokerService.getBroker();
        this.brokerInfo.setBrokerName(this.broker.getBrokerName());
        this.brokerInfo.setBrokerId(this.broker.getBrokerId());
        this.brokerInfo.setPeerBrokerInfos(this.broker.getPeerBrokerInfos());
        this.brokerInfo.setFaultTolerantConfiguration(this.broker.isFaultTolerantConfiguration());
        this.brokerInfo.setBrokerURL(this.broker.getBrokerService().getDefaultSocketURIString());
        this.getServer().setAcceptListener(new TransportAcceptListener(){

            @Override
            public void onAccept(final Transport transport) {
                try {
                    TransportConnector.this.brokerService.getTaskRunnerFactory().execute(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                if (TransportConnector.this.brokerService.isStopping()) {
                                    throw new BrokerStoppedException("Broker " + TransportConnector.this.brokerService + " is being stopped");
                                }
                                Connection connection = TransportConnector.this.createConnection(transport);
                                connection.start();
                            }
                            catch (Exception e) {
                                String remoteHost = transport.getRemoteAddress();
                                ServiceSupport.dispose(transport);
                                this.onAcceptError(e, remoteHost);
                            }
                        }
                    });
                }
                catch (Exception e) {
                    String remoteHost = transport.getRemoteAddress();
                    ServiceSupport.dispose(transport);
                    this.onAcceptError(e, remoteHost);
                }
            }

            @Override
            public void onAcceptError(Exception error) {
                this.onAcceptError(error, null);
            }

            private void onAcceptError(Exception error, String remoteHost) {
                if (TransportConnector.this.brokerService != null && TransportConnector.this.brokerService.isStopping()) {
                    TransportConnector.this.LOG.info("Could not accept connection during shutdown {} : {} ({})", remoteHost == null ? "" : "from " + remoteHost, error.getLocalizedMessage(), TransportConnector.getRootCause(error).getMessage());
                } else {
                    TransportConnector.this.LOG.warn("Could not accept connection {}: {} ({})", remoteHost == null ? "" : "from " + remoteHost, error.getMessage(), TransportConnector.getRootCause(error).getMessage());
                    TransportConnector.this.LOG.debug("Reason: " + error.getMessage(), error);
                }
            }
        });
        this.getServer().setBrokerInfo(this.brokerInfo);
        this.getServer().start();
        DiscoveryAgent da = this.getDiscoveryAgent();
        if (da != null) {
            da.registerService(this.getPublishableConnectString());
            da.start();
        }
        if (this.enableStatusMonitor) {
            this.statusDector = new TransportStatusDetector(this);
            this.statusDector.start();
        }
        this.LOG.info("Connector {} started", (Object)this.getName());
    }

    static Throwable getRootCause(Throwable throwable) {
        List<Throwable> list = TransportConnector.getThrowableList(throwable);
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    static List<Throwable> getThrowableList(Throwable throwable) {
        ArrayList<Throwable> list = new ArrayList<Throwable>();
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }

    public String getPublishableConnectString() throws Exception {
        String publishableConnectString = this.publishedAddressPolicy.getPublishableConnectString(this);
        this.LOG.debug("Publishing: {} for broker transport URI: {}", (Object)publishableConnectString, (Object)this.getConnectUri());
        return publishableConnectString;
    }

    public URI getPublishableConnectURI() throws Exception {
        return this.publishedAddressPolicy.getPublishableConnectURI(this);
    }

    @Override
    public void stop() throws Exception {
        ServiceStopper ss = new ServiceStopper();
        if (this.discoveryAgent != null) {
            ss.stop(this.discoveryAgent);
        }
        if (this.server != null) {
            ss.stop(this.server);
        }
        if (this.statusDector != null) {
            this.statusDector.stop();
        }
        for (TransportConnection connection : this.connections) {
            ss.stop(connection);
        }
        this.server = null;
        ss.throwFirstException();
        this.LOG.info("Connector {} stopped", (Object)this.getName());
    }

    protected Connection createConnection(Transport transport) throws IOException {
        TransportConnection answer = new TransportConnection(this, transport, this.broker, this.disableAsyncDispatch ? null : this.taskRunnerFactory, this.brokerService.getTaskRunnerFactory());
        boolean statEnabled = this.getStatistics().isEnabled();
        answer.getStatistics().setEnabled(statEnabled);
        answer.setMessageAuthorizationPolicy(this.messageAuthorizationPolicy);
        return answer;
    }

    protected TransportServer createTransportServer() throws IOException, URISyntaxException {
        if (this.uri == null) {
            throw new IllegalArgumentException("You must specify either a server or uri property");
        }
        if (this.brokerService == null) {
            throw new IllegalArgumentException("You must specify the brokerService property. Maybe this connector should be added to a broker?");
        }
        return TransportFactorySupport.bind(this.brokerService, this.uri);
    }

    public DiscoveryAgent getDiscoveryAgent() throws IOException {
        if (this.discoveryAgent == null) {
            this.discoveryAgent = this.createDiscoveryAgent();
        }
        return this.discoveryAgent;
    }

    protected DiscoveryAgent createDiscoveryAgent() throws IOException {
        if (this.discoveryUri != null) {
            DiscoveryAgent agent = DiscoveryAgentFactory.createDiscoveryAgent(this.discoveryUri);
            if (agent != null && agent instanceof BrokerServiceAware) {
                ((BrokerServiceAware)((Object)agent)).setBrokerService(this.brokerService);
            }
            return agent;
        }
        return null;
    }

    public void setDiscoveryAgent(DiscoveryAgent discoveryAgent) {
        this.discoveryAgent = discoveryAgent;
    }

    public URI getDiscoveryUri() {
        return this.discoveryUri;
    }

    public void setDiscoveryUri(URI discoveryUri) {
        this.discoveryUri = discoveryUri;
    }

    public URI getConnectUri() throws IOException, URISyntaxException {
        if (this.server != null) {
            return this.server.getConnectURI();
        }
        return this.uri;
    }

    public void onStarted(TransportConnection connection) {
        this.connections.add(connection);
    }

    public void onStopped(TransportConnection connection) {
        this.connections.remove(connection);
    }

    public String getName() {
        if (this.name == null) {
            this.uri = this.getUri();
            if (this.uri != null) {
                this.name = this.uri.toString();
            }
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String rc = this.getName();
        if (rc == null) {
            rc = super.toString();
        }
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ConnectionControl getConnectionControl() {
        boolean rebalance = this.isRebalanceClusterClients();
        String connectedBrokers = "";
        String separator = "";
        if (this.isUpdateClusterClients()) {
            LinkedList<String> linkedList = this.peerBrokers;
            synchronized (linkedList) {
                for (String uri : this.getPeerBrokers()) {
                    connectedBrokers = connectedBrokers + separator + uri;
                    separator = ",";
                }
                if (rebalance) {
                    String shuffle = this.peerBrokers.removeFirst();
                    this.peerBrokers.addLast(shuffle);
                }
            }
        }
        ConnectionControl control = new ConnectionControl();
        control.setConnectedBrokers(connectedBrokers);
        control.setRebalanceConnection(rebalance);
        return control;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addPeerBroker(BrokerInfo info) {
        if (this.isMatchesClusterFilter(info.getBrokerName())) {
            LinkedList<String> linkedList = this.peerBrokers;
            synchronized (linkedList) {
                this.getPeerBrokers().addLast(info.getBrokerURL());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removePeerBroker(BrokerInfo info) {
        LinkedList<String> linkedList = this.peerBrokers;
        synchronized (linkedList) {
            this.getPeerBrokers().remove(info.getBrokerURL());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LinkedList<String> getPeerBrokers() {
        LinkedList<String> linkedList = this.peerBrokers;
        synchronized (linkedList) {
            if (this.peerBrokers.isEmpty()) {
                this.peerBrokers.add(this.brokerService.getDefaultSocketURIString());
            }
            return this.peerBrokers;
        }
    }

    @Override
    public void updateClientClusterInfo() {
        if (this.isRebalanceClusterClients() || this.isUpdateClusterClients()) {
            ConnectionControl control = this.getConnectionControl();
            for (Connection connection : this.connections) {
                connection.updateClient(control);
                if (!this.isRebalanceClusterClients()) continue;
                control = this.getConnectionControl();
            }
        }
    }

    private boolean isMatchesClusterFilter(String brokerName) {
        boolean result = true;
        String filter2 = this.getUpdateClusterFilter();
        if (filter2 != null && (filter2 = filter2.trim()).length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(filter2, ",");
            while (result && tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                result = this.isMatchesClusterFilter(brokerName, token);
            }
        }
        return result;
    }

    private boolean isMatchesClusterFilter(String brokerName, String match) {
        boolean result = true;
        if (brokerName != null && match != null && brokerName.length() > 0 && match.length() > 0) {
            result = Pattern.matches(match, brokerName);
        }
        return result;
    }

    public boolean isDisableAsyncDispatch() {
        return this.disableAsyncDispatch;
    }

    public void setDisableAsyncDispatch(boolean disableAsyncDispatch) {
        this.disableAsyncDispatch = disableAsyncDispatch;
    }

    public boolean isEnableStatusMonitor() {
        return this.enableStatusMonitor;
    }

    public void setEnableStatusMonitor(boolean enableStatusMonitor) {
        this.enableStatusMonitor = enableStatusMonitor;
    }

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public Broker getBroker() {
        return this.broker;
    }

    public BrokerService getBrokerService() {
        return this.brokerService;
    }

    @Override
    public boolean isUpdateClusterClients() {
        return this.updateClusterClients;
    }

    public void setUpdateClusterClients(boolean updateClusterClients) {
        this.updateClusterClients = updateClusterClients;
    }

    @Override
    public boolean isRebalanceClusterClients() {
        return this.rebalanceClusterClients;
    }

    public void setRebalanceClusterClients(boolean rebalanceClusterClients) {
        this.rebalanceClusterClients = rebalanceClusterClients;
    }

    @Override
    public boolean isUpdateClusterClientsOnRemove() {
        return this.updateClusterClientsOnRemove;
    }

    public void setUpdateClusterClientsOnRemove(boolean updateClusterClientsOnRemove) {
        this.updateClusterClientsOnRemove = updateClusterClientsOnRemove;
    }

    @Override
    public String getUpdateClusterFilter() {
        return this.updateClusterFilter;
    }

    public void setUpdateClusterFilter(String updateClusterFilter) {
        this.updateClusterFilter = updateClusterFilter;
    }

    @Override
    public int connectionCount() {
        return this.connections.size();
    }

    @Override
    public boolean isAllowLinkStealing() {
        return this.server.isAllowLinkStealing();
    }

    public void setAllowLinkStealing(boolean allowLinkStealing) {
        this.allowLinkStealing = allowLinkStealing;
    }

    public boolean isAuditNetworkProducers() {
        return this.auditNetworkProducers;
    }

    public void setAuditNetworkProducers(boolean auditNetworkProducers) {
        this.auditNetworkProducers = auditNetworkProducers;
    }

    public int getMaximumProducersAllowedPerConnection() {
        return this.maximumProducersAllowedPerConnection;
    }

    public void setMaximumProducersAllowedPerConnection(int maximumProducersAllowedPerConnection) {
        this.maximumProducersAllowedPerConnection = maximumProducersAllowedPerConnection;
    }

    public int getMaximumConsumersAllowedPerConnection() {
        return this.maximumConsumersAllowedPerConnection;
    }

    public void setMaximumConsumersAllowedPerConnection(int maximumConsumersAllowedPerConnection) {
        this.maximumConsumersAllowedPerConnection = maximumConsumersAllowedPerConnection;
    }

    public PublishedAddressPolicy getPublishedAddressPolicy() {
        return this.publishedAddressPolicy;
    }

    public void setPublishedAddressPolicy(PublishedAddressPolicy publishedAddressPolicy) {
        this.publishedAddressPolicy = publishedAddressPolicy;
    }
}

