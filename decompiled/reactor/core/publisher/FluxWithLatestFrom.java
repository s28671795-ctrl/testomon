/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
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

final class FluxWithLatestFrom<T, U, R>
extends InternalFluxOperator<T, R> {
    final Publisher<? extends U> other;
    final BiFunction<? super T, ? super U, ? extends R> combiner;

    FluxWithLatestFrom(Flux<? extends T> source2, Publisher<? extends U> other, BiFunction<? super T, ? super U, ? extends R> combiner) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
        this.combiner = Objects.requireNonNull(combiner, "combiner");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        CoreSubscriber<? super R> serial = Operators.serialize(actual);
        WithLatestFromSubscriber<? super T, ? super U, ? extends R> main = new WithLatestFromSubscriber<T, U, R>(serial, this.combiner);
        WithLatestFromOtherSubscriber<? super U> secondary = new WithLatestFromOtherSubscriber<U>(main);
        this.other.subscribe(secondary);
        return main;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class WithLatestFromOtherSubscriber<U>
    implements InnerConsumer<U> {
        final WithLatestFromSubscriber<?, U, ?> main;

        WithLatestFromOtherSubscriber(WithLatestFromSubscriber<?, U, ?> main) {
            this.main = main;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.main.setOther(s2);
            s2.request(Long.MAX_VALUE);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
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
        public void onNext(U t2) {
            this.main.otherValue = t2;
        }

        @Override
        public void onError(Throwable t2) {
            this.main.otherError(t2);
        }

        @Override
        public void onComplete() {
            this.main.otherComplete();
        }
    }

    static final class WithLatestFromSubscriber<T, U, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "other");
        volatile U otherValue;

        WithLatestFromSubscriber(CoreSubscriber<? super R> actual, BiFunction<? super T, ? super U, ? extends R> combiner) {
            this.actual = actual;
            this.combiner = combiner;
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
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override
        public void request(long n) {
            this.main.request(n);
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

        @Override
        public void cancel() {
            this.cancelMain();
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
            U u = this.otherValue;
            if (u != null) {
                R r;
                try {
                    r = Objects.requireNonNull(this.combiner.apply(t2, u), "The combiner returned a null value");
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this, e, t2, this.actual.currentContext()));
                    return;
                }
                this.actual.onNext(r);
            } else {
                this.main.request(1L);
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                this.cancelOther();
                Operators.error(this.actual, t2);
                return;
            }
            this.cancelOther();
            this.otherValue = null;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.cancelOther();
            this.otherValue = null;
            this.actual.onComplete();
        }

        void otherError(Throwable t2) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                this.cancelMain();
                Operators.error(this.actual, t2);
                return;
            }
            this.cancelMain();
            this.otherValue = null;
            this.actual.onError(t2);
        }

        void otherComplete() {
            if (this.otherValue == null) {
                if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                    this.cancelMain();
                    Operators.complete(this.actual);
                    return;
                }
                this.cancelMain();
                this.actual.onComplete();
            }
        }
    }
}

