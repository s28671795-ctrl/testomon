/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;

public abstract class AbstractAttributedInterceptorProvider
extends ConcurrentHashMap<String, Object>
implements InterceptorProvider {
    private static final long serialVersionUID = -1915876045710441978L;
    private List<Interceptor<? extends Message>> in = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> out = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> outFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> inFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();

    @Override
    public Object put(String s2, Object o) {
        if (o == null) {
            return super.remove(s2);
        }
        return super.put(s2, o);
    }

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
        this.in = interceptors;
    }

    public void setInFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.inFault = interceptors;
    }

    public void setOutInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.out = interceptors;
    }

    public void setOutFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.outFault = interceptors;
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

