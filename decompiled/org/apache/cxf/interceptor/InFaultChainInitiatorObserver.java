/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.Collection;
import java.util.SortedSet;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractFaultChainInitiatorObserver;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.phase.PhaseManager;

public class InFaultChainInitiatorObserver
extends AbstractFaultChainInitiatorObserver {
    public InFaultChainInitiatorObserver(Bus bus) {
        super(bus);
    }

    @Override
    protected void initializeInterceptors(Exchange ex, PhaseInterceptorChain chain) {
        Endpoint e = ex.getEndpoint();
        Client c = ex.get(Client.class);
        InterceptorProvider ip2 = ex.get(InterceptorProvider.class);
        chain.add(this.getBus().getInFaultInterceptors());
        if (c != null) {
            chain.add(c.getInFaultInterceptors());
        } else if (ip2 != null) {
            chain.add(ip2.getInFaultInterceptors());
        }
        chain.add(e.getService().getInFaultInterceptors());
        chain.add(e.getInFaultInterceptors());
        chain.add(e.getBinding().getInFaultInterceptors());
        if (e.getService().getDataBinding() instanceof InterceptorProvider) {
            chain.add(((InterceptorProvider)((Object)e.getService().getDataBinding())).getInFaultInterceptors());
        }
        this.addToChain(chain, ex.getInFaultMessage());
        this.addToChain(chain, ex.getOutMessage());
    }

    private void addToChain(PhaseInterceptorChain chain, Message m4) {
        Collection<Interceptor<? extends Message>> is;
        Collection<InterceptorProvider> providers = CastUtils.cast((Collection)m4.get(Message.INTERCEPTOR_PROVIDERS));
        if (providers != null) {
            for (InterceptorProvider p : providers) {
                chain.add(p.getInFaultInterceptors());
            }
        }
        if ((is = CastUtils.cast((Collection)m4.get(Message.FAULT_IN_INTERCEPTORS))) != null) {
            chain.add(is);
        }
    }

    @Override
    protected SortedSet<Phase> getPhases() {
        return this.getBus().getExtension(PhaseManager.class).getInPhases();
    }

    @Override
    protected boolean isOutboundObserver() {
        return false;
    }
}

