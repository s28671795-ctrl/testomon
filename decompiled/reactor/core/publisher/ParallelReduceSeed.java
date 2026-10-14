/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelReduceSeed<T, R>
extends ParallelFlux<R>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;
    final Supplier<R> initialSupplier;
    final BiFunction<R, ? super T, R> reducer;

    ParallelReduceSeed(ParallelFlux<? extends T> source2, Supplier<R> initialSupplier, BiFunction<R, ? super T, R> reducer) {
        this.source = source2;
        this.initialSupplier = initialSupplier;
        this.reducer = reducer;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void subscribe(CoreSubscriber<? super R>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            R initialValue;
            try {
                initialValue = Objects.requireNonNull(this.initialSupplier.get(), "The initialSupplier returned a null value");
            }
            catch (Throwable ex) {
                this.reportError(subscribers, Operators.onOperatorError(ex, subscribers[i].currentContext()));
                return;
            }
            parents[i] = new ParallelReduceSeedSubscriber<T, R>(subscribers[i], initialValue, this.reducer);
        }
        this.source.subscribe(parents);
    }

    void reportError(Subscriber<?>[] subscribers, Throwable ex) {
        for (Subscriber<?> s2 : subscribers) {
            Operators.error(s2, ex);
        }
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    static final class ParallelReduceSeedSubscriber<T, R>
    extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiFunction<R, ? super T, R> reducer;
        R accumulator;
        boolean done;

        ParallelReduceSeedSubscriber(CoreSubscriber<? super R> subscriber, R initialValue, BiFunction<R, ? super T, R> reducer) {
            super(subscriber);
            this.accumulator = initialValue;
            this.reducer = reducer;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            ParallelReduceSeedSubscriber parallelReduceSeedSubscriber = this;
            synchronized (parallelReduceSeedSubscriber) {
                R v;
                try {
                    if (this.accumulator == null) {
                        return;
                    }
                    v = Objects.requireNonNull(this.reducer.apply(this.accumulator, t2), "The reducer returned a null value");
                }
                catch (Throwable ex) {
                    this.onError(Operators.onOperatorError(this.s, ex, t2, this.actual.currentContext()));
                    return;
                }
                this.accumulator = v;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            R a;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            ParallelReduceSeedSubscriber parallelReduceSeedSubscriber = this;
            synchronized (parallelReduceSeedSubscriber) {
                a = this.accumulator;
                if (a != null) {
                    this.accumulator = null;
                }
            }
            if (a == null) {
                return;
            }
            Operators.onDiscard(a, this.currentContext());
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
            R a;
            ParallelReduceSeedSubscriber parallelReduceSeedSubscriber = this;
            synchronized (parallelReduceSeedSubscriber) {
                a = this.accumulator;
                if (a != null) {
                    this.accumulator = null;
                }
            }
            return a;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            R a;
            this.s.cancel();
            ParallelReduceSeedSubscriber parallelReduceSeedSubscriber = this;
            synchronized (parallelReduceSeedSubscriber) {
                a = this.accumulator;
                if (a != null) {
                    this.accumulator = null;
                }
            }
            if (a == null) {
                return;
            }
            Operators.onDiscard(a, this.currentContext());
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return !this.done && this.accumulator == null;
            }
            return super.scanUnsafe(key);
        }
    }
}

