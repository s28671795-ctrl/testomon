/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;
import org.jooq.Configuration;
import org.jooq.impl.CacheType;
import org.jooq.impl.DefaultCacheContext;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.tools.StringUtils;

final class Cache {
    private static final Object NULL = new Object();

    Cache() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final <V> V run(Configuration configuration, Supplier<V> operation, CacheType type, Supplier<?> key) {
        if (configuration == null) {
            configuration = new DefaultConfiguration();
        }
        if (!type.category.predicate.test(configuration.settings())) {
            return operation.get();
        }
        Object cacheOrNull = configuration.data((Object)type);
        if (cacheOrNull == null) {
            CacheType cacheType = type;
            synchronized (cacheType) {
                cacheOrNull = configuration.data((Object)type);
                if (cacheOrNull == null) {
                    cacheOrNull = StringUtils.defaultIfNull(configuration.cacheProvider().provide(new DefaultCacheContext(configuration, type)), NULL);
                    configuration.data((Object)type, cacheOrNull);
                }
            }
        }
        if (cacheOrNull == NULL) {
            return operation.get();
        }
        Map cache = (Map)cacheOrNull;
        Object k = key.get();
        Object v = cache.get(k);
        if (v == null) {
            Map map = cache;
            synchronized (map) {
                v = cache.get(k);
                if (v == null) {
                    v = operation.get();
                    cache.put(k, v == null ? NULL : v);
                }
            }
        }
        return v == NULL ? null : (V)v;
    }

    static final Object key(Object key1, Object key2) {
        return new Key2(key1, key2);
    }

    static final Object key(Object key1, Object key2, Object key3) {
        return new Key3(key1, key2, key3);
    }

    private record Key2(Object key1, Object key2) implements Serializable
    {
    }

    private record Key3(Object key1, Object key2, Object key3) implements Serializable
    {
    }
}

