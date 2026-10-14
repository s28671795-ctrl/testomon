/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.TimeUnit;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

public interface Scheduler
extends Disposable {
    public Disposable schedule(Runnable var1);

    default public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        throw Exceptions.failWithRejectedNotTimeCapable();
    }

    default public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        throw Exceptions.failWithRejectedNotTimeCapable();
    }

    default public long now(TimeUnit unit) {
        if (unit.compareTo(TimeUnit.MILLISECONDS) >= 0) {
            return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        return unit.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    public Worker createWorker();

    @Override
    default public void dispose() {
    }

    default public Mono<Void> disposeGracefully() {
        return Mono.fromRunnable(this::dispose);
    }

    @Deprecated
    default public void start() {
    }

    default public void init() {
        this.start();
    }

    public static interface Worker
    extends Disposable {
        public Disposable schedule(Runnable var1);

        default public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        default public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }
    }
}

