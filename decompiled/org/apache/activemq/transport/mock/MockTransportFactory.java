/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.mock;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.activemq.transport.MutexTransport;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.mock.MockTransport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class MockTransportFactory
extends TransportFactory {
    @Override
    public Transport doConnect(URI location) throws URISyntaxException, Exception {
        Transport transport = this.createTransport(URISupport.parseComposite(location));
        transport = new MutexTransport(transport);
        transport = new ResponseCorrelator(transport);
        return transport;
    }

    @Override
    public Transport doCompositeConnect(URI location) throws URISyntaxException, Exception {
        return this.createTransport(URISupport.parseComposite(location));
    }

    public Transport createTransport(URISupport.CompositeData compositData) throws Exception {
        MockTransport transport = new MockTransport(TransportFactory.compositeConnect(compositData.getComponents()[0]));
        IntrospectionSupport.setProperties(transport, compositData.getParameters());
        return transport;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        throw new IOException("This protocol does not support being bound.");
    }
}

