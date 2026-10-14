/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.spi.Dependency;
import jakarta.inject.Provider;
import javax.annotation.Nullable;

abstract class ProviderInternalFactory<T>
implements InternalFactory<T> {
    protected final Object source;

    ProviderInternalFactory(Object source2) {
        this.source = Preconditions.checkNotNull(source2, "source");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected T circularGet(final Provider<? extends T> provider, InternalContext context, final Dependency<?> dependency, @Nullable ProvisionListenerStackCallback<T> provisionCallback) throws InternalProvisionException {
        final ConstructionContext constructionContext = context.getConstructionContext(this);
        if (constructionContext.isConstructing()) {
            Class<?> expectedType = dependency.getKey().getTypeLiteral().getRawType();
            Object proxyType = constructionContext.createProxy(context.getInjectorOptions(), expectedType);
            return (T)proxyType;
        }
        constructionContext.startConstruction();
        try {
            if (provisionCallback == null) {
                T t2 = this.provision(provider, dependency, constructionContext);
                return t2;
            }
            T t3 = provisionCallback.provision(context, new ProvisionListenerStackCallback.ProvisionCallback<T>(){

                @Override
                public T call() throws InternalProvisionException {
                    return ProviderInternalFactory.this.provision(provider, dependency, constructionContext);
                }
            });
            return t3;
        }
        finally {
            constructionContext.removeCurrentReference();
            constructionContext.finishConstruction();
        }
    }

    protected T provision(Provider<? extends T> provider, Dependency<?> dependency, ConstructionContext<T> constructionContext) throws InternalProvisionException {
        T t2 = provider.get();
        if (t2 == null && !dependency.isNullable()) {
            InternalProvisionException.onNullInjectedIntoNonNullableDependency(this.source, dependency);
        }
        constructionContext.setProxyDelegates(t2);
        return t2;
    }
}

