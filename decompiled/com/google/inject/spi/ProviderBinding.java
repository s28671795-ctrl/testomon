/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.Provider;

public interface ProviderBinding<T extends Provider<?>>
extends Binding<T> {
    public Key<?> getProvidedKey();
}

