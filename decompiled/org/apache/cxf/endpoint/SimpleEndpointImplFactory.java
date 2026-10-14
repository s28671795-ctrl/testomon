/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;

public class SimpleEndpointImplFactory
implements EndpointImplFactory {
    private static EndpointImplFactory singleton = new SimpleEndpointImplFactory();

    @Override
    public EndpointImpl newEndpointImpl(Bus bus, Service service2, EndpointInfo endpointInfo) throws EndpointException {
        return new EndpointImpl(bus, service2, endpointInfo);
    }

    public static EndpointImplFactory getSingleton() {
        return singleton;
    }
}

