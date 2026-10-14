/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.core.scheduler.Schedulers;

final class SingleScheduler
implements Scheduler,
Supplier<ScheduledExecutorService>,
Scannable,
SchedulerState.DisposeAwaiter<ScheduledExecutorService> {
    static final AtomicLong COUNTER = new AtomicLong();
    static final ScheduledExecutorService TERMINATED = Executors.newSingleThreadScheduledExecutor();
    final ThreadFactory factory;
    volatile SchedulerState<ScheduledExecutorService> state;
    private static final AtomicReferenceFieldUpdater<SingleScheduler, SchedulerState> STATE;
    private static final SchedulerState<ScheduledExecutorService> INIT;

    SingleScheduler(ThreadFactory factory2) {
        this.factory = factory2;
        STATE.lazySet(this, INIT);
    }

    @Override
    public ScheduledExecutorService get() {
        ScheduledThreadPoolExecutor e = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, this.factory);
        e.setRemoveOnCancelPolicy(true);
        e.setMaximumPoolSize(1);
        return e;
    }

    @Override
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService> current = this.state;
        return current != INIT && current.currentResource == TERMINATED;
    }

    @Override
    public void init() {
        SchedulerState<ScheduledExecutorService> a = this.state;
        if (a != INIT) {
            if (a.currentResource == TERMINATED) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState<ScheduledExecutorService> b = SchedulerState.init(Schedulers.decorateExecutorService(this, this.get()));
        if (!STATE.compareAndSet(this, INIT, b)) {
            ((ScheduledExecutorService)b.currentResource).shutdownNow();
            if (this.isDisposed()) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
        }
    }

    @Override
    public void start() {
        SchedulerState<ScheduledExecutorService> a = this.state;
        if (a.currentResource != TERMINATED) {
            return;
        }
        SchedulerState<ScheduledExecutorService> b = SchedulerState.init(Schedulers.decorateExecutorService(this, this.get()));
        if (STATE.compareAndSet(this, a, b)) {
            return;
        }
        ((ScheduledExecutorService)b.currentResource).shutdownNow();
    }

    @Override
    public boolean await(ScheduledExecutorService resource, long timeout2, TimeUnit timeUnit) throws InterruptedException {
        return resource.awaitTermination(timeout2, timeUnit);
    }

    @Override
    public void dispose() {
        SchedulerState<ScheduledExecutorService> previous = this.state;
        if (previous.currentResource == TERMINATED) {
            assert (previous.initialResource != null);
            ((ScheduledExecutorService)previous.initialResource).shutdownNow();
            return;
        }
        SchedulerState<ScheduledExecutorService> terminated = SchedulerState.transition(previous.currentResource, TERMINATED, this);
        STATE.compareAndSet(this, previous, terminated);
        assert (terminated.initialResource != null);
        ((ScheduledExecutorService)terminated.initialResource).shutdownNow();
    }

    @Override
    public Mono<Void> disposeGracefully() {
        return Mono.defer(() -> {
            SchedulerState<ScheduledExecutorService> previous = this.state;
            if (previous.currentResource == TERMINATED) {
                return previous.onDispose;
            }
            SchedulerState<ScheduledExecutorService> terminated = SchedulerState.transition(previous.currentResource, TERMINATED, this);
            STATE.compareAndSet(this, previous, terminated);
            assert (terminated.initialResource != null);
            ((ScheduledExecutorService)terminated.initialResource).shutdown();
            return terminated.onDispose;
        });
    }

    @Override
    public Disposable schedule(Runnable task) {
        ScheduledExecutorService executor2 = (ScheduledExecutorService)this.state.currentResource;
        return Schedulers.directSchedule(executor2, task, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        return Schedulers.directSchedule((ScheduledExecutorService)this.state.currentResource, task, null, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return Schedulers.directSchedulePeriodically((ScheduledExecutorService)this.state.currentResource, task, initialDelay, period, unit);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("single").append('(');
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append('\"').append(((ReactorThreadFactory)this.factory).get()).append('\"');
        }
        return ts.append(')').toString();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        if (key == Scannable.Attr.CAPACITY || key == Scannable.Attr.BUFFERED) {
            return 1;
        }
        return Schedulers.scanExecutor((Executor)this.state.currentResource, key);
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker((ScheduledExecutorService)this.state.currentResource);
    }

    static {
        TERMINATED.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(SingleScheduler.class, SchedulerState.class, "state");
        INIT = SchedulerState.init(TERMINATED);
    }
}

