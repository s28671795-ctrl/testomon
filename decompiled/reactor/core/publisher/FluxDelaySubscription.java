/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
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

final class FluxDelaySubscription<T, U>
extends InternalFluxOperator<T, T>
implements Consumer<DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    FluxDelaySubscription(Flux<? extends T> source2, Publisher<U> other) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        this.other.subscribe(new DelaySubscriptionOtherSubscriber(actual, this));
        return null;
    }

    @Override
    public void accept(DelaySubscriptionOtherSubscriber<T, U> s2) {
        this.source.subscribe(new DelaySubscriptionMainSubscriber(s2.actual, s2));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DelaySubscriptionMainSubscriber<T>
    implements InnerConsumer<T> {
        final CoreSubscriber<? super T> actual;
        final DelaySubscriptionOtherSubscriber<?, ?> arbiter;

        DelaySubscriptionMainSubscriber(CoreSubscriber<? super T> actual, DelaySubscriptionOtherSubscriber<?, ?> arbiter) {
            this.actual = actual;
            this.arbiter = arbiter;
        }

        @Override
        public Context currentContext() {
            return this.arbiter.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
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
    }

    static final class DelaySubscriptionOtherSubscriber<T, U>
    extends Operators.DeferredSubscription
    implements InnerOperator<U, T> {
        final Consumer<DelaySubscriptionOtherSubscriber<T, U>> source;
        final CoreSubscriber<? super T> actual;
        Subscription s;
        boolean done;

        DelaySubscriptionOtherSubscriber(CoreSubscriber<? super T> actual, Consumer<DelaySubscriptionOtherSubscriber<T, U>> source2) {
            this.actual = actual;
            this.source = source2;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void cancel() {
            this.s.cancel();
            super.cancel();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(U t2) {
            if (this.done) {
                return;
            }
            this.done = true;
            this.s.cancel();
            this.source.accept(this);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.source.accept(this);
        }
    }
}

