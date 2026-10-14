/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import org.apache.activemq.broker.LockableServiceSupport;
import org.apache.activemq.broker.Locker;
import org.apache.activemq.util.ServiceSupport;

public abstract class AbstractLocker
extends ServiceSupport
implements Locker {
    public static final long DEFAULT_LOCK_ACQUIRE_SLEEP_INTERVAL = 10000L;
    protected String name;
    protected boolean failIfLocked = false;
    protected long lockAcquireSleepInterval = 10000L;
    protected LockableServiceSupport lockable;

    @Override
    public boolean keepAlive() throws IOException {
        return true;
    }

    @Override
    public void setLockAcquireSleepInterval(long lockAcquireSleepInterval) {
        this.lockAcquireSleepInterval = lockAcquireSleepInterval;
    }

    public long getLockAcquireSleepInterval() {
        return this.lockAcquireSleepInterval;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setFailIfLocked(boolean failIfLocked) {
        this.failIfLocked = failIfLocked;
    }

    @Override
    public void setLockable(LockableServiceSupport lockableServiceSupport) {
        this.lockable = lockableServiceSupport;
    }
}

