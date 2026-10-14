/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.session.SessionContext;
import org.eclipse.jetty.server.session.SessionData;
import org.eclipse.jetty.server.session.SessionDataStore;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public abstract class AbstractSessionDataStore
extends ContainerLifeCycle
implements SessionDataStore {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSessionDataStore.class);
    public static final int DEFAULT_GRACE_PERIOD_SEC = 3600;
    public static final int DEFAULT_SAVE_PERIOD_SEC = 0;
    protected SessionContext _context;
    protected int _gracePeriodSec = 3600;
    protected long _lastExpiryCheckTime = 0L;
    protected long _lastOrphanSweepTime = 0L;
    protected int _savePeriodSec = 0;

    public abstract boolean doExists(String var1) throws Exception;

    public abstract void doStore(String var1, SessionData var2, long var3) throws Exception;

    public abstract SessionData doLoad(String var1) throws Exception;

    public abstract Set<String> doCheckExpired(Set<String> var1, long var2);

    public abstract Set<String> doGetExpired(long var1);

    public abstract void doCleanOrphans(long var1);

    @Override
    public void initialize(SessionContext context) throws Exception {
        if (this.isStarted()) {
            throw new IllegalStateException("Context set after SessionDataStore started");
        }
        this._context = context;
    }

    public void cleanOrphans(long timeLimit) {
        if (!this.isStarted()) {
            throw new IllegalStateException("Not started");
        }
        Runnable r = () -> this.doCleanOrphans(timeLimit);
        this._context.run(r);
    }

    @Override
    public SessionData load(String id) throws Exception {
        if (!this.isStarted()) {
            throw new IllegalStateException("Not started");
        }
        FuturePromise result = new FuturePromise();
        Runnable r = () -> {
            try {
                result.succeeded(this.doLoad(id));
            }
            catch (Exception e) {
                result.failed(e);
            }
        };
        this._context.run(r);
        return (SessionData)result.getOrThrow();
    }

    @Override
    public void store(String id, SessionData data) throws Exception {
        long savePeriodMs;
        if (!this.isStarted()) {
            throw new IllegalStateException("Not started");
        }
        if (data == null) {
            return;
        }
        long lastSave = data.getLastSaved();
        long l = savePeriodMs = this._savePeriodSec <= 0 ? 0L : TimeUnit.SECONDS.toMillis(this._savePeriodSec);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Store: id={}, mdirty={}, dirty={}, lsave={}, period={}, elapsed={}", id, data.isMetaDataDirty(), data.isDirty(), data.getLastSaved(), savePeriodMs, System.currentTimeMillis() - lastSave);
        }
        if (data.isDirty() || lastSave <= 0L || data.isMetaDataDirty() && System.currentTimeMillis() - lastSave >= savePeriodMs) {
            data.setLastSaved(System.currentTimeMillis());
            FuturePromise result = new FuturePromise();
            Runnable r = () -> {
                try {
                    this.doStore(id, data, lastSave);
                    data.clean();
                    result.succeeded(null);
                }
                catch (Exception e) {
                    data.setLastSaved(lastSave);
                    result.failed(e);
                }
            };
            this._context.run(r);
            result.getOrThrow();
        }
    }

    @Override
    public boolean exists(String id) throws Exception {
        FuturePromise result = new FuturePromise();
        Runnable r = () -> {
            try {
                result.succeeded(this.doExists(id));
            }
            catch (Exception e) {
                result.failed(e);
            }
        };
        this._context.run(r);
        return (Boolean)result.getOrThrow();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> getExpired(Set<String> candidates) {
        if (!this.isStarted()) {
            throw new IllegalStateException("Not started");
        }
        long now = System.currentTimeMillis();
        HashSet<String> expired = new HashSet<String>();
        Runnable r = () -> {
            Set<String> expiredCandidates = this.doCheckExpired(candidates, now);
            if (expiredCandidates != null) {
                expired.addAll(expiredCandidates);
            }
        };
        this._context.run(r);
        try {
            long t2 = 0L;
            if (this._lastExpiryCheckTime <= 0L) {
                t2 = now - TimeUnit.SECONDS.toMillis(this._gracePeriodSec * 3);
            } else if (now > this._lastExpiryCheckTime + TimeUnit.SECONDS.toMillis(this._gracePeriodSec)) {
                t2 = now - TimeUnit.SECONDS.toMillis(this._gracePeriodSec);
            }
            if (t2 > 0L) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Searching for sessions expired before {} for context {}", (Object)t2, (Object)this._context.getCanonicalContextPath());
                }
                long expiryTime = t2;
                r = () -> {
                    Set<String> tmp = this.doGetExpired(expiryTime);
                    if (tmp != null) {
                        expired.addAll(tmp);
                    }
                };
                this._context.run(r);
            }
        }
        finally {
            this._lastExpiryCheckTime = now;
        }
        try {
            if (now > this._lastOrphanSweepTime + TimeUnit.SECONDS.toMillis(10 * this._gracePeriodSec)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Cleaning orphans at {}, last sweep at {}", (Object)now, (Object)this._lastOrphanSweepTime);
                }
                this.cleanOrphans(now - TimeUnit.SECONDS.toMillis(10 * this._gracePeriodSec));
            }
        }
        finally {
            this._lastOrphanSweepTime = now;
        }
        return expired;
    }

    @Override
    public SessionData newSessionData(String id, long created, long accessed, long lastAccessed, long maxInactiveMs) {
        return new SessionData(id, this._context.getCanonicalContextPath(), this._context.getVhost(), created, accessed, lastAccessed, maxInactiveMs);
    }

    protected void checkStarted() throws IllegalStateException {
        if (this.isStarted()) {
            throw new IllegalStateException("Already started");
        }
    }

    @Override
    protected void doStart() throws Exception {
        if (this._context == null) {
            throw new IllegalStateException("No SessionContext");
        }
        super.doStart();
    }

    @ManagedAttribute(value="interval in secs to prevent too eager session scavenging", readonly=true)
    public int getGracePeriodSec() {
        return this._gracePeriodSec;
    }

    public void setGracePeriodSec(int sec) {
        this._gracePeriodSec = sec;
    }

    @ManagedAttribute(value="min secs between saves", readonly=true)
    public int getSavePeriodSec() {
        return this._savePeriodSec;
    }

    public void setSavePeriodSec(int savePeriodSec) {
        this._savePeriodSec = savePeriodSec;
    }

    @Override
    public String toString() {
        return String.format("%s@%x[passivating=%b,graceSec=%d]", this.getClass().getName(), this.hashCode(), this.isPassivating(), this.getGracePeriodSec());
    }
}

