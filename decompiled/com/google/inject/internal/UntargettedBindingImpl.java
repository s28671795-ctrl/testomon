/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.Scoping;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Elements;
import com.google.inject.spi.UntargettedBinding;

final class UntargettedBindingImpl<T>
extends BindingImpl<T>
implements UntargettedBinding<T> {
    UntargettedBindingImpl(InjectorImpl injector, Key<T> key, Object source2) {
        super(injector, key, source2, new InternalFactory<T>(){

            @Override
            public T get(InternalContext context, Dependency<?> dependency, boolean linked) {
                throw new AssertionError();
            }
        }, Scoping.UNSCOPED);
    }

    public UntargettedBindingImpl(Object source2, Key<T> key, Scoping scoping) {
        super(source2, key, scoping);
    }

    @Override
    public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
        return visitor.visit(this);
    }

    @Override
    public BindingImpl<T> withScoping(Scoping scoping) {
        return new UntargettedBindingImpl(this.getSource(), this.getKey(), scoping);
    }

    @Override
    public BindingImpl<T> withKey(Key<T> key) {
        return new UntargettedBindingImpl<T>(this.getSource(), key, this.getScoping());
    }

    @Override
    public void applyTo(Binder binder) {
        this.getScoping().applyTo(Elements.withTrustedSource(GuiceInternal.GUICE_INTERNAL, binder, this.getSource()).bind(this.getKey()));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(UntargettedBinding.class).add("key", this.getKey()).add("source", this.getSource()).toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof UntargettedBindingImpl) {
            UntargettedBindingImpl o = (UntargettedBindingImpl)obj;
            return this.getKey().equals(o.getKey()) && this.getScoping().equals(o.getScoping());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.getKey(), this.getScoping());
    }
}

