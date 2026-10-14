/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.Cache
 *  org.hibernate.cache.CacheConcurrencyStrategy
 *  org.hibernate.cache.CacheException
 */
package net.sf.ehcache.hibernate.ccs;

import net.sf.ehcache.hibernate.EhCache;
import net.sf.ehcache.hibernate.ccs.EhcacheReadOnlyCache;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheConcurrencyStrategy;
import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
abstract class AbstractEhcacheConcurrencyStrategy
implements CacheConcurrencyStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheReadOnlyCache.class);
    protected EhCache cache;

    AbstractEhcacheConcurrencyStrategy() {
    }

    public final void setCache(Cache cache) throws CacheException {
        if (!(cache instanceof EhCache)) {
            throw new CacheException("Ehcache concurrency strategies must be used with Ehcache caches");
        }
        this.cache = (EhCache)cache;
    }

    public final Cache getCache() {
        return this.cache;
    }

    public final String getRegionName() {
        return this.cache.getRegionName();
    }

    public final void remove(Object key) throws CacheException {
        this.cache.remove(key);
    }

    public final void clear() throws CacheException {
        this.cache.clear();
    }

    public final void destroy() {
        try {
            this.cache.destroy();
        }
        catch (Exception e) {
            LOG.warn("could not destroy cache", e);
        }
    }
}

