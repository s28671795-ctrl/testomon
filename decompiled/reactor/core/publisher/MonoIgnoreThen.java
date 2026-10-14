/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoIgnoreThen<T>
extends Mono<T>
implements Scannable {
    final Publisher<?>[] ignore;
    final Mono<T> last;

    MonoIgnoreThen(Publisher<?>[] ignore, Mono<T> last) {
        this.ignore = Objects.requireNonNull(ignore, "ignore");
        this.last = Objects.requireNonNull(last, "last");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        ThenIgnoreMain<T> manager = new ThenIgnoreMain<T>(actual, this.ignore, this.last);
        actual.onSubscribe(manager);
        manager.subscribeNext();
    }

    <U> MonoIgnoreThen<U> shift(Mono<U> newLast) {
        Objects.requireNonNull(newLast, "newLast");
        Publisher<?>[] a = this.ignore;
        int n = a.length;
        Publisher[] b = new Publisher[n + 1];
        System.arraycopy(a, 0, b, 0, n);
        b[n] = this.last;
        return new MonoIgnoreThen<U>(b, newLast);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ThenIgnoreMain<T>
    implements InnerOperator<T, T> {
        final Publisher<?>[] ignoreMonos;
        final Mono<T> lastMono;
        final CoreSubscriber<? super T> actual;
        T value;
        int index;
        Subscription activeSubscription;
        boolean done;
        volatile int state;
        private static final AtomicIntegerFieldUpdater<ThenIgnoreMain> STATE = AtomicIntegerFieldUpdater.newUpdater(ThenIgnoreMain.class, "state");
        static final int HAS_REQUEST = 2;
        static final int HAS_SUBSCRIPTION = 4;
        static final int HAS_VALUE = 8;
        static final int HAS_COMPLETION = 16;
        static final int CANCELLED = 128;

        ThenIgnoreMain(CoreSubscriber<? super T> subscriber, Publisher<?>[] ignoreMonos, Mono<T> lastMono) {
            this.actual = subscriber;
            this.ignoreMonos = ignoreMonos;
            this.lastMono = lastMono;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.activeSubscription;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return ThenIgnoreMain.isCancelled(this.state);
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.activeSubscription, s2)) {
                this.activeSubscription = s2;
                int previousState = this.markHasSubscription();
                if (ThenIgnoreMain.isCancelled(previousState)) {
                    s2.cancel();
                    return;
                }
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void cancel() {
            int previousState = this.markCancelled();
            if (ThenIgnoreMain.hasSubscription(previousState)) {
                this.activeSubscription.cancel();
            }
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                int state;
                do {
                    if (ThenIgnoreMain.isCancelled(state = this.state)) {
                        return;
                    }
                    if (!ThenIgnoreMain.hasRequest(state)) continue;
                    return;
                } while (!STATE.compareAndSet(this, state, state | 2));
                if (ThenIgnoreMain.hasValue(state)) {
                    CoreSubscriber<T> actual = this.actual;
                    T v = this.value;
                    actual.onNext(v);
                    actual.onComplete();
                }
                return;
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onDiscard(t2, this.currentContext());
                return;
            }
            if (this.index != this.ignoreMonos.length) {
                Operators.onDiscard(t2, this.currentContext());
                return;
            }
            this.done = true;
            this.complete(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            if (this.index != this.ignoreMonos.length) {
                int previousState = this.markUnsubscribed();
                if (ThenIgnoreMain.isCancelled(previousState)) {
                    return;
                }
                this.activeSubscription = null;
                ++this.index;
                this.subscribeNext();
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        void subscribeNext() {
            Publisher<Object> m4;
            Publisher<?>[] a = this.ignoreMonos;
            while (true) {
                int i;
                if ((i = this.index) == a.length) {
                    m4 = this.lastMono;
                    if (m4 instanceof Callable) {
                        Object v;
                        if (ThenIgnoreMain.isCancelled(this.state)) {
                            return;
                        }
                        try {
                            v = ((Callable)((Object)m4)).call();
                        }
                        catch (Throwable ex) {
                            this.onError(Operators.onOperatorError(ex, this.currentContext()));
                            return;
                        }
                        if (v != null) {
                            this.onNext(v);
                        }
                        this.onComplete();
                    } else {
                        m4.subscribe(this);
                    }
                    return;
                }
                m4 = a[i];
                if (!(m4 instanceof Callable)) break;
                if (ThenIgnoreMain.isCancelled(this.state)) {
                    return;
                }
                try {
                    Operators.onDiscard(((Callable)((Object)m4)).call(), this.currentContext());
                }
                catch (Throwable ex) {
                    this.onError(Operators.onOperatorError(ex, this.currentContext()));
                    return;
                }
                this.index = i + 1;
            }
            m4.subscribe(this);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual().currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        final void complete(T value) {
            int s2;
            do {
                if (ThenIgnoreMain.isCancelled(s2 = this.state)) {
                    Operators.onDiscard(value, this.actual.currentContext());
                    return;
                }
                if (ThenIgnoreMain.hasRequest(s2) && STATE.compareAndSet(this, s2, s2 | 0x18)) {
                    CoreSubscriber<T> actual = this.actual;
                    actual.onNext(value);
                    actual.onComplete();
                    return;
                }
                this.value = value;
            } while (!STATE.compareAndSet(this, s2, s2 | 0x18));
        }

        final int markHasSubscription() {
            int state;
            do {
                if ((state = this.state) == 128) {
                    return state;
                }
                if ((state & 4) != 4) continue;
                return state;
            } while (!STATE.compareAndSet(this, state, state | 4));
            return state;
        }

        final int markUnsubscribed() {
            int state;
            do {
                if (ThenIgnoreMain.isCancelled(state = this.state)) {
                    return state;
                }
                if (ThenIgnoreMain.hasSubscription(state)) continue;
                return state;
            } while (!STATE.compareAndSet(this, state, state & 0xFFFFFFFB));
            return state;
        }

        final int markCancelled() {
            int state;
            do {
                if ((state = this.state) != 128) continue;
                return state;
            } while (!STATE.compareAndSet(this, state, 128));
            return state;
        }

        static boolean isCancelled(int s2) {
            return s2 == 128;
        }

        static boolean hasSubscription(int s2) {
            return (s2 & 4) == 4;
        }

        static boolean hasRequest(int s2) {
            return (s2 & 2) == 2;
        }

        static boolean hasValue(int s2) {
            return (s2 & 8) == 8;
        }
    }
}

