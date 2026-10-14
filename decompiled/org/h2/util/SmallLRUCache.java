/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmallLRUCache<K, V>
extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;
    private int size;

    private SmallLRUCache(int n) {
        super(n, 0.75f, true);
        this.size = n;
    }

    public static <K, V> SmallLRUCache<K, V> newInstance(int n) {
        return new SmallLRUCache<K, V>(n);
    }

    public void setMaxSize(int n) {
        this.size = n;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        return this.size() > this.size;
    }
}

