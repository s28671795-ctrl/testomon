/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

final class DelegateServiceScheduler
implements Scheduler,
SchedulerState.DisposeAwaiter<ScheduledExecutorService>,
Scannable {
    static final ScheduledExecutorService TERMINATED = Executors.newSingleThreadScheduledExecutor();
    final String executorName;
    final ScheduledExecutorService original;
    @Nullable
    volatile SchedulerState<ScheduledExecutorService> state;
    static final AtomicReferenceFieldUpdater<DelegateServiceScheduler, SchedulerState> STATE;

    DelegateServiceScheduler(String executorName, ExecutorService executorService2) {
        this.executorName = executorName;
        this.original = DelegateServiceScheduler.convert(executorService2);
    }

    ScheduledExecutorService getOrCreate() {
        SchedulerState<ScheduledExecutorService> s2 = this.state;
        if (s2 == null) {
            this.init();
            s2 = this.state;
            if (s2 == null) {
                throw new IllegalStateException("executor is null after implicit start()");
            }
        }
        return (ScheduledExecutorService)s2.currentResource;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(this.getOrCreate());
    }

    @Override
    public Disposable schedule(Runnable task) {
        return Schedulers.directSchedule(this.getOrCreate(), task, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        return Schedulers.directSchedule(this.getOrCreate(), task, null, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return Schedulers.directSchedulePeriodically(this.getOrCreate(), task, initialDelay, period, unit);
    }

    @Override
    public void start() {
        STATE.compareAndSet(this, null, SchedulerState.init(Schedulers.decorateExecutorService(this, this.original)));
    }

    @Override
    public void init() {
        SchedulerState<ScheduledExecutorService> a = this.state;
        if (a != null) {
            if (a.currentResource == TERMINATED) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        if (!STATE.compareAndSet(this, null, SchedulerState.init(Schedulers.decorateExecutorService(this, this.original))) && this.isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    @Override
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService> current = this.state;
        return current != null && current.currentResource == TERMINATED;
    }

    @Override
    public boolean await(ScheduledExecutorService resource, long timeout2, TimeUnit timeUnit) throws InterruptedException {
        return resource.awaitTermination(timeout2, timeUnit);
    }

    @Override
    public void dispose() {
        SchedulerState<ScheduledExecutorService> previous = this.state;
        if (previous != null && previous.currentResource == TERMINATED) {
            assert (previous.initialResource != null);
            ((ScheduledExecutorService)previous.initialResource).shutdownNow();
            return;
        }
        SchedulerState<ScheduledExecutorService> terminated = SchedulerState.transition(previous == null ? null : (ScheduledExecutorService)previous.currentResource, TERMINATED, this);
        STATE.compareAndSet(this, previous, terminated);
        if (terminated.initialResource != null) {
            ((ScheduledExecutorService)terminated.initialResource).shutdownNow();
        }
    }

    @Override
    public Mono<Void> disposeGracefully() {
        return Mono.defer(() -> {
            SchedulerState<ScheduledExecutorService> previous = this.state;
            if (previous != null && previous.currentResource == TERMINATED) {
                return previous.onDispose;
            }
            SchedulerState<ScheduledExecutorService> terminated = SchedulerState.transition(previous == null ? null : (ScheduledExecutorService)previous.currentResource, TERMINATED, this);
            STATE.compareAndSet(this, previous, terminated);
            if (terminated.initialResource != null) {
                ((ScheduledExecutorService)terminated.initialResource).shutdown();
            }
            return terminated.onDispose;
        });
    }

    static ScheduledExecutorService convert(ExecutorService executor2) {
        if (executor2 instanceof ScheduledExecutorService) {
            return (ScheduledExecutorService)executor2;
        }
        return new UnsupportedScheduledExecutorService(executor2);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        SchedulerState<ScheduledExecutorService> s2 = this.state;
        if (s2 != null) {
            return Schedulers.scanExecutor((Executor)s2.currentResource, key);
        }
        return null;
    }

    public String toString() {
        return "fromExecutorService(" + this.executorName + ')';
    }

    static {
        TERMINATED.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(DelegateServiceScheduler.class, SchedulerState.class, "state");
    }

    static final class UnsupportedScheduledExecutorService
    implements ScheduledExecutorService,
    Supplier<ExecutorService> {
        final ExecutorService exec;

        UnsupportedScheduledExecutorService(ExecutorService exec) {
            this.exec = exec;
        }

        @Override
        public ExecutorService get() {
            return this.exec;
        }

        @Override
        public void shutdown() {
            this.exec.shutdown();
        }

        @Override
        @NonNull
        public List<Runnable> shutdownNow() {
            return this.exec.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return this.exec.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return this.exec.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout2, @NonNull TimeUnit unit) throws InterruptedException {
            return this.exec.awaitTermination(timeout2, unit);
        }

        @Override
        @NonNull
        public <T> Future<T> submit(@NonNull Callable<T> task) {
            return this.exec.submit(task);
        }

        @Override
        @NonNull
        public <T> Future<T> submit(@NonNull Runnable task, T result) {
            return this.exec.submit(task, result);
        }

        @Override
        @NonNull
        public Future<?> submit(@NonNull Runnable task) {
            return this.exec.submit(task);
        }

        @Override
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks2) throws InterruptedException {
            return this.exec.invokeAll(tasks2);
        }

        @Override
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks2, long timeout2, @NonNull TimeUnit unit) throws InterruptedException {
            return this.exec.invokeAll(tasks2, timeout2, unit);
        }

        @Override
        @NonNull
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks2) throws InterruptedException, ExecutionException {
            return this.exec.invokeAny(tasks2);
        }

        @Override
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks2, long timeout2, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.exec.invokeAny(tasks2, timeout2, unit);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            this.exec.execute(command);
        }

        @Override
        @NonNull
        public ScheduledFuture<?> schedule(@NonNull Runnable command, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public <V> ScheduledFuture<V> schedule(@NonNull Callable<V> callable, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable command, long initialDelay, long period, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable command, long initialDelay, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        public String toString() {
            return this.exec.toString();
        }
    }
}

