/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.util;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.ProviderWithDependencies;
import java.util.Set;

@CheckReturnValue
public final class Providers {
    private Providers() {
    }

    public static <T> Provider<T> of(T instance) {
        return new ConstantProvider(instance);
    }

    @Deprecated
    public static <T> Provider<T> guicify(Provider<T> provider) {
        return provider;
    }

    public static <T> Provider<T> guicify(jakarta.inject.Provider<T> provider) {
        if (provider instanceof Provider) {
            return (Provider)provider;
        }
        jakarta.inject.Provider<T> delegate = Preconditions.checkNotNull(provider, "provider");
        Set<InjectionPoint> injectionPoints = InjectionPoint.forInstanceMethodsAndFields(provider.getClass());
        if (injectionPoints.isEmpty()) {
            return new GuicifiedJakartaProvider(delegate);
        }
        ImmutableSet dependencies = injectionPoints.stream().flatMap(ip2 -> ip2.getDependencies().stream()).collect(ImmutableSet.toImmutableSet());
        return new GuicifiedJakartaProviderWithDependencies(dependencies, delegate);
    }

    private static final class GuicifiedJakartaProviderWithDependencies<T>
    extends GuicifiedJakartaProvider<T>
    implements ProviderWithDependencies<T> {
        private final Set<Dependency<?>> dependencies;

        private GuicifiedJakartaProviderWithDependencies(Set<Dependency<?>> dependencies, jakarta.inject.Provider<T> delegate) {
            super(delegate);
            this.dependencies = dependencies;
        }

        @Inject
        void initialize(Injector injector) {
            injector.injectMembers(this.delegate);
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.dependencies;
        }
    }

    private static class GuicifiedJakartaProvider<T>
    implements Provider<T> {
        protected final jakarta.inject.Provider<T> delegate;

        private GuicifiedJakartaProvider(jakarta.inject.Provider<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get() {
            return this.delegate.get();
        }

        public String toString() {
            return "guicified(" + this.delegate + ")";
        }

        public boolean equals(Object obj) {
            return obj instanceof GuicifiedJakartaProvider && Objects.equal(this.delegate, ((GuicifiedJakartaProvider)obj).delegate);
        }

        public int hashCode() {
            return Objects.hashCode(this.delegate);
        }
    }

    private static final class ConstantProvider<T>
    implements Provider<T> {
        private final T instance;

        private ConstantProvider(T instance) {
            this.instance = instance;
        }

        @Override
        public T get() {
            return this.instance;
        }

        public String toString() {
            return "of(" + this.instance + ")";
        }

        public boolean equals(Object obj) {
            return obj instanceof ConstantProvider && Objects.equal(this.instance, ((ConstantProvider)obj).instance);
        }

        public int hashCode() {
            return Objects.hashCode(this.instance);
        }
    }
}

