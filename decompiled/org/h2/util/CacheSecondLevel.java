/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayList;
import java.util.Map;
import org.h2.util.Cache;
import org.h2.util.CacheObject;

class CacheSecondLevel
implements Cache {
    private final Cache baseCache;
    private final Map<Integer, CacheObject> map;

    CacheSecondLevel(Cache cache, Map<Integer, CacheObject> map) {
        this.baseCache = cache;
        this.map = map;
    }

    @Override
    public void clear() {
        this.map.clear();
        this.baseCache.clear();
    }

    @Override
    public CacheObject find(int n) {
        CacheObject cacheObject = this.baseCache.find(n);
        if (cacheObject == null) {
            cacheObject = this.map.get(n);
        }
        return cacheObject;
    }

    @Override
    public CacheObject get(int n) {
        CacheObject cacheObject = this.baseCache.get(n);
        if (cacheObject == null) {
            cacheObject = this.map.get(n);
        }
        return cacheObject;
    }

    @Override
    public ArrayList<CacheObject> getAllChanged() {
        return this.baseCache.getAllChanged();
    }

    @Override
    public int getMaxMemory() {
        return this.baseCache.getMaxMemory();
    }

    @Override
    public int getMemory() {
        return this.baseCache.getMemory();
    }

    @Override
    public void put(CacheObject cacheObject) {
        this.baseCache.put(cacheObject);
        this.map.put(cacheObject.getPos(), cacheObject);
    }

    @Override
    public boolean remove(int n) {
        boolean bl = this.baseCache.remove(n);
        return bl |= this.map.remove(n) != null;
    }

    @Override
    public void setMaxMemory(int n) {
        this.baseCache.setMaxMemory(n);
    }

    @Override
    public CacheObject update(int n, CacheObject cacheObject) {
        CacheObject cacheObject2 = this.baseCache.update(n, cacheObject);
        this.map.put(n, cacheObject);
        return cacheObject2;
    }
}

