/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.List;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

public interface InterceptorProvider {
    public List<Interceptor<? extends Message>> getInInterceptors();

    public List<Interceptor<? extends Message>> getOutInterceptors();

    public List<Interceptor<? extends Message>> getInFaultInterceptors();

    public List<Interceptor<? extends Message>> getOutFaultInterceptors();
}

