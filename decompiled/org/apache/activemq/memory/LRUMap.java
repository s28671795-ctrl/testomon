/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<K, V>
extends LinkedHashMap<K, V> {
    protected static final float DEFAULT_LOAD_FACTOR = 0.75f;
    protected static final int DEFAULT_INITIAL_CAPACITY = 5000;
    private static final long serialVersionUID = -9179676638408888162L;
    private int maximumSize;

    public LRUMap(int maximumSize) {
        this(5000, 0.75f, true, maximumSize);
    }

    public LRUMap(int maximumSize, boolean accessOrder) {
        this(5000, 0.75f, accessOrder, maximumSize);
    }

    public LRUMap(int initialCapacity, float loadFactor, boolean accessOrder, int maximumSize) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maximumSize = maximumSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maximumSize;
    }
}

