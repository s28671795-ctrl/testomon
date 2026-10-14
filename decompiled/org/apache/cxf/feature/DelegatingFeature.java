/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.interceptor.InterceptorProvider;

public class DelegatingFeature<T extends AbstractPortableFeature>
extends AbstractFeature {
    protected T delegate;

    protected DelegatingFeature(T d) {
        this.delegate = d == null ? this.getDelegate() : d;
    }

    protected T getDelegate() {
        return this.delegate;
    }

    public void setDelegate(T delegate) {
        this.delegate = delegate;
    }

    @Override
    public void initialize(Server server, Bus bus) {
        this.delegate.initialize(server, bus);
    }

    @Override
    public void initialize(Client client, Bus bus) {
        this.delegate.initialize(client, bus);
    }

    @Override
    public void initialize(InterceptorProvider interceptorProvider, Bus bus) {
        this.delegate.initialize(interceptorProvider, bus);
    }

    @Override
    public void initialize(Bus bus) {
        this.delegate.initialize(bus);
    }

    @Override
    protected void initializeProvider(InterceptorProvider interceptorProvider, Bus bus) {
        this.delegate.doInitializeProvider(interceptorProvider, bus);
    }
}

