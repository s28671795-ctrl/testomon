/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.List;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;

public abstract class AbstractBasicInterceptorProvider
implements InterceptorProvider {
    private List<Interceptor<? extends Message>> in = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> out = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> outFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> inFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();

    @Override
    public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        return this.outFault;
    }

    @Override
    public List<Interceptor<? extends Message>> getInFaultInterceptors() {
        return this.inFault;
    }

    @Override
    public List<Interceptor<? extends Message>> getInInterceptors() {
        return this.in;
    }

    @Override
    public List<Interceptor<? extends Message>> getOutInterceptors() {
        return this.out;
    }

    public void setInInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.in.clear();
        this.in.addAll(interceptors);
    }

    public void setInFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.inFault.clear();
        this.inFault.addAll(interceptors);
    }

    public void setOutInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.out.clear();
        this.out.addAll(interceptors);
    }

    public void setOutFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.outFault.clear();
        this.outFault.addAll(interceptors);
    }
}

