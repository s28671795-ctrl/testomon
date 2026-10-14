/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.DelegatingInvocationHandler;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalProvisionException;
import java.util.ArrayList;
import java.util.List;

final class ConstructionContext<T> {
    T currentReference;
    boolean constructing;
    List<DelegatingInvocationHandler<T>> invocationHandlers;

    ConstructionContext() {
    }

    public T getCurrentReference() {
        return this.currentReference;
    }

    public void removeCurrentReference() {
        this.currentReference = null;
    }

    public void setCurrentReference(T currentReference) {
        this.currentReference = currentReference;
    }

    public boolean isConstructing() {
        return this.constructing;
    }

    public void startConstruction() {
        this.constructing = true;
    }

    public void finishConstruction() {
        this.constructing = false;
        this.invocationHandlers = null;
    }

    public Object createProxy(InjectorImpl.InjectorOptions injectorOptions, Class<?> expectedType) throws InternalProvisionException {
        if (injectorOptions.disableCircularProxies) {
            throw InternalProvisionException.circularDependenciesDisabled(expectedType);
        }
        if (!expectedType.isInterface()) {
            throw InternalProvisionException.cannotProxyClass(expectedType);
        }
        if (this.invocationHandlers == null) {
            this.invocationHandlers = new ArrayList<DelegatingInvocationHandler<T>>();
        }
        DelegatingInvocationHandler invocationHandler = new DelegatingInvocationHandler();
        this.invocationHandlers.add(invocationHandler);
        return BytecodeGen.newCircularProxy(expectedType, invocationHandler);
    }

    public void setProxyDelegates(T delegate) {
        if (this.invocationHandlers != null) {
            for (DelegatingInvocationHandler<T> handler : this.invocationHandlers) {
                handler.setDelegate(delegate);
            }
            this.invocationHandlers = null;
        }
    }
}

