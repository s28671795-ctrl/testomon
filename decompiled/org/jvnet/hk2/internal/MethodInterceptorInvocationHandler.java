/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javassist.util.proxy.MethodHandler;

public class MethodInterceptorInvocationHandler
implements InvocationHandler {
    private final MethodHandler interceptor;

    public MethodInterceptorInvocationHandler(MethodHandler interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        return this.interceptor.invoke(proxy, method2, null, args2);
    }
}

