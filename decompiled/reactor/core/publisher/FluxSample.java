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
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSample<T, U>
extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxSample(Flux<? extends T> source2, Publisher<U> other) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        CoreSubscriber<? super T> serial = Operators.serialize(actual);
        SampleMainSubscriber<? super T> main = new SampleMainSubscriber<T>(serial);
        actual.onSubscribe(main);
        this.other.subscribe(new SampleOther(main));
        return main;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SampleOther<T, U>
    implements InnerConsumer<U> {
        final SampleMainSubscriber<T> main;

        SampleOther(SampleMainSubscriber<T> main) {
            this.main = main;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main.other == Operators.cancelledSubscription();
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
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.main.setOther(s2);
        }

        @Override
        public void onNext(U t2) {
            SampleMainSubscriber<T> m4 = this.main;
            T v = m4.getAndNullValue();
            if (v != null) {
                if (m4.requested != 0L) {
                    m4.actual.onNext(v);
                    if (m4.requested != Long.MAX_VALUE) {
                        m4.decrement();
                    }
                    return;
                }
                m4.cancel();
                m4.actual.onError(Exceptions.failWithOverflow("Can't signal value due to lack of requests"));
                Operators.onDiscard(v, m4.ctx);
            }
        }

        @Override
        public void onError(Throwable t2) {
            SampleMainSubscriber<T> m4 = this.main;
            m4.cancelMain();
            m4.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            SampleMainSubscriber<T> m4 = this.main;
            m4.cancelMain();
            m4.actual.onComplete();
        }
    }

    static final class SampleMainSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile T value;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Object.class, "value");
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "other");
        volatile long requested;
        static final AtomicLongFieldUpdater<SampleMainSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleMainSubscriber.class, "requested");

        SampleMainSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
            this.ctx = actual.currentContext();
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (!MAIN.compareAndSet(this, null, s2)) {
                s2.cancel();
                if (this.main != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
                return;
            }
            s2.request(Long.MAX_VALUE);
        }

        void cancelMain() {
            Subscription s2 = this.main;
            if (s2 != Operators.cancelledSubscription() && (s2 = MAIN.getAndSet(this, Operators.cancelledSubscription())) != null && s2 != Operators.cancelledSubscription()) {
                s2.cancel();
            }
        }

        void cancelOther() {
            Subscription s2 = this.other;
            if (s2 != Operators.cancelledSubscription() && (s2 = OTHER.getAndSet(this, Operators.cancelledSubscription())) != null && s2 != Operators.cancelledSubscription()) {
                s2.cancel();
            }
        }

        void setOther(Subscription s2) {
            if (!OTHER.compareAndSet(this, null, s2)) {
                s2.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
                return;
            }
            s2.request(Long.MAX_VALUE);
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        @Override
        public void cancel() {
            this.cancelMain();
            this.cancelOther();
        }

        @Override
        public void onNext(T t2) {
            Object old = VALUE.getAndSet(this, t2);
            if (old != null) {
                Operators.onDiscard(old, this.ctx);
            }
        }

        @Override
        public void onError(Throwable t2) {
            this.cancelOther();
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.cancelOther();
            T v = this.value;
            if (v != null) {
                this.actual.onNext(this.value);
            }
            this.actual.onComplete();
        }

        @Nullable
        T getAndNullValue() {
            return VALUE.getAndSet(this, null);
        }

        void decrement() {
            REQUESTED.decrementAndGet(this);
        }
    }
}

