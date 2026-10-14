/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.util.annotation.Nullable;

final class FluxDematerialize<T>
extends InternalFluxOperator<Signal<T>, T> {
    FluxDematerialize(Flux<Signal<T>> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super Signal<T>> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DematerializeSubscriber<T>(actual, false);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DematerializeSubscriber<T>
    implements InnerOperator<Signal<T>, T> {
        final CoreSubscriber<? super T> actual;
        final boolean completeAfterOnNext;
        Subscription s;
        boolean done;
        volatile boolean cancelled;

        DematerializeSubscriber(CoreSubscriber<? super T> subscriber, boolean completeAfterOnNext) {
            this.actual = subscriber;
            this.completeAfterOnNext = completeAfterOnNext;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(Signal<T> t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            if (t2.isOnComplete()) {
                this.s.cancel();
                this.onComplete();
            } else if (t2.isOnError()) {
                this.s.cancel();
                this.onError(t2.getThrowable());
            } else if (t2.isOnNext()) {
                this.actual.onNext(t2.get());
                if (this.completeAfterOnNext) {
                    this.onComplete();
                }
            }
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
            this.actual.onComplete();
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                this.s.request(n);
            }
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}

