/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.phase.PhaseManager;
import org.apache.cxf.transport.MessageObserver;

public class MultipleEndpointObserver
implements MessageObserver {
    public static final String ENDPOINTS = "multipleEndpointObserver.endpoints";
    protected Bus bus;
    protected List<Interceptor<? extends Message>> bindingInterceptors = new CopyOnWriteArrayList<Interceptor<? extends Message>>();
    protected List<Interceptor<? extends Message>> routingInterceptors = new CopyOnWriteArrayList<Interceptor<? extends Message>>();
    private Set<Endpoint> endpoints = new CopyOnWriteArraySet<Endpoint>();
    private ClassLoader loader;

    public MultipleEndpointObserver(Bus bus) {
        this.bus = bus;
        this.loader = bus.getExtension(ClassLoader.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onMessage(Message message) {
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        ClassLoaderUtils.ClassLoaderHolder origLoader = null;
        try {
            Exchange exchange;
            if (this.loader != null) {
                origLoader = ClassLoaderUtils.setThreadContextClassloader(this.loader);
            }
            if ((exchange = message.getExchange()) == null) {
                exchange = new ExchangeImpl();
                exchange.setInMessage(message);
                message.setExchange(exchange);
            }
            message = this.createMessage(message);
            message.setExchange(exchange);
            exchange.setInMessage(message);
            this.setExchangeProperties(exchange, message);
            PhaseInterceptorChain chain = this.createChain();
            message.setInterceptorChain(chain);
            chain.add(this.bus.getInInterceptors());
            if (this.bindingInterceptors != null) {
                chain.add(this.bindingInterceptors);
            }
            if (this.routingInterceptors != null) {
                chain.add(this.routingInterceptors);
            }
            if (this.endpoints != null) {
                exchange.put(ENDPOINTS, this.endpoints);
            }
            chain.doIntercept(message);
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

    protected Message createMessage(Message message) {
        return message;
    }

    protected PhaseInterceptorChain createChain() {
        return new PhaseInterceptorChain(this.bus.getExtension(PhaseManager.class).getInPhases());
    }

    protected void setExchangeProperties(Exchange exchange, Message m4) {
        exchange.put(Bus.class, this.bus);
        if (exchange.getDestination() == null) {
            exchange.setDestination(m4.getDestination());
        }
    }

    public List<Interceptor<? extends Message>> getBindingInterceptors() {
        return this.bindingInterceptors;
    }

    public List<Interceptor<? extends Message>> getRoutingInterceptors() {
        return this.routingInterceptors;
    }

    public Set<Endpoint> getEndpoints() {
        return this.endpoints;
    }
}

