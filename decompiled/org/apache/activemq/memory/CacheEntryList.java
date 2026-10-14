/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import org.apache.activemq.memory.CacheEntry;
import org.apache.activemq.memory.CacheEvictor;

public class CacheEntryList {
    public final CacheEntry tail;

    public CacheEntryList() {
        this.tail.next = this.tail = new CacheEntry(null, null);
        this.tail.previous = this.tail;
    }

    public void add(CacheEntry ce) {
        this.addEntryBefore(this.tail, ce);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addEntryBefore(CacheEntry position, CacheEntry ce) {
        assert (ce.key != null && ce.next == null && ce.owner == null);
        CacheEntry cacheEntry = this.tail;
        synchronized (cacheEntry) {
            ce.owner = this;
            ce.next = position;
            ce.previous = position.previous;
            ce.previous.next = ce;
            ce.next.previous = ce;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        CacheEntry cacheEntry = this.tail;
        synchronized (cacheEntry) {
            this.tail.next = this.tail;
            this.tail.previous = this.tail;
        }
    }

    public CacheEvictor createFIFOCacheEvictor() {
        return new CacheEvictor(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public CacheEntry evictCacheEntry() {
                CacheEntry rc;
                CacheEntry cacheEntry = CacheEntryList.this.tail;
                synchronized (cacheEntry) {
                    rc = CacheEntryList.this.tail.next;
                }
                return rc.remove() ? rc : null;
            }
        };
    }

    public CacheEvictor createLIFOCacheEvictor() {
        return new CacheEvictor(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public CacheEntry evictCacheEntry() {
                CacheEntry rc;
                CacheEntry cacheEntry = CacheEntryList.this.tail;
                synchronized (cacheEntry) {
                    rc = CacheEntryList.this.tail.previous;
                }
                return rc.remove() ? rc : null;
            }
        };
    }
}

