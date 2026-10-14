/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import java.io.Closeable;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.cxf.Bus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.ws.security.cache.EHCacheUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.wss4j.common.cache.EHCacheManagerHolder;

public class EHCacheTokenStore
implements TokenStore,
Closeable,
BusLifeCycleListener {
    public static final long DEFAULT_TTL = 3600L;
    public static final long MAX_TTL = 43200L;
    private Ehcache cache;
    private Bus bus;
    private CacheManager cacheManager;
    private long ttl = 3600L;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public EHCacheTokenStore(String key, Bus b, URL configFileURL) {
        this.bus = b;
        if (this.bus != null) {
            b.getExtension(BusLifeCycleManager.class).registerLifeCycleListener(this);
        }
        this.cacheManager = EHCacheUtils.getCacheManager(this.bus, configFileURL);
        CacheConfiguration cc = EHCacheManagerHolder.getCacheConfiguration(key, this.cacheManager).overflowToDisk(false);
        RefCountCache newCache = new RefCountCache(cc);
        Ehcache ehcache = this.cache = this.cacheManager.addCacheIfAbsent(newCache);
        synchronized (ehcache) {
            if (this.cache.getStatus() != Status.STATUS_ALIVE) {
                this.cache = this.cacheManager.addCacheIfAbsent(newCache);
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

    @Override
    public void add(SecurityToken token) {
        if (token != null && !StringUtils.isEmpty(token.getId())) {
            Element element = new Element(token.getId(), token, this.getTTL(), this.getTTL());
            element.resetAccessStatistics();
            this.cache.put(element);
        }
    }

    @Override
    public void add(String identifier, SecurityToken token) {
        if (token != null && !StringUtils.isEmpty(identifier)) {
            Element element = new Element(identifier, token, this.getTTL(), this.getTTL());
            element.resetAccessStatistics();
            this.cache.put(element);
        }
    }

    @Override
    public void remove(String identifier) {
        if (this.cache != null && !StringUtils.isEmpty(identifier) && this.cache.isKeyInCache(identifier)) {
            this.cache.remove((Serializable)((Object)identifier));
        }
    }

    @Override
    public Collection<String> getTokenIdentifiers() {
        if (this.cache == null) {
            return null;
        }
        return this.cache.getKeysWithExpiryCheck();
    }

    @Override
    public SecurityToken getToken(String identifier) {
        if (this.cache == null) {
            return null;
        }
        Element element = this.cache.get((Serializable)((Object)identifier));
        if (element != null && !this.cache.isExpired(element)) {
            return (SecurityToken)element.getObjectValue();
        }
        return null;
    }

    private int getTTL() {
        int parsedTTL = (int)this.ttl;
        if (this.ttl != (long)parsedTTL) {
            parsedTTL = 3600;
        }
        return parsedTTL;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
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
            if (this.bus != null) {
                this.bus.getExtension(BusLifeCycleManager.class).unregisterLifeCycleListener(this);
            }
        }
    }

    @Override
    public void initComplete() {
    }

    @Override
    public void preShutdown() {
        this.close();
    }

    @Override
    public void postShutdown() {
        this.close();
    }

    private static class RefCountCache
    extends Cache {
        AtomicInteger count = new AtomicInteger();

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

