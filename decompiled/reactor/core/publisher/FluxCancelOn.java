/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxCancelOn<T>
extends InternalFluxOperator<T, T> {
    final Scheduler scheduler;

    public FluxCancelOn(Flux<T> source2, Scheduler scheduler) {
        super(source2);
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new CancelSubscriber<T>(actual, this.scheduler);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class CancelSubscriber<T>
    implements InnerOperator<T, T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final Scheduler scheduler;
        Subscription s;
        volatile int cancelled = 0;
        static final AtomicIntegerFieldUpdater<CancelSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(CancelSubscriber.class, "cancelled");

        CancelSubscriber(CoreSubscriber<? super T> actual, Scheduler scheduler) {
            this.actual = actual;
            this.scheduler = scheduler;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.scheduler;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void run() {
            this.s.cancel();
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                try {
                    this.scheduler.schedule(this);
                }
                catch (RejectedExecutionException ree) {
                    throw Operators.onRejectedExecution(ree, this.actual.currentContext());
                }
            }
        }
    }
}

