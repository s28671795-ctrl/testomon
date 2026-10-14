/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.Key;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.DelayedInitialize;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProviderInternalFactory;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.spi.Dependency;
import jakarta.inject.Provider;

class ProvidedByInternalFactory<T>
extends ProviderInternalFactory<T>
implements DelayedInitialize {
    private final Class<?> rawType;
    private final Class<? extends Provider<?>> providerType;
    private final Key<? extends Provider<T>> providerKey;
    private BindingImpl<? extends Provider<T>> providerBinding;
    private ProvisionListenerStackCallback<T> provisionCallback;

    ProvidedByInternalFactory(Class<?> rawType, Class<? extends Provider<?>> providerType, Key<? extends Provider<T>> providerKey) {
        super(providerKey);
        this.rawType = rawType;
        this.providerType = providerType;
        this.providerKey = providerKey;
    }

    void setProvisionListenerCallback(ProvisionListenerStackCallback<T> listener) {
        this.provisionCallback = listener;
    }

    @Override
    public void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
        this.providerBinding = injector.getBindingOrThrow(this.providerKey, errors2, InjectorImpl.JitLimitation.NEW_OR_EXISTING_JIT);
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        BindingImpl<Provider<T>> localProviderBinding = this.providerBinding;
        if (localProviderBinding == null) {
            throw new IllegalStateException("not initialized");
        }
        Key<? extends Provider<T>> localProviderKey = this.providerKey;
        try {
            Provider<T> provider = localProviderBinding.getInternalFactory().get(context, dependency, true);
            return this.circularGet(provider, context, dependency, this.provisionCallback);
        }
        catch (InternalProvisionException ipe) {
            throw ipe.addSource(localProviderKey);
        }
    }

    @Override
    protected T provision(Provider<? extends T> provider, Dependency<?> dependency, ConstructionContext<T> constructionContext) throws InternalProvisionException {
        try {
            T o = super.provision(provider, dependency, constructionContext);
            if (o != null && !this.rawType.isInstance(o)) {
                throw InternalProvisionException.subtypeNotProvided(this.providerType, this.rawType);
            }
            T t2 = o;
            return t2;
        }
        catch (RuntimeException e) {
            throw InternalProvisionException.errorInProvider(e).addSource(this.source);
        }
    }
}

