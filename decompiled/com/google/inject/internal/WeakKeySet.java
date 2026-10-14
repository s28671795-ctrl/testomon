/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.inject.Key;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InjectorBindingData;
import com.google.inject.internal.util.SourceProvider;
import java.util.Map;
import java.util.Set;

final class WeakKeySet {
    private Map<Key<?>, Multiset<Object>> backingMap;
    private final Object lock;
    private final Cache<InjectorBindingData, Set<KeyAndSource>> evictionCache = CacheBuilder.newBuilder().weakKeys().removalListener(this::cleanupOnRemoval).build();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void cleanupOnRemoval(RemovalNotification<InjectorBindingData, Set<KeyAndSource>> notification) {
        Preconditions.checkState(RemovalCause.COLLECTED.equals((Object)notification.getCause()));
        Object object = this.lock;
        synchronized (object) {
            for (KeyAndSource keyAndSource : (Set)notification.getValue()) {
                Multiset<Object> set = this.backingMap.get(keyAndSource.key);
                if (set == null) continue;
                set.remove(keyAndSource.source);
                if (!set.isEmpty()) continue;
                this.backingMap.remove(keyAndSource.key);
            }
        }
    }

    WeakKeySet(Object lock) {
        this.lock = lock;
    }

    public void add(Key<?> key, InjectorBindingData state, Object source2) {
        if (this.backingMap == null) {
            this.backingMap = Maps.newHashMap();
        }
        if (source2 instanceof Class || source2 == SourceProvider.UNKNOWN_SOURCE) {
            source2 = null;
        }
        Object convertedSource = Errors.convert(source2);
        this.backingMap.computeIfAbsent(key, k -> LinkedHashMultiset.create()).add(convertedSource);
        if (state.parent().isPresent()) {
            Set<KeyAndSource> keyAndSources = this.evictionCache.getIfPresent(state);
            if (keyAndSources == null) {
                keyAndSources = Sets.newHashSet();
                this.evictionCache.put(state, keyAndSources);
            }
            keyAndSources.add(new KeyAndSource(key, convertedSource));
        }
    }

    public boolean contains(Key<?> key) {
        this.evictionCache.cleanUp();
        return this.backingMap != null && this.backingMap.containsKey(key);
    }

    public Set<Object> getSources(Key<?> key) {
        this.evictionCache.cleanUp();
        Multiset<Object> sources = this.backingMap == null ? null : this.backingMap.get(key);
        return sources == null ? null : sources.elementSet();
    }

    private static final class KeyAndSource {
        final Key<?> key;
        final Object source;

        KeyAndSource(Key<?> key, Object source2) {
            this.key = key;
            this.source = source2;
        }

        public int hashCode() {
            return Objects.hashCode(this.key, this.source);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof KeyAndSource)) {
                return false;
            }
            KeyAndSource other = (KeyAndSource)obj;
            return Objects.equal(this.key, other.key) && Objects.equal(this.source, other.source);
        }
    }
}

