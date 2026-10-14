/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

class SerializeReentrantCallsDirectExecutor
implements Executor {
    private static final Logger log = Logger.getLogger(SerializeReentrantCallsDirectExecutor.class.getName());
    private boolean executing;
    private ArrayDeque<Runnable> taskQueue;

    SerializeReentrantCallsDirectExecutor() {
    }

    @Override
    public void execute(Runnable task) {
        Preconditions.checkNotNull(task, "'task' must not be null.");
        if (!this.executing) {
            this.executing = true;
            try {
                task.run();
            }
            catch (Throwable t2) {
                log.log(Level.SEVERE, "Exception while executing runnable " + task, t2);
            }
            finally {
                if (this.taskQueue != null) {
                    this.completeQueuedTasks();
                }
                this.executing = false;
            }
        } else {
            this.enqueue(task);
        }
    }

    private void completeQueuedTasks() {
        Runnable task = null;
        while ((task = this.taskQueue.poll()) != null) {
            try {
                task.run();
            }
            catch (Throwable t2) {
                log.log(Level.SEVERE, "Exception while executing runnable " + task, t2);
            }
        }
    }

    private void enqueue(Runnable r) {
        if (this.taskQueue == null) {
            this.taskQueue = new ArrayDeque(4);
        }
        this.taskQueue.add(r);
    }
}

