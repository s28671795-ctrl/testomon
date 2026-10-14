/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.collections4.map;

import java.util.Comparator;
import java.util.SortedMap;
import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.map.LazyMap;

public class LazySortedMap<K, V>
extends LazyMap<K, V>
implements SortedMap<K, V> {
    private static final long serialVersionUID = 2715322183617658933L;

    public static <K, V> LazySortedMap<K, V> lazySortedMap(SortedMap<K, V> map, Factory<? extends V> factory2) {
        return new LazySortedMap<K, V>(map, factory2);
    }

    public static <K, V> LazySortedMap<K, V> lazySortedMap(SortedMap<K, V> map, Transformer<? super K, ? extends V> factory2) {
        return new LazySortedMap<K, V>(map, factory2);
    }

    protected LazySortedMap(SortedMap<K, V> map, Factory<? extends V> factory2) {
        super(map, factory2);
    }

    protected LazySortedMap(SortedMap<K, V> map, Transformer<? super K, ? extends V> factory2) {
        super(map, factory2);
    }

    protected SortedMap<K, V> getSortedMap() {
        return (SortedMap)this.map;
    }

    @Override
    public K firstKey() {
        return this.getSortedMap().firstKey();
    }

    @Override
    public K lastKey() {
        return this.getSortedMap().lastKey();
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.getSortedMap().comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        SortedMap<K, V> map = this.getSortedMap().subMap(fromKey, toKey);
        return new LazySortedMap<K, V>(map, this.factory);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        SortedMap<K, V> map = this.getSortedMap().headMap(toKey);
        return new LazySortedMap<K, V>(map, this.factory);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        SortedMap<K, V> map = this.getSortedMap().tailMap(fromKey);
        return new LazySortedMap<K, V>(map, this.factory);
    }
}

