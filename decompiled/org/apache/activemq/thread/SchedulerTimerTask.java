/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import java.util.TimerTask;

public class SchedulerTimerTask
extends TimerTask {
    private final Runnable task;

    public SchedulerTimerTask(Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        this.task.run();
    }
}

