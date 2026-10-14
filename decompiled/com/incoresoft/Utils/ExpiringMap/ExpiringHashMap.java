/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.ExpiringMap;

import com.incoresoft.Utils.ExpiringMap.ExpiringMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ExpiringHashMap<K, V>
implements ExpiringMap<K, V> {
    private final Map<K, V> internalMap;
    private final Map<K, ExpiringKey<K>> expiringKeys;
    private final DelayQueue<ExpiringKey> delayQueue = new DelayQueue();
    private final long maxLifeTimeMillis;

    public ExpiringHashMap() {
        this.internalMap = new ConcurrentHashMap();
        this.expiringKeys = new WeakHashMap<K, ExpiringKey<K>>();
        this.maxLifeTimeMillis = Long.MAX_VALUE;
    }

    public ExpiringHashMap(long defaultMaxLifeTimeMillis) {
        this.internalMap = new ConcurrentHashMap();
        this.expiringKeys = new WeakHashMap<K, ExpiringKey<K>>();
        this.maxLifeTimeMillis = defaultMaxLifeTimeMillis;
    }

    public ExpiringHashMap(long defaultMaxLifeTimeMillis, int initialCapacity) {
        this.internalMap = new ConcurrentHashMap(initialCapacity);
        this.expiringKeys = new WeakHashMap<K, ExpiringKey<K>>(initialCapacity);
        this.maxLifeTimeMillis = defaultMaxLifeTimeMillis;
    }

    public ExpiringHashMap(long defaultMaxLifeTimeMillis, int initialCapacity, float loadFactor) {
        this.internalMap = new ConcurrentHashMap(initialCapacity, loadFactor);
        this.expiringKeys = new WeakHashMap<K, ExpiringKey<K>>(initialCapacity, loadFactor);
        this.maxLifeTimeMillis = defaultMaxLifeTimeMillis;
    }

    @Override
    public int size() {
        this.cleanup();
        return this.internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        this.cleanup();
        return this.internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        this.cleanup();
        return this.internalMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        this.cleanup();
        return this.internalMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        this.cleanup();
        this.renewKey(key);
        return this.internalMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return this.put(key, value, this.maxLifeTimeMillis);
    }

    @Override
    public V put(K key, V value, long lifeTimeMillis) {
        this.cleanup();
        ExpiringKey<K> delayedKey = new ExpiringKey<K>(key, lifeTimeMillis);
        ExpiringKey<K> oldKey = this.expiringKeys.put(key, delayedKey);
        if (oldKey != null) {
            this.expireKey(oldKey);
            this.expiringKeys.put(key, delayedKey);
        }
        this.delayQueue.offer(delayedKey);
        return this.internalMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V removedValue = this.internalMap.remove(key);
        this.expireKey(this.expiringKeys.remove(key));
        return removedValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean renewKey(K key) {
        ExpiringKey<K> delayedKey = this.expiringKeys.get(key);
        if (delayedKey != null) {
            delayedKey.renew();
            return true;
        }
        return false;
    }

    private void expireKey(ExpiringKey<K> delayedKey) {
        if (delayedKey != null) {
            delayedKey.expire();
            this.cleanup();
        }
    }

    @Override
    public void clear() {
        this.delayQueue.clear();
        this.expiringKeys.clear();
        this.internalMap.clear();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private void cleanup() {
        ExpiringKey delayedKey = (ExpiringKey)this.delayQueue.poll();
        while (delayedKey != null) {
            this.internalMap.remove(delayedKey.getKey());
            this.expiringKeys.remove(delayedKey.getKey());
            delayedKey = (ExpiringKey)this.delayQueue.poll();
        }
    }

    private class ExpiringKey<K>
    implements Delayed {
        private long startTime = System.currentTimeMillis();
        private final long maxLifeTimeMillis;
        private final K key;

        public ExpiringKey(K key, long maxLifeTimeMillis) {
            this.maxLifeTimeMillis = maxLifeTimeMillis;
            this.key = key;
        }

        public K getKey() {
            return this.key;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            ExpiringKey other = (ExpiringKey)obj;
            return this.key == other.key || this.key != null && this.key.equals(other.key);
        }

        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.getDelayMillis(), TimeUnit.MILLISECONDS);
        }

        private long getDelayMillis() {
            return this.startTime + this.maxLifeTimeMillis - System.currentTimeMillis();
        }

        public void renew() {
            this.startTime = System.currentTimeMillis();
        }

        public void expire() {
            this.startTime = Long.MIN_VALUE;
        }

        @Override
        public int compareTo(Delayed that) {
            return Long.compare(this.getDelayMillis(), ((ExpiringKey)that).getDelayMillis());
        }
    }
}

