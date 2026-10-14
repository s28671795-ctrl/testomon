/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.apache.cxf.common.util.WeakIdentityHashMap;

public class CacheMap<K, V>
implements Map<K, V> {
    Map<K, V> mainDataMap = new WeakHashMap();
    Map<K, V> extraKeyMap = new WeakIdentityHashMap();

    @Override
    public void clear() {
        this.mainDataMap.clear();
        this.extraKeyMap.clear();
    }

    private void updateMainDataMap() {
        for (Map.Entry<K, V> entry : this.extraKeyMap.entrySet()) {
            if (this.mainDataMap.containsKey(entry.getKey())) continue;
            this.mainDataMap.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (!this.mainDataMap.containsKey(key)) {
            this.updateMainDataMap();
            return this.mainDataMap.containsKey(key);
        }
        return true;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.mainDataMap.containsValue(value) || this.extraKeyMap.containsValue(value);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        this.updateMainDataMap();
        return this.mainDataMap.entrySet();
    }

    @Override
    public V get(Object key) {
        V val = this.mainDataMap.get(key);
        if (val == null) {
            this.updateMainDataMap();
            val = this.mainDataMap.get(key);
        }
        if (val != null) {
            this.extraKeyMap.put(key, val);
        }
        return val;
    }

    @Override
    public boolean isEmpty() {
        return this.mainDataMap.isEmpty() && this.extraKeyMap.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        this.updateMainDataMap();
        return this.mainDataMap.keySet();
    }

    @Override
    public V put(K key, V value) {
        V v = this.mainDataMap.put(key, value);
        V v2 = this.extraKeyMap.put(key, value);
        return v == null ? v2 : v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t2) {
        for (Map.Entry<K, V> ent : t2.entrySet()) {
            this.put(ent.getKey(), ent.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        V v = this.mainDataMap.remove(key);
        HashSet<K> keys2 = new HashSet<K>(this.extraKeyMap.keySet());
        V v2 = this.extraKeyMap.remove(key);
        for (Object nk : keys2) {
            if ((key == null || !key.equals(nk)) && (key != null || nk != null)) continue;
            V v3 = this.extraKeyMap.remove(nk);
            if (v2 != null) continue;
            v2 = v3;
        }
        return v == null ? v2 : v;
    }

    @Override
    public int size() {
        this.updateMainDataMap();
        return this.mainDataMap.size();
    }

    @Override
    public Collection<V> values() {
        this.updateMainDataMap();
        return this.mainDataMap.values();
    }

    public String toString() {
        this.updateMainDataMap();
        return this.mainDataMap.toString();
    }
}

