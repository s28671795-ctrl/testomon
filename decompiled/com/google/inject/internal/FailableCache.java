/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FailableCache<K, V> {
    private final Set<K> loadingSet = ConcurrentHashMap.newKeySet();
    private final LoadingCache<K, Object> delegate = CacheBuilder.newBuilder().build(new CacheLoader<K, Object>(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object load(K key) {
            FailableCache.this.loadingSet.add(key);
            Errors errors2 = new Errors();
            Object result = null;
            try {
                result = FailableCache.this.create(key, errors2);
            }
            catch (ErrorsException e) {
                errors2.merge(e.getErrors());
            }
            finally {
                FailableCache.this.loadingSet.remove(key);
            }
            return errors2.hasErrors() ? errors2 : result;
        }
    });

    protected abstract V create(K var1, Errors var2) throws ErrorsException;

    public V get(K key, Errors errors2) throws ErrorsException {
        Object resultOrError = this.delegate.getUnchecked(key);
        if (resultOrError instanceof Errors) {
            errors2.merge((Errors)resultOrError);
            throw errors2.toException();
        }
        Object result = resultOrError;
        return (V)result;
    }

    boolean remove(K key) {
        return this.delegate.asMap().remove(key) != null;
    }

    boolean isLoading(K key) {
        return this.loadingSet.contains(key);
    }

    Map<K, V> asMap() {
        return Maps.transformValues(Maps.filterValues(ImmutableMap.copyOf(this.delegate.asMap()), resultOrError -> !(resultOrError instanceof Errors)), resultOrError -> {
            Object result = resultOrError;
            return result;
        });
    }
}

