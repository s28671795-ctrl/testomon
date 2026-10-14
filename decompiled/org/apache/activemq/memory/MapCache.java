/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.activemq.memory.Cache;

public class MapCache
implements Cache {
    protected final Map<Object, Object> map;

    public MapCache() {
        this(new ConcurrentHashMap<Object, Object>());
    }

    public MapCache(Map<Object, Object> map) {
        this.map = map;
    }

    @Override
    public Object put(Object key, Object value) {
        return this.map.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void close() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }
}

