/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.interceptors;

import java.util.Set;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;

public abstract class AbstractEndpointSelectionInterceptor
extends AbstractPhaseInterceptor<Message> {
    public AbstractEndpointSelectionInterceptor(String phase) {
        super(phase);
    }

    public AbstractEndpointSelectionInterceptor(String id, String phase) {
        super(id, phase);
    }

    @Override
    public void handleMessage(Message message) {
        Exchange ex = message.getExchange();
        Set<Endpoint> endpoints = CastUtils.cast((Set)ex.get("multipleEndpointObserver.endpoints"));
        Endpoint ep = this.selectEndpoint(message, endpoints);
        if (ep == null) {
            return;
        }
        ex.put(Endpoint.class, ep);
        ex.put(Binding.class, ep.getBinding());
        ex.put(Service.class, ep.getService());
        InterceptorChain chain = message.getInterceptorChain();
        chain.add(ep.getInInterceptors());
        chain.add(ep.getBinding().getInInterceptors());
        chain.add(ep.getService().getInInterceptors());
        chain.setFaultObserver(ep.getOutFaultObserver());
    }

    protected abstract Endpoint selectEndpoint(Message var1, Set<Endpoint> var2);
}

