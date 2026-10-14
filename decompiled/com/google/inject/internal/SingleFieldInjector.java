/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.SingleMemberInjector;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.Field;

final class SingleFieldInjector
implements SingleMemberInjector {
    final Field field;
    final InjectionPoint injectionPoint;
    final Dependency<?> dependency;
    final BindingImpl<?> binding;

    public SingleFieldInjector(InjectorImpl injector, InjectionPoint injectionPoint, Errors errors2) throws ErrorsException {
        this.injectionPoint = injectionPoint;
        this.field = (Field)injectionPoint.getMember();
        this.dependency = injectionPoint.getDependencies().get(0);
        this.field.setAccessible(true);
        this.binding = injector.getBindingOrThrow(this.dependency.getKey(), errors2, InjectorImpl.JitLimitation.NO_JIT);
    }

    @Override
    public InjectionPoint getInjectionPoint() {
        return this.injectionPoint;
    }

    @Override
    public void inject(InternalContext context, Object o) throws InternalProvisionException {
        try {
            Object value = this.binding.getInternalFactory().get(context, this.dependency, false);
            this.field.set(o, value);
        }
        catch (InternalProvisionException e) {
            throw e.addSource(this.dependency);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
    }
}

