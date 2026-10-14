/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DebuggingThreadLocal<T> {
    private final ConcurrentHashMap<Long, T> map = new ConcurrentHashMap();

    public void set(T t2) {
        this.map.put(Thread.currentThread().getId(), t2);
    }

    public void remove() {
        this.map.remove(Thread.currentThread().getId());
    }

    public T get() {
        return this.map.get(Thread.currentThread().getId());
    }

    public HashMap<Long, T> getSnapshotOfAllThreads() {
        return new HashMap<Long, T>(this.map);
    }
}

