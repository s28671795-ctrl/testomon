/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.NetworkBridgeView;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.network.ConnectionFilter;
import org.apache.activemq.network.DemandForwardingBridgeSupport;
import org.apache.activemq.network.NetworkBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NetworkConnector
extends NetworkBridgeConfiguration
implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkConnector.class);
    protected URI localURI;
    protected ConnectionFilter connectionFilter;
    protected ConcurrentMap<URI, NetworkBridge> bridges = new ConcurrentHashMap<URI, NetworkBridge>();
    protected ServiceSupport serviceSupport = new ServiceSupport(){

        @Override
        protected void doStart() throws Exception {
            NetworkConnector.this.handleStart();
        }

        @Override
        protected void doStop(ServiceStopper stopper) throws Exception {
            NetworkConnector.this.handleStop(stopper);
        }
    };
    private Set<ActiveMQDestination> durableDestinations;
    private BrokerService brokerService;
    private ObjectName objectName;

    public NetworkConnector() {
    }

    public NetworkConnector(URI localURI) {
        this.localURI = localURI;
    }

    public URI getLocalUri() throws URISyntaxException {
        return this.localURI;
    }

    public void setLocalUri(URI localURI) {
        this.localURI = localURI;
    }

    public Set<ActiveMQDestination> getDurableDestinations() {
        return this.durableDestinations;
    }

    public void setDurableDestinations(Set<ActiveMQDestination> durableDestinations) {
        this.durableDestinations = durableDestinations;
    }

    public void addExcludedDestination(ActiveMQDestination destination) {
        this.excludedDestinations.add(destination);
    }

    public void addStaticallyIncludedDestination(ActiveMQDestination destination) {
        this.staticallyIncludedDestinations.add(destination);
    }

    public void addDynamicallyIncludedDestination(ActiveMQDestination destination) {
        this.dynamicallyIncludedDestinations.add(destination);
    }

    public ConnectionFilter getConnectionFilter() {
        return this.connectionFilter;
    }

    public void setConnectionFilter(ConnectionFilter connectionFilter) {
        this.connectionFilter = connectionFilter;
    }

    protected NetworkBridge configureBridge(DemandForwardingBridgeSupport result) {
        List<ActiveMQDestination> destsList = this.getDynamicallyIncludedDestinations();
        ActiveMQDestination[] dests = destsList.toArray(new ActiveMQDestination[destsList.size()]);
        result.setDynamicallyIncludedDestinations(dests);
        destsList = this.getExcludedDestinations();
        dests = destsList.toArray(new ActiveMQDestination[destsList.size()]);
        result.setExcludedDestinations(dests);
        destsList = this.getStaticallyIncludedDestinations();
        dests = destsList.toArray(new ActiveMQDestination[destsList.size()]);
        result.setStaticallyIncludedDestinations(dests);
        result.setDurableDestinations(NetworkConnector.getDurableTopicDestinations(this.durableDestinations));
        return result;
    }

    protected Transport createLocalTransport() throws Exception {
        return NetworkBridgeFactory.createLocalTransport(this, this.localURI);
    }

    public static ActiveMQDestination[] getDurableTopicDestinations(Set<ActiveMQDestination> durableDestinations) {
        if (durableDestinations != null) {
            HashSet<ActiveMQDestination> topics = new HashSet<ActiveMQDestination>();
            for (ActiveMQDestination d : durableDestinations) {
                if (!d.isTopic()) continue;
                topics.add(d);
            }
            ActiveMQDestination[] dest = new ActiveMQDestination[topics.size()];
            dest = topics.toArray(dest);
            return dest;
        }
        return null;
    }

    @Override
    public void start() throws Exception {
        this.serviceSupport.start();
    }

    @Override
    public void stop() throws Exception {
        this.serviceSupport.stop();
    }

    protected void handleStart() throws Exception {
        if (this.localURI == null) {
            throw new IllegalStateException("You must configure the 'localURI' property");
        }
        LOG.info("Network Connector {} started", (Object)this);
    }

    protected void handleStop(ServiceStopper stopper) throws Exception {
        LOG.info("Network Connector {} stopped", (Object)this);
    }

    public boolean isStarted() {
        return this.serviceSupport.isStarted();
    }

    public boolean isStopped() {
        return this.serviceSupport.isStopped();
    }

    public boolean isStopping() {
        return this.serviceSupport.isStopping();
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    public BrokerService getBrokerService() {
        return this.brokerService;
    }

    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    protected void registerNetworkBridgeMBean(NetworkBridge bridge) {
        if (!this.getBrokerService().isUseJmx()) {
            return;
        }
        NetworkBridgeView view = new NetworkBridgeView(bridge);
        try {
            ObjectName objectName = this.createNetworkBridgeObjectName(bridge);
            AnnotatedMBean.registerMBean(this.getBrokerService().getManagementContext(), view, objectName);
        }
        catch (Throwable e) {
            LOG.debug("Network bridge could not be registered in JMX: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    protected void unregisterNetworkBridgeMBean(NetworkBridge bridge) {
        if (!this.getBrokerService().isUseJmx()) {
            return;
        }
        try {
            ObjectName objectName = this.createNetworkBridgeObjectName(bridge);
            this.getBrokerService().getManagementContext().unregisterMBean(objectName);
        }
        catch (Throwable e) {
            LOG.debug("Network bridge could not be unregistered in JMX: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    protected ObjectName createNetworkBridgeObjectName(NetworkBridge bridge) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createNetworkBridgeObjectName(this.getObjectName(), bridge.getRemoteAddress());
    }

    public boolean removeDemandSubscription(ConsumerId consumerId) {
        boolean removeSucceeded = false;
        for (NetworkBridge bridge : this.bridges.values()) {
            DemandForwardingBridgeSupport demandBridge;
            if (!(bridge instanceof DemandForwardingBridgeSupport) || !(demandBridge = (DemandForwardingBridgeSupport)bridge).removeDemandSubscriptionByLocalId(consumerId)) continue;
            removeSucceeded = true;
            break;
        }
        return removeSucceeded;
    }

    public Collection<NetworkBridge> activeBridges() {
        return this.bridges.values();
    }
}

