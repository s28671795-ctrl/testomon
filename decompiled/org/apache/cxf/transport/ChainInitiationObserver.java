/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseChainCache;
import org.apache.cxf.phase.PhaseManager;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.MessageObserver;

public class ChainInitiationObserver
implements MessageObserver {
    protected Endpoint endpoint;
    protected Bus bus;
    protected ClassLoader loader;
    private PhaseChainCache chainCache = new PhaseChainCache();

    public ChainInitiationObserver(Endpoint endpoint, Bus bus) {
        this.endpoint = endpoint;
        this.bus = bus;
        if (bus != null) {
            this.loader = bus.getExtension(ClassLoader.class);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onMessage(Message m4) {
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        ClassLoaderUtils.ClassLoaderHolder origLoader = null;
        try {
            Message message;
            Exchange exchange;
            if (this.loader != null) {
                origLoader = ClassLoaderUtils.setThreadContextClassloader(this.loader);
            }
            InterceptorChain phaseChain = null;
            if (m4.getInterceptorChain() != null) {
                InterceptorChain interceptorChain = phaseChain = m4.getInterceptorChain();
                synchronized (interceptorChain) {
                    if (phaseChain.getState() == InterceptorChain.State.PAUSED || phaseChain.getState() == InterceptorChain.State.SUSPENDED) {
                        phaseChain.resume();
                        return;
                    }
                }
            }
            if ((exchange = (message = this.getBinding().createMessage(m4)).getExchange()) == null) {
                exchange = new ExchangeImpl();
                m4.setExchange(exchange);
            }
            exchange.setInMessage(message);
            this.setExchangeProperties(exchange, message);
            InterceptorProvider dbp = null;
            if (this.endpoint.getService().getDataBinding() instanceof InterceptorProvider) {
                dbp = (InterceptorProvider)((Object)this.endpoint.getService().getDataBinding());
            }
            phaseChain = dbp == null ? this.chainCache.get(this.bus.getExtension(PhaseManager.class).getInPhases(), this.bus.getInInterceptors(), this.endpoint.getService().getInInterceptors(), this.endpoint.getInInterceptors(), this.getBinding().getInInterceptors()) : this.chainCache.get(this.bus.getExtension(PhaseManager.class).getInPhases(), this.bus.getInInterceptors(), this.endpoint.getService().getInInterceptors(), this.endpoint.getInInterceptors(), this.getBinding().getInInterceptors(), dbp.getInInterceptors());
            message.setInterceptorChain(phaseChain);
            phaseChain.setFaultObserver(this.endpoint.getOutFaultObserver());
            this.addToChain(phaseChain, message);
            phaseChain.doIntercept(message);
            return;
        }
        finally {
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
            if (origLoader != null) {
                origLoader.reset();
            }
        }
    }

    private void addToChain(InterceptorChain chain, Message m4) {
        Collection<Interceptor<? extends Message>> is;
        Collection<InterceptorProvider> providers = CastUtils.cast((Collection)m4.get(Message.INTERCEPTOR_PROVIDERS));
        if (providers != null) {
            for (InterceptorProvider p : providers) {
                chain.add(p.getInInterceptors());
            }
        }
        if ((is = CastUtils.cast((Collection)m4.get(Message.IN_INTERCEPTORS))) != null) {
            chain.add(is);
        }
        if (m4.getDestination() instanceof InterceptorProvider) {
            chain.add(((InterceptorProvider)((Object)m4.getDestination())).getInInterceptors());
        }
    }

    protected Binding getBinding() {
        return this.endpoint.getBinding();
    }

    protected void setExchangeProperties(Exchange exchange, Message m4) {
        exchange.put(Endpoint.class, this.endpoint);
        exchange.put(Binding.class, this.getBinding());
        exchange.put(Bus.class, this.bus);
        if (exchange.getDestination() == null) {
            exchange.setDestination(m4.getDestination());
        }
        if (this.endpoint != null && this.endpoint.getService() != null) {
            exchange.put(Service.class, this.endpoint.getService());
            EndpointInfo endpointInfo = this.endpoint.getEndpointInfo();
            if (endpointInfo.getService() != null) {
                QName serviceQName = endpointInfo.getService().getName();
                exchange.put("javax.xml.ws.wsdl.service", serviceQName);
                QName interfaceQName = endpointInfo.getService().getInterface().getName();
                exchange.put("javax.xml.ws.wsdl.interface", interfaceQName);
                QName portQName = endpointInfo.getName();
                exchange.put("javax.xml.ws.wsdl.port", portQName);
                URI wsdlDescription = endpointInfo.getProperty("URI", URI.class);
                if (wsdlDescription == null && !endpointInfo.hasProperty("URI")) {
                    String address = endpointInfo.getAddress();
                    try {
                        wsdlDescription = new URI(address + "?wsdl");
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        // empty catch block
                    }
                    endpointInfo.setProperty("URI", wsdlDescription);
                }
                exchange.put("javax.xml.ws.wsdl.description", wsdlDescription);
            }
        } else {
            exchange.put(Service.class, null);
        }
    }

    public Endpoint getEndpoint() {
        return this.endpoint;
    }
}

