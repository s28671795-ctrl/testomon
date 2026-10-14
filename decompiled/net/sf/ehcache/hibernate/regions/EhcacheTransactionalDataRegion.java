/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheDataDescription
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.TransactionalDataRegion
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.concurrent.CacheLockProvider;
import net.sf.ehcache.concurrent.LockType;
import net.sf.ehcache.concurrent.StripedReadWriteLockSync;
import net.sf.ehcache.constructs.nonstop.NonStopCacheException;
import net.sf.ehcache.hibernate.nonstop.HibernateNonstopCacheExceptionHandler;
import net.sf.ehcache.hibernate.regions.EhcacheDataRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.TransactionalDataRegion;
import org.hibernate.cfg.Settings;

public class EhcacheTransactionalDataRegion
extends EhcacheDataRegion
implements TransactionalDataRegion {
    private static final int LOCAL_LOCK_PROVIDER_CONCURRENCY = 128;
    protected final Settings settings;
    protected final CacheDataDescription metadata;
    private final CacheLockProvider lockProvider;

    EhcacheTransactionalDataRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache cache, Settings settings, CacheDataDescription metadata, Properties properties) {
        super(accessStrategyFactory, cache, properties);
        this.settings = settings;
        this.metadata = metadata;
        Object context = cache.getInternalContext();
        this.lockProvider = context instanceof CacheLockProvider ? (CacheLockProvider)context : new StripedReadWriteLockSync(128);
    }

    public Settings getSettings() {
        return this.settings;
    }

    public boolean isTransactionAware() {
        return false;
    }

    public CacheDataDescription getCacheDataDescription() {
        return this.metadata;
    }

    public final Object get(Object key) {
        try {
            Element element = this.cache.get(key);
            if (element == null) {
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

    public final void put(Object key, Object value) throws org.hibernate.cache.CacheException {
        this.put(key, value, false);
    }

    public final void putEternal(Object key, Object value) throws org.hibernate.cache.CacheException {
        this.put(key, value, true);
    }

    private void put(Object key, Object value, boolean eternal) throws org.hibernate.cache.CacheException {
        try {
            Element element = new Element(key, value);
            element.setEternal(eternal);
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
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
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
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void writeLock(Object key) {
        try {
            this.lockProvider.getSyncForKey(key).lock(LockType.WRITE);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void writeUnlock(Object key) {
        try {
            this.lockProvider.getSyncForKey(key).unlock(LockType.WRITE);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void readLock(Object key) {
        try {
            this.lockProvider.getSyncForKey(key).lock(LockType.READ);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final void readUnlock(Object key) {
        try {
            this.lockProvider.getSyncForKey(key).unlock(LockType.READ);
        }
        catch (CacheException e) {
            if (e instanceof NonStopCacheException) {
                HibernateNonstopCacheExceptionHandler.getInstance().handleNonstopCacheException((NonStopCacheException)e);
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final boolean locksAreIndependentOfCache() {
        return this.lockProvider instanceof StripedReadWriteLockSync;
    }
}

