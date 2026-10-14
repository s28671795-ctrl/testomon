/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxDefaultIfEmpty<T>
extends InternalFluxOperator<T, T> {
    final T value;

    FluxDefaultIfEmpty(Flux<? extends T> source2, T value) {
        super(source2);
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DefaultIfEmptySubscriber<T>(actual, this.value);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DefaultIfEmptySubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, T> {
        boolean done;
        boolean hasValue;
        volatile T fallbackValue;
        static final AtomicReferenceFieldUpdater<DefaultIfEmptySubscriber, Object> FALLBACK_VALUE = AtomicReferenceFieldUpdater.newUpdater(DefaultIfEmptySubscriber.class, Object.class, "fallbackValue");

        DefaultIfEmptySubscriber(CoreSubscriber<? super T> actual, T fallbackValue) {
            super(actual);
            FALLBACK_VALUE.lazySet(this, fallbackValue);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void request(long n) {
            if (!this.hasRequest) {
                this.hasRequest = true;
                int state = this.state;
                if (state != 1 && STATE.compareAndSet(this, state, state | 1) && state > 1) {
                    T fallbackValue = this.fallbackValue;
                    if (fallbackValue != null && FALLBACK_VALUE.compareAndSet(this, fallbackValue, null)) {
                        this.actual.onNext(fallbackValue);
                        this.actual.onComplete();
                    }
                    return;
                }
            }
            this.s.request(n);
        }

        @Override
        public void cancel() {
            super.cancel();
            T fallbackValue = this.fallbackValue;
            if (fallbackValue != null && FALLBACK_VALUE.compareAndSet(this, fallbackValue, null)) {
                Operators.onDiscard(fallbackValue, this.actual.currentContext());
            }
        }

        @Override
        public void onNext(T t2) {
            if (!this.hasValue) {
                this.hasValue = true;
                T fallbackValue = this.fallbackValue;
                if (fallbackValue != null && FALLBACK_VALUE.compareAndSet(this, fallbackValue, null)) {
                    Operators.onDiscard(fallbackValue, this.actual.currentContext());
                }
            }
            this.actual.onNext(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (!this.hasValue) {
                this.completePossiblyEmpty();
                return;
            }
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable t2) {
            T fallbackValue;
            if (this.done) {
                return;
            }
            this.done = true;
            if (!this.hasValue && (fallbackValue = this.fallbackValue) != null && FALLBACK_VALUE.compareAndSet(this, fallbackValue, null)) {
                Operators.onDiscard(t2, this.actual.currentContext());
            }
            this.actual.onError(t2);
        }

        @Override
        T accumulatedValue() {
            T fallbackValue = this.fallbackValue;
            if (fallbackValue != null && FALLBACK_VALUE.compareAndSet(this, fallbackValue, null)) {
                return fallbackValue;
            }
            return null;
        }
    }
}

