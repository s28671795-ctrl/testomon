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
public class EhcacheReadOnlyCache
extends AbstractEhcacheConcurrencyStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheReadOnlyCache.class);

    public Object get(Object key, long timestamp) throws CacheException {
        return this.cache.get(key);
    }

    public SoftLock lock(Object key, Object version) throws UnsupportedOperationException {
        LOG.error("Application attempted to edit read only item: " + key);
        throw new UnsupportedOperationException("Can't write to a readonly object");
    }

    public boolean put(Object key, Object value, long timestamp, Object version, Comparator versionComparator, boolean minimalPut) throws CacheException {
        if (minimalPut && this.cache.get(key) != null) {
            return false;
        }
        this.cache.put(key, value);
        return true;
    }

    public void release(Object key, SoftLock lock) {
        LOG.error("Application attempted to edit read only item: " + key);
    }

    public boolean afterUpdate(Object key, Object value, Object version, SoftLock lock) throws UnsupportedOperationException {
        LOG.error("Application attempted to edit read only item: " + key);
        throw new UnsupportedOperationException("Can't write to a readonly object");
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        this.cache.update(key, value);
        return true;
    }

    public void evict(Object key) throws CacheException {
    }

    public boolean insert(Object key, Object value, Object currentVersion) {
        return false;
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws UnsupportedOperationException {
        LOG.error("Application attempted to edit read only item: " + key);
        throw new UnsupportedOperationException("Can't write to a readonly object");
    }

    public String toString() {
        return this.cache + "(read-only)";
    }
}

