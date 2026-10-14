/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.Initializable;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProviderInternalFactory;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.spi.Dependency;
import jakarta.inject.Provider;

final class InternalFactoryToInitializableAdapter<T>
extends ProviderInternalFactory<T> {
    private final ProvisionListenerStackCallback<T> provisionCallback;
    private final Initializable<? extends Provider<? extends T>> initializable;

    public InternalFactoryToInitializableAdapter(Initializable<? extends Provider<? extends T>> initializable, Object source2, ProvisionListenerStackCallback<T> provisionCallback) {
        super(source2);
        this.provisionCallback = provisionCallback;
        this.initializable = Preconditions.checkNotNull(initializable, "provider");
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        return this.circularGet(this.initializable.get(), context, dependency, this.provisionCallback);
    }

    @Override
    protected T provision(Provider<? extends T> provider, Dependency<?> dependency, ConstructionContext<T> constructionContext) throws InternalProvisionException {
        try {
            return super.provision(provider, dependency, constructionContext);
        }
        catch (RuntimeException userException) {
            throw InternalProvisionException.errorInProvider(userException).addSource(this.source);
        }
    }

    public String toString() {
        return this.initializable.toString();
    }
}

