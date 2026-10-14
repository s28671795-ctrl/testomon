/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.EndpointResolver;
import org.apache.cxf.endpoint.EndpointResolverRegistry;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

@NoJSR250Annotations
public class EndpointResolverRegistryImpl
implements EndpointResolverRegistry {
    private Bus bus;
    private List<EndpointResolver> resolvers = new CopyOnWriteArrayList<EndpointResolver>();

    public EndpointResolverRegistryImpl() {
    }

    public EndpointResolverRegistryImpl(Bus b) {
        this.setBus(b);
    }

    public final void setBus(Bus b) {
        this.bus = b;
        if (this.bus != null) {
            this.bus.setExtension(this, EndpointResolverRegistry.class);
        }
    }

    @Override
    public synchronized void register(EndpointResolver resolver2) {
        this.resolvers.add(resolver2);
    }

    @Override
    public synchronized void unregister(EndpointResolver resolver2) {
        this.resolvers.remove(resolver2);
    }

    @Override
    public synchronized EndpointReferenceType resolve(EndpointReferenceType logical) {
        EndpointResolver resolver2;
        EndpointReferenceType physical = null;
        Iterator<EndpointResolver> iterator2 = this.resolvers.iterator();
        while (iterator2.hasNext() && (physical = (resolver2 = iterator2.next()).resolve(logical)) == null) {
        }
        return physical;
    }

    @Override
    public EndpointReferenceType renew(EndpointReferenceType logical, EndpointReferenceType physical) {
        EndpointResolver resolver2;
        EndpointReferenceType fresh = null;
        Iterator<EndpointResolver> iterator2 = this.resolvers.iterator();
        while (iterator2.hasNext() && (fresh = (resolver2 = iterator2.next()).renew(logical, physical)) == null) {
        }
        return fresh;
    }

    @Override
    public EndpointReferenceType mint(QName serviceName) {
        EndpointResolver resolver2;
        EndpointReferenceType logical = null;
        Iterator<EndpointResolver> iterator2 = this.resolvers.iterator();
        while (iterator2.hasNext() && (logical = (resolver2 = iterator2.next()).mint(serviceName)) == null) {
        }
        return logical;
    }

    @Override
    public EndpointReferenceType mint(EndpointReferenceType physical) {
        EndpointResolver resolver2;
        EndpointReferenceType logical = null;
        Iterator<EndpointResolver> iterator2 = this.resolvers.iterator();
        while (iterator2.hasNext() && (logical = (resolver2 = iterator2.next()).mint(physical)) == null) {
        }
        return logical;
    }

    protected List<EndpointResolver> getResolvers() {
        return this.resolvers;
    }
}

