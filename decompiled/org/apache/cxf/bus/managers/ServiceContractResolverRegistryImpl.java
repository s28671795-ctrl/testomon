/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.ServiceContractResolver;
import org.apache.cxf.endpoint.ServiceContractResolverRegistry;

@NoJSR250Annotations(unlessNull={"bus"})
public class ServiceContractResolverRegistryImpl
implements ServiceContractResolverRegistry {
    private Bus bus;
    private List<ServiceContractResolver> resolvers = new CopyOnWriteArrayList<ServiceContractResolver>();

    public ServiceContractResolverRegistryImpl() {
    }

    public ServiceContractResolverRegistryImpl(Bus b) {
        this.setBus(b);
    }

    public final void setBus(Bus b) {
        this.bus = b;
        if (this.bus != null) {
            this.bus.setExtension(this, ServiceContractResolverRegistry.class);
        }
    }

    @Override
    public URI getContractLocation(QName qname) {
        for (ServiceContractResolver resolver2 : this.resolvers) {
            URI contractLocation = resolver2.getContractLocation(qname);
            if (null == contractLocation) continue;
            return contractLocation;
        }
        return null;
    }

    @Override
    public boolean isRegistered(ServiceContractResolver resolver2) {
        return this.resolvers.contains(resolver2);
    }

    @Override
    public synchronized void register(ServiceContractResolver resolver2) {
        this.resolvers.add(resolver2);
    }

    @Override
    public synchronized void unregister(ServiceContractResolver resolver2) {
        this.resolvers.remove(resolver2);
    }

    protected List<ServiceContractResolver> getResolvers() {
        return this.resolvers;
    }
}

