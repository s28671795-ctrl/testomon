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

public class OutFaultChainInitiatorObserver
extends AbstractFaultChainInitiatorObserver {
    public OutFaultChainInitiatorObserver(Bus bus) {
        super(bus);
    }

    @Override
    protected void initializeInterceptors(Exchange ex, PhaseInterceptorChain chain) {
        Endpoint e = ex.getEndpoint();
        Client c = ex.get(Client.class);
        chain.add(this.getBus().getOutFaultInterceptors());
        if (c != null) {
            chain.add(c.getOutFaultInterceptors());
        }
        chain.add(e.getService().getOutFaultInterceptors());
        chain.add(e.getOutFaultInterceptors());
        chain.add(e.getBinding().getOutFaultInterceptors());
        if (e.getService().getDataBinding() instanceof InterceptorProvider) {
            chain.add(((InterceptorProvider)((Object)e.getService().getDataBinding())).getOutFaultInterceptors());
        }
        this.addToChain(chain, ex.getInMessage());
        this.addToChain(chain, ex.getOutFaultMessage());
    }

    private void addToChain(PhaseInterceptorChain chain, Message m4) {
        Collection<Interceptor<? extends Message>> is;
        Collection<InterceptorProvider> providers = CastUtils.cast((Collection)m4.get(Message.INTERCEPTOR_PROVIDERS));
        if (providers != null) {
            for (InterceptorProvider p : providers) {
                chain.add(p.getOutFaultInterceptors());
            }
        }
        if ((is = CastUtils.cast((Collection)m4.get(Message.FAULT_OUT_INTERCEPTORS))) != null) {
            chain.add(is);
        }
        if (m4.getDestination() instanceof InterceptorProvider) {
            chain.add(((InterceptorProvider)((Object)m4.getDestination())).getOutFaultInterceptors());
        }
    }

    @Override
    protected SortedSet<Phase> getPhases() {
        return this.getBus().getExtension(PhaseManager.class).getOutPhases();
    }

    @Override
    protected boolean isOutboundObserver() {
        return true;
    }
}

