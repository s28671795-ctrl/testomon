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

import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.strategy.AbstractEhcacheAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

public class NonStrictReadWriteEhcacheCollectionRegionAccessStrategy
extends AbstractEhcacheAccessStrategy<EhcacheCollectionRegion>
implements CollectionRegionAccessStrategy {
    public NonStrictReadWriteEhcacheCollectionRegionAccessStrategy(EhcacheCollectionRegion region, Settings settings) {
        super(region, settings);
    }

    public CollectionRegion getRegion() {
        return (CollectionRegion)this.region;
    }

    public Object get(Object key, long txTimestamp) throws CacheException {
        return ((EhcacheCollectionRegion)this.region).get(key);
    }

    @Override
    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        if (minimalPutOverride && ((EhcacheCollectionRegion)this.region).contains(key)) {
            return false;
        }
        ((EhcacheCollectionRegion)this.region).put(key, value);
        return true;
    }

    public SoftLock lockItem(Object key, Object version) throws CacheException {
        return null;
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        ((EhcacheCollectionRegion)this.region).remove(key);
    }

    @Override
    public void remove(Object key) throws CacheException {
        ((EhcacheCollectionRegion)this.region).remove(key);
    }
}

