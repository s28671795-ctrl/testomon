/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding;

import java.util.Collection;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.ChainInitiationObserver;
import org.apache.cxf.transport.Destination;

public abstract class AbstractBindingFactory
implements BindingFactory {
    protected Collection<String> activationNamespaces;
    protected Bus bus;

    public AbstractBindingFactory() {
    }

    public AbstractBindingFactory(Collection<String> ns) {
        this.activationNamespaces = ns;
    }

    public AbstractBindingFactory(Bus b) {
        this.bus = b;
        this.registerWithBindingManager();
    }

    public AbstractBindingFactory(Bus b, Collection<String> ns) {
        this.activationNamespaces = ns;
        this.bus = b;
        this.registerWithBindingManager();
    }

    private void registerWithBindingManager() {
        if (this.bus != null && this.activationNamespaces != null) {
            BindingFactoryManager manager = this.bus.getExtension(BindingFactoryManager.class);
            for (String ns : this.activationNamespaces) {
                manager.registerBindingFactory(ns, this);
            }
        }
    }

    public BindingInfo createBindingInfo(ServiceInfo service2, String namespace, Object config) {
        return new BindingInfo(service2, namespace);
    }

    @Override
    public BindingInfo createBindingInfo(Service service2, String namespace, Object config) {
        BindingInfo bi = this.createBindingInfo(service2.getServiceInfos().get(0), namespace, config);
        if (bi.getName() == null) {
            bi.setName(new QName(service2.getName().getNamespaceURI(), service2.getName().getLocalPart() + "Binding"));
        }
        return bi;
    }

    @Override
    public void addListener(Destination d, Endpoint e) {
        ChainInitiationObserver observer = new ChainInitiationObserver(e, this.bus);
        d.setMessageObserver(observer);
    }

    public Bus getBus() {
        return this.bus;
    }

    @Resource
    public void setBus(Bus bus) {
        if (this.bus != bus) {
            this.bus = bus;
            this.registerWithBindingManager();
        }
    }

    public Collection<String> getActivationNamespaces() {
        return this.activationNamespaces;
    }

    public void setActivationNamespaces(Collection<String> activationNamespaces) {
        this.activationNamespaces = activationNamespaces;
        this.registerWithBindingManager();
    }
}

