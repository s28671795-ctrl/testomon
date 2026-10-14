/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.stax;

import java.util.LinkedList;
import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.databinding.stax.StaxDataBindingInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

public class StaxDataBindingFeature
extends DelegatingFeature<Portable> {
    public StaxDataBindingFeature() {
        super(new Portable());
    }

    public static class Portable
    implements AbstractPortableFeature {
        @Override
        public void initialize(Client client, Bus bus) {
            this.removeDatabindingInterceptor(client.getEndpoint().getBinding().getInInterceptors());
        }

        @Override
        public void initialize(Server server, Bus bus) {
            this.removeDatabindingInterceptor(server.getEndpoint().getBinding().getInInterceptors());
        }

        private void removeDatabindingInterceptor(List<Interceptor<? extends Message>> inInterceptors) {
            LinkedList<Interceptor<? extends Message>> remove = new LinkedList<Interceptor<? extends Message>>();
            for (Interceptor<? extends Message> i : inInterceptors) {
                if (!(i instanceof AbstractInDatabindingInterceptor)) continue;
                remove.add(i);
            }
            inInterceptors.removeAll(remove);
            inInterceptors.add(new StaxDataBindingInterceptor());
        }
    }
}

