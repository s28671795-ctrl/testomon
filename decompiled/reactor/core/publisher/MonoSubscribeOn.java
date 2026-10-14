/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoSubscribeOn<T>
extends InternalMonoOperator<T, T> {
    final Scheduler scheduler;

    MonoSubscribeOn(Mono<? extends T> source2, Scheduler scheduler) {
        super(source2);
        this.scheduler = scheduler;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        block2: {
            Scheduler.Worker worker = this.scheduler.createWorker();
            SubscribeOnSubscriber<? super T> parent = new SubscribeOnSubscriber<T>(this.source, actual, worker);
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
        final Publisher<? extends T> parent;
        final Scheduler.Worker worker;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Subscription.class, "s");
        volatile long requested;
        static final AtomicLongFieldUpdater<SubscribeOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SubscribeOnSubscriber.class, "requested");
        volatile Thread thread;
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Thread.class, "thread");

        SubscribeOnSubscriber(Publisher<? extends T> parent, CoreSubscriber<? super T> actual, Scheduler.Worker worker) {
            this.actual = actual;
            this.parent = parent;
            this.worker = worker;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
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
        public void run() {
            THREAD.lazySet(this, Thread.currentThread());
            this.parent.subscribe(this);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            long r;
            if (Operators.setOnce(S, this, s2) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                this.trySchedule(r, s2);
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
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
                THREAD.lazySet(this, null);
            }
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
            this.worker.dispose();
            THREAD.lazySet(this, null);
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                Subscription a = this.s;
                if (a != null) {
                    this.trySchedule(n, a);
                } else {
                    long r;
                    Operators.addCap(REQUESTED, this, n);
                    a = this.s;
                    if (a != null && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                        this.trySchedule(n, a);
                    }
                }
            }
        }

        void trySchedule(long n, Subscription s2) {
            block4: {
                if (Thread.currentThread() == THREAD.get(this)) {
                    s2.request(n);
                } else {
                    try {
                        this.worker.schedule(() -> s2.request(n));
                    }
                    catch (RejectedExecutionException ree) {
                        if (this.worker.isDisposed()) break block4;
                        this.actual.onError(Operators.onRejectedExecution(ree, this, null, null, this.actual.currentContext()));
                    }
                }
            }
        }

        @Override
        public void cancel() {
            Operators.terminate(S, this);
            this.worker.dispose();
        }
    }
}

