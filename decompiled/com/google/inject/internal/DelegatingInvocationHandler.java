/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DelegatingInvocationHandler<T>
implements InvocationHandler {
    private volatile boolean initialized;
    private T delegate;

    DelegatingInvocationHandler() {
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        try {
            Preconditions.checkState(this.initialized, "This is a proxy used to support circular references. The object we're proxying is not constructed yet. Please wait until after injection has completed to use this object.");
            Preconditions.checkNotNull(this.delegate, "This is a proxy used to support circular references. The object we're  proxying is initialized to null. No methods can be called.");
            return method2.invoke(this.delegate, args2);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    void setDelegate(T delegate) {
        this.delegate = delegate;
        this.initialized = true;
    }
}

