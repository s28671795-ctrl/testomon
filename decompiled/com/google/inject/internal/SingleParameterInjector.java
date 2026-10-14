/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.Dependency;

final class SingleParameterInjector<T> {
    private static final Object[] NO_ARGUMENTS = new Object[0];
    private final Dependency<T> dependency;
    private final Object source;
    private final InternalFactory<? extends T> factory;

    SingleParameterInjector(Dependency<T> dependency, BindingImpl<? extends T> binding) {
        this.dependency = dependency;
        this.source = binding.getSource();
        this.factory = binding.getInternalFactory();
    }

    T inject(InternalContext context) throws InternalProvisionException {
        Dependency<T> localDependency = this.dependency;
        try {
            return this.factory.get(context, localDependency, false);
        }
        catch (InternalProvisionException ipe) {
            throw ipe.addSource(localDependency);
        }
    }

    static Object[] getAll(InternalContext context, SingleParameterInjector<?>[] parameterInjectors) throws InternalProvisionException {
        if (parameterInjectors == null) {
            return NO_ARGUMENTS;
        }
        int size = parameterInjectors.length;
        Object[] parameters = new Object[size];
        for (int i = 0; i < size; ++i) {
            parameters[i] = parameterInjectors[i].inject(context);
        }
        return parameters;
    }
}

