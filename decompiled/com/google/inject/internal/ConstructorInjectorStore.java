/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.internal.ConstructionProxyFactory;
import com.google.inject.internal.ConstructorInjector;
import com.google.inject.internal.DefaultConstructionProxyFactory;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.FailableCache;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.MembersInjectorImpl;
import com.google.inject.internal.MethodAspect;
import com.google.inject.internal.ProxyFactory;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InterceptorBinding;

final class ConstructorInjectorStore {
    private final InjectorImpl injector;
    private final FailableCache<InjectionPoint, ConstructorInjector<?>> cache = new FailableCache<InjectionPoint, ConstructorInjector<?>>(){

        @Override
        protected ConstructorInjector<?> create(InjectionPoint constructorInjector, Errors errors2) throws ErrorsException {
            return ConstructorInjectorStore.this.createConstructor(constructorInjector, errors2);
        }
    };

    ConstructorInjectorStore(InjectorImpl injector) {
        this.injector = injector;
    }

    boolean isLoading(InjectionPoint ip2) {
        return this.cache.isLoading(ip2);
    }

    public ConstructorInjector<?> get(InjectionPoint constructorInjector, Errors errors2) throws ErrorsException {
        return this.cache.get(constructorInjector, errors2);
    }

    boolean remove(InjectionPoint ip2) {
        return this.cache.remove(ip2);
    }

    private <T> ConstructorInjector<T> createConstructor(InjectionPoint injectionPoint, Errors errors2) throws ErrorsException {
        int numErrorsBefore = errors2.size();
        SingleParameterInjector<?>[] constructorParameterInjectors = this.injector.getParametersInjectors(injectionPoint.getDependencies(), errors2);
        MembersInjectorImpl<?> membersInjector = this.injector.membersInjectorStore.get(injectionPoint.getDeclaringType(), errors2);
        ConstructionProxyFactory factory2 = null;
        if (InternalFlags.isBytecodeGenEnabled()) {
            ImmutableList<InterceptorBinding> injectorBindings = this.injector.getBindingData().getInterceptorBindings();
            ImmutableCollection methodAspects = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll((Iterable)Lists.transform(injectorBindings, MethodAspect::fromBinding))).addAll(membersInjector.getAddedAspects())).build();
            factory2 = new ProxyFactory(injectionPoint, methodAspects);
        } else {
            factory2 = new DefaultConstructionProxyFactory(injectionPoint);
        }
        errors2.throwIfNewErrors(numErrorsBefore);
        return new ConstructorInjector(membersInjector.getInjectionPoints(), factory2.create(), constructorParameterInjectors, membersInjector);
    }
}

