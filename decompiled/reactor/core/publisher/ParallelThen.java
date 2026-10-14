/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
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

final class ParallelThen
extends Mono<Void>
implements Scannable,
Fuseable {
    final ParallelFlux<?> source;

    ParallelThen(ParallelFlux<?> source2) {
        this.source = source2;
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
    public void subscribe(CoreSubscriber<? super Void> actual) {
        ThenMain parent = new ThenMain(actual, this.source);
        actual.onSubscribe(parent);
    }

    static final class ThenInner
    implements InnerConsumer<Object> {
        final ThenMain parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ThenInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ThenInner.class, Subscription.class, "s");

        ThenInner(ThenMain parent) {
            this.parent = parent;
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
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
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
        public void onNext(Object t2) {
            Operators.onDiscard(t2, this.parent.actual.currentContext());
        }

        @Override
        public void onError(Throwable t2) {
            this.parent.innerError(t2, this);
        }

        @Override
        public void onComplete() {
            this.parent.innerComplete();
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class ThenMain
    implements InnerProducer<Void>,
    Fuseable,
    Fuseable.QueueSubscription<Void> {
        final ThenInner[] subscribers;
        final CoreSubscriber<? super Void> actual;
        final ParallelFlux<?> source;
        volatile long state;
        static final AtomicLongFieldUpdater<ThenMain> STATE = AtomicLongFieldUpdater.newUpdater(ThenMain.class, "state");
        static final long CANCELLED_FLAG = Long.MIN_VALUE;
        static final long REQUESTED_FLAG = 0x4000000000000000L;
        static final long INNER_COMPLETED_MAX = Integer.MAX_VALUE;

        ThenMain(CoreSubscriber<? super Void> actual, ParallelFlux<?> source2) {
            this.actual = actual;
            this.source = source2;
            int n = source2.parallelism();
            ThenInner[] a = new ThenInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new ThenInner(this);
            }
            this.subscribers = a;
        }

        @Override
        public CoreSubscriber<? super Void> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return ThenMain.innersCompletedCount(this.state) == this.source.parallelism();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return ThenMain.isCancelled(this.state) && ThenMain.innersCompletedCount(this.state) != this.source.parallelism();
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void cancel() {
            long previousState = ThenMain.markCancelled(this);
            if (ThenMain.isCancelled(previousState) || !ThenMain.isRequestedOnce(previousState)) {
                return;
            }
            for (ThenInner inner : this.subscribers) {
                inner.cancel();
            }
        }

        @Override
        public void request(long n) {
            if (!STATE.compareAndSet(this, 0L, 0x4000000000000000L)) {
                return;
            }
            this.source.subscribe(this.subscribers);
        }

        void innerError(Throwable ex, ThenInner innerCaller) {
            long previousState = ThenMain.markForceTerminated(this);
            int n = this.source.parallelism();
            if (ThenMain.isCancelled(previousState) || ThenMain.innersCompletedCount(previousState) == n) {
                return;
            }
            for (ThenInner inner : this.subscribers) {
                if (inner == innerCaller) continue;
                inner.cancel();
            }
            this.actual.onError(ex);
        }

        void innerComplete() {
            long previousState = ThenMain.markInnerCompleted(this);
            int n = this.source.parallelism();
            int innersCompletedCount = ThenMain.innersCompletedCount(previousState);
            if (ThenMain.isCancelled(previousState) || innersCompletedCount == n) {
                return;
            }
            if (innersCompletedCount + 1 == n) {
                this.actual.onComplete();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public Void poll() {
            return null;
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

        static long markForceTerminated(ThenMain instance) {
            long nextState;
            long state;
            int n = instance.source.parallelism();
            do {
                if (!ThenMain.isCancelled(state = instance.state) && ThenMain.innersCompletedCount(state) != n) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state & Integer.MIN_VALUE | Long.MIN_VALUE | (long)n));
            return state;
        }

        static boolean isRequestedOnce(long state) {
            return (state & 0x4000000000000000L) == 0x4000000000000000L;
        }

        static long markCancelled(ThenMain instance) {
            long nextState;
            long state;
            int n = instance.source.parallelism();
            do {
                if (!ThenMain.isCancelled(state = instance.state) && ThenMain.innersCompletedCount(state) != n) continue;
                return state;
            } while (!STATE.weakCompareAndSet(instance, state, nextState = state | Long.MIN_VALUE));
            return state;
        }

        static boolean isCancelled(long state) {
            return (state & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static int innersCompletedCount(long state) {
            return (int)(state & Integer.MAX_VALUE);
        }

        static long markInnerCompleted(ThenMain instance) {
            long nextState;
            long state;
            int n = instance.source.parallelism();
            do {
                if (!ThenMain.isCancelled(state = instance.state) && ThenMain.innersCompletedCount(state) != n) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state + 1L));
            return state;
        }
    }
}

