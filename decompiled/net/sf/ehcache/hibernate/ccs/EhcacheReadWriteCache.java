/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.access.SoftLock
 */
package net.sf.ehcache.hibernate.ccs;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.sf.ehcache.hibernate.ccs.AbstractEhcacheConcurrencyStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.access.SoftLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class EhcacheReadWriteCache
extends AbstractEhcacheConcurrencyStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheReadWriteCache.class);
    private final UUID uuid = UUID.randomUUID();
    private final AtomicLong nextLockId = new AtomicLong();
    private final ReentrantReadWriteLock.ReadLock coarseReadLock;
    private final ReentrantReadWriteLock.WriteLock coarseWriteLock;

    public EhcacheReadWriteCache() {
        ReentrantReadWriteLock coarseLock = new ReentrantReadWriteLock();
        this.coarseReadLock = coarseLock.readLock();
        this.coarseWriteLock = coarseLock.writeLock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object get(Object key, long txTimestamp) throws CacheException {
        this.readLockIfCoarse(key);
        try {
            boolean readable;
            Lockable item = (Lockable)this.cache.get(key);
            boolean bl = readable = item != null && item.isReadable(txTimestamp);
            if (readable) {
                Object object = item.getValue();
                return object;
            }
            Object var6_6 = null;
            return var6_6;
        }
        finally {
            this.readUnlockIfCoarse(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean put(Object key, Object value, long txTimestamp, Object version, Comparator versionComparator, boolean minimalPut) throws CacheException {
        this.writeLock(key);
        try {
            boolean writeable;
            Lockable item = (Lockable)this.cache.get(key);
            boolean bl = writeable = item == null || item.isWriteable(txTimestamp, version, versionComparator);
            if (writeable) {
                this.cache.put(key, new Item(value, version, this.cache.nextTimestamp()));
                boolean bl2 = true;
                return bl2;
            }
            boolean bl3 = false;
            return bl3;
        }
        finally {
            this.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SoftLock lock(Object key, Object version) throws CacheException {
        this.writeLock(key);
        try {
            Lockable item = (Lockable)this.cache.get(key);
            long timeout2 = this.cache.nextTimestamp() + (long)this.cache.getTimeout();
            Lock lock = item == null ? new Lock(timeout2, this.uuid, this.nextLockId(), version) : item.lock(timeout2, this.uuid, this.nextLockId());
            this.cache.update(key, lock);
            Lock lock2 = lock;
            return lock2;
        }
        finally {
            this.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void release(Object key, SoftLock lock) throws CacheException {
        this.writeLock(key);
        try {
            Lockable item = (Lockable)this.cache.get(key);
            if (item != null && item.isUnlockable(lock)) {
                this.decrementLock(key, (Lock)item);
            } else {
                this.handleLockExpiry(key);
            }
        }
        finally {
            this.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean afterUpdate(Object key, Object value, Object version, SoftLock softlock) throws CacheException {
        this.writeLock(key);
        try {
            Lockable item = (Lockable)this.cache.get(key);
            if (item != null && item.isUnlockable(softlock)) {
                Lock lock = (Lock)item;
                if (lock.wasLockedConcurrently()) {
                    this.decrementLock(key, lock);
                    boolean bl = false;
                    return bl;
                }
                this.cache.update(key, new Item(value, version, this.cache.nextTimestamp()));
                boolean bl = true;
                return bl;
            }
            this.handleLockExpiry(key);
            boolean bl = false;
            return bl;
        }
        finally {
            this.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        this.writeLock(key);
        try {
            Lockable item = (Lockable)this.cache.get(key);
            if (item == null) {
                this.cache.update(key, new Item(value, version, this.cache.nextTimestamp()));
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        finally {
            this.writeUnlock(key);
        }
    }

    public void evict(Object key) throws CacheException {
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return false;
    }

    public boolean insert(Object key, Object value, Object currentVersion) throws CacheException {
        return false;
    }

    private long nextLockId() {
        return this.nextLockId.getAndIncrement();
    }

    private void decrementLock(Object key, Lock lock) {
        lock.unlock(this.cache.nextTimestamp());
        this.cache.update(key, lock);
    }

    private void handleLockExpiry(Object key) {
        long ts = this.cache.nextTimestamp() + (long)this.cache.getTimeout();
        Lock lock = new Lock(ts, this.uuid, this.nextLockId.getAndIncrement(), null);
        lock.unlock(ts);
        this.cache.update(key, lock);
    }

    private void writeLock(Object key) {
        if (this.cache.canLockEntries()) {
            this.cache.lock(key);
        } else {
            this.coarseWriteLock.lock();
        }
    }

    private void writeUnlock(Object key) {
        if (this.cache.canLockEntries()) {
            this.cache.unlock(key);
        } else {
            this.coarseWriteLock.unlock();
        }
    }

    private void readLockIfCoarse(Object key) {
        if (!this.cache.canLockEntries()) {
            this.coarseReadLock.lock();
        }
    }

    private void readUnlockIfCoarse(Object key) {
        if (!this.cache.canLockEntries()) {
            this.coarseReadLock.unlock();
        }
    }

    private static final class Lock
    implements Serializable,
    Lockable,
    SoftLock {
        private static final long serialVersionUID = 2L;
        private final UUID sourceUuid;
        private final long lockId;
        private final Object version;
        private long timeout;
        private boolean concurrent;
        private int multiplicity;
        private long unlockTimestamp;

        Lock(long timeout2, UUID sourceUuid, long lockId, Object version) {
            this.timeout = timeout2;
            this.lockId = lockId;
            this.version = version;
            this.sourceUuid = sourceUuid;
        }

        @Override
        public boolean isReadable(long txTimestamp) {
            return false;
        }

        @Override
        public boolean isWriteable(long txTimestamp, Object newVersion, Comparator versionComparator) {
            if (txTimestamp > this.timeout) {
                return true;
            }
            if (this.multiplicity > 0) {
                return false;
            }
            return this.version == null ? txTimestamp > this.unlockTimestamp : versionComparator.compare(this.version, newVersion) < 0;
        }

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public boolean isUnlockable(SoftLock lock) {
            return this.equals(lock);
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Lock) {
                return this.lockId == ((Lock)o).lockId && this.sourceUuid.equals(((Lock)o).sourceUuid);
            }
            return false;
        }

        public int hashCode() {
            int hash = this.sourceUuid != null ? this.sourceUuid.hashCode() : 0;
            int temp = (int)this.lockId;
            for (int i = 1; i < 2; ++i) {
                temp = (int)((long)temp ^ this.lockId >>> i * 32);
            }
            return hash + temp;
        }

        private boolean wasLockedConcurrently() {
            return this.concurrent;
        }

        @Override
        public Lock lock(long timeout2, UUID uuid, long lockId) {
            this.concurrent = true;
            ++this.multiplicity;
            this.timeout = timeout2;
            return this;
        }

        private void unlock(long timestamp) {
            if (--this.multiplicity == 0) {
                this.unlockTimestamp = timestamp;
            }
        }
    }

    private static final class Item
    implements Serializable,
    Lockable {
        private static final long serialVersionUID = 1L;
        private final Object value;
        private final Object version;
        private final long timestamp;

        private Item(Object value, Object version, long timestamp) {
            this.value = value;
            this.version = version;
            this.timestamp = timestamp;
        }

        @Override
        public boolean isReadable(long txTimestamp) {
            return txTimestamp > this.timestamp;
        }

        @Override
        public boolean isWriteable(long txTimestamp, Object newVersion, Comparator versionComparator) {
            return this.version != null && versionComparator.compare(this.version, newVersion) < 0;
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        @Override
        public boolean isUnlockable(SoftLock lock) {
            return false;
        }

        @Override
        public Lock lock(long timeout2, UUID uuid, long lockId) {
            return new Lock(timeout2, uuid, lockId, this.version);
        }
    }

    private static interface Lockable {
        public boolean isReadable(long var1);

        public boolean isWriteable(long var1, Object var3, Comparator var4);

        public Object getValue();

        public boolean isUnlockable(SoftLock var1);

        public Lock lock(long var1, UUID var3, long var4);
    }
}

