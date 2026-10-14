/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoSupplier<T>
extends Mono<T>
implements Callable<T>,
Fuseable,
SourceProducer<T> {
    final Supplier<? extends T> supplier;

    MonoSupplier(Supplier<? extends T> callable) {
        this.supplier = Objects.requireNonNull(callable, "callable");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(new MonoSupplierSubscription<T>(actual, this.supplier));
    }

    @Override
    @Nullable
    public T block(Duration m4) {
        return this.supplier.get();
    }

    @Override
    @Nullable
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return this.supplier.get();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static class MonoSupplierSubscription<T>
    implements InnerProducer<T>,
    Fuseable,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Supplier<? extends T> supplier;
        boolean done;
        volatile int requestedOnce;
        static final AtomicIntegerFieldUpdater<MonoSupplierSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoSupplierSubscription.class, "requestedOnce");
        volatile boolean cancelled;

        MonoSupplierSubscription(CoreSubscriber<? super T> actual, Supplier<? extends T> callable) {
            this.actual = actual;
            this.supplier = callable;
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
            return this.supplier.get();
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
                value = this.supplier.get();
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

