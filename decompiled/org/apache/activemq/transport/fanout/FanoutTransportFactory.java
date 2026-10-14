/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.fanout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.activemq.transport.MutexTransport;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.discovery.DiscoveryTransport;
import org.apache.activemq.transport.discovery.DiscoveryTransportFactory;
import org.apache.activemq.transport.fanout.FanoutTransport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class FanoutTransportFactory
extends TransportFactory {
    @Override
    public Transport doConnect(URI location) throws IOException {
        try {
            Transport transport = this.createTransport(location);
            transport = new MutexTransport(transport);
            transport = new ResponseCorrelator(transport);
            return transport;
        }
        catch (URISyntaxException e) {
            throw new IOException("Invalid location: " + location);
        }
    }

    @Override
    public Transport doCompositeConnect(URI location) throws IOException {
        try {
            return this.createTransport(location);
        }
        catch (URISyntaxException e) {
            throw new IOException("Invalid location: " + location);
        }
    }

    public Transport createTransport(URI location) throws IOException, URISyntaxException {
        URISupport.CompositeData compositeData = URISupport.parseComposite(location);
        Map<String, String> parameters = compositeData.getParameters();
        FanoutTransport fanoutTransport = this.createTransport(parameters);
        DiscoveryTransport discoveryTransport = DiscoveryTransportFactory.createTransport(fanoutTransport, compositeData, parameters);
        return discoveryTransport;
    }

    public FanoutTransport createTransport(Map<String, String> parameters) throws IOException {
        FanoutTransport transport = new FanoutTransport();
        IntrospectionSupport.setProperties(transport, parameters);
        return transport;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        throw new IOException("Invalid server URI: " + location);
    }
}

