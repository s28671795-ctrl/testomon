/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.databinding.AbstractDataBinding;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.interceptor.OneWayProcessorInterceptor;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.URIResolver;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.FactoryBeanListenerManager;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Document;

public abstract class AbstractServiceFactoryBean {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractServiceFactoryBean.class);
    protected boolean dataBindingSet;
    protected List<String> schemaLocations;
    private Bus bus;
    private DataBinding dataBinding;
    private Service service;
    private List<FactoryBeanListener> listeners = new ModCountCopyOnWriteArrayList<FactoryBeanListener>();
    private Map<String, Object> sessionState = new HashMap<String, Object>();

    public abstract Service create();

    public Map<String, Object> getSessionState() {
        return this.sessionState;
    }

    public void sendEvent(FactoryBeanListener.Event ev, Object ... args2) {
        for (FactoryBeanListener l : this.listeners) {
            l.handleEvent(ev, this, args2);
        }
    }

    protected void initializeDefaultInterceptors() {
        this.service.getInInterceptors().add(new ServiceInvokerInterceptor());
        this.service.getInInterceptors().add(new OutgoingChainInterceptor());
        this.service.getInInterceptors().add(new OneWayProcessorInterceptor());
    }

    protected void initializeDataBindings() {
        if (this.getDataBinding() instanceof AbstractDataBinding && this.schemaLocations != null) {
            this.fillDataBindingSchemas();
        }
        this.getDataBinding().initialize(this.getService());
        this.service.setDataBinding(this.getDataBinding());
        this.sendEvent(FactoryBeanListener.Event.DATABINDING_INITIALIZED, this.dataBinding);
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
        FactoryBeanListenerManager m4 = bus.getExtension(FactoryBeanListenerManager.class);
        if (m4 != null) {
            this.listeners.addAll(m4.getListeners());
        }
    }

    public DataBinding getDataBinding() {
        return this.getDataBinding(true);
    }

    public DataBinding getDataBinding(boolean create) {
        if (this.dataBinding == null && create) {
            this.dataBinding = this.createDefaultDataBinding();
        }
        return this.dataBinding;
    }

    protected DataBinding createDefaultDataBinding() {
        return null;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
        this.dataBindingSet = dataBinding != null;
    }

    public Service getService() {
        return this.service;
    }

    protected void setService(Service service2) {
        this.service = service2;
    }

    private void fillDataBindingSchemas() {
        ResourceManager rr = this.getBus().getExtension(ResourceManager.class);
        ArrayList<DOMSource> schemas = new ArrayList<DOMSource>();
        for (String l : this.schemaLocations) {
            Document d;
            URL url2 = rr.resolveResource(l, URL.class);
            if (url2 == null) {
                URIResolver res;
                try {
                    res = new URIResolver(l);
                }
                catch (IOException e) {
                    throw new ServiceConstructionException(new Message("INVALID_SCHEMA_URL", LOG, l), (Throwable)e);
                }
                if (!res.isResolved()) {
                    throw new ServiceConstructionException(new Message("INVALID_SCHEMA_URL", LOG, l));
                }
                url2 = res.getURL();
            }
            try {
                d = StaxUtils.read(url2.openStream());
            }
            catch (Exception e) {
                throw new ServiceConstructionException(new Message("ERROR_READING_SCHEMA", LOG, l), (Throwable)e);
            }
            schemas.add(new DOMSource(d, url2.toString()));
        }
        ((AbstractDataBinding)this.getDataBinding()).setSchemas(schemas);
    }
}

