/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

public class Pair<K, V> {
    public K first;
    public V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair p = (Pair)o;
        return p.first == this.first && p.second == this.second;
    }
}

