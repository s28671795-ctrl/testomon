/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.access.SoftLock
 */
package net.sf.ehcache.hibernate.ccs;

import java.util.Comparator;
import net.sf.ehcache.hibernate.ccs.AbstractEhcacheConcurrencyStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.access.SoftLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class EhcacheNonstrictReadWriteCache
extends AbstractEhcacheConcurrencyStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheNonstrictReadWriteCache.class);

    public Object get(Object key, long txTimestamp) throws CacheException {
        return this.cache.get(key);
    }

    public boolean put(Object key, Object value, long txTimestamp, Object version, Comparator versionComparator, boolean minimalPut) throws CacheException {
        if (minimalPut && this.cache.get(key) != null) {
            return false;
        }
        this.cache.put(key, value);
        return true;
    }

    public SoftLock lock(Object key, Object version) throws CacheException {
        return null;
    }

    public void evict(Object key) throws CacheException {
        this.cache.remove(key);
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        this.evict(key);
        return false;
    }

    public boolean insert(Object key, Object value, Object currentVersion) throws CacheException {
        return false;
    }

    public void release(Object key, SoftLock lock) throws CacheException {
        this.cache.remove(key);
    }

    public boolean afterUpdate(Object key, Object value, Object version, SoftLock lock) throws CacheException {
        this.release(key, lock);
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public String toString() {
        return this.cache + "(non-strict-read-write)";
    }
}

