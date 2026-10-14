/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sweeper
extends AbstractLifeCycle
implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Sweeper.class);
    private final AtomicReference<List<Sweepable>> items = new AtomicReference();
    private final AtomicReference<Scheduler.Task> task = new AtomicReference();
    private final Scheduler scheduler;
    private final long period;

    public Sweeper(Scheduler scheduler, long period) {
        this.scheduler = scheduler;
        this.period = period;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this.items.set(new CopyOnWriteArrayList());
        this.activate();
    }

    @Override
    protected void doStop() throws Exception {
        this.deactivate();
        this.items.set(null);
        super.doStop();
    }

    public int getSize() {
        List<Sweepable> refs = this.items.get();
        return refs == null ? 0 : refs.size();
    }

    public boolean offer(Sweepable sweepable) {
        List<Sweepable> refs = this.items.get();
        if (refs == null) {
            return false;
        }
        refs.add(sweepable);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Resource offered {}", (Object)sweepable);
        }
        return true;
    }

    public boolean remove(Sweepable sweepable) {
        List<Sweepable> refs = this.items.get();
        return refs != null && refs.remove(sweepable);
    }

    @Override
    public void run() {
        List<Sweepable> refs = this.items.get();
        if (refs == null) {
            return;
        }
        for (Sweepable sweepable : refs) {
            try {
                if (!sweepable.sweep()) continue;
                refs.remove(sweepable);
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Resource swept {}", (Object)sweepable);
            }
            catch (Throwable x) {
                LOG.info("Exception while sweeping {}", (Object)sweepable, (Object)x);
            }
        }
        this.activate();
    }

    private void activate() {
        if (this.isRunning()) {
            Scheduler.Task t2 = this.scheduler.schedule(this, this.period, TimeUnit.MILLISECONDS);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Scheduled in {} ms sweep task {}", (Object)this.period, (Object)t2);
            }
            this.task.set(t2);
        } else if (LOG.isDebugEnabled()) {
            LOG.debug("Skipping sweep task scheduling");
        }
    }

    private void deactivate() {
        Scheduler.Task t2 = this.task.getAndSet(null);
        if (t2 != null) {
            boolean cancelled = t2.cancel();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cancelled ({}) sweep task {}", (Object)cancelled, (Object)t2);
            }
        }
    }

    public static interface Sweepable {
        public boolean sweep();
    }
}

