/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxZip;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoZip<T, R>
extends Mono<R>
implements SourceProducer<R> {
    final boolean delayError;
    final Mono<?>[] sources;
    final Iterable<? extends Mono<?>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    <U> MonoZip(boolean delayError, Mono<? extends T> p1, Mono<? extends U> p2, BiFunction<? super T, ? super U, ? extends R> zipper2) {
        this(delayError, new FluxZip.PairwiseZipper(new BiFunction[]{Objects.requireNonNull(zipper2, "zipper2")}), Objects.requireNonNull(p1, "p1"), Objects.requireNonNull(p2, "p2"));
    }

    MonoZip(boolean delayError, Function<? super Object[], ? extends R> zipper, Mono<?> ... sources) {
        this.delayError = delayError;
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.sources = Objects.requireNonNull(sources, "sources");
        this.sourcesIterable = null;
    }

    MonoZip(boolean delayError, Function<? super Object[], ? extends R> zipper, Iterable<? extends Mono<?>> sourcesIterable) {
        this.delayError = delayError;
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.sources = null;
        this.sourcesIterable = Objects.requireNonNull(sourcesIterable, "sourcesIterable");
    }

    @Nullable
    Mono<R> zipAdditionalSource(Mono source2, BiFunction zipper) {
        Mono<?>[] oldSources = this.sources;
        if (oldSources != null && this.zipper instanceof FluxZip.PairwiseZipper) {
            int oldLen = oldSources.length;
            Mono[] newSources = new Mono[oldLen + 1];
            System.arraycopy(oldSources, 0, newSources, 0, oldLen);
            newSources[oldLen] = source2;
            FluxZip.PairwiseZipper z = ((FluxZip.PairwiseZipper)this.zipper).then(zipper);
            return new MonoZip<T, R>(this.delayError, z, newSources);
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super R> actual) {
        Mono<?>[] a;
        int n = 0;
        if (this.sources != null) {
            a = this.sources;
            n = a.length;
        } else {
            a = new Mono[8];
            for (Mono<?> m4 : this.sourcesIterable) {
                if (n == a.length) {
                    Mono[] b = new Mono[n + (n >> 2)];
                    System.arraycopy(a, 0, b, 0, n);
                    a = b;
                }
                a[n++] = m4;
            }
        }
        if (n == 0) {
            Operators.complete(actual);
            return;
        }
        actual.onSubscribe(new ZipCoordinator<R>(a, actual, n, this.delayError, this.zipper));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ZipInner<R>
    implements InnerConsumer<Object> {
        final ZipCoordinator<R> parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ZipInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ZipInner.class, Subscription.class, "s");
        Object value;
        Throwable error;

        ZipInner(ZipCoordinator<R> parent) {
            this.parent = parent;
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
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.setOnce(S, this, s2)) {
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(Object t2) {
            if (this.value == null) {
                this.value = t2;
                this.parent.signal();
                Subscription a = this.s;
                if (a != Operators.cancelledSubscription() && S.compareAndSet(this, a, Operators.cancelledSubscription())) {
                    return;
                }
                Operators.onDiscard(t2, this.parent.actual.currentContext());
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.value != null) {
                Operators.onErrorDropped(t2, this.parent.actual.currentContext());
                return;
            }
            this.error = t2;
            if (this.parent.delayError) {
                if (!this.parent.signal()) {
                    Operators.onErrorDropped(t2, this.parent.actual.currentContext());
                }
            } else {
                long previousState = ZipCoordinator.markForceTerminated(this.parent);
                if (ZipCoordinator.isInterrupted(previousState)) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onError(t2);
            }
        }

        @Override
        public void onComplete() {
            if (this.value != null) {
                return;
            }
            if (this.parent.delayError) {
                this.parent.signal();
            } else {
                long previousState = ZipCoordinator.markForceTerminated(this.parent);
                if (ZipCoordinator.isInterrupted(previousState)) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onComplete();
            }
        }

        boolean cancel() {
            return !Operators.terminate(S, this);
        }
    }

    static final class ZipCoordinator<R>
    implements InnerProducer<R>,
    Fuseable,
    Fuseable.QueueSubscription<R> {
        final Mono<?>[] sources;
        final ZipInner<R>[] subscribers;
        final CoreSubscriber<? super R> actual;
        final boolean delayError;
        final Function<? super Object[], ? extends R> zipper;
        volatile long state;
        static final AtomicLongFieldUpdater<ZipCoordinator> STATE = AtomicLongFieldUpdater.newUpdater(ZipCoordinator.class, "state");
        static final long INTERRUPTED_FLAG = Long.MIN_VALUE;
        static final long REQUESTED_ONCE_FLAG = 0x4000000000000000L;
        static final long MAX_SIGNALS_VALUE = Integer.MAX_VALUE;

        ZipCoordinator(Mono<?>[] sources, CoreSubscriber<? super R> subscriber, int n, boolean delayError, Function<? super Object[], ? extends R> zipper) {
            this.sources = sources;
            this.actual = subscriber;
            this.delayError = delayError;
            this.zipper = zipper;
            ZipInner[] ss = new ZipInner[n];
            this.subscribers = ss;
            for (int i = 0; i < n; ++i) {
                ss[i] = new ZipInner(this);
            }
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return ZipCoordinator.deliveredSignals(this.state) == this.subscribers.length;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.subscribers.length;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.CANCELLED) {
                long state = this.state;
                return ZipCoordinator.isInterrupted(state) && ZipCoordinator.deliveredSignals(state) != this.subscribers.length;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public void request(long n) {
            long previousState = ZipCoordinator.markRequestedOnce(this);
            if (ZipCoordinator.isRequestedOnce(previousState) || ZipCoordinator.isInterrupted(previousState)) {
                return;
            }
            Mono<?>[] monos = this.sources;
            ZipInner<R>[] subs = this.subscribers;
            for (int i = 0; i < this.subscribers.length; ++i) {
                monos[i].subscribe((CoreSubscriber<?>)subs[i]);
            }
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        boolean signal() {
            ZipInner<R>[] a = this.subscribers;
            int n = a.length;
            long previousState = ZipCoordinator.markDeliveredSignal(this);
            int deliveredSignals = ZipCoordinator.deliveredSignals(previousState);
            if (ZipCoordinator.isInterrupted(previousState) || deliveredSignals == n) {
                return false;
            }
            if (deliveredSignals + 1 != n) {
                return true;
            }
            Object[] o = new Object[n];
            Throwable error = null;
            Throwable compositeError = null;
            boolean hasEmpty = false;
            for (int i = 0; i < a.length; ++i) {
                ZipInner<R> m4 = a[i];
                Object v = m4.value;
                if (v != null) {
                    o[i] = v;
                    continue;
                }
                Throwable e = m4.error;
                if (e != null) {
                    if (compositeError != null) {
                        compositeError.addSuppressed(e);
                        continue;
                    }
                    if (error != null) {
                        compositeError = Exceptions.multiple(error, e);
                        continue;
                    }
                    error = e;
                    continue;
                }
                hasEmpty = true;
            }
            if (compositeError != null) {
                this.actual.onError(compositeError);
            } else if (error != null) {
                this.actual.onError(error);
            } else if (hasEmpty) {
                this.actual.onComplete();
            } else {
                R r;
                try {
                    r = Objects.requireNonNull(this.zipper.apply((Object[])o), "zipper produced a null value");
                }
                catch (Throwable t2) {
                    Operators.onDiscardMultiple(Arrays.asList(o), this.actual.currentContext());
                    this.actual.onError(Operators.onOperatorError(null, t2, o, this.actual.currentContext()));
                    return true;
                }
                this.actual.onNext(r);
                this.actual.onComplete();
            }
            return true;
        }

        @Override
        public void cancel() {
            long previousState = ZipCoordinator.markInterrupted(this);
            if (ZipCoordinator.isInterrupted(previousState) || !ZipCoordinator.isRequestedOnce(previousState) || ZipCoordinator.deliveredSignals(previousState) == this.subscribers.length) {
                return;
            }
            Context context = this.actual.currentContext();
            for (ZipInner<R> ms : this.subscribers) {
                if (!ms.cancel()) continue;
                Operators.onDiscard(ms.value, context);
            }
        }

        void cancelExcept(ZipInner<R> source2) {
            Context context = this.actual.currentContext();
            for (ZipInner<R> ms : this.subscribers) {
                if (ms == source2 || !ms.cancel()) continue;
                Operators.onDiscard(ms.value, context);
            }
        }

        @Override
        public R poll() {
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

        static <T> long markRequestedOnce(ZipCoordinator<T> instance) {
            long nextState;
            long state;
            do {
                if (!ZipCoordinator.isInterrupted(state = instance.state) && !ZipCoordinator.isRequestedOnce(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | 0x4000000000000000L));
            return state;
        }

        static <T> long markDeliveredSignal(ZipCoordinator<T> instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!ZipCoordinator.isInterrupted(state = instance.state) && n != ZipCoordinator.deliveredSignals(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state + 1L));
            return state;
        }

        static <T> long markForceTerminated(ZipCoordinator<T> instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!ZipCoordinator.isInterrupted(state = instance.state) && n != ZipCoordinator.deliveredSignals(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state & Integer.MIN_VALUE | (long)n | Long.MIN_VALUE));
            return state;
        }

        static <T> long markInterrupted(ZipCoordinator<T> instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!ZipCoordinator.isInterrupted(state = instance.state) && n != ZipCoordinator.deliveredSignals(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | Long.MIN_VALUE));
            return state;
        }

        static boolean isRequestedOnce(long state) {
            return (state & 0x4000000000000000L) == 0x4000000000000000L;
        }

        static int deliveredSignals(long state) {
            return (int)(state & Integer.MAX_VALUE);
        }

        static boolean isInterrupted(long state) {
            return (state & Long.MIN_VALUE) == Long.MIN_VALUE;
        }
    }
}

