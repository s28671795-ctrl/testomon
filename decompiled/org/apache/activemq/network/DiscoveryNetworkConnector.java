/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.management.ObjectName;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.network.DemandForwardingBridge;
import org.apache.activemq.network.MBeanNetworkListener;
import org.apache.activemq.network.NetworkBridge;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryNetworkConnector
extends NetworkConnector
implements DiscoveryListener {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryNetworkConnector.class);
    private DiscoveryAgent discoveryAgent;
    private Map<String, String> parameters;
    private final ConcurrentMap<URI, DiscoveryEvent> activeEvents = new ConcurrentHashMap<URI, DiscoveryEvent>();
    private URI discoveryUri;

    public DiscoveryNetworkConnector() {
    }

    public DiscoveryNetworkConnector(URI discoveryURI) throws IOException {
        this.setUri(discoveryURI);
    }

    public void setUri(URI discoveryURI) throws IOException {
        this.discoveryUri = discoveryURI;
        this.setDiscoveryAgent(DiscoveryAgentFactory.createDiscoveryAgent(discoveryURI));
        try {
            this.parameters = URISupport.parseParameters(discoveryURI);
            IntrospectionSupport.setProperties(this.getDiscoveryAgent(), this.parameters);
        }
        catch (URISyntaxException e) {
            LOG.warn("failed to parse query parameters from discoveryURI: {}", (Object)discoveryURI, (Object)e);
        }
    }

    public URI getUri() {
        return this.discoveryUri;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onServiceAdd(DiscoveryEvent event) {
        if (this.serviceSupport.isStopped() || this.serviceSupport.isStopping()) {
            return;
        }
        String url2 = event.getServiceName();
        if (url2 != null) {
            Transport localTransport;
            Transport remoteTransport;
            URI uri;
            try {
                uri = new URI(url2);
            }
            catch (URISyntaxException e) {
                LOG.warn("Could not connect to remote URI: {} due to bad URI syntax: ", (Object)url2, (Object)e);
                return;
            }
            if (this.localURI.equals(uri)) {
                LOG.debug("not connecting loopback: {}", (Object)uri);
                return;
            }
            if (this.connectionFilter != null && !this.connectionFilter.connectTo(uri)) {
                LOG.debug("connectionFilter disallows connection to: {}", (Object)uri);
                return;
            }
            if (this.activeEvents.putIfAbsent(uri, event) != null) {
                LOG.debug("Discovery agent generated a duplicate onServiceAdd event for: {}", (Object)uri);
                return;
            }
            URI connectUri = uri;
            try {
                connectUri = URISupport.applyParameters(connectUri, this.parameters, "discovered.");
            }
            catch (URISyntaxException e) {
                LOG.warn("could not apply query parameters: {} to: {}", (Object)new Object[]{this.parameters, connectUri}, (Object)e);
            }
            LOG.info("Establishing network connection from {} to {}", (Object)this.localURI, (Object)connectUri);
            try {
                SslContext.setCurrentSslContext(this.getBrokerService().getSslContext());
                try {
                    remoteTransport = TransportFactory.connect(connectUri);
                }
                catch (Exception e) {
                    LOG.warn("Could not connect to remote URI: {}: {}", (Object)connectUri, (Object)e.getMessage());
                    LOG.debug("Connection failure exception: ", e);
                    try {
                        this.discoveryAgent.serviceFailed(event);
                    }
                    catch (IOException e1) {
                        LOG.debug("Failure while handling create remote transport failure event: {}", (Object)e1.getMessage(), (Object)e1);
                    }
                    SslContext.setCurrentSslContext(null);
                    return;
                }
                try {
                    localTransport = this.createLocalTransport();
                }
                catch (Exception e) {
                    ServiceSupport.dispose(remoteTransport);
                    LOG.warn("Could not connect to local URI: {}: {}", (Object)this.localURI, (Object)e.getMessage());
                    LOG.debug("Connection failure exception: ", e);
                    try {
                        this.discoveryAgent.serviceFailed(event);
                    }
                    catch (IOException e1) {
                        LOG.debug("Failure while handling create local transport failure event: {}", (Object)e1.getMessage(), (Object)e1);
                    }
                    SslContext.setCurrentSslContext(null);
                    return;
                }
            }
            finally {
                SslContext.setCurrentSslContext(null);
            }
            NetworkBridge bridge = this.createBridge(localTransport, remoteTransport, event);
            try {
                ConcurrentMap e1 = this.bridges;
                synchronized (e1) {
                    this.bridges.put(uri, bridge);
                }
                bridge.start();
            }
            catch (Exception e) {
                ServiceSupport.dispose(localTransport);
                ServiceSupport.dispose(remoteTransport);
                LOG.warn("Could not start network bridge between: {} and: {} due to: {}", this.localURI, uri, e.getMessage());
                LOG.debug("Start failure exception: ", e);
                try {
                    this.discoveryAgent.serviceFailed(event);
                }
                catch (IOException e1) {
                    LOG.debug("Discovery agent failure while handling failure event: {}", (Object)e1.getMessage(), (Object)e1);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onServiceRemove(DiscoveryEvent event) {
        String url2 = event.getServiceName();
        if (url2 != null) {
            URI uri;
            try {
                uri = new URI(url2);
            }
            catch (URISyntaxException e) {
                LOG.warn("Could not connect to remote URI: {} due to bad URI syntax: ", (Object)url2, (Object)e);
                return;
            }
            if (this.activeEvents.remove(uri, event)) {
                ConcurrentMap concurrentMap = this.bridges;
                synchronized (concurrentMap) {
                    this.bridges.remove(uri);
                }
            }
        }
    }

    public DiscoveryAgent getDiscoveryAgent() {
        return this.discoveryAgent;
    }

    public void setDiscoveryAgent(DiscoveryAgent discoveryAgent) {
        this.discoveryAgent = discoveryAgent;
        if (discoveryAgent != null) {
            this.discoveryAgent.setDiscoveryListener(this);
        }
    }

    @Override
    protected void handleStart() throws Exception {
        if (this.discoveryAgent == null) {
            throw new IllegalStateException("You must configure the 'discoveryAgent' property");
        }
        this.discoveryAgent.start();
        super.handleStart();
    }

    @Override
    protected void handleStop(ServiceStopper stopper) throws Exception {
        for (NetworkBridge bridge : this.bridges.values()) {
            try {
                bridge.stop();
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
        }
        this.bridges.clear();
        this.activeEvents.clear();
        try {
            this.discoveryAgent.stop();
        }
        catch (Exception e) {
            stopper.onException(this, e);
        }
        super.handleStop(stopper);
    }

    protected NetworkBridge createBridge(Transport localTransport, Transport remoteTransport, final DiscoveryEvent event) {
        class DiscoverNetworkBridgeListener
        extends MBeanNetworkListener {
            public DiscoverNetworkBridgeListener(BrokerService brokerService, ObjectName connectorName) {
                super(brokerService, DiscoveryNetworkConnector.this, connectorName);
            }

            @Override
            public void bridgeFailed() {
                if (!DiscoveryNetworkConnector.this.serviceSupport.isStopped()) {
                    try {
                        DiscoveryNetworkConnector.this.discoveryAgent.serviceFailed(event);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
        }
        DiscoverNetworkBridgeListener listener = new DiscoverNetworkBridgeListener(this.getBrokerService(), this.getObjectName());
        DemandForwardingBridge result = this.getBridgeFactory().createNetworkBridge(this, localTransport, remoteTransport, listener);
        result.setBrokerService(this.getBrokerService());
        return this.configureBridge(result);
    }

    public String toString() {
        return "DiscoveryNetworkConnector:" + this.getName() + ":" + this.getBrokerService();
    }
}

