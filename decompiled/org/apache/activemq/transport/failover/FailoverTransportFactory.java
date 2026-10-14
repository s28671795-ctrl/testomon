/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.failover;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.activemq.transport.MutexTransport;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.failover.FailoverTransport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class FailoverTransportFactory
extends TransportFactory {
    @Override
    public Transport doConnect(URI location) throws IOException {
        try {
            Transport transport = this.createTransport(URISupport.parseComposite(location));
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
            return this.createTransport(URISupport.parseComposite(location));
        }
        catch (URISyntaxException e) {
            throw new IOException("Invalid location: " + location);
        }
    }

    public Transport createTransport(URISupport.CompositeData compositData) throws IOException {
        Map<String, String> options = compositData.getParameters();
        FailoverTransport transport = this.createTransport(options);
        if (!options.isEmpty()) {
            throw new IllegalArgumentException("Invalid connect parameters: " + options);
        }
        transport.add(false, compositData.getComponents());
        return transport;
    }

    public FailoverTransport createTransport(Map<String, String> parameters) throws IOException {
        FailoverTransport transport = new FailoverTransport();
        Map<String, Object> nestedExtraQueryOptions = IntrospectionSupport.extractProperties(parameters, "nested.");
        IntrospectionSupport.setProperties(transport, parameters);
        try {
            transport.setNestedExtraQueryOptions(URISupport.createQueryString(nestedExtraQueryOptions));
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return transport;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        throw new IOException("Invalid server URI: " + location);
    }
}

