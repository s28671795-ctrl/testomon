/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoWhen
extends Mono<Void>
implements SourceProducer<Void> {
    final boolean delayError;
    final Publisher<?>[] sources;
    final Iterable<? extends Publisher<?>> sourcesIterable;

    MonoWhen(boolean delayError, Publisher<?> ... sources) {
        this.delayError = delayError;
        this.sources = Objects.requireNonNull(sources, "sources");
        this.sourcesIterable = null;
    }

    MonoWhen(boolean delayError, Iterable<? extends Publisher<?>> sourcesIterable) {
        this.delayError = delayError;
        this.sources = null;
        this.sourcesIterable = Objects.requireNonNull(sourcesIterable, "sourcesIterable");
    }

    @Nullable
    Mono<Void> whenAdditionalSource(Publisher<?> source2) {
        Publisher<?>[] oldSources = this.sources;
        if (oldSources != null) {
            int oldLen = oldSources.length;
            Publisher[] newSources = new Publisher[oldLen + 1];
            System.arraycopy(oldSources, 0, newSources, 0, oldLen);
            newSources[oldLen] = source2;
            return new MonoWhen(this.delayError, newSources);
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Void> actual) {
        Publisher<?>[] a;
        int n = 0;
        if (this.sources != null) {
            a = this.sources;
            n = a.length;
        } else {
            a = new Publisher[8];
            for (Publisher<?> m4 : this.sourcesIterable) {
                if (n == a.length) {
                    Publisher[] b = new Publisher[n + (n >> 2)];
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
        WhenCoordinator parent = new WhenCoordinator(a, actual, n, this.delayError);
        actual.onSubscribe(parent);
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

    static final class WhenInner
    implements InnerConsumer<Object> {
        final WhenCoordinator parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<WhenInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(WhenInner.class, Subscription.class, "s");
        Throwable error;

        WhenInner(WhenCoordinator parent) {
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
            Operators.onDiscard(t2, this.currentContext());
        }

        @Override
        public void onError(Throwable t2) {
            this.error = t2;
            if (this.parent.delayError) {
                if (!this.parent.signal()) {
                    Operators.onErrorDropped(t2, this.parent.actual.currentContext());
                }
            } else {
                long previousState = WhenCoordinator.markForceTerminated(this.parent);
                if (WhenCoordinator.isInterrupted(previousState)) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onError(t2);
            }
        }

        @Override
        public void onComplete() {
            this.parent.signal();
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class WhenCoordinator
    implements InnerProducer<Void>,
    Fuseable,
    Fuseable.QueueSubscription<Void> {
        final CoreSubscriber<? super Void> actual;
        final Publisher<?>[] sources;
        final WhenInner[] subscribers;
        final boolean delayError;
        volatile long state;
        static final AtomicLongFieldUpdater<WhenCoordinator> STATE = AtomicLongFieldUpdater.newUpdater(WhenCoordinator.class, "state");
        static final long INTERRUPTED_FLAG = Long.MIN_VALUE;
        static final long REQUESTED_ONCE_FLAG = 0x4000000000000000L;
        static final long MAX_SIGNALS_VALUE = Integer.MAX_VALUE;

        WhenCoordinator(Publisher<?>[] sources, CoreSubscriber<? super Void> actual, int n, boolean delayError) {
            this.sources = sources;
            this.actual = actual;
            this.delayError = delayError;
            this.subscribers = new WhenInner[n];
            for (int i = 0; i < n; ++i) {
                this.subscribers[i] = new WhenInner(this);
            }
        }

        @Override
        public CoreSubscriber<? super Void> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return WhenCoordinator.deliveredSignals(this.state) == this.subscribers.length;
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
                return WhenCoordinator.isInterrupted(state) && WhenCoordinator.deliveredSignals(state) != this.subscribers.length;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        boolean signal() {
            WhenInner[] a = this.subscribers;
            int n = a.length;
            long previousState = WhenCoordinator.markDeliveredSignal(this);
            int deliveredSignals = WhenCoordinator.deliveredSignals(previousState);
            if (WhenCoordinator.isInterrupted(previousState) || deliveredSignals == n) {
                return false;
            }
            if (deliveredSignals + 1 != n) {
                return true;
            }
            Throwable error = null;
            Throwable compositeError = null;
            for (int i = 0; i < a.length; ++i) {
                WhenInner m4 = a[i];
                Throwable e = m4.error;
                if (e == null) continue;
                if (compositeError != null) {
                    compositeError.addSuppressed(e);
                    continue;
                }
                if (error != null) {
                    compositeError = Exceptions.multiple(error, e);
                    continue;
                }
                error = e;
            }
            if (compositeError != null) {
                this.actual.onError(compositeError);
            } else if (error != null) {
                this.actual.onError(error);
            } else {
                this.actual.onComplete();
            }
            return true;
        }

        @Override
        public void request(long n) {
            long previousState = WhenCoordinator.markRequestedOnce(this);
            if (WhenCoordinator.isRequestedOnce(previousState) || WhenCoordinator.isInterrupted(previousState)) {
                return;
            }
            Publisher<?>[] sources = this.sources;
            WhenInner[] subs = this.subscribers;
            for (int i = 0; i < this.subscribers.length; ++i) {
                sources[i].subscribe(subs[i]);
            }
        }

        @Override
        public void cancel() {
            long previousState = WhenCoordinator.markInterrupted(this);
            if (WhenCoordinator.isInterrupted(previousState) || !WhenCoordinator.isRequestedOnce(previousState) || WhenCoordinator.deliveredSignals(previousState) == this.subscribers.length) {
                return;
            }
            for (WhenInner ms : this.subscribers) {
                ms.cancel();
            }
        }

        void cancelExcept(WhenInner source2) {
            for (WhenInner ms : this.subscribers) {
                if (ms == source2) continue;
                ms.cancel();
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

        static long markRequestedOnce(WhenCoordinator instance) {
            long nextState;
            long state;
            do {
                if (!WhenCoordinator.isInterrupted(state = instance.state) && !WhenCoordinator.isRequestedOnce(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | 0x4000000000000000L));
            return state;
        }

        static long markDeliveredSignal(WhenCoordinator instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!WhenCoordinator.isInterrupted(state = instance.state) && n != WhenCoordinator.deliveredSignals(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state + 1L));
            return state;
        }

        static long markForceTerminated(WhenCoordinator instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!WhenCoordinator.isInterrupted(state = instance.state) && n != WhenCoordinator.deliveredSignals(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state & Integer.MIN_VALUE | (long)n | Long.MIN_VALUE));
            return state;
        }

        static long markInterrupted(WhenCoordinator instance) {
            long nextState;
            long state;
            int n = instance.subscribers.length;
            do {
                if (!WhenCoordinator.isInterrupted(state = instance.state) && n != WhenCoordinator.deliveredSignals(state)) continue;
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

