/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.Cache
 *  org.hibernate.cache.CacheException
 */
package net.sf.ehcache.hibernate;

import java.util.HashMap;
import java.util.Map;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.concurrent.CacheLockProvider;
import net.sf.ehcache.concurrent.LockType;
import net.sf.ehcache.util.Timestamper;
import org.hibernate.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public final class EhCache
implements Cache {
    private static final Logger LOG = LoggerFactory.getLogger(EhCache.class.getName());
    private static final int SIXTY_THOUSAND_MS = 60000;
    private final Ehcache cache;
    private final CacheLockProvider lockProvider;

    public EhCache(Ehcache cache) {
        this.cache = cache;
        Object context = cache.getInternalContext();
        this.lockProvider = context instanceof CacheLockProvider ? (CacheLockProvider)context : null;
    }

    public final Object get(Object key) throws org.hibernate.cache.CacheException {
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
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final Object read(Object key) throws org.hibernate.cache.CacheException {
        return this.get(key);
    }

    public final void update(Object key, Object value) throws org.hibernate.cache.CacheException {
        this.put(key, value);
    }

    public final void put(Object key, Object value) throws org.hibernate.cache.CacheException {
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
    }

    public final void remove(Object key) throws org.hibernate.cache.CacheException {
        try {
            this.cache.remove(key);
        }
        catch (ClassCastException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
        catch (IllegalStateException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void clear() throws org.hibernate.cache.CacheException {
        try {
            this.cache.removeAll();
        }
        catch (IllegalStateException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void destroy() throws org.hibernate.cache.CacheException {
        try {
            this.cache.getCacheManager().removeCache(this.cache.getName());
        }
        catch (IllegalStateException e) {
            LOG.debug("This can happen if multiple frameworks both try to shutdown ehcache", e);
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void lock(Object key) throws org.hibernate.cache.CacheException {
        if (this.lockProvider != null) {
            this.lockProvider.getSyncForKey(key).lock(LockType.WRITE);
        }
    }

    public final void unlock(Object key) throws org.hibernate.cache.CacheException {
        if (this.lockProvider != null) {
            this.lockProvider.getSyncForKey(key).unlock(LockType.WRITE);
        }
    }

    public final long nextTimestamp() {
        return Timestamper.next();
    }

    public final int getTimeout() {
        return Timestamper.ONE_MS * 60000;
    }

    public final String getRegionName() {
        return this.cache.getName();
    }

    public final long getSizeInMemory() {
        try {
            return this.cache.getStatistics().getLocalHeapSizeInBytes();
        }
        catch (Throwable t2) {
            return -1L;
        }
    }

    public final long getElementCountInMemory() {
        try {
            return this.cache.getSize();
        }
        catch (CacheException ce) {
            throw new org.hibernate.cache.CacheException((Throwable)ce);
        }
    }

    public final long getElementCountOnDisk() {
        return -1L;
    }

    public final Map toMap() {
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
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final boolean canLockEntries() {
        return this.lockProvider != null;
    }

    public final String toString() {
        return "EHCache(" + this.getRegionName() + ')';
    }

    final Ehcache getBackingCache() {
        return this.cache;
    }
}

