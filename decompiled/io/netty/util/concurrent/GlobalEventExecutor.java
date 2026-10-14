/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FailedFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.OrderedEventExecutor;
import io.netty.util.concurrent.ScheduledFutureTask;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.ThreadExecutorMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GlobalEventExecutor
extends AbstractScheduledEventExecutor
implements OrderedEventExecutor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
    private static final long SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    final ScheduledFutureTask<Void> quietPeriodTask = new ScheduledFutureTask<Object>((AbstractScheduledEventExecutor)this, Executors.callable(new Runnable(){

        @Override
        public void run() {
        }
    }, null), ScheduledFutureTask.deadlineNanos(SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
    final ThreadFactory threadFactory;
    private final TaskRunner taskRunner = new TaskRunner();
    private final AtomicBoolean started = new AtomicBoolean();
    volatile Thread thread;
    private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());

    private GlobalEventExecutor() {
        this.scheduledTaskQueue().add(this.quietPeriodTask);
        this.threadFactory = ThreadExecutorMap.apply(new DefaultThreadFactory(DefaultThreadFactory.toPoolName(this.getClass()), false, 5, null), (EventExecutor)this);
    }

    Runnable takeTask() {
        Runnable task;
        BlockingQueue<Runnable> taskQueue = this.taskQueue;
        do {
            ScheduledFutureTask<?> scheduledTask;
            if ((scheduledTask = this.peekScheduledTask()) == null) {
                Runnable task2 = null;
                try {
                    task2 = taskQueue.take();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return task2;
            }
            long delayNanos = scheduledTask.delayNanos();
            task = null;
            if (delayNanos > 0L) {
                try {
                    task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
                }
                catch (InterruptedException e) {
                    return null;
                }
            }
            if (task != null) continue;
            this.fetchFromScheduledTaskQueue();
            task = (Runnable)taskQueue.poll();
        } while (task == null);
        return task;
    }

    private void fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable scheduledTask = this.pollScheduledTask(nanoTime);
        while (scheduledTask != null) {
            this.taskQueue.add(scheduledTask);
            scheduledTask = this.pollScheduledTask(nanoTime);
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    private void addTask(Runnable task) {
        this.taskQueue.add(ObjectUtil.checkNotNull(task, "task"));
    }

    @Override
    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    @Override
    public Future<?> shutdownGracefully(long quietPeriod, long timeout2, TimeUnit unit) {
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShuttingDown() {
        return false;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) {
        return false;
    }

    public boolean awaitInactivity(long timeout2, TimeUnit unit) throws InterruptedException {
        ObjectUtil.checkNotNull(unit, "unit");
        Thread thread2 = this.thread;
        if (thread2 == null) {
            throw new IllegalStateException("thread was not started");
        }
        thread2.join(unit.toMillis(timeout2));
        return !thread2.isAlive();
    }

    @Override
    public void execute(Runnable task) {
        this.addTask(ObjectUtil.checkNotNull(task, "task"));
        if (!this.inEventLoop()) {
            this.startThread();
        }
    }

    private void startThread() {
        if (this.started.compareAndSet(false, true)) {
            final Thread t2 = this.threadFactory.newThread(this.taskRunner);
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    t2.setContextClassLoader(null);
                    return null;
                }
            });
            this.thread = t2;
            t2.start();
        }
    }

    final class TaskRunner
    implements Runnable {
        TaskRunner() {
        }

        @Override
        public void run() {
            while (true) {
                Runnable task;
                if ((task = GlobalEventExecutor.this.takeTask()) != null) {
                    try {
                        task.run();
                    }
                    catch (Throwable t2) {
                        logger.warn("Unexpected exception from the global event executor: ", t2);
                    }
                    if (task != GlobalEventExecutor.this.quietPeriodTask) continue;
                }
                PriorityQueue scheduledTaskQueue = GlobalEventExecutor.this.scheduledTaskQueue;
                if (!GlobalEventExecutor.this.taskQueue.isEmpty() || scheduledTaskQueue != null && scheduledTaskQueue.size() != 1) continue;
                boolean stopped = GlobalEventExecutor.this.started.compareAndSet(true, false);
                assert (stopped);
                if (GlobalEventExecutor.this.taskQueue.isEmpty() || !GlobalEventExecutor.this.started.compareAndSet(false, true)) break;
            }
        }
    }
}

