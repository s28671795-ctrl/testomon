/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.access.SoftLock
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.strategy;

import net.sf.ehcache.hibernate.regions.EhcacheTransactionalDataRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

abstract class AbstractEhcacheAccessStrategy<T extends EhcacheTransactionalDataRegion> {
    protected final T region;
    protected final Settings settings;

    AbstractEhcacheAccessStrategy(T region, Settings settings) {
        this.region = region;
        this.settings = settings;
    }

    public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
        return this.putFromLoad(key, value, txTimestamp, version, this.settings.isMinimalPutsEnabled());
    }

    public abstract boolean putFromLoad(Object var1, Object var2, long var3, Object var5, boolean var6) throws CacheException;

    public final SoftLock lockRegion() {
        return null;
    }

    public final void unlockRegion(SoftLock lock) throws CacheException {
        ((EhcacheTransactionalDataRegion)this.region).clear();
    }

    public void remove(Object key) throws CacheException {
    }

    public final void removeAll() throws CacheException {
        ((EhcacheTransactionalDataRegion)this.region).clear();
    }

    public final void evict(Object key) throws CacheException {
        ((EhcacheTransactionalDataRegion)this.region).remove(key);
    }

    public final void evictAll() throws CacheException {
        ((EhcacheTransactionalDataRegion)this.region).clear();
    }
}

