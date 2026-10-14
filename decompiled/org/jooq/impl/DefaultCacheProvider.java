/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jooq.CacheContext;
import org.jooq.CacheProvider;
import org.jooq.impl.LRUCache;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class DefaultCacheProvider
implements CacheProvider {
    DefaultCacheProvider() {
    }

    @Override
    public Map<Object, Object> provide(CacheContext ctx) {
        switch (ctx.cacheType()) {
            case CACHE_PARSING_CONNECTION: {
                return Collections.synchronizedMap(new LRUCache(StringUtils.defaultIfNull(Tools.settings(ctx.configuration()).getCacheParsingConnectionLRUCacheSize(), 8912)));
            }
        }
        return new ConcurrentHashMap<Object, Object>();
    }
}

