/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.DelayedInitialize;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProviderInstanceBindingImpl;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.internal.Scoping;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.HasDependencies;

final class InternalProviderInstanceBindingImpl<T>
extends ProviderInstanceBindingImpl<T>
implements DelayedInitialize {
    private final Factory<T> originalFactory;

    InternalProviderInstanceBindingImpl(InjectorImpl injector, Key<T> key, Object source2, Factory<T> originalFactory, InternalFactory<? extends T> scopedFactory, Scoping scoping) {
        super(injector, key, source2, scopedFactory, scoping, originalFactory, ImmutableSet.of());
        this.originalFactory = originalFactory;
    }

    InitializationTiming getInitializationTiming() {
        return ((Factory)this.originalFactory).initializationTiming;
    }

    @Override
    public void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
        ((Factory)this.originalFactory).source = this.getSource();
        this.originalFactory.provisionCallback = injector.provisionListenerStore.get(this);
        ((Factory)this.originalFactory).delegateProvider = this.getProvider();
        this.originalFactory.initialize(injector, errors2);
    }

    static abstract class CyclicFactory<T>
    extends Factory<T> {
        CyclicFactory(InitializationTiming initializationTiming) {
            super(initializationTiming);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public final T get(final InternalContext context, final Dependency<?> dependency, boolean linked) throws InternalProvisionException {
            final ConstructionContext constructionContext = context.getConstructionContext(this);
            if (constructionContext.isConstructing()) {
                Class<?> expectedType = dependency.getKey().getTypeLiteral().getRawType();
                Object proxyType = constructionContext.createProxy(context.getInjectorOptions(), expectedType);
                return (T)proxyType;
            }
            constructionContext.startConstruction();
            try {
                if (this.provisionCallback == null) {
                    Object t2 = this.provision(dependency, context, constructionContext);
                    return t2;
                }
                Object t3 = this.provisionCallback.provision(context, new ProvisionListenerStackCallback.ProvisionCallback<T>(){

                    @Override
                    public T call() throws InternalProvisionException {
                        return this.provision(dependency, context, constructionContext);
                    }
                });
                return t3;
            }
            finally {
                constructionContext.removeCurrentReference();
                constructionContext.finishConstruction();
            }
        }

        private T provision(Dependency<?> dependency, InternalContext context, ConstructionContext<T> constructionContext) throws InternalProvisionException {
            try {
                Object t2 = this.doProvision(context, dependency);
                constructionContext.setProxyDelegates(t2);
                return t2;
            }
            catch (InternalProvisionException ipe) {
                throw ipe.addSource(this.getSource());
            }
            catch (Throwable t3) {
                throw InternalProvisionException.errorInProvider(t3).addSource(this.getSource());
            }
        }
    }

    static abstract class Factory<T>
    implements InternalFactory<T>,
    Provider<T>,
    HasDependencies {
        private final InitializationTiming initializationTiming;
        private Object source;
        private Provider<T> delegateProvider;
        ProvisionListenerStackCallback<T> provisionCallback;

        Factory(InitializationTiming initializationTiming) {
            this.initializationTiming = initializationTiming;
        }

        final Object getSource() {
            return this.source;
        }

        abstract void initialize(InjectorImpl var1, Errors var2) throws ErrorsException;

        @Override
        public final T get() {
            Provider<T> local = this.delegateProvider;
            if (local == null) {
                throw new IllegalStateException("This Provider cannot be used until the Injector has been created.");
            }
            return local.get();
        }

        @Override
        public T get(final InternalContext context, final Dependency<?> dependency, boolean linked) throws InternalProvisionException {
            if (this.provisionCallback == null) {
                return this.doProvision(context, dependency);
            }
            return this.provisionCallback.provision(context, new ProvisionListenerStackCallback.ProvisionCallback<T>(){

                @Override
                public T call() throws InternalProvisionException {
                    return this.doProvision(context, dependency);
                }
            });
        }

        protected abstract T doProvision(InternalContext var1, Dependency<?> var2) throws InternalProvisionException;
    }

    static enum InitializationTiming {
        EAGER,
        DELAYED;

    }
}

