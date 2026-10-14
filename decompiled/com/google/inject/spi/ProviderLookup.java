/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.internal.Errors;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.ProviderWithDependencies;
import com.google.inject.util.Types;
import java.util.Set;

public final class ProviderLookup<T>
implements Element {
    private final Object source;
    private final Dependency<T> dependency;
    private Provider<T> delegate;

    public ProviderLookup(Object source2, Key<T> key) {
        this(source2, Dependency.get(Preconditions.checkNotNull(key, "key")));
    }

    public ProviderLookup(Object source2, Dependency<T> dependency) {
        this.source = Preconditions.checkNotNull(source2, "source");
        this.dependency = Preconditions.checkNotNull(dependency, "dependency");
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public Key<T> getKey() {
        return this.dependency.getKey();
    }

    public Dependency<T> getDependency() {
        return this.dependency;
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public void initializeDelegate(Provider<T> delegate) {
        Preconditions.checkState(this.delegate == null, "delegate already initialized");
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    @Override
    public void applyTo(Binder binder) {
        this.initializeDelegate(binder.withSource(this.getSource()).getProvider(this.dependency));
    }

    public Provider<T> getDelegate() {
        return this.delegate;
    }

    public Provider<T> getProvider() {
        return new ProviderWithDependencies<T>(){

            @Override
            public T get() {
                Provider local = ProviderLookup.this.delegate;
                if (local == null) {
                    throw new IllegalStateException("This Provider cannot be used until the Injector has been created.");
                }
                return local.get();
            }

            @Override
            public Set<Dependency<?>> getDependencies() {
                Key<?> providerKey = ProviderLookup.this.getKey().ofType(Types.providerOf(ProviderLookup.this.getKey().getTypeLiteral().getType()));
                return ImmutableSet.of(Dependency.get(providerKey));
            }

            public String toString() {
                return "Provider<" + ProviderLookup.this.getKey().getTypeLiteral() + ">";
            }
        };
    }

    public String toString() {
        return MoreObjects.toStringHelper(ProviderLookup.class).add("dependency", this.dependency).add("source", Errors.convert(this.source)).toString();
    }

    public boolean equals(Object obj) {
        return obj instanceof ProviderLookup && ((ProviderLookup)obj).dependency.equals(this.dependency) && ((ProviderLookup)obj).source.equals(this.source);
    }

    public int hashCode() {
        return Objects.hashCode(this.dependency, this.source);
    }
}

