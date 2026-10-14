/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoCallable<T>
extends Mono<T>
implements Callable<T>,
Fuseable,
SourceProducer<T> {
    final Callable<? extends T> callable;

    MonoCallable(Callable<? extends T> callable) {
        this.callable = Objects.requireNonNull(callable, "callable");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(new MonoCallableSubscription<T>(actual, this.callable));
    }

    @Override
    @Nullable
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    @Nullable
    public T block(Duration m4) {
        try {
            return this.callable.call();
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return this.callable.call();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static class MonoCallableSubscription<T>
    implements InnerProducer<T>,
    Fuseable,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Callable<? extends T> callable;
        boolean done;
        volatile int requestedOnce;
        static final AtomicIntegerFieldUpdater<MonoCallableSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoCallableSubscription.class, "requestedOnce");
        volatile boolean cancelled;

        MonoCallableSubscription(CoreSubscriber<? super T> actual, Callable<? extends T> callable) {
            this.actual = actual;
            this.callable = callable;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public T poll() {
            if (this.done) {
                return null;
            }
            this.done = true;
            try {
                return this.callable.call();
            }
            catch (Throwable e) {
                throw Exceptions.propagate(e);
            }
        }

        @Override
        public void request(long n) {
            T value;
            if (this.cancelled) {
                return;
            }
            if (this.requestedOnce == 1 || !REQUESTED_ONCE.compareAndSet(this, 0, 1)) {
                return;
            }
            CoreSubscriber<T> s2 = this.actual;
            try {
                value = this.callable.call();
            }
            catch (Exception e) {
                if (this.cancelled) {
                    Operators.onErrorDropped(e, s2.currentContext());
                    return;
                }
                s2.onError(e);
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(value, s2.currentContext());
                return;
            }
            if (value != null) {
                s2.onNext(value);
            }
            s2.onComplete();
        }

        @Override
        public void cancel() {
            this.cancelled = true;
        }

        @Override
        public int requestFusion(int requestedMode) {
            return requestedMode & 1;
        }

        @Override
        public int size() {
            return this.done ? 0 : 1;
        }

        @Override
        public boolean isEmpty() {
            return this.done;
        }

        @Override
        public void clear() {
            this.done = true;
        }
    }
}

