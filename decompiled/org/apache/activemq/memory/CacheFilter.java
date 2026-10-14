/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import org.apache.activemq.memory.Cache;

public class CacheFilter
implements Cache {
    protected final Cache next;

    public CacheFilter(Cache next) {
        this.next = next;
    }

    @Override
    public Object put(Object key, Object value) {
        return this.next.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return this.next.get(key);
    }

    @Override
    public Object remove(Object key) {
        return this.next.remove(key);
    }

    @Override
    public void close() {
        this.next.close();
    }

    @Override
    public int size() {
        return this.next.size();
    }
}

