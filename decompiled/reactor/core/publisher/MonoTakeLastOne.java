/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoTakeLastOne<T>
extends MonoFromFluxOperator<T, T>
implements Fuseable {
    final T defaultValue;

    MonoTakeLastOne(Flux<? extends T> source2) {
        super(source2);
        this.defaultValue = null;
    }

    MonoTakeLastOne(Flux<? extends T> source2, T defaultValue) {
        super(source2);
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new TakeLastOneSubscriber<T>(actual, this.defaultValue, true);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TakeLastOneSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, T> {
        static final Object CANCELLED = new Object();
        final boolean mustEmit;
        T value;
        boolean done;

        TakeLastOneSubscriber(CoreSubscriber<? super T> actual, @Nullable T defaultValue, boolean mustEmit) {
            super(actual);
            this.value = defaultValue;
            this.mustEmit = mustEmit;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && this.value == null;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.value == CANCELLED;
            }
            return super.scanUnsafe(key);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onNext(T t2) {
            T old = this.value;
            if (old == CANCELLED) {
                Operators.onDiscard(t2, this.actual.currentContext());
                return;
            }
            TakeLastOneSubscriber takeLastOneSubscriber = this;
            synchronized (takeLastOneSubscriber) {
                old = this.value;
                if (old != CANCELLED) {
                    this.value = t2;
                }
            }
            if (old == CANCELLED) {
                Operators.onDiscard(t2, this.actual.currentContext());
                return;
            }
            Operators.onDiscard(old, this.actual.currentContext());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            T v;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            TakeLastOneSubscriber takeLastOneSubscriber = this;
            synchronized (takeLastOneSubscriber) {
                v = this.value;
                this.value = null;
            }
            if (v == CANCELLED) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            if (v != null) {
                Operators.onDiscard(v, this.actual.currentContext());
            }
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            T v = this.value;
            if (v == CANCELLED) {
                return;
            }
            if (v == null) {
                if (this.mustEmit) {
                    this.actual.onError(Operators.onOperatorError(new NoSuchElementException("Flux#last() didn't observe any onNext signal"), this.actual.currentContext()));
                } else {
                    this.actual.onComplete();
                }
                return;
            }
            this.completePossiblyEmpty();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            T v;
            this.s.cancel();
            TakeLastOneSubscriber takeLastOneSubscriber = this;
            synchronized (takeLastOneSubscriber) {
                v = this.value;
                this.value = CANCELLED;
            }
            if (v != null) {
                Operators.onDiscard(v, this.actual.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        T accumulatedValue() {
            T v;
            TakeLastOneSubscriber takeLastOneSubscriber = this;
            synchronized (takeLastOneSubscriber) {
                v = this.value;
                this.value = null;
            }
            if (v == CANCELLED) {
                return null;
            }
            return v;
        }
    }
}

