/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoftValuesHashMap<K, V>
extends AbstractMap<K, V> {
    private final Map<K, SoftValue<V>> map;
    private final ReferenceQueue<V> queue = new ReferenceQueue();

    public SoftValuesHashMap() {
        this.map = new HashMap<K, SoftValue<V>>();
    }

    private void processQueue() {
        Reference<V> reference;
        while ((reference = this.queue.poll()) != null) {
            SoftValue softValue = (SoftValue)reference;
            Object object = softValue.key;
            this.map.remove(object);
        }
        return;
    }

    @Override
    public V get(Object object) {
        this.processQueue();
        SoftReference softReference = this.map.get(object);
        if (softReference == null) {
            return null;
        }
        return (V)softReference.get();
    }

    @Override
    public V put(K k, V v) {
        this.processQueue();
        SoftValue<V> softValue = this.map.put(k, new SoftValue<V>(v, this.queue, k));
        return softValue == null ? null : (V)softValue.get();
    }

    @Override
    public V remove(Object object) {
        this.processQueue();
        SoftReference softReference = this.map.remove(object);
        return softReference == null ? null : (V)softReference.get();
    }

    @Override
    public void clear() {
        this.processQueue();
        this.map.clear();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private static class SoftValue<T>
    extends SoftReference<T> {
        final Object key;

        public SoftValue(T t2, ReferenceQueue<T> referenceQueue, Object object) {
            super(t2, referenceQueue);
            this.key = object;
        }
    }
}

