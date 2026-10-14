/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.wss4j.common.cache.EHCacheManagerHolder;
import org.apache.wss4j.common.cache.ReplayCache;

public class EHCacheReplayCache
implements ReplayCache {
    public static final long DEFAULT_TTL = 3600L;
    public static final long MAX_TTL = 43200L;
    protected Ehcache cache;
    protected CacheManager cacheManager;
    private long ttl = 3600L;

    public EHCacheReplayCache(String key, URL configFileURL) {
        this(key, EHCacheManagerHolder.getCacheManager("", configFileURL));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public EHCacheReplayCache(String key, CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        CacheConfiguration cc = EHCacheManagerHolder.getCacheConfiguration(key, cacheManager);
        RefCountCache newCache = new RefCountCache(cc);
        Ehcache ehcache = this.cache = cacheManager.addCacheIfAbsent(newCache);
        synchronized (ehcache) {
            if (this.cache.getStatus() != Status.STATUS_ALIVE) {
                this.cache = cacheManager.addCacheIfAbsent(newCache);
            }
            if (this.cache instanceof RefCountCache) {
                ((RefCountCache)this.cache).incrementAndGet();
            }
        }
        this.ttl = cc.getTimeToLiveSeconds();
    }

    public void setTTL(long newTtl) {
        this.ttl = newTtl;
    }

    public long getTTL() {
        return this.ttl;
    }

    @Override
    public void add(String identifier) {
        this.add(identifier, this.ttl);
    }

    @Override
    public void add(String identifier, long timeToLive) {
        if (identifier == null || "".equals(identifier)) {
            return;
        }
        int parsedTTL = (int)timeToLive;
        if ((timeToLive != (long)parsedTTL || parsedTTL < 0 || (long)parsedTTL > 43200L) && this.ttl != (long)(parsedTTL = (int)this.ttl)) {
            parsedTTL = 3600;
        }
        Element cacheElement = new Element(identifier, identifier, parsedTTL, parsedTTL);
        cacheElement.resetAccessStatistics();
        this.cache.put(cacheElement);
    }

    @Override
    public boolean contains(String identifier) {
        if (this.cache == null) {
            return false;
        }
        Element element = this.cache.get((Serializable)((Object)identifier));
        if (element != null) {
            if (this.cache.isExpired(element)) {
                this.cache.remove((Serializable)((Object)identifier));
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void close() {
        if (this.cacheManager != null) {
            if (this.cache != null) {
                Ehcache ehcache = this.cache;
                synchronized (ehcache) {
                    if (this.cache instanceof RefCountCache && ((RefCountCache)this.cache).decrementAndGet() == 0) {
                        this.cacheManager.removeCache(this.cache.getName());
                    }
                }
            }
            EHCacheManagerHolder.releaseCacheManger(this.cacheManager);
            this.cacheManager = null;
            this.cache = null;
        }
    }

    public void initComplete() {
    }

    public void preShutdown() {
        this.close();
    }

    public void postShutdown() {
        this.close();
    }

    private static class RefCountCache
    extends Cache {
        private AtomicInteger count = new AtomicInteger();

        RefCountCache(CacheConfiguration cc) {
            super(cc);
        }

        public int incrementAndGet() {
            return this.count.incrementAndGet();
        }

        public int decrementAndGet() {
            return this.count.decrementAndGet();
        }
    }
}

