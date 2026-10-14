/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class DelegatingScheduledExecutorService
implements ScheduledExecutorService {
    final ScheduledExecutorService scheduledExecutorService;

    DelegatingScheduledExecutorService(ScheduledExecutorService service2) {
        this.scheduledExecutorService = service2;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return this.scheduledExecutorService.schedule(command, delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return this.scheduledExecutorService.schedule(callable, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.scheduledExecutorService.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.scheduledExecutorService.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    public void shutdown() {
        this.scheduledExecutorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.scheduledExecutorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.scheduledExecutorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.scheduledExecutorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
        return this.scheduledExecutorService.awaitTermination(timeout2, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.scheduledExecutorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.scheduledExecutorService.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.scheduledExecutorService.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2) throws InterruptedException {
        return this.scheduledExecutorService.invokeAll(tasks2);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException {
        return this.scheduledExecutorService.invokeAll(tasks2, timeout2, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks2) throws InterruptedException, ExecutionException {
        return this.scheduledExecutorService.invokeAny(tasks2);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.scheduledExecutorService.invokeAny(tasks2, timeout2, unit);
    }

    @Override
    public void execute(Runnable command) {
        this.scheduledExecutorService.execute(command);
    }
}

