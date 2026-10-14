/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.List;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.databinding.AbstractDataBinding;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;

public abstract class AbstractInterceptorProvidingDataBinding
extends AbstractDataBinding
implements InterceptorProvider {
    protected ModCountCopyOnWriteArrayList<Interceptor<? extends Message>> inInterceptors = new ModCountCopyOnWriteArrayList();
    protected ModCountCopyOnWriteArrayList<Interceptor<? extends Message>> outInterceptors = new ModCountCopyOnWriteArrayList();
    protected ModCountCopyOnWriteArrayList<Interceptor<? extends Message>> outFaultInterceptors = new ModCountCopyOnWriteArrayList();
    protected ModCountCopyOnWriteArrayList<Interceptor<? extends Message>> inFaultInterceptors = new ModCountCopyOnWriteArrayList();

    @Override
    public List<Interceptor<? extends Message>> getInInterceptors() {
        return this.inInterceptors;
    }

    @Override
    public List<Interceptor<? extends Message>> getOutInterceptors() {
        return this.outInterceptors;
    }

    @Override
    public List<Interceptor<? extends Message>> getInFaultInterceptors() {
        return this.inFaultInterceptors;
    }

    @Override
    public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        return this.outFaultInterceptors;
    }
}

