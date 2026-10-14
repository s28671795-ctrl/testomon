/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.CollectionRegion
 *  org.hibernate.cache.access.CollectionRegionAccessStrategy
 *  org.hibernate.cache.access.SoftLock
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.strategy;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.strategy.AbstractEhcacheAccessStrategy;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

public class TransactionalEhcacheCollectionRegionAccessStrategy
extends AbstractEhcacheAccessStrategy<EhcacheCollectionRegion>
implements CollectionRegionAccessStrategy {
    private final Ehcache ehcache;

    public TransactionalEhcacheCollectionRegionAccessStrategy(EhcacheCollectionRegion region, Ehcache ehcache, Settings settings) {
        super(region, settings);
        this.ehcache = ehcache;
    }

    public Object get(Object key, long txTimestamp) throws org.hibernate.cache.CacheException {
        try {
            Element element = this.ehcache.get(key);
            return element == null ? null : element.getObjectValue();
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public CollectionRegion getRegion() {
        return (CollectionRegion)this.region;
    }

    public SoftLock lockItem(Object key, Object version) throws org.hibernate.cache.CacheException {
        return null;
    }

    @Override
    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws org.hibernate.cache.CacheException {
        try {
            if (minimalPutOverride && this.ehcache.get(key) != null) {
                return false;
            }
            this.ehcache.put(new Element(key, value));
            return true;
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    @Override
    public void remove(Object key) throws org.hibernate.cache.CacheException {
        try {
            this.ehcache.remove(key);
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void unlockItem(Object key, SoftLock lock) throws org.hibernate.cache.CacheException {
    }
}

