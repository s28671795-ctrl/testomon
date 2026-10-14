/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.Scoping;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Elements;
import com.google.inject.spi.HasDependencies;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InstanceBinding;
import java.util.Set;

final class InstanceBindingImpl<T>
extends BindingImpl<T>
implements InstanceBinding<T> {
    final T instance;
    final ImmutableSet<InjectionPoint> injectionPoints;

    public InstanceBindingImpl(InjectorImpl injector, Key<T> key, Object source2, InternalFactory<? extends T> internalFactory, Set<InjectionPoint> injectionPoints, T instance) {
        super(injector, key, source2, internalFactory, Scoping.EAGER_SINGLETON);
        this.injectionPoints = ImmutableSet.copyOf(injectionPoints);
        this.instance = instance;
    }

    public InstanceBindingImpl(Object source2, Key<T> key, Scoping scoping, Set<InjectionPoint> injectionPoints, T instance) {
        super(source2, key, scoping);
        this.injectionPoints = ImmutableSet.copyOf(injectionPoints);
        this.instance = instance;
    }

    @Override
    public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
        return visitor.visit(this);
    }

    @Override
    public T getInstance() {
        return this.instance;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return this.injectionPoints;
    }

    @Override
    public Set<Dependency<?>> getDependencies() {
        return this.instance instanceof HasDependencies ? ImmutableSet.copyOf(((HasDependencies)this.instance).getDependencies()) : Dependency.forInjectionPoints(this.injectionPoints);
    }

    @Override
    public BindingImpl<T> withScoping(Scoping scoping) {
        return new InstanceBindingImpl(this.getSource(), this.getKey(), scoping, this.injectionPoints, this.instance);
    }

    @Override
    public BindingImpl<T> withKey(Key<T> key) {
        return new InstanceBindingImpl<T>(this.getSource(), key, this.getScoping(), this.injectionPoints, this.instance);
    }

    @Override
    public void applyTo(Binder binder) {
        Elements.withTrustedSource(GuiceInternal.GUICE_INTERNAL, binder, this.getSource()).bind(this.getKey()).toInstance(this.instance);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(InstanceBinding.class).add("key", this.getKey()).add("source", this.getSource()).add("instance", this.instance).toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof InstanceBindingImpl) {
            InstanceBindingImpl o = (InstanceBindingImpl)obj;
            return this.getKey().equals(o.getKey()) && this.getScoping().equals(o.getScoping()) && Objects.equal(this.instance, o.instance);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.getKey(), this.getScoping());
    }
}

