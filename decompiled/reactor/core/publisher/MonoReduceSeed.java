/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoReduceSeed<T, R>
extends MonoFromFluxOperator<T, R>
implements Fuseable {
    final Supplier<R> initialSupplier;
    final BiFunction<R, ? super T, R> accumulator;

    MonoReduceSeed(Flux<? extends T> source2, Supplier<R> initialSupplier, BiFunction<R, ? super T, R> accumulator) {
        super(source2);
        this.initialSupplier = Objects.requireNonNull(initialSupplier, "initialSupplier");
        this.accumulator = Objects.requireNonNull(accumulator, "accumulator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        R initialValue = Objects.requireNonNull(this.initialSupplier.get(), "The initial value supplied is null");
        return new ReduceSeedSubscriber<T, R>(actual, this.accumulator, initialValue);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ReduceSeedSubscriber<T, R>
    extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiFunction<R, ? super T, R> accumulator;
        R seed;
        boolean done;

        ReduceSeedSubscriber(CoreSubscriber<? super R> actual, BiFunction<R, ? super T, R> accumulator, R seed) {
            super(actual);
            this.accumulator = accumulator;
            this.seed = seed;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return !this.done && this.seed == null;
            }
            return super.scanUnsafe(key);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            R seed;
            this.s.cancel();
            ReduceSeedSubscriber reduceSeedSubscriber = this;
            synchronized (reduceSeedSubscriber) {
                seed = this.seed;
                this.seed = null;
            }
            if (seed == null) {
                return;
            }
            Operators.onDiscard(seed, this.actual.currentContext());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onNext(T t2) {
            try {
                ReduceSeedSubscriber reduceSeedSubscriber = this;
                synchronized (reduceSeedSubscriber) {
                    R v = this.seed;
                    if (v != null) {
                        R accumulated = Objects.requireNonNull(this.accumulator.apply(v, t2), "The accumulator returned a null value");
                        this.seed = accumulated;
                        return;
                    }
                }
                Operators.onDiscard(t2, this.actual.currentContext());
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.actual.currentContext()));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            R seed;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            ReduceSeedSubscriber reduceSeedSubscriber = this;
            synchronized (reduceSeedSubscriber) {
                seed = this.seed;
                this.seed = null;
            }
            if (seed == null) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            Operators.onDiscard(seed, this.actual.currentContext());
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.completePossiblyEmpty();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        R accumulatedValue() {
            R seed;
            ReduceSeedSubscriber reduceSeedSubscriber = this;
            synchronized (reduceSeedSubscriber) {
                seed = this.seed;
                this.seed = null;
            }
            return seed;
        }
    }
}

