/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.endpoint.dynamic;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.jaxws.support.JaxWsEndpointImplFactory;

public class JaxWsDynamicClientFactory
extends DynamicClientFactory {
    protected JaxWsDynamicClientFactory(Bus bus) {
        super(bus);
    }

    @Override
    protected EndpointImplFactory getEndpointImplFactory() {
        return JaxWsEndpointImplFactory.getSingleton();
    }

    @Override
    protected boolean allowWrapperOps() {
        return true;
    }

    public static JaxWsDynamicClientFactory newInstance(Bus b) {
        return new JaxWsDynamicClientFactory(b);
    }

    public static JaxWsDynamicClientFactory newInstance() {
        Bus bus = BusFactory.getThreadDefaultBus();
        return new JaxWsDynamicClientFactory(bus);
    }
}

