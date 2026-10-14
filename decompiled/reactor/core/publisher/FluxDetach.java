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
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxDetach<T>
extends InternalFluxOperator<T, T> {
    FluxDetach(Flux<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DetachSubscriber<T>(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DetachSubscriber<T>
    implements InnerOperator<T, T> {
        CoreSubscriber<? super T> actual;
        Subscription s;

        DetachSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override
        public Context currentContext() {
            return this.actual == null ? Context.empty() : this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.actual == null;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.actual == null && this.s == null;
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
        public void onNext(T t2) {
            CoreSubscriber<T> a = this.actual;
            if (a != null) {
                a.onNext(t2);
            }
        }

        @Override
        public void onError(Throwable t2) {
            CoreSubscriber<T> a = this.actual;
            if (a != null) {
                this.actual = null;
                this.s = null;
                a.onError(t2);
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void onComplete() {
            CoreSubscriber<T> a = this.actual;
            if (a != null) {
                this.actual = null;
                this.s = null;
                a.onComplete();
            }
        }

        @Override
        public void request(long n) {
            Subscription a = this.s;
            if (a != null) {
                a.request(n);
            }
        }

        @Override
        public void cancel() {
            Subscription a = this.s;
            if (a != null) {
                this.actual = null;
                this.s = null;
                a.cancel();
            }
        }
    }
}

