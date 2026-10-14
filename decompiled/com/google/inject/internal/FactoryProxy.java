/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.MoreObjects;
import com.google.inject.Key;
import com.google.inject.internal.CreationListener;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.Dependency;

final class FactoryProxy<T>
implements InternalFactory<T>,
CreationListener {
    private final InjectorImpl injector;
    private final Key<T> key;
    private final Key<? extends T> targetKey;
    private final Object source;
    private InternalFactory<? extends T> targetFactory;

    FactoryProxy(InjectorImpl injector, Key<T> key, Key<? extends T> targetKey, Object source2) {
        this.injector = injector;
        this.key = key;
        this.targetKey = targetKey;
        this.source = source2;
    }

    @Override
    public void notify(Errors errors2) {
        try {
            this.targetFactory = this.injector.getInternalFactory(this.targetKey, errors2.withSource(this.source), InjectorImpl.JitLimitation.NEW_OR_EXISTING_JIT);
        }
        catch (ErrorsException e) {
            errors2.merge(e.getErrors());
        }
    }

    @Override
    public T get(InternalContext context, Dependency<?> dependency, boolean linked) throws InternalProvisionException {
        Key<? extends T> localTargetKey = this.targetKey;
        try {
            return this.targetFactory.get(context, dependency, true);
        }
        catch (InternalProvisionException ipe) {
            throw ipe.addSource(localTargetKey);
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(FactoryProxy.class).add("key", this.key).add("provider", this.targetFactory).toString();
    }
}

