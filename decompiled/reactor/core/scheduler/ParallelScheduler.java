/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.core.scheduler.Schedulers;

final class ParallelScheduler
implements Scheduler,
Supplier<ScheduledExecutorService>,
SchedulerState.DisposeAwaiter<ScheduledExecutorService[]>,
Scannable {
    static final ScheduledExecutorService TERMINATED;
    static final ScheduledExecutorService[] SHUTDOWN;
    static final AtomicLong COUNTER;
    final int n;
    final ThreadFactory factory;
    volatile SchedulerState<ScheduledExecutorService[]> state;
    private static final AtomicReferenceFieldUpdater<ParallelScheduler, SchedulerState> STATE;
    int roundRobin;

    ParallelScheduler(int n, ThreadFactory factory2) {
        if (n <= 0) {
            throw new IllegalArgumentException("n > 0 required but it was " + n);
        }
        this.n = n;
        this.factory = factory2;
    }

    @Override
    public ScheduledExecutorService get() {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1, this.factory);
        poolExecutor.setMaximumPoolSize(1);
        poolExecutor.setRemoveOnCancelPolicy(true);
        return poolExecutor;
    }

    @Override
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService[]> current = this.state;
        return current != null && current.currentResource == SHUTDOWN;
    }

    @Override
    public void init() {
        SchedulerState<ScheduledExecutorService[]> a = this.state;
        if (a != null) {
            if (a.currentResource == SHUTDOWN) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState<ScheduledExecutorService[]> b = SchedulerState.init(new ScheduledExecutorService[this.n]);
        for (int i = 0; i < this.n; ++i) {
            ((ScheduledExecutorService[])b.currentResource)[i] = Schedulers.decorateExecutorService(this, this.get());
        }
        if (!STATE.compareAndSet(this, null, b)) {
            for (ScheduledExecutorService exec : (ScheduledExecutorService[])b.currentResource) {
                exec.shutdownNow();
            }
            if (this.isDisposed()) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
        }
    }

    @Override
    public void start() {
        SchedulerState<ScheduledExecutorService[]> a = this.state;
        if (a != null && a.currentResource != SHUTDOWN) {
            return;
        }
        SchedulerState<ScheduledExecutorService[]> b = SchedulerState.init(new ScheduledExecutorService[this.n]);
        for (int i = 0; i < this.n; ++i) {
            ((ScheduledExecutorService[])b.currentResource)[i] = Schedulers.decorateExecutorService(this, this.get());
        }
        if (STATE.compareAndSet(this, a, b)) {
            return;
        }
        for (ScheduledExecutorService exec : (ScheduledExecutorService[])b.currentResource) {
            exec.shutdownNow();
        }
    }

    @Override
    public boolean await(ScheduledExecutorService[] resource, long timeout2, TimeUnit timeUnit) throws InterruptedException {
        for (ScheduledExecutorService executor2 : resource) {
            if (executor2.awaitTermination(timeout2, timeUnit)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        SchedulerState<ScheduledExecutorService[]> previous = this.state;
        if (previous != null && previous.currentResource == SHUTDOWN) {
            if (previous.initialResource != null) {
                for (ScheduledExecutorService executor2 : (ScheduledExecutorService[])previous.initialResource) {
                    executor2.shutdownNow();
                }
            }
            return;
        }
        SchedulerState<ScheduledExecutorService[]> shutdown = SchedulerState.transition(previous == null ? null : (ScheduledExecutorService[])previous.currentResource, SHUTDOWN, this);
        STATE.compareAndSet(this, previous, shutdown);
        if (shutdown.initialResource != null) {
            for (ScheduledExecutorService executor3 : (ScheduledExecutorService[])shutdown.initialResource) {
                executor3.shutdownNow();
            }
        }
    }

    @Override
    public Mono<Void> disposeGracefully() {
        return Mono.defer(() -> {
            SchedulerState<ScheduledExecutorService[]> previous = this.state;
            if (previous != null && previous.currentResource == SHUTDOWN) {
                return previous.onDispose;
            }
            SchedulerState<ScheduledExecutorService[]> shutdown = SchedulerState.transition(previous == null ? null : (ScheduledExecutorService[])previous.currentResource, SHUTDOWN, this);
            STATE.compareAndSet(this, previous, shutdown);
            if (shutdown.initialResource != null) {
                for (ScheduledExecutorService executor2 : (ScheduledExecutorService[])shutdown.initialResource) {
                    executor2.shutdown();
                }
            }
            return shutdown.onDispose;
        });
    }

    ScheduledExecutorService pick() {
        SchedulerState<ScheduledExecutorService[]> a = this.state;
        if (a == null) {
            this.init();
            a = this.state;
            if (a == null) {
                throw new IllegalStateException("executors uninitialized after implicit init()");
            }
        }
        if (a.currentResource != SHUTDOWN) {
            int idx = this.roundRobin;
            if (idx == this.n) {
                idx = 0;
                this.roundRobin = 1;
            } else {
                this.roundRobin = idx + 1;
            }
            return ((ScheduledExecutorService[])a.currentResource)[idx];
        }
        return TERMINATED;
    }

    @Override
    public Disposable schedule(Runnable task) {
        return Schedulers.directSchedule(this.pick(), task, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        return Schedulers.directSchedule(this.pick(), task, null, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return Schedulers.directSchedulePeriodically(this.pick(), task, initialDelay, period, unit);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("parallel").append('(').append(this.n);
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append(",\"").append(((ReactorThreadFactory)this.factory).get()).append('\"');
        }
        ts.append(')');
        return ts.toString();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.CAPACITY || key == Scannable.Attr.BUFFERED) {
            return this.n;
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[])this.state.currentResource).map(exec -> key -> Schedulers.scanExecutor(exec, key));
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(this.pick());
    }

    static {
        SHUTDOWN = new ScheduledExecutorService[0];
        COUNTER = new AtomicLong();
        TERMINATED = Executors.newSingleThreadScheduledExecutor();
        TERMINATED.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(ParallelScheduler.class, SchedulerState.class, "state");
    }
}

