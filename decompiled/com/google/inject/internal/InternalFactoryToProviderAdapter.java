/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.inject.Provider;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.Dependency;

final class InternalFactoryToProviderAdapter<T>
implements InternalFactory<T> {
    private final Provider<? extends T> provider;
    private final Object source;

    public InternalFactoryToProviderAdapter(Provider<? extends T> provider, Object source2) {
        this.provider = Preconditions.checkNotNull(provider, "provider");
        this.source = Preconditions.checkNotNull(source2, "source");
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        context.setDependency(dependency);
        try {
            T t2 = this.provider.get();
            if (t2 == null && !dependency.isNullable()) {
                InternalProvisionException.onNullInjectedIntoNonNullableDependency(this.source, dependency);
            }
            return t2;
        }
        catch (RuntimeException userException) {
            throw InternalProvisionException.errorInProvider(userException).addSource(this.source);
        }
    }

    public String toString() {
        return this.provider.toString();
    }
}

