/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

import java.util.LinkedHashMap;
import java.util.Map;
import org.simpleframework.xml.util.Cache;

public class LimitedCache<T>
extends LinkedHashMap<Object, T>
implements Cache<T> {
    private final int capacity;

    public LimitedCache() {
        this(50000);
    }

    public LimitedCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void cache(Object key, T value) {
        this.put(key, value);
    }

    @Override
    public T take(Object key) {
        return (T)this.remove(key);
    }

    @Override
    public T fetch(Object key) {
        return (T)this.get(key);
    }

    @Override
    public boolean contains(Object key) {
        return this.containsKey(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Object, T> entry) {
        return this.size() > this.capacity;
    }
}

