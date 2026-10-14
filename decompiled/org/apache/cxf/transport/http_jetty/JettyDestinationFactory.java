/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty;

import java.io.IOException;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;

@NoJSR250Annotations
public class JettyDestinationFactory
implements HttpDestinationFactory {
    @Override
    public AbstractHTTPDestination createDestination(EndpointInfo endpointInfo, Bus bus, DestinationRegistry registry) throws IOException {
        JettyHTTPServerEngineFactory serverEngineFactory = bus.getExtension(JettyHTTPServerEngineFactory.class);
        return new JettyHTTPDestination(bus, registry, endpointInfo, serverEngineFactory);
    }
}

