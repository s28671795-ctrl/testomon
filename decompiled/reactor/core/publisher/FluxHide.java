/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxHide<T>
extends InternalFluxOperator<T, T> {
    FluxHide(Flux<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new HideSubscriber<T>(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SuppressFuseableSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        Subscription s;

        SuppressFuseableSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
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
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void clear() {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class HideSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        Subscription s;

        HideSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.s = s2;
            this.actual.onSubscribe(this);
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
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

