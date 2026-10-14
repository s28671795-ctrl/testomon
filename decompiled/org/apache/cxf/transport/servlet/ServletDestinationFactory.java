/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.servlet.ServletDestination;

public class ServletDestinationFactory
implements HttpDestinationFactory {
    @Override
    public AbstractHTTPDestination createDestination(EndpointInfo endpointInfo, Bus bus, DestinationRegistry registry) throws IOException {
        return new ServletDestination(bus, registry, endpointInfo, endpointInfo.getAddress());
    }
}

