/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractAttributedInterceptorProvider;
import org.apache.cxf.interceptor.ClientFaultConverter;
import org.apache.cxf.interceptor.InFaultChainInitiatorObserver;
import org.apache.cxf.interceptor.MessageSenderInterceptor;
import org.apache.cxf.interceptor.OutFaultChainInitiatorObserver;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.MessageObserver;

public class EndpointImpl
extends AbstractAttributedInterceptorProvider
implements Endpoint,
Configurable {
    private static final long serialVersionUID = -7660560719050162091L;
    private static final Logger LOG = LogUtils.getL7dLogger(EndpointImpl.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private Service service;
    private Binding binding;
    private EndpointInfo endpointInfo;
    private Executor executor;
    private Bus bus;
    private MessageObserver inFaultObserver;
    private MessageObserver outFaultObserver;
    private List<Feature> activeFeatures;
    private List<Closeable> cleanupHooks;

    public EndpointImpl(Bus bus, Service s2, QName endpointName) throws EndpointException {
        this(bus, s2, s2.getEndpointInfo(endpointName));
    }

    public EndpointImpl(Bus bus, Service s2, EndpointInfo ei) throws EndpointException {
        if (ei == null) {
            throw new NullPointerException("EndpointInfo can not be null!");
        }
        this.bus = bus == null ? BusFactory.getThreadDefaultBus() : bus;
        this.service = s2;
        this.endpointInfo = ei;
        this.createBinding(this.endpointInfo.getBinding());
        this.inFaultObserver = new InFaultChainInitiatorObserver(bus);
        this.outFaultObserver = new OutFaultChainInitiatorObserver(bus);
        this.getInFaultInterceptors().add(new ClientFaultConverter());
        this.getOutInterceptors().add(new MessageSenderInterceptor());
        this.getOutFaultInterceptors().add(new MessageSenderInterceptor());
    }

    @Override
    public String getBeanName() {
        return this.endpointInfo.getName().toString() + ".endpoint";
    }

    @Override
    public EndpointInfo getEndpointInfo() {
        return this.endpointInfo;
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public Binding getBinding() {
        return this.binding;
    }

    @Override
    public Executor getExecutor() {
        return this.executor == null ? this.service.getExecutor() : this.executor;
    }

    @Override
    public void setExecutor(Executor e) {
        this.executor = e;
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    final void createBinding(BindingInfo bi) throws EndpointException {
        if (null != bi) {
            String namespace = bi.getBindingId();
            BindingFactory bf = null;
            try {
                bf = this.bus.getExtension(BindingFactoryManager.class).getBindingFactory(namespace);
                if (null == bf) {
                    Message msg = new Message("NO_BINDING_FACTORY", BUNDLE, namespace);
                    throw new EndpointException(msg);
                }
                this.binding = bf.createBinding(bi);
            }
            catch (BusException ex) {
                throw new EndpointException(ex);
            }
        }
    }

    @Override
    public MessageObserver getInFaultObserver() {
        return this.inFaultObserver;
    }

    @Override
    public MessageObserver getOutFaultObserver() {
        return this.outFaultObserver;
    }

    @Override
    public void setInFaultObserver(MessageObserver observer) {
        this.inFaultObserver = observer;
    }

    @Override
    public void setOutFaultObserver(MessageObserver observer) {
        this.outFaultObserver = observer;
    }

    public void setProperties(Map<String, Object> properties) {
        this.putAll(properties);
    }

    @Override
    public List<Feature> getActiveFeatures() {
        return this.activeFeatures;
    }

    public void initializeActiveFeatures(List<? extends Feature> features) {
        this.activeFeatures = CastUtils.cast(features);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EndpointImpl)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.endpointInfo.hashCode();
    }

    @Override
    public synchronized void addCleanupHook(Closeable c) {
        if (this.cleanupHooks == null) {
            this.cleanupHooks = new CopyOnWriteArrayList<Closeable>();
        }
        this.cleanupHooks.add(c);
    }

    @Override
    public List<Closeable> getCleanupHooks() {
        if (this.cleanupHooks == null) {
            return Collections.emptyList();
        }
        return this.cleanupHooks;
    }
}

