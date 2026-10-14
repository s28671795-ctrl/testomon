/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.classmate.util;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.util.ResolvedTypeCache;
import com.fasterxml.classmate.util.ResolvedTypeKey;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentTypeCache
extends ResolvedTypeCache {
    private static final long serialVersionUID = 1L;
    protected final int _maxEntries;
    protected final transient ConcurrentHashMap<ResolvedTypeKey, ResolvedType> _map;

    public ConcurrentTypeCache(int maxEntries) {
        this._map = new ConcurrentHashMap(maxEntries, 0.8f, 4);
        this._maxEntries = maxEntries;
    }

    Object readResolve() {
        return new ConcurrentTypeCache(this._maxEntries);
    }

    @Override
    public ResolvedType find(ResolvedTypeKey key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed");
        }
        return this._map.get(key);
    }

    @Override
    public int size() {
        return this._map.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void put(ResolvedTypeKey key, ResolvedType type) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed");
        }
        if (this._map.size() >= this._maxEntries) {
            ConcurrentTypeCache concurrentTypeCache = this;
            synchronized (concurrentTypeCache) {
                if (this._map.size() >= this._maxEntries) {
                    this._map.clear();
                }
            }
        }
        this._map.put(key, type);
    }
}

