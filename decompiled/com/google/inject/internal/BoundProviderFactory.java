/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.Key;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.CreationListener;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProviderInternalFactory;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.spi.Dependency;
import jakarta.inject.Provider;

final class BoundProviderFactory<T>
extends ProviderInternalFactory<T>
implements CreationListener {
    private final ProvisionListenerStackCallback<T> provisionCallback;
    private final InjectorImpl injector;
    final Key<? extends Provider<? extends T>> providerKey;
    private InternalFactory<? extends Provider<? extends T>> providerFactory;

    BoundProviderFactory(InjectorImpl injector, Key<? extends Provider<? extends T>> providerKey, Object source2, ProvisionListenerStackCallback<T> provisionCallback) {
        super(source2);
        this.provisionCallback = provisionCallback;
        this.injector = injector;
        this.providerKey = providerKey;
    }

    @Override
    public void notify(Errors errors2) {
        try {
            this.providerFactory = this.injector.getInternalFactory(this.providerKey, errors2.withSource(this.source), InjectorImpl.JitLimitation.NEW_OR_EXISTING_JIT);
        }
        catch (ErrorsException e) {
            errors2.merge(e.getErrors());
        }
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        try {
            Provider<? extends T> provider = this.providerFactory.get(context, dependency, true);
            return this.circularGet(provider, context, dependency, this.provisionCallback);
        }
        catch (InternalProvisionException ipe) {
            throw ipe.addSource(this.providerKey);
        }
    }

    @Override
    protected T provision(Provider<? extends T> provider, Dependency<?> dependency, ConstructionContext<T> constructionContext) throws InternalProvisionException {
        try {
            return super.provision(provider, dependency, constructionContext);
        }
        catch (RuntimeException userException) {
            throw InternalProvisionException.errorInProvider(userException);
        }
    }

    public String toString() {
        return this.providerKey.toString();
    }
}

