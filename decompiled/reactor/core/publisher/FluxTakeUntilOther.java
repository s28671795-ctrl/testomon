/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxTakeUntilOther<T, U>
extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxTakeUntilOther(Flux<? extends T> source2, Publisher<U> other) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        TakeUntilMainSubscriber<? super T> mainSubscriber = new TakeUntilMainSubscriber<T>(actual);
        TakeUntilOtherSubscriber otherSubscriber = new TakeUntilOtherSubscriber(mainSubscriber);
        this.other.subscribe(otherSubscriber);
        return mainSubscriber;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TakeUntilMainSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "other");

        TakeUntilMainSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = Operators.serialize(actual);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        void setOther(Subscription s2) {
            if (!OTHER.compareAndSet(this, null, s2)) {
                s2.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            }
        }

        @Override
        public void request(long n) {
            this.main.request(n);
        }

        void cancelMainAndComplete() {
            Subscription s2 = this.main;
            if (s2 != Operators.cancelledSubscription()) {
                s2 = MAIN.getAndSet(this, Operators.cancelledSubscription());
                if (s2 != null && s2 != Operators.cancelledSubscription()) {
                    s2.cancel();
                }
                if (s2 == null) {
                    Operators.complete(this.actual);
                } else {
                    this.actual.onComplete();
                }
            }
        }

        void cancelOther() {
            Subscription s2 = this.other;
            if (s2 != Operators.cancelledSubscription() && (s2 = OTHER.getAndSet(this, Operators.cancelledSubscription())) != null && s2 != Operators.cancelledSubscription()) {
                s2.cancel();
            }
        }

        @Override
        public void cancel() {
            Subscription s2 = this.main;
            if (s2 != Operators.cancelledSubscription() && (s2 = MAIN.getAndSet(this, Operators.cancelledSubscription())) != null && s2 != Operators.cancelledSubscription()) {
                s2.cancel();
            }
            this.cancelOther();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (!MAIN.compareAndSet(this, null, s2)) {
                s2.cancel();
                if (this.main != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            } else {
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                Operators.error(this.actual, t2);
                return;
            }
            this.cancelOther();
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                Operators.complete(this.actual);
                return;
            }
            this.cancelOther();
            this.actual.onComplete();
        }
    }

    static final class TakeUntilOtherSubscriber<U>
    implements InnerConsumer<U> {
        final TakeUntilMainSubscriber<?> main;
        boolean once;

        TakeUntilOtherSubscriber(TakeUntilMainSubscriber<?> main) {
            this.main = main;
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.main.other == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.main.setOther(s2);
            s2.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(U t2) {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.cancelOther();
            this.main.cancelMainAndComplete();
        }

        @Override
        public void onError(Throwable t2) {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.cancelMainAndComplete();
        }
    }
}

