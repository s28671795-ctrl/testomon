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
import net.sf.ehcache.hibernate.strategy.AbstractReadWriteEhcacheAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

public class ReadWriteEhcacheEntityRegionAccessStrategy
extends AbstractReadWriteEhcacheAccessStrategy<EhcacheEntityRegion>
implements EntityRegionAccessStrategy {
    public ReadWriteEhcacheEntityRegionAccessStrategy(EhcacheEntityRegion region, Settings settings) {
        super(region, settings);
    }

    public EntityRegion getRegion() {
        return (EntityRegion)this.region;
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        ((EhcacheEntityRegion)this.region).writeLock(key);
        try {
            AbstractReadWriteEhcacheAccessStrategy.Lockable item = (AbstractReadWriteEhcacheAccessStrategy.Lockable)((EhcacheEntityRegion)this.region).get(key);
            if (item == null) {
                ((EhcacheEntityRegion)this.region).put(key, new AbstractReadWriteEhcacheAccessStrategy.Item(value, version, ((EhcacheEntityRegion)this.region).nextTimestamp()));
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        finally {
            ((EhcacheEntityRegion)this.region).writeUnlock(key);
        }
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        ((EhcacheEntityRegion)this.region).writeLock(key);
        try {
            AbstractReadWriteEhcacheAccessStrategy.Lockable item = (AbstractReadWriteEhcacheAccessStrategy.Lockable)((EhcacheEntityRegion)this.region).get(key);
            if (item != null && item.isUnlockable(lock)) {
                AbstractReadWriteEhcacheAccessStrategy.Lock lockItem = (AbstractReadWriteEhcacheAccessStrategy.Lock)item;
                if (lockItem.wasLockedConcurrently()) {
                    this.decrementLock(key, lockItem);
                    boolean bl = false;
                    return bl;
                }
                ((EhcacheEntityRegion)this.region).put(key, new AbstractReadWriteEhcacheAccessStrategy.Item(value, currentVersion, ((EhcacheEntityRegion)this.region).nextTimestamp()));
                boolean bl = true;
                return bl;
            }
            this.handleMissingLock(key, item);
            boolean bl = false;
            return bl;
        }
        finally {
            ((EhcacheEntityRegion)this.region).writeUnlock(key);
        }
    }
}

