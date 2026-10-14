/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class SchedulerState<T> {
    @Nullable
    final T initialResource;
    final T currentResource;
    final Mono<Void> onDispose;

    private SchedulerState(@Nullable T initialResource, T currentResource, Mono<Void> onDispose) {
        this.initialResource = initialResource;
        this.currentResource = currentResource;
        this.onDispose = onDispose;
    }

    static <T> SchedulerState<T> init(T resource) {
        return new SchedulerState<T>(resource, resource, Mono.empty());
    }

    static <T> SchedulerState<T> transition(@Nullable T initial, T next, DisposeAwaiter<T> awaiter) {
        return new SchedulerState<T>(initial, next, initial == null ? Mono.empty() : Flux.create(sink2 -> DisposeAwaiterRunnable.awaitInPool(awaiter, initial, sink2, 100)).replay().refCount().next());
    }

    static class DisposeAwaiterRunnable<T>
    implements Runnable {
        static final ScheduledExecutorService TRANSITION_AWAIT_POOL;
        private final DisposeAwaiter<T> awaiter;
        private final T initial;
        private final int awaitMs;
        private final FluxSink<Void> sink;
        volatile boolean cancelled;

        static <R> void awaitInPool(DisposeAwaiter<R> awaiter, R initial, FluxSink<Void> sink2, int awaitMs) {
            DisposeAwaiterRunnable<R> poller = new DisposeAwaiterRunnable<R>(awaiter, initial, sink2, awaitMs);
            TRANSITION_AWAIT_POOL.submit(poller);
        }

        DisposeAwaiterRunnable(DisposeAwaiter<T> awaiter, T initial, FluxSink<Void> sink2, int awaitMs) {
            this.awaiter = awaiter;
            this.initial = initial;
            this.sink = sink2;
            this.awaitMs = awaitMs;
            sink2.onCancel(this::cancel);
        }

        void cancel() {
            this.cancelled = true;
        }

        @Override
        public void run() {
            if (this.cancelled) {
                return;
            }
            try {
                if (this.awaiter.await(this.initial, this.awaitMs, TimeUnit.MILLISECONDS)) {
                    this.sink.complete();
                } else {
                    if (this.cancelled) {
                        return;
                    }
                    TRANSITION_AWAIT_POOL.submit(this);
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }

        static {
            ScheduledThreadPoolExecutor executor2 = new ScheduledThreadPoolExecutor(0);
            executor2.setKeepAliveTime(10L, TimeUnit.SECONDS);
            executor2.allowCoreThreadTimeOut(true);
            executor2.setMaximumPoolSize(Schedulers.DEFAULT_POOL_SIZE);
            TRANSITION_AWAIT_POOL = executor2;
        }
    }

    static interface DisposeAwaiter<T> {
        public boolean await(T var1, long var2, TimeUnit var4) throws InterruptedException;
    }
}

