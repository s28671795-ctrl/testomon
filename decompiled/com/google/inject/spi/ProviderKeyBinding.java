/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.Binding;
import com.google.inject.Key;
import jakarta.inject.Provider;

public interface ProviderKeyBinding<T>
extends Binding<T> {
    public Key<? extends Provider<? extends T>> getProviderKey();
}

