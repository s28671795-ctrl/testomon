/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.MessageObserver;

public interface Endpoint
extends Map<String, Object>,
InterceptorProvider {
    public EndpointInfo getEndpointInfo();

    public Binding getBinding();

    public Service getService();

    public void setExecutor(Executor var1);

    public Executor getExecutor();

    public MessageObserver getInFaultObserver();

    public MessageObserver getOutFaultObserver();

    public void setInFaultObserver(MessageObserver var1);

    public void setOutFaultObserver(MessageObserver var1);

    public List<Feature> getActiveFeatures();

    public void addCleanupHook(Closeable var1);

    public List<Closeable> getCleanupHooks();
}

