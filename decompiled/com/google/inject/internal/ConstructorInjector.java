/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.ConstructionProxy;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.MembersInjectorImpl;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import javax.annotation.Nullable;

final class ConstructorInjector<T> {
    private final ImmutableSet<InjectionPoint> injectableMembers;
    private final SingleParameterInjector<?>[] parameterInjectors;
    private final ConstructionProxy<T> constructionProxy;
    private final MembersInjectorImpl<T> membersInjector;

    ConstructorInjector(Set<InjectionPoint> injectableMembers, ConstructionProxy<T> constructionProxy, SingleParameterInjector<?>[] parameterInjectors, MembersInjectorImpl<T> membersInjector) {
        this.injectableMembers = ImmutableSet.copyOf(injectableMembers);
        this.constructionProxy = constructionProxy;
        this.parameterInjectors = parameterInjectors;
        this.membersInjector = membersInjector;
    }

    public ImmutableSet<InjectionPoint> getInjectableMembers() {
        return this.injectableMembers;
    }

    ConstructionProxy<T> getConstructionProxy() {
        return this.constructionProxy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object construct(final InternalContext context, Dependency<?> dependency, @Nullable ProvisionListenerStackCallback<T> provisionCallback) throws InternalProvisionException {
        final ConstructionContext constructionContext = context.getConstructionContext(this);
        if (constructionContext.isConstructing()) {
            return constructionContext.createProxy(context.getInjectorOptions(), dependency.getKey().getTypeLiteral().getRawType());
        }
        Object t2 = constructionContext.getCurrentReference();
        if (t2 != null) {
            if (context.getInjectorOptions().disableCircularProxies) {
                throw InternalProvisionException.circularDependenciesDisabled(dependency.getKey().getTypeLiteral().getRawType());
            }
            return t2;
        }
        constructionContext.startConstruction();
        try {
            if (provisionCallback == null) {
                Object t3 = this.provision(context, constructionContext);
                return t3;
            }
            T t4 = provisionCallback.provision(context, new ProvisionListenerStackCallback.ProvisionCallback<T>(){

                @Override
                public T call() throws InternalProvisionException {
                    return ConstructorInjector.this.provision(context, constructionContext);
                }
            });
            return t4;
        }
        finally {
            constructionContext.finishConstruction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private T provision(InternalContext context, ConstructionContext<T> constructionContext) throws InternalProvisionException {
        try {
            T t2;
            try {
                Object[] parameters = SingleParameterInjector.getAll(context, this.parameterInjectors);
                t2 = this.constructionProxy.newInstance(parameters);
                constructionContext.setProxyDelegates(t2);
            }
            finally {
                constructionContext.finishConstruction();
            }
            constructionContext.setCurrentReference(t2);
            MembersInjectorImpl<T> localMembersInjector = this.membersInjector;
            localMembersInjector.injectMembers(t2, context, false);
            localMembersInjector.notifyListeners(t2);
            T t3 = t2;
            return t3;
        }
        catch (InvocationTargetException userException) {
            Throwable cause = userException.getCause() != null ? userException.getCause() : userException;
            throw InternalProvisionException.errorInjectingConstructor(cause).addSource(this.constructionProxy.getInjectionPoint());
        }
        finally {
            constructionContext.removeCurrentReference();
        }
    }
}

