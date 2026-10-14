/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.xml.namespace.QName;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;

public interface Service
extends Map<String, Object>,
InterceptorProvider {
    public QName getName();

    public List<ServiceInfo> getServiceInfos();

    public EndpointInfo getEndpointInfo(QName var1);

    public DataBinding getDataBinding();

    public void setDataBinding(DataBinding var1);

    public Executor getExecutor();

    public void setExecutor(Executor var1);

    public Invoker getInvoker();

    public void setInvoker(Invoker var1);

    public Map<QName, Endpoint> getEndpoints();
}

