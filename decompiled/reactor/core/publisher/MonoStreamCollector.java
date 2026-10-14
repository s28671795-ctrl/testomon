/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoStreamCollector<T, A, R>
extends MonoFromFluxOperator<T, R>
implements Fuseable {
    final Collector<? super T, A, ? extends R> collector;

    MonoStreamCollector(Flux<? extends T> source2, Collector<? super T, A, ? extends R> collector) {
        super(source2);
        this.collector = Objects.requireNonNull(collector, "collector");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        A container = this.collector.supplier().get();
        BiConsumer<A, ? super T> accumulator = this.collector.accumulator();
        Function<A, ? extends R> finisher = this.collector.finisher();
        return new StreamCollectorSubscriber<T, A, R>(actual, container, accumulator, finisher);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class StreamCollectorSubscriber<T, A, R>
    extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiConsumer<? super A, ? super T> accumulator;
        final Function<? super A, ? extends R> finisher;
        A container;
        boolean done;

        StreamCollectorSubscriber(CoreSubscriber<? super R> actual, A container, BiConsumer<? super A, ? super T> accumulator, Function<? super A, ? extends R> finisher) {
            super(actual);
            this.container = container;
            this.accumulator = accumulator;
            this.finisher = finisher;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            return super.scanUnsafe(key);
        }

        void discardIntermediateContainer(A a) {
            Context ctx = this.actual.currentContext();
            if (a instanceof Collection) {
                Operators.onDiscardMultiple((Collection)a, ctx);
            } else {
                Operators.onDiscard(a, ctx);
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
            try {
                StreamCollectorSubscriber streamCollectorSubscriber = this;
                synchronized (streamCollectorSubscriber) {
                    A container = this.container;
                    if (container != null) {
                        this.accumulator.accept(container, t2);
                        return;
                    }
                }
                Operators.onDiscard(t2, this.actual.currentContext());
            }
            catch (Throwable ex) {
                Context ctx = this.actual.currentContext();
                Operators.onDiscard(t2, ctx);
                this.onError(Operators.onOperatorError(this.s, ex, t2, ctx));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            A c;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            StreamCollectorSubscriber streamCollectorSubscriber = this;
            synchronized (streamCollectorSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c == null) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.discardIntermediateContainer(c);
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
        public void cancel() {
            A c;
            super.cancel();
            StreamCollectorSubscriber streamCollectorSubscriber = this;
            synchronized (streamCollectorSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c != null) {
                this.discardIntermediateContainer(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        R accumulatedValue() {
            R r;
            A c;
            StreamCollectorSubscriber streamCollectorSubscriber = this;
            synchronized (streamCollectorSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c == null) {
                return null;
            }
            try {
                r = this.finisher.apply(c);
            }
            catch (Throwable ex) {
                this.discardIntermediateContainer(c);
                this.actual.onError(Operators.onOperatorError(ex, this.actual.currentContext()));
                return null;
            }
            if (r == null) {
                this.actual.onError(Operators.onOperatorError(new NullPointerException("Collector returned null"), this.actual.currentContext()));
            }
            return r;
        }
    }
}

