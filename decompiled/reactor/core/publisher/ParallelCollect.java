/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelCollect<T, C>
extends ParallelFlux<C>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;
    final Supplier<? extends C> initialCollection;
    final BiConsumer<? super C, ? super T> collector;

    ParallelCollect(ParallelFlux<? extends T> source2, Supplier<? extends C> initialCollection, BiConsumer<? super C, ? super T> collector) {
        this.source = source2;
        this.initialCollection = initialCollection;
        this.collector = collector;
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
    public void subscribe(CoreSubscriber<? super C>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            C initialValue;
            try {
                initialValue = Objects.requireNonNull(this.initialCollection.get(), "The initialSupplier returned a null value");
            }
            catch (Throwable ex) {
                this.reportError(subscribers, Operators.onOperatorError(ex, subscribers[i].currentContext()));
                return;
            }
            parents[i] = new ParallelCollectSubscriber<T, C>(subscribers[i], initialValue, this.collector);
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

    static final class ParallelCollectSubscriber<T, C>
    extends Operators.BaseFluxToMonoOperator<T, C> {
        final BiConsumer<? super C, ? super T> collector;
        C collection;
        boolean done;

        ParallelCollectSubscriber(CoreSubscriber<? super C> subscriber, C initialValue, BiConsumer<? super C, ? super T> collector) {
            super(subscriber);
            this.collection = initialValue;
            this.collector = collector;
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
                ParallelCollectSubscriber parallelCollectSubscriber = this;
                synchronized (parallelCollectSubscriber) {
                    C collection = this.collection;
                    if (collection != null) {
                        this.collector.accept(collection, t2);
                    }
                }
            }
            catch (Throwable ex) {
                this.onError(Operators.onOperatorError(this.s, ex, t2, this.actual.currentContext()));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            C c;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            ParallelCollectSubscriber parallelCollectSubscriber = this;
            synchronized (parallelCollectSubscriber) {
                c = this.collection;
                this.collection = null;
            }
            if (c == null) {
                return;
            }
            Operators.onDiscard(c, this.actual.currentContext());
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
            C c;
            this.s.cancel();
            ParallelCollectSubscriber parallelCollectSubscriber = this;
            synchronized (parallelCollectSubscriber) {
                c = this.collection;
                this.collection = null;
            }
            if (c != null) {
                Operators.onDiscard(c, this.actual.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        C accumulatedValue() {
            C c;
            ParallelCollectSubscriber parallelCollectSubscriber = this;
            synchronized (parallelCollectSubscriber) {
                c = this.collection;
                this.collection = null;
            }
            return c;
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
                return this.collection == null && !this.done;
            }
            return super.scanUnsafe(key);
        }
    }
}

