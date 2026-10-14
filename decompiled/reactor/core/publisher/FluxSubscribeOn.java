/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxSubscribeOn<T>
extends InternalFluxOperator<T, T> {
    final Scheduler scheduler;
    final boolean requestOnSeparateThread;

    FluxSubscribeOn(Flux<? extends T> source2, Scheduler scheduler, boolean requestOnSeparateThread) {
        super(source2);
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
        this.requestOnSeparateThread = requestOnSeparateThread;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        block2: {
            Scheduler.Worker worker = Objects.requireNonNull(this.scheduler.createWorker(), "The scheduler returned a null Function");
            SubscribeOnSubscriber<? super T> parent = new SubscribeOnSubscriber<T>(this.source, actual, worker, this.requestOnSeparateThread);
            actual.onSubscribe(parent);
            try {
                worker.schedule(parent);
            }
            catch (RejectedExecutionException ree) {
                if (parent.s == Operators.cancelledSubscription()) break block2;
                actual.onError(Operators.onRejectedExecution(ree, parent, null, null, actual.currentContext()));
            }
        }
        return null;
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

    static final class SubscribeOnSubscriber<T>
    implements InnerOperator<T, T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final CorePublisher<? extends T> source;
        final Scheduler.Worker worker;
        final boolean requestOnSeparateThread;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Subscription.class, "s");
        volatile long requested;
        static final AtomicLongFieldUpdater<SubscribeOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SubscribeOnSubscriber.class, "requested");
        volatile Thread thread;
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Thread.class, "thread");

        SubscribeOnSubscriber(CorePublisher<? extends T> source2, CoreSubscriber<? super T> actual, Scheduler.Worker worker, boolean requestOnSeparateThread) {
            this.actual = actual;
            this.worker = worker;
            this.source = source2;
            this.requestOnSeparateThread = requestOnSeparateThread;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            long r;
            if (Operators.setOnce(S, this, s2) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                this.requestUpstream(r, s2);
            }
        }

        void requestUpstream(long n, Subscription s2) {
            block4: {
                if (!this.requestOnSeparateThread || Thread.currentThread() == THREAD.get(this)) {
                    s2.request(n);
                } else {
                    try {
                        this.worker.schedule(() -> s2.request(n));
                    }
                    catch (RejectedExecutionException ree) {
                        if (this.worker.isDisposed()) break block4;
                        throw Operators.onRejectedExecution(ree, this, null, null, this.actual.currentContext());
                    }
                }
            }
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            try {
                this.actual.onError(t2);
            }
            finally {
                this.worker.dispose();
            }
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
            this.worker.dispose();
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                Subscription s2 = S.get(this);
                if (s2 != null) {
                    this.requestUpstream(n, s2);
                } else {
                    long r;
                    Operators.addCap(REQUESTED, this, n);
                    s2 = S.get(this);
                    if (s2 != null && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                        this.requestUpstream(r, s2);
                    }
                }
            }
        }

        @Override
        public void run() {
            THREAD.lazySet(this, Thread.currentThread());
            this.source.subscribe(this);
        }

        @Override
        public void cancel() {
            Subscription a = this.s;
            if (a != Operators.cancelledSubscription() && (a = S.getAndSet(this, Operators.cancelledSubscription())) != null && a != Operators.cancelledSubscription()) {
                a.cancel();
            }
            this.worker.dispose();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.worker;
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
    }
}

