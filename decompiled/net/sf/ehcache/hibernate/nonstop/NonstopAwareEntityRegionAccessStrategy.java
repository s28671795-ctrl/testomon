/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.EntityRegion
 *  org.hibernate.cache.access.EntityRegionAccessStrategy
 *  org.hibernate.cache.access.SoftLock
 */
package net.sf.ehcache.hibernate.nonstop;

import net.sf.ehcache.constructs.nonstop.NonStopCacheException;
import net.sf.ehcache.hibernate.nonstop.HibernateNonstopCacheExceptionHandler;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

public class NonstopAwareEntityRegionAccessStrategy
implements EntityRegionAccessStrategy {
    private final EntityRegionAccessStrategy actualStrategy;
    private final HibernateNonstopCacheExceptionHandler hibernateNonstopExceptionHandler;

    public NonstopAwareEntityRegionAccessStrategy(EntityRegionAccessStrategy actualStrategy, HibernateNonstopCacheExceptionHandler hibernateNonstopExceptionHandler) {
        this.actualStrategy = actualStrategy;
        this.hibernateNonstopExceptionHandler = hibernateNonstopExceptionHandler;
    }

    public EntityRegion getRegion() {
        return this.actualStrategy.getRegion();
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        try {
            return this.actualStrategy.afterInsert(key, value, version);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        try {
            return this.actualStrategy.afterUpdate(key, value, currentVersion, previousVersion, lock);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }

    public void evict(Object key) throws CacheException {
        try {
            this.actualStrategy.evict(key);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public void evictAll() throws CacheException {
        try {
            this.actualStrategy.evictAll();
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public Object get(Object key, long txTimestamp) throws CacheException {
        try {
            return this.actualStrategy.get(key, txTimestamp);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return null;
        }
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        try {
            return this.actualStrategy.insert(key, value, version);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }

    public SoftLock lockItem(Object key, Object version) throws CacheException {
        try {
            return this.actualStrategy.lockItem(key, version);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return null;
        }
    }

    public SoftLock lockRegion() throws CacheException {
        try {
            return this.actualStrategy.lockRegion();
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return null;
        }
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        try {
            return this.actualStrategy.putFromLoad(key, value, txTimestamp, version, minimalPutOverride);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
        try {
            return this.actualStrategy.putFromLoad(key, value, txTimestamp, version);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }

    public void remove(Object key) throws CacheException {
        try {
            this.actualStrategy.remove(key);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public void removeAll() throws CacheException {
        try {
            this.actualStrategy.removeAll();
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        try {
            this.actualStrategy.unlockItem(key, lock);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public void unlockRegion(SoftLock lock) throws CacheException {
        try {
            this.actualStrategy.unlockRegion(lock);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
        }
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        try {
            return this.actualStrategy.update(key, value, currentVersion, previousVersion);
        }
        catch (NonStopCacheException nonStopCacheException) {
            this.hibernateNonstopExceptionHandler.handleNonstopCacheException(nonStopCacheException);
            return false;
        }
    }
}

