/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> Collection<T> diff(Collection<T> c1, Collection<T> c2) {
        if (c1 == null || c1.isEmpty() || c2 == null || c2.isEmpty()) {
            return c1;
        }
        ArrayList<T> difference = new ArrayList<T>();
        for (T item : c1) {
            if (c2.contains(item)) continue;
            difference.add(item);
        }
        return difference;
    }

    public static <T> boolean isEmpty(Collection<T> c) {
        if (c != null && !c.isEmpty()) {
            for (T item : c) {
                if (item == null) continue;
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean isEmpty(Map<K, V> m4) {
        return m4 == null || m4.isEmpty();
    }

    public static <S, T> Dictionary<S, T> singletonDictionary(S s2, T t2) {
        return CollectionUtils.toDictionary(Collections.singletonMap(s2, t2));
    }

    public static <S, T> Dictionary<S, T> toDictionary(Map<S, T> map) {
        return new MapToDictionary<S, T>(map);
    }

    static class MapToDictionary<S, T>
    extends Dictionary<S, T> {
        private final Map<S, T> map;

        MapToDictionary(Map<S, T> map) {
            this.map = map;
        }

        @Override
        public Enumeration<T> elements() {
            return this.map != null ? new IteratorToEnumeration<T>(this.map.values().iterator()) : null;
        }

        @Override
        public T get(Object key) {
            return this.map != null ? (T)this.map.get(key) : null;
        }

        @Override
        public boolean isEmpty() {
            return this.map == null || this.map.isEmpty();
        }

        @Override
        public Enumeration<S> keys() {
            return this.map != null ? new IteratorToEnumeration<S>(this.map.keySet().iterator()) : null;
        }

        @Override
        public T put(S key, T value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public T remove(Object key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.map != null ? this.map.size() : 0;
        }

        static class IteratorToEnumeration<X>
        implements Enumeration<X> {
            private final Iterator<X> iter;

            IteratorToEnumeration(Iterator<X> iter) {
                this.iter = iter;
            }

            @Override
            public boolean hasMoreElements() {
                return this.iter != null && this.iter.hasNext();
            }

            @Override
            public X nextElement() {
                return this.iter != null ? (X)this.iter.next() : null;
            }
        }
    }
}

