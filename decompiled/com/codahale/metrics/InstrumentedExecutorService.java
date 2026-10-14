/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class InstrumentedExecutorService
implements ExecutorService {
    private static final AtomicLong nameCounter = new AtomicLong();
    private final ExecutorService delegate;
    private final Meter submitted;
    private final Counter running;
    private final Meter completed;
    private final Timer duration;

    public InstrumentedExecutorService(ExecutorService delegate, MetricRegistry registry) {
        this(delegate, registry, "instrumented-delegate-" + nameCounter.incrementAndGet());
    }

    public InstrumentedExecutorService(ExecutorService delegate, MetricRegistry registry, String name) {
        this.delegate = delegate;
        this.submitted = registry.meter(MetricRegistry.name(name, "submitted"));
        this.running = registry.counter(MetricRegistry.name(name, "running"));
        this.completed = registry.meter(MetricRegistry.name(name, "completed"));
        this.duration = registry.timer(MetricRegistry.name(name, "duration"));
    }

    @Override
    public void execute(Runnable runnable2) {
        this.submitted.mark();
        this.delegate.execute(new InstrumentedRunnable(runnable2));
    }

    @Override
    public Future<?> submit(Runnable runnable2) {
        this.submitted.mark();
        return this.delegate.submit(new InstrumentedRunnable(runnable2));
    }

    @Override
    public <T> Future<T> submit(Runnable runnable2, T result) {
        this.submitted.mark();
        return this.delegate.submit(new InstrumentedRunnable(runnable2), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        this.submitted.mark();
        return this.delegate.submit(new InstrumentedCallable<T>(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2) throws InterruptedException {
        this.submitted.mark(tasks2.size());
        Collection<Callable<T>> instrumented = this.instrument(tasks2);
        return this.delegate.invokeAll(instrumented);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException {
        this.submitted.mark(tasks2.size());
        Collection<Callable<T>> instrumented = this.instrument(tasks2);
        return this.delegate.invokeAll(instrumented, timeout2, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks2) throws ExecutionException, InterruptedException {
        this.submitted.mark(tasks2.size());
        Collection<Callable<T>> instrumented = this.instrument(tasks2);
        return this.delegate.invokeAny(instrumented);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        this.submitted.mark(tasks2.size());
        Collection<Callable<T>> instrumented = this.instrument(tasks2);
        return this.delegate.invokeAny(instrumented, timeout2, unit);
    }

    private <T> Collection<? extends Callable<T>> instrument(Collection<? extends Callable<T>> tasks2) {
        ArrayList<InstrumentedCallable<T>> instrumented = new ArrayList<InstrumentedCallable<T>>(tasks2.size());
        for (Callable<T> task : tasks2) {
            instrumented.add(new InstrumentedCallable<T>(task));
        }
        return instrumented;
    }

    @Override
    public void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.awaitTermination(l, timeUnit);
    }

    private class InstrumentedCallable<T>
    implements Callable<T> {
        private final Callable<T> callable;

        InstrumentedCallable(Callable<T> callable) {
            this.callable = callable;
        }

        @Override
        public T call() throws Exception {
            InstrumentedExecutorService.this.running.inc();
            Timer.Context context = InstrumentedExecutorService.this.duration.time();
            try {
                T t2 = this.callable.call();
                return t2;
            }
            finally {
                context.stop();
                InstrumentedExecutorService.this.running.dec();
                InstrumentedExecutorService.this.completed.mark();
            }
        }
    }

    private class InstrumentedRunnable
    implements Runnable {
        private final Runnable task;

        InstrumentedRunnable(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            InstrumentedExecutorService.this.running.inc();
            Timer.Context context = InstrumentedExecutorService.this.duration.time();
            try {
                this.task.run();
            }
            finally {
                context.stop();
                InstrumentedExecutorService.this.running.dec();
                InstrumentedExecutorService.this.completed.mark();
            }
        }
    }
}

