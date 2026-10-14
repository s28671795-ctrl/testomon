/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelMergeReduce<T>
extends Mono<T>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;
    final BiFunction<T, T, T> reducer;

    ParallelMergeReduce(ParallelFlux<? extends T> source2, BiFunction<T, T, T> reducer) {
        this.source = source2;
        this.reducer = reducer;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeReduceMain<? super T> parent = new MergeReduceMain<T>(this.source, actual, this.source.parallelism(), this.reducer);
        actual.onSubscribe(parent);
    }

    static final class SlotPair<T> {
        T first;
        T second;
        volatile int acquireIndex;
        static final AtomicIntegerFieldUpdater<SlotPair> ACQ = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "acquireIndex");
        volatile int releaseIndex;
        static final AtomicIntegerFieldUpdater<SlotPair> REL = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "releaseIndex");

        SlotPair() {
        }

        int tryAcquireSlot() {
            int acquired;
            do {
                if ((acquired = this.acquireIndex) < 2) continue;
                return -1;
            } while (!ACQ.compareAndSet(this, acquired, acquired + 1));
            return acquired;
        }

        boolean releaseSlot() {
            return REL.incrementAndGet(this) == 2;
        }
    }

    static final class MergeReduceInner<T>
    implements InnerConsumer<T> {
        final MergeReduceMain<T> parent;
        final BiFunction<T, T, T> reducer;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeReduceInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeReduceInner.class, Subscription.class, "s");
        T value;
        boolean done;

        MergeReduceInner(MergeReduceMain<T> parent, BiFunction<T, T, T> reducer) {
            this.parent = parent;
            this.reducer = reducer;
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
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
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.setOnce(S, this, s2)) {
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.currentContext());
                return;
            }
            T v = this.value;
            if (v == null) {
                this.value = t2;
            } else {
                try {
                    v = Objects.requireNonNull(this.reducer.apply(v, t2), "The reducer returned a null value");
                }
                catch (Throwable ex) {
                    this.onError(Operators.onOperatorError(this.s, ex, t2, this.currentContext()));
                    return;
                }
                this.value = v;
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.parent.actual.currentContext());
                return;
            }
            this.done = true;
            this.parent.innerError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete(this.value);
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class MergeReduceMain<T>
    implements InnerProducer<T>,
    Fuseable,
    Fuseable.QueueSubscription<T> {
        final ParallelFlux<? extends T> source;
        final CoreSubscriber<? super T> actual;
        final MergeReduceInner<T>[] subscribers;
        final BiFunction<T, T, T> reducer;
        volatile SlotPair<T> current;
        static final AtomicReferenceFieldUpdater<MergeReduceMain, SlotPair> CURRENT = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, SlotPair.class, "current");
        volatile int remaining;
        static final AtomicIntegerFieldUpdater<MergeReduceMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(MergeReduceMain.class, "remaining");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeReduceMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, Throwable.class, "error");

        MergeReduceMain(ParallelFlux<? extends T> source2, CoreSubscriber<? super T> actual, int n, BiFunction<T, T, T> reducer) {
            this.actual = actual;
            this.source = source2;
            MergeReduceInner[] a = new MergeReduceInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new MergeReduceInner<T>(this, reducer);
            }
            this.subscribers = a;
            this.reducer = reducer;
            REMAINING.lazySet(this, n | Integer.MIN_VALUE);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.remaining == 0;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.remaining == Integer.MIN_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Nullable
        SlotPair<T> addValue(T value) {
            int c;
            SlotPair<T> curr;
            while (true) {
                if ((curr = this.current) == null && !CURRENT.compareAndSet(this, null, curr = new SlotPair())) {
                    continue;
                }
                c = curr.tryAcquireSlot();
                if (c >= 0) break;
                CURRENT.compareAndSet(this, curr, null);
            }
            if (c == 0) {
                curr.first = value;
            } else {
                curr.second = value;
            }
            if (curr.releaseSlot()) {
                CURRENT.compareAndSet(this, curr, null);
                return curr;
            }
            return null;
        }

        @Override
        public void cancel() {
            int r = REMAINING.getAndSet(this, Integer.MIN_VALUE);
            if ((r & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                for (MergeReduceInner<T> inner : this.subscribers) {
                    inner.cancel();
                }
            }
        }

        @Override
        public void request(long n) {
            int r = this.remaining;
            if ((r & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                return;
            }
            if (REMAINING.compareAndSet(this, r, r & Integer.MAX_VALUE)) {
                this.source.subscribe(this.subscribers);
            }
        }

        void innerError(Throwable ex) {
            if (ERROR.compareAndSet(this, null, ex)) {
                this.cancel();
                this.actual.onError(ex);
            } else if (this.error != ex) {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }

        void innerComplete(@Nullable T value) {
            SlotPair<T> sp2;
            if (value != null) {
                while ((sp2 = this.addValue(value)) != null) {
                    try {
                        value = Objects.requireNonNull(this.reducer.apply(sp2.first, sp2.second), "The reducer returned a null value");
                    }
                    catch (Throwable ex) {
                        this.innerError(Operators.onOperatorError(this, ex, this.actual.currentContext()));
                        return;
                    }
                }
            }
            if (MergeReduceMain.decrementAndGet(this) == 0) {
                sp2 = this.current;
                CURRENT.lazySet(this, null);
                if (sp2 != null) {
                    this.actual.onNext(sp2.first);
                    this.actual.onComplete();
                } else {
                    this.actual.onComplete();
                }
            }
        }

        static <T> int decrementAndGet(MergeReduceMain<T> instance) {
            int next;
            int prev;
            do {
                if ((prev = instance.remaining) != Integer.MIN_VALUE) continue;
                return Integer.MIN_VALUE;
            } while (!REMAINING.compareAndSet(instance, prev, next = prev - 1));
            return next;
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

