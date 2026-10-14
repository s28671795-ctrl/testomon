/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.Provider;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;

final class ProviderToInternalFactoryAdapter<T>
implements Provider<T> {
    private final InjectorImpl injector;
    private final InternalFactory<? extends T> internalFactory;

    public ProviderToInternalFactoryAdapter(InjectorImpl injector, InternalFactory<? extends T> internalFactory) {
        this.injector = injector;
        this.internalFactory = internalFactory;
    }

    @Override
    public T get() {
        try (InternalContext context = this.injector.enterContext();){
            T t2;
            T t3 = t2 = this.internalFactory.get(context, context.getDependency(), true);
            return t3;
        }
    }

    InjectorImpl getInjector() {
        return this.injector;
    }

    public String toString() {
        return this.internalFactory.toString();
    }
}

