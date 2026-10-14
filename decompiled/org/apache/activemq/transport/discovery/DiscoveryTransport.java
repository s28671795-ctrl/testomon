/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.transport.CompositeTransport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.Suspendable;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryTransport
extends TransportFilter
implements DiscoveryListener {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryTransport.class);
    private final CompositeTransport next;
    private DiscoveryAgent discoveryAgent;
    private final ConcurrentMap<String, URI> serviceURIs = new ConcurrentHashMap<String, URI>();
    private Map<String, String> parameters;

    public DiscoveryTransport(CompositeTransport next) {
        super(next);
        this.next = next;
    }

    @Override
    public void start() throws Exception {
        if (this.discoveryAgent == null) {
            throw new IllegalStateException("discoveryAgent not configured");
        }
        this.discoveryAgent.setDiscoveryListener(this);
        this.discoveryAgent.start();
        this.next.start();
    }

    @Override
    public void stop() throws Exception {
        ServiceStopper ss = new ServiceStopper();
        ss.stop(this.discoveryAgent);
        ss.stop(this.next);
        ss.throwFirstException();
    }

    @Override
    public void onServiceAdd(DiscoveryEvent event) {
        String url2 = event.getServiceName();
        if (url2 != null) {
            try {
                URI uri = new URI(url2);
                LOG.info("Adding new broker connection URL: " + uri);
                uri = URISupport.applyParameters(uri, this.parameters, "discovered.");
                this.serviceURIs.put(event.getServiceName(), uri);
                this.next.add(false, new URI[]{uri});
            }
            catch (URISyntaxException e) {
                LOG.warn("Could not connect to remote URI: " + url2 + " due to bad URI syntax: " + e, e);
            }
        }
    }

    @Override
    public void onServiceRemove(DiscoveryEvent event) {
        URI uri = (URI)this.serviceURIs.get(event.getServiceName());
        if (uri != null) {
            this.next.remove(false, new URI[]{uri});
        }
    }

    public DiscoveryAgent getDiscoveryAgent() {
        return this.discoveryAgent;
    }

    public void setDiscoveryAgent(DiscoveryAgent discoveryAgent) {
        this.discoveryAgent = discoveryAgent;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void transportResumed() {
        if (this.discoveryAgent instanceof Suspendable) {
            try {
                ((Suspendable)((Object)this.discoveryAgent)).suspend();
            }
            catch (Exception e) {
                LOG.warn("Exception suspending discoverAgent: {}", (Object)this.discoveryAgent);
            }
        }
        super.transportResumed();
    }

    @Override
    public void transportInterupted() {
        if (this.discoveryAgent instanceof Suspendable) {
            try {
                ((Suspendable)((Object)this.discoveryAgent)).resume();
            }
            catch (Exception e) {
                LOG.warn("Exception resuming discoverAgent: {}", (Object)this.discoveryAgent);
            }
        }
        super.transportInterupted();
    }
}

