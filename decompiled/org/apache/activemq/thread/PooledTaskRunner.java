/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import java.util.concurrent.Executor;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PooledTaskRunner
implements TaskRunner {
    private static final Logger LOG = LoggerFactory.getLogger(PooledTaskRunner.class);
    private final int maxIterationsPerRun;
    private final Executor executor;
    private final Task task;
    private final Runnable runable;
    private boolean queued;
    private boolean shutdown;
    private boolean iterating;
    private volatile Thread runningThread;

    public PooledTaskRunner(Executor executor2, final Task task, int maxIterationsPerRun) {
        this.executor = executor2;
        this.maxIterationsPerRun = maxIterationsPerRun;
        this.task = task;
        this.runable = new Runnable(){

            @Override
            public void run() {
                PooledTaskRunner.this.runningThread = Thread.currentThread();
                try {
                    PooledTaskRunner.this.runTask();
                }
                finally {
                    LOG.trace("Run task done: {}", (Object)task);
                    PooledTaskRunner.this.runningThread = null;
                }
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void wakeup() throws InterruptedException {
        Runnable runnable2 = this.runable;
        synchronized (runnable2) {
            if (this.queued || this.shutdown) {
                return;
            }
            this.queued = true;
            if (!this.iterating) {
                this.executor.execute(this.runable);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(long timeout2) throws InterruptedException {
        LOG.trace("Shutdown timeout: {} task: {}", (Object)timeout2, (Object)this.task);
        Runnable runnable2 = this.runable;
        synchronized (runnable2) {
            this.shutdown = true;
            if (this.runningThread != Thread.currentThread() && this.iterating) {
                this.runable.wait(timeout2);
            }
        }
    }

    @Override
    public void shutdown() throws InterruptedException {
        this.shutdown(0L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void runTask() {
        Runnable runnable2 = this.runable;
        synchronized (runnable2) {
            this.queued = false;
            if (this.shutdown) {
                this.iterating = false;
                this.runable.notifyAll();
                return;
            }
            this.iterating = true;
        }
        boolean done = false;
        try {
            for (int i = 0; i < this.maxIterationsPerRun; ++i) {
                LOG.trace("Running task iteration {} - {}", (Object)i, (Object)this.task);
                if (this.task.iterate()) continue;
                done = true;
                break;
            }
        }
        finally {
            Runnable runnable3 = this.runable;
            synchronized (runnable3) {
                this.iterating = false;
                this.runable.notifyAll();
                if (this.shutdown) {
                    this.queued = false;
                    this.runable.notifyAll();
                } else {
                    if (!done) {
                        this.queued = true;
                    }
                    if (this.queued) {
                        this.executor.execute(this.runable);
                    }
                }
            }
        }
    }
}

