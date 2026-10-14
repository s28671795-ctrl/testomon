/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.Element;
import java.util.List;
import java.util.Set;

public interface MultibinderBinding<T> {
    public Key<T> getSetKey();

    public Set<Key<?>> getAlternateSetKeys();

    public TypeLiteral<?> getElementTypeLiteral();

    public List<Binding<?>> getElements();

    public boolean permitsDuplicates();

    public boolean containsElement(Element var1);
}

