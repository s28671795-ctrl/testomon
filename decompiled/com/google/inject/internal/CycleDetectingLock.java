/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface CycleDetectingLock<ID> {
    public ListMultimap<Thread, ID> lockOrDetectPotentialLocksCycle();

    public void unlock();

    public static class CycleDetectingLockFactory<ID> {
        private static Map<Thread, ReentrantCycleDetectingLock<?>> lockThreadIsWaitingOn = Maps.newHashMap();
        private static final Multimap<Thread, ReentrantCycleDetectingLock<?>> locksOwnedByThread = LinkedHashMultimap.create();

        CycleDetectingLock<ID> create(ID userLockId) {
            return new ReentrantCycleDetectingLock<ID>(this, userLockId, new ReentrantLock());
        }

        static class ReentrantCycleDetectingLock<ID>
        implements CycleDetectingLock<ID> {
            private final Lock lockImplementation;
            private final ID userLockId;
            private final CycleDetectingLockFactory<ID> lockFactory;
            private Thread lockOwnerThread = null;
            private int lockReentranceCount = 0;

            ReentrantCycleDetectingLock(CycleDetectingLockFactory<ID> lockFactory, ID userLockId, Lock lockImplementation) {
                this.lockFactory = lockFactory;
                this.userLockId = Preconditions.checkNotNull(userLockId, "userLockId");
                this.lockImplementation = Preconditions.checkNotNull(lockImplementation, "lockImplementation");
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public ListMultimap<Thread, ID> lockOrDetectPotentialLocksCycle() {
                Thread currentThread = Thread.currentThread();
                Class<CycleDetectingLockFactory> clazz = CycleDetectingLockFactory.class;
                synchronized (CycleDetectingLockFactory.class) {
                    this.checkState();
                    if (this.lockOwnerThread != currentThread) {
                        lockThreadIsWaitingOn.put(currentThread, this);
                        ListMultimap<Thread, ID> locksInCycle = this.detectPotentialLocksCycle();
                        if (!locksInCycle.isEmpty()) {
                            lockThreadIsWaitingOn.remove(currentThread);
                            // ** MonitorExit[var2_2] (shouldn't be in output)
                            return locksInCycle;
                        }
                    }
                    // ** MonitorExit[var2_2] (shouldn't be in output)
                    this.lockImplementation.lock();
                    clazz = CycleDetectingLockFactory.class;
                    synchronized (CycleDetectingLockFactory.class) {
                        lockThreadIsWaitingOn.remove(currentThread);
                        this.checkState();
                        this.lockOwnerThread = currentThread;
                        ++this.lockReentranceCount;
                        locksOwnedByThread.put(currentThread, this);
                        // ** MonitorExit[var2_2] (shouldn't be in output)
                        return ImmutableListMultimap.of();
                    }
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void unlock() {
                Thread currentThread = Thread.currentThread();
                Class<CycleDetectingLockFactory> clazz = CycleDetectingLockFactory.class;
                synchronized (CycleDetectingLockFactory.class) {
                    this.checkState();
                    Preconditions.checkState(this.lockOwnerThread != null, "Thread is trying to unlock a lock that is not locked");
                    Preconditions.checkState(this.lockOwnerThread == currentThread, "Thread is trying to unlock a lock owned by another thread");
                    this.lockImplementation.unlock();
                    --this.lockReentranceCount;
                    if (this.lockReentranceCount == 0) {
                        this.lockOwnerThread = null;
                        Preconditions.checkState(locksOwnedByThread.remove(currentThread, this), "Internal error: Can not find this lock in locks owned by a current thread");
                        if (locksOwnedByThread.get(currentThread).isEmpty()) {
                            locksOwnedByThread.removeAll(currentThread);
                        }
                    }
                    // ** MonitorExit[var2_2] (shouldn't be in output)
                    return;
                }
            }

            void checkState() throws IllegalStateException {
                Thread currentThread = Thread.currentThread();
                Preconditions.checkState(!lockThreadIsWaitingOn.containsKey(currentThread), "Internal error: Thread should not be in a waiting thread on a lock now");
                if (this.lockOwnerThread != null) {
                    Preconditions.checkState(this.lockReentranceCount >= 0, "Internal error: Lock ownership and reentrance count internal states do not match");
                    Preconditions.checkState(locksOwnedByThread.get(this.lockOwnerThread).contains(this), "Internal error: Set of locks owned by a current thread and lock ownership status do not match");
                } else {
                    Preconditions.checkState(this.lockReentranceCount == 0, "Internal error: Reentrance count of a non locked lock is expect to be zero");
                    Preconditions.checkState(!locksOwnedByThread.values().contains(this), "Internal error: Non locked lock should not be owned by any thread");
                }
            }

            private ListMultimap<Thread, ID> detectPotentialLocksCycle() {
                Thread currentThread = Thread.currentThread();
                if (this.lockOwnerThread == null || this.lockOwnerThread == currentThread) {
                    return ImmutableListMultimap.of();
                }
                Multimap potentialLocksCycle = MultimapBuilder.linkedHashKeys().arrayListValues().build();
                ReentrantCycleDetectingLock<?> lockOwnerWaitingOn = this;
                while (lockOwnerWaitingOn != null && lockOwnerWaitingOn.lockOwnerThread != null) {
                    Thread threadOwnerThreadWaits = lockOwnerWaitingOn.lockOwnerThread;
                    lockOwnerWaitingOn = this.addAllLockIdsAfter(threadOwnerThreadWaits, lockOwnerWaitingOn, (ListMultimap<Thread, ?>)potentialLocksCycle);
                    if (threadOwnerThreadWaits != currentThread) continue;
                    return potentialLocksCycle;
                }
                return ImmutableListMultimap.of();
            }

            private ReentrantCycleDetectingLock<?> addAllLockIdsAfter(Thread thread2, ReentrantCycleDetectingLock<?> lock, ListMultimap<Thread, ID> potentialLocksCycle) {
                boolean found = false;
                Collection ownedLocks = locksOwnedByThread.get(thread2);
                Preconditions.checkNotNull(ownedLocks, "Internal error: No locks were found taken by a thread");
                for (ReentrantCycleDetectingLock ownedLock : ownedLocks) {
                    if (ownedLock == lock) {
                        found = true;
                    }
                    if (!found || ownedLock.lockFactory != this.lockFactory) continue;
                    ID userLockId = ownedLock.userLockId;
                    potentialLocksCycle.put(thread2, userLockId);
                }
                Preconditions.checkState(found, "Internal error: We can not find locks that created a cycle that we detected");
                ReentrantCycleDetectingLock unownedLock = (ReentrantCycleDetectingLock)lockThreadIsWaitingOn.get(thread2);
                if (unownedLock != null && unownedLock.lockFactory == this.lockFactory) {
                    ID typed = unownedLock.userLockId;
                    potentialLocksCycle.put(thread2, typed);
                }
                return unownedLock;
            }

            public String toString() {
                Thread thread2 = this.lockOwnerThread;
                if (thread2 != null) {
                    return String.format("%s[%s][locked by %s]", super.toString(), this.userLockId, thread2);
                }
                return String.format("%s[%s][unlocked]", super.toString(), this.userLockId);
            }
        }
    }
}

