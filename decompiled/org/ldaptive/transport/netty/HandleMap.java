/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.extended.UnsolicitedNotification;
import org.ldaptive.transport.DefaultOperationHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class HandleMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleMap.class);
    private static final String THROTTLE_REQUESTS_PROPERTY = "org.ldaptive.transport.netty.throttleRequests";
    private static final String THROTTLE_TIMEOUT_PROPERTY = "org.ldaptive.transport.netty.throttleTimeout";
    private static final int THROTTLE_REQUESTS = Integer.parseInt(System.getProperty("org.ldaptive.transport.netty.throttleRequests", "0"));
    private static final Duration THROTTLE_TIMEOUT = Duration.ofSeconds(Long.parseLong(System.getProperty("org.ldaptive.transport.netty.throttleTimeout", "60")));
    private final Map<Integer, DefaultOperationHandle> pending = new ConcurrentHashMap<Integer, DefaultOperationHandle>();
    private final AtomicBoolean notificationLock = new AtomicBoolean();
    private final Semaphore throttle = THROTTLE_REQUESTS > 0 ? new Semaphore(THROTTLE_REQUESTS) : null;
    private boolean open;

    HandleMap() {
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

    public boolean isOpen() {
        return this.open;
    }

    public DefaultOperationHandle get(int id) {
        return this.open ? this.pending.get(id) : null;
    }

    public DefaultOperationHandle remove(int id) {
        if (this.open) {
            DefaultOperationHandle handle2 = this.pending.remove(id);
            this.releaseThrottle(1);
            return handle2;
        }
        return null;
    }

    public DefaultOperationHandle put(int id, DefaultOperationHandle handle2) throws LdapException {
        if (!this.open) {
            throw new LdapException(ResultCode.CONNECT_ERROR, "Connection is closed, could not store handle " + handle2);
        }
        this.acquireThrottle();
        return this.pending.putIfAbsent(id, handle2);
    }

    public Collection<DefaultOperationHandle> handles() {
        return this.pending.values();
    }

    public int size() {
        return this.pending.size();
    }

    public void clear() {
        this.releaseThrottle(this.pending.size());
        this.pending.clear();
    }

    private void acquireThrottle() throws LdapException {
        if (this.throttle != null) {
            try {
                if (!this.throttle.tryAcquire(THROTTLE_TIMEOUT.toSeconds(), TimeUnit.SECONDS)) {
                    throw new LdapException(ResultCode.LOCAL_ERROR, "Could not acquire request semaphore");
                }
            }
            catch (InterruptedException e) {
                throw new LdapException(ResultCode.LOCAL_ERROR, "Could not acquire request semaphore", e);
            }
        }
    }

    private void releaseThrottle(int permits) {
        if (this.throttle != null) {
            this.throttle.release(permits);
        }
    }

    public void abandonRequests() {
        if (this.notificationLock.compareAndSet(false, true)) {
            try {
                Iterator<DefaultOperationHandle> i = this.pending.values().iterator();
                while (i.hasNext()) {
                    DefaultOperationHandle h2 = i.next();
                    if (h2.getSentTime() == null || h2.getReceivedTime() != null) continue;
                    i.remove();
                    this.releaseThrottle(1);
                    h2.abandon();
                }
            }
            finally {
                this.notificationLock.set(false);
            }
        } else {
            LOGGER.debug("Handle notification is already in progress");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyOperationHandles(LdapException e) {
        if (this.notificationLock.compareAndSet(false, true)) {
            try {
                Iterator<DefaultOperationHandle> i = this.pending.values().iterator();
                while (i.hasNext()) {
                    DefaultOperationHandle h2 = i.next();
                    i.remove();
                    this.releaseThrottle(1);
                    h2.exception(e);
                }
            }
            finally {
                this.notificationLock.set(false);
            }
        } else {
            LOGGER.debug("Handle notification is already in progress");
        }
    }

    public void notifyOperationHandles(UnsolicitedNotification notification) {
        if (this.notificationLock.compareAndSet(false, true)) {
            try {
                this.pending.values().forEach(h2 -> {
                    if (h2.getSentTime() != null && h2.getReceivedTime() == null) {
                        h2.unsolicitedNotification(notification);
                    }
                });
            }
            finally {
                this.notificationLock.set(false);
            }
        } else {
            LOGGER.debug("Handle notification is already in progress");
        }
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "open=" + this.open + ", " + "throttle=" + this.throttle + ", " + "handles=" + this.pending;
    }
}

