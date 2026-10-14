/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.classmate.util;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.util.ResolvedTypeCache;
import com.fasterxml.classmate.util.ResolvedTypeKey;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUTypeCache
extends ResolvedTypeCache {
    private static final long serialVersionUID = 1L;
    protected final int _maxEntries;
    protected final transient CacheMap _map;

    public LRUTypeCache(int maxEntries) {
        this._map = new CacheMap(maxEntries);
        this._maxEntries = maxEntries;
    }

    Object readResolve() {
        return new LRUTypeCache(this._maxEntries);
    }

    @Override
    public synchronized ResolvedType find(ResolvedTypeKey key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed");
        }
        return (ResolvedType)this._map.get(key);
    }

    @Override
    public synchronized int size() {
        return this._map.size();
    }

    @Override
    public synchronized void put(ResolvedTypeKey key, ResolvedType type) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed");
        }
        this._map.put(key, type);
    }

    private static final class CacheMap
    extends LinkedHashMap<ResolvedTypeKey, ResolvedType> {
        protected final int _maxEntries;

        public CacheMap(int maxEntries) {
            this._maxEntries = maxEntries;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<ResolvedTypeKey, ResolvedType> eldest) {
            return this.size() > this._maxEntries;
        }
    }
}

