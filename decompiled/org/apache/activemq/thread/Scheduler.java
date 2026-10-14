/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.activemq.thread.SchedulerTimerTask;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Scheduler
extends ServiceSupport {
    private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
    private final String name;
    private Timer timer;
    private final HashMap<Runnable, TimerTask> timerTasks = new HashMap();

    public Scheduler(String name) {
        this.name = name;
    }

    public synchronized void executePeriodically(Runnable task, long period) {
        TimerTask existing = this.timerTasks.get(task);
        if (existing != null) {
            LOG.debug("Task {} already scheduled, cancelling and rescheduling", (Object)task);
            this.cancel(task);
        }
        SchedulerTimerTask timerTask2 = new SchedulerTimerTask(task);
        this.timer.schedule((TimerTask)timerTask2, period, period);
        this.timerTasks.put(task, timerTask2);
    }

    public synchronized void cancel(Runnable task) {
        TimerTask ticket = this.timerTasks.remove(task);
        if (ticket != null) {
            ticket.cancel();
            this.timer.purge();
        }
    }

    public synchronized void executeAfterDelay(Runnable task, long redeliveryDelay) {
        SchedulerTimerTask timerTask2 = new SchedulerTimerTask(task);
        this.timer.schedule((TimerTask)timerTask2, redeliveryDelay);
    }

    public void shutdown() {
        this.timer.cancel();
    }

    @Override
    protected synchronized void doStart() throws Exception {
        this.timer = new Timer(this.name, true);
    }

    @Override
    protected synchronized void doStop(ServiceStopper stopper) throws Exception {
        if (this.timer != null) {
            this.timer.cancel();
        }
    }

    public String getName() {
        return this.name;
    }
}

