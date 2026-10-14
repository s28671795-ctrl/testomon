/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.collections4.iterators;

import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.Unmodifiable;

public final class UnmodifiableOrderedMapIterator<K, V>
implements OrderedMapIterator<K, V>,
Unmodifiable {
    private final OrderedMapIterator<? extends K, ? extends V> iterator;

    public static <K, V> OrderedMapIterator<K, V> unmodifiableOrderedMapIterator(OrderedMapIterator<K, ? extends V> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("OrderedMapIterator must not be null");
        }
        if (iterator2 instanceof Unmodifiable) {
            OrderedMapIterator<K, ? extends V> tmpIterator = iterator2;
            return tmpIterator;
        }
        return new UnmodifiableOrderedMapIterator<K, V>(iterator2);
    }

    private UnmodifiableOrderedMapIterator(OrderedMapIterator<K, ? extends V> iterator2) {
        this.iterator = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public K next() {
        return this.iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return this.iterator.hasPrevious();
    }

    @Override
    public K previous() {
        return this.iterator.previous();
    }

    @Override
    public K getKey() {
        return this.iterator.getKey();
    }

    @Override
    public V getValue() {
        return this.iterator.getValue();
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("setValue() is not supported");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }
}

