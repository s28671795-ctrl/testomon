/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.spi.Element;
import java.util.Set;

public interface OptionalBinderBinding<T> {
    public Key<T> getKey();

    public Set<Key<?>> getAlternateKeys();

    public Binding<?> getDefaultBinding();

    public Binding<?> getActualBinding();

    public boolean containsElement(Element var1);
}

