/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SynchronizationContext
implements Executor {
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();
    private final AtomicReference<Thread> drainingThread = new AtomicReference();

    public SynchronizationContext(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Preconditions.checkNotNull(uncaughtExceptionHandler, "uncaughtExceptionHandler");
    }

    public final void drain() {
        do {
            if (!this.drainingThread.compareAndSet(null, Thread.currentThread())) {
                return;
            }
            try {
                Runnable runnable2;
                while ((runnable2 = this.queue.poll()) != null) {
                    try {
                        runnable2.run();
                    }
                    catch (Throwable t2) {
                        this.uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), t2);
                    }
                }
            }
            finally {
                this.drainingThread.set(null);
            }
        } while (!this.queue.isEmpty());
    }

    public final void executeLater(Runnable runnable2) {
        this.queue.add(Preconditions.checkNotNull(runnable2, "runnable is null"));
    }

    @Override
    public final void execute(Runnable task) {
        this.executeLater(task);
        this.drain();
    }

    public void throwIfNotInThisSynchronizationContext() {
        Preconditions.checkState(Thread.currentThread() == this.drainingThread.get(), "Not called from the SynchronizationContext");
    }

    public final ScheduledHandle schedule(final Runnable task, long delay, TimeUnit unit, ScheduledExecutorService timerService) {
        final ManagedRunnable runnable2 = new ManagedRunnable(task);
        ScheduledFuture<?> future = timerService.schedule(new Runnable(){

            @Override
            public void run() {
                SynchronizationContext.this.execute(runnable2);
            }

            public String toString() {
                return task.toString() + "(scheduled in SynchronizationContext)";
            }
        }, delay, unit);
        return new ScheduledHandle(runnable2, future);
    }

    public final ScheduledHandle scheduleWithFixedDelay(final Runnable task, long initialDelay, final long delay, TimeUnit unit, ScheduledExecutorService timerService) {
        final ManagedRunnable runnable2 = new ManagedRunnable(task);
        ScheduledFuture<?> future = timerService.scheduleWithFixedDelay(new Runnable(){

            @Override
            public void run() {
                SynchronizationContext.this.execute(runnable2);
            }

            public String toString() {
                return task.toString() + "(scheduled in SynchronizationContext with delay of " + delay + ")";
            }
        }, initialDelay, delay, unit);
        return new ScheduledHandle(runnable2, future);
    }

    public static final class ScheduledHandle {
        private final ManagedRunnable runnable;
        private final ScheduledFuture<?> future;

        private ScheduledHandle(ManagedRunnable runnable2, ScheduledFuture<?> future) {
            this.runnable = Preconditions.checkNotNull(runnable2, "runnable");
            this.future = Preconditions.checkNotNull(future, "future");
        }

        public void cancel() {
            this.runnable.isCancelled = true;
            this.future.cancel(false);
        }

        public boolean isPending() {
            return !this.runnable.hasStarted && !this.runnable.isCancelled;
        }
    }

    private static class ManagedRunnable
    implements Runnable {
        final Runnable task;
        boolean isCancelled;
        boolean hasStarted;

        ManagedRunnable(Runnable task) {
            this.task = Preconditions.checkNotNull(task, "task");
        }

        @Override
        public void run() {
            if (!this.isCancelled) {
                this.hasStarted = true;
                this.task.run();
            }
        }
    }
}

