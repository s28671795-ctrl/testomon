/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.io.SelectorManager;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.ManagedOperation;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.statistic.RateStatistic;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class AcceptRateLimit
extends AbstractLifeCycle
implements SelectorManager.AcceptListener,
Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(AcceptRateLimit.class);
    private final AutoLock _lock = new AutoLock();
    private final Server _server;
    private final List<AbstractConnector> _connectors = new ArrayList<AbstractConnector>();
    private final Rate _rate;
    private final int _acceptRateLimit;
    private boolean _limiting;
    private Scheduler.Task _task;

    public AcceptRateLimit(@Name(value="acceptRateLimit") int acceptRateLimit, @Name(value="period") long period, @Name(value="units") TimeUnit units, @Name(value="server") Server server) {
        this._server = server;
        this._acceptRateLimit = acceptRateLimit;
        this._rate = new Rate(period, units);
    }

    public AcceptRateLimit(@Name(value="limit") int limit, @Name(value="period") long period, @Name(value="units") TimeUnit units, Connector ... connectors) {
        this(limit, period, units, (Server)null);
        for (Connector c : connectors) {
            if (c instanceof AbstractConnector) {
                this._connectors.add((AbstractConnector)c);
                continue;
            }
            LOG.warn("Connector {} is not an AbstractConnector. Connections not limited", (Object)c);
        }
    }

    @ManagedAttribute(value="The accept rate limit")
    public int getAcceptRateLimit() {
        return this._acceptRateLimit;
    }

    @ManagedAttribute(value="The accept rate period")
    public long getPeriod() {
        return this._rate.getPeriod();
    }

    @ManagedAttribute(value="The accept rate period units")
    public TimeUnit getUnits() {
        return this._rate.getUnits();
    }

    @ManagedAttribute(value="The current accept rate")
    public int getRate() {
        return this._rate.getRate();
    }

    @ManagedAttribute(value="The maximum accept rate achieved")
    public long getMaxRate() {
        return this._rate.getMax();
    }

    @ManagedOperation(value="Resets the accept rate", impact="ACTION")
    public void reset() {
        try (AutoLock l = this._lock.lock();){
            this._rate.reset();
            if (this._limiting) {
                this._limiting = false;
                this.unlimit();
            }
        }
    }

    protected void age(long period, TimeUnit units) {
        this._rate.age(period, units);
    }

    @Override
    protected void doStart() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._server != null) {
                for (Connector c : this._server.getConnectors()) {
                    if (c instanceof AbstractConnector) {
                        this._connectors.add((AbstractConnector)c);
                        continue;
                    }
                    LOG.warn("Connector {} is not an AbstractConnector. Connections not limited", (Object)c);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("AcceptLimit accept<{} rate<{} in {}", this._acceptRateLimit, this._rate, this._connectors);
            }
            for (AbstractConnector c : this._connectors) {
                c.addBean(this);
            }
        }
    }

    @Override
    protected void doStop() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._task != null) {
                this._task.cancel();
            }
            this._task = null;
            for (AbstractConnector c : this._connectors) {
                c.removeBean(this);
            }
            if (this._server != null) {
                this._connectors.clear();
            }
            this._limiting = false;
        }
    }

    protected void limit() {
        for (AbstractConnector c : this._connectors) {
            c.setAccepting(false);
        }
        this.schedule();
    }

    protected void unlimit() {
        for (AbstractConnector c : this._connectors) {
            c.setAccepting(true);
        }
    }

    @Override
    public void onAccepting(SelectableChannel channel) {
        try (AutoLock l = this._lock.lock();){
            int rate = this._rate.record();
            if (LOG.isDebugEnabled()) {
                LOG.debug("onAccepting rate {}/{} for {} {}", rate, this._acceptRateLimit, this._rate, channel);
            }
            if (rate > this._acceptRateLimit && !this._limiting) {
                this._limiting = true;
                LOG.warn("AcceptLimit rate exceeded {}>{} on {}", rate, this._acceptRateLimit, this._connectors);
                this.limit();
            }
        }
    }

    private void schedule() {
        long oldest = this._rate.getOldest(TimeUnit.MILLISECONDS);
        long period = TimeUnit.MILLISECONDS.convert(this._rate.getPeriod(), this._rate.getUnits());
        long delay = period - (oldest > 0L ? oldest : 0L);
        if (delay < 0L) {
            delay = 0L;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("schedule {} {}", (Object)delay, (Object)TimeUnit.MILLISECONDS);
        }
        this._task = this._connectors.get(0).getScheduler().schedule(this, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        try (AutoLock l = this._lock.lock();){
            this._task = null;
            if (!this.isRunning()) {
                return;
            }
            int rate = this._rate.getRate();
            if (rate > this._acceptRateLimit) {
                this.schedule();
                return;
            }
            if (this._limiting) {
                this._limiting = false;
                LOG.warn("AcceptLimit rate OK {}<={} on {}", rate, this._acceptRateLimit, this._connectors);
                this.unlimit();
            }
        }
    }

    private static final class Rate
    extends RateStatistic {
        private Rate(long period, TimeUnit units) {
            super(period, units);
        }

        @Override
        protected void age(long period, TimeUnit units) {
            super.age(period, units);
        }
    }
}

