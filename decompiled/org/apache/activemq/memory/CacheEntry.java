/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import org.apache.activemq.memory.CacheEntryList;

public class CacheEntry {
    public final Object key;
    public final Object value;
    public CacheEntry next;
    public CacheEntry previous;
    public CacheEntryList owner;

    public CacheEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean remove() {
        if (this.owner == null || this.key == null || this.next == null) {
            return false;
        }
        CacheEntry cacheEntry = this.owner.tail;
        synchronized (cacheEntry) {
            this.next.previous = this.previous;
            this.previous.next = this.next;
            this.owner = null;
            this.next = null;
            this.previous = null;
        }
        return true;
    }
}

