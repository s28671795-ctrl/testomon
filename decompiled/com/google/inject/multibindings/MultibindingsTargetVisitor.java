/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.multibindings.MapBinderBinding;
import com.google.inject.multibindings.MultibinderBinding;
import com.google.inject.multibindings.OptionalBinderBinding;
import com.google.inject.spi.BindingTargetVisitor;

public interface MultibindingsTargetVisitor<T, V>
extends BindingTargetVisitor<T, V> {
    @Override
    public V visit(MultibinderBinding<? extends T> var1);

    @Override
    public V visit(MapBinderBinding<? extends T> var1);

    @Override
    public V visit(OptionalBinderBinding<? extends T> var1);
}

