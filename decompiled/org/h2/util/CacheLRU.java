/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayList;
import java.util.Collections;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.util.Cache;
import org.h2.util.CacheHead;
import org.h2.util.CacheObject;
import org.h2.util.CacheSecondLevel;
import org.h2.util.CacheTQ;
import org.h2.util.CacheWriter;
import org.h2.util.MathUtils;
import org.h2.util.SoftValuesHashMap;

public class CacheLRU
implements Cache {
    static final String TYPE_NAME = "LRU";
    private final CacheWriter writer;
    private final boolean fifo;
    private final CacheObject head = new CacheHead();
    private final int mask;
    private CacheObject[] values;
    private int recordCount;
    private final int len;
    private long maxMemory;
    private long memory;

    CacheLRU(CacheWriter cacheWriter, int n, boolean bl) {
        this.writer = cacheWriter;
        this.fifo = bl;
        this.setMaxMemory(n);
        try {
            long l = this.maxMemory / 64L;
            if (l > Integer.MAX_VALUE) {
                throw new IllegalArgumentException();
            }
            this.len = MathUtils.nextPowerOf2((int)l);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalStateException("This much cache memory is not supported: " + n + "kb", illegalArgumentException);
        }
        this.mask = this.len - 1;
        this.clear();
    }

    public static Cache getCache(CacheWriter cacheWriter, String string, int n) {
        Cache cache;
        SoftValuesHashMap<Integer, CacheObject> softValuesHashMap = null;
        if (string.startsWith("SOFT_")) {
            softValuesHashMap = new SoftValuesHashMap<Integer, CacheObject>();
            string = string.substring("SOFT_".length());
        }
        if (TYPE_NAME.equals(string)) {
            cache = new CacheLRU(cacheWriter, n, false);
        } else if ("TQ".equals(string)) {
            cache = new CacheTQ(cacheWriter, n);
        } else {
            throw DbException.getInvalidValueException("CACHE_TYPE", string);
        }
        if (softValuesHashMap != null) {
            cache = new CacheSecondLevel(cache, softValuesHashMap);
        }
        return cache;
    }

    @Override
    public void clear() {
        this.head.cacheNext = this.head.cachePrevious = this.head;
        this.values = null;
        this.values = new CacheObject[this.len];
        this.recordCount = 0;
        this.memory = (long)this.len * 8L;
    }

    @Override
    public void put(CacheObject cacheObject) {
        int n;
        CacheObject cacheObject2;
        if (SysProperties.CHECK && (cacheObject2 = this.find(n = cacheObject.getPos())) != null) {
            throw DbException.getInternalError("try to add a record twice at pos " + n);
        }
        n = cacheObject.getPos() & this.mask;
        cacheObject.cacheChained = this.values[n];
        this.values[n] = cacheObject;
        ++this.recordCount;
        this.memory += (long)cacheObject.getMemory();
        this.addToFront(cacheObject);
        this.removeOldIfRequired();
    }

    @Override
    public CacheObject update(int n, CacheObject cacheObject) {
        CacheObject cacheObject2 = this.find(n);
        if (cacheObject2 == null) {
            this.put(cacheObject);
        } else {
            if (cacheObject2 != cacheObject) {
                throw DbException.getInternalError("old!=record pos:" + n + " old:" + cacheObject2 + " new:" + cacheObject);
            }
            if (!this.fifo) {
                this.removeFromLinkedList(cacheObject);
                this.addToFront(cacheObject);
            }
        }
        return cacheObject2;
    }

    private void removeOldIfRequired() {
        if (this.memory >= this.maxMemory) {
            this.removeOld();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeOld() {
        int n = 0;
        ArrayList<CacheObject> arrayList = new ArrayList<CacheObject>();
        long l = this.memory;
        int n2 = this.recordCount;
        boolean bl = false;
        CacheObject cacheObject = this.head.cacheNext;
        while (n2 > 16 && !(arrayList.isEmpty() ? l <= this.maxMemory : l * 4L <= this.maxMemory * 3L)) {
            CacheObject cacheObject2 = cacheObject;
            cacheObject = cacheObject2.cacheNext;
            if (++n >= this.recordCount) {
                if (!bl) {
                    this.writer.flushLog();
                    bl = true;
                    n = 0;
                } else {
                    this.writer.getTrace().info("cannot remove records, cache size too small? records:" + this.recordCount + " memory:" + this.memory);
                    break;
                }
            }
            if (cacheObject2 == this.head) {
                throw DbException.getInternalError("try to remove head");
            }
            if (!cacheObject2.canRemove()) {
                this.removeFromLinkedList(cacheObject2);
                this.addToFront(cacheObject2);
                continue;
            }
            --n2;
            l -= (long)cacheObject2.getMemory();
            if (cacheObject2.isChanged()) {
                arrayList.add(cacheObject2);
                continue;
            }
            this.remove(cacheObject2.getPos());
        }
        if (!arrayList.isEmpty()) {
            CacheObject cacheObject3;
            if (!bl) {
                this.writer.flushLog();
            }
            Collections.sort(arrayList);
            long l2 = this.maxMemory;
            int n3 = arrayList.size();
            try {
                this.maxMemory = Long.MAX_VALUE;
                for (n = 0; n < n3; ++n) {
                    cacheObject3 = (CacheObject)arrayList.get(n);
                    this.writer.writeBack(cacheObject3);
                }
            }
            finally {
                this.maxMemory = l2;
            }
            for (n = 0; n < n3; ++n) {
                cacheObject3 = (CacheObject)arrayList.get(n);
                this.remove(cacheObject3.getPos());
                if (cacheObject3.cacheNext == null) continue;
                throw DbException.getInternalError();
            }
        }
    }

    private void addToFront(CacheObject cacheObject) {
        if (cacheObject == this.head) {
            throw DbException.getInternalError("try to move head");
        }
        cacheObject.cacheNext = this.head;
        cacheObject.cachePrevious = this.head.cachePrevious;
        cacheObject.cachePrevious.cacheNext = cacheObject;
        this.head.cachePrevious = cacheObject;
    }

    private void removeFromLinkedList(CacheObject cacheObject) {
        if (cacheObject == this.head) {
            throw DbException.getInternalError("try to remove head");
        }
        cacheObject.cachePrevious.cacheNext = cacheObject.cacheNext;
        cacheObject.cacheNext.cachePrevious = cacheObject.cachePrevious;
        cacheObject.cacheNext = null;
        cacheObject.cachePrevious = null;
    }

    @Override
    public boolean remove(int n) {
        CacheObject cacheObject;
        int n2 = n & this.mask;
        CacheObject cacheObject2 = this.values[n2];
        if (cacheObject2 == null) {
            return false;
        }
        if (cacheObject2.getPos() == n) {
            this.values[n2] = cacheObject2.cacheChained;
        } else {
            do {
                cacheObject = cacheObject2;
                cacheObject2 = cacheObject2.cacheChained;
                if (cacheObject2 != null) continue;
                return false;
            } while (cacheObject2.getPos() != n);
            cacheObject.cacheChained = cacheObject2.cacheChained;
        }
        --this.recordCount;
        this.memory -= (long)cacheObject2.getMemory();
        this.removeFromLinkedList(cacheObject2);
        if (SysProperties.CHECK) {
            cacheObject2.cacheChained = null;
            cacheObject = this.find(n);
            if (cacheObject != null) {
                throw DbException.getInternalError("not removed: " + cacheObject);
            }
        }
        return true;
    }

    @Override
    public CacheObject find(int n) {
        CacheObject cacheObject = this.values[n & this.mask];
        while (cacheObject != null && cacheObject.getPos() != n) {
            cacheObject = cacheObject.cacheChained;
        }
        return cacheObject;
    }

    @Override
    public CacheObject get(int n) {
        CacheObject cacheObject = this.find(n);
        if (cacheObject != null && !this.fifo) {
            this.removeFromLinkedList(cacheObject);
            this.addToFront(cacheObject);
        }
        return cacheObject;
    }

    @Override
    public ArrayList<CacheObject> getAllChanged() {
        ArrayList<CacheObject> arrayList = new ArrayList<CacheObject>();
        CacheObject cacheObject = this.head.cacheNext;
        while (cacheObject != this.head) {
            if (cacheObject.isChanged()) {
                arrayList.add(cacheObject);
            }
            cacheObject = cacheObject.cacheNext;
        }
        return arrayList;
    }

    @Override
    public void setMaxMemory(int n) {
        long l = (long)n * 1024L / 4L;
        this.maxMemory = l < 0L ? 0L : l;
        this.removeOldIfRequired();
    }

    @Override
    public int getMaxMemory() {
        return (int)(this.maxMemory * 4L / 1024L);
    }

    @Override
    public int getMemory() {
        return (int)(this.memory * 4L / 1024L);
    }
}

