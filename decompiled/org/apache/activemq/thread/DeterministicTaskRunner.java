/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import java.util.concurrent.Executor;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;

public class DeterministicTaskRunner
implements TaskRunner {
    private final Executor executor;
    private final Task task;
    private final Runnable runable;
    private boolean shutdown;

    public DeterministicTaskRunner(Executor executor2, Task task) {
        this.executor = executor2;
        this.task = task;
        this.runable = new Runnable(){

            @Override
            public void run() {
                Thread.currentThread();
                DeterministicTaskRunner.this.runTask();
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
            if (this.shutdown) {
                return;
            }
            this.executor.execute(this.runable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(long timeout2) throws InterruptedException {
        Runnable runnable2 = this.runable;
        synchronized (runnable2) {
            this.shutdown = true;
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
            if (this.shutdown) {
                this.runable.notifyAll();
                return;
            }
        }
        this.task.iterate();
    }
}

