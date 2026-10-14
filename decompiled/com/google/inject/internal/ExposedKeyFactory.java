/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.Key;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.CreationListener;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.PrivateElements;

final class ExposedKeyFactory<T>
implements InternalFactory<T>,
CreationListener {
    private final Key<T> key;
    private final PrivateElements privateElements;
    private BindingImpl<T> delegate;

    ExposedKeyFactory(Key<T> key, PrivateElements privateElements) {
        this.key = key;
        this.privateElements = privateElements;
    }

    @Override
    public void notify(Errors errors2) {
        InjectorImpl privateInjector = (InjectorImpl)this.privateElements.getInjector();
        BindingImpl<T> explicitBinding = privateInjector.getBindingData().getExplicitBinding(this.key);
        if (explicitBinding.getInternalFactory() == this) {
            errors2.withSource(explicitBinding.getSource()).exposedButNotBound(this.key);
            return;
        }
        this.delegate = explicitBinding;
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        return this.delegate.getInternalFactory().get(context, dependency, linked);
    }
}

