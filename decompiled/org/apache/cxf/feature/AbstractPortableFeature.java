/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;

public interface AbstractPortableFeature
extends Feature {
    @Override
    default public void initialize(Server server, Bus bus) {
        this.doInitializeProvider(server.getEndpoint(), bus);
    }

    @Override
    default public void initialize(Client client, Bus bus) {
        this.doInitializeProvider(client, bus);
    }

    @Override
    default public void initialize(InterceptorProvider interceptorProvider, Bus bus) {
        this.doInitializeProvider(interceptorProvider, bus);
    }

    @Override
    default public void initialize(Bus bus) {
        this.doInitializeProvider(bus, bus);
    }

    default public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
    }

    public static <T> T getActive(List<? extends Feature> features, Class<T> type) {
        T active = null;
        if (features != null) {
            for (Feature feature : features) {
                if (!type.isInstance(feature)) continue;
                active = type.cast(feature);
                break;
            }
        }
        return active;
    }
}

