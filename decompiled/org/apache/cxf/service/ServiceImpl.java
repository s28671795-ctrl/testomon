/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractAttributedInterceptorProvider;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.workqueue.SynchronousExecutor;

@NoJSR250Annotations
public class ServiceImpl
extends AbstractAttributedInterceptorProvider
implements Service,
Configurable {
    private static final long serialVersionUID = -3920605127948701230L;
    private List<ServiceInfo> serviceInfos;
    private DataBinding dataBinding;
    private Executor executor;
    private Invoker invoker;
    private Map<QName, Endpoint> endpoints = new HashMap<QName, Endpoint>();

    public ServiceImpl() {
        this((ServiceInfo)null);
    }

    public ServiceImpl(ServiceInfo si) {
        this.serviceInfos = new ArrayList<ServiceInfo>();
        if (si != null) {
            this.serviceInfos.add(si);
        }
        this.executor = SynchronousExecutor.getInstance();
    }

    public ServiceImpl(List<ServiceInfo> si) {
        this.serviceInfos = si;
        this.executor = SynchronousExecutor.getInstance();
    }

    @Override
    public String getBeanName() {
        return this.getName().toString();
    }

    @Override
    public QName getName() {
        return this.serviceInfos.get(0).getName();
    }

    @Override
    public List<ServiceInfo> getServiceInfos() {
        return this.serviceInfos;
    }

    @Override
    public EndpointInfo getEndpointInfo(QName endpoint) {
        for (ServiceInfo inf : this.serviceInfos) {
            EndpointInfo ef = inf.getEndpoint(endpoint);
            if (ef == null) continue;
            return ef;
        }
        return null;
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public void setExecutor(Executor executor2) {
        this.executor = executor2;
    }

    @Override
    public Invoker getInvoker() {
        return this.invoker;
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public DataBinding getDataBinding() {
        return this.dataBinding;
    }

    @Override
    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    @Override
    public Map<QName, Endpoint> getEndpoints() {
        return this.endpoints;
    }

    public void setEndpoints(Map<QName, Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public void setProperties(Map<String, Object> properties) {
        this.putAll(properties);
    }

    @Override
    public String toString() {
        return "[ServiceImpl " + this.getName() + "]";
    }
}

