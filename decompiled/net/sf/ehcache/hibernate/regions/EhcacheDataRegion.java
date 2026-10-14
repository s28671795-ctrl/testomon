/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.Region
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.nonstop.NonStopCacheException;
import net.sf.ehcache.hibernate.nonstop.HibernateNonstopCacheExceptionHandler;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import net.sf.ehcache.util.Timestamper;
import org.hibernate.cache.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EhcacheDataRegion
implements Region {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheDataRegion.class);
    private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "net.sf.ehcache.hibernate.cache_lock_timeout";
    private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 60000;
    protected final Ehcache cache;
    protected final EhcacheAccessStrategyFactory accessStrategyFactory;
    private final int cacheLockTimeout;

    EhcacheDataRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache cache, Properties properties) {
        this.accessStrategyFactory = accessStrategyFactory;
        this.cache = cache;
        String timeout2 = properties.getProperty(CACHE_LOCK_TIMEOUT_PROPERTY, Integer.toString(60000));
        this.cacheLockTimeout = Timestamper.ONE_MS * Integer.decode(timeout2);
    }

    public String getName() {
        return this.cache.getName();
    }

    public void destroy() throws org.hibernate.cache.CacheException {
        try {
            this.cache.getCacheManager().removeCache(this.cache.getName());
        }
        catch (IllegalStateException e) {
            LOG.debug("This can happen if multiple frameworks both try to shutdown ehcache", e);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public long getSizeInMemory() {
        return -1L;
    }

    public long getElementCountInMemory() {
        try {
            return this.cache.getSize();
        }
        catch (CacheException ce) {
            if (ce instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)ce);
                return -1L;
            }
            throw new org.hibernate.cache.CacheException((Throwable)ce);
        }
    }

    public long getElementCountOnDisk() {
        return -1L;
    }

    public Map toMap() {
        try {
            HashMap result = new HashMap();
            for (Object key : this.cache.getKeys()) {
                Element e = this.cache.get(key);
                if (e == null) continue;
                result.put(key, e.getObjectValue());
            }
            return result;
        }
        catch (Exception e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
                return Collections.emptyMap();
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public int getTimeout() {
        return this.cacheLockTimeout;
    }

    public Ehcache getEhcache() {
        return this.cache;
    }

    public boolean contains(Object key) {
        return this.cache.isKeyInCache(key);
    }
}

