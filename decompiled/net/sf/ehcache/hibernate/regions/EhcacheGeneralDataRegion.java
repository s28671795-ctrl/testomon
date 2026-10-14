/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.GeneralDataRegion
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.nonstop.NonStopCacheException;
import net.sf.ehcache.hibernate.nonstop.HibernateNonstopCacheExceptionHandler;
import net.sf.ehcache.hibernate.regions.EhcacheDataRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.GeneralDataRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class EhcacheGeneralDataRegion
extends EhcacheDataRegion
implements GeneralDataRegion {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheGeneralDataRegion.class);

    public EhcacheGeneralDataRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache cache, Properties properties) {
        super(accessStrategyFactory, cache, properties);
    }

    public Object get(Object key) throws org.hibernate.cache.CacheException {
        try {
            LOG.debug("key: {}", key);
            if (key == null) {
                return null;
            }
            Element element = this.cache.get(key);
            if (element == null) {
                LOG.debug("Element for key {} is null", key);
                return null;
            }
            return element.getObjectValue();
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
                return null;
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void put(Object key, Object value) throws org.hibernate.cache.CacheException {
        LOG.debug("key: {} value: {}", key, value);
        try {
            Element element = new Element(key, value);
            this.cache.put(element);
        }
        catch (IllegalArgumentException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (IllegalStateException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void evict(Object key) throws org.hibernate.cache.CacheException {
        try {
            this.cache.remove(key);
        }
        catch (ClassCastException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (IllegalStateException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void evictAll() throws org.hibernate.cache.CacheException {
        try {
            this.cache.removeAll();
        }
        catch (IllegalStateException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }
}

