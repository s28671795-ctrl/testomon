/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

import java.util.concurrent.ConcurrentHashMap;
import org.simpleframework.xml.util.Cache;

public class ConcurrentCache<T>
extends ConcurrentHashMap<Object, T>
implements Cache<T> {
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
}

