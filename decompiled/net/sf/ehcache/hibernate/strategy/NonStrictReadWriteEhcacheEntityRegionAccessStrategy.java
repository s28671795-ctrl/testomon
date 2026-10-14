/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.EntityRegion
 *  org.hibernate.cache.access.EntityRegionAccessStrategy
 *  org.hibernate.cache.access.SoftLock
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.strategy;

import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.strategy.AbstractEhcacheAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

public class NonStrictReadWriteEhcacheEntityRegionAccessStrategy
extends AbstractEhcacheAccessStrategy<EhcacheEntityRegion>
implements EntityRegionAccessStrategy {
    public NonStrictReadWriteEhcacheEntityRegionAccessStrategy(EhcacheEntityRegion region, Settings settings) {
        super(region, settings);
    }

    public EntityRegion getRegion() {
        return (EntityRegion)this.region;
    }

    public Object get(Object key, long txTimestamp) throws CacheException {
        return ((EhcacheEntityRegion)this.region).get(key);
    }

    @Override
    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        if (minimalPutOverride && ((EhcacheEntityRegion)this.region).contains(key)) {
            return false;
        }
        ((EhcacheEntityRegion)this.region).put(key, value);
        return true;
    }

    public SoftLock lockItem(Object key, Object version) throws CacheException {
        return null;
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        ((EhcacheEntityRegion)this.region).remove(key);
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        this.remove(key);
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        this.unlockItem(key, lock);
        return false;
    }

    @Override
    public void remove(Object key) throws CacheException {
        ((EhcacheEntityRegion)this.region).remove(key);
    }
}

