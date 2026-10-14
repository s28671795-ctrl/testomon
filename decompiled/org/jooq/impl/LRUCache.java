/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.LinkedHashMap;
import java.util.Map;

final class LRUCache<K, V>
extends LinkedHashMap<K, V> {
    private final int size;

    LRUCache(int size) {
        super(size, 0.75f, true);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.size;
    }
}

