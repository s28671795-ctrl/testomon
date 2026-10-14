/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class HouseKeeper
extends AbstractLifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(HouseKeeper.class);
    public static final long DEFAULT_PERIOD_MS = 600000L;
    private final AutoLock _lock = new AutoLock();
    protected SessionIdManager _sessionIdManager;
    protected Scheduler _scheduler;
    protected Scheduler.Task _task;
    protected Runner _runner;
    protected boolean _ownScheduler = false;
    private long _intervalMs = 600000L;

    public void setSessionIdManager(SessionIdManager sessionIdManager) {
        if (this.isStarted()) {
            throw new IllegalStateException("HouseKeeper started");
        }
        this._sessionIdManager = sessionIdManager;
    }

    @Override
    protected void doStart() throws Exception {
        if (this._sessionIdManager == null) {
            throw new IllegalStateException("No SessionIdManager for Housekeeper");
        }
        this.setIntervalSec(this.getIntervalSec());
        super.doStart();
    }

    protected void startScavenging() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._scheduler == null) {
                if (this._sessionIdManager instanceof DefaultSessionIdManager) {
                    this._scheduler = ((DefaultSessionIdManager)this._sessionIdManager).getServer().getBean(Scheduler.class);
                }
                if (this._scheduler == null) {
                    this._scheduler = new ScheduledExecutorScheduler(String.format("Session-HouseKeeper-%x", this.hashCode()), false);
                    this._ownScheduler = true;
                    this._scheduler.start();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} using own scheduler for scavenging", (Object)this._sessionIdManager.getWorkerName());
                    }
                } else if (!this._scheduler.isStarted()) {
                    throw new IllegalStateException("Shared scheduler not started");
                }
            }
            if (this._task != null) {
                this._task.cancel();
            }
            if (this._runner == null) {
                this._runner = new Runner();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} scavenging every {}ms", (Object)this._sessionIdManager.getWorkerName(), (Object)this._intervalMs);
            }
            this._task = this._scheduler.schedule(this._runner, this._intervalMs, TimeUnit.MILLISECONDS);
        }
    }

    protected void stopScavenging() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._task != null) {
                this._task.cancel();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} stopped scavenging", (Object)this._sessionIdManager.getWorkerName());
                }
            }
            this._task = null;
            if (this._ownScheduler && this._scheduler != null) {
                this._ownScheduler = false;
                this._scheduler.stop();
                this._scheduler = null;
            }
            this._runner = null;
        }
    }

    @Override
    protected void doStop() throws Exception {
        try (AutoLock l = this._lock.lock();){
            this.stopScavenging();
            this._scheduler = null;
        }
        super.doStop();
    }

    public void setIntervalSec(long sec) throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this.isStarted() || this.isStarting()) {
                if (sec <= 0L) {
                    this._intervalMs = 0L;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} scavenging disabled", (Object)this._sessionIdManager.getWorkerName());
                    }
                    this.stopScavenging();
                } else {
                    if (sec < 10L) {
                        LOG.warn("{} short interval of {}sec for session scavenging.", (Object)this._sessionIdManager.getWorkerName(), (Object)sec);
                    }
                    this._intervalMs = sec * 1000L;
                    long tenPercent = this._intervalMs / 10L;
                    if (System.currentTimeMillis() % 2L == 0L) {
                        this._intervalMs += tenPercent;
                    }
                    if (this.isStarting() || this.isStarted()) {
                        this.startScavenging();
                    }
                }
            } else {
                this._intervalMs = sec * 1000L;
            }
        }
    }

    @ManagedAttribute(value="secs between scavenge cycles", readonly=true)
    public long getIntervalSec() {
        try (AutoLock l = this._lock.lock();){
            long l2 = this._intervalMs / 1000L;
            return l2;
        }
    }

    public void scavenge() {
        if (this.isStopping() || this.isStopped()) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} scavenging sessions", (Object)this._sessionIdManager.getWorkerName());
        }
        for (SessionHandler manager : this._sessionIdManager.getSessionHandlers()) {
            if (manager == null) continue;
            try {
                manager.scavenge();
            }
            catch (Exception e) {
                LOG.warn("Unable to scavenge", e);
            }
        }
    }

    @Override
    public String toString() {
        try (AutoLock l = this._lock.lock();){
            String string = super.toString() + "[interval=" + this._intervalMs + ", ownscheduler=" + this._ownScheduler + "]";
            return string;
        }
    }

    protected class Runner
    implements Runnable {
        protected Runner() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                HouseKeeper.this.scavenge();
            }
            finally {
                try (AutoLock l = HouseKeeper.this._lock.lock();){
                    if (HouseKeeper.this._scheduler != null && HouseKeeper.this._scheduler.isRunning()) {
                        HouseKeeper.this._task = HouseKeeper.this._scheduler.schedule(this, HouseKeeper.this._intervalMs, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }
}

