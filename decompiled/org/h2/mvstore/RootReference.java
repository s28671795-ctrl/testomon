/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import org.h2.mvstore.Page;

public final class RootReference<K, V> {
    public final Page<K, V> root;
    public final long version;
    private final byte holdCount;
    private final long ownerId;
    volatile RootReference<K, V> previous;
    final long updateCounter;
    final long updateAttemptCounter;
    private final byte appendCounter;

    RootReference(Page<K, V> page, long l) {
        this.root = page;
        this.version = l;
        this.previous = null;
        this.updateCounter = 1L;
        this.updateAttemptCounter = 1L;
        this.holdCount = 0;
        this.ownerId = 0L;
        this.appendCounter = 0;
    }

    private RootReference(RootReference<K, V> rootReference, Page<K, V> page, long l) {
        this.root = page;
        this.version = rootReference.version;
        this.previous = rootReference.previous;
        this.updateCounter = rootReference.updateCounter + 1L;
        this.updateAttemptCounter = rootReference.updateAttemptCounter + l;
        this.holdCount = 0;
        this.ownerId = 0L;
        this.appendCounter = rootReference.appendCounter;
    }

    private RootReference(RootReference<K, V> rootReference, int n) {
        this.root = rootReference.root;
        this.version = rootReference.version;
        this.previous = rootReference.previous;
        this.updateCounter = rootReference.updateCounter + 1L;
        this.updateAttemptCounter = rootReference.updateAttemptCounter + (long)n;
        assert (rootReference.holdCount == 0 || rootReference.ownerId == Thread.currentThread().getId()) : Thread.currentThread().getId() + " " + rootReference;
        this.holdCount = (byte)(rootReference.holdCount + 1);
        this.ownerId = Thread.currentThread().getId();
        this.appendCounter = rootReference.appendCounter;
    }

    private RootReference(RootReference<K, V> rootReference, Page<K, V> page, boolean bl, int n) {
        this.root = page;
        this.version = rootReference.version;
        this.previous = rootReference.previous;
        this.updateCounter = rootReference.updateCounter;
        this.updateAttemptCounter = rootReference.updateAttemptCounter;
        assert (rootReference.holdCount > 0 && rootReference.ownerId == Thread.currentThread().getId()) : Thread.currentThread().getId() + " " + rootReference;
        this.holdCount = (byte)(rootReference.holdCount - (bl ? (byte)0 : 1));
        this.ownerId = this.holdCount == 0 ? 0L : Thread.currentThread().getId();
        this.appendCounter = (byte)n;
    }

    private RootReference(RootReference<K, V> rootReference, long l, int n) {
        RootReference<K, V> rootReference2;
        RootReference<K, V> rootReference3 = rootReference;
        while ((rootReference2 = rootReference3.previous) != null && rootReference2.root == rootReference.root) {
            rootReference3 = rootReference2;
        }
        this.root = rootReference.root;
        this.version = l;
        this.previous = rootReference3;
        this.updateCounter = rootReference.updateCounter + 1L;
        this.updateAttemptCounter = rootReference.updateAttemptCounter + (long)n;
        this.holdCount = rootReference.holdCount == 0 ? (byte)0 : (byte)(rootReference.holdCount - 1);
        long l2 = this.ownerId = this.holdCount == 0 ? 0L : rootReference.ownerId;
        assert (rootReference.appendCounter == 0);
        this.appendCounter = 0;
    }

    RootReference<K, V> updateRootPage(Page<K, V> page, long l) {
        return this.isFree() ? this.tryUpdate(new RootReference<K, V>(this, page, l)) : null;
    }

    RootReference<K, V> tryLock(int n) {
        return this.canUpdate() ? this.tryUpdate(new RootReference<K, V>(this, n)) : null;
    }

    RootReference<K, V> tryUnlockAndUpdateVersion(long l, int n) {
        return this.canUpdate() ? this.tryUpdate(new RootReference<K, V>(this, l, n)) : null;
    }

    RootReference<K, V> updatePageAndLockedStatus(Page<K, V> page, boolean bl, int n) {
        return this.canUpdate() ? this.tryUpdate(new RootReference<K, V>(this, page, bl, n)) : null;
    }

    void removeUnusedOldVersions(long l) {
        RootReference<K, V> rootReference = this;
        while (rootReference != null) {
            if (rootReference.version < l) {
                RootReference<K, V> rootReference2;
                assert ((rootReference2 = rootReference.previous) == null || rootReference2.getAppendCounter() == 0) : l + " " + rootReference.previous;
                rootReference.previous = null;
            }
            rootReference = rootReference.previous;
        }
    }

    boolean isLocked() {
        return this.holdCount != 0;
    }

    private boolean isFree() {
        return this.holdCount == 0;
    }

    private boolean canUpdate() {
        return this.isFree() || this.ownerId == Thread.currentThread().getId();
    }

    public boolean isLockedByCurrentThread() {
        return this.holdCount != 0 && this.ownerId == Thread.currentThread().getId();
    }

    private RootReference<K, V> tryUpdate(RootReference<K, V> rootReference) {
        assert (this.canUpdate());
        return this.root.map.compareAndSetRoot(this, rootReference) ? rootReference : null;
    }

    long getVersion() {
        RootReference<K, V> rootReference = this.previous;
        return rootReference == null || rootReference.root != this.root || rootReference.appendCounter != this.appendCounter ? this.version : rootReference.getVersion();
    }

    boolean hasChangesSince(long l, boolean bl) {
        return bl && (!this.root.isSaved() ? this.getTotalCount() > 0L : this.getAppendCounter() > 0) || this.getVersion() > l;
    }

    int getAppendCounter() {
        return this.appendCounter & 0xFF;
    }

    public boolean needFlush() {
        return this.appendCounter != 0;
    }

    public long getTotalCount() {
        return this.root.getTotalCount() + (long)this.getAppendCounter();
    }

    public String toString() {
        return "RootReference(" + System.identityHashCode(this.root) + ", v=" + this.version + ", owner=" + this.ownerId + (this.ownerId == Thread.currentThread().getId() ? "(current)" : "") + ", holdCnt=" + this.holdCount + ", keys=" + this.root.getTotalCount() + ", append=" + this.getAppendCounter() + ")";
    }
}

