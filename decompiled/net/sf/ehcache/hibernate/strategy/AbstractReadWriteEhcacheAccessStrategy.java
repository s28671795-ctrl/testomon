/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.access.SoftLock
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.strategy;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import net.sf.ehcache.hibernate.regions.EhcacheTransactionalDataRegion;
import net.sf.ehcache.hibernate.strategy.AbstractEhcacheAccessStrategy;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.access.SoftLock;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractReadWriteEhcacheAccessStrategy<T extends EhcacheTransactionalDataRegion>
extends AbstractEhcacheAccessStrategy<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractReadWriteEhcacheAccessStrategy.class);
    private final UUID uuid = UUID.randomUUID();
    private final AtomicLong nextLockId = new AtomicLong();
    private final Comparator versionComparator;

    public AbstractReadWriteEhcacheAccessStrategy(T region, Settings settings) {
        super(region, settings);
        this.versionComparator = ((EhcacheTransactionalDataRegion)region).getCacheDataDescription().getVersionComparator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Object get(Object key, long txTimestamp) throws CacheException {
        this.readLockIfNeeded(key);
        try {
            boolean readable;
            Lockable item = (Lockable)this.region.get(key);
            boolean bl = readable = item != null && item.isReadable(txTimestamp);
            if (readable) {
                Object object = item.getValue();
                return object;
            }
            Object var6_6 = null;
            return var6_6;
        }
        finally {
            this.readUnlockIfNeeded(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        this.region.writeLock(key);
        try {
            boolean writeable;
            Lockable item = (Lockable)this.region.get(key);
            boolean bl = writeable = item == null || item.isWriteable(txTimestamp, version, this.versionComparator);
            if (writeable) {
                this.region.put(key, new Item(value, version, this.region.nextTimestamp()));
                boolean bl2 = true;
                return bl2;
            }
            boolean bl3 = false;
            return bl3;
        }
        finally {
            this.region.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final SoftLock lockItem(Object key, Object version) throws CacheException {
        this.region.writeLock(key);
        try {
            Lockable item = (Lockable)this.region.get(key);
            long timeout2 = this.region.nextTimestamp() + (long)this.region.getTimeout();
            Lock lock = item == null ? new Lock(timeout2, this.uuid, this.nextLockId(), version) : item.lock(timeout2, this.uuid, this.nextLockId());
            this.putLock(key, lock);
            Lock lock2 = lock;
            return lock2;
        }
        finally {
            this.region.writeUnlock(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void unlockItem(Object key, SoftLock lock) throws CacheException {
        this.region.writeLock(key);
        try {
            Lockable item = (Lockable)this.region.get(key);
            if (item != null && item.isUnlockable(lock)) {
                this.decrementLock(key, (Lock)item);
            } else {
                this.handleMissingLock(key, item);
            }
        }
        finally {
            this.region.writeUnlock(key);
        }
    }

    private long nextLockId() {
        return this.nextLockId.getAndIncrement();
    }

    protected void decrementLock(Object key, Lock lock) {
        lock.unlock(this.region.nextTimestamp());
        this.putLock(key, lock);
    }

    protected void handleMissingLock(Object key, Lockable lock) {
        LOG.error("Cache " + this.region.getName() + " Key " + key + " Lockable : " + lock + "\nA soft-locked cache entry was removed already. Out of balance lock/unlock sequences ?");
        long ts = this.region.nextTimestamp() + (long)this.region.getTimeout();
        Lock newLock = new Lock(ts, this.uuid, this.nextLockId.getAndIncrement(), null);
        newLock.unlock(ts);
        this.putLock(key, newLock);
    }

    private void putLock(Object key, Lock newLock) {
        if (newLock.isLocked()) {
            this.region.putEternal(key, newLock);
        } else {
            this.region.put(key, newLock);
        }
    }

    private void readLockIfNeeded(Object key) {
        if (this.region.locksAreIndependentOfCache()) {
            this.region.readLock(key);
        }
    }

    private void readUnlockIfNeeded(Object key) {
        if (this.region.locksAreIndependentOfCache()) {
            this.region.readUnlock(key);
        }
    }

    protected static final class Lock
    implements Serializable,
    Lockable,
    SoftLock {
        private static final long serialVersionUID = 2L;
        private final UUID sourceUuid;
        private final long lockId;
        private final Object version;
        private long timeout;
        private boolean concurrent;
        private int multiplicity = 1;
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

        public boolean wasLockedConcurrently() {
            return this.concurrent;
        }

        @Override
        public Lock lock(long timeout2, UUID uuid, long lockId) {
            this.concurrent = true;
            ++this.multiplicity;
            this.timeout = timeout2;
            return this;
        }

        public void unlock(long timestamp) {
            if (--this.multiplicity == 0) {
                this.unlockTimestamp = timestamp;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Lock Source-UUID:" + this.sourceUuid + " Lock-ID:" + this.lockId);
            return sb.toString();
        }

        public boolean isLocked() {
            return this.multiplicity != 0;
        }
    }

    protected static final class Item
    implements Serializable,
    Lockable {
        private static final long serialVersionUID = 1L;
        private final Object value;
        private final Object version;
        private final long timestamp;

        Item(Object value, Object version, long timestamp) {
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

    protected static interface Lockable {
        public boolean isReadable(long var1);

        public boolean isWriteable(long var1, Object var3, Comparator var4);

        public Object getValue();

        public boolean isUnlockable(SoftLock var1);

        public Lock lock(long var1, UUID var3, long var4);
    }
}

