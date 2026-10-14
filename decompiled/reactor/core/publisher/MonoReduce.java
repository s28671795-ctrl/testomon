/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoReduce<T>
extends MonoFromFluxOperator<T, T>
implements Fuseable {
    final BiFunction<T, T, T> aggregator;

    MonoReduce(Flux<? extends T> source2, BiFunction<T, T, T> aggregator) {
        super(source2);
        this.aggregator = Objects.requireNonNull(aggregator, "aggregator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ReduceSubscriber<T>(actual, this.aggregator);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ReduceSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable,
    Fuseable.QueueSubscription<T> {
        static final Object CANCELLED = new Object();
        final BiFunction<T, T, T> aggregator;
        final CoreSubscriber<? super T> actual;
        T aggregate;
        Subscription s;
        boolean done;

        ReduceSubscriber(CoreSubscriber<? super T> actual, BiFunction<T, T, T> aggregator) {
            this.actual = actual;
            this.aggregator = aggregator;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return !this.done && this.aggregate == CANCELLED;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
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
            T r = this.aggregate;
            if (r == CANCELLED) {
                Operators.onDiscard(t2, this.actual.currentContext());
                return;
            }
            if (r == null) {
                ReduceSubscriber reduceSubscriber = this;
                synchronized (reduceSubscriber) {
                    if (this.aggregate == null) {
                        this.aggregate = t2;
                        return;
                    }
                }
                Operators.onDiscard(t2, this.actual.currentContext());
            } else {
                try {
                    ReduceSubscriber reduceSubscriber = this;
                    synchronized (reduceSubscriber) {
                        if (this.aggregate != CANCELLED) {
                            this.aggregate = Objects.requireNonNull(this.aggregator.apply(r, t2), "The aggregator returned a null value");
                            return;
                        }
                    }
                    Operators.onDiscard(t2, this.actual.currentContext());
                }
                catch (Throwable ex) {
                    this.done = true;
                    Context ctx = this.actual.currentContext();
                    ReduceSubscriber reduceSubscriber = this;
                    synchronized (reduceSubscriber) {
                        this.aggregate = null;
                    }
                    Operators.onDiscard(t2, ctx);
                    Operators.onDiscard(r, ctx);
                    this.actual.onError(Operators.onOperatorError(this.s, ex, t2, this.actual.currentContext()));
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            T r;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            ReduceSubscriber reduceSubscriber = this;
            synchronized (reduceSubscriber) {
                r = this.aggregate;
                this.aggregate = null;
            }
            if (r == CANCELLED) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            if (r != null) {
                Operators.onDiscard(r, this.actual.currentContext());
            }
            this.actual.onError(t2);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onComplete() {
            T r;
            if (this.done) {
                return;
            }
            this.done = true;
            ReduceSubscriber reduceSubscriber = this;
            synchronized (reduceSubscriber) {
                r = this.aggregate;
                this.aggregate = null;
            }
            if (r == CANCELLED) {
                return;
            }
            if (r == null) {
                this.actual.onComplete();
            } else {
                this.actual.onNext(r);
                this.actual.onComplete();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            T r;
            this.s.cancel();
            ReduceSubscriber reduceSubscriber = this;
            synchronized (reduceSubscriber) {
                r = this.aggregate;
                this.aggregate = CANCELLED;
            }
            if (r == null || r == CANCELLED) {
                return;
            }
            Operators.onDiscard(r, this.actual.currentContext());
        }

        @Override
        public void request(long n) {
            this.s.request(Long.MAX_VALUE);
        }

        @Override
        public T poll() {
            return null;
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void clear() {
        }
    }
}

