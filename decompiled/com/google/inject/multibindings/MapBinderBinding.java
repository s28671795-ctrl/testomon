/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.Element;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MapBinderBinding<T> {
    public Key<T> getMapKey();

    public Set<Key<?>> getAlternateMapKeys();

    public TypeLiteral<?> getKeyTypeLiteral();

    public TypeLiteral<?> getValueTypeLiteral();

    public List<Map.Entry<?, Binding<?>>> getEntries();

    public List<Map.Entry<?, Binding<?>>> getEntries(Iterable<? extends Element> var1);

    public boolean permitsDuplicates();

    public boolean containsElement(Element var1);
}

