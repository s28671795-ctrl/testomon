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

final class FluxSkipUntilOther<T, U>
extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxSkipUntilOther(Flux<? extends T> source2, Publisher<U> other) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        SkipUntilMainSubscriber<? super T> mainSubscriber = new SkipUntilMainSubscriber<T>(actual);
        SkipUntilOtherSubscriber otherSubscriber = new SkipUntilOtherSubscriber(mainSubscriber);
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

    static final class SkipUntilMainSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<SkipUntilMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(SkipUntilMainSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<SkipUntilMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SkipUntilMainSubscriber.class, Subscription.class, "other");
        volatile boolean gate;

        SkipUntilMainSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = Operators.serialize(actual);
            this.ctx = actual.currentContext();
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
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

        @Override
        public void cancel() {
            Operators.terminate(MAIN, this);
            Operators.terminate(OTHER, this);
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
            if (this.gate) {
                this.actual.onNext(t2);
            } else {
                Operators.onDiscard(t2, this.ctx);
                this.main.request(1L);
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                Operators.error(this.actual, t2);
                return;
            }
            if (this.main == Operators.cancelledSubscription()) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.cancel();
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            Operators.terminate(OTHER, this);
            this.actual.onComplete();
        }
    }

    static final class SkipUntilOtherSubscriber<U>
    implements InnerConsumer<U> {
        final SkipUntilMainSubscriber<?> main;

        SkipUntilOtherSubscriber(SkipUntilMainSubscriber<?> main) {
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
            if (this.main.gate) {
                return;
            }
            SkipUntilMainSubscriber<?> m4 = this.main;
            m4.other.cancel();
            m4.gate = true;
            m4.other = Operators.cancelledSubscription();
        }

        @Override
        public void onError(Throwable t2) {
            SkipUntilMainSubscriber<?> m4 = this.main;
            if (m4.gate) {
                Operators.onErrorDropped(t2, this.main.currentContext());
                return;
            }
            m4.onError(t2);
        }

        @Override
        public void onComplete() {
            SkipUntilMainSubscriber<?> m4 = this.main;
            if (m4.gate) {
                return;
            }
            m4.gate = true;
            m4.other = Operators.cancelledSubscription();
        }
    }
}

