/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;

public class WrappedFeature
extends AbstractFeature {
    final Feature wrapped;

    public WrappedFeature(Feature f) {
        this.wrapped = f;
    }

    @Override
    public void initialize(Server server, Bus bus) {
        this.wrapped.initialize(server, bus);
    }

    @Override
    public void initialize(Client client, Bus bus) {
        this.wrapped.initialize(client, bus);
    }

    @Override
    public void initialize(InterceptorProvider interceptorProvider, Bus bus) {
        this.wrapped.initialize(interceptorProvider, bus);
    }

    @Override
    public void initialize(Bus bus) {
        this.wrapped.initialize(bus);
    }
}

