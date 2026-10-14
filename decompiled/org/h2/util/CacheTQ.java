/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayList;
import org.h2.util.Cache;
import org.h2.util.CacheLRU;
import org.h2.util.CacheObject;
import org.h2.util.CacheWriter;
import org.h2.util.SmallLRUCache;

public class CacheTQ
implements Cache {
    static final String TYPE_NAME = "TQ";
    private final Cache lru;
    private final Cache fifo;
    private final SmallLRUCache<Integer, Object> recentlyUsed = SmallLRUCache.newInstance(1024);
    private int lastUsed = -1;
    private int maxMemory;

    CacheTQ(CacheWriter cacheWriter, int n) {
        this.maxMemory = n;
        this.lru = new CacheLRU(cacheWriter, (int)((double)n * 0.8), false);
        this.fifo = new CacheLRU(cacheWriter, (int)((double)n * 0.2), true);
        this.setMaxMemory(4 * n);
    }

    @Override
    public void clear() {
        this.lru.clear();
        this.fifo.clear();
        this.recentlyUsed.clear();
        this.lastUsed = -1;
    }

    @Override
    public CacheObject find(int n) {
        CacheObject cacheObject = this.lru.find(n);
        if (cacheObject == null) {
            cacheObject = this.fifo.find(n);
        }
        return cacheObject;
    }

    @Override
    public CacheObject get(int n) {
        CacheObject cacheObject = this.lru.find(n);
        if (cacheObject != null) {
            return cacheObject;
        }
        cacheObject = this.fifo.find(n);
        if (cacheObject != null && !cacheObject.isStream()) {
            if (this.recentlyUsed.get(n) != null) {
                if (this.lastUsed != n) {
                    this.fifo.remove(n);
                    this.lru.put(cacheObject);
                }
            } else {
                this.recentlyUsed.put(n, this);
            }
            this.lastUsed = n;
        }
        return cacheObject;
    }

    @Override
    public ArrayList<CacheObject> getAllChanged() {
        ArrayList<CacheObject> arrayList = this.lru.getAllChanged();
        ArrayList<CacheObject> arrayList2 = this.fifo.getAllChanged();
        ArrayList<CacheObject> arrayList3 = new ArrayList<CacheObject>(arrayList.size() + arrayList2.size());
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        return arrayList3;
    }

    @Override
    public int getMaxMemory() {
        return this.maxMemory;
    }

    @Override
    public int getMemory() {
        return this.lru.getMemory() + this.fifo.getMemory();
    }

    @Override
    public void put(CacheObject cacheObject) {
        if (cacheObject.isStream()) {
            this.fifo.put(cacheObject);
        } else if (this.recentlyUsed.get(cacheObject.getPos()) != null) {
            this.lru.put(cacheObject);
        } else {
            this.fifo.put(cacheObject);
            this.lastUsed = cacheObject.getPos();
        }
    }

    @Override
    public boolean remove(int n) {
        boolean bl = this.lru.remove(n);
        if (!bl) {
            bl = this.fifo.remove(n);
        }
        this.recentlyUsed.remove(n);
        return bl;
    }

    @Override
    public void setMaxMemory(int n) {
        this.maxMemory = n;
        this.lru.setMaxMemory((int)((double)n * 0.8));
        this.fifo.setMaxMemory((int)((double)n * 0.2));
        this.recentlyUsed.setMaxSize(4 * n);
    }

    @Override
    public CacheObject update(int n, CacheObject cacheObject) {
        if (this.lru.find(n) != null) {
            return this.lru.update(n, cacheObject);
        }
        return this.fifo.update(n, cacheObject);
    }
}

