/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxTimeout<T, U, V>
extends InternalFluxOperator<T, T> {
    final Publisher<U> firstTimeout;
    final Function<? super T, ? extends Publisher<V>> itemTimeout;
    final Publisher<? extends T> other;
    final String timeoutDescription;

    FluxTimeout(Flux<? extends T> source2, Publisher<U> firstTimeout, Function<? super T, ? extends Publisher<V>> itemTimeout, String timeoutDescription) {
        super(source2);
        this.firstTimeout = Objects.requireNonNull(firstTimeout, "firstTimeout");
        this.itemTimeout = Objects.requireNonNull(itemTimeout, "itemTimeout");
        this.other = null;
        this.timeoutDescription = FluxTimeout.addNameToTimeoutDescription(source2, Objects.requireNonNull(timeoutDescription, "timeoutDescription is needed when no fallback"));
    }

    FluxTimeout(Flux<? extends T> source2, Publisher<U> firstTimeout, Function<? super T, ? extends Publisher<V>> itemTimeout, Publisher<? extends T> other) {
        super(source2);
        this.firstTimeout = Objects.requireNonNull(firstTimeout, "firstTimeout");
        this.itemTimeout = Objects.requireNonNull(itemTimeout, "itemTimeout");
        this.other = Objects.requireNonNull(other, "other");
        this.timeoutDescription = null;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new TimeoutMainSubscriber(actual, this.firstTimeout, this.itemTimeout, this.other, this.timeoutDescription);
    }

    @Nullable
    static String addNameToTimeoutDescription(Publisher<?> source2, @Nullable String timeoutDescription) {
        if (timeoutDescription == null) {
            return null;
        }
        Scannable s2 = Scannable.from(source2);
        if (s2.isScanAvailable()) {
            return timeoutDescription + " in '" + s2.name() + "'";
        }
        return timeoutDescription;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TimeoutTimeoutSubscriber
    implements InnerConsumer<Object>,
    IndexedCancellable {
        final TimeoutMainSubscriber<?, ?> main;
        final long index;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<TimeoutTimeoutSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(TimeoutTimeoutSubscriber.class, Subscription.class, "s");

        TimeoutTimeoutSubscriber(TimeoutMainSubscriber<?, ?> main, long index) {
            this.main = main;
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (!S.compareAndSet(this, null, s2)) {
                s2.cancel();
                if (this.s != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            } else {
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(Object t2) {
            this.s.cancel();
            this.main.doTimeout(this.index);
        }

        @Override
        public void onError(Throwable t2) {
            this.main.doError(this.index, t2);
        }

        @Override
        public void onComplete() {
            this.main.doTimeout(this.index);
        }

        @Override
        public void cancel() {
            Subscription a = this.s;
            if (a != Operators.cancelledSubscription() && (a = S.getAndSet(this, Operators.cancelledSubscription())) != null && a != Operators.cancelledSubscription()) {
                a.cancel();
            }
        }

        @Override
        public long index() {
            return this.index;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static enum CancelledIndexedCancellable implements IndexedCancellable
    {
        INSTANCE;


        @Override
        public long index() {
            return Long.MAX_VALUE;
        }

        @Override
        public void cancel() {
        }
    }

    static interface IndexedCancellable {
        public long index();

        public void cancel();
    }

    static final class TimeoutOtherSubscriber<T>
    implements InnerConsumer<T> {
        final CoreSubscriber<? super T> actual;
        final Operators.MultiSubscriptionSubscriber<T, T> arbiter;

        TimeoutOtherSubscriber(CoreSubscriber<? super T> actual, Operators.MultiSubscriptionSubscriber<T, T> arbiter) {
            this.actual = actual;
            this.arbiter = arbiter;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.arbiter.set(s2);
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class TimeoutMainSubscriber<T, V>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Publisher<?> firstTimeout;
        final Function<? super T, ? extends Publisher<V>> itemTimeout;
        final Publisher<? extends T> other;
        final String timeoutDescription;
        Subscription s;
        volatile IndexedCancellable timeout;
        static final AtomicReferenceFieldUpdater<TimeoutMainSubscriber, IndexedCancellable> TIMEOUT = AtomicReferenceFieldUpdater.newUpdater(TimeoutMainSubscriber.class, IndexedCancellable.class, "timeout");
        volatile long index;
        static final AtomicLongFieldUpdater<TimeoutMainSubscriber> INDEX = AtomicLongFieldUpdater.newUpdater(TimeoutMainSubscriber.class, "index");

        TimeoutMainSubscriber(CoreSubscriber<? super T> actual, Publisher<?> firstTimeout, Function<? super T, ? extends Publisher<V>> itemTimeout, @Nullable Publisher<? extends T> other, @Nullable String timeoutDescription) {
            super(Operators.serialize(actual));
            this.itemTimeout = itemTimeout;
            this.other = other;
            this.timeoutDescription = timeoutDescription;
            this.firstTimeout = firstTimeout;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.set(s2);
                TimeoutTimeoutSubscriber timeoutSubscriber = new TimeoutTimeoutSubscriber(this, 0L);
                this.timeout = timeoutSubscriber;
                this.actual.onSubscribe(this);
                this.firstTimeout.subscribe(timeoutSubscriber);
            }
        }

        @Override
        protected boolean shouldCancelCurrent() {
            return true;
        }

        @Override
        public void onNext(T t2) {
            Publisher<V> p;
            this.timeout.cancel();
            long idx = this.index;
            if (idx == Long.MIN_VALUE) {
                this.s.cancel();
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            if (!INDEX.compareAndSet(this, idx, idx + 1L)) {
                this.s.cancel();
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            this.actual.onNext(t2);
            this.producedOne();
            try {
                p = Objects.requireNonNull(this.itemTimeout.apply(t2), "The itemTimeout returned a null Publisher");
            }
            catch (Throwable e) {
                this.actual.onError(Operators.onOperatorError(this, e, t2, this.actual.currentContext()));
                return;
            }
            TimeoutTimeoutSubscriber ts = new TimeoutTimeoutSubscriber(this, idx + 1L);
            if (!this.setTimeout(ts)) {
                return;
            }
            p.subscribe(ts);
        }

        @Override
        public void onError(Throwable t2) {
            long idx = this.index;
            if (idx == Long.MIN_VALUE) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            if (!INDEX.compareAndSet(this, idx, Long.MIN_VALUE)) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.cancelTimeout();
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            long idx = this.index;
            if (idx == Long.MIN_VALUE) {
                return;
            }
            if (!INDEX.compareAndSet(this, idx, Long.MIN_VALUE)) {
                return;
            }
            this.cancelTimeout();
            this.actual.onComplete();
        }

        void cancelTimeout() {
            IndexedCancellable s2 = this.timeout;
            if (s2 != CancelledIndexedCancellable.INSTANCE && (s2 = TIMEOUT.getAndSet(this, CancelledIndexedCancellable.INSTANCE)) != null && s2 != CancelledIndexedCancellable.INSTANCE) {
                s2.cancel();
            }
        }

        @Override
        public void cancel() {
            this.index = Long.MIN_VALUE;
            this.cancelTimeout();
            super.cancel();
        }

        boolean setTimeout(IndexedCancellable newTimeout) {
            IndexedCancellable currentTimeout;
            do {
                if ((currentTimeout = this.timeout) == CancelledIndexedCancellable.INSTANCE) {
                    newTimeout.cancel();
                    return false;
                }
                if (currentTimeout == null || currentTimeout.index() < newTimeout.index()) continue;
                newTimeout.cancel();
                return false;
            } while (!TIMEOUT.compareAndSet(this, currentTimeout, newTimeout));
            if (currentTimeout != null) {
                currentTimeout.cancel();
            }
            return true;
        }

        void doTimeout(long i) {
            if (this.index == i && INDEX.compareAndSet(this, i, Long.MIN_VALUE)) {
                this.handleTimeout();
            }
        }

        void doError(long i, Throwable e) {
            if (this.index == i && INDEX.compareAndSet(this, i, Long.MIN_VALUE)) {
                super.cancel();
                this.actual.onError(e);
            }
        }

        void handleTimeout() {
            if (this.other == null) {
                super.cancel();
                this.actual.onError(new TimeoutException("Did not observe any item or terminal signal within " + this.timeoutDescription + " (and no fallback has been configured)"));
            } else {
                this.set(Operators.emptySubscription());
                this.other.subscribe(new TimeoutOtherSubscriber(this.actual, this));
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

