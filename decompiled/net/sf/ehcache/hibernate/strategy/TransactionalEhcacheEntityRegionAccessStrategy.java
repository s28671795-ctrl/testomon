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

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.strategy.AbstractEhcacheAccessStrategy;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;

public class TransactionalEhcacheEntityRegionAccessStrategy
extends AbstractEhcacheAccessStrategy<EhcacheEntityRegion>
implements EntityRegionAccessStrategy {
    private final Ehcache ehcache;

    public TransactionalEhcacheEntityRegionAccessStrategy(EhcacheEntityRegion region, Ehcache ehcache, Settings settings) {
        super(region, settings);
        this.ehcache = ehcache;
    }

    public boolean afterInsert(Object key, Object value, Object version) {
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) {
        return false;
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

    public EntityRegion getRegion() {
        return (EntityRegion)this.region;
    }

    public boolean insert(Object key, Object value, Object version) throws org.hibernate.cache.CacheException {
        try {
            this.ehcache.put(new Element(key, value));
            return true;
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
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

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws org.hibernate.cache.CacheException {
        try {
            this.ehcache.put(new Element(key, value));
            return true;
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }
}

