/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.transport.CompositeTransport;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.DiscoveryTransport;
import org.apache.activemq.transport.failover.FailoverTransport;
import org.apache.activemq.transport.failover.FailoverTransportFactory;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class DiscoveryTransportFactory
extends FailoverTransportFactory {
    @Override
    public Transport createTransport(URISupport.CompositeData compositeData) throws IOException {
        HashMap<String, String> parameters = new HashMap<String, String>(compositeData.getParameters());
        FailoverTransport failoverTransport = this.createTransport(parameters);
        return DiscoveryTransportFactory.createTransport(failoverTransport, compositeData, parameters);
    }

    public static DiscoveryTransport createTransport(CompositeTransport compositeTransport, URISupport.CompositeData compositeData, Map<String, String> parameters) throws IOException {
        DiscoveryTransport transport = new DiscoveryTransport(compositeTransport);
        IntrospectionSupport.setProperties(transport, parameters);
        transport.setParameters(parameters);
        URI discoveryAgentURI = compositeData.getComponents()[0];
        DiscoveryAgent discoveryAgent = DiscoveryAgentFactory.createDiscoveryAgent(discoveryAgentURI);
        transport.setDiscoveryAgent(discoveryAgent);
        return transport;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        throw new IOException("Invalid server URI: " + location);
    }
}

