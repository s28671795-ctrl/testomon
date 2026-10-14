/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.PreexistingConduitSelector;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.PhaseChainCache;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.phase.PhaseManager;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.transport.Conduit;

public class OutgoingChainInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(OutgoingChainInterceptor.class);
    private static final String CACHE_INPUT_PROPERTY = "cxf.io.cacheinput";
    private PhaseChainCache chainCache = new PhaseChainCache();

    public OutgoingChainInterceptor() {
        super("post-invoke");
    }

    @Override
    public void handleMessage(Message message) {
        Exchange ex = message.getExchange();
        BindingOperationInfo binding = ex.getBindingOperationInfo();
        if (message.getExchange().get(CACHE_INPUT_PROPERTY) == null) {
            message.put(CACHE_INPUT_PROPERTY, Boolean.TRUE);
        }
        if (null != binding && null != binding.getOperationInfo() && binding.getOperationInfo().isOneWay()) {
            this.closeInput(message);
            return;
        }
        Message out = ex.getOutMessage();
        if (out != null) {
            InterceptorChain outChain;
            try {
                OutgoingChainInterceptor.getBackChannelConduit(message);
            }
            catch (IOException ioe) {
                throw new Fault(ioe);
            }
            if (binding != null) {
                out.put(MessageInfo.class, binding.getOperationInfo().getOutput());
                out.put(BindingMessageInfo.class, binding.getOutput());
            }
            if ((outChain = out.getInterceptorChain()) == null) {
                outChain = OutgoingChainInterceptor.getChain(ex, this.chainCache);
                out.setInterceptorChain(outChain);
            } else if (outChain.getState() == InterceptorChain.State.PAUSED) {
                outChain.resume();
                return;
            }
            outChain.doIntercept(out);
        }
    }

    private void closeInput(Message message) {
        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            try {
                is.close();
                message.removeContent(InputStream.class);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    protected static Conduit getBackChannelConduit(Message message) throws IOException {
        Conduit conduit = null;
        Exchange ex = message.getExchange();
        if (ex.getConduit(message) == null && ex.getDestination() != null) {
            try {
                conduit = ex.getDestination().getBackChannel(ex.getInMessage());
                ex.put(ConduitSelector.class, new PreexistingConduitSelector(conduit, ex.getEndpoint()));
            }
            catch (IOException e) {
                throw new Fault(e);
            }
        }
        return conduit;
    }

    public static InterceptorChain getOutInterceptorChain(Exchange ex) {
        Bus bus = ex.getBus();
        Binding binding = ex.getBinding();
        PhaseManager pm = bus.getExtension(PhaseManager.class);
        PhaseInterceptorChain chain = new PhaseInterceptorChain(pm.getOutPhases());
        Endpoint ep = ex.getEndpoint();
        List<Interceptor<? extends Message>> il = ep.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by endpoint: " + il);
        }
        chain.add(il);
        il = ep.getService().getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by service: " + il);
        }
        chain.add(il);
        il = bus.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by bus: " + il);
        }
        chain.add(il);
        if (binding != null) {
            il = binding.getOutInterceptors();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Interceptors contributed by binding: " + il);
            }
            chain.add(il);
        }
        OutgoingChainInterceptor.modifyChain(chain, ex);
        chain.setFaultObserver(ep.getOutFaultObserver());
        return chain;
    }

    private static void modifyChain(PhaseInterceptorChain chain, Exchange ex) {
        OutgoingChainInterceptor.modifyChain(chain, ex.getInMessage());
        OutgoingChainInterceptor.modifyChain(chain, ex.getOutMessage());
    }

    private static void modifyChain(PhaseInterceptorChain chain, Message m4) {
        Collection<Interceptor<? extends Message>> is;
        if (m4 == null) {
            return;
        }
        Collection<InterceptorProvider> providers = CastUtils.cast((Collection)m4.get(Message.INTERCEPTOR_PROVIDERS));
        if (providers != null) {
            for (InterceptorProvider p : providers) {
                chain.add(p.getOutInterceptors());
            }
        }
        if ((is = CastUtils.cast((Collection)m4.get(Message.OUT_INTERCEPTORS))) != null) {
            chain.add(is);
        }
        if (m4.getDestination() instanceof InterceptorProvider) {
            chain.add(((InterceptorProvider)((Object)m4.getDestination())).getOutInterceptors());
        }
    }

    private static PhaseInterceptorChain getChain(Exchange ex, PhaseChainCache chainCache) {
        Bus bus = ex.getBus();
        Binding binding = ex.getBinding();
        Endpoint ep = ex.getEndpoint();
        List<Interceptor<? extends Message>> i1 = bus.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by bus: " + i1);
        }
        List<Interceptor<? extends Message>> i2 = ep.getService().getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by service: " + i2);
        }
        List<Interceptor<? extends Message>> i3 = ep.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by endpoint: " + i3);
        }
        List<Interceptor<? extends Message>> i4 = null;
        if (binding != null) {
            i4 = binding.getOutInterceptors();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Interceptors contributed by binding: " + i4);
            }
        }
        List<Interceptor<? extends Message>> i5 = null;
        if (ep.getService().getDataBinding() instanceof InterceptorProvider) {
            i5 = ((InterceptorProvider)((Object)ep.getService().getDataBinding())).getOutInterceptors();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Interceptors contributed by databinding: " + i5);
            }
            if (i4 == null) {
                i4 = i5;
                i5 = null;
            }
        }
        PhaseInterceptorChain chain = i5 != null ? chainCache.get(bus.getExtension(PhaseManager.class).getOutPhases(), i1, i2, i3, i4, i5) : (i4 != null ? chainCache.get(bus.getExtension(PhaseManager.class).getOutPhases(), i1, i2, i3, i4) : chainCache.get(bus.getExtension(PhaseManager.class).getOutPhases(), i1, i2, i3));
        OutgoingChainInterceptor.modifyChain(chain, ex);
        chain.setFaultObserver(ep.getOutFaultObserver());
        return chain;
    }
}

