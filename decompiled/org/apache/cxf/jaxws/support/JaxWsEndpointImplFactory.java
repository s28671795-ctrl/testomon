/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;

public class JaxWsEndpointImplFactory
implements EndpointImplFactory {
    private static JaxWsEndpointImplFactory singleton = new JaxWsEndpointImplFactory();

    @Override
    public EndpointImpl newEndpointImpl(Bus bus, Service service2, EndpointInfo endpointInfo) throws EndpointException {
        return new JaxWsEndpointImpl(bus, service2, endpointInfo);
    }

    public static EndpointImplFactory getSingleton() {
        return singleton;
    }
}

